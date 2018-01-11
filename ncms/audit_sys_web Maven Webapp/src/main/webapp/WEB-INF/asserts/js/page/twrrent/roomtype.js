//已显示表格list
var showTableList = null;

var operate_type = 1;// 1 新增，2 修改

$(document).ready(function() {
	initCurrentPage();
});

function initCurrentPage(){
/*	curPageNum = 1;*/
	// 查询默认条件表单数据
	loadTableData();
}
/**
 * 默认条件表单数据
 * 
 */
function loadTableData(){
	$("#tb").css("font-size","14px");
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryAllRoomType", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		pageSize : ipageCount, // 每页显示的记录数
		pageNum : curPageNum, // 当前第几页
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
					commTypeGroup:$("#commTypeGroup").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
        	field: 'commtype_name',
            title: '机房名称'
        },{
            field: 'commtype_state',
            title: '状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 9:return '停用';
            		case 0:return '启用';
            	}
            	return value;
            }
        },{
            field: 'commtype_note',
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
			/*	if(res.success != "1"){
		            alertModel(res.msg.result);
				}*/
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
 * 根据不同条件查询
 */
function searchTableData(){
	var commTypeName= $('#commTypeName').val();
	var commTypeState= $('commTypeState').val();;
	$("#tb").css("font-size","14px");
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryAllRoomTypeByCondition", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		pageSize : ipageCount, // 每页显示的记录数
		pageNum : curPageNum, // 当前第几页
		minimumCountColumns: 1,  //最少允许的列数
		clickToSelect: true,  //是否启用点击选中行
		pageList : [10, 25, 50, 100, 500], // 记录数可选列表
		search : false, // 是否启用查询
		sidePagination : "server", // 表示服务端请求
		// 设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
		// 设置为limit可以获取limit, offset, search, sort, order
		queryParamsType : "undefined",
		queryParams : function queryParams(params) { // 设置查询参数
			console.log(params);
			var param = {
					pageNum: params.pageNumber,   
					pageSize: params.pageSize,
					commTypeName : $('#commTypeName').val(),
					commTypeState : $('#commTypeState').val(),
					commTypeGroup:$("#commTypeGroup").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
            field: 'commtype_name',
            title: '机房类型'
        },{
            field: 'commtype_state',
            title: '状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 9:return '停用';
            		case 0:return '启用';
            	}
            	return value;
            }
        },{
            field: 'commtype_note',
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

/**
 * 新增
 * */
function insertRoomType(){
	operate_type = 1;// 新增
	$("#dataForm")[0].reset();	//清空表单
	$('#EditRoomType').modal();	//弹出表单
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
function updateRoomType(){
	if(!hadCheckedRowData()){
		return;
	}
	operate_type = 2;// 修改
	$('#roomtypeName').val(rowschecked[0].commtype_name);
	$("#roomtypeNote").val(rowschecked[0].commtype_note);
	$('#EditRoomType').modal();
}


function twr_close(){
	removeError('dataForm');
}

function formSubmit(){
	if ($("#roomtypeName").val() == "") {
		alert("机房名称必须输入");
		return false;
	}
	checkName();
	if(flag==false){
		alertModel("机房名称重复，请重新输入！！");
		return false;
	}
	var roomtypeName=$('#roomtypeName').val();
	var roomtypeGroup="ROOM_TYPE";
	var roomtypeState = $('#roomtypeState').val();
	var roomtypeNote=$('#roomtypeNote').val();
	if(operate_type==1){
		$.ajax({
		    url:'insertRoomType',
		    data: {
		    	commTypeName:roomtypeName,
		    	commtypeState:roomtypeState,
		    	commTypeGroup:roomtypeGroup,
		    	commTypeNote:roomtypeNote
		    },
	 		type : 'post',
		    cache:false,
		    async:true,
		    success:function(result){
		        //请求成功时
		    	if(result!=null){
	    			alertModel(result.msg);
	    			loadTableData();
		    	}
    			$('#EditRoomType').modal('hide');
		    },
		    error:function(){
		    	alertModel("请求失败！");
		    }
		})
	}
	else if(operate_type==2){
		$.ajax({
		    url:'updateRoomType',
		    data: {
		    	commTypeName:roomtypeName,
		    	commTypeNote:roomtypeNote,
		    	commtypeState:roomtypeState,
		    	commTypeId:rowschecked[0].commtype_id,
		    	commTypeGroup:$("#commTypeGroup").val()
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
    			$('#EditRoomType').modal('hide');
    			
		    },
		    error:function(){
				alertModel("请求失败！");
		    }
		})
	}
	
}


/**
 * 删除
 */
function deleteRoomType(){
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
			deleteuseObjs.push(row.commtype_id);
			console.log(deleteuseObjs);
		}
     	$.ajax({
     		url : 'deleteRoomType',
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
function openRoomType(type){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var openuseObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		openuseObjs.push(row.commtype_id);
		//console.log(openuseObjs);
	}
 	$.ajax({
 		url : 'startRoomType',
 		data : JSON.stringify(openuseObjs),
 		type : 'post',
		cache : false,
 		dataType : 'json',
		contentType: "application/json;charset=utf-8",
 		success : function(back) {
 			console.log(back);
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
 */
function stopRoomType(type){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var openuseObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		openuseObjs.push(row.commtype_id);
		//console.log(openuseObjs);
	}
 	$.ajax({
 		url : 'stopRoomType',
 		data : JSON.stringify(openuseObjs),
 		type : 'post',
		cache : false,
 		dataType : 'json',
		contentType: "application/json;charset=utf-8",
 		success : function(back) {
 			console.log(back);
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


/*检查塔名称是否重复*/
var flag=null;
function checkName(){
	flag=true;
	var producttypeName = $('#roomtypeName').val();
	var commtypeGroup = "ROOM_TYPE";
	var commtypeState = 0;
	$.ajax({
		type : "post",
		url : "checkTwrName",
		data : {
			"commtypeName":producttypeName,
			"commtypeGroup":commtypeGroup,
			"commtypeState":commtypeState
		},
		async:false,
		dataType : 'json',
		success : function(data) {
			if(data.Obj != null&&data.Obj.length>0){
				flag=false;
			}
		},
		error : function(data) {
			alertModel("请求失败！");
		}
	});
};
