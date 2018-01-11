
var showTableList = null;// 已显示表格list
var treeObj;
var treeNodes; // 后台返回到前台的所有树节点
var pMenuNodeHadClicked = null;// 已点选父节点
var treeN;// 定义初始化的树对象
var nodes;// 定义某一获取的节点
var operate_type = 1;// 1 新增，2 修改
var flag=true;
$(document).ready(function() {
	initDeptTree();
});

var setting = {
	data : {
		key : {
			name : "producttypeName"// 自定义后台返回前台的节点变量
		},
		simpleData : {
			enable : true,
			idKey : "producttypeId",// 自定义后台返回前台的节点变量
			pIdKey : "producttypePid"// 自定义后台返回前台的节点变量
		}
	},
	callback : {
		onClick : zTreeOnClick,
		onDblClick : zTreeOnDblClick,
		onNodeCreated : zTreeOnClick
	// 初始化创建树后在表格中罗列出根节点下的所有第一节子节点
	}
};
function initDeptTree() {
	treeObj = $("#orgTree");
	treeObj.addClass("showIcon");
	reloadDeptTree();
}
function reloadDeptTree(item) {
    $.ajax({
        url : 'queryAllTwrProductTypeVO',
        type : 'post',
        cache : false,
        dataType : 'json',
        success : function(data) {
        if (data != null) {
            data = sortByKey(data.Obj, "producttypeId");
            treeNodes = data; // 把后台封装好的简单Json格式赋给treeNodes
            $.fn.zTree.init(treeObj, setting, treeNodes);// 初始化树
             if(item != undefined){
                    $('#tb tr:gt(0)').remove();//删除之前的数据
                    //点击表格选中父节点的同时左边对应树节点也同时选上
                    nodes = treeN.getNodeByParam("producttypeId", item.depCode, null);
                    treeN.selectNode(nodes);
                    $.each(nodes.children,function(){
                        createTableData(this);
                    });
                }
                else{
                    // 默认选中根节点
                    treeN = $.fn.zTree.getZTreeObj("orgTree");
                    nodes = treeN.getNodes();
                    pMenuNodeHadClicked=nodes[0];
                    if (nodes.length > 0) {
                        treeN.selectNode(nodes[0]);
                      /*  $.each(nodes[0].children,function(){
                            createTableData(this);
                        showTableList.push(this);
                        });*/
                    }
                }
            }
        },
        error : function() {
            alert("请求异常");
        }
    });
            
}
function addDiyDom(treeId, treeNode) {
	var spaceWidth = 10;
	var switchObj = $("#" + treeNode.tId + "_switch"), icoObj = $("#"
			+ treeNode.tId + "_ico");
	switchObj.remove();
	icoObj.before(switchObj);
	if (treeNode.level > 1) {
		var spaceStr = "<span style='display: inline-block;width:"
				+ (spaceWidth * treeNode.level) + "px'></span>";
		switchObj.before(spaceStr);
	}
}

// 点击父节点回调在表格中展现子节点
function zTreeOnClick(event, treeId, treeNode) {// treeNode 已选的节点
	pMenuNodeHadClicked = treeNode;// 已点选父节点
	$('#tb tr:gt(0)').remove();// 删除之前的数据
	showTableList = new Array();
	if (treeNode.children != undefined) {
		$.each(treeNode.children, function() {
			createTableData(this);
			showTableList.push(this);
		});
	}
}

// 单击父节点回调展开或者折叠
function zTreeOnDblClick(event, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("orgTree");
	zTree.expandNode(treeNode);
	return false;
}
/**
 * table数据生成
 */
