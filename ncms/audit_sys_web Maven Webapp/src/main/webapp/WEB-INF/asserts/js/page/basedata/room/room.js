var operate_type = 1;// 1 新增，2 修改
var resType = 0;
var queryType = 0;//0 录入数据，1-导入数据，2-接口导入数据
var curPageNum = 0;
$(document).ready(function() {
	initialize();
});
/**
 * 初始化加页面
 */
function initialize() {
	curPageNum = 1;
	getAddress();
	findRoom();
}
/**
 * 列表查询
 */
function findRoom() {
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "../resource/list", // 获取数据的地址
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
					reg : $("#reg").val(),
					property : $("#property option:selected").val(),
					auditStatus : $("#auditStatus option:selected").val(),
					status : $("#status option:selected").val(),
					city : $("#city option:selected").val(),
					region : $("#region option:selected").val(),
					queryType : queryType,
					resType : resType,
					cur_page_num: params.pageNumber,    
					page_count: params.pageSize
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
            field: 'auditingState',
            title: '审核状态',
            formatter: fmtAuditState
        }, {
            field: 'baseresourceCode',
            title: '机房编码',
            formatter: clickFormatter
        }, {
            field: 'baseresourceName',
            title: '机房名称'
        }, {
            field: 'pregName',
            title: '所属地市'
        }, {
            field: 'regName',
            title: '所属区县'
        }, {
            field: 'roomOwner',
            title: '机房归属',
            formatter: fmtProperty
        }, {
            field: 'baseresourceState',
            title: '机房状态',
            formatter: fmtState
        }, {
            field: 'baseresourceType',
            title: '资源类型',
            formatter: function (value, row, index) {
                return '机房';
            }
        }, {
	        field: 'dataFrom',
	        title: '数据来源',
	        formatter: fmtDataFrom
        }],
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success != "1"){
		            alertModel(res.msg);
				}
				showTableList = res.obj.list;
			}
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.list //数据
	         };
		}
	});
}
/**
 * 增加数据
 */
function add() {
	operate_type = 1;
	$("#basesiteId").html("");
	$("#dataForm")[0].reset(); // 清空表单
	$("#baseresourceCode").removeAttr("readonly");
	$('#baseresourceCode').unbind();
	$("#baseresourceCode").blur(function(){
		checkBaseresourceCode();
	});
	getAddressUpdate();
	$("#basesiteId").next("#err").html('');
	$('#EditPanel').modal(); // 弹出表单
}
/**
 * 修改数据
 */
function modify() {
	$('#EditPanel .form-horizontal span.modal-error').remove();
	operate_type = 2;
	var rows = getSelectItem(1);
	if(rows == null){
		return;
	}
	if(rows[0].auditingState == 9 || rows[0].auditingState == 0){
		alertModel("已进入流程，无法修改");
		return;
	}
	$('#baseresourceCode').unbind();
	$("#baseresourceCode").attr("readonly",true);
	var id = rows[0].baseresourceId;
	$.ajax({
		url : '../resource/one',
		data : {
			id : id
		},
		type : 'get',
		dataType : 'json',
		success : function(list) {
			if(list == null){
				alertModel("数据无法获取!");
				return false;
			}
			// 请求成功时
			var item = list.Obj;
			$("#baseresourceId").val(item.baseresourceId);
			$("#baseresourceCuid").val(item.baseresourceCuid);
			$("#baseresourceCode").val(item.baseresourceCode);
			$("#baseresourceName").val(item.baseresourceName);
			$("#baseresourceAddress").val(item.baseresourceAddress);
			$("#baseresourceArea").val(item.baseresourceArea);
			$("#baseresourceCategory").val(item.baseresourceCategory);
			$("#baseresourceState").val(item.baseresourceState);
			$("#baseresourceOpendate").val(item.baseresourceOpendate);
			$("#roomOwner").val(item.roomOwner);
			$("#roomProperty").val(item.roomProperty);
			$("#roomShare").val(item.roomShare);
			$("#basesiteId").empty();
			var str = "<option value='"+ item.basesiteId +"'>"+item.basesiteName + "</option>";
			$("#basesiteId").append(str);
			$("#baseresourceLongitude").val(item.baseresourceLongitude);
			$("#baseresourceLatitude").val(item.baseresourceLatitude);
			$("#airconditionerPower").val(item.airconditionerPower);
			$("#prvId").val(item.prvId);
			$("#prvSname").val(item.prvSname);
			$("#regName").val(item.regName);
			$("#pregName").val(item.pregName);
			$("#dataFrom").val(item.dataFrom);
			$("#auditingState").val(item.auditingState);
			$("#auditingUserId").val(item.auditingUserId);
			$("#auditingDate").val(item.auditingDate);
			
			getAddressUpdate(item.pregId,item.regId);
			
			$('#EditPanel').modal();
		},
		error : function() {
			alertModel("请求异常");
		}
	})
}

