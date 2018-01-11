//已显示表格list
var showTableList = null;

var operate_type = 1;// 1 新增，2 修改

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
		url : "queryAllWinPress", // 获取数据的地址
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
					windpressureState : $("#windState option:selected").val(),
					windpressureName : $("#windName").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
        	field: 'windpressureName',
            title: '名称'
        }, {
            field: 'windpressureUpper',
            title: '上限'
        },{
            field: 'windpressureLower',
            title: '下限'
        },{
            field: 'windpressureState',
            title: '状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 9:return '停用';
            		case 0:return '启用';
            	}
            	return value;
            }
        },{
            field: 'windpressureNote',
            title: '备注'
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

//
function searchTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryWinPressByCondition", // 获取数据的地址
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
					windpressureState : $("#windState option:selected").val(),
					windpressureName : $("#windName").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
        	field: 'windpressureName',
            title: '名称'
        }, {
            field: 'windpressureUpper',
            title: '上限'
        },{
            field: 'windpressureLower',
            title: '下限'
        },{
            field: 'windpressureState',
            title: '状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 9:return '停用';
            		case 0:return '启用';
            	}
            	return value;
            }
        },{
            field: 'windpressureNote',
            title: '备注'
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
				//console.log(showTableList);
			}
	        return {
	            "total": res.Obj.total,//总页数
	            "rows": res.Obj.result, //数据
	         };
		}
	});
	
}

function insertCost(){
	operate_type = 1;// 新增
	
	$("#dataForm")[0].reset();	//清空表单
	$('#EditCost').modal();	//弹出表单
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
function updateCost(){
	if(!hadCheckedRowData()){
		return;
	}
	operate_type = 2;// 修改
	$("#windpressureName").val(rowschecked[0].windpressureName);
	$("#windpressureUpper").val(rowschecked[0].windpressureUpper);
	$("#windpressureLower").val(rowschecked[0].windpressureLower);
	$("#windpressureState").val(rowschecked[0].windpressureState);
	$("#windpressureNote").val(rowschecked[0].windpressureNote);
	$('#EditCost').modal();
}
//验证
function validform(){

	var public_validate= bindformvalidate("#dataForm", {
		windpressureName : {
			required : true,
			maxlength : 20,
		},
		windpressureUpper:{
			required : true,
			min:0,
		},
		windpressureLower:{
			required : true,
			min:0,
		},
		
		
	}, {
		windpressureName:{
			required : "请输入名称",
			maxlength: "铁塔名称最大长度20"
		},
		windpressureUpper : {
			required : "请输入上限值",
			min:"只能输入数字"
		},
		windpressureLower:{
			required : "请输入下限值",
			min:"只能输入数字"
		},
		
	});
  return public_validate;
}

function public_close(){
	removeError('dataForm');
}
function formSubmit(){
	if(validform().form()){
	var windpressureName=$('#windpressureName').val();
	var windpressureUpper=$('#windpressureUpper').val();
	var windpressureLower=$('#windpressureLower').val();
	var windpressureNote=$('#windpressureNote').val();
	
	if(windpressureUpper <= windpressureLower){
		alertModel("下限值不可以大于等于上限值！");
		return false;
	}
	
	if(operate_type==1){
		$("#saveSet").attr("disabled",true);
		$.ajax({
		    url:'insertWinPress',
		    data: {
		    	windpressureName:windpressureName,
		    	windpressureUpper:windpressureUpper,
		    	windpressureLower:windpressureLower,
		    	windpressureNote:windpressureNote
		    },
	 		type : 'post',
		    cache:false,
		    async:true,
		    success:function(result){
		    	console.log(result);
		        //请求成功时
		    	if(result!=null){
	    			alertModel(result.msg);
	    			loadTableData();
		    	}
    			$('#EditCost').modal('hide');
    			$("#saveSet").attr("disabled",false);
		    },
		    error:function(){
		    	alertModel("请求失败！");
		    	$("#saveSet").attr("disabled",false);
		    }
		})
	}
	else if(operate_type==2){
		$("#saveSet").attr("disabled",true);
		$.ajax({
		    url:'updateWinPress',
		    data: {
		    	windpressureName:$('#windpressureName').val(),
		    	windpressureUpper:$('#windpressureUpper').val(),
		    	windpressureLower:$('#windpressureLower').val(),
		    	windpressureNote:$('#windpressureNote').val(),
		    	windpressureId:rowschecked[0].windpressureId
		    },
	 		type : 'post',
		    cache:false,
		    async:true,
		    success:function(result){
		        //请求成功时
		    	if(result!=null){
	    			//alertModel(result.msg);
	    			loadTableData();
		    	}
    			$('#EditCost').modal('hide');
    			$("#saveSet").attr("disabled",false);
		    },
		    error:function(){
				alertModel("请求失败！");
				$("#saveSet").attr("disabled",false);
		    }
		});
	}
  }
}


/**
 * 删除
 */
function deleteCost(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	if(confirm("确认删除吗？"))
	{
		var deleteuseObjs = new Array();
		// 从选中行中挑出可以启用的item
		for (var s = 0; s < rowschecked.length; s++) {
			var row = rowschecked[s];
			deleteuseObjs.push(row.windpressureId);
			//console.log(deleteuseObjs);
		}
     	$.ajax({
     		url : 'deleteWinPress',
     		data : JSON.stringify(deleteuseObjs),
			type : 'post',
			cache : false,
			dataType : 'json',
			contentType: "application/json;charset=utf-8",
     		success : function(data) {
     			if (data != null) {
     				loadTableData();
    				//alertModel(data.msg);
     			}
     		},
     		error : function() {
    			alertModel("请求失败！");
     		}
     	});
	}
}
/**
 * 启用
 */
function openCost(type){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var openuseObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		openuseObjs.push(row.windpressureId);
		//openstateObjs.push(row.windpressureState,row.windpressureState);
		console.log(openuseObjs);
	}
 	$.ajax({
 		url : 'startOrStopWinPress?windpressureState='+type,
 		data : JSON.stringify(openuseObjs),
 		type : 'post',
		cache : false,
 		dataType : 'json',
		contentType: "application/json;charset=utf-8",
 		success : function(back) {
 			if (back != null) {
 				loadTableData();
				alertModel(back.msg);
 			}
 		},
 		error : function() {
			alertModel("请求失败！");
 		}
 	});
}
/**
 * 停用
 * */
function stopCost(type){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var stopuseObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		stopuseObjs.push(row.windpressureId);
		console.log(stopuseObjs);
	}
 	$.ajax({
 		url : 'startOrStopWinPress?windpressureState='+type,
 		data : JSON.stringify(stopuseObjs),
 		type : 'post',
		cache : false,
 		dataType : 'json',
		contentType: "application/json;charset=utf-8",
 		success : function(back) {
 			if (back != null) {
 				loadTableData();
				alertModel(back.msg);
 			}
 		},
 		error : function() {
			alertModel("请求失败！");
 		}
 	});
} 

