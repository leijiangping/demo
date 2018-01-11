$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	queryCurUser();
	getUpdateParam();
	getAuditState();

}
function getAuditState(){
	var auditState = getParameter("auditState");
	if(auditState == -1){
		findFirstUsersByProcDefKey(TwrRegPunish.procDefKey);
	}else{
		//下级审核人
		$("#conductAudit_title").remove();
		$("#conductAudit_operation").remove();
	}
	if(auditState == 8){
		//审核未通过
		$(".detailsSubmit").css("display","block");
	}
	
	if(auditState == -1){
		//未提交
		$("#detailsAuditSubmit").css("display","block");
	}
	
	if(auditState == 9||auditState == 0){
		$(".detailsSubmit").css("display","none");
	}
	//流转记录
	var twrRegPunishId=getParameter("twrRegPunishId");
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
	    		if(datas.auditState == 8){
	    			$('#regId').html(datas.sysRegionVO.pRegName);
		    		//$('#punishYearMonth').html(datas.punishYearMonth);
		    		$('#punishYearMonth').html("");
	    			$("#punishName").html("");
	    			$("#punishPerson").html("");
	    			$('#punishAmount').html("");
	    			$("#punishCause").attr("readonly",false);
	    			$('#punishCause').html(datas.punishCause);
	    			var str0 = "<input type='text' class='form-control' id='datetimepicker1' name='datetimepicker1' style='float:left' value='"+datas.punishYearMonth+"'/><span id='err' class='modal-error'></span>";
	    			$("#punishYearMonth").append(str0);
	    			getUpdateTime();
	    			var str1 = "<input type='text' class='form-control' id='punishName1' name='punishName1' style='float:left' value='"+datas.punishName+"'/><span id='err' class='modal-error'></span>";
	    			$("#punishName").append(str1);
	    			var str2 = "<input type='text' class='form-control' id='punishAmount1' name='punishAmount1' style='float:left' value='"+datas.punishAmount+"'/><span id='err' class='modal-error'></span>";
	    			$("#punishAmount").append(str2);
	    			var str3 = "<input type='text' class='form-control' id='punishPerson1' name='punishPerson1' style='float:left' value='"+datas.punishPerson+"'/><span id='err' class='modal-error'></span>";
	    			$("#punishPerson").append(str3);
	    		}else{
		    		$('#regId').html(datas.sysRegionVO.pRegName);
		    		$('#punishYearMonth').html(datas.punishYearMonth);
		    		$('#punishName').html(datas.punishName);
		    		$('#punishPerson').html(datas.punishPerson);
		    		$('#punishAmount').html(datas.punishAmount);
		    		$('#punishCause').html(datas.punishCause);
	    		}
	    	}
	    },
	    error:function(){
	    	alertModel("请求失败！");
	    }
	});
}

function getUpdateTime(){
	$('#datetimepicker1').datetimepicker({
    	format: 'YYYY/M',
		locale: moment.locale('zh-cn')
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
	});
}

//验证
function validform(){

	var twr_validate= bindformvalidate("#dataForm", {
		datetimepicker1:{
			required : true
		},
		punishName1:{
			required : true
		},
		punishAmount1:{
			required : true
		},
		punishPerson1:{
			required : true
		},
		punishCause:{
			required : true,
			maxlength : 300,
		}
	}, {
		datetimepicker1:{
			required : "必选！"
		},
		punishName1:{
			required : "必填！"
		},
		punishAmount1:{
			required : "必填！"
		},
		punishPerson1:{
			required : "必填！"
		},
		punishCause:{
			required : "必填！",
			maxlength: "详情说明最大长度300"
		},
	});
  return twr_validate;
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
 * 审核表单(未提交的提交审核)
 * */
function detailsAuditFormSubmit(){
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
				window.location.href='cityExamination.html';
			}
 		},
 		error : function() {
			alertModel("请求失败！");
 		}
 	});

}

/**
 * 审核未通过的表单修改保存detailsFormSubmit
 */
function detailsFormSubmit(){
	if(validform().form()){
	var punishYearMonth=$('#datetimepicker1').val();
	var punishName=$('#punishName1').val();
	var punishPerson=$('#punishPerson1').val();
	var punishAmount=$('#punishAmount1').val();
	var punishCause=$('#punishCause').val();
	var detailsAuditIds = new Array();
	/*if(punishYearMonth == ""||punishName==""||punishName==""||punishPerson==""||punishAmount==""||punishCause==""){
		alertModel("修改内容不可以为空");
		return false;
	}*/
	// 从选中行中挑出可以启用的item
	var obj={
    	"punishYearMonth":punishYearMonth,
    	"punishName":punishName,
    	"punishPerson":punishPerson,
    	"punishAmount":punishAmount,
    	"punishCause":punishCause,
    	"twrRegPunishId":getParameter("twrRegPunishId")
    };
	detailsAuditIds.push(obj);
	
	$.ajax({
 		url : 'updateTwrRegPunish',
 		data : JSON.stringify(detailsAuditIds),
		type : 'post',
		cache : false,
		dataType : 'json',
		contentType: "application/json;charset=utf-8",
 		success : function(data) {
 			if (data != null) {
				alertModel(data.msg);
				window.location.href='cityExamination.html';
			}
 		},
 		error : function() {
			alertModel("请求失败！");
 		}
 	});
	}
}
