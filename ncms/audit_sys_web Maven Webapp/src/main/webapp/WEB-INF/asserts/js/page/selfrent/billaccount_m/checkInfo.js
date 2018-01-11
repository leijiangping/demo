
$(document).ready(function() {
	initCurrentPage();
});

function initCurrentPage(){
	queryCurUser();
	 payment();
}

function goBack(){
	localStorage.removeItem('billaccountId');
	localStorage.removeItem('item1');
	
	javascript:history.back(-1);
}

function payment(){
	var paymentId=getParameter("paymentId");
		$.ajax({
		url : 'queryContractPayment',// 跳转到 action
		data : {
			paymentId : paymentId
			},
		type : 'post',
		dataType : 'json',
		async:false,
		success : function(back) {
			if(back != null){
				if(back.success == "1"){
						item=back.obj;
					/*	*/
						$('#tb1 tr:gt(0)').remove();
						var s = '';
						for(var i=0;i<item.length;i++){
							switch(item[i].includeTax){
			        		case 0:
			        			includeTax= '否';
			        			break;
			        		case 1:
			        			includeTax= '是';
			        			break;
			        		default:
			        			includeTax ="/";
								break;
						}
						switch(item[i].taxMethod){
							case 0:
								taxMethod = "公摊";
								break;
							case 1: 
								taxMethod ="均摊";
								break;
							default:
								taxMethod ="/";
								break;
						}
						switch(item[i].businessType){
							case 0:
								businessType = "开启业务";
								break;
							case 1:
								businessType = "注销业务";
								break;
							default:
								businessType ="/";
								break;
						}
						switch(item[i].amountType){
							case '0':
								amountType = " 机房租赁  ";
								break;	
							case '1':
								amountType = " 基站租赁";
								break;
							case '2': 
								amountType ="其他网元租赁";
								break;
							default:
								amountType ="/";
								break;
						}
						switch(item[i].chargeType){
						case '0':
							chargeType = " 租金  ";
							break;	
						case '1':
							chargeType = " 电费";
							break;
						default:
							chargeType ="/";
							break;
					}
						switch(item[i].paymentMethod){
						case '0':
							paymentMethod = " 现金  ";
							break;	
						case '1':
							paymentMethod = " 转账";
							break;
						case '2': 
							paymentMethod ="网银";
							break;
						default:
							paymentMethod ="/";
							break;
					}
						switch(item[i].invoiceType){
						case '0':
							invoiceType = " 发票 ";
							break;	
						case '1':
							invoiceType = " 收据";
							break;
						default:
							invoiceType ="/";
							break;
					}
						switch(item[i].billType){
						case '0':
							billType = " 现金报账 ";
							break;	
						case '1':
							billType = " 银行卡报账";
							break;
						default:
							billType ="/";
							break;
					}
					
							
							$("#billaccountCode").text(item[i].billaccountCode);
							$("#billaccountName").text(item[i].billaccountName);
							$("#paymentDate").text(new Date(item[i].paymentDate).format("yyyy-MM-dd"));
							$("#contractCode").text(item[i].contractCode);
							$("#contractName").text(item[i].contractName);
							$("#payActamount").text(item[i].payActamount);
							$("#totalAmount").text(item[i].totalAmount);
							$("#includeTax").text(includeTax);
							$("#billamountTaxratio").text(item[i].billamountTaxratio);
							$("#paymentStartdate").text(new Date(item[i].paymentStartdate).format("yyyy-MM-dd"));
							$("#paymentEnddate").text(new Date(item[i].paymentEnddate).format("yyyy-MM-dd"));
							$("#contractYearquantity").text(item[i].contractYearquantity);
							$("#supplierCode").text(item[i].supplierCode);
							$("#supplierName").text(item[i].supplierName);
							$("#billType").text(billType);
							$("#businessType").text(businessType);
							$("#amountType").text(amountType);
							$("#chargeType").text(chargeType);
							$("#paymentMethod").text(paymentMethod);
							$("#invoiceType").text(invoiceType);
							$("#taxMethod").text(taxMethod);
							$("#contractStartdate").text(new Date(item[i].contractStartdate).format("yyyy-MM-dd"));
							$("#contractEnddate").text(new Date(item[i].contractEnddate).format("yyyy-MM-dd"));
							$("#taxDeduction").text(item[i].taxDeduction);
							$("#dueamount").text(item[i].dueamount);
							$("#billamountTaxamount").text(item[i].billamountTaxamount);
							$("#payCalcamount").text(item[i].payCalcamount);
							$("#paymentNote").text(item[i].paymentNote);
							$("#yearAmount").text(item[i].yearAmount);
							$("#totalAmount").text(item[i].totalAmount);
							$("#paymentId").val(item[i].paymentId);
							

							 checkInfo(item[i].regId);
							
							//报账点与机房
							for(var j=0;j<item[i].baseresourcePaymentdetail.length;j++){
								s = "<tr style='text-align: center;'><td>"
									+ item[i].baseresourcePaymentdetail[j].baseresourceCode
									+ "</td><td>"
									+ item[i].baseresourcePaymentdetail[j].baseresourceName
									+ "</td>"
									+"<td>"
									+ item[i].baseresourcePaymentdetail[j].dueamount
									+ "</td>"
									+ "<td>"
									+item[i].baseresourcePaymentdetail[j].billamountTaxamount
									+ "</td><td>"
									+ item[i].baseresourcePaymentdetail[j].payamount
									+ "</td></tr>";
							
								$('#tb1').append(s);
							}
							
						}
				
				}
			}
		},
		error : function(back) {
					
		}
	});
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
			alertModel("请求异常");
		}
	})
}

function changeSave(){
	if($("#nextUsers option").length > 1 && $("#checkState option:selected").val()=='0'){
		if($('#nextUsers option:selected').val() == ""){
			$("#nextUsers").next("#err").html("<img src=\"../../../image/error.png\"/>必选！");
			$("#nextUsers").next("#err").css({"color":"red"});
			flag=true;
		}else{
			$("#nextUsers").next("#err").html("<img src=\"../../../image/right_1.png\"/>验证成功");
			$("#nextUsers").next("#err").css({"color":"green"});
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

function saveCheckInfo(){
	changeSave();
	if(flag){
		return false;
	}
	var state=$("#checkState option:selected").val();
	var	comment=$("#comment").val();
	var leader=null;
	if($("#nextUsers option:selected").val()!=null){
		leader=$("#nextUsers option:selected").val();	
	}
	var taskId=getParameter("taskId");
	var paymentId=$("#paymentId").val();

	var paymentIds = new Array();
	// 从选中行中挑出可以启用的item
	var obj={
    	"state":state,
    	"leader":leader,
    	"comment":comment,
    	"taskId":taskId,
    	"paymentId":paymentId
    };
	$("#saveSet").attr("disable",true);
	paymentIds.push(obj);
	$.ajax({
		url:'saveCheckInfo',
		data:JSON.stringify(paymentIds),
		type:'post',
		dataType:'json',
		contentType: "application/json;charset=utf-8",
		success:function(back){
			if (back != null) {
				if(back.success=="1"){
					alertModel(back.msg);
					setTimeout('goBack()',1000)
//					window.location.href='paymentCheck.html';
				}
			}
			$("#saveSet").attr("disable",false);
		},
		error:function(){
			alertModel("请求异常");
			$("#saveSet").attr("disable",false);
		}
	});
	
}

function checkInfo(regId){
	var paymentId=getParameter("paymentId");
	findUsersByRoleIds(RembursePoint.tableName,paymentId,regId);
	histoicFlowList(RembursePoint.tableName,paymentId);
}

