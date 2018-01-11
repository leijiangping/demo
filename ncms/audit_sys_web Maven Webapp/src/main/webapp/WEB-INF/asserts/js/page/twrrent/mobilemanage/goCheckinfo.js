$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	var rentinformationId=getParam("rentinformationId");
	var endDate=getParam("endDate");
	loadTableData(rentinformationId,endDate);
	// 审核数据详情
	loadTowerResourceInfo(rentinformationId,endDate);
	queryCurUser();
	checkInfo(rentinformationId);
	findUserByRoleIds(rentinformationId);
}
//拿到地址参数值
function getParam(paramName) {  
    paramValue = "",
    isFound = !1;  
    if (this.location.search.indexOf("?") == 0 && this.location.search.indexOf("=") > 1) {  
        arrSource = unescape(this.location.search).substring(1, this.location.search.length).split("&"), i = 0;  
        while (i < arrSource.length && !isFound) arrSource[i].indexOf("=") > 0 &&
        arrSource[i].split("=")[0].toLowerCase() == paramName.toLowerCase() &&
        (paramValue = arrSource[i].split("=")[1], isFound = !0), i++  
    }  
    return paramValue == "" && (paramValue = null), paramValue  
}


//审核展示信息
function loadTowerResourceInfo(rentinformationId,endDate){
	$.ajax({
		type:'post',
		contentType: "application/x-www-form-urlencoded;charset=utf-8",
		url:'queryTwrRentInformationHistoryCheck',
		data:{
			rentinformationId:rentinformationId,
			pageNum:1,    
			pageSize: 10,
			endDate:endDate
	    }, 
	    dataType : 'json',
		success:function(back){
			if(back!=null){
				console.log(back);
				if(back.success == "1"){
					console.log(back)
				}
			}
		}
	})
}

function goBack(){
	window.history.go(-1);
}

function queryCurUser(){
	$.ajax({
		url:'queryCurUser',
		data:{
			conutn:1
		},
		type:'get',
		dataType:'json',
		success:function(back){
			if (back != null) {
				item=back.obj;
				if(back.success=="1"){
					$("#curUser").text(item[0]);
				}
			}
		},
		error:function(){
			alert("请求异常");
		}
	})
}

function findUserByRoleIds(rentinformationId){
	findUsersByRoleIds(TwrRentInformation.tableName,rentinformationId);
}

function checkInfo(rentinformationId){
	histoicFlowList(TwrRentInformation.tableName,rentinformationId);
}
var rentInformation=getParameter("rentInformation");
function formSubmit(){
	var rentinformationId=getParam("rentinformationId");
	var taskId=getParameter("taskId");
	var date=getParam("endDate");
	var state=$("#checkState option:selected").val();
	var	comment=$("#comment").val();
	var leader=null;
	if($("#nextUsers option:selected").val()!=null){
		leader=$("#nextUsers option:selected").val();	
	}
	var rentinformationIds = new Array();
	// 从选中行中挑出可以启用的item
	var obj={
			"state":state,
			"comment":comment,
			"leader":leader,
			"rentinformationId":rentinformationId,
			"taskId":taskId,
			"endDate":date
    };
	rentinformationIds.push(obj);
	$("#saveSet").attr("disabled",true);
	$.ajax({
		url:'saveCheckInfo',
		data:JSON.stringify(rentinformationIds),
		type:'post',
		dataType:'json',
		contentType : 'application/json;charset=utf-8',
		success:function(back){
			if (back != null) {
				if(back.success=="1"){
					window.location.href='changeCheck.html';
				}
			}
			$("#saveSet").attr("disabled",false);
		},
		error:function(){
			alert("请求异常");
			$("#saveSet").attr("disabled",false);
		}
	})
}


//变更表格数据
function loadTableData(rentinformationId,endDate){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryTwrRentInformationChangeCheck", // 变更记录
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		pageSize : ipageCount, // 每页显示的记录数
		pageNumber : curPageNum, // 当前第几页
		minimumCountColumns: 1,  //最少允许的列数
		fixedColumns:true,
		fixedNumber:5,
		clickToSelect: true,  //是否启用点击选中行
		pageList : [10, 25, 50, 100, 500], // 记录数可选列表
		search : false, // 是否启用查询
		sidePagination : "server", // 表示服务端请求
		// 设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
		// 设置为limit可以获取limit, offset, search, sort, order
		queryParamsType : "undefined",
		queryParams : function queryParams(params) { // 设置查询参数
			var param = {
					rentinformationId:rentinformationId,
					endDate:endDate
			};
			return param;
		},
		columns: [{
	        checkbox: true
	    }, {
	        field: 'fielNdame',
	        title: '变更内容'
	    }, {
	        field: 'oldValue',
	        title: '变更前'
	    },{
	        field: 'newValue',
	        title: '变更后'
	    },],
		/*onLoadSuccess : function() { // 加载成功时执行
			console.log("加载成功");
		},*/
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			console.log(res)
			if(res != null){//报错反馈
				if(res.success != "1"){
		            //alertModel(res.msg);
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
							

