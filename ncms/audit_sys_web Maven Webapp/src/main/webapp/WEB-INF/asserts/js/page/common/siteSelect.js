var siteCurPageNum = 0;
var sitelist = null;
var siteData = null;
$(document).ready(function() {
	init();
});
/**
 * 弹出站点查询窗口
 */
function siteBind() {
	siteCurPageNum = 1;
//	$("#siteForm")[0].reset(); // 清空表单
	$('#SitePanel').modal(); // 弹出表单
	getAddressSite();
}
/**
 * 初始化加页面
 */
function init() {
	$("#selectSite").load("../common/selectSite.html");
	$("#selectSite").css({position:'relative',zIndex:'999999'});
}
/**
 * 列表查询
 */
function findSite() {
	// 先销毁表格
	$('#tbSite').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tbSite").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "../site/list", // 获取数据的地址
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
					siteReg : $("#siteReg").val(),
					property : $("#siteProperty").val(),
					auditStatus : 0,
					status : $("#siteStatus").val(),
					city : $("#siteCity").val(),
					region : $("#siteRegion").val(),
					queryType : queryType,
					cur_page_num: params.pageNumber,    
					page_count: params.pageSize,
					flag : 0
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
            field: 'auditingState',
            title: '审核状态',
            formatter: fmtAuditState
        }, {
            field: 'basesiteCode',
            title: '站点编号'
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
            formatter: fmtProperty
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
function siteSelect(){
	var selectItems = $('#tbSite').bootstrapTable('getSelections');
	if(selectItems.length != 1){
		alertModel("请选择一条数据");
		return;
	}
	if(selectItems[0].auditingState == 9){
		alertModel("站点处于流程中，无法选择！");
		return;
	}
	siteSelectBack(selectItems);
}

function getAddressSite(citySelect,villageSelect){
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
			findSite();
		},
		error : function(data) {
			alertModel('获取用户地区信息失败!');
		}
	});
}