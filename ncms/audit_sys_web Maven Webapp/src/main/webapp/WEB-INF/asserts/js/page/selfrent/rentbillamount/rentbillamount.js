$(function(){
	loadTableData();
	loadSearch();
});
//加载查询条件
function loadSearch(){
	getAddress();
}
//租费合同汇总
function loadTableData() {
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryRentamount", // 获取数据的地址
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

	            billamountCode:$('#billamountCode').val(),
	            prvId:$("#City").val(),
	            pregId:$("#Village").val(),
	            billamountDateStart:$("#billamountDateStart").val(),
	            billamountDateEnd:$("#billamountDateEnd").val(),

	            billamountState:$("#billamountState").val(),
	            rentcontractCode:$("#rentcontractCode").val(),//合同名称或者合同代码
	            supplierCode:$("#supplierCode").val()//供应商代码或者名称
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
            field: 'billamountCode',
            title: '汇总单编码',
            formatter:billamountCodeFormatter
        }, {
            field: 'billamountDate',
            title: '汇总日期',
            formatter : function(value){
            	return new Date(value).format("yyyy-MM-dd");
            }
        }, {
        	field: 'billamountState',
            title: '推送状态',
            formatter:function(value){
            	switch(value){
            		case '1':return '已推送';
            		case '9':return '已报账';
            		case '-1':return '未推送';
            		default:return "未推送";
            	}
            	return value;
            }
        }, {

        	field: 'rentBillamountDetail',
            title: '付款数量',
            formatter : billamountDetailNum
        }, {

            field: 'billamountWithtax',
            title: '应付金额'
        }, {
            field: 'rentContractVO.datContractVO.sysRegionVO.regName',
            title: '地市'
        }, {
            field: 'rentContractVO.datContractVO.sysRegionVO.pRegName',
            title: '区域'
        }, {
            field: 'supplierCode',
            title: '供应商编码'
        }, {
            field: 'supplierName',
            title: '供应商名称'
        }
        ],
        
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
function billamountDetailNum(billamountDetails){
	var num=0;
	$.each(billamountDetails,function(i,v){
		num+=v.billamountNumber;
	});
	return num;
}
//生成汇总单
function createBillamount(){
	location.href="create_summary_sheet.html?v=2";
	
}
//推送汇总单
function pushBillamount(){
	
}
//查看待汇总缴费
function viewBillamount(){
	
}
function billamountCodeFormatter(value, row, index){
	return '<a href="summary_sheet.html?billamountId='+row.billamountId+'">'+value+'</a>';
}