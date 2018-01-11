
//初始化
$(function(){
	//初始化
	init();
});
var billaccountPaymentInfo;
var uploader;
function init(){
	
	var billaccountPaymentInfo = localStorage.getItem("billaccountPaymentInfo");
	billaccountPaymentInfo = JSON.parse(billaccountPaymentInfo);
	
	$("#billaccountCode").text(billaccountPaymentInfo.billaccountCode);
	$("#billaccountName").text(billaccountPaymentInfo.billaccountName);
	
	$("#eleBillaccountPaymentdetailForm #billaccountId").val(billaccountPaymentInfo.billaccountId);
	
	$("#eleBillaccountPaymentdetailForm #billaccountpaymentdetailId").val(billaccountPaymentInfo.billaccountpaymentdetailId);
	$("#eleBillaccountPaymentdetailForm #billamountStartdate").val(billaccountPaymentInfo.billamountStartdate);
	$("#eleBillaccountPaymentdetailForm #billamountEnddate").val(billaccountPaymentInfo.billamountEnddate);
	$("#eleBillaccountPaymentdetailForm #billamountDate").val(billaccountPaymentInfo.billamountDate);
	$("#eleBillaccountPaymentdetailForm #buyMethod").val(billaccountPaymentInfo.buyMethod);
	$("#eleBillaccountPaymentdetailForm #billType").val(billaccountPaymentInfo.billType);
	$("#eleBillaccountPaymentdetailForm #paymentMethod").val(billaccountPaymentInfo.paymentMethod);
	$("#eleBillaccountPaymentdetailForm #invoiceType").val(billaccountPaymentInfo.invoiceType);
	$("#eleBillaccountPaymentdetailForm #taxPaymentType").val(billaccountPaymentInfo.taxPaymentType);
	$("#eleBillaccountPaymentdetailForm #isVatDeduction").val(billaccountPaymentInfo.isVatDeduction);
	/*$("#eleBillaccountPaymentdetailForm #businessType").val(billaccountPaymentInfo.businessType);
	$("#eleBillaccountPaymentdetailForm #amountType").val(billaccountPaymentInfo.amountType);
	$("#eleBillaccountPaymentdetailForm #billamountType").val(billaccountPaymentInfo.billamountType);*/
	$("#eleBillaccountPaymentdetailForm #billamountNotaxSys").val(billaccountPaymentInfo.billamountNotaxSys);
	$("#eleBillaccountPaymentdetailForm #billamountNotax").val(billaccountPaymentInfo.billamountNotax);
	$("#eleBillaccountPaymentdetailForm #lossAmountSys").val(billaccountPaymentInfo.lossAmountSys);
	$("#eleBillaccountPaymentdetailForm #billamountTaxamountSys").val(billaccountPaymentInfo.billamountTaxamountSys);
	$("#eleBillaccountPaymentdetailForm #billamountTaxamount").val(billaccountPaymentInfo.billamountTaxamount);
	$("#eleBillaccountPaymentdetailForm #lossAmount").val(billaccountPaymentInfo.lossAmount);
	$("#eleBillaccountPaymentdetailForm #otherAmount").val(billaccountPaymentInfo.otherAmount);
	$("#eleBillaccountPaymentdetailForm #billAmountSys").val(billaccountPaymentInfo.billAmountSys);
	$("#eleBillaccountPaymentdetailForm #billAmountActual").val(billaccountPaymentInfo.billAmountActual);
	$("#eleBillaccountPaymentdetailForm #totalDegree").val(billaccountPaymentInfo.totalDegree);
	$("#eleBillaccountPaymentdetailForm #calcMulti").val(billaccountPaymentInfo.calcMulti);
	$("#eleBillaccountPaymentdetailForm #totalDegreeActual").val(billaccountPaymentInfo.totalDegreeActual);
	$("#eleBillaccountPaymentdetailForm #paymentdetailNote").val(billaccountPaymentInfo.paymentdetailNote);
	
	 $.ajax({
		  type : "POST",
		  url : 'queryUpdateElectricmeterInfo',
		  data:{"billaccountId": billaccountPaymentInfo.billaccountId , "billaccountpaymentdetailId":billaccountPaymentInfo.billaccountpaymentdetailId},
		  dataType : 'json',
		  cache : false,
		  success : function(data) {
			  
			  if(data.Obj.contract){
				//合同信息
				  var contract = data.Obj.contract;
					$("#contractCode").text(contract.contractCode);
					$("#contractName").text(contract.contractName);
					$("#isIncludeAll").val(contract.isIncludeAll);
					
					$("#paySignAccount").val(contract.paySignAccount);
					$("#paySign").val(contract.paySign);
					
					$("#taxRate").text(contract.taxRate);
					$("#supplyMethod").val(contract.supplyMethod);
					$("#includeLoss").val(contract.includeLoss);
					$("#lossType").val(contract.lossType);
					
					$("#independentMeter").val(contract.independentMeter);
					$("#contractTotalAmount").text(contract.contractTotalAmount == null ? "":contract.contractTotalAmount);
					$("#cmccRatio").text(contract.cmccRatio== null ? "":contract.cmccRatio);
					
					$("#includePriceTax").val(contract.includePriceTax);
					$("#supplierCode").text(contract.supplierCode);
					$("#supplierName").text(contract.supplierName);
					$("#supplierAddress").text(contract.supplierAddress);
					selectFileList(billaccountPaymentInfo);
			  }else{
				  alert("没有关联合同");
				  return;
			  }
			  
				
				$("#myTab").html(data.Obj.li);
				  $("#myTabContent").html(data.Obj.tabcontent);
				//处理分摊后度数和税金
				if (objNotNull($("#cmccRatioAfter")) && objNotNull($("#nowDegree")) && objNotNull($("#cmccRatio"))){
					$("#myTabContent").find("form").each(function(){
						var nowDegree = $(this).find("#nowDegree").val();
						var cmccRatio = $(this).find("#cmccRatio").val();
						if (nowDegree != null && nowDegree != '' && cmccRatio != null && cmccRatio != ''){
							var useDegrees = nowDegree*cmccRatio/100;
							$(this).find("#cmccRatioAfter").val(useDegrees);
						}
/*						if ($(this).find("#tax").val() == null || $(this).find("#tax").val() == ''){
							$(this).find("#tax").val($("#billamountTaxamount").val());
						}*/
						var payamountTax = $(this).find("#tax").val();
						if(payamountTax != null && payamountTax != ''){
							$(this).find("#tax").val(payamountTax);
						}
					});
				}	
		  }
	 });
	 /**
		 * 表单table文件上传
		 * */
		var callback=function uploadSuccessBack(file,json){
			if(json.success == 1){
				alertModel("上传成功");
				selectFileList(billaccountPaymentInfo);//上传成功重新查询附件列表
			}else{
				alertModel(msg);
			}	
		};
		uploader = webUploadInit("billaccountpaymentdetailId","合同附件","uploadFile",callback);
}
function selectFileList(billaccountPaymentInfo){
	var billaccountPaymentInfo = localStorage.getItem("billaccountPaymentInfo");
	billaccountPaymentInfo = JSON.parse(billaccountPaymentInfo);
	if(billaccountPaymentInfo.billaccountId==null||billaccountPaymentInfo.billaccountId==""){
		return false;
	}
	$("#attachFile").html("");
	$.ajax({
		url : 'selectByBusiness',// 跳转到 action
		data : {
			businessId:billaccountPaymentInfo.billaccountId,
			businessType:"ele_payment"
		},
		type : 'post',
		dataType : 'json',
		beforeSend: function(){
			console.log("正在进行，请稍候");
		},
		success : function(back) {
			if(back.success == 1){
				if(back.obj!=null){

					var file = "<li class='list-group-item row'>";
					file += "<div class='col-md-2'></div>";
					file += "<div class='col-md-4'>文件名</div>";
					file += "<div class='col-md-2'>操作</div>";
					file += " </li>";
					$.each(back.obj.list,function(i,v){
						file += "<li class='list-group-item row'>" +
						"<div class='col-md-2'></div>" +
						"<div class='col-md-4'><a href='downLoad?path="+v.attachmentUrl+"' >"+v.attachmentName+"</a></div>" +
						"<div class='col-md-2'><a href='#' class='btn' onclick=\"removeFile('"+v.attachmentUrl+"')\">删除</a></div>" +
						"</li>";
					});

					$("#attachFile").html(file);
				}
			}
		},
		error : function(back){
			alertModel(back.msg);
		}
	});
	
}
//上传附件
function uploadFile(){
	var billaccountPaymentInfo = localStorage.getItem("billaccountPaymentInfo");
	billaccountPaymentInfo = JSON.parse(billaccountPaymentInfo);
	var obj={"billaccountpaymentdetailId":billaccountPaymentInfo.billaccountId};
	webUploadClick("billaccountpaymentdetailId",uploader,obj);
}
//删除附件
function removeFile(fileUrl){
	$.ajax({
		url : 'delFile',// 跳转到 action
		data : {
			fileUrl : fileUrl
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		beforeSend: function(){
			console.log("正在进行，请稍候");
		},
		success : function(back) {
			if(back.success == 1){
				selectFileList(billaccountPaymentInfo);
			}
			alertModel(back.msg);
		},
		error : function(back){
			alertModel(back.msg);
		}
	});
}

function objNotNull(obj){
	 return obj.length > 0;
}


function save(submitState){
	if(calcAmount()){	
	}else{
		return;
	}
	
	var isIncludeAll = $("#isIncludeAll").val();
	
	//valid
	if(isIncludeAll==1){
		//包干  备注校验
		validIncludeAllNote();
	}else{
		//非包干  备注校验
		if(validYuzhi()==false){
			return;
		}
	}
	//组件disabled为true的时候无法序列化
	$("input[type='text']").attr("disabled",false);
	var billaccountdata = $("#eleBillaccountPaymentdetailForm").serializeObject();
	
	billaccountdata.submitState=submitState;
	
	var billaccountsubmitData = JSON.stringify(billaccountdata);
	$("#saveSet").attr("disabled",true);
	
	$.ajax({
		  type : "POST",
		  url : 'saveOrUpdateBillaccountDetail',
		  data:billaccountdata,
		  dataType : 'json',
		  cache : false,
		  async:false,
		  success :function(data){
		
		var electricmeterArr = [];
		$("#myTabContent").find("form").each(function(){
			var electricmeterdata = $(this).serializeObject();
			
			var useDegrees = [];
			$(this).find("input[name='useDegrees']").each(function(){
				useDegrees.push($(this).val());
			});
			if(useDegrees.length>0){
				electricmeterdata.useDegrees = useDegrees.join("|");
			}
			
			
			var lossDegrees = [];
			$(this).find("input[name='lossDegrees']").each(function(){
				lossDegrees.push($(this).val());
			});
			if(lossDegrees.length>0){
				electricmeterdata.lossDegrees = lossDegrees.join("|");
			}
			
			electricmeterArr.push(electricmeterdata);
		});
		$("#saveSet").attr("disabled",false);
		var totalDegreeActual = $("#eleBillaccountPaymentdetailForm #totalDegreeActual").val();
		var billamountEnddate = $("#eleBillaccountPaymentdetailForm #billamountEnddate").val();
		var billamountStartdate = $("#eleBillaccountPaymentdetailForm #billamountStartdate").val();
		var billaccountId = $("#eleBillaccountPaymentdetailForm #billaccountId").val();
		var billaccountpaymentdetailId=$("#eleBillaccountPaymentdetailForm #billaccountpaymentdetailId").val
		var datasbillamount={"param":JSON.stringify(electricmeterArr),"totalDegreeActual":totalDegreeActual,"billamountEnddate":billamountEnddate,
				"billamountStartdate":billamountStartdate,"billaccountId":billaccountId,"billaccountpaymentdetailId":billaccountpaymentdetailId};
		
		$.ajax({
			  type : "POST",
			  url : 'saveOrUpdateDetail',
			  data:datasbillamount,
			  dataType : 'json',
			  cache : false,
			  async:false,
			  success :function(data11){
					localStorage.removeItem("billaccountPaymentInfo");
					window.location.href = "manage.html";
			  }
		});
	  }
	});
}


function back(){
	localStorage.removeItem("billaccountPaymentInfo");
	javascript:history.back(-1);
}