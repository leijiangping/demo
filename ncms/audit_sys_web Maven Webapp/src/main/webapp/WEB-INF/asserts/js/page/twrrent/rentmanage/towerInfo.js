//已显示表格list
var showTableList = null;

var operate_type = 1;// 1 新增，2 修改

$(document).ready(function() {
	initCurrentPage();
});

function initCurrentPage(){
	curPageNum = 1;
	// 查询默认条件表单数据
	getAddress();
	loadTableData();
}

function loadTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryTowerResourceInfo", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		pageSize : ipageCount, // 每页显示的记录数
		pageNumber : curPageNum, // 当前第几页
		minimumCountColumns: 1,  //最少允许的列数
		clickToSelect: true,  //是否启用点击选中行
		fixedColumns: true,
        fixedNumber:5,
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
					pregId :	$("#City option:selected").val(),
		            regId :	$("#Village option:selected").val()
			};
			return param;
		},
		columns: [{
        	field: 'sysRegionVO.prvName',
            title: '省份'
        },{
        	field: 'sysRegionVO.pRegName',
            title: '地市'
        },{
        	field: 'sysRegionVO.regName',
            title: '区县'
        },{
            field: 'agreeBillCode',
            title: '需求单号'
        },{
            field: 'operator',
            title: '运营商'
        },{
            field: 'operatorRegName',
            title: '运营商地市'
        },{
            field: 'demandRegName',
            title: '需求承接地市'
        },{
            field: 'stationRegName',
            title: '站址所属地市'
        },{
            field: 'businessConfirmNumber',
            title: '业务确认单号'
        },{
        	field: 'scenceType',
            title: '场景划分'
        },{
        	field: 'towerStationCode',
            title: '站址编码'
        },{
            field: 'towerStationName',
            title: '站址名称'
        },{
            field: 'detailAddress',
            title: '详细地址'
        },{
            field: 'longitude',
            title: '经度'
        },{
            field: 'latitude',
            title: '纬度'
        },{
            field: 'startDate',
            title: '服务开始日期',
            formatter : function(value){
            	if(value == null){
            		return "-";
            	}
        		return new Date(value).format("yyyy-MM-dd");
            }
        },{
            field: 'endDate',
            title: '服务结束日期',
            formatter : function(value){
            	if(value == null){
            		return "-";
            	}
        		return new Date(value).format("yyyy-MM-dd");
            }
        },{
            field: 'productConfigurationId',
            title: '产品配置'
        },{
            field: 'resourcesTypeId',
            title: '共享信息',
            formatter : function(value){
            	switch(value){
	        		case "1":return '原产权方';
	        		case "2":return '既有共享';
	        		case "3":return '存量自改';
	        		case "4":return '存量改造';
	        		case "5":return '新建铁塔';
            	}
            	return value;
            }
            
            
        },{
            field: 'productTypeId',
            title: '铁塔产品'
        },{
            field: 'roomTypeId',
            title: '机房产品'
        },{
            field: 'reserveBattery',
            title: '后备电池（小时）'
        },{
            field: 'ifHasPowerCondition',
            title: '是否具备发电条件',
            formatter : function(value){
            	switch(value){
	        		case 1:return '是';
	        		case 0:return '否';
            	}
            	return value;
            }
        },{
            field: 'isSpecEnterStation',
            title: '0-6点是否上站',
            formatter : function(value){
            	switch(value){
	        		case 1:return '是';
	        		case 0:return '否';
            	}
            	return value;
            }
        },{
            field: 'ifSelectPowerService',
            title: '是否选择发电服务',
            formatter : function(value){
            	switch(value){
	        		case 1:return '是';
	        		case 0:return '否';
            	}
            	return value;
            }
        },{
            field: 'electricProtectionMethodId',
            title: '电力保障服务费模式'
        },{
            field: 'oilGenerateElectricMethodId',
            title: '油机发电服务费模式'
        },{
            field: 'maintenanceLevelId',
            title: '维护等级'
        },{
            field: 'windPressure',
            title: '风压系数'
        },{
            field: 'ifstandardBuildFee',
            title: '是否非标准建造成本',
            formatter : function(value){
            	switch(value){
	        		case 1:return '否';
	        		case 0:return '是';
            	}
            	return value;
            } 
        },{
            field: 'product1UnitNum',
            title: '产品单元1：产品单元数（个）'
        },{
            field: 'product1Height',
            title: '产品单元1：挂高（米）'
        },{
            field: 'product1AntennaNum',
            title: '产品单元1：天线数量（副）'
        },{
            field: 'product1SystemNum',
            title: '产品单元1：系统数量（套）'
        },{
            field: 'product1IfBBUOnRoom',
            title: '产品单元1：RRU拉远时BBU是否放在铁塔机房',
            formatter : function(value){
            	switch(value){
	        		case 1:return '是';
	        		case 0:return '否';
            	}
            	return value;
            }
        },{
            field: 'product1IsUpTowerRRU',
            title: '产品单元1：RRU是否上塔',
            formatter : function(value){
            	switch(value){
	        		case 1:return '是';
	        		case 0:return '否';
            	}
            	return value;
            }
        },{
            field: 'product1BBUFee',
            title: '产品单元1：BBU安装在铁塔机房的服务费（元/年）'
        },{
            field: 'product2UnitNum',
            title: '产品单元2：产品单元数（个）'
        },{
            field: 'product2Height',
            title: '产品单元2：挂高（米）'
        },{
            field: 'product2AntennaNum',
            title: '产品单元2：天线数量（副）'
        },{
            field: 'product2SystemNum',
            title: '产品单元2：系统数量（套）'
        },{
            field: 'product2IfBBUOnRoom',
            title: '产品单元2：RRU拉远时BBU是否放在铁塔机房',
            formatter : function(value){
            	switch(value){
	        		case 1:return '是';
	        		case 0:return '否';
            	}
            	return value;
            }
        },{
            field: 'product2IsUpTowerRRU',
            title: '产品单元2：RRU是否上塔',
            formatter : function(value){
            	switch(value){
	        		case 1:return '是';
	        		case 0:return '否';
            	}
            	return value;
            }
        },{
            field: 'product2BBUFee',
            title: '产品单元2：BBU安装在铁塔机房的服务费（元/年）'
        },{
            field: 'product3UnitNum',
            title: '产品单元3：产品单元数（个）'
        },{
            field: 'product3Height',
            title: '产品单元3：挂高（米）'
        },{
            field: 'product3AntennaNum',
            title: '产品单元3：天线数量（副）'
        },{
            field: 'product3SystemNum',
            title: '产品单元3：系统数量（套）'
        },{
            field: 'product3IfBBUOnRoom',
            title: '产品单元3：RRU拉远时BBU是否放在铁塔机房',
            formatter : function(value){
            	switch(value){
	        		case 1:return '是';
	        		case 0:return '否';
            	}
            	return value;
            }
        },{
            field: 'product3IsUpTowerRRU',
            title: '产品单元3：RRU是否上塔',
            formatter : function(value){
            	switch(value){
	        		case 1:return '是';
	        		case 0:return '否';
            	}
            	return value;
            }
        },{
            field: 'product3BBUFee',
            title: '产品单元3：BBU安装在铁塔机房的服务费（元/年）'
        },{
            field: 'currentTowerShareNum',
            title: '当前铁塔共享客户数量'
        },{
            field: 'addRoomSupportingShareNum',
            title: '当前机房及配套共享客户数量'
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
            field: 'towerPrice',
            title: '铁塔建造成本（元/年）'
        },{
            field: 'towerPrice',
            title: '机房建造成本（元/年）'
        },{
            field: 'supportingPrice',
            title: '配套建造成本（元/年）'
        },{
            field: 'maintenanceFee',
            title: '维护费'
        },{
            field: 'stageFee',
            title: '场地费'
        },{
            field: 'electricImportFee',
            title: '电力引入费（元/年）'
        },{
            field: 'maintenanceFeeDis',
            title: '维护费折扣'
        },{
            field: 'stageFeeDis',
            title: '场地费折扣'
        },{
            field: 'electricImportFeeDis',
            title: '电力引入费折扣'
        },{
            field: 'towerShareDis',
            title: '铁塔共享折扣'
        },{
            field: 'roomSupportingShareDis',
            title: '机房及配套共享折扣'
        },{
            field: 'ifRRUDis',
            title: 'RRU是否折扣',
            formatter : function(value){
            	switch(value){
	        		case 1:return '是';
	        		case 0:return '否';
            	}
            	return value;
            }
        },{
            field: 'electricProtectionFee',
            title: '包干电费'
        },{
            field: 'oilGeneratorElectricFee',
            title: '油机发电服务费'
        },{
            field: 'hightLevelFee',
            title: '超过10%高等级服务站址额外维护服务费'
        },{
            field: 'batteryProtectionFee',
            title: '蓄电池额外保障费'
        },{
            field: 'wlanServiceFee',
            title: 'WLAN产品服务费（元/年）（不含税）'
        },{
            field: 'microwaveServiceFee',
            title: '微波产品服务费（元/年）（不含税）'
        },{
            field: 'otherFee',
            title: '其他费用（元/年）（不含税）'
        },{
            field: 'otherFeeRemark',
            title: '其他费用说明'
        },{
            field: 'totalAmount',
            title: '塔类产品服务费（元/年）（不含税）'
        },{
            field: 'totalActualAmount',
            title: '产品服务费合计（元/年）（含税）'
        },{
            field: 'publishUser',
            title: '业务单起租发起人'
        },{
            field: 'publishTime',
            title: '业务单起租发起时间',
            formatter : function(value){
            	if(value == null){
            		return "-";
            	}
        		return new Date(value).format("yyyy-MM-dd");
            }
        },{
            field: 'towerHeight',
            title: '塔高（米）'
        },{
            field: 'roomFeeMethod',
            title: '场地费模式'
        },{
            field: 'elecImportFeeMethod',
            title: '电力引入费模式'
        },],
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success != "1"){
		            alertModel(res.msg);
				}
				showTableList = res.obj.result;
			}
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.result //数据
	         };
		}
	});
}

function hadCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}


/**
 * 导入
 */

function importResourceInfo(){
	//"铁塔侧账单导入" 弹出框名称
	//"_towerBillbalance" 功能标识
	//"formSubmit" 回调方法
	importModel("铁塔资源信息导入","_towerResourceInfo","formSubmit");//弹出导入框
}	
/**
 * 上传文件并保存到数据库
 * @param suffix 模块标识
 */
function formSubmit(suffix){
	var formData = new FormData($("#dataForm")[0]); 
	$.ajax({
		url:'importTowerResourceInfo',
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
function exportResourceInfo(){
	confirmModel("您确定要导出吗？","exportInfo");
} 

function exportInfo(){

//	var exportObjs = new Array();
//	// 从选中行中挑出可以启用的item
//	if($("#educeStyle").val() == 1){
//		$('#tb').bootstrapTable('getData');
//	}else if($("#educeStyle").val() == 2){
//		var selectRows=$('#tb').bootstrapTable('getSelections');
//		
//			$.each(selectRows,function(i,v){
//				exportObjs.push(v.rentinformationtowerId);
//			});
//	}
	//修改导出方法
	
	//修改导出方法 author：zhujj
	var para="&pregId="+ $("#City").val();
	para+="&regId="+ $("#Village").val();
	window.open("exportTowerResourceInfo?"+para,"_blank");
}

