var showTableList=null;//已显示表格list
var treeN ;//定义初始化的树对象
var treeNodes; //树节点
var pMenuNodeHadClicked=null;//已点选父菜单
var ipageCount = 10;//每页条数

var setting = {
	data: {
		key: {
			name: "rName"
		},
		simpleData: {
			enable: true,
			idKey: "regId",
			pIdKey: "pRegId"
		}
	},
	callback: {
		onClick: zTreeOnClick,
		onDblClick:zTreeOnDblClick,
		onNodeCreated:zTreeOnClick//初始化创建树后在表格中罗列出根节点下的所有第一节子节点
	}
};

$(document).ready(function(){
	
	initCurrentPage();
});

function initCurrentPage(){
	curPageNum = 1;
	 $(".btn-success").click();
}

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
	pMenuNodeHadClicked = treeNode;//已点选父菜单
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
	var zTree = $.fn.zTree.getZTreeObj("regionTree");
	zTree.expandNode(treeNode);
	return false;
}




/**
 * table数据生成
 */
function createTableData(item){ 
    var childNodes = '';
    if(item.children != undefined){
    	childNodes = '<a class=\'node_a\' onclick=\'createClidrenTableData("'+ item.rCode +'")\' >'+ item.rName +'</a>';
    }else{
    	childNodes = item.rName;
    }
    var s = 	'<tr style=\'text-align: center;\'><td><input type=\'checkbox\'  name=\'checkbox\' value="'+item.regId+'"/></td><td>'
    			+ item.rCode + '</td><td>'
		    	+ childNodes + '</td><td>'
		    	+ item.regOrder + '</td><td id=\'pi\' style=\'display:none\'>'
		    	+ item.pRegId +'</td><td id=\'pn\'>'
		    	+ item.pRegName +'</td></tr>';

	$('#tb').append(s);
}

//单击表格中节点名称,有子节点的话，点击进去在表格中罗列出所有子节点的信息
function createClidrenTableData(rCode){
$('#tb tr:gt(0)').remove();//删除之前的数据
	$.each(pMenuNodeHadClicked.children,function(){
		if(this.rCode === rCode){//获知在表格中点选的是哪一个父节点
			pMenuNodeHadClicked = this;//已点选父菜单重新赋值给pMenuNodeHadClicked
			$.each(this.children,function(){
	    		createTableData(this);
			nodes = treeN.getNodeByParam("code", rCode, null);//点击表格选中父节点的同时左边对应树节点也同时选上
    		treeN.selectNode(nodes);
//    		showTableList.push(this);
	    	});
		}
	});
}


$(document).ready(function(){
	var treeObj = $("#regionTree");
	treeObj.addClass("showIcon");
	/**
	 * 获取左侧组织机构
	 */
	function reloadMenuTree(item){
		$.ajax({
			url : 'region/getSysRegionManage',   
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if (data != null) {
					data = sortByKey(data, "regOrder");
		            treeNodes = data;   //把后台封装好的简单Json格式赋给treeNodes 
		        	$.fn.zTree.init(treeObj, setting, treeNodes);
		        	
		        	
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
						treeN = $.fn.zTree.getZTreeObj("regionTree");
						nodes = treeN.getNodes();
						pMenuNodeHadClicked=nodes[0];
						if (nodes.length > 0) {
							treeN.selectNode(nodes[0]);
						/*	$.each(nodes[0].children,function(){
			    	    		createTableData(this);
			    	    	});*/
						}
		            }
				}
			},
			error : function() {
//				layer.msg("请求异常");
			}
		});
	}
	reloadMenuTree()
	 var listData = null;
