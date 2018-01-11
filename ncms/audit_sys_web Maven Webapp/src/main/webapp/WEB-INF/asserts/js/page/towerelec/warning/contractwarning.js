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
}

/**
 * 获取用户所有权限 的地市 区县信息
 * @param title 名称 例如：供应商地市，传入title为供应商
 */
function getAddress(title){
	$.ajax({
		type : "post",
		url : projectName + "/rent/common/getAddressRent",
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

var dataFrom = null;
//条件合同
function loadTableData(type) {
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	if(type == 0){
		dataFrom = 0;
		// 初始化表格,动态从服务器加载数据
		$("#tb").bootstrapTable({
			method : "post",
			contentType : "application/x-www-form-urlencoded",
			url : "queryEleccontractWarningList", // 获取数据的地址
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
		            contractItem : 	$('#contractName').val(),//合同编码或名称
		            pregId :	$("#City option:selected").val(),//地市id
		            regId :	$("#Village option:selected").val(),//区县id
		            contractStartdate :	$("#contractStartdate").val(),//开始时间
		            contractEnddate :	$("#contractEnddate").val(),//结束时间
		            managerDepartment : $("#managerDepartment option:selected").val(),//部门
		            isWarning	: $("#warningDate option:selected").val(),//是否已超提醒
		            dataFrom : dataFrom //数据来源
				};
				return param;
			},
			columns: [{
		        field: 'contractCode',
		        title: '固化信息代码',
		    }, {
		        field: 'contractName',
		        title: '固化信息名称'
		    }, {
		        field: 'contractStartdate',
		        title: '固化信息期始',
		        formatter : dateFormat
		    }, {
		        field: 'contractEnddate',
		        title: '固化信息期终',
		    	formatter : dateFormat
		    }, {
		        field: 'contractSigndate',
		        title: '签约日期',
		    	formatter : dateFormat
		    }, {
		    	field: 'contractAllMounth',
		        title: '固化信息总月数',
		        formatter : function(value){
		        	if(value != null && value != ""){
		        		return value+" 月";
		        	}else{
		        		return " 月";
		        	}
		        }
		    }, {
		        field: 'contractCheckname1',
		        title: '我方联系人'
		    }, {
		    	field: 'contractCheckname2',
		        title: '对方联系人',
		    }, {
		        field: 'pregName',
		        title: '所属地市'
		    }, {
		        field: 'regName',
		        title: '所属区县'
		    }, {
		    	field: 'isDownshare',
		        title: '是否向下共享',
		        formatter:function(value,row,index){
		        	switch(value){
		    		case 1:return '是';
		    		case 0:return '否';
		    	}
		    	return value;
		        }
		    }, {
		        field: 'contractTotalAmount',
		        title: '固化信息总金额',
		        formatter : function(value,row,index){
		        	if(value != null && value != ""){
		        		return value+" 元";
		        	}else{
		        		return " 元";
		        	}
		        }
		    }, {
		        field: 'contractResidueDays',
		        title: '剩余天数',
		        formatter : function(value,row,index){
		        	if(value != null){
		        		if(value == 0){
			        		return "已超期";
			        	}else{
			        		return value +" 天";
			        	}
		        	}
		        }
		    }, {
		    	field: 'managerDepartment',
	            title: '所属部门',
	            formatter : function(value,row,index){
	            	if(value != null && value != ''){
	            		return value;
	            	}else{
	            		return '-';
	            	}
	            }
	        }, {
		        field: 'contractType',
		        title: '固化信息类型',
		        formatter:function(value,row,index){
		        	switch(value){
		    		case 0:return '房租固化信息';
		    		case 1:return '电费固化信息';
		    		default : return '综合固化信息';
		        	}
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
					showTableList = res.obj.vEleContractList.result;
					if(res.obj.deptVO!=null){
						$('#managerDepartment').empty();
						var str = "<option value=''>-选择部门-</option>";
						
						$.each(res.obj.deptVO, function (i, item){
							str += "<option>" + res.obj.deptVO[i]+ "</option>";
						});
			            $("#managerDepartment").append(str);
					}
				}
		        return {
		            "total": res.obj.vEleContractList.total,//总页数
		            "rows": res.obj.vEleContractList.list //数据
		         };
			}
		});
	}
	else if(type == 2){
		dataFrom = 2;
		// 初始化表格,动态从服务器加载数据
		$("#tb").bootstrapTable({
			method : "post",
			contentType : "application/x-www-form-urlencoded",
			url : "queryEleccontractWarningList", // 获取数据的地址
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
		            contractItem : 	$('#contractName').val(),//合同编码或名称
		            pregId :	$("#City option:selected").val(),//地市id
		            regId :	$("#Village option:selected").val(),//区县id
		            contractStartdate :	$("#contractStartdate").val(),//开始时间
		            contractEnddate :	$("#contractEnddate").val(),//结束时间
		            managerDepartment : $("#managerDepartment option:selected").val(),//部门
		            isWarning	: $("#warningDate option:selected").val(),//是否已超提醒
		            dataFrom : dataFrom //数据来源
				};
				return param;
			},
			columns: [{
		        field: 'contractCode',
		        title: '合同代码',
		    }, {
		        field: 'contractName',
		        title: '合同名称'
		    }, {
		        field: 'contractStartdate',
		        title: '合同期始',
		        formatter : dateFormat
		    }, {
		        field: 'contractEnddate',
		        title: '合同期终',
		    	formatter : dateFormat
		    }, {
		        field: 'contractSigndate',
		        title: '签约日期',
		    	formatter : dateFormat
		    }, {
		    	field: 'contractAllMounth',
		        title: '合同总月数',
		        formatter : function(value){
		        	if(value != null && value != ""){
		        		return value+" 月";
		        	}else{
		        		return " 月";
		        	}
		        }
		    }, {
		        field: 'contractCheckname1',
		        title: '我方联系人'
		    }, {
		    	field: 'contractCheckname2',
		        title: '对方联系人',
		    }, {
		        field: 'pregName',
		        title: '所属地市'
		    }, {
		        field: 'regName',
		        title: '所属区县'
		    }, {
		    	field: 'isDownshare',
		        title: '是否向下共享',
		        formatter:function(value,row,index){
		        	switch(value){
		    		case 1:return '是';
		    		case 0:return '否';
		    	}
		    	return value;
		        }
		    }, {
		        field: 'contractTotalAmount',
		        title: '合同总金额',
		        formatter : function(value,row,index){
		        	if(value != null && value != ""){
		        		return value+" 元";
		        	}else{
		        		return " 元";
		        	}
		        }
		    }, {
		        field: 'contractResidueDays',
		        title: '剩余天数',
		        formatter : function(value,row,index){
		        	if(value != null){
		        		if(value == 0){
			        		return "已超期";
			        	}else{
			        		return value +" 天";
			        	}
		        	}
		        }
		    }, {
		    	field: 'managerDepartment',
	            title: '所属部门',
	            formatter : function(value,row,index){
	            	if(value != null && value != ''){
	            		return value;
	            	}else{
	            		return '-';
	            	}
	            }
	        }, {
		        field: 'contractType',
		        title: '合同类型',
		        formatter:function(value,row,index){
		        	switch(value){
		    		case 0:return '房租合同';
		    		case 1:return '电费合同';
		    		default : return '综合合同';
		        	}
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
					showTableList = res.obj.vEleContractList.result;
					if(res.obj.deptVO!=null){
						$('#managerDepartment').empty();
						var str = "<option value=''>-选择部门-</option>";
						
						$.each(res.obj.deptVO, function (i, item){
							str += "<option>" + res.obj.deptVO[i]+ "</option>";
						});
			            $("#managerDepartment").append(str);
					}
				}
		        return {
		            "total": res.obj.vEleContractList.total,//总页数
		            "rows": res.obj.vEleContractList.list //数据
		         };
			}
		});
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
	para+="&contractItem="+ $('#contractName').val();
	para+="&managerDepartment="+ $("#managerDepartment option:selected").val();
	para+="&isWarning="+ $("#warningDate option:selected").val();
	para+="&contractStartdate="+ $("#contractStartdate").val();
	para+="&contractEnddate="+ $("#contractEnddate").val();
	para+="&dataFrom="+dataFrom;
	window.open("exportEleContractWarning?"+para,"_blank");
}

function dateFormat(value,row,index){
	if(value != null){
		return new Date(value).format("yyyy-MM-dd");
	}else{
		return "-";
	}
}

