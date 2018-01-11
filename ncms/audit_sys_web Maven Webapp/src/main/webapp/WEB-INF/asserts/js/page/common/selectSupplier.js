var siteCurPageNum = 0;
$(document).ready(function() {
	initSelectSupplier();
});
/**
 * 弹出站点查询窗口
 */
function supplierBind() {
	getAddress();
	siteCurPageNum = 1;
//	$("#siteForm")[0].reset(); // 清空表单
	$('#SupplierPanel').modal(); // 弹出表单
	findSelectSupplier();
}
/**
 * 初始化加页面
 */
function initSelectSupplier() {
	supplierBind();
	$("#selectSupplier").load("../../common/selectSupplier.html");
	$("#selectSupplier").css({position:'relative',zIndex:'999999'});
}
/**
 * 列表查询
 */
function findSelectSupplier() {
	// 先销毁表格
	$('#tbSupplier').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tbSupplier").bootstrapTable({
		method : "get",
		contentType : "application/x-www-form-urlencoded",
		url : "../../basedata/supplier/list", // 获取数据的地址
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
					city : $("#siteCity option:selected").val(),
					region : $("#siteRegion option:selected").val(),
					supplierReg : $("#supplierReg").val(),
					state : 0,
					sRegId : $("#regId").val(),
					sPregId : $("#pregId").val(),
					cur_page_num: params.pageNumber,    
					page_count: params.pageSize
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
            field: 'Number',
            title: '序号',
            formatter: function (value, row, index) {
                   return index+1;
            }
        }, {
            field: 'supplierCode',
            title: '供应商编号'
        }, {
            field: 'supplierName',
            title: '供应商名称'
        }, {
            field: 'pregName',
            title: '所属地市'
        }, {
            field: 'regName',
            title: '所属区县'
        }, {
            field: 'supplierSite',
            title: '地点'
        }, {
            field: 'supplierContact',
            title: '联系人'
        }, {
            field: 'supplierTelephone',
            title: '联系电话'
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
function supplierSelect(){
	var selectItems = $('#tbSupplier').bootstrapTable('getSelections');
	if(selectItems.length != 1){
		alertModel("请选择一条数据");
		return;
	}
	backSupplierSelect(selectItems);
}

function getAddress(citySelect,villageSelect){
	$.ajax({
		type : "post",
		url :"../../common/region/getAddressElec",
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		success : function(value) {
			if(value != null){
				sysReguins = value.obj;
				if(sysReguins!=null){
					$('#siteCity').empty();
					$('#siteRegion').empty();
					var strCity = "<option value=''>--选择地市--</option>";
					$.each(sysReguins, function (i, item){
						strCity += "<option value='" +item.regId+"' "+(citySelect&&citySelect==item.regId?"selected='true'":"")+">"+item.regName+ "</option>";
					});

					$("#siteCity").append(strCity);

					var strReg = "<option value=''>--选择区县--</option>";
					$("#siteRegion").append(strReg);
					//绑定联动事件 修改人zhujj
					$("#siteCity").change(function(){
						$("#siteRegion").empty();
						strReg = "<option value=''>--选择区县--</option>";
						if($("#siteCity").val()!=""){
							$.each(sysReguins, function (i, item){
								if(item.regId==$("#siteCity").val()){
									$.each(item.child, function (j, itemchild){
										strReg += "<option value='" + itemchild.regId+"' "+(villageSelect&&villageSelect==itemchild.regId?"selected='true'":"")+">"+itemchild.regName+ "</option>";
									});
								}
							});
						}
						$("#siteRegion").append(strReg);
					});
				}
			}
		},
		error : function(data) {
			alertModel('获取用户地区信息失败!');
		}
	});
}