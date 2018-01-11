
var optype = 1;//1 新增,2 修改,3 续签,4 变更
var elecontractId = null;
var priceType = "";
var VEleContract = null;
//用于判断合同类型
var type = getParameter("type");
var contractId = getParameter("contractId");
	//用于判断用户代码
var contractExist = false;
var flagUpdateRent = localStorage.getItem("flagUpdateRent");
// 修改之前的合同编码
var oragionCode = localStorage.getItem("oragionCode");

$(document).ready(function() {
	initCurrentPage();
	$("#contractId").val(contractId);
});
var uploader;
function initCurrentPage(){
	$("#datetimepicker5").hide();
	getAddressUpdate();
	haschecked();
	elecontractId = localStorage.getItem("elecontractId");
	optype = localStorage.getItem("optype");
	if(optype == 1){
		if(type == 0){
			$("title").html("固化信息新增");
		}else{
			$("title").html("电费合同新增");
		}
		$("#contractState").val("0");
		$("#contractState").attr("disabled",true);
		elecontractId = "ELECONTRACT-"+new Date().toLocaleTimeString();
		queryPaymentperiodId();
	}else if(optype == 2){
		if(type == 0){
			$("title").html("固化信息修改");
		}else{
			$("title").html("电费合同修改");
		}
		$("#contractState").attr("disabled",true);
		queryInfo();
	}else if(optype == 3){
		if(type == 0){
			$("title").html("固化信息续签");
		}else{
			$("title").html("电费合同续签");
		}
		queryInfo();
	}else if(optype == 4){
		if(type == 0){
			$("title").html("固化信息变更");
		}else{
			$("title").html("电费合同变更");
		}
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
	showIncludePriceTax();
	blurF();
	selectFileList();//查询附件列表
	/**
	 * 表单table文件上传
	 * */
	var callback=function uploadSuccessBack(file,json){
		if(json.success == 1){
			alertModel("上传成功");
			selectFileList();//上传成功重新查询附件列表
		}else{
			alertModel(msg);
		}	
	}
	uploader = webUploadInit("eleContractId","合同附件","uploadFile",callback);
}
//显示红星提示
function showstar(){
	$('#includeLoss').change(function(){
		var curVal=$("#includeLoss  option:selected").val();
		if(curVal==-1){
			$('#lossType').attr('disabled',true);
			$('#lossType').find('.star').hide();
			
		}else if(curVal==0){
			$('#lossType').attr('disabled',true);
			$('#lossType').val("");
			$('#lossType').parents('li').find('.star').hide();
		}else if(curVal==1){
			$('#lossType').parents('li').find('.star').show();
			$('#lossType').attr('disabled',false);
		}
	});
}
function haschecked(){
	
	var currentflag=$("#isIncludeAll  option:selected").val();
	if(currentflag==0){//否
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
		/*$("#paySign").attr("disabled", selectedVal);*/
		$("input[name='contractMoney']").val("");
		$("input[name='contractTax']").val("");
		$("input[name='contractTotalAmount']").val("");
	}else if(currentflag==1){//是
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
		$("#independentMeter").val("");
		$("#cmccRatio").val("");//移动分摊比例
		$("#unicomRatio").val("");//联通分摊比例
		$("#telcomRatio").val("");//电信分摊比例
		$("#priceType").val(""); //非包干单价类型
		$("#elecontractPrice").html("");
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
	if(priceType == "1"){
		var priceArr = price.split("|");
		$("#elecontractPrice").html("");
		$(".list-group #flatPrice").html(priceArr[0]);
		$(".list-group #peakPrice").html(priceArr[1]);
		$(".list-group #valleyPrice").html(priceArr[2]);
		$(".list-group #TopPrice").html(priceArr[3]);
	}else{
		$(".list-group #flatPrice").html("");
		$(".list-group #peakPrice").html("");
		$(".list-group #valleyPrice").html("");
		$(".list-group #TopPrice").html("");
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
	default : ""
		break;
}
	$("#supplierCode").html(supplier[0].supplierCode);
	$("#supplierName").html(supplier[0].supplierName);
	$("#supplierSite").html(supplier[0].supplierSite);
	$("#supplierAddress").html(supplier[0].supplierAddress);
	$("#supplierContact").html(supplier[0].supplierContact);
	$("#supplierTelephone").html(supplier[0].supplierTelephone);
	$("#sPregName").html(supplier[0].pregName);
	$("#sRegName").html(supplier[0].regName);
	$("#sPregId").val(supplier[0].pregId);
	$("#sRegId").val(supplier[0].regId);
	$("#sIsDownshare").html(isDownshare);
	$("#accountType").html(fmtAccountType(supplier[0].accountType));
	$("#depositBank").html(supplier[0].depositBank);
	$("#bankAccount").html(supplier[0].bankAccount);
	$("#bankUser").html(supplier[0].bankUser);
	$("#supplierType").html(fmtSupplierType(supplier[0].supplierType));
	$("#supplierNote").html(supplier[0].supplierNote);
	$("#supplierId").val(supplier[0].supplierId);
}
function selectFileList(){
	var contractId=$("#contractId").val();
	if(contractId==null||contractId==""){
		return false;
	}
	$("#attachFile").html("");
	$.ajax({
		url : 'selectByBusiness',// 跳转到 action
		data : {
			businessId:contractId,
			businessType:"dat_contract"
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
						"<div class='col-md-4'><a href='"+projectName+"/asserts/tpl/common/webupload/downLoad?path="+v.attachmentUrl+"' >"+v.attachmentName+"</a></div>" +
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
	var obj={"contractId":$("#contractId").val()};
	webUploadClick("eleContractId",uploader,obj);
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
				selectFileList();
			}
			alertModel(back.msg);
		},
		error : function(back){
			alertModel(back.msg);
		}
	});
}

/**
 * 保存合同 提交form表单
 */
function formSubmitContract(){
	if(optype == 1 || optype == 3){
		checkContractCode();
		if(contractExist){
			return;
		}
	}
	var newContractCode = $("#contractCode").val();
	if(optype == 2){
		if(oldContractCode != newContractCode){
			checkContractCode();
			if(contractExist){
				return;
			}
		}
	}
	
	if(!checkPaymentperiod()){
		return;
	}
	if(!checkContractEndDate(0)){
		return;
	}
	if(!checkSupplierId()){
		return;
	}
	
	if($("#isIncludeAll").val()=="-1" ){
		alertModel("请选择是否包干!");
		return;
	}
	if($("#regId").val()=="" && $("#isDownshare").val()=="0"){
		alertModel("请选择地市或区县信息或向下共享选择‘是’!");
		return;
	}
	
	if($("#taxRate").val() == ""){
		alertModel("请填写电费税率，如不含税请填写0！");
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
	if($("#includePriceTax").val()==""){
		alertModel("请选择单价是否含税!");
		return;
	}
	
	if ($("#isIncludeAll  option:selected").val() ==0) {//否
		if ($("#independentMeter").val() == "") {
			alertModel("请选择是否独立挂表!");
			return;
		}
		if ($("#cmccRatio").val() == "") {
			alertModel("移动分摊比例不能为空!");
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
	} else if($("#isIncludeAll").val() == 1){//是
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
		if($("#flatPrice").html() == "" && $("#peakPrice").html() == "" && $("#valleyPrice").html() == "" && $("#TopPrice").html() == ""){
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
	
	//区县信息都选择时  必须相等
	if($("#regId").val() != "" && $("#sRegId").val() != "" && $("#regId").val() != $("#sRegId").val()){
		if(type == 0){
			alertModel("固化信息区县信息与供应商区县信息选择不符合要求，请重新选择!");
		}else{
			alertModel("合同区县信息与供应商区县信息选择不符合要求，请重新选择!");
		}
		return;
	}
	//地市信息都选择时  必须相等
	if($("#pregId").val() != "" && $("#sPregId").val() != "" && $("#pregId").val() != $("#sPregId").val()){
		if(type == 0){
			alertModel("固化信息地市信息与供应商地市信息选择不符合要求，请重新选择!");
		}else{
			alertModel("合同地市信息与供应商地市信息选择不符合要求，请重新选择!");
		}
		return;
	}
	
	$("#isDownshare").removeAttr("disabled");
	var data = $('#dataForm').serializeObject();
	if(optype == 1){
		data.contractId = contractId;
	}else{
		data.contractId = $("#contractId").val();
	}
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
	data.topPrice = $(".list-group #TopPrice").html();
	data.auditingState =  $("#auditingState").val();
	data.auditingUserId =  $("#auditingUserId").val();
	data.auditingDate =  $("#auditingDate").val();
	delete data.enddate;
	data["supplierId"] = $("#supplierId").val();
	var submitData = JSON.stringify(data);
	if(optype == 1){
		$("#saveSet").attr("disabled",true);
		$.post("save" , {"param":submitData} ,function(result){
			// 请求成功时
			if (result != null) {
				alertModel(result.msg);
	    		if(result.success == "1"){
	    			alert('保存成功');
	    			window.location.href="managenewCuring.html";
	    			/*if(flagUpdateRent == 123){
	    				window.location.href="managenew.html";
	    			}else{
	    				window.location.href="manage.html";
	    			}*/
	    			localStorage.removeItem("optype");
	    		}
			}
			$("#saveSet").attr("disabled",false);
		});
	}else if(optype == 2){
		$("#saveSet").attr("disabled",true);
		$.post("modify" , {"param":submitData} ,function(result){
			// 请求成功时
			if (result != null) {
				alertModel(result.msg);
	    		if(result.success == "1"){
	    			alertModel('保存成功',sss);
	    			localStorage.removeItem("optype");
	    		}
			}
			$("#saveSet").attr("disabled",false);
		});
	}else{
		$("#saveSet").attr("disabled",true);
		$.post("modify" , {"param":submitData} ,function(result){
			// 请求成功时
			if (result != null) {
				alertModel(result.msg);
	    		if(result.success == "1"){
	    			alert('保存成功');
	    			if(flagUpdateRent == 123){
	    				if(getParameter("type")==2){
		    				window.location.href="managenew.html";    					
	    				}else if(getParameter("type")==0){
	    					window.location.href="managenewCuring.html";
	    				}
	    				//window.location.href="managenew.html";
	    			}else{
	    				if(getParameter("type")==2){
		    				window.location.href="manage.html";    					
	    				}else if(getParameter("type")==0){
	    					window.location.href="manageCuring.html";
	    				}
	    				//window.location.href="manage.html";
	    			}
	    			localStorage.removeItem("optype");
	    		}
			}
			$("#saveSet").attr("disabled",false);
		});
	}
  
}
function sss(){
	if(flagUpdateRent == 123){
		if(getParameter("type")==2){
			window.location.href="managenew.html";    					
		}else if(getParameter("type")==0){
			window.location.href="managenewCuring.html";
		}
	}else{
		if(getParameter("type")==2){
			window.location.href="manage.html";    					
		}else if(getParameter("type")==0){
			window.location.href="manageCuring.html";
		}
		//window.location.href="manage.html";
	}
}

function back(){
	localStorage.removeItem("optype");
	javascript:history.back(-1);
}

//判断合同代码唯一性
function checkContractCode(){
	var contractCode = $("#contractCode").val();
	if($("#contractCode").val()==""){
		contractExist = true;
		if(type == 0){
			alertModel("固化信息编码不能为空!");
		}else{
			alertModel("合同编码不能为空!");
		}
	}else{
		$.ajax({
			url : 'checkContractCode',
			data : {
				contractCode : contractCode
			},
			async : false,
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(back) {
				if (back != null) {
	 				if(back.success=="1"){
	 					var data = back.Obj;
	 					if(data != null && data.length > 0 ){
	 						if(optype == 2){
	 							if(oragionCode == data.contractCode){
	 								contractExist = false;
	 							}
	 							else {
	 								contractExist = true;
	 								if(type == 0){
	 									alertModel("固化信息编码已存在!");
	 								}else{
	 									alertModel("合同编码已存在!");
	 								}
	 							}
	 						} else {
	 							contractExist = true;
	 							if(type == 0){
 									alertModel("固化信息编码已存在!");
 								}else{
 									alertModel("合同编码已存在!");
 								}
	 						}
	 					}else{
	 						contractExist = false;
	 						if(type == 0){
									$("#codeMsg").html("固化信息编码可用！");
								}else{
									$("#codeMsg").html("合同编码可用！");
								}
	 						
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

//判断合同终期
function checkContractEndDate(type){
	if(type == 0 && ($("#contractEnddate").val() == "" || $("#contractStartdate").val() == "")){
		alertModel("期始或期终不能为空!");
	}else{
		var contractStartdate = $("#contractStartdate").val();
		var contractEnddate = $("#contractEnddate").val();
		var startDate = contractStartdate.split("-");
		var endDate = contractEnddate.split("-");
		var start=Date.UTC(startDate[0],startDate[1],startDate[2]);
		var end=Date.UTC(endDate[0],endDate[1],endDate[2]);
		if(start>end){
			alertModel("期终应大于期始!");
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
		alertModel("请选择缴费周期!");
		return false
	}
	return true;
}
//检查供应商
function checkSupplierId(){
	if($("#supplierId").val() == ""){
		alertModel("请选择供应商!");
		return false;
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
var oldContractCode;
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
		dataType : 'json',
		contentType : "application/x-www-form-urlencoded",
		success : function(back) {
			if(back != null){
				if(back.success == "1"){
					VEleContract = back.Obj.list[0];
					//合同信息
					$("#contractState").val(VEleContract.contractState);
					$("#contractCode").removeAttr("onblur");
					$("#contractCode").val(VEleContract.contractCode);
					oldContractCode = VEleContract.contractCode;
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
					$("#taxRate").attr("readonly",false);
					$("#taxRate").val(VEleContract.taxRate);
					if(VEleContract.includePriceTax != null && VEleContract.includePriceTax ==0){
						$("#taxRate").val("0");
						$("#taxRate").attr("readonly",true);
					}
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
					$("#TopPrice").html(VEleContract.topPrice);
					$("#includeLoss").val(VEleContract.includeLoss);
					$("#lossType").val(VEleContract.lossType);
					$("#elecontractNote").val(VEleContract.elecontractNote);
					$(".list-group #TopPrice").html(VEleContract.topPrice);
					
					//供应商信息
					$("#supplierCode").html(VEleContract.supplierCode);
					$("#supplierName").html(VEleContract.supplierName);
					$("#supplierSite").html(VEleContract.supplierSite);
					$("#supplierAddress").html(VEleContract.supplierAddress);
					$("#supplierContact").html(VEleContract.supplierContact);
					$("#supplierTelephone").html(VEleContract.supplierTelephone);
					$("#sPregName").html(VEleContract.sPregName);
					$("#sRegName").html(VEleContract.sRegName);
					$("#sPregId").html(VEleContract.sPregId);
					$("#sRegId").html(VEleContract.sRegId);
					$("#sIsDownshare").html(VEleContract.sIsDownshare == 1 ? "是" : "否");
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
					selectFileList(VEleContract.contractId);//查询合同附件列表
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
				checkIsDownShare();
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

//显示红星提示 电费税率
function showIncludePriceTax(){
	$('#includePriceTax').change(function(){
		var curVal=$("#includePriceTax  option:selected").val();
		if(curVal != null && curVal != ''){
			if(curVal==0){
				$('#taxRate').attr('readonly',true);
				$('#taxRate').val("0");
				$('#taxRate').parents('li').find('.star').hide();
			}else if(curVal==1){
				$('#taxRate').val("");
				$('#taxRate').parents('li').find('.star').show();
				$('#taxRate').attr('readonly',false);
			}
		}else{
			$('#taxRate').val("");
			$('#taxRate').parents('li').find('.star').show();
			$('#taxRate').attr('readonly',false);
		}
	});
}
