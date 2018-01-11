
//已显示表格list
var showTableList = null;

$(document).ready(function() {
	var myDate = new Date();
	var str = "" + myDate.getFullYear();
	str += (myDate.getMonth()+1);
	$('#datetimepicker').val(str); 
    $('#datetimepicker').datetimepicker({
       	format: 'YYYY/MM',
		locale: moment.locale('zh-cn')
    });
    initCurrentPage();
});
function initCurrentPage(){
	curPageNum = 1;
	getAddress();
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
		url : "querySummaryPunish", // 获取数据的地址
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
					punishYearMonth : $("#datetimepicker").val(),
					regId : $("#City option:selected").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        },{
            field: 'regName',
            title: '地市'
        },{
            field: 'punishYearMonth',
            title: '年/月份'
        },{
            field: 'groupPunishAmount',
            title: '集团考核指标扣罚（元）'
        },{
            field: 'provincePunishAmount',
            title: '省内考核指标扣罚（元）'
        },{
            field: 'regionPunishAmount',
            title: '地市考核指标扣罚（元）'
        },{
            field: 'sumPunishAmount',
            title: '扣罚费用总计（元）'
        },],
		/*onLoadSuccess : function() { // 加载成功时执行
			console.log("加载成功");
		},*/
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				showTableList = res.Obj.resultAny;
			}
	        return {
	            "total": res.Obj.total,//总页数
	            "rows": res.Obj.resultAny, //数据
	         };
		}
	});	
}

function loadInfo(obj){
	 var twrRentInfoId=$(obj).parent().next().text();
	 window.location.href="punishInner.html?twrRentInfoId="+twrRentInfoId+"&goType=1";
}

/**
 * 帮助
 */

function helpExamination(){
	
}


