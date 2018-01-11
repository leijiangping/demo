
$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	queryCurUser();
	getAccountSummary();
	
	var itemId = getParameter("accountsummaryId");	
	findUsersByRoleIds(TwrAccountsummary.tableName,itemId);
	//流转记录
	histoicFlowList(TwrAccountsummary.tableName,itemId);
}

/**
 * 编辑详情信息表单
 */
function getAccountSummary(){
	var id = getParameter("accountsummaryId");
	$.ajax({
	    url:projectName+'/twrAccountsummary/queryAccountSummaryById',
	    data: {
	    	accountId:id
	    },
 		type : 'post',
	    cache:false,
	    async:true,
	    success:function(result){
	    	var datas = result.Obj;
	    	for(name in datas){
	    		if(name == 'state'){
	    			$('#'+name).html("未审核");
	    		} 
	    		else if (name == 'operatorSysRegion'){
	    			var reginon = datas[name];
	    			$('#'+name).html(reginon.pregName);
	    		} 
	    		else 
	    		{
	    			if($('#'+name)){
	    				$('#'+name).html(datas[name]);
	    			}
	    		}
	    		
			}
	    },
	    error:function(){
	    	alertModel("请求失败！");
	    }
	});
}

/**
 * 加载审核人
 * */
function queryCurUser(){
	$.ajax({
		url:projectName+'/twrAccountsummary/queryCurUser',
		data:{
			conutn:1
		},
		type:'get',
		dataType:'json',
		success:function(back){
			if (back != null) {
				item = back.obj;
				if(back.success=="1"){
					$("#curUser").text(item[0]);
				}
			}
		},
		error:function(){
			alertModel("请求异常");
		}
	})
}

/**
 * 详情保存
 */
function detailsFormSubmit(){
	var auditState=$('#auditState option:selected').val(),
	curUser=$('#curUser').html(),
	nextUser=$('#nextUsers option:selected').val(),
	comment=$('#auditNote').val();
	var accountId = getParameter("accountsummaryId");
	var taskId = getParameter("taskId");
	if($("#nextUsers option").length > 1){
		if($('#nextUsers option:selected').val() == ""){
			alertModel("下级审核人不能为空！");
			return false;
		}
	}
	if(auditState == 8){
		if(comment == ""){
			alertModel("审核不通过必须填写审核备注/意见！");
			return false;
		}
	}
	var accountSummary = new Array();
	// 从选中行中挑出可以启用的item
	var obj={
    	"auditState":auditState,
    	"nextAuditUserId":nextUser,
    	"auditNote":comment,
    	"taskId":taskId,
    	"accountId":accountId
    };
	accountSummary.push(obj);
	$.ajax({
		url : projectName + "/twrAccountsummary/updateComplete",
		data : JSON.stringify(accountSummary),
		type : "post",
		cache : false,
		dataType : 'json',
		contentType: "application/json;charset=utf-8",
		success : function(data) {
			if (data != null) {
				alertModel(data.msg);
				window.location.href='costCheck.html';
			}
		},
		error : function(data) {
			console.log('保存失败')
		}
	});
}