function createTableData(item) {
	var state = "未知";
	switch (item.producttypeState) {
	case 0:
		state = "启用";
		break;
	case 9:
		state = "停用";
		break;
	}
	var childNodes = '';
	if (item.children != undefined) {
		childNodes = '<a class=\'node_a\' onclick=\'createClidrenTableData("'
				+ item.producttypeId + '")\' >' + item.producttypeName
				+ '</a></td><td>';
		
	} else {
		childNodes = item.producttypeName + '</td><td>';
	}
	var s = '<tr style=\'text-align: center;\'><td><input type=\'checkbox\' name=\'checkbox\'/></td><td>'
			+ childNodes
			+ item.pproductName
			/*+ '</td><td>' + state*/
			+'</td><td>' + item.producttypeNote
			+ '</td></tr>';
	$('#tb').find('tbody').append(s);
}
//单击表格中节点名称,有子节点的话，点击进去在表格中罗列出所有子节点的信息
function createClidrenTableData(depCode){
    $('#tb tr:gt(0)').remove();//删除之前的数据
	$.each(pMenuNodeHadClicked.children,function(){
		if(this.producttypeId === depCode){//获知在表格中点选的是哪一个父节点
			pMenuNodeHadClicked = this;//已点选父菜单重新赋值给pMenuNodeHadClicked
			$.each(this.children,function(){
	    		createTableData(this);
    			nodes = treeN.getNodeByParam("producttypeId", depCode, null);//点击表格选中父节点的同时左边对应树节点也同时选上
        		treeN.selectNode(nodes);
	    	});
		}
	});
}
//验证
function validform(){
	var twr_validate= bindformvalidate("#dataForm", {
		frontDeptName : {
			required : true,
			maxlength : 20,
		},
	}, {
		frontDeptName : {
			required : "请输入铁塔名称",
			maxlength: "铁塔名称最大长度20"
		}
	});
  return twr_validate;
}
function twr_close(){
	removeError('dataForm');
	$("#frontDeptName").next("#err").html('');
}
/*
 * 新增
 * 
 */

function insertNew() {
	$("#myModalLabel").html("新增铁塔");
	
	if (pMenuNodeHadClicked == null) {
		alert("至少选择左侧一条父菜单");
		return false;
	}
	$("#frontDeptName").unbind();
	$("#frontDeptName").blur(function(){
		checkName();
	});
	operate_type = 1;// 新增
	$("#dataForm")[0].reset(); // 清空表单
	// 反显
	$('input[name=producttypeName]').val(pMenuNodeHadClicked.producttypeName);/* 根级: */
	$('input[name=producttypeState]').val("启用");
	$('input[name=producttypeState]').prop('readOnly', 'true');
	$('input[name=producttypePid]').val(pMenuNodeHadClicked.producttypePid);
	$('input[name=pdepId]').val(pMenuNodeHadClicked.producttypeId);
	$('#EditPanel').modal(); // 弹出表单
	twr_close();
	
}

/*
 * 新增塔传参
 * 
 */
function formSubmit() {
	var producttypeName = $('#frontDeptName').val();
	var producttypeNote = $('#depName').val();
	var producttypePid = $('#proTypeId').val();	
	if (operate_type == 1) {
		checkName();
		if(flag){
			return;
		}
		$("#saveSet").attr("disabled",true);
		$.ajax({
			url : 'insertTwr',
			data : {
				producttypeName : producttypeName,
				producttypeNote : producttypeNote,
				producttypePid : producttypePid,
			},
			type : 'post',
			cache : false,
			async : true,
			success : function(result) {
				// 请求成功时
				if (result != null) {
					reloadDeptTree();
				}
				$('#EditPanel').modal('hide');
				
				$("#saveSet").attr("disabled",false);
			},
			error : function() {
				alert("请求失败！");
				$("#saveSet").attr("disabled",false);
			}
		});		
	} else if (operate_type == 2) {
		var pName = $('#frontDeptName').val();//名称
	    var regId = $('#proTypeId').val();//需点击数据id
		var producttypeNote=$("#depName").val();
       if(rowschecked[0].producttypeName==$('#frontDeptName').val()){
		}else{
			checkName();
			if(flag){
				return;
			}
		}
		$("#saveSet").attr("disabled",true);
		$.ajax({
			type : "post",
			url : "updateTwr",
			data : {
				producttypeName : pName,
				producttypeId : regId,
				producttypeNote:producttypeNote
			},
			dataType : 'json',
			success : function(result) {
				if (result != null) {
					alert(result.msg);
					reloadDeptTree();
				}
				$('#EditPanel').modal('hide');
				$("#saveSet").attr("disabled",false);
			},
			error : function(data) {
				$("#saveSet").attr("disabled",false);
			 }
		  });		
	   }
}

/*
 * 查询
 */