function getAddressUpdate(citySelect,villageSelect){
	$.ajax({
		type : "post",
		url :"../../common/region/getAddressElec",
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		success : function(value) {
			if(value != null){
				sysReguins = value.obj;
				if(sysReguins!=null){
					$('#pregId').empty();
					$('#regId').empty();
					var strCity = "<option value=''>--选择地市--</option>";
					$.each(sysReguins, function (i, item){
						strCity += "<option value='" +item.regId+"' "+(citySelect&&citySelect==item.regId?"selected='true'":"")+">"+item.regName+ "</option>";
					});

					$("#pregId").append(strCity);

					$("#regId").empty();
					var strReg = "<option value=''>--选择区县--</option>";
					if($("#pregId").val()!=""){
						$.each(sysReguins, function (i, item){
							if(item.regId==$("#pregId").val()){
								$.each(item.child, function (j, itemchild){
									strReg += "<option value='" + itemchild.regId+"' "+(villageSelect&&villageSelect==itemchild.regId?"selected='true'":"")+">"+itemchild.regName+ "</option>";
								});
							}
						});
					}
					$("#regId").append(strReg);
					//绑定联动事件 修改人zhujj
					$("#pregId").change(function(){
						$("#regId").empty();
						var strReg = "<option value=''>--选择区县--</option>";
						if($("#pregId").val()!=""){
							$.each(sysReguins, function (i, item){
								if(item.regId==$("#pregId").val()){
									$.each(item.child, function (j, itemchild){
										strReg += "<option value='" + itemchild.regId+"' "+(villageSelect&&villageSelect==itemchild.regId?"selected='true'":"")+">"+itemchild.regName+ "</option>";
									});
								}
							});
						}
						$("#regId").append(strReg);
					});
				}
			}
		},
		error : function(data) {
			alertModel('获取用户地区信息失败!');
		}
	});
}

/**
 * 删除数据
 */
function rmData() {
	var rows = getSelectItem(0);
	var containAuditRecord = false;
	if(rows == null){
		return;
	}
	for (var i = 0; i < rows.length; i++) {
		delete rows[i]["0"];
		if(rows[i].auditingState == 9 || rows[i].auditingState == 0){
			containAuditRecord = true;
		}
	}
	if (containAuditRecord){
		alertModel("包含已进入流程数据，无法删除!");
		return;
	}
	var submitData = JSON.stringify(rows);
	if (confirm("确定删除所选项目?")) {
		$.ajax({
			url : "../resource/remove",
			type : "post",
			dataType : 'json',
			contentType : "application/json;charset=UTF-8",
			data : submitData,
			success : function(data) {
				findRoom();
				alertModel('删除成功!');
				$('#controlAll').attr("checked", false);
			},
			error : function(data) {
				alertModel('删除失败!');
			}
		});
	}
};
/**
 * 提交审核
 * @returns
 */
function submitAudit(){
	var rows = getSelectItem(0);
	for(var i=0;i<rows.length;i++){
		if(rows[i].auditingState == 0){
			alertModel('已审核成功!');
			return;
		}
		if(rows[i].auditingState == 8){
			alertModel('此数据为审核不通过，请先修改再提交审核!');
			return;
		}
		if(rows[i].auditingState == 9){
			alertModel('此数据正在审核中!');
			return;
		}
		
	}
	queryFirstUsersByProcDefKey(BaseResourceAudit.procDefKey,"startActFlow",rows[0].regId);
}

