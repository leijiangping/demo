var operate_type = 1;// 1 新增，2 修改
var resType = 0;
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
		url : "../resource/queryDatBaseresource", // 获取数据的地址
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
					 baseresourceCodeOrName: $("#reg").val(),
					 roomOwner : $("#property option:selected").val(),
					 baseresourceState : $("#status option:selected").val(),
					 pregId : $("#city option:selected").val(),
					 regId : $("#region option:selected").val(),
					 queryType : resType,
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
            field: 'baseresourceCode',
            title: '机房编号',
            formatter:clickFormatter
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

/**
 * 审核
 * @returns
 */
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
	}
	var submitData = JSON.stringify(rows);
	var id = rows[0].baseresourceId;
	var taskId=rows[0].act.taskId;		
	var regId=rows[0].regId;
	window.location.href='roomAuditNews.html?baseresourceId='+id+'&taskId='+taskId+"&regId="+regId;
}
//导出
/*function exportData(){
	$.ajax({
		url : '../resource/exportAudit',// 跳转到 action
		type : 'get',
		contentType : "application/json;charset=UTF-8",
		dataType : 'json',
		data : {
			baseresourceCodeOrName: $("#reg").val(),
			 roomOwner : $("#property option:selected").val(),
			 baseresourceState : $("#status option:selected").val(),
			 pregId : $("#city option:selected").val(),
			 regId : $("#region option:selected").val(),
			queryType : resType,
		     pageNumber: 0,    
			 pageSize: 10
		},
		success : function(data) {
			if (data != null) {
				if (data.success == "1") {
					window.open(data.Obj);
					alertModel(data.msg);
				}else{
					alertModel(data.msg);
				}
			}
		},
		error : function() {
			alertModel("导出数据异常");
		}
	});
}*/

//导出
function exportData(data){
	var para="reg="+$('#reg').val()+"&city="+$("#city option:selected").val();	
	para+="&property="+$('#property option:selected').val();
	para+="&region="+$('#region option:selected').val()+"&status="+$("#status option:selected").val()+"&queryType="+queryType+"&resType="+resType;
	window.open("../resource/exportAudit?"+para);
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
				$("#basesiteId_Details").val(item.baseresourceId);
				$("#dataForm input").attr("readonly","readonly");
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