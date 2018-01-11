var operate_type = 1;//1 新增，2 修改
var curPageNum = 0;
$(document).ready(function() {
	initialize();
});

/**
 * 获取所有用户信息
 */
var datalist = null;
//var listData = null;
//var userDataList=null;
//var provinceList=null;
/**
 * 初始化加页面
 */
function initialize(){
	curPageNum = 1;
	//allProvince();
	
}
/**
 * 复选框全选
 */
function controlAll() {
	var checklist = document.getElementsByName("checkbox");
	if (document.getElementById("controlAll").checked) {
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
var rowschecked = new Array();
var checkNum = 0;
function isCheckedLessOne() {
	rowschecked = new Array();
	var checklist = document.getElementsByName("checkbox");
	datalist = "";
	for (var i = 0; i < checklist.length; i++) {
		if (checklist[i].checked == 1) {
			rowschecked.push(datalist[i]);
			checkNum += 1;
		}
	}
	return checkNum;
};

function hadCheckedRowData(){
	if(showTableList==null || !isCheckedLessOne()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}

/**
 * 列表查询
 */	 
function find() {
	var city =  $("#city").val();
	var district =  $("#district").val();
	var supplierReg =  $("#supplierReg").val();
	//var prvId=$('#province option:selected').val();
	$.ajax({
			url : 'suppliers',// 跳转到 action    
			data : {
				city : city,
				district : district,
				supplierReg:supplierReg,
				cur_page_num : curPageNum,
				page_count : ipageCount
			},
			type : 'get',
			dataType : 'json',
			success : function(back) {
				if (back != null) {
	 				if(back.success=="1"){
	 					var page = back.Obj;
	 					listData = list = page.result;
	 				}
	 				if (list == "") {
						alertModelModel("无数据!");
						return false;
					}
					$('#tb tr:gt(0)').remove();//删除之前的数据
					var s = '';
					for (var i = 0; i < list.length; i++) {
						s = "<tr style='text-align: center;'><td><input type='checkbox' name='checkbox' value='1'/></td><td>"
								+ (i+1)
								+"<td>"
								+ list[i].supplierCode
								+ "</td><td>"
								+list[i].supplierName
								+ "</td><td>"
								+list[i].supplierType
								+ "</td><td>"
								+ "西安市"
								+ "</td><td>"
								+ "新城区"
								+ "</td><td>"
								+list[i].supplierContact
								+ "</td><td>"
								+list[i].supplierTelephone
								+ "</td><td>"
								+list[i].depositBank
								+ "</td><td>"
								+list[i].bankAccount
								+ "</td></tr>";
							$('#tb').append(s);
					}
					$("#page_ctr li").remove();
					createPageNum(page.pages);
	 			}
			},
			error : function() {
				//alertModel("请求失败");
			}
		});
	$('#tb tr:gt(0)').remove();//删除之前的数据
	var s = '';
	for (var i = 0; i < 1; i++) {
		s = "<tr style='text-align: center;'><td><input type='checkbox' name='checkbox' value='1'/></td><td>"
				+ (i+1)
				+"<td>"
				+ "GSY1251242"+(i+1)
				+ "</td><td>"
				+ "国网西安市电力公司"
				+ "</td><td>"
				+ "电费"
				+ "</td><td>"
				+ "西安市"
				+ "</td><td>"
				+ "新城区"
				+ "</td><td>"
				+"张三"
				+ "</td><td>"
				+"15826070436"
				+ "</td><td>"
				+"招商银行东大街支行"
				+ "</td><td>"
				+"6236681730000664377"
				+ "</td></tr>";
			$('#tb').append(s);
	}
	$("#page_ctr li").remove();
	//createPageNum(page.pages);
}
	 
/**
 * 删除用户
 * 
 */
function deleteRole() {
	//判断至少写了一项
   	var checkedNum = $("input[name='checkbox']:checked").length;
   	var checkNum = 0;
	rowschecked = new Array();
	var checkboxlist = document.getElementsByName ("checkbox");
	
	for(var i=0;i<checkboxlist.length;i++)
    {
	    if(checkboxlist[i].checked == 1){
	    	checkNum ++;
	    	rowschecked.push(listData[i]);
	    }
    } 
   if(confirm("确定删除所选项目?")){
   $.ajax({
       type:"post",
       url:"delRole",
       data : JSON.stringify(rowschecked),
   	   dataType : 'json',
   	   contentType:"application/json;charset=UTF-8",
       success:function(data){
    	   findEleContract();
    	   art.dialog.tips('删除成功!');
    	   $('#controlAll').attr("checked",false);
       },
       error:function(data){
    	  
           art.dialog.tips('删除失败!');
       }
   });
   }
};


/**
 * 增加用户信息
 */
function add(){
	operate_type = 1;
	$("#dataForm")[0].reset(); // 清空表单
	$('#EditPanel').modal(); // 弹出表单
	$("#province1").html("");
	console.log("provinceList==="+provinceList[0].prvName);
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
					 findEleContract();
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
/**
 * 修改用户信息
 */
function updateRole(){
	
	var checkedNum = $("input[name='checkbox']:checked").length;
	if (checkedNum > 1) {
		alertModel("修改只能选择一项数据!");
		return false;
	} else if (checkedNum <= 0) {
		alertModel("至少选择一条操作数据!");
		return false;
	}
	operate_type = 2;
	var checkedNum = $("input[name='checkbox']:checked").length;
   	var checkNum = 0;
	rowschecked = new Array();
	var checkboxlist = document.getElementsByName ("checkbox");
	for(var i=0;i<checkboxlist.length;i++)
    {
	    if(checkboxlist[i].checked == 1){
	    	checkNum ++;
	    	rowschecked.push(listData[i].roleId);
	    }
    } 
	var roleId=rowschecked[0];
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
		    		console.log("list:"+list[0]);
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
	var s=$("input[name='roleName']").val();
	if(s=="" ){
		alertModel("角色名不能为空！");
		return false;
	}
	var data=$('#dataForm').serialize();
	var submitData=decodeURIComponent(data,true);
	console.log(submitData);
    if(operate_type==1){
		$.ajax({
		    url:'addRole',
		    data: submitData,
	 		type : 'post',
		    cache:false,
		    async:true,
		    success:function(result){
		        //请求成功时
		    	if(result!=null){
	    			alertModel(result.msg);
	    			findEleContract();
		    	}
				$('#EditPanel').modal('hide');
		    },
		    error:function(){
				alertModel("请求失败！");
		    }
		})
    }else{
    	$.ajax({
		    url:'updateRole',
		    data: submitData,
	 		type : 'post',
		    cache:false,
		    async:true,
		    success:function(result){
		        //请求成功时
		    	if(result!=null){
	    			alertModel(result.msg);
	    			findEleContract();
		    	}
				$('#EditPanel').modal('hide');
		    },
		    error:function(){
				alertModel("请求失败！");
		    }
		})
    }
	
}


function dispatch(){
	rowschecked = new Array();
	var prvIdchecked = new Array();
	var checkboxlist = document.getElementsByName ("checkbox");
	for(var i=0;i<checkboxlist.length;i++)
    {
	    if(checkboxlist[i].checked == 1){
	    	rowschecked.push(listData[i].roleId);
	    	prvIdchecked.push(listData[i].prvId);
//	    	console.log("listData[i].roleUser==="+listData[i].roleUser.length);
	    	$("#roleNames").text(listData[i].roleName);
//	    	console.log("listData[i].roleId==="+listData[i].roleId);
	    	
	    	localStorage.setItem("roleId",listData[i].roleId);
	    }
    } 
	var roleId=rowschecked[0];
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
//				console.log("list]=====");
				roleUser=list[0].roleUser;
//				console.log("roleUser]====="+roleUser.length);
		},
		error : function() {
			alertModel("请求异常");
		}
	})
	
	var prvId=prvIdchecked[0];
//	console.log("====="+roleUser);
	allUser(roleUser,prvId);
}

function formSave(){
	var rowschecked = [];
	var checkboxlists = $(".c1");
	for(var i=0;i<checkboxlists.length;i++){
	    if(checkboxlists[i].checked == 1){
//	    	console.log("userDataList[i].userId"+userDataList[i].userId);
	    	rowschecked.push(userDataList[i].userId);
	    	
	    }
    } 
	var roleId = localStorage.getItem("roleId");
	$("#newuserId").val(rowschecked);
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
    			findEleContract();
	    	}
			$('#EditPanel').modal('hide');
	    },
	    error:function(){
			alertModel("请求失败！");
	    }
	})
	
}

