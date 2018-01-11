//已显示表格list
var showTableList = null;

var operate_type = 1;// 1 新增，2 修改

$(document).ready(function() {
	initCurrentPage();
});

function initCurrentPage(){
	curPageNum = 1;
	// 查询默认条件表单数据
	getAddress();
	loadTableData();
}

function loadTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryStopServerCheckInfo", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		pageSize : ipageCount, // 每页显示的记录数
		pageNumber : curPageNum, // 当前第几页
		minimumCountColumns: 1,  //最少允许的列数
		clickToSelect: true,  //是否启用点击选中行
		fixedColumns: true,
        fixedNumber:5,
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
					pregId :	$("#City option:selected").val(),
		            regId :	$("#Village option:selected").val()
		            //taskDefKey : $("#taskDefKey").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        },{
        	field: 'businessConfirmNumber',
            title: '产品业务确认单编号'
        },{
        	field: 'sysRegionVO.prvName',
            title: '省'
        },{
        	field: 'sysRegionVO.pRegName',
            title: '地市'
        },{
        	field: 'sysRegionVO.regName',
            title: '区县'
        },{
            field: 'towerStationName',
            title: '站点名称'
        },{
        	field: 'towerStationCode',
            title: '站点编号'
        }, {
            field: 'startDate',
            title: '起始时间',
            formatter : function(value){
            	if(value == null){
            		return "-";
            	}
        		return new Date(value).format("yyyy-MM-dd");
            }
        },{
            field: 'endDate',
            title: '截止时间',
            formatter : function(value){
            	if(value == null){
            		return "-";
            	}
        		return new Date(value).format("yyyy-MM-dd");
            }
        },{
            field: 'stopUseDate',
            title: '服务终止生效日期',
            formatter : function(value){
            	if(value == null){
            		return "-";
            	}
        		return new Date(value).format("yyyy-MM-dd");
            }
        },{
            field: 'stopReason',
            title: '服务终止原因'
        },],
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success == "1"){
					showTableList = res.obj.result;
					unique(showTableList);
					return {
			            "total": res.obj.total,//总页数
			            "rows": res.obj.result //数据
			        };
				}else{
					//alertModel(res.msg);
					return {
			            "total": null,//总页数
			            "rows": null //数据
			        };
				}
			}
		}
	});
}
//加载不重复的流程环节
function unique(arr){
	// 遍历arr，把元素分别放入tmp数组(不存在才放)
	$("#taskDefKey").empty();
	$("#taskDefKey").append("<option value=''>---审核环节---</option>");
	
	var tmp = new Array();
	for(var i in arr){
		//该元素在tmp内部不存在才允许追加
		if(arr[i].act!=null&&tmp.indexOf(arr[i].act.taskDefKey)==-1){
			$("#taskDefKey").append("<option value='"+arr[i].act.taskDefKey+"'>"+arr[i].act.taskName+"</option>");
			tmp.push(arr[i].act.taskDefKey);
		}
	}
	return tmp;
}
function hadCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}

function endAudit(){
	if(!hadCheckedRowData()){
		return false;
	}
	window.location.href="serviceEndAudit.html?stopServerId="+rowschecked[0].stopServerId+'&taskId='+rowschecked[0].act.taskId;
}

/**
 * 导入
 */

function importResourceInfo(){
	//"铁塔侧账单导入" 弹出框名称
	//"_towerBillbalance" 功能标识
	//"formSubmit" 回调方法
	importModel("铁塔变更信息导入","_towerChangeInfo","formSubmit");//弹出导入框
}	
/**
 * 上传文件并保存到数据库
 * @param suffix 模块标识
 */
function formSubmit(suffix){
	var formData = new FormData($("#dataForm")[0]); 
	$.ajax({
		url:'importTowerChangeInfo',
		type : 'post',
		data : formData,
	    async: true,  
     	cache: false,  
        contentType: false,  
        processData: false, 
 		beforeSend:function(){//启动a
 			startTimeJob(suffix);
        },  
 		success : function(result){
			stopTimeJob();//停止进度条
 			if(result != null&&result.success=="1"){
 					loadTableData();
 					alertModel(result.msg);
 			}
 		},
        complete:function(){//ajax请求完成时执行    
			stopTimeJob();//停止进度条
        },
 		error : function() {
			stopTimeJob();//停止进度条
			alertModel("请求失败！");
 		}
	});
}


/**
 * 导出
 */
function exportResourceInfo(){
	confirmModel("您确定要导出吗？","exportInfo");
} 

function exportInfo(){

	var exportObjs = new Array();
	// 从选中行中挑出可以启用的item
	if($("#educeStyle").val() == 1){
		$('#tb').bootstrapTable('getData');
	}else if($("#educeStyle").val() == 2){
		var selectRows=$('#tb').bootstrapTable('getSelections');
		
			$.each(selectRows,function(i,v){
				exportObjs.push(v.twrRentinformationBizchangeId);
			});
	}
	//修改导出方法
	
	//修改导出方法 author：zhujj
	var para="&pregId="+ $("#City option:selected").val();
	para+="&regId="+ $("#Village option:selected").val();
	para+="&taskDefKey="+$("#taskDefKey").val();
	window.open("exportStopServerCheckInfo?"+para,"_blank");

}
