<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String prvGroupJson="";
	if(request.getSession().getAttribute("prvGroupJson")!=null){
	 prvGroupJson=request.getSession().getAttribute("prvGroupJson").toString();
	}
%>
<!DOCTYPE html>
<html>
  <head>
	<base href="<%=basePath%>">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
    <title>中国移动电费租费管理系统</title>  
	<link rel="stylesheet" href="asserts/css/ncms.css">
	
  </head>
  <body id="login-bg">
  <div class="login-logo"></div>
  <div style="height:380px;width:1200px;margin:0 auto;position:relative;top: 50%;margin-top: -190px;">
  <!-- 	<div class="arc_img"></div> -->
  	<div class="login-box">
		<div class="header">
			<h1>欢迎登录</h1>
		</div>
		<div class="login-main">
			 <form class="form-horizontal" role="form" form action="login.action" id="loginSubmit" method="post">
    			<input name="__RequestVerificationToken" type="hidden" value="">
    			<div class="form-group" style="position:relative;">
        			<label for="firstname"></label>
        			<div class="box_item" style="position:relative;">
        				<i class="iconfont login-icon use"></i>
            			<input type="text" class="form-control"  id="userLoginname" name="userLoginname"
            				placeholder="用户名">
            			<input type="hidden" name="prvFlag" id="prvFlag" value=""/>	
            			<input type="hidden" name="prvState" id="prvState" value=""/>	
            			<input type="hidden" name="prvCode" id="prvCode" value=""/>
            			<input type="hidden" name="prvSname" id="prvSname" value=""/>
            			<span id="loginMsg" style="position:absolute;top:42px;left:35px;"></span>
        				<div id="area" class="clear">
	        				<div id="area_name"></div>
	        				<div id="locate_icon"></div>
	        				<div id="areaList" class="test2">
	        					<div style="margin:0px 10px;" id="areaFor"></div>
        					</div>
        				</div>
        			</div>
    			</div>
    			<div class="form-group">
        			<label for="lastname"></label>
        			<div class="box_item">
        			<i class="iconfont login-icon psw"></i>
            			<input type="password" class="form-control" id="userPassword" 
            			 placeholder="请输入密码" onblur="resetKey();"/>
            			<input type="hidden" name="userPassword" id="realPsw"/>
        			</div>
    			</div>
    			<div class="form-group">
        			<label for="lastname"></label>
        			<div class="box_item" style="position:relative;">
            			<input type="text" maxlength="4" class="form-control" id="check" 
            			name="verifyCode" placeholder="请输入验证码">
            			<span class="send">
            				<img id="img" src="imageGen" onclick="changeImg()">  
            			</span>
            			<span id="verification" style="position:absolute;top:45px;left:35px;"></span>
        			</div>
    			</div>
    		
    			<div class="form-group">
        			<div>
            			<button type="button" id="login" class="btn btn-default login_button" onclick="formSubmit()">登录</button>
        			</div>
    			</div>
    				<div class="form-group remember">
        			<div>
            			<div class="checkbox">
                			<label class="col_specail">
                    			<input type="checkbox" id="remFlag" name="remFlag" value="1"> 记住密码
                			</label>
                			<a class="a_specil">忘记密码？</a>
            			</div>
        			</div>
   				</div>
			</form>		
		</div>
	</div>
  </div>
  
	
	<div class="copyright">
			<ul>
				<li>官网网站:www.10086.cn</li>
				<li>语音自助服务:10086</li>
				<li>短信服务:10086</li>
			</ul>
		<p>CopyRight@1999-2017. 中国移动 版权所有</p>
		</div>	 
	<script type="text/javascript" src="asserts/lib/jquery.js"></script>
	<script type="text/javascript" src="asserts/bootstrap/js/bootstrap.min.js"></script>
	<!--[if lte IE 9]>
	    <script src="asserts/bootstrap/js/html5shiv.js"></script>
	    <script src="asserts/bootstrap/js/respond.min.js"></script>
	    <script src="asserts/bootstrap/js/jquery.placeholder.min.js"></script>
    <![endif]-->
    <script src="asserts/js/common.js"></script>
     <script type="text/javascript">
		var prvGroupJson='<%=prvGroupJson%>';
	</script>
	<script src="asserts/js/login.js" type="text/javascript"></script>
	
</body>
</html>