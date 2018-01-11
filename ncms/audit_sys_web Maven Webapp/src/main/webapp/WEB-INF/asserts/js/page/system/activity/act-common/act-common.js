/**
 * SelfRentAudit:模块名
 * procDefKey：流程标识
 * tableName:表名
 * title:名称
 */
var SelfRentAudit={procDefKey:"SelfRentAudit",tableName:"rent_contract",title:"自维租费合同审核"};
var RembursePoint={procDefKey:"SelfRentAudit",tableName:"rent_payment",title:"自维租费报账点缴费审核"};
var RentBillAccount={procDefKey:"SelfRentAudit",tableName:"rent_billaccount",title:"自维租费报账点审核"};
var TwrRentInformation={procDefKey:"SelfRentAudit",tableName:"twr_rentinformation",title:"移动资源信息审核"};
var TwrRentInformationBizchange={procDefKey:"SelfRentAudit",tableName:"twr_rentinformation_bizchange",title:"铁塔信息变更审核"};
var TwrRentInformationtower={procDefKey:"SelfRentAudit",tableName:"twr_rentinformationtower",title:"铁塔资源信息审核"};
var TwrStopServer={procDefKey:"SelfRentAudit",tableName:"twr_stopserver",title:"铁塔服务终止"};
var TwrAccountsummary={procDefKey:"SelfRentAudit",tableName:"twr_accountsummary",title:"费用汇总审核"};
var TwrProvincePunish={procDefKey:"SelfRentAudit",tableName:"twr_province_punish",title:"省扣罚审核"};
var TwrRegPunish={procDefKey:"SelfRentAudit",tableName:"twr_reg_punish",title:"地市扣审核"};

var EleBillaccount={procDefKey:"ReimbursementPointAudit",tableName:"ele_billaccount",title:"电费报账点审核"};
var SelfElecAudit={procDefKey:"contractRenewalAudit",tableName:"dat_contract",title:"电费合同审核"};
var EleBillaccountPaymentdetail={procDefKey:"ElectricityAudit",tableName:"ele_payment",title:"电费报账点缴费信息审核"};

var SiteAudit={procDefKey:"SelfRentAudit",tableName:"dat_basesite",title:"站点审核"};
var BaseResourceAudit={procDefKey:"SelfRentAudit",tableName:"dat_baseresource",title:"基础资源审核"};

var baseDir=projectName+"/act/common";//公共流程基本目录
/**
 * 获取审核流转历史记录
 * @param tableName 业务主表名
 * @param id 业务表主键
 */
function histoicFlowList(tableName,id){
	$.ajax({
		url:baseDir+'/histoicFlowList',
		data:{
			tableName:tableName,
			id:id
			},
		type:'post',
		async:false,
		dataType:'json',
		success:function(back){
			if (back != null&&back.success=="1") {
					var arrList= back.Obj;
					var s='';

				if(back.Obj!=null&&arrList.length>0){//如果没有流转记录，就不操作
					s+='<div class="search_title">审核信息</div>';
					s+='<table id="tb" class="table table-bordered table-hover table-striped">';
					s+='<thead>';
						s+='<tr>';
							s+='<th>执行环节</th>';
							s+='<th>执行人</th>';
							s+='<th>开始时间</th>';
							s+='<th>结束时间</th>';
							s+='<th>提交意见</th>';
							s+='<th>任务历时</th>';
						s+='</tr>';
					s+='</thead>';
					s+='<tbody>';
					for(var i=0;i<arrList.length;i++){
						console.log('112222')	
						var endDate;
						if(arrList[i].endDate!=null){
							endDate=new Date(arrList[i].endDate).format("yyyy-MM-dd hh:mm:ss")
						}
						else{
							endDate="";
						}
						console.log(endDate)
						s += '<tr><td>'
							+ arrList[i].title
							+ '</td><td>'
							+ arrList[i].assignee
							+ '</td>'
							+'<td>'
							+ new Date(arrList[i].beginDate).format("yyyy-MM-dd hh:mm:ss")
							+ '</td>'
							+ '<td>'
							+endDate
							+ '</td><td>'
							+ arrList[i].comment
							+ '</td><td>' + arrList[i].durationTime+ '</td></tr>';
						
					}
					s+='</tbody>';  
					s+='</table>';
					
					$('#histoicFlowList').html(s);
				}
			}
		},
		error:function(){
			alert("请求异常");
		}
	});
	
}
/**
 * 获取首环节候选用户 （弹出表格选择）
 * @param procDefKey 模型标识
 * @param funcName 保存调用方法名
 * @param regId 数据所属区县
 * 调用方法如：queryFirstUsersByProcDefKey(TwrRegPunish.procDefKey,"startActFlow")参数需自己更改
 */
