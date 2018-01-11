
var item = null;
var startTime = null;
var endTime = null;
var payTime = null;
var actMoney = null;

//税金承担方式对象
var taxMethodObject=null;

//增值税抵扣对象
var taxDeductionObject=null;

//票据类型对象
var invoiceTypeObject=null;

//查询支付方式
var paymentMethodObject=null;

//查询费用类型
var amountTypeObject=null;

//查询业务类型
var businessTypeObject=null;

//查询报账类型
var billTypeObject=null;

//是否含税
var includeTaxObject=null;

//查询费用类别
var chargeTypeObject=null;

$(document).ready(function() {
	initCurrentPage();
	//税金承担方式
	taxMethodObject=queryDictType("TAX_METHOD");
	//增值税抵扣
	taxDeductionObject=queryDictType("TAX_DEDUCTION");
	//票据类型
	invoiceTypeObject=queryDictType("INVOICE_TYPE");
	//查询支付方式
	paymentMethodObject=queryDictType("PAYMENY_METHOD");
	//查询费用类型
	amountTypeObject=queryDictType("AMOUNT_TYPE");
	//查询业务类型
	businessTypeObject=queryDictType("BUSINESS_TYPE");
	//查询报账类型
	billTypeObject=queryDictType("BILL_TYPE");
	//是否含税
	includeTaxObject=queryDictType("INCLUDE_TAX");
	//查询费用类别
	chargeTypeObject=queryDictType("CHARGE_TYPE");
	$('#paymentEnddate').focus();
	$("#commitbtn").click(function(){
		
	});
	$("#savebtn").click(function(){
		var sum = 0;
		startTime = $('#paymentStartdate').val();
		if(startTime.length == 0){
			//alertModel("请输入缴费期始！");
			$("#noway1").html("<img src=\"../../../image/error.png\"/>请输入缴费期始！");
			setTimeout(function(){
    			$('#noway1').html("");
    		}, 3000);
			return;
		}
		endTime = $('#paymentEnddate').val();
		if(endTime.length == 0){
			//alertModel("请输入缴费期终！");
			$("#noway2").html("<img src=\"../../../image/error.png\"/>请输入缴费期终！");
			setTimeout(function(){
    			$('#noway2').html("");
    		}, 3000);
			return;
		}
		actmoney = $('#payActamount').val();
		if(actmoney.length == 0){
			//alertModel("请输入实际报账金额！");
			$("#noway3").html("<img src=\"../../../image/error.png\"/>请输入实际报账金额！");
			setTimeout(function(){
    			$('#noway3').html("");
    		}, 3000);
			return;
		}
		$('.numclass').each(function(){
			num = $(this).val();
			sum = sum + parseFloat(num);
		})
		if(sum != 0 && sum != null){
			if(sum != parseFloat($('#payActamount').val())){
				alertModel("实际报账金额与各机房金额总和不符，请确认金额！");
				return;
			}
		}
		$('#paymentState').val(0);	
		$('#paymentFlowstate').val(-1);
		formSubmit();
	});
	$("#backbtn").click(function(){
		back();
	});
	$('#paymentStartdate').blur(function(){
		startTime = $(this).val();
		if(startTime.length == 0){
			$("#noway1").html("<img src=\"../../../image/error.png\"/>缴费期始必须录入！");
    		setTimeout(function(){
    			$('#noway1').html("");
    		}, 3000);
    		$('#paymentStartdate').focus();
    		return;
		}
		conStartTime = $('#contractStartdate').html();
		var stateDate = new Date(startTime.replace(/-/g, "/"));
		var conStartDate = new Date(conStartTime.replace(/-/g, "/"));
		if(stateDate.getTime() - conStartDate.getTime() < 0){
    		//$('#noway1').html("只允许在合同期间内缴费！");
    		$("#noway1").html("<img src=\"../../../image/error.png\"/>只允许在合同期间内缴费！");
    		setTimeout(function(){
    			$('#noway1').html("");
    		}, 3000);
    		$('#paymentStartdate').focus();
    		return;
    	}
		endTime = $('#paymentEnddate').val();
		if(endTime.length != 0){
	    	var endDate = new Date(endTime.replace(/-/g, "/"));
	    	var time = endDate.getTime() - stateDate.getTime();
	    	if(time<0 || time == NaN){
	    		//$('#noway1').html("缴费期始必须小于缴费起终！");
	    		$("#noway1").html("<img src=\"../../../image/error.png\"/>缴费期始必须小于缴费起终！");
	    		setTimeout(function(){
	    			$('#noway1').html("");
	    		}, 3000);
	    		$('#paymentStartdate').focus();
	    		return;
	    	}
		}
		payTime = parseInt(time / (1000 * 60 * 60 * 24))+1;
    	calMoney(payTime);
	});
	
	
	$('#paymentEnddate').blur(function(){
		endTime = $(this).val();
		if(endTime.length == 0){
			//$('#noway2').html("缴费期终必须录入！");
			$("#noway2").html("<img src=\"../../../image/error.png\"/>缴费期终必须录入！");
    		setTimeout(function(){
    			$('#noway2').html("");
    		}, 3000);
    		$('#paymentEnddate').focus();
    		return;
		}
		conEndTime = $('#contractEnddate').html();
		var endDate = new Date(endTime.replace(/-/g, "/"));
		var conEndDate = new Date(conEndTime.replace(/-/g, "/"));
		if(endDate.getTime() - conEndDate.getTime() > 0){
    		//$('#noway2').html("只允许在合同期间内缴费！");
    		$("#noway2").html("<img src=\"../../../image/error.png\"/>只允许在合同期间内缴费！");
    		setTimeout(function(){
    			$('#noway2').html("");
    		}, 3000);
    		$('#paymentEnddate').focus();
    		return;
    	}
		startTime = $('#paymentStartdate').val();
		if(startTime.length != 0){
			var stateDate = new Date(startTime.replace(/-/g, "/"));
	    	var time = endDate.getTime() - stateDate.getTime();
	    	if(time<0 || time == NaN){
	    		//$('#noway2').html("缴费期终必须大于缴费起始！");
	    		$("#noway2").html("<img src=\"../../../image/error.png\"/>缴费期终必须大于缴费起始！");
	    		setTimeout(function(){
	    			$('#noway2').html("");
	    		}, 3000);
	    		$('#paymentEnddate').focus();
	    		return;
	    	}
		}
		payTime = parseInt(time / (1000 * 60 * 60 * 24))+1;
    	calMoney(payTime);
	});
	$('#payActamount').blur(function(){
		actMoney = $(this).val();
		creatTable();
	});
});
function getSelect(){
	//税金承担方式
	taxMethodObject=queryDictType("TAX_METHOD");
	//增值税抵扣
	taxDeductionObject=queryDictType("TAX_DEDUCTION");
	//票据类型
	invoiceTypeObject=queryDictType("INVOICE_TYPE");
	//查询支付方式
	paymentMethodObject=queryDictType("PAYMENY_METHOD");
	//查询费用类型
	amountTypeObject=queryDictType("AMOUNT_TYPE");
	//查询业务类型
	businessTypeObject=queryDictType("BUSINESS_TYPE");
	//查询报账类型
	billTypeObject=queryDictType("BILL_TYPE");
	//是否含税
	includeTaxObject=queryDictType("INCLUDE_TAX");
	//查询费用类别
	chargeTypeObject=queryDictType("CHARGE_TYPE");
}
function initCurrentPage(){
	show();
//	initSelect();
}
function getSelect(){
	//税金承担方式
	taxMethodObject=queryDictType("TAX_METHOD");
	//增值税抵扣
	taxDeductionObject=queryDictType("TAX_DEDUCTION");
	//票据类型
	invoiceTypeObject=queryDictType("INVOICE_TYPE");
	//查询支付方式
	paymentMethodObject=queryDictType("PAYMENY_METHOD");
	//查询费用类型
	amountTypeObject=queryDictType("AMOUNT_TYPE");
	//查询业务类型
	businessTypeObject=queryDictType("BUSINESS_TYPE");
	//查询报账类型
	billTypeObject=queryDictType("BILL_TYPE");
	//是否含税
	includeTaxObject=queryDictType("INCLUDE_TAX");
	//查询费用类别
	chargeTypeObject=queryDictType("CHARGE_TYPE");
}
function back(){
	localStorage.removeItem("billaccountId");
	localStorage.removeItem("billaccountcontractId");
	//window.location.href="recording.html";
	javascript:history.back(-1);
}
function show(){
	getSelect();
	var paymentId=getParameter("paymentId");
	var billaccountId=localStorage.getItem("billaccountId");
	var billaccountcontractId=localStorage.getItem("billaccountcontractId");
	$.ajax({
		url : 'queryContractPayment',// 跳转到 action
		data : {
			paymentId : paymentId,
			billaccountId:billaccountId,
			billaccountcontractId:billaccountcontractId
			},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if(back != null){
				if(back.success == "1"){
						item=back.obj;
						$('#tb1 tr:gt(0)').remove();
						var s = '';
						for(var i=0;i<item.length;i++){
							//报账点合同信息
							$("#billaccountCode").text(item[i].billaccountCode);
							$("#billaccountName").text(item[i].billaccountName);
							$("#paymentDate").text(new Date(item[i].paymentDate).format("yyyy-MM-dd"));
							$("#contractCode").text(item[i].contractCode);
							$("#contractName").text(item[i].contractName);
							$("#payActamount").text(item[i].payActamount);
							$("#totalAmount").text(item[i].totalAmount);
							$("#billamountTaxratio").text(item[i].billamountTaxratio);
							$("#yearAmount").text(item[i].yearAmount);
							$("#contractStartdate").text(new Date(item[i].contractStartdate).format("yyyy-MM-dd"));
							$("#contractEnddate").text(new Date(item[i].contractEnddate).format("yyyy-MM-dd"));
							$("#contractYearquantity").text(item[i].contractYearquantity);
							$("#supplierCode").text(item[i].supplierCode);
							$("#supplierName").text(item[i].supplierName);
							
							$("#includeTax-hidden").val(item[i].includeTax);
							$("#billamountTaxratio-hidden").val(item[i].billamountTaxratio);
							
							//报账点缴费信息
							$('#taxMethod').empty();
							for (var j = 0; j < taxMethodObject.length; j++) {
								if(item[i].taxMethod == taxMethodObject[j].dict_value){
									$("#taxMethod").append("<option selected = 'selected' value=" + taxMethodObject[j].dict_value + ">" + taxMethodObject[j].dict_name + "</option>");
								}else{
									$("#taxMethod").append("<option value=" + taxMethodObject[j].dict_value + ">" + taxMethodObject[j].dict_name + "</option>");
								}
							}
							
							$('#taxDeduction').empty();
							for (var j = 0; j < taxDeductionObject.length; j++) {
								if(item[i].taxDeduction == taxDeductionObject[j].dict_value){
									$("#taxDeduction").append("<option selected = 'selected' value=" + taxDeductionObject[j].dict_value + ">" + taxDeductionObject[j].dict_name + "</option>");
								}else{
									$("#taxDeduction").append("<option value=" + taxDeductionObject[j].dict_value + ">" + taxDeductionObject[j].dict_name + "</option>");
								}
							}
							
							$('#invoiceType').empty();
							for (var j = 0; j < invoiceTypeObject.length; j++) {
								if(item[i].invoiceType == invoiceTypeObject[j].dict_value){
									$("#invoiceType").append("<option selected = 'selected' value=" + invoiceTypeObject[j].dict_value + ">" + invoiceTypeObject[j].dict_name + "</option>");
								}else{
									$("#invoiceType").append("<option value=" + invoiceTypeObject[j].dict_value + ">" + invoiceTypeObject[j].dict_name + "</option>");
								}
							}
							
							$('#paymentMethod').empty();
							for (var j = 0; j < paymentMethodObject.length; j++) {
								if(item[i].paymentMethod == paymentMethodObject[j].dict_value){
									$("#paymentMethod").append("<option selected = 'selected' value=" + paymentMethodObject[j].dict_value + ">" + paymentMethodObject[j].dict_name + "</option>");
								}else{
									$("#paymentMethod").append("<option value=" + paymentMethodObject[j].dict_value + ">" + paymentMethodObject[j].dict_name + "</option>");
								}
							}
							
							$('#amountType').empty();
							for (var j = 0; j < amountTypeObject.length; j++) {
								if(item[i].amountType == amountTypeObject[j].dict_value){
									$("#amountType").append("<option selected = 'selected' value=" + amountTypeObject[j].dict_value + ">" + amountTypeObject[j].dict_name + "</option>");
								}else{
									$("#amountType").append("<option value=" + amountTypeObject[j].dict_value + ">" + amountTypeObject[j].dict_name + "</option>");
								}
							}
							
							$('#businessType').empty();
							for (var j = 0;j < businessTypeObject.length; j++) {
								if(item[i].businessType == businessTypeObject[j].dict_value){
									$("#businessType").append("<option selected = 'selected' value=" + businessTypeObject[j].dict_value + ">" + businessTypeObject[j].dict_name + "</option>");
								}else{
									$("#businessType").append("<option value=" + businessTypeObject[j].dict_value + ">" + businessTypeObject[j].dict_name + "</option>");
								}
							}
							
							$('#billType').empty();
							for (var j = 0; j < billTypeObject.length;j++) {
								if(item[i].billType == billTypeObject[j].dict_value){
									$("#billType").append("<option selected = 'selected' value=" + billTypeObject[j].dict_value + ">" + billTypeObject[j].dict_name + "</option>");
								}else{
									$("#billType").append("<option value=" + billTypeObject[j].dict_value + ">" + billTypeObject[j].dict_name + "</option>");
								}
							}
							$('#includeTax').empty();
							for (var j = 0; j < includeTaxObject.length; j++) {
								if(item[i].includeTax == includeTaxObject[j].dict_value){
									$("#includeTax").append("<option selected = 'selected' value=" + includeTaxObject[j].dict_value + ">" + includeTaxObject[j].dict_name + "</option>");
								}else{
									$("#includeTax").append("<option value=" + includeTaxObject[j].dict_value + ">" + includeTaxObject[j].dict_name + "</option>");
								}
							}
							
							$('#chargeType').empty();
							for (var j = 0; j < chargeTypeObject.length; j++) {
								if(item[i].chargeType == chargeTypeObject[j].dict_value){
									$("#chargeType").append("<option selected = 'selected' value=" + chargeTypeObject[j].dict_value + ">" + chargeTypeObject[j].dict_name + "</option>");
								}else{
									$("#chargeType").append("<option value=" + chargeTypeObject[j].dict_value + ">" + chargeTypeObject[j].dict_name + "</option>");
								}
							}
							
							$("#paymentStartdate").val(new Date(item[i].paymentStartdate).format("yyyy-MM-dd"));
							$("#paymentEnddate").val(new Date(item[i].paymentEnddate).format("yyyy-MM-dd"));
							$("#dueamount").val(item[i].dueamount);
							$("#billamountTaxamount").val(item[i].billamountTaxamount);
							$("#payCalcamount").val(item[i].payCalcamount);
							$("#payActamount").val(item[i].payActamount);
							$("#paymentNote").val(item[i].paymentNote);
							$("#paymentId").val(item[i].paymentId);
							//报账点与机房
							
						/*	for(var j=0;j<item[i].baseresourcePaymentdetail.length;j++){
								s = "<tr style='text-align: center;'><td>"
									+ item[i].baseresourcePaymentdetail[j].baseresourceCode
									+ "</td><td>"
									+ item[i].baseresourcePaymentdetail[j].baseresourceName
									+ "</td>"
									+"<td><input type='text' value='"+item[i].baseresourcePaymentdetail[j].dueamount+"'>"
									+ "</td>"
									+ "<td><input type='text' value='"+item[i].baseresourcePaymentdetail[j].billamountTaxamount+"'>"
									+ "</td><td><input type='text' value='"+item[i].baseresourcePaymentdetail[j].payamount+"'>"
									+ "</td></tr>";
								$('#tb1').append(s);
							}*/
							creatTable();
							
						}
				
				}
			}
		},
		error : function(back) {
					
		}
	});
}
function calMoney(day){
	billamountTaxratio = getBillamountTaxratio();
	var dayMoney = $("#yearAmount").html()/365;
	var billamountTaxamount = dayMoney*billamountTaxratio*day;
	var dueamount = dayMoney*day - billamountTaxamount;
	$('#billamountTaxamount').val(billamountTaxamount);
	$('#dueamount').val(dueamount);
	$('#payCalcamount').val(dayMoney*day);
}
function creatTable(){
	list = item[0].baseresourcePaymentdetail;
	$('#tb1 td').remove();//删除之前的数据
	var s = '';
	for(var j=0;j<list.length;j++){
		s = "<tr style='text-align: center;'><td>"
			+ list[j].baseresourceCode
			+ "</td><td>"
			+ list[j].baseresourceName
			+ "</td>"
			+ "<input name='lstDatbase["+j+"].rentPaymentdetailVO.paymentdetailId' type='hidden' value='"+list[j].paymentdetailId+ "' name='lstDatbase["+j+"].rentPaymentdetailVO.paymentdetailId' ></input>"
			+"<td><input type='text' readOnly='readOnly' id='dueamount"+j+"' value='"+list[j].dueamount+"' name='lstDatbase["+j+"].rentPaymentdetailVO.dueamount' >"
			+ "</td>"
			+ "<td><input type='text' readOnly='readOnly' id='billamountTaxamount"+j+"' value='"+list[j].billamountTaxamount+"' name='lstDatbase["+j+"].rentPaymentdetailVO.billamountTaxamount' >"
			+ "</td><td><input type='text' class='numclass' id='payamount"+j+"' value='"+list[j].payamount+"' name='lstDatbase["+j+"].rentPaymentdetailVO.payamount' >"
			+ "</td></tr>";
		$('#tb1').append(s);
		eachPayMoney();
	}
}
function eachPayMoney(){
	$('.numclass').each(function(i){
		$(this).blur(function(){
			billamountTaxratio = getBillamountTaxratio();
			var actMoney = $(this).val();
			$('#billamountTaxamount'+i).val(actMoney * billamountTaxratio);
			$('#dueamount'+i).val(actMoney - actMoney * billamountTaxratio);
		})
	})
}
function getBillamountTaxratio(){
	var billamountTaxratio = null;
	if($('#includeTax-hidden').val() == '0'){
		billamountTaxratio = 0;
	}else if($('#includeTax-hidden').val() == '1'){
		billamountTaxratio = $('#billamountTaxratio').val();
	}
	return billamountTaxratio;
}


