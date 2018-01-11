var operate_type = 1;// 1 新增，2 修改
var curPageNum = 0;
$(document).ready(function() {
	initialize();
});
/**
 * 初始化加页面
 */
function initialize() {
	curPageNum = 1;
	findData();
}
/**
 * 列表查询
 */
function findData() {
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "list", // 获取数据的地址
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
					reg : $("#reg").val(),
					status : $("#status option:selected").val(),
					devType : $("#devType option:selected").val(),
					cur_page_num: params.pageNumber,    
					page_count: params.pageSize
			};
			return param;
		},
		columns: [{
            field: 'Number',
            title: '序号',
            formatter: function (value, row, index) {
                   return index+1;
            }
        }, {
            field: 'basestationCategory',
            title: '设备类型',
            formatter: fmtDevType
        }, {
            field: 'basestationCuid',
            title: '设备CID'
        }, {
            field: 'basestationCode',
            title: '设备编号'
        }, {
            field: 'basestationName',
            title: '设备名称'
        }, {
            field: 'basestationOpendate',
            title: '入网时间',
        	formatter : function(value){
        		return new Date(value).format("yyyy-MM-dd");
            }
        }, {
            field: 'baseresourceName',
            title: '所属机房/资源点/热点'
        }, {
            field: 'basestationVendor',
            title: '设备制造商',
            formatter : selectDeviceName
        }, {
            field: 'basestationModel',
            title: '设备型号'
        }, {
            field: 'basestationPower',
            title: '设备功率'
        }],
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
	$.ajax({
		url : 'export',// 跳转到 action
		type : 'post',
		contentType : "application/json;charset=UTF-8",
		dataType : 'json',
		data : {
			devType : $("#devType option:selected").val(),
			reg : $("#reg").val(),
			status : $("#status option:selected").val()
		},
		beforeSend: function () {
		    $("#loading").html("<img src=\""+ +"/asserts/image/loading-b.gif\"/>");
		},
		success : function(data) {
			$("#loading").html("");
			if (data != null) {
				alertModel(data.msg);
			}
		},
		error : function() {
			$("#loading").html("");
			alertModel("导出数据异常");
		}
	});
}

