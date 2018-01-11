
//已显示表格list
var showTableList = null;
var item = null;
var curPageNum=1;
//查询支付方式
var paymentMethodObject=null;
paymentPeriodObject=null;
$(document).ready(function() {
	//查询支付方式
	paymentMethodObject=queryDictType("PAYMENY_METHOD");
	//查询缴费周期
	paymentPeriodObject=queryDictType("PAYMENTPERIOD");
	initCurrentPage(); 
})
function initCurrentPage(){
	queryPaymentWays();
	querypaymentPeriods();
	getAddress();
}
function queryPaymentWays(){
	$('#paymentMethod').empty();
	var str = "<option value='' >-请选择支付方式-</option>";
		$("#paymentMethod").append(str);
	for (var j = 0; j < paymentMethodObject.length; j++) {
			$("#paymentMethod").append("<option value=" + paymentMethodObject[j].dict_value + ">" + paymentMethodObject[j].dict_name + "</option>");
	}
}
function querypaymentPeriods(){
	$('#paymentPeriod').empty();
	var str = "<option value='' >-请选择缴费周期-</option>";
	$("#paymentPeriod").append(str);
	for (var j = 0; j < paymentPeriodObject.length; j++) {
		$("#paymentPeriod").append("<option value=" + paymentPeriodObject[j].dict_value + ">" + paymentPeriodObject[j].dict_name + "</option>");
	}
}
function getContractAddress(title){
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
function getResourceAddress(title){
	$.ajax({
		type : "post",
		url : "getAddressRent",
		
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		success : function(value) {
			if(value != null){
				sysReguins = value.obj;
				if(sysReguins!=null){
					$('#City2').empty();
					$('#Village2').empty();
					var strCity = "<option value=''>-选择"+(title?title:"")+"地市-</option>";
					
					$.each(sysReguins, function (i, item){
						strCity += "<option value='" +item.regId+"'>"+item.regName+ "</option>";
						
					});

					$("#City2").append(strCity);
					var strReg = "<option value=''>-选择"+(title?title:"")+"区县-</option>";
					$("#Village2").append(strReg);

					//绑定联动事件 修改人zhujj
					$("#City2").change(function(){
						$("#Village2").empty();
						strReg = "<option value=''>-选择"+(title?title:"")+"区县-</option>";
						if($("#City2").val()!=""){
							$.each(sysReguins, function (i, item){
								if(item.regId==$("#City2").val()){
									$.each(item.child, function (j, itemchild){
										strReg += "<option value='" + itemchild.regId+"'>"+itemchild.regName+ "</option>";
									});
								}
							});
						}
					
						$("#Village2").append(strReg);
					});
					
				}
			}
		},
		error : function(data) {
			alertModel('获取用户地区信息失败!');
		}
	});
}

function queryContractInfo(){
	getContractAddress();
	queryContractData();
	$("#EditPanel1").modal();
}
function queryContractData() {
	
	// 先销毁表格
	$('#tb1').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb1").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryContractAgreement", // 获取数据的地址
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
	            contractCodeOrName : $('#contractCodeOrName').val(),
	            regId :	$("#Village1 option:selected").val(),
	            pregId :	$("#City1 option:selected").val()
			};              
			return param;
		},
		columns: [{
            radio: true
        }, {
            field: 'datContractVO.contractCode',
            title: '合同代码',
//            formatter:clickFormatter
        }, {
            field: 'datContractVO.contractName',
            title: '合同名称'
        }, {
        	field: 'datContractVO.contractState',
            title: '合同状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 0:return '正常';
            		case 9:return '终止';
            		default:return '/';
            	}
            	return value;
            }
        }, {
            field: 'datContractVO.pregName',
            title: '所属地市'
        }, {
            field: 'datContractVO.regName',
            title: '所属区县'
        }, ],
       /* onClickRow: function (row, td) {
        	var msg = row.rentcontractId;
        	window.location.href='rentcontractDetail.html';
        },*/	
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success != "1"){
//		            alertModel(res.msg);
				}
				showTableList = res.obj.result;
//				getAddress()
			}
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.result //数据
	         };
		}
	});
}


/**
 * 获取选中的radio
 * */
