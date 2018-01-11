//已显示表格list
var showTableList = null;

var operate_type = 1;// 1 新增，2 修改
var twrRentInfoId;
var rentinformationhistoryId;//拆分
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

$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	curPageNum = 1;
	getAddress();
	// 加载修改数据
	
	twrRentInfoId=getParam("twrRentInfoId");
	rentinformationhistoryId=getParam("rentinformationhistoryId");
	loadData(rentinformationhistoryId);
}


function loadForm(){
	var arr=new Array();
	var product1IfbbuOnRoom=($("#product1IfbbuOnRoom option:selected").val())==1?true:false;//RRU拉远时BBU是否放在铁塔公司机房

	var isSpecEnterStation=$("#isSpecEnterStation option:selected").val();//0-6可否上战
	var maintenanceLevelId=$("#maintenanceLevelId option:selected").val();//维护等级
	var electricProtectionMethodId=$("#electricProtectionMethodId option:selected").val();//电力保障服务模式
	var ifSelectPowerService=$("#ifSelectPowerService option:selected").val();//是否选择发电服务
	var ifHasPowerCondition=$("#ifHasPowerCondition option:selected").val();//是否具备发电条件
	var oilGenerateElectricMethodId=$("#oilGenerateElectricMethodId option:selected").val();//油机发电服务模式
	var product1UnitNum=$("#product1UnitNum").val();//产品单元数
	var maintenanceFeeDis=$("#maintenanceFeeDis").val();//维护费折扣
	var electricImportFeeDis=$("#electricImportFeeDis").val();//电力引入费折扣
	var towerShareDis=$("#towerShareDis").val();//铁塔共享折扣
	var startDate=$("#startDate").html();//开始时间
	var endDate=$("#endDate").html();//结束时间
	
	arr.push({"product1IfbbuOnRoom":product1IfbbuOnRoom},
			{"isSpecEnterStation":isSpecEnterStation},
			{"maintenanceLevelId":maintenanceLevelId},
			{"electricProtectionMethodId":electricProtectionMethodId},
			{"ifSelectPowerService":ifSelectPowerService},
			{"ifHasPowerCondition":ifHasPowerCondition},
			{"oilGenerateElectricMethodId":oilGenerateElectricMethodId},
			{"product1UnitNum":product1UnitNum},
			{"maintenanceFeeDis":maintenanceFeeDis},
			{"electricImportFeeDis":electricImportFeeDis},
			{"towerShareDis":towerShareDis},
			{"startDate":startDate},
			{"endDate":endDate}
		   );
	return arr;
}
function loadJsou(){
	var Jsou={};
	var product1IfbbuOnRoom=($("#product1IfbbuOnRoom option:selected").val())==1?true:false;//RRU拉远时BBU是否放在铁塔公司机房
	var isSpecEnterStation=($("#isSpecEnterStation option:selected").val())==1?true:false;//0-6可否上战
	var maintenanceLevelId=($("#maintenanceLevelId option:selected").val()).toString();//维护等级
	var electricProtectionMethodId=($("#electricProtectionMethodId option:selected").val()).toString();//电力保障服务模式
	var ifSelectPowerService=($("#ifSelectPowerService option:selected").val())==1?true:false;;//是否选择发电服务
	var ifHasPowerCondition=($("#ifHasPowerCondition option:selected").val())==1?true:false;;//是否具备发电条件
	var oilGenerateElectricMethodId=($("#oilGenerateElectricMethodId option:selected").val()).toString();//油机发电服务模式
	var product1UnitNum=($("#product1UnitNum").val()).toString();//产品单元数
	var maintenanceFeeDis=$("#maintenanceFeeDis").val();//维护费折扣
	var electricImportFeeDis=$("#electricImportFeeDis").val();//电力引入费折扣
	var towerShareDis=$("#towerShareDis").val();//铁塔共享折扣
	var startDate=($("#startDate").html()).toString();//开始时间
	var endDate=($("#endDate").html()).toString();//结束时间
	
	Jsou={"product1IfbbuOnRoom":product1IfbbuOnRoom,
		"isSpecEnterStation":isSpecEnterStation,
			"maintenanceLevelId":maintenanceLevelId,
			"electricProtectionMethodId":electricProtectionMethodId,
			"ifSelectPowerService":ifSelectPowerService,
			"ifHasPowerCondition":ifHasPowerCondition,
			"oilGenerateElectricMethodId":oilGenerateElectricMethodId,
			"product1UnitNum":product1UnitNum,
			"maintenanceFeeDis":maintenanceFeeDis,
			"electricImportFeeDis":electricImportFeeDis,
			"towerShareDis":towerShareDis,
			"startDate":startDate,
			"endDate":endDate
			};
	return Jsou;
}
var oldArr=[];
function loadData(twrRentInfoId){
	$.ajax({
        url : 'queryTwrHistoryById',
        type : 'post',
        cache : false,
        dataType : 'json',
        data : {
        	pageSize : 10,
        	pageNum : 1,
        	twrRentHistoryId:twrRentInfoId
		},
        success : function(data) {
        if (data != null) {
        	oldArr=loadForm();
        	//回显
        	console.log(data.Obj.businessConfirmNumber)
        	$("#rentinformationId").html(data.Obj.rentinformationId);
        	$("#businessConfirmNumber").html(data.Obj.businessConfirmNumber);
           }
        },
        error : function() {
            alert("请求异常");
        }
    });
}

