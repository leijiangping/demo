//已显示表格list
var showTableList = null;

var operate_type = 1;// 1 新增，2 修改

$(document).ready(function() {
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
		url : "", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		pageSize : ipageCount, // 每页显示的记录数
		pageNumber : curPageNum, // 当前第几页
		minimumCountColumns: 1,  //最少允许的列数
		clickToSelect: true,  //是否启用点击选中行
		fixedColumns: true,
        fixedNumber:5,
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
					pregId :	$("#City option:selected").val(),
		            regId :	$("#Village option:selected").val(),
		            rentcontractState : $("#rentcontractState option:selected").val()
			};
			return param;
		},
		columns: [{
            checkbox: true,
        },{
	        field: '',
	        title: '',
        },{
            field: 'calpter_number',
            title: '序号',
        }, {
            field: 'calpter_city',
            title: '地市',
        },{
            field: '',
            title: '区县',
        }, {
        	field: '',
            title: '业务确认单号'
        }, {
            field: '',
            title: '铁塔公司站址编码'
        },{
            field: '',
            title: '运营商自有物理站址名称'
        },{
            field: '',
            title: '运营商自有物理站址编码'
        },{
            field: '',
            title: '详细地址'
        },{
            field: '',
            title: '经度'
        },{
            field: '',
            title: '纬度'
        },{
            field: '',
            title: '产品配置'
        },{
            field: '',
            title: '实际最高天线挂高（米）'
        },{
            field: '',
            title: 'RRU拉远时BBU是否放在铁塔公司机房'
        },{
            field: '',
            title: '铁塔既有共享客户总数'
        },{
            field: '',
            title: '机房及配套既有共享客户总数'
        },{
            field: '',
            title: '铁塔存量新增共享客户数'
        },{
            field: '',
            title: '机房及配套存量新增共享客户数'
        },{
            field: '',
            title: '0-6点是否可上站'
        },{
            field: '',
            title: '维护等级'
        },{
            field: '',
            title: '电力保障服务费模式'
        },{
            field: '',
            title: '是否具备发电条件'
        },{
            field: '',
            title: '是否选择发电服务'
        },{
            field: '',
            title: '油机发电服务费模式'
        },{
            field: '',
            title: '包干电费（元/年）（含税）'
        },{
            field: '',
            title: '油机发电服务费（元/年）（含税）'
        },{
            field: '',
            title: '超过10%高等级服务站址额外维护服务费（元/年）（含税）'
        },{
            field: '',
            title: 'BBU安装在铁塔机房的服务费（元/年）（含税）'
        },{
            field: '',
            title: '其他费用（元/年）（含税）'
        },{
            field: '',
            title: '其它费用说明'
        },{
            field: '',
            title: '铁塔基准价格（元/年）（含税）'
        },{
            field: '',
            title: '机房及配套基准价格（元/年）（含税）'
        },{
            field: '',
            title: '维护费（元/年）（含税）'
        },{
            field: '',
            title: '产品单元数'
        },{
            field: '',
            title: '场地费（元/年）（含税）'
        },{
            field: '',
            title: '电力引入费（元/年）（含税）'
        },{
            field: '',
            title: '维护费折扣'
        },{
            field: '',
            title: '场地费折扣'
        },{
            field: '',
            title: '电力引入费折扣'
        },{
            field: '',
            title: '铁塔共享折扣'
        },{
            field: '',
            title: '机房及配套享折扣'
        },{
            field: '',
            title: '服务起始日期'
        },{
            field: '',
            title: '服务结束日期'
        },{
            field: '',
            title: '产品服务费合计（元/年）不含税'
        },{
            field: '',
            title: '维护费用原始录入值'
        },{
            field: '',
            title: '场地费原始录入值'
        },{
            field: '',
            title: '电力引入原始录入值'
        },{
            field: '',
            title: '油机发电服务费原始录入值'
        },{
            field: '',
            title: '其他费用原始录入值'
        },],
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
function checkInfo(){
	/*hadCheckedRowData();*/
	window.location.href="changeAudit.html";
	
}
function hadCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}
//导出
function exportExcel(){
	
}
//帮助
function help(){
	
}