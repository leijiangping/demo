
//初始化
$(function(){
	//初始化
	init();
	checkInfo();
	queryCurUser();
});

var billaccountPaymentInfo;

//查询报账点电费信息
function selectBillaccountPaymentInfo(){
	var billaccountpaymentdetailId = getParameter("billaccountpaymentdetailId");
	$.ajax({
	  type : "POST",
	  url : 'selectBillaccountPaymentInfoById',
	  data:{"billaccountpaymentdetailId":billaccountpaymentdetailId},
	  dataType : 'json',
	  async : false,
	  cache : false,
	  success : function(data) {
		  if(data != null){
			  billaccountPaymentInfo = data.obj[0];
		  }
	  }
	});
}

function init(){
	selectBillaccountPaymentInfo();
	
	$("#billaccountCode").text(billaccountPaymentInfo.billaccountCode);
	$("#billaccountName").text(billaccountPaymentInfo.billaccountName);

	$("#eleBillaccountPaymentdetailForm #billaccountId").val(billaccountId);
	
	$("#eleBillaccountPaymentdetailForm #billaccountpaymentdetailId").val(billaccountPaymentInfo.billaccountpaymentdetailId);
	$("#eleBillaccountPaymentdetailForm #billamountStartdate").val(billaccountPaymentInfo.billamountStartdate);
	$("#eleBillaccountPaymentdetailForm #billamountEnddate").val(billaccountPaymentInfo.billamountEnddate);
	$("#eleBillaccountPaymentdetailForm #billamountDate").val(billaccountPaymentInfo.billamountDate);
	$("#eleBillaccountPaymentdetailForm #buyMethod").text(billaccountPaymentInfo.buyMethod == 0 ? "后付费":billaccountPaymentInfo.buyMethod == 1?"预付费":"IC卡");
	$("#eleBillaccountPaymentdetailForm #paymentMethod").text(billaccountPaymentInfo.paymentMethod == 1 ? "转账" : "托收");
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
	$("#eleBillaccountPaymentdetailForm #billamountNotaxSys").text(billaccountPaymentInfo.billamountNotaxSys==null?"":billaccountPaymentInfo.billamountNotaxSys+" 元");
	$("#eleBillaccountPaymentdetailForm #billamountNotax").text(billaccountPaymentInfo.billamountNotax==null?"":billaccountPaymentInfo.billamountNotax+" 元");
	$("#eleBillaccountPaymentdetailForm #lossAmountSys").text(billaccountPaymentInfo.lossAmountSys==null?"":billaccountPaymentInfo.lossAmountSys+" 元" );
	$("#eleBillaccountPaymentdetailForm #billamountTaxamountSys").text( billaccountPaymentInfo.billamountTaxamountSys==null?"":billaccountPaymentInfo.billamountTaxamountSys+" 元");
	$("#eleBillaccountPaymentdetailForm #billamountTaxamount").text(billaccountPaymentInfo.billamountTaxamount==null?"":billaccountPaymentInfo.billamountTaxamount+" 元");
	$("#eleBillaccountPaymentdetailForm #lossAmount").text(billaccountPaymentInfo.lossAmount==null?"":billaccountPaymentInfo.lossAmount+" 元");
	$("#eleBillaccountPaymentdetailForm #otherAmount").text(billaccountPaymentInfo.otherAmount==null?"":billaccountPaymentInfo.otherAmount+" 元");
	$("#eleBillaccountPaymentdetailForm #billAmountSys").text(billaccountPaymentInfo.billAmountSys==null?"":billaccountPaymentInfo.billAmountSys+" 元");
	$("#eleBillaccountPaymentdetailForm #billAmountActual").text(billaccountPaymentInfo.billAmountActual==null?"":billaccountPaymentInfo.billAmountActual+" 元");
	$("#eleBillaccountPaymentdetailForm #totalDegree").text(billaccountPaymentInfo.totalDegree==null?"":billaccountPaymentInfo.totalDegree+" 度");
	$("#eleBillaccountPaymentdetailForm #calcMulti").text(billaccountPaymentInfo.calcMulti==null?"":billaccountPaymentInfo.calcMulti);
	$("#eleBillaccountPaymentdetailForm #totalDegreeActual").text(billaccountPaymentInfo.totalDegreeActual==null?"":billaccountPaymentInfo.totalDegreeActual +" 度");
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
					default:
						charegMethod = "";
					}
					$("#supplyMethod").text(charegMethod);
					$("#includeLoss").text(contract.includeLoss == 0 ? "否" : "是");
					$("#lossType").text(contract.lossType == 0 ? "按度数" : "按金额");
					$("#contractTotalAmount").text(contract.contractTotalAmount==null?"":contract.contractTotalAmount);
					
					$("#independentMeter").text(contract.independentMeter == 0 ? "否":"是");
					$("#cmccRatio").text(contract.cmccRatio==null?"":contract.cmccRatio);
					
					$("#includePriceTax").text(contract.includePriceTax == 0 ? "否":"是");
					$("#supplierCode").text(contract.supplierCode);
					$("#supplierName").text(contract.supplierName);
					$("#supplierAddress").text(contract.supplierAddress);
					 selectFileList(billaccountPaymentInfo);//查询合同附件列表
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
/*						if ($(this).find("#tax").val() == null || $(this).find("#tax").val() == ''){
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
	
	 
}
function selectFileList(billaccountPaymentInfo){
	var billaccountpaymentdetailId = getParameter("billaccountpaymentdetailId");
	if(billaccountpaymentdetailId==null||billaccountpaymentdetailId==""){
		return false;
	}
	$.ajax({
		url : 'selectByBusiness',// 跳转到 action
		data : {
			businessId:billaccountpaymentdetailId,
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

/**
 * 显示标杆信息
 */