function allUser(roleUser,prvId){
	 $.ajax({
		url : 'queryAllUser',
		data : {
			prvId:prvId
		},
		type : 'post',
		dataType : 'json',
		success : function(list) {
			if (list != null) {
				userDataList=list;
				$('#tb1 tr:gt(0)').remove();
				var s = '';
				$("input[name=checkbox"+i+"]").attr("checked", false);
				for (var i = 0; i < list.length; i++) {
					
						s = "<tr style='text-align: center;'><td><input class='c1' type='checkbox' name='checkbox"+i+"' value='"+ list[i].listRoleUserVOs+"'/></td><td style='display:none'>"
						+ list[i].userId
						+ "</td>"
						+"<td>"
						+ list[i].userName
						+ "</td>"
						+ "<td>"
						+list[i].userLoginname
						+ "</td></tr>";
					$('#tb1').append(s);
					console.log("list[i].userId========="+list[i].userId);
					for(var j=0;j<roleUser.length;j++){
						 if(roleUser[j].userId==list[i].userId){
							 console.log("roleUser[j].userId========="+roleUser[j].userId);
							 $("input[name=checkbox"+i+"]").attr("checked", "checked");
				         }
					}
					
			
					
				}
				$('#EditPanel1').modal();	
			}
		},
		error : function() {
			alertModel("请求失败");
		}
	});
}
function gopage(i){
	if(curPageNum == i)
		return;
	curPageNum = i;
	findEleContract();
}