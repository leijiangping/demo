//已显示表格list
var showTableList = null;
var type = 1;// 1 添加，2 修改
$(document).ready(function() {
	initCurrentPage();
	getAddress();
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
		url : "queryTwrRegPunishList", // 获取数据的地址
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
					punishYearMonth : $("#datetimepicker").val(),
					regId : $("#City option:selected").val(),
					auditState : $("#auditState option:selected").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        },{
            field: 'punishName',
            title: '其他扣罚名称',
        	formatter:detailsFormatter
        },{
            field: 'punishAmount',
            title: '扣罚费用'
        },{
            field: 'punishPerson',
            title: '扣罚申请人'
        },{
            field: 'punishCause',
            title: '扣罚原因详细说明'	
        },{
        	field: 'sysRegionVO.pRegName',
            title: '所属地市'
        },{
            field: 'punishYearMonth',
            title: '扣罚日期'
        },{
            field: 'auditState',
            title: '审核状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 0:return '审核通过';
            		case 8:return '审核未通过';
            		case 9:return '审核中';
            		case -1:return '未提交';
            	}
            	return value;
            }
        },],
		onLoadError : function() { // 加载失败时执行
		},
		responseHandler: function(res) {
			
			if(res != null){//报错反馈
				
				if(res.success != "1"){
		            alertModel(res.msg.result);
				}
				showTableList = res.Obj.result;
			}
	        return {
	            "total": res.Obj.total,//总页数
	            "rows": res.Obj.result, //数据
	         };
		}
	});	
}

function detailsFormatter(value, row, index){
	return '<a href="cityExaminationDetails.html?twrRegPunishId='+row.twrRegPunishId+'&taskId='+row.taskId+'&auditState='+row.auditState+'">'+value+'</a>';
 }

/**
 * 添加
 */
function insertExamination(type){
	window.location.href='cityExaminationNews.html?type='+type;
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
	var auditState = rowschecked[0].auditState;
	if(auditState == 0){
		alertModel("审核通过的无法修改！");
		return;
	}
	if(auditState == 9){
		alertModel("审核中的无法修改！");
		return;
	}
	var twrRegPunishId=rowschecked[0].twrRegPunishId;
	var auditState=rowschecked[0].auditState;
	window.location.href='cityExaminationNews.html?type='+type+"&twrRegPunishId="+twrRegPunishId+"&auditState="+auditState;
	
}

/**
 * 提交审核
 */
function submitAudit(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var stateObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		stateObjs.push(row.auditState);
	}
	// 判断是否有"审核中"的
	if($.inArray(9, stateObjs)>=0){
		alertModel("审核中的无法提交审核");
		return false;
	}
	// 判断是否有"审核通过"的
	if($.inArray(0, stateObjs)>=0){
		alertModel("审核通过的无法提交审核");
		return false;
	}
	var regId=rowschecked[0].regId;
	queryFirstUsersByProcDefKey(TwrRegPunish.procDefKey,"startActFlow",null);

}

/**
 * 提交流程，开始审核流程
 */
function startActFlow(){
	$('#firstUsers').modal("hide");
	var twrRegPunishIds = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		twrRegPunishIds.push(row.twrRegPunishId);
	}
	var nextUserId=$("input[name='userChecked']:checked").val();

	var datasObjs = new Array();
	for(var i = 0; i < twrRegPunishIds.length; i++){
		var id = twrRegPunishIds[i];
		var obj={"id":id,"nextUserId":nextUserId};
		datasObjs.push(obj);
	}

	$.ajax({
		url : 'updateTwrRegPunishStartFlow',// 跳转到 action    
		data :JSON.stringify(datasObjs),
		type : 'post',
		dataType : 'json',
		contentType : 'application/json;charset=utf-8',
		success : function(res) {
			if (res != null) {
				alertModel(res.msg);
				loadTableData();
			}
			$('#EditPanel').modal('hide');
			
		},
		error : function() {
			alertModel("请求失败");
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
	confirmModel("您确定要删除吗？","deleteOperation");
}

function deleteOperation(){
	var deleteuseObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		deleteuseObjs.push(row.twrRegPunishId);
	}
 	$.ajax({
 		url : 'deleteTwrRegPunish',
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
	var para="accountSummaryId="+$("#City option:selected").val();
	para+="&punishYearMonth="+$("#datetimepicker").val();
	window.open("export?"+para,"_blank");
}


/**
 * 导入样例弹出导入选择文件框
 */
function importExamination(){
	//"考核管理单导入" 弹出框名称
	//"_towerBillbalance" 功能标识
	//"formSubmit" 回调方法
	importModel("考核管理单导入","_cityExamination","formSubmit",true);//弹出导入框
}	
/**
 * 上传文件并保存到数据库
 * @param suffix 模块标识
 */
function formSubmit(suffix){
	var formData = new FormData($("#dataForm")[0]); 
	$.ajax({
		url:'importFile',
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
 * 帮助
 */

function helpExamination(){
	
}


