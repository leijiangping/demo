
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
		url : "queryRentContractVOList", // 获取数据的地址
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
	            prvId	:	$("#Province option:selected").val(),
	            pregId :	$("#City option:selected").val(),
	            regId :	$("#Village option:selected").val(),
	            contractState :	$("#contractState option:selected").val(),
	            auditingState : $("#auditState option:selected").val()
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
            		case 0:return '审核通过';
            		case 8:return '审核未通过';
            		case 9:return '审核中';
            		case -1:return '未提交';
            	}
            	return value;
            }
        }, {
        	field: 'datContractVO.contractState',
            title: '合同状态',
            formatter:function(value,row,index){
            	switch(value){
	            	case -2:return '无效';
	            	case -1:return '删除';
	            	case 0:return '正常';
	            	case 1:return '起草';
	            	case 2:return '履行完毕';
	            	case 3:return '审批中';
	            	case 4:return '审批完毕';
	            	case 8:return '合同异动';
            		case 9:return '停用';
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
            	//当select组件只读的时候(向下共享为否)，保存的时候无法序列化该组件，这里暂时规避下该情况
            	if (null == value || value == 'null' || value == undefined){
            		return '否';
            	}
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
            title: '合同期终',
        	formatter : function(value){
        		return new Date(value).format("yyyy-MM-dd");
            }
        }, {
            field: 'datContractVO.contractYearquantity',
            title: '合同年限',
            formatter : function(value,row,index){
            	return value+" 年";
            }
        }, ],
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success != "1"){
		            alertModel(res.msg);
				}
				showTableList = res.obj.result;
			}
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.result //数据
	         };
		}
	});
}

function reviewContract(){
	if(!hadCheckedRowData()){
		return false;
	}
	localStorage.setItem("item1",rowschecked[0].rentcontractId);
	window.location.href='Info_check.html';
	
}

//终止合同
function stopContract(){
	if(!hadCheckedRowData()){
		return;
	}
	var contractState = rowschecked[0].datContractVO.contractState;
	if(contractState != 0){
		alertModel("此合同已经停用！");
		return;
	}
	confirmModel('确定终止合同!','stopCont');
}

function stopCont(){
	var contractIds = '';
	for(var i = 0 ; i < rowschecked.length ; i++){
		contractIds += rowschecked[i].datContractVO.contractId+",";
	}
	$.ajax({
	    type: "POST",
	    url: "stopContract",
	    data: {
	    	contractIds : contractIds
	    },
	    dataType: "JSON",
	    success: function (back) {
	    	if(back != null){
    			alertModel("合同终止成功！");
    			loadTableData();
	    	}
	    },
	    error: function () { alertModel("终止合同失败！"); }
	});
}

// 新增合同
function addnewContract(){
	localStorage.removeItem("rentcontractId");
	localStorage.removeItem("renewFlag");
	localStorage.removeItem("item1");
	window.location.href='contract_addnew.html';
}

//修改合同信息
function updateContract(){
	if(!hadCheckedRowData()){
		return;
	}
	var auditState = rowschecked[0].datContractVO.auditingState;
	var contractState = rowschecked[0].datContractVO.contractState;
	if(contractState != 0){
		alertModel("此合同已经停用，无法修改！");
		return;
	}
	if(auditState == 0 || auditState == 9){
		alertModel("此合同已经进入流程，无法修改！");
		return;
	}
	var msg = rowschecked[0].rentcontractId;
	localStorage.setItem("rentcontractId",msg);
	window.location.href="contract_addnew.html";
}

//删除合同
function deleteContract(){
	if(!hadCheckedRowData()){
		return;
	}
	var auditState = rowschecked[0].datContractVO.auditingState;
	if(auditState == 0 || auditState == 9){
		alertModel("此合同已经进入流程，无法删除！");
		return;
	}
	confirmModel('确定删除!','deleteCont');
}