function queryFirstUsersByProcDefKey(procDefKey,funcName,regId){
	$.ajax({
		url:baseDir+'/queryFirstUserByProcDefKey',
		data:{
			procDefKey:procDefKey,
			regId:regId
			},
		type:'post',
		dataType:'json',
		async:false,
		success:function(back){
			if (back != null) {
				if(back.success=="1"){
					 var item= back.Obj;
					 var s="";
				 s+="<div class='modal-dialog' role='document'>";
					 s+="<div class='modal-content'>";
						 s+="<div class='modal-header'>";
							 s+="<button class='close' type='button' data-dismiss='modal'>×</button>";
							 s+="<h4 id='myModalLabel'>请选择审批人</h4>";
						 s+="</div>";
						 s+="<div class='modal-body'>";
							
					 s+="<table class='table table-bordered table-striped table-hover' id='firstUsersSelect'>";
						 s+="<thead>";
							 s+="<tr style='text-align: center;'>";
								 s+="<th><input type='hidden' ></th>";
								 s+="<th>审批人姓名</th>";
								 s+="<th>审批人电话</th>";
								 s+="<th>电子邮件</th>";
							 s+="</tr>";
							 s+="</thead>";
							 s+="<tbody>";
							 if(item!=null){
								 for(var i=0;i<item.length;i++){
									 s += "<tr style='text-align: center;' class='usersTr'><td><input type='radio' name='userChecked' value='"+item[i].userLoginname+"'/></td><td>"
										+ item[i].userLoginname
										+ "</td><td>"
										+ (item[i].userPhone?item[i].userPhone:"")
										+ "</td><td>"
										+ (item[i].userEmail?item[i].userEmail:"")
										+ "</td></tr>";
								 }
							 }
		
							s+="</tbody>";
					 s+="</table>";
				 s+="</div>";
					 s+="<div class='modal-footer'>";
						 s+="<a href='#' class='btn' data-dismiss='modal' >取消</a>";
						 s+="<a href='#' class='btn btn-primary' onclick='verify("+funcName+");'>确定</a>";
					 s+="</div>";
				 s+="</div>";
			 s+="</div>";
				 	
					 $('#firstUsers').html(s);
					 $('#firstUsers').modal();
					 $("tr.usersTr").on("click",function(){
						 $(this).find("input:radio").attr("checked","checked");
					 });
				}
			}
		},
		error:function(){
			alertModel("获取首环节候选用户，模型标识："+procDefKey);
		}
	});
}
//限制下级审核人必选
function verify(funcName){
	var checkNum = 0;
	var rowschecked = new Array();
	var checklist = $("#firstUsers  input[type='radio']");
	for(var i=0;i<checklist.length;i++)
    {
		// 已选中可操作行
	    if(checklist[i].checked == 1){
	    	checkNum ++;
	    	rowschecked.push(checklist[i].val);
	    }
    } 
	if(checkNum=='0'){
		alertModel("请选择一条记录");
		return false;
	}
	funcName();
}
/**
 * 获取首环节候选用户 （下拉选择）
 * @param tableName
 * @param id
 */
function findFirstUsersByProcDefKey(procDefKey,regId){
	$.ajax({
		url:baseDir+'/queryFirstUserByProcDefKey',
		data:{
			procDefKey:procDefKey,
			regId:regId
		},
		type:'post',
		dataType:'json',
		async:false,
		success:function(back){
			if (back != null) {
				if(back.success=="1"){
					 var item= back.Obj;
					 var s="<option value=''>---请选择---</option>";
					 if(item!=null){
						 
						 for(var i=0;i<item.length;i++){
							s+="<option value='"+item[i].userLoginname+"'>"+ item[i].userLoginname+"</option>";
						 }
					 }
					 $('#nextUsers').html(s);
				}
			}
		},
		error:function(){
			 $("#nextUsers").append("<option>---请选择---</option>");
		}
	});
}
/**
 * 获取下一步候选用户 （弹出框审核）
 * @param tableName
 * @param id
 */
