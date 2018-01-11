var showTableList=null;//已显示表格list
var treeObj;
var treeNodes; //后台返回到前台的所有树节点
var pMenuNodeHadClicked=null;//已点选父节点
var treeN ;//定义初始化的树对象
var nodes;//定义某一获取的节点

var operate_type = 1;//1 新增，2 修改

var setting = {
	data: {
		key: {
			name: "depName"//自定义后台返回前台的节点变量
		},
		simpleData: {
			enable: true,
			idKey: "depCode",//自定义后台返回前台的节点变量
			pIdKey: "parentCode"//自定义后台返回前台的节点变量
		}
	},	
	callback: {
		onClick: zTreeOnClick,
		onDblClick:zTreeOnDblClick,
		onNodeCreated:zTreeOnClick//初始化创建树后在表格中罗列出根节点下的所有第一节子节点
	}
};

$(document).ready(function(){
	initDeptTree();
});
function addDiyDom(treeId, treeNode) {
	var spaceWidth = 10;
	var switchObj = $("#" + treeNode.tId + "_switch"),
	icoObj = $("#" + treeNode.tId + "_ico");
	switchObj.remove();
	icoObj.before(switchObj);
	if (treeNode.level > 1) {
		var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
		switchObj.before(spaceStr);
	}
}
//点击父节点回调在表格中展现子节点
function zTreeOnClick(event, treeId, treeNode) {//treeNode 已选的节点
	pMenuNodeHadClicked = treeNode;//已点选父节点
    $('#tb tr:gt(0)').remove();//删除之前的数据
    showTableList = new Array();
    if(treeNode.children != undefined){
    	$.each(treeNode.children,function(){
    		createTableData(this);
            showTableList.push(this);
    	});
    }
}
//单击父节点回调展开或者折叠
function zTreeOnDblClick(event, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("orgTree");
	zTree.expandNode(treeNode);
	return false;
}
/**
 * table数据生成
 */
function createTableData(item){ 
    var state = "未知";
    switch(item.depState){
    case 0:state="已启用";
    	break;
    case 9:state="已停用";
    	break;
    case -1:state="已删除";
    	break;
    }
    var childNodes = '';
    if(item.children != undefined){
    	childNodes = '<a class=\'node_a\' onclick=\'createClidrenTableData("'+ item.depCode +'")\' >'+ item.depName +'</a></td><td>';
    }else{
    	childNodes = item.depName + '</td><td>';
    }
    var s = 	'<tr style=\'text-align: center;\'><td><input type=\'checkbox\' name=\'checkbox\'/></td><td>'
    			+ item.depCode + '</td><td>'
		    	+ childNodes + 
		    	+ item.depOrder + '</td><td>'
		    	+ item.parentName +'</td><td>'
		        + state  +'</td></tr>';
	$('#tb').append(s);
}
//单击表格中节点名称,有子节点的话，点击进去在表格中罗列出所有子节点的信息
function createClidrenTableData(depCode){
    $('#tb tr:gt(0)').remove();//删除之前的数据
	$.each(pMenuNodeHadClicked.children,function(){
		if(this.depCode === depCode){//获知在表格中点选的是哪一个父节点
			pMenuNodeHadClicked = this;//已点选父菜单重新赋值给pMenuNodeHadClicked
			$.each(this.children,function(){
	    		createTableData(this);
    			nodes = treeN.getNodeByParam("depCode", depCode, null);//点击表格选中父节点的同时左边对应树节点也同时选上
        		treeN.selectNode(nodes);
//        		showTableList.push(this);
	    	});
		}
	});
}

function initDeptTree(){
	treeObj = $("#orgTree");
	treeObj.addClass("showIcon");
	
	reloadDeptTree();
	
	allProvince();
}
/**
 * 获取左侧组织机构
 */
