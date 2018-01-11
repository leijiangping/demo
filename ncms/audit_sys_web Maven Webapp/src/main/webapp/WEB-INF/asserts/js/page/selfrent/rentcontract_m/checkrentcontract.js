//已显示表格list
var showTableList = null;
var item=null;
var accountType=null;
var supplierType=null;
var operate_type = 1;// 1 新增，2 修改

$(document).ready(function() {

	initCurrentPage();
});

function initCurrentPage(){
	curPageNum = 1;
	// 查询默认条件表单数据
	querySupplierType();
	queryAccountType()
	queryCurUser();
	allContract();
	checkInfo();
	findUserByRoleIds();
}
 var regId;
function allContract(){
	var rentContract=localStorage.getItem("item1");
		$.ajax({
		url : 'queryAllOfContract',// 跳转到 action
		data : {
			rentcontractId : rentContract
			},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if(back != null){
				if(back.success == "1"&&back.obj!=null){
						item=back.obj;
						if(item.datContractVO.contractType != null){
							switch(item.datContractVO.contractType){
			        		case 0:
			        			contractType= '房租合同';
			        			break;
			        		case 1:
			        			contractType= '电费合同';
			        			break;
			        		default:
			        			contractType ="综合合同";
								break;
						   }
						}
						
						
						switch(item.houseType){
							case 0:
								houseType = "板房";
								break;
							case 1: 
								houseType ="砖房";
								break;
							case 2:
								houseType ="砖混";
								break;
							default:
								houseType ="/";
								break;
						}
						switch(item.chargeType){
							case 0:
								chargeType = "租金";
								break;
	
							default:
								chargeType ="/";
								break;
						}
						
						regId=item.datContractVO.regId;
						$("#rentId").val(item.rentcontractId);
						$("#datId").val(item.datContractVO.contractId);
						$("#contractType").text(contractType);
						$("#contractCode").text(item.datContractVO.contractCode);
						$("#contractName").text(item.datContractVO.contractName);
						$("#contractStartdate").text(new Date(item.datContractVO.contractStartdate).format("yyyy-MM-dd"));
						$("#contractEnddate").text(new Date(item.datContractVO.contractEnddate).format("yyyy-MM-dd"));
						$("#pRegName").text(item.datContractVO.pregName==null?"":item.datContractVO.pregName+"--"+item.datContractVO.regName==null?"":item.datContractVO.regName);
						$("#totalAmount").text(item.totalAmount);
						$("#contractCheckname1").text(item.datContractVO.contractCheckname1==null?"":item.datContractVO.contractCheckname1);
						$("#contractCheckname2").text(item.datContractVO.contractCheckname2);
						$("#contractSigndate").text(new Date(item.datContractVO.contractSigndate).format("yyyy-MM-dd"));
						$("#contractYearquantity").text(item.datContractVO.contractYearquantity);
						$("#managerUser").text(item.datContractVO.managerUser == null ? "" :item.datContractVO.managerUser);
						$("#managerDepartment").text(item.datContractVO.managerDepartment);
						$("#yearAmount").text(item.yearAmount);
						$("#houseType").text(houseType);
						$("#contractType1").text(contractType);
						$("#charge_type").text(chargeType);
						queryPaymentperiodId(item);
						$("#property_address").text(item.propertyAddress);
						$("#rentcontract_note").val(item.rentcontractNote);
						$("#supplier_code").text(item.datSupplierVO.supplierCode);
						$("#supplier_name").text(item.datSupplierVO.supplierName);
						$("#supplier_address").text(item.datSupplierVO.supplierAddress);
						$("#supplier_contact").text(item.datSupplierVO.supplierContact);
						$("#supplier_telephone").text(item.datSupplierVO.supplierTelephone);
						$("#supplier_contact").text(item.datSupplierVO.supplierContact);
						if(item.datContractVO!=null&&item.datContractVO.sysRegionVO!=null){
							$("#pRegName1").text(item.datContractVO.sysRegionVO.pRegName);
							$("#regName1").text(item.datContractVO.sysRegionVO.prvName);
						}
						$("#supplier_contact").text(item.datSupplierVO.supplierContact);
						$("#bank_user").text(item.datSupplierVO.bankUser);
						$("#deposit_bank").text(item.datSupplierVO.depositBank);
						$("#bank_account").text(item.datSupplierVO.bankAccount);
						for (var i = 0; i < supplierType.length; i++) {
							if(item.datSupplierVO.supplierType==supplierType[i].dict_value){
								$("#suppliertype").text(supplierType[i].dict_name);
							}
						}
						for (var i = 0; i < accountType.length; i++) {
							if(item.datSupplierVO.accountType==accountType[i].dict_value){
								$("#account_type").text(accountType[i].dict_name);
							}
						}
						
						
				}
			}
		},
		error : function(back) {
					
		}
	});
}


