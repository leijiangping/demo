$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	queryCurUser();
	getUpdateParam();
	//下级审核人
	var twrRegPunishId=getParameter("twrRegPunishId");
	var regId=getParameter("regId");
	findUsersByRoleIds(TwrRegPunish.tableName,twrRegPunishId,regId);
	//流转记录
	histoicFlowList(TwrRegPunish.tableName,twrRegPunishId);
}

/**
 * 编辑详情信息表单
 * */
function getUpdateParam(){
	var twrRegPunishId = getParameter("twrRegPunishId");
	$.ajax({
	    url:'queryTwrRegPunishId',
	    data: {
	    	twrRegPunishId:twrRegPunishId
	    },
 		type : 'post',
	    cache:false,
	    async:true,
	    success:function(result){
	    	var datas = result.Obj;
	    	if(result!=null){
	    		$('#regId').html(datas.sysRegionVO.pRegName);
	    		$('#punishYearMonth').html(datas.punishYearMonth);
	    		$('#punishName').html(datas.punishName);
	    		$('#punishPerson').html(datas.punishPerson);
	    		$('#punishAmount').html(datas.punishAmount);
	    		$('#punishCause').html(datas.punishCause);
	    	}
	    },
	    error:function(){
	    	alertModel("请求失败！");
	    }
	});
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
		
	var twrRegPunishIds = new Array();
	// 从选中行中挑出可以启用的item
	var obj={
    	"auditState":auditState,
    	"nextAuditUserId":nextUserId,
    	"auditNote":auditNote,
    	"taskId":taskId,
    	"twrRegPunishId":getParameter("twrRegPunishId")
    };
	$("#saveSet").attr("disabled",true);
	twrRegPunishIds.push(obj);
	$.ajax({
 		url : 'updateCompleteTwrRegPunish',
 		data : JSON.stringify(twrRegPunishIds),
		type : 'post',
		cache : false,
		dataType : 'json',
		contentType: "application/json;charset=utf-8",
 		success : function(data) {
 			if (data != null) {
				alertModel(data.msg);
				window.location.href='cityExaminationAudit.html';
			}
 			$("#saveSet").attr("disabled",false);
 		},
 		error : function() {
			alertModel("请求失败！");
			$("#saveSet").attr("disabled",false);
 		}
 	});
}
