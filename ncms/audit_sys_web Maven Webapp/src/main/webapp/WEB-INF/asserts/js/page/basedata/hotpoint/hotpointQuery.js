var operate_type = 1;// 1 新增，2 修改
var resType = 2;
var queryType = 1;//0 录入数据，1-导入数据，2-接口导入数据,null 代表查询所有数据
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
	findData();
}
/**
 * 列表查询
 */
function findData() {
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
					resType : resType,
					queryType : queryType,
					cur_page_num: params.pageNumber,    
					page_count: params.pageSize
			};
			return param;
		},
		columns: [{
            field: 'auditingState',
            title: '审核状态',
            formatter: fmtAuditState
        }, {
            field: 'baseresourceCode',
            title: '热点编号',
            formatter: clickFormatter
        }, {
            field: 'baseresourceName',
            title: '热点名称'
        }, {
            field: 'pregName',
            title: '所属地市'
        }, {
            field: 'regName',
            title: '所属区县'
        }, /*{
            field: 'roomOwner',
            title: '热点归属',
            formatter: fmtProperty
        }, */{
            field: 'baseresourceState',
            title: '热点状态',
            formatter: fmtState
        }, {
            field: 'baseresourceType',
            title: '资源类型',
            formatter: function (value, row, index) {
                return '热点';
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
	para+="&property="+$('#property option:selected').val()+"&auditStatus="+$("#auditStatus option:selected").val();
	para+="&region="+$('#region option:selected').val()+"&status="+$("#status option:selected").val()+"&queryType="+queryType+"&resType="+resType;
	window.open("../resource/export?"+para);
}

function clickFormatter(value, row, index){
	var baseresourceId=row.baseresourceId;
	var baseresourceCode=row.baseresourceCode;
	return '<a href="javascript:queryDetails(\''+baseresourceId+'\');" >'+baseresourceCode+'</a>';
}

function queryDetails(baseresourceId){
	//流转记录
//	histoicFlowList(BaseResourceAudit.tableName,baseresourceId);
	$('#EditPanel1').modal(); 
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
				//生命周期状态
				switch(item.baseresourceState){
				case 1:
					baseresourceState = "在网";
					break;
				case 2: 
					baseresourceState ="工程";
					break;
				case 3:
					baseresourceState ="退网";
					break;
				default:
					baseresourceState ="-";
					break;
				}
				
				$("#baseresourceCuid1").val(item.baseresourceCuid);
				$("#baseresourceCode1").val(item.baseresourceCode);
				$("#baseresourceName1").val(item.baseresourceName);
				$("#baseresourceAddress1").val(item.baseresourceAddress);
				$("#baseresourceArea1").val(item.baseresourceArea);
				$("#baseresourceCategory1").val(item.baseresourceCategory);
				$("#baseresourceState1").val(baseresourceState);
				$("#baseresourceOpendate1").val(item.baseresourceOpendate);
				$("#baseresourceLongitude1").val(item.baseresourceLongitude);
				$("#baseresourceLatitude1").val(item.baseresourceLatitude);
				$("#airconditionerPower1").val(item.airconditionerPower);
				$("#pregId1").val(item.pregName);
				$("#regId1").val(item.regName);
				$("#EditPanel1 input").attr("readonly","readonly");
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