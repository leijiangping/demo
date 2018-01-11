$(function(){
	
	$(" #openGuide").on("click",function(){
		var target=0;
		$("#useHelp").css("display","block");
		$("#useHelp #open").css("display","none");
		$("#useHelp .box").css("display","block");
		$("#useHelp .alertList").css("display","block");
	});
	
	// 引导页
	$("#useHelp").css({
	    width:$(window).width(),
	    height:$(window).height(),
	});
	$('#useHelp .all').each(function(){
		var winW=$(window).width();
		var pdW=winW*0.7;
		var imgLen=$(this).find('.ad img').length;
		$(this).find('.ad li').width(pdW);
		$(this).width(pdW*imgLen);
		$(this).find('#arr').width(pdW);
	});
	$(window).on('resize',function(){
		$("#useHelp").css({
		    width:$(window).width(),
		    height:$(window).height(),
		});
		$('#useHelp .all').each(function(){
			var winW=$(window).width();
			var pdW=winW*0.7;
			var imgLen=$(this).find('.ad img').length;
			$(this).find('.ad li').width(pdW);
			$(this).width(pdW*imgLen);
			$(this).find('#arr').width(pdW);
		});
		var winW=$(window).width();
		var pdW=winW*0.7;
		var curIndex=$('.toPage').index(".selected #imgs li");
		var curLeft=-pdW*curIndex+"px";
		$(".selected #imgs").css('left',curLeft);
		lub();tablub();
	})
	

	$("#useHelp .closebtn,#useHelp .quit,#useHelp .over ").on("click",function(){
		$("#useHelp").css("display","none");
	});
	
    $("#useHelp .goinfo").on("click",function(){
        $(this).parent().css("display","none");
        $("#useHelp .box").css("display","block");
    });
	   lub();
	   tablub();
	 // 流程切换
	$(".tab li").on('click',function(){
	    var $this = $(this),
	        index = $this.index();
	         //alert(index);
	    var tabLen=$('.tab li').length;
	     
	    $this.addClass("active").siblings(".tab li").removeClass("active");
	    $(".products .main").eq(index).addClass("selected").siblings(".products .main").removeClass("selected");
	     
	    var uls = $(".tab")[0];
	    var tabW=$('.tab-item').eq(0).width();
	    if(tabLen>4){
	     if(index<4){
	         var target = -(index) * tabW-(24*index);
	         animates(uls, target);
	       }else{
	    	   var target = -3* tabW-72;
		         animates(uls, target);
	       }
	   }
	    $(".selected #imgs").css('left','0px'); 
	    $('#alertBox').hide();
	     lub();
	     
});
	//列表页指向
$(".alert li").on('click',function(){
	debugger;
	    var index = $(this).index(".alert li");
	    var tabLen=$('.tab li').length;
	    var uls = $(".tab")[0];
	    var tabW=$('.tab-item').eq(0).width();
	     if(tabLen>4){
	    	 if(index<4){
		         var target = -(index) * tabW-(20*index);
		         animates(uls, target);
		       }else{
		    	   var target = -3 * tabW-72;
			         animates(uls, target);
		       }
	    	 if(index>5 && tabLen==7){
	    		 var target = -3 * tabW-60;
		         animates(uls, target);
	    	 }
	   }
	     //console.log('target'+target);
	    animates(uls, target);
	    $(".tab li").eq(index).addClass("active").siblings(".tab li").removeClass("active");
	    $(".products .main").eq(index).addClass("selected").siblings(".products .main").removeClass("selected");
	    $(".selected #imgs").css('left','0px'); 
	    $('#alertBox').hide();
	     lub();
});


function lub(){
	var box = $(".selected #box")[0];
	//console.log(box);
	var ad = box.children[0];
	var arr = $(".selected #arr")[0];
	var ul = $(".selected #imgs")[0];
	var lis = ul.children;
	var arrRight = $(".selected #right")[0];
	var arrLeft = $(".selected #left")[0];
	// var imgWidth = ad.offsetWidth;
	var winW=$(window).width();
	var imgWidth=winW*0.7;
	
    //console.log(imgWidth);
	var pic = 0;//用来表示当前应该显示的图片的索引
arrRight.onclick = function () {
		if (pic < lis.length - 1) {
			 pic++;
			 var target = -pic * imgWidth;
			 animate(ul, target);
			 $(".selected #imgs").find('li').eq(pic).addClass('toPage').siblings('li').removeClass('toPage');
			}else if(pic = lis.length - 1){
			 var index=$(arrRight).index('.right');
			 var nextIndex=index+1;
			 var length=$('.right').length;
		     $('#alertBox').show();
		     lub();
	    }
	}
arrLeft.onclick = function () {
	if (pic > 0) {
	 pic--;
	 var target = -pic * imgWidth;
	 animate(ul, target);
	}
}
function animate(obj, target) {
	clearInterval(obj.timer);
	obj.timer = setInterval(function () {
	var leader = obj.offsetLeft;
	var step = 200;
	step = leader < target ? step : -step;
	if (Math.abs(leader - target) > Math.abs(step)) {
	     obj.style.left = leader + step + "px";
	 } else {
	     clearInterval(obj.timer);
	     obj.style.left = target + "px";
	     }
	  }, 25);
   }
}

function tablub(){
	var uls = $(".tab")[0];
    $("#tabR").on('click',function(){
	  var index=$('.tab-item.active').index(".tab li");
	  var tabLen=$('.tab li').length;
	  var tabW=$('.tab-item').eq(0).width();
	  if(tabLen<=4){
		  index=index+1;
		  $(".tab li").eq(index).addClass("active").siblings(".tab li").removeClass("active");
		  $(".products .main").eq(index).addClass("selected").siblings(".products .main").removeClass("selected");
	  }
	  if(index<tabLen-4){
	     index=index+1;
	     var target = -index * tabW-(24*index);
	   $(".tab li").eq(index).addClass("active").siblings(".tab li").removeClass("active");
	   $(".products .main").eq(index).addClass("selected").siblings(".products .main").removeClass("selected");
	   $(".selected #imgs").css('left','0px'); 
	   $('#alertBox').hide();
	   animates(uls, target);
	     lub();
	  }else{
		  index=index+1;
		   $(".tab li").eq(index).addClass("active").siblings(".tab li").removeClass("active");
		   $(".products .main").eq(index).addClass("selected").siblings(".products .main").removeClass("selected");
	  }
});
$("#tabL").on('click',function(){
  // 当前索引所在的位置
  var index=$('.tab-item.active').index(".tab li");
  var tabLen=$('.tab li').length;
  var tabW=$('.tab-item').eq(0).width();
 
  if(index>3){
	   index=index-1;
	   var target = -index * tabW -(24*index);
	   $(".tab li").eq(index).addClass("active").siblings(".tab li").removeClass("active");
	   $(".products .main").eq(index).addClass("selected").siblings(".products .main").removeClass("selected");
	    /* animates(uls, target);
	     lub();*/
	  }else if(index>0){
		  index=index-1;
		   var target = -index * tabW -(24*index);
		   $(".tab li").eq(index).addClass("active").siblings(".tab li").removeClass("active");
		   $(".products .main").eq(index).addClass("selected").siblings(".products .main").removeClass("selected");
		     animates(uls, target);
		     lub();
	  }
   });
}
function animates(obj, target) {
	 clearInterval(obj.timer);
	 obj.timer = setInterval(function () {
	     var leader = obj.offsetLeft;
	     var step = 20;
	     //console.log(leader);
	     step = leader < target ? step : -step;
	     if (Math.abs(leader - target) > Math.abs(step)) {
	         obj.style.left = leader + step + "px";
	     } else {
	         clearInterval(obj.timer);
	         obj.style.left = target + "px";
	    }
	}, 25);
}
  /*判断登陆状态切换引导页  */
  var UserId=window.localStorage.getItem("loginUseid");
  var dictValue=window.localStorage.getItem("logindictValue");
  //获取cookie
 function getCookie(cookie_name){
	    var allcookies = document.cookie;
	    var cookie_pos = allcookies.indexOf(cookie_name);   //索引的长度
	    if (cookie_pos != -1){
	        // 把cookie_pos放在值的开始，只要给值加1即可。
	        cookie_pos += cookie_name.length + 1;      //这里容易出问题，所以请大家参考的时候自己好好研究一下
	        var cookie_end = allcookies.indexOf(";", cookie_pos);
	  
	        if (cookie_end == -1){
	            cookie_end = allcookies.length;
	        }
	        var value = unescape(allcookies.substring(cookie_pos, cookie_end));         //这里就可以得到你想要的cookie的值了。。。
	   }
	    return value;
}
	var flag = getCookie("flag");
	if(flag=='true'){
	 $("#useHelp").css("display","block");
	 }else{
	 $("#useHelp").css("display","none");
   }
});