function isCheckedRadio(){
	var checkNum = 0;
	rowschecked = new Array();
	var checklist = $("#tb1 tbody input[type='radio']");
	for(var i=0;i<checklist.length;i++)
    {
		// 已选中可操作行
	    if(checklist[i].checked == 1){
	    	checkNum ++;
	    	rowschecked.push(showTableList[i]);
	    }
    } 
	return checkNum;
}
function hadCheckedRadioRowData(){
	if(showTableList==null || isCheckedRadio()==0){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}
/**
 * 选择合同/协议信息（确定）
 * */
function choiceContract(){
	if(!hadCheckedRadioRowData()){
		return ;
	}
	var regionId=rowschecked[0].datContractVO.regId
	$.ajax({
		url : 'queryContractAndSupplier',// 跳转到 action
		data : {
			rentcontractId : rowschecked[0].rentcontractId,
			regId:regionId,
			},
		type : 'post',
		dataType : 'json',
		success : function(res) {
				$("#contractCode").val(res.obj.datContractVO.contractCode);
				$("#rentcontractId").val(res.obj.rentcontractId);
				$("#contractName").val(res.obj.datContractVO.contractName);
				$("#contractStartdate").val(res.obj.datContractVO.contractStartdate);
				$("#contractEnddate").val(res.obj.datContractVO.contractEnddate);
				$("#contractSigndate").val(res.obj.datContractVO.contractSigndate);
				$("#contractYearquantity").val(res.obj.datContractVO.contractYearquantity);
				$("#totalAmount").val(res.obj.totalAmount);
				$("#yearAmount").val(res.obj.yearAmount);
				$("#city").val(res.obj.datContractVO.pregName);
				$("#distract").val(res.obj.datContractVO.regName);
				$("#supplierCode").val(res.obj.datSupplierVO.supplierCode);
				$("#supplierName").val(res.obj.datSupplierVO.supplierName);
				$("#contractInfo").css('display', 'block');
		},
		error : function() {
			alertModel("请求失败！");
		}
	});
}

/**
 * 选择机房/资源点信息（确定）
 * */
function choiceResource(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}	
	var resourceObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		console.log("row=========="+row.baseresourceId);
		resourceObjs.push(row.baseresourceId);
	}
	
	$.ajax({
		url : 'queryContractByResourceId',// 跳转到 action
		data: { "resourceIds":resourceObjs.join(",")},
		type : 'post',
		dataType : 'json',
		success : function(res) {
			$('#resources tbody').remove();
			var obj=eval(res.obj);
			var tbody=$('<tbody ></tbody>');
			$(obj).each(function (index){
				var val=obj[index];
				var tr=$('<tr></tr>');
				tr.append('<td>'+"<input id='' name='baseresourceId' type='hidden' value='"+ val.baseresourceId+ "'/>"
						+ formatterRes(val.baseResourceType) + '</td>' + '<td>'+ val.baseResourceCode + '</td>' +'<td>'+ 
						val.baseResourceName + '</td>' +'<td>'+ formatterRes1(val.baseResourceState) + '</td>' + '<td><a href="#" onclick="deleteResources(this);">删除</a></td>');
				tbody.append(tr);
				$('#resources').append(tbody);
			});
		},
		error : function() {
			alertModel("请求失败！");
		}
	});
}
function deleteResources(obj){
	$(obj).parents("tr").remove();
}
function formatterRes(value){
    	switch(value){
    		case 0 :return '机房';
    		case 1 :return '资源点';
    		case 2 :return '热点';
    		default:return '/';
    	}
    	return value;
    }
function formatterRes1(value){
	switch(value){
	case 0 :return '正常';
	case 9 :return '终结';
	default:return '/';
	}
	return value;
}
function choiceResourcePoint(){
	queryResourceInfo();
	getResourceAddress();
	$("#EditPanel2").modal();
}
/**
 * 查询与用户关联区县的资源点信息
 */
function queryResourceInfo(){
	// 先销毁表格
	$('#tb2').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb2").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryResourceInfo", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		pageSize : ipageCount, // 每页显示的记录数
		pageNumber : curPageNum, // 当前第几页
		minimumCountColumns: 1,  //最少允许的列数
		clickToSelect: true,  //是否启用点击选中行
		pageList : [10, 25, 50, 100, 500], // 记录数可选列表
		search : false, // 是否启用查询
		sidePagination : "server", // 表示服务端请求
		queryParamsType : "undefined",
		queryParams : function queryParams(params) { // 设置查询参数
			var param = {
				pageNumber: params.pageNumber,    
	            pageSize: params.pageSize,
	            resourceCodeOrName : 	$('#resourceCodeOrName').val(),
		        regId :	$("#Village2 option:selected").val(),
		        pregId :	$("#City2 option:selected").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
            field: 'baseResourceCode',
            title: '资源编码',
        }, {
            field: 'baseResourceName',
            title: '资源名称'
        },  {
            field: 'sysRegionVO.pRegName',
            title: '所属地市'
        }, {
            field: 'sysRegionVO.regName',
            title: '所属区县'
        },{
        	field: 'baseResourceState',
            title: '资源状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 0:return '正常';
            		case 9:return '终止';
            		default:return '/';
            	}
            	return value;
            }
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
function isChecked(){
	var checkNum = 0;
	rowschecked = new Array();
	var checklist = $("#tb2 tbody input[type='checkbox']");
	for(var i=0;i<checklist.length;i++)
    {
		// 已选中可操作行
	    if(checklist[i].checked == 1){
	    	checkNum ++;
	    	rowschecked.push(showTableList[i]);
	    }
    } 
	return checkNum;
}

function validform(){
	var twr_validate= bindformvalidate("#dataForm", {
		billAccountCode : {
			required : true,
		},
		billAccountName:{
			required : true,
		},
		City : {
			required : true,
		},
		Village:{
			required : true,
		},
		paymentPeriod:{
			required : true,
		},
		paymentMethod:{
			required : true,
		}
	}, {
		billAccountCode:{
			required : "必填！"
		},
		billAccountName : {
			required : "必填！"
		},
		City:{
			required : "必选！"
		},
		Village:{
			required : "必选！"
		},
		paymentPeriod:{
			required : "必选！"
		},
		paymentMethod:{
			required : "必选！"
		}
	});

  return twr_validate;
}


function addRebursePoint(){
	if(validform().form()){
		var rentcontId = $("#rentcontractId").val();
		var array = new Array();
		$('#resources input').each(function(){
			array.push($(this).val());
		})
		var baseresourceIds= array.join(",");
		var s=$("#Village").val();
		$("#saveSet").attr("disable",true);
		$.ajax({
		    url:'insertBillAcount',
		    data: {
		    	billAccountCode:$("#billAccountCode").val(),
		    	billAccountName:$("#billAccountName").val(),
		    	regId:$("#Village").val(),
		    	billAccountNote:$("#billAccountNote").val(),
		    	rentcontractId:$("#rentcontractId").val(),
		    	baseresourceIds: baseresourceIds,
		    	
		    },
	 		type : 'post',
		    async:false,
		    success:function(){
		    	window.location.href='reimbursePointManage.html';
		    	$("#saveSet").attr("disable",false);
		    },
		    error:function(){
				alertModel("请求失败！");
				$("#saveSet").attr("disable",false);
		    }
		});
	}
}



