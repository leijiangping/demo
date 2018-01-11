/**
 * 计算包干
 * @returns {Boolean}
 */
function calcAmountIncludeAll(){
	
	/**
	 * 准备合同数据
	 */
	//包干总金额
	var contractTotalAmount = $("#contractTotalAmount").text();
	//支付单价
	var paySignAccount = $("#paySignAccount").val();
	//电费税率
	//电费单价是否含税
	//根据单价是否含税是否含税，判断是正算还是反算
	var includePriceTax = $("#includePriceTax").val();
	if(includePriceTax==null || includePriceTax==""){
		alert("单价是否含税为空，无法计算");
		return false;
		includePriceTax=0;
	}
	var taxRate=$("#taxRate").text();
	if(taxRate==null || taxRate==""){
		alert("税率为空，无法计算");
		return false;
		taxRate=0;
	}else{
		taxRate = taxRate*1.00/100;
	}
	
	/**
	 * 开始计算
	 * 按照支付单价是年、月、日分为三种场景，其中根据单价是否含税，每种场景又细分为两种
	 */
	//支付单价周期    1：日  2：月  3: 年
	var paySign = $("#paySign").val();
	if(paySign==null || paySign==""){
		alert("支付单价周期为空！！");
		return false;
	}
	
	var billamountStartdate = $("#eleBillaccountPaymentdetailForm #billamountStartdate").val();
	if(billamountStartdate == null || billamountStartdate==""){
		alert("请先填写缴费期始");
		return false;
	}
	var billamountEnddate = $("#eleBillaccountPaymentdetailForm #billamountEnddate").val();
	if(billamountEnddate == null || billamountEnddate==""){
		alert("请先填写缴费期终");
		return false;
	}
	
	if(compareDate(billamountStartdate , billamountEnddate)<0){
		alert("缴费期始不能大于缴费期终");
		return false;
	}
	
	var payamountNotax = 0;
	var payamountTax = 0;
	var tax = 0;
	if(paySign == 1){
		/**
		 * 日
		 * (不含税计算公式)系统计算不含税金额=日支付单价*（缴费期终-缴费期始）；
		 * (不含税计算公式)系统计算税金=系统计算不含税金额*电费税率；
		 * 
		 * (含税计算公式)含税金额=日支付单价*（缴费期终-缴费期始）；
		 * (含税计算公式)不含税金额=含税金额/（1+电费税率）；
		 * (含税计算公式)税金=含税金额-不含税金额
		 * 
		 */
		var days = getSubDays(billamountStartdate , billamountEnddate);
		if(includePriceTax==1){
			//含税
			payamountTax = paySignAccount*days;//(不含税计算公式)系统计算不含税金额
			payamountNotax = payamountTax/(1+taxRate);//含税计算公式)不含税金额
			tax = payamountTax-payamountNotax;
		}else{
			payamountNotax = paySignAccount*days;
			tax = payamountNotax*taxRate;
		}
		
	}else if(paySign == 2){
		/**
		 * 月
		 * 系统计算（缴费期终-缴费期始）/30.42，如果结果的十分位为1到8（包含1、8），则备注为必填项。（需要用户填写说明，为什么缴费周期不是整月）
		 * F{（缴费期终-缴费期始）/30.42 }
		 * F{*}是 对*值进行4舍5入，归为整数
		 * 
		 * (不含税计算公式)系统计算不含税金额=月支付单价*    F{（缴费期终-缴费期始）/30.42 }；
		 * (不含税计算公式)系统计算税金=系统计算不含税金额*电费税率；
		 * 
		 * (含税计算公式)含税金额=月支付单价*    F{（缴费期终-缴费期始）/30.42 }；
		 * (含税计算公式)不含税金额=含税金额/（1+电费税率）；
		 * (含税计算公式)税金=含税金额-不含税金额；
		 * 
		 */
		var days = getSubDays(billamountStartdate , billamountEnddate);
		var xishu = Math.round(days/30.42);
		if(includePriceTax==1){
			//含税
			payamountTax = paySignAccount*xishu;
			payamountNotax = payamountTax/(1+taxRate);
			tax = payamountTax-payamountNotax;
		}else{
			payamountNotax = paySignAccount*xishu;
			tax = payamountNotax*taxRate;
		}
	}else if(paySign == 3){
		/**
		 * 年
		 * 系统计算（缴费期终-缴费期始）/365，如果结果的十分位为1到8（包含1、8），则备注为必填项。（需要用户填写说明，为什么缴费周期不是整年）
		 * F{（缴费期终-缴费期始）/30.42 }
		 * F{*}是 对*值进行4舍5入，归为整数9
		 * 
		 * (不含税计算公式)系统计算不含税金额=年支付单价*    F{（缴费期终-缴费期始）/365}；
		 * (不含税计算公式)系统计算税金=系统计算不含税金额*电费税率；
		 * 
		 * (含税计算公式)含税金额=年支付单价*    F{（缴费期终-缴费期始）/365}；
		 * (含税计算公式)不含税金额=含税金额/（1+电费税率）；
		 * (含税计算公式)税金=含税金额-不含税金额；
		 * 
		 */
		var days = getSubDays(billamountStartdate , billamountEnddate);
		var xishu = Math.round(days/365);
		if(includePriceTax==1){
			//含税
			payamountTax = paySignAccount*xishu;
			payamountNotax = payamountTax/(1+taxRate);
			tax = payamountTax-payamountNotax;
		}else{
			payamountNotax = paySignAccount*xishu;
			tax = payamountNotax*taxRate;
		}
	}
	
	var billamountNotaxSys = payamountNotax;
	var lossAmountSys = 0;
	var billamountTaxamountSys = tax;
	var otherfee = 0;
	
	$("#eleBillaccountPaymentdetailForm #billamountNotaxSys").val( billamountNotaxSys.toFixed(6) + "");
	$("#eleBillaccountPaymentdetailForm #lossAmountSys").val(lossAmountSys + "");
	$("#eleBillaccountPaymentdetailForm #billamountTaxamountSys").val(billamountTaxamountSys.toFixed(6)+ "");
	var result = parseFloat(billamountNotaxSys,6) + parseFloat(billamountTaxamountSys,6) + parseFloat(lossAmountSys,6) + parseFloat(otherfee,6);
	$("#eleBillaccountPaymentdetailForm #billAmountSys").val(result+"");
	//实际不含税金额
	if($("#billamountNotax") == '' || $("#billamountNotax") == null){
		var billamountNotax = billamountNotaxSys  + "";
		$("#eleBillaccountPaymentdetailForm #billamountNotax").val(Number(billamountNotax).toFixed(6));
	}
	if($("#billamountTaxamount") == '' || $("#billamountTaxamount") == null){
		//实际税金
		var billamountTaxamount = billamountTaxamountSys + "";
		$("#eleBillaccountPaymentdetailForm #billamountTaxamount").val(Number(billamountTaxamount).toFixed(6));
	}
	if($("#lossAmount") == '' || $("#lossAmount") == null){
		//实际电损金额
		var lossAmount = lossAmountSys + "";
		$("#eleBillaccountPaymentdetailForm #lossAmount").val(lossAmount);
	}
	if($("#billAmountActual") == '' || $("#billAmountActual") == null){
		//实际报账金额
		var billAmountActual = $("#eleBillaccountPaymentdetailForm #billAmountSys").val();
		$("#billAmountActual").val(billAmountActual);
	}
	$("#eleBillaccountPaymentdetailForm #totalDegree").val(0);
	$("#eleBillaccountPaymentdetailForm #totalDegreeActual").val(0);
	
	return true;
}

