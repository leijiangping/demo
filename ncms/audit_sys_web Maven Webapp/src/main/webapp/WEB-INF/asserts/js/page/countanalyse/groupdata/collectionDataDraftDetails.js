//已显示表格list
var showTableList = null;
$(document).ready(function() {
	initCurrentPage();
});
var uploader=null;
function initCurrentPage(){
	curPageNum = 1;	
	// 加载省份
	datacollectPro();	
	// 编辑修改的数据
	getUpdateParam();
	
	//全局设置加载中的效果
	/*$.ajaxSetup({
		 beforeSend: function () {
		    $("#loading").html("<img src=\""+projectName+"/asserts/image/loading-b.gif\"/>");
		 },
		 complete: function () {
		    $("#loading").html("");
		},
		error: function () {
		    $("#loading").html("");
		}
	});*/
	
	/**
	 * 表单table文件上传
	 * */
	var callback=function uploadSuccessBack(file,json){
		if(json.success == 1){
			var datacollecttypeId=json.Obj.datacollecttypeId;
			var fileName = json.Obj.name;
			$("#"+datacollecttypeId).parent().next().find("input[type='text']").val(fileName);
		}else{
			alertModel(json.msg);
		}	
	}
	uploader = webUploadInit("dataPrvTypeId","数据收集","uploadFile",callback);	
}

/**
 * 加载省份数据
 * */
function datacollectPro(){
	$.ajax({
	    url:'queryAllProvince',
 		type : 'post',
	    cache:false,
	    async:true,
	    success:function(result){
	    	if(result!=null){
		    	var datas = result.Obj; 
	    		var html='';
	    		html+='<div class="distribute-list">';
	    			html+='<input type="button" value="全选" class="btn btn-primary" id="selectAll" style="color:#fff!important;margin-right:5px">';
	    			html+='<input type="button" value="全不选" class="btn btn-primary" id="unSelect" style="color:#fff!important;">';
	    		html+='</div>';
	    		html+='<div class="distribute-item">';
	    			for(var i=0;i<datas.length;i++){
			    		html+='<span>';
		    				html+='<input type="checkbox" name="box" id="'+datas[i].id+'" value="'+datas[i].id+'">'+ datas[i].name+'';
			    		html+='</span>';
					 }
	    		html+='</div>';
	    		$('#distribute').append(html);
	    		/*全选*/
	    		$("#selectAll").click(function(){
	    			$("#distribute :checkbox").prop("checked", true);  
    			});
	    		/*全不选*/
	    		$("#unSelect").click(function(){  
    			   $("#distribute :checkbox").prop("checked", false);  
    			});
	    		$('#distribute span').click(function(e){
	    			e.stopPropagation();
	    		    $(this).find(":checkbox").click();
	    		});
	    		$('#distribute input[type="checkbox"]').click(function(e){
	    			e.stopPropagation();
	    		    $(this).find(":checkbox").click();
	    		});	    		
	    	}
	    },
	    error:function(){
	    	alertModel("请求失败！");
	    }
	});
}
/**
 * 抄送加载的人员
 * */
function selectionRange(){
	$("#selectionRange").empty();
	$.ajax({
	    url:'queryCopyUser',
 		type : 'post',
	    cache:false,
	    async:true,
	    success:function(result){
	    	if(result!=null){
		    	var datas = result.Obj;
	    		var html='';
    			for(var i=0;i<datas.length;i++){
		    		html+='<span>';
	    				html+='<input type="checkbox" name="rangebox" id="'+datas[i].userId+'" value="'+datas[i].userName+'">'+ datas[i].userName+'';
		    		html+='</span>';
				 }
	    		$('#selectionRange').append(html);
	    		$('#EditCost').modal();
	    		$('#selectionRange span').click(function(e){
	    			e.stopPropagation();
	    		    $(this).find(":checkbox").click();
	    		});
	    		$('#selectionRange input[type="checkbox"]').click(function(e){
	    			e.stopPropagation();
	    		    $(this).find(":checkbox").click();
	    		});	    		
	    	}
	    },
	    error:function(){
	    	alertModel("请求失败！");
	    }
	});
}
function sendFormSubmit(){
	var rangeStr = $("input:checkbox[name='rangebox']:checked");
	var html = '';
	for(var i=0;i<rangeStr.length;i++){
		var userId = rangeStr[i].id;
		var userName = rangeStr[i].value;
		html+='<span class="rangePersonStyle" id="'+userId+'">' + userName + '<i class="rangePersonDelectStyle" id="'+userName+'" onclick="rangePersonDelete(this);">X</i></span>';
	}
	$("#selectionRangePerson").append(html);
	$('#EditCost').modal('hide');
}
function rangePersonDelete(obj){
	var rangeName = obj.id;
	console.log(rangeName);
	$("#"+rangeName).parent().remove();
}
/**
 * datacollectTb的点击新增addTr
 * */