function showBenchmark(){
	var billaccountId = billaccountPaymentInfo.billaccountId;
	var billamountStartdate = billaccountPaymentInfo.billamountStartdate;
	var billamountEnddate = billaccountPaymentInfo.billamountEnddate;
	var totalDegreeActual ="";
	if(totalDegreeActual!=null){
		totalDegreeActual = billaccountPaymentInfo.totalDegreeActual;
	}
	$.post("getBenchmark",{"billaccountId":billaccountId , "billamountStartdate":billamountStartdate,"billamountEnddate":billamountEnddate,"totalDegreeActual":totalDegreeActual},function(data){
		if(data.success == 0){
			alert(data.msg);
		}else{
			$("#tb").html(data.Obj);
			var benchmarkList=$(".benchmark");
			for(i=1;i<benchmarkList.length;i++){//取最小标杆值
				var v=""+benchmarkList[i].firstChild.data;
				if(v!="-"){//"-"超标率不存在
					var mb=v.replace("%","");
					if(mb>=0){//有值
						if(minBenchmark=="666666"){//最小标杆没有值时赋值
							minBenchmark=mb;
						}else if(minBenchmark>mb){//如果最小标杆值minBenchmark小于mb值则赋值
							minBenchmark=mb;
						}
					}
				}
			}


			/**
			 * 增加获取下级审核人的参数
			 */

			var billaccountpaymentdetailId=getParameter("billaccountpaymentdetailId");
			var regId=getParameter("regId");
			var elName='{"state":"0","minBenchmark":"'+minBenchmark+'"}';
			var elNameobj = eval('(' + elName + ')');
			//下级审核人
			findUsersByRoleIds(EleBillaccountPaymentdetail.tableName,billaccountpaymentdetailId,regId,elNameobj);

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
	  $("#comment").attr("readonly",false);
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

	window.location.href='auditing.html';
}

function changeSave(){
	if($("#nextUsers option").length > 1 && $("#checkState option:selected").val()=='0'){
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
var minBenchmark="666666";
function saveInfo(){
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
			"minBenchmark":minBenchmark
    };
	billAccountIds.push(obj);
	$("#saveSet").attr("disabled",true);
	 $.ajax({
		  type : "POST",
		  url : 'reviewBillAccountAudit',
		  data:JSON.stringify(billAccountIds),
		  dataType : 'json',
		  contentType : 'application/json;charset=utf-8',
		  cache : false,
		  success : function(data) {
			  window.location="auditing.html";
			  $("#saveSet").attr("disabled",false);
		  }
	 });
}

function checkInfo(){
	var billaccountpaymentdetailId=getParameter("billaccountpaymentdetailId");
	var regId=getParameter("regId");
	histoicFlowList(EleBillaccountPaymentdetail.tableName,billaccountpaymentdetailId);
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