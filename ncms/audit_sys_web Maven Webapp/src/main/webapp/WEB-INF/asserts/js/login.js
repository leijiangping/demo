
$(document).ready(function() {
	loadGroup();
	calculate();
	//获去cookie中的参数  填入input输入框
	var str = getCookie("loginInfo");
	if(str != null && str != undefined){
		if(str.length>0){
			var userLoginname = str.split(",")[0];
			var userPassword = str.split(",")[1];
			//自动填充用户名和密码
			$("#userLoginname").val(userLoginname);
			$("#userPassword").val("123456");
			$("#realPsw").val(userPassword);
			$("#remFlag").attr("checked",'checked');
		}
	}
	//如果输入框内容改变  清除多选框选择
	/*$("#userLoginname").keyup(function(){
		 $("#remFlag").removeAttr('checked');
	});*/
	//同步隐藏的userpassword
	$("#userPassword").keyup(function(){
		var userPassword=$('#userPassword').val();
		if(userPassword!=null&&userPassword!=""){
			$('#realPsw').val(userPassword);
		}
	});
	
	$("#userLoginname").focus(function(){
		$(this).css({"borderColor":"#0085d0"});
		$(this).prev().removeClass("use");
		$(this).prev().addClass("useing");
	})
	$("#userLoginname").blur(function(){
		$(this).css({"borderColor":"#c5c5c5"});
		$(this).prev().removeClass("useing");
		$(this).prev().addClass("use");
		
    })
    $("#userPassword").focus(function(){
		$(this).css({"borderColor":"#0085d0"});
		$(this).prev().removeClass("psw");
		$(this).prev().addClass("pswing");
		
    }) 
    $("#userPassword").blur(function(){
		$(this).css({"borderColor":"#c5c5c5"});
		$(this).prev().removeClass("pswing");
		$(this).prev().addClass("psw");
		
    })
    
    $("#check").focus(function(){
    	$(this).css({"borderColor":"#0085d0"});
    });
    $("#check").blur(function(){
    	$(this).css({"borderColor":"#c5c5c5"});
    });
    
    $(document).bind('click',function(){
        $('#areaList').css('display','none');
    });
    $('#area').bind('click',function(e){
    	$(this).children("#areaList").css({'display':'block','transition':'1s'});
      	$('#areaFor .area_item').each(function(){
      		var spanH=$(this).children('.province').height();
      		$(this).children('.center_line').css('height',spanH);
      		$(this).children('.district').css({'height':spanH,'line-height':spanH+'px'});
      	});  
      	e.stopPropagation();//调用停止冒泡方法,阻止document方法的执行
    });
    $("#areaFor").click(function(event){
    	$("#areaList").hide();
    	event.stopPropagation();
    });
    $(window).resize(function () {
    	calculate();
    })
  
    //记住密码功能	
	$("#remFlag").click(function() {
	  	var remFlag = $("input[type='checkbox']").is(':checked');
	  	if (remFlag == true) { //如果选中设置remFlag为1
	  		//cookie存用户名和密码,回显的是真实的用户名和密码,存在安全问题.
	  		var conFlag = confirm("记录密码功能不宜在公共场所(如网吧等)使用,以防密码泄露.您确定要使用此功能吗?");
	  		if (conFlag) { //确认标志
	  			$("#remFlag").val("1");
	  		} else {
	  			$("input[name='remFlag']").attr('checked',false);
	  			$("#remFlag").val("");
	  		}
	  	} 
	});
	/*if(){
		
	}*/
	
	//alert(window.localStorage.getItem("loginUseid"));
	if(window.localStorage.getItem("loginUseid")==null){
		//资源预加载
	    preload([
	 			'asserts/echart/echarts.min.js'
	 			,'asserts/image/help/2-1.png'
	            ,'asserts/image/help/2-2.png'
	            ,'asserts/image/help/2-3.png'
	            ,'asserts/image/help/2-4.png'
				,'asserts/image/help/2-5.png'
				,'asserts/image/help/2-6.png'
				,'asserts/image/help/2-7.png'
				,'asserts/image/help/2-8.png'
				,'asserts/image/help/2-9.png'
				,'asserts/image/help/2-10.png'
				,'asserts/image/help/2-11.png'
				,'asserts/image/help/2-12.png'
				,'asserts/image/help/2-13.png'
				,'asserts/image/help/2-14.png'
				,'asserts/image/help/2-15.png'
				,'asserts/image/help/lc16.png'
				,'asserts/image/help/lc17.png'
				,'asserts/image/help/lc18.png'
				,'asserts/image/help/lc19.png'
				,'asserts/image/help/lc20.png'
				,'asserts/image/help/lc21.png'
				,'asserts/image/help/lc22.png'
				,'asserts/image/help/lc23.png'
				,'asserts/image/help/lc24.png'
				,'asserts/image/help/lc25.png'
				,'asserts/image/help/lc26.png'
				,'asserts/image/help/lc27.png'
				,'asserts/image/help/lc28.png'
				,'asserts/image/help/lc29.png'
				,'asserts/image/help/lc30.png'
				,'asserts/image/help/lc31.png'
				,'asserts/image/help/lc32.png'
				,'asserts/image/help/lc33.png'
		]);
	}
	function preload(files){
	    //如果传入的参数files不是数组，返回
	    if(Object.prototype.toString.call(files) !== "[object Array]"){return;}
	    var obj = null,
	        ie  = '\v'=='v';

	    for(var i = 0, l = files.length; i < l; i ++){
	        if (ie) {
	            new Image().src = files[i];
	            continue;
	        }
	        obj = document.createElement('object');
	        obj.data = files[i];
	        obj.width  = 0;
	        obj.height = 0;
	        document.body.appendChild(obj);
	         
	    }
	}
});
function getP(prvFlag,prvSname,prvCode){
	$('#area_name').html(prvSname);
	$('#prvSname').val(prvSname);
	$('#prvFlag').val(prvFlag);
	$('#prvCode').val(prvCode);
	$("#areaList").css('display','none');
}
function calculate(){
	var w=$(window).width(),
	h=$(window).height();
	$('#login-bg').css({'width':w,'height':h});
}
		
