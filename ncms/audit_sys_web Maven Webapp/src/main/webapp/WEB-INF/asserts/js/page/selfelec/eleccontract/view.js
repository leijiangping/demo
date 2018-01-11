//已显示表格list
var showTableList = null;
$(document).ready(function() {
	initCurrentPage();
});

var VEleContract;

/**
 * add by jiacy
 * 查看用户信息
 */
function initCurrentPage() {
	var elecContractId = getParameter("elecContractId");
	$.ajax({
		url : 'queryOne',
		data : {
			elecontractId : elecContractId
		},
		type : 'get',
		dataType : 'json',
		success : function(list) {
			if(list == null){
				alertModel("数据无法获取!");
				return false;
			}
			var item = list.Obj;
			VEleContract = item;
			switch(item.contractType){
    		case 0:
    			contractType= '房租';
    			break;
    		case 1:
    			contractType= '电费';
    			break;
    		default : "综合";
			}
			switch(item.chargeType){
			case 0:
				chargeType = "租金";
				break;
			case 1:
				chargeType = "电费";
				break;
			default:
				chargeType ="";
				break;
			}
			switch(item.paymentMethod){
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
			switch(item.contractState){
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
			switch(item.supplierType){
			case 1:
				supplierType = "业主";
				break;
			case 2:
				supplierType = "供电局";
				break;
			case 3:
				supplierType = "经营性第三方公司";
				break;
			case 4:
				supplierType = "代收性第三方公司";
				break;
			default:
				supplierType ="";
				break;
			}
			 switch(item.supplyMethod){
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
			 switch(item.paySign){
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
			$("#contractState").val(contractState);
			$("input[name='contractChangeenddate']").val(item.contractChangeenddate);
			$("input[name='contractCode']").val(item.contractCode);
			$("input[name='contractName']").val(item.contractName);
			$("input[name='contractStartdate']").val(item.contractStartdate != null ?new Date(item.contractStartdate).format("yyyy-MM-dd") : "");
			$("input[name='contractEnddate']").val(item.contractEnddate != null ?new Date(item.contractEnddate).format("yyyy-MM-dd") : "");
			$("input[name='contractSigndate']").val(item.contractSigndate != null ? new Date(item.contractSigndate).format("yyyy-MM-dd") : "");
			$("input[name='contractChangedate']").val(item.contractChangedate != null ? new Date(item.contractChangedate).format("yyyy-MM-dd") : "");
			$("input[name='pregId']").val(item.pregName);
			$("input[name='regId']").val(item.regName);
			$("input[name='isDownshare']").val(item.isDownshare == 1 ? "是" : "否");
			$("input[name='contractCheckname1']").val(item.contractCheckname1);
			$("input[name='contractCheckname2']").val(item.contractCheckname2);
			$("input[name='userId']").val(item.userId);
			$("input[name='sysDepId']").val(item.sysDepId);
			$("input[name='oldContractCode']").val(item.oldContractCode);
			$("input[name='contractFlow']").val(item.contractFlow);
			$("input[name='contractIntroduction']").val(item.contractIntroduction);
			$("input[name='contractNote']").val(item.contractNote);
			$("input[name='elecontractId']").val(item.elecontractId);
			$("input[name='taxRate']").val(item.taxRate == null ? "" : item.taxRate+"%");
			$("input[name='supplyMethod']").val(supplyMethod);
			queryPaymentperiodId();
			$("input[name='isIncludeAll']").val(item.isIncludeAll == 1 ? "是" : "否" );
            switch(item.buyMethod){
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
			$("input[name='buyMethod']").val(buyMethod);
			$("input[name='paymentMethod']").val(paymentMethod);
			$("input[name='contractMoney']").val(item.contractMoney);
			$("input[name='contractTax']").val(item.contractTax);
			$("input[name='contractTotalAmount']").val(item.contractTotalAmount);
			$("input[name='paySign']").val(paySign);
			$("input[name='paySignAccount']").val(item.paySignAccount);
			$("input[name='independentMeter']").val(item.independentMeter == 1 ? "是" : "否" );
			$("input[name='cmccRatio']").val(item.cmccRatio == null ? "" : item.cmccRatio +"%");
			$("input[name='unicomRatio']").val(item.unicomRatio == null ? "" : item.unicomRatio +"%");
			$("input[name='telcomRatio']").val(item.telcomRatio == null ? "" : item.telcomRatio +"%");
			 switch(item.priceType){
				case 0:
					priceType = "非平峰谷单价";
					break;	
				case 1:
					priceType = "平峰谷单价";
					break;
				default:
					priceType ="";
					break;
				}
			$("input[name='priceType']").val(priceType);
			$("input[name='includePriceTax']").val(item.includePriceTax  == 1 ? "是" : "否");
			$("input[name='elecontractPrice']").val(item.elecontractPrice);
			$("input[name='flatPrice']").val(item.flatPrice);
			$("input[name='topPrice']").val(item.topPrice);
			$("input[name='peakPrice']").val(item.peakPrice);
			$("input[name='valleyPrice']").val(item.valleyPrice);
			$("input[name='TopPrice']").val(item.TopPrice);
			$("input[name='includeLoss']").val(item.includeLoss == 1 ? "是" : "否");
			switch(item.lossType){
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
			$("input[name='lossType']").val(lossType);
			$("input[name='elecontractNote']").val(item.elecontractNote);
			$("input[name='supplierName']").val(item.supplierName);
			$("input[name='supplierSite']").val(item.supplierSite);
			$("input[name='supplierAddress']").val(item.supplierAddress);
			$("input[name='supplierContact']").val(item.supplierContact);
			$("input[name='supplierTelephone']").val(item.supplierTelephone);
			$("#sRegId").val(item.sRegId);
			$("#sPregId").val(item.sPregId);
			$("#sPregName").val(item.sPregName);
			$("#sRegName").val(item.sRegName);
			$("#sIsDownshare").val(item.sIsDownshare  == 1 ? "是" : "否");
			$("#supplierSite").val(item.supplierSite);
			switch(item.accountType){
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
			$("input[name='accountType']").val(accountType);
			$("input[name='depositBank']").val(item.depositBank);
			$("input[name='bankAccount']").val(item.bankAccount);
			$("input[name='bankUser']").val(item.bankUser);
			$("input[name='supplierType']").val(supplierType);
			$("input[name='supplierNote']").val(item.supplierNote);
			$("#ViewPanel input").attr("readonly","true");
			histoicFlowList(SelfElecAudit.tableName,VEleContract.contractId);
			selectFileList(VEleContract.contractId);//查询合同附件列表
		},
		error : function() {
			alertModel("请求异常");
		}
	});

}

function goBack(){
	javascript:history.back(-1);
}

//获取地址参数
function getParam(paramName) {  
    paramValue = "",
    isFound = !1;  
    if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) {  
        arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0;  
        while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 &&
        arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() &&
        (paramValue = arrSource[i].split("=")[1], isFound = !0), i++  
    }  
    return paramValue == "" && (paramValue = null), paramValue  
}

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
					for (var i = 0; i < msg.length; i++) {
						if(id == msg[i].paymentperiodId){
							$("#paymentperiodId").val(msg[i].paymentperiodName);
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