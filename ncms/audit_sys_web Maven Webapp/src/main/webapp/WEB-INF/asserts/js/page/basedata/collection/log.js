var showTableList = null;
var logIds = '';
var startTime = null;
var endTime = null;
$(document).ready(function() {
	curPageNum = 1;
	allProvince();
	queryLogMsg();
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
		url : "queryHistoryLogMsg", // 获取数据的地址
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
				pageNum : params.pageNumber,    
				pageSize: params.pageSize,
	        	prvId	 	: 	$('#province option:selected').val(),
				startTime 	: 	startTime,
				endTime		: 	endTime
			};
			return param;
		},
		columns: [{
            checkbox: true
        },{
            field: 'prvName',
            title: '所属省份'
        },{
            field: 'startDatetime',
            title: '操作时间',
            formatter : dateFormat
        },{
            field: 'taskName',
            title: '任务名称'
        },{
            field: 'ftpUser',
            title: 'FTP用户'
        },{
            field: 'comment',
            title: '日志内容'
        },{
            title: '日志详情',
            formatter:function(value,row,index){
            	return "<a><font color='red' onclick='download(\""+row.taskHistoryId+"LOG\");'>下载日志</font></a>";
            }
        },{
            title: '原始文件',
            formatter:function(value,row,index){
            	return "<a><font color='blue' onclick='download(\""+row.taskHistoryId+"CSV\");'>下载原文件</font></a>";
            }
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
//				if(res.Obj.list.length == 1 && res.Obj.list[0] == '0'){
//					res.Obj.result = [];
//				}
				showTableList = res.Obj.list;
				console.log(showTableList);
			}
	        return {
	            "total": res.Obj.total,//总页数
	            "rows": res.Obj.list, //数据
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
//	if(!hadCheckedRowData()){
//		return;
//	}
//	for(var i = 0;i<rowschecked.length;i++){
//		var id = rowschecked[i].logId;
//		console.log(id);
//		logIds += id+",";
//	}
//	console.log(logIds);
	$.ajax({
	    url:'clearHistoryLogMsg',
	    data: {
	    	prvId:$('#province option:selected').val()
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
		url : '../../system/parameter/queryAllProvince',   
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

function dateFormat(value,row,index){
	if(value != null){
		return new Date(value).format("yyyy-MM-dd hh:mm:ss");
	}else{
		return "-";
	}
}

function download(key,fileType){
	$.ajax({
		url : 'validateFileExist',   
		data : {
			fileKey:key
		},
		type : 'get',
		async:false,
		dataType : 'json',
		success : function(back) {
			if (back != null) {
 				if(back.success!="1"){
					alertModel(back.msg);
 				}else{
 					window.open("download?fileKey="+key+"&fileType="+fileType,"_blank");
 				}
 			}
		},
		error : function() {
			alertModel("请求异常");
		}
	})
>>>>>>> .merge-right.r7537
}