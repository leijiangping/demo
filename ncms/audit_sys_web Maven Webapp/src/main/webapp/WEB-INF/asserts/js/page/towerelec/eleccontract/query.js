
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
var dataFrom = null;
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
	            title: '固化信息终止',
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
	            title: '合同终止',
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
//导出
function exportData(){
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