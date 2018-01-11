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
	//加载下拉框数据
	loadProductTypeList();
}

/**
 * 加载所有的铁塔种类
 * */
function loadProductTypeList(){
	var producttypeState = 0;
	 $.ajax({
		url : 'queryTwrProductTypeNameList',
		data : {
			producttypeState:producttypeState
		},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if (back != null) {
				if(back.success=="1"){
					console.log(back);
					var list = back.Obj;
					var opts = "<option value=''>-请选择铁塔类型-</option>";  
					for(var i = 0; i < list.length; i++){
						var data = list[i];
						opts += "<option value='"+data.producttypeId+"'>"+data.producttypeName+"</option>";  
					}
					$("#producttypeId").append(opts);
					$("#pcttypeId").append(opts);
				}
				else{
					alertModel(back.msg);
				}
			}
		},
		error : function() {
			alertModel("请求失败！");
		}
	 })
}
/**
 * 默认条件表单数据
 * */
function loadTableData(){
	$("#tb").css("font-size","14px");
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryAllHighUp", // 获取数据的地址
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
					pageSize: params.pageSize
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
        	field: 'producttypeName',
            title: '铁塔类型'
        }, {
        	field: 'highupName',
            title: '名称'
        }, {
            field: 'highupUpper',
            title: '上限'
        },{
            field: 'highupLower',
            title: '下限'
        },{
            field: 'highupState',
            title: '状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 9:return '停用';
            		case 0:return '启用';
            	}
            	return value;
            }
        },{
            field: 'highupNote',
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
 * 根据不同条件查询表单数据
 * */
function searchTableData(){
	$("#tb").css("font-size","14px");
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryHighUpByCondition", // 获取数据的地址
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
					highupState : $("#higState option:selected").val(),
					producttypeId : $("#pcttypeId option:selected").val(),
					highupName : $("#higName").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
        	field: 'producttypeName',
            title: '铁塔类型'
        }, {
        	field: 'highupName',
            title: '名称'
        }, {
            field: 'highupUpper',
            title: '上限'
        },{
            field: 'highupLower',
            title: '下限'
        },{
            field: 'highupState',
            title: '状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 9:return '停用';
            		case 0:return '启用';
            	}
            	return value;
            }
        },{
            field: 'highupNote',
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
	$("#producttypeId option[value='"+rowschecked[0].producttypeId+"']").attr("selected","selected");//根据值让option选中 
	$("#highupName").val(rowschecked[0].highupName);
	$("#highupUpper").val(rowschecked[0].highupUpper);
	$("#highupLower").val(rowschecked[0].highupLower);
	//$("#highupState").val(rowschecked[0].highupState);
	$("#highupNote").val(rowschecked[0].highupNote);
	$('#EditCost').modal();
}
//验证
function validform(){

	var public_validate= bindformvalidate("#dataForm", {
		producttypeId : {
			required : true,
			maxlength : 20,
		},
		highupName:{
			required : true,
			maxlength : 20,
		},
		highupUpper:{
			required : true,
			min:0,
		},
		highupLower:{
			required : true,
			min:0,
		}
		
		
	}, {
		producttypeId:{
			required : "请选择铁塔种类",
			maxlength: "铁塔名称最大长度20"
		},
		highupName : {
			required : "请输入名称",
			
		},
		highupUpper:{
			required : "请输入上限值",
			min:"只能输入数字"
		},
		highupLower:{
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
		
	
	//var producttypeId=$('#producttypeId option:selected').val();
	var highupName=$('#highupName').val();
	var highupUpper=$('#highupUpper').val();
	var highupLower=$('#highupLower').val();
	var highupNote=$('#highupNote').val();
	var producttypeId=$('#producttypeId option:selected').val();
	var producttypeName=$('#producttypeId option:selected').html();
	
	if(parseInt(highupUpper) <= parseInt(highupLower)){
		alertModel("下限值不可以大于等于上限值！");
		return false;
	}
	
	if(operate_type==1){
		$("#saveSet").attr("disabled",true);
		$.ajax({
		    url:'insertHighUp',
		    data: {
		    	producttypeId:producttypeId,
		    	producttypeName:producttypeName,
		    	highupName:highupName,
		    	highupUpper:highupUpper,
		    	highupLower:highupLower,
		    	highupNote:highupNote
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
		    url:'updateHighUp',
		    data: {
		    	producttypeId:$('#producttypeId option:selected').val(),
		    	producttypeName:$('#producttypeId option:selected').html(),
		    	highupName:$('#highupName').val(),
		    	highupUpper:$('#highupUpper').val(),
		    	highupLower:$('#highupLower').val(),
		    	highupNote:$('#highupNote').val(),
		    	highupId:rowschecked[0].highupId
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
			deleteuseObjs.push(row.highupId);
			console.log(deleteuseObjs);
		}
     	$.ajax({
     		url : 'deleteHighUp',
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
		openuseObjs.push(row.highupId);
		//console.log(openuseObjs);
	}
 	$.ajax({
 		url : 'startOrStopHighUp?highupState='+type,
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
		stopuseObjs.push(row.highupId);
		//console.log(stopuseObjs);
	}
 	$.ajax({
 		url : 'startOrStopHighUp?highupState='+type,
 		data : JSON.stringify(stopuseObjs),
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

