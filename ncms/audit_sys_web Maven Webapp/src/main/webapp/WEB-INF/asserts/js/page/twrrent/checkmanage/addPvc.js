var type=null;// 1 添加，2 修改



$(document).ready(function() {
	
	type=getParam("type");
	var regId=getParam("regId");
	getUpdateParam();
	if(type==2){
		$('#City').attr({"selected":true,"disabled":"disabled"});
	}else{
		getAddress();
		$('#City').removeAttr("readonly");
	}
});	


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
/**
 * 判断为修改，编辑表单
 * */
function getUpdateParam(){
	if(getParam("type")==2){
		var twrProvincePunishId = getParam("twrProvincePunishId");
		$.ajax({
		    url:'queryCheckIndexFine',
		    data: {
		    	checkId:twrProvincePunishId
		    },
	 		type : 'post',
		    cache:false,
		    async:true,
		    success:function(result){
		    	console.log(result);
		    	var datas = result.Obj;
		    	console.log(datas)
		    	if(result!=null){
		    		$('#City').attr({"selected":true,"disabled":"disabled"});
		    		$('#City option').val(datas.regName).text(datas.regName);
		    		$('#datetimepicker').val(datas.punishYearMonth);
		    		$('#punishName').val(datas.punishName);
		    		$('#punishCause').val(datas.punishCause);
		    		$('#punishPerson').val(datas.punishPerson);
		    		$('#punishAmount').val(datas.punishAmount);
		    		$('#auditState').val(datas.auditState);		    		
		    	}
		    },
		    error:function(){
		    	alertModel("请求失败！");
		    }
		});
	}
};


//验证
function validform(){

	var twr_validate= bindformvalidate("#dataForm", {
		City : {
			required : true,
			maxlength : 20,
		},
		datetimepicker:{
			required : true,
			maxlength : 20,
		},
		punishName:{
			required : true,
			maxlength : 20,
		},
		punishPerson:{
			required : true,
			maxlength : 20,
		},
		
		punishAmount:{
			required : true,
			min:1,
		},
		punishCause:{
			required : true,
			maxlength : 20,
		}
	}, {
		City:{
			required : "请选择区县",
			maxlength: "铁塔名称最大长度20"
		},
		datetimepicker : {
			required : "请选择日期",
			maxlength: "铁塔名称最大长度20"
		},
		punishName:{
			required : "请输入扣罚指标",
			maxlength: "铁塔名称最大长度20"
		},
		punishPerson:{
			required : "请输入申请人",
			maxlength: "铁塔名称最大长度20"
		},
		
		punishAmount:{
			required : "请输扣罚金额",
			min:"只能输入数字"
		},
		punishCause:{
			required :"请输入原因说明",
		}
	});
  return twr_validate;
}


function FormSubmit(){
	if(validform().form()){
	var regId=$('#City option:selected').val();//地市id
	var punishYearMonth=$('#datetimepicker').val();//时间
	var punishCause=$('#punishCause').val();//扣罚原因
	var punishName=$('#punishName').val();//扣罚指标
	var punishPerson=$('#punishPerson').val();//扣罚申请人
	var punishAmount=$('#punishAmount').val();//扣罚金额
	if(type=="1"){
		$("#saveSet").attr("disabled",true);
		$.ajax({ 
		    url:'addCheckIndexFine',
		    data: {
		    	regId:regId,
		    	punishYearMonth:punishYearMonth,
		    	punishCause:punishCause,
		    	punishName:punishName,
		    	punishPerson:punishPerson,
		    	punishAmount:punishAmount,
		    	auditState:auditState
		    },
	 		type : 'post',
		    cache:false,
		    async:true,
		    success:function(result){
		    	console.log(result);
		        //请求成功时
		    	if(result!=null){
	    			alertModel(result.msg);
	    			//loadTableData();
		    	}
		    	window.location.href="pvcExam.html";
		    	$("#saveSet").attr("disabled",false);
		    },
		    error:function(){
		    	alertModel("请求失败！");
		    	$("#saveSet").attr("disabled",false);
		    }
		});
	}
	else if(type=="2"){
		var auditState=$('#auditState').val();//审核状态
		$.ajax({
		    url:'updateCheckIndexFine',
		   
		    data: {
		    	regId:getParam("regId"),
		    	twrProvincePunishId:getParam("twrProvincePunishId"),//主站id
		    	punishYearMonth:punishYearMonth,
		    	punishCause:punishCause,
		    	punishName:punishName,
		    	punishPerson:punishPerson,
		    	punishAmount:punishAmount,
		    	auditState:auditState
		    },
	 		type : 'post',
		    cache:false,
		    async:true,
		    success:function(result){
		        //请求成功时
		    	if(result!=null){
	    			alertModel(result.msg);
	    			//loadTableData();
		    	}
		    	window.location.href="pvcExam.html";
    			
		    },
		    error:function(){
				alertModel("请求失败！");
		    }
		});
	}	
  }
}