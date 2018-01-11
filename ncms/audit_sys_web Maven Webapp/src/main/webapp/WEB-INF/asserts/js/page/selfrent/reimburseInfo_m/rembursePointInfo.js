//已显示表格list
var showTableList = null;

$(document).ready(function() {
	initCurrentPage();
});

function initCurrentPage(){
	curPageNum = 1;
	// 查询默认条件表单数据
	loadTableData();
	getAddress();
}
//条件合同
function loadTableData() {
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryRembursePointInfo", // 获取数据的地址
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
		            billAccountCodeOrName : 	$('#billAccountCodeOrName').val(),
		            pregId :	$("#City option:selected").val(),
		            regId :	$("#Village option:selected").val(),
		            billAccountState :	$("#billAccountState option:selected").val(),
		            billaccountAuditState :	$("#billaccountAuditState option:selected").val()
				};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
            field: 'auditState',
            title: '审批状态',
            formatter:function(value,row,index){
            	switch(value){
            	case 0:return '审核通过';
        		case 8:return '审核未通过';
        		case 9:return '审核中';
        		case -1:return '未审核';
        		default:return '/';
            	}
            	return value;
            }
        }, {
            field: 'billAccountCode',
            title: '报账点编码'
        }, {
            field: 'billAccountName',
            title: '报账点名称'
        }, {
            field: 'sysRegionVO.pRegName',
            title: '所属地市'
        }, {
            field: 'sysRegionVO.regName',
            title: '所属区县'
        }, {
            field: 'billAccountState',
            title: '报账点状态',
            	formatter:function(value,row,index){
                	switch(value){
                		case 0:return '正常';
                		case 9:return '终止';
                		default:return '/';
                	}
                	return value;
                }
        }, /*{
            field: 'datContractVO.contractStartdate',
            title: '报账点资源关系'
        },*/ ],
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

function addRembursePoint(){
	window.location.href='addRembursePoint.html';
}
function modifyRembursePoint(){
	if(!haveCheckedRowData()){
		return false;
	}
	var auditState = rowschecked[0].auditState;
	var billAccountState = rowschecked[0].billAccountState;
	if(billAccountState != 0){
		alertModel("此报账点已经停用，无法修改！");
		return;
	}
	if(auditState != -1){
		alertModel("此报账点已经进入流程，无法修改！");
		return;
	}
	var billAccountId=rowschecked[0].billAccountId;
	window.location.href='updateRembursePoint.html?billAccountId='+billAccountId;
	
}
function haveCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}

function deleteBillAcount(){
	if(!haveCheckedRowData()){
		return false;
	}
	var auditState = rowschecked[0].auditState;
	if(auditState != -1){
		alertModel("此报账点已经进入流程，无法删除！");
		return;
	}
	confirmModel('确定删除!','deleteBillPoint');
}

function deleteBillPoint(){
	$.ajax({
	    url:'deleteBillAcount',
	    data: {
	    	billAccountId:rowschecked[0].billAccountId,
	    },
 		type : 'post',
	    cache:false,
	    async:true,
	    success:function(){
	    	window.location.href='reimbursePointManage.html';
	    },
	    error:function(){
			alertModel("请求失败！");
	    }
	});
}

//提交审批
function submitApproval(){
	if(!haveCheckedRowData()){
		return false;
	}
	if(rowschecked[0].auditState==9){
		alertModel("该数据正在审核，不能再次提交审核");
		return false;
	}
	if(rowschecked[0].auditState==0){
		alertModel("该数据已经审核完成，不能再次提交审核");
		return false;
	}
	if(rowschecked[0].auditState==8){
		alertModel("该数据审核未通过，请进行修改");
		return false;
	}
	queryFirstUsersByProcDefKey(RembursePoint.procDefKey,"submitAudit",rowschecked[0].regId);	
}

function submitAudit(){
	var ids=new Array();
	for(var i=0;i<rowschecked.length;i++){
		ids.push(rowschecked[i].billAccountId);
	}
	var nextUserId=$("input[name='userChecked']:checked").val();
	
	var datasObjs = new Array();
	for(var i = 0; i < ids.length; i++){
		var id = ids[i];
		var obj={"id":id,"nextUserId":nextUserId};
		datasObjs.push(obj);
	}
//	var objButton=$("#commit2");    
	$.ajax({
	        type: "POST",
	        url: "billAccountSubmitAudit",
	        data: JSON.stringify(datasObjs),
	        dataType: "JSON",
	        contentType : 'application/json;charset=utf-8',
	       /* beforeSend:function(){//触发ajax请求开始时执行    
	        	objButton.val('提交审核中').attr('disabled',true);//改变提交按钮上的文字并将按钮设置为不可点击    
	        },    */
	        success: function (back) {
	        	if(back != null){
	        		data = back.obj;
	        		if(data != null){
	        			alertModel("提交审核成功！");
	        			loadTableData();
	        		}
	        		$('#firstUsers').modal('hide');
	        	}
	        },
	        error: function () { 
	        	alertModel("提交失败！");
//	        	$("#commit2").attr("disabled",false);
	        },
	     /*   complete:function(){//ajax请求完成时执行    
	              objButton.val('提交审核').attr('disabled',false);//改变提交按钮上的文字并将按钮设置为可以点击    
	        }  */  
	    });
}

function exportExcel(){
	confirmModel("您确定要导出吗？","exportRembursePointInfo");
}
function exportRembursePointInfo(){
	var para="billAccountState="+$("#billAccountState option:selected").val();
	para+="&billaccountAuditState="+$("#billaccountAuditState option:selected").val();
	para+="&regId="+ $("#Village option:selected").val();
	window.open("exportRembursePointInfo?"+para,"_blank");
}

function importFile(){
	importModel("报账点信息导入","_towerBillbalance","formSubmit");//弹出导入框
}

/**
 * 上传文件并保存到数据库
 * @param suffix 模块标识
 */
function formSubmit(suffix){
	var formData = new FormData($("#dataForm")[0]); 
	$.ajax({
		url:'importFile',
		type : 'post',
		data : formData,
	    async: true,  
     	cache: false,  
        contentType: false,  
        processData: false, 
 		beforeSend:function(){//启动a
 			startTimeJob(suffix);
        },  
 		success : function(result){
			stopTimeJob();//停止进度条
 			if(result != null&&result.success=="1"){
 					loadTableData();
 			}else if(result != null&&result.success=="0"){
 				//出错
 			}
				alertModel(result.msg);
 		},
        complete:function(){//ajax请求完成时执行    
			stopTimeJob();//停止进度条
        },
 		error : function() {
			stopTimeJob();//停止进度条
			alertModel("请求失败！");
 		}
	});
}