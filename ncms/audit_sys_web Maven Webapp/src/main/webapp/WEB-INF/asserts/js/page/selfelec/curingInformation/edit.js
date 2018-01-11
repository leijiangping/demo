var optype = 1;//1 新增,2 修改,3 续签,4 变更
var elecontractId = null;
var priceType = "";
var VEleContract = null;
	//用于判断用户代码
var contractExist = false;
var flagUpdateRent = localStorage.getItem("flagUpdateRent");
// 修改之前的固化信息编码
var oragionCode = localStorage.getItem("oragionCode");

$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	$("#datetimepicker5").hide();
	getAddressUpdate();
	
	elecontractId = localStorage.getItem("elecontractId");
	optype = localStorage.getItem("optype");
	if(optype == 1){
		$("title").html("电费固化信息新增");
		$("#contractState").val("0");
		$("#contractState").attr("disabled",true);
		elecontractId = "ELECONTRACT-"+new Date().toLocaleTimeString();
		queryPaymentperiodId();
	}else if(optype == 2){
		$("title").html("电费固化信息修改");
		$("#contractState").attr("disabled",true);
		queryInfo();
	}else if(optype == 3){
		$("title").html("电费固化信息续签");
		queryInfo();
	}else if(optype == 4){
		$("title").html("电费固化信息变更");
		$("#contractChangedate").val(getNowFormatDate());
		queryInfo();
	}
	//是否包干为否的时候相关选项需要置空
	$("#isIncludeAll").change( function() {
		var currentflag=$("#isIncludeAll  option:selected").val();
		var selectedVal = $(this).children('option:selected').val() == 0 ? true : false; 
//		$("input[name='contractMoney']").attr("readonly", selectedVal);
//		$("input[name='contractTax']").attr("readonly", selectedVal);
//		$("input[name='contractTotalAmount']").attr("readonly", selectedVal);
		//$("input[name='paySignAccount']").attr("readonly", selectedVal);
		$("#paySign").attr("disabled", selectedVal);
		$("input[name='contractMoney']").val("");
		$("input[name='contractTax']").val("");
		$("input[name='contractTotalAmount']").val("");

		haschecked();

		$("input[name='paySignAccount']").val("");
		
		/*$("input[name='cmccRatio']").attr("readonly", selectedVal ? false : true);
		$("input[name='unicomRatio']").attr("readonly", selectedVal ? false : true);
		$("input[name='telcomRatio']").attr("readonly", selectedVal ? false : true);*/
	});
	// 单价类型改变
	$("#paySign").change(function(value){
		$("input[name='paySignAccount']").val("");
	});
	// 是否有电损失
	$("#includeLoss").change(function(value){
		var selectedVal = $(this).children('option:selected').val() == 0 ? true : false;
		$("select[name='lossType']").attr("disabled",selectedVal);

	});
	showstar();
	blurF();
	
}
//显示红星提示
function showstar(){
	$('#includeLoss').change(function(){
		var curVal=$("#includeLoss  option:selected").val();
		if(curVal==-1){
			$('#lossType').attr('disabled',true);
			$('#lossType').find('.star').hide();
			
		}else if(curVal==0){
			$('#lossType').attr('disabled',false);
			$('#lossType').parents('li').find('.star').hide();
		}else if(curVal==1){
			
			$('#lossType').parents('li').find('.star').show();
			$('#lossType').attr('disabled',false);
		}
	})
}
function haschecked(){
	
	var currentflag=$("#isIncludeAll  option:selected").val();
	//alert(currentflag);
	if(currentflag==1){//否
		$("#contractMoney").attr("disabled",true);//包干价款
		$("#contractTax").attr("disabled",true);//包干税款
		$("#contractTotalAmount").attr("disabled",true);//包干总金额
		$("#paySign").attr("disabled",true); //包干支付单价
		$("#paySignAccount").attr("disabled",true);
		$("#independentMeter").attr("disabled",false);// 是否独立挂表
		$("#cmccRatio").attr("disabled",false);//移动分摊比例
		$("#unicomRatio").attr("disabled",false);//联通分摊比例
		$("#telcomRatio").attr("disabled",false);//电信分摊比例
		$("#priceType").attr("disabled",false); //非包干单价类型
		//$("#contractMoney").attr("disabled",true);//录入电费单价
		$("#includeLoss").attr("disabled",false);//是否有电损
		$("#lossType").attr("disabled",false);//电损计算方式
		
	}else if(currentflag==0){//是
		$("#contractMoney").attr("disabled",false);//包干价款
		$("#contractTax").attr("disabled",false);//包干税款
		$("#contractTotalAmount").attr("disabled",false);//包干总金额
		$("#paySign").attr("disabled",false); //包干支付单价
		$("#paySignAccount").attr("disabled",false);
		$("#independentMeter").attr("disabled",true);// 是否独立挂表
		$("#cmccRatio").attr("disabled",true);//移动分摊比例
		$("#unicomRatio").attr("disabled",true);//联通分摊比例
		$("#telcomRatio").attr("disabled",true);//电信分摊比例
		$("#priceType").attr("disabled",true); //非包干单价类型
		//$("#contractMoney").attr("disabled",true);//录入电费单价
		$("#includeLoss").attr("disabled",true);//是否有电损
		$("#lossType").attr("disabled",true);//电损计算方式
	}else if(currentflag==-1){
		$("#contractMoney").attr("disabled",true);//包干价款
		$("#contractTax").attr("disabled",true);//包干税款
		$("#contractTotalAmount").attr("disabled",true);//包干总金额
		$("#paySign").attr("disabled",true); //包干支付单价
		$("#independentMeter").attr("disabled",true);// 是否独立挂表
		$("#cmccRatio").attr("disabled",true);//移动分摊比例
		$("#unicomRatio").attr("disabled",true);//联通分摊比例
		$("#telcomRatio").attr("disabled",true);//电信分摊比例
		$("#priceType").attr("disabled",true); //非包干单价类型
		$("#paySignAccount").attr("disabled",true);
		//$("#contractMoney").attr("disabled",true);//录入电费单价
		$("#includeLoss").attr("disabled",true);//是否有电损
		$("#lossType").attr("disabled",true);//电损计算方式
	}
}

