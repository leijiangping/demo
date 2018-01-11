var dataFrom = 0;
//已显示表格list
var showTableList = null;
$(document).ready(function() {
	initCurrentPage();
});

function initCurrentPage(){
	curPageNum = 1;
	getAddress();
}

//条件查询
function loadTableData(type) {
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	var dataFrom = null;
	if(type == 0){
		dataFrom = 2;
	}
	else if(type == 2){
		dataFrom = 3;
	}
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
            checkbox: true
        },{
            field: 'contractCode',
            title: '固化信息编码',
            formatter:clickFormatter
        }, {
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
            formatter: fmtContractState
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
// 新增固化信息
function addnewContract(){
	localStorage.setItem("optype",1);
	window.location.href='edit.html';
}
//修改固化信息信息
function updateContract(){
	var rows = getSelectItem(1);
	if(rows == null){
		return;
	}
	if(rows[0].contractState != 0){
		alertModel("此固化信息已经停用，无法修改！");
		return;
	}
	if(rows[0].auditingState == 9){
		alertModel("此固化信息已经进入流程，无法修改！");
		return;
	}
	var elecontractId = rows[0].elecontractId;
	localStorage.setItem("elecontractId",elecontractId);
	localStorage.setItem("optype",2);
	var contractCode = rows[0].contractCode;
	localStorage.setItem("oragionCode",contractCode);
	var flagUpdateRent = $("#flagUpdateRent").val();
	localStorage.setItem("flagUpdateRent",flagUpdateRent);
	window.location.href="edit.html";
}


//删除固化信息
function deleteContract(){
	
	var rows = getSelectItem(0);
	if(rows == null){
		return;
	}
	for (var i = 0; i < rows.length; i++) {
		if(rows[0].auditingState == 9){
			alertModel("此固化信息已经进入流程，无法删除！");
			return;
		}
		if(rows[0].auditingState == 0){
			alertModel("已经审核通过的固化信息，无法删除！");
			return;
		}
		delete rows[i]["0"];
	}
	var submitData = JSON.stringify(rows);
	var flagUpdateRent = $("#flagUpdateRent").val();
	if (confirm("确定删除?")) {
		$.ajax({
			url : "remove",
			type : "post",
			dataType : 'json',
			contentType : 'application/json;charset=utf-8',
			data : submitData,
			success : function(data) {
				alert('删除成功!');
				if(flagUpdateRent == 123){
    				window.location.href="managenew.html";
    			}else{
    				window.location.href="manage.html";
    			}
    			localStorage.removeItem("optype");
				$('#controlAll').attr("checked", false);
			},
			error : function(data) {
				alertModel('删除失败!');
			}
		});
	}
}
//提交审核
function submitAudit(){
	var rows = getSelectItem(1);
	if(rows == null){
		return;
	}
	for (var s = 0; s < rows.length; s++) {
		var auditingState = rows[s].auditingState;
		if (auditingState == 9){
			alertModel("审核中的固化信息不能再次提交审核！");
			return;
		}else if(auditingState == 8){
			alertModel("审核未通过的固化信息，无法提交审核。需修改信息后重新提交审核");
			return;
		}else if(auditingState == 0){
			alertModel("审核通过的无法提交审核");
			return;
		}
	}
	queryFirstUsersByProcDefKey(SelfElecAudit.procDefKey,"startActFlow");
}

/**
* 提交流程，开始审核流程
*/
function startActFlow(){
	$('#firstUsers').modal("hide");
	var rows = getSelectItem(0);
	var ids = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rows.length; s++) {
		var row = rows[s];
		ids.push(row.contractId);
	}
	var nextUserId=$("input[name='userChecked']:checked").val();
	
	var datasObjs = new Array();
	for(var i = 0; i < ids.length; i++){
		var id = ids[i];
		var obj={"id":id,"nextUserId":nextUserId};
		datasObjs.push(obj);
	}
	var objButton=$(this);    
	$.ajax({
		url : 'submitAudit',// 跳转到 action    
		data :JSON.stringify(datasObjs),
		type: "POST",
		dataType: "JSON",
		contentType : 'application/json;charset=utf-8',
		beforeSend:function(){//触发ajax请求开始时执行    
			objButton.val('提交审核中').attr('disabled',true);//改变提交按钮上的文字并将按钮设置为不可点击    
		},    
		success: function (back) {
			if(back != null){
				data = back.obj;
				if(data != null){
					$("#commit2").attr("disabled",false);
					alertModel("提交审核成功！");
					loadTableData(3);
				}
			}
		},
		error: function () { 
			$("#commmit2").attr("disabled",false);
			alertModel("获取区县信息异常！");
		},
		complete:function(){//ajax请求完成时执行    
			  objButton.val('提交审核').attr('disabled',false);//改变提交按钮上的文字并将按钮设置为可以点击    
		}    
	});
}

//续签固化信息
function renewContract(renewFlag){
	var rows = getSelectItem(1);
	if(rows == null){
		return;
	}
	var renewFlag = $("#renew").attr("data-ss");
	var auditState = rows[0].auditingState;
	var contractState = rows[0].contractState;
	if(contractState != 0){
		alertModel("此固化信息已经停用，无法续签！");
		return;
	}
	if(auditState == 9){
		alertModel("此固化信息正在审核中，无法续签！");
		return;
	}
	if(auditState == -1){
		alertModel("此固化信息未审核，无法续签！");
		return;
	}
	var elecontractId = rows[0].elecontractId;
	localStorage.setItem("elecontractId",elecontractId);
	localStorage.setItem("renewFlag",renewFlag);
	localStorage.setItem("optype",3);
	console.log(rows[0]);
	var flagUpdateRent = $("#flagUpdateRent").val();
	localStorage.setItem("flagUpdateRent",flagUpdateRent);
	window.location.href='edit.html';
}
//变更固化信息
function changeContract(){
	var rows = getSelectItem(0);
	var elecontractId = rows[0].elecontractId;
	localStorage.setItem("elecontractId",elecontractId);
	localStorage.setItem("optype",4);
	getAddress();
	var flagUpdateRent = $("#flagUpdateRent").val();
	localStorage.setItem("flagUpdateRent",flagUpdateRent);
	window.location.href='edit.html';
}
//导出
function exportData(data){
	dataFrom = data;
	var para="reg="+$('#contractName').val()+"&city="+$("#city option:selected").val()+"&dataFrom="+dataFrom;
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
