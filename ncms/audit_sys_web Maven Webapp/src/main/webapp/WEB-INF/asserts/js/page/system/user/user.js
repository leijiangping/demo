var operate_type;//1 新增，2 修改
//已显示表格list
var showTableList = null;
var curPageNum = 0;
var row = "";
var flag = true;
$(document).ready(function() {
	initialize();
});

/**
 * 获取所有用户信息
 */
var datalist = null;

/**
 * 初始化加页面
 */
function initialize(){
	//显示页数
	curPageNum = 1;
	//每页显示个数
	ipageCount = 10;
	allProvince();
	 //绑定事件
    $("#userProvince1").change( function () {
        CityBind();
        addRoleName();
        addDepName();
    });
}

/**
 * 查询所有用户
 */
function findUsers() {
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "findUser", // 获取数据的地址
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
				userLoginName : ($("#userLoginName").val()).replace(/\s/g, ""),
				userName : $("#userNameFind").val().replace(/\s/g, ""),
				prvId : $("#userProvince option:selected").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        },{
            field: 'userCode',
            title: '用户工号'
        },{
            field: 'userName',
            title: '用户姓名'
        }, {
            field: 'userLoginname',
            title: '用户账号'
        }, {
            field: 'sysRegionVO.prvName',
            title: '所属省份'
        },  {
            field: 'sysRegionVO.pRegName',
            title: '所属地市'
        }, {
            field: 'sysRegionVO.regName',
            title: '所属区县'
        }, {
            field: 'userState',
            title: '当前状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 0:return '启用';
            		case 9:return '停用';
            		case -1:return '已删除';
            	}
            	return value;
            }
        }, {
            field: 'userEmail',
            title: '电子邮箱'
        }, ],
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
 * 选中的条数
 */
function hadCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}

/**
 * 删除用户
 * 
 */
function deleteUser() {
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var deleteuseObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		deleteuseObjs.push(row.userId);
	}
	if (confirm("确定删除所选项目?")) {
		$.ajax({
			type : "post",
			url : "user/delete",
			data : JSON.stringify(deleteuseObjs),
			dataType : 'json',
			contentType : "application/json;charset=UTF-8",
			success : function(data) {
				if(data != null){
					alertModel(data.msg);
					findUsers();
				}
			},
			error : function(data) {
				alertModel('删除失败!');
			}
		});
	}
};


/**
 * 启用用户
 */

function openUser() {
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	for (s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		if(row.userState == 0){
			alertModel("选择中已有启用用户，请重新选择!");
			return;
		}
	}
	var openuseObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		openuseObjs.push(row.userId);
	}
	$.ajax({
		url : 'user/open',
		data : JSON.stringify(openuseObjs),
		type : 'post',
		cache : false,
		dataType : 'json',
		contentType : "application/json;charset=utf-8",
		success : function(feedback) {
			alertModel(feedback.msg);
			findUsers();
		},
		error : function() {
			alertModel("请求异常");
		}
	});
};


/**
 * 停用用户
 */
function stopUser(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	for (s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		if(row.userState == 9){
			alertModel("选择中已有停用用户请重新选择!");
			return;
		}
	}
	confirmModel('确定停用用户!','confirmStopUser');
}	
function confirmStopUser(){
	// 从选中行中挑出可以启用的item
	var stopuseObjs = new Array();
	for (s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		stopuseObjs.push(row.userId);
	}
	$.ajax({
		url : 'user/stop',
		data : JSON.stringify(stopuseObjs),
		type : 'post',
		cache : false,
		dataType : 'json',
		contentType : "application/json;charset=utf-8",
		success : function(feedback) {
			alertModel(feedback.msg);
			findUsers();
		},
		error : function() {
			alertModel("请求异常");
		}
	});
}

/**
 * 重置密码
 */
function updatePassword(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	confirmModel('确定重置该用户密码!','confirmUpdatePassword');
}	
function confirmUpdatePassword(){
	// 从选中行中挑出可以启用的item
	var updateuseObjs = new Array();
	for (s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		updateuseObjs.push(row.userId);
	}
	$.ajax({
		url : 'updatePassword',
		data : JSON.stringify(updateuseObjs),
		type : 'post',
		cache : false,
		dataType : 'json',
		contentType : "application/json;charset=utf-8",
		success : function(feedback) {
			alertModel(feedback.msg);
			findUsers();
		},
		error : function() {
			alertModel("请求异常");
		}
	});
}

/**
 * 判断用户名唯一性校验
 */
