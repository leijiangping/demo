
//省内自设考核指标审核



//已显示表格list
var showTableList = null;

$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	curPageNum = 1;
	// 查询默认条件表单数据
	loadTableData();
	getAddress();
}
function loadTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryCheckIndexFineInfo", // 获取数据的地址
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
					pageNum: params.pageNumber,    
					pageSize: params.pageSize,
					punishYearMonth : $("#datetimepicker").val(),
					regId:$("#City").val(),
					prvId:localStorage.getItem("prvId"),
					auditState:9,
					taskDefKey:$("#taskDefKey").val(),
			};
			return param;
		},
		columns: [{
            checkbox: true
        },{
        	field: 'regName',
            title: '所属地市'
        },{
            field: 'punishYearMonth',
            title: '扣罚时间'
        },{
            field: 'punishName',
            title: '扣罚指标'
        },{
            field: 'punishCause',
            title: '扣罚原因'
        },{
            field: 'punishPerson',
            title: '扣罚申请人'
        },{
            field: 'punishAmount',
            title: '扣罚金额'
        },
        {
            field: 'auditState',//-1:未提交\r\n            9:审核中\r\n            8:审核未通过\r\n            0:审核通过',
            title: '审核状态',
            	 formatter:function(value,row,index){
                 	switch(value){
                 		case 9:return '审核中';
                 		case 8:return '审核未通过';
                 		case -1:return '未提交';
                 		case 0:return '审核通过';
                 	}
                 	return value;
                 }
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
function check(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	
	/*var billaccountcontractId=rowschecked[0].billaccountcontractId;
	localStorage.setItem("item1",rowschecked[0].paymentId);
	localStorage.setItem("billaccountId",rowschecked[0].billaccountId);*/
	var twrProvincePunishId=rowschecked[0].twrProvincePunishId;//主站id
	var taskId=rowschecked[0].act.taskId;
	console.log(taskId)
	window.location.href='checkinner.html?twrProvincePunishId='+twrProvincePunishId+'&taskId='+taskId;
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


