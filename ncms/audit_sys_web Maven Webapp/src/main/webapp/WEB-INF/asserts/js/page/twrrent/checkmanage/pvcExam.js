

//省内考核指标扣罚

//已显示表格list
var showTableList = null;
var type;// 1 添加，2 修改
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
		url : "queryrAllPrvCheckIndexFine", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		pageSize : 20, // 每页显示的记录数
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
					regId:$('#City option:selected').val(),//地市id
					punishYearMonth:$('#datetimepicker').val(),//时间
					prvId:localStorage.getItem("prvId"),
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
            field: 'auditState',//-1:未提交\r\n    9:审核中\r\n    8:审核未通过\r\n      0:审核通过',
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
					/*window.location.href="pvcExam.html";*/
				}
				showTableList = res.Obj.result;
				console.log('showTableList');
				console.log(showTableList)
				//console.log(showTableList[0].twrProvincePunishId);
			}
	        return {
	            "total": res.Obj.total,//总页数
	            "rows": res.Obj.result, //数据
	         };
		}
	});	
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
 * 添加
 */
function addPvc(){
	type = 1;//添加
	window.location.href="addPvc.html?type="+type;
}
/**
 * 修改
 */
function updatePvc(){
	if(!hadCheckedRowData()){
		return;
	}

	type = 2;//修改
	
	var twrProvincePunishId=rowschecked[0].twrProvincePunishId;//主站id
	var regId=rowschecked[0].regId;
	if(rowschecked[0].auditState==9){
		alertModel("审核中的数据不能操作");
		return;
	}
	if(rowschecked[0].auditState==0){
		alertModel("审核通过的数据不能操作");
		return;
	}
	window.location.href="addPvc.html?type="+type+"&twrProvincePunishId="+twrProvincePunishId+"&regId="+regId;
	                                              
	
	
	
}

/**
 * 提交审核
 */
function submitCheck(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var state = rowschecked[0].state;
	if(state != 0){
		alertModel("此合同已经停用，无提交审核！");
		return;
	}
	if(rowschecked[0].auditState==9){
		alertModel("审核中的数据不能操作");
		return;
	}
	if(rowschecked[0].auditState==0){
		alertModel("审核通过的数据不能操作");
		return;
	}
	var chaeckObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		console.log(row);
		chaeckObjs.push(row.twrProvincePunishId);
		
	}
	
/*	$.ajax({
 		url :'checkCheckIndexFine',
 		data : JSON.stringify(chaeckObjs),
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
 	});	*/
	queryFirstUsersByProcDefKey(TwrRegPunish.procDefKey,"startActFlow");
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
		twrRegPunishIds.push(row.twrProvincePunishId);
	}
	//var twrRegPunishIds = JSON.parse(str);//把字符串转换成对象
	var nextUserId=$("input[name='userChecked']:checked").val();

	var datasObjs = new Array();
	for(var i = 0; i < twrRegPunishIds.length; i++){
		var id = twrRegPunishIds[i];
		var obj={"id":id,"nextUserId":nextUserId};
		datasObjs.push(obj);
	}

	$.ajax({
		url : 'checkCheckIndexFine',// 跳转到 action    
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
function deletePvc(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	if(rowschecked[0].auditState==9){
		alertModel("审核中的数据不能操作");
		return;
	}
	if(rowschecked[0].auditState==0){
		alertModel("审核通过的数据不能操作");
		return;
	}
	confirmModel("您确定要删除吗？","deleteOperation");
}

function deleteOperation(){
	var deleteuseObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		deleteuseObjs.push(row.twrProvincePunishId);
		//console.log(deleteuseObjs);
	}
 	$.ajax({
 		url : 'deleteCheckIndexFine',
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
 * 导出 全部导出
 */
function exportPvc(){
	confirmModel("您确定要导出吗？","exportOperation");
} 
function exportOperation(){
	/*var exportObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		exportObjs.push(row.twrProvincePunishId);
		console.log(exportObjs);
	}
	// 从选中行中挑出可以启用的item
	var prvCheckIds= JSON.stringify(exportObjs);
	console.log(prvCheckIds);
	window.open("export?prvCheckIds="+prvCheckIds,"_blank");*/
	debugger;
	var para="&regName="+ $('#City option:selected').val();
	para+="&punishYearMonth="+$('#datetimepicker').val();
	window.open("export?"+para,"_blank");
}


/**
 * 导入样例弹出导入选择文件框
 */
function importPvc(){
	//"考核管理单导入" 弹出框名称
	//"_towerBillbalance" 功能标识
	//"formSubmit" 回调方法
	importModel("考核管理单导入","_ExamPvc","formSubmit");//弹出导入框
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


/*function helpExamination(){
	
}
*/

