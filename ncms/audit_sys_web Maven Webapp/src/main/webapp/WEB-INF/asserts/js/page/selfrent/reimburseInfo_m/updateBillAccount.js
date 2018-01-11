/**
 * 报账点编辑
 * 修改人：梁鹏伟
 */
//已显示表格list
var showTableList = null;
var curPageNum=1;
var resourceIdArray=new Array();
var billID = getParameter("billAccountId");

$(document).ready(function() {
	initCurrentPage();
});

//查报账点
function queryBillAccountById(){
	$.ajax({
		url : 'queryBillAccountById',// 跳转到 action
		type : 'post',
		data:{
			billAccountId:billID,
		},
		dataType : 'json',
		success : function(res) {
			$("#billAccountCode").val(res.obj.billAccountCode);
			$("#billAccountName").val(res.obj.billAccountName);
			$("#billAccountState").val(res.obj.billAccountState);
			$("#billAccountNote").val(res.obj.billAccountNote);
			$("#City").val(res.obj.sysRegionVO.pRegName);
			$("#Village").val(res.obj.sysRegionVO.regName);
			$("#regId").val(res.obj.sysRegionVO.regId);
//			$("#Village option[value="+res.obj.sysRegionVO.regId+"]").attr("selected","selected");
		},
		error : function() {
			alertModel("请求失败！");
		}
	});
}
function queryContAndSupByBillId (){
	$.ajax({
		url : 'queryContAndSupByBillId',// 跳转到 action
		data:{
			billaccountId:billID
		},
		type : 'post',
		dataType : 'json',
		success : function(res) {
				var rentObj= res.obj;
				rentContractVO = rentObj;
				$("#contractCode").val(rentObj.datContractVO.contractCode);
				$("#contractName").val(rentObj.datContractVO.contractName);
				$("#contractStartdate").val(rentObj.datContractVO.contractStartdate);
				$("#contractEnddate").val(rentObj.datContractVO.contractEnddate);
				$("#contractSigndate").val(rentObj.datContractVO.contractSigndate);
				$("#contractYearquantity").val(rentObj.datContractVO.contractYearquantity);
				$("#citys").val(rentObj.datContractVO.pregName);
				$("#distract").val(rentObj.datContractVO.regName);
				$("#isDownShare").val(rentObj.datContractVO.isDownShare == 1 ? "是" : "否");
				$("#totalAmount").val(rentObj.totalAmount);
				$("#supplierCode").val(rentObj.datSupplierVO.supplierCode);
				$("#supplierName").val(rentObj.datSupplierVO.supplierName);
				queryPaymentperiodId();
//				$("#paymentPeriodValue").val(rentObj.datPaymentPeriodVO.paymentperiodValue)
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
			billaccountId:billID
		},
		dataType : 'json',
		success : function(res) {
			var obj=eval(res.obj);
			var tbody=$('<tbody></tbody>');
			$(obj).each(function (index){
				var val=obj[index];
				resourceIdArray.push(val.baseresourceId);
				var tr=$('<tr></tr>');
					tr.append('<td>'+ formatterRes(val.baseResourceType) + '</td>' + '<td>'+ val.baseResourceCode + '</td>' +'<td>'+ val.baseResourceName + '</td>' +'<td>'+ formatterRes1(val.baseResourceState) + '</td>' + '<td><a href="#" onclick="deleteRes(this,\''+val.baseresourceId+'\');">删除</a></td><br/>');
				$("#resourcesBody").append(tr);
//				$('#resources').append(tbody);
			});
		},
		error : function() {
			alertModel("请求失败！");
		}
	});
} 

