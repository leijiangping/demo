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
	getProvinceByIds();//加载省份列表
	modelList();
}
var provinceList="";
function getProvinceByIds(){
	$.ajax({
		url:projectName+'/asserts/tpl/system/region/selectProvinceByIds',
//		data:data,
		type:'post',
		dataType:'json',
		aysnc:false,
		success:function(back){
			if (back != null) {
				item=back.obj;
				if(back.success=="1"&&item!=null){
					provinceList=item;
					var option="<option value=''>---请选择所属分类---</option>";
					$.each(item,function(i,v){
						option+="<option value=" + v.prvCode + ">" + v.prvName + "</option>";
					});
					$(".province").html(option);
				}
			}
		},
		error:function(){
			alertModel("请求异常");
		}
	});
}
//
/**
 * 查询模型列表
 */
function modelList() {
	
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "modelList", // 获取数据的地址
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
				category : $("#searchCategory").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        },{
            field: 'name',
            title: '流程名称'
        },{
            field: 'category',
            title: '所属分类',
            formatter:provinceListFormatter
        },{
            field: 'key',
            title: '流程标识'
        }, {
            field: 'version',
            title: '版本号'
        }, {
            field: 'createTime',
            title: '创建时间',
            formatter:tableDateFormatter
        },  {
            field: 'lastUpdateTime',
            title: '最后更新时间',
            formatter:tableDateFormatter
        } ],
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
				showTableList = res.Obj.result;
			}
	        return {
	            "total": res.Obj.total,//总页数
	            "rows": res.Obj.result //数据
	         };
		}
	});
}
//新增
function addModel(){

	$("#dataForm")[0].reset(); // 清空表单
	$('#EditPanel .form-group span.modal-error').children().remove();
	$("#oper").val("insert");//
	$("#key").removeAttr("readonly");
	$("#description").text("");
	$('#EditPanel').modal(); // 弹出表单
}
//修改
function updateModel(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}

    window.open('act/process-editor/modeler?modelId='+rowschecked[0].id) ; 
}
//克隆
function cloneModel(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	$("#dataForm")[0].reset(); // 清空表单
	$('#EditPanel .form-group span.modal-error').children().remove();
	
	$("#category").val(rowschecked[0].category);
	$("#modelId").val(rowschecked[0].id);
	$("#name").val(rowschecked[0].name);
	$("#key").val(rowschecked[0].key);
	$("#key").attr("readonly","readonly");
	var matainfo=JSON.parse(rowschecked[0].metaInfo);
	$("#description").text(matainfo.description);

	$("#oper").val("clone");//操作
	$('#EditPanel').modal(); // 弹出表单
}
//保存提交
function formSubmit(){
	if(validform().form()){
		console.log("-----------开始保存流程模型--------------------------");
		$("#category").removeAttr("disabled");
		var data=$("#dataForm").serialize();
		$("#saveSet").attr("disabled",true);
		console.log("-----------开始ajax流程模型--------------------------"+data);
		$.ajax({
			url:"createByAjax?v=1",
			data:data,
			type:"POST",
			dataType:"JSON",
			aysnc:false,
			cache:false,
			success:function(back){
				console.log("-----------返回ajax结果流程模型--------------------------");
				if (back != null) {
					item=back.Obj;
					if(back.success=="1"){
						$('#EditPanel').modal("hide"); // 弹出表单
						 window.open('act/process-editor/modeler?modelId='+item) ; 
						 modelList();
					}
				}
				$("#saveSet").attr("disabled",false);
			},
			error:function(msg){
				console.log("-----------返回ajax报错流程模型--------------------------"+JSON.stringify(msg));
				alertModel("请求异常");
				$("#saveSet").attr("disabled",false);
			}
		});
	}
}
//验证
function validform(){
	var addnew_validate= bindformvalidate("#dataForm", {
//		category:{
//			required : true,
//		},
		name:{
			required : true,
		},
		key:{
			required : true,
		}
	},{
//		category:{
//			required : "必选！"
//		},
		name:{
			required : "必填！"
		},
		key:{
			required : "必填！"
		}
	});

  return addnew_validate;
}
/**
 * 删除模型
 * 
 */
function deleteModel() {
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	if (confirm("确定重新部署所选模型?")) {
		var modelId=rowschecked[0].id;
		$.ajax({
		    url:'delete',
		    data:{modelId:modelId},
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
		})
	}
};


/**
 * 部署流程
 */

function deployModel() {
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var modelId=rowschecked[0].id;
	if (confirm("确定重新部署所选模型?")) {
		$.ajax({
			url : 'deploy',
		    data:{id:modelId},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(feedback) {
				alertModel(feedback.msg);
				modelList();
			},
			error : function() {
				alertModel("请求异常");
			}
		});
	}
};


/**
 * 导出模型
 */
function exportModel(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	window.open("export?id="+rowschecked[0].id);
	
};

function tableDateFormatter(value, row, index){
	return new Date(value).format("yyyy-MM-dd hh:mm:ss");
}

function provinceListFormatter(value, row, index){
	var prvName="公共";
	var flag=true;
	if(provinceList!=null&&provinceList!=""){
		$.each(provinceList,function(i,v){
			 if(value==v.prvCode){
				 prvName= v.prvName;
				 flag=false;
			 }
		 });
	}
	 return prvName;
}