/**
 * 计算非包干
 * @returns {Boolean}
 */
function calcAmountNotIncludeAll(){
	/**
	 * 以下为非包干合同计算方式
	 * 首先 计算电表相关数据
	 */
	var calcFlag = true;
	$("#myTabContent").find("form").each(function(){
		//电表上限值
		if($(this).find("#meterType").val() == "普通表"){
			var upperValue=$(this).find("#upperValue").val();
			var priceArr = $(this).find("input[name='elecPrice']");
			
			//计算本期度数  （本期读数-上期读数）*电表倍率
			var nowDegree = 0;
			var lastReadnum = $(this).find("#lastReadnum").val();
			if(lastReadnum==null || lastReadnum==""){
				lastReadnum=0;
			}
			if(parseFloat(lastReadnum)>parseFloat(upperValue)){
				alertModel("上期读数不能超过电表上限值,上限值为："+parseFloat(upperValue)+"");
				calcFlag = false;
				return false;
			}
			var nowReadnum = $(this).find("#nowReadnum").val();
			if(parseFloat(nowReadnum)>parseFloat(upperValue)){
				alertModel("本期读数不能超过电表上限值,上限值为："+parseFloat(upperValue)+"");
				calcFlag = false;
				return false;
			}
			if(nowReadnum==null || nowReadnum==""){
				alert("请填写本期读数！！");
				calcFlag = false;
				$(this).find("#nowReadnum").focus();
				return false;
			}
			var meterRate = $(this).find("#meterRate").val();
			if(meterRate==null || meterRate==""){
				meterRate=0;
			}
			if(parseFloat(nowReadnum)>=parseFloat(lastReadnum)){
				nowDegree = (parseFloat(nowReadnum)-parseFloat(lastReadnum))*meterRate;
			}else{
				if(upperValue==null || upperValue=="null"){
					calcFlag = false;
					alert("该电表无上限值，上期读数应小于本期读数！！");
					return false;
				}
				nowDegree = (parseFloat(upperValue)-parseFloat(lastReadnum)+parseFloat(nowReadnum))*meterRate;
			}
			$(this).find("#nowDegree").val(nowDegree.toFixed(6));
//			$(this).find("#nowDegree").val(showCalc(nowDegree+""));
			
			//分摊后度数=本期度数*移动分摊比例*报账点计量倍数；
			var calcMulti = 1;
			var calcMultiVal = $("#eleBillaccountPaymentdetailForm #calcMulti").val();
			if(calcMultiVal!=null && calcMultiVal!=""){
				calcMulti = parseFloat(calcMultiVal,10);
			}else{
				alert("报账点计量倍数为空，无法计算");
				calcFlag = false;
				return false;
			}
			var cmccRatio = $(this).find("#cmccRatio").val();
			if(cmccRatio==null || cmccRatio==""){
				cmccRatio=0;
			}
			var cmccRatioAfter=nowDegree*cmccRatio*calcMulti/100;
			$(this).find("#cmccRatioAfter").val(cmccRatioAfter.toFixed(6));
			
			//列出合同中所有电费单价，将分摊后的度数填写到各个单价对应的电量中，保证分摊后的度数等于各个单价对应的电量之和（系统要做判断）。
			var useDegrees = 0;
			var tempPayamount = 0;
			debugger;
			if($(this).find("input[name='useDegrees']").length == 1){
				useDegrees=cmccRatioAfter;
				$("input[name='useDegrees']").val(useDegrees);

				if($(priceArr[0]).val()!=null && $(priceArr[0]).val()!=""){
					var floatval = parseFloat($(priceArr[0]).val(),10);
					tempPayamount += useDegrees*floatval;
				}
			}else{
				
				$(this).find("input[name='useDegrees']").each(function(index,element){
					if($(this).val()!=null && $(this).val()!=""){
						var intval = parseFloat($(this).val(),10);
						useDegrees += intval;
						
						if($(priceArr[index]).val()!=null && $(priceArr[index]).val()!=""){
							var floatval = parseFloat($(priceArr[index]).val(),10);
							tempPayamount += intval*floatval;
						}
					}else{
						alert("用电总量不能为空，请输入！！");
						calcFlag = false;
						return false;
					}
				});
			}
			
			if(useDegrees.toFixed(6)!=cmccRatioAfter.toFixed(6)){
				alert("分摊后的度数和用电总量不相等，请重新输入！！");
				calcFlag = false;
				return false;
			}
			/*if($("input[name='useDegrees']").val()!=$("#cmccRatioAfter").val()){
				alert("分摊后的度数和用电总量不相等，请重新输入！！");
				calcFlag = false;
				return false;
			}*/
			//根据单价是否含税是否含税，判断是正算还是反算
			var includePriceTax = $("#includePriceTax").val();
			if(includePriceTax==null || includePriceTax==""){
				alert("单价是否含税为空，无法计算");
				calcFlag = false;
				return false;
				includePriceTax=0;
			}
			var taxRate=$("#taxRate").text();
			if(taxRate==null || taxRate==""){
				alert("税率为空，无法计算");
				calcFlag = false;
				return false;
				taxRate=0;
			}else{
				taxRate = taxRate*1.00/100;
			}
			var payamountNotax = 0;
			var payamountTax = 0;
			var tax = 0;
			if(includePriceTax==1){
				//含税    含税金额=∑_(i=1)^n▒〖电费电价i×用电量i〗；（n为单价个数之和）；
				//不含税金额=含税金额/（1+电费税率）；
				//税金=含税金额-不含税金额；
				payamountTax = tempPayamount;
				payamountNotax = (payamountTax/(1+taxRate)).toFixed(6);
				tax = (payamountTax-payamountNotax).toFixed(6);
			}else{
				//不含税  不含税金额=∑_(i=1)^n▒〖电费电价i×用电量i〗；（n为单价个数之和）
				//税金=不含税金额*电费税率
				payamountNotax = tempPayamount;
				tax = (payamountNotax*taxRate).toFixed(6);
			}
			$(this).find("#tax").val(tax);
			$(this).find("#payamountNotax").val(payamountNotax);
			
			// 是否有电损(0:否 1:是)
			var includeLoss = $("#includeLoss").val();
			if(includeLoss == 1){// 计算电损
				var lossType = $("#lossType").val();
				if(lossType==null || lossType==""){
					alert("电损计算方式为空，无法计算");
					calcFlag = false;
					return false;
				}
				lossType = parseInt(lossType);
				if(lossType==0){
					//计算电损金额 (可以修改，可以计算)(lossType 0:度数   1:金额)
					
					var tempmeterLoss = 0;
					$(this).find("input[name='lossDegrees']").each(function(index,element){
						if($(this).val()!=null && $(this).val()!=""){
							if($(priceArr[index]).val()!=null && $(priceArr[index]).val()!=""){
								tempmeterLoss += parseInt($(this).val(),10)*parseFloat($(priceArr[index]).val(),10);
							}
						}else{
							alert("当前电损计算方式为按度数，请填写电损度数");
							calcFlag = false;
							$(this).focus();
							return false;
						}
						
					});
					var meterLoss = 0;
					if(includePriceTax==1){
						//含税
						//电损金额=∑_(i=1)^n▒〖电费电价i×电损电量i〗；（n为单价个数之和）；
						meterLoss=tempmeterLoss.toFixed(6);
					}else{
						//不含税
						//电损金额=（∑_(i=1)^n▒〖电费电价i×电损电量i）*（1+税率）〗；（n为单价个数之和）
						meterLoss= (tempmeterLoss*(1+taxRate)).toFixed(6);
					}
					$(this).find("#meterLoss").val(meterLoss);
				}else{
					var meterLoss=$(this).find("#meterLoss").val();
					if(meterLoss==null || meterLoss==""){
						alert("当前电损计算方式为金额，请填写电损金额");
						calcFlag = false;
						$(this).find("#meterLoss").focus();
						return false;
					}
				}
			}
		}
		
		if($(this).find("#meterType").val() == "平峰谷表"){
			var flatUpperValue=$(this).find("#flatUpperValue").val();
			var peakUpperValue=$(this).find("#peakUpperValue").val();
			var valleyUpperValue=$(this).find("#valleyUpperValue").val();
			var topUpperValue=$(this).find("#topUpperValue").val();
			var flatPrice =  $(this).find("#flatPrice").val();
			if(flatPrice==null || flatPrice==""){
				flatPrice=0;
			}
			var peakPrice =  $(this).find("#peakPrice").val();
			if(peakPrice==null || peakPrice==""){
				peakPrice=0;
			}
			var valleyPrice =  $(this).find("#valleyPrice").val();
			if(valleyPrice==null || valleyPrice==""){
				valleyPrice=0;
			}
			var topPrice =  $(this).find("#topPrice").val();
			if(topPrice==null || topPrice==""){
				topPrice=0;
			}

			//计算上期读数
			var lastFlatReadnum = $(this).find("#lastFlatReadnum").val();
			if(lastFlatReadnum==null || lastFlatReadnum==""){
				lastFlatReadnum=0;
			}
			var nowFlatReadnum = $(this).find("#nowFlatReadnum").val();
			if(nowFlatReadnum==null || nowFlatReadnum==""){
				nowFlatReadnum=0;
			}
			if(parseFloat(lastFlatReadnum)>parseFloat(nowFlatReadnum)){
				if(flatUpperValue==null || flatUpperValue=="null"){
					calcFlag = false;
					alert("该平电表无上限值，上期读数应小于本期读数！！");
					return true;
				}
				flatReadnum=parseFloat(flatUpperValue)-parseFloat(lastFlatReadnum)+parseFloat(nowFlatReadnum);
			}else{
				flatReadnum=parseFloat(nowFlatReadnum)-parseFloat(lastFlatReadnum);
			}
			
			
			//计算上期读数
			var lastPeakReadnum = $(this).find("#lastPeakReadnum").val();
			if(lastPeakReadnum == null || lastPeakReadnum == ""){
				lastPeakReadnum = 0;
			}
			var nowPeakReadnum = $(this).find("#nowPeakReadnum").val();
			if(nowPeakReadnum==null || nowPeakReadnum==""){
				nowPeakReadnum=0;
			}
			if(parseFloat(lastPeakReadnum)>parseFloat(nowPeakReadnum)){
				if(peakUpperValue==null || peakUpperValue=="null"){
					calcFlag = false;
					alert("该峰电表无上限值，上期读数应小于本期读数！！");
					return true;
				}
				peakReadnum=parseFloat(peakUpperValue)-parseFloat(lastPeakReadnum)+parseFloat(nowPeakReadnum);
			}else{
				peakReadnum=parseFloat(nowPeakReadnum)-parseFloat(lastPeakReadnum);
			}
			
			//计算上期读数
			var lastValleyReadnum = $(this).find("#lastValleyReadnum").val();
			if(lastValleyReadnum == null || lastValleyReadnum == ""){
				lastValleyReadnum = 0;
			}
			var nowValleyReadnum = $(this).find("#nowValleyReadnum").val();
			if(nowValleyReadnum==null || nowValleyReadnum==""){
				nowValleyReadnum=0;
			}
			if(parseFloat(lastValleyReadnum)>parseFloat(nowValleyReadnum)){
				if(valleyUpperValue==null || valleyUpperValue=="null"){
					calcFlag = false;
					alert("该谷电表无上限值，上期读数应小于本期读数！！");
					return true;
				}
				valleyReadnum=parseFloat(valleyUpperValue)-parseFloat(lastValleyReadnum)+parseFloat(nowValleyReadnum);
			}else{
				valleyReadnum=parseFloat(nowValleyReadnum)-parseFloat(lastValleyReadnum);
			}
			
			//计算上期读数
			var lastTopReadnum = $(this).find("#lastTopReadnum").val();
			if(lastTopReadnum == null || lastTopReadnum == ""){
				lastTopReadnum = 0;
			}
			var nowTopReadnum = $(this).find("#nowTopReadnum").val();
			if(nowTopReadnum==null || nowTopReadnum==""){
				nowTopReadnum=0;
			}
			if(parseFloat(lastTopReadnum)>parseFloat(nowTopReadnum)){
				if(topUpperValue==null ||topUpperValue=="null"){
					calcFlag = false;
					alert("该尖电表无上限值，上期读数应小于本期读数！！");
					return true;
				}
				topReadnum=parseFloat(topUpperValue)-parseFloat(lastTopReadnum)+parseFloat(nowTopReadnum);
			}else{
				topReadnum=parseFloat(nowTopReadnum)-parseFloat(lastTopReadnum);
			}
		
			//计算本期度数
			var nowDegree = 0;
		/*	var lastReadnum = $(this).find("#lastReadnum").val();
			if(lastReadnum==null || lastReadnum==""){
				lastReadnum=0;
			}*/
			var meterRate = $(this).find("#meterRate").val();
			if(meterRate==null || meterRate==""){
				meterRate=0;
			}
			var totalReadnum = parseFloat(flatReadnum) + parseFloat(peakReadnum) + parseFloat(valleyReadnum) + parseFloat(topReadnum);
			//上期读数
			var lastReadnum = parseFloat(lastFlatReadnum) + parseFloat(lastPeakReadnum) + parseFloat(lastValleyReadnum) + parseFloat(lastTopReadnum);
			$(this).find("#lastReadnum").val(lastReadnum);
			
			//本期读数=平峰谷尖相加
			var nowReadnum = parseFloat(nowFlatReadnum) + parseFloat(nowPeakReadnum) + parseFloat(nowValleyReadnum) + parseFloat(nowTopReadnum);
			$(this).find("#nowReadnum").val(nowReadnum);
			nowDegree = totalReadnum*meterRate;
			$(this).find("#nowDegree").val(nowDegree);
			
			//分摊后度数=本期度数*移动分摊比例*报账点计量倍数；
			var calcMulti = 1;
			var calcMultiVal = $("#eleBillaccountPaymentdetailForm #calcMulti").val();
			if(calcMultiVal!=null && calcMultiVal!=""){
				calcMulti = parseFloat(calcMultiVal,10);
			}
			var cmccRatio = $(this).find("#cmccRatio").val();
			if(cmccRatio==null || cmccRatio==""){
				cmccRatio=0;
			}
			var cmccRatioAfter=nowDegree*cmccRatio*calcMulti/100;
			$(this).find("#cmccRatioAfter").val(cmccRatioAfter);
			
			//列出合同中所有电费单价，将分摊后的度数填写到各个单价对应的电量中，保证分摊后的度数等于各个单价对应的电量之和（系统要做判断）。
			var tempPayamount = 0;
			tempPayamount +=parseFloat(flatPrice,10)*(parseFloat(flatReadnum,10));
			tempPayamount +=parseFloat(peakPrice,10)*(parseFloat(peakReadnum,10));
			tempPayamount +=parseFloat(valleyPrice,10)*(parseFloat(valleyReadnum,10));
			tempPayamount +=parseFloat(topPrice,10)*(parseFloat(topReadnum,10));
			//×移动分摊比例×报账点计量倍数；
			tempPayamount=tempPayamount*cmccRatio*calcMulti/100;
			//根据单价是否含税是否含税，判断是正算还是反算
			var includePriceTax = $("#includePriceTax").val();
			if(includePriceTax==null || includePriceTax==""){
				alert("单价是否含税为空，无法计算");
				calcFlag = false;
				return false;
				includePriceTax=0;
			}
			var taxRate=$("#taxRate").text();
			if(taxRate==null || taxRate==""){
				alert("税率为空，无法计算");
				calcFlag = false;
				return false;
				taxRate=0;
			}else{
				taxRate = taxRate*1.00/100;
			}
			var payamountNotax = 0;
			var payamountTax = 0;
			var tax = 0;
			if(includePriceTax==1){
				//含税    含税金额=∑_(i=1)^n▒〖电费电价i×用电量i〗；（n为单价个数之和）；
				//不含税金额=含税金额/（1+电费税率）；
				//税金=含税金额-不含税金额；
				payamountTax = tempPayamount;
				payamountNotax = (payamountTax/(1+taxRate)).toFixed(6)*meterRate;
				tax=(payamountTax-payamountNotax).toFixed(6);
			}else{
				//不含税  不含税金额=∑_(i=1)^n▒〖电费电价i×用电量i〗；（n为单价个数之和）
				//税金=不含税金额*电费税率
				payamountNotax = tempPayamount*meterRate;
				tax=(payamountNotax*taxRate).toFixed(6);
			}
			$(this).find("#tax").val(tax);
			$(this).find("#payamountNotax").val(payamountNotax);
			
			// 是否有电损(0:否 1:是)
			var includeLoss = $("#includeLoss").val();
			if(includeLoss == 1){// 计算电损
				var lossType = $("#lossType").val();
				if(lossType==null || lossType==""){
					alert("电损计算方式为空，无法计算");
					calcFlag = false;
					return false;
				}
				lossType = parseInt(lossType);
				if(lossType==0){
					//计算电损金额 (可以修改，可以计算)
					var tempmeterLoss = 0;
					$(this).find("input[name='lossDegrees']").each(function(index,element){
						if($(this).val()==null || $(this).val()==""){
							alert("电损计算方式为按度数，请填写电损度数");
							$(this).focus();
							calcFlag = false;
							return false;
						}
						if(index == 0){
							if(flatPrice!=null && flatPrice!="" && $(this).val()!=null && $(this).val()!=""){
								tempmeterLoss += parseFloat(flatPrice,10)*parseInt($(this).val(),10);
							}
						}
						if(index == 1){
							if(peakPrice!=null && peakPrice!="" && $(this).val()!=null && $(this).val()!=""){
								tempmeterLoss += parseFloat(peakPrice,10)*parseInt($(this).val(),10);
							}
						}
						if(index == 2){
							if(valleyPrice!=null && valleyPrice!="" && $(this).val()!=null && $(this).val()!=""){
								tempmeterLoss += parseFloat(valleyPrice,10)*parseInt($(this).val(),10);
							}
						}
						if(index == 3){
							if(topPrice!=null && topPrice!="" && $(this).val()!=null && $(this).val()!=""){
								tempmeterLoss += parseFloat(topPrice,10)*parseInt($(this).val(),10);
							}
						}
						
					});
					var meterLoss = 0;
					if(includePriceTax==1){
						//含税
						//电损金额=∑_(i=1)^n▒〖电费电价i×电损电量i〗；（n为单价个数之和）；
						meterLoss=tempmeterLoss;
					}else{
						//不含税
						//电损金额=（∑_(i=1)^n▒〖电费电价i×电损电量i）*（1+税率）〗；（n为单价个数之和）
						meterLoss= (tempmeterLoss*(1+taxRate)).toFixed(6);
					}
					$(this).find("#meterLoss").val(meterLoss);
				}else{
					var meterLoss=$(this).find("#meterLoss").val();
					if(meterLoss==null || meterLoss==""){
						alert("当前电损计算方式为金额，请填写电损金额");
						calcFlag = false;
						$(this).find("#meterLoss").focus();
						return false;
					}
				}
			}
		}
	});
	
	if(!calcFlag){
		return false;
	}
	
	/**
	 * 其次 统计报账点信息
	 */
	//系统计算不含税金额  " eleBillaccountPaymentdetailForm billamountNotaxSys"
	var billamountNotaxSys = 0;
	$("#myTabContent").find("input[name='payamountNotax']").each(function(){
		var val = $(this).val();
		if(val!=null && val!=""){
			billamountNotaxSys += parseFloat(val,10);
		}
	});
	billamountNotaxSys = billamountNotaxSys.toFixed(6);
	$("#eleBillaccountPaymentdetailForm #billamountNotaxSys").val(billamountNotaxSys);
	
	//系统计算电损金额 lossAmountSys
	var lossAmountSys = 0;
	$("#myTabContent").find("input[name='meterLoss']").each(function(){
		var val = $(this).val();
		if(val!=null && val!=""){
			lossAmountSys += parseFloat(val,10);
		}
	});
	lossAmountSys = lossAmountSys.toFixed(6);
	$("#eleBillaccountPaymentdetailForm #lossAmountSys").val(lossAmountSys);
	
	//系统计算税金 billamountTaxamountSys
	var billamountTaxamountSys = 0;
	$("#myTabContent").find("input[name='tax']").each(function(){
		var val = $(this).val();
		if(val!=null && val!=""){
			billamountTaxamountSys += parseFloat(val,6);
		}
	});
	billamountTaxamountSys = Number(billamountTaxamountSys).toFixed(6);
	$("#eleBillaccountPaymentdetailForm #billamountTaxamountSys").val(billamountTaxamountSys);
	
	//计算报账金额   [系统计算不含税金额+系统计算税金+系统计算电损金额+其他费用]
	var otherfee = 0;
	var otherVal = $("#eleBillaccountPaymentdetailForm #otherAmount").val();
	if(otherVal!=null && otherVal!=""){
		otherfee = parseFloat(otherVal,10);
	}else{
		otherfee = 0;
	}
	$("#eleBillaccountPaymentdetailForm #billAmountSys").val(parseFloat(billamountNotaxSys,6)+parseFloat(billamountTaxamountSys,6)+parseFloat(lossAmountSys,6)+parseFloat(otherfee,6));
	/**
	 * TODO
	 * 实际金额：
实际各个费用初始值为对应的系统计算的费用，可以由用户改写。

|实际报账金额-系统计算报账金额|/实际报账金额>=百分比阈值（各省自己维护）
|实际报账金额-系统计算报账金额|>=绝对值阈值（各省自己维护）
百分比阈值默认值：0.0001；绝对值阈值默认值：1。
以上两个条件都满足时，备注信息需要必填。备注信息中系统默认填写第一句：“实际报账金额与系统计算金额偏差超过阈值，原因如下：”。用户填写的备注说明接在这一句之后。

	 */
	//实际不含税金额
	if($("#billamountNotax").val() == '' || $("#billamountNotax").val() == null ){
		var billamountNotax = Number(billamountNotaxSys).toFixed(6);
		$("#eleBillaccountPaymentdetailForm #billamountNotax").val(billamountNotax);//赋值
	}
	//实际税金
	if($("#billamountTaxamount").val() == '' || $("#billamountTaxamount").val() == null ){
		var billamountTaxamount = Number(billamountTaxamountSys).toFixed(6);
		$("#eleBillaccountPaymentdetailForm #billamountTaxamount").val(billamountTaxamount);
	}
	//实际电损金额
	if($("#lossAmount").val() == '' || $("#lossAmount").val() == null ){
		var lossAmount = lossAmountSys;
		$("#eleBillaccountPaymentdetailForm #lossAmount").val(lossAmount);
	}
	//实际报账金额
	if($("#billAmountActual").val() == '' || $("#billAmountActual").val() == null ){
		var billAmountActual = $("#eleBillaccountPaymentdetailForm #billAmountSys").val();
		$("#billAmountActual").val(billAmountActual);
	}
	
	//计算电表总电量
	var totalDegrees = 0;
	$("#myTabContent").find("input[name='nowDegree']").each(function(){
		var val = $(this).val();
		if(val!=null && val!=""){
			totalDegrees += parseFloat(val,10);
		}
	});
	totalDegrees = totalDegrees.toFixed(6);
	$("#eleBillaccountPaymentdetailForm #totalDegree").val(totalDegrees);
	

	//实际总耗电量=电表总电量*报账点计量倍数
	var calcMulti = 1;
	var calcMultiVal = $("#eleBillaccountPaymentdetailForm #calcMulti").val();
	if(calcMultiVal!=null && calcMultiVal!=""){
		calcMulti = parseFloat(calcMultiVal,10);
	}
	$("#eleBillaccountPaymentdetailForm #totalDegreeActual").val(totalDegrees*calcMulti);
	
	return true;
}