//修改保存
function sendData(){
	  var array1 = oldArr;
	  var array2 = loadForm();
	  var loadObj= loadJsou();
	  var effectDateChange = false;
	  console.log(loadJsou());
	console.log(loadObj.product1IfbbuOnRoom)
	  var dataArr = new Array();
	  var result = [];
	  for (var i = 0; i < array2.length; i++)  
	  {  
	      for (key in array2[i])  
	      {  
	          if (array2[i][key] != array1[i][key])  
	          {  
	        	  if (key == "startDate"){
	        		  effectDateChange = true;
	        	  }
	                console.log(array2[i]);
	                var oldValue=array1[i][key];
	                newarr(key,oldValue,array2[i][key]);
	          }  
	      }  
	  }
	 // console.log(result);
	
 function newarr(fieldName,oldValue,newValue){
	
    dataArr.push({ "rentinformationchangeId":'',
			        "rentinformationhistoryId":rentinformationhistoryId,
			        "fieldName":fieldName,
			        "oldValue":oldValue,
			        "oldStartDate":'',
			        "oldEndDate":'', 
			        "newValue":newValue, 
			        "newStartDate":'',
			        "newEndDate":'',
			        "updateUserId":'',
			        "updateTime":'', 
			        "checkState":''
      });
       // console.log(dataArr);
	  }

      var historyVo = {
    		    "rentinformationhistoryId":rentinformationhistoryId,
    	    	"rentinformationId":twrRentInfoId,
    	    	"product1IfbbuOnRoom":loadObj.product1IfbbuOnRoom,
    	    	"isSpecEnterStation":loadObj.isSpecEnterStation,
    	    	"maintenanceLevelId":loadObj.maintenanceLevelId,
    	    	"electricProtectionMethodId":loadObj.electricProtectionMethodId,
    	    	"ifSelectPowerService":loadObj.ifSelectPowerService,
    	    	"ifHasPowerCondition":loadObj.ifHasPowerCondition,
    	    	"oilGenerateElectricMethodId":loadObj.oilGenerateElectricMethodId,
    	    	"product1UnitNum":loadObj.product1UnitNum,
    	    	"maintenanceFeeDis":loadObj.maintenanceFeeDis,
    	        "electricImportFeeDis":loadObj.electricImportFeeDis,
    	        "towerShareDis":loadObj.towerShareDis,
    	        "startDate":loadObj.startDate
            };

      
	$.ajax({
        url : 'addRentInfoHistoryVO?effectDateChange='+effectDateChange,
        type : 'post',
        cache : false,
        contentType: "application/json;charset=utf-8",
        dataType : 'json',
		data:JSON.stringify({"historyVO":historyVo,"twrRentInfoChangeVO":dataArr}),
        success : function(data) {
        if (data != null) {
        	
        	window.history.go(-1);
            }
        },
        error : function() {
            alert("请求异常");
        }
    });

}

function goBack(){
	window.history.go(-1);
}