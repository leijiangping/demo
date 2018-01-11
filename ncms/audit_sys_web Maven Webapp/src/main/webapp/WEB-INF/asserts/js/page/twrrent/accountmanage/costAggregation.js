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
	getAddressForModalCity();
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
		url : projectName + "/twrAccountsummary/queryAccountsummaryPage", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		pageSize : ipageCount, // 每页显示的记录数
		pageNumber : curPageNum, // 当前第几页
		minimumCountColumns: 1,  //最少允许的列数
		clickToSelect: true,  //是否启用点击选中行
		/*fixedColumns: true,
        fixedNumber:5,*/
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
					regId :	$("#City option:selected").val(),
		            yearmonth :	$("#year option:selected").val() + $("#month option:selected").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        },{
            field: 'yearmonth',
            title: '账期'
        },{
            field: 'reg_name',
            title: '地市'
        },{
            field: 'sum_amount_type',
            title: '费用类型'
        }, {
        	field: 'sum_tower_amount',
            title: '产品服务费-确认金额（不含税）'
        }, {
            field: 'sum_adjustment_amount',
            title: '产品服务费-调整金额（不含税）'
        }, {
            field: 'sum_diff_amount',
            title: '产品服务费-调整后金额（不含税）'
        },{
            field: 'sum_calc_amount',
            title: '产品服务费-结算金额（不含税）'
        },{
            field: 'sum_hist_amount',
            title: '历史销账追溯费用（不含税）'
        }, {
            field: 'debit_type',
            title: '扣款类型'
        }, {
            field: 'sum_back_amount',
            title: '服务质量退款（不含税）'
        }, {
        	field: 'sum_total_amount',
            title: '合计金额(不含税）'
        }, {
            field: 'sum_total_amount_tax',
            title: '合计金额(含税）'
        }, {
            field: 'state',
            title: '状态',
            formatter : function (value, row, index) {
                var htmls = '';
                if(value == -1){
                	htmls += '待提交';
                }else if(value == 9){
                	htmls += '审核中';
                }else if(value == 0){
                	htmls += '审核通过';
                }else if(value == 8){
                	htmls += '<font style="color:red;">审核未通过</font>';
                }
                return htmls;
            }
        }],
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

