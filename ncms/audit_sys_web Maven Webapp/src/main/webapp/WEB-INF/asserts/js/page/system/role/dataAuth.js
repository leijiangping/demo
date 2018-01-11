var setting = {
	check : {
		enable : true,
		chkStyle: "checkbox",
		chkboxType: { "Y": "ps", "N": "s" }
	},
	data: {
		simpleData: {
			enable: true, 
			idKey: "id",
			pIdKey: "pid"
		}
	}
};
var treeNodes; 
var result;
var treeObj;
var nodes;
var showTableList = null;
$(document).ready(function() {
	curPageNum = 1;
	queryTree();
	allProvince();
	
	$("#choosePower").click(function(){
		if(!hadCheckedRadioRowData()){
			return ;
		}
		  var id =  rowschecked[0].roleId;
		  result = fun_getCheckValue();
		  insertRoleMenu(id,result);
	  })

	
		
	 $("#showUser").click(function(){
		 if(!hadCheckedRadioRowData()){
				return ;
			}
		 showUser();
	 })
});

function showUser() {
	// 先销毁表格
	$('#tb1').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb1").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryAllUserByRoleId", // 获取数据的地址
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
				roleId 			:	rowschecked[0].roleId
			};
			return param;
		},
		columns: [{
            field: 'userCode',
            title: '用户编号'
        },{
            field: 'userName',
            title: '用户名称'
        }, {
            field: 'userLoginname',
            title: '登陆名称'
        }, ],
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
				$('#EditPanel').modal();
			}
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.result //数据
	         };
		}
	});
}

function  fun_getCheckValue(){ 
	treeObj = $.fn.zTree.getZTreeObj("tree");
	var checkNodes = treeObj.getCheckedNodes(true);
	var result=''; 
	var msg = new Array();
	for (var i = 0; i < checkNodes.length; i++) { 
	  	var halfCheck = checkNodes[i].getCheckStatus();
	  	result += checkNodes[i].id +','; 
	}  
	result=result.substring(0,result.lastIndexOf(",")); 
	return result;
}

function insertRoleMenu(id,result){
	 $.ajax({
			url : 'insertRoleMenu',// 跳转到 action
			data : {
				roleId : id,
				msg : result
			},
			type : 'post',
			dataType : 'json',
			success : function(res) {
				alertModel(res.msg);
			},error : function(res){
				alertModel(res.msg);
			}
		});
}

function isCheckedRadio(){
	var checkNum = 0;
	rowschecked = new Array();
	var checklist = $("#tb tbody input[type='radio']");
	for(var i=0;i<checklist.length;i++)
    {
		// 已选中可操作行
	    if(checklist[i].checked == 1){
	    	checkNum ++;
	    	rowschecked.push(showTableList[i]);
	    }
    } 
	return checkNum;
}
function hadCheckedRadioRowData(){
	if(showTableList==null || isCheckedRadio()==0){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}


 function gopage(i){
	 if(curPageNum == i)
		 return;
	 curPageNum = i;
	 reload();
 }

 function queryTree(){
	 $.ajax({  
			url : 'queryAllMenuTree',
			type : 'post',
			cache : false,
			dataType : 'json',
		    success : function(res){  
		        treeNodes = res;  
		        eval("var ztreenode="+result);
		        $.fn.zTree.init($("#tree"), setting, treeNodes); 
		     
		    } ,  
		    error : function(){  
		        console.log("网络延时，请重试.");  
		    } 
		});  
 }
 
 function reload() {
		// 先销毁表格
		$('#tb').bootstrapTable('destroy');
		// 初始化表格,动态从服务器加载数据
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
					prvId 		:	$("#userProvince option:selected").val(),
					roleCode 	: 	$("#functionCode").val(),
					roleName 	: 	$("#functionName").val()
				};
				return param;
			},
			columns: [{
	            radio: true
	        },{
	            field: 'roleCode',
	            title: '角色编号'
	        },{
	            field: 'roleName',
	            title: '角色名称'
	        }, {
	            field: 'roleuserNum',
	            title: '用户数量'
	        }, {
	            field: 'roleNote',
	            title: '角色备注'
	        }, ],
	        
			onCheck: function (row) {
	        	showBack(row.roleId);
	        },
	        
			//onLoadSuccess : function() { // 加载成功时执行
			//},
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
 function showBack(id){
	 $.ajax({
			url : 'queryMenuId',// 跳转到 action
			data : {
				roleId : id
				},
			type : 'post',
			dataType : 'json',
			success : function(res) {
				list = res.Obj;
				treeObj = $.fn.zTree.getZTreeObj("tree");
				treeObj.checkAllNodes(false);
				for(var i = 0 ; i < list.length ; i ++){
					var obj=treeObj.getNodeByParam("id",list[i],null);
					if(obj)
					treeObj.checkNode(obj);
				}
			},
			error : function() {
				console.log("处理失败！");
			}
		});
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
 					
					$('#tb tr:gt(0)').remove();//删除之前的数据
					var s = '';
					if(list.length==1){
						$("#userProvince").append("<option value=" + list[0].prvId + ">" + list[0].prvName + "</option>");
					}else{
						$("#userProvince").append("<option value=''>" +'--请选择--'+ "</option>");
						for (var i = 0; i < list.length; i++) {
							$("#userProvince").append("<option value=" + list[i].prvId + ">" + list[i].prvName + "</option>");
						}
					}
 				}else{
					alertModel(back.msg);
 				}
 			}
			reload();
		
		},
		error : function() {
			alertModel("请求异常");
		}
	})
} 