/**
* 提交流程，开始审核流程
*/
function startActFlow(){
	$('#firstUsers').modal("hide");
	var rows = getSelectItem(0);
	var ids = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rows.length; s++) {
		var row = rows[s];
		ids.push(row.baseresourceId);
	}
	var nextUserId=$("input[name='userChecked']:checked").val();
	var datasObjs = new Array();
	for(var i = 0; i < ids.length; i++){
		var id = ids[i];
		var obj={"id":id,"nextUserId":nextUserId};
		datasObjs.push(obj);
	}
	var objButton=$(this);    
	$.ajax({
		url : '../resource/submitaudit',// 跳转到 action    
		data :JSON.stringify(datasObjs),
		type: "POST",
		dataType: "JSON",
		contentType : 'application/json;charset=utf-8',
		beforeSend:function(){//触发ajax请求开始时执行    
			objButton.val('提交审核中').attr('disabled',true);//改变提交按钮上的文字并将按钮设置为不可点击    
		},    
		success: function (back) {
			if(back != null){
				data = back.obj;
				if(data != null){
					$("#commit2").attr("disabled",false);
					alertModel("提交审核成功！");
					findRoom();
				}
			}
		},
		error: function () { 
			$("#commmit2").attr("disabled",false);
			alertModel("获取区县信息异常！");
		},
		complete:function(){//ajax请求完成时执行    
			  objButton.val('提交审核').attr('disabled',false);//改变提交按钮上的文字并将按钮设置为可以点击    
		}    
	});
}
/**
 * 增加提交form表单
 */
function formSubmit() {
	if(!checkForm()){
		return false;
	}
	$("input[name='regName']").val($("#regId option:selected").text());
	$("input[name='pregName']").val($("#pregId option:selected").text());
	$('#basesiteId').removeAttr("disabled");
	var data = $('#dataForm').serializeObject();
	data.baseresourceOpendate = $("#baseresourceOpendate").val();
	var submitData = JSON.stringify(data);
	if (operate_type == 1) {
		checkBaseresourceCode();
		if(flag){
			return;
		}
		$("#saveSet").attr("disabled",true);
		$.post("../resource/save" , {"param":submitData} ,function(result){
			// 请求成功时
			if (result != null) {
				alertModel(result.msg);
	    		if(result.success == "1"){
					$('#EditPanel').modal('hide');
					findRoom();
	    		}
			}
			$("#saveSet").attr("disabled",false);
		});
	} else {
		$("#saveSet").attr("disabled",true);
		$.post("../resource/modify" , {"param":submitData} ,function(result){
			// 请求成功时
			if (result != null) {
				alertModel(result.msg);
	    		if(result.success == "1"){
					$('#EditPanel').modal('hide');
					findRoom();
	    		}
			}
			$("#saveSet").attr("disabled",false);
		});
	}
}
//导出
/*function exportData(){
	$.ajax({
		url : '../resource/export',// 跳转到 action
		type : 'get',
		contentType : "application/json;charset=UTF-8",
		dataType : 'json',
		data : {
			city : $("#city option:selected").val(),
			region : $("#region option:selected").val(),
			reg : $("#reg").val(),
			property : $("#property option:selected").val(),
			auditStatus : $("#auditStatus option:selected").val(),
			status : $("#status option:selected").val(),
			resType : resType,
			queryType : queryType
		},
		beforeSend: function () {
		    $("#loading").html("<img src=\""+ +"/asserts/image/loading-b.gif\"/>");
		},
		success : function(data) {
			$("#loading").html("");
			if (data != null) {
				window.open(data.Obj);
				alertModel(data.msg);
			}
		},
		error : function() {
			$("#loading").html("");
			alertModel("导出数据异常");
		}
	});
}*/

//导出
function exportData(data){
	var para="reg="+$('#reg').val()+"&city="+$("#city option:selected").val();	
	para+="&property="+$('#property option:selected').val()+"&auditStatus="+$("#auditStatus option:selected").val();
	para+="&region="+$('#region option:selected').val()+"&status="+$("#status option:selected").val()+"&queryType="+queryType+"&resType="+resType;
	window.open("../resource/export?"+para);
}

function importData(){
	$("#fileForm")[0].reset(); // 清空表单
	$('#filePanel').modal(); // 弹出表单
}

