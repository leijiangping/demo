
//已显示表格list
var showTableList = null;

$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	curPageNum = 1;
	getAddress();
	// 查询默认条件表单数据
	loadTableData();
}
function loadInfo(obj){
	/*	row[0].rentinformationId*/
		 //alert(showTableList[0].rentinformationId)
		 var twrRentInfoId=$(obj).parent().next().text();
		 window.location.href="mobileInfoinner.html?twrRentInfoId="+twrRentInfoId+"&goType=2";
}
function loadTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryTwrRentInformationCheck", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		pageSize : ipageCount, // 每页显示的记录数
		pageNumber : curPageNum, // 当前第几页
		minimumCountColumns: 1,  //最少允许的列数
		clickToSelect: true,  //是否启用点击选中行
		/*fixedColumns: true,
        fixedNumber:5,*/
		pageList : [10, 25, 50, 100, 500], // 记录数可选列表
		search : false, // 是否启用查询
		sidePagination : "server", // 表示服务端请求
		// 设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
		// 设置为limit可以获取limit, offset, search, sort, order
		queryParamsType : "undefined",
		queryParams : function queryParams(params) { // 设置查询参数
			var param = {
					pageNum: params.pageNumber,    
					pageSize: params.pageSize,
					taskDefKey:$("#taskDefKey").val(),
					pregId :	$("#City option:selected").val(),
		            regId :	$("#Village option:selected").val(),
		            checkState : $("#rentcontractState option:selected").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        },{
            formatter:function(){ 
            	var strHtml =
            	'<a onclick="loadInfo(this)" style="white-space:nowrap">详情</a>'; 
            	return strHtml; 
            	}
        },{
            field: 'regId',
            title: '区域编码',
        }, {
            field: 'businessConfirmNumber',
            title: '业务确认单编号',
        },{
            field: 'scenceType',
            title: '场景划分',
        },{
            field: 'agreeBillCode',
            title: '需求确认单编号',
        }, {
        	field: 'towerStationCode',
            title: '铁塔公司站址编码'
        },{
            field: 'towerStationName',
            title: '铁塔公司站址名称'
        },{
            field: 'operator',
            title: '运营商'
        },{
            field: 'operatorRegId',
            title: '运营商地市'
        },{
            field: 'demandRegId',
            title: '需求承接地市'
        },{
            field: 'stationRegId',
            title: '站址所属地市'
        },{
            field: 'ifTowerLinkOperator',
            title: '是否铁塔与移动侧关联站址编码'
        },{
            field: 'detailAddress',
            title: '详细地址'
        },{
            field: 'productConfigurationId',
            title: '产品配置（33种）'
        },{
            field: 'longitude',
            title: '经度'
        },{
            field: 'latitude',
            title: '纬度'
        },{
            field: 'productTypeId',
            title: '铁塔产品'
        },{
            field: 'roomTypeId',
            title: '机房产品'
        },{
            field: 'supportingTypeId',
            title: '配套编码'
        },{
            field: 'windPressure',
            title: '风压系数'
        },{
            field: 'shareTypeId',
            title: '共享类型编码'
        },{
            field: 'product1Height',
            title: '产品单元1：挂高（米）'
        },{
            field: 'product1UnitNum',
            title: '产品单元1：产品单元数（个）'
        },{
            field: ' product1AntennaNum',
            title: '产品单元1：天线数量'
        },{
            field: 'product1SystemNum',
            title: '产品单元1：系统数量'
        },{
            field: 'product1IsUpTowerrru',
            title: '产品单元1：RRU是否上塔'
        },{
            field: 'product1IfbbuOnRoom',
            title: '产品单元1：RRU拉远时BBU是否放在铁塔机房'
        },{
            field: 'product1BbuFee',
            title: '产品单元1：BBU安装在铁塔机房的服务费（元/年）'
        },{
            field: 'product2UnitNum',
            title: '产品单元2：产品单元数（个）'
        },{
            field: 'product2Height',
            title: '产品单元2：挂高（米）'
        },{
            field: 'product2AntennaNum',
            title: '产品单元2：天线数量'
        },{
            field: 'product2SystemNum',
            title: '产品单元2：系统数量'
        },{
            field: 'product2IsUpTowerrru',
            title: '产品单元2：RRU是否上塔'
        },{
            field: 'product2IfbbuOnRoom',
            title: '产品单元2：RRU拉远时BBU是否放在铁塔机房'
        },{
            field: 'product2BbuFee',
            title: '产品单元2：BBU安装在铁塔机房的服务费（元/年）'
        },{
            field: 'product3UnitNum',
            title: '产品单元3：产品单元数（个）'
        },{
            field: 'product3Height',
            title: '产品单元3：挂高（米）'
        },{
            field: 'product3AntennaNum',
            title: '产品单元3：天线数量'
        },{
            field: 'product3SystemNum',
            title: '产品单元3：系统数量'
        },{
            field: 'product3IsUpTowerrru',
            title: '产品单元3：RRU是否上塔'
        },{
            field: 'product3IfbbuOnRoom',
            title: '产品单元3：RRU拉远时BBU是否放在铁塔机房'
        },{
            field: 'product3BbuFee',
            title: '产品单元3：BBU安装在铁塔机房的服务费（元/年）'
        },{
            field: 'antennaHeightRangeId',
            title: '实际最高天线挂高（米）编码'
        },{
            field: 'towerHeight',
            title: '塔高（米）'
        },{
            field: 'ifstandardBuildFee',
            title: '是否标准建造成本'
        },{
            field: 'ifrruDis',
            title: 'RRU是否折扣'
        },{
            field: 'currentTowerShareNum',
            title: '当前铁塔共享客户总数'
        },{
            field: 'currentRoomSupportingShareNum',
            title: '当前机房及配套存量新增共享客户总数'
        },{
            field: 'currentServiceShareNum',
            title: '当前维护费共享客户数量'
        },{
            field: 'currentRoomFeeShareNum',
            title: '当前场地费共享客户数量'
        },{
            field: 'currentElecimportShareNum',
            title: '当前电力引入费共享客户数量'
        },{
            field: 'addTowerShareNum',
            title: '铁塔存量新增共享客户数'
        },{
            field: 'addRoomSupportingShareNum',
            title: '机房及配套存量新增共享客户数'
        },{
            field: 'isSpecEnterStation',
            title: '0-6点是否可上站'
        },{
            field: 'startDate',
            title: '服务起始日期',
            formatter : function(value){
            	if(value != null){
            		return new Date(value).format("yyyy-MM-dd");
            	}else{
            		return "";
            	}
            }
        },{
            field: 'endDate',
            title: '服务结束日期',
            formatter : function(value){
            	if(value != null){
            		return new Date(value).format("yyyy-MM-dd");
            	}else{
            		return "";
            	}
            }
        },{
            field: 'unitProductNumber',
            title: '产品单元数'
        },{
            field: 'towerPrice',
            title: '铁塔建造成本'
        },{
            field: 'roomPrice',
            title: '机房建造成本'
        },{
            field: 'supportingPrice',
            title: '配套建造成本'
        },{
            field: 'towerShareDis',
            title: '铁塔共享折扣'
        },{
            field: 'roomSupportingShareDis',
            title: '机房及配套共享折扣'
        },{
            field: 'computeRoomSupportingPrice',
            title: '系统计算机房及配套基准价格'
        },{
            field: 'maintenanceFee',
            title: '维护费'
        },{
            field: 'maintenanceFeeDis',
            title: '维护费折扣'
        },{
            field: 'originalMaintenanceFee',
            title: '维护费原始录入值'
        },{
            field: 'computeMaintenanceFee',
            title: '系统计算维护费'
        },{
            field: 'stageFee',
            title: '场地费'
        },{
            field: 'stageFeeDis',
            title: '场地费折扣'
        },{
            field: 'originalStageFee',
            title: '场地费原始录入值'
        },{
            field: 'electricImportFee',
            title: '电力引入费（元/年）'
        },{
            field: 'electricImportFeeDis',
            title: '电力引入费折扣'
        },{
            field: 'originalElectricImportFee',
            title: '电力引入费原始录入值'
        },{
            field: 'computeElectricImportFee',
            title: '系统计算电力引入费'
        },{
            field: 'ifHasPowerCondition',
            title: '是否具备发电条件'
        },{
            field: 'ifSelectPowerService',
            title: '是否选择发电服务'
        },{
            field: 'oilGenerateElectricMethodId',
            title: '油机发电服务费模式（0包干，3按次）'
        },{
            field: 'oilGeneratorElectricFee',
            title: '油机发电服务费'
        },{
            field: 'originalOilGenerateElectricFee',
            title: '油机发电服务费原始录入值'
        },{
            field: 'computeOilGenerateElectricFee',
            title: '系统计算油机发电服务费'
        },{
            field: 'otherFee',
            title: '其他费用（元/年）'
        },{
            field: 'otherFeeRemark',
            title: '其他费用说明'
        },{
            field: 'originalOtherFee',
            title: '其他费用原始录入值'
        },{
            field: 'computeOtherFee',
            title: '系统计算其他费用'
        },{
            field: 'hightLevelFee',
            title: '超过10%高等级维护费'
        },{
            field: 'electricProtectionFee',
            title: '包干电费'
        },{
            field: 'electricProtectionMethodId',
            title: '电力报账服务模式id'
        },{
            field: 'reserveBattery',
            title: '后备电池时长（小时）'
        },{
            field: 'roomFeeMethod',
            title: '场地费模式'
        },{
            field: 'elecImportFeeMethod',
            title: '电力引入费模式'
        },{
            field: 'microwaveServiceFee',
            title: '微波产品服务费（元/年）（不含税）'
        },{
            field: 'wlanServiceFee',
            title: 'WLAN产品服务费（元/年）（不含税）'
        },{
            field: 'batteryProtectionFee',
            title: '蓄电池额外保障费'
        },{
            field: 'totalAmount',
            title: '产品服务费合计（不含税）'
        },{
            field: 'totalActualAmount',
            title: '产品服务费合计（含税）'
        },{
            field: 'computeTotalAmount',
            title: '系统计算产品服务费合计（不含税）'
        },{
            field: 'computeTotalActualAmount',
            title: '系统计算产品服务费合计（含税）'
        },{
            field: 'computeRoomPrice',
            title: '系统计算机房基准价格'
        },{
            field: 'computeSupportingPrice',
            title: '系统计算配套基准价格'
        },{
            field: 'computeTowerPrice1',
            title: '系统计算铁塔基准价格1'
        },{
            field: 'computeTowerPrice2',
            title: '系统计算铁塔基准价格2'
        },{
            field: 'computeTowerPrice3',
            title: '系统计算铁塔基准价格3'
        },{
            field: 'publishUser',
            title: '业务单起租发起人'
        },{
            field: 'publishTime',
            title: '业务单起租发起时间'
        },{
            field: 'createUserId',
            title: '创建用户编码'
        },{
            field: 'createTime',
            title: '创建时间'
        },{
            field: 'updateUserId',
            title: '更新用户编码'
        },{
            field: 'updateTime',
            title: '更新时间'
        },{
            field: 'checkState',
            title: '审核状态'
        },],
		/*onLoadSuccess : function() { // 加载成功时执行
			console.log("加载成功");
		},*/
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success != "1"){
		            //alertModel(res.msg);
				}
				showTableList = res.obj.result;
        		unique(showTableList);
			}
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.result //数据
	         };
		}
	});
}

