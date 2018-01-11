
//初始化
$(function(){
	//初始化
	init();
	checkInfo();
	queryCurUser();
});

/*function init(){
	var billaccountPaymentInfo = localStorage.getItem("billaccountPaymentInfo");
	billaccountPaymentInfo = JSON.parse(billaccountPaymentInfo);
	
	$("#billaccountCode").text(billaccountPaymentInfo.billaccountCode);
	$("#billaccountName").text(billaccountPaymentInfo.billaccountName);
	
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
	$("#eleBillaccountPaymentdetailForm #businessType").val(billaccountPaymentInfo.businessType);
	$("#eleBillaccountPaymentdetailForm #amountType").val(billaccountPaymentInfo.amountType);
	$("#eleBillaccountPaymentdetailForm #billamountType").val(billaccountPaymentInfo.billamountType);
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
	$("#eleBillaccountPaymentdetailForm input").attr('readonly','readonly');
	$("#eleBillaccountPaymentdetailForm select").attr('disabled','disabled');
	 $.ajax({
		  type : "POST",
		  url : 'queryUpdateElectricmeterInfo',
		  data:{"billaccountId": billaccountPaymentInfo.billaccountId , "billaccountpaymentdetailId":billaccountPaymentInfo.billaccountpaymentdetailId},
		  dataType : 'json',
		  cache : false,
		  success : function(data) {
			  
			  //合同信息
			  var contract = data.Obj.contract;
				$("#contractCode").text(contract.contractCode);
				$("#contractName").text(contract.contractName);
				$("#isIncludeAll").val(contract.isIncludeAll);
				
				$("#taxRate").text(contract.taxRate);
				$("#supplyMethod").val(contract.supplyMethod);
				$("#includeLoss").val(contract.includeLoss);
				$("#lossType").val(contract.lossType);
				$("#contractTotalAmount").text(contract.contractTotalAmount);
				
				$("#independentMeter").val(contract.independentMeter);
				$("#contractTotalAmount").text(contract.contractTotalAmount);
				$("#cmccRatio").text(contract.cmccRatio);
				
				$("#includeTax").val(contract.includeTax);
				$("#supplierCode").text(contract.supplierCode);
				$("#supplierName").text(contract.supplierName);
				$("#supplierAddress").text(contract.supplierAddress);
				
				$("#myTab").html(data.Obj.li);
				  $("#myTabContent").html(data.Obj.tabcontent);
				
		  }
	 });
}*/

function init(){
	var billaccountPaymentInfo = localStorage.getItem("billaccountPaymentInfo");
	
	billaccountPaymentInfo = JSON.parse(billaccountPaymentInfo);
	
	
	$("#billaccountCode").text(billaccountPaymentInfo.billaccountCode);
	$("#billaccountName").text(billaccountPaymentInfo.billaccountName);

	$("#eleBillaccountPaymentdetailForm #billaccountId").val(billaccountId);
	
	$("#eleBillaccountPaymentdetailForm #billaccountpaymentdetailId").val(billaccountPaymentInfo.billaccountpaymentdetailId);
	$("#eleBillaccountPaymentdetailForm #billamountStartdate").text(billaccountPaymentInfo.billamountStartdate);
	$("#eleBillaccountPaymentdetailForm #billamountEnddate").text(billaccountPaymentInfo.billamountEnddate);
	$("#eleBillaccountPaymentdetailForm #billamountDate").text(billaccountPaymentInfo.billamountDate);
	$("#eleBillaccountPaymentdetailForm #buyMethod").text(billaccountPaymentInfo.buyMethod == 0 ? "预付费":"后付费");
	$("#eleBillaccountPaymentdetailForm #paymentMethod").text(billaccountPaymentInfo.paymentMethod == 0 ? "转账" : "托收");
	$("#eleBillaccountPaymentdetailForm #paymentMethod").val(billaccountPaymentInfo.paymentMethod);
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
	$("#eleBillaccountPaymentdetailForm #isVatDeduction").text(billaccountPaymentInfo.isVatDeduction ==0 ? "不可抵扣":"可抵扣");
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
	$("#eleBillaccountPaymentdetailForm #billamountNotaxSys").text(billaccountPaymentInfo.billamountNotaxSys+" 元");
	$("#eleBillaccountPaymentdetailForm #billamountNotax").text(billaccountPaymentInfo.billamountNotax+" 元");
	$("#eleBillaccountPaymentdetailForm #lossAmountSys").text(billaccountPaymentInfo.lossAmountSys+" 元" );
	$("#eleBillaccountPaymentdetailForm #billamountTaxamountSys").text( billaccountPaymentInfo.billamountTaxamountSys+" 元");
	$("#eleBillaccountPaymentdetailForm #billamountTaxamount").text(billaccountPaymentInfo.billamountTaxamount+" 元");
	$("#eleBillaccountPaymentdetailForm #lossAmount").text(billaccountPaymentInfo.lossAmount+" 元");
	$("#eleBillaccountPaymentdetailForm #otherAmount").text(billaccountPaymentInfo.otherAmount+" 元");
	$("#eleBillaccountPaymentdetailForm #billAmountSys").text(billaccountPaymentInfo.billAmountSys+" 元");
	$("#eleBillaccountPaymentdetailForm #billAmountActual").text(billaccountPaymentInfo.billAmountActual+" 元");
	$("#eleBillaccountPaymentdetailForm #totalDegree").text(billaccountPaymentInfo.totalDegree+" 度");
	$("#eleBillaccountPaymentdetailForm #calcMulti").text(billaccountPaymentInfo.calcMulti);
	$("#eleBillaccountPaymentdetailForm #totalDegreeActual").text(billaccountPaymentInfo.totalDegreeActual +" 度");
	$("#eleBillaccountPaymentdetailForm #paymentdetailNote").text(billaccountPaymentInfo.paymentdetailNote);
	
	 $.ajax({
		  type : "POST",
		  url : 'queryUpdateElectricmeterInfo',
		  data:{"billaccountId": billaccountPaymentInfo.billaccountId , "billaccountpaymentdetailId":billaccountPaymentInfo.billaccountpaymentdetailId},
		  dataType : 'json',
		  cache : false,
		  success : function(data) {
			  
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
					$("#contractTotalAmount").text(contract.contractTotalAmount==null?0:contract.contractTotalAmount);
					
					$("#independentMeter").text(contract.independentMeter == 0 ? "否":"是");
					$("#cmccRatio").text(contract.cmccRatio);
					
					$("#includePriceTax").text(contract.includePriceTax == 0 ? "否":"是");
					$("#supplierCode").text(contract.supplierCode);
					$("#supplierName").text(contract.supplierName);
					$("#supplierAddress").text(contract.supplierAddress);
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
						if ($(this).find("#tax").val() == null || $(this).find("#tax").val() == ''){
							$(this).find("#tax").val($("#billamountTaxamountSys").text());
						}
					});
				}	
				  //报账点电费明细维护不需要进行校验
				  //calcBenchmark();
		  }
	 });
	 
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
}
function objNotNull(obj){
	 return obj.length > 0;
}
/*function save(){
	$.post("submitBillaccountDetail" , {"billaccountpaymentdetailId": $("#billaccountpaymentdetailId").val() , "auditingState": $("#checkState").val()} ,function(data){
		window.location.href="auditing.html";
	});
	
}*/