//生成汇总单
function summarySheet(){
	$('#EditPanel').modal();
}
//保存
function formSubmit(){
	confirmModel('您确定要生成吗？','confirmOk')
}
function confirmOk(){
	$('#EditPanel').modal('hide');
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

/**
 * 获取用户所有权限 的地市 区县信息
 * 
 * @param title 名称 例如：供应商地市，传入title为供应商
 */
function getAddressForModalCity(title){
	$.ajax({
		type : "post",
		url :projectName+"/rent/common/getAddressRent",
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		success : function(value) {
			if(value != null){
				sysReguins = value.obj;
				if(sysReguins!=null){
					$('#modal_city').empty();
					var strCity = "<option value=''>-选择"+(title?title:"")+"地市-</option>";
					$.each(sysReguins, function (i, item){
						strCity += "<option value='" +item.regId+"'>"+item.regName+ "</option>";
					});

					$("#modal_city").append(strCity);

				}
			}
		},
		error : function(data) {
			alertModel('获取用户地区信息失败!');
		}
	});
}

var generateAccountsummary = function(){
	var checked = 0;
	var modalCity = $('#modal_city option:selected').val();
	if(modalCity == ''){
		alertModel("请选择地市");
		return;
	}
	var yearmonth = ($('#modal_year option:selected').val() + $('#modal_month option:selected').val());
	$feeTypeArray = $('input[name="feeType"]:checked');
	$debitType = $('input[name="debitType"]:checked');
	var feeTypeStr = '';
	if($feeTypeArray != undefined && $feeTypeArray.length > 0){
		$.each($feeTypeArray,function(i,item){
			feeTypeStr += item.value;
			if((i+1) < $feeTypeArray.size()){
				feeTypeStr += ',';
			}
			checked += 1;
		});
	}
	
	var debitTypeStr = '';
	if($debitType != undefined && $debitType.length > 0){
		$.each($debitType,function(i,item){
			debitTypeStr += item.value;
			if((i+1) < $debitType.size()){
				debitTypeStr += ',';
			}
			checked += 1;
		});
	}
	if(checked == 0){
		alertModel("请至少选择一项汇总费用类型或者汇总扣款类型");
		return;
	}
	$("#saveSet").attr("disabled",true);
	$.ajax({
		type : "post",
		url : projectName + "/twrAccountsummary/addAccountsummary",
		dataType : 'json',
		data:{
			regId: modalCity,
			yearmonth: yearmonth,
			feeTypeStr: feeTypeStr,
			debitTypeStr:debitTypeStr
		},
		success : function(data) {
			if(data.success){
				alertModel(data.msg);
				loadTableData();
				$('#EditPanel').modal('hide');
			}else{
				alertModel(data.msg);
			}
			$("#saveSet").attr("disabled",false);
		},
		error : function(data) {
			alertModel('生成汇总单失败!');
			$("#saveSet").attr("disabled",false);
		}
	});
}

var revertAccountsummary = function(){
	var checkedStr = '';
	if(isChecked() > 0){
		// 从选中行中挑出可以启用的item
		for (var s = 0; s < rowschecked.length; s++) {
			var row = rowschecked[s];
			checkedStr += (row.accountsummary_id + ",");
		}
	}
	$.ajax({
		type : "post",
		url : projectName + "/twrAccountsummary/revertAccountsummary",
		dataType : 'json',
		data:{
			accountsummaryIds: checkedStr
		},
		success : function(data) {
			if(data.success){
				alertModel(data.msg);
				loadTableData();
				$('#EditPanel').modal('hide');
			}else{
				alertModel(data.msg);
			}
		},
		error : function(data) {
			alertModel('生成汇总单失败!');
		}
	});
}

//var submitAccountsummaryApproval = function(){
//	var checkedStr = '';
//	if(isChecked() > 0){
//		// 从选中行中挑出可以启用的item
//		for (var s = 0; s < rowschecked.length; s++) {
//			var row = rowschecked[s];
//			checkedStr += (row.accountsummary_id + ",");
//		}
//	}
//	$.ajax({
//		type : "post",
//		url : projectName + "/twrAccountsummary/applyAccountsummaryApproval",
//		dataType : 'json',
//		data:{
//			accountsummaryIds: checkedStr
//		},
//		success : function(data) {
//			if(data.success){
//				alertModel(data.msg);
//				loadTableData();
//				$('#EditPanel').modal('hide');
//			}else{
//				alertModel(data.msg);
//			}
//		},
//		error : function(data) {
//			alertModel('生成汇总单失败!');
//		}
//	});
//}

/**
 * 提交审核
 */
function submitAudit(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var stateObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		stateObjs.push(row.state);
	}
	// 判断是否有"审核中"的
	if($.inArray(9, stateObjs)>=0){
		alertModel("审核中的无法提交审核");
		return false;
	}
	// 判断是否有"审核通过"的
	if($.inArray(0, stateObjs)>=0){
		alertModel("审核通过的无法提交审核");
		return false;
	}
	queryFirstUsersByProcDefKey(TwrAccountsummary.procDefKey,"startActFlow");

}

/**
 * 提交流程，开始审核流程
 */
function startActFlow(){
	$('#firstUsers').modal("hide");
	var twrAccountsummaryIds = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		twrAccountsummaryIds.push(row.accountsummary_id);
	}
	//var twrRegPunishIds = JSON.parse(str);//把字符串转换成对象
	var nextUserId=$("input[name='userChecked']:checked").val();

	var datasObjs = new Array();
	for(var i = 0; i < twrAccountsummaryIds.length; i++){
		var id = twrAccountsummaryIds[i];
		var obj={"id":id,"nextUserId":nextUserId}
		datasObjs.push(obj);
	}

	$.ajax({
		url : projectName + "/twrAccountsummary/applyAccountsummaryApproval",// 跳转到 action    
		data :JSON.stringify(datasObjs),
		type : 'post',
		dataType : 'json',
		contentType : 'application/json;charset=utf-8',
		success : function(res) {
			if (res != null) {
				alertModel(res.msg);
				loadTableData();
			}
			$('#EditPanel').modal('hide');
			
		},
		error : function() {
			alertModel("请求失败");
		}
	});
}