//加载不重复的流程环节
function unique(arr){
	// 遍历arr，把元素分别放入tmp数组(不存在才放)
	$("#taskDefKey").empty();
	$("#taskDefKey").append("<option value=''>---审核环节---</option>");
	
	var tmp = new Array();
	for(var i in arr){
		//该元素在tmp内部不存在才允许追加
		if(arr[i].act!=null&&tmp.indexOf(arr[i].act.taskDefKey)==-1){
			$("#taskDefKey").append("<option value='"+arr[i].act.taskDefKey+"'>"+arr[i].act.taskName+"</option>");
			tmp.push(arr[i].act.taskDefKey);
		}
	}
	return tmp;
}


//审核
function checkInfo(){
	if(!hadCheckedRowData()){
	   return false;
	}
	var rentInformation = showTableList[0];
	var rentinformationId=rentInformation.rentinformationId;
	console.log("rowschecked==="+rowschecked.length);
	var taskId=rowschecked[0].act.taskId;
	var endDate=rentInformation.endDate;
	
	//转化时间戳
	function getTime(ns){
		var date = new Date(ns);
		var datTime=date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate()+'   ' + date.getHours()+
		':' + date.getMinutes()+':' + date.getSeconds();
		return datTime;
		}
	var date=getTime(endDate);
	window.location.href='goCheckinfo.html?rentinformationId='+rentinformationId+'&endDate='+date+'&taskId='+taskId;
}

function hadCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
	return false;
	}
	return true;
}
//导出
function exportExcel(){
	if(confirm("您确定要导出吗？")){
		var exportObjs = new Array();
		// 从选中行中挑出可以启用的item
		for (var s = 0; s < rowschecked.length; s++) {
			var row = rowschecked[s];
			exportObjs.push(row.rentinformationId);
		}
		exportObjs.push(1);
		exportObjs.push(2);
		var para="rentinformationIds="+exportObjs.join(",");
		window.open("exportRentInformation?"+para,"_blank");
	}
}
