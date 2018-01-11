//已显示表格list
var showTableList = null;
$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	curPageNum = 1;
	// 编辑信息
	getUpdateParam();
	//全局设置加载中的效果
	$.ajaxSetup({
		 beforeSend: function () {
		    $("#loading").html("<img src=\""+projectName+"/asserts/image/loading-b.gif\"/>");
		 },
		 complete: function () {
		    $("#loading").html("");
		},
		error: function () {
		    $("#loading").html("");
		}
	});
}

/**
 * 编辑信息
 * */
function getUpdateParam(){
	var datacollectId = getParameter("datacollectId");		
	$.ajax({
	    url:'queryGrpPrvCollectById',
	    data: {
	    	datacollectId:datacollectId
	    },
 		type : 'post',
	    cache:false,
	    async:true,
	    success:function(result){
	    	if(result!=null){
		    	var datas = result.Obj;
	    		$('#datacollectTitle').html(datas.grpDatacollectVO.datacollectTitle);
	    		$('#datacollectNote').html(datas.grpDatacollectVO.datacollectNote);
	    		var date1=datas.grpDatacollectVO.datacollectDate;
	    		$('#datacollectDate').html(date1.substring(0,16));
	    		
	    		/*var datacollectDate = datas.grpDatacollectVO.datacollectDate;    			    		
	    		function add0(m){return m<10?'0'+m:m }
	    		function getTime(ns){
	    			var date = new Date(ns);
	    			//var datTime=date.getFullYear() + '-' + add0((date.getMonth() + 1)) + '-' +add0(date.getDate());
	    			var datTime=date.getFullYear() + '-' + add0((date.getMonth() + 1)) + '-' +add0(date.getDate())+' '+add0(date.getHours())+':'+add0(date.getMinutes());
	    			return datTime;
    			}
	    		var date1=getTime(datacollectDate);
	    		$('#datacollectDate').html(date1);*/
	    		var date2=datas.grpDatacollectVO.datacollectDeadline;
	    		$('#datacollectDeadline').html(date2.substring(0,16));
	    		/*var datacollectDeadline = datas.grpDatacollectVO.datacollectDeadline;    			    		
	    		function add0(m){return m<10?'0'+m:m }
	    		function getTime(ns){
	    			var date = new Date(ns);
	    			//var datTime=date.getFullYear() + '-' + add0((date.getMonth() + 1)) + '-' +add0(date.getDate());
	    			var datTime=date.getFullYear() + '-' + add0((date.getMonth() + 1)) + '-' +add0(date.getDate())+' '+add0(date.getHours())+':'+add0(date.getMinutes());
	    			return datTime;
    			}
	    		var date=getTime(datacollectDeadline);
	    		$('#datacollectDeadline').html(date);*/
	    		/*编辑上报状态的省份*/
	    		var html='';
	    		html+='<div class="distribute-item">';
	    			var item = datas.downPrvId;
	    			for(var i=0;i<item.length;i++){
			    		html+='<span id="'+item[i].prvId+'" onclick="loadTableData(this)">';
		    				html+='<i>&#10004</i>'+item[i].prvName+'';
			    		html+='</span>';
					 }
	    		html+='</div>';
	    		$('#distribute').append(html);
	    		$('.distribute-item span').click(function(){
	    	        if ($(this).hasClass('distribute-style')) {
	    	            return true;
	    	        } else {
	    	            $('.distribute-item span').removeClass('distribute-style');
	    	            $(this).addClass('distribute-style');
	    	        }
	    	    });
	    		var upPrvId = datas.upPrvId;//勾选的省份ID
	    		var downPrvId = datas.downPrvId;//全部的省份ID
	    		for(var s=0;s<downPrvId.length;s++){
    				var downStr = downPrvId[s].prvId;
    				for(var i=0;i<upPrvId.length;i++){
    					var upStr = upPrvId[i];
    					if(downStr==upStr){
    						$("#"+downStr).find("i").css("display","inline");
    					}
    				}
    			}
	    		$("#distributePrvs").val(datas.upPrvId);
	    		$("#datacollectIds").val(getParameter("datacollectId"));
	    		/*编辑表格*/
	    		var s="";
	    		var item = datas.GrpDatacollecttypeList;
	    		if(item!=null){
					for(var i=0;i<item.length;i++){
						 s += "<tr><td>"
							+ item[i].dict_module
							+ "</td><td>"
							+ item[i].dict_wenjian
							+ "</td></tr>";
					}
				}
				$('#datacollectExamineTb tbody').html(s);
	    		
	    	}
	    },
	    error:function(){
	    	alertModel("请求失败！");
	    }
	});
};
/**
 * 点击省份加载table的数据
 * */
