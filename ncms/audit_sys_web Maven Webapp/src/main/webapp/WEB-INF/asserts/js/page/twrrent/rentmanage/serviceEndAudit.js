$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	// 审核数据详情
	loadTowerResourceInfo();
}
function loadTowerResourceInfo(){
	var id = getParameter("stopServerId");
	$.ajax({
		type:'POST',
		url:'queryStopServerById',
		data:{
			stopServerId : id
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
					$('#prvName').html(resourceInfoVO.sysRegionVO.prvName);//省
					$('#pRegName').html(resourceInfoVO.sysRegionVO.pRegName);//地市
					$('#regName').html(resourceInfoVO.sysRegionVO.regName);//区县
					var unixTimestamp = new Date(resourceInfoVO.endDate) ;
					commonTime = unixTimestamp.toLocaleString();
					$('#endDate').html(commonTime);//服务终止生效日期
					$('input[name="businessConfirmNumber"]').val(resourceInfoVO.businessConfirmNumber);
					$('input[name="towerStationCode"]').val(resourceInfoVO.towerStationCode);
					$('input[name="endDate"]').val(commonTime);
					findUsersByRoleIds(TwrStopServer.tableName,id);
					histoicFlowList(TwrStopServer.tableName,id);
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
	/*	nextUsers : {
			required : true,
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
		var isverify=$('#isverify option:selected').val();
		var Auditor = null;
		if($("#nextUsers option:selected").val()!=null){
			Auditor=$('#nextUsers option:selected').val();
		}
		var auditRemark=$('#auditRemark').val();
		var endDate=$('input[name="endDate"]').val();
		var businessConfirmNumber=$('input[name="businessConfirmNumber"]').val();
		var towerStationCode=$('input[name="towerStationCode"]').val();
		var stopServerId = getParameter("stopServerId");
		var taskId=getParameter("taskId");
		var billAccountIds = new Array();
		var obj = {
				"stopServerId": getParameter("stopServerId"),//选中的数据id
		    	"isverify":isverify, //审核状态
		    	"Auditor":Auditor, //审核人
		    	"auditRemark":auditRemark, //审核备注
		    	"endDate":endDate,
		    	"businessConfirmNumber":businessConfirmNumber, //业务确认单号
		    	"towerStationCode":towerStationCode, //铁塔公司站址编码
		    	"taskId":taskId,
		};
		billAccountIds.push(obj);
		$("#saveSet").attr("disabled",true);
		$.ajax({
			type:'post',
			url:'deleteCheckInfo',
			data:JSON.stringify(billAccountIds),
		    dataType : 'json',
		    contentType : 'application/json;charset=utf-8',
			success:function(value){
				goBack();
				$("#saveSet").attr("disabled",false);
			},
			error : function(back) {
				$("#saveSet").attr("disabled",false);
			}
		})
	}
}
