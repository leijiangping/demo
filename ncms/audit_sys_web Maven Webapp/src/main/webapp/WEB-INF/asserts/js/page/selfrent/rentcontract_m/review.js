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
	checkInfo();
	querySupplierType();
	queryAccountType();
	allContract();
}
 
function allContract(){
	var rentcontractId=getParameter("rentcontractId");
		$.ajax({
		url : 'queryAllOfContract',// 跳转到 action
		data : {
			rentcontractId : rentcontractId
			},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if(back != null){
				if(back.success == "1"){
					item=back.obj;
					rentContractVO = item;
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
						houseType ="";
						break;
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
					$("#rentId").val(item.rentcontractId);
					$("#isDownShare").text((item.datContractVO.isDownShare == 1) ? "是" : "否" );
					$("#contractType").text(contractType);
					$("#contractCode").text(item.datContractVO.contractCode);
					$("#contractName").text(item.datContractVO.contractName);
					$("#contractStartdate").text(new Date(item.datContractVO.contractStartdate).format("yyyy-MM-dd"));
					$("#contractEnddate").text(new Date(item.datContractVO.contractEnddate).format("yyyy-MM-dd"));
					var regName = item.datContractVO.regName;
					var pregName = item.datContractVO.pregName;
					if(regName != null && regName != ''){
						$("#pRegName").text(pregName + "--" + regName);
					}else if(pregName != null  && pregName != ''){
						$("#pRegName").text(pregName);
					}else{
						$("#pRegName").text("-");
					}
					$("#totalAmount").text(item.totalAmount);
					$("#contractCheckname1").text(item.datContractVO.contractCheckname1);
					$("#contractCheckname2").text(item.datContractVO.contractCheckname2);
					$("#contractSigndate").text((item.datContractVO.contractSigndate) == null ? "-" : new Date(item.datContractVO.contractSigndate).format("yyyy-MM-dd"));
					$("#contractYearquantity").text(item.datContractVO.contractYearquantity);
					$("#managerUser").text(item.datContractVO.managerUser);
					$("#managerDepartment").text(item.datContractVO.managerDepartment);
					$("#yearAmount").text(item.yearAmount);
					$("#houseType").text(houseType);
					$("#contractType1").text(contractType);
					$("#charge_type").text(chargeType);
					queryPaymentperiodId();
					$("#property_address").text(item.propertyAddress);
					$("#rentcontract_note").val(item.rentcontractNote);
					$("#supplier_code").text(item.datSupplierVO.supplierCode);
					$("#supplier_name").text(item.datSupplierVO.supplierName);
					$("#supplier_address").text(item.datSupplierVO.supplierAddress);
					$("#supplier_contact").text(item.datSupplierVO.supplierContact);
					$("#supplier_telephone").text(item.datSupplierVO.supplierTelephone);
					$("#supplier_contact").text(item.datSupplierVO.supplierContact);
					$("#pRegName1").text(item.datSupplierVO.pregName);
					$("#regName1").text(item.datSupplierVO.regName);
					$("#supplier_contact").text(item.datSupplierVO.supplierContact);
					$("#bank_user").text(item.datSupplierVO.bankUser != null ? item.datSupplierVO.bankUser : "");
					$("#deposit_bank").text(item.datSupplierVO.depositBank);
					$("#bank_account").text(item.datSupplierVO.bankAccount != null ? item.datSupplierVO.bankAccount : "");
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



function checkInfo(){
	var rentcontractId=getParameter("rentcontractId");
	histoicFlowList(SelfRentAudit.tableName,rentcontractId);
	
}
function goBack(){
	javascript:history.back(-1);
}

var rentContractVO;
function queryPaymentperiodId(){
	var id = null;
	if(rentContractVO != null){
		id = rentContractVO.paymentperiodId;
	}
	$.ajax({
		url : 'queryDatPaymentPeriod',   
		data : {
			dictgroup_code : id
		},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if (back != null) {
				if(back.success=="1"){
					var dictionary = back.Obj;
					msg = dictionary;
					$('#paymentperiod_id').empty();//删除之前的数据
					for (var i = 0; i < msg.length; i++) {
						if(id == msg[i].paymentperiodId){
							$("#paymentperiod_id").html(msg[i].paymentperiodName);
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
