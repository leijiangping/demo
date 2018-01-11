//已显示表格list
var showTableList = null;
$(document).ready(function() {
	initCurrentPage();
});

function initCurrentPage(){
	curPageNum = 1;
	getAddress();
	loadTableData();
}
//条件查询
function loadTableData(type) {
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "listAll", // 获取数据的地址
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
				cur_page_num : params.pageNumber,    
				page_count : params.pageSize,
	            reg : 	$('#contractName').val(),
	            city :	$("#city option:selected").val(),
	            region :	$("#region option:selected").val(),
	            status :	$("#contractState option:selected").val(),
	            dataFrom : dataFrom
			};
			return param;
		},
		columns: [{
            field: 'contractCode',
            title: '固化信息编码',
            formatter:clickFormatter
        },{
            field: 'contractName',
            title: '固化信息名称'
        }, {
            field: 'pregName',
            title: '所属地市'
        }, {
            field: 'regName',
            title: '所属区县'
        }, {
        	field: 'isDownshare',
            title: '是否向下共享',
            formatter:function(value,row,index){
            	switch(value){
        		case 1:return '是';
        		case 0:return '否';
        	}
        	return value;
            }
        }, {
        	field: 'contractState',
            title: '固化信息状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 0:return '正常';
            		case 9:return '终止';
            		case -1:return '已删除';
            	}
            	return value;
            }
        }, {
            field: 'auditingState',
            title: '审核状态',
            formatter:function(value){
            	switch(value){
            		case 0:return '审核通过';
            		case 8:return '审核未通过';
            		case 9:return '审核中';
            		case -1:return '未提交';
            	}
            	return value;
            }
        }, {
            field: 'contractStartdate',
            title: '固化信息期始',
            formatter : function(value){
            	return new Date(value).format("yyyy-MM-dd");
            }
        }, {
            field: 'contractEnddate',
            title: '固化信息终止',
        	formatter : function(value){
        		return new Date(value).format("yyyy-MM-dd");
            }
        }, {
            field: 'contractYearquantity',
            title: '固化信息年限'
        }
        ],
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success != "1"){
		            alertModel(res.msg);
				}
				showTableList = res.obj.list;
			}
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.list //数据
	         };
		}
	});
}
//导出
function exportData(){
	var para="reg="+$('#contractName').val()+"&city="+$("#city option:selected").val();
	para+="&region="+$("#region option:selected").val()+"&status="+$("#contractState option:selected").val()+"&opType=0";
	window.open("export?"+para);
	
}

/**
 * add by jiacy
 * 查看用户信息
 */
function viewRecord(obj) {
	var elecId=$(obj).parent().next().text();
	window.location.href='view.html?elecContractId='+elecId;
}


function clickFormatter(value, row, index){
	return '<a href="view.html?elecContractId='+row.elecontractId+'">'+value+'</a>';
}