function searchFuncMenus() {
	//0启用  9停用
	var funcName = $('#funcName').val();
	var openSelect=$('#provinceSelect').val();
	
	pageSize = "10";
	pageNumber = "1";
	pMenuNodeHadClicked = null;
	var culist = [];
	$.ajax({
		url : 'queryProductVOByCondition',
		data : {
			productState : openSelect,
			productName : funcName,
			pageSize : pageSize, // 展示多少条数据
			pageNumber : pageNumber // 展示多少页数据
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(resList) {
		
			if (resList != null) {
				culist = sortByKey(resList.Obj.result, "producttypeId");
				$('#tb tr:gt(0)').remove();// 删除之前的数据
				showTableList = new Array();
				$.each(culist, function(n, item) {
					createTableData(item);
					showTableList.push(item);
				});
			}
		},
		error : function() {
			alert("请求异常");
		}
	});
}
/*
 * 修改
 */
function update() {
	$("#myModalLabel").html("修改铁塔种类");
	if(!hadCheckedRowData()){
		return;
	}
	operate_type = 2;// 修改 producttypeNote
	$("#dataForm")[0].reset(); // 清空表单
	$('input[name=producttypeName]').val(pMenuNodeHadClicked.producttypeName);/* 根级: */
	$('input[name=producttypeNote]').val(rowschecked[0].producttypeNote);
	$('input[name=frontDeptName]').val(rowschecked[0].producttypeName);
	$('input[name=producttypePid]').val(pMenuNodeHadClicked.producttypePid);
	$('input[name=pdepId]').val(rowschecked[0].producttypeId);
	$('#EditPanel').modal(); // 弹出表单
	twr_close();

}
function hadCheckedRowData(){
	
	if(showTableList==null || isCheckedLessOne()!==1){
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

function openUse(type) {
	var checkedNum = $("input[name='checkbox']:checked").length;
	if (checkedNum == 0) {
		alertModel("至少选择一条操作数据");
		return false;
	}
	var checkNum = 0;
	rowschecked = new Array();
	var statusA = new Array();
	var checkboxlist = document.getElementsByName("checkbox");
	for (var i = 0; i < checkboxlist.length; i++) {
		if (checkboxlist[i].checked == 1) {
			checkNum++;
			statusA.push(showTableList[i].producttypeState);
			rowschecked.push(showTableList[i].producttypeId);
		}
	}
	$.ajax({
		url : 'stopOrStartTwr?twrState='
			+ type,
	data : JSON.stringify(rowschecked),
	type : 'post',
	cache : false,
	dataType : 'json',
	contentType : "application/json;charset=utf-8",
	success : function(feedback) {
		alert(feedback.msg);
		reloadDeptTree();
	},
	error : function() {
		alert("请求异常!");
	}
  });		
}
/*
 * 删除数据
 * 
 */

function deleteUse() {
	var checkedNum = $("input[name='checkbox']:checked").length;
	if (checkedNum == 0) {
		alertModel("至少选择一条操作数据");
		return false;
	}
	
	var checkNum = 0;
	rowschecked = new Array();
	var checkboxlist = document.getElementsByName("checkbox");
	for (var i = 0; i < checkboxlist.length; i++) {
		if (checkboxlist[i].checked == 1) {
			if($(checkboxlist[i]).parents('tr').find('a').length>0){
				alertModel('请不要直接删除根级！');
			}else{
				checkNum++;
				rowschecked.push(showTableList[i].producttypeId);
			
			}
		}
	}
	if (confirmModel('您要删除吗','confirmOk')) ;
}
function confirmOk(){
	debugger;
	$.ajax({
		type : "post",
		url : "deleteTwr",
		data : JSON.stringify(rowschecked),
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		success : function(data) {
			alertModel(data.msg);
			reloadDeptTree();
		},
		error : function(data) {
			alertModel("请求失败！");
		}
	});
};


/*检查塔名称是否重复*/
function checkName(){

	var name = $('#frontDeptName').val();
	var producttypeName = name.replace(/\s/g, "");
	var producttypeState = 0;
	if(producttypeName.length <= 0 || producttypeName==null || producttypeName=="" || producttypeName==undefined){
		$("#frontDeptName").next("#err").html("<img src=\"../../../image/error.png\"/>功能名称必须输入!！");
		$("#frontDeptName").next("#err").css({"color":"red"});
		return;
	}else{
		$.ajax({
			type : "post",
			url : "chekTwrName",
			data : {
				"producttypeName":producttypeName,
				"producttypeState":producttypeState
			},
			async:false,
			dataType : 'json',
			success : function(data) {
				if(data.Obj != null&&data.Obj.length>0){
					$("#frontDeptName").next("#err").html("<img src=\"../../../image/error.png\"/>此铁塔名称已存在!");
					$("#frontDeptName").next("#err").css({"color":"red"});				
					flag=true;
				}else{
					$("#frontDeptName").next("#err").html("<img src=\"../../../image/right_1.png\"/>验证成功!");
					$("#frontDeptName").next("#err").css({"color":"green"});
					flag=false;
				}
			},
			error : function(data) {
				alertModel("请求失败！");
			}
		});
	}
};
