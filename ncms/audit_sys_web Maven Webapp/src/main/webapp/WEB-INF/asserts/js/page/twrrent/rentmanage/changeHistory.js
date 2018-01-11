//已显示表格list
var showTableList = null;

var operate_type = 1;// 1 新增，2 修改
var pageNumber = 1;//默认当前页
var pageSize = 10;//默认分页数目
$(document).ready(function() {
	initCurrentPage();
});

function initCurrentPage(){
	curPageNum = 1;
	// 查询默认条件表单数据
	getAddress();
	loadTableData();
}

function loadTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	
	var operatorPhyStationCode = $('#operatorPhyStationCode').val();
	var operatorPhyStationName = $('#operatorPhyStationName').val();
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : projectName + "/twrRentInformationChange/queryTwrRentInformationChangePage", // 获取数据的地址
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
					pageNumber: params.pageNumber,    
					pageSize: params.pageSize,
					operatorPhyStationCode:operatorPhyStationCode,
					operatorPhyStationName:operatorPhyStationName,
					pregId :	$("#City option:selected").val(),
		            regId :	$("#Village option:selected").val()
			};
			pageNumber = params.pageNumber;
			pageSize = params.pageSize;
			return param;
		},
		columns: [{
            checkbox: true
        },{
            field: 'operator_phy_station_code',
            title: '运营商物理站址编码'
        }, {
        	field: 'operator_phyStation_name',
            title: '运营商物理站址名称'
        }, {
            field: 'field_name',
            title: '参数名称'
        },{
            field: 'old_value',
            title: '原数值'
        },{
            field: 'new_value',
            title: '新数值'
        },{
            field: 'user_loginname',
            title: '变更人'
        }, {
            field: 'update_time',
            title: '变更时间',
            formatter : function (value, row, index) {
                var datetime = new Date(value).Format("yyyy-MM-dd HH:mm:ss");  
                return datetime;
            }
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



function hadCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}
/**
 * 导入
 */
function importBill(){
	$('#EditCost').modal();
}	

function formSubmit(){
	var data=$('#dataForm').serialize();
	var submitData = decodeURIComponent(data,true);
 	$.ajax({
 		url : '',
 		data : submitData,
 		type : 'post',
		cache : false,
 		dataType : 'json',
		contentType: "application/json;charset=utf-8",
 		success : function(back) {
 			console.log(back);
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
/**
 * 导出
 */
function exportBill(){
	confirmModel('您确定要导出吗？','execRxport');
} 

var execRxport = function(){
	var operatorPhyStationCode = $('#operatorPhyStationCode').val();
	var operatorPhyStationName = $('#operatorPhyStationName').val();
	
	var checkedStr = '';
	
	var pregId = $("#City option:selected").val();
    var regId =	$("#Village option:selected").val();
	
	var url = projectName + '/twrRentInformationChange/exportTwrRentInformationChange?pageNumber=' + pageNumber + '&pageSize=' + pageSize;
	if(operatorPhyStationCode != ''){
		url += ('&operatorPhyStationCode=' + operatorPhyStationCode);
	}
	
	if(operatorPhyStationName != ''){
		url += ('&operatorPhyStationName=' + operatorPhyStationName);
	}
	
	if(pregId != ''){
		url += ('&regId=' + regId);
	}
	
	if(pregId != ''){
		url += ('&pregId=' + pregId);
	}
	
	if(isChecked() > 0){
		// 从选中行中挑出可以启用的item
		for (var s = 0; s < rowschecked.length; s++) {
			var row = rowschecked[s];
			checkedStr += (row.rentinformationchange_id + ",");
		}

		url += ('&checkedStr=' + checkedStr);
	}
	
	var selectStyle = $("#educeStyle option:selected").val();
	selectStyle = 0;
	url += ('&selectStyle=' + selectStyle);
	console.log(url);
	window.open(url);
}

//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
 var o = {
     "M+": this.getMonth() + 1, //月份 
     "d+": this.getDate(), //日 
     "h+": this.getHours(), //小时 
     "m+": this.getMinutes(), //分 
     "s+": this.getSeconds(), //秒 
     "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
     "S": this.getMilliseconds() //毫秒 
 };
 if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
 for (var k in o)
 if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
 return fmt;
}