//已显示表格list
var showTableList = null;

var operate_type = 1;// 1 新增，2 修改

$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	curPageNum = 1;
	/*默认条件表单数据*/
	loadTableData();
	//加载省份下拉数据
	getProvince();
}

/**
 * 默认条件表单数据
 * */
function loadTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryAllChargeDiff", // 获取数据的地址
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
            field: 'prvName',
            title: '省份'
        }, {
            field: 'chargediffUpnum',
            title: '上限'
        }, {
        	field: 'chargediffDnnum',
            title: '下限'
        }, {
            field: 'chargediffNote',
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
		            alertModel(res.msg);
				}
				showTableList = res.obj.result;
				console.log(showTableList);
			}
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.result //数据
	         };
		}
	});
}

/**
 * 查询不同条件表单数据
 * */
function searchTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryChargeDiffByCondition", // 获取数据的地址
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
	            prvId : $("#provinceChoice option:selected").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
            field: 'prvName',
            title: '省份'
        }, {
            field: 'chargediffUpnum',
            title: '上限'
        }, {
        	field: 'chargediffDnnum',
            title: '下限'
        }, {
            field: 'chargediffNote',
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
		            alertModel(res.msg);
				}
				showTableList = res.obj.result;
				console.log(showTableList);
			}
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.result //数据
	         };
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

function hadCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}
/**
 * 修改
 * */
function updateCost(){	
	if(!hadCheckedRowData()){
		return;
	}
	operate_type = 2;// 修改
	$("#chargediffUpnum").val(rowschecked[0].chargediffUpnum);
	$("#chargediffDnnum").val(rowschecked[0].chargediffDnnum);
	$("#chargediffNote").val(rowschecked[0].chargediffNote);
	$('#EditCost').modal();
	
}
//验证
function validform(){

	var public_validate= bindformvalidate("#dataForm", {
		provinceChoicePop : {
			required : true,
		},
		chargediffUpnum:{
			required : true,
			min:0,
		},
		chargediffDnnum:{
			required : true,
			min:0,
		},
	}, {
		provinceChoicePop:{
			required : "请选择省份",
		},
		chargediffUpnum : {
			required : "请输入上限",
			min:"只能输入数字",
		},
		chargediffDnnum:{
			required : "请输入上限",
			min:"只能输入数字",
		},
	});
  return public_validate;
}
function public_close(){
	removeError('dataForm');
}
function formSubmit(){
	if(validform().form()){
	if($("#chargediffDnnum").val()=="" || $("#chargediffUpnum").val()==null || $("#chargediffNote").val()==null){
		alertModel("不可以为空！");
		return false;
	}
	var chargediffDnnum=$('#chargediffDnnum').val();
	var chargediffUpnum=$('#chargediffUpnum').val();
	var chargediffNote=$('#chargediffNote').val();
	var prvId=$('#provinceChoice option:selected').val();
	console.log(prvId);
	if(parseInt(chargediffUpnum) <= parseInt(chargediffDnnum)){
		alertModel("下限值不可以大于等于上限值！");
		return false;
	}
	if(operate_type==1){
		$("#saveSet").attr("disabled",true);
		$.ajax({
		    url:'insertChargeDiff',
		    data: {
		    	chargediffDnnum:chargediffDnnum,
		    	chargediffUpnum:chargediffUpnum,
		    	chargediffNote:chargediffNote,
		    	prvId:prvId
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
		    url:'updateChargeDiff',
		    data: {
		    	chargediffUpnum:$('#chargediffUpnum').val(),
		    	chargediffDnnum:$('#chargediffDnnum').val(),
		    	chargediffNote:$('#chargediffNote').val(),
		    	prvId:$('#provinceChoice option:selected').val(),
		    	chargediffId:rowschecked[0].chargediffId
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
 * */
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
			deleteuseObjs.push(row.chargediffId);
		}
     	$.ajax({
     		url : 'deleteChargeDiff',
     		data : JSON.stringify(deleteuseObjs),
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
}

