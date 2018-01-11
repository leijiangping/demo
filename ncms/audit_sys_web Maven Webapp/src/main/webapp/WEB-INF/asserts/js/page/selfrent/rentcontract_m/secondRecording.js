var rentContractVO = null;
$(document).ready(function(){
	initCurrentPage();
});
function initCurrentPage(){
	getAddress();
	amount();
}
function getAddress(){
	$.ajax({
		type : "post",
		url : "getAddressRent",
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		success : function(value) {
			if(value != null){
				sysReguins = value.obj;
				if(sysReguins!=null){
					$('#City').empty();
					$('#Village').empty();
					var strCity = "<option value=''>-选择地市-</option>";
					$.each(sysReguins, function (i, item){
						strCity += "<option value='" +item.regId+"'>"+item.regName+ "</option>";
					});

					$("#City").append(strCity);

					var strReg = "<option value=''>-选择区县-</option>";
					$("#Village").append(strReg);
					//绑定联动事件 修改人zhujj
					$("#City").change(function(){
						$("#Village").empty();
						strReg = "<option value=''>-选择区县-</option>";
						if($("#City").val()!=""){
							$.each(sysReguins, function (i, item){
								if(item.regId==$("#City").val()){
									$.each(item.child, function (j, itemchild){
										strReg += "<option value='" + itemchild.regId+"'>"+itemchild.regName+ "</option>";
									});
								}
							});
						}
						$("#Village").append(strReg);
					});
				}
				queryAll();
			}
		},
		error : function(data) {
			alertModel('获取用户地区信息失败!');
		}
	});
}
/**
 * 供应商弹出层 地市 区县信息
 * 
 * @param title 名称 例如：供应商地市，传入title为供应商
 */

function getAddressFind(title){
	$.ajax({
		type : "post",
		url : "getAddressRent",
		
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		success : function(value) {
			if(value != null){
				sysReguins = value.obj;
				if(sysReguins!=null){
					$('#City1').empty();
					$('#Village1').empty();
					var strCity = "<option value=''>-选择"+(title?title:"")+"地市-</option>";
					$.each(sysReguins, function (i, item){
						strCity += "<option value='" +item.regId+"'>"+item.regName+ "</option>";
					});

					$("#City1").append(strCity);

					var strReg = "<option value=''>-选择"+(title?title:"")+"区县-</option>";
					$("#Village1").append(strReg);
					//绑定联动事件 修改人zhujj
					$("#City1").change(function(){
						$("#Village1").empty();
						strReg = "<option value=''>-选择"+(title?title:"")+"区县-</option>";
						if($("#City1").val()!=""){
							$.each(sysReguins, function (i, item){
								if(item.regId==$("#City1").val()){
									$.each(item.child, function (j, itemchild){
										strReg += "<option value='" + itemchild.regId+"'>"+itemchild.regName+ "</option>";
									});
								}
							});
						}
						$("#Village1").append(strReg);
					});
				}
			}
		},
		error : function(data) {
			alertModel('获取用户地区信息失败!');
		}
	});
}

function show(){
	queryHouseType();
	queryPaymentperiodId();
}

function back(){
	localStorage.removeItem("rentcontractId");
	//window.location.href="recording.html";
	javascript:history.back(-1);
}

function tableDateFormatter(value, row, index){
	return new Date(value).format("yyyy-MM-dd");
}

