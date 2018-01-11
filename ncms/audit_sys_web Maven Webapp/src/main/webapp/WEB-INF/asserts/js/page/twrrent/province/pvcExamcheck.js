//已显示表格list
var showTableList = null;

$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	curPageNum = 1;
	// 查询默认条件表单数据
	loadTableData();
}
function loadTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "", // 获取数据的地址
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
					accountPeroid : $("#datetimepicker").val(),
					operatorRegId : $("#City option:selected").val(),
			};
			return param;
		},
		columns: [{
            checkbox: true
        },{
        	field: 'operatorRegId',
            title: '所属地市'
        },{
            field: 'preId',
            title: '所属区县'
        },{
            field: 'accountPeroid',
            title: '年/月份'
        },{
            field: 'debitName',
            title: '其他扣罚名称'
        },{
            field: 'debitCost',
            title: '扣罚费用'
        },{
            field: 'debitProposer',
            title: '扣罚申请人'
        },{
            field: 'debitNote',
            title: '扣罚原因详细说明'
        },],
		/*onLoadSuccess : function() { // 加载成功时执行
			console.log("加载成功");
		},*/
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success != "1"){
		            alertModel(res.msg.result);
				}
				showTableList = res.Obj.result;
				console.log(showTableList);
			}
	        return {
	            "total": res.Obj.total,//总页数
	            "rows": res.Obj.result, //数据
	         };
		}
	});	
}

/**
 * 审核
 */
function auditExamination(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
}
/**
 * 批量审核
 */
function batchAuditExamination(){
	
}

/**
 * 帮助
 */

function helpExamination(){
	
}