function addTrData(){
	var datacollectId = getParameter("datacollectId");
	$.ajax({
		url:"insertDataType",
		data:{
			datacollectId : datacollectId
		},
		type:'post',
		dataType:'json',
		async:false,
		success:function(back){
			if (back != null) {
				 var item= back.Obj;
				 console.log(item);
				 var s="";
				 var strItem = item.typeList;				 
				 s +='<tr>' 
				 	s +='<td><input type="checkbox" name="checkbox" checked="checked" value="'+item.id+'"/></td>';
				 	s +='<td>';
				 		s +='<select id="'+item.id+'" class="form-control dict_selsect" onchange="gradeChange(this)">'
					 		if(strItem != null){
					 			s+='<option value="请选择数据类型">-请选择数据类型-</option>';
								for(var i=0;i<strItem.length;i++){									
									s+='<option value="'+strItem[i]+'">'+ strItem[i]+'</option>';
								}
							}		
			 			s +='</select>'
		 			s +='</td>';
		 			s +='<td>';
		 			s +='</td>';
		 			s +='<td>';
		 				s +='<input type="button" class="btn btn-danger" value="删除" onclick="del(this)" style="padding: 5px 10px 2px 10px;margin-bottom: -1px; color: #fff!important" />'
		 			s +='</td>';
		 		 s +='</tr>';
						
				 $('#datacollectTb tbody').append(s);			 				 
			}
		},
		error:function(){
			alertModel("请求失败");
		}
	});
}
/**
 * 删除所在行
 * */
function del(obj)
{
	var datacollectId = getParameter("datacollectId");
   var tr=obj.parentNode.parentNode;
   var id = tr.cells[0].childNodes[0].value;
   $.ajax({
		url:"deleteTypeBytypeId",
		data:{
			datacollectypeId:id,
			datacollecId:datacollectId
		},
		type:'post',
		dataType:'json',
		async:false
	});
   tr.parentNode.removeChild(tr);
}

/**
 * datacollectTb下拉框选中执行的事件
 * */
function gradeChange(obj){
	var itemId = obj.id;
	var type = $("#"+itemId).val();
	$.ajax({
		url:"queryPathName",
		data:{
			id:itemId,
			type:type
		},
		type:'post',
		dataType:'json',
		async:false,
		success:function(back){
			if(back.success=="1"){
				var data=back.Obj;
				if(data.name!=""&&data.name!=null){
					$("#"+obj.id).parent().next().html(data.name);
				}else{
					if($("#"+obj.id).val()=="请选择数据类型"){
						$("#"+obj.id).parent().next().html("");
					}else{
						$("#"+obj.id).parent().next().html('<input type="text" class="form-control" id="dataMoudle" onblur="dataMoudleblur(this)" style="width:70%"/><a id="'+obj.id+'" onclick="collectPrvUpload(this)">【上传】</a>');
					}
					
				}
			}
		},
		error:function(){
			alertModel("请求失败");
		}
	});
}
/**
 * datacollectTb input>text光标丢失事件
 * */
function dataMoudleblur(obj){
	var dataMoudleTd = obj.parentNode;
	var id = $(dataMoudleTd).prev().prev().find("input[name='checkbox']").val();
	console.log(id);
	var dataMoudle = $(dataMoudleTd).find("input[type='text']").val();
	console.log(dataMoudle);
	$.ajax({
		url:"updateFileName",
		data:{
			id:id,
			dataMoudle:dataMoudle
		},
		type:'post',
		dataType:'json',
		async:false,
		success:function(back){
			if (back != null) {
				
			}
		},
		error:function(){
			alertModel("请求失败");
		}
	});
}

/**
 * 判断为修改，编辑表单
 * */
