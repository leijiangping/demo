var operate_type = 1;// 1 新增，2 修改
var queryType = 1;
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
		url : "querySiteInfo", // 获取数据的地址
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
					basesiteCodeOrName : $("#siteReg").val(),
					basesiteBelong : $("#property option:selected").val(),
					basesiteState : $("#status option:selected").val(),
					pregId : $("#city option:selected").val(),
					regId : $("#region option:selected").val(),
					pageNumber: params.pageNumber,    
					pageSize: params.pageSize
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
            field: 'basesiteCode',
            title: '站点编号',
            formatter:clickFormatter
        }, {
            field: 'basesiteName',
            title: '站点名称'
        }, {
            field: 'pregName',
            title: '所属地市'
        }, {
            field: 'regName',
            title: '所属区县'
        }, {
            field: 'basesiteBelong',
            title: '站点归属',
            formatter: basesiteBelongFormt
        }, {
            field: 'basesiteState',
            title: '站点状态',
            formatter: fmtState
        }, {
            field: 'basesiteType',
            title: '资源类型',
            formatter: function (value, row, index) {
                return '站点';
            }
        }],
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				showTableList = res.obj.list;
			}
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.list //数据
	         };
		}
	});
}

function clickFormatter(value, row, index){
	var basesiteId=row.basesiteId;
	var basesiteCode=row.basesiteCode;
	return '<a href="javascript:loadInfo(\''+basesiteId+'\');">'+basesiteCode+'</a>';
}
//详细信息弹出层
function loadInfo(basesiteId){
	var id = basesiteId;
	//流转记录
	histoicFlowList(SiteAudit.tableName,basesiteId);
	$.ajax({
		url : 'queryOne',
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
			//站点归属
//			switch(item.basesiteBelong){
//			case "1":
//				basesiteBelong = "铁塔维护";
//				break;
//			case "2": 
//				basesiteBelong ="移动维护";
//				break;
//			default:
//				basesiteBelong ="-";
//				break;
//			}
			
			var strs= new Array(); //定义一数组 
			strs=item.basesiteBelong.split(","); //字符分割 
			var belong = "";
			for (i=0;i<strs.length ;i++ ) {
				switch(strs[i]){
					case "1": belong +=  "中国移动,";
					break;
					case "2": belong +=  "中国联通,";
					break;
					case "3": belong +=  "中国电信,";
					break;
					case "4": belong +=  "中国铁通,";
					break;
					case "5": belong +=  "广电,";
					break;
					case "6": belong +=  "业主,";
					break;
					case "7": belong +=  "其他,";
					break;
					case "8": belong +=  "电力,";
					break;
					case "9": belong +=  "中国铁塔,";
					break;
					default : belong += "";
				}
			}
			//站点类型
			switch(item.basesiteType){
			case 1:
				basesiteType = "核心站点";
				break;
			case 2: 
				basesiteType ="骨干站点";
				break;
			case 3:
				basesiteType ="汇聚站点";
				break;
			case 4: 
				basesiteType ="接入站点";
				break;
			case 5:
				basesiteType ="其他站点";
				break;
			default:
				basesiteType ="-";
				break;
			}
			//共享属性
			switch(item.basesiteShare){
			case "1":
				basesiteShare = "联通";
				break;
			case "2": 
				basesiteShare ="电信";
				break;
			case "3":
				basesiteShare ="联通电信";
				break;
			default:
				basesiteShare ="-";
				break;
			}
			//产权属性
			switch(item.basesiteProperty){
			case 1:
				basesiteProperty = "自建";
				break;
			case 2: 
				basesiteProperty ="共建";
				break;
			case 3:
				basesiteProperty ="租用";
				break;
			case 4:
				basesiteProperty ="购买";
				break;
			default:
				basesiteProperty ="-";
				break;
			}
			//生命周期状态
			switch(item.basesiteState){
			case 1:
				basesiteState = "在网";
				break;
			case 2: 
				basesiteState ="工程";
				break;
			case 3:
				basesiteState ="退网";
				break;
			default:
				basesiteState ="-";
				break;
			}
			$("#basesiteId1").val(item.basesiteId);
			$("#basesiteCuid1").val(item.basesiteCuid);
			$("#basesiteCode1").val(item.basesiteCode);
			$("#basesiteName1").val(item.basesiteName);
			$("#basesiteState1").val(basesiteState);
			$("#basesiteAddress1").val(item.basesiteAddress);
			$("#basesiteBelong1").val(belong);
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
			$('#EditPanel1 form input').attr('readonly','readonly');
			$('#EditPanel1').modal();
			$("#tbody").text("");
			var s = '';
			listData = item.datBaseresourceList;
			for (var i = 0; i < listData.length; i++) {
				//生命周期状态
				switch(listData[i].baseresourceType){
				case 0:
					baseresourceType = "机房";
					break;
				case 1:
					baseresourceType = "资源点";
					break;
				case 2: 
					baseresourceType ="热点";
					break;
				case 3:
					baseresourceType ="位置点";
					break;
				default:
					baseresourceType ="-";
					break;
				}
				s = "<tr style='text-align: center;'><td>"
						+ listData[i].baseresourceCode
						+ "</td><td>"
						+ listData[i].baseresourceName
						+ "</td><td>"
						+ listData[i].basesiteId
						+ "</td><td>"
						+baseresourceType
						+ "</td><td>" 
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

// 审核
function audit(){
	var rows = getSelectItem(0);
	if(rows == null){
		return;
	}
	for(var i=0;i<rows.length;i++){
		if(rows[i].auditingState != 9){
			alertModel('未进入流程，无法审核!');
			return;
		}
		delete rows[i]["0"];
	}
	var submitData = JSON.stringify(rows);
	console.log(submitData);
	var id = rows[0].basesiteId;
	var taskId=rows[0].act.taskId;	
	var regId=rows[0].regId;
	window.location.href='siteAuditNews.html?basesiteId='+id+'&taskId='+taskId+"&regId="+regId;
}
//导出
/*function exportData(){
	$.ajax({
		url : 'exportAudit',// 跳转到 action
		type : 'get',
		contentType : "application/json;charset=UTF-8",
		dataType : 'json',
		data : {
			basesiteCodeOrName : $("#siteReg").val(),
			basesiteBelong : $("#property option:selected").val(),
			basesiteState : $("#status option:selected").val(),
			pregId : $("#city option:selected").val(),
			regId : $("#region option:selected").val(),
			pageNumber: 0,    
			pageSize: 10
		},
		beforeSend: function () {
		    $("#loading").html("<img src=\""+ +"/asserts/image/loading-b.gif\"/>");
		},
		success : function(data) {
			$("#loading").html("");
			if (data != null) {
				alertModel(data.msg);
				window.open(data.Obj);
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
	var para="basesiteCodeOrName="+$('#siteReg').val()+"&pregId="+$("#city option:selected").val();	
	para+="&basesiteBelong="+$('#property option:selected').val()+"&pageNumber=0"+"&pageSize=10";
	para+="&regId="+$('#region option:selected').val()+"&basesiteState="+$("#status option:selected").val()+"&queryType="+queryType;
	window.open("exportAudit?"+para);
}

function basesiteBelongFormt(value, row, index){
	var strs= new Array(); //定义一数组 
	if(value != null){
		strs=value.split(","); //字符分割 
	}
	var belong = "";
	for (i=0;i<strs.length ;i++ ) {
		switch(strs[i]){
			case "1": belong +=  "中国移动,";
			break;
			case "2": belong +=  "中国联通,";
			break;
			case "3": belong +=  "中国电信,";
			break;
			case "4": belong +=  "中国铁通,";
			break;
			case "5": belong +=  "广电,";
			break;
			case "6": belong +=  "业主,";
			break;
			case "7": belong +=  "其他,";
			break;
			case "8": belong +=  "电力,";
			break;
			case "9": belong +=  "中国铁塔,";
			break;
			default : belong += "";
		}
	}
	return  belong.substring(0, belong.length-1);
}