function reloadDeptTree(){
	$.ajax({
		url : 'getDepartmentManage',   
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if (data != null) {
				data = sortByKey(data, "depOrder");
	            treeNodes = data;   //把后台封装好的简单Json格式赋给treeNodes 
	            $.fn.zTree.init(treeObj, setting, treeNodes);//初始化树
	            
	            //默认选中根节点
	            treeN =  $.fn.zTree.getZTreeObj("orgTree");
	        	nodes = treeN.getNodes();
	        	if (nodes.length>0) {
	        		treeN.selectNode(nodes[0]);
	        	}
			}
		},
		error : function() {
			alertModel("请求异常");
		}
	});
}
function allProvince(){
	$.ajax({
		url : 'queryAllProvince', 
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if (back != null) {
 				if(back.success=="1"){
 					var list = back.Obj;
					var s = "";
					for (var i = 0; i < list.length; i++) {
						s += "<option value=" + list[i].prvId + ">" + list[i].prvName + "</option>";
					}
					$("#provinceSelect").append(s);
				
 				}else{
					alertModel(back.msg);
 				}
 			}
		},
		error : function() {
			alertModel("请求异常");
		}
	})
}

function searchFuncMenus(){
	var provSelect=$('#provinceSelect').val();
	var funcCode=$('#funcCode').val();
	var funcName=$('#funcName').val();
	
	pMenuNodeHadClicked = null;
	
	$.ajax({
		url : 'queryDepartByConditions',   
		data : {
			funcCode : funcCode,
			funcName : funcName,
			provSelect : provSelect
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(resList) {
			if(resList != null){
				resList = sortByKey(resList, "depOrder");
				$('#tb tr:gt(0)').remove();//删除之前的数据
	            showTableList = new Array();
				$.each(resList, function (n, item) {
	               createTableData(item);
	               showTableList.push(item);
	           });
			}
		},
		error : function() {
			alertModel("请求异常");
		}
	});
}

function insertNew(){
	if(pMenuNodeHadClicked == null){
		alertModel("至少选择左侧一条父菜单");
		return false;
	}
	operate_type = 1;// 新增
	
	$("#dataForm")[0].reset();	//清空表单
	//反显
	$('input[name=frontDeptName]').val(pMenuNodeHadClicked.depName);
	$('input[name=frontDeptCode]').val(pMenuNodeHadClicked.depCode);
	$('input[name=prvId]').val(pMenuNodeHadClicked.prvId);
	$('input[name=pdepId]').val(pMenuNodeHadClicked.depId);
	//去除错误提示信息
	$('#EditPanel .form-group span.modal-error').children().remove();
	$('#EditPanel').modal();	//弹出表单
	
	
}

function update(){
	
	if(!hadCheckedRowData()){
		return;
	}
	
	var updateObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		console.log("row======"+row.depState);
		if(row.depState=="-1" || row.depState=="9"){//0可用9停用-1已删除
			updateObjs.push(row);
		}
	}
	if(updateObjs.length>0){
		alertModel("已停用，不可修改项");
		return;
	}
	operate_type = 2;// 修改
	$.ajax({
	    url:'queryDeptitemByCode',
	    data: {
	    	deptCode : rowschecked[0].depCode
	    },
 		type : 'post',
	    cache:false,
	    async:true,
	    success:function(result){
	        //请求成功时
	    	if(result!=null){
		    	if(result.success == "1"){
		    		var item = result.Obj;
		    		
		    		//反显
		    		$('input[name=frontDeptName]').val(pMenuNodeHadClicked==null?item.parentName:pMenuNodeHadClicked.depName);
		    		$('input[name=frontDeptCode]').val(pMenuNodeHadClicked==null?item.parentCode:pMenuNodeHadClicked.depCode);

		    		$('input[name=depOrder]').val(item.depOrder);
		    		$('input[name=depName]').val(item.depName);

		    		$('input[name=prvId]').val(item.prvId);
		    		$('input[name=depId]').val(item.depId);
		    		$('input[name=pdepId]').val(item.pdepId);
		    		$('input[name=depCode]').val(item.depCode);
		    		//去除错误提示信息
		    		$('#EditPanel .form-group span.modal-error').children().remove();
		    		$('#EditPanel').modal();
		    	}else{
		    		alertModel(result.msg);
    			}
	    	}
	    },
	    error:function(){
	    	alertModel("请求失败！");
	    }
	})
}

