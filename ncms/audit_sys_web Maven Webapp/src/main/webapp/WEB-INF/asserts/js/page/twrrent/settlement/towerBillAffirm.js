//已显示表格list
var showTableList = null;
var operate_type = 1;// 1 确认，2 修改

$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	curPageNum = 1;
	getAddress("运营商");
	// 查询默认条件表单数据
	loadTableData();
}

function loadTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryTowerAndMobileConfirmBalance", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
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
        	field: 'confirmState',
            title: '确认状态',
        	formatter:function(value,row,index){
            	switch(value){
            		case 0:return '未确认';
            		case 1:return '已确认';
            	}
            	return value;
            }	
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
 * 获取（确认）页面点击checkbox个数
 */
var confirmRowschecked = new Array();
function isCheckedLessOne(){
	var checkNum = 0;
	confirmRowschecked = new Array();
	var checklist = document.getElementsByName ("checkbox");
	for(var i=0;i<checklist.length;i++)
    {
		// 已选中可操作行
	    if(checklist[i].checked == 1){
	    	checkNum ++;
	    	confirmRowschecked.push(confirmDataList[i]);
	    }
    } 
	return checkNum;
}
function confirmIsChecked(){
	var checkNum = 0;
	confirmRowschecked = new Array();
	var checklist = $("#affirmTb tbody input[type='checkbox']");
	for(var i=0;i<checklist.length;i++)
    {
		// 已选中可操作行
	    if(checklist[i].checked == 1){
	    	checkNum ++;
	    	confirmRowschecked.push(confirmDataList[i]);
	    }
    } 
	return checkNum;
}
/**
 * 获取（修改）页面点击checkbox个数
 */
var updateRowschecked = new Array();
function isCheckedLessOne(){
	var checkNum = 0;
	updateRowschecked = new Array();
	var checklist = document.getElementsByName ("checkbox");
	for(var i=0;i<checklist.length;i++)
    {
		// 已选中可操作行
	    if(checklist[i].checked == 1){
	    	checkNum ++;
	    	updateRowschecked.push(updateDataList[i]);
	    }
    } 
	return checkNum;
}
function updteIsChecked(){
	var checkNum = 0;
	updateRowschecked = new Array();
	var checklist = $("#updateTb tbody input[type='checkbox']");
	for(var i=0;i<checklist.length;i++)
    {
		// 已选中可操作行
	    if(checklist[i].checked == 1){
	    	checkNum ++;
	    	updateRowschecked.push(updateDataList[i]);
	    }
    } 
	return checkNum;
}

/**
 * 选中的条数
 */
function hadCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}
/**
 * 确认
 */
function confirmBill(){
	if(!hadCheckedRowData()){
		return;
	}
	var stateObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		stateObjs.push(row.confirmState);
	}
	// 判断是否有"已确认"的账单
	if($.inArray(1, stateObjs)>=0){
		alertModel("已经确认的账单不能再次确认");
		return false;
	}
	$('#affirmTb tbody').empty();	
	$('#EditCost').modal();	
	comfirmTable();	
}
/**
 * 确定页面的bootstrapTable
 * */
function comfirmTable(){
	var towerbillbalanceIds = new Array();
	var selectRows=$('#tb').bootstrapTable('getSelections');
	$.each(selectRows,function(i,v){
		towerbillbalanceIds.push(v.towerbillbalanceId);
		console.log(towerbillbalanceIds);
	});
	$('#affirmTb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#affirmTb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryTowerBillbalanceByIds", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
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
					towerbillbalanceIds:towerbillbalanceIds.join(",")
			};
			return param;
		},
		columns: [{
            checkbox: true,
            formatter : stateFormatter
        },{
            field: 'businessConfirmNumber',
            title: '产品业务确认单编号'
        },{
            field: 'towerStationName',
            title: '站址名称'
        },{
            field: 'agreeBillCode',
            title: '需求确认单编号'
        },{
            field: 'comfirmBill',
            title: '确认铁塔侧账单费用'
        },],
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success != "1"){
					 alertModel(res.msg);
				}
				
			}
			confirmDataList = res.obj.result;
	        //$('#EditCost').modal();	
			
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.result //数据
	         };
	        
		}
	});
	function stateFormatter(value, row, index) {
	   checked : true;		    
	}
}
/**
 * 确认页面的保存
 */
function affirmFormSubmit(){
	var confirmObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < confirmRowschecked.length; s++) {
		var row = confirmRowschecked[s];
		confirmObjs.push(row.towerbillbalanceId);
		console.log(confirmObjs);
	}
	
	var confirmTowerbillbalanceIds = new Array();
	var selectRows=$('#affirmTb').bootstrapTable('getSelections');
	$.each(selectRows,function(i,v){
		confirmTowerbillbalanceIds.push(v.towerbillbalanceId);
	});
	$("#saveSet").attr("disabled",true);
	$.ajax({
	    url:'updateTowerMobileBillConfirmState',
	    data:{
	    	confirmTowerbillbalanceIds:confirmTowerbillbalanceIds.join(",")
	    },
 		type : 'post',
	    cache:false,
	    async:true,
	    success:function(result){
	    	if(result!=null){
    			alertModel(result.msg);
    			loadTableData();
	    	}
			$('#EditCost').modal('hide');
			$("#saveSet").attr("disabled",false);
	    },
	    error:function(){
	    	alertModel("请求失败！");
	    	$("#saveSet").attr("disabled",true);
	    }
	});
}