/**
 * 登陆提交form表单,判断用户名  密码 不能为空
 */
function formSubmit(){
	if($("#userLoginname").val()=="" || $("#userPassword").val()==""){
		$("#loginMsg").html("用户名或者密码不能为空!");
		$("#loginMsg").css("color","red");
		setTimeout(function(){
			$("#loginMsg").html("");
		},2000);
		return false;
	}
	
	 $("#login").attr("disabled","true"); //设置变灰按钮  
	var data = $("#loginSubmit").serialize();
	var submitData = decodeURIComponent(data,true);
	 $.ajax({
	    url:'login',
	    data: submitData,
 		type : 'post',
	    cache:false,
	    async:true,
	    success:function(result){
			$("#login").attr("disabled","true"); //设置变灰按钮  
			$("#login").css("background","#ccc");
			setTimeout(function(){
				
			  	$('#login').removeAttr('disabled');
			  	$("#login").css("background","#0085d0");
			  },3000); //设置三秒后提交按钮 显示   
	        //请求成功时
	    	if(result!=null){
	    		if (result.success == "1") {
	    			var flag;
	    			var userId = result.obj.userId;//返回
	    			var dictValue = result.obj.dictValue;
	    			
	    			var locUserid=window.localStorage.getItem("loginUseid");
	    			var locdictValue=window.localStorage.getItem("logindictValue");
	    			//保存选中的省份
    				window.localStorage.setItem("prvSname",$('#area_name').text());
    				window.localStorage.setItem("prvFlag",$('#prvFlag').val());
    				window.localStorage.setItem("prvCode",$('#prvCode').val());
	    			if(userId==locUserid && locdictValue==dictValue ){
	    				/*window.localStorage.setItem("loginUseid",userId);
	    				window.localStorage.setItem("logindictValue",dictValue);*/
	    				flag=false;//影藏
	    			}else{
	    				flag=true;//显示
	    				window.localStorage.setItem("loginUseid",userId);
	    				window.localStorage.setItem("logindictValue",dictValue);
	    			}
	    			setCookie("flag",flag);
					window.location.href = "welcome";
				}else{
					$("#verification").html(result.msg);
					$("#verification").css("color","red");
					setTimeout(function(){
						$("#verification").html("");
					},2000);
					
				}
	    	}
	    },
	    error:function(msg){
			$("#login").attr("disabled","true"); //设置变灰按钮  
			$("#login").css("background","#ccc");
			setTimeout(function(){
			  	$('#login').removeAttr('disabled');
			  	$("#login").css("background","#0085d0");
			  },3000); //设置三秒后提交按钮 显示   
			$("#loginMsg").html("网络异常："+msg);
			$("#loginMsg").css("color","red");
			setTimeout(function(){
				$("#loginMsg").html("");
			},2000);
	    }
	});  
}
$(document).keydown(function(event){ 
	if(event.keyCode==13){ 
		formSubmit();
      } 
})

function loadGroup(){
	var prvSname=window.localStorage.getItem("prvSname");
	var prvFlag=window.localStorage.getItem("prvFlag");
	var prvCode=window.localStorage.getItem("prvCode");
	if(prvSname&&prvFlag){//如果已经存在的。则显示保存的
		getP(prvFlag,prvSname,prvCode);
	}
	var lists=JSON.parse(prvGroupJson);
	var html="";
	if(!prvSname&&!prvFlag){//如果没有保存的。则默认显示第一个省份
		getP(lists[0].sysProvince[0].prvFlag,lists[0].sysProvince[0].prvSname,lists[0].sysProvince[0].prvCode);
	}
	$.each(lists, function (i, item){
		var str="";
		$.each(item.sysProvince,function(j,itemchild){
			str+='<span onclick="getP(\''+itemchild.prvFlag+'\',\''+itemchild.prvSname+'\',\''+itemchild.prvCode+'\');">'+itemchild.prvSname+'</span>';
		});
		html+='<div class="area_item clear"><div class="district">'+item.prvGroup+'</div><div class="center_line"></div><div class="province">'+str+'</div></div>';
		
	});
	$('#areaFor').append(html);
}
function changeImg(){
	var img = document.getElementById("img"); 
	img.src = "imageGen?date=" + new Date();
} 
function resetKey(){
	var userPassword=$('#userPassword').val();
	if(userPassword!=null&&userPassword!=""){
		$('input[name="userPassword"]').val(userPassword);
	}
}