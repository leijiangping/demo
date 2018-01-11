var dataFrom = 0;
//已显示表格list
var showTableList = null;
$(document).ready(function() {
	initCurrentPage();
});

function initCurrentPage(){
	curPageNum = 1;
	getAddress();
}

//条件查询
function loadTableData(type) {
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	if(type == 0){
		dataFrom = 0;
		// 初始化表格,动态从服务器加载数据
		$("#tb").bootstrapTable({
			method : "post",
			contentType : "application/x-www-form-urlencoded",
			url : "queryEleccontractList", // 获取数据的地址
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
					pageNum: params.pageNumber,    
					pageSize: params.pageSize,
					contractItem : 	$('#contractName').val(),
		            pregId :	$("#city option:selected").val(),
		            regId :	$("#region option:selected").val(),
		            contractState :	$("#contractState option:selected").val(),
		            auditState : $("#auditState").val(),
		            dataFrom : dataFrom
				};
				return param;
			},
			columns: [{
				checkbox:true
			},{
	            field: 'contractCode',
	            title: '固化信息编码',
	            formatter:clickFormatter1
	        },{
	            field: 'contractName',
	            title: '固化信息名称'
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
	        	field: 'contractState',
	            title: '固化信息状态',
	            formatter:function(value,row,index){
	            	switch(value){
	    			case -2:
	    				contractState = "无效";
	    				break;	
	    			case -1:
	    				contractState = "删除";
	    				break;
	    			case 0:
	    				contractState = "正常";
	    				break;	
	    			case 1:
	    				contractState = "起草";
	    				break;
	    			case 2: 
	    				contractState ="履行完毕";
	    				break;
	    			case 3:
	    				contractState ="审批中";
	    				break;
	    			case 4:
	    				contractState = "审批完毕";
	    				break;	
	    			case 8:
	    				contractState = "异动";
	    				break;
	    			case 9: 
	    				contractState ="停用";
	    				break;
	    			default:
	    				contractState ="-";
	    				break;
	    			}
	            	return contractState;
	            }
	        }, {
	            field: 'auditingState',
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
	            field: 'contractStartdate',
	            title: '固化信息期始',
	            formatter : function(value){
	            	return new Date(value).format("yyyy-MM-dd");
	            }
	        }, {
	            field: 'contractEnddate',
	            title: '固化信息期终',
	        	formatter : function(value){
	        		return new Date(value).format("yyyy-MM-dd");
	            }
	        }, {
	            field: 'contractYearquantity',
	            title: '固化信息年限'
	        }
	        ],
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
		            "rows": res.obj.list //数据
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
			url : "queryEleccontractList", // 获取数据的地址
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
					pageNum: params.pageNumber,    
					pageSize: params.pageSize,
					contractItem : 	$('#contractName').val(),
		            pregId :	$("#city option:selected").val(),
		            regId :	$("#region option:selected").val(),
		            contractState :	$("#contractState option:selected").val(),
		            auditState : $("#auditState").val(),
		            dataFrom : dataFrom
				};
				return param;
			},
			columns: [{
				checkbox:true
			},{
	            field: 'contractCode',
	            title: '合同编码',
	            formatter:clickFormatter
	        },{
	            field: 'contractName',
	            title: '合同名称'
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
	        	field: 'contractState',
	            title: '合同状态',
	            formatter:function(value,row,index){
	            	switch(value){
	    			case -2:
	    				contractState = "无效";
	    				break;	
	    			case -1:
	    				contractState = "删除";
	    				break;
	    			case 0:
	    				contractState = "正常";
	    				break;	
	    			case 1:
	    				contractState = "起草";
	    				break;
	    			case 2: 
	    				contractState ="履行完毕";
	    				break;
	    			case 3:
	    				contractState ="审批中";
	    				break;
	    			case 4:
	    				contractState = "审批完毕";
	    				break;	
	    			case 8:
	    				contractState = "异动";
	    				break;
	    			case 9: 
	    				contractState ="停用";
	    				break;
	    			default:
	    				contractState ="-";
	    				break;
	    			}
	            	return contractState;
	            }
	        }, {
	            field: 'auditingState',
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
	            field: 'contractStartdate',
	            title: '合同期始',
	            formatter : function(value){
	            	return new Date(value).format("yyyy-MM-dd");
	            }
	        }, {
	            field: 'contractEnddate',
	            title: '合同期终',
	        	formatter : function(value){
	        		return new Date(value).format("yyyy-MM-dd");
	            }
	        }, {
	            field: 'contractYearquantity',
	            title: '合同年限'
	        }
	        ],
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
		            "rows": res.obj.list //数据
		         };
			}
		});
	}
}

