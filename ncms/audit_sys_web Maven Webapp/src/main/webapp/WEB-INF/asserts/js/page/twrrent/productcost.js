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
	//加载铁塔类型下拉框数据
	loadProductTypeList();
	//加载机房类型下拉框数据
	loadRoomTypeList();
	//加载配套类型下拉框数据
	loadSuptTypeList();
	//加载风压类型下拉框数据
	loadwinPressTypeList();
	//加载挂高类型下拉框数据
	loadHighupTypeList();
	//价格（卡控）
	getProductCostPrice();
}

/**
 * 加载所有的铁塔类型
 */
function loadProductTypeList(){
	 $.ajax({
		url : 'queryTwrVoList',
		data : {
			pageSize:100,
			pageNumber:1
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
					$("#pctcosttypeId").append(opts);
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
 * 加载所有的风压类型
 * */
function loadwinPressTypeList(){
	 $.ajax({
		url : 'querywinPressList',
		data : {
			pageSize:100,
			pageNumber:1
		},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if (back != null) {
				if(back.success=="1"){
					console.log(back);
					var list = back.Obj;
					var opts = "<option value=''>-请选择-</option>";  
					for(var i = 0; i < list.length; i++){
						var data = list[i];
						opts += "<option value='"+data.windpressureId+"'>"+data.windpressureName+"</option>";  
					}
					$("#windpressureId").append(opts);
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
 * 加载所有的挂高类型
 */
function loadHighupTypeList(){
	 $.ajax({
		url : 'queryHighUpList',
		data : {
			pageSize:100,
			pageNumber:1
		},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if (back != null) {
				if(back.success=="1"){
					console.log(back);
					var list = back.Obj;
					var opts = "<option value=''>-请选择-</option>";  
					for(var i = 0; i < list.length; i++){
						var data = list[i];
						opts += "<option value='"+data.highupId+"'>"+data.highupName+"</option>";  
					}
					$("#highupId").append(opts);
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
 * 加载所有的机房类型
 */
function loadRoomTypeList(){
	 $.ajax({
		url : 'queryRoomAndSuptType',
		data : {
			commType:"ROOM_TYPE"
		},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if (back != null) {
				if(back.success=="1"){
					console.log(back);
					var list = back.Obj;
					var opts = "<option value=''>-请选择机房类型-</option>";  
					for(var i = 0; i < list.length; i++){
						var data = list[i];
						opts += "<option value='"+data.commtype_id+"'>"+data.commtype_name+"</option>";  
					}
					$("#roomtypeId").append(opts);
					$("#roomtypeSearchId").append(opts);
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
 * 加载所有的配套类型
 */
function loadSuptTypeList(){
	 $.ajax({
		url : 'queryRoomAndSuptType',
		data : {
			commType:"SUPT_TYPE"
		},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if (back != null) {
				if(back.success=="1"){
					console.log(back);
					var list = back.Obj;
					var opts = "<option value=''>-请选择配套类型-</option>";  
					for(var i = 0; i < list.length; i++){
						var data = list[i];
						opts += "<option value='"+data.commtype_id+"'>"+data.commtype_name+"</option>";  
					}
					$("#supttypeId").append(opts);
					$("#supttypeSearchId").append(opts);
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
		url : "queryAllProCost", // 获取数据的地址
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
            field: 'roomtypeName',
            title: '机房类型'
        },{
            field: 'supttypeName',
            title: '配套类型'
        },{
            field: 'windpressureName',
            title: '风压类型'
        },{
            field: 'highupName',
            title: '挂高类型'
        },{
            field: 'productcostPrice',
            title: '单价（万元）'
        },{
            field: 'productcostState',
            title: '状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 9:return '停用';
            		case 0:return '启用';
            	}
            	return value;
            }
        },{
            field: 'productcostNote',
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
 * 根据不同条件查询
 */
function searchTableData(){
	$("#tb").css("font-size","14px");
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryProCostByCondition", // 获取数据的地址
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
					productcostState : $("#pctcostState option:selected").val(),
					producttypeId : $("#pcttypeId option:selected").val(),
					roomtypeId : $("#roomtypeSearchId option:selected").val(),
					supttypeId : $("#supttypeSearchId option:selected").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
        	field: 'producttypeName',
            title: '铁塔类型'
        }, {
            field: 'roomtypeName',
            title: '机房类型'
        },{
            field: 'supttypeName',
            title: '配套类型'
        },{
            field: 'windpressureName',
            title: '风压类型'
        },{
            field: 'highupName',
            title: '挂高类型'
        },{
            field: 'productcostPrice',
            title: '单价（万元）'
        },{
            field: 'productcostState',
            title: '状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 9:return '停用';
            		case 0:return '启用';
            	}
            	return value;
            }
        },{
            field: 'productcostNote',
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
 * 价格（限制）,只能输入数字和.（点） 且 小数点后不能超过两位
 * */
function getProductCostPrice(){
	$("#productcostPrice").keyup(function () {
	    var reg = $(this).val().match(/\d+\.?\d{0,2}/);
	    var txt = '';
	    if (reg != null) {
	        txt = reg[0];
	    }
	    $(this).val(txt);
	}).change(function () {
	    $(this).keypress();
	    var v = $(this).val();
	    if (/\.$/.test(v))
	    {
	        $(this).val(v.substr(0, v.length - 1));
	    }
	});
}
/**
 * 新增
 * */
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
	$("#supttypeId option[value='"+rowschecked[0].supttypeId+"']").attr("selected","selected");//根据值让option选中
	$("#roomtypeId option[value='"+rowschecked[0].roomtypeId+"']").attr("selected","selected");//根据值让option选中
	$("#windpressureId option[value='"+rowschecked[0].windpressureId+"']").attr("selected","selected");//根据值让option选中
	$("#highupId option[value='"+rowschecked[0].highupId+"']").attr("selected","selected");//根据值让option选中
	$("#productcostPrice").val(rowschecked[0].productcostPrice);
	$("#productcostNote").val(rowschecked[0].productcostNote);
	$('#EditCost').modal();
}
//验证
function validform(){

	var twr_validate= bindformvalidate("#dataForm", {
		producttypeId : {
			required : true,
			maxlength : 20,
		},
		windpressureId:{
			required : true,
			maxlength : 20,
		},
		highupId:{
			required : true,
			maxlength : 20,
		},
		roomtypeId:{
			required : true,
			maxlength : 20,
		},
		supttypeId:{
			required : true,
			maxlength : 20,
		},
		productcostPrice:{
			required : true,
			min:1,
		}
	}, {
		producttypeId:{
			required : "请选择铁塔种类",
			maxlength: "铁塔名称最大长度20"
		},
		windpressureId : {
			required : "请选择铁分压系数",
			maxlength: "铁塔名称最大长度20"
		},
		productcostPrice:{
			required : "请输入价钱",
			min:"最小不能少于1万,不能输入汉字等"
		}
	});
  return twr_validate;
}

function twr_close(){
	removeError('dataForm');
}

function formSubmit(){
	if(validform().form()){
		
	
	var productcostPrice=$('#productcostPrice').val();
	var productcostNote=$('#productcostNote').val();
	var windpressureId=$('#windpressureId option:selected').val();
	var windpressureName=$('#windpressureId option:selected').html();
	var highupId=$('#highupId option:selected').val();
	var highupName=$('#highupId option:selected').html();
	var roomtypeId=$('#roomtypeId option:selected').val();
	var roomtypeName=$('#roomtypeId option:selected').html();
	var supttypeId=$('#supttypeId option:selected').val();
	var supttypeName=$('#supttypeId option:selected').html();
	var producttypeId=$('#producttypeId option:selected').val();
	var producttypeName=$('#producttypeId option:selected').html();
	
	if(operate_type==1){
		$.ajax({
		    url:'insertProCost',
		    data: {
		    	producttypeId:producttypeId,
		    	producttypeName:producttypeName,
		    	productcostPrice:productcostPrice,
		    	productcostNote:productcostNote,
		    	windpressureId:windpressureId,
		    	windpressureName:windpressureName,
		    	highupId:highupId,
		    	highupName:highupName,
		    	roomtypeId:roomtypeId,
		    	roomtypeName:roomtypeName,
		    	supttypeId:supttypeId,
		    	supttypeName:supttypeName
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
		    },
		    error:function(){
		    	alertModel("请求失败！");
		    }
		})
	}
	else if(operate_type==2){
		$.ajax({
		    url:'updateProCost',
		    data: {
		    	producttypeId:$('#producttypeId option:selected').val(),
		    	producttypeName:$('#producttypeId option:selected').html(),
		    	productcostNote:$('#productcostNote').val(),
		    	productcostPrice:$('#productcostPrice').val(),
		    	supttypeId:$('#supttypeId option:selected').val(),
		    	supttypeName:$('#supttypeId option:selected').html(),
		    	roomtypeId:$('#roomtypeId option:selected').val(),
		    	roomtypeName:$('#roomtypeId option:selected').html(),
		    	windpressureId:$('#windpressureId option:selected').val(),
		    	windpressureName:$('#windpressureId option:selected').html(),
		    	highupId:$('#highupId option:selected').val(),
		    	highupName:$('#highupId option:selected').html(),
		    	productcostId:rowschecked[0].productcostId
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
    			
		    },
		    error:function(){
				alertModel("请求失败！");
		    }
		})
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
			deleteuseObjs.push(row.productcostId);
			console.log(deleteuseObjs);
		}
     	$.ajax({
     		url : 'deleteProCost',
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
		openuseObjs.push(row.productcostId);
		//console.log(openuseObjs);
	}
 	$.ajax({
 		url : 'startOrStopProCost?productcostState='+type,
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
function stopCost(type){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var stopuseObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		stopuseObjs.push(row.productcostId);
		//console.log(stopuseObjs);
	}
 	$.ajax({
 		url : 'startOrStopProCost?productcostState='+type,
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
