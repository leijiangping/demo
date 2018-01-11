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

function loadTableData() {
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryRembursePointVO", // 获取数据的地址
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
					billAccountCodeOrName : 	$('#billAccountCodeOrName').val(),
		            regId :	$("#Village option:selected").val(),
//		            billaccountAuditState :	$("#billaccountAuditState option:selected").val(),
		            taskDefKey:$("#taskDefKey").val()
			};
			return param;
		},
		columns: [{
			checkbox: true
		}, {
			field: 'auditState',
			title: '审批状态',
			formatter:function(value,row,index){
				switch(value){
				case 1:return '已审核';
        		case 9:return '审核中';
        		case -1:return '未审核';
        		default:return '/';
				}
				return value;
			}
		}, {
            field: 'billAccountCode',
            title: '报账点编码'
        },  {
			field: 'billAccountName',
			title: '报账点名称'
		}, {
			field: 'sysRegionVO.pRegName',
			title: '所属地市'
		}, {
			field: 'sysRegionVO.regName',
			title: '所属区县'
		}, {
			field: 'billAccountState',
			title: '报账点状态',
			formatter:function(value,row,index){
				switch(value){
				case 0:return '正常';
				case 9:return '终止';
				}
				return value;
			}
		}, /*{
            field: 'datContractVO.contractStartdate',
            title: '报账点资源关系'
        },*/ ],
        onLoadError : function() { // 加载失败时执行
        	console.log("请求失败！");
        },
        responseHandler: function(res) {
        	if(res != null){//报错反馈
        		showTableList = res.obj.result;
        		unique(showTableList);
        	}
        	return {
        		"total": res.obj.total,//总页数
        		"rows": res.obj.result //数据
        	};
        }
	});
} 
//加载不重复的流程环节
function unique(arr){
	// 遍历arr，把元素分别放入tmp数组(不存在才放)
	$("#taskDefKey").empty();
	$("#taskDefKey").append("<option value=''>---审核环节---</option>");
	
	var tmp = new Array();
	for(var i in arr){
		//该元素在tmp内部不存在才允许追加
		if(arr[i].act!=null&&tmp.indexOf(arr[i].act.taskDefKey)==-1){
			$("#taskDefKey").append("<option value='"+arr[i].act.taskDefKey+"'>"+arr[i].act.taskName+"</option>");
			tmp.push(arr[i].act.taskDefKey);
		}
	}
	return tmp;
}
function haveCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}
function AuditRembursePoint(){
	if(!haveCheckedRowData()){
		return false;
	}
	var billAccountId=rowschecked[0].billAccountId;
	var taskId=rowschecked[0].act.taskId;
//	localStorage.setItem("item1",rowschecked[0].billAccountId);
	window.location.href='auditInfo.html?billAccountId='+billAccountId+'&taskId='+taskId;
}
/*function exportExcel(){
	confirmModel("您确定要导出吗？","exportRembursePointInfo");
}
function exportRembursePointInfo(){
	var para="billaccountAuditState="+$("#billaccountAuditState option:selected").val();
	para+="&regId="+ $("#Village option:selected").val();
	window.open("exportRembursePointInfo?"+para,"_blank");
}*/
