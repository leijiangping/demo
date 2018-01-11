$(document).ready(function() {
	initCurrentPage();
});
var id = null;
function initCurrentPage(){
	// 审核数据详情
	loadTowerResourceInfo();
}
function loadTowerResourceInfo(){
	id = getParameter("twrRentinformationBizchangeId");
	$.ajax({
		type:'post',
		url:'queryBizChangeById',
		data:{
			twrRentinformationBizchangeId : id
	    }, 
	    dataType : 'json',
		success:function(back){
			if(back!=null){
				if(back.success == "1"){
					resourceInfoVO= back.Obj;
					for(name in resourceInfoVO){
						if($('#'+name))
						$('#'+name).html(resourceInfoVO[name]);
					}
					var unixTimestamp = new Date(resourceInfoVO.changeActiveDate) ;
					commonTime = unixTimestamp.toLocaleString();
					$('#prvName').html(resourceInfoVO.sysRegionVO.prvName);//省份
					$('#pRegName').html(resourceInfoVO.sysRegionVO.pRegName);//地市
					$('#regName').html(resourceInfoVO.sysRegionVO.regName);//区县
					$('#changeActiveDate').html(commonTime);
					$('input[name="changeItem"]').val(resourceInfoVO.changeItem);
					$('input[name="changeBeforeContent"]').val(resourceInfoVO.changeBeforeContent);
					$('input[name="changeBehindContent"]').val(resourceInfoVO.changeBehindContent);
					$('input[name="changeActiveDate"]').val(commonTime);
					$('input[name="businessConfirmNumber"]').val(resourceInfoVO.businessConfirmNumber);
					$('input[name="towerStationCode"]').val(resourceInfoVO.towerStationCode);
					findUsersByRoleIds(TwrRentInformationBizchange.tableName,id);
					histoicFlowList(TwrRentInformationBizchange.tableName,id);
				}
			}
		}
	})
}
Date.prototype.toLocaleString = function() {
    return this.getFullYear() + "-" + (this.getMonth() + 1) + "-" + this.getDate();
};
function goBack(){
	window.history.go(-1);
}
function validform(){
	var twr_validate= bindformvalidate("#dataForm", {
/*		nextUsers : {
			required : false,
		},
		auditRemark:{
			required : true,
		}
	}, {
		nextUsers:{
			required : "必选！"
		},
		auditRemark:{
			required : "必选！"
		}*/
	});

  return twr_validate;
}
function formSubmit(){
	if(validform().form()){
		var checkState=$('#isverify option:selected').val();//审核状态
		var auditRemark=$('#auditRemark').val();//审核备注
		var leader=$('#nextUsers option:selected').val();//审核人
		var changeItem=$('input[name="changeItem"]').val();//变更字段
		var changeBeforeContent=$('input[name="changeBeforeContent"]').val();//变更前数据
		var changeBehindContent=$('input[name="changeBehindContent"]').val();//变更后数据
		var changeActiveDate=$('input[name="changeActiveDate"]').val();//变更生效日期
		var businessConfirmNumber=$('input[name="businessConfirmNumber"]').val();//业务确认单号
		var towerStationCode=$('input[name="towerStationCode"]').val();//站址编码
		var taskId=getParameter("taskId");
		var ids = new Array();
		var obj = {
				"twrRentinformationBizchangeId": id,
		    	"checkState":checkState,
		    	"auditRemark":auditRemark,
		    	"leader":leader,
		    	"changeItem":changeItem,
		    	"changeBeforeContent":changeBeforeContent,
		    	"changeBehindContent":changeBehindContent,
		    	"changeActiveDate":changeActiveDate,
		    	"businessConfirmNumber":businessConfirmNumber,
		    	"towerStationCode":towerStationCode,
		    	"taskId":taskId,
		};
		ids.push(obj);
		$("#saveSet").attr("disabled",true);
		$.ajax({
			type:'post',
			url:'updateCheckInfo',
			data:JSON.stringify(ids), 
		    dataType : 'json',
		    contentType: "application/json;charset=utf-8",
			success:function(value){
				window.location.href='auditInfoChange.html';
				$("#saveSet").attr("disabled",false);
			},
			error : function(back) {
				$("#saveSet").attr("disabled",false);
			}
		})
	}
}