/**
 * 条件查询
 */	
	 $("#queryRegion").click(function(){
		 var regCode=$("#regCode").val();
		 var regName=$("#regName").val();
		 
		 $.ajax({
				url : 'region/queryByConditions',
				data : {
					regCode:regCode,
					regName:regName
				},
				type : 'post',
				dataType : 'json',
				success : function(resList) {
					if(resList != null){
						pMenuNodeHadClicked = null;
			            showTableList = new Array();
			            $('#tb tr:gt(0)').remove();//删除之前的数据
						$.each(resList.obj, function (n, item) {
			               createTableData(item);
			               showTableList.push(item);
			           });
					}
		 		},
				error : function() {
					alertModel("请求失败");
				}
			});
		 
		 
	 });

	 $("#insertRegions").click(function(){
		 var treeObj = $.fn.zTree.getZTreeObj("regionTree");
		 var nodes = treeObj.getSelectedNodes();
		 var nodess=JSON.stringify(nodes)
		 var pId=nodes[0].regId;
		 var pName=nodes[0].rName;
		 $("#preg_name1").val(pName);
		 $("#preg_id1").val(pId);
		 $('#EditPanel1 .form-group span.modal-error').children().remove();
		 $('#EditPanel1').modal();
	 });
	 
	 $("#insertFormSubmit").click(function(){
		 if(validformNew().form()){
			var data=$('#insertRegion').serialize();
			
			var submitData=decodeURIComponent(data,true);
			$("#insertFormSubmit").attr("disabled",true);
			$.ajax({
			    url:'region/insertRegion',
			    data: submitData,
		 		type : 'post',
			    cache:false,
			    async:true,
			    success:function(result){
			        //请求成功时
			    	if(result!=null){
		    			alertModel(result.msg);
		    			reloadMenuTree();
			    	}
			    	$("#insertFormSubmit").attr("disabled",false);
			    },
			    error:function(){
					alertModel("请求失败！");
					$("#insertFormSubmit").attr("disabled",false);
			    }
			})
		 }
	 });
	//验证
		function validformNew(){
			var addnew_validate= bindformvalidate("#insertRegion", {
				regCode:{
					required : true,
				},
				regOrder:{
					required : true,
					number: true,
				},
				regName:{
					required : true,
				}
			},{
				regCode:{
					required : "必填！"
				},
				regOrder:{
					required : "必填！"
				},
				regName:{
					required : "必填！"
				}
			});

		  return addnew_validate;
		}
	 $("#updateRegions").click(function(){
			if(!hadCheckedRowData()){
				return false;
			}
			$("#preg_name").val(rowschecked[0].pRegName);
		   $.ajax({
		       type:"get",
		       url:"region/getRegion",
		       data : {
		    	   regId:rowschecked[0].regId
		       },
		   	   dataType : 'json',
		   	   contentType:"application/json;charset=UTF-8",
		       success:function(data){
		    	   $("#reg_code").val(data.regCode);
		    	   $("#reg_name").val(data.regName);
		    	   $("#reg_id").val(data.regId);
		    	   $("#reg_order").val(data.regOrder);
		    	   $('#EditPanel .form-group span.modal-error').children().remove();
		    	   $('#EditPanel').modal();
		       },
		       error:function(data){
		    	  
//		           art.dialog.tips('删除失败!');
		       }
		   });
	 });

	 $("#updateFormSubmit").click(function(){
		 if(validform().form()){
			var data=$('#updateRegion').serialize();
			var submitData=decodeURIComponent(data,true);
			$("#updateFormSubmit").attr("disabled",true);
			$.ajax({
			    url:'region/updateRegion',
			    data: submitData,
		 		type : 'post',
		 		cache:false,
			    async:true,
			    success:function(result){
			        //请求成功时
			    	if(result!=null){
			    		$('#EditPanel').modal('hide');
		    			alertModel(result.msg);
		    			reloadMenuTree();
			    	}
			    	$("#updateFormSubmit").attr("disabled",false);
			    },
			    error:function(){
			    	alertModel("请求失败！");
			    	$("#updateFormSubmit").attr("disabled",false);
			    }
			})
		 }
	 });
	//验证
		function validform(){
			var addnew_validate= bindformvalidate("#updateRegion", {
				regCode:{
					required : true,
				},
				regOrder:{
					required : true,
					number: true,
				}
			},{
				regCode:{
					required : "必填！"
				},
				regOrder:{
					required : "必填！"
				}
			});

		  return addnew_validate;
		}
	 
	 	$("#deleteRegion").click(function(){
		   	var checkedNum = $("input[name='checkbox']:checked").length;
		    if(checkedNum==0){
				alertModel("至少选择一条操作数据");
				return false;
			}
		    if(confirm("确定删除所选项目?")){
			  var checkNum = 0;
			  rowschecked = new Array();
				var checkboxlist = document.getElementsByName ("checkbox");
				
				for(var i=0;i<checkboxlist.length;i++)
			    {
				    if(checkboxlist[i].checked == 1){
				    	checkNum ++;
				    	rowschecked.push(showTableList[i].regId);
				    }
			    } 
			   $.ajax({
			       type:"post",
			       url:"region/delRegion",
			       data : JSON.stringify(rowschecked),
			   	   dataType : 'json',
			   	   contentType:"application/json;charset=UTF-8",
			       success:function(data){
			    	alertModel(data.msg);
			    	   reloadMenuTree();
			       },
			       error:function(data){
		//	           art.dialog.tips('删除失败!');
			    	   alertModel("请求失败！");
			       }
			   });
		    }
			  
			
	 	});

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
		

});