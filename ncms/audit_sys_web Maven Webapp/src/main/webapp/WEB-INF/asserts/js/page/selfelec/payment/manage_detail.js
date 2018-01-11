
//初始化
$(function(){
	//初始化
	init();
});
var charegMethod = "";
var taxPayType = "";
var amountType = "";

function init(){
	var billaccountPaymentInfo = localStorage.getItem("billaccountPaymentInfo");
	
	billaccountPaymentInfo = JSON.parse(billaccountPaymentInfo);
	
	
	$("#billaccountCode").text(billaccountPaymentInfo.billaccountCode);
	$("#billaccountName").text(billaccountPaymentInfo.billaccountName);

	$("#eleBillaccountPaymentdetailForm #billaccountId").val(billaccountPaymentInfo.billaccountId);
	
	$("#eleBillaccountPaymentdetailForm #billaccountpaymentdetailId").val(billaccountPaymentInfo.billaccountpaymentdetailId);
	$("#eleBillaccountPaymentdetailForm #billamountStartdate").text(filterNullValue(billaccountPaymentInfo.billamountStartdate));
	$("#eleBillaccountPaymentdetailForm #billamountEnddate").text(filterNullValue(billaccountPaymentInfo.billamountEnddate));
	$("#eleBillaccountPaymentdetailForm #billamountDate").text(filterNullValue(billaccountPaymentInfo.billamountDate));
	$("#eleBillaccountPaymentdetailForm #buyMethod").text(billaccountPaymentInfo.buyMethod == 0 ? "后付费":billaccountPaymentInfo.buyMethod == 1?"预付费":"IC卡");
	$("#eleBillaccountPaymentdetailForm #paymentMethod").text(billaccountPaymentInfo.paymentMethod == 2? "托收":"转账" );
	$("#eleBillaccountPaymentdetailForm #paymentMethod").val(billaccountPaymentInfo.paymentMethod);
	var billTypeText;
	switch(billaccountPaymentInfo.billType)
	{
	case 0:
		billTypeText = "借款";
	  break;
	case 1:
		billTypeText = "报账";
	  break;
	case 2:
		billTypeText = "核销";
		break;
	case 3:
		billTypeText = "计提";
		break;
	case 4:
		billTypeText = "冲销";
		break;
	default:
		billTypeText = "";
	}
	
	$("#eleBillaccountPaymentdetailForm #billType").text(billTypeText );
	$("#eleBillaccountPaymentdetailForm #billType").val(billaccountPaymentInfo.billType);
	$("#eleBillaccountPaymentdetailForm #invoiceType").text(billaccountPaymentInfo.invoiceType  == 0 ? "发票" : "收据");
	switch(billaccountPaymentInfo.taxPaymentType)
	{
	case 0:
		taxPayType = "移动开票认税";
	  break;
	case 1:
		taxPayType = "对方开票认税";
	  break;
	case 2:
		taxPayType = "移动开票对方认税";
		break;
	default:
		taxPayType = "";
	}
	$("#eleBillaccountPaymentdetailForm #taxPaymentType").text(taxPayType);
	$("#eleBillaccountPaymentdetailForm #isVatDeduction").text(billaccountPaymentInfo.isVatDeduction == 1 ? "不可抵扣":"可抵扣");
	$("#eleBillaccountPaymentdetailForm #businessType").text(billaccountPaymentInfo.businessType == 0 ? "扩展字段" :"网络类型租赁");
	switch(billaccountPaymentInfo.amountType)
	{
	case 0:
		amountType = "机房租赁";
	  break;
	case 1:
		amountType = "基站租赁";
	  break;
	case 2:
		amountType = "其它网元租赁";
		break;
	case 3:
		amountType = "扩展字段";
		break;
	default:
		amountType = "";
	}
	$("#eleBillaccountPaymentdetailForm #amountType").text(amountType);
	$("#eleBillaccountPaymentdetailForm #billamountType").text(billaccountPaymentInfo.billamountType == 0 ? "租金":"扩展字段");
	$("#eleBillaccountPaymentdetailForm #billamountNotaxSys").text(filterNullValue(billaccountPaymentInfo.billamountNotaxSys)+" 元");
	$("#eleBillaccountPaymentdetailForm #billamountNotax").text(filterNullValue(billaccountPaymentInfo.billamountNotax)+" 元");
	$("#eleBillaccountPaymentdetailForm #lossAmountSys").text(filterNullValue(billaccountPaymentInfo.lossAmountSys)+" 元" );
	$("#eleBillaccountPaymentdetailForm #billamountTaxamountSys").text(filterNullValue( billaccountPaymentInfo.billamountTaxamountSys)+" 元");
	$("#eleBillaccountPaymentdetailForm #billamountTaxamount").text(filterNullValue(billaccountPaymentInfo.billamountTaxamount)+" 元");
	$("#eleBillaccountPaymentdetailForm #lossAmount").text(filterNullValue(billaccountPaymentInfo.lossAmount)+" 元");
	$("#eleBillaccountPaymentdetailForm #otherAmount").text(filterNullValue(billaccountPaymentInfo.otherAmount)+" 元");
	$("#eleBillaccountPaymentdetailForm #billAmountSys").text(filterNullValue( billaccountPaymentInfo.billAmountSys)+" 元");
	$("#eleBillaccountPaymentdetailForm #billAmountActual").text(filterNullValue(billaccountPaymentInfo.billAmountActual)+" 元");
	$("#eleBillaccountPaymentdetailForm #totalDegree").text(filterNullValue(billaccountPaymentInfo.totalDegree)+" 度");
	$("#eleBillaccountPaymentdetailForm #calcMulti").text(filterNullValue(billaccountPaymentInfo.calcMulti));
	$("#eleBillaccountPaymentdetailForm #totalDegreeActual").text(filterNullValue(billaccountPaymentInfo.totalDegreeActual ));
	$("#eleBillaccountPaymentdetailForm #paymentdetailNote").text(filterNullValue(billaccountPaymentInfo.paymentdetailNote));
	
	 $.ajax({
		  type : "POST",
		  url : 'queryUpdateElectricmeterInfo',
		  data:{"billaccountId": billaccountPaymentInfo.billaccountId , "billaccountpaymentdetailId":billaccountPaymentInfo.billaccountpaymentdetailId},
		  dataType : 'json',
		  cache : false,
		  success : function(data) {
			  debugger;
			  if(data.Obj.contract!=null){
				//合同信息
				  var contract = data.Obj.contract;
					$("#contractCode").text(contract.contractCode);
					$("#contractName").text(contract.contractName);
					$("#isIncludeAll").text(contract.isIncludeAll == 0 ? "否" : "是");
					
					$("#paySignAccount").val(contract.paySignAccount);
					$("#paySign").val(contract.paySign);
					
					$("#taxRate").text(contract.taxRate);
					

					switch(contract.supplyMethod)
					{
					case 1:
						charegMethod = "直供电";
					  break;
					case 2:
						charegMethod = "转供电";
					  break;
					case 3:
						charegMethod = "市电";
						break;
					case 4:
						charegMethod = "太阳能";
						break;
					default:
						charegMethod = "";
					}
					$("#supplyMethod").text(charegMethod);
					$("#includeLoss").text(contract.includeLoss == 0 ? "否" : "是");
					$("#lossType").text(contract.lossType == 0 ? "按度数" : "按金额");
					$("#contractTotalAmount").text(filterNullValue(contract.contractTotalAmount));
					
					$("#independentMeter").text(contract.independentMeter == 0 ? "否":"是");
					$("#cmccRatio").text(filterNullValue(contract.cmccRatio));
					
					$("#includePriceTax").text(contract.includePriceTax == 0 ? "否":"是");
					$("#supplierCode").text(filterNullValue(contract.supplierCode));
					$("#supplierName").text(filterNullValue(contract.supplierName));
					$("#supplierAddress").text(filterNullValue(contract.supplierAddress));
					selectFileList(billaccountPaymentInfo);//查询附件列表
			  }else{
				  alert("没有关联合同");
				  return;
			  }
			  
				$("#myTab").html(data.Obj.li);
				$("#myTabContent").html(data.Obj.tabcontent);
				addValidateOnFiled();
				//处理分摊后度数和税金
				if (objNotNull($("#cmccRatioAfter")) && objNotNull($("#nowDegree")) && objNotNull($("#cmccRatio"))){
					$("#myTabContent").find("form").each(function(){
						var nowDegree = $(this).find("#nowDegree").val();
						var cmccRatio = $(this).find("#cmccRatio").val();
						if (nowDegree != null && nowDegree != '' && cmccRatio != null && cmccRatio != ''){
							var useDegrees = nowDegree*cmccRatio/100;
							$(this).find("#cmccRatioAfter").val(useDegrees);
						}
						/*if ($(this).find("#tax").val() == null || $(this).find("#tax").val() == ''){
							$(this).find("#tax").val($("#billamountTaxamountSys").text());
						}*/
						var payamountTax = $(this).find("#tax").val();
						if(payamountTax != null && payamountTax != ''){
							$(this).find("#tax").val(payamountTax);
						}
					});
				}	
				  //报账点电费明细维护不需要进行校验
				  //calcBenchmark();
				showBenchmark();
		  }
	 });
	 
	 

	 /**
	  * 显示标杆信息
	  */
	 function showBenchmark(){
	 	var billaccountId = $("#eleBillaccountPaymentdetailForm #billaccountId").val();;
	 	var billamountStartdate = $("#eleBillaccountPaymentdetailForm #billamountStartdate").text();
	 	var billamountEnddate = $("#eleBillaccountPaymentdetailForm #billamountEnddate").text();
	 	var totalDegreeActual = $("#eleBillaccountPaymentdetailForm #totalDegreeActual").text();
	 	$.post("getBenchmark",{"billaccountId":billaccountId , "billamountStartdate":billamountStartdate,"billamountEnddate":billamountEnddate,"totalDegreeActual":totalDegreeActual},function(data){
	 		if(data.success == 0){
	 			alert(data.msg);
	 		}else{
	 			$("#tb").html(data.Obj);
	 		}
	 		
	 	});
	 }
	 
	 
	 function addValidateOnFiled(){
		  //修改和查看走的同一个接口，在查看这里需要将不能编辑的组件置为不可用
		  var propertyLst = ["#payamountNotax","#flatPrice","#nowFlatReadnum","#nowPeakReadnum","#lastTopReadnum","#nowTopReadnum","#nowTopReadnum",
		                     "#lastReadnum","#nowReadnum","#meterRate","input[name='useDegrees']",
		                     "#nowValleyReadnum","#valleyPrice","#lastValleyReadnum","#tax","#meterLoss","input[name='lossDegrees']"];
		  for(var i in propertyLst){
			  if(objNotNull($(propertyLst[i]))){
				  $(propertyLst[i]).attr("readonly",true);
			  }
		  }
		  $("input[type='text']").attr("readonly",true);
	 }
		//加载流程
		histoicFlowList(EleBillaccountPaymentdetail.tableName,billaccountPaymentInfo.billaccountpaymentdetailId);
		
}

function filterNullValue(obj){
	if (obj == null||obj == "null"){
		return "";
	}
	return obj;
}

function objNotNull(obj){
	 return obj.length > 0;
}

function back(){
	
	javascript:history.back(-1);
}
function selectFileList(billaccountPaymentInfo){
	debugger;
	var billaccountPaymentInfo = localStorage.getItem("billaccountPaymentInfo");
	billaccountPaymentInfo = JSON.parse(billaccountPaymentInfo);
	$.ajax({
		url : 'selectByBusiness',// 跳转到 action
		data : {
			businessId:billaccountPaymentInfo.billaccountId,
			businessType:"ele_payment"
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		beforeSend: function(){
			console.log("正在进行，请稍候");
		},
		success : function(back) {
			if(back.success == 1){
				if(back.obj!=null){
					$.each(back.obj.list,function(i,v){
						var file = "<li class='list-group-item row'>" +
						"<div class='col-md-2'></div>" +
						"<div class='col-md-4'><a href='downLoad?path="+v.attachmentUrl+"' >"+v.attachmentName+"</a></div>" +
						"</li>";
						$("#attachFile").append(file);
					});
				}
			}
		},
		error : function(back){
			alertModel(back.msg);
		}
	});
	
}