(function($) {
    $.fn.extend({
        serializeObject : function() {
            var o = {};
            var a = this.serializeArray();
            $.each(a, function() {
                if (o[this.name]) {
                    if (!o[this.name].push) {
                        o[this.name] = [ o[this.name] ];
                    }
                    o[this.name].push(this.value || '');
                } else {
                    o[this.name] = this.value || '';
                }
            });
            return o;
        }
    });

})(jQuery)

function getSelectItem(type){
	var selectItems = $('#tb').bootstrapTable('getSelections');
	if(type == 1){
		if(selectItems.length != 1){
			alertModel("请选择一条数据");
			return null;
		}
	}else{
		if(selectItems.length < 1){
			alertModel("请选择至少一条数据");
			return null;
		}
	}
	return selectItems;
}

//function initCity() {
//	$("#city").html("");
//    $("#region").html("");
//    var str = "<option value=''>-选择地市-</option>";
//    $.ajax({
//        type: "GET",
//        url: "../../common/region/citys",
//        dataType: "JSON",
//        success: function (data) {
//        	cityList = data.Obj;
//            //从服务器获取数据进行绑定
//            $.each(data.Obj, function (i, item) {
//                str += "<option value=" + item.regId + ">" + item.regName + "</option>";
//            })
//            //将数据添加到省份这个下拉框里面
//            $("#city").append(str);
//            $("#region").append("<option value=''>-选择区县-</option>");
//        },
//        error: function () { alert("获取地市信息异常！"); }
//    });
//}
//function regionBind(){
//	var city = $("#city option:selected").val();
//    //判断市这个下拉框选中的值是否为空
//    if (city == "") {
//        return;
//    }
//    $("#region").html("");
//    var str = "<option value=''>-选择区县-</option>";
//    //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
//    $.ajax({
//        type: "GET",
//        url: "../../common/region/regions",
//        data: {"cityId": city},
//        dataType: "JSON",
//        success: function (data) {
//            //从服务器获取数据进行绑定
//            $.each(data.Obj, function (i, item) {
//                str += "<option value=" + item.regId + ">" + item.regName + "</option>";
//            });
//            //将数据添加到省份这个下拉框里面
//            $("#region").append(str);
//        },
//        error: function () { alert("获取区县信息异常！"); }
//    });
//}
//
//function pregIdBind(){
//    $("#pregId").html("");
//    $("#regId").html("");
//    var str = "<option value=''>-请选择-</option>";
//    $.ajax({
//        type: "GET",
//        url: "../../common/region/citys",
//        dataType: "JSON",
//        success: function (data) {
//            //从服务器获取数据进行绑定
//            $.each(data.Obj, function (i, item) {
//                str += "<option value=" + item.regId + ">" + item.regName + "</option>";
//            })
//            //将数据添加到省份这个下拉框里面
//            $("#pregId").append(str);
//            $("#regId").append("<option value=''>-请选择-</option>");
//        },
//        error: function () { alert("获取地市信息异常！"); }
//    });
//}
//function regIdBind(id){
//	var city = $("#pregId option:selected").val();
//    //判断市这个下拉框选中的值是否为空
//    if (city == "") {
//        return;
//    }
//    $("#regId").html("");
//    var str = "<option value=''>-请选择-</option>";
//    //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
//    $.ajax({
//        type: "GET",
//        url: "../../common/region/regions",
//        data: {"cityId": city},
//        dataType: "JSON",
//        success: function (data) {
//            //从服务器获取数据进行绑定
//            $.each(data.Obj, function (i, item) {
//                str += "<option value=" + item.regId;
//                if(id != null && id == item.regId){
//                	str += " selected='selected'";
//                }
//                str += ">" + item.regName + "</option>";
//            });
//            //将数据添加到省份这个下拉框里面
//            $("#regId").append(str);
//        },
//        error: function () { alert("获取区县信息异常！"); }
//    });
//}
//
//function pregIdBind1(){
//    $("#pregId1").html("");
//    $("#regId1").html("");
//    var str = "<option value=''>-请选择-</option>";
//    $.ajax({
//        type: "GET",
//        url: "../../common/region/citys",
//        dataType: "JSON",
//        async:false,
//        success: function (data) {
//            //从服务器获取数据进行绑定
//            $.each(data.Obj, function (i, item) {
//                str += "<option value=" + item.regId + ">" + item.regName + "</option>";
//            })
//            //将数据添加到省份这个下拉框里面
//            $("#pregId1").append(str);
//            $("#regId1").append("<option value=''>-请选择-</option>");
//        },
//        error: function () { alert("获取地市信息异常！"); }
//    });
//}
//function regIdBind1(id){
//	var city = $("#pregId1 option:selected").val();
//    //判断市这个下拉框选中的值是否为空
//    if (city == "") {
//        return;
//    }
//    $("#regId1").html("");
//    var str = "<option value=''>-请选择-</option>";
//    //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
//    $.ajax({
//        type: "GET",
//        url: "../../common/region/regions",
//        data: {"cityId": city},
//        dataType: "JSON",
//        async:false,
//        success: function (data) {
//            //从服务器获取数据进行绑定
//            $.each(data.Obj, function (i, item) {
//                str += "<option value=" + item.regId;
//                if(id != null && id == item.regId){
//                	str += " selected='selected'";
//                }
//                str += ">" + item.regName + "</option>";
//            });
//            //将数据添加到省份这个下拉框里面
//            $("#regId1").append(str);
//        },
//        error: function () { alert("获取区县信息异常！"); }
//    });
//}
//function initSiteCity() {
//	$("#siteCity").html("");
//    $("#siteRegion").html("");
//    var str = "<option value=''>-选择地市-</option>";
//    $.ajax({
//        type: "GET",
//        url: "../../common/region/citys",
//        dataType: "JSON",
//        success: function (data) {
//        	cityList = data.Obj;
//            //从服务器获取数据进行绑定
//            $.each(data.Obj, function (i, item) {
//                str += "<option value=" + item.regId + ">" + item.regName + "</option>";
//            })
//            //将数据添加到省份这个下拉框里面
//            $("#siteCity").append(str);
//            $("#siteRegion").append("<option value=''>-选择区县-</option>");
//        },
//        error: function () { alert("获取地市信息异常！"); }
//    });
//}
//function siteRegionBind(){
//	var city = $("#siteCity option:selected").val();
//    //判断市这个下拉框选中的值是否为空
//    if (city == "") {
//        return;
//    }
//    $("#siteRegion").html("");
//    var str = "<option value=''>-选择区县-</option>";
//    //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
//    $.ajax({
//        type: "GET",
//        url: "../../common/region/regions",
//        data: {"cityId": city},
//        dataType: "JSON",
//        success: function (data) {
//            //从服务器获取数据进行绑定
//            $.each(data.Obj, function (i, item) {
//                str += "<option value=" + item.regId + ">" + item.regName + "</option>";
//            });
//            //将数据添加到省份这个下拉框里面
//            $("#siteRegion").append(str);
//        },
//        error: function () { alert("获取区县信息异常！"); }
//    });
//}
/* 枚举   */
function fmtAuditState(value, row, index){
	switch(value){
		case -1: return "未提交";
		case 9: return "审核中";
		case 8: return "审核不通过";
		case 0: return "审核通过";
	}
	return value;
}
function fmtState(value, row, index){
	switch(value){
		case -1: return "已删除";
		case 1: return "在网";
		case 2: return "工程";
		case 3: return "退网";
	}
	return value;
}
function fmtDataFrom(value, row, index){
	switch(value){
		case 0: return "系统录入";
		case 1: return "系统导入";
		case 2: return "接口采集";
	}
	return value;
}
function fmtDevType(value, row, index){
	switch(value){
		case 1: return "AP";
		case 2: return "2G基站";
		case 3: return "3G基站";
		case 4: return "4G基站";
		case 5: return "WLAN交换机";
		case 6: return "RRU";
		case 7: return "直放站";
		case 8: return "分布系统";
		case 9: return "ONU";
		case 10: return "OLT";
		case 11: return "PTN";
		case 12: return "OTN";
	}
	return value;
}
function fmtAccountType(e){
	if(e == 0){
		return "公户";
	}else if(e == 1){
		return "私户";
	}else{
		return "-";
	}
}
function fmtSupplierType(e){
	if(e == 1){
		return "业主";
	}else if(e == 2){
		return "供电局";
	}else if(e == 3){
		return "经营性第三方公司";
	}else if(e == 4){
		return "代收性第三方公司";
	}else{
		return "-";
	}
}
function fmtProperty(value, row, index){
	switch(value){
		case "1": return "移动维护";
		case "2": return "铁塔维护";
	}
}
function fmtContractState(value, row, index){
	switch(value){
		case -2: return "无效";
		case -1: return "删除";
		case 0: return "正常";
		case 1: return "起草";
		case 2: return "履行完毕";
		case 3: return "审批中";
		case 4: return "审批完毕";
		case 8: return "合同异动";
		case 9: return "停用";
	}
	return value;
}