function loadTableData(obj){
	var datacollectId = getParameter("datacollectId");
	var prvId = obj.id;
	$.ajax({
	    url:'queryEveryPrvMsg',
	    data: {
	    	datacollectId:datacollectId,
	    	prvId:prvId
	    },
 		type : 'post',
	    cache:false,
	    async:true,
	    success:function(result){
	    	if(result!=null){
	    		var datas = result.Obj;
	    		console.log(datas)
	    		if(datas!=null){
		    		var s="";//编辑表格
		    		var item = datas.list;
		    		if(item!=null){
						for(var i=0;i<item.length;i++){
							 s += "<tr><td>"
								+ item[i].grpDatacollecttypeVO.datacollecttempType
								+ "</td><td>"
								//+ "<a href='"+encodeURI(path)+"'>"+item[i].datacollecttypePrvFilename+"</a>"							
								if(item[i].datacollecttypePrvFilename!=null){
									s+= "<a href='downLoad?path="+item[i].datacollecttypePrvFilepath+"'>"+item[i].datacollecttypePrvFilename+"</a>";
									//s += "<a href='"+encodeURI(path)+"' target='_blank' download='"+encodeURI(path)+"' >"+item[i].datacollecttypePrvFilename+"</a>"
									//s+= "<span id='"+item[i].datacollecttypePrvFilepath+"' onclick='downLoadData(this)' style='cursor:pointer;color:#1E9FFF'>"+item[i].datacollecttypePrvFilename+"</span>"
								}else{
									s += "-"
								}
							    + "</td></tr>";
						}
					}
					$('#datacollectExamineTb tbody').html(s);
					if(datas.grpDatacollectPrvVO.datacollectPrvState==1||datas.grpDatacollectPrvVO.datacollectPrvState==11){
		    			$("#examineTbData").css("display","block");
		    		}else{
		    			$("#examineTbData").css("display","none");
		    		}
					
		    		var str = datas.grpDatacollectPrvVO;
		    		if(str != null){
		    			if(str.datacollectPrvState==11||str.datacollectPrvState==1){
			    			$("#OtherData").css("display","block");
			    		}else{
				    		$("#OtherData").css("display","none");			    			
			    		}
		    			$("#prvData").css("display","block");
		    			$("#notePrvData").html(str.datacollectPrvNote);//备注的内容
		    			var r="";//编辑其他文件
						r += "<tr><td>"
							/*+ str.datacollectPrvOtherfilename*/
							if(str.datacollectPrvOtherfilename!=null){
								r += str.datacollectPrvOtherfilename
							r += "</td><td>"
								//r += "<a href='"+encodeURI(otherPath)+"'>"+str.datacollectPrvOtherfilename+"</a>"
								//r += "<span id='"+str.datacollectPrvOtherfilepath+"' onclick='downLoadData(this)' style='cursor:pointer;color:#1E9FFF'>"+str.datacollectPrvOtherfilename+"</span>" 
								r += "<a href='downLoad?path="+str.datacollectPrvOtherfilepath+"'>"+str.datacollectPrvOtherfilename+"</a>";
							r += "</td></tr>";
							}else{
								 r += "-"
							 r += "</td><td>"
								 r += "-"
							 r += "</td></tr>";		 
							}
						
			    		$('#datacollectOtherTb tbody').html(r);
			    		if(str.datacollectPrvState == 1){
			    			$("#opinionData").css("display","block");
			    		}else{
				    		$("#opinionData").css("display","none");
			    		}
			    		/*驳回*/
			    		var m="";
			    		m += '<div class="col-md-2 report-item">集团意见：</div>'
			    		   + '<div class="col-md-10 report-mm" id="collectionOpinionNews">'
			    		   		if(str.datacollectGroupOpinion!=null){
			    		   			m += '<textarea rows="4" class="form-control" id="collectionOpinion" name="collectionOpinion" style="display:block">'+str.datacollectGroupOpinion+'</textarea>'
			    		   		}else{
			    		   			m += '<textarea rows="4" class="form-control" id="collectionOpinion" name="collectionOpinion" style="display:block"></textarea>'
			    		   		}
			    				//+ '<textarea rows="4" class="form-control" id="collectionOpinion" name="collectionOpinion" style="display:block">'+str.datacollectGroupOpinion+'</textarea>'
			    				m += '<button type="button" class="btn btn-primary" value="'+prvId+'" id="collectionOpinionReject">驳回</button>'
			    		   + '</div>';
			    		$('#opinionData').html(m);
			    		
			    		if(str.datacollectPrvState==-1||str.datacollectPrvState==0){
			    			$("#collectionOpinionReject").css("display","none");
			    		}
			    		
			    		$('#collectionOpinionReject').click(function(){
			    			if($("#collectionOpinion").val()==""){
			    				alertModel("请填写集团意见！");
			    				return false;
			    			}
			    			confirmModel("您确定要驳回吗？","collectionOpinionRejectOperation");
			    		})
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
					    		}*/
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
		    		}else{
		    			$("#prvData").css("display","none");
			    		$("#OtherData").css("display","none");
			    		$("#opinionData").css("display","none");
			    		$("#operationData").css("display","none");
			    		$('#datacollectOtherTb tbody').html("");
		    		}

	    		}	    		
	    	}
	    },
	    error:function(){
	    	alertModel("请求失败！");
	    }
	});
}
/**
 * 点击下载
 * */