function uploadExcel(){
	var fileURL=$("#file_url").val();
	if(fileURL==null){
		alert("请选择上传文件!");
	}
	else{
		var formobj =  document.getElementById("fileForm");
		var formdata = new FormData(formobj);
		$.ajax({
			type : "POST",
			url : "../resource/import",
			data :formdata,
			async: false,  
	        cache: false,  
	        contentType: false,  
	        processData: false,
	        beforeSend: LoadFunction, //加载执行方法    
	        error: erryFunction,  //错误执行方法    
	        success: succFunction //成功执行方法      
		});
	    function LoadFunction() {  
	        $("#list").html('上传中...');  
	    }  
	    function erryFunction() {  
	    	 alert("上传文件超过500MB,无法正常上传！");
	    }  
	    function succFunction(ret) {  
			var json = eval(ret);
			if (json.status == 0) {
				$('#filePanel').modal('hide');
				findRoom();
			}
	    } 
	}
}
 
function siteSelectBack(siteData){
	$("#basesiteId").html("");
	$("#siteRegId").val("");
	$("#siteRegId").val(siteData[0].regId);
	var str = "<option value='"+ siteData[0].basesiteId +"'>"+siteData[0].basesiteName + "</option>";
	$("#basesiteId").append(str);
}

function checkForm(){
	var baseresourceCuid = $("input[name='baseresourceCuid']").val();
	if (baseresourceCuid == "") {
		alertModel("机房CID不能为空！");
		return false;
	}
	var baseresourceCode = $("input[name='baseresourceCode']").val();
	if (baseresourceCode == "") {
		alertModel("机房编码不能为空！");
		return false;
	}
	var baseresourceName = $("input[name='baseresourceName']").val();
	if (baseresourceName == "") {
		alertModel("机房名称不能为空！");
		return false;
	}
	var regId = $("#regId").val();
	var sateRegId = $("#siteRegId").val();
	if (regId == "") {
		alertModel("区县信息不能为空！");
		return false;
	}
	if(regId != "" && sateRegId != "") {
		if(regId != sateRegId){
			alertModel("站点区县应和资源点相同！");
			return false;
		}
	}
	if($('#basesiteId').val() == null){
		$("#basesiteId").next("#err").html("<img src=\"../../../image/error.png\"/>必选！");
		$("#basesiteId").next("#err").css({"color":"red","float":"left"});
		return false;
	}else{
		$("#basesiteId").next("#err").html("<img src=\"../../../image/right_1.png\"/>验证成功");
		$("#basesiteId").next("#err").css({"color":"green","float":"left"});
		return true;
	}
	return true;
}


function clickFormatter(value, row, index){
	var baseresourceId=row.baseresourceId;
	var baseresourceCode=row.baseresourceCode;
	return '<a href="javascript:queryDetails(\''+baseresourceId+'\');" >'+baseresourceCode+'</a>';
}


