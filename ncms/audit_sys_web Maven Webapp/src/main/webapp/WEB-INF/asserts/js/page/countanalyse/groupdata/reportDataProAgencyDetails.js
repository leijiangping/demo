//已显示表格list
var showTableList = null;
$(document).ready(function() {
	initCurrentPage();
});
var uploader=null;
var uploader2=null;
function initCurrentPage(){
	curPageNum = 1;
	getUpdateParam();

	/**
	 * 加载上传文件模块
	 * @param suffix 模块标记
	 * @param func 上传成功回调方法
	 * @param url 后台方法，默认使用公用上传方法
	 * @returns 用法：webUpload("上传","formSubmit")
	 * @author zhujj
	 */
	/**
	 * 表单table文件上传
	 * */
	var callback=function uploadSuccessBack(file,json){
		if(json.success == 1){
			var datacollecttypePrvId=json.Obj.datacollecttypePrvId;
			var fileName = json.Obj.name;
			$("#"+datacollecttypePrvId).parent().prev().html(fileName);
		}else{
			alertModel(json.msg);
		}
	}
	uploader =webUploadInit("dataPrvId","数据收集","uploadPrvFile",callback);
	/**
	 * 其他文件上传
	 * */
	var callback2=function uploadSuccessBack(file,json){
		if(json.success == 1){
			var filename = json.Obj.name;
			$("#otherFile").val(filename);
		}else{
			alertModel(json.msg);
		}
	}
	uploader2 =webUploadInit("dataPrvOtherId","数据收集","uploadOtherPrvFile?datacollectPrvId="+getParameter("datacollectPrvId")+"",callback2);
}
/**
 * 编辑信息
 * */
function getUpdateParam(){
	var datacollectPrvId = getParameter("datacollectPrvId");	
	$.ajax({
	    url:'queryCollectPrvById',
	    data: {
	    	datacollectPrvId:datacollectPrvId
	    },
 		type : 'post',
	    cache:false,
	    async:true,
	    success:function(result){
	    	if(result!=null){
		    	var datas = result.Obj;
		    	console.log(datas)
	    		$("#datacollectTitle").html(datas.grpDatacollectPrvVO.grpDatacollectVO.datacollectTitle);
		    	var date1 = datas.grpDatacollectPrvVO.grpDatacollectVO.datacollectDate;
		    	$('#datecollectDate').html(date1.substring(0,16));
	    		/*var datacollectDate = datas.grpDatacollectPrvVO.grpDatacollectVO.datacollectDate;   			    		
	    		function add0(m){return m<10?'0'+m:m }
	    		function getTime(ns){
	    			var date = new Date(ns);
	    			//var datTime=date.getFullYear() + '-' + add0((date.getMonth() + 1)) + '-' +add0(date.getDate());
	    			var datTime=date.getFullYear() + '-' + add0((date.getMonth() + 1)) + '-' +add0(date.getDate())+' '+add0(date.getHours())+':'+add0(date.getMinutes());
	    			return datTime;
    			}
	    		var date1=getTime(datacollectDate);
	    		$('#datecollectDate').html(date1);*/
	    		var date2=datas.grpDatacollectPrvVO.grpDatacollectVO.datacollectDeadline;
	    		$('#datacollectDeadline').html(date2.substring(0,16));
	    		/*时间戳转换日期*/
	    		/*var datacollectDeadline = datas.grpDatacollectPrvVO.grpDatacollectVO.datacollectDeadline;    			    		
	    		function add0(m){return m<10?'0'+m:m }
	    		function getTime(ns){
	    			var date = new Date(ns);
	    			//var datTime=date.getFullYear() + '-' + add0((date.getMonth() + 1)) + '-' +add0(date.getDate());
	    			var datTime=date.getFullYear() + '-' + add0((date.getMonth() + 1)) + '-' +add0(date.getDate())+' '+add0(date.getHours())+':'+add0(date.getMinutes());
	    			return datTime;
    			}
	    		var date=getTime(datacollectDeadline);
	    		$('#datacollectDeadline').html(date);*/
	    		$("#datacollectText").html(datas.grpDatacollectPrvVO.grpDatacollectVO.datacollectNote);
	    		
	    		/*编辑驳回信息*/
	    		if(datas.grpDatacollectPrvVO.datacollectGroupOpinion!=null){
	    			$("#rejectInfo").css("display","block");
	    			$("#datacollectGroupOpinion").val(datas.grpDatacollectPrvVO.datacollectGroupOpinion);
	    		}else{
	    			$("#rejectInfo").css("display","none");
	    		}
	    		/*编辑表格*/
	    		var s="";
	    		var item = datas.list;
	    		if(item!=null){
		    		for(var i=0;i<item.length;i++){
		    			s += "<tr><td><input type='hidden' id='id"+i+"' value='"+item[i].datacollecttypePrvId+"'/>"
		    				+ item[i].grpDatacollecttypeVO.datacollecttempType
		    				+ "</td><td>"
		    				+ "<a href='downLoad?path="+item[i].grpDatacollecttypeVO.datacollecttempFilepath+"'>"+item[i].grpDatacollecttypeVO.datacollecttempFilename+"</a>"
		    				+ "</td><td>"
		    				if(item[i].datacollecttypePrvFilename!=null){
		    					s += item[i].datacollecttypePrvFilename;
		    				}else{
		    					s += "-";
		    				}		    				
		    				s += "</td><td><a id='"+item[i].datacollecttypePrvId+"' onclick='collectdataUpload(this)'>【上传】</a>"
		    				+ "</td></tr>";
		    			
				 	}
	    		}
	    		$('#datacollectPrvTb tbody').html(s);
	    		
	    		if(datas.grpDatacollectPrvVO.datacollectPrvOtherfilename!=null){
	    			$("#otherFile").val(datas.grpDatacollectPrvVO.datacollectPrvOtherfilename);
	    		}
	    		if(datas.grpDatacollectPrvVO.datacollectPrvNote!=null){
	    			$("#datacollectPrvNote").val(datas.grpDatacollectPrvVO.datacollectPrvNote);
	    		}
	    		
	    		/*操作日志*/
	    		$("#operationData").css("display","block");
	    		var operat = datas.hisList;
	    		var n="";
	    		if(operat!=null){
					for(var j=0;j<operat.length;j++){
						/*var operatDate = operat[j].hisyoryDate;    			    		
			    		function add0(m){return m<10?'0'+m:m }
			    		function getTime(ns){
			    			var date = new Date(ns);
			    			var datTime=date.getFullYear() + '-' + add0((date.getMonth() + 1)) + '-' +add0(date.getDate())+' '+add0(date.getHours())+':'+add0(date.getMinutes());
			    			return datTime;
			    		}
			    		var date2=getTime(operatDate);*/
			    		var date3=operat[j].hisyoryDate;
			    		
						n += "<tr><td>"
							+	(j+1)								
							n+= "</td><td>" 
							n+= operat[j].historyMsg
								var pathList = operat[j].historyPrvFilePathList;
								if(pathList!=null){
									for(var b=0;b<pathList.length;b++){
										var beforeHref=""+pathList[b]+"";
										var pathListLast=beforeHref.slice(beforeHref.lastIndexOf("/")+1,(beforeHref.indexOf("?")=="-1"?beforeHref.length:beforeHref.indexOf("?")));
										n += "<a href='downLoad?path="+pathList[b]+"' style='display:block;padding-top:3px;'>"+(b+1)+'.'+pathListLast+"</a>";
									}
								}
							n+= "</td><td>"
							+ date3.substring(0,16)								
						    + "</td></tr>";
					}
				}
	    		$('#operationDataTb tbody').html(n);
	    		
	    	}
	    },
	    error:function(){
	    	alertModel("请求失败！");
	    }
	});
};
/**
 * 点击下载
 * */