function initSelect(){

}

function tableDateFormatter(value, row, index){
	return new Date(value).format("yyyy-MM-dd");
}
/**
 * 时间对象的格式化;
 */
Date.prototype.format = function(format){
    /*
     * eg:format="YYYY-MM-dd hh:mm:ss";
     */
	
    var o = {
        "M+" :this.getMonth() + 1, // month
        "d+" :this.getDate(), // day
        "h+" :this.getHours(), // hour
        "m+" :this.getMinutes(), // minute
        "s+" :this.getSeconds(), // second
        "q+" :Math.floor((this.getMonth() + 3) / 3), // quarter
        "S" :this.getMilliseconds()
    // millisecond
    }
 
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
                .substr(4 - RegExp.$1.length));
    }
 
    for ( var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                    : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}
function formSubmit(){
	var objButton1=$("#savebtn");
    var data=$('#dataForm').serialize();
	var submitData = decodeURIComponent(data,true);
	$("#savebtn").attr("disable",true);
	$.ajax({
	    url:'updateAllForm',
	    data: submitData,
 		type : 'post',
		beforeSend:function(){//触发ajax请求开始时执行    
	        objButton1.val('请等待').attr('disabled',true);//改变提交按钮上的文字并将按钮设置为不可点击    
	    },  
	    cache:false,
	    async:true,
	    success:function(res){
	    	alert(res.msg);
	    	window.location.href="paymentDataMainte.html";
	    	$("#savebtn").attr("disable",false);
	    },
	    error:function(res){
			alert(res.msg);
			$("#savebtn").attr("disable",false);
	    },
	    complete:function(){//ajax请求完成时执行    
            objButton1.val('保存').attr('disabled',false);//改变提交按钮上的文字并将按钮设置为不可点击    
	    }  
	});
}