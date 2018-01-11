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
		url : "queryWiteDoGrpDataCollectVO", // 获取数据的地址
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
			var pages=params.pageNumber
			if(	pages<=0){
				pages=1;
			}
			var param = {
					pageNumber:pages,    
					pageSize: params.pageSize,
					datacollectTitle : $("#datacollectTitle").val(),
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
            title: '标题'
        },{
            field: 'datacollectDeadline',
            title: '处理时限',
            formatter : function(value,row,index){
            	return value.substring(0,16)
            }
            /*formatter : function(value,row,index){
        		return new Date(value).format("yyyy-MM-dd hh:mm");
            }*/
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
        },/*{
        	field: '',
            title: '处理人'
        },*/{
            field: '',
            title: '操作',
            formatter : operateFormatter
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
function operateFormatter(row,data){
	var operate='<div class="btn-group">';
		operate+='<button type="button" class="btn btn-primary btn-sm" title="修改" onclick="updateDatas(2,\''+data.datacollectId+'\')"><i class="glyphicon glyphicon-pencil" ></i></button>';
		operate+='<button type="button" class="btn btn-danger btn-sm" title="删除" onclick="deleteDatas(\''+data.datacollectId+'\')"><i class="glyphicon glyphicon-trash" ></i></button>';
		operate+='</div>';
	return operate;
}
/**
 * 新增
 */
function insertDatas(type){
	$.ajax({
 		url : 'deleteUserLessMsg',
		type : 'post',
		cache : false,
		dataType : 'json',
		contentType: "application/json;charset=utf-8",
 	});
	window.location.href='collectionDataDraftDetails.html?type='+type;
}
/**
 * 修改
 */
function updateDatas(type,id){
	window.location.href='collectionDataDraftDetails.html?type='+type+"&datacollectId="+id;	
}
/**
 * 删除
 */
var deleteOperationId;
function deleteDatas(id){
	deleteOperationId = id;
	confirmModel("您确定要删除吗？","deleteOperation");
}
function deleteOperation(){
 	$.ajax({
 		url : 'deleteByPrimaryKey',
 		data : {
 			datacollectId:deleteOperationId
 		},
		type : 'post',
		cache : false,
		dataType : 'json',
 		success : function(data) {
 			if (data != null) {
 				loadTableData();
				alertModel(data.msg);
 			}
 		},
 		error : function() {
			alertModel("请求失败！");
 		}
 	});
}