function queryAll(){
	var rentcontractId = localStorage.getItem("rentcontractId");
	if($("#City").val() == null && $("#Village").val() == null){
		getAddress();
	}
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
					rentContractVO = back.Obj;
					//主合同信息
					flag3 = false;
					operate_type = 2;
					$("#contractCode").attr("readonly","readonly");
					$("#contractCode").removeAttr("onblur");
					$("#contractName").val(rentContractVO.datContractVO.contractName);
					$("#contractCode").val(rentContractVO.datContractVO.contractCode);
					$("#contractStartdate").val(new Date(rentContractVO.datContractVO.contractStartdate).format("yyyy-MM-dd"));
					$("#contractEnddate").val(new Date(rentContractVO.datContractVO.contractEnddate).format("yyyy-MM-dd"));
					$("#contractCheckname2").val(rentContractVO.datContractVO.contractCheckname2);
					$("#contractYearquantity").val(rentContractVO.datContractVO.contractYearquantity);
					$("#contractCheckname1").val(rentContractVO.datContractVO.contractCheckname1);
					$("#contractSigndate").val(new Date(rentContractVO.datContractVO.contractSigndate).format("yyyy-MM-dd"));
					$("#managerUser").val(rentContractVO.datContractVO.managerUser);
					$("#managerDepartment").val(rentContractVO.datContractVO.managerDepartment);
					$("#City").val(rentContractVO.datContractVO.pregId);
					$("#City").change();
					$("#Village").val(rentContractVO.datContractVO.regId);
					
					//房屋租赁合同信息
					$("#propertyAddress").val(rentContractVO.propertyAddress);
					$("#yearAmount").val(rentContractVO.yearAmount);
					$("#rentcontractNote").val(rentContractVO.rentcontractNote);
					$("#paymentperiodId").val(rentContractVO.paymentperiodId);
					$("#houseType").val(rentContractVO.houseType);
					$("#paymentperiodId").val(rentContractVO.paymentperiodId);
					$("#totalAmount").val(rentContractVO.totalAmount);
					//供应商信息
					$("#supplierCode").html(rentContractVO.datSupplierVO.supplierCode);
					$("#supplierName").html(rentContractVO.datSupplierVO.supplierName);
					$("#supplierAddress").html(rentContractVO.datSupplierVO.supplierAddress);
					$("#supplierContact").html(rentContractVO.datSupplierVO.supplierContact);
					$("#supplierTelephone").html(rentContractVO.datSupplierVO.supplierTelephone);
					queryAccountType();
					$("#bankAccount").html(rentContractVO.datSupplierVO.bankAccount);
					$("#depositBank").html(rentContractVO.datSupplierVO.depositBank);
					$("#bankUser").html(rentContractVO.datSupplierVO.bankUser);
					//隐藏ID
					$("#rentcontractId").val(rentContractVO.rentcontractId);
					$("#supplierId").val(rentContractVO.datSupplierVO.supplierId);
					$("#contractId").val(rentContractVO.datContractVO.contractId);
					flag = false;
				}
			}
			show();
		},
		error : function(back) {
					
		}
	});
}