function downLoadData(obj){
	var path = obj.id;
	var name = obj.innerHTML;
	window.location.href="downLoad?name="+decodeURI(name)+"&path="+decodeURI(path);
}

function batchDownload(){
	var datacollectId = getParameter("datacollectId");
	var distributePrvIds = $("#distributePrvs").val();
	console.log(distributePrvIds)
	//<a href='uploadZip?datacollectId'+datacollectId+'&distributePrvIds='+distributePrvIds target='_blank' >
	window.location.href="uploadZip?datacollectId="+datacollectId+"&distributePrvIds="+distributePrvIds;
}
/**
 * 驳回
 */
function collectionOpinionRejectOperation(){
	var prvId = $('#collectionOpinionReject').val();
	var datacollectId = getParameter("datacollectId");
	var collectionOpinion = $("#collectionOpinion").val();
	$.ajax({
	    url:'updateStateReject',
	    data: {
	    	prvId:prvId,
	    	datacollectId:datacollectId,
	    	datacollectGroupOpinion:collectionOpinion
	    },
 		type : 'post',
	    cache:false,
	    async:true,
	    success:function(result){
	    	alertModel(result.msg);
	    	window.location.href="collectionDataDistribute.html";
	    },
	    error:function(){
	    	alertModel("请求失败！");
	    }
	});

}
/*完结工单*/
function closeWorkOrder(){
	var datacollectId = getParameter("datacollectId");
	$.ajax({
	    url:'updateStateToFinish',
	    data: {
	    	datacollectId:datacollectId
	    },
 		type : 'post',
	    cache:false,
	    async:true,
	    success:function(result){
	    	alertModel(result.msg);
	    	window.location.href="collectionDataDistribute.html";
	    },
	    error:function(){
	    	alertModel("请求失败！");
	    }
	});

}