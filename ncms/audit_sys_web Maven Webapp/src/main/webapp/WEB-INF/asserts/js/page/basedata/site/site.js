var operate_type = 1;// 1 新增，2 修改
var queryType = 0;
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
	findSite();
}
/**
 * 列表查询
 */
function findSite() {
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
		minimumCountColumns : 1, // 最少允许的列数
		clickToSelect : true, // 是否启用点击选中行
		pageList : [ 10, 25, 50, 100, 500 ], // 记录数可选列表
		search : false, // 是否启用查询
		sidePagination : "server", // 表示服务端请求
		// 设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
		// 设置为limit可以获取limit, offset, search, sort, order
		queryParamsType : "undefined",
		queryParams : function queryParams(params) { // 设置查询参数
			var param = {
				siteReg : $("#siteReg").val(),
				property : $("#property option:selected").val(),
				auditStatus : $("#auditStatus option:selected").val(),
				status : $("#status option:selected").val(),
				city : $("#city option:selected").val(),
				region : $("#region option:selected").val(),
				queryType : queryType,
				cur_page_num : params.pageNumber,
				page_count : params.pageSize
			};
			return param;
		},
		columns : [ {
			checkbox : true
		}, {
			field : 'auditingState',
			title : '审核状态',
			formatter : fmtAuditState
		}, {
			field : 'basesiteCode',
			title : '站点编号',
			formatter : clickFormatter
		}, {
			field : 'basesiteName',
			title : '站点名称'
		}, {
			field : 'pregName',
			title : '所属地市'
		}, {
			field : 'regName',
			title : '所属区县'
		}, {
			field : 'basesiteBelong',
			title : '站点归属',
			formatter : basesiteBelongFormt
		}, {
			field : 'basesiteState',
			title : '站点状态',
			formatter : fmtState
		}, {
			field : 'basesiteType',
			title : '资源类型',
			formatter : function(value, row, index) {
				return '站点';
			}
		} ],
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler : function(res) {
			if (res != null) {// 报错反馈
				if (res.success != "1") {
					alertModel(res.msg);
				}
				showTableList = res.obj.list;
			}
			return {
				"total" : res.obj.total,// 总页数
				"rows" : res.obj.list
			// 数据
			};
		}
	});
}