function formSubmit(){
	if(validform().form()){
		if($("#menuName").val()==""){
			alertModel("功能名称必须输入");
			return false;
		}
		var data=$('#dataForm').serialize();
		var submitData=decodeURIComponent(data,true);
		if(operate_type==1){
			$("#conserve").attr("disabled",true);
			$.ajax({
			    url:'addNewDepartNode',
			    data: submitData,
		 		type : 'post',
			    cache:false,
			    async:true,
			    success:function(result){
			        //请求成功时
			    	if(result!=null){
			    		alertModel(result.msg);
		    			reloadDeptTree();
			    	}
	    			$('#EditPanel').modal('hide');
	    			$("#conserve").attr("disabled",false);
			    },
			    error:function(){
			    	alertModel("请求失败！");
			    	$("#conserve").attr("disabled",false);
			    }
			})
		}
		else{
			$("#conserve").attr("disabled",true);
			$.ajax({
			    url:'modifyDepartNode',
			    data: submitData,
		 		type : 'post',
			    cache:false,
			    async:true,
			    success:function(result){
			        //请求成功时
			    	if(result!=null){
			    		alertModel(result.msg);
		    			reloadDeptTree();
			    	}
	    			$('#EditPanel').modal('hide');
	    			$('#chooseAll_id').attr("checked",false);
	    			$("#conserve").attr("disabled",false);
			    },
			    error:function(){
			    	alertModel("请求失败！");
			    	$("#conserve").attr("disabled",false);
			    }
			})
		}
	
	}
	
}

function deleteUse(){
	if(isCheckedLessOne()<=0){
		alertModel("请先选择操作行");
		return false;
	}
	
	confirmModel('确定删除所选项目','deleteDept');
}

function deleteDept(){
	$.ajax({
		url : 'depart/deleteUse',   
		data : JSON.stringify(rowschecked),
		type : 'post',
		cache : false,
		dataType : 'json',
		contentType: "application/json;charset=utf-8",
		success : function(feedback) {
			reloadDeptTree();
			alertModel(feedback.msg);
		},
		error : function() {
			alertModel("请求异常");
		}
	});
}

function openUse(){
	if(isCheckedLessOne()<=0){
		alertModel("请先选择操作行");
		return false;
	}
	var had_openuseObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		if(row.depState=="9" || row.depState=="-1"){//0可用9停用-1已删除
			had_openuseObjs.push(row);
		}
	}
	if(had_openuseObjs.length<=0){
		alertModel("已启用，无可修改项");
		return;
	}
	$.ajax({
		url : 'depart/openUse',   
		data : JSON.stringify(had_openuseObjs),
		type : 'post',
		cache : false,
		dataType : 'json',
		contentType: "application/json;charset=utf-8",
		success : function(feedback) {
			alertModel(feedback.msg);
			reloadDeptTree();
		},
		error : function() {
			alertModel("请求异常");
		}
	});
}
function stopUse(){
	if(isCheckedLessOne()<=0){
		alertModel("请先选择操作行");
		return false;
	}
	var had_stopuseObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		if(row.depState=="0" || row.depState=="-1"){//0可用9停用
			had_stopuseObjs.push(row);
		}
	}
	if(had_stopuseObjs.length<=0){
		alertModel("已停用，无可修改项");
		return;
	}
	$.ajax({
		url : 'depart/stopUse',   
		data : JSON.stringify(had_stopuseObjs),
		type : 'post',
		cache : false,
		dataType : 'json',
		contentType: "application/json;charset=utf-8",
		success : function(feedback) {
			alertModel(feedback.msg);
			reloadDeptTree();
		},
		error : function() {
			alertModel("请求异常");
		}
	});
}

function hadCheckedRowData(){
	if(showTableList==null || !isCheckedLessOne()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}
/**
 * 获取点击checkbox个数
 */
var rowschecked = new Array();
function isCheckedLessOne(){
	var checkNum = 0;
	rowschecked = new Array();
	var checklist = document.getElementsByName ("checkbox");
	for(var i=0;i<checklist.length;i++)
    {
	    if(checklist[i].checked == 1){
	    	checkNum ++;
	    	rowschecked.push(showTableList[i]);
	    }
    } 
	return checkNum;
};
//验证
function validform(){
	var addnew_validate= bindformvalidate("#dataForm", {
		depName:{
			required : true,
		},
		depOrder:{
			required : true,
			number: true,
		}
	},{
		depName:{
			required : "必填！"
		},
		depOrder:{
			required : "必填！"
		}
	});

  return addnew_validate;
}