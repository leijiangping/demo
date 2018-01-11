var queryType = 1;
var curPageNum = 0;
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
	//$('#tb').bootstrapTable('hideColumn', 'supplierId');
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
					page_count: params.pageSize
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
        },{
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
        }, {
            field: 'dataFrom',
            title: '数据来源',
            formatter:function(value,row,index){
            	switch(value){
	        		case 0:return '系统录入';
	        		case 1:return '系统导入';
	        		case 2:return '接口采集';
	        	}
            	return value;
            }
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

/*function exportData(){
	$.ajax({
		url : 'export',// 跳转到 action
		type : 'get',
		contentType : "application/json;charset=UTF-8",
		dataType : 'json',
		data : {
            reg : 	$('#supplierReg').val(),
            city :	$("#city option:selected").val(),
            region :	$("#region option:selected").val(),
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
	var para="reg="+$('#supplierReg').val()+"&city="+$("#city option:selected").val();
	para+="&region="+$("#region option:selected").val()+"&queryType="+queryType;
	window.open("export?"+para);
}


function loadInfo(obj){
	 var twrRentInfoId=$(obj).parent().next().text();
	 window.location.href="mobileInfoinner.html?twrRentInfoId="+twrRentInfoId+"&goType=1";
}


/**
 * 查看用户信息
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