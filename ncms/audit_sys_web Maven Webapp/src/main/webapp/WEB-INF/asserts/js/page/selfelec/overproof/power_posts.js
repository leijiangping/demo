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
	getYear();
	getMonth();
	loadTableData();
}

function loadTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryPowerRate", // 获取数据的地址
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
					billaccountCode:$("#billaccountCode").val(),
					billaccountState:$("#billaccountState").val(),
					pregId :$("#City").val(),
		            regId :	$("#Village").val(),
					year:$("#year option:selected").val(),
					month:$("#month option:selected").val(),
			};
			return param;
		},
		columns: [
		{
            title: '序号',
            formatter: function (value, row, index) {  
                return index+1;  
            } 
        },{
        	field: 'billaccountCode',
            title: '报账点编码'
        },{
        	field: 'billaccountName',
            title: '报账点名称'
        },{
        	field: 'billaccountState',
            title: '报账点状态',
            formatter:function(value,row,index){
            	switch(value){
            		case '0': return "正常";
            		case '9': return "暂停";
            		case '10': return "终止";
            	}
            	return value;
            }
        },{
        	field: 'pregName',
            title: '地市'
        },{
        	field: 'regName',
            title: '区县'
        },{
        	field: 'year',
            title: '年份'
        },{
        	field: 'month',
            title: '月份'
        },{
        	field: 'monthTotal',
            title: '月总标杆',
            class:"columnsRed"
        },{
        	field: 'day1',
            title: '1',
            class:"columnsRed"
        },{
        	field: 'day2',
            title: '2',
            class:"columnsRed"
        },{
        	field: 'day3',
            title: '3',
            class:"columnsRed"
        },{
        	field: 'day4',
            title: '4',
            class:"columnsRed"
        },{
        	field: 'day5',
            title: '5',
            class:"columnsRed"
        },{
        	field: 'day6',
            title: '6',
            class:"columnsRed"
        },{
        	field: 'day7',
            title: '7',
            class:"columnsRed"
        },{
        	field: 'day8',
            title: '8',
            class:"columnsRed"
        },{
        	field: 'day9',
            title: '9',
            class:"columnsRed"
        },{
        	field: 'day10',
            title: '10',
            class:"columnsRed"
        },{
        	field: 'day11',
            title: '11',
            class:"columnsRed"
        },{
        	field: 'day12',
            title: '12',
            class:"columnsRed"
        },{
        	field: 'day13',
            title: '13',
            class:"columnsRed"
        },{
        	field: 'day14',
            title: '14',
            class:"columnsRed"
        },{
        	field: 'day15',
            title: '15',
            class:"columnsRed"
        },{
        	field: 'day16',
            title: '16',
            class:"columnsRed"
        },{
        	field: 'day17',
            title: '17',
            class:"columnsRed"
        },{
        	field: 'day18',
            title: '18',
            class:"columnsRed"
        },{
        	field: 'day19',
            title: '19',
            class:"columnsRed"
        },{
        	field: 'day20',
            title: '20',
            class:"columnsRed"
        },{
        	field: 'day21',
            title: '21',
            class:"columnsRed"
        },{
        	field: 'day22',
            title: '22',
            class:"columnsRed"
        },{
        	field: 'day23',
            title: '23',
            class:"columnsRed"
        },{
        	field: 'day24',
            title: '24',
            class:"columnsRed"
        },{
        	field: 'day25',
            title: '25',
            class:"columnsRed"
        },{
        	field: 'day26',
            title: '26',
            class:"columnsRed"
        },{
        	field: 'day27',
            title: '27',
            class:"columnsRed"
        },{
        	field: 'day28',
            title: '28',
            class:"columnsRed"
        },{
        	field: 'day29',
            title: '29',
            class:"columnsRed"
        },{
        	field: 'day30',
            title: '30',
            class:"columnsRed"
        },{
        	field: 'day31',
            title: '31',
            class:"columnsRed"
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
/**
 * 导出
 */
function exportResourceInfo(){
	confirmModel("您确定要导出吗？","exportInfo");
} 

function exportInfo(){
	var para="&pregId="+ $("#City").val();
	para+="&regId="+ $("#Village").val();
	para+="&billaccountCode="+ $("#billaccountCode").val();
	para+="&billaccountState="+$("#billaccountState").val();
	para+="&year="+$("#year option:selected").val();
	para+="&month="+$("#month option:selected").val();
		
	window.open("exportPowerRate?"+para,"_blank");
}
var getYear = function(){
	var d = new Date();
	var year = $('#year');
	var modal_year = $('#modal_year');
	var curYear = d.getFullYear();//当前年
	for(var i = 0; i < 10; i++){
		if(i == 0){
			//当前年
			year.append('<option value="' + curYear + '">' + curYear + '年</option>');
			modal_year.append('<option value="' + curYear + '">' + curYear + '年</option>');
		}else{
			year.append('<option value="' + (parseInt(curYear) - i) + '">' + (parseInt(curYear) - i) + '年</option>');
			modal_year.append('<option value="' + (parseInt(curYear) - i) + '">' + (parseInt(curYear) - i) + '年</option>');
		}
	}
}

var getMonth = function(){
	var d = new Date();
	var curMonth = (d.getMonth() + 1);//当前月
	if(curMonth < 10){
		curMonth = ('0' + curMonth);
	}
	$('#month').val(curMonth);
	$('#modal_month').val(curMonth);
}
