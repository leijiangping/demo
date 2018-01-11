//已显示表格list
var showTableList = null;

$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	curPageNum = 1;
	getAddress("运营商");
	// 查询默认条件表单数据
}
/**
 * 对账
 */
function reconciliationBill(){
	if($("#City option:selected").val() == ''){
		alertModel("请先选择运营商地市！");
		//$("#tb tbody").html("");
		return false;
	}else{
		loadTableData();
	}
	if(showTableList == null){
		alertModel("您的对账信息有误或者不存在，请重新选择！");
	}
}
/**
 *查询 
 */
function searchTableData(){
	loadTableData();	
}
function loadTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryTowerAndMobileFee", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		fixedColumns: true,//是否启用固定列
        fixedNumber: 6,//固定列的个数
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
					pregId : $("#City option:selected").val(),
					regId : $("#Village option:selected").val(),
					accountPeroid : $("#datetimepicker").val(),
					businessConfirmNumber : $("#businessConfirmNumber").val(),
					towerStationCodeOrName : $("#towerStationCodeOrName").val(),
					resCompare : $("#resCompare option:selected").val()
			};
			return param;
		},
		columns: [{
			checkbox: true
		},{
        	field: 'resCompare',
            title: '对账结果',
        	formatter:function(value,row,index){
            	switch(value){
            		case 0:return '费用一致';
            		case 1:return '铁塔账单高';
            		case 2:return '铁塔账单低';
            	}
            	return value;
            }
        },{
        	field: 'accountPeroid',
            title: '账期月份'
        }, {
            field: 'businessConfirmNumber',
            title: '产品业务确认单编号'
        },{
            field: 'sysRegionVO.pRegName',
            title: '运营商地市'
        },{
            field: 'sysRegionVO.regName',
            title: '运营商区县'
        },{
            field: 'orderProp',//
            title: '订单属性'
        },{
            field: 'rightProp',//
            title: '产权属性'
        },{
            field: 'oriRight',//
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
            field: 'totalAmountMonthOut',
            title: '铁塔侧产品服务费月费用合计（不含税）'
        },{
            field: 'totalAmountMonthOut1',
            title: '移动侧产品服务费月费用合计（不含税）'
        },{
            field: 'elecFeeOut',
            title: '铁塔侧用电服务费（包干）'
        },{
            field: 'elecFeeOut1',
            title: '移动侧用电服务费（包干）'
        },{
            field: 'usePowerServiceFeeOut',
            title: '铁塔侧油机发电服务费'
        },{
            field: 'usePowerServiceFeeOut1',
            title: '移动侧油机发电服务费'
        },{
            field: 'hightLevelFeeOut',
            title: '铁塔侧超过10%高等级服务站址额外维护服务费'
        },{
            field: 'hightLevelFeeOut1',
            title: '移动侧超过10%高等级服务站址额外维护服务费'
        },{
            field: 'batteryprotectionfeeOut',
            title: '铁塔侧蓄电池额外保障费'
        },{
            field: 'batteryprotectionfeeOut1',
            title: '移动侧蓄电池额外保障费'
        },{
            field: 'currentTowerShareSumPriceOut',
            title: '铁塔侧期末铁塔共享后基准价格1+2+3'
        },{
            field: 'currentTowerShareSumPriceOut1',
            title: '移动侧期末铁塔共享后基准价格1+2+3'
        },{
            field: 'currentRoomShareSumPriceOut',
            title: '铁塔侧期末机房共享后基准价格1+2+3'
        },{
            field: 'currentRoomShareSumPriceOut1',
            title: '移动侧期末机房共享后基准价格1+2+3'
        },{
            field: 'currentSupportingShareSumPriceOut',
            title: '铁塔侧配套共享后基准价格1+2+3'
        },{
            field: 'currentSupportingShareSumPriceOut1',
            title: '移动侧配套共享后基准价格1+2+3'
        },{
            field: 'bbuOnRoomFeeBack',
            title: '铁塔侧bbu安装在铁塔机房费'
        },{
            field: 'bbuOnRoomFeeBack1',
            title: '移动侧bbu安装在铁塔机房费'
        },{
            field: 'currentManagerFeeShareSumPriceOut',
            title: '铁塔侧维护费折扣后金额1+2+3'
        },{
            field: 'currentManagerFeeShareSumPriceOut1',
            title: '移动侧维护费折扣后金额1+2+3'
        },{
            field: 'currentPraceFeeShareSumPriceOut',
            title: '铁塔侧场地费折扣后金额'
        },{
            field: 'currentPraceFeeShareSumPriceOut1',
            title: '移动侧场地费折扣后金额'
        },{
            field: 'currentPowerInFeeShareSumPriceOut',
            title: '铁塔侧电力引入费折扣后金额'
        },{
            field: 'currentPowerInFeeShareSumPriceOut1',
            title: '移动侧电力引入费折扣后金额'
        },{
            field: 'wlanFeeOut',
            title: '铁塔侧WLAN费用'
        },{
            field: 'wlanFeeOut1',
            title: '移动侧WLAN费用'
        },{
            field: 'microwaveFeeOut',
            title: '铁塔侧微波费用'
        },{
            field: 'microwaveFeeOut1',
            title: '移动侧微波费用'
        },{
            field: 'otherFee1Out',
            title: '铁塔侧其他费用1'
        },{
            field: 'otherFee1Out1',
            title: '移动侧其他费用1'
        },],
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			console.log(res.Obj.result);
			if(res != null){//报错反馈
				if(res.success != "1"){
		            alertModel(res.msg.result);
				}
				showTableList = res.Obj.result;
				console.log(showTableList);
			}
	        return {
	            "total": res.Obj.total,//总页数
	            "rows": res.Obj.result, //数据
	         };
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