//弹出-电费单价
function showSetPrice(){
	priceType = $("#priceType").val();
	if(priceType == ""){
		return;
	}
	elecPriceBind(priceType);
}
//回调-录入电费单价
function backElecPrice(price){
	if(price == ""){
		return;
	}
	$("#elecontractPrice").html("");
	$("#flatPrice").html("");
	$("#peakPrice").html("");
	$("#valleyPrice").html("");
	$("#TopPrice").html("");
	if(priceType == "1"){
		var priceArr = price.split("|");
		$(".list-group #flatPrice").html(priceArr[0]);
		$(".list-group #peakPrice").html(priceArr[1]);
		$(".list-group #valleyPrice").html(priceArr[2]);
		$(".list-group #TopPrice").html(priceArr[3]);
		//alert($("#TopPrice").html())
	}else{
		$("#elecontractPrice").html(price);
	}
}
//回调-选择供应商
function backSupplierSelect(supplier){
	switch(supplier[0].isDownshare){
	case 0:
		isDownshare="否";
		break;
	case 1:
		isDownshare="是";
		break;
}
	$("#supplierCode").html(supplier[0].supplierCode);
	$("#supplierName").html(supplier[0].supplierName);
	$("#supplierSite").html(supplier[0].supplierSite);
	$("#supplierAddress").html(supplier[0].supplierAddress);
	$("#supplierContact").html(supplier[0].supplierContact);
	$("#supplierTelephone").html(supplier[0].supplierTelephone);
	$("#supplierpregName").html(supplier[0].pregName);
	$("#supplierregName").html(supplier[0].regName);
	$("#supplierIsDownshare").html(isDownshare);
	$("#accountType").html(fmtAccountType(supplier[0].accountType));
	$("#depositBank").html(supplier[0].depositBank);
	$("#bankAccount").html(supplier[0].bankAccount);
	$("#bankUser").html(supplier[0].bankUser);
	$("#supplierType").html(fmtSupplierType(supplier[0].supplierType));
	$("#supplierNote").html(supplier[0].supplierNote);
	$("#supplierId").val(supplier[0].supplierId);
}
//上传附件
function uploadFile(){
	var fileURL=$("#file_url").val();
	if(fileURL==null){
		alertModel("请选择上传文件!");
		return;
	}
	var formobj =  document.getElementById("dataForm");
	var formdata = new FormData(formobj);
	$.ajax({
		url : 'upload',// 跳转到 action
		type : 'post',
		data : formdata, 
		// 告诉jQuery不要去处理发送的数据
		processData : false, 
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		beforeSend:function(){
			console.log("正在进行，请稍候");
		},
		success : function(back) {
			if(back.success == 0){
				alertModel(back.msg);
				return;
			}
			var data = back.Obj;
			var file = "<li class='list-group-item row'>" +
					"<div class='col-md-2'></div>" +
					"<div class='col-md-4'>"+data[0].fileName+"</div>" +
					"<div class='col-md-4'>"+data[0].fileSize+"</div>" +
					"<div class='col-md-2'><a href='#' class='btn' onclick='removeFile(this)'>删除</a></div>" +
					"<input type='text' value='"+data[0].fileUrl+"' id='fileUrl' style='display: none;'/>" +
					"</li>";
			$("#attachFile").append(file);
		},
		error : function(back){
			alertModel(back.msg);
		}
	});
}
//删除附件
function removeFile(t){
	var l = $(t).parent().parent();
	var fileUrl = l.find('#fileUrl').val();
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
				l.remove();
			}
			alertModel(back.msg);
		},
		error : function(back){
			alertModel(back.msg);
		}
	});
}

