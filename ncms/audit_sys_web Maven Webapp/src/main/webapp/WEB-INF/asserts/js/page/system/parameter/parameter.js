var showTableList = null;
$(document).ready(function() {
	curPageNum = 1;
	allProvince();
	
	$("#updateParameter").click(function(){
		updateParameter();
	})
	
	$("#stopParameter").click(function(){
		if(!hadCheckedRadioRowData()){
			return ;
		}
		if(rowschecked[0].paraState == '停用'){
			alertModel("参数已停用，请勿重复停用！");
			return;
		}
		$.ajax({
			url : 'stopParameter',// 跳转到 action
			data : {
				paraId : rowschecked[0].paraId
				},
			type : 'post',
			dataType : 'json',
			success : function(back) {
						alertModel(back.msg);
						showTable();
					},
					error : function(back) {
						alertModel(back.msg);
					}
				});
	})
	
	$("#openParameter").click(function(){
		if(!hadCheckedRadioRowData()){
			return ;
		}
		if(rowschecked[0].paraState == '启用'){
			alertModel("参数已启用，请勿重复启用！");
			return;
		}
		$.ajax({
			url : 'openParameter',// 跳转到 action
			data : {
				paraId : rowschecked[0].paraId
				},
			type : 'post',
			dataType : 'json',
			success : function(back) {
				alertModel(back.msg);
				showTable();
			},
			error : function(back) {
				alertModel(back.msg);
			}
		});
	})
	$("#querySomeParameter").click(function(){
		showTable();
	})
});

function gopage(i){
	 if(curPageNum == i)
		 return;
	 curPageNum = i;
	 showTable();
}

function showTable() {
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryParameter", // 获取数据的地址
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
				cur_page_num: params.pageNumber,    
				page_count: params.pageSize,
				prvId 		: 	$("#userProvince option:selected").val(),
				paraCode 	: 	$("#paraCode").val(),
				paraValue	: 	$("#paraValue").val(),
				paraNote 	: 	$("#paraNote").val()
			};
			return param;
		},
		columns: [{
            radio: true
        },{
            field: 'paraCode',
            title: '参数代码'
        },{
            field: 'paraState',
            title: '参数状态'
        }, {
            field: 'paraValue',
            title: '参数值'
        }, {
            field: 'paraNote',
            title: '参数说明'
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
/**
 * 获取选中的radio
 * */
function isCheckedRadio(){
	var checkNum = 0;
	rowschecked = new Array();
	var checklist = $("#tb tbody input[type='radio']");
	for(var i=0;i<checklist.length;i++)
    {
		// 已选中可操作行
	    if(checklist[i].checked == 1){
	    	checkNum ++;
	    	rowschecked.push(showTableList[i]);
	    }
    } 
	return checkNum;
}
function hadCheckedRadioRowData(){
	if(showTableList==null || isCheckedRadio()==0){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}

function init(){
	$('input[name=radio]').each(function(i){
		if(i == 0){
			$(this).attr("checked","checked");
		}
	})
}

function updateParameter(){

	if(!hadCheckedRadioRowData()){
		return ;
	}
	$.ajax({
		url : 'getParameter',// 跳转到 action
		data : {
			paraId : rowschecked[0].paraId
			},
		type : 'post',
		dataType : 'json',
		success : function(res) {
					$("#para_id").val(res.paraId);
					$("#para_code").val(res.paraCode);
					$("#para_value").val(res.paraValue);
					$("#para_note").val(res.paraNote);
					$("#para_state").val(res.paraState);
					$('#EditPanel .form-group span.modal-error').children().remove();
					$('#EditPanel').modal();
				},
				error : function() {
					alertModel("请求失败！");
				}
			});
}
function formSubmit(){
	if(validform().form()){
		var data=$('#dataForm').serialize();
		var submitData=decodeURIComponent(data,true);
		$("#saveSet").attr("disabled",true);
		$.ajax({
		    url:'updateParameter',
		    data: submitData,
	 		type : 'post',
		    cache:false,
		    async:true,
		    success:function(result){
		        //请求成功时
		    	if(result!=null){
	    			alertModel(result.msg);
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
//验证
function validform(){
	var addnew_validate= bindformvalidate("#dataForm", {
		para_value:{
			required : true,
		},
		para_note:{
			required : true,
		}
	},{
		para_value:{
			required : "必填！"
		},
		para_note:{
			required : "必填！"
		}
	});

  return addnew_validate;
}
function allProvince(){
	$.ajax({
		url : 'queryAllProvince',   
		data : {
			count:10
		},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if (back != null) {
 				if(back.success=="1"){
 					var province = back.Obj;
 					provinceList=list = province;
 					
					$('#tb tr:gt(0)').remove();//删除之前的数据
					var s = '';
					if(list.length==1){
						//$('#userProvince').attr("disabled","disabled"); 
						$("#userProvince").append("<option value=" + list[0].prvId + ">" + list[0].prvName + "</option>");
					}else{
						$("#userProvince").append("<option value=''>" +'--请选择--'+ "</option>");
						for (var i = 0; i < list.length; i++) {
							$("#userProvince").append("<option value=" + list[i].prvId + ">" + list[i].prvName + "</option>");
						}
						$('#userProvince').prop('selectedIndex', 0);
					}
					showTable();
 				}else{
					alertModel(back.msg);
 				}
 			}
		},
		error : function() {
			alertModel("请求异常");
		}
	})
} 

