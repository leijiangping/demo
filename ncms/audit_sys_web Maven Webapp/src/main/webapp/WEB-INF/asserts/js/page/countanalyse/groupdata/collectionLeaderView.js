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
		url : "queryWatchGrpDataCollectVO", // 获取数据的地址
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
					datacollectState : $("#datacollectState").val(),
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
            field: 'datacollectTitle',
            title: '标题',
        	formatter:detailsFormatter
        },{
            field: 'datacollectDeadline',
            title: '处理时限',
            /*formatter : function(value,row,index){
            	return value.substring(0,16)
            }*/
            formatter : function(value,row,index){
        		if(value != null){
        			return value.substring(0,16);
            	}else{
            		return "-";
            	}
            }
        },{
            field: 'datacollectState',
            title: '状态',
            formatter:function(value,row,index){
            	switch(value){
	            	case 0:return '草稿';
	        		case 1:return '集团收集中';
	        		case 11:return '已完结';
            	}
            	return value;
            }
        },{
            field: 'datacollectDate',
            title: '派发时间',
            formatter : function(value,row,index){
            	return value.substring(0,16)
            }
            /*formatter : function(value,row,index){       		
        		if(value != null){
        			return new Date(value).format("yyyy-MM-dd hh:mm");
            	}else{
            		return "-";
            	}
            }*/
        },{
        	field: 'datacollectUser',
            title: '派发人'
        },{
            field: '',
            title: '短信发送状态',
            formatter : sendFormatter
        },],
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

function detailsFormatter(value, row, index){
	return '<a href="collectionLeaderViewDetails.html?datacollectId='+row.datacollectId+'">'+value+'</a>';
}
/**
 * 短信发送状态弹出页面
 * */
function sendFormatter(value, row, index){
	var datacollectId=row.datacollectId;
	return '<a href="javascript:loadInfo(\''+datacollectId+'\');">查询</a>';
}
function loadInfo(datacollectId){
	var id = datacollectId;
	// 先销毁表格
	$('#tbs').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tbs").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryHistroyByCollId", // 获取数据的地址
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
					grpDatacollectId: id
			};
			return param;
		},
		columns: [{
            field: 'prvSname',
            title: '省份名称',
        },{
            field: 'roleName',
            title: '角色名称'
        },{
            field: 'userName',
            title: '用户名称',
        },{
            field: 'isSendSuccess',
            title: '短信发送状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 0:return '发送成功';
            		case 1:return '发送失败';
            	}
            	return value;
            }
        },{
            field: 'sendBackMsg',
            title: '备注',
        },],
		responseHandler: function(res) {			
			if(res != null){//报错反馈				
				if(res.success != "1"){
		            alertModel(res.msg.result);
				}
				$('#EditPanel').modal();
				sendShowTableList = res.Obj.result;
				console.log(sendShowTableList)
			}
	        return {
	            "total": res.Obj.total,//总页数
	            "rows": res.Obj.result, //数据
	         };
		}
		
	});	
}