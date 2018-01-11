var calcflag = false;

//初始化
$(function(){
	//初始化
	init();
});
var paymentObj=null;
var uploader;
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
		  async:true,
		  success : function(data) {
			if(data.Obj != null){
				paymentObj=data.Obj.elePaymentdetail;
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
					
					//显示缴费起始，缴费期终，申请缴费日期
					if(data.Obj.elePaymentdetail != null){
						if(data.Obj.elePaymentdetail.billamountEnddate != null){
							var startDate = new Date(data.Obj.elePaymentdetail.billamountEnddate);
							$("#billamountStartdate").val(new Date(startDate.setDate(startDate.getDate()+1)).format("yyyy-MM-dd"));
							if(data.Obj.elePaymentdetail.planDate != null){
								$("#billamountEnddate").val(new Date(data.Obj.elePaymentdetail.planDate).format("yyyy-MM-dd"));
							}
						}
					}
					$("#billamountDate").val(new Date().format("yyyy-MM-dd"));				
					
					
					if(contract.includeLoss != null && contract.includeLoss == 0){
						$("#lossAmountSys").attr("disabled","disabled");
						$("#lossAmount").attr("disabled","disabled");
					}
					$("#lossType").val(contract.lossType);
					if(contract.contractTotalAmount!=null){
						$("#contractTotalAmount").text(filterNullValue(contract.contractTotalAmount)+" 元");
					}else{
						$("#contractTotalAmount").text("  元");
					}
					
					$("#independentMeter").val(contract.independentMeter);
//					$("#contractTotalAmount").text(contract.contractTotalAmount);
					if(contract.cmccRatio==null){
						$("#cmccRatio").text(" %");
					}else{
						
						$("#cmccRatio").text(parseFloat(filterNullValue(contract.cmccRatio))+" %");
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
				selectFileList();//查询附件列表
			  }else{
				  alert("没有关联合同");
				  return;
			  }
				//补充报账点信息
				var billobj = data.Obj.billaccount;
				if(billobj != null){
					$("#calcMulti").val(billobj.calcMulti);
				}
				
				$("#myTab").html(data.Obj.li);
				$("#myTabContent").html(data.Obj.tabcontent);


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
		};
		uploader = webUploadInit("billaccountpaymentdetailId","合同附件","uploadFile",callback);
}
function selectFileList(){
	var billaccountId=$("#eleBillaccountPaymentdetailForm #billaccountId").val();
	if(billaccountId==null||billaccountId==""){
		return false;
	}
	$("#attachFile").html("");
	$.ajax({
		url : 'selectByBusiness',// 跳转到 action
		data : {
			businessId:billaccountId,
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
	var obj={"billaccountpaymentdetailId":$("#eleBillaccountPaymentdetailForm #billaccountId").val()};
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
				selectFileList();
			}
			alertModel(back.msg);
		},
		error : function(back){
			alertModel(back.msg);
		}
	});
}