function compareDate(d1,d2){
	var date1 = new Date(d1);
	var date2 = new Date(d2);
	var s1 = date1.getTime(),s2 = date2.getTime();
	if(s1>s2){
		return -1;
	}
	return 1;
}

function getSubDays(d1 , d2){
	var date1 = new Date(d1);
	var date2 = new Date(d2);
	var s1 = date1.getTime(),s2 = date2.getTime();
	var total = (s2 - s1)/1000;
	 
	 
	var day = parseInt(total / (24*60*60));//计算整数天数
	
	return day + 1;
}

//计算各种金额
function calcAmount(){
	var isIncludeAll = $("#isIncludeAll").val();
	if(isIncludeAll==1){
		//包干
		return calcAmountIncludeAll();
	}else{
		//非包干计算
		return calcAmountNotIncludeAll();
	}
}

/**
 * 计算标杆
 */
function calcBenchmark(){
	var billaccountId = $("#eleBillaccountPaymentdetailForm #billaccountId").val();
	if(billaccountId == null || billaccountId == ""){
		alert("报账点为空");
		return;
	}
	
	var billamountStartdate = $("#eleBillaccountPaymentdetailForm #billamountStartdate").val();
	if(billamountStartdate == null || billamountStartdate==""){
		alert("请先填写缴费期始");
		return;
	}
	var billamountEnddate = $("#eleBillaccountPaymentdetailForm #billamountEnddate").val();
	if(billamountEnddate == null || billamountEnddate==""){
		alert("请先填写缴费期终");
		return;
	}
	
	if(compareDate(billamountStartdate , billamountEnddate)<0){
		alert("缴费期始不能大于缴费期终");
		return ;
	}
	
	//计算费用
	if(calcAmount()){	
	}else{
		return;
	}
	
	var totalDegreeActual = $("#eleBillaccountPaymentdetailForm #totalDegreeActual").val();
	
	$.post("getBenchmark",{"billaccountId":billaccountId , "billamountStartdate":billamountStartdate,"billamountEnddate":billamountEnddate,"totalDegreeActual":totalDegreeActual},function(data){
		if(data.success == 0){
			alert(data.msg);
		}else{
			$("#benchmarkDiv").html(data.Obj);
		}
		
	});
}

