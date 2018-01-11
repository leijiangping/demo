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
					$('input[name="businessConfirmNumber"]').html(resourceInfoVO.businessConfirmNumber);
					/*$('#businessConfirmNumber').html(resourceInfoVO.businessConfirmNumber);
					$('input[name="businessConfirmNumber"]').html(resourceInfoVO.businessConfirmNumber);
					$('#towerStationCode').html(resourceInfoVO.towerStationCode);
					$('#pRegName').html(resourceInfoVO.sysRegionVO.pRegName);//地市
					$('#regName').html(resourceInfoVO.sysRegionVO.regName);//区县
					/*$('#detailAddress').html(resourceInfoVO.detailAddress);
					$('#antennaHeightRangeId').html(resourceInfoVO.antennaHeightRangeId);
					$('#longitude').html(resourceInfoVO.longitude);
					$('#latitude').html(resourceInfoVO.latitude);
					$('#productConfigurationId').html(resourceInfoVO.productConfigurationId);
					//$('#').html(resourceInfoVO.);//RRU拉远时BBU是否放在铁塔公司机房
					//$('#').html(resourceInfoVO.);//铁塔既有共享客户总数
					//$('#').html(resourceInfoVO.);//机房及配套既有共享客户总数
					$('#addTowerShareNum').html(resourceInfoVO.addTowerShareNum);
					$('#addRoomSupportingShareNum').html(resourceInfoVO.addRoomSupportingShareNum);
					$('#isSpecEnterStation').html(resourceInfoVO.isSpecEnterStation);
					$('#maintenanceLevelId').html(resourceInfoVO.maintenanceLevelId);
					$('#electricProtectionMethodId').html(resourceInfoVO.electricProtectionMethodId);
					$('#ifHasPowerCondition').html(resourceInfoVO.ifHasPowerCondition);
					$('#ifSelectPowerService').html(resourceInfoVO.ifSelectPowerService);
					$('#oilGenerateElectricMethodId').html(resourceInfoVO.oilGenerateElectricMethodId);
					$('#electricProtectionFee').html(resourceInfoVO.electricProtectionFee);
					$('#oilGenerateElectricMethodId').html(resourceInfoVO.oilGenerateElectricMethodId);
					$('#hightLevelFee').html(resourceInfoVO.hightLevelFee);
					//$('#').html(resourceInfoVO.);//BBU安装在铁塔机房的服务费（元/年）（不含税）
					$('#otherFee').html(resourceInfoVO.otherFee);
					$('#otherFeeRemark').html(resourceInfoVO.otherFeeRemark);
					//$('#').html(resourceInfoVO.);//铁塔基准价格
					//$('#').html(resourceInfoVO.);//机房及配套基准价格
					$('#maintenanceFee').html(resourceInfoVO.maintenanceFee);
					$('#unitProductNumber').html(resourceInfoVO.unitProductNumber);
					$('#').html(resourceInfoVO.stageFee);//场地费（元/年）（不含税）
					$('#electricImportFee').html(resourceInfoVO.electricImportFee);
					$('#maintenanceFeeDis').html(resourceInfoVO.maintenanceFeeDis);
					$('#stageFeeDis').html(resourceInfoVO.stageFeeDis);
					$('#electricImportFeeDis').html(resourceInfoVO.electricImportFeeDis);
					$('#towerShareDis').html(resourceInfoVO.towerShareDis);
					$('#roomSupportingShareDis').html(resourceInfoVO.roomSupportingShareDis);
					$('#startDate').html(resourceInfoVO.startDate);
					$('#endDate').html(resourceInfoVO.endDate);
					$('#totalAmount').html(resourceInfoVO.totalAmount);*/
				}
			}
		}
	})
}

function goBack(){
	window.history.go(-1);
}
function formSubmit(){
	var isverify=$('#isverify option:selected').val(),
	auditor=$('#auditor').val(),
	auditRemark=$('#auditRemark').val(),
	businessConfirmNumber=$('input[name="businessConfirmNumber"]').val(),
	towerStationCode=$('input[name="towerStationCode"]').val();
	$.ajax({
		type:'post',
		url:'updateCheckInfo',
		data:{
	    	id: getParameter("rentinformationtowerId"),//选中的数据id
	    	isverify:isverify, //审核状态
	    	auditor:auditor, //审核人
	    	auditRemark:auditRemark, //审核备注
	    	businessConfirmNumber:businessConfirmNumber, //业务确认单号
	    	towerStationCode:towerStationCode //铁塔公司站址编码
	    }, 
	    dataType : 'json',
		success:function(value){
			console.log(value)
		},
		error : function(back) {
			console.log('失败')
		}
	})
}