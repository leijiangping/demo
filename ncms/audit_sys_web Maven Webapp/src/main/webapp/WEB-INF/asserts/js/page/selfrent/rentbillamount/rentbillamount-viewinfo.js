/**
 * 租费报账汇总明细
 */
$(function(){
	loadInit();
});

//获取路径传值
var billamountId=null;
//加载查询条件
function loadInit(){
	//获取路径传值
	billamountId=getParameter("billamountId");
	
	searchBillamount();//加载租费报账汇总
}
//根据租费汇总ID获取租费报账汇总数据
function searchBillamount(){
	$.ajax({
		url : 'queryRentBillamountById', 
		type : 'post',
		cache : false,
		data:{billamountId:billamountId},
		dataType : 'json',
		success : function(result) {
			if (result != null&&result.success=="1") {
				setBillamountInfo(result.obj)
			}else{
				alertModel(result.msg);
			}
		},
		error : function() {
			alertModel("请求异常");
		}
	});
}
function setBillamountInfo(obj){
	$("#billamountCode").html(obj.billamountCode);
	$("#billamountDate").html(obj.billamountDate);
	$("#billamountWithtax").html(obj.billamountWithtax);
	$("#supplierCode").html(obj.supplierCode);
	$("#supplierName").html(obj.supplierName);
	$("#depositBank").html(obj.depositBank);
	$("#bankAccount").html(obj.bankAccount);

	$("#billamountDetail").html(obj.billamountDetail);
	
	$("#billamountDetail").html(obj.rentBillamountDetail.length);
	$("#billType").html(obj.billType);
	$("#City").html(obj.rentContractVO.datContractVO.sysRegionVO.regName);
	$("#Village").html(obj.rentContractVO.datContractVO.sysRegionVO.pRegName);
	
	loadTableData(obj.rentBillamountDetail);//加载汇总单明细表
}
//汇总单明细表
function loadTableData(data){
	// 先销毁表格
	$('#billamountDetailTable').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#billamountDetailTable").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryPaymentNyNoAmount", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		pageSize : ipageCount, // 每页显示的记录数
		pageNumber : curPageNum, // 当前第几页
		minimumCountColumns: 1,  //最少允许的列数
		clickToSelect: false,  //是否启用点击选中行
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
	            billamountId:$("#billamountId").val()
			};
			return param;
		},
		columns: [ {
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
            formatter : function(value){value.length}
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
function billamountDetailNum(billamountDetails){
	var num=0;
	$.each(billamountDetails,function(i,v){
		num+=v.billamountNumber;
	});
	return num;
}
//打印汇总单
function printBillamountPDF(){
	
}
//导出PDF汇总单
function exportBillamount(){
	
}
//导出缴费明细
function exportPaymentDatil(){
	window.open("exportPaymentDatil?billamountId="+billamountId,"_blank"); 
}
//完成
function finishBillamount(){

	location.href="rentbillamount.html";
	
}