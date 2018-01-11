//已显示表格list
var showTableList = null;
$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	curPageNum = 1;
	// 查询默认条件表单数据
	loadTableData();
}
function loadTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryDoDataCollectPrvVO", // 获取数据的地址
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
					datacollectTitle : $("#datacollectTitle").val(),
					datacollectPrvState : $("#datacollectPrvState").val(),
					startDatacollectDeadline : $("#startDatacollectDeadline").val(),
					endDatacollectDeadline : $("#endDatacollectDeadline").val(),
					order:$("#order").val()
			};
			return param;
		},
		columns: [{
            title: '序号',
            formatter: function (value, row, index) {  
                return index+1;  
            }  
        },{
            field: 'grpDatacollectVO.datacollectTitle',
            title: '标题',
        },{
            field: 'grpDatacollectVO.datacollectDeadline',
            title: '处理时限',
            formatter : function(value,row,index){
            	return value.substring(0,16)
            }
            /*formatter : function(value,row,index){
        		return new Date(value).format("yyyy-MM-dd hh:mm");
            }*/
        },{
            field: 'datacollectPrvState',
            title: '状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 0:return '待签收';
            		case 9:return '已签收，省公司收集';
            		case -1:return '被驳回，重新收集';
            		case 8:return '已被他人签收';
            	}
            	return value;
            }
        },{
        	field: 'datacollectPrvUser',
            title: '处理人'
        },{
            field: '',
            title: '处理操作',
            formatter : operateFormatter
        },],
		onLoadError : function() { // 加载失败时执行
		},
		responseHandler: function(res) {
			
			if(res != null){//报错反馈
				
				if(res.success != "1"){
		            alertModel(res.msg.result);
				}
				showTableList = res.Obj.result;
				console.log(showTableList)
			}
	        return {
	            "total": res.Obj.total,//总页数
	            "rows": res.Obj.result, //数据
	         };
		}
	});	
}
/*function operateFormatter(row,data){
	return '<button type="button" class="btn btn-primary btn-sm" title="签收" onclick="uploadDatas(\''+data.datacollectId+'\')"><i class="glyphicon glyphicon-hand-right" ></i></button>'
}*/
function operateFormatter(row,data){
	var operate='<div class="btn-group">';
		if(data.datacollectPrvState == 0){
			operate+='<button type="button" class="btn btn-primary btn-sm" title="签收" onclick="signDatas(\''+data.datacollectPrvId+'\')"><i class="glyphicon glyphicon-edit" ></i></button>';
		}else if(data.datacollectPrvState == 9){
			operate+='<button type="button" class="btn btn-primary btn-sm" title="上传文件" onclick="uploadDatas(\''+data.datacollectPrvId+'\')"><i class="glyphicon glyphicon-hand-right" ></i></button>';			
		}else if(data.datacollectPrvState == -1){
			operate+='<button type="button" class="btn btn-primary btn-sm" title="上传文件" onclick="uploadDatas(\''+data.datacollectPrvId+'\')"><i class="glyphicon glyphicon-hand-right" ></i></button>';			
		}else if(data.datacollectPrvState == 8){
			operate+='<button type="button" disabled="disabled" class="btn btn-info btn-sm" title="已被他人签收"><i class="glyphicon glyphicon-minus" ></i></button>';			
		}		
		operate+='</div>';
	return operate;
}

/**
 * 签收
 */
function signDatas(id){
	$.ajax({
 		url : "updateGrpToUserSelf",
 		data : {
 			datacollectPrvId:id
 		},
 		type : 'post',
		cache : false,
 		dataType : 'json',
		//contentType: "application/json;charset=utf-8",
 		success : function(back) {
 			if (back != null) {
 				loadTableData();
				alertModel(back.msg);
 			}
 		},
 		error : function(back) {
			alertModel(back.msg);
 		}
 	});
}
/**
 * 文件上传
 */
function uploadDatas(id){
	window.location.href='reportDataProAgencyDetails.html?datacollectPrvId='+id;
}


/**
 * 上报
 * */
function reportDatas(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var datacollectPrvState = rowschecked[0].datacollectPrvState;
	if(datacollectPrvState == 1){
		alertModel("已经上报的不能再次上报！");
		return;
	}
	if(datacollectPrvState == -1){
		alertModel("已驳回的不能上报！");
		return;
	}
	confirmModel("您确定要上报吗？","reportDatasOperation");
}
function reportDatasOperation(){
	var reportObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		reportObjs.push(row.datacollectPrvId);
		console.log(reportObjs);
	}
 	$.ajax({
 		url : "updateDataPrvById",
 		data : {
 			datacollectPrvId:reportObjs.join(",")
 		},
 		type : 'post',
		cache : false,
 		dataType : 'json',
		//contentType: "application/json;charset=utf-8",
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