var resourceid;
function deleteRes(obj,baseresourceId){
	confirmModel('确定删除!','deleteResConfirm');
	resourceid = baseresourceId;
	$(obj).parents("tr").remove();
}
function deleteResConfirm(){
	$.ajax({
		url : 'deleteResourcePoint',// 跳转到 action
		type : 'post',
		data:{
			baseresourceId:resourceid
		},
		dataType : 'json',
		success : function(res) {
			alert("资源点已删除")
		},
		error : function() {
			alertModel("请求失败！");
		}
	});
	
	
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

function initCurrentPage(){
//	var paymenyMethod=queryDictType("PAYMENY_METHOD");//付款方式
	getAddress(false);
	queryBillAccountById();
	queryContAndSupByBillId();
	queryResource();
//	queryPaymentData();
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
            		case '0':return '正常';
            		case '9':return '终止';
            		case '-1':return '删除';
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
	queryContractAndSupplier();
//	queryPaymentData();
}

function queryContractAndSupplier(){
//	var regionId=rowschecked[0].datContractVO.sysRegionVO.regId
	$.ajax({
		url : 'queryContractAndSupplier',// 跳转到 action
		data : {
			rentcontractId : rowschecked[0].rentcontractId,
//			regId:regionId,
			},
		type : 'post',
		dataType : 'json',
		success : function(res) {
				var rentObj= res.obj;
				rentContractVO = rentObj;
				$("#rentcontractId").val(rentObj.rentcontractId);
				$("#totalAmount").val(rentObj.totalAmount);
				$("#contractCode").val(rentObj.datContractVO.contractCode);
				$("#contractName").val(rentObj.datContractVO.contractName);
				$("#contractStartdate").val(rentObj.datContractVO.contractStartdate);
				$("#contractEnddate").val(rentObj.datContractVO.contractEnddate);
				$("#contractSigndate").val(rentObj.datContractVO.contractSigndate);
				$("#contractYearquantity").val(rentObj.datContractVO.contractYearquantity);
				$("#citys").val(rentObj.datContractVO.pregName);
				$("#distract").val(rentObj.datContractVO.regName);
				$("#supplierCode").val(rentObj.datSupplierVO.supplierCode);
				$("#supplierName").val(rentObj.datSupplierVO.supplierName);
				queryPaymentperiodId();
		},
		error : function() {
			alertModel("请求失败！");
		}
	});
}
var rentContractVO;
//function queryPaymentData(){
//	$.ajax({
//		url : 'queryPaymentMethod',// 跳转到 action
//		data : {
//				billAccountId:billID,
//			   },
//		type : 'post',
//		dataType : 'json',
//		success : function(res) {
//				console.log(res);
//				var rentObj= res.obj;
//				$("#paymentMethod").val(rentObj.rentPaymentVO.paymentMethod);
//		},
//		error : function() {
//			alertModel("请求失败！");
//		}
//	});
//}
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
//			$('#resources tbody').remove();
			var obj=eval(res.obj);
//			var tbody=$('<tbody></tbody>');
			$(obj).each(function (index){
				var val=obj[index];
				var tr=$('<tr></tr>');
				tr.append('<td>'+"<input id='baseresourceId'"+index+" name='baseresourceId' type='hidden' value='"+ val.baseresourceId+ "'/>"+ formatterRes(val.baseResourceType) + '</td>' + '<td>'+ val.baseResourceCode + '</td>' +'<td>'+ val.baseResourceName + '</td>' +'<td>'+ formatterRes1(val.baseResourceState) + '</td>' + '<td><a href="#" onclick="deleteResources(this);">删除</a></td>');
				$("#resourcesBody").append(tr);
//				$('#resources').append(tbody);
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
	            billAccountId:billID,
		        regId :	$("#Village2 option:selected").val(),
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

function updateRebursePoint(){
	var resourceObjs = new Array();
	$('#resources input').each(function(){
		resourceObjs.push($(this).val());
	})
	$("#saveSet").attr("disable",true);
	$.ajax({
	    url:'updateBillAcount',
	    data: {
	    	billAccountCode:$("#billAccountCode").val(),
	    	billAccountId:billID,
	    	billAccountName:$("#billAccountName").val(),
	    	regId:$("#regId").val(),
	    	billAccountState:$("#billAccountState").val(),
	    	billAccountNote:$("#billAccountNote").val(),
	    	rentcontractId:$("#rentcontractId").val(),
	    	baseresourceIds:resourceObjs.join(","),
	    	
	    },
 		type : 'post',
	    cache:false,
	    async:true,
	    success:function(){
	    	window.location.href='reimbursePointManage.html';
	    	$("#saveSet").attr("disable",false);
	    },
	    error:function(){
	    	$("#saveSet").attr("disable",false);
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

function queryPaymentperiodId(){
	var id = null;
	if(rentContractVO != null){
		id = rentContractVO.paymentperiodId;
	}
	$.ajax({
		url : '../rentcontract_m/queryDatPaymentPeriod',   
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
					$('#paymentPeriodValue').empty();//删除之前的数据
					for (var i = 0; i < msg.length; i++) {
						if(id == msg[i].paymentperiodId){
							$("#paymentPeriodValue").val(msg[i].paymentperiodName);
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

