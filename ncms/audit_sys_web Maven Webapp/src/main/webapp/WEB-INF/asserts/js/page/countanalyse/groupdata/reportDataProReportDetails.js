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
		    	var date1=datas.grpDatacollectPrvVO.grpDatacollectVO.datacollectDate;
	    		$('#datacollectDate').html(date1.substring(0,16));
		    	/*var datacollectDate = datas.grpDatacollectPrvVO.grpDatacollectVO.datacollectDate;   			    		
	    		function add0(m){return m<10?'0'+m:m }
	    		function getTime(ns){
	    			var date = new Date(ns);
	    			//var datTime=date.getFullYear() + '-' + add0((date.getMonth() + 1)) + '-' +add0(date.getDate());
	    			var datTime=date.getFullYear() + '-' + add0((date.getMonth() + 1)) + '-' +add0(date.getDate())+' '+add0(date.getHours())+':'+add0(date.getMinutes());
	    			return datTime;
    			}
	    		var date1=getTime(datacollectDate);
	    		$('#datacollectDate').html(date1);*/
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
	    		console.log(item)
	    		if(item!=null){
		    		for(var i=0;i<item.length;i++){
		    			s += "<tr><td><input type='hidden' id='id"+i+"' value='"+item[i].datacollecttypePrvId+"'/>"
		    				+ item[i].grpDatacollecttypeVO.datacollecttempType
		    				+ "</td><td>"
		    				//+ "<a href='"+encodeURI(path)+"'>"+item[i].grpDatacollecttypeVO.datacollecttempFilename+"</a>"
		    				+ "<a href='downLoad?path="+item[i].grpDatacollecttypeVO.datacollecttempFilepath+"'>"+item[i].grpDatacollecttypeVO.datacollecttempFilename+"</a>"
		    				//+ "<span id='"+item[i].grpDatacollecttypeVO.datacollecttempFilepath+"' onclick='downLoadData(this)' style='cursor:pointer;color:#1E9FFF'>"+item[i].grpDatacollecttypeVO.datacollecttempFilename+"</span>"
		    				+ "</td><td>"
		    				if(item[i].datacollecttypePrvFilename != null){
		    					//s+= "<span id='"+item[i].datacollecttypePrvFilepath+"' onclick='downLoadData(this)' style='cursor:pointer;color:#1E9FFF'>"+item[i].datacollecttypePrvFilename+"</span>"
		    					s+= "<a href='downLoad?path="+item[i].datacollecttypePrvFilepath+"'>"+item[i].datacollecttypePrvFilename+"</a>";
		    					//s+= "<a href='"+encodeURI(pathType)+"'>"+item[i].datacollecttypePrvFilename+"</a>"
		    				}else{
		    					s+= "-"
		    				}
		    				s+= "</td></tr>";
			    		
				 	}
	    		}
	    		$('#datacollectPrvTb tbody').html(s);
	    		/*编辑其他文件*/
	    		var t = "";
	    		var pathOther = datas.path+datas.grpDatacollectPrvVO.datacollectPrvOtherfilepath;
	    		//t += "<a href='"+encodeURI(pathOther)+"'>"+datas.grpDatacollectPrvVO.datacollectPrvOtherfilename+"</a>"
	    		if(datas.grpDatacollectPrvVO.datacollectPrvOtherfilename!=null){
	    			//t += "<span id='"+datas.grpDatacollectPrvVO.datacollectPrvOtherfilepath+"' onclick='downLoadData(this)' style='cursor:pointer;color:#1E9FFF'>"+datas.grpDatacollectPrvVO.datacollectPrvOtherfilename+"</span>"	    		
	    			t += "<a href='downLoad?path="+datas.grpDatacollectPrvVO.datacollectPrvOtherfilepath+"'>"+datas.grpDatacollectPrvVO.datacollectPrvOtherfilename+"</a>";
	    		}else{
	    			t += "-"
	    		}
	    		$('#prvOtherFile').html(t);
	    		$("#datacollectPrvNote").val(datas.grpDatacollectPrvVO.datacollectPrvNote);
	    		
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
			    			var datTime=date.getFullYear() + '-' + add0((date.getMonth() + 1)) + '-' +add0(date.getDate())+' '+add0(date.getHours())+':'+add0(date.getMinutes());;
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


/*function reportFormSubmit(){
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
	    	window.location.href="reportDataPro.html";
	    },
	    error:function(){
	    	alertModel("请求失败！");
	    }
	})
}*/

