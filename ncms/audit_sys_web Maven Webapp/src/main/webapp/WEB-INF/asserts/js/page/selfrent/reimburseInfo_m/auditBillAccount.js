//已显示表格list
var showTableList = null;
var curPageNum=1;
var item=null;
var operate_type = 1;// 1 新增，2 修改
var ID =getParameter("billAccountId");
$(document).ready(function() {
	initMethod();
})

var regId;
function queryBillAccount(){
	$.ajax({
		url : 'queryBillAccountById',// 跳转到 action
		type : 'post',
		data:{
			billAccountId:ID
		},
		dataType : 'json',
		success : function(res) {
			switch(res.obj.billAccountState){
			case 0:
				billAccountState= '启用';
    			break;
    		case 9:
    			billAccountState= '停用';
    			break;
    		default:
    			billAccountState= '/';
    			break;
			}
			$("#billAccountId").html(res.obj.billAccountId)
			$("#billAccountCode").html(res.obj.billAccountCode);
			$("#billAccountName").html(res.obj.billAccountName);
			$("#billAccountNote").html(res.obj.billAccountNote);
			$("#billAccountState").html(billAccountState);
			$("#City").html(res.obj.sysRegionVO.pRegName).attr('checked','true');
			$("#Village").html(res.obj.sysRegionVO.regName).attr('checked','true');
			regId=res.obj.sysRegionVO.regId;
		},
		error : function() {
			alertModel("请求失败！");
		}
	});
	
}
function queryContract(){
	$.ajax({
		url : 'queryContAndSupByBillId',// 跳转到 action
		data:{
			billaccountId:ID
		},
		type : 'post',
		dataType : 'json',
		async:false,
		success : function(res) {
			if(res!=null){
				var rentObj= res.obj;
				if(rentObj!=null){
	//				$("#totalAmount").html(rentObj.totalAmount);
					$("#contractCode").html(rentObj.datContractVO.contractCode);
					$("#contractName").html(rentObj.datContractVO.contractName);
					$("#contractStartdate").html(rentObj.datContractVO.contractStartdate);
					$("#contractEnddate").html(rentObj.datContractVO.contractEnddate);
					$("#contractSigndate").html(rentObj.datContractVO.contractSigndate);
					$("#contractYearquantity").html(rentObj.datContractVO.contractYearquantity);
					if(rentObj!=null&&rentObj.datContractVO!=null&&rentObj.datContractVO.sysRegionVO!=null&&rentObj.datContractVO.sysRegionVO.pRegName!=null)
					$("#city").html(rentObj.datContractVO.sysRegionVO.pRegName);
					if(rentObj!=null&&rentObj.datContractVO!=null&&rentObj.datContractVO.sysRegionVO!=null&&rentObj.datContractVO.sysRegionVO.regName!=null)
					$("#distract").html(rentObj.datContractVO.sysRegionVO.regName);
					
					$("#supplierCode").html(rentObj.datSupplierVO.supplierCode);
					$("#supplierName").html(rentObj.datSupplierVO.supplierName);
					/*$("#datetimepicker2").html(rentObj.datSupplierVO.datetimepicker2);
					$("#datetimepicker3").html(rentObj.datSupplierVO.datetimepicker3);
					$("#datetimepicker4").html(rentObj.datSupplierVO.datetimepicker4);*/
				}
			}
		},
		error : function() {
			alertModel("请求失败！");
		}
	});
}

function queryResource(){
	$.ajax({
		url : 'queryResource',// 跳转到 action
		type : 'post',
		data:{
			billaccountId:ID
		},
		dataType : 'json',
		success : function(res) {
			var obj=eval(res.obj);
			var tbody=$('<tbody></tbody>');
			$(obj).each(function (index){
				var val=obj[index];
				switch(val.baseResourceType){
				case 0:
					baseResourceType= '机房';
	    			break;
				case 1:
					baseResourceType= '资源点';
	    			break;
				case 2:
					baseResourceType= '热点';
	    			break;
	    		case 3:
	    			baseResourceType= '位置点';
	    			break;
	    		default:
	    			baseResourceType ="/";
					break;	
				}
				var tr=$('<tr></tr>');
				tr.append('<td>'+ baseResourceType+ '</td>' + '<td>'+ val.baseResourceCode + '</td>' +'<td>'+ val.baseResourceName + '</td>' +'<td>'+ val.baseResourceState + '</td>' + '<td><a href="">删除</a></td>');
				tbody.append(tr);
				$('#resources').append(tbody);
			});
		},
		error : function() {
			alertModel("请求失败！");
		}
	});
}
function initMethod(){
	// 查询默认条件表单数据
	queryBillAccount();
	queryResource();
	queryPaymentData();
	findUserByRoleIds();
	queryCurUser();
	checkInfo();
	queryContract();
}
function queryPaymentData(){
	$.ajax({
		url : 'queryPaymentMethod',// 跳转到 action
		data : {
				billAccountId:ID,
			   },
		type : 'post',
		dataType : 'json',
		success : function(res) {
			if(res!=null){
				var rentObj= res.obj;
				if(rentObj!=null&&rentObj.rentPaymentVO!=null&&rentObj.rentPaymentVO.paymentMethod!=null)
				$("#paymentMethod").val(rentObj.rentPaymentVO.paymentMethod);
			}
		},
		error : function() {
			alertModel("请求失败！");
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
			alert("请求异常");
		}
	})
}

function changeSave(){
	if($("#nextUsers option").length > 1){
		if($('#nextUsers option:selected').val() == "" && $("#checkState option:selected").val()=='0'){
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

function saveBillAccountAudit(){
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
	var billAccountIds = new Array();
	// 从选中行中挑出可以启用的item
	var obj={
			"state":state,
			"comment":comment,
			"leader":leader,
			"billAccountId":ID,
			"taskId":taskId,
    };
	billAccountIds.push(obj);
	
	$.ajax({
		type: "post",
		url:'saveBillAccountAudit',
		data:JSON.stringify(billAccountIds),
		type:'post',
		dataType:'json',
		contentType : 'application/json;charset=utf-8',
		success:function(back){
			if (back != null) {
				if(back.success=="1"){
					window.location.href='auditReimbursePointInfo.html';
				}
			}
		},
		error:function(){
			alert("请求异常");
		}
	})

}

function findUserByRoleIds(){
	findUsersByRoleIds(RentBillAccount.tableName,ID,regId);
}
function checkInfo(){
	histoicFlowList(RentBillAccount.tableName,ID);
}


