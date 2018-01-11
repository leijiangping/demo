//已显示表格list
var showTableList = null;

$(document).ready(function() {
	initCurrentPage();
	getAddress();
});
function initCurrentPage(){
	curPageNum = 1;
	// 查询默认条件表单数据
	loadTableData();
	$("#auditState").attr("disabled","disabled");
}
function loadTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryTwrRegPunishList", // 获取数据的地址
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
					regId : $("#City option:selected").val(),
					auditState:9,
					taskDefKey:$("#taskDefKey").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        },{
            field: 'punishName',
            title: '其他扣罚名称'
        },{
            field: 'punishAmount',
            title: '扣罚费用'
        },{
            field: 'punishPerson',
            title: '扣罚申请人'
        },{
            field: 'punishCause',
            title: '扣罚原因详细说明'
        },{
        	field: 'sysRegionVO.pRegName',
            title: '所属地市'
        },{
            field: 'punishYearMonth',
            title: '扣罚日期'
        },{
        	field: 'act.taskName',
            title: '审核环节'
        },{
            field: 'auditState',
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
        },],
		onLoadError : function() { // 加载失败时执行
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success=="1"){
					var list=res.obj.result;
					unique(list);					
				}
				if(res.success != "1"){
		            alertModel(res.msg.result);
				}
				showTableList = res.Obj.result;
			}
	        return {
	            "total": res.Obj.total,//总页数
	            "rows": res.Obj.result, //数据
	         };
		}
	});	
}
//加载不重复的流程环节
function unique(arr){
	// 遍历arr，把元素分别放入tmp数组(不存在才放)
	$("#taskDefKey").empty();
	$("#taskDefKey").append("<option value=''>---审核环节---</option>");
	
	var tmp = new Array();
	for(var i in arr){
		//该元素在tmp内部不存在才允许追加
		if(arr[i].act!=null&&tmp.indexOf(arr[i].act.taskDefKey)==-1){
			$("#taskDefKey").append("<option value='"+arr[i].act.taskDefKey+"'>"+arr[i].act.taskName+"</option>");
			tmp.push(arr[i].act.taskDefKey);
		}
	}
	return tmp;
}

/**
 * 审核
 */
function auditExamination(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var twrRegPunishId=rowschecked[0].twrRegPunishId;	
	var taskId=rowschecked[0].act.taskId;		
	window.location.href='cityExaminationAuditNews.html?twrRegPunishId='+twrRegPunishId+'&taskId='+taskId;
}
/**
 * 加载审核人
 * */
function queryCurUser(){
	$.ajax({
		url:projectName+'/asserts/tpl/getCurrUser',
		data:{
			conutn:1
		},
		type:'get',
		dataType:'json',
		success:function(back){
			if (back != null) {
				item=back.obj;
				if(back.success=="1"){
					$("#curUser").text(item.user_loginname);
				}
			}
		},
		error:function(){
			alertModel("请求异常");
		}
	})
}

/**
 * 批量提交审核
 */
function submitAudit(){
	if(!isChecked()){
		alertModel("请先选择至少一条数据再操作");
		return;
	}
	getAuditTime();
	queryCurUser();
//	findNextUsersByBuniessKey(TwrRegPunish.procDefKey,"startActFlow");
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
	var twrRegPunishIdObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		twrRegPunishIdObjs.push(row.twrRegPunishId);
	}
	var twrRegPunishId = rowschecked[0].twrRegPunishId;
	findNextUsersByBuniessKey(TwrRegPunish.tableName,twrRegPunishId,"NextUsersSumbit");
}

//验证
function changeSave(){
	if($("#nextUsers option").length > 1 && $("#auditState option:selected").val()=='0'){
		if($('#nextUsers option:selected').val() == ""){
			$("#nextUsers").next("#err").html("<img src=\"../../../image/error.png\"/>必选！");
			$("#nextUsers").next("#err").css({"color":"red","float":"left"});
			flag=true;
		}else{
			$("#nextUsers").next("#err").html("<img src=\"../../../image/right_1.png\"/>验证成功");
			$("#nextUsers").next("#err").css({"color":"green","float":"left"});
			flag=false;
		}
	}else{
		flag=false;
	}
	if($("#auditState option:selected").val()==8){
		if($("#auditNote").val()==""){
			$("#auditNote").next("#err").html("<img src=\"../../../image/error.png\"/>必填！");
			$("#auditNote").next("#err").css({"color":"red"});
			flag=true;
		}else{
			$("#auditNote").next("#err").html("<img src=\"../../../image/right_1.png\"/>验证成功");
			$("#auditNote").next("#err").css({"color":"green"});
			flag=false;
		}
		
	}
}
/**
 * 提交流程，开始审核流程
 */
function NextUsersSumbit(){
	changeSave();
	if(flag){
		return false;
	}
	var auditState=$('#auditState option:selected').val();//审核状态
	var auditTime=$('#auditTime').val();//审核时间
	var nextUserId=$('#nextUsers').val();//指定下级审核人
	var auditNote=$('#auditNote').val();//审核意见
	
	var datasObjs = new Array();
	for(var i = 0; i < rowschecked.length; i++){
		var row = rowschecked[i];
		var obj={
	    	"auditState":auditState,
	    	"auditTime":auditTime,
	    	"nextAuditUserId":nextUserId,
	    	"auditNote":auditNote,
	    	"taskId":row.act.taskId,
	    	"twrRegPunishId":row.twrRegPunishId
	    };
		datasObjs.push(obj);
	}	
	$.ajax({
		url : 'updateCompleteTwrRegPunish',// 跳转到 action    
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
/**
 * 帮助
 */

function helpExamination(){
	
}


