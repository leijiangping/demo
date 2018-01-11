//已显示表格list
var showTableList = null;

var operate_type = 1;// 1 新增，2 修改

$(document).ready(function() {
	initCurrentPage();
});

function initCurrentPage(){
	curPageNum = 1;
	// 查询默认条件表单数据
	getAddress();
	loadTableData();
}

function loadTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "GET",
		contentType : "application/x-www-form-urlencoded",
		url : "queryAllModel", // 获取数据的地址
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
					regId :$("#City option:selected").val(),
					monthNo:$("#month option:selected").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        },{
        	field: 'monthNo',
            title: '月份'
        },{
        	field: 'regName',
            title: '地市'
        },{
            field: 'pue',
            title: 'PUE'
        },{
            field: 't',
            title: '空调运行时长'
        },],
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
/*新增*/
function addRow(){
	operate_type = 1;
	getAddressUpdate();
	$("#dataForm")[0].reset();
	$('#regId').attr("disabled",false);
	$('#months').attr("disabled",false);
	$('#EditPanel span.modal-error').children().remove();
	$('#EditPanel').modal();
}
/*修改*/
function revamp(){
	if(!hadCheckedRowData()){
		return false;
	}
	var checkItem=$('#tb').bootstrapTable('getSelections');//选中条的数据
	operate_type = 2;
	$("#dataForm")[0].reset();
	$("#months").val(checkItem[0].monthNo);
	$("#pue").val(checkItem[0].pue);
	$("#t").val(checkItem[0].t);
	var regId = checkItem[0].regId;
	getAddressUpdate(regId);
	$('#regId').attr("disabled",true);
	$('#months').attr("disabled",true);
	$('#EditPanel span.modal-error').children().remove();
	$('#EditPanel').modal();
}
//保存按钮
function formSubmit(){
	$('#regId').attr("disabled",false);
	$('#months').attr("disabled",false);
	var data=$('#dataForm').serialize();
	var submitData = decodeURIComponent(data,true);
	if(validform().form()){
		var t=$('input[name="t"]').val();
		if(t>=0&&t<=24){
			$('input[name="t"]').next('#err').html('<span style=\"width: 40%;color: green;\"><img src=\""+projectName+"/asserts/image/right_1.png\"/>验证成功</span>');
		}else{
			$('input[name="t"]').next('#err').html('<span style=\"width: 40%;color: red;\"><img src=\""+projectName+"/asserts/image/error.png\"/>空调运行时长在0到24之间</span>');
			return false;
		}
		var pue=$('input[name="pue"]').val();
		if(pue<1){
			$('input[name="pue"]').next('#err').html('<span style=\"width: 40%;color: red;\"><img src=\""+projectName+"/asserts/image/error.png\"/>PUE值必须是不小于1的正数</span>');
			return false;
		}else{
			$('input[name="pue"]').next('#err').html('<span style=\"width: 40%;color: green;\"><img src=\""+projectName+"/asserts/image/right_1.png\"/>验证成功</span>');
		}
		
		if(operate_type==1){//若为新增
			$("#saveSet").attr("disabled",true);
			$.ajax({
			    url:'insertModel',
			    data:submitData,
		 		type : 'post',
			    cache:false,
			    async:true,
			    success:function(result){
			        //请求成功时
			    	if(result!=null){
			    		if(result!=null){
			    			alertModel(result.msg);
			    			loadTableData();//刷新列表
				    	}
						$('#EditPanel').modal('hide');
						$('input[name="pue"]').next('#err').html('');
			    	}
			    	$("#saveSet").attr("disabled",false);
			    },
			    error:function(){
					alertModel("请求失败！");
					$("#saveSet").attr("disabled",false);
			    }
			})
		}else{//若为修改
			$("#saveSet").attr("disabled",true);
			$.ajax({
			    url:'updateModel',
			    data:submitData,
		 		type : 'GET',
			    cache:false,
			    async:true,
			    success:function(result){
			        //请求成功时
			    	if(result!=null){
			    		if(result!=null){
			    			alertModel(result.msg);
			    			loadTableData();//刷新列表
				    	}
						$('#EditPanel').modal('hide');
						$("#dataForm")[0].reset();
						//removeError(dataForm);//清除验证信息
						$('input[name="pue"]').next('#err').html('');
			    	}
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
		regId:{
			required : true,
		},
		monthNo:{
			required : true,
		},
		pue:{
			required : true,
			number: true,
		},
		t:{
			required : true,
		}
	},{
		regId:{
			required : "必填！"
		},
		monthNo:{
			required : "必填！"
		},
		pue:{
			required : "必填！",
		},
		t:{
			required : "必填！",
		}
	});

  return addnew_validate;
}


/**
 * 导出
 */
function exportResourceInfo(){
	confirmModel("您确定要导出吗？","exportInfo");
} 

function exportInfo(){
	//修改导出方法 author：zhujj
	var para="&prvId="+ $("#City").val();
	para+="&monthNo="+ $("#month").val();
	
	window.open("exportModel?"+para,"_blank");
}

function hadCheckedRowData(){
if(showTableList==null || !isChecked()){
	alertModel("请先选择一条操作数据");
	return false;
}
return true;
}

function getAddressUpdate(citySelect,villageSelect){
	$.ajax({
		type : "post",
		url :"../../common/region/getAddressElec",
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		success : function(value) {
			if(value != null){
				sysReguins = value.obj;
				if(sysReguins!=null){
					$('#regId').empty();
					$('#modifyRegion').empty();
					var strCity = "<option value=''>--选择地市--</option>";
					$.each(sysReguins, function (i, item){
						strCity += "<option value='" +item.regId+"' "+(citySelect&&citySelect==item.regId?"selected='true'":"")+">"+item.regName+ "</option>";
					});
					$("#regId").append(strCity);
					$("#modifyRegion").empty();
					var strReg = "<option value=''>--选择区县--</option>";
					if($("#regId").val()!=""){
						$.each(sysReguins, function (i, item){
							if(item.regId==$("#regId").val()){
								$.each(item.child, function (j, itemchild){
									strReg += "<option value='" + itemchild.regId+"' "+(villageSelect&&villageSelect==itemchild.regId?"selected='true'":"")+">"+itemchild.regName+ "</option>";
								});
							}
						});
					}
					$("#modifyRegion").append(strReg);
					//绑定联动事件 修改人zhujj
					$("#regId").change(function(){
						$("#modifyRegion").empty();
						var strReg = "<option value=''>--选择区县--</option>";
						if($("#regId").val()!=""){
							$.each(sysReguins, function (i, item){
								if(item.regId==$("#regId").val()){
									$.each(item.child, function (j, itemchild){
										strReg += "<option value='" + itemchild.regId+"' "+(villageSelect&&villageSelect==itemchild.regId?"selected='true'":"")+">"+itemchild.regName+ "</option>";
									});
								}
							});
						}
						$("#modifyRegion").append(strReg);
					});
				}
			}
		},
		error : function(data) {
			alertModel('获取用户地区信息失败!');
		}
	});
}