function validYuzhi(){
	/**
	 * |实际报账金额-系统计算报账金额|/实际报账金额>=百分比阈值（各省自己维护）
	|实际报账金额-系统计算报账金额|>=绝对值阈值（各省自己维护）
	百分比阈值默认值：0.0001；绝对值阈值默认值：1。
	以上两个条件都满足时，备注信息需要必填。备注信息中系统默认填写第一句：“实际报账金额与系统计算金额偏差超过阈值，原因如下：”。用户填写的备注说明接在这一句之后。
	 */
	//实际报账金额
	var billamountNotax = $("#eleBillaccountPaymentdetailForm #billAmountActual").val();
	//系统计算报账金额
	var billAmountSys=$("#eleBillaccountPaymentdetailForm #billAmountSys").val();
	billamountNotax = parseFloat(billamountNotax , 10);
	billAmountSys = parseFloat(billAmountSys , 10);
	if(Math.abs(billamountNotax-billAmountSys)/billamountNotax>=0.0001 && Math.abs(billamountNotax-billAmountSys)>=1){
		var paymentdetailNote = $("#paymentdetailNote").val();
		if(paymentdetailNote==null || paymentdetailNote==""){
			$("#paymentdetailNote").val("实际报账金额与系统计算金额偏差超过阈值，原因如下：");
			alert("备注必须填写偏差超过阈值原因");
			$("#paymentdetailNote").focus();
			return false;
		}
	}
	return true;
}

