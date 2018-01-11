//已显示表格list
var showTableList = null;


$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	curPageNum = 1;
	getAddress("运营商");
	// 查询默认条件表单数据
	loadTableData();
}

/**
 * 查询默认条件表单数据
 */
function loadTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryTowerBillbalanceList", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		//fixedColumns: true,//是否启用固定列
        //fixedNumber: 8,//固定列的个数
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
					accountPeroid : $("#datetimepicker").val(),
					operatorRegId : $("#City option:selected").val(),
					pregId : $("#Village option:selected").val(),
					businessConfirmNumber : $("#businessConfirmNumber").val(),
					towerStationCodeOrName : $("#towerStationCodeOrName").val()
			};
			return param;
		},
		columns: [{
			checkbox:true,
		},{
        	field: 'accountPeroid',
            title: '账期月份'
        },{
            field: 'businessConfirmNumber',
            title: '产品业务确认单编号'
        },{
            field: 'operator',
            title: '运营商'
        },{
            field: 'operatorSysRegion.prvName',//获取不到
            title: '运营商地市'
        },{
            field: 'operatorSysRegion.regName',//找不到
            title: '运营商区县'
        },{
            field: 'agreeAreaName',
            title: '需求承接地市'
        },{
            field: 'towerStationAreaName',
            title: '站址所属地市'
        },{
            field: 'orderProp',
            title: '订单属性'
        },{
            field: 'rightProp',
            title: '产权属性'
        },{
            field: 'oriRight',
            title: '原产权方'
        },{
            field: 'towerStationName',
            title: '站址名称'
        },{
            field: 'towerStationCode',
            title: '站址编码'
        },{
            field: 'agreeBillCode',
            title: '需求确认单编号'
        },{
            field: 'serviceAttribute',
            title: '业务属性'
        },{
            field: 'startDate',
            title: '服务起始日期'
        },{
            field: 'productTypeId',
            title: '产品类型'
        },{
            field: 'roomTypeId',
            title: '机房类型'
        },{
            field: 'usePowerServiceFee',
            title: '用电服务费（包干）'
        },{
            field: 'oilGenerateElectricMethodId',
            title: '油机发电模式'
        },{
            field: 'usePowerServiceFeeOut',
            title: '油机发电服务费（包干）'
        },{
            field: 'hightLevelFeeOut',
            title: '超过10%高等级服务站址额外维护服务费'
        },{
            field: 'batteryprotectionfeeOut',
            title: '蓄电池额外保障费'
        },{
            field: 'unitProductNumber1',
            title: '产品单元数1'
        },{
            field: 'height1',
            title: '对应实际最高天线挂高（米）1'
        },{
            field: 'ifbbuOnRoom1',
            title: 'RRU拉远时BBU是否放在铁塔公司机房1'
        },{
            field: 'otherDis1',
            title: '其他折扣1'
        },{
            field: 'towerPrice1',
            title: '对应铁塔基准价格1'
        },{
            field: 'unitProductNumber2',
            title: '产品单元数2'
        },{
            field: 'height2',
            title: '实际最高天线挂高（米）2'
        },{
            field: 'ifbbuOnRoom2',
            title: 'RRU拉远时BBU是否放在铁塔公司机房2'
        },{
            field: 'otherDis2',
            title: '其他折扣2'
        },{
            field: 'towerPrice2',
            title: '对应铁塔基准价格2'
        },{
            field: 'ifbbuOnRoom3',
            title: 'RRU拉远时BBU是否放在铁塔公司机房3'
        },{
            field: 'otherDis3',
            title: '其他折扣3'
        },{
            field: 'towerPrice3',
            title: '对应铁塔基准价格3'
        },{
            field: 'currentTowerShareNum',
            title: '期末铁塔共享用户数'
        },{
            field: 'towerOperatorStartdate1',
            title: '铁塔共享运营商1的起租日期'
        },{
            field: 'towerSupportingShareDis1',
            title: '铁塔共享运营商1起租后的共享折扣'
        },{
            field: 'towerOperatorStartdate2',
            title: '铁塔共享运营商2的起租日期'
        },{
            field: 'towerSupportingShareDis2',
            title: '铁塔共享运营商2起租后的共享折扣'
        },{
            field: 'currentTowerShareSumPriceOut',
            title: '期末铁塔共享后基准价格1+2+3'
        },{
            field: 'roomBasePrice1',
            title: '对应机房基准价格1'
        },{
            field: 'roomBasePrice2',
            title: '对应机房基准价格2'
        },{
            field: 'roomBasePrice3',
            title: '对应机房基准价格3'
        },{
            field: 'currentRoomShareNum',
            title: '期末机房共享用户数'
        },{
            field: 'roomOperatorStartdate1',
            title: '机房共享运营商1的起租日期'
        },{
            field: 'roomSupportingShareDis1',
            title: '机房共享运营商1起租后的共享折扣'
        },{
            field: 'roomOperatorStartdate2',
            title: '机房共享运营商2的起租日期'
        },{
            field: 'roomSupportingShareDis2',
            title: '机房共享运营商2起租后的共享折扣'
        },{
            field: 'currentRoomShareSumPriceOut',
            title: '期末机房共享后基准价格1+2+3'
        },{
            field: 'supportingBasePrice1',
            title: '对应配套基准价格1'
        },{
            field: 'supportingBasePrice2',
            title: '对应配套基准价格2'
        },{
            field: 'supportingBasePrice3',
            title: '对应配套基准价格3'
        },{
            field: 'currentSupportingShareNum',
            title: '配套共享用户数'
        },{
            field: 'supportingOperatorStartdate1',
            title: '配套共享运营商1的起租日期'
        },{
            field: 'supportingShareDis1',
            title: '配套共享运营商1起租后的共享折扣'
        },{
            field: 'supportingOperatorStartdate2',
            title: '配套共享运营商2的起租日期'
        },{
            field: 'supportingShareDis2',
            title: '配套共享运营商2起租后的共享折扣'
        },{
            field: 'supportingShareDis2',
            title: '配套共享运营商2起租后的共享折扣'
        },{
            field: 'currentSupportingShareSumPriceOut',
            title: '配套共享后基准价格1+2+3'
        },{
            field: 'bbuOnRoomFeeBack',
            title: 'bbu安装在铁塔机房费'
        },{
            field: 'managerFee1',
            title: '对应维护费1'
        },{
            field: 'managerFee2',
            title: '对应维护费2'
        },{
            field: 'managerFee3',
            title: '对应维护费3'
        },{
            field: 'managerFeeShareNum',
            title: '维护费共享用户数'
        },{
            field: 'managerFeeOperatorStartdate1',
            title: '维护费共享运营商1的起租日期'
        },{
            field: 'managerFeeShareDis1',
            title: '维护费共享运营商1起租后的共享折扣'
        },{
            field: 'managerFeeOperatorStartdate2',
            title: '维护费共享运营商2的起租日期'
        },{
            field: 'managerFeeShareDis2',
            title: '维护费共享运营商2起租后的共享折扣'
        },{
            field: 'currentManagerFeeShareSumPriceOut',
            title: '维护费折扣后金额1+2+3'
        },{
            field: 'praceFee',
            title: '场地费'
        },{
            field: 'praceFeeOperatorStartdate1',
            title: '场地费共享运营商1的起租日期'
        },{
            field: 'praceFeeshareDis1',
            title: '场地费共享运营商1起租后的共享折扣'
        },{
            field: 'praceFeeOperatorStartdate2',
            title: '场地费共享运营商2的起租日期'
        },{
            field: 'praceFeeshareDis2',
            title: '场地费共享运营商2起租后的共享折扣'
        },{
            field: 'currentPraceFeeShareSumPriceOut',
            title: '场地费折扣后金额'
        },{
            field: 'powerInFee',
            title: '电力引入费'
        },{
            field: 'powerInFeeShareNum',
            title: '电力引入费共享用户数'
        },{
            field: 'powerInFeeOperatorStartdate1',
            title: '电力引入费共享运营商1的起租日期'
        },{
            field: 'powerInFeeShareDis1',
            title: '电力引入费共享运营商1起租后的共享折扣'
        },{
            field: 'powerInFeeOperatorStartdate2',
            title: '电力引入费共享运营商2的起租日期'
        },{
            field: 'powerInFeeShareDis2',
            title: '电力引入费共享运营商2起租后的共享折扣'
        },{
            field: 'currentPowerInFeeShareSumPriceOut',
            title: '电力引入费折扣后金额'
        },{
            field: 'wlanFeeOut',
            title: 'WLAN费用'
        },{
            field: 'microwaveFeeOut',
            title: '微波费用'
        },{
            field: 'otherFee1Out',
            title: '其他费用1'
        },{
            field: 'totalAmountMonthOut',
            title: '产品服务费月费用合计（不含税）'
        },{
            field: 'ifServicefeeChange',//产品服务费与上月相比是否变化(自己添加)
            title: '产品服务费与上月相比是否变化'
        },{
            field: 'confimState',
            title: '确认状态（调整）'
        },{
            field: 'adjustmentFeeType',
            title: '调整费项'
        },{
            field: 'adjustmentFee',
            title: '费项的调整金额（正负表示）（不含税）'
        },{
            field: 'afterAdjustmentFee',
            title: '费项的调整后金额（不含税）'
        },{
            field: 'adjustmentFeeNote',
            title: '费项调整原因'
        },{
            field: 'disputeFee',
            title: '费项的争议金额（不含税）'
        },{
            field: 'servicefeeChange',
            title: '产品服务费与上月相比的变化量'
        },{
            field: 'usePowerServiceFeeOut2',
            title: '油机发电服务费（非包干）'
        },{
            field: 'retroactiveCost',//找不到
            title: '追溯费用'
        },{
            field: 'carryoverAmount',//找不到
            title: '结转金额'
        },],
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success != "1"){
		            alertModel(res.msg.result);
				}
			}
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.result //数据
	         };
		}
	});	
}

