//已显示表格list
var showTableList = null;
$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	curPageNum = 1;
	// 查询默认条件表单数据
	loadTableData();
}
function loadTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	
	if($("#startTime").val() != null && $("#startTime").val() != ''){
		var startTime = $("#startTime").val()+" 00:00:00";
	}
	if($("#endTime").val() != null && $("#endTime").val() != ''){
		var endTime = $("#endTime").val()+" 23:59:59";
	}
	
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "backlogList", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		pageSize : ipageCount, // 每页显示的记录数
		pageNumber : curPageNum, // 当前第几页
		minimumCountColumns: 1,  //最少允许的列数
		clickToSelect: true,  //是否启用点击选中行
		pageList : [10, 25, 50, 100, 500], // 记录数可选列表
		search : false, // 是否启用查询
		sidePagination : "server", // 表示服务端请求
		// 设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
		// 设置为limit可以获取limit, offset, search, sort, order
		queryParamsType : "undefined",
		queryParams : function queryParams(params) { // 设置查询参数
			var param = {
					pageNumber: params.pageNumber,    
					pageSize: params.pageSize,
					agentClass:$('#agentClass').val(), //代办类别
					treatmentState:$('#treatmentState').val(),//处理状态
					startTime:startTime,   //产生日期----开始
					endTime:endTime        //产生日期----结束
			};
			return param;
		},
		columns: [{
            title: '序号',
            formatter: function (value, row, index) {  
                return index+1;  
            }  
        },{
            field: 'title',
            title: '待办类别'
        },{
            field: 'taskName',
            title: '审核环节'
        },{
            field: 'beginDate',
            title: '产生时间'
        },{
        	field: 'endDate',
            title: '处理时间'
        },{
            field: 'status',
            title: '处理状态',
            formatter:function(value,row,index){
            	switch(value){
            		case '1':return '已处理';
            		case '0':return '待处理';
            		case '-1':return '已驳回';
            		case '9':return '已签收';
            		case '11':return '已完结';
            		default:"异常";
            	}
            	return value;
            }
        },{
            field: '',
            title: '处理操作',
            formatter : operateFormatter
        }],
		onLoadError : function() { // 加载失败时执行
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success != "1"){
		            alertModel(res.msg.result);
				}else{
					showTableList = res.Obj.result;
				}
				if(res.Obj!=null){
					return {
			            "total": res.Obj.total,//总页数
			            "rows": res.Obj.result, //数据
			         };
				}else{
					return {
			            "total":0,//总页数
			            "rows":0, //数据
			         };
				}
			}
	        
		}
	});	
}
function operateFormatter(row,data){
	var operate='<div class="btn-group">';
		if(data.businessTable=="grpDatacollect"&&data.status == 0){
			operate+='<button type="button" class="btn btn-primary btn-sm" title="签收" onclick="signDatas(\''+data.businessId+'\')"><i class="glyphicon glyphicon-edit" ></i></button>';
		}else if(data.businessTable=="grpDatacollect"&&data.status == 9){
			operate+='<button type="button" class="btn btn-primary btn-sm" title="上传文件" onclick="uploadDatas(\''+data.businessId+'\')"><i class="glyphicon glyphicon-hand-right" ></i></button>';			
		}else if(data.businessTable=="grpDatacollect"&&data.status == -1){
			operate+='<button type="button" class="btn btn-primary btn-sm" title="上传文件" onclick="uploadDatas(\''+data.businessId+'\')"><i class="glyphicon glyphicon-hand-right" ></i></button>';			
		}else if(data.businessTable=="grpDatacollect"&&data.status == 8){
			operate+='<button type="button" disabled="disabled" class="btn btn-info btn-sm" title="已被他人签收"><i class="glyphicon glyphicon-minus" ></i></button>';			
		}
		else if(data.status=='0'){
			operate+='<button type="button" class="btn btn-primary btn-sm" title="处理" onclick="signActDatas(\''+data.businessTable+'\',\''+data.businessId+'\',\''+data.taskId+'\',\''+data.status+'\')"><i class="glyphicon glyphicon-edit" ></i></button>';
		}else if(data.status=='1'){
			operate+="-";
		}
		operate+='</div>';
	return operate;
}
//流程待办跳转页面
var actForUrlArr=[
                  {"rent_contract":"asserts/tpl/selfrent/rentcontract_m/Info_check.html"},
                  {"rent_payment":"asserts/tpl/selfrent/billaccount_m/checkInfo.html"},
                  {"rent_billaccount":"asserts/tpl/selfrent/reimburseInfo_m/auditInfo.html?billAccountId=27073fe97db94365925447935508c1d0&taskId=175275"},
                  {"twr_rentinformation":"asserts/tpl/selfelec/billaccount/auditing_check.html?taskId=342562"},
                  {"twr_rentinformation_bizchange":""},
                  {"twr_rentinformationtower":""},
                  {"twr_stopserver":""},
                  {"twr_province_punish":""},
                  {"twr_accountsummary":""},
                  {"ele_billaccount":"asserts/tpl/selfelec/billaccount/auditing_check.html?taskId=342562"},
                  {"dat_contract":"asserts/tpl/selfelec/eleccontract/elecContractAuditNewsCuring.html?contractId=fdd77ef1078c4df6b40fbcda55e3c6aa&taskId=240008&type=0&regId=310115&elecontractId=ELECONTRACT-1505703859260"},
                  {"ele_payment":"asserts/tpl/selfelec/payment/auditing_check.html?taskId=292520&billaccountpaymentdetailId=077bee1f243849d498193c1fb54bd25d&regId=310101"},
                  {"dat_basesite":"asserts/tpl/basedata/site/siteAuditNews.html?basesiteId=SITE-1504407107180&taskId=185020&regId=310118"},
                  {"dat_baseresource":""}
                  ];
