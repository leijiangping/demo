

//省内考核指标扣罚

//已显示表格list
var showTableList = null;
var type = 1;// 1 添加，2 修改
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
        },
        {
            field: 'state',
            title: '审核状态'
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
 * 添加
 */
function insertExamination(type){
	type = 1;//添加
	window.location.href="cityExaminationNews.html?type="+type;
}

/**
 * 选中的条数
 */
function hadCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}
/**
 * 修改
 */
function updateExamination(type){
	if(!hadCheckedRowData()){
		return;
	}
	type = 2;//修改
	var datas="operatorRegId="+rowschecked[0].operatorRegId;
	datas+="&accountPeroid="+rowschecked[0].accountPeroid;
	datas+="&preId="+rowschecked[0].preId;
	datas+="&debitName="+rowschecked[0].debitName;
	datas+="&debitCost="+rowschecked[0].debitCost;
	datas+="&debitProposer="+rowschecked[0].debitProposer;
	datas+="&debitNote="+rowschecked[0].debitNote;
	console.log(datas);
	window.location.href="cityExaminationNews.html?type="+type+"&datas="+datas;	
}

/**
 * 提交审核
 */
function submitAudit(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var state = rowschecked[0].state;
	if(state != 0){
		alertModel("此合同已经停用，无提交审核！");
		return;
	}
	
	$.ajax({
 		url : '',
 		data : {
 			id:rowschecked[0].id
 		},
		type : 'post',
		cache : false,
		dataType : 'json',
		contentType: "application/json;charset=utf-8",
 		success : function(data) {
 			if (data != null) {
 				loadTableData();
 				alertModel("提交审核成功！");
 			}
 		},
 		error : function() {
			alertModel("请求失败！");
 		}
 	});	
}

/**
 * 删除
 */
function deleteExamination(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	confirmModel("您确定要导出吗？","deleteOperation");
}

function deleteOperation(){
	var deleteuseObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		deleteuseObjs.push(row.id);
		console.log(deleteuseObjs);
	}
 	$.ajax({
 		url : '',
 		data : JSON.stringify(deleteuseObjs),
		type : 'post',
		cache : false,
		dataType : 'json',
		contentType: "application/json;charset=utf-8",
 		success : function(data) {
 			if (data != null) {
 				loadTableData();
				alertModel(data.msg);
 			}
 		},
 		error : function() {
			alertModel("请求失败！");
 		}
 	});
}



/**
 * 导出
 */
function exportExamination(){
	confirmModel("您确定要导出吗？","exportOperation");
} 
function exportOperation(){
	// 从选中行中挑出可以启用的item
	if($("#educeStyle").val() == 1){			
		//导出当前页数据
		var currentExportObjs = new Array();
		var currentRows=$('#tb').bootstrapTable('getData');
		$.each(currentRows,function(i,v){
			currentExportObjs.push(v.id);
			console.log(currentExportObjs);
		});			
		var ids = currentExportObjs.join(",");			
	}else if($("#educeStyle").val() == 2){
		if(!isChecked()){
			alertModel("请先选择一条数据再操作");
			return;
		}
		//导出所选数据
		var exportObjs = new Array();
		var selectRows=$('#tb').bootstrapTable('getSelections');
		$.each(selectRows,function(i,v){
			exportObjs.push(v.id);
			console.log(exportObjs);
		});
		var ids = exportObjs.join(",");
	}

	var para="operatorRegId="+$("#City").val();
	para+="&accountPeroid="+$("#datetimepicker").val();
	window.open("export?"+para+"&ids="+ids,"_blank");
}


/**
 * 导入样例弹出导入选择文件框
 */
function importBill(){
	//"考核管理单导入" 弹出框名称
	//"_towerBillbalance" 功能标识
	//"formSubmit" 回调方法
	importModel("考核管理单导入","_towerBillbalance","formSubmit");//弹出导入框
}	
/**
 * 上传文件并保存到数据库
 * @param suffix 模块标识
 */
function formSubmit(suffix){
	var formData = new FormData($("#dataForm")[0]); 
	$.ajax({
		url:'',
		type : 'post',
		data : formData,
	    async: true,  
     	cache: false,  
        contentType: false,  
        processData: false, 
 		beforeSend:function(){//启动a
 			startTimeJob(suffix);
        },  
 		success : function(result){
			stopTimeJob();//停止进度条
 			if(result != null&&result.success=="1"){
 					loadTableData();
 			}else if(result != null&&result.success=="0"){
 				//出错
 			}
				alertModel(result.msg);
 		},
        complete:function(){//ajax请求完成时执行    
			stopTimeJob();//停止进度条
        },
 		error : function() {
			stopTimeJob();//停止进度条
			alertModel("请求失败！");
 		}
	});
}
/**
 * 导入样例
 */
function updateImportBill(){
	importModel("考核管理单导入","_towerBillbalance","formSubmitUpdate");//弹出导入框
}
/**
 * 上传文件并保存到数据库
 * @param suffix 模块标识
 */
function formSubmitUpdate(suffix){
	var formData = new FormData($("#dataForm")[0]); 
	$.ajax({
		url:'',
		type : 'post',
		data : formData,
	    async: true,  
     	cache: false,  
        contentType: false,  
        processData: false, 
 		beforeSend:function(){//启动a
 			startTimeJob(suffix);
        },  
 		success : function(result){
			stopTimeJob();//停止进度条
 			if(result != null&&result.success=="1"){
 					loadTableData();
 					alertModel(result.msg);
 			}
 		},
        complete:function(){//ajax请求完成时执行    
			stopTimeJob();//停止进度条
        },
 		error : function() {
			stopTimeJob();//停止进度条
			alertModel("请求失败！");
 		}
	});
}


/**
 * 帮助
 */

function helpExamination(){
	
}


