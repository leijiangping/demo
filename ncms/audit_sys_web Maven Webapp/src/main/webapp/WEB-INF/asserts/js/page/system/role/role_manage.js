var operate_type = 1;//1 新增，2 修改
var ipageCounts=10;
$(document).ready(function() {
	initialize();
});
/**
 * 获取所有用户信息
 */
var showTableList = null;
var userDataList=null;
var provinceList=null;
/**
 * 初始化加页面
 */
function initialize(){
	curPageNum = 1;
	allProvince();
	
}
/**
 * 复选框全选
 */
function chooseAll_id() {
	var checklist = document.getElementsByName("checkbox");
	if (document.getElementById("chooseAll_id").checked) {
		for (var i = 0; i < checklist.length; i++) {
			checklist[i].checked = 1;
		}
	} else {
		for (var j = 0; j < checklist.length; j++) {
			checklist[j].checked = 0;
		}
	}
};


/**
 * 获取点击checkbox个数
 */

/**
 * 全查
 */	 
function findRole() {
	var prvId=$('#province option:selected').val();
	var roleCode =  $("#functionCode").val();
	var roleName =  $("#functionName").val();
	$('#tb').bootstrapTable('destroy');
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "querySysRole", // 获取数据的地址
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
				cur_page_num: params.pageNumber,    
				page_count: params.pageSize,
	            roleCode : roleCode,
				roleName : roleName,
				prvId:prvId
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
            field: 'roleCode',
            title: '角色编码'
        },{
            field: 'roleName',
            title: '角色名'
        }, {
            field: 'roleuserNum',
            title: '用户数'
        }, {
            field: 'roleState',
            title: '当前状态'
        }, {
            field: 'roleNote',
            title: '备注'
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
	 
/**
 * 删除用户
 * 
 */
function deleteRole() {
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	 confirmModel('确定删除所选项目','deletedRole');
};
function deletedRole(){
	  var deleteuseObjs = new Array();
		// 从选中行中挑出可以启用的item
		for (s = 0; s < rowschecked.length; s++) {
			var row = rowschecked[s];
			deleteuseObjs.push(row.roleId);
		}
 $.ajax({
     type:"post",
     url:"deleteRole",
     data : JSON.stringify(deleteuseObjs),
 	   dataType : 'json',
 	   contentType:"application/json;charset=UTF-8",
     success:function(data){
  	   findRole();
  	   alertModel("删除成功!");
  	   $('#chooseAll_id').attr("checked",false);
     },
     error:function(data){
  	  
         art.dialog.tips('删除失败!');
     }
 });
	
}

/**
 * 启用用户
 */
function openRole() {
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var openuseObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		openuseObjs.push(row.roleId);
	}
	$.ajax({
		url : 'openUse',   
		data : JSON.stringify(openuseObjs),
		type : 'post',
		cache : false,
		dataType : 'json',
		contentType: "application/json;charset=utf-8",
		success : function(feedback) {
			alertModel(feedback.msg);
			findRole();
			$('#chooseAll_id').attr("checked",false);
		},
		error : function() {
			alertModel("请求异常");
		}
	});
};

/**
 * 停用用户
 */
function stopRole(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var stopuseObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		stopuseObjs.push(row.roleId);
	}
	$.ajax({
		url : 'closeUse',   
		data : JSON.stringify(stopuseObjs),
		type : 'post',
		cache : false,
		dataType : 'json',
		contentType: "application/json;charset=utf-8",
		success : function(feedback) {
		
		alertModel(feedback.msg);
		findRole();
		$('#chooseAll_id').attr("checked",false);
		},
		error : function() {
			alertModel("请求异常");
		}
	});
};

/**
 * 增加用户信息
 */
function addRole(){
	operate_type = 1;
	$("#dataForm")[0].reset(); // 清空表单
	$('#EditPanel .form-group span.modal-error').children().remove();
	$('#EditPanel').modal(); // 弹出表单
	$("#province1").html("");
	list=provinceList;
	if(list.length==1){
		$("#province1").append("<option value=" + list[0].prvId + " selected='selected'>" + list[0].prvName + "</option>");
	}else{
		$("#province1").append("<option >" + "--请选择--" + "</option>");
		for (var i = 0; i < list.length; i++) {
			$("#province1").append("<option value=" + list[i].prvId + ">" + list[i].prvName + "</option>");
			
		}
	}
}

function hadCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}

/**
 * 修改用户信息
 */
function updateRole(){
	if(!hadCheckedRowData()){
		return;
	}
	operate_type = 2;
	var roleId=rowschecked[0].roleId;
	$.ajax({
		url : 'queryRoleById',   
		data : {
			roleId:roleId
		},
		type : 'post',
		dataType : 'json',
		success : function(list) {
		     //请求成功时
			for(var i=0;i<list.length;i++){
				if(list!=null){
			    		var item = list[0];
			    		$("input[name='roleName']").val(item.roleName);
			    		$("input[name='roleNote']").val(item.roleNote);
			    		$("input[name='roleId']").val(item.roleId);
			    		var newList=provinceList;
			    		$("#province1").html("");
						if(newList.length==1){
							$("#province1").append("<option value=" + newList[0].prvId + " selected='selected'>" + newList[0].prvName + "</option>");
						}else{
							$("#province1").append("<option value=''>" + "--请选择--" + "</option>");
							for (var i = 0; i < newList.length; i++) {
								if(item.prvId==newList[i].prvId){
									$("#province1").append("<option value=" + newList[i].prvId + " selected='selected'>" + newList[i].prvName + "</option>");
								}
								$("#province1").append("<option value=" + newList[i].prvId + ">" + newList[i].prvName + "</option>");
								
							}
						}
						$('#EditPanel .form-group span.modal-error').children().remove();
			    		$('#EditPanel').modal();
				}	
			}
		},
		error : function() {
			alertModel("请求异常");
		}
	})
}
/**
 * 增加提交form表单
 */
function formSubmit(){
	if(validform().form()){
		var s=$("input[name='roleName']").val();
		if(s=="" ){
			alertModel("角色名不能为空！");
			return false;
		}
		var data=$('#dataForm').serialize();
		var submitData=decodeURIComponent(data,true);
	    if(operate_type==1){
	    	$("#saveSet").attr("disabled",true);
			$.ajax({
			    url:'insertRole',
			    data: submitData,
		 		type : 'post',
			    async:false,
			    success:function(result){
			        //请求成功时
			    	if(result!=null){
		    			alertModel(result.msg);
		    			findRole();
			    	}
					$('#EditPanel').modal('hide');
					$("#saveSet").attr("disabled",false);
			    },
			    error:function(){
					alertModel("请求失败！");
					$("#saveSet").attr("disabled",false);
			    }
			});
	    }else{
	    	$("#saveSet").attr("disabled",true);
	    	$.ajax({
			    url:'updateRole',
			    data: submitData,
		 		type : 'post',
			    async:false,
			    success:function(result){
			        //请求成功时
			    	if(result!=null){
		    			alertModel(result.msg);
		    			findRole();
			    	}
					$('#EditPanel').modal('hide');
					$("#saveSet").attr("disabled",false);
			    },
			    error:function(){
					alertModel("请求失败！");
					$("#saveSet").attr("disabled",false);
			    }
			});
	    }
	}
}
//验证
function validform(){
	var addnew_validate= bindformvalidate("#dataForm", {
		roleName:{
			required : true,
		}
	},{
		roleName:{
			required : "必填！"
		}
	});

  return addnew_validate;
}

function dispatch(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var dispatch = new Array();
	var prvIdchecked = new Array();
	// 从选中行中挑出可以启用的item
	for (s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		dispatch.push(row.roleId);
		prvIdchecked.push(row.prvId);
		$("#roleNames").text(row.roleName);
		localStorage.setItem("roleId",row.roleId);
	}
	var roleId=dispatch[0];
	var roleUser=null;
	$.ajax({
		url : 'queryRoleById',   
		data : {
			roleId:roleId
		},
		type : 'post',
		async:false,
		dataType : 'json',
		success : function(list) {
				roleUser=list[0].roleUser;
		},
		error : function() {
			alertModel("请求异常");
		}
	})
	
	var prvId=prvIdchecked[0];
	allUser(roleUser,prvId);
	$('#tb1 .pull-right.pagination-detai').remove();
}

