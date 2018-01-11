

//初始化
$(function(){
	loadCode();
	getAddressUpdate();
});

function back(){
	localStorage.removeItem('billaccountInfo');
	localStorage.removeItem('billaccountId');
	javascript:history.back(-1);
}

//判断报账点编码
//var flag = true;
function save(){
	var billaccountId = $("#billaccountId").val();
	var billaccountCode = $("#billaccountCode").val();
	var billaccountName = $("#billaccountName").val();
	var billaccountState = $("#billaccountState").val();
	var pregId = $("#modifyCity").val();
	var regId = $("#modifyRegion").val();
	var billaccountType = $("#billaccountType").val();
	var planDate = $("#planDate").val();
	var calcMulti = $("#calcMulti").val();
	var billaccountNote = $("#billaccountNote").val();
//	checkBillaccountCode();
/*	if(flag){
		return;
	}*/
	if(billaccountName==null || billaccountName==""){
		alert("报账点名称为空，请输入");
		$("#billaccountName").focus();
		return;
	}
	if(pregId==null || pregId==""){
		alert("报账点所属地市为空，请输入");
		return;
	}
	if(regId==null || regId==""){
		alert("报账点所属区县为空，请输入");
		return;
	}
	/*if(calcMulti==null || calcMulti==""){
		alert("报账点计量倍数为空，请输入");
//		$("#calcMulti").focus();
		return;
	}*/
	$.post("saveOrUpdate" , {"billaccountId":billaccountId,"billaccountCode":billaccountCode,
		"billaccountName":billaccountName,"pregId":pregId,"regId":regId,
		"billaccountType":billaccountType,"planDate":planDate,"calcMulti":0,
		"billaccountNote":billaccountNote,"billaccountState":billaccountState , "auditingState":-1
	},function(data){
		localStorage.removeItem('billaccountInfo');
		localStorage.removeItem('billaccountId');
		var newBillaccountId=data.obj.billaccountId;
		var reg=data.obj.regId;
		window.location.href = 'update.html?billaccountId='+newBillaccountId+'&regId='+reg+'';
	});
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
					$('#modifyCity').empty();
					$('#modifyRegion').empty();
					var strCity = "<option value=''>--选择地市--</option>";
					$.each(sysReguins, function (i, item){
						strCity += "<option value='" +item.regId+"' "+(citySelect&&citySelect==item.regId?"selected='true'":"")+">"+item.regName+ "</option>";
					});

					$("#modifyCity").append(strCity);

					$("#modifyRegion").empty();
					var strReg = "<option value=''>--选择区县--</option>";
					if($("#modifyCity").val()!=""){
						$.each(sysReguins, function (i, item){
							if(item.regId==$("#modifyCity").val()){
								$.each(item.child, function (j, itemchild){
									strReg += "<option value='" + itemchild.regId+"' "+(villageSelect&&villageSelect==itemchild.regId?"selected='true'":"")+">"+itemchild.regName+ "</option>";
								});
							}
						});
					}
					$("#modifyRegion").append(strReg);
					//绑定联动事件 修改人zhujj
					$("#modifyCity").change(function(){
						$("#modifyRegion").empty();
						var strReg = "<option value=''>--选择区县--</option>";
						if($("#modifyCity").val()!=""){
							$.each(sysReguins, function (i, item){
								if(item.regId==$("#modifyCity").val()){
									$.each(item.child, function (j, itemchild){
										strReg += "<option value='" + itemchild.regId+"' "+(villageSelect&&villageSelect==itemchild.regId?"selected='true'":"")+">"+itemchild.regName+ "</option>";
									});
								}
							});
						}
						$("#modifyRegion").append(strReg);
					});
				}
			}
		},
		error : function(data) {
			alertModel('获取用户地区信息失败!');
		}
	});
}

function checkBillaccountCode(){
	var billaccountCode = $("#billaccountCode").val();
	if(billaccountCode==null || billaccountCode==""){
		alert("报账点编码为空，请输入");
		return;
	}else{
		$.ajax({
			type : "post",
			url : "checkBillaccountCode",
			data : {
				billaccountCode : billaccountCode,
			},
			dataType : 'json',
			success : function(value) {
				if(value != null){
					data = value.obj;
					if(data != null && data.length > 0){
						alertModel('报账点编码已存在，请重新输入!');
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

function loadCode(){
	$.ajax({
		type : "post",
		url : "queryBillaccountCode",
		data : '',
		dataType : 'json',
		success : function(data) {
			if(data != null){
				$("#billaccountCode").val(data.obj);
			}
		},
		error : function(data) {
			alertModel('查询失败!');
		}
	});
	
	
}