function clickFormatter(value, row, index) {
	var basesiteId = row.basesiteId;
	var basesiteCode = row.basesiteCode;
	return '<a href="javascript:loadInfo(\'' + basesiteId + '\');">'
			+ basesiteCode + '</a>';
}
// 详细信息弹出层
function loadInfo(basesiteId) {
	var id = basesiteId;

	// 流转记录
	histoicFlowList(SiteAudit.tableName, basesiteId);
	$.ajax({
				url : 'queryOne',
				data : {
					id : id
				},
				type : 'get',
				dataType : 'json',
				success : function(list) {
					if (list == null) {
						alertModel("数据无法获取!");
						return false;
					}
					// 请求成功时
					var item = list.Obj;
					// 站点归属
					switch (item.basesiteBelong) {
					case "1":
						basesiteBelong = "铁塔维护";
						break;
					case "2":
						basesiteBelong = "移动维护";
						break;
					default:
						basesiteBelong = "-";
						break;
					}
					// 站点类型
					switch (item.basesiteType) {
					case 1:
						basesiteType = "核心站点";
						break;
					case 2:
						basesiteType = "骨干站点";
						break;
					case 3:
						basesiteType = "汇聚站点";
						break;
					case 4:
						basesiteType = "接入站点";
						break;
					case 5:
						basesiteType = "其他站点";
						break;
					default:
						basesiteType = "-";
						break;
					}
					// 共享属性
					switch (item.basesiteShare) {
					case "1":
						basesiteShare = "联通";
						break;
					case "2":
						basesiteShare = "电信";
						break;
					case "3":
						basesiteShare = "联通电信";
						break;
					default:
						basesiteShare = "-";
						break;
					}
					// 产权属性
					switch (item.basesiteProperty) {
					case 1:
						basesiteProperty = "自建";
						break;
					case 2:
						basesiteProperty = "共建";
						break;
					case 3:
						basesiteProperty = "租用";
						break;
					case 4:
						basesiteProperty = "购买";
						break;
					default:
						basesiteProperty = "-";
						break;
					}
					// 生命周期状态
					switch (item.basesiteState) {
					case 1:
						basesiteState = "在网";
						break;
					case 2:
						basesiteState = "工程";
						break;
					case 3:
						basesiteState = "退网";
						break;
					default:
						basesiteState = "-";
						break;
					}
					$("#basesiteId1").val(item.basesiteId);
					$("#basesiteCuid1").val(item.basesiteCuid);
					$("#basesiteCode1").val(item.basesiteCode);
					$("#basesiteName1").val(item.basesiteName);
					$("#basesiteState1").val(basesiteState);
					$("#basesiteAddress1").val(item.basesiteAddress);
					$("#basesiteBelong1").val(
							basesiteBelongFormt(item.basesiteBelong));
					$("#basesiteType1").val(basesiteType);
					$("#basesiteShare1").val(basesiteShare);
					$("#basesiteOpendate1").val(item.basesiteOpendate);
					$("#basesiteProperty1").val(basesiteProperty);
					$("#basesiteLongitude1").val(item.basesiteLongitude);
					$("#basesiteLatitude1").val(item.basesiteLatitude);
					$("#prvName1").val(item.prvName);
					$("#regName1").val(item.regName);
					$("#pregName1").val(item.pregName);
					$("#dataFrom1").val(item.dataFrom);
					$("#auditingState1").val(item.auditingState);
					$("#auditingUserId1").val(item.auditingUserId);
					$("#auditingDate1").val(item.auditingDate);
					$('#EditPanel1 input').attr('disabled',true);
					$('#EditPanel1').modal();
					$("#tbody").text("");
					var s = '';
					listData = item.datBaseresourceList;
					for (var i = 0; i < listData.length; i++) {
						// 生命周期状态
						switch (listData[i].baseresourceType) {
						case 0:
							baseresourceType = "机房";
							break;
						case 1:
							baseresourceType = "资源点";
							break;
						case 2:
							baseresourceType = "热点";
							break;
						case 3:
							baseresourceType = "位置点";
							break;
						default:
							baseresourceType = "-";
							break;
						}
						s = "<tr style='text-align: center;'><td>"
								+ listData[i].baseresourceCode + "</td><td>"
								+ listData[i].baseresourceName + "</td><td>"
								+ item.basesiteCode + "</td><td>"
								+ baseresourceType + "</td><td>"
								+ listData[i].pregName + "</td><td>"
								+ listData[i].regName + "</td></tr>";
						$('#tbs').append(s);
					}
				},
				error : function() {
					alertModel("请求异常");
				}
			})
}

/**
 * 增加站点信息
 */
function add() {
	addBlong();
	operate_type = 1;
	$("#basesiteCode").removeAttr("readonly");
	$('#basesiteCode').unbind();
	$("#basesiteCode").blur(function() {
		checkBasesiteState();
	});

	$("#dataForm")[0].reset(); // 清空表单
	getAddressUpdate();
	$('#EditPanel').modal(); // 弹出表单
}

/**
 * 修改信息
 */
