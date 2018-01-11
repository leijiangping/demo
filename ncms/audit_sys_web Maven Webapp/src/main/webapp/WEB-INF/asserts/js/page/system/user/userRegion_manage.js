
var treeNodes; 
var setting;
var result;
var treeObj;
var nodes;
 $(document).ready(function(){
	 curPageNum = 1;
	 
	 allProvince();
	
	$("#choosePower").click(function(){
		if(!hadCheckedRadioRowData()){
			return ;
		} 
		var id =  rowschecked[0].userId;
		 console.log(id);
		  result = fun_getCheckValue();
		  insertUserRegion(id,result);
	  })
	  function  fun_getCheckValue(){ 
		  	treeObj = $.fn.zTree.getZTreeObj("tree");
		  	var checkNodes = treeObj.getCheckedNodes(true);
		  	var result=''; 
		  	var msg = new Array();
	        for (var i = 0; i < checkNodes.length; i++) { 
	        	if(!checkNodes[i].isParent){
	        		result += checkNodes[i].id +','; 
	        	}
	        }  
	        result=result.substring(0,result.lastIndexOf(",")); 
	        return result;
	  }
});
 function insertUserRegion(id,result){
	 $.ajax({
			url : 'insertUserRegion',// 跳转到 action
			data : {
				userId : id,
				msg : result
			},
			type : 'post',
			dataType : 'json',
			success : function(res){
				alertModel(res.msg);
			},error : function(res){
				alertModel(res.msg);
			}
		});
 }
 function queryTree(){
	 var id = $("#userProvince option:selected").val();
	  $.ajax({  
			url : 'queryOnePro',
			data : {
				prvId : id
			},
			type : 'post',
			cache : false,
			dataType : 'json',
		    success : function(res){  
		        treeNodes = res.Obj;  
		        setting = {
		        		check : {
		        			enable : true,
		        			chkStyle: "checkbox",
		        			chkboxType: { "Y": "p", "Y": "s" }
		        		},
		        		data: {
		        			simpleData: {
		        				enable: true,
		        				idKey: "id",
		        				pIdKey: "pid"
		        			}
		        		}
					};
		        $("#tree li").remove();
		        eval("var ztreenode="+result);
		        $.fn.zTree.init($("#tree"), setting, treeNodes); 
		    } ,  
		    error : function(){  
		        alertModel("网络延时，请重试.");  
		    } 
		});  
 }
 function gopage(i){
	 if(curPageNum == i)
		 return;
	 curPageNum = i;
	 loadUserTableData();
}
 /**
  * 获取选中的radio
  * */
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
 function loadUserTableData() {
	 queryTree();
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
					prvId : 		$("#userProvince option:selected").val(),
					userLoginName : $("#userLoginName").val(),
					userName : 		$("#userName").val()
				};
				return param;
			},
			columns: [{
	            radio: true
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
	            field: 'userEmail',
	            title: '电子邮箱'
	        }, ],
	        onCheck: function (row) {
	        	showBack(row.userId);
	        },
//			onLoadSuccess : function() { // 加载成功时执行
//				
//			},
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
			url : 'queryRegionId',// 跳转到 action
			data : {
				userId : id
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
				alertModel("处理失败！");
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
							$('#userProvince').attr("disabled","disabled"); 
							$("#userProvince").append("<option value=" + list[0].prvId + ">" + list[0].prvName + "</option>");
						}else{
							for (var i = 0; i < list.length; i++) {
								$("#userProvince").append("<option value=" + list[i].prvId + ">" + list[i].prvName + "</option>");
								
							}
						}
						
	 				}else{
						alertModel(back.msg);
	 				}
	 				loadUserTableData();
	 			}
			},
			error : function() {
				alertModel("请求异常");
			}
		})
	} 
	 
	 
	 
	 
	 
	 