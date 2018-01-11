var calcflag = false;

//初始化
$(function(){
	//初始化
	init();
});

function init(){
	var billaccountId = localStorage.getItem("billaccountId");
	var billaccountCode = localStorage.getItem("billaccountCode");
	var billaccountName = localStorage.getItem("billaccountName");
	
	 //billaccountId = "09287f0bb378429f9b7853ae4448e8f7";
	 //var regId = "310105";
	
	$("#eleBillaccountPaymentdetailForm #billaccountId").val(billaccountId);
	 $.ajax({
		  type : "POST",
		  url : 'queryElectricmeterInfo',
		  data:{"billaccountId": billaccountId },
		  dataType : 'json',
		  cache : false,
		  success : function(data) {
			if(data.Obj != null){
				$("#billaccountCode").text(billaccountCode);
				$("#billaccountName").text(billaccountName);
				if(data.Obj.contract != null){
					var contract = data.Obj.contract;
					$("#contractCode").text(filterNullValue(contract.contractCode));
					$("#contractName").text(filterNullValue(contract.contractName));
					$("#isIncludeAll").val(contract.isIncludeAll);
					$("#paySignAccount").val(contract.paySignAccount);
					$("#paySign").val(contract.paySign);
					
					$("#taxRate").text(filterNullValue(contract.taxRate));
					$("#supplyMethod").val(contract.supplyMethod);
					$("#includeLoss").val(contract.includeLoss);
					if(contract.includeLoss != null && contract.includeLoss == 0){
						$("#lossAmountSys").attr("disabled","disabled");
						$("#lossAmount").attr("disabled","disabled");
					}
					$("#lossType").val(contract.lossType);
					$("#contractTotalAmount").text(filterNullValue(contract.contractTotalAmount));
					
					$("#independentMeter").val(contract.independentMeter);
//					$("#contractTotalAmount").text(contract.contractTotalAmount);
					if(contract.cmccRatio==null){
						$("#cmccRatio").text("");
					}else{
						
						$("#cmccRatio").text(parseFloat(filterNullValue(contract.cmccRatio)));
					}
					
					$("#includePriceTax").val(contract.includePriceTax);
					$("#supplierCode").text(filterNullValue(contract.supplierCode));
					$("#supplierName").text(filterNullValue(contract.supplierName));
					$("#supplierAddress").text(filterNullValue(contract.supplierAddress));
					if(contract.buyMethod!=null){
						$("#buyMethod").val(contract.buyMethod);
					}
					if(contract.paymentMethod!=null){
						$("#paymentMethod").val(contract.paymentMethod);
					}
				}
			  }else{
				  alert("没有关联合同");
				  return;
			  }
				//补充报账点信息
				var billobj = data.Obj.billaccount;
				$("#calcMulti").val(billobj.calcMulti);
				
				$("#myTab").html(data.Obj.li);
				$("#myTabContent").html(data.Obj.tabcontent);

				console.log(data.Obj.contract.contractStartdate)
				console.log(data.Obj.contract.contractEnddate)
				$("#billamountStartdate").blur(function(){
					var billamountStartdate = $("#billamountStartdate").val();
					if(billamountStartdate < data.Obj.contract.contractStartdate){
						alert("缴费期始不能小于固化信息期始！");
						$("#billamountStartdate").val("");
						return false;
					}
				});
				$("#billamountEnddate").blur(function(){
					var billamountEnddate = $("#billamountEnddate").val();
					if(billamountEnddate > data.Obj.contract.contractEnddate){
						alert("缴费期终不能大于固化信息期终！");
						$("#billamountEnddate").val("");
						return false;
					}
				});
		  }
	 });
}



function save(submitState){
	if(calcAmount()){	
	}else{
		alert("计算校验失败，");
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
	
//	$("#eleBillaccountPaymentdetailForm").find("input").each(function(){
//		var val = $(this).val();
//		if(val==null || val == ""){
//			$(this).val("1");
//		}
//	});
//	
//	$("#myTabContent").find("input").each(function(){
//		var val = $(this).val();
//		if(val==null || val == ""){
//			$(this).val("1");
//		}
//	});
	//组件disabled为true的时候无法序列化
	$("input[type='text']").attr("disabled",false);
	var billaccountdata = $("#eleBillaccountPaymentdetailForm").serializeObject();
	
	billaccountdata.submitState=submitState;
	
	var billaccountsubmitData = JSON.stringify(billaccountdata);
	
	$.post("saveOrUpdateBillaccountDetail" , billaccountdata ,function(data){
		if(data.success == 1){
			var isIncludeAll = $("#isIncludeAll").val();
			
			if(isIncludeAll==1){
				//包干  没有电表
				localStorage.clear();
				window.location.href = "record.html";
			}else{
				$("form #billaccountpaymentdetailId").val(data.obj.billaccountpaymentdetailId);
				
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
					localStorage.clear();
					window.location.href = "record.html";
				});
			}
		}else{
			alert(data.msg);
			window.location.href = "record.html";
		}
	
	});
}

function filterNullValue(obj){
	if (obj == null){
		return "";
	}
	return obj;
}

function back(){
	localStorage.clear();
	javascript:history.back(-1);
}