function getUpdateParam(){ 
	if(getParameter("type")==2){
		var datacollectId = getParameter("datacollectId");		
		$.ajax({
		    url:'queryGrpDataCollectById',
		    data: {
		    	datacollectId:datacollectId
		    },
	 		type : 'post',
		    cache:false,
		    async:true,
		    success:function(result){
		    	console.log(result);
		    	if(result!=null){
			    	var datas = result.Obj;
		    		$('#datacollectTitle').val(datas.grpDatacollectVO.datacollectTitle);
		    		$("#datacollectTitle").attr({ readonly: 'true' });
		    		$('#datacollectNote').val(datas.grpDatacollectVO.datacollectNote);
		    		/*时间戳转换日期*/
		    		/*var datacollectDeadline = datas.grpDatacollectVO.datacollectDeadline;    			    		
		    		function add0(m){return m<10?'0'+m:m }
		    		function getTime(ns){
		    			var date = new Date(ns);
		    			var datTime=date.getFullYear() + '/' + add0((date.getMonth() + 1)) + '/' +add0(date.getDate())+' '+add0(date.getHours())+':'+add0(date.getMinutes());
		    			return datTime;
	    			}
		    		var date=getTime(datacollectDeadline);
		    		$('#datacollectDeadline').val(date);*/
		    		var date2=datas.grpDatacollectVO.datacollectDeadline;
		    		$('#datacollectDeadline').html(date2.substring(0,16));
		    		//返回的省份id(需要勾选的)
		    		var boxPrvId = datas.prvIds;
		    		//全部的省份id(遍历)
	    			var allCheck = $('.distribute-item input[name="box"]');
	    			var allPrvId = new Array();
	    			for(var j=0;j<allCheck.length;j++){
	    				var checkId = allCheck[j].id;
	    				allPrvId.push(checkId);
	    			}
	    			//遍历重复省份id(让其选中)
	    			for(var s=0;s<allPrvId.length;s++){
	    				var allStr = allPrvId[s];
	    				for(var i=0;i<boxPrvId.length;i++){
	    					var boxStr = boxPrvId[i].prvId;
	    					console.log()
	    					if(allStr==boxStr){
	    						$("#"+allStr).attr("checked", true);
	    					}
	    				}
	    			}
	    			//抄送人编辑
	    			var userList = datas.userList;
	    			var u="";
	    			console.log(userList)
	    			for(var j=0;j<userList.length;j++){
		    			if(userList[j]!=null){
		    				//u+= "<span id='"+userList[j].userId+"'>"+userList[j].userName+"</span>";
		    				u +='<span class="rangePersonStyle" id="'+userList[j].userId+'">' +userList[j].userName+ '<i class="rangePersonDelectStyle" id="'+userList[j].userName+'" onclick="rangePersonDelete(this);" >X</i></span>';		    						    				
		    			}
	    			}
	    			$("#selectionRangePerson").html(u);
	    			
	    			
	    			
	    			/*编辑表格*/
	    			var item= datas.GrpDatacollecttypeList;
	    			if(item!=null){		
    					 for(var i=0;i<item.length;i++){
    						 var s="";
	    					 s +='<tr>' 
	    					 	s +='<td><input type="checkbox" name="checkbox" value="'+item[i].datacollecttypeId+'" checked="checked"/></td>';
	    					 	s +='<td>';
	    					 		s +='<select id="'+item[i].datacollecttypeId+'" class="form-control dict_selsect" onchange="gradeChange(this)">'
										s+='<option value="'+item[i].datacollecttempType+'" selected = "selected">'+ item[i].datacollecttempType+'</option>';
	    				 			s +='</select>'
	    			 			s +='</td>';
	    			 			s +='<td>';
	    			 				if(item[i].datacollecttempFilename !=""){
	    			 					s +=''+item[i].datacollecttempFilename+''
	    			 				}else{
	    			 					s +='<input type="text" class="form-control" id="dataMoudle" onblur="dataMoudleblur(this)" style="width:70%"/><a id="'+item[i].datacollecttypeId+'" onclick="collectPrvUpload(this)">【上传】</a>';	    								
	    			 				}	    			 				
	    			 			s +='</td>';
    			 				s +='<td>';
	    			 				s +='<input type="button" class="btn btn-danger" value="删除" onclick="del(this)" style="padding: 5px 10px 2px 10px;margin-bottom: -1px; color: #fff!important" />'
	    			 			s +='</td>';	
	    			 		 s +='</tr>';
	    							
	    					 $('#datacollectTb tbody').append(s);
    					}
	    			}    			
		    	}
		    },
		    error:function(){
		    	alertModel("请求失败！");
		    }
		});
	}
};


