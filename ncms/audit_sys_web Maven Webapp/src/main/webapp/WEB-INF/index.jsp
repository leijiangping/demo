﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
  <head>
 	<%@ page isELIgnored="false" %>
	<base href="<%=basePath%>">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="renderer" content="webkit">
    <title>中国移动电费租费管理系统</title>
	<link rel="stylesheet" href="asserts/bootstrap/css/bootstrap.min.css" media="screen">
	<link rel="stylesheet" href="asserts/css/ncms.css"/>
	<style>
	  .wrap{
		  width:120px;
		  height: 640px;
		  overflow: hidden;
		  position: absolute;
		  z-index:1000;
		  top: 80px;
		  left:-18px;
	}
	 .menu_l{
	  	position: absolute;
	    width: 120px;
	    height: 640px;
	    margin: 0px 18px;
	    overflow-x: hidden;
	    overflow-y: auto;
	 }
	 .menu-item-child .sign img{
		  width:22px;
		  height:22px;
	 }
	  .menu-item-child .level3 img{
		  width:22px;
		  height:22px;
	 }
  	</style>
</head>

  <body class='index'>

   
	<div class="layout-admin">
		<div class="layout-header">
			<div class="logo" title="中国移动"></div>
			<div class="layout-side-arrow"></div>
			<div class="locationImg"></div><span id="prvname" >${prvName}</span>
			<ul class="header-bar">
				<li id="taskagents">
					<div class="agency first">
						<div id="agencyNum">-</div>
					</div>
					<div class="message">待办任务</div>
				</li>
				<li id="guideIcon">
					<div class="guideIcon first" id="openGuide"></div>
					<div class="message">引导</div>
				</li>
				<li id="switchUser">
					<div class="changeAccount first"></div>
					<div class="message">切换账号</div>
				</li>
				<li id="userInfo">
					<div class="userPhoto first"></div>
					<div class="message">个人中心</div>
				</li>
				<li id="exitSystem">
					<div class="exit first"></div>
					<div class="message">退出系统</div>
				</li>
			</ul>
		</div>
		<!-- 个人信息弹窗 -->
		<div class="modal hide fade" id="EditPanel" tabindex="-1">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button class="close" type="button" data-dismiss="modal">×</button>
						<h4 id="myModalLabel" style="font-size:18px;">个人信息</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" id="dataForm" style="text-align:center;">
							<div class="form-group">
				    			<label class="control-label" style="display:inline-block;width:80px;text-align: right;">登录名称:</label>
							    <input type="text" id="login_name" name="login_name" style="width:170px;padding: 4px 5px;" disabled="disabled">
				  			</div>
				  			<div class="form-group">
				    			<label class="control-label" style="display:inline-block;width:80px;text-align: right;">用户邮箱:</label>
							    <input type="text" id="user_email" name="user_email" style="width:170px;padding: 4px 5px;">
							    <input type="hidden" id="user_id" name="user_id">
				  			</div>
				  			<div class="form-group">
				    			<label class="control-label" style="display:inline-block;width:80px;text-align: right;">手机号:</label>
							    <input type="text" id="user_phone" name="user_phone"style="width:170px;padding: 4px 5px;">
				  			</div>
				  			<div class="form-group">
				    			<label class="control-label" style="display:inline-block;width:80px;text-align: right;">修改密码:</label>
							   	<input type="button" id="updatePsw" style="width: 170px;height:31px;" value="点击修改密码"/>
				  			</div>
						</form>
					</div>
					<div class="modal-footer">
						<a href="javascript:void(0)" class="btn" data-dismiss="modal" style="color:#333;">关闭</a>
						<a href="javascript:void(0)" class="btn btn-primary" onclick="formSubmit();">确定修改</a>
					</div>
				</div>
			</div>
		</div>
		<!-- 个人信息弹窗 end -->
		<!-- 切换账号 弹窗 -->
		<div class="modal hide fade" id="EditPanelAccount" tabindex="-1">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button class="close" type="button" data-dismiss="modal">×</button>
						<h4 id="myModalLabel" style="font-size:18px;">切换账号</h4>
					</div>
					<div class="modal-body">
						<p>确定切换用户？</p>
					</div>
					<div class="modal-footer">
						<a href="javascript:void(0)" class="btn" data-dismiss="modal" style="color:#333;">关闭</a>
						<a href="javascript:void(0)" class="btn btn-primary" onclick="formAccount();">确定</a>
					</div>
				</div>
			</div>
		</div>
		<!-- 切换账号 end -->
		<!-- 退出系统 弹窗 -->
		<div class="modal hide fade" id="EditPanelExit" tabindex="-1">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button class="close" type="button" data-dismiss="modal">×</button>
						<h4 id="myModalLabel" style="font-size:18px;">退出系统</h4>
					</div>
					<div class="modal-body">
						<p>确定要退出系统？</p>
					</div>
					<div class="modal-footer">
						<a href="javascript:void(0)" class="btn" data-dismiss="modal" style="color:#333;">关闭</a>
						<a href="javascript:void(0)" class="btn btn-primary" onclick="formAccount();">确定</a>
					</div>
				</div>
			</div>
		</div>
		<!-- 退出系统 end -->
		<!-- 修改密码弹窗 -->
		<div class="modal hide fade" id="EditPanelPsw" tabindex="-1" style="z-index:1100;">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button class="close" type="button" data-dismiss="modal">×</button>
						<h4 id="myModalLabel" style="font-size:18px;">修改密码</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal" id="dataForm" style="text-align:center;">
						<div class="form-group">
				    			<label class="control-label" style="display:inline-block;width:80px;text-align: right;">旧密码:</label>
							    <input type="password" id="oldPsw"  name="oldPsw" placeholder="请输旧密码" style="width:170px;padding: 4px 5px;">
				  			</div>
							<div class="form-group">
				    			<label class="control-label" style="display:inline-block;width:80px;text-align: right;">新密码:</label>
							    <input type="password" id="newPsw" name="newPsw" minlength ="6" placeholder="请输入新密码" style="width:170px;padding: 4px 5px;">
				  			</div>
				  			<div class="form-group">
				    			<label class="control-label" style="display:inline-block;width:80px;text-align: right;">确认新密码:</label>
							    <input type="password" id="newPsw1" name="newPsw1" minlength ="6" placeholder="请输入新密码" style="width:170px;padding: 4px 5px;">
				  			</div>
						</form>
					</div>
					<div class="modal-footer">
						<a href="javascript:void(0)" class="btn" data-dismiss="modal" style="color:#333;">关闭</a>
						<a href="javascript:void(0)" class="btn btn-primary" onclick="formSubmitPsw();">保存</a>
					</div>
				</div>
			</div>
		</div>
		
		<!-- .main-menu -->
		<div class="wrap">
			<aside class="menu_l">
				<ul class="side-menu" >
				
				</ul>
			</aside>
	    </div>
		<!-- .main-content -->
		<section class="layout-main">
			<div class="layout-main-tab">
				<!-- <button class="tab-btn btn-left"><i class="iconfont icon-guanbi">&#xe675;</i></button> -->
	               <nav class="tab-nav">
	                   <div class="tab-nav-content">
	                       <a href="javascript:;" class="content-tab active" data-id="asserts/tpl/homePage.html">首页</a>
	                   </div>
	               </nav>
	               <!-- <button class="tab-btn btn-right"><i class="iconfont">&#xe60f;</i></button> -->
			</div>
			<div class="layout-main-body">
				<iframe class="body-iframe" name="iframe0" width="100%" height="100%" src="asserts/tpl/homePage.html" 
				frameborder="0" data-id="asserts/tpl/homePage.html" seamless></iframe>
			</div>
		</section>
		<div class="layout-footer">Copyright©1999-2017.中国移动&nbsp;版权所有</div>
	</div>
	<!--引导  -->
	<div id="useHelp" style="display:none;">
        <div id="open" class="open">
             <span class="closebtn"></span>
             <img src="asserts/image/help/new.png" alt="" />
             <p class="new_art">
	             <i>新版上线啦！</i>
	             <i>  页面全新改版，功能更加明确。</i>
	         </p>
             <span class="goinfo">了解详情</span>
        </div>
	    <div class="box">
	         <div class="wrapper">
	         <span class="title">新用户功能引导示意图</span>	
	         <!--tab盒子  -->
			    <div class="tabBox">
			        <ul class="tab">
			            <li class="tab-item active">电费合同管理</li>
			            <li class="tab-item">电费固化信息管理</li>
			            <li class="tab-item">电表信息管理</li>
			            <li class="tab-item">报账点信息管理</li>
			            <li class="tab-item">报账点电费管理</li>
			            <li class="tab-item">汇总报账管理</li>
			            <li class="tab-item">统计数据收集</li>
			        </ul>
			        
			    </div>
			    <span class="closebtn"></span>
			    <div id="tabarr"><span id="tabL">&lt;</span><span id="tabR" class='right'>&gt;</span></div>
			    <!-- 具体类容 -->
		        <div class="products">
		            <div class="main selected">
		                <div id="box" class="all">
		                  <div class="ad">
		                      <ul id="imgs">
		                        <li>
			                        <p class="tit">电费合同管理-电费合同信息查询</p>
			                        <img src="asserts/image/help/2-1.png"/>
			                        <div>
			                        <p>1.电费合同信息查询</p>	
			                        <span> STEP1：在自维电费-电费合同信息管理-电费合同信息查询中，根据“合同编码或名称”、“选择地市”、“选择区县”、“合同状态”等条件，做查询过滤。</span> 
			                        </div>
		                        </li>
		                        <li>
			                        <p class="tit">电费合同管理-电费合同信息维护</p>
			                        <img src="asserts/image/help/2-2.png"/>
			                         <div>
			                        <p>2 .电费合同信息维护-补录</p>	
			                        <span> （1）电费合同信息补录步骤如下：</span> 
					                <span> STEP1：选择合同项目；</span> 
					                <span> STEP2：点击“补录”按钮，可对从合同系统中接入的合同进行信息补录。</span> 
			                        </div>
		                        </li>
		                        <li>
			                        <p class="tit">电费合同管理-电费合同信息维护</p>
			                        <img src="asserts/image/help/2-3.png"/>
			                        <div>
			                        <p>3 .电费合同信息维护-补录-基本信息</p>	
			                        <span>STEP3：点击“基本信息”按钮，进入电费合同基本信息修改页面，可修改和完善电费合同的基本信息；</span> 
			                        </div>
		                        </li>
		                        <li>
			                        <p class="tit">电费合同管理-电费合同信息维护</p>
			                        <img src="asserts/image/help/2-4.png"/>
			                        <div>
			                        <p>4 .电费合同信息维护-补录-电费信息</p>	
			                        <span> STEP4：点击“电费信息”按钮，进入电费信息修改页面，可修改和完善电费信息；</span> 
			                        <span> STEP5：对于非包干合同，选择非包干单价类型后，点击“录入电费单价”按钮，跳出录入电费单价对话框；</span>
			                        </div>
		                        </li>
		                        <li>
			                        <p class="tit">电费合同管理-电费合同信息维护</p>
			                        <img src="asserts/image/help/2-5.png"/>
			                        <div>
			                       
			                        <p> 5 .电费合同信息维护-补录-电费单价</p>	
			                        <span> STEP6：在跳出的录入电费单价对话框中，录入相应的单个电费单价；</span> 
			                        <span> STEP7：若有多种电价，则点击“+”按钮，可录入多种单价；若是平峰谷表，则需要分别在对话框中录入平峰谷单价；</span>
			                        </div>
		                        </li>
		                        <li>
			                        <p class="tit">电费合同管理-电费合同信息维护</p>
			                        <img src="asserts/image/help/2-6.png"/>
			                        <div>
			                        <p> 6. 电费合同信息维护-供应商</p>	
			                        <span>STEP8：点击“供应商”按钮，进入供应商信息修改页面，可修改和完善供应商信息；</span> 
			                        <span>STEP9：点击“选择供应商”按钮，跳出选择供应商对话框；</span>
			                        </div>
		                        </li>
		                        <li>
			                        <p class="tit">电费合同管理-电费合同信息维护</p>
			                        <img src="asserts/image/help/2-7.png"/>
			                        <div>
			                        <p> 7 .电费合同信息维护-供应商选择</p>	
			                        <span>STEP10：在跳出供应商对话框可通过输入供应商编码或名称、选择地市、
			                        选择区县，点击查询按钮，可筛选出匹配的供应商；</span> 
			                        <span>STEP11：勾选一个供应商；</span>
			                        <span>STEP12：点击“确认”按钮，完成供应商选择功能；</span>
			                        </div>
		                        </li>
		                        <li>
			                        <p class="tit">电费合同管理-电费合同信息维护</p>
			                        <img src="asserts/image/help/2-8.png"/>
			                        <div>
			                        <p> 8 .电费合同信息维护-合同附件</p>	
			                        <span>STEP13：点击“合同附件”按钮，进入合同附件上传页面，点击选择点击保存；</span> 
			                        <span>STEP14：点击“选择文件”按钮，可选择要上传的合同附件；</span>
			                        <span>STEP15：点击“上传”按钮，可向系统保存并上传已选的合同附件；</span>
			                        <span>STEP16：点击“保存”按钮，可保存以上各类录入信息；</span>
			                        </div>
		                        </li>
		                        <li>
			                        <p class="tit">电费合同管理-电费合同信息维护</p>
			                        <img src="asserts/image/help/2-9.png"/>
			                        <div>
			                        <p> 9. 电费合同信息维护-变更</p>
			                        <span>（2）合同变更步骤如下：</span> 
			                        <span>STEP1：选择一条状态为 “审核通过”的合同；</span>
			                        <span>STEP2：点击‘变更’按钮，对合同内容进行变更</span>
			                        </div>
		                        </li>
		                        <li>
			                        <p class="tit">电费合同管理-电费合同信息维护</p>
			                        <img src="asserts/image/help/2-10.png"/>
			                        <div>
			                         <p> 10 .电费合同信息维护-变更操作</p>
			                        <span>STEP3：与补录合同业务类似，分别在基本信息、电费信息、供应商、合同附件四个栏目中变更所要调整的字段内容；</span> 
			                        <span>STEP4：点击“保存”按钮，即可保存变更内容。</span>
			                        </div>
		                        </li>
		                         <li>
			                        <p class="tit">电费合同管理-电费合同信息维护</p>
			                        <img src="asserts/image/help/2-11.png"/>
			                        <div>
			                        <span>（（3）合同续签步骤如下：</span> 
			                        <span>STEP1：选择一条合同；</span>
			                        <span>STEP2：点击‘续签’按钮，对合同内容进行变更；</span>
			                        </div>
		                        </li>
		                         <li>
			                        <p class="tit">电费合同管理-电费合同信息维护</p>
			                        <img src="asserts/image/help/2-12.png"/>
			                        <div>
			                        <span>STEP3：在续签页面，可对“合同期始”、“合同期终”进行修改，按照新续签周期进行完善。</span> 
			                        <span>STEP4：点击“保存”按钮，即可保存续签内容。</span>
			                        </div>
		                        </li>
		                        <li>
			                        <p class="tit">电费合同管理-电费合同信息维护</p>
			                        <img src="asserts/image/help/2-13.png"/>
			                        <div>
			                        <span>  （5）合同提交审核步骤如下：</span> 
			                        <span>（对于补录、续签、变更后的合同，均需提交审核。）</span>
			                        <span> STEP1：通过输入合同编码或名称、选择地市、选择区县、合同状态，点击查询按钮，可筛选出匹配的合同；</span> 
			                        <span>STEP2：选择一条审核状态为“未提交”的合同条目；</span> 
			                        <span>STEP3：点击“提交审核”按钮；</span>
			                        </div>
		                        </li>
		                        <li>
			                        <p class="tit">电费合同管理-电费合同信息维护</p>
			                        <img src="asserts/image/help/2-14.png"/>
			                        <div>
			                        <span>STEP4：弹出“选择审批人”对话框，选择审批人；</span> 
			                        <span>STEP5：点击“确定”按钮，可提交审核。</span>
			                        </div>
		                        </li>
		                        <li>
			                        <p class="tit">电费合同管理-电费合同信息审核</p>
			                        <img src="asserts/image/help/2-15.png"/>
			                        <div>
			                        <span>STEP1：选中一条状态为“审核中”的合同条目；</span> 
			                        <span>STEP2：点击‘审核’按钮，弹出对话框，点击“确定”按钮即可对该条电费合同进行审核。</span>
			                        </div>
		                        </li>
		                        
		                      </ul>
		                  </div>
		                  <div id="arr">
		                  <span id="left">上一步</span><span id="right" class='right'>下一步</span>
		                  </div>
		              </div>
		            </div>
		             <!--  电费固话信息-->
		            <div class="main">
		               <div id="box" class="all">
		                <div class="ad">
		                    <ul id="imgs">
		                        <li>
		                        <p class="tit">电费固化信息管理-电费固化信息查询</p>
		                        <img src="asserts/image/help/3-1.png"/>
		                            <div>
			                        <span>STEP1：在自维电费-电费固化信息管理-电费固化信息查询中，根据“固化信息编码或名称”、“选择地市”、“选择区县”、“固化信息状态”等条件，做查询过滤。</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">电费固化信息管理-电费固化信息维护</p>
		                        <img src="asserts/image/help/3-2.png"/>
		                            <div>
			                        <span>在本页面可对电费固化信息做修改、删除、提交审核、变更操作。</span> 
                                    <span>（1）新增固化信息修改步骤如下：</span> 
                                    <span>STEP1：在页面中点击“新增”按钮，进入电费固化信息新增页面；</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">电费固化信息管理-电费固化信息维护</p>
		                        <img src="asserts/image/help/3-3.png"/>
		                            <div>
			                        <span>STEP2：点击“基本信息”按钮，进入电费固化信息的基本信息页面，可完善新增的固化基本信息；</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">电费固化信息管理-电费固化信息维护</p>
		                        <img src="asserts/image/help/3-4.png"/>
		                            <div>
			                        <span>STEP3：点击“电费信息”按钮，进入电费信息完善页面，可完善电费信息；</span> 
									 <span>STEP4：对于非包干合同，选择非包干单价类型后，点击“录入电费单价”按钮，跳出录入电费单价对话框；</span> 
			                        </div>
		                        </li>
		                         <li>
		                        <p class="tit">电费固化信息管理-电费固化信息维护</p>
		                        <img src="asserts/image/help/3-5.png"/>
		                            <div>
			                        <span>STEP5：在跳出的录入电费单价对话框中，录入相应的单个电费单价；</span> 
									 <span>STEP6：若有多种电价，则点击“+”按钮，可录入多种单价；若是平峰谷表，则需要分别在对话框中录入平峰谷单价；</span> 
			                        </div>
		                        </li>
		                         <li>
		                        <p class="tit">电费固化信息管理-电费固化信息维护</p>
		                        <img src="asserts/image/help/3-6.png"/>
		                            <div>
			                        <span>STEP7：点击“供应商”按钮，进入供应商信息完善页面，可完善供应商信息；</span> 
									 <span>STEP8：点击“选择供应商”按钮，跳出选择供应商对话框；</span> 
			                        </div>
		                        </li>
		                         <li>
		                        <p class="tit">电费固化信息管理-电费固化信息维护</p>
		                        <img src="asserts/image/help/3-7.png"/>
		                            <div>
			                        <span>STEP9：在跳出的供应商对话框中可通过输入供应商编码或名称、选择地市、选择区县，点击查询按钮，筛选出匹配的供应商；</span> 
									 <span>STEP10：勾选一个供应商；</span> 
									 <span>STEP11：点击“确认”按钮，完成供应商选择功能；</span> 
			                        </div>
		                        </li>
		                         <li>
		                        <p class="tit">电费固化信息管理-电费固化信息维护</p>
		                        <img src="asserts/image/help/3-8.png"/>
		                            <div>
			                        <span>STEP12：点击“固化信息附件”按钮，进入固化信息附件上传页面，点击选择点击保存；</span>  
									 <span>STEP13：点击“选择文件”按钮，可选择要上传的固化信息附件；</span> 
									 <span>STEP14：点击“上传”按钮，可向系统保存并上传已选的固化信息附件；</span> 
									 <span>STEP15：点击“保存”按钮，可保存以上各类新增录入信息；</span> 
			                        </div>
		                        </li>
		                         <li>
		                        <p class="tit">电费固化信息管理-电费固化信息维护</p>
		                        <img src="asserts/image/help/3-9.png"/>
		                            <div>
			                        <span>（2）固化信息修改步骤如下：</span> 
			                        <span>（对于新增后，但未提交审核的固化信息，可实现修改功能。）</span> 
			                        <span>STEP1：选择一条固化信息状态为“未提交”的固化信息条目；</span> 
									 <span>STEP2：点击“修改”按钮，对电费固化信息内容进行修改和完善。</span> 
			                        </div>
		                        </li>
		                         <li>
		                        <p class="tit">电费固化信息管理-电费固化信息维护</p>
		                        <img src="asserts/image/help/3-10.png"/>
		                            <div>
			                        <span>STEP3：与新增固化信息业务类似，分别在基本信息、电费信息、供应商、固化信息附件四个栏目中修改所要调整的字段内容；</span> 
									 <span>STEP4：点击“保存”按钮，即可保存修改内容。</span> 
			                        </div>
		                        </li>
		                         <li>
		                        <p class="tit">电费固化信息管理-电费固化信息维护</p>
		                        <img src="asserts/image/help/3-11.png"/>
		                            <div>
			                        <span>（3）固化信息变更步骤如下：</span> 
			                        <span>（对于已提交审核并且审核通过的固化信息，可实现变更功能。）</span>
									 <span>STEP1：选择一条状态为 “审核通过”的固化信息；</span> 
									 <span>STEP2：点击‘变更’按钮，对电费固化信息内容进行变更；</span> 
			                        </div>
		                        </li>
		                         <li>
		                        <p class="tit">电费固化信息管理-电费固化信息维护</p>
		                        <img src="asserts/image/help/3-12.png"/>
		                            <div>
			                        <span>STEP3：与新增、修改固化信息业务类似，分别在基本信息、电费信息、供应商、固化信息附件四个栏目中变更所要调整的字段内容；</span> 
									 <span>STEP4：点击“保存”按钮，即可保存变更内容。</span> 
			                        </div>
		                        </li>
		                         <li>
		                        <p class="tit">电费固化信息管理-电费固化信息维护</p>
		                        <img src="asserts/image/help/3-13.png"/>
		                            <div>
			                        <span>（4）固化信息续签步骤如下：</span> 
									 <span>STEP1：选择一条固化信息；</span> 
									 <span>STEP2：点击‘续签’按钮，对电费固化信息内容进行变更；</span> 
			                        </div>
		                        </li>
		                         <li>
		                        <p class="tit">电费固化信息管理-电费固化信息维护</p>
		                        <img src="asserts/image/help/3-14.png"/>
		                            <div>
			                        <span>STEP3：在续签页面，可对“固化信息期始”、“固化信息期终”进行修改，按照新续签周期进行完善。</span> 
									 <span>STEP4：点击“保存”按钮，即可保存续签内容。</span> 
			                        </div>
		                        </li>
		                         <li>
		                        <p class="tit">电费固化信息管理-电费固化信息维护</p>
		                        <img src="asserts/image/help/3-15.png"/>
		                            <div>
			                        <span>（5）固化信息提交审核步骤如下：</span> 
			                        <span>（对于新增、修改、变更、续签后的固化信息，均需提交审核。）</span> 
                                    <span>STEP1：通过输入固化信息编码或名称、选择地市、选择区县、固化信息状态，点击查询按钮，可筛选出匹配的固化信息；</span> 
									 <span>STEP2：选择一条审核状态为“未提交”的固化信息条目；</span> 
									 <span>STEP3：点击“提交审核”按钮，跳出选择审核人对话框；</span> 
			                        </div>
		                        </li>
		                         <li>
		                        <p class="tit">电费固化信息管理-电费固化信息维护</p>
		                        <img src="asserts/image/help/3-16.png"/>
		                            <div>
			                        <span>STEP4：在对话框中选择审核人；</span> 
									 <span>STEP5：点击“保存”按钮，可提交审核。</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">电费固化信息管理-电费固化信息审核</p>
		                        <img src="asserts/image/help/3-17.png"/>
		                            <div>
			                        <span>STEP1：选中一条状态为“审核中”的电费固化信息条目；</span> 
									 <span>STEP2：点击‘审核’按钮，弹出对话框，点击“确定”按钮即可对该条电费固化信息进行审核。</span> 
			                        </div>
		                        </li>
		                       
		                    </ul>
		                </div>
		                <div id="arr">
		                  <span id="left">上一步</span><span id="right" class='right'>下一步</span>
		                  </div>
		            </div>
		            </div>
		            <div class="main">
		               <div id="box" class="all">
		                <div class="ad">
		                    <ul id="imgs">
		                        <li>
		                        <p class="tit">电表信息管理-电表信息查询</p>
		                        <img src="asserts/image/help/lc13.png"/>
		                            <div>
			                        <span>STEP1：根据“电表编码”、“地市”、“区县”、“电表状态”、“电表类型”、“电表关联资源状态”等条件，点击“查询”按钮，即可做查询过滤；</span> 
			                        <span>STEP2：点击某个电表的电表编码，可查看电表详情；</span>
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">电表信息管理-电表信息维护</p>
		                        <img src="asserts/image/help/lc14.png"/>
		                            <div>
         							<p>在本页面可对电表信息做新增、修改、删除操作。</p>
			                        <span>（1）新增电表步骤如下：</span> 
			                        <span>STEP1：点击“新增”按钮，弹出新增电表对话框；</span>
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">电表信息管理-电表信息维护</p>
		                        <img src="asserts/image/help/lc15.png"/>
		                            <div>
			                        <span>STEP2：在对话框中填写电表信息后点击保存按钮，即可保存新增电表信息；</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">电表信息管理-电表信息维护</p>
		                        <img src="asserts/image/help/lc16.png"/>
		                            <div>
			                        <span>（2）修改电表步骤如下：</span> 
			                        <span>STEP1：选择一条电表条目；</span> 
			                        <span>STEP2：点击“修改”按钮，弹出修改电表对话框；</span> 
			                        </div>
		                        </li>
		                         <li>
		                        <p class="tit">电表信息管理-电表信息维护</p>
		                        <img src="asserts/image/help/lc17.png"/>
		                            <div>
			                        <span>STEP3：在对话框中修改电表信息后点击保存按钮，即可保存修改的电表信息；</span> 
			                        </div>
		                        </li>
		                    </ul>
		                </div>
		                <div id="arr">
		                  <span id="left">上一步</span><span id="right" class='right'>下一步</span>
		                  </div>
		             </div>
		            </div>
		            <div class="main">
		                <div id="box" class="all">
		                <div class="ad">
		                    <ul id="imgs">
		                        <li>
		                        <p class="tit">报账点信息管理-报账点信息查询</p>
		                        <img src="asserts/image/help/lc18.png"/>
		                            <div>
			                        <span>STEP1：根据“报账点编码”、 “报账点名称”、“地市”、“区县”、“报账点状态”、“审批状态”等条件，点击“查询”按钮，可对报账点做查询过滤:；</span> 
			                        <span>STEP2：点击某个报账点的报账点编码，可查看报账点详情；</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">报账点信息管理-报账点信息维护</p>
		                        <img src="asserts/image/help/lc19.png"/>
		                            <div>
			                        <p>在本页面可对报账点做新增、修改、删除、提交审批操作。</p> 
			                        <span>（1）新增报账点步骤如下：</span> 
			                        <span>STEP1：点击“新增”按钮，进入新增报账点页面；</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">报账点信息管理-报账点信息维护</p>
		                        <img src="asserts/image/help/lc20.png"/>
		                            <div>
			                        <span>STEP2：在新增报账点页面，填写好新增报账点信息，点击“选择关联合同”按钮，弹出“关联合同”对话框，在对话框中可选择想要关联的合同，点击“确定”按钮，完成合同关联；</span> 
			                        <span>STEP3：点击“选择关联资源”按钮，弹出“关联资源”对话框，在对话框中可选择想要关联的电表信息，点击“确定”按钮，完成资源关联；</span> 
			                        <span>STEP4：点击“保存”按钮，可保存新增的报账点信息，报账点状态为“未提交”；若点击“保存并提交审核”按钮，可保存新增的报账点信息并提交信息，报账点状态为“审核中”；</span>
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">报账点信息管理-报账点信息维护</p>
		                        <img src="asserts/image/help/lc21.png"/>
		                            <div>
			                        <span> （2）修改报账点步骤如下：</span> 
			                        <span>STEP1：选择一条状态为“未提交”的报账点条目；</span> 
			                        <span>STEP2：点击“修改”按钮，进入报账点信息修改页面；</span>
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">报账点信息管理-报账点信息维护</p>
		                        <img src="asserts/image/help/lc22.png"/>
		                            <div>
			                        <span>STEP3：与新增报账点类似，在修改报账点信息页面，点击“选择关联合同”按钮，弹出“关联合同”对话框，在对话框中可选择想要关联的合同，点击“确定”按钮，完成合同关联；</span> 
			                        <span>STEP4：点击“选择关联资源”按钮，弹出“关联资源”对话框，在对话框中可选择想要关联的电表信息，点击“确定”按钮，完成资源关联；</span> 
			                        <span>STEP5：点击“保存”按钮，可保存修改的报账点信息，报账点状态为“未提交”；若点击“保存并提交审核”按钮，可保存修改的报账点信息并提交信息，报账点状态为“审核中”；</span>
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">报账点信息管理-报账点信息维护</p>
		                        <img src="asserts/image/help/lc23.png"/>
		                            <div>
			                        <span>（3）报账点提交审批步骤如下：</span> 
			                        <span>STEP1：选择一条或多条状态为“未提交”的报账点条目；</span> 
			                        <span>STEP2：点击“提交审核”按钮，可对该报账点提交该审核。</span>
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">报账点信息管理-报账点信息维护</p>
		                        <img src="asserts/image/help/lc24.png"/>
		                            <div>
			                        <span> STEP3：选择一个审批人；</span> 
			                        <span>STEP4：点击“确定”按钮，提交审批；</span> 
			                        </div>
		                        </li>
		                         <li>
		                        <p class="tit">报账点信息管理-报账点信息审核</p>
		                        <img src="asserts/image/help/lc25.png"/>
		                            <div>
			                        <span>STEP1：选择一条审核状态为“审核中”的报账点信息；</span> 
			                        <span>STEP2：点击“审核”按钮，进入审核页面；</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">报账点信息管理-报账点信息审核</p>
		                        <img src="asserts/image/help/lc26.png"/>
		                            <div>
			                        <span>STEP3：可弹出批量审核对话框，填写相应信息后，点击“确定”按钮，可完成批量审核。</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">报账点信息管理-报账点信息审核</p>
		                        <img src="asserts/image/help/lc27.png"/>
		                            <div>
			                        <span>STEP4：在审核页面，审核人可勾选多条报账点信息，点击“批量审核”，可弹出批量审核对话框，在对话框中选择或填写相应信息；点击“确定”按钮，完成批量审核。</span> 
			                        </div>
		                        </li>
		                    </ul>
		                </div>
		                <div id="arr">
		                  <span id="left">上一步</span><span id="right" class='right'>下一步</span>
		                  </div>
		            </div>
		            </div>
		            <div class="main">
		                <div id="box" class="all">
		                <div class="ad">
		                    <ul id="imgs">
		                        <li>
		                        <p class="tit">报账点电费管理-报账点电费录入</p>
		                        <img src="asserts/image/help/lc28.png"/>
		                            <div>
			                        <span>STEP1：根据“报账点编码”、 “报账点名称”、“地市”、“区县”、“报账点状态”、“缴费状态”等条件，
			                                                                             点击“查询”按钮，可对报账点做查询过滤。</span> 
			                        <span>STEP2：单独选择一个状态为“待缴费”的报账点;</span> 
			                        <span>STEP3：点击“缴费录入”按钮，进入费用填报页面；</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">报账点电费管理-报账点电费录入</p>
		                        <img src="asserts/image/help/lc29.png"/>
		                            <div>
			                        <span>STEP4：在费用填报页面填写报账明细，点击“保存”按钮，可保存填入的电费录入信息，则在审核页面，
			                                                                该条提交状态为“未提交”；若点击“保存并提交审核”按钮，可保存并将该报账点费用提交状态置为“已提交”； </span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">报账点电费管理-报账点电费审核</p>
		                        <img src="asserts/image/help/lc30.png"/>
		                            <div>
			                        <span> STEP1：根据“报账点编码”、 “报账点名称”、“地市”、“区县”、“提交状态”、“审批状态”等条件，
			                                                                                           点击“查询”按钮，可对报账点做查询过滤。</span> 
			                        <span>STEP2：选择一条提交状态为“已提交”，审核状态为“审核中”的待审核明细；</span>
			                        <span>STEP3：点击“审批”按钮，进入审核页面；也可以选择多个缴费明细，进行批量审核；</span>
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">报账点电费管理-报账点电费审核</p>
		                        <img src="asserts/image/help/lc31.png"/>
		                            <div>
			                        <span>STEP4：填写相应审核信息和审核结论后，点击“保存”按钮，该笔缴费明细则通过审核。</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">报账点电费管理-报账点电费明细维护</p>
		                        <img src="asserts/image/help/lc32.png"/>
		                            <div>
			                        <span>STEP1：选择一项提交状态为“未提交”的报账点；</span> 
			                        <span>STEP2：点击“修改”按钮，可进入报账点电费明细修改页面，在页面中可对电费信息进行修改，
									                        点击“保存”按钮，可保存电费信息，提交状态为“未提交”；若点击“保存并提交审核”按钮，
									                        可保存电费信息并提交信息，提交状态为“已提交”；</span> 
			                        <span>STEP:3：选择一项报账点，点击“查看明细”按钮，可进入报账点电费明细查看页面；可以对费用明细进行查看、修改；</span> 
 								    <span>STEP4：选择一项报账点，点击“提交审批”按钮，则推送费用进入审核页面。</span> 
			                        </div>
		                        </li>
		                         <li>
		                        <p class="tit">报账点电费管理-报账点电费查询</p>
		                        <img src="asserts/image/help/lc33.png"/>
		                            <div>
			                        <span>STEP1：选择一项报账点，</span> 
			                        <span>STEP2：点击“查看明细”按钮，可进入报账点电费明细页面，在页面中可查看费用明细。</span> 
			                        </div>
		                        </li>
		                    </ul>
		                </div>
		                <div id="arr">
		                  <span id="left">上一步</span><span id="right" class='right'>下一步</span>
		                  </div>
		            </div>
		            </div>
		            <div class="main">
		                <div id="box" class="all">
		                <div class="ad">
		                    <ul id="imgs">
		                        <li>
		                        <p class="tit">汇总报账管理-电费报账汇总</p>
		                        <img src="asserts/image/help/lc34.png"/>
		                            <div>
		                            <p>在本板块可进行电费汇总报账单的生成、推送和查看。</p>	
			                        <span>（1）生成汇总单步骤如下：</span> 
			                        <span>STEP1：在此界面上可查看已汇总的报账点电费数据，可以根据‘汇总单编码’、
			                        ‘汇总日期起’、‘汇总日期止’、‘选择地市’、‘选择区县’、‘选中状态’、‘合同编号或名称’、
			                        ‘供应商编号或名称’、‘机房编码或名称’等条件，做查询过滤；</span> 
			                        <span>STEP2：点击“生成汇总单”按钮，进入汇总操作页面；</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">汇总报账管理-电费报账汇总</p>
		                        <img src="asserts/image/help/lc35.png"/>
		                            <div>
			                        <span>STEP3：可根据汇总条件：地市、区县、供应商、报账类型、付款方式、报账日期等条件筛选，点击“查询”按钮，筛选相关内容；</span> 
                                    <span>STEP4：在汇总操作页面，勾选待汇总的缴费项目；</span> 
                                    <span>STEP5：点击“生成汇总单”按钮，进入汇总结果页面；</span>
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">汇总报账管理-电费报账汇总</p>
		                        <img src="asserts/image/help/lc36.png"/>
		                            <div>
			                        <span>STEP6：在汇总结果页面，可点击“打印”按钮，打印汇总结果；点击“PDF导出”按钮，
			                        可将汇总结果页面以PDF形式进行导出；点击“导出缴费明细”按钮，可导出缴费明细，点击“完成”按钮，回到“电费汇总报账”主页；</span>
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">汇总报账管理-电费报账汇总</p>
		                        <img src="asserts/image/help/lc37.png"/>
		                            <div>
			                        <span>（2）推送生成汇总单步骤如下：</span> 
									 <span>STEP1：选择一条或多条推送状态为“未推送”的项目；</span> 
									 <span>STEP2：点击“推送汇总单”按钮，可对汇总单进行推送，并将汇总单的推送状态由“未推送”变为“已推送”。</span> 
			                        </div>
		                        </li>
		                       
		                    </ul>
		                </div>
		                <div id="arr">
		                  <span id="left">上一步</span><span id="right" class='right'>下一步</span>
		                  </div>
		            </div>
		            </div>
		            <!-- 统计数据收集-->
		           <div class="main">
		                <div id="box" class="all">
		                <div class="ad">
		                    <ul id="imgs">
		                        <li>
		                        <p class="tit">统计数据收集-集团数据汇总任务的新增与派发</p>
		                        <img src="asserts/image/help/8-1.png"/>
		                            <div>
			                        <span>（1）新增派发任务工单：</span> 
			                        <span>STEP1：在上方输入标题，选择派发状态、派发时间、处理时限，点击“查询”按钮，可按条件筛选任务工单；</span> 
			                        <span>STEP2：点击“新增”按钮，进入新增数据汇总任务页面；</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">统计数据收集-集团数据汇总任务的新增与派发</p>
		                        <img src="asserts/image/help/8-2.png"/>
		                            <div>
			                        <span>STEP3：输入标题，勾选派发省份，选择处理时限，输入正文内容；</span> 
                                    <span>STEP4：点击“选择抄送范围”按钮，弹出选择短信抄送范围对话框</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">统计数据收集-集团数据汇总任务的新增与派发</p>
		                        <img src="asserts/image/help/8-3.png"/>
		                            <div>
			                        <span>STEP5：可在“选择抄送范围”对话框中勾选想要抄送的集团领导；</span>
                                     <span>STEP6：点击“保存”按钮，可保存已勾选的抄送领导范围；</span>
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">统计数据收集-集团数据汇总任务的新增与派发</p>
		                        <img src="asserts/image/help/8-4.png"/>
		                            <div>
			                        <span>STEP7：点击“增加”按钮，可增加下发模板条目；</span> 
									 <span>STEP8：在下拉列表中，选择所要下发的数据类型模板；</span> 
									 <span>STEP9：点击“删除”可删除该条下发的数据类型模板；</span> 
									 <span>STEP10：下拉列表中若选择了“其他”，则可以上传其他类型的临时文件，以便下发给各省公司；</span> 
									 <span>STEP11：点击“保存”按钮，可保存当前新增的派发任务；</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">统计数据收集-集团数据汇总任务的新增与派发</p>
		                        <img src="asserts/image/help/8-5.png"/>
		                            <div>
			                        <span>（2）修改派发任务工单：</span>
									 <span>STEP1：对于派发状态为“未派发”的任务工单，可以进行修改，勾选所要修改的任务工单；</span>
									 <span>STEP2：点击“修改”按钮，可进入派发任务工单修改的页面，操作方法与“新增”功能类似；</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">统计数据收集-集团数据汇总任务的新增与派发</p>
		                        <img src="asserts/image/help/8-6.png"/>
		                            <div>
			                        <span>（3）派发任务工单：</span>
									 <span>STEP1：对于派发状态为“未派发”的任务工单，可以进行派发，勾选所要派发的一条或多条任务工单；</span>
									 <span>STEP2：点击“派发”按钮，弹出确认派发提示框，点击“确认”按钮，即可完成任务工单的派发工作；</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">统计数据收集-集团数据汇总任务的新增与派发</p>
		                        <img src="asserts/image/help/8-7.png"/>
		                            <div>
			                        <span>（4）任务工单的收集：</span>
                                     <span>STEP1：对于派发状态为“已派发”的任务工单，可点击该任务工单标题，进入任务工单的收集页面；</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">统计数据收集-集团数据汇总任务的新增与派发</p>
		                        <img src="asserts/image/help/8-8.png"/>
		                            <div>
			                        <span>STEP2：对于已上报的省份，省份名字标注为“√”符号，点击该省份，下方将展示该省份已经上报的数据文件；</span>
									<span>STEP3：点击文件标题可下载数据文件；</span>
									<span>STEP4：集团公司管理员可填写集团意见，点击“驳回”按钮，可对该省份上报的内容进行驳回，则该省公司需要重新上传和上报；</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">统计数据收集-省公司数据上报</p>
		                        <img src="asserts/image/help/8-9.png"/>
		                            <div>
			                        <span>（1）任务工单的文件上传与修改：</span> 
									 <span>STEP1：在上方输入标题，选择上报状态、派发时间、处理时限，点击“查询”按钮，可按条件筛选任务工单；</span> 
									 <span>STEP2：勾选一条上报状态为“未上报”的任务工单
									 <span>STEP3：点击“文件上传”按钮，进入数据汇总任务文件上传页面；</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">统计数据收集-省公司数据上报</p>
		                        <img src="asserts/image/help/8-10.png"/>
		                            <div>
			                        <span>STEP4：在文件上传页面内，可查看标题、处理时限、正文、集团意见，</span> 
									  <span>STEP5：在“数据模块”下载一栏中，可下载集团提供的数据收集模板和其他文件；</span> 
									  <span>STEP6：省公司在填写好数据收集模板和其他文件后，在对应的栏目，点击“上传”按钮，可上传相应的文件；</span> 
									  <span>STEP7：除集团下发的文件外，省公司亦可上传其他说明文件，可点击“上传文件”按钮，上传省公司的其他说明文件；</span> 
									  <span>STEP8：可在“备注”一栏填写备注信息；</span> 
									  <span>STEP9：点击“保存”按钮，可保存已输入的信息；</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">统计数据收集-省公司数据上报</p>
		                        <img src="asserts/image/help/8-11.png"/>
		                            <div>
			                        <span>（2）任务工单的上报：</span>
									<span>STEP1：勾选一条上报状态为“未上报”的任务工单；</span>
									<span>STEP2：点击“上报”按钮，弹出确认上报提示框，在框内点击“确定”按钮，即可向集团上报所选工单任务；</span> 
			                        </div>
		                        </li>
		                        <li>
		                        <p class="tit">统计数据收集-省公司数据上报</p>
		                        <img src="asserts/image/help/lc37.png"/>
		                            <div>
			                        <span>（2）推送生成汇总单步骤如下：</span> 
									 <span>STEP1：选择一条或多条推送状态为“未推送”的项目；</span> 
									 <span>STEP2：点击“推送汇总单”按钮，可对汇总单进行推送，并将汇总单的推送状态由“未推送”变为“已推送”。</span> 
			                        </div>
		                        </li>
		                       
		                    </ul>
		                </div>
		                <div id="arr">
		                  <span id="left">上一步</span><span id="right" class='right'>下一步</span>
		                  </div>
		            </div>
		            </div>
		            <div class="alertList" id='alertBox'>
				        <div class="shadow">
				        <div class="shadow_tit">
				        <i></i>
				        <span class="top">帮助中心</span>
				        <span class="bottom">请从下列项目中，选择您需要帮助的内容。</span>
				        </div>
				        <div class="item_tit">
				        <i></i>
				        <span class="top">自维电费</span>
				        </div>
				          <ul class='alert'>
				            <li> <span class="elc1"></span>电费合同管理</li>
				            <li> <span class="elc6"></span>电费固化信息管理</li>
				            <li> <span class="elc2"></span>电表信息管理</li>
				            <li> <span class="elc3"></span>报账点信息管理</li>
				            <li> <span class="elc4"></span>报账点电费管理</li>
				            <li> <span class="elc5" style="margin-left: 22px;"></span>汇总报账管理</li>
				          </ul>
				          <ul class='alert'>
						         <div class="tjItem">
								        <i></i>
								        <span class="act">系统数据查询</span>
						         </div>
						        <li > <span class="elc7"></span>统计数据收集</li>
				           </ul>
				          </div>
		            </div>
		        </div>
            </div>
	    </div>
	</div> 
	
	<script src="asserts/lib/jquery.js" type="text/javascript"></script>
	<script src="asserts/bootstrap/js/ui.loading.js" type="text/javascript"></script>
	<script src="asserts/js/sccl.js" type="text/javascript"></script>
	<script src="asserts/js/guide.js" type="text/javascript"></script>
	<script src="asserts/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<!--[if lte IE 9]>
	 <script src="asserts/bootstrap/js/html5shiv.js"></script>
	 <script src="asserts/bootstrap/js/respond.min.js"></script>
	 <script src="asserts/bootstrap/js/jquery.placeholder.min.js"></script>
	<![endif]-->
	<!-- 自定义 -->
	<script type="text/javascript" src="asserts/js/common.js"></script>
	<!-- load MenuList -->
	<script type="text/javascript" src="asserts/js/page/headPage.js?v=2"></script>
	<script>
		$('.wrap').css('height',($(window).height()-80)+"px");
		$('.menu_l').css('height',($(window).height()-60)+"px");
		$(window).on('resize',function(){
			$('.wrap').css('height',($(window).height()-80)+"px");
			$('.menu_l').css('height',($(window).height()-60)+"px");
		})
		function setTodoNum(num){
			$("#agencyNum").html(num);
		}
		
	</script>
  </body>
</html>