function deleteCont(){
	var contractIds = '';
	var rentcontractIds = '';
	var supplierIds = '';
	for(var i = 0 ; i < rowschecked.length ; i++){
		contractIds += rowschecked[i].datContractVO.contractId+",";
		rentcontractIds += rowschecked[i].rentcontractId+",";
	}
	$.ajax({
	    type: "POST",
	    url: "deleteContract",
	    data: {
	    	contractIds : contractIds,
	    	rentcontractIds : rentcontractIds
	    },
	    dataType: "JSON",
	    success: function (back) {
	    	if(back != null){
    			alertModel("删除合同成功");
    			loadTableData();
	    	}
	    },
	    error: function () { 
	    	alertModel("删除合同失败！已发生业务不能删除"); 
	    }
	});
}

// 续签合同
function renewContract(renewFlag){
	if(!hadCheckedRowData()){
		return;
	}
	var renewFlag = $("#renew").attr("data-ss");
	var auditState = rowschecked[0].datContractVO.auditingState;
	var contractState = rowschecked[0].datContractVO.contractState;
	if(contractState != 0){
		alertModel("此合同已经停用，无法续签！");
		return;
	}
	if(auditState == 9){
		alertModel("此合同正在审核中，无法续签！");
		return;
	}
	if(auditState == -1){
		alertModel("此合同未审核，无法续签！");
		return;
	}
	var rentcontractId = rowschecked[0].rentcontractId;
	localStorage.setItem("rentcontractId",rentcontractId);
	//用来判断是维护页面提交续签还是查询页面提交续签
	localStorage.setItem("renewFlag",renewFlag);
	window.location.href='contract_renew.html';
}




//提交审核
function SubmitAudit(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var stateObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		var regId = rowschecked[0].datContractVO.regId;
		if(regId != row.datContractVO.regId){
			alertModel("只能批量提交同一个地区的合同！");
			return;
		}
		stateObjs.push(row.datContractVO.auditingState);
	}
	// 判断是否有"审核未通过"的
	if($.inArray(8, stateObjs)>=0){
		alertModel("审核未通过的合同，无法提交审核。需修改信息后重新提交审核");
		return false;
	}
	// 判断是否有"审核中"的
	if($.inArray(9, stateObjs)>=0){
		alertModel("审核中的无法提交审核");
		return false;
	}
	// 判断是否有"审核通过"的
	if($.inArray(0, stateObjs)>=0){
		alertModel("审核通过的无法提交审核");
		return false;
	}
	queryFirstUsersByProcDefKey(SelfRentAudit.procDefKey,"formSubmit",rowschecked[0].datContractVO.regId);	
}

function formSubmit(){
	
	$('#firstUsers').modal("hide");
	var rentcontractIds = new Array();
	var objButton=$("#commit2"); 
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		rentcontractIds.push(row.rentcontractId);
	}
	var nextUserId=$("input[name='userChecked']:checked").val();

	var datasObjs = new Array();
	for(var i = 0; i < rentcontractIds.length; i++){
		var id = rentcontractIds[i];
		var obj={"id":id,"nextUserId":nextUserId};
		datasObjs.push(obj);
	}

	$.ajax({
		url : 'SubmitAudit',// 跳转到 action    
		data :JSON.stringify(datasObjs),
		type : 'post',
		dataType : 'json',
		contentType : 'application/json;charset=utf-8',
		beforeSend:function(){//触发ajax请求开始时执行    
        	objButton.val('提交审核中').attr('disabled',true);//改变提交按钮上的文字并将按钮设置为不可点击    
        },  
		success : function(res) {
			if (res != null) {
				alertModel(res.msg);
				loadTableData();
			}
			$('#EditPanel').modal('hide');
			
		},
		error : function() {
			alertModel("请求失败");
			$("#commit2").attr("disabled",false);
		},
		complete:function(){//ajax请求完成时执行    
            objButton.val('提交审核').attr('disabled',false);//改变提交按钮上的文字并将按钮设置为可以点击    
      }
	});
}

/**
 * 导出
 */
function exportContractInfo(){
	confirmModel("您确定要导出吗？","exportInfo");
} 

function exportInfo(){
	var para="&pregId="+ $("#City").val();
	para+="&regId="+ $("#Village").val();
	para+="&contractName="+ $('#contractName').val();
	para+="&contractState="+ $("#contractState option:selected").val();
	para+="&auditingState="+ $("#auditState option:selected").val();
	window.open("exportContractInfo?"+para,"_blank");
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