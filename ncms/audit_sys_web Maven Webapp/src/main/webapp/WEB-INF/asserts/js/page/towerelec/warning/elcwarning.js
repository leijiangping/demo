//已显示表格list
var showTableList = null;

$(document).ready(function() {

	initCurrentPage();
});

function initCurrentPage(){
	curPageNum = 1;
	getAddress();
	// 查询默认条件表单数据
}

/**
 * 获取用户所有权限 的地市 区县信息
 * 
 * @param title 名称 例如：供应商地市，传入title为供应商
 */
function getAddress(title){
	$.ajax({
		type : "post",
		url : projectName + "/rent/common/getAddressRent",
		
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		success : function(value) {
			if(value != null){
				sysReguins = value.obj;
				if(sysReguins!=null){
					$('#City').empty();
					$('#Village').empty();
					var strCity = "<option value=''>-选择"+(title?title:"")+"地市-</option>";
					$.each(sysReguins, function (i, item){
						strCity += "<option value='" +item.regId+"'>"+item.regName+ "</option>";
					});

					$("#City").append(strCity);

					var strReg = "<option value=''>-选择"+(title?title:"")+"区县-</option>";
					$("#Village").append(strReg);
					//绑定联动事件 修改人zhujj
					$("#City").change(function(){
						$("#Village").empty();
						strReg = "<option value=''>-选择"+(title?title:"")+"区县-</option>";
						if($("#City").val()!=""){
							$.each(sysReguins, function (i, item){
								if(item.regId==$("#City").val()){
									$.each(item.child, function (j, itemchild){
										strReg += "<option value='" + itemchild.regId+"'>"+itemchild.regName+ "</option>";
									});
								}
							});
						}
						$("#Village").append(strReg);
					});
					
				}
			}
		},
		error : function(data) {
			alertModel('获取用户地区信息失败!');
		}
	});

}

//条件合同
function loadTableData() {
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
		$("#tb").bootstrapTable({
			method : "post",
			contentType : "application/x-www-form-urlencoded",
			url : "queryElecWarningList", // 获取数据的地址
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
		            pregId : $("#City option:selected").val(),//地市id
		            regId :	$("#Village option:selected").val(),
		            paymentState :	$("#auditingStateQ").val(),
		            billaccountCode :	$("#billaccountCodeQ").val(),
		            billaccountName :	$("#billaccountNameQ").val()
				};
				return param;
			},
			columns: [{
	            field: 'billaccountCode',
	            title: '报账点编码',
	        }, {
	            field: 'billaccountName',
	            title: '报账点名称'
	        }, {
	            field: 'pregName',
	            title: '所属地市'
	        }, {
	            field: 'regName',
	            title: '所属区县'
	        },{
	            field: 'billaccountState',
	            title: '报账点状态',
	            formatter:function(value,row,index){
	            	switch(value){
	            		case 0:return '正常';
	            		case 9:return '暂停';
	            		case -1:return '已删除';
	            		default : "";
	            	}
	            	return value;
	            }
	        }, {
	            field: 'billamountEnddate',
	            title: '上次缴费期终',
	        	formatter : function(value,row,index){
	        		if(value != null){
	        			return new Date(value).format("yyyy-MM-dd");
	        		}else{
	        			return "未开始缴费";
	        		}
	            }
	        }, {
	            field: 'planDate',
	            title: '计划缴费日期',
	            formatter : dateFormat
	        }, ],
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

/**
 * 导出
 */
function exportEleWarning(){
	confirmModel("您确定要导出吗？","exportInfo");
} 

function exportInfo(){
	var para="&pregId="+ $("#City").val();
	para+="&regId="+ $("#Village").val();
	para+="&paymentState="+ $('#auditingStateQ').val();
	para+="&billaccountCode="+ $('#auditingStateQ').val();
	para+="&billaccountName="+ $('#billaccountNameQ').val();
	window.open("exportEleWarning?"+para,"_blank");
}

function dateFormat(value,row,index){
	if(value != null){
		return new Date(value).format("yyyy-MM-dd");
	}else{
		return "-";
	}
}
