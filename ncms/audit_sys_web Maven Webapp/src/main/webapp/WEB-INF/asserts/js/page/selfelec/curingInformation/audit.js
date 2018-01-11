//已显示表格list
var showTableList = null;

$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	curPageNum = 1;
	getAddress();
	loadTableData();
}
//条件查询
function loadTableData() {
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryAll", // 获取数据的地址
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
					contractItem : $('#contractName').val(),
					pregId :	$("#city option:selected").val(),
					regId :$("#region option:selected").val(),
		            contractState :	$("#contractState option:selected").val(),
		            auditState : 9,
		            prvId:localStorage.getItem("prvId"),
		            pageNum: params.pageNumber,    
					pageSize: params.pageSize,
					taskDefKey:$("#taskDefKey").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        },{
            field: 'contractCode',
            title: '固化信息编码',
            formatter:clickFormatter
        }, {
            field: 'contractName',
            title: '固化信息名称'
        }, {
            field: 'pregName',
            title: '所属地市'
        }, {
            field: 'regName',
            title: '所属区县'
        }, {
        	field: 'isDownshare',
            title: '是否向下共享',
            formatter:function(value,row,index){
            	switch(value){
        		case 1:return '是';
        		case 0:return '否';
        	}
        	return value;
            }
        }, {
        	field: 'contractState',
            title: '固化信息状态',
            formatter: fmtContractState
        }, {
            field: 'auditingState',
            title: '审核状态',
            formatter: fmtAuditState
        }, {
            field: 'contractStartdate',
            title: '固化信息期始',
            formatter : function(value){
            	return new Date(value).format("yyyy-MM-dd");
            }
        }, {
            field: 'contractEnddate',
            title: '固化信息终止',
        	formatter : function(value){
        		return new Date(value).format("yyyy-MM-dd");
            }
        }],
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success != "1"){
		            alertModel(res.msg);
				}
				showTableList = res.Obj.result;
			}
	        return {
	            "total": res.Obj.total,//总页数
	            "rows": res.Obj.result //数据
	         };
		}
	});
}

//审核
function auditData(){
	var rows = getSelectItem(1);
	if(rows == null){
		return;
	}
	var auditState = rows[0].auditingState;
	if(auditState != 9){
		alertModel("此固化信息无法审核！");
		return;
	}
	var id = rows[0].contractId;
	var taskId=rows[0].act.taskId;		
	window.location.href='elecContractAuditNews.html?contractId='+id+'&taskId='+taskId;
}

//导出
function exportData(){
	var para="reg="+$('#contractName').val()+"&city="+$("#city option:selected").val();
	para+="&region="+$("#region option:selected").val()+"&status="+$("#contractState option:selected").val()+"&opType=2";//&auditStatus="+$("#auditState option:selected").val()+"
	window.open("export?"+para);
	
}
function hadCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}

/** 
 * add by jiacy
 * 查看用户信息
 */
function viewRecord(obj) {
	var elecId=$(obj).parent().next().text();
	window.location.href='view.html?elecContractId='+elecId;
}

function clickFormatter(value, row, index){
	return '<a href="view.html?elecContractId='+row.elecontractId+'">'+value+'</a>';
}
