$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	queryCurUser();
	//下级审核人
	var contractId=getParameter("contractId");
	findUsersByRoleIds(SelfElecAudit.tableName,contractId);
	//流转记录
	histoicFlowList(SelfElecAudit.tableName,contractId);
}
function queryCurUser(){
	$.ajax({
		url:projectName+'/asserts/tpl/getCurrUser',
		data:{
			conutn:1
		},
		type:'get',
		dataType:'json',
		success:function(back){
			if (back != null) {
				item=back.obj;
				if(back.success=="1"){
					$("#curUser").text(item.user_loginname);
				}
			}
		},
		error:function(){
			alertModel("请求异常");
		}
	})
}
function changeSave(){
	if($("#nextUsers option").length > 1 && $("#auditState option:selected").val()=='0'){
		if($('#nextUsers option:selected').val() == ""){
			$("#nextUsers").next("#err").html("<img src=\"../../../image/error.png\"/>必选！");
			$("#nextUsers").next("#err").css({"color":"red","float":"left"});
			flag=true;
		}else{
			$("#nextUsers").next("#err").html("<img src=\"../../../image/right_1.png\"/>验证成功");
			$("#nextUsers").next("#err").css({"color":"green","float":"left"});
			flag=false;
		}
	}else{
		flag=false;
	}
	if($("#auditState option:selected").val()==8){
		if($("#auditNote").val()==""){
			$("#auditNote").next("#err").html("<img src=\"../../../image/error.png\"/>必填！");
			$("#auditNote").next("#err").css({"color":"red"});
			flag=true;
		}else{
			$("#auditNote").next("#err").html("<img src=\"../../../image/right_1.png\"/>验证成功");
			$("#auditNote").next("#err").css({"color":"green"});
			flag=false;
		}		
	}
}

/**
 * 审核表单保存
 * */
function auditNewsFormSubmit(){
	changeSave();
	if(flag){
		return false;
	}	
	var auditState=$('#auditState option:selected').val();//审核状态
	var nextUserId=$('#nextUsers').val();//指定下级审核人
	var auditNote=$('#auditNote').val();//审核意见
	var taskId=getParameter("taskId");
	var ids = new Array();
	// 从选中行中挑出可以启用的item
	var obj={
    	"auditState":auditState,
    	"nextAuditUserId":nextUserId,
    	"auditNote":auditNote,
    	"taskId":taskId,
    	"id":getParameter("contractId")
    };
	ids.push(obj);
	$.ajax({
 		url : 'auditContract',
 		data : JSON.stringify(ids),
		type : 'post',
		cache : false,
		dataType : 'json',
		contentType: "application/json;charset=utf-8",
 		success : function(data) {
 			if (data != null) {
				alertModel(data.msg);
				window.location.href='audit.html';
			}
 		},
 		error : function() {
			alertModel("请求失败！");
 		}
 	});
}
