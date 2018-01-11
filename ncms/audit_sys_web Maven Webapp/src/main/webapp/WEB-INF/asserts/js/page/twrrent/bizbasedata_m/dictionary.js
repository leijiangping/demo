//已显示表格list
var showTableList = null;

var operate_type = 1;// 1 新增，2 修改

$(document).ready(function() {
	initCurrentPage();
});

function initCurrentPage(){
	curPageNum = 1;
	// 查询默认条件表单数据
	loadDropDownList();
}
 //加载所有的字典表数据
 function loadDropDownList(){
	
	 $.ajax({
		url : 'queryAllDictionaryGroup',
		data : {dictgroup_code:$("#dictgroup_code").val()},
		type : 'get',
		dataType : 'json',
		success : function(back) {
			if (back != null) {
				if(back.success=="1"){
					var data = back.Obj;
					var opt = "<input type='hidden' id='dictgroup_id_query' value='"+data.dictgroup_id+"'>"
					$("#dictgroup_code").after(opt);
					opt = "<option value='"+data.dictgroup_id+"'>"+data.dictgroup_name+"</option>"
					$("#dictgroup_id").append(opt);
					loadTableData();
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

//条件查询字典表
function loadTableData() {
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryDictionary", // 获取数据的地址
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
				dictgroup_id : 	$('#dictgroup_id_query').val(),
				dict_name : 	$('#dict_name_query').val(),
				dict_value : 	$('#dict_value_query').val(),
				dict_state : 	$('#dict_state_query').val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
            field: 'dictgroup_name',
            title: '字典分组'
        }, {
            field: 'dict_name',
            title: '字典名称'
        }, {
            field: 'dict_value',
            title: '字典值'
        }, {
            field: 'dict_state',
            title: '字典状态',
            formatter:function(value, row, index){
            	switch(value){
            	case '0':return '启用';
            	case '9':return '停用';
            	case '-1':return '删除';
            	}
                return value;
            }
        }, {
            field: 'dict_note',
            title: '备注信息'
        }, ],
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
			}
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.result //数据
	         };
		}
	});
}

function insertNew(){
	operate_type = 1;// 新增
	
	$("#dataForm")[0].reset();	//清空表单
	$('#EditPanel .form-group span.modal-error').children().remove();
	$('#EditPanel').modal();	//弹出表单
}

function hadCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}

function update(){
	
	if(!hadCheckedRowData()){
		return;
	}
	
	operate_type = 2;// 修改

	$.ajax({
	    url:'queryDictionaryByID',
	    data: {
	    	ID : rowschecked[0].dict_id
	    },
 		type : 'get',
	    cache:false,
	    async:true,
	    success:function(result){
	        //请求成功时
	    	if(result!=null){
		    	if(result.success == "1"){
		    		var item = result.Obj;
		    		$("#dict_id").val(item.dict_id);
		    		$("#dict_name").val(item.dict_name);
		    		$("#dict_value").val(item.dict_value);
		    		$("#dictgroup_id").val(item.dictgroup_id);
		    		$("#dict_state").val(item.dict_state);
		    		$("#dict_note").val(item.dict_note);
		    		$('#EditPanel .form-group span.modal-error').children().remove();
		    		$('#EditPanel').modal();
		    	}else{
		    		alertModel(result.msg);
    			}
	    	}
	    },
	    error:function(){
			alertModel("请求失败！");
	    }
	})
}

function formSubmit(){
	if(validform().form()){
		if($("#dictgroup_id").val()=="" || $("#dictgroup_id").val()==null){
			alertModel("请先选择字典分组");
			return false;
		}
		if($("#dict_name").val()=="" || $("#dict_name").val()==null ||
				$("#dict_value").val()=="" || $("#dict_value").val()==null){
			alertModel("字典名称和字典值必须输入");
			return false;
		}
		
		var data=$('#dataForm').serialize();
		
		var submitData=decodeURIComponent(data,true);
	
		if(operate_type==1){
			$("#saveSet").attr("disabled",true);
			$.ajax({
			    url:'insertDictionary',
			    data: submitData,
		 		type : 'post',
			    cache:false,
			    async:true,
			    success:function(result){
			        //请求成功时
			    	if(result!=null){
		    			alertModel(result.msg);
		    			loadTableData();
			    	}
	    			$('#EditPanel').modal('hide');
	    			$("#saveSet").attr("disabled",false);
			    },
			    error:function(){
			    	alertModelModel("请求失败！");
			    	$("#saveSet").attr("disabled",false);
			    }
			})
		}
		else{
			$("#saveSet").attr("disabled",true);
			$.ajax({
			    url:'updateDictionary',
			    data: submitData,
		 		type : 'post',
			    cache:false,
			    async:true,
			    success:function(result){
			        //请求成功时
			    	if(result!=null){
		    			alertModel(result.msg);
		    			loadTableData();
			    	}
	    			$('#EditPanel').modal('hide');
	    			$('#chooseAll_id').attr("checked",false);
	    			$("#saveSet").attr("disabled",false);
			    },
			    error:function(){
					alertModel("请求失败！");
					$("#saveSet").attr("disabled",false);
			    }
			})
		}
	}
}
//验证
function validform(){
	var addnew_validate= bindformvalidate("#dataForm", {
		dictgroup_id:{
			required : true,
		},
		dict_name:{
			required : true,
		},
		dict_value:{
			required : true,
			number: true,
		}
	},{
		dictgroup_id:{
			required : "必选！"
		},
		dict_name:{
			required : "必填！"
		},
		dict_value:{
			required : "必填！"
		}
	});

  return addnew_validate;
}
function deleteUse(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	if(confirm("确认删除吗？"))
	{
		var deleteuseObjs = new Array();
		// 从选中行中挑出可以启用的item
		for (s = 0; s < rowschecked.length; s++) {
			var row = rowschecked[s];
			deleteuseObjs.push(row.dict_id);
		}
     	$.ajax({
     		url : 'deleteDictionaryBranch',
     		data : JSON.stringify(deleteuseObjs),
			type : 'post',
			cache : false,
			dataType : 'json',
			contentType: "application/json;charset=utf-8",
     		success : function(back) {
     			if (back != null) {
     				loadTableData();
    				alertModel(back.msg);
        			$('#chooseAll_id').attr("checked",false);
     			}
     		},
     		error : function() {
    			alertModel("请求失败！");
     		}
     	});
	}
} 

function openUse(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var openuseObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		openuseObjs.push(row.dict_id);
	}
 	$.ajax({
 		url : 'openUseDictionaryBranch',
 		data : JSON.stringify(openuseObjs),
 		type : 'post',
		cache : false,
 		dataType : 'json',
		contentType: "application/json;charset=utf-8",
 		success : function(back) {
 			if (back != null) {
 				loadTableData();
				alertModel(back.msg);
    			$('#chooseAll_id').attr("checked",false);
 			}
 		},
 		error : function() {
			alertModel("请求失败！");
 		}
 	});
} 
function stopUse(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var stopuseObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		stopuseObjs.push(row.dict_id);
	}
 	$.ajax({
 		url : 'stopUseDictionaryBranch',
 		data : JSON.stringify(stopuseObjs),
 		type : 'post',
		cache : false,
 		dataType : 'json',
		contentType: "application/json;charset=utf-8",
 		success : function(back) {
 			if (back != null) {
 				loadTableData();
				alertModel(back.msg);
    			$('#chooseAll_id').attr("checked",false);
 			}
 		},
 		error : function() {
			alertModel("请求失败！");
 		}
 	});
} 