// 新增合同
function addnewContract(type){
	var contractId;
	$.ajax({
		url : "getContractId",
		type : "post",
		dataType : 'json',
		async : false,
		contentType : 'application/json;charset=utf-8',
		success : function(data) {
			if(data != null){
				contractId = data.obj;
			}
		},
		error : function(data) {
			alertModel('获取信息失败!');
		}
	});
	localStorage.setItem("optype",1);
	window.location.href="editCuring.html?type="+type+"&contractId="+contractId;
	
}
//修改合同信息
function updateContract(type){
	var rows = getSelectItem(1);
	if(rows == null){
		return;
	}
	if(rows[0].contractState != 0){
		if(dataFrom == 0){
			alertModel("此固化信息已经停用，无法修改！");
		}else{
			alertModel("此合同已经停用，无法修改！");
		}
		return;
	}
	if(rows[0].auditingState == 9 || rows[0].auditingState == 0){
		alertModel("已经进入流程，无法修改！");
		return;
	}
	var elecontractId = rows[0].elecontractId;
	localStorage.setItem("elecontractId",elecontractId);
	localStorage.setItem("optype",2);
	var contractCode = rows[0].contractCode;
	localStorage.setItem("oragionCode",contractCode);
	var flagUpdateRent = $("#flagUpdateRent").val();
	localStorage.setItem("flagUpdateRent",flagUpdateRent);
	if(type == 2){
		window.location.href="edit.html?type="+type;
	}else{
		window.location.href="editCuring.html?type="+type;
	}
}


//删除合同
function deleteContract(){
	var rows = getSelectItem(0);
	if(rows == null){
		return;
	}
	for (var i = 0; i < rows.length; i++) {
		if(rows[0].auditingState == 9){
			alertModel("已经进入流程，无法删除！");
			return;
		}
		if(rows[0].auditingState == 0){
			alertModel("已审核通过，无法删除！");
			return;
		}
		delete rows[i]["0"];
	}
	var submitData = JSON.stringify(rows);
	if (confirm("确定删除?")) {
		$.ajax({
			url : "remove",
			type : "post",
			dataType : 'json',
			contentType : 'application/json;charset=utf-8',
			data : submitData,
			success : function(data) {
				alert('删除成功!');
				loadTableData(dataFrom);
    			localStorage.removeItem("optype");
				$('#controlAll').attr("checked", false);
			},
			error : function(data) {
				alertModel('删除失败!');
			}
		});
	}
}
//提交审核
function submitAudit(){
	var rows = getSelectItem(1);
	if(rows == null){
		return;
	}
	for (var s = 0; s < rows.length; s++) {
		var auditingState = rows[s].auditingState;
		if (auditingState == 9){
			alertModel("审核中的不能再次提交审核！");
			return;
		}else if(auditingState == 8){
			alertModel("审核未通过，无法提交审核。需修改信息后重新提交审核");
			return;
		}else if(auditingState == 0){
			alertModel("审核通过的无法提交审核");
			return;
		}
		
	}
	queryFirstUsersByProcDefKey(SelfElecAudit.procDefKey,"startActFlow", rows[0].regId);
}

/**
* 提交流程，开始审核流程
*/

function startActFlow(){
	$('#firstUsers').modal("hide");
	var rows = getSelectItem(0);

	var nextUserId=$("input[name='userChecked']:checked").val();
	var ids = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rows.length; s++) {
		var row = rows[s];

		var maxElePrice=0;
		 switch(row.priceType){
			case 0:
				maxElePrice=row.elecontractPrice != null ? row.elecontractPrice:0;
				break;	
			case 1:
				if(row.flatPrice != null&&row.flatPrice != ""&&row.flatPrice>maxElePrice){
					maxElePrice=row.flatPrice;
				}
				if(row.peakPrice != null&&row.peakPrice != ""&&row.peakPrice>maxElePrice){
					maxElePrice=row.peakPrice;
				}
				if(row.valleyPrice != null&&row.valleyPrice != ""&&row.valleyPrice>maxElePrice){
					maxElePrice=row.valleyPrice;
				}
				if(row.topPrice != null&&row.topPrice != ""&&row.topPrice>maxElePrice){
					maxElePrice=row.topPrice;
				}
				break;
			default:
				maxElePrice =0;
				break;
			}

		var obj={"id":row.contractId,"nextUserId":nextUserId,"maxPrice":maxElePrice};
		ids.push(obj);
	}
	
	var objButton=$(this);    
	$.ajax({
		url : 'submitAudit',// 跳转到 action    
		data :JSON.stringify(ids),
		type: "POST",
		dataType: "JSON",
		contentType : 'application/json;charset=utf-8',
		beforeSend:function(){//触发ajax请求开始时执行    
			objButton.val('提交审核中').attr('disabled',true);//改变提交按钮上的文字并将按钮设置为不可点击    
		},    
		success: function (back) {
			if(back != null){
				data = back.obj;
				if(data != null){
					$("#commit2").attr("disabled",false);
					alertModel("提交审核成功！");
					loadTableData(dataFrom);
				}
			}
		},
		error: function () { 
			$("#commmit2").attr("disabled",false);
			alertModel("提交审核信息异常！");
		},
		complete:function(){//ajax请求完成时执行    
			  objButton.val('提交审核').attr('disabled',false);//改变提交按钮上的文字并将按钮设置为可以点击    
		}    
	});
}