/**
 * 保存固化信息 提交form表单
 */
function formSubmitContract(){
	//alert(validform().form())
	
	if(!checkPaymentperiod()){
		return;
	}
	if(!checkContractEndDate(0)){
		return;
	}
	if(!checkSupplierId()){
		return;
	}
	
	if($("#pregId").val()=="" || $("#regId").val()==""){
		alertModel("请选择地市或区县信息!");
		return;
	}
	
	if($("#pregId").val()=="" && $("#regId").val()=="" && $("#isDownshare").val()=="0"){
		alertModel("请选择地市或区县信息或向下共享选择‘是’!");
		return;
	}
	
	if($("#taxRate").val() == ""){
		alertModel("请填写电费税率!");
		return;
	}
	$("#share").val($("#isDowmShare").val());
	if($("#supplyMethod").val()==""){
		alertModel("请选择供电类型!");
		return;
	}
	if($("#buyMethod").val()==""){
		alertModel("请选择购电方式!");
		return;
	}
	if($("#paymentMethod").val()==""){
		alertModel("请选择支付方式!");
		return;
	}
	if($("#includePriceTax").val()==""){
		alertModel("请选择单价是否含税!");
		return;
	}
	
	// 包干与非包干
	/*if ($("#isIncludeAll").val() == 1) {//否
		if ($("#independentMeter").val() == "") {
			alertModel("请选择是否独立挂表!");
			return;
		}
		if ($("#cmccRatio").val() == "") {
			alertModel("请填写移动摊分比例!");
			return;
		}
		if ($("#unicomRatio").val() == "") {
			alertModel("请填写联通摊分比例!");
			return;
		}
		if ($("#telcomRatio").val() == "") {
			alertModel("请填写电信摊分比例!");
			return;
		}
		if ($("#priceType").val() == "") {
			alertModel("请选单价类型!");
			return;
		}
		if ($("#includePriceTax").val() == "") {
			alertModel("请选择单价类型是否含税!");
			return;
		}
		if ($("#includeLoss").val() == "") {
			alertModel("请选择是否有电损!");
			return;
		}
		
		if($("#elecontractPrice").html()=="" && $("#flatPrice").html()==""){
			alertModel("请填写单价!");
			return;
		}
	} else if($("#isIncludeAll").val() == 0){
		if ($("#contractMoney").val() == "") {
			alertModel("请填写包干价款!");
			return;
		}
		if ($("#contractTax").val() == "") {
			alertModel("请填写包干税款!");
			return;
		}
		if ($("#contractTotalAmount").val() == "") {
			alertModel("请填写包干总金额!");
			return;
		}
		if ($("#paySign").val() == "") {
			alertModel("请选择支付单价!");
			return;
		}
		if ($("#paySignAccount").val() == "") {
			alertModel("请填写支付单价!");
			return;
		}*/
	
	if ($("#isIncludeAll  option:selected").val() ==1) {//否
		if ($("#independentMeter").val() == "") {
			alertModel("请选择是否独立挂表!");
			return;
		}
		if ($("#cmccRatio").val() == "") {
			alertModel("请填写移动摊分比例!");
			return;
		}
		if ($("#unicomRatio").val() == "") {
			alertModel("请填写联通摊分比例!");
			return;
		}
		if ($("#telcomRatio").val() == "") {
			alertModel("请填写电信摊分比例!");
			return;
		}
		if ($("#priceType").val() == "") {
			alertModel("请选单价类型!");
			return;
		}
		if ($("#includePriceTax").val() == "") {
			alertModel("请选择单价类型是否含税!");
			return;
		}
		if ($("#includeLoss").val() == "") {
			alertModel("请选择是否有电损!");
			return;
		}
		
		if($("#elecontractPrice").html()=="" && $("#flatPrice").html()==""){
			alertModel("请填写单价!");
			return;
		}
	} else if($("#isIncludeAll").val() == 0){
		if ($("#contractMoney").val() == "") {
			alertModel("请填写包干价款!");
			return;
		}
		if ($("#contractTax").val() == "") {
			alertModel("请填写包干税款!");
			return;
		}
		if ($("#contractTotalAmount").val() == "") {
			alertModel("请填写包干总金额!");
			return;
		}
		if ($("#paySignAccount").val() == "") {
			alertModel("请填写包干单价!");
			return;
		}
		
		
	}
	
	// 单价类型
	if ($("#priceType").val() == 1) { //否
		if($("#flatPrice").html() == "" || $("#peakPrice").html() == "" || $("#valleyPrice").html() == ""){
			alertModel("请选录入平峰谷单价!");
			return;
		}
		
	}
	
	// 有电损
	if ($("#includeLoss").val() == 1) { 
		if ($("#lossType").val() == "") {
			alertModel("请选择电损计算方式!");
			return;
		}
	}
	
	if(optype == 1 || optype == 2 || optype == 3){
		checkContractCode();
		if(contractExist){
			return;
		}
	}
	$("#share").val($("#isDownshare").val());
	var data = $('#dataForm').serializeObject();
	data.contractId = $("#contractId").val();
	data.contractState =  $("#contractState").val();
	data.prvId = $("#prvId").val();
	data.prvSname = $("#prvSname").val();
	data.pregId = $("#pregId").val();
	data.pregName = $("#pregId").find("option:selected").text();
	data.regId = $("#regId").val();
	data.regName = $("#regId").find("option:selected").text();
	data.elecontractId =  $("#elecontractId").val();
	data.elecontractPrice = $("#elecontractPrice").html();
	data.flatPrice = $("#flatPrice").html();
	data.peakPrice = $("#peakPrice").html();
	data.valleyPrice = $("#valleyPrice").html();
	data.topPrice = $("#TopPrice").html();
	data.auditingState =  $("#auditingState").val();
	data.auditingUserId =  $("#auditingUserId").val();
	data.auditingDate =  $("#auditingDate").val();
	$("#isShare").val($("#isDownShare").val());
	delete data.enddate;
	data["supplierId"] = $("#supplierId").val();
	var submitData = JSON.stringify(data);
	if(optype == 1){
		$.post("save" , {"param":submitData} ,function(result){
			// 请求成功时
			if (result != null) {
				alertModel(result.msg);
	    		if(result.success == "1"){
	    			if(flagUpdateRent == 123){
	    				window.location.href="managenew.html";
	    			}else{
	    				window.location.href="manage.html";
	    			}
	    			localStorage.removeItem("optype");
	    		}
			}
		});
	}else if(optype == 2){
		$.post("modify" , {"param":submitData} ,function(result){
			// 请求成功时
			if (result != null) {
				alertModel(result.msg);
	    		if(result.success == "1"){
	    			if(flagUpdateRent == 123){
	    				window.location.href="managenew.html";
	    			}else{
	    				window.location.href="manage.html";
	    			}
	    			localStorage.removeItem("optype");
	    		}
			}
		});
	}else{
		$.post("modify" , {"param":submitData} ,function(result){
			// 请求成功时
			if (result != null) {
				alertModel(result.msg);
	    		if(result.success == "1"){
	    			if(flagUpdateRent == 123){
	    				window.location.href="managenew.html";
	    			}else{
	    				window.location.href="manage.html";
	    			}
	    			localStorage.removeItem("optype");
	    		}
			}
		});
	}
  
}

