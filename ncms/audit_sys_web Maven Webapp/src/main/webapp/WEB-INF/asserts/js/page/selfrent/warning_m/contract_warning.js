//已显示表格list
var showTableList = null;
// 查询部门集合
var queryDeptList = null;

$(document).ready(function() {

	initCurrentPage();
});

function initCurrentPage(){
	curPageNum = 1;

	getAddress();
	// 查询默认条件表单数据
	loadTableData();
}

/**
 * 获取用户所有权限 的地市 区县信息
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
		url : "queryRentContractVOWarning", // 获取数据的地址
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
	            pregId : $("#City option:selected").val(),//地市id
	            regId :	$("#Village option:selected").val(),
	            contractStartdate :	$("#contractStartdate").val(),
	            contractEnddate :	$("#contractEnddate").val(),
	            managerDepartment : $("#managerDepartment option:selected").val(),
	            warningDate	: $("#warningDate option:selected").val()
			};
			return param;
		},
		columns: [{
            field: 'datContractVO.contractCode',
            title: '合同代码',
        }, {
            field: 'datContractVO.contractName',
            title: '合同名称'
        }, {
            field: 'datContractVO.contractStartdate',
            title: '合同期始',
            formatter : function(value,row,index){
            	return new Date(value).format("yyyy-MM-dd");
            }
        }, {
            field: 'datContractVO.contractEnddate',
            title: '合同期终',
        	formatter : function(value,row,index){
        		return new Date(value).format("yyyy-MM-dd");
            }
        }, {
            field: 'datContractVO.contractSigndate',
            title: '签约日期',
        	formatter : function(value,row,index){
        		return new Date(value).format("yyyy-MM-dd");
            }
        }, {
        	field: 'datContractVO.sumMouth',
            title: '合同总月数',
            formatter : function(value){
            	if(value != null){
            		return value+" 月"
            	}else{
            		return " 月"
            	}
            }
        }, {
            field: 'datContractVO.contractCheckname1',
            title: '我方联系人'
        }, {
        	field: 'datContractVO.contractCheckname2',
            title: '对方联系人',
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
            field: 'totalAmount',
            title: '合同总金额',
            formatter : function(value,row,index){
            	return value+" 元";
            }
        }, {
            field: 'datContractVO.surplusDay',
            title: '剩余天数',
            formatter : function(value){
            	if(value != null  && value != ''){
            		return value;	
            	}else{
            		return "";
            	}
            }
        }, {
        	field: 'datContractVO.managerDepartment',
            title: '所属部门',
            formatter : function(value,row,index){
            	if(value != null && value != ''){
            		return value;
            	}else{
            		return '-';
            	}
            }
        }, {
            field: 'datContractVO.contractType',
            title: '合同类型',
            formatter:function(value,row,index){
            	switch(value){
        		case 0:return '房租合同';
        		case 1:return '电费合同';
        	}
        	return value;
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
				showTableList = res.obj.pageVO.result;
				if(queryDeptList == null){
					queryDeptList = res.obj.deptVO;
					getQueryDeptList(queryDeptList);
				}
			}
	        return {
	            "total": res.obj.pageVO.total,//总页数
	            "rows": res.obj.pageVO.result //数据
	         };
		}
	});
}

function getQueryDeptList(list){
	if(list!=null){
		$('#managerDepartment').empty();
		var str = "<option value=''>-选择部门-</option>";
		
		$.each(list, function (i, item){
			str += "<option>" + list[i]+ "</option>";
		});
        $("#managerDepartment").append(str);
	}
}

/**
 * 导出
 */
function exportContractWarning(){
	confirmModel("您确定要导出吗？","exportInfo");
} 

function exportInfo(){
	var para="&pregId="+ $("#City").val();
	para+="&regId="+ $("#Village").val();
	para+="&contractName="+ $('#contractName').val();
	para+="&managerDepartment="+ $("#managerDepartment option:selected").val();
	para+="&warningDate="+ $("#warningDate option:selected").val();
	para+="&contractStartdate="+ $("#contractStartdate").val();
	para+="&contractEnddate="+ $("#contractEnddate").val();
	para+="&exportContractWarning=exportContractWarning";
	window.open("exportContractWarning?"+para,"_blank");
}

