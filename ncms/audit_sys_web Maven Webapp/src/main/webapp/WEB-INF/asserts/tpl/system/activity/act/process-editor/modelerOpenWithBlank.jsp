<%@ page import="java.util.*" contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/asserts/tpl/system/activity/act/process-editor/";
String actbasePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/asserts/tpl/system/activity/act";

%>
<!doctype html>
<!--[if lt IE 7]>
<html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>
<html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>
<html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js"> <!--<![endif]-->
<head>
	<base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>加载流程流程编辑器</title>
    <meta name="description" content="">
    <meta name="viewport"
          content="initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, width=device-width">
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

	<script src="editor-app/libs/jquery_1.11.0/jquery.min.js"></script>

	<script type="text/javascript" src="../../../../../js/common.js?v=3"></script>
	<script type="text/javascript">
		$(function(){
		 	 window.open('modeler?modelId='+${modelId}) ; 
		 	 window.location="../../modList.html?v=2";
		});
	</script>
</head>
<body>
</body>
</html>
