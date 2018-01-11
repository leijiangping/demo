
//初始化
$(function(){
	//初始化
	init();
});

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
	$("#eleBillaccountPaymentdetailForm #paymentMethod").val(billaccountPaymentInfo.paymentMethod);
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
					$("#cmccRatio").text(contract.cmccRatio);
					
					$("#includePriceTax").val(contract.includePriceTax);
					$("#supplierCode").text(contract.supplierCode);
					$("#supplierName").text(contract.supplierName);
					$("#supplierAddress").text(contract.supplierAddress);
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
						if ($(this).find("#tax").val() == null || $(this).find("#tax").val() == ''){
							$(this).find("#tax").val($("#billamountTaxamount").val());
						}
					});
				}	
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
		validYuzhi();
	}
	//组件disabled为true的时候无法序列化
	$("input[type='text']").attr("disabled",false);
	var billaccountdata = $("#eleBillaccountPaymentdetailForm").serializeObject();
	
	billaccountdata.submitState=submitState;
	
	var billaccountsubmitData = JSON.stringify(billaccountdata);
	
	$.post("saveOrUpdateBillaccountDetail" , billaccountdata ,function(data){
		
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
		
		$.post("saveOrUpdateDetail" , {"param":JSON.stringify(electricmeterArr)} ,function(data11){
			localStorage.removeItem("billaccountPaymentInfo");
			window.location.href = "manage.html";
		});
	});
}


function back(){
	localStorage.removeItem("billaccountPaymentInfo");
	javascript:history.back(-1);
}