/**
 * 判断标题唯一性
 * */
function checkOnly(){
	var datacollectTitle=$('#datacollectTitle').val();
	$.ajax({
		url : 'querySameThing',
		data : {
			datacollectTitle : datacollectTitle
		},
		type : 'post',
		dataType : 'json',
		aysnc:false,
		cache : false,
		success : function(back) {
			if (back != null) {
 				if(back.success=="0"){
					$("#datacollectTitle").next("#err").html("<img src=\"../../../image/error.png\"/>标题已存在");
					$("#datacollectTitle").next("#err").css({"color":"red"});
					flag=true;
 					/*alertModel("标题已存在");
 					return false;*/
				}else{
					$("#datacollectTitle").next("#err").html("<img src=\"../../../image/right_1.png\"/>验证成功");
					$("#datacollectTitle").next("#err").css({"color":"green"});
					flag=false;
				}
			}	
		},
		error : function() {
			alertModel("请求异常");
			flag=true;
		}
	});
	
}

/**
 * 验证
 * */
function validform(){
	var twr_validate= bindformvalidate("#collectionDataForm", {
		datacollectTitle:{
			required : true
		},
		datacollectDeadline:{
			required : true
		},
		datacollectNote:{
			required : true
		},
		/*box:{
			required : true
		}*/
	}, {
		datacollectTitle:{
			required : "请填写标题！"
		},
		datacollectDeadline:{
			required : "请选择处理时限！"
		},
		datacollectNote:{
			required : "请填写正文！"
		},
		/*box:{
			required : "派发省份必选！"
		}*/
	});
  return twr_validate;
}

/**
 * 新增/修改的确定保存草稿
 * */
