$(document).ready(function() {
	initCurrentPage();
});
var contractId=getParameter("contractId");
var regId=getParameter("regId");
var maxElePrice=0;
function initCurrentPage(){
	queryCurUser();
	//流转记录
	histoicFlowList(SelfElecAudit.tableName,contractId);
	queryContractInfo();
}
function queryCurUser(){
	$.ajax({
		url:projectName+'/asserts/tpl/getCurrUser',
		data:{
			conutn:1
		},
		type:'get',
		dataType:'json',
		success:function(back){
			if (back != null) {
				item=back.obj;
				if(back.success=="1"){
					$("#curUser").text(item.user_loginname);
				}
			}
		},
		error:function(){
			alertModel("请求异常");
		}
	})
}
function changeSave(){
	if($("#nextUsers option").length > 1 && $("#auditState option:selected").val()=='0'){
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
	if($("#auditState option:selected").val()==8){
		if($("#auditNote").val()==""){
			$("#auditNote").next("#err").html("<img src=\"../../../image/error.png\"/>必填！");
			$("#auditNote").next("#err").css({"color":"red"});
			flag=true;
		}else{
			$("#auditNote").next("#err").html("<img src=\"../../../image/right_1.png\"/>验证成功");
			$("#auditNote").next("#err").css({"color":"green"});
			flag=false;
		}		
	}
}

$('#auditState').change(function(){
	if($("#auditState option:selected").val()==8){
		$('#nextUsers').empty();
		$('#nextUsers').append("<option>无</option>");
	}else{
		$('#nextUsers').empty();
		/**
		 * 增加获取下级审核人的参数
		 */
		var elName='{"state":"0","maxPrice":'+maxElePrice+'}';
		var elNameobj = eval('(' + elName + ')');
		//下级审核人
		findUsersByRoleIds(SelfElecAudit.tableName,contractId,regId,elNameobj);
	}
});

/**
 * 审核表单保存
 * */
function auditNewsFormSubmit(){
	changeSave();
	if(flag){
		return false;
	}	
	var auditState=$('#auditState option:selected').val();//审核状态
	var nextUserId=$('#nextUsers').val();//指定下级审核人
	var auditNote=$('#auditNote').val();//审核意见
	var taskId=getParameter("taskId");
	var regId=getParameter("regId");
	var ids = new Array();
	// 从选中行中挑出可以启用的item
	var obj={
    	"auditState":auditState,
    	"nextAuditUserId":nextUserId,
    	"auditNote":auditNote,
    	"taskId":taskId,
    	"id":getParameter("contractId"),
    	"regId":regId,
    	"maxPrice":maxElePrice //增加电费单价参数，根据单价不同判断走不同的流程
    };
	ids.push(obj);
	$("#saveSet").attr("disabled",true);
	$.ajax({
 		url : 'auditContract',
 		data : JSON.stringify(ids),
		type : 'post',
		cache : false,
		dataType : 'json',
		contentType: "application/json;charset=utf-8",
 		success : function(data) {
 			if (data != null) {
				alertModel(data.msg,backList);
			}
 			$("#saveSet").attr("disabled",false);
 		},
 		error : function() {
			alertModel("请求失败！");
			$("#saveSet").attr("disabled",false);
 		}
 	});
}
function backList(){
	if(VEleContract.dataFrom == 2){
		window.location.href='audit.html';
	}else{
		window.location.href='auditCuring.html';
	}
}
var VEleContract;
function queryContractInfo(){
	var Id = getParameter("contractId");
	$.ajax({
		url : 'list',// 跳转到 action
		data : {
			contractId : Id,
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
					switch(VEleContract.contractState){
					case -2:
						contractState = "无效";
						break;	
					case -1:
						contractState = "删除";
						break;
					case 0:
						contractState = "正常";
						break;	
					case 1:
						contractState = "起草";
						break;
					case 2: 
						contractState ="履行完毕";
						break;
					case 3:
						contractState ="审批中";
						break;
					case 4:
						contractState = "审批完毕";
						break;	
					case 8:
						contractState = "异动";
						break;
					case 9: 
						contractState ="未知";
						break;
					default:
						contractState ="-";
						break;
					}
					$("#contractState").html(contractState);
					$("#contractCode").html(VEleContract.contractCode);
					$("#contractName").html(VEleContract.contractName);
					$("#contractType").html(VEleContract.contractType);
					$("#contractStartdate").html(VEleContract.contractStartdate != null ?new Date(VEleContract.contractStartdate).format("yyyy-MM-dd") : "");
					$("#contractEnddate").html(VEleContract.contractEnddate != null ?new Date(VEleContract.contractEnddate).format("yyyy-MM-dd") : "");
					$("#contractSigndate").html(VEleContract.contractSigndate != null ?new Date(VEleContract.contractSigndate).format("yyyy-MM-dd") : "");
					$("#pregId").html(VEleContract.pregName);
					$("#regId").html(VEleContract.regName);
					$("#isDownshare").html(VEleContract.isDownshare == 1 ? "是" : "否");
					$("#contractCheckname1").html(VEleContract.contractCheckname1);
					$("#contractCheckname2").html(VEleContract.contractCheckname2);
					$("#contractYearquantity").html(VEleContract.contractYearquantity);
					$("#userId").html(VEleContract.userId);
					$("#sysDepId").html(VEleContract.sysDepId);
					$("#oldContractCode").html(VEleContract.oldContractCode);
					$("#contractFlow").html(VEleContract.contractFlow);
					$("#contractIntroduction").html(VEleContract.contractIntroduction);
					$("#contractNote").html(VEleContract.contractNote);
					$("#oldContractCode").html(VEleContract.contractCode);
					//电费信息
					$("#taxRate").html(VEleContract.taxRate+"%");
					switch(VEleContract.supplyMethod){
					case 1:
						supplyMethod = "直供电";
						break;	
					case 2:
						supplyMethod = "转供电";
						break;
					default:
						supplyMethod ="";
						break;
					}
					$("#supplyMethod").html(supplyMethod);
					$("#isIncludeAll").html(VEleContract.isIncludeAll  == 1 ? "是" : "否");
					 switch(VEleContract.buyMethod){
						case 2:
							buyMethod = "IC卡";
							break;	
						case 0:
							buyMethod = "后付费";
							break;
						case 1: 
							buyMethod ="预付费";
							break;
						default:
							buyMethod ="";
							break;
						}
					$("#buyMethod").html(buyMethod);
					switch(VEleContract.paymentMethod){
					case 2:
						paymentMethod = "托收";
						break;
					case 1:
						paymentMethod = "转账";
						break;
					default:
						paymentMethod ="";
						break;
					}
					$("#paymentMethod").html(paymentMethod);
					$("#contractMoney").html(VEleContract.contractMoney != null ? VEleContract.contractMoney + "  元" : "  元");
					$("#contractTax").html(VEleContract.contractTax != null ? VEleContract.contractTax + "  元" : "  元");
					$("#contractTotalAmount").html(VEleContract.contractTotalAmount != null ? VEleContract.contractTotalAmount + "  元" : "  元");
					//支付周期
					 switch(VEleContract.paySign){
						case 1:
							paySign = "日";
							break;	
						case 2:
							paySign = "月";
							break;
						case 3: 
							paySign ="年";
							break;
						default:
							paySign ="";
							break;
						}
					$("#paySign").html(paySign);
					//支付价格
					$("#paySignAccount").html(VEleContract.paySignAccount != null ? VEleContract.paySignAccount + "  元" : "  元");
					$("#independentMeter").html(VEleContract.independentMeter == 1 ? "是" : "否");
					$("#cmccRatio").html(VEleContract.cmccRatio != null ? VEleContract.cmccRatio + " %" : " %" );
					$("#unicomRatio").html(VEleContract.unicomRatio != null ? VEleContract.unicomRatio + " %"  : " %" );
					$("#telcomRatio").html(VEleContract.telcomRatio != null ? VEleContract.telcomRatio + " %"  : " %" );
					 switch(VEleContract.priceType){
						case 0:
							priceType = "非平峰谷单价";
							if(VEleContract.elecontractPrice != null){
								var elecontractPrice=VEleContract.elecontractPrice.split("|");//分割多个单价为数组
								for(p in elecontractPrice){
									if(elecontractPrice[p]>maxElePrice){//判断最大单价
										maxElePrice=elecontractPrice[p];
									}
								}
							}
							break;	
						case 1:
							priceType = "平峰谷单价";
							if(VEleContract.flatPrice != null&&VEleContract.flatPrice != ""&&VEleContract.flatPrice>maxElePrice){
								maxElePrice=VEleContract.flatPrice;
							}
							if(VEleContract.peakPrice != null&&VEleContract.peakPrice != ""&&VEleContract.peakPrice>maxElePrice){
								maxElePrice=VEleContract.peakPrice;
							}
							if(VEleContract.valleyPrice != null&&VEleContract.valleyPrice != ""&&VEleContract.valleyPrice>maxElePrice){
								maxElePrice=VEleContract.valleyPrice;
							}
							if(VEleContract.topPrice != null&&VEleContract.topPrice != ""&&VEleContract.topPrice>maxElePrice){
								maxElePrice=VEleContract.topPrice;
							}
							break;
						default:
							priceType ="";
							break;
						}
					$("#priceType").html(priceType);
					$("#includePriceTax").html(VEleContract.includePriceTax  == 1 ? "是" : "否");
					$("#elecontractPrice").html(VEleContract.elecontractPrice != null ? VEleContract.elecontractPrice + "  元" : "  元");
					$("#flatPrice").html(VEleContract.flatPrice != null ? VEleContract.flatPrice + "  元" : "  元");
					$("#peakPrice").html(VEleContract.peakPrice != null ? VEleContract.peakPrice + "  元" : "  元");
					$("#valleyPrice").html(VEleContract.valleyPrice != null ? VEleContract.valleyPrice + "  元" : "  元");
					$("#TopPrice").html(VEleContract.topPrice != null ? VEleContract.topPrice + "  元" : "  元");
					
					
					$("#includeLoss").html(VEleContract.includeLoss == 1 ? "是" : "否");
					switch(VEleContract.lossType){
					case 0:
						lossType = "按度数";
						break;	
					case 1:
						lossType = "按金额";
						break;
					default:
						lossType ="";
						break;	
					}
					$("#lossType").html(lossType);
					$("#elecontractNote").html(VEleContract.elecontractNote);
					switch(VEleContract.accountType){
					case 1:
						accountType = "私户";
						break;	
					case 0:
						accountType = "公户";
						break;
					default:
						accountType ="";
						break;
					}
					$("#accountType").html(accountType);
					//供应商信息
					$("#supplierCode").html(VEleContract.supplierCode);
					$("#supplierName").html(VEleContract.supplierName);
					$("#supplierSite").html(VEleContract.supplierSite);
					$("#supplierAddress").html(VEleContract.supplierAddress);
					$("#supplierContact").html(VEleContract.supplierContact);
					$("#supplierTelephone").html(VEleContract.supplierTelephone);
					$("#depositBank").html(VEleContract.depositBank);
					$("#bankAccount").html(VEleContract.bankAccount);
					$("#bankUser").html(VEleContract.bankUser);
					$("#supplierNote").html(VEleContract.supplierNote);
					$("#sPregName").html(VEleContract.sPregName);
					$("#sRegName").html(VEleContract.sRegName);
					$("#sIsDownshare").html(VEleContract.sIsDownshare  == 1 ? "是" : "否");
					$("#supplierSite").html(VEleContract.supplierSite);
					selectFileList(VEleContract.contractId);//查询合同附件列表
				}
			}

			
			queryPaymentperiodId();
			
			var elName='{"state":"0","maxPrice":'+maxElePrice+'}';
			var elNameobj = eval('(' + elName + ')');
			//下级审核人
			findUsersByRoleIds(SelfElecAudit.tableName,contractId,regId,elNameobj);
		},
		error : function(back) {
					
		}
	});
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
					$('#paymentperiodId').html("");//删除之前的数据
					var s = '';
					for (var i = 0; i < msg.length; i++) {
						if(id == msg[i].paymentperiodId){
							$("#paymentperiodId").html(msg[i].paymentperiodName);
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

//查询合同附件
function selectFileList(contractId){
	$.ajax({
		url : 'selectByBusiness',// 跳转到 action
		data : {
			businessId:contractId,
			businessType:"dat_contract"
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
						"<div class='col-md-4'><a href='"+projectName+"/asserts/tpl/common/webupload/downLoad?path="+v.attachmentUrl+"' >"+v.attachmentName+"</a></div>" +
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
