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
		url : projectName + "/twrAccountsummary/queryTwrAccountSummeryList", // 获取数据的地址
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
					pregId :$("#City option:selected").val(),
					year  :	$("#year option:selected").val(),
					month :	$("#month option:selected").val(),
					state :	$("#state option:selected").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        },{
            field: 'state',
            title: '审核状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 0:return '审核通过';
            		case 8:return '审核未通过';
            		case 9:return '审核中';
            		case -1:return '未提交';
            	}
            	return value;
            }
        },{
            field: 'yearmonth',
            title: '账期'
        },{
            field: 'operatorSysRegion.pregName',
            title: '地市'
        }, {
        	field: 'sumAmountType',
            title: '费用类型'
        }, {
            field: 'sumTowerAmount',
            title: '产品服务费-确认金额（不含税）'
        },{
            field: 'sumDiffAmount',
            title: '产品服务费-调整后金额（不含税）'
        },{
            field: 'sumCalcAmount',
            title: '产品服务费-结算金额（不含税）'
        },{
            field: 'sumHistAmount',
            title: '历史销账追溯费用（不含税）'
        }, {
            field: 'sumHistAmount',
            title: '历史欠费追溯费用（不含税）'
        }, {
            field: 'debitType',
            title: '扣款类型'
        }, {
        	field: 'sumBackAmount',
            title: '服务质量退款（不含税）'
        }, {
            field: 'sumTotalAmount',
            title: '合计金额(不含税）'
        }, {
            field: 'sumTotalAmountTax',
            title: '合计金额(含税）'
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
 * 审核
 */
function auditExamination(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var itemId = rowschecked[0].accountsummaryId;
	var taskId = rowschecked[0].act.taskId;
	window.location.href='costCheckNew.html?accountsummaryId=' + itemId +"&taskId="+taskId;
}

/**
 * 加载审核人 
 */
function queryCurUser(){
	$.ajax({
		url:projectName+'/twrAccountsummary/queryCurUser',
		data:{
			conutn:1
		},
		type:'get',
		dataType:'json',
		success:function(back){
			if (back != null) {
				item = back.obj;
				if(back.success=="1"){
					$("#curUser").text(item[0]);
				}
			}
		},
		error:function(){
			alertModel("请求异常");
		}
	})
}

//批量审核
function submitAudit(){
	if(!hadCheckedRowData()){
		return false;
	}
	
	queryCurUser();
	//批量审核不指定审核人，因为有可能所处环节不一样
	var taskNameObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		taskNameObjs.push(row.act.taskDefKey);
	}
	for(var i=0; i<taskNameObjs.length-1;i++){
		if(taskNameObjs[i] != taskNameObjs[i + 1]){
			alertModel("所有的数据必须在同一审核环节");
			return false;
		}
	}
	
	var itemId = rowschecked[0].accountsummaryId;//选中的一条数据的id
	findNextUsersByBuniessKey(TwrAccountsummary.tableName,itemId,"saveCheck");
}

function saveCheck(){
	var checkState=$('#checkState option:selected').val(),
	auditTime=$('#auditTime').val(),
	curUser=$('#curUser').html(),
	nextUser=$('#nextUsers option:selected').val(),
	comment=$('#comment').val();
	if($("#nextUsers option").length > 1){
		if($('#nextUsers option:selected').val() == ""){
			alertModel("下级审核人不能为空！");
			return false;
		}
	}
	if(checkState == 8){
		if(comment == ""){
			alertModel("审核不通过必须填写审核备注/意见！");
			return false;
		}
	}
	
	var datasObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		var obj={
		    	"auditState":checkState,
		    	"auditTime":auditTime,
		    	"nextAuditUserId":nextUser,
		    	"auditNote":comment,
		    	"taskId":row.act.taskId,
		    	"accountId":row.accountsummaryId
		    };
		datasObjs.push(obj);
	}
	
	$.ajax({
		url : projectName + '/twrAccountsummary/updateComplete',// 跳转到 action    
		data :JSON.stringify(datasObjs),
		type : 'post',
		dataType : 'json',
		contentType : 'application/json;charset=utf-8',
		success : function(res) {
			if (res != null) {
				alertModel(res.msg);
				loadTableData();
			}
			$('#NextUsersModel').modal('hide');
			
		},
		error : function() {
			alertModel("请求失败");
		}
	});
}

//保存
/*function formSubmit(){
	var checkState=$('#checkState option:selected').val(),
	auditTime=$('#auditTime').val(),
	curUser=$('#curUser').html(),
	nextUser=$('#nextUser option:selected').val(),
	accountIds = $('#accountIds').val();
	comment=$('#comment').val();
	$.ajax({
		type : "post",
		url : projectName + "/twrAccountsummary/saveCheckInfo",
		data : {
			"accountIds":accountIds,//审批数据ID
			"checkState":checkState,//审核状态
			"auditTime":auditTime,//审核时间
			"curUser":curUser,//审核人
			"nextUser":nextUser,//下级审核人
			"comment":comment//审核备注
		},
		dataType : 'json',
		success : function(data) {
			$('#EditPanel').modal('hide');
		},
		error : function(data) {
			console.log('保存失败')
		}
	});
}*/

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