//查询账户类型
function queryAccountType(){
	var msg = "ACCT_TYPE";
	$.ajax({
		url : 'queryDictionaryByCode',   
		data : {
			dictgroup_code : msg
		},
		type : 'post',
		dataType : 'json',
		async:false,
		success : function(back) {
			if (back != null) {
				if(back.success=="1"){
					var dictionary = back.Obj;
					accountType = dictionary;
				}
			}
		},
		error : function() {
			alert("请求异常");
		}
	})
} 
//查询供应商类型
function querySupplierType(){
	var msg = "SUPPLIER_TYPE";
	$.ajax({
		url : 'queryDictionaryByCode',   
		data : {
			dictgroup_code : msg
		},
		async:false,
		type : 'post',
		dataType : 'json',
		async:false,
		success : function(back) {
			if (back != null) {
				if(back.success=="1"){
					var dictionary = back.Obj;
					supplierType = dictionary;
				}
			}
		},
		error : function() {
			alert("请求异常");
		}
	})
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
	var taskId=getParameter("taskId");
	var leader=null;
	if($("#nextUsers option:selected").val()!=null){
		leader=$("#nextUsers option:selected").val();	
	}
	var rentcontractId=$("#rentId").val();
	var contractId=$("#datId").val();
	var rentcontractIds = new Array();
	// 从选中行中挑出可以启用的item
	var obj={
			"state":state,
			"comment":comment,
			"leader":leader,
			"rentcontractId":rentcontractId,
			"taskId":taskId,
			"contractId":contractId,
    };
	rentcontractIds.push(obj);
	$("#saveSet").attr("disable",true);
	$.ajax({
		url:'saveCheckInfo',
		data:JSON.stringify(rentcontractIds),
		type:'post',
		dataType:'json',
		contentType : 'application/json;charset=utf-8',
		success:function(back){
			if (back != null) {
				if(back.success=="1"){
					window.location.href='review.html';
				}
			}
			$("#saveSet").attr("disable",false);
		},
		error:function(){
			alert("请求异常");
			$("#saveSet").attr("disable",false);
		}
	});
}

function findUserByRoleIds(){
	var rentcontractId=localStorage.getItem("item1");
	
	findUsersByRoleIds(SelfRentAudit.tableName,rentcontractId,regId);
}
function checkInfo(){
	var rentcontractId=localStorage.getItem("item1");
	histoicFlowList(SelfRentAudit.tableName,rentcontractId);
}
function goBack(){
	localStorage.removeItem("item1");
	javascript:history.back(-1);
}

//查询缴费周期
function queryPaymentperiodId(rentContractVO){
	var id = null;
	if(rentContractVO != null){
		id = rentContractVO.paymentperiodId;
	}
	var msg = "PAYMENTPERIOD";
	$.ajax({
		url : 'queryDatPaymentPeriod',   
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
					$('#paymentperiod_id').empty();//删除之前的数据
					var s = '';
					for (var i = 0; i < msg.length; i++) {
						if(id == msg[i].paymentperiodId){
							$("#paymentperiod_id").text(msg[i].paymentperiodName);
						}
					}
				}
			}
		},
		error : function() {
			alert("请求异常");
		}
	})
}