/**
 * 导入样例弹出导入选择文件框
 */
function importBill(){
	//"铁塔侧账单导入" 弹出框名称
	//"_towerBillbalance" 功能标识
	//"formSubmit" 回调方法
	importModel("铁塔侧账单导入","_towerBillbalance","formSubmit");//弹出导入框
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
/**
 * 导入样例
 */
function updateImportBill(){
	importModel("铁塔侧账单导入","_towerBillbalance","formSubmitUpdate");//弹出导入框
}
/**
 * 上传文件并保存到数据库
 * @param suffix 模块标识
 */
function formSubmitUpdate(suffix){
	var formData = new FormData($("#dataForm")[0]); 
	$.ajax({
		url:'importUpdateFile',
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
 					alertModel(result.msg);
 			}
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
/**
 * 导出
 */
function exportBill(){
	confirmModel("您确定要导出吗？","exportBillOperation");
} 
function exportBillOperation(){
		// 从选中行中挑出可以启用的item
		/*if($("#educeStyle").val() == 1){
			//alert($("#educeStyle").val());
			//导出当前页数据
			var currentExportObjs = new Array();
			var currentRows=$('#tb').bootstrapTable('getData');
			$.each(currentRows,function(i,v){
				currentExportObjs.push(v.towerbillbalanceId);
				console.log(currentExportObjs);
			});
			
			var towerbillbalanceIds = currentExportObjs.join(",");
			
		}else if($("#educeStyle").val() == 2){
			//alert($("#educeStyle").val());
			if(!isChecked()){
				alertModel("请先选择一条数据再操作");
				return;
			}
			//导出所选数据
			var exportObjs = new Array();
			var selectRows=$('#tb').bootstrapTable('getSelections');
			$.each(selectRows,function(i,v){
				exportObjs.push(v.towerbillbalanceId);
				console.log(exportObjs);
			});
			var towerbillbalanceIds = exportObjs.join(",");
		}*/
		
		
	var para="businessConfirmNumber="+$("#businessConfirmNumber").val();
	para+="&towerStationName="+$("#towerStationName").val();
	para+="&accountPeroid="+$("#datetimepicker").val();
	para+="&operatorRegId="+ $("#City").val();
	para+="&pregId="+ $("#Village").val();
	//window.open("export?"+para+"&towerbillbalanceIds="+towerbillbalanceIds,"_blank");
	window.open("export?"+para,"_blank");
	console.log(para);
} 