/**
 * 校验 包干备注校验
 */
function validIncludeAllNote(){
	var paySign = $("#paySign").val();
	var billamountStartdate = $("#eleBillaccountPaymentdetailForm #billamountStartdate").val();
	var billamountEnddate = $("#eleBillaccountPaymentdetailForm #billamountEnddate").val();
	if(paySign == 2){
		//月
		var days = getSubDays(billamountStartdate , billamountEnddate);
		var temp = days/30.42;
		
		var shifenwei = Math.floor(temp*10)%10;
		if(shifenwei>=1 && shifenwei<=8){
			var paymentdetailNote = $("#paymentdetailNote").val();
			if(paymentdetailNote==null || paymentdetailNote==""){
				alert("备注必须填写为什么缴费周期不是整月");
				$("#paymentdetailNote").focus();
				return false;
			}
		}
	}else if(paySign == 3){
		//年
		var days = getSubDays(billamountStartdate , billamountEnddate);
		var temp = days/365;
		
		var shifenwei = Math.floor(temp*10)%10;
		if(shifenwei>=1 && shifenwei<=8){
			var paymentdetailNote = $("#paymentdetailNote").val();
			if(paymentdetailNote==null || paymentdetailNote==""){
				alert("备注必须填写为什么缴费周期不是整年");
				$("#paymentdetailNote").focus();
				return false;
			}
		}
	}
}

/*
function showCalc(s){
	        if(s.indexOf(".") > 0){  
	            s = s.replaceAll("0+?$", "");//去掉多余的0  
	            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉  
	        }  
	        return s;  
}
*/

function showCalc(old){  
    //拷贝一份 返回去掉零的新串  
    newstr=old;  
    //循环变量 小数部分长度  
    var leng = old.length-old.indexOf(".")-1  
    //判断是否有效数  
    if(old.indexOf(".")>-1){  
        //循环小数部分  
        for(i=leng;i>0;i--){  
                //如果newstr末尾有0  
                if(newstr.lastIndexOf("0")>-1 && newstr.substr(newstr.length-1,1)==0){  
                    var k = newstr.lastIndexOf("0");  
                    //如果小数点后只有一个0 去掉小数点  
                    if(newstr.charAt(k-1)=="."){  
                        return  newstr.substring(0,k-1);  
                    }else{  
                    //否则 去掉一个0  
                        newstr=newstr.substring(0,k);  
                    }  
                }else{  
                //如果末尾没有0  
                    return newstr;  
                }  
            }  
        }  
        return old;  
  } 