//基础数据资源点 asserts/tpl/basedata/respoint/respointAuditNews.html?baseresourceId=resource-1504158854807&taskId=173191&regId=310101
//
function signActDatas(businessTable,businessId,taskId,status){
	console.log(businessTable+"-------------"+businessId);
	if(businessTable=="ele_billaccount"){//自维电费报账点审核
		window.parent.addIframeTop("asserts/tpl/selfelec/billaccount/auditing_check.html?billaccountId="+businessId+"&taskId="+taskId,"1001","报账点信息审核");
	}else if(businessTable=="dat_contract"){//自维电费合同审核
		$.ajax({
			url : '../selfelec/eleccontract/checkContractCode',
			data : {
				contractId : businessId
			},
			async : false,
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(back) {
				if(back.success==1&&back.obj!=null){
					if(back.obj[0].dataFrom!=null&&back.obj[0].dataFrom!=2){
						window.parent.addIframeTop("asserts/tpl/selfelec/eleccontract/elecContractAuditNewsCuring.html?contractId="+businessId+"&taskId="+taskId,"1002","电费固化信息审核");
					}else{
						window.parent.addIframeTop("asserts/tpl/selfelec/eleccontract/elecContractAuditNews.html?contractId="+businessId+"&taskId="+taskId,"1002","电费合同审核");
					}
				}
			},
			error : function() {
				alertModel("请求异常");
			}
		});
	}
	else if(businessTable=="ele_payment"){//自维电费缴费审核
		window.parent.addIframeTop("asserts/tpl/selfelec/payment/auditing_check.html?billaccountpaymentdetailId="+businessId+"&taskId="+taskId,"1003","报账点电费审核");
	}
	else if(businessTable=="dat_basesite"){//基础数据站点审核
		window.parent.addIframeTop("asserts/tpl/basedata/site/siteAuditNews.html?basesiteId="+businessId+"&taskId="+taskId,"1004","站点信息审核");

	}
	else if(businessTable=="dat_baseresource"){//基础数据-资源审核
		var id = businessId;
		$.ajax({
			url : '../basedata/resource/queryDetails',
			data : {
				id : id
			},
			type : 'get',
			dataType : 'json',
			async:false,
			success : function(list) {
				if(list == null||list.Obj==null){
					alertModel("数据无法获取,请重试!");
					return false;
				}
				// 请求成功时
				var item = list.Obj;
				var regId=item.regId;
				if(item.baseresourceType=="0"){
					window.parent.addIframeTop("asserts/tpl/basedata/room/roomAuditNews.html?baseresourceId="+businessId+"&taskId="+taskId+"&regId="+regId,"1004","机房信息审核");
				}else if(item.baseresourceType=="1"){
					window.parent.addIframeTop("asserts/tpl/basedata/respoint/respointAuditNews.html?baseresourceId="+businessId+"&taskId="+taskId+"&regId="+regId,"1004","资源点信息审核");
				}else if(item.baseresourceType=="2"){
					window.parent.addIframeTop("asserts/tpl/basedata/hotpoint/hotpointAuditNews.html?baseresourceId="+businessId+"&taskId="+taskId+"&regId="+regId,"1004","热点信息审核");
				}else if(item.baseresourceType=="3"){
					alertModel("位置点审核");
					//window.parent.addIframeTop("asserts/tpl/basedata/room/roomAuditNews.html?baseresourceId="+businessId+"&taskId="+taskId+"&regId="+regId,"1004","资源点信息审核");
				}else{
					alertModel("资源点类别未知!");
				}
			},
			error : function() {
				alertModel("请求异常");
			}
		});
	}
	else if(businessTable=="rent_contract"){//自维租费合同审核
		window.parent.addIframeTop("asserts/tpl/selfrent/rentcontract_m/Info_check.html?rentcontractId="+businessId+"&taskId="+taskId,"1004","资源点信息审核");
	}else if(businessTable=="rent_payment"){//自维租费缴费审核
		window.parent.addIframeTop("asserts/tpl/selfrent/billaccount_m/checkInfo.html?paymentId="+businessId+"&taskId="+taskId,"1004","资源点信息审核");
	}else if(businessTable=="rent_billaccount"){//自维租赁费报账点审核
		window.parent.addIframeTop("asserts/tpl/selfrent/reimburseInfo_m/auditInfo.html?billAccountId="+businessId+"&taskId="+taskId,"1004","报账点电费审核");
	}
	
	/**else if(businessTable=="twr_rentinformation"){//移动资源信息审核
		window.parent.addIframeTop("asserts/tpl/basedata/site/siteAuditNews.html?basesiteId="+businessId+"&taskId="+taskId,"1004","移动资源信息审核");
	}else if(businessTable=="twr_rentinformation_bizchange"){//铁塔信息变更审核
		window.parent.addIframeTop("asserts/tpl/basedata/site/siteAuditNews.html?basesiteId="+businessId+"&taskId="+taskId,"1004","铁塔信息变更审核");
	}else if(businessTable=="twr_rentinformationtower"){//铁塔资源信息审核
		window.parent.addIframeTop("asserts/tpl/basedata/site/siteAuditNews.html?basesiteId="+businessId+"&taskId="+taskId,"1004","铁塔资源信息审核");
	}else if(businessTable=="twr_stopserver"){//铁塔服务终止审核
		window.parent.addIframeTop("asserts/tpl/basedata/site/siteAuditNews.html?basesiteId="+businessId+"&taskId="+taskId,"1004","铁塔服务终止审核");
	}else if(businessTable=="twr_accountsummary"){//铁塔服务费汇总审核
		window.parent.addIframeTop("asserts/tpl/basedata/site/siteAuditNews.html?basesiteId="+businessId+"&taskId="+taskId,"1004","铁塔服务费汇总审核");
	}*/
	
	
	else if(businessTable=="twr_province_punish"){//省扣罚审核
		window.parent.addIframeTop("asserts/tpl/towerrent/checkmanage/checkinner.html?twrProvincePunishId="+businessId+"&taskId="+taskId,"1004","省扣罚审核");
	}else if(businessTable=="twr_reg_punish"){//地市扣罚审核
		window.parent.addIframeTop("asserts/tpl/towerrent/assessment/cityExaminationAuditNews.html?twrRegPunishId="+businessId+"&taskId="+taskId,"1004","地市扣罚审核");
	}
	else{
		alertModel("此类别的待办任务处理正在努力开发中，请您手动前往相应页面进行处理，多谢您的谅解！");
	}
}

/**
 * 签收
 */
function signDatas(id){
	$.ajax({
 		url : projectName+"/asserts/tpl/countanalyse/groupdata/updateGrpToUserSelf",
 		data : {
 			datacollectPrvId:id
 		},
 		type : 'post',
		cache : false,
 		dataType : 'json',
		//contentType: "application/json;charset=utf-8",
 		success : function(back) {
 			if (back != null) {
				alertModel(back.msg,loadTableData);
 			}
 		},
 		error : function(back) {
			alertModel(back.msg);
 		}
 	});
}
/**
 * 文件上传
 */
function uploadDatas(businessId){
	window.parent.addIframeTop("asserts/tpl/countanalyse/groupdata/reportDataProAgencyDetails.html?datacollectPrvId="+businessId,"1031","数据上报待办");
	
}