/**
 * 获取用户所有权限 的地市 区县信息
 * @param title 名称 例如：供应商地市，传入title为供应商
 * @param async 同步异步设置,默认：false-异步;true-同步。不传则默认异步
 * @param CitySelect 选中地市 例如：回显选中，传入地市ID，不传则不选中
 * @param VillageSelect 选中县区 例如：回显选中，传入区县ID，不传则不选中
 */
function getAddress(title,async,citySelect,villageSelect){
	$.ajax({
		type : "post",
		url :"../../common/region/getAddressElec",
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		async:(async?true:false),
		success : function(value) {
			if(value != null){
				sysReguins = value.obj;
				if(sysReguins!=null){
					$('#city').empty();
					$('#region').empty();
					var strCity = "<option value=''>--选择"+(title?title:"")+"地市--</option>";
					$.each(sysReguins, function (i, item){
						strCity += "<option value='" +item.regId+"' "+(citySelect&&citySelect==item.regId?"selected='true'":"")+">"+item.regName+ "</option>";
					});

					$("#city").append(strCity);

					var strReg = "<option value=''>--选择"+(title?title:"")+"区县--</option>";
					$("#region").append(strReg);
					//绑定联动事件 修改人zhujj
					$("#city").change(function(){
						$("#region").empty();
						strReg = "<option value=''>--选择"+(title?title:"")+"区县--</option>";
						if($("#city").val()!=""){
							$.each(sysReguins, function (i, item){
								if(item.regId==$("#city").val()){
									$.each(item.child, function (j, itemchild){
										strReg += "<option value='" + itemchild.regId+"' "+(villageSelect&&villageSelect==itemchild.regId?"selected='true'":"")+">"+itemchild.regName+ "</option>";
									});
								}
							});
						}
						$("#region").append(strReg);
					});
				}
			}
		},
		error : function(data) {
			alertModel('获取用户地区信息失败!');
		}
	});
}
var deviceNameList;
function selectDeviceName(value){
	if(value != null && value != ''){
		if(deviceNameList == null){
			$.ajax({
				url :"../../basedata/device/selectDevice",
				type : 'post',
				contentType : "application/json;charset=UTF-8",
				dataType : 'json',
				async : false,
				data : {
					
				},
				success : function(data) {
					if (data != null) {
						deviceNameList = data.obj;
					}
				},
				error : function() {
					alertModel("查询失败");
				}
			});
			for(var i= 0 ; i < deviceNameList.length ; i++){
				if(value == deviceNameList[i].devicefactoryId){
					return deviceNameList[i].devicefactoryName
				}
			}
		}else{
			for(var i= 0 ; i < deviceNameList.length ; i++){
				if(value == deviceNameList[i].devicefactoryId){
					return deviceNameList[i].devicefactoryName
				}
			}
		}
	}else{
		return "-";
	}
}