//查询房屋类型 
function queryHouseType(){
	var id = rentContractVO.houseType;
	var msg = "HOUSE_TYPE";
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
					$('#houseType').empty();//删除之前的数据
					var s = '';
					$("#houseType").append("<option value='' selected='selected'>" +'--房屋类型--'+ "</option>");
					for (var i = 0; i < msg.length; i++) {
						if(id == msg[i].dict_value){
							$("#houseType").append("<option selected = 'selected' value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}else{
							$("#houseType").append("<option value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}
					}
 				}
 			}
		},
		error : function() {
			alertModel("请求异常");
		}
	})
} 
//查询缴费周期
function queryPaymentperiodId(){
	var id = rentContractVO.paymentperiodId;
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
						if(id == msg[i].dict_value){
							$("#paymentperiodId").append("<option selected = 'selected' value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}else{
							$("#paymentperiodId").append("<option value=" + msg[i].dict_value + ">" + msg[i].dict_name + "</option>");
						}
					}
 				}
 			}
		},
		error : function() {
			alertModel("请求异常");
		}
	})
}
//查询账户类型
function queryAccountType(){
	var id = ($('#tb').bootstrapTable('getSelections')[0]).accountType;
	if(rentContractVO != null){
		id = rentContractVO.datSupplierVO.accountType;
	}
	var msg = "ACCT_TYPE";
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
					$('#accountType').empty();//删除之前的数据
					var s = '';
					for (var i = 0; i < msg.length; i++) {
						if(id == msg[i].dict_value){
							$("#accountType").html(msg[i].dict_name);
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
//判断合同日期
function checkContractEndDate(){
	$("#contractEnddate").siblings("span").html("");
	var contractStartdate = $("#contractStartdate").val();
	var contractEnddate = $("#contractEnddate").val();
	var startDate = contractStartdate.split("-");
	var endDate = contractEnddate.split("-");
	var start=Date.UTC(startDate[0],startDate[1],startDate[2]);
	var end=Date.UTC(endDate[0],endDate[1],endDate[2]);
	var year = end-start;
	$("#contractYearquantity").val((year/(1000 * 24 * 60 * 60 * 365)).toFixed(1));
	flag1 = false;
}
function yearAmountVal(){
	//合同总金额
	var totalAmount =  $("#totalAmount").val();
	//合同年限
	var contractYearquantity = $("#contractYearquantity").val();

	if(contractYearquantity > 0){
		yearAmount = totalAmount/contractYearquantity;
		$("#yearAmount").val(yearAmount);
	}
}
/**
 * 查询供应商信息
 */
function findSupplier(){
	$("#EditPanel").modal();
	loadTableData();
	getAddressFind();
}
function loadTableData(){
		// 先销毁表格
		$('#tb').bootstrapTable('destroy');
		// 初始化表格,动态从服务器加载数据
		$("#tb").bootstrapTable({
			method : "post",
			contentType : "application/x-www-form-urlencoded",
			url : "querySupplierInfo", // 获取数据的地址
			striped : true, // 表格显示条纹
			pagination : true, // 启动分页
			pageSize : ipageCount, // 每页显示的记录数
			pageNumber : curPageNum, // 当前第几页
			minimumCountColumns: 1,  //最少允许的列数
			clickToSelect: true,  //是否启用点击选中行
			pageList : [10, 25, 50, 100, 500], // 记录数可选列表
			search : false, // 是否启用查询
			sidePagination : "server", // 表示服务端请求
			// 设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
			// 设置为limit可以获取limit, offset, search, sort, order
			queryParamsType : "undefined",
			queryParams : function queryParams(params) { // 设置查询参数
				var param = {
					pageNumber: params.pageNumber,    
		            pageSize: params.pageSize,
		            supplierName : 	$('#supplierNameFind').val(),
		            pregId :	$("#City1").val(),
		            regId :	$("#Village1").val()
				};
				return param;
			},
			columns: [{
	            checkbox: true
	        }, {
	            field: 'supplierCode',
	            title: '供应商代码'
	        }, {
	            field: 'supplierName',
	            title: '供应商名称'
	        }, {
	            field: 'pregName',
	            title: '所属地市'
	        }, {
	            field: 'regName',
	            title: '所属区县'
	        }, {
        	    field: 'supplierType',
	            title: '供应商类别',
	            formatter:function(value){
	            	switch(value){
	            		case 0:return '电费供应商';
	            		case 1:return '房租供应商';
	            	}
	            	return value;
	            }
	        }, {
	            field: 'supplierAddress',
	            title: '供应商地址'
	        }, {
	            field: 'supplierTelephone',
	            title: '供应商联系电话'
	        }, ],
			onLoadError : function() { // 加载失败时执行
				console.log("请求失败！");
			},
			responseHandler: function(res) {
				if(res != null){//报错反馈
					if(res.success != "1"){
			            alertModel(res.msg);
					}
					showTableList = res.obj.result;
				}
		        return {
		            "total": res.obj.total,//总页数
		            "rows": res.obj.result //数据
		         };
			}
		});
}
function hadCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}
//弹窗确定按钮
function formSubmit(){
	if(!hadCheckedRowData()){
		return false;
	}
	queryAccountType();
	var selectRows=$('#tb').bootstrapTable('getSelections')[0];
	$("#supplierName").html(selectRows.supplierName);
	$("#supplierCode").html(selectRows.supplierCode);
	$("#supplierAddress").html(selectRows.supplierAddress);
	$("#supplierContact").html(selectRows.supplierContact);
	$("#supplierTelephone").html(selectRows.supplierTelephone);
	$("#bankAccount").html(selectRows.bankAccount);
	$("#depositBank").html(selectRows.depositBank);
	$("#bankUser").html(selectRows.bankUser);
	$("#supplierId").val(selectRows.supplierId);
	$("#EditPanel").modal('hide');
}	

/**
 * 金额（限制）,只能输入数字和.（点） 且 小数点后不能超过两位
 * */
function amount(){
	$("#totalAmount").keyup(function () {
	    var reg = $(this).val().match(/\d+\.?\d{0,2}/);
	    var txt = '';
	    if (reg != null) {
	        txt = reg[0];
	    }
	    $(this).val(txt);
	}).change(function () {
	    $(this).keypress();
	    var v = $(this).val();
	    if (/\.$/.test(v))
	    {
	        $(this).val(v.substr(0, v.length - 1));
	    }
	});
}
function validform(){
	var addnew_validate= bindformvalidate("#dataForm", {
		"datContractVO.contractCode" : {
			required : true,
		},
		"datContractVO.contractName" : {
			required : true,
		},
		"datContractVO.contractType" : {
			required : true,
		},
		"datContractVO.contractStartdate":{
			required : true,
		},
		"datContractVO.contractEnddate":{
			required : true,
		},
		"datContractVO.contractSigndate":{
			required : true,
		},
		"datContractVO.contractYearquantity":{
			required : true,
			min : 0.0000000000001,
		},
		totalAmount:{
			required : true
		},
		yearAmount:{
			required : true,
		},
		paymentperiodId:{
			required : true,
		}
	}, {
		"datContractVO.contractCode":{
			required : "必填！"
		},
		"datContractVO.contractName":{
			required : "必填！"
		},
		"datContractVO.contractType":{
			required : "必选！"
		},
		"datContractVO.contractStartdate":{
			required : "必选！"
		},
		"datContractVO.contractEnddate":{
			required : "必选！"
		},
		"datContractVO.contractSigndate":{
			required : "必选！"
		},
		"datContractVO.contractYearquantity":{
			required : "年限不能为空！",
			min : "年限不能等于0",
		},
		totalAmount:{
			required : "必填！"
		},
		yearAmount:{
			required : "必填！"
		},
		paymentperiodId:{
			required : "必选！"
		}
	});

  return addnew_validate;
}
/**
 * 保存合同 提交form表单
 */
function formSubmitAddContract(){
	if(validform().form()){
		if(flag3){
			checkContractCode();
			if(flag){
				return;
			}
		}
		checkContractEndDate();
		if(flag1){
			return;
		}
		if($("#Village").val()=="" && $("#isDownShare").val() != 0){
			alertModel("请选择区县信息或者选择向下共享!");
			return;
		}
		if($("#yearAmount").val()==""){
			alertModel("请输入年租费信息!");
			return;
		}
		if($("#paymentperiodId").val() == ""){
			alertModel("请选择合同缴费周期!");
			return;
		}
		if($("#supplierCode").html()==""){ 
			alertModel("请选择供应商!");
			return;
		}
		if($("#Village").val() != ""){
			 $("#isDownShare").val("1");
		}
	    var data=$('#dataForm').serialize();
		var submitData = decodeURIComponent(data,true);
		$("#saveSet").attr("disable",true);
		$.ajax({
		    url:'updateContract',
		    data: submitData,
	 		type : 'post',
	 		dataType:'json',
		    cache:false,
		    async:true,
		    success:function(result){
		        //请求成功时
		    	if(result!=null){
		    		if(result.success == "1"){
		    			localStorage.removeItem("rentcontractId");
		    			alert("补录租费合同成功!");
		    			window.location.href="recording.html";
		    		}
		    	}
		    	$("#saveSet").attr("disable",false);
		    },
		    error:function(){
				alertModel("修改租费合同失败");
				$("#saveSet").attr("disable",false);
		    }
		});
	}
}

$('#contractEnddate').on('input propertychange',function(){
	$('#enddate').val($(this).val());
})