function datacollectFormSubmit(){
	confirmModel("您确定要保存草稿吗？","newOrUpdateSubmit");
}
function newOrUpdateSubmit(){
	var deadlineDate = $("#datacollectDeadline").val();
	var newDate = new Date(deadlineDate);
	var sysDate = new Date();
	if(newDate < sysDate){
		alertModel("处理时限不能小于当前时间！");
		$("#datacollectDeadline").next("#err").html("<img src=\"../../../image/error.png\"/>处理时限不能小于当前时间");
		$("#datacollectDeadline").next("#err").css({"color":"red"});
		return false;
	}
	if(validform().form()){		
		if($("input:checkbox[name='box']:checked").length == 0){
			alertModel("请至少选择一个派发省份！");
			return false;
		}
		
		if($("input:checkbox[name='checkbox']:checked").length == 0){
			alertModel("请添加您的数据模版！");
			return false;
		}
		var tr = $("#datacollectTb tbody").find("tr");
		for(var i=0;i<tr.length;i++){
			var text =$("#datacollectTb tbody").find("tr").eq(i).find("td").eq(2).text();
			console.log(text);
			if(text===""){
				alertModel("数据信息不可以为空！");
				return false;
			}
		}
		if($("#dataMoudle").val() == ""){
			alertModel("上传数据不可以为空!");
			return false;
		}
		if(getParameter("type")==1){
			checkOnly();
			if(flag){
				return false;
			}
			var datacollectTitle=$('#datacollectTitle').val();//标题数据参数
			/*选择省份的id*/
			var prvArray=new Array();
			var prvChecked = $('.distribute-item input[name="box"]:checked');
			for (var s = 0; s < prvChecked.length; s++) {
				var prvRow = prvChecked[s];
				prvArray.push(prvRow.id);
			}
			/*抄送人的id*/
			var rangeArray=new Array();
			var rangeChecked = $('#selectionRangePerson span');
			for (var s = 0; s < rangeChecked.length; s++) {
				var rangeRow = rangeChecked[s];
				rangeArray.push(rangeRow.id);
			}
			
			var datacollectDeadline=$('#datacollectDeadline').val();//处理时限数据参数
			var datacollectNote=$('#datacollectNote').val();//备注数据参数
			/*最下面table的数据*/
			var tableChecked = $('input[name="checkbox"]:checked');
			var selectedData = new Array();
			for(var i=0;i<tableChecked.length;i++){
				var tableRow = tableChecked[i];
				selectedData.push(tableRow.value);
			}
			$("#saveSet").attr("disabled",true);			
			$.ajax({
			    url:'insertSelective',
			    data: {
			    	datacollectTitle:datacollectTitle,
			    	prvId:prvArray.join(","),
			    	datacollectCopy:rangeArray.join(","),
			    	datacollectDeadline:datacollectDeadline,
			    	datacollectNote:datacollectNote,
			    	id:selectedData.join(",")
			    },
		 		type : 'post',
			    cache:false,
			    async:true,
			    success:function(result){
			        //请求成功时
			    	if(result!=null){
			    		alertModel(result.msg);
			    	}
			    	window.location.href="collectionDataDraft.html";
			    	$("#saveSet").attr("disabled",false);
			    },
			    error:function(){
			    	alertModel("请求失败！");
			    	$("#saveSet").attr("disabled",false);
			    }
			});
		}
		else if(getParameter("type")==2){
			var datacollectTitle=$('#datacollectTitle').val();//标题数据参数
			/*选择省份的id*/
			var prvArray=new Array();
			var prvChecked = $('.distribute-item input[name="box"]:checked');
			for (var s = 0; s < prvChecked.length; s++) {
				var prvRow = prvChecked[s];
				prvArray.push(prvRow.id);
			}
			/*抄送人的id*/
			var rangeArray=new Array();
			var rangeChecked = $('#selectionRangePerson span');
			for (var s = 0; s < rangeChecked.length; s++) {
				var rangeRow = rangeChecked[s];
				rangeArray.push(rangeRow.id);
			}
			
			var datacollectDeadline=$('#datacollectDeadline').val();//处理时限数据参数
			var datacollectNote=$('#datacollectNote').val();//备注数据参数
			/*最下面table的数据*/
			var tableChecked = $('input[name="checkbox"]:checked');
			var selectedData = new Array();
			for(var i=0;i<tableChecked.length;i++){
				var tableRow = tableChecked[i];
				selectedData.push(tableRow.value);
			}
					
			$("#saveSet").attr("disabled",true);
			$.ajax({
			    url:'updateByPrimaryKeySelective',
			    data:{
			    	datacollectId:getParameter("datacollectId"),
			    	datacollectTitle:datacollectTitle,
			    	prvId:prvArray.join(","),
			    	datacollectCopy:rangeArray.join(","),
			    	datacollectDeadline:datacollectDeadline,
			    	datacollectNote:datacollectNote,
			    	id:selectedData.join(",")
			    },
		 		type : 'post',
			    cache:false,
			    async:true,
			    success:function(result){
			        //请求成功时
			    	if(result!=null){
			    		alertModel(result.msg);	    			
			    	}
		    		window.location.href="collectionDataDraft.html";
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

/**
 * 新增/修改的确定保存并派发
 * */
function datacollectDistributeSubmit(){
	confirmModel("您确定要保存并派发吗？","newOrDistributeSubmit");
}
function newOrDistributeSubmit(){
	var deadlineDate = $("#datacollectDeadline").val();
	var newDate = new Date(deadlineDate);
	var sysDate = new Date();
	if(newDate < sysDate){
		alertModel("处理时限不能小于当前时间！");
		$("#datacollectDeadline").next("#err").html("<img src=\"../../../image/error.png\"/>处理时限不能小于当前时间");
		$("#datacollectDeadline").next("#err").css({"color":"red"});
		return false;
	}
	if(validform().form()){		
		if($("input:checkbox[name='box']:checked").length == 0){
			alertModel("请至少选择一个派发省份！");
			return false;
		}
		
		if($("input:checkbox[name='checkbox']:checked").length == 0){
			alertModel("请添加您的数据模版！");
			return false;
		}
		var tr = $("#datacollectTb tbody").find("tr");
		for(var i=0;i<tr.length;i++){
			var text =$("#datacollectTb tbody").find("tr").eq(i).find("td").eq(2).text();
			console.log(text);
			if(text===""){
				alertModel("数据信息不可以为空！");
				return false;
			}
		}
		if($("#dataMoudle").val() == ""){
			alertModel("上传数据不可以为空!");
			return false;
		}
		if(getParameter("type")==1){
			checkOnly();
			if(flag){
				return false;
			}
			var datacollectTitle=$('#datacollectTitle').val();//标题数据参数
			/*选择省份的id*/
			var prvArray=new Array();
			var prvChecked = $('.distribute-item input[name="box"]:checked');
			for (var s = 0; s < prvChecked.length; s++) {
				var prvRow = prvChecked[s];
				prvArray.push(prvRow.id);
			}
			/*抄送人的id*/
			var rangeArray=new Array();
			var rangeChecked = $('#selectionRangePerson span');
			for (var s = 0; s < rangeChecked.length; s++) {
				var rangeRow = rangeChecked[s];
				rangeArray.push(rangeRow.id);
			}
			
			var datacollectDeadline=$('#datacollectDeadline').val();//处理时限数据参数
			var datacollectNote=$('#datacollectNote').val();//备注数据参数
			/*最下面table的数据*/
			var tableChecked = $('input[name="checkbox"]:checked');
			var selectedData = new Array();
			for(var i=0;i<tableChecked.length;i++){
				var tableRow = tableChecked[i];
				selectedData.push(tableRow.value);
			}
						
			$.ajax({
			    url:'insertAndSend',
			    data: {
			    	datacollectId:null,
			    	datacollectTitle:datacollectTitle,
			    	prvId:prvArray.join(","),
			    	datacollectCopy:rangeArray.join(","),
			    	datacollectDeadline:datacollectDeadline,
			    	datacollectNote:datacollectNote,
			    	id:selectedData.join(",")
			    },
		 		type : 'post',
			    cache:false,
			    async:true,
			    success:function(result){
			    	if(result.success==1){
			    		alertModel(result.msg);	
			    		window.location.href="collectionDataDistribute.html";
			    	}else{
			    		alertModel(result.msg);
			    	}
			    },
			    error:function(result){
			    	alertModel(result.msg);
			    }
			});
		}
		else if(getParameter("type")==2){
			var datacollectTitle=$('#datacollectTitle').val();//标题数据参数
			/*选择省份的id*/
			var prvArray=new Array();
			var prvChecked = $('.distribute-item input[name="box"]:checked');
			for (var s = 0; s < prvChecked.length; s++) {
				var prvRow = prvChecked[s];
				prvArray.push(prvRow.id);
			}
			/*抄送人的id*/
			var rangeArray=new Array();
			var rangeChecked = $('#selectionRangePerson span');
			for (var s = 0; s < rangeChecked.length; s++) {
				var rangeRow = rangeChecked[s];
				rangeArray.push(rangeRow.id);
			}
			
			var datacollectDeadline=$('#datacollectDeadline').val();//处理时限数据参数
			var datacollectNote=$('#datacollectNote').val();//备注数据参数
			/*最下面table的数据*/
			var tableChecked = $('input[name="checkbox"]:checked');
			var selectedData = new Array();
			for(var i=0;i<tableChecked.length;i++){
				var tableRow = tableChecked[i];
				selectedData.push(tableRow.value);
			}			
			$.ajax({
			    url:'insertAndSend',
			    data:{
			    	datacollectId:getParameter("datacollectId"),
			    	datacollectTitle:datacollectTitle,
			    	prvId:prvArray.join(","),
			    	datacollectCopy:rangeArray.join(","),
			    	datacollectDeadline:datacollectDeadline,
			    	datacollectNote:datacollectNote,
			    	id:selectedData.join(",")
			    },
		 		type : 'post',
			    cache:false,
			    async:true,
			    success:function(result){
			    	if(result.success==1){
			    		alertModel(result.msg);	
			    		window.location.href="collectionDataDistribute.html";
			    	}else{
			    		alertModel(result.msg);
			    	}
			    },
			    error:function(result){
					alertModel(result.msg);
			    }
			});
		}
	}	
}

/**
 * 表单table中的上传
 * */
function collectPrvUpload(tel){
	var obj={"datacollecttypeId":tel.id};
	console.log(obj);
	webUploadClick("dataPrvTypeId",uploader,obj);

	/**
	 * 导入弹出框
	 * @param title 弹出框名称()
	 * @param suffix 模块标记
	 * @param func 回调保存调用的方法名称
	 * @param url 后台方法
	 * @returns 用法：webUpload(url,title,suffix,func);
	 * @author zhujj
	 */
	//webUpload("url","上传","CollertionData","fileSubmit");
}
/**
 * 返回按钮
 */
function back(){
	$.ajax({
		url:"deleteUserLessMsg",
		type:'post',
		dataType:'json',
		async:false
	});
	javascript:history.back(-1);
}
