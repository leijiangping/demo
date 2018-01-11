//已显示表格list
var showTableList = null;
$(document).ready(function() {
	initCurrentPage();
});



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
			switch(item.contractType){
    		case 0:
    			contractType= '房租固化信息';
    			break;
    		case 1:
    			contractType= '电费固化信息';
    			break;
    		default : "综合固化信息";
			}
			switch(item.chargeType){
			case 0:
				chargeType = "租金";
				break;
			case 1:
				chargeType = "电费";
				break;
			}
			switch(item.paymentMethod){
			case 0:
				paymentMethod = "现金";
				break;
			case 1:
				paymentMethod = "转账";
				break;
			case 2: 
				paymentMethod ="网银";
				break;
			default:
				paymentMethod ="-";
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
				contractState = "固化信息异动";
				break;
			case 9: 
				contractState ="未知";
				break;
			default:
				contractState ="-";
				break;
			}
			switch(item.paymentperiodId){
			case '0':
				paymentperiodId = "按天";
				break;	
			case '1':
				paymentperiodId = "按月";
				break;
			case '2': 
				paymentperiodId ="按季度";
				break;
			case '3':
				paymentperiodId ="按年";
				break;
			default:
				paymentperiodId ="未知";
				break;
			}
			switch(item.supplierType){
			case 0:
				supplierType = "电费供应商";
				break;	
			case 1:
				supplierType = "房租供应商";
				break;
			default:
				supplierType ="未知";
				break;
			}
			 switch(item.supplyMethod){
				case 1:
					supplyMethod = "直供电";
					break;	
				case 2:
					supplyMethod = "转供电";
					break;
				case 3: 
					supplyMethod ="市电";
					break;
				case 4:
					supplyMethod ="太阳能";
					break;
				default:
					supplyMethod ="未知";
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
					paySign ="未知";
					break;
				}
			$("#contractState").val(contractState);
			$("input[name='contractChangeenddate']").val(item.contractChangeenddate);
			$("input[name='contractCode']").val(item.contractCode);
			$("input[name='contractName']").val(item.contractName);
			$("input[name='contractStartdate']").val(new Date(item.contractStartdate).format("yyyy-MM-dd"));
			$("input[name='contractEnddate']").val(new Date(item.contractEnddate).format("yyyy-MM-dd"));
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
			$("#paymentperiodId").val(paymentperiodId);
			$("input[name='isIncludeAll']").val(item.isIncludeAll == 1 ? "否" : "是" );
			$("input[name='buyMethod']").val(item.buyMethod == 1 ? "后付费" : "预付费");
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
			$("input[name='priceType']").val(item.priceType == 0 ? "非平峰谷单价" : "平峰谷单价");
			$("input[name='includePriceTax']").val(item.includePriceTax  == 1 ? "是" : "否");
			$("input[name='elecontractPrice']").val(item.elecontractPrice);
			$("input[name='flatPrice']").val(item.flatPrice);
			$("input[name='peakPrice']").val(item.peakPrice);
			$("input[name='valleyPrice']").val(item.valleyPrice);
			$("input[name='includeLoss']").val(item.includeLoss == 1 ? "是" : "否");
			$("input[name='lossType']").val(item.lossType == 0 ? "按度数" : "按金额");
			$("input[name='elecontractNote']").val(item.elecontractNote);
			$("input[name='supplierName']").val(item.supplierName);
			$("input[name='supplierSite']").val(item.supplierSite);
			$("input[name='supplierAddress']").val(item.supplierAddress);
			$("input[name='supplierContact']").val(item.supplierContact);
			$("input[name='supplierTelephone']").val(item.supplierTelephone);
			$("input[name='pregName']").val(item.pregName);
			$("input[name='regName']").val(item.regName);
			$("input[name='supplierIsDownshare']").val(item.supplierIsDownshare  == 1 ? "是" : "否");
			$("input[name='accountType']").val(item.accountType  == 1 ? "私户" : "公户");
			$("input[name='depositBank']").val(item.depositBank);
			$("input[name='bankAccount']").val(item.bankAccount);
			$("input[name='bankUser']").val(item.bankUser);
			$("input[name='supplierType']").val(supplierType);
			$("input[name='supplierNote']").val(item.supplierNote);
			$("#ViewPanel input").attr("readonly","true");
		},
		error : function() {
			alertModel("请求异常");
		}
	})
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