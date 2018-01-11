$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	// 审核数据详情
	loadTowerResourceInfo();
}
function loadTowerResourceInfo(){
	var id = getParameter("rentinformationtowerId");
	$.ajax({
		type:'post',
		url:'queryTowerResourceInfoVOById',
		data:{
			rentinformationtowerId : id
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
					$('#pRegName').html(resourceInfoVO.sysRegionVO.pRegName);//地市
					$('#regName').html(resourceInfoVO.sysRegionVO.regName);//区县
					var unixTimestamp = new Date(resourceInfoVO.endDate) ;
					commonTime = unixTimestamp.toLocaleString();
					$('#startDate').html(commonTime);
					$('#endDate').html(commonTime);
					$('input[name="businessConfirmNumber"]').val(resourceInfoVO.businessConfirmNumber);
					$('input[name="towerStationCode"]').val(resourceInfoVO.towerStationCode);
					findUsersByRoleIds(TwrRentInformationtower.tableName,id);
					histoicFlowList(TwrRentInformationtower.tableName,id);
				}
			}
		}
	})
}

function goBack(){
	window.history.go(-1);
}

function validform(){
	var twr_validate= bindformvalidate("#dataForm", {
/*		nextUsers : {
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
		var auditor=$('#nextUsers').val();
		var auditRemark=$('#auditRemark').val();	
		var businessConfirmNumber=$('input[name="businessConfirmNumber"]').val();
		var towerStationCode=$('input[name="towerStationCode"]').val();
		var rentinformationtowerId=getParameter("rentinformationtowerId");
		var taskId=getParameter("taskId");
		var ids = new Array();
		var obj = {
				"rentinformationtowerId":rentinformationtowerId,//选中的数据id
		    	"isverify":isverify, //审核状态
		    	"auditor":auditor, //审核人
		    	"auditRemark":auditRemark, //审核备注
		    	"businessConfirmNumber":businessConfirmNumber, //业务确认单号
		    	"towerStationCode":towerStationCode, //铁塔公司站址编码
		    	"taskId":taskId,
		};
		ids.push(obj);
		$("#saveSet").attr("disabled",true);
		$.ajax({
			type:'post',
			url:'insertCheckInfo',
			data:JSON.stringify(ids), 
		    dataType : 'json',
		    contentType: "application/json;charset=utf-8",
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
