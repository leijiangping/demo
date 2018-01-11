//已显示表格list
var showTableList = null;
$(document).ready(function() {
	initCurrentPage();
});

function initCurrentPage(){
	curPageNum = 1;
	getAddress();
	loadTableData();
}

/**
 * 获取用户所有权限 的地市 区县信息
 * 
 * @param title 名称 例如：供应商地市，传入title为供应商
 */
function getAddress(title){
	$.ajax({
		type : "post",
		url : "getAddressRent",
		
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		success : function(value) {
			if(value != null){
				sysReguins = value.obj;
				if(sysReguins!=null){
					$('#City').empty();
					$('#Village').empty();
					var strCity = "<option value=''>-选择"+(title?title:"")+"地市-</option>";
					$.each(sysReguins, function (i, item){
						strCity += "<option value='" +item.regId+"'>"+item.regName+ "</option>";
						
					});

					$("#City").append(strCity);

					var strReg = "<option value=''>-选择"+(title?title:"")+"区县-</option>";
					$("#Village").append(strReg);
					//绑定联动事件 修改人zhujj
					$("#City").change(function(){
						$("#Village").empty();
						strReg = "<option value=''>-选择"+(title?title:"")+"区县-</option>";
						if($("#City").val()!=""){
							$.each(sysReguins, function (i, item){
								if(item.regId==$("#City").val()){
									$.each(item.child, function (j, itemchild){
										strReg += "<option value='" + itemchild.regId+"'>"+itemchild.regName+ "</option>";
									});
								}
							});
						}
						$("#Village").append(strReg);
					});
					
				}
			}
		},
		error : function(data) {
			alertModel('获取用户地区信息失败!');
		}
	});

}


//条件合同
function loadTableData() {
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryRentContractVO", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		pageSize : ipageCount, // 每页显示的记录数
		pageNumber : curPageNum, // 当前第几页
		minimumCountColumns: 1,  //最少允许的列数
		clickToSelect: true,  //是否启用点击选中行
		pageList : [10, 25, 50, 100, 500], // 记录数可选列表
		search : false, // 是否启用查询
		sidePagination : "server", // 表示服务端请求
		// 设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
		// 设置为limit可以获取limit, offset, search, sort, order
		queryParamsType : "undefined",
		queryParams : function queryParams(params) { // 设置查询参数
			var param = {
				pageNumber: params.pageNumber,    
	            pageSize: params.pageSize,
	            contractName : 	$('#contractName').val(),
	            pregId :	$("#City option:selected").val(),
	            regId :	$("#Village option:selected").val(),
	            contractState :	$("#contractState option:selected").val(),
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
            field: 'datContractVO.contractCode',
            title: '合同代码',
            formatter:clickFormatter
        }, {
            field: 'datContractVO.contractName',
            title: '合同名称'
        }, {
        	field: 'datContractVO.auditingState',
            title: '审核状态',
            formatter:function(value){
            	switch(value){
            		case 1:return '已审核';
            		case 9:return '审核中';
            		case -1:return '未审核';
            	}
            	return value;
            }
        }, {
        	field: 'datContractVO.contractState',
            title: '合同状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 0:return '正常';
            		case 9:return '终止';
            		case -1:return '已删除';
            	}
            	return value;
            }
        }, {
            field: 'datContractVO.pregName',
            title: '所属地市'
        }, {
            field: 'datContractVO.regName',
            title: '所属区县'
        }, {
        	field: 'datContractVO.isDownShare',
            title: '是否向下共享',
            formatter:function(value,row,index){
            	switch(value){
        		case 1:return '是';
        		case 0:return '否';
        	}
        	return value;
            }
        }, {
            field: 'datContractVO.contractType',
            title: '合同分类',
            formatter:function(value,row,index){
            	switch(value){
        		case 0:return '房租合同';
        		case 1:return '电费合同';
        	}
        	return value;
            }
        }, {
            field: 'datContractVO.contractStartdate',
            title: '合同期始',
            formatter : function(value){
            	return new Date(value).format("yyyy-MM-dd");
            }
        }, {
            field: 'datContractVO.contractEnddate',
            title: '合同终止',
        	formatter : function(value){
        		return new Date(value).format("yyyy-MM-dd");
            }
        }, {
            field: 'datContractVO.contractYearquantity',
            title: '合同年限'
        }, ],
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				showTableList = res.obj.result;
				unique(showTableList);
			}
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.result //数据
	         };
		}
	});
}

//加载不重复的流程环节
function unique(arr){
	// 遍历arr，把元素分别放入tmp数组(不存在才放)
	$("#taskDefKey").empty();
	$("#taskDefKey").append("<option value=''>---审核环节---</option>");
	
	var tmp = new Array();
	for(var i in arr){
		//该元素在tmp内部不存在才允许追加
		if(arr[i].act!=null&&tmp.indexOf(arr[i].act.taskDefKey)==-1){
			$("#taskDefKey").append("<option value='"+arr[i].act.taskDefKey+"'>"+arr[i].act.taskName+"</option>");
			tmp.push(arr[i].act.taskDefKey);
		}
	}
	return tmp;
}

/**
 * 合同审核按钮
 */
function reviewContract(){
	if(!hadCheckedRowData()){
		return false;
	}
	var rentcontractId=rowschecked[0].rentcontractId;
	var taskId=rowschecked[0].act.taskId;
	localStorage.setItem("item1",rowschecked[0].rentcontractId);
	window.location.href='Info_check.html?rentcontractId='+rentcontractId+'&taskId='+taskId;
}

function SubmitAudit(){
	if(!hadCheckedRowData()){
		return;
	}
	var msg = rowschecked[0].rentcontractId;
	var objButton=$("#commmit2");    
	$.ajax({
	        type: "POST",
	        url: "SubmitAudit",
	        data: {
	        	id : msg
	        },
	        dataType: "JSON",
	        beforeSend:function(){//触发ajax请求开始时执行    
	        	objButton.val('提交审核中').attr('disabled',true);//改变提交按钮上的文字并将按钮设置为不可点击    
	        },    
	        success: function (back) {
	        	if(back != null){
	        		data = back.obj;
	        		if(data != null){
	        			alertModel("提交审核成功！");
	        			loadTableData();
	        		}
	        	}
	        },
	        error: function () { 
	        	alertModel("获取区县信息异常！");
	        	$("#commmit2").attr("disabled",false);
	        },
	        complete:function(){//ajax请求完成时执行    
	              objButton.val('提交审核').attr('disabled',false);//改变提交按钮上的文字并将按钮设置为可以点击    
	        }    
	    });
}

function updateContract(){
	if(!hadCheckedRowData()){
		return;
	}
	var msg = rowschecked[0].rentcontractId;
	localStorage.setItem("rentcontractId",msg);
	window.location.href="Info_update.html";
}

function update(){
	if(!hadCheckedRowData()){
		return;
	}
	var msg = rowschecked[0].rentcontractId;
	localStorage.setItem("rentcontractId",msg);
	window.location.href="Info_collection.html";
}


function hadCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}

function clickFormatter(value, row, index){
	return '<a href="rentcontractDetail.html?rentcontractId='+row.rentcontractId+'">'+row.datContractVO.contractCode+'</a>';
}