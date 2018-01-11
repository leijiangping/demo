$(function(){
	loadSearch();
});
//加载查询条件
function loadSearch(){
	loadTableData();
	getAddress();
	queryDatSupplierByPrvID();//查询省份对应的供应商
	querDictBillType();//加载报账类型
	querDictPaymenyMethod();//加载付款方式
	
}
//验证查询条件
function validateSeachParam(){
	var prvId= $("#City").val();
	var pregId=$("#Village").val();
	var supplierId=$("#supplierId").val();
	var billType=$("#billType").val();
	if(prvId!=null&&prvId!=""){
		alertModel("请选择地市！");
		return;
	}
	if(pregId!=null&&pregId!=""){
		alertModel("请选择县区!");
		return;
	}
	if(supplierId!=null&&supplierId!=""){
		alertModel("请选择供应商！");
		return;
	}
	if(billType!=null&&billType!=""){
		alertModel("请选择报账类型");
		return;
	}
}
//租费合同汇总
function loadTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		//url : "queryPaymentNyNoAmount", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		pageSize : ipageCount, // 每页显示的记录数
		pageNumber : curPageNum, // 当前第几页
		minimumCountColumns: 1,  //最少允许的列数
		clickToSelect: true,  //是否启用点击选中行
		pageList : [10, 25, 50, 100, 500], // 记录数可选列表
		search : false, // 是否启用查询
		sidePagination : "server", // 表示服务端请求,
		columns: [{
            checkbox: true
        }, {
            field: 'contractCode',
            title: '合同编码'
        }, {
            field: 'baseresourceName',
            title: '资源名称'
        }, {
        	field: 'payCalcamount',
            title: '应付金额'
        }, {
        	field: 'billamountTaxamount',
            title: '税率'
        }, {
            field: 'billamountTaxratio',
            title: '税金'
        }, {
            field: 'rentPaymentdetails',
            title: '缴费数量',
            formatter : function(value){
            	return value.length;
            }
        }, {
            field: 'payActamount',
            title: '实付金额'
        }, {
            field: 'supplierCode',
            title: '供应商编码'
        }, {
            field: 'paymentStartdate',
            title: '缴费期始',
            formatter : function(value){
            	return new Date(value).format("yyyy-MM-dd");
            }
        }, {
            field: 'paymentEnddate',
            title: '缴费期终',
            formatter : function(value){
            	return new Date(value).format("yyyy-MM-dd");
            }
        }, {
            field: 'paymentDate',
            title: '报账日期',
            formatter : function(value){
            	return new Date(value).format("yyyy-MM-dd");
            }
        }
        ]
	});
	
}
//点击查询触发
function searchTableData(){
	/**
	//验证查询条件
	var prvId= $("#City").val();
	var pregId=$("#Village").val();
	var supplierId=$("#supplierId").val();
	var billType=$("#billType").val();
	if(prvId==null||prvId==""||prvId=="null"){
		alertModel("请选择地市！");
		$("#City").focus();
		return false;
	}
	if(pregId==null||pregId==""||pregId=="null"){
		alertModel("请选择县区!");
		$("#Village").focus();
		return false;
	}
	if(supplierId==null||supplierId==""||supplierId=="null"){
		alertModel("请选择供应商！");
		$("#supplierId").focus();
		return false;
	}
	if(billType==null||billType==""||billType=="null"){
		alertModel("请选择报账类型");
		$("#billType").focus();
		return false;
	}
	**/
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryPaymentNyNoAmount", // 获取数据的地址
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
	            prvId	:	$("#City").val(),
	            pregId :	$("#Village").val(),
	            supplierId :	$("#supplierId").val(),
	            billType :	$("#billType").val(),
	            paymentMethod:$("#paymentMethod").val(),
	            paymentDate:$("#paymentDate").val(),
	            rentcontractCode:$("#rentcontractCode").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
            field: 'contractCode',
            title: '合同编码'
        }, {
            field: 'baseresourceName',
            title: '资源名称'
        }, {
        	field: 'payCalcamount',
            title: '应付金额'
        }, {
        	field: 'billamountTaxamount',
            title: '税率'
        }, {
            field: 'billamountTaxratio',
            title: '税金'
        }, {
            field: 'rentPaymentdetails',
            title: '缴费数量',
            formatter : function(value){
            	return value.length;
            }
        }, {
            field: 'payActamount',
            title: '实付金额'
        }, {
            field: 'supplierCode',
            title: '供应商编码'
        }, {
            field: 'paymentStartdate',
            title: '缴费期始',
            formatter : function(value){
            	return new Date(value).format("yyyy-MM-dd");
            }
        }, {
            field: 'paymentEnddate',
            title: '缴费期终',
            formatter : function(value){
            	return new Date(value).format("yyyy-MM-dd");
            }
        }, {
            field: 'paymentDate',
            title: '报账日期',
            formatter : function(value){
            	return new Date(value).format("yyyy-MM-dd");
            }
        }
        ],	
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
		},
		onCheck: function (row) {
			tableCheckedChange(row);
        },
        onUncheck: function (row) {
        	tableCheckedChange(row);
	    },
	    onCheckAll: function (row) {
	    	tableCheckedChange(row);
		},
		onUncheckAll: function (row) {
			tableCheckedChange(row);
		}
	});
}
//查询省份对应的供应商
function queryDatSupplierByPrvID(){
	$.ajax({
		url : projectName+'/rent/common/queryDatSupplierByPrvID', 
		type : 'post',
		cache:false,
		async:false,
		contentType:"application/json",
		dataType : 'json',
		success : function(result) {
			if (result != null&&result.success=="1") {
				appendSelect("supplierId",result.obj,"supplierId","supplierName","","供应商");
			}else{
				alertModel(result.msg);
			}
		},
		error:function() {
			alertModel("请求异常");
		}
	});
	
}
//加载报账类型
function querDictBillType(){
	var billType=queryDictType("BILL_TYPE");
	appendSelect("billType",billType,"dict_id","dict_name","","报账类型");
	
}
//加载付款方式
function querDictPaymenyMethod(){
	var paymenyMethod=queryDictType("PAYMENY_METHOD");
	appendSelect("paymentMethod",paymenyMethod,"dict_id","dict_name","","付款方式");
	
}

function tableCheckedChange(){
	var rows= $("#tb").bootstrapTable('getSelections');
	$("#checkedNum").html(rows.length);
	var checkedAmount=0;
	$.each(rows,function(i,row){
		checkedAmount+=row.payActamount;
	});
	$("#checkedAmount").html(checkedAmount);
}
//生成汇总单
function createBillamount(){
	var rows= $("#tb").bootstrapTable('getSelections');
	if(rows.length==0){
		alertModel("请选则至少一条数据");
		return;
	}
	var ids=new Array();
	$.each(rows,function(i,row){
		ids.push(row.paymentId);
	});
	$.ajax({
		url : 'createRentBillamount', 
		type : 'post',
		cache : false,
		data:JSON.stringify(ids),
		contentType:"application/json", 
		dataType : 'json',
		success : function(result) {
			if (result != null&&result.success=="1") {
				location.href="summary_sheet.html?billamountId="+result.obj.billamountId;
			}else{
				alertModel(result.msg);
			}
		},
		error : function() {
			alertModel("请求异常");
		}
	});
}
//完成
function finishBillamount(){

	location.href="billamount.html";
	
}