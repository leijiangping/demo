//已显示表格list
var showTableList = null;

$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	curPageNum = 1;
	getAddress();
	// 查询默认条件表单数据
	loadTableData();
	$('#calAmount').click(function(){
		
	});
}
function loadTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryGroupPunishVO", // 获取数据的地址
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
					pRegId:$("#City option:selected").val(),
					punishYearMonth : $("#punishYearMonth").val(),
					towerstationcode: $("#towerstationcode").val(),
					ifduty:$("#ifduty option:selected").val(),
					ifcal:$("#ifcal option:selected").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        },{
        	field: 'towerstationcode',
            title: '铁塔站点编码'
        },{
            field: 'sysRegionVO.pRegName',
            title: '地市'
        },{
            field: 'sysRegionVO.regName',
            title: '区县'
        },{
            field: 'punishYearMonth',
            title: '年/月份'
        },{
            field: 'maintenancelevelid',
            title: '维护等级',
            formatter:function(value,row,index){
            	switch(value){
            		case 0:return '标准';
            		case 1:return '高等级';
            		default:return '/';
            	}
            	return value;
            }
        },{
            field: 'punishamount',
            title: '罚金（元）'
        },{
            field: 'ifduty',
            title: '是否免责',
            formatter:function(value,row,index){
            	switch(value){
            		case 0:return '不免责';
            		case 1:return '免责';
            		default:return '/';
            	}
            	return value;
            }
        },{
            field: 'coversceneid',
            title: '覆盖场景',
            formatter:function(value,row,index){
            	switch(value){
            		case 0:return '非高铁';
            		case 1:return '高铁';
            		default:return '/';
            	}
            	return value;
            }
        },],
		/*onLoadSuccess : function() { // 加载成功时执行
			console.log("加载成功");
		},*/
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success != "1"){
		           /* alertModel(res.msg);*/
				}
				showTableList = res.Obj.result;
			}
	        return {
	            "total": res.Obj.total,//总页数
	            "rows": res.Obj.result, //数据
	         };
		}
	});	
}
/**
 * 删除
 */
function deleteItem() {
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var selectRows=$('#tb').bootstrapTable('getSelections');
	if (confirm("确定删除所选项目?")) {
		$.ajax({
			type : "post",
			url : "deleteGroupPunish",
			data :{
				"twrGroupPunishId":selectRows[0].twrGroupPunishId
			},
			dataType : 'json',
			success : function(data) {
				loadTableData();
			},
			error : function(data) {
				alertModel('删除失败!');
			}
		});
	}
};
/**
 * 按地市计算考核
 */
function punishAmount(){
	var pregId=$("#City option:selected").val();
	var punishYearMonth=$("#punishYearMonth").val();
	if(pregId == undefined || pregId == ''){
		alertModel("请先选择要计算的城市！");
		return;
	}
	if(punishYearMonth == undefined || punishYearMonth == ''){
		alertModel("请先选择要计算的年月！");
		return;
	}
	$.ajax({
		type : "post",
		url : "queryPunishAmount",
		data :{
			pregId:pregId,
			punishYearMonth:punishYearMonth
		},
		dataType : 'json',
		success : function(back) {
			if(back != null){
				if(back.success == 1){
					alertModel(back.msg);
					loadTableData();
				}else{
					alertModel(back.msg);
				}
			}
		},
		error : function(back) {
			alertModel(back.msg);
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
	var para="&pregId="+ $("#City").val();
	para+="&punishYearMonth="+ $("#punishYearMonth").val();
	para+="&towerstationcode="+ $("#towerstationcode").val();
	para+="&ifduty="+ $("#ifduty").val();
	para+="&ifcal="+ $("#ifcal").val();
	window.open("exportTowerResourceInfo?"+para,"_blank");
}
/**
 * 导入
 */

function importResourceInfo(){
	//"铁塔侧账单导入" 弹出框名称
	//"_towerBillbalance" 功能标识
	//"formSubmit" 回调方法
	importModel("考核管理单导入","_towerChangeInfo","formSubmit");//弹出导入框
}	
/**
 * 上传文件并保存到数据库
 * @param suffix 模块标识
 */
function formSubmit(suffix){
	var formData = new FormData($("#dataForm")[0]); 
	$.ajax({
		url:'importGroupPunish',
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
 * 帮助
 */

function helpExamination(){
	
}


