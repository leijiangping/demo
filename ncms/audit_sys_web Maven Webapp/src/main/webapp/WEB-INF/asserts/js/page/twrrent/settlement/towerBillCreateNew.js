//已显示表格list
var showTableList = null;
$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	curPageNum = 1;
	getAddress("运营商");	
}
function searchNewLoadTableData(){
	// 查询默认条件表单数据
	newLoadTableData();
}

function newLoadTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryRentHistroyList", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		fixedColumns: true,//是否启用固定列
        fixedNumber: 8,//固定列的个数
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
					operatorRegId : $("#City option:selected").val(),
					pregId : $("#Village option:selected").val(),
					accountPeroid : $("#datetimepicker").val(),
					businessConfirmNumber : $("#businessConfirmNumber").val(),
					towerStationName : $("#towerStationCodeOrName").val()
			};
			return param;
		},
		columns: [{
			checkbox: true
		},{
            field: 'operatorSysRegion.prvName',
            title: '运营商地市'
        },{
            field: 'operatorSysRegion.regName',//找不到
            title: '运营商区县'
        },{
            field: 'businessConfirmNumber',
            title: '产品业务确认单编号'
        },{
            field: 'towerStationCode',
            title: '铁塔公司站址编码'
        },{
            field: 'towerStationName',
            title: '移动侧站址名称'
        },{
            field: 'towerStationCode',
            title: '移动侧站址编码'
        },{
            field: 'detailAddress',
            title: '详细地址'
        },],
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success != "1"){
		            alertModel(res.msg.result);
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

function newSubmit(){
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var towerbillbalanceIdObjs =new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		towerbillbalanceIdObjs.push(row.rentinformationhistoryId);
	}
	$("#saveSet").attr("disabled",true);
	$.ajax({
	    url:'reGenerateBills',
	    data:{
	    	towerbillbalanceIds:JSON.stringify(towerbillbalanceIdObjs)
	    	},
 		type : 'post',
	    cache:false,
	    async:true,
	    success:function(result){
	        //请求成功时
	    	if(result!=null){
    			loadTableData();
    			alertModel(result.msg);
	    	}
    		window.location.href="towerBilICreate.html";
    		$("#saveSet").attr("disabled",false);
	    },
	    error:function(){
			alertModel("请求失败！");
			$("#saveSet").attr("disabled",false);
	    }
	});
}