function downLoadData(obj){
	var path = obj.id;
	var name = obj.innerHTML;
	window.location.href="downLoad?name="+decodeURI(name)+"&path="+decodeURI(path);
}

/**
 * 保存草稿
 * */
function reportFormSubmit(){
	/*var tr = $("#datacollectPrvTb tbody").find("tr");
	for(var i=0;i<tr.length;i++){
		var text =$("#datacollectPrvTb tbody").find("tr").eq(i).find("td").eq(2).text();
		console.log(text);
		if(text==="-"){
			alertModel("数据文件不能为空！");
			return false;
		}
	}*/
	var datacollectPrvNote = $("#datacollectPrvNote").val();
	$.ajax({
	    url:'saveCollectPrv',
	    data: {
	    	datacollectPrvId:getParameter("datacollectPrvId"),
	    	datacollectPrvNote:datacollectPrvNote
	    },
 		type : 'post',
	    cache:false,
	    async:true,
	    success:function(result){
	        //请求成功时
	    	if(result!=null){
    			alertModel(result.msg);
	    	}
	    	window.location.href="reportDataProAgency.html";
	    },
	    error:function(){
	    	alertModel("请求失败！");
	    }
	})
}

/**
 * 保存并上报
 * */
function reportOrUploadFormSubmit(){
	var datacollectPrvNote = $("#datacollectPrvNote").val();
	$.ajax({
	    url:'insertAndSubmit',
	    data: {
	    	datacollectPrvId:getParameter("datacollectPrvId"),
	    	datacollectPrvNote:datacollectPrvNote
	    },
 		type : 'post',
	    cache:false,
	    async:true,
	    success:function(result){
	    	if(result.success==1){
	    		alertModel(result.msg);
		    	window.location.href="reportDataProReport.html";
	    	}else{
	    		alertModel(result.msg);
	    	}
	    },
	    error:function(){
	    	alertModel("请求失败！");
	    }
	})
}

/**
 * 省公司上传文件
 * */
function datacollectPrvOtherFile(){
	webUploadClick("dataPrvOtherId");
	//$(".webuploader-element-invisible").click();
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
 * 表单table中的上传
 * */
function collectdataUpload(tel){
	var obj={"datacollecttypePrvId":tel.id};
	console.log(obj);
	webUploadClick("dataPrvId",uploader,obj);

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
	//javascript:history.back(-1);
	window.location.href="reportDataProAgency.html";
}