function queryDetails(baseresourceId){

	//流转记录
	histoicFlowList(BaseResourceAudit.tableName,baseresourceId);
	$('#detail').modal(); 
	$.ajax({
		url : '../resource/queryDetails',
		type : 'get',
		data : {
			id:baseresourceId
		},
 	    dataType : 'json',
		success : function(result) {
			// 请求成功时
			if (result != null) {
				var item = result.Obj;
				switch(item.baseresourceCategory){
				case "1":
					baseresourceCategorys = "交换机房";
					break;
				case "2": 
					baseresourceCategorys ="数据机房";
					break;
				case "3":
					baseresourceCategorys ="动力机房";
					break;
				case "4":
					baseresourceCategorys = "传输机房";
					break;
				case "5":
					baseresourceCategorys = "无线机房";
					break;
				case "6":
					baseresourceCategorys = "综合机房";
					break;
				default:
					baseresourceCategorys ="";
					break;
				}
				
				switch(item.baseresourceState){
				case 1:
					baseresourceStates = "在网";
					break;
				case 2: 
					baseresourceStates ="工程";
					break;
				case 3:
					baseresourceStates ="退网";
					break;
				default:
					baseresourceStates ="";
					break;
				}
				
				switch(item.roomOwner){
				case "1":
					roomOwners = "移动维护";
					break;
				case "2": 
					roomOwners ="铁塔维护";
					break;
				default:
					roomOwners ="";
					break;
				}
				
				switch(item.roomProperty){
				case 1:
					roomPropertys = "自建";
					break;
				case 2: 
					roomPropertys ="共建";
					break;
				case 3:
					roomPropertys ="租用";
					break;
				case 4:
					roomPropertys ="购买";
					break;
				default:
					roomPropertys ="";
					break;
				}
				
				switch(item.roomShare){
				case "1":
					roomShares = "联通";
					break;
				case "2": 
					roomShares ="电信";
					break;
				case "3":
					roomShares ="联通电信";
					break;
				default:
					roomShares ="";
					break;
				}
				$("#baseresourceCuid_Details").val(item.baseresourceCuid);
				$("#baseresourceCode_Details").val(item.baseresourceCode);
				$("#baseresourceName_Details").val(item.baseresourceName);
				$("#baseresourceAddress_Details").val(item.baseresourceAddress);
				$("#baseresourceArea_Details").val(item.baseresourceArea);
				$("#baseresourceCategory_Details").val(baseresourceCategorys);
				$("#baseresourceState_Details").val(baseresourceStates);
				$("#baseresourceOpendate_Details").val(item.baseresourceOpendate);
				$("#regId_Details").val(item.regName);
				$("#pregId_Details").val(item.pregName);
				$("#roomOwner_Details").val(roomOwners);
				$("#roomProperty_Details").val(roomPropertys);
				$("#roomShare_Details").val(roomShares);
				$("#baseresourceLongitude_Details").val(item.baseresourceLongitude);
				$("#baseresourceLatitude_Details").val(item.baseresourceLatitude);
				$("#airconditionerPower_Details").val(item.airconditionerPower);
				$("#baseresourceAreaDetail").val(item.baseresourceAddress);
				$("#baseresourceStateDetail").val(item.baseresourceState);
				$("#basesiteId_Details").val(item.basesiteName);
				$("#detail form input").attr('readonly','readonly');
				$('#tbs tr:gt(0)').remove();
				var s = '';
				listData = item.datBasestationVO;
				if(listData != null){
					for (var i = 0; i < listData.length; i++) {
						var basestationOpendate = new Date(listData[i].basestationOpendate).format("yyyy-MM-dd");
						switch(listData[i].basestationCategory){
						case 1:
							basestationCategory = "AP";
							break;
						case 2: 
							basestationCategory ="2G基站";
							break;
						case 3:
							basestationCategory ="3G基站";
							break;
						case 4:
							basestationCategory ="4G基站";
							break;
						case 5:
							basestationCategory ="WLAN交换机";
							break;
						case 6:
							basestationCategory ="RRU";
							break;
						case 7:
							basestationCategory ="直放站";
							break;
						case 8:
							basestationCategory ="分布系统";
							break;
						case 9:
							basestationCategory ="ONU";
							break;
						case 10:
							basestationCategory ="OLT";
							break;
						case 11:
							basestationCategory ="PTN";
							break;
						case 12:
							basestationCategory ="OTN";
							break;
						default:
							basestationCategory ="";
							break;
						}
						s = "<tr style='text-align: center;'><td>"
								+ (basestationCategory==null?"":basestationCategory)
								+ "</td><td>"
								+ (listData[i].basestationCode==null?"":listData[i].basestationCode)
								+ "</td><td>"
								+ (listData[i].basestationName==null?"":listData[i].basestationName)
								+ "</td><td>"
								+ (selectDeviceName(listData[i].basestationVendor))
								+ "</td><td>" 
								+ (basestationOpendate==null?"":basestationOpendate) 
								+ "</td><td>" 
								+ (listData[i].basestationModel==null?"":listData[i].basestationModel)
								+ "</td><td>" 
								+ (listData[i].basestationPower==null?"":listData[i].basestationPower)
								+ "</td></tr>";
						$('#tbs').append(s);
					}
				}
			}else{
				alertModel(result.msg);
			}
		},
		error : function() {
			alertModel("请求失败！");
		}
	})
	
}

//用于判断机房编码
var flag = true;
//判断机房编码唯一性验证
function checkBaseresourceCode(){
	var baseresourceCode = $("#baseresourceCode").val();
	if(baseresourceCode == "" || baseresourceCode == null){
		alertModel("编码不能为空");
		return;
	}else{
		$.ajax({
			type : "post",
			url : "../resource/checkBaseresourceCode",
			data : {
				baseresourceCode : baseresourceCode,
			},
			dataType : 'json',
			success : function(value) {
				if(value != null){
					data = value.obj;
					if(data != null && data.length > 0){
						alertModel('编码已存在，请重新输入!');
						flag = true;
						return;
					}else{
						flag = false;
					}
				}
			},
			error : function(data) {
				alertModel('查询失败!');
			}
		});
	}
}