function back(){
	localStorage.removeItem("billaccountPaymentInfo");
	javascript:history.back(-1);
}

function changeSave(){
	if($("#nextUsers option").length > 1){
		if($('#nextUsers option:selected').val() == ""){
			$("#nextUsers").next("#err").html("<img src=\"../../../image/error.png\"/>必选！");
			$("#nextUsers").next("#err").css({"color":"red","float":"left"});
			flag=true;
		}else{
			$("#nextUsers").next("#err").html("<img src=\"../../../image/right_1.png\"/>验证成功");
			$("#nextUsers").next("#err").css({"color":"green","float":"left"});
			flag=false;
		}
	}else{
		flag=false;
	}
	if($("#checkState option:selected").val()==8){
		if($("#comment").val()==""){
			$("#comment").next("#err").html("<img src=\"../../../image/error.png\"/>必填！");
			$("#comment").next("#err").css({"color":"red"});
			flag=true;
		}else{
			$("#comment").next("#err").html("<img src=\"../../../image/right_1.png\"/>验证成功");
			$("#comment").next("#err").css({"color":"green"});
			flag=false;
		}		
	}
}

function save(){
	changeSave();
	if(flag){
		return false;
	}
	var billaccountpaymentdetailId=getParameter("billaccountpaymentdetailId");
	var state=$("#checkState option:selected").val();
	var	comment=$("#comment").val();
	var taskId=getParameter("taskId");
	var	leader=$("#nextUsers option:selected").val();	
	var billAccountIds = new Array();
	// 从选中行中挑出可以启用的item
	var obj={
			"state":state,
			"comment":comment,
			"leader":leader,
			"billaccountpaymentdetailId":billaccountpaymentdetailId,
			"taskId":taskId,
    };
	billAccountIds.push(obj);
	
	 $.ajax({
		  type : "POST",
		  url : 'reviewBillAccountAudit',
		  data:JSON.stringify(billAccountIds),
		  dataType : 'json',
		  contentType : 'application/json;charset=utf-8',
		  cache : false,
		  success : function(data) {
			  window.location="auditing.html";
		  }
	 });
}

function checkInfo(){
	var billaccountPaymentInfo = localStorage.getItem("billaccountPaymentInfo");
	var billaccountpaymentdetailId=getParameter("billaccountpaymentdetailId");
	var regId=getParameter("regId");
//	var billaccountpaymentdetailId=billaccountPaymentInfo.billaccountpaymentdetailId;
	console.log("billaccountpaymentdetailId====="+billaccountpaymentdetailId);
	histoicFlowList(EleBillaccountPaymentdetail.tableName,billaccountpaymentdetailId);
	findUsersByRoleIds(EleBillaccountPaymentdetail.tableName,billaccountpaymentdetailId,regId);
}
function queryCurUser(){
	$.ajax({
		url:'queryCurUser',
		data:{
			conutn:1
		},
		type:'get',
		dataType:'json',
		success:function(back){
			if (back != null) {
				item=back.obj;
				if(back.success=="1"){
					$("#curUser").text(item[0]);
				}
			}
		},
		error:function(){
			alert("请求异常");
		}
	})
}