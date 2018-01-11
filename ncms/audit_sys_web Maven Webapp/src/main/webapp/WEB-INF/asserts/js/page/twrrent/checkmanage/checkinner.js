//审核详情页面

var item=null;
var accountType=null;
var supplierType=null;


$(document).ready(function() {

	initCurrentPage();
});

function initCurrentPage(){
	
	//查询被审核数据
	queryAll();
	// 查询默认条件表单数据
	findUserByRoleIds();
	queryCurUser();
	checkInfo();
}
//获取地址参数
function getParam(paramName) {  
    paramValue = "",
    isFound = !1;  
    if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) {  
        arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0;  
        while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 &&
        arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() &&
        (paramValue = arrSource[i].split("=")[1], isFound = !0), i++  
    }  
    return paramValue == "" && (paramValue = null), paramValue  
}


function queryAll(){
	$.ajax({
 		url :'queryCheckIndexFine',
 		data : {
 			checkId:getParam('twrProvincePunishId'),
 		},
		type : 'post',
		cache : false,
		dataType : 'json',
 		success : function(data) {
 			if (data != null) {
 				console.log(data)
 				console.log(data.Obj.auditState)
 				$("#regName").text(data.Obj.regName);
 				$("#punishYearMonth").text(data.Obj.punishYearMonth);
 				$("#punishName").text(data.Obj.punishName);
 				$("#punishCause").text(data.Obj.punishCause);
 				$("#punishPerson").text(data.Obj.punishPerson);
 				$("#punishAmount").text(data.Obj.punishAmount);
 				$("#auditState").text(data.Obj.auditState);
 			}
 		},
 		error : function() {
			alertModel("请求失败！");
 		}
 	});	
}



function queryCurUser(){
	$.ajax({
		url:'queryCurUser',
		data:{
			conutn:1
		},
		type:'get',
		dataType:'json',
		success:function(back){
			if (back != null) {
				item=back.obj;
				if(back.success=="1"){
					console.log("curUser======="+item);
					$("#curUser").text(item[0]);
				}
			}
		},
		error:function(){
			alert("请求异常");
		}
	})
}

function saveCheckInfo(){
	var state=$("#checkState option:selected").val();//审核状态
	var	comment=$("#comment").val();//审核备注
	var leader=null;
	if($("#nextUsers option:selected").val()!=null){
		leader=$("#nextUsers option:selected").val();	//下级审核人
	}
	var datasObjs = new Array();
	var obj={
	    	"auditState":state,//审核状态
	    	"nextAuditUserId":leader,
	    	"auditNote":comment,//审核备注
	    	"taskId":getParameter("taskId"),
	    	"twrProvincePunishId":getParameter("twrProvincePunishId")
	    };
	datasObjs.push(obj);
	$("#saveSet").attr("disabled",true);
	$.ajax({
		url:'commitCheckInfo',//commitCheckInfo
		data:JSON.stringify(datasObjs),
		type:'post',
		dataType : 'json',
		contentType: "application/json;charset=utf-8",
		success:function(back){
			if (back != null) {
				if(back.success=="1"){
					window.location.href='pvcExamcheck.html';
				}
			}
			$("#saveSet").attr("disabled",false);
		},
		error:function(){
			alert("请求异常");
			$("#saveSet").attr("disabled",false);
		}
	})
}

function findUserByRoleIds(){
	//var rentcontractId=localStorage.getItem("item1");
	findUsersByRoleIds(TwrProvincePunish.tableName,getParam('twrProvincePunishId'));
}
function checkInfo(){
	//var rentcontractId=localStorage.getItem("item1");
	histoicFlowList(TwrProvincePunish.tableName,getParam('twrProvincePunishId'));
}
function goBack(){
	javascript:history.back(-1);
}