function back(){
	localStorage.removeItem("optype");
	javascript:history.back(-1);
}

//判断固化信息代码唯一性
function checkContractCode(){
	var contractCode = $("#contractCode").val();
	if($("#contractCode").val()==""){
		contractExist = true;
		alertModel("固化信息编码不能为空!");
	}else{
		$.ajax({
			url : 'checkContractCode',
			data : {
				contractCode : contractCode
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(back) {
				if (back != null) {
	 				if(back.success=="1"){
	 					var data = back.Obj;
	 					if(data != null ){
	 						if(optype == 2){
	 							if(oragionCode == data.contractCode){
	 								contractExist = false;
	 							}
	 							else {
	 								contractExist = true;
		 							alertModel("固化信息编码已存在!");
	 							}
	 						} else {
	 							contractExist = true;
	 							alertModel("固化信息编码已存在!");
	 						}
	 					}else{
	 						$("#codeMsg").html("固化信息编码可用！");
	 						$("#codeMsg").css("color","green");
	 						setTimeout(function(){
	 							$("#codeMsg").html("");
	 						},2000);
	 					}
	 				}
				}
			},
			error : function() {
				alertModel("请求异常");
			}
		});
	}
}

//判断固化信息终期
function checkContractEndDate(type){
	if(type == 0 && ($("#contractEnddate").val() == "" || $("#contractStartdate").val() == "")){
		alertModel("固化信息日期不能为空!");
	}else{
		var contractStartdate = $("#contractStartdate").val();
		var contractEnddate = $("#contractEnddate").val();
		var startDate = contractStartdate.split("-");
		var endDate = contractEnddate.split("-");
		var start=Date.UTC(startDate[0],startDate[1],startDate[2]);
		var end=Date.UTC(endDate[0],endDate[1],endDate[2]);
		if(start>end){
			alertModel("固化信息终期应大于固化信息始期!");
		}else{
			if(start != null && end != null){
				var year = end-start;
				$("#contractYearquantity").val((year/(1000 * 24 * 60 * 60 * 365)).toFixed(1));
			}
			return true;
		}
	}
	return false;
}

//判断缴费周期
function checkPaymentperiod(){
	if($("#paymentperiodId").val() == ""){
		alertModel("请选择固化信息缴费周期!");
		return false
	}
	return true;
}
//检查供应商
function checkSupplierId(){
	if($("#supplierId").val() == ""){
		alertModel("请选择供应商!");
		return fals;
	}
	return true;
}

//查询缴费周期
function queryPaymentperiodId(){
	var id = null;
	if(VEleContract != null){
		id = VEleContract.paymentperiodId;
	}
	var msg = "PAYMENTPERIOD";
	$.ajax({
		url : 'queryDictionaryByCode',   
		data : {
			dictgroup_code : msg
		},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if (back != null) {
				if(back.success=="1"){
					var dictionary = back.Obj;
					msg = dictionary;
					$('#paymentperiodId').empty();//删除之前的数据
					var s = '';
					$("#paymentperiodId").append("<option value='' selected='selected'>" +'--缴费周期--'+ "</option>");
					for (var i = 0; i < msg.length; i++) {
						if(id == msg[i].paymentperiodId){
							$("#paymentperiodId").append("<option selected = 'selected' value=" + msg[i].paymentperiodId + ">" + msg[i].paymentperiodName + "</option>");
						}else{
							$("#paymentperiodId").append("<option value=" + msg[i].paymentperiodId + ">" + msg[i].paymentperiodName + "</option>");
						}
					}
				}
			}
		},
		error : function() {
			alert("请求异常");
		}
	});
}

