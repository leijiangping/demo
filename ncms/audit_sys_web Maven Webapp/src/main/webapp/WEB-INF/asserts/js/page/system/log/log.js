var showTableList = null;
var logIds = '';
var startTime = null;
var endTime = null;
$(document).ready(function() {
	curPageNum = 1;
	allProvince();
});

function gopage(i){
	 if(curPageNum == i)
		 return;
	 curPageNum = i;
	 queryLogMsg();
}

function queryLogMsg() {
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	if($("#log_startTime").val() != null && $("#log_startTime").val() != ''){
		var startTime = $("#log_startTime").val()+" 00:00:00";
	}
	if($("#log_endTime").val() != null && $("#log_endTime").val() != ''){
		var endTime = $("#log_endTime").val()+" 23:59:59";
	}
	
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryLogMsg", // 获取数据的地址
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
	        	prvId	 	: 	$('#province option:selected').val(),
	        	logType 	: 	$("#log_type option:selected").val(),
				startTime 	: 	startTime,
				endTime		: 	endTime,
				keyNote 	: 	$("#log_keyNote").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        },{
            field: 'userName',
            title: '用户名称'
        },{
            field: 'logType',
            title: '日志类型'
        },{
            field: 'prvName',
            title: '所属省份'
        }, {
            field: 'logNote',
            title: '操作状态'
        }, {
            field: 'logTime',
            title: '操作时间'
        }, {
            field: 'logIp',
            title: '操作IP'
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
	            "rows": res.obj.list //数据
	         };
		}
	});
}

function hadCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}

function deleteLog(){
	if(!hadCheckedRowData()){
		return;
	}
	for(var i = 0;i<rowschecked.length;i++){
		var id = rowschecked[i].logId;
		console.log(id);
		logIds += id+",";
	}
	console.log(logIds);
	$.ajax({
	    url:'deleteLogMsg',
	    data: {
	    	logIds : logIds
	    },
 		type : 'post',
	    cache:false,
	    async:true,
	    success:function(result){
	    	alertModel(result.msg);
	    	queryLogMsg();
	    },
	    error:function(result){
	    	alertModel(result.msg);
	    }
	})
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
					if(list.length==1){
						$("#province").append("<option value=" + list[0].prvId + " selected='selected'>" + list[0].prvName + "</option>");
						$("input[name='prvId']").val(list[0].prvId);
					}else{
						$("#province").append("<option value=''>" + "--请选择--" + "</option>");
						for (var i = 0; i < list.length; i++) {
							$("#province").append("<option value=" + list[i].prvId + ">" + list[i].prvName + "</option>");
							
						}
					}
					queryLogMsg();
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