/**
 * 修改
 */
function updateBill(){
	if(!hadCheckedRowData()){
		return;
	}
	var stateObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		stateObjs.push(row.confirmState);
	}
	// 判断是否有"已确认"的账单
	if($.inArray(1, stateObjs)>=0){
		alertModel("已经确认的账单不能再修改确认");
		return false;
	}
	$('#updateTb tbody').empty();	
	$('#EditCost1').modal();
	updateTable();
}
/**
 * 修改页面的bootstrapTable
 * */
function updateTable(){
	var towerbillbalanceIds = new Array();
	var selectRows=$('#tb').bootstrapTable('getSelections');
	$.each(selectRows,function(i,v){
		towerbillbalanceIds.push(v.towerbillbalanceId);
		console.log(towerbillbalanceIds);
	});
	console.log("towerbillbalanceIds============"+towerbillbalanceIds);
	$('#updateTb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#updateTb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryTowerBillbalanceByIds", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
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
					towerbillbalanceIds:towerbillbalanceIds.join(",")
			};
			return param;
		},
		columns: [{
            checkbox: true,
            formatter : stateFormatter
        },{
            field: 'businessConfirmNumber',
            title: '产品业务确认单编号'
        },{
            field: 'towerStationName',
            title: '站址名称'
        },{
            field: 'agreeBillCode',
            title: '需求确认单编号'
        },{
            field: 'updateBill',
            title: '修改铁塔侧账单费用'
        },{
            field: 'updateBillTotal',
            title: '铁塔侧产品服务费月费用合计'
        },{
            field: 'totalBillValue',
            title: '费用（输入）',
            edit:false,
            formatter:function(){ 
            	var strHtml =
            	'<input type="text" name="totalBillValue" style="margin-left:5px;border:1px solid #ccc;width:100px;height:20px;">'; 
            	return strHtml; 
        	}
        },],
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success != "1"){
					 alertModel(res.msg);
				}
				
			}
			 updateDataList = res.obj.result;
			console.log("updateDataList===="+updateDataList);
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.result //数据
	         };
	        
		}
	});
	function stateFormatter(value, row, index) {
	   checked : true;		    
	}
}
/**
 * 修改页面的保存
 * */
function updateFormSubmit(){
	var updateTowerbillbalanceIds = new Array();
	var selectRows=$('#updateTb').bootstrapTable('getSelections');
	$.each(selectRows,function(i,v){
		var afterAdjustmentFee = $("input[name='totalBillValue']").val();
		console.log(afterAdjustmentFee);
		updateTowerbillbalanceIds.push({afterAdjustmentFee: afterAdjustmentFee, towerbillbalanceId: v.towerbillbalanceId});
		console.log(updateTowerbillbalanceIds);
	});
	$("#saveSet1").attr("disabled",true);
	$.ajax({
	    url:'updateTowerMobileBillState',
	    contentType : 'application/json;charset=utf-8', //设置请求头信息
        dataType:"json",
	    data: JSON.stringify(updateTowerbillbalanceIds),
 		type : 'post',
	    cache:false,
	    async:true,
	    success:function(result){
	    	if(result!=null){
    			alertModel(result.msg);
    			loadTableData();
	    	}
			$('#EditCost1').modal('hide');
			$("#saveSet1").attr("disabled",false);
	    },
	    error:function(){
	    	alertModel("请求失败！");
	    	$("#saveSet1").attr("disabled",false);
	    }
	});
}
/**
 * 取消确认
 */
function removeConfirmBill(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var removeConfirmObjs = new Array();
	var stateObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		removeConfirmObjs.push(row.towerbillbalanceId);
		stateObjs.push(row.confirmState);
	}
	// 判断是否有"未确认"的账单
	if($.inArray(0, stateObjs)>=0){
		alertModel("未确认的账单不能取消确认");
		return false;
	}
 	$.ajax({
 		url : 'updateCancleConfirmState',
 		data : JSON.stringify(removeConfirmObjs),
 		type : 'post',
		cache : false,
 		dataType : 'json',
		contentType: "application/json;charset=utf-8",
 		success : function(back) {
 			console.log(back);
 			if (back != null) {
 				loadTableData();
				alertModel(back.msg);
 			}
 		},
 		error : function() {
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
		window.open("export?"+para,"_blank");
		//window.open("export?"+para+"&towerbillbalanceIds="+towerbillbalanceIds,"_blank");
		console.log(para);
}