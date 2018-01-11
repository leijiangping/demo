
var item = null;
var id = null;
var startTime = null;
var endTime = null;
var payTime = null;
var actMoney = null;

$(document).ready(function() {
	initCurrentPage();
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
		if(sum != 0){
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
			//$('#noway1').html("缴费期始必须录入！");
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
function initCurrentPage(){
	show();
}
function back(){
	localStorage.removeItem("billAccountId")
	//window.location.href="recording.html";
	javascript:history.back(-1);
}
function show(){
	var ID = localStorage.getItem("billAccountId");
	$.ajax({
		url : 'queryAll',
		data : {
			billAccountId : ID
			},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if(back != null){
				if(back.success == "1"){
					item = back.Obj;
					initSelect();
					$("#paymentDate").val(tableDateFormatter(new Date()));
					if(item.billAccountVO != null){
						$("#billAcconutId").val(item.billAccountVO.billAccountId);
						$("#billAcconutCode").text(item.billAccountVO.billAccountCode);
						$("#billAcconutName").text(item.billAccountVO.billAccountName);
					}
					if(item.datContractVO != null){
						$("#contractCode").text(item.datContractVO.contractCode);
						$("#contractName").text(item.datContractVO.contractName);
						$("#contractStartdate").text(item.datContractVO.contractStartdate);
						$("#contractEnddate").text(item.datContractVO.contractEnddate);
						$("#contractYearquantity").text(item.datContractVO.contractYearquantity);
						$("#paymentStartdate").val(item.datContractVO.contractStartdate);
					}
					if(item.rentContractVO != null){
						$("#totalAmount").text(item.rentContractVO.totalAmount);
						$("#includeTax-hidden").val(item.rentContractVO.includeTax);
						if(item.rentContractVO.includeTax == '1'){
							$("#includeTax").text("是");
						}else if(item.rentContractVO.includeTax == '0'){
							$("#includeTax").text("否");
						}
						$("#billamountTaxratio").text(item.rentContractVO.billamountTaxratio);
						$("#billamountTaxratio-hidden").val(item.rentContractVO.billamountTaxratio);
						//$("#yearAmount").val(item.rentContractVO.yearAmount);
						$("#yearAmount").text((item.rentContractVO.totalAmount/item.datContractVO.contractYearquantity).toFixed(6));
					}
					if(item.datSupplierVO != null){
						$("#supplierCode").text(item.datSupplierVO.supplierCode);
						$("#supplierName").text(item.datSupplierVO.supplierName);
					}
					if(item.rentPayMentVO != null){
						//$("#billType").val(item.rentPayMentVO.billType);
						//$("#businessType").val(item.rentPayMentVO.businessType);
						//$("#amountType").val(item.rentPayMentVO.amountType);
						//$("#chargeType").val(item.rentPayMentVO.chargeType);
						//$("#paymentMethod").val(item.rentPayMentVO.paymentMethod);
						//$("#invoiceType").val(item.rentPayMentVO.invoiceType);
						//$("#taxMethod").val(item.rentPayMentVO.taxMethod);
						$("#paymentStartdate").val(item.rentPayMentVO.paymentEnddate);
						//$("#paymentStartdate").val(item.rentPayMentVO.paymentStartdate);
						//$("#paymentEnddate").val(item.rentPayMentVO.paymentEnddate);
						//$("#taxDeduction").val(item.rentPayMentVO.taxDeduction);
						//$("#dueamount").val(item.rentPayMentVO.dueamount);
						//$("#billamountTaxamount").val(item.rentPayMentVO.billamountTaxamount);
						//$("#payCalcamount").val(item.rentPayMentVO.payCalcamount);
						//$("#payActamount").val(item.rentPayMentVO.payActamount);
						//$("#paymentNote").val(item.rentPayMentVO.paymentNote);
					}
					creatTable();
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
	dayMoney=dayMoney.toFixed(6);
	var billamountTaxamount = dayMoney*billamountTaxratio*day;
	billamountTaxamount=billamountTaxamount.toFixed(6);
	var dueamount = dayMoney*day - billamountTaxamount;
	dueamount=dueamount.toFixed(6);
	$('#billamountTaxamount').val(billamountTaxamount);
	$('#dueamount').val(dueamount);
	$('#payCalcamount').val((dayMoney*day).toFixed(6));
}
function creatTable(){
	if(item.lstDatbase != null){
		list = item.lstDatbase;
		billamountTaxratio = getBillamountTaxratio();
		var allMoney = actMoney / list.length;
		var billamountTaxamount = allMoney * billamountTaxratio;
		var trueMoney = allMoney - billamountTaxamount;
		var money = trueMoney + billamountTaxamount;
		$('#DBRtb td').remove();//删除之前的数据
		var s = '';
		for (var i = 0; i < list.length; i++) {
			s = "<tr style='text-align: center;'><td>"
					+ "<input id='' name='lstDatbase["+i+"].baseresourceId' type='hidden' value='"+list[i].baseresourceId+ "'></input>"
					+ list[i].baseresourceCode
					+ "</td><td>"
					+ list[i].baseresourceName
					+ "</td><td><input readOnly='readOnly' id='dueamount"+i+"' name='lstDatbase["+i+"].rentPaymentdetailVO.dueamount' value='"+trueMoney.toFixed(6)+"'"
					+ "</input></td><td><input readOnly='readOnly' id='billamountTaxamount"+i+"' name='lstDatbase["+i+"].rentPaymentdetailVO.billamountTaxamount' value='"+billamountTaxamount.toFixed(6)+"'"
					+ "</input></td><td><input type='text' class='numclass' id='payamount"+i+"' name='lstDatbase["+i+"].rentPaymentdetailVO.payamount' value='"+money.toFixed(6)+"'"
					+ "</input></td><td>"
					+ "</td></tr>";
			$('#DBRtb').append(s);
		}
		eachPayMoney();
	}
}
function getBillamountTaxratio(){
	var billamountTaxratio = null;
	if($('#includeTax-hidden').val() == '0'){
		billamountTaxratio = 0;
	}else if($('#includeTax-hidden').val() == '1'){
		billamountTaxratio = $('#billamountTaxratio-hidden').val();
	}
	return billamountTaxratio;
}


function initSelect(){
	queryChargeType();
	//queryIncludeTax();
	queryBillType();
	queryBusinessType();
	queryAmountType();
	queryPaymentMethod();
	queryInvoiceType();
	queryTaxMethod();
	queryTaxDeduction();
}
//增值税抵扣
function queryTaxDeduction(){
	if(item.rentPayMentVO != null){
		$("#taxDeduction").val(item.rentPayMentVO.taxDeduction);
		id = item.rentPayMentVO.taxDeduction;
	}
	var msgg = "TAX_DEDUCTION";
	$.ajax({
		url : 'queryDictionaryByCode',   
		data : {
			dictgroup_code : msgg
		},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if (back != null) {
 				if(back.success=="1"){
 					var dictionary = back.Obj;
 					msg = dictionary;
					$('#taxDeduction').empty();//删除之前的数据
					var s = '';
					$("#taxDeduction").append("<option value='' selected='selected'>" +'--增值税抵扣--'+ "</option>");
					for (var i = 0; i < msg.length; i++) {
						if(id == msg[i].dict_value){
							$("#taxDeduction").append("<option selected = 'selected' value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}else{
							$("#taxDeduction").append("<option value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}
					}
 				}
 			}
		},
		error : function() {
			alertModel("请求异常");
		}
	})
}
//税金承担方式
function queryTaxMethod(){
	if(item.rentPayMentVO != null){
		$("#taxMethod").val(item.rentPayMentVO.taxMethod);
		id = item.rentPayMentVO.taxMethod;
	}
	var msgg = "TAX_METHOD";
	$.ajax({
		url : 'queryDictionaryByCode',   
		data : {
			dictgroup_code : msgg
		},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if (back != null) {
 				if(back.success=="1"){
 					var dictionary = back.Obj;
 					msg = dictionary;
					$('#taxMethod').empty();//删除之前的数据
					var s = '';
					$("#taxMethod").append("<option value='' selected='selected'>" +'--税金承担方式--'+ "</option>");
					for (var i = 0; i < msg.length; i++) {
						if(id == msg[i].dict_value){
							$("#taxMethod").append("<option selected = 'selected' value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}else{
							$("#taxMethod").append("<option value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}
					}
 				}
 			}
		},
		error : function() {
			alertModel("请求异常");
		}
	})
}
//票据类型
function queryInvoiceType(){
	if(item.rentPayMentVO != null){
		$("#invoiceType").val(item.rentPayMentVO.invoiceType);
		id = item.rentPayMentVO.invoiceType;
	}
	var msgg = "INVOICE_TYPE";
	$.ajax({
		url : 'queryDictionaryByCode',   
		data : {
			dictgroup_code : msgg
		},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if (back != null) {
 				if(back.success=="1"){
 					var dictionary = back.Obj;
 					msg = dictionary;
					$('#invoiceType').empty();//删除之前的数据
					var s = '';
					$("#invoiceType").append("<option value='' selected='selected'>" +'--票据类型--'+ "</option>");
					for (var i = 0; i < msg.length; i++) {
						if(id == msg[i].dict_value){
							$("#invoiceType").append("<option selected = 'selected' value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}else{
							$("#invoiceType").append("<option value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}
					}
 				}
 			}
		},
		error : function() {
			alertModel("请求异常");
		}
	})
}
//查询支付方式
function queryPaymentMethod(){
	if(item.rentPayMentVO != null){
		$("#paymentMethod").val(item.rentPayMentVO.paymentMethod);
		id = item.rentPayMentVO.paymentMethod;
	}
	var msgg = "PAYMENY_METHOD";
	$.ajax({
		url : 'queryDictionaryByCode',   
		data : {
			dictgroup_code : msgg
		},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if (back != null) {
 				if(back.success=="1"){
 					var dictionary = back.Obj;
 					msg = dictionary;
					$('#paymentMethod').empty();//删除之前的数据
					var s = '';
					$("#paymentMethod").append("<option value='' selected='selected'>" +'--支付方式--'+ "</option>");
					for (var i = 0; i < msg.length; i++) {
						if(id == msg[i].dict_value){
							$("#paymentMethod").append("<option selected = 'selected' value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}else{
							$("#paymentMethod").append("<option value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}
					}
 				}
 			}
		},
		error : function() {
			alertModel("请求异常");
		}
	})
}
//查询费用类型
function queryAmountType(){
	if(item.rentPayMentVO != null){
		$("#amountType").val(item.rentPayMentVO.amountType);
		id = item.rentPayMentVO.amountType;
	}
	var msgg = "AMOUNT_TYPE";
	$.ajax({
		url : 'queryDictionaryByCode',   
		data : {
			dictgroup_code : msgg
		},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if (back != null) {
 				if(back.success=="1"){
 					var dictionary = back.Obj;
 					msg = dictionary;
					$('#amountType').empty();//删除之前的数据
					var s = '';
					$("#amountType").append("<option value='' selected='selected'>" +'--费用类型--'+ "</option>");
					for (var i = 0; i < msg.length; i++) {
						if(id == msg[i].dict_value){
							$("#amountType").append("<option selected = 'selected' value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}else{
							$("#amountType").append("<option value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}
					}
 				}
 			}
		},
		error : function() {
			alertModel("请求异常");
		}
	})
}
//查询业务类型
function queryBusinessType(){
	if(item.rentPayMentVO != null){
		$("#businessType").val(item.rentPayMentVO.businessType);
		id = item.rentPayMentVO.businessType;
	}
	var msgg = "BUSINESS_TYPE";
	$.ajax({
		url : 'queryDictionaryByCode',   
		data : {
			dictgroup_code : msgg
		},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if (back != null) {
 				if(back.success=="1"){
 					var dictionary = back.Obj;
 					msg = dictionary;
					$('#businessType').empty();//删除之前的数据
					var s = '';
					$("#businessType").append("<option value='' selected='selected'>" +'--业务类型--'+ "</option>");
					for (var i = 0; i < msg.length; i++) {
						if(id == msg[i].dict_value){
							$("#businessType").append("<option selected = 'selected' value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}else{
							$("#businessType").append("<option value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}
					}
 				}
 			}
		},
		error : function() {
			alertModel("请求异常");
		}
	})
} 
//查询报账类型
function queryBillType(){
	if(item.rentPayMentVO != null){
		$("#billType").val(item.rentPayMentVO.billType);
		id = item.rentPayMentVO.billType;
	}
	var msgg = "BILL_TYPE";
	$.ajax({
		url : 'queryDictionaryByCode',   
		data : {
			dictgroup_code : msgg
		},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if (back != null) {
 				if(back.success=="1"){
 					var dictionary = back.Obj;
 					msg = dictionary;
					$('#billType').empty();//删除之前的数据
					var s = '';
					$("#billType").append("<option value='' selected='selected'>" +'--报账类型--'+ "</option>");
					for (var i = 0; i < msg.length; i++) {
						if(id == msg[i].dict_value){
							$("#billType").append("<option selected = 'selected' value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}else{
							$("#billType").append("<option value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}
					}
 				}
 			}
		},
		error : function() {
			alertModel("请求异常");
		}
	})
} 
/*//是否含税
function queryIncludeTax(){
	if(item.rentContractVO != null){
		$("#includeTax").val(item.rentContractVO.includeTax);
		id = item.rentContractVO.includeTax;
	}
	var msgg = "INCLUDE_TAX";
	$.ajax({
		url : 'queryDictionaryByCode',   
		data : {
			dictgroup_code : msgg
		},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if (back != null) {
 				if(back.success=="1"){
 					var dictionary = back.Obj;
 					msg = dictionary;
					$('#includeTax').empty();//删除之前的数据
					var s = '';
					for (var i = 0; i < msg.length; i++) {
						if(id == msg[i].dict_value){
							$("#includeTax").append("<option selected = 'selected' value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}else{
							$("#includeTax").append("<option value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}
					}
 				}
 			}
		},
		error : function() {
			alertModel("请求异常");
		}
	})
} */
//查询费用类别
function queryChargeType(){
	if(item.rentPayMentVO != null){
		$("#chargeType").val(item.rentPayMentVO.chargeType);
		id = item.rentPayMentVO.chargeType;
	}
	var msgg = "CHARGE_TYPE";
	$.ajax({
		url : 'queryDictionaryByCode',   
		data : {
			dictgroup_code : msgg
		},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if (back != null) {
 				if(back.success=="1"){
 					var dictionary = back.Obj;
 					msg = dictionary;
					$('#chargeType').empty();//删除之前的数据
					var s = '';
					$("#chargeType").append("<option value='' selected='selected'>" +'--费用类别--'+ "</option>");
					for (var i = 0; i < msg.length; i++) {
						if(id == msg[i].dict_value){
							$("#chargeType").append("<option selected = 'selected' value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}else{
							$("#chargeType").append("<option value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}
					}
 				}
 			}
		},
		error : function() {
			alertModel("请求异常");
		}
	})
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
	    url:'insertAllForm',
	    data: submitData,
	    dataType : 'json',
 		type : 'post',
		beforeSend:function(){//触发ajax请求开始时执行    
	        objButton1.val('请等待').attr('disabled',true);//改变提交按钮上的文字并将按钮设置为不可点击    
	    },  
	    cache:false,
	    async:true,
	    success:function(res){
	    	if(res != null){
	    		if(res.success == 1){
	    			alert(res.msg);
	    	    	window.location.href="insertBillAccount.html";
	    		}else{
	    			alert(res.msg);
	    		}
	    	}
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