function findName(){
	var name = $("#userLoginname").val();
	var userLoginname = name.replace(/\s/g, "");
	if(userLoginname.length <= 0){
		$("#userLoginname").next("#err").html("<img src=\"../../../image/error.png\"/>用户账户不能为空!！");
		$("#userLoginname").next("#err").css({"color":"red"});
		return;
	}else{
		$.ajax({
			url : 'findUserByLoginName',
			data : {
				userLoginname : userLoginname
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			async:true,
			success : function(back) {
				if (back != null) {
	 				if(back.success=="1"){
	 					var data = back.Obj;
	 					if(data != null && data.length > 0){
	 						$("#userLoginname").next("#err").html("<img src=\"../../../image/error.png\"/>此用户名已存在!");
	 						$("#userLoginname").next("#err").css({"color":"red"});
	 						flag = true;
	 					}else{
	 						$("#userLoginname").next("#err").html("<img src=\"../../../image/right_1.png\"/>此用户名可用!");
	 						$("#userLoginname").next("#err").css({"color":"green"});
	 						flag = false;
	 					}
	 				}
				}
			},
			error : function() {
				alertModel("请求异常");
			}
		});
	}
}

/**
 * 增加用户信息
 */
var b_add_user = false;
function addUser(){	
	operate_type = 1;
	b_add_user = true;
	allProvince();
	CityBind();
	$("#userLoginname").removeAttr("readonly");
	$("#userLoginname").unbind();
	$("#userLoginname").blur(function(){
		findName();
	});
	$("#userMsg").html("");
	addDepName();
	addRoleName();
	$("#myModalLabel").html("添加用户");
	//清除之前的选择
	$("#dataForm")[0].reset(); // 清空表单
	$('#EditPanel span.modal-error').html('');
	$("#City").selectpicker('refresh');
	$("#Village").selectpicker('refresh');
	$("#deptName").selectpicker('refresh');
	$("#roleName").selectpicker('refresh');
	if($("#userProvince1").val() != 000000){
		$("#cityHide").css("display","block");
		$("#villageHide").css("display","block");
	}
	$('#EditPanel').modal(); // 弹出表单
}

/**
 * 修改用户信息
 */
function updateUser(){
	operate_type = 2;
	b_add_user = false;
	allProvince();
	$("#userProvince1").selectpicker('refresh');
	$("#userMsg").html("");
	$("#userLoginname").attr({ readonly: 'true' });
	$("#userLoginname").unbind();
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var stopuseObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		stopuseObjs.push(row.userId);
	}
	$("#myModalLabel").html("修改用户");
	findUserData();
	$('#EditPanel span.modal-error').html('');
	$('#EditPanel').modal(); // 弹出表单
}

/**
 * 提交form表单
 */
function formSubmit(){
	var roleIds = [];
	var depIds = [];
    var select1 = document.getElementById("roleName");
    for(i=0;i<select1.length;i++){
        if(select1.options[i].selected){
        	roleIds.push(select1[i].value);
        }
    }
    $("#selectRoleIds").val(roleIds);
    var select2 = document.getElementById("deptName");
    for(i=0;i<select2.length;i++){
        if(select2.options[i].selected){
        	depIds.push(select2[i].value);
        }
    }
    $("#selectDepIds").val(depIds);
    var data=$('#dataForm').serialize();
	var submitData = decodeURIComponent(data,true);
	if($("#userProvince1").val() != 000000){
		if($("#Village").val() == ""){
			alertModel("请选择区县信息");
			return;
		}
	}
    if(operate_type==1){
    	findName();
    	if(flag){
    		return;
    	}
    	$("#saveSet").attr("disabled",true);
    	$.ajax({
			url : 'add',
			data : submitData,
			type : 'post',
			dataType : 'json',
			async:false,
			success : function(result) {
				//请求成功时
		    	if(result!=null){
	    			alertModel(result.msg);
	    			findUsers();
		    	}
				$('#EditPanel').modal('hide');
				$("#saveSet").attr("disabled",false);
			},
			error : function() {
				alertModel("请求失败！");
				$("#saveSet").attr("disabled",false);
			}
		});
		
    }else{
    	$("#saveSet").attr("disabled",true);
    	$.ajax({
		    url:'updateUser',
		    data: submitData,
	 		type : 'post',
		    cache:false,
		    async:false,
			dataType : 'json',
		    success:function(result){
		        //请求成功时
		    	if(result!=null){
	    			alertModel(result.msg);
	    			findUsers();
		    	}
    			$('#EditPanel').modal('hide');
    			$("#dataForm")[0].reset(); // 清空表单
    			$("#City").selectpicker('refresh');
    			$('#chooseAll_id').attr("checked",false);
    			$("#saveSet").attr("disabled",false);
		    },
		    error:function(){
				alertModel("请求失败！");
				$("#saveSet").attr("disabled",false);
		    }
		});
    }
}