$('#contractEnddate').on('input propertychange',function(){
	$('#enddate').val($(this).val());
});

function queryInfo(){
	var Id = localStorage.getItem("elecontractId");
	$.ajax({
		url : 'list',// 跳转到 action
		data : {
			elecontractId : Id,
			cur_page_num : 1,    
			page_count : 1
			},
		type : 'get',
		contentType : "application/x-www-form-urlencoded",
		success : function(back) {
			if(back != null){
				if(back.success == "1"){
					VEleContract = back.Obj.list[0];
					console.log(VEleContract);
					//固化信息信息
					$("#contractState").val(VEleContract.contractState);
					$("#contractCode").removeAttr("onblur");
					$("#contractCode").val(VEleContract.contractCode);
					$("#contractName").val(VEleContract.contractName);
					$("#contractType").val(VEleContract.contractType);
					$("#contractStartdate").val(new Date(VEleContract.contractStartdate).format("yyyy-MM-dd"));
					$("#contractEnddate").val(new Date(VEleContract.contractEnddate).format("yyyy-MM-dd"));
					$("#contractSigndate").val(VEleContract.contractSigndate != null ?new Date(VEleContract.contractSigndate).format("yyyy-MM-dd") : "");
					$("#isDownshare").val(VEleContract.isDownshare);
					$("#contractCheckname1").val(VEleContract.contractCheckname1);
					$("#contractCheckname2").val(VEleContract.contractCheckname2);
					$("#contractYearquantity").val(VEleContract.contractYearquantity);
					$("#userId").val(VEleContract.userId);
					$("#sysDepId").val(VEleContract.sysDepId);
					$("#oldContractCode").val(VEleContract.oldContractCode);
					$("#contractFlow").val(VEleContract.contractFlow);
					$("#contractIntroduction").val(VEleContract.contractIntroduction);
					$("#contractNote").val(VEleContract.contractNote);
					if(optype == 3){
						$("#contractCode").val("");
						$("#oldContractCode").val(VEleContract.contractCode);
						
					}
					//电费信息
					$("#elecontractId").val(VEleContract.elecontractId);
					$("#taxRate").val(VEleContract.taxRate);
					$("#supplyMethod").val(VEleContract.supplyMethod);
					$("#paymentperiodId").val(VEleContract.paymentperiodId);
					$("#isIncludeAll").val(VEleContract.isIncludeAll);
					$("#buyMethod").val(VEleContract.buyMethod);
					$("#paymentMethod").val(VEleContract.paymentMethod);
					$("#contractMoney").val(VEleContract.contractMoney);
					$("#contractTax").val(VEleContract.contractTax);
					$("#contractTotalAmount").val(VEleContract.contractTotalAmount);
					$("#paySign").val(VEleContract.paySign);
					$("#paySignAccount").val(VEleContract.paySignAccount);
					$("#independentMeter").val(VEleContract.independentMeter);
					$("#cmccRatio").val(VEleContract.cmccRatio);
					$("#unicomRatio").val(VEleContract.unicomRatio);
					$("#telcomRatio").val(VEleContract.telcomRatio);
					$("#priceType").val(VEleContract.priceType);
					$("#includePriceTax").val(VEleContract.includePriceTax);
					$("#elecontractPrice").html(VEleContract.elecontractPrice);
					$("#flatPrice").html(VEleContract.flatPrice);
					$("#peakPrice").html(VEleContract.peakPrice);
					$("#valleyPrice").html(VEleContract.valleyPrice);
					$("#includeLoss").val(VEleContract.includeLoss);
					$("#lossType").val(VEleContract.lossType);
					$("#elecontractNote").val(VEleContract.elecontractNote);
					$("#TopPrice").val(VEleContract.topPrice);
					
					//供应商信息
					$("#supplierCode").html(VEleContract.supplierCode);
					$("#supplierName").html(VEleContract.supplierName);
					$("#supplierSite").html(VEleContract.supplierSite);
					$("#supplierAddress").html(VEleContract.supplierAddress);
					$("#supplierContact").html(VEleContract.supplierContact);
					$("#supplierTelephone").html(VEleContract.supplierTelephone);
					$("#pregName").html(VEleContract.pregName);
					$("#regName").html(VEleContract.regName);
					$("#supplierIsDownshare").html(VEleContract.isDownshare);
					$("#accountType").html(fmtAccountType(VEleContract.accountType));
					$("#depositBank").html(VEleContract.depositBank);
					$("#bankAccount").html(VEleContract.bankAccount);
					$("#bankUser").html(VEleContract.bankUser);
					$("#supplierType").html(fmtSupplierType(VEleContract.supplierType));
					$("#supplierNote").html(VEleContract.supplierNote);
					$("#supplierId").val(VEleContract.supplierId);
					//附件信息
					//隐藏ID
					$("#contractId").val(VEleContract.contractId);
					$("#prvId").val(VEleContract.prvId);
					$("#prvSname").val(VEleContract.prvSname);
					$("#auditingState").val(VEleContract.auditingState);
					$("#auditingUserId").val(VEleContract.auditingUserId);
					$("#auditingDate").val(VEleContract.auditingDate);
					$("#rentcontractId").val(VEleContract.rentcontractId);
					
				}
			}
			haschecked();
			showstar();
			getAddressUpdate(VEleContract.pregId,VEleContract.regId);
			queryPaymentperiodId();
		},
		error : function(back) {
					
		}
	});
}

