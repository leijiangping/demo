//用于判断唯一性
var flag = false;
$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	getAddress("",true);
	getUpdateParam();
	getPunishCause();
}

/**
 * 扣罚费用（限制）,只能输入数字和.（点） 且 小数点后不能超过两位
 * */
function getPunishCause(){
	$("#punishAmount").keyup(function () {
	    var reg = $(this).val().match(/\d+\.?\d{0,2}/);
	    var txt = '';
	    if (reg != null) {
	        txt = reg[0];
	    }
	    $(this).val(txt);
	}).change(function () {
	    $(this).keypress();
	    var v = $(this).val();
	    if (/\.$/.test(v))
	    {
	        $(this).val(v.substr(0, v.length - 1));
	    }
	});
}

/**
 * 判断唯一性
 * */
function checkExaminationOnly(){
	var regId = $("#City").val();
	var punishYearMonth = $("#datetimepicker").val();
	$.ajax({
		url : 'queryTwrRegPunish',
		data : {
			regId : regId,
			punishYearMonth : punishYearMonth
		},
		type : 'post',
		dataType : 'json',
		aysnc:false,
		cache : false,
		success : function(back) {
			if (back != null) {
				console.log(back);
 				if(back.success=="1"){
 					if(back.Obj!=null){
 	 					var data = back.Obj;
	 					if(data!=null&&data.length>0){
	 						alertModel("您所输入的所属地市和日期数据的地市指标扣罚已经存在！");
	 						$("#City").val("");
	 						$("#datetimepicker").val("");
	 						return false
	 						/*$("#City").next("#err").html("<img src=\"../../../image/error.png\"/>数据已存在！");
	 						$("#City").next("#err").css({"color":"red"});
	 						$("#datetimepicker").next("#err").html("<img src=\"../../../image/error.png\"/>数据已存在！");
	 						$("#datetimepicker").next("#err").css({"color":"red"});*/
	 						//flag=true;
	 					}
 					}
 				}
			}
		},
		error : function() {
			alertModel("请求异常");
			//flag=true;
		}
	});
	
}

/**
 * 判断为修改，编辑表单
 * */
function getUpdateParam(){ 
	if(getParameter("type")==2){
		var twrRegPunishId = getParameter("twrRegPunishId");
		var auditState = getParameter("auditState");
		if(auditState == 8){
			//流转记录
			histoicFlowList(TwrRegPunish.tableName,twrRegPunishId);
		}
		
		$.ajax({
		    url:'queryTwrRegPunishId',
		    data: {
		    	twrRegPunishId:twrRegPunishId
		    },
	 		type : 'post',
		    cache:false,
		    async:true,
		    success:function(result){
		    	var datas = result.Obj;
		    	if(result!=null){
		    		getAddress("",true,datas.regId);
		    		$("#City").attr("disabled","disabled");
		    		$('#datetimepicker').val(datas.punishYearMonth);
		    		$('#punishName').val(datas.punishName);
		    		$('#punishCause').val(datas.punishCause);
		    		$('#punishPerson').val(datas.punishPerson);
		    		$('#punishAmount').val(datas.punishAmount);
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
			required : "请输入扣罚名称",
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

/**
 * 添加/修改的确定保存
 * */
function newsFormSubmit(){
	checkExaminationOnly();
	if(validform().form()){
	if(getParameter("type")==1){
		
		/*if($('#City option:selected').val() == ""){
			alertModel("地市不能为空！");
			return false;
		}
		if($("#datetimepicker").val()==""){
			alertModel("扣罚日期不能为空，请选择！");
			return false;
		}
		checkExaminationOnly();
		if(flag){
			return false;
		}
		
		if($("#punishName").val()==""){
			alertModel("其他扣罚名称不能为空！");
			return false;
		}
		if($("#punishAmount").val()==""){
			alertModel("扣罚费用不能为空！");
			return false;
		}
		if($("#punishPerson").val()==""){
			alertModel("扣罚申请人不能为空！");
			return false;
		}*/
		var regId=$('#City option:selected').val();
		var punishYearMonth=$('#datetimepicker').val();
		var punishName=$('#punishName').val();
		var punishCause=$('#punishCause').val();
		var punishPerson=$('#punishPerson').val();
		var punishAmount=$('#punishAmount').val();
		$("#saveSet").attr("disabled",true);
		$.ajax({
		    url:'insertTwrRegPunish',
		    data: {
		    	regId:regId,
		    	punishYearMonth:punishYearMonth,
		    	punishName:punishName,
		    	punishCause:punishCause,
		    	punishPerson:punishPerson,
		    	punishAmount:punishAmount
		    },
	 		type : 'post',
		    cache:false,
		    async:true,
		    success:function(result){
		        //请求成功时
		    	if(result!=null){
		    		alertModel(result.msg);
		    	}
		    	window.location.href="cityExamination.html";
		    	$("#saveSet").attr("disabled",false);
		    },
		    error:function(){
		    	alertModel("请求失败！");
		    	$("#saveSet").attr("disabled",false);
		    }
		});
	}
	else if(getParameter("type")==2){
    	$("#City").removeAttr("disabled");
		$.ajax({
		    url:'updateTwrRegPunish',
		    data: {
		    	regId:$('#City option:selected').val(),
		    	punishYearMonth:$('#datetimepicker').val(),
		    	punishName:$('#punishName').val(),
		    	punishCause:$('#punishCause').val(),
		    	punishPerson:$('#punishPerson').val(),
		    	punishAmount:$('#punishAmount').val(),
		    	twrRegPunishId:getParameter("twrRegPunishId"),
		    },
	 		type : 'post',
		    cache:false,
		    async:true,
		    success:function(result){
		        //请求成功时
		    	if(result!=null){
		    		alertModel(result.msg);	    			
		    	}
	    		window.location.href="cityExamination.html";
		    },
		    error:function(){
				alertModel("请求失败！");
		    }
		});
	}	
  }
}