//查询角色名称
function addRoleName() {
	var prvId = $("#userProvince1").val();
	if(!b_add_user){
		var userId = '';
		if(rowschecked.length > 0){
			userId = rowschecked[0].userId;
		}
	}
	$.ajax({
		type : "get",
		url : "findRoleName",
		data : {
			"userId" : userId,
			"prvId" : prvId
		},
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		success : function(value) {
			var s = '';
			if(value != null){
				list = value.obj;
			var roles = list.roles;
			var roleIds = list.roleIds;
				$("#roleName").find("option").remove();
				for(var i = 0;i<roles.length;i++){
					s += "<option id='a"+roles[i].roleId+"' value='"+roles[i].roleId+"' >"+roles[i].roleName+"</option>";
				}
				$('#roleName').append(s);
				$('#roleName').selectpicker('refresh');
				if(roleIds != null){
					for (var i = 0; i < roleIds.length; i++) {
						 $('#a'+roleIds[i]).attr("selected","selected");
						$('#roleName').selectpicker('refresh');
					}
				}
			}
		},
		error : function(data) {
			alertModel('获取角色名称参数失败!');
		}
	});
}

// 查询部门名称
function addDepName() {
	var prvId = $("#userProvince1").val();
	if(!b_add_user){
		var userId = '';
		if(rowschecked.length > 0){
			userId = rowschecked[0].userId;
		}
	}
	$.ajax({
		type : "get",
		url : "findDeptName",
		data : {
			"userId" : userId,
			"prvId" : prvId
		},
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		success : function(value) {
			var s = '';
			if(value != null){
				map = value.obj;
				var departments = map.departments;
				var depIds = map.deptIds;
				$("#deptName").find("option").remove();
				$('#deptName').attr("selected",false);
				for(var i = 0;i<departments.length;i++){
					s += "<option id='a"+departments[i].depId+"' value='"+departments[i].depId+"' >"+departments[i].depName+"</option>";
				}
				$('#deptName').append(s);
				$('#deptName').selectpicker('refresh');
				if(depIds != null){
					for (var i = 0; i < depIds.length; i++) {
						 $($('#a'+depIds[i])).attr("selected","selected");
						 $('#deptName').selectpicker('refresh');
					}
				}
			}
		},
		error : function(data) {
			alertModel('获取部门名称参数失败!');
		}
	});
}

/**
 * 把选择修改用户信息写到input中
 */
function findUserData(){
	var userId = rowschecked[0].userId;
	var prvId = $("#userProvince1").val();
	$.ajax({
		type : "get",
		url : "findUserById",
		data : {
			"userId" : userId,
			prvId : prvId
		},
		async:false,
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		success : function(value) {
			if(value != null){
				list = value.obj;
				if(list!=null){
					var user = list.user;
					var sysReguinVOs = list.sysReguinVOs;
					var sysReguins = list.sysReguins;
					if (user != null) {
						$("input[name=userLoginname]").val(user.userLoginname);
						$("#userNote").val(user.userNote);
						$("input[name=userCode]").val(user.userCode);
						$("input[name=userName]").val(user.userName);
						$("input[name=userPhone]").val(user.userPhone);
						$("input[name=userEmail]").val(user.userEmail);
						$("input[name=userId]").val(user.userId);
						$("input[name=userClass]").val(user.userClass);
						$("input[name=userCode]").val(user.userCode);
					}
					
					if(sysReguinVOs != null){
						var userPrvId,userCityId,userRegId;
						if(sysReguins != null && sysReguins.length > 0){
							userPrvId = sysReguins[0].prvId;
							userCityId = sysReguins[0].pregId;
							userRegId = sysReguins[0].regId;
						}else{
							$('#userProvince1').selectpicker('val',"000000");
							$('#userProvince1').selectpicker('refresh');
							$("#cityHide").css("display","none");
			        		$("#villageHide").css("display","none");
			        		addRoleName();
			    			addDepName();
			    			return;
						}
						if(userPrvId){// 选中用户所属省
							$('#userProvince1').selectpicker('val',userPrvId);
						}
					    if($("#userProvince1").val() != '000000'){
			        		$("#cityHide").css("display","block");
			        		$("#villageHide").css("display","block");
			        	}
						$('#City').empty();
						$('#Village').empty();
						var strCity = "<option value=''>-选择地市-</option>";
						$.each(sysReguinVOs, function (i, item){
							if(item.pregId == item.prvId){
								strCity += "<option value='" +item.regId+"'>"+item.regName+ "</option>";
							}
						});

						$("#City").append(strCity);
						//绑定联动事件 
						$("#City").change(function(){
							$("#Village").empty();
							if($("#City").val()!=""){
								$.each(sysReguinVOs, function (i, item){
									if(item.pregId==$("#City").val()){
										strReg += "<option value='" + item.regId+"'>"+item.regName+ "</option>";
									}
								});
							}
							$("#Village").append(strReg);
							$('#Village').selectpicker('refresh');
						});
						if(userCityId){// 选中用户所属市
							$('#City').selectpicker('val',userCityId);
							$("#City").change();
						}
						if(userRegId){// 选中用户所属区
							$('#Village').selectpicker('val',userRegId);
						}
				        $('#City').selectpicker('refresh');
						$('#City').selectpicker('refresh');
						var strReg = "<option value=''>-选择区县-</option>";
						$("#Village").append(strReg);
						$('#Village').selectpicker('refresh');
					}
				}
			}
			addRoleName();
			addDepName();
		},
		error : function(data) {
			alertModel('获取用户信息失败!');
		}
	});
}