function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
    return currentdate;
} 

function getNowFormatTime() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
} 

function getAddressUpdate(citySelect,villageSelect){
	$.ajax({
		type : "post",
		url :"../../common/region/getAddressElec",
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		success : function(value) {
			if(value != null){
				sysReguins = value.obj;
				if(sysReguins!=null){
					$('#pregId').empty();
					$('#regId').empty();
					var strCity = "<option value=''>--选择地市--</option>";
					$.each(sysReguins, function (i, item){
						strCity += "<option value='" +item.regId+"' "+(citySelect&&citySelect==item.regId?"selected='true'":"")+">"+item.regName+ "</option>";
					});

					$("#pregId").append(strCity);

					$("#regId").empty();
					var strReg = "<option value=''>--选择区县--</option>";
					if($("#pregId").val()!=""){
						$.each(sysReguins, function (i, item){
							if(item.regId==$("#pregId").val()){
								$.each(item.child, function (j, itemchild){
									strReg += "<option value='" + itemchild.regId+"' "+(villageSelect&&villageSelect==itemchild.regId?"selected='true'":"")+">"+itemchild.regName+ "</option>";
								});
							}
						});
					}
					$("#regId").append(strReg);
					//绑定联动事件 修改人zhujj
					$("#pregId").change(function(){
						$("#regId").empty();
						var strReg = "<option value=''>--选择区县--</option>";
						if($("#pregId").val()!=""){
							$.each(sysReguins, function (i, item){
								if(item.regId==$("#pregId").val()){
									$.each(item.child, function (j, itemchild){
										strReg += "<option value='" + itemchild.regId+"' "+(villageSelect&&villageSelect==itemchild.regId?"selected='true'":"")+">"+itemchild.regName+ "</option>";
									});
								}
							});
						}
						$("#regId").append(strReg);
					});
				}
			}
		},
		error : function(data) {
			alertModel('获取用户地区信息失败!');
		}
	});

}

//验证
function validform(){
	var public_validate= bindformvalidate("#dataForm", {
		priceType : {
			required : true,
			maxlength : 20,
		},
		supplyMethod:{
			required : true,
			maxlength : 20,
		},
	}, {
		priceType:{
			required : "请选择价格",
			
		},
		supplyMethod:{
			required : "请选择供电类型",
		},
	});
  return public_validate;
}

function public_close(){
	removeError('dataForm');

}

function checkIsDownShare(){
	var regId = $("#regId").val();
	if(regId == ""){
		$("#isDownshare").removeAttr("disabled");
	}else{
		$("#isDownshare").val(0);
		$("#isDownshare").attr("disabled",true);
	}

}