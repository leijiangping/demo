var operate_type = 1;// 1 新增，2 修改
var queryType = 0;
var curPageNum = 0;
var cityList = null;
var showTableList = null;
$(document).ready(function() {
	initialize();
});
/**
 * 初始化加页面
 */
function initialize() {
	curPageNum = 1;
//	initCity();
	getAddress();
	findSupplier();
}
/**
 * 列表查询
 */
function findSupplier() {
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "list", // 获取数据的地址
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
					city : $("#city option:selected").val(),
					region : $("#region option:selected").val(),
					supplierReg : $("#supplierReg").val(),
					queryType : queryType,
					cur_page_num: params.pageNumber,    
					page_count: params.pageSize,
					dataFrom : 0
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
            field: 'supplierCode',
            title: '供应商编号',
            formatter:function(value,row,index){
            	var supplierId = row.supplierId;
            	var supplierLink = '<a href="javascript:void(0)"  onclick=viewRecord("'+supplierId+'") style="white-space:nowrap">'+ value + '</a>';
            	return supplierLink;
            }
        }, {
            field: 'supplierName',
            title: '供应商名称'
        }, {
            field: 'pregName',
            title: '所属地市'
        }, {
            field: 'regName',
            title: '所属区县'
        }, {
        	field: 'isDownshare',
            title: '是否向下共享',
            formatter:function(value,row,index){
            	switch(value){
        		case 1:return '是';
        		case 0:return '否';
        	}
        	return value;
            }
        }, {
            field: 'supplierSite',
            title: '地点'
        }, {
            field: 'supplierContact',
            title: '联系人'
        }, {
            field: 'supplierTelephone',
            title: '联系电话'
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
 * 增加供应商信息
 */
function add() {
	operate_type = 1;
//	location.href = "supplierEdit.html";
	$("#dataForm")[0].reset(); // 清空表单
	getAddressUpdate();
	$('#EditPanel').modal(); // 弹出表单
}
/**
 * 修改用户信息
 */
function modify() {
	operate_type = 2;
	checkIsDownShare();
	var rows = getSelectItem(1);
	if(rows == null){
		return;
	}
	var id = rows[0].supplierId;
	$.ajax({
		url : 'one',
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
			$("input[name='supplierId']").val(item.supplierId);
			$("input[name='supplierCode']").val(item.supplierCode);
			$("input[name='supplierName']").val(item.supplierName);
			$("input[name='supplierSite']").val(item.supplierSite);
			$("input[name='supplierAddress']").val(item.supplierAddress);
			$("input[name='supplierContact']").val(item.supplierContact);
			$("input[name='supplierTelephone']").val(item.supplierTelephone);
			$("#isDownshare").val(item.isDownshare)
			$("#accountType").val(item.accountType)
			$("input[name='depositBank']").val(item.depositBank);
			$("#supplierType").val(item.supplierType)
			$("#supplierNote").val(item.supplierNote);
			$("#prvId").val(item.prvId);
			$("#prvSname").val(item.prvSname);
			$("#regName").val(item.regName);
			$("#pregName").val(item.pregName);
			
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
 * 删除用户
 */
function rmData() {
	var rows = getSelectItem(0);
	if(rows == null){
		return;
	}
	for (var i = 0; i < rows.length; i++) {
		delete rows[i]["0"];
	}
	var submitData = JSON.stringify(rows);
	console.log(submitData);
	if (confirm("确定删除所选项目?")) {
		$.ajax({
			type : "post",
			url : "remove",
			data : submitData,
			dataType : 'json',
			contentType : "application/json;charset=UTF-8",
			success : function(data) {
				findSupplier();
				alertModel('删除成功!');
				$('#controlAll').attr("checked", false);
			},
			error : function(data) {
				alertModel('删除失败!');
			}
		});
	}
};
//用户判断供应商代码唯一性
var flag = true;
function checkSupplierCode(){
	var supplierCode = $("#supplierCode").val();
	var supplierName = $("#supplierName").val();
	var supplierSite = $("#supplierSite").val();
	if (supplierCode == "") {
		alertModel("供应商编码不能为空！");
		flag = true;
		return;
	}
	else if(supplierCode == supplierName || supplierCode == supplierSite){
		alertModel("供应商编码,名称,地点(服务类别)不能为相同数值！");
		flag = true;
		return;
	}
	else
	{
		$.ajax({
			type : "post",
			async: false,
			url : "checkSupplierCode",
			data : {
				supplierCode : supplierCode,
			},
			dataType : 'json',
			success : function(value) {
				if(value != null){
					data = value.obj;
					if(data != null && data.length > 0){
						alertModel('供应商编码已存在，请重新输入!');
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

/**
 * 增加提交form表单
 */
function formSubmit() {
	var supplierName = $("input[name='supplierName']").val();
	if (supplierName == "") {
		alertModel("供应商名称不能为空！");
		return false;
	}
	var supplierSpace = $("input[name='supplierSpace']").val();
	if (supplierSpace == "") {
		alertModel("供应商地点不能为空！");
		return false;
	}
	if($("#regId").val()=="" && $("#isDownshare").val() != "1"){
		alertModel("请选择地市或区县信息或向下共享选择‘是’!");
		return;
	}
	
	var telePhone = $("input[name='supplierTelephone']").val();
	if (!checkTel(telePhone) &&  !checkPhone(telePhone)){
		alertModel("联系电话格式不正确!");
		return;
	}
	$("#isDownshare").removeAttr("disabled");
	$("input[name='regName']").val($("#regId option:selected").text());
	$("input[name='pregName']").val($("#pregId option:selected").text());
	var data = $('#dataForm').serializeObject();
	var submitData = JSON.stringify(data);
	if (operate_type == 1) {
		checkSupplierCode();
		if(flag){
			return;
		}
		$("#saveSet").attr("disabled",true);
		$.ajax({
			url : 'save',
			type : 'post',
	 	    contentType : 'application/json;charset=UTF-8',
			data : submitData,
			cache : false,
			async : true,
			success : function(result) {
				// 请求成功时
				if (result != null) {
					$('#EditPanel').modal('hide');
					alertModel(result.msg);
					findSupplier();
				}
				$("#saveSet").attr("disabled",false);
			},
			error : function() {
				alertModel("请求失败！");
				$("#saveSet").attr("disabled",false);
			}
		})
	} else {
		$("#saveSet").attr("disabled",true);
		$.ajax({
			url : 'modify',
			type : 'post',
			dataType: 'json',
	 	    contentType : 'application/json;charset=UTF-8',
			data : submitData,
			async : true,
			success : function(result) {
				// 请求成功时
				if (result != null) {
					$('#EditPanel').modal('hide');
					alertModel(result.msg);
					findSupplier();
				}
				$("#saveSet").attr("disabled",false);
			},
			error : function() {
				alertModel("请求失败！");
				$("#saveSet").attr("disabled",false);
			}
		})
	}
}

//导出
function exportData(data){
	var para="reg="+$('#supplierReg').val()+"&city="+$("#city option:selected").val();
	para+="&region="+$("#region option:selected").val()+"&queryType="+queryType+"&dataFrom=0";
	window.open("export?"+para);
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
			url : "import",
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
			alert(json.message); 
			if (json.status == 0) {
				$('#filePanel').modal('hide');
				findSupplier();
			}
	    } 
	}
}

/**
 * 查看数据信息
 */
function viewRecord(obj) {
	$.ajax({
		url : 'one',
		data : {
			id : obj
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
			$("input[name='supplierId1']").val(item.supplierId);
			$("input[name='supplierCode1']").val(item.supplierCode);
			$("input[name='supplierName1']").val(item.supplierName);
			$("input[name='supplierSite1']").val(item.supplierSite);
			$("input[name='supplierAddress1']").val(item.supplierAddress);
			$("input[name='supplierContact1']").val(item.supplierContact);
			$("input[name='supplierTelephone1']").val(item.supplierTelephone);
			$("select[name='accountType1']").val(item.accountType);
			$("select[name='supplierType1']").val(item.supplierType);
			$("#pregId1").val(item.pregName);
			$("#regId1").val(item.regName);
			$("#isDownshare1").val(item.isDownshare)
			$("input[name='depositBank1']").val(item.depositBank);
			$("#supplierType1").val(item.supplierType);
			$("#supplierNote1").val(item.supplierNote);

			$('#ViewPanel input').attr("readonly","true");
			$('#ViewPanel textarea').attr("readonly","true");
			$('#ViewPanel select').attr("disabled","true");
			$('#ViewPanel').modal();
		},
		error : function() {
			alertModel("请求异常");
		}
	})
}

function checkIsDownShare(){
	var regId = $("#regId").val();
	if(regId == ""){
		$("#isDownshare").removeAttr("disabled");
	}else{
		$("#isDownshare").val(0);
		$("#isDownshare").attr("disabled",true);
	}
}

//校验座机
function checkTel(phoneNumber){
	if(!/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(phoneNumber)){
		return false;
	}
	return true;
}
//校验手机
function checkPhone(phoneNumber){ 
    if(!(/^1(3|4|5|7|8)\d{9}$/.test(phoneNumber))){ 
        return false; 
    } 
    return true;
}