function modify() {
	operate_type = 2;
	$('#basesiteCode').unbind();
	$("#basesiteCode").attr("readonly", true);
	var rows = getSelectItem(1);
	if (rows == null) {
		return;
	}
	if (rows[0].basesiteState == -1) {
		alertModel("已删除的数据，无法修改");
		return;
	}
	if (rows[0].auditingState == 9 || rows[0].auditingState == 0) {
		alertModel("已进入流程，无法修改");
		return;
	}
	var id = rows[0].basesiteId;
	$.ajax({
		url : 'one',
		data : {
			id : id
		},
		type : 'get',
		dataType : 'json',
		success : function(list) {
			if (list == null) {
				alertModel("数据无法获取!");
				return false;
			}
			// 请求成功时
			var item = list.Obj;
			$("input[name='basesiteId']").val(item.basesiteId);
			$("input[name='basesiteCuid']").val(item.basesiteCuid);
			$("input[name='basesiteCode']").val(item.basesiteCode);
			$("input[name='basesiteName']").val(item.basesiteName);
			$("#basesiteState").val(item.basesiteState);
			$("input[name='basesiteAddress']").val(item.basesiteAddress);
			var strs = new Array(); // 定义一数组
			if (item.basesiteBelong != null) {
				strs = item.basesiteBelong.split(","); // 字符分割
			}
			$("#basesiteBelong").val(addBlong(strs));
			// $("#basesiteBelong").val(item.basesiteBelong);
			$("#basesiteType").val(item.basesiteType);
			$("#basesiteShare").val(item.basesiteShare);
			$("#basesiteOpendate").val(item.basesiteOpendate);
			$("#basesiteProperty").val(item.basesiteProperty);
			$("#basesiteLongitude").val(item.basesiteLongitude);
			$("#basesiteLatitude").val(item.basesiteLatitude);
			$("#prvId").val(item.prvId);
			$("#prvName").val(item.prvName);
			$("#regName").val(item.regName);
			$("#pregName").val(item.pregName);
			$("#dataFrom").val(item.dataFrom);
			$("#auditingState").val(item.auditingState);
			$("#auditingUserId").val(item.auditingUserId);
			$("#auditingDate").val(item.auditingDate);

			getAddressUpdate(item.pregId, item.regId);

			$('#EditPanel').modal();
		},
		error : function() {
			alertModel("请求异常");
		}
	})
}
function getAddressUpdate(citySelect, villageSelect) {
	$
			.ajax({
				type : "post",
				url : "../../common/region/getAddressElec",
				dataType : 'json',
				contentType : "application/json;charset=UTF-8",
				success : function(value) {
					if (value != null) {
						sysReguins = value.obj;
						if (sysReguins != null) {
							$('#pregId').empty();
							$('#regId').empty();
							var strCity = "<option value=''>--选择地市--</option>";
							$
									.each(
											sysReguins,
											function(i, item) {
												strCity += "<option value='"
														+ item.regId
														+ "' "
														+ (citySelect
																&& citySelect == item.regId ? "selected='true'"
																: "") + ">"
														+ item.regName
														+ "</option>";
											});

							$("#pregId").append(strCity);

							$("#regId").empty();
							var strReg = "<option value=''>--选择区县--</option>";
							if ($("#pregId").val() != "") {
								$
										.each(
												sysReguins,
												function(i, item) {
													if (item.regId == $(
															"#pregId").val()) {
														$
																.each(
																		item.child,
																		function(
																				j,
																				itemchild) {
																			strReg += "<option value='"
																					+ itemchild.regId
																					+ "' "
																					+ (villageSelect
																							&& villageSelect == itemchild.regId ? "selected='true'"
																							: "")
																					+ ">"
																					+ itemchild.regName
																					+ "</option>";
																		});
													}
												});
							}
							$("#regId").append(strReg);
							// 绑定联动事件 修改人zhujj
							$("#pregId")
									.change(
											function() {
												$("#regId").empty();
												var strReg = "<option value=''>--选择区县--</option>";
												if ($("#pregId").val() != "") {
													$
															.each(
																	sysReguins,
																	function(i,
																			item) {
																		if (item.regId == $(
																				"#pregId")
																				.val()) {
																			$
																					.each(
																							item.child,
																							function(
																									j,
																									itemchild) {
																								strReg += "<option value='"
																										+ itemchild.regId
																										+ "' "
																										+ (villageSelect
																												&& villageSelect == itemchild.regId ? "selected='true'"
																												: "")
																										+ ">"
																										+ itemchild.regName
																										+ "</option>";
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
	if (rows == null) {
		return;
	}
	if (rows[0].auditingState == 0 || rows[0].auditingState == 9) {
		alertModel("已进入流程，无法删除");
		return;
	}
	for (var i = 0; i < rows.length; i++) {
		delete rows[i]["0"];
	}
	if (confirm("确定删除所选项目?")) {

		for (var i = 0; i < rows.length; i++) {
			delete rows[i]["0"];
		}
		$.ajax({
			type : "post",
			url : "remove",
			data : JSON.stringify(rows),
			dataType : 'json',
			contentType : "application/json;charset=UTF-8",
			success : function(data) {
				findSite();
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
 * 
 * @returns
 */
function submitAudit() {
	var rows = getSelectItem(0);
	for (var i = 0; i < rows.length; i++) {
		if (rows[i].auditingState == 0) {
			alertModel('已审核成功!');
			return;
		}
		if (rows[i].auditingState == 8) {
			alertModel('此数据为审核不通过，请先修改再提交审核!');
			return;
		}
		if (rows[i].auditingState == 9) {
			alertModel('此数据正在审核中!');
			return;
		}
	}
	queryFirstUsersByProcDefKey(SiteAudit.procDefKey, "startActFlow",
			rows[0].regId);
}

/**
 * 提交流程，开始审核流程
 */
function startActFlow() {
	$('#firstUsers').modal("hide");
	var rows = getSelectItem(0);
	var ids = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rows.length; s++) {
		var row = rows[s];
		ids.push(row.basesiteId);
	}
	var nextUserId = $("input[name='userChecked']:checked").val();
	var datasObjs = new Array();
	for (var i = 0; i < ids.length; i++) {
		var id = ids[i];
		var obj = {
			"id" : id,
			"nextUserId" : nextUserId
		};
		datasObjs.push(obj);
	}
	var objButton = $(this);
	$.ajax({
		url : 'submitAudit',// 跳转到 action
		data : JSON.stringify(datasObjs),
		type : "POST",
		dataType : "JSON",
		contentType : 'application/json;charset=utf-8',
		beforeSend : function() {// 触发ajax请求开始时执行
			objButton.val('提交审核中').attr('disabled', true);// 改变提交按钮上的文字并将按钮设置为不可点击
		},
		success : function(back) {
			if (back != null) {
				data = back.obj;
				if (data != null) {
					$("#commit2").attr("disabled", false);
					alertModel("提交审核成功！");
					findSite();
				}
			}
		},
		error : function() {
			$("#commmit2").attr("disabled", false);
			alertModel("获取区县信息异常！");
		},
		complete : function() {// ajax请求完成时执行
			objButton.val('提交审核').attr('disabled', false);// 改变提交按钮上的文字并将按钮设置为可以点击
		}
	});
}
// 导出
/*
 * function exportData(){ $.ajax({ url : 'export',// 跳转到 action type : 'get',
 * contentType : "application/json;charset=UTF-8", dataType : 'json', data : {
 * city : $("#city option:selected").val(), region : $("#region
 * option:selected").val(), siteReg : $("#siteReg").val(), property :
 * $("#property option:selected").val(), auditStatus : $("#auditStatus
 * option:selected").val(), status : $("#status option:selected").val(),
 * queryType : queryType }, beforeSend: function () { $("#loading").html("<img
 * src=\""+ +"/asserts/image/loading-b.gif\"/>"); }, success : function(data) {
 * $("#loading").html(""); if (data != null) { alertModel(data.msg); } }, error :
 * function() { $("#loading").html(""); alertModel("导出数据异常"); } }); }
 */

// 导出
function exportData(data) {
	var para = "siteReg=" + $('#siteReg').val() + "&city="
			+ $("#city option:selected").val();
	para += "&property=" + $('#property option:selected').val()
			+ "&auditStatus=" + $("#auditStatus option:selected").val();
	para += "&region=" + $('#region option:selected').val() + "&status="
			+ $("#status option:selected").val() + "&queryType=" + queryType;
	window.open("export?" + para);
}
/**
 * 增加提交form表单
 */
function formSubmit() {
	var basesiteCuid = $("input[name='basesiteCuid']").val();
	if (basesiteCuid == "") {
		alertModel("站点CID不能为空！");
		return false;
	}
	var basesiteCode = $("input[name='basesiteCode']").val();
	if (basesiteCode == "") {
		alertModel("站点编码不能为空！");
		return false;
	}
	var basesiteName = $("input[name='basesiteName']").val();
	if (basesiteName == "") {
		alertModel("站点名称不能为空！");
		return false;
	}
	if ($("#regId").val() == "") {
		alertModel("请选择区县信息！");
		return false;
	}
	if ($("input[name='basesiteLongitude']").val() == "") {
		$("input[name='basesiteLongitude']").val(0);
	}
	if ($("input[name='basesiteLatitude']").val() == "") {
		$("input[name='basesiteLatitude']").val(0);
	}
	$("input[name='regName']").val($("#regId option:selected").text());
	$("input[name='pregName']").val($("#pregId option:selected").text());
	var basesiteBelongIds = [];
	var select1 = document.getElementById("basesiteBelong");
	for (i = 0; i < select1.length; i++) {
		if (select1.options[i].selected) {
			if (select1[i].value != null && select1[i].value != '') {
				basesiteBelongIds.push(select1[i].value);
			}
		}
	}
	$("#basesiteBelongIds").val(basesiteBelongIds);
	var data = $('#dataForm').serializeObject();
	data.basesiteOpendate = $("#basesiteOpendate").val();
	var submitData = JSON.stringify(data);
	if (operate_type == 1) {
		checkBasesiteState();
		if (flag) {
			return;
		}
		$("#saveSet").attr("disabled", true);
		$.post("save", {
			"param" : submitData
		}, function(result) {
			// 请求成功时
			if (result != null) {
				alertModel(result.msg);
				if (result.success == "1") {
					$('#EditPanel').modal('hide');
					findSite();
				}
			}
			$("#saveSet").attr("disabled", false);
		});
	} else {
		$.post("modify", {
			"param" : submitData
		}, function(result) {
			// 请求成功时
			if (result != null) {
				alertModel(result.msg);
				if (result.success == "1") {
					$('#EditPanel').modal('hide');
					findSite();
				}
			}
			$("#saveSet").attr("disabled", false);
		});
	}
}

function importData() {
	$("#fileForm")[0].reset(); // 清空表单
	$('#filePanel').modal(); // 弹出表单
}

function uploadExcel() {
	var fileURL = $("#file_url").val();
	if (fileURL == null) {
		alert("请选择上传文件!");
	} else {
		var formobj = document.getElementById("fileForm");
		var formdata = new FormData(formobj);
		$.ajax({
			type : "POST",
			url : "import",
			data : formdata,
			async : false,
			cache : false,
			contentType : false,
			processData : false,
			beforeSend : LoadFunction, // 加载执行方法
			error : erryFunction, // 错误执行方法
			success : succFunction
		// 成功执行方法
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
				findSite();
			}
		}
	}
}

// 用于判断站点编码
var flag = true;
// 判断站点编码唯一性验证
function checkBasesiteState() {
	var basesiteCode = $("#basesiteCode").val();
	if (basesiteCode == "" || basesiteCode == null) {
		alertModel("站点编码不能为空");
		return;
	} else {
		$.ajax({
			type : "post",
			url : "checkBasesiteCode",
			data : {
				basesiteCode : basesiteCode,
			},
			dataType : 'json',
			success : function(value) {
				if (value != null) {
					data = value.obj;
					if (data != null && data.length > 0) {
						alertModel('站点编码已存在，请重新输入!');
						flag = true;
						return;
					} else {
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

function basesiteBelongFormt(value, row, index) {
	var strs = new Array(); // 定义一数组
	if (value != null) {
		strs = value.split(","); // 字符分割
	}
	var belong = "";
	for (i = 0; i < strs.length; i++) {
		switch (strs[i]) {
		case "1":
			belong += "中国移动,";
			break;
		case "2":
			belong += "中国联通,";
			break;
		case "3":
			belong += "中国电信,";
			break;
		case "4":
			belong += "中国铁通,";
			break;
		case "5":
			belong += "广电,";
			break;
		case "6":
			belong += "业主,";
			break;
		case "7":
			belong += "其他,";
			break;
		case "8":
			belong += "电力,";
			break;
		case "9":
			belong += "中国铁塔,";
			break;
		default:
			belong += "";
		}
	}
	return belong.substring(0, belong.length-1);
}

function addBlong(basesiteBelongIds) {
	$("#basesiteBelong").empty();
	$("#basesiteBelong").append(
			"<option  id='a1' value='1'>中国移动</option>"
			+ "<option  id='a2' value='2'>中国联通</option>"
			+ "<option  id='a3' value='3'>中国电信</option>"
			+ "<option  id='a4' value='4'>中国铁通</option>"
			+ "<option  id='a5' value='5'>广电</option>"
			+ "<option  id='a6' value='6'>业主</option>"
			+ "<option  id='a7' value='7'>其他</option>"
			+ "<option  id='a8' value='8'>电力</option>"
			+ "<option  id='a9' value='9'>中国铁塔</option>");
	$('#basesiteBelong').selectpicker('refresh');
	if (basesiteBelongIds != null) {
		for (var i = 0; i < basesiteBelongIds.length; i++) {
			$('#a' + basesiteBelongIds[i]).attr("selected", "selected");
			$('#basesiteBelong').selectpicker('refresh');
		}
	}
	return basesiteBelongIds;
}