/**
 * 获取地市信息
 */
function CityBind() {
	var provice = $("#userProvince1").val();
    //判断省份这个下拉框选中的值是否为空
    if (provice == "") {
        return;
    }
    $("#City").html("");
    $("#Village").html("");
    $.ajax({
        type: "POST",
        url: "getAddress",
        data: { "prvId": provice},
        dataType: "JSON",
        async:false,
        success: function (value) {
			if(value != null){
				sysReguins = value.obj;
				if(sysReguins!=null){
					$('#City').empty();
					$('#Village').empty();
					var strCity = "<option value=''>-选择地市-</option>";
					$.each(sysReguins, function (i, item){
						if(item.pregId == item.prvId){
							strCity += "<option value='" +item.regId+"'>"+item.regName+ "</option>";
						}
					});

					$("#City").append(strCity);
					$('#City').selectpicker('refresh');
					var strReg = "<option value=''>-选择区县-</option>";
					$("#Village").append(strReg);
					$('#Village').selectpicker('refresh');
					//绑定联动事件 
					$("#City").change(function(){
						$("#Village").empty();
						strReg = "<option value=''>-选择区县-</option>";
						if($("#City").val()!=""){
							$.each(sysReguins, function (i, item){
								if(item.pregId==$("#City").val()){
									strReg += "<option value='" + item.regId+"'>"+item.regName+ "</option>";
								}
							});
						}
						$("#Village").append(strReg);
						$('#Village').selectpicker('refresh');
					});
				}
			}
		},
        error: function () { alertModel("获取地市信息异常！"); }
    });
}


/**
 * 查询所有省份信息
 */
function allProvince(){
	$.ajax({
		url : 'queryAllProvince',
		data : {
			count:10
		},
		type : 'post',
		dataType : 'json',
		async:false,
		success : function(back) {
			if (back != null) {
 				if(back.success=="1"){
 					var province = back.Obj;//省
 					list = province;
					$('#userProvince').empty(); //删除之前的数据
					$('#userProvince1').empty(); //删除之前的数据
					if(list.length==1){
						$("#userProvince").append("<option value='" + list[0].prvId + "'>" + list[0].prvName + "</option>");
						$("#userProvince1").append("<option value='" + list[0].prvId + "'>" + list[0].prvName + "</option>");
					    if($("#userProvince1").val() == '000000'){
					    	CityBind();
					    }
					}else{
						$("#userProvince").append("<option value=''>" +'--请选择--'+ "</option>");
						$("#userProvince1").append("<option value=''>" +'--请选择--'+ "</option>");
						for (var i = 0; i < list.length; i++) {
							$("#userProvince").append("<option value='" + list[i].prvId + "'>" + list[i].prvName + "</option>");
							$("#userProvince1").append("<option value='" + list[i].prvId + "'>" + list[i].prvName + "</option>");
						}
						$('#userProvince').prop('selectedIndex', 0);
						$('#userProvince1').prop('selectedIndex', 0);
					}
					$('#userProvince').selectpicker('refresh');
					$('#userProvince1').selectpicker('refresh');
					
 				}else{
					alertModel(back.msg);
 				}
 			}
			if(operate_type != 1 && operate_type != 2){
				findUsers();
			}
		},
		error : function() {
			alertModel("请求异常");
		}
	});
} 

function selectOnchang(obj){ 
	//var value = obj.options[obj.selectedIndex].value;
	var text = obj.options[obj.selectedIndex].text;
	if(text !=="中国移动集团公司"){
		$("#cityHide").css("display","block")
		$("#villageHide").css("display","block")
	}else{
		$("#cityHide").css("display","none")
		$("#villageHide").css("display","none")
	}
}