function save(submitState){
	
	if(calcAmount()){	
	}else{
		alert("计算校验失败，");
		return;
	}
	
	//缴费申请日期
	var billamountDate = $("#billamountDate").val();
	if(billamountDate == '' || billamountDate == null){
		alertModel("缴费申请日期不能为空");
		return;
	}
	
	//是否包干
	var isIncludeAll = $("#isIncludeAll").val();
	//valid
	if(isIncludeAll==1){
		//包干  备注校验
		if(validIncludeAllNote()==false){
			return;
		}
	}else{
		//非包干  备注校验
		if(validYuzhi()==false){
			return;
		}
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
	$("#saveSet").attr("disabled",true);
	/*$.post("queryEleBillaccountPaymentdetailByBillaccountId" , billaccountdata ,function(data){
		if(data.success!=1){
			alertModel(data.msg);
			return ;
		}
	});*/
	var flag=false;
	 $.ajax({
		  type : "POST",
		  url : 'queryEleBillaccountPaymentdetailByBillaccountId',
		  data:billaccountdata,
		  dataType : 'json',
		  cache : false,
		  async:false,
		  success : function(data) {
			  if(data.success!=1){
					alertModel(data.msg);
					flag=true;
				}
		  }
	 });
	if(flag==true){
		$("#saveSet").attr("disabled",false);
		return false;
	}
	//由
	 $.ajax({
		  type : "POST",
		  url : 'saveOrUpdateBillaccountDetail',
		  data:billaccountdata,
		  dataType : 'json',
		  cache : false,
		  async:false,
		  success : function(data){
			if(data.success == 1){
				var isIncludeAll = $("#isIncludeAll").val();
				
				if(isIncludeAll==1){
					//包干  没有电表
					localStorage.removeItem("billaccountId");
					localStorage.removeItem("billaccountCode");
					localStorage.removeItem("billaccountName");
					window.location.href = "record.html";
				}else{
					$("form #billaccountpaymentdetailId").val(data.obj.billaccountpaymentdetailId);
					var billaccountpaymentdetailId=$("form #billaccountpaymentdetailId").val();
					var electricmeterArr = [];
					$("#myTabContent").find("form").each(function(){
						var electricmeterdata = $(this).serializeObject();
						
						var payamountTax = [];
						$(this).find("input[name='tax']").each(function(){
							if($(this).val()==null||$(this).val()==''){
								payamountTax.push(",");
							}else{
								payamountTax.push($(this).val());
							}
						});
						if(payamountTax.length > 0){
							electricmeterdata.payamountTax = payamountTax.join("|");
						}
						
						var useDegrees = [];
						$(this).find("input[name='useDegrees']").each(function(){
							if($(this).val()==null||$(this).val()==''){
								useDegrees.push(",");
							}else{
								useDegrees.push($(this).val());
							}
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
						debugger;
						var useDegreesPrice =[];
						$(this).find("input[name='elecPrice']").each(function(){
							if($(this).val()==null||$(this).val()==''){
								useDegreesPrice.push(",");
							}else{
								useDegreesPrice.push($(this).val());
							}														
						});
						if(useDegreesPrice.length > 0){
							electricmeterdata.useDegreesPrice = useDegreesPrice.join("|");
						}
						
						var flatPrice =[];
						$(this).find("input[name='flatPrice']").each(function(){
							if($(this).val()==null||$(this).val()==''){
								flatPrice.push(",");
							}else{
								flatPrice.push($(this).val());
							}														
						});
						if(flatPrice.length > 0){
							electricmeterdata.flatPrice = flatPrice.join("|");
						}
						
						var peakPrice =[];
						$(this).find("input[name='peakPrice']").each(function(){
							if($(this).val()==null||$(this).val()==''){
								peakPrice.push(",");
							}else{
								peakPrice.push($(this).val());
							}														
						});
						if(peakPrice.length > 0){
							electricmeterdata.peakPrice = peakPrice.join("|");
						}
						
						var valleyPrice =[];
						$(this).find("input[name='valleyPrice']").each(function(){
							if($(this).val()==null||$(this).val()==''){
								valleyPrice.push(",");
							}else{
								valleyPrice.push($(this).val());
							}														
						});
						if(peakPrice.length > 0){
							electricmeterdata.valleyPrice = valleyPrice.join("|");
						}
						
						var topPrice =[];
						$(this).find("input[name='topPrice']").each(function(){
							if($(this).val()==null||$(this).val()==''){
								topPrice.push(",");
							}else{
								topPrice.push($(this).val());
							}														
						});
						if(topPrice.length > 0){
							electricmeterdata.topPrice = topPrice.join("|");
						}
						
						electricmeterArr.push(electricmeterdata);
					});
					var totalDegreeActual = $("#eleBillaccountPaymentdetailForm #totalDegreeActual").val();
					var billamountEnddate = $("#eleBillaccountPaymentdetailForm #billamountEnddate").val();
					var billamountStartdate = $("#eleBillaccountPaymentdetailForm #billamountStartdate").val();
					var billaccountId = $("#eleBillaccountPaymentdetailForm #billaccountId").val();
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
						  localStorage.removeItem("billaccountId");
							localStorage.removeItem("billaccountCode");
							localStorage.removeItem("billaccountName");
						window.location.href = "record.html";
					  }
					});
				}
			}else{
				alert(data.msg);
				window.location.href = "record.html";
			}
		$("#saveSet").attr("disabled",false);
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
	localStorage.removeItem("billaccountId");
	localStorage.removeItem("billaccountCode");
	localStorage.removeItem("billaccountName");
	window.location.href='record.html';
}