function findNextUsersByBuniessKey(tableName,id,func){
	$.ajax({
		url:baseDir+'/queryUserByRoleId',
		data:{
			//procDefKey:procDefKey,
			tableName:tableName,
			id:id,
			regId:regId
			},
		type:'post',
		dataType:'json',
		async:false,
		success:function(back){
			if (back != null) {
				if(back.success=="1"){
					 var item= back.Obj;
					 $("#NextUsersModel").remove();
					 var s="";
					 s+='<div class="modal fade" id="NextUsersModel" tabindex="-1">';
						s+='<div class="modal-dialog" role="document" style="width:800px;">';
							s+='<div class="modal-content">';
								s+='<div class="modal-header">';
									 s+='<button class="close" type="button" data-dismiss="modal">×</button>';
									 s+='<h4 id="myModalLabel">审核</h4>';
								s+='</div>';
								s+='<div class="modal-body">';
								  s+='<form id="dataForm">';
									s+='<ul class="list-group">';
										s+='<li class="list-group-item row">';
											s+='<div class="col-md-2">审核状态</div>';
											s+='<div class="col-md-4">';
												 s+='<select class="form-control" id="auditState" style="width:60%;">';
												 	s+='<option value="0">通过</option>';
											 	    s+='<option value="8">不通过</option>';
										 	     s+='</select>';
								 	    	s+='</div>';
							 	    		s+='<div class="col-md-2">审核时间</div>';
						 	    			s+='<div class="col-md-4">';
						 	    				 s+='<input type="text" id="auditTime" class="form-control" readonly="readonly" style="width:60%;"/>';
					 	    				s+='</div>';
			 	    					s+='</li>';
		 	    						s+='<li class="list-group-item row">';
	 	    								s+='<div class="col-md-2">审核人</div>';
	    									s+='<div class="col-md-4" id="curUser" style="text-indent: 1em;text-align:left"></div>';
											s+='<div class="col-md-2">下级审核人</div>';
											s+='<div class="col-md-4" id="firstUsers">';
												s+='<select id="nextUsers" name="nextUsers" class="form-control selectpicker" data-hide-disabled="true" data-size="4" style="width:60%;float:left">';
												  	s+='<option value="">---请选择---</option>';
													 if(item!=null){
														for(var i=0;i<item.length;i++){
															s+="<option value='"+item[i].userLoginname+"'>"+ item[i].userLoginname+"</option>";
														 }
													 }
												s+='</select>';
												s+='<span id="err" class="modal-error"></span>';
											s+='</div> ';
										s+='</li>';
										s+='<li class="list-group-item row">';
											s+='<div class="col-md-2">审核备注</div>';
											s+='<div class="col-md-10">';
												s+='<input type="text" class="form-control" id="auditNote" name="auditNote" style="width:80%;float:left">';
												s+='<span id="err" class="modal-error" style="float:left;margin-left:10px"></span>';
											s+='</div>';
										s+='</li>';
									s+='</ul>';
								  s+='</form>';
								s+='</div>';
								s+='<div class="modal-footer">';
									 s+='<a href="#"  class="btn" data-dismiss="modal">关闭</a> ';
									 s+='<a href="#" class="btn btn-primary" onclick="'+func+'()">保存</a>';
								s+='</div>';
							s+='</div>';
						s+='</div>';
					s+='</div>';
					$("body").append(s);
					$("#NextUsersModel").modal("show");
					getAuditTime();
				}
			}
		},
		error:function(){
			alertModel("获取首环节候选用户，模型标识："+taskDefKey);//+procDefKey);
		}
	});
}
/**
 * 
 * */
function getAuditTime(){
	var myDate = new Date();
    var str = "" + myDate.getFullYear()+"/";
    str +=""+ (myDate.getMonth()+1)+"/";
    str += "" + myDate.getDate()+" ";
    str += "" + myDate.getHours()+":";
    str += "" + myDate.getMinutes()+"";
    $('#auditTime').val(str);
	/*$('#auditTime').datetimepicker({
    	format: 'YYYY/M',
		locale: moment.locale('zh-cn')
    });*/
}
function extend(destination, source) {
    for (var property in source)
    	destination[property] = source[property];
    return destination;
} 
/**
 * 获取下一步候选用户（下拉选择）
 * @param tableName
 * @param id
 * @param regId 数据所属区县
 * @param elName 流程判断条件（如果为空，默认有state）
 */
function findUsersByRoleIds(tableName,id,regId,elNameObj){
	var datas='{"tableName":tableName,"id":id,"regId":regId}';
	var datasObj = eval('(' + datas + ')');
	extend(datasObj, elNameObj); 
	console.log(datasObj);
	$.ajax({
		url:baseDir+'/queryUserByRoleId',
		data: datasObj,    
		type:'post',
		dataType:'json',
		async:false,
		success:function(back){
			if (back != null) {
				if(back.success=="1"){
					 var item= back.Obj;
					 var s="<option value=''>---请选择---</option>";
					 if(item!=null){
						 
						 for(var i=0;i<item.length;i++){
							s+="<option value='"+item[i].userLoginname+"'>"+ item[i].userLoginname+"</option>";
						 }
					 }
					 $('#nextUsers').html(s);
				}
			}
		},
		error:function(){
			 $("#nextUsers").append("<option>---请选择---</option>");
		}
	});
}