//续签合同
function renewContract(type){
	var rows = getSelectItem(1);
	if(rows == null){
		return;
	}
	var renewFlag = $("#renew").attr("data-ss");
	var auditState = rows[0].auditingState;
	var contractState = rows[0].contractState;
	if(contractState != 0){
		if(dataFrom == 0){
			alertModel("此固化信息已经停用，无法续签！");
		}else{
			alertModel("此合同已经停用，无法续签！");
		}
		return;
	}
	if(auditState == 9){
		alertModel("正在审核中，无法续签！");
		return;
	}
	if(auditState == 8){
		alertModel("审核未通过，无法续签！");
		return;
	}
	if(auditState == -1){
		alertModel("未审核，无法续签！");
		return;
	}
	var elecontractId = rows[0].elecontractId;
	localStorage.setItem("elecontractId",elecontractId);
	localStorage.setItem("renewFlag",renewFlag);
	localStorage.setItem("optype",3);
	var flagUpdateRent = $("#flagUpdateRent").val();
	localStorage.setItem("flagUpdateRent",flagUpdateRent);
	if(type == 2){
		window.location.href="edit.html?type="+type;
	}else{
		window.location.href="editCuring.html?type="+type;
	}
}

var flagChange = true;
//变更合同
function changeContract(type){
	var rows = getSelectItem(0);
	var elecontractId = rows[0].elecontractId;
	var auditState = rows[0].auditingState;
	var contractState = rows[0].contractState;
	if(contractState != 0){
		if(dataFrom == 0){
			alertModel("此固化信息已经停用，无法变更！");
		}else{
			alertModel("此合同已经停用，无法变更！");
		}
		return;
	}
	if(auditState != 0){
		alertModel("未通过审核，无法变更！");
		return;
	}
//	$.ajax({
//		url : 'queryBillaccountContract',// 跳转到 action    
//		data :{
//			elecontractId : elecontractId
//		},
//		type: "POST",
//		dataType: "JSON",
//		async : false,
//		contentType : 'application/json;charset=utf-8',
//		success: function (back) {
//			if(back != null){
//				data = back.obj;
//				console.log(data);
//				if(data != null && data.length > 0){
//					alertModel("此合同已经被关联，无法变更！");
//					flagChange = true;
//				}
//			}else{
//				flagChange = false;
//			}
//		},
//		error: function () { 
//			alertModel("获取信息异常！");
//		}    
//	});
//	if(flagChange){
//		return;
//	}
	localStorage.setItem("elecontractId",elecontractId);
	localStorage.setItem("optype",4);
	getAddress();
	var flagUpdateRent = $("#flagUpdateRent").val();
	localStorage.setItem("flagUpdateRent",flagUpdateRent);
	if(type == 2){
		window.location.href="edit.html?type="+type;
	}else{
		window.location.href="editCuring.html?type="+type;
	}
}
//导出
function exportData(data){
	dataFrom = data;
	var para="reg="+$('#contractName').val()+"&city="+$("#city option:selected").val()+"&dataFrom="+dataFrom;
	para+="&region="+$("#region option:selected").val()+"&status="+$("#contractState option:selected").val()+"&opType=0";
	para+="&auditStatus="+$("#auditState option:selected").val();
	window.open("export?"+para);
}

/**
 * add by jiacy
 * 查看用户信息
 */
function viewRecord(obj) {
	var elecId=$(obj).parent().next().text();
	window.location.href='view.html?elecContractId='+elecId;
}

function clickFormatter(value, row, index){
	return '<a href="view.html?elecContractId='+row.elecontractId+'">'+value+'</a>';
}
function clickFormatter1(value, row, index){
	return '<a href="view1.html?elecContractId='+row.elecontractId+'">'+value+'</a>';
}
