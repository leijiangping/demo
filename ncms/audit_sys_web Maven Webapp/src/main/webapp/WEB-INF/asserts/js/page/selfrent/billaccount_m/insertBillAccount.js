//已显示表格list
var showTableList = null;

$(document).ready(function() {
	initCurrentPage();
	getAddress();
});

function initCurrentPage(){
	curPageNum = 1;
	loadTableData();
}
/**
 * 获取用户所有权限 的地市 区县信息
 * @param title 名称 例如：供应商地市，传入title为供应商
 */
function getAddress(title){
	$.ajax({
		type : "post",
		url : "getAddressBill",
		
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
//查询报账点信息
function loadTableData() {
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryBillAccountVO", // 获取数据的地址
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
				pageNumber		: 	params.pageNumber,    
	            pageSize		: 	params.pageSize,
	            billaccountName : 	$("#billaccountName").val(),
				pRegId 			: 	$("#City").val(),
				regId 			: 	$("#Village").val(),
				billaccountState: 	$("#billaccountState").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
            title: '序号',
            formatter: function (value, row, index) {  
                return index+1;  
            }  
        }, {
            field: 'isPay',
            title: '缴费状态'
        }, {
        	field: 'billAccountCode',
            title: '报账点编码'
        }, {
        	field: 'billAccountName',
            title: '报账点名称'
        }, {
            field: 'sysRegionVO.pRegName',
            title: '所属地市'
        }, {
            field: 'sysRegionVO.regName',
            title: '所属区县'
        }, {
            field: 'billAccountState',
            title: '报账点状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 0:return '正常';
            		case 9:return '终止';
            		default:return '/';
            	}
            	return value;
            }
        }, {
            field: '',
            title: '报账点资源关系',
            formatter:function(value,row,index){
            	return "<a onclick='tuopoPopup(\""+row["billAccountId"]+"\");'>报账点资源关系</a>";
            }
        }, ],
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success != "1"){
		            alertModel(res.msg);
				}
				showTableList = res.obj.result;
			}
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.result //数据
	         };
		}
	});
}


function reviewContract(){
	if(!hadCheckedRowData()){
		return false;
	}
	localStorage.setItem("item1",rowschecked[0].rentcontractId);
	window.location.href='Info_check.html';
	
}

function update(){
	if(!hadCheckedRowData()){
		return;
	}
	var msg = rowschecked[0].billAccountId;
	localStorage.setItem("billAccountId",msg);
	window.location.href="rent_c_enter.html";
}

function hadCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}
function tuopoPopup(billaccountId){
	localStorage.setItem("billaccountId" , billaccountId);
	window.location.href = "tuopo.html";
}