function formSave(){
	var rowsUserId = [];
	if(isCheckeds()>0){
		for (s = 0; s < rowscheckeds.length; s++) {
			var row = rowscheckeds[s];
			rowsUserId.push(row.userId);
		}
		
	}
	var newUserId = [];
	for(var i=0; i < oldUserId.length; i++){   
        if(!in_array(oldUserId[i], rowsUserId)){
        	newUserId.push(oldUserId[i]);   
        }   
    }
	for(var i=0; i < rowsUserId.length; i++){   
        if(!in_array(rowsUserId[i], oldUserId)){
        	newUserId.push(rowsUserId[i]);   
        }   
    }

	var roleId = localStorage.getItem("roleId");
	$("#newuserId").val(newUserId);
	$("#newroleId").val(roleId);
	var data=$('#dataForm1').serialize();
	var submitData=decodeURIComponent(data,true);
	$.ajax({
	    url:'grantPrivilege',
	    data: submitData,
 		type : 'post',
	    cache:false,
	    async:true,
	    success:function(result){
	        //请求成功时
	    	if(result!=null){
    			alertModel(result.msg);
    			findRole();
	    	}
			$('#EditPanel').modal('hide');
	    },
	    error:function(){
			alertModel("请求失败！");
	    }
	})
	
}
var rowscheckeds=null;
function isCheckeds(){
	var checkNums = 0;
	rowscheckeds = new Array();
	var checklists = $("#tb1 tbody input[type='checkbox']");
	for(var i=0;i<checklists.length;i++)
    {
		// 已选中可操作行
	    if(checklists[i].checked == 1){
	    	checkNums ++;
	    	rowscheckeds.push(userDataList[i]);
	    	console.log("rowscheckeds"+i+"========"+rowscheckeds);
	    }
    } 
	return checkNums;
}

function allUser(roleUser,prvId){
	var roleUsers=roleUser;
	$('#tb1').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb1").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryAllUser", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		pageSize : ipageCounts, // 每页显示的记录数
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
				cur_page_num: params.pageNumber,    
				page_count: params.pageSize,
	            prvId:prvId
			};
			return param;
		},
		columns: [{
            checkbox:true,
        }, {
            field: 'userName',
            title: '用户姓名'
        }, {
            field: 'userLoginname',
            title: '用户账号'
        },],
		onLoadSuccess : function() { // 加载成功时执行
			compare(roleUsers);
		},
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success != "1"){
					 alertModel(res.msg);
				}
				
			}
			userDataList = res.obj.result;
			$('#EditPanel1 .form-group span.modal-error').children().remove();
	        $('#EditPanel1').modal();	
			
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.result //数据
	         };
	        
		}
	});
}
var oldUserId = null;
function compare(roleUser){
	 var checklist = $("#tb1 tbody input[type='checkbox']");
	 oldUserId=new Array();
	 for(var j=0;j<roleUser.length;j++){
		 for(var i=0;i<userDataList.length;i++){
			 if(roleUser[j].userId==userDataList[i].userId){
				 $("#tb1 tbody input[data-index='"+i+"']").prop("checked", true);
				 oldUserId.push(roleUser[j].userId);
			}
		 }
	 }
}
function allProvince(){
	$.ajax({
		url : 'queryAllProvince',   
		data : {
			count:10
		},
		type : 'post',
		dataType : 'json',
		success : function(back) {
			if (back != null) {
 				if(back.success=="1"){
 					var province = back.Obj;
 					provinceList=list = province;
					if(list.length==1){
						$("#province").append("<option value=" + list[0].prvId + " selected='selected'>" + list[0].prvName + "</option>");
						$("input[name='prvId']").val(list[0].prvId);
					}else{
						$("#province").append("<option value=''>" + "--请选择--" + "</option>");
						for (var i = 0; i < list.length; i++) {
							$("#province").append("<option value=" + list[i].prvId + ">" + list[i].prvName + "</option>");
							
						}
					}
					 findRole();
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
