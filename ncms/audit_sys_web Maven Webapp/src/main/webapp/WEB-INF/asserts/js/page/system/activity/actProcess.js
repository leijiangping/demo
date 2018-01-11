var operate_type = 1;//1 新增，2 修改
$(document).ready(function(){
	initialize();
});

/**
 * 获取模型列表
 */
var datalist = null;

/**
 * 初始化加页面
 */
function initialize(){
	//显示页数
	curPageNum = 1;
	//每页显示个数
	ipageCount = 10;
	getProvinceByIds();//加载分类列表
	processList();
}

/**
 * 查询模型列表
 */
function processList() {
	
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "process/list", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		pageSize : ipageCount, // 每页显示的记录数
		pageNumber : curPageNum, // 当前第几页
		minimumCountColumns: 1,  //最少允许的列数
		clickToSelect: true,  //是否启用点击选中行
		singleSelect:true,
		pageList : [10, 25, 50, 100, 500], // 记录数可选列表
		search : false, // 是否启用查询
		showColumns : false, // 显示下拉框勾选要显示的列
		showRefresh : false, // 显示刷新按钮
		sidePagination : "server", // 表示服务端请求
		// 设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
		// 设置为limit可以获取limit, offset, search, sort, order
		queryParamsType : "undefined",
		queryParams : function queryParams(params) { // 设置查询参数
			var param = {
				cur_page_num: params.pageNumber,    
				page_count: params.pageSize,
				category : $("#category").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
            field: 'name',
            title: '流程名称'
        },{
            field: 'category',
            title: '所属分类',
            formatter:provinceListFormatter
        },{
            field: 'id',
            title: '流程ID'
        },{
            field: 'key',
            title: '流程标识'
        }, {
            field: 'version',
            title: '流程版本'
        }, {
            field: 'deploymentTime',
            title: '部署时间',
            formatter:tableDateFormatter
        },  {
            field: 'id',
            title: '流程XMl',
            formatter:processXMLFormatter
        },  {
            field: 'id',
            title: '流程图片',
            formatter:processIMGFormatter
        } ],
		/*onLoadSuccess : function() { // 加载成功时执行
			console.log("加载成功");
		},*/
        onClickRow: function (row, td) {
        	butState(row);
        },
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			console.log(res);
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
function butState(row){
	if(row.suspended){
		$("#processBut").val("active");

		$("#processBut").html("激活");
	}else{
		$("#processBut").val("suspend");
		$("#processBut").html("挂起");
	}
}
/**
 * 删除流程实例
 * 
 */
function deleteProcess() {
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	if (confirm("确定删除流程实例?")) {
		var deploymentId=rowschecked[0].deploymentId;
		$.ajax({
		    url:'process/delete',
		    data:{deploymentId:deploymentId},
	 		type : 'post',
		    cache:false,
		    async:true,
		    dataType:"json",
		    success:function(result){
		    	alertModel(result.msg);
		    	modelList();
		    },
		    error:function(result){
		    	alertModel(result.msg);
		    }
		});
	}
};

/**
 *	激活流程实例
 * 
 */
function activeSuspendProcess() {
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	if (confirm("确定激活流程实例吗?")) {
		var procDefId=rowschecked[0].id;
		var state=$("#processBut").val();
		$.ajax({
		    url:'process/update/'+state,
	 		type : 'post',
		    data:{procDefId:procDefId},
		    cache:false,
		    async:true,
		    dataType:"json",
		    success:function(result){
		    	alertModel(result.msg);
		    	modelList();
		    },
		    error:function(result){
		    	alertModel(result.msg);
		    }
		});
	}
};
/**
 *	转换为模型
 * 
 */
function toModel() {
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var procDefId=rowschecked[0].id;
	if (confirm("确定转换流程实例为模型吗?")) {
		$.ajax({
		    url:'process/convert/toModel',
		    data:{procDefId:procDefId},
	 		type : 'post',
		    cache:false,
		    async:true,
		    dataType:"json",
		    success:function(result){
		    	alertModel(result.msg);
		    	modelList();
		    },
		    error:function(result){
		    	alertModel(result.msg);
		    }
		});
	}
};

function processXMLFormatter(value, row, index){
	return '<a target="_blank" href="process/resource/read?procDefId='+value+'&resType=xml">查看XML</a>';
}

function processIMGFormatter(value, row, index){
	return '<a target="_blank" href="process/resource/read?procDefId='+value+'&resType=image">查看流程图</a>'
 }
function tableDateFormatter(value, row, index){
	return new Date(value).format("yyyy-MM-dd hh:mm:ss");
}
