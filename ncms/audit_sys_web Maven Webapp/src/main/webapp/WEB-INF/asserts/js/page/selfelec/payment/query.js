var operate_type = 1;// 1 新增，2 修改
var curPageNum = 0;
var cityList = null;
var regionList = null;

//初始化
$(function(){
	initCurrentPage();
});

/**
 * 初始化加页面
 */
function initCurrentPage() {
	curPageNum = 1;
	// 查询默认条件表单数据
	loadTableData();
	getAddress();
}

//报账点列表
function loadTableData() {
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryVEleBillaccountPaymentInfo", // 获取数据的地址
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
	            billaccountCode : 	$('#billaccountCodeQ').val(),
	            billaccountName	:	$("#billaccountNameQ").val(),
	            pregId :	$("#city").val(),
	            regId :	$("#region").val(),
	            auditingState :	$("#auditingStateQ").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        },{
            field: 'auditingState',
            title: '审核状态',
            formatter:function(value,row,index){
            	// 0:审核通过, 8:审核未通过, 9:审核中, -1:未提交
            	switch(value){
            		case 0:return '审核通过';
            		case 8:return '审核未通过';
            		case 9:return '审核中';
            		case -1:return '未提交';
            	}
            	return value;
            }
        }, {
            field: 'pregName',
            title: '所属地市'
        }, {
            field: 'regName',
            title: '所属区县'
        }, {
            field: 'billaccountCode',
            title: '报账点编码'
        }, {
            field: 'billaccountName',
            title: '报账点名称'
        },{
            field: 'contractCode',
            title: '合同编码'
        }, {
            field: 'contractName',
            title: '合同名称'
        },{
            field: 'billamountDate',
            title: '缴费申请日期'
        },
        {
            field: 'billamountStartdate',
            title: '缴费期始',
            formatter:function(value,row,index){
            	if(value==null){
            		return "-";
            	}
            	return "<a><font color='red'>"+value+"</font></a>";
            }
        },
        {
            field: 'billamountEnddate',
            title: '缴费期终',
            formatter:function(value,row,index){
            	if(value==null){
            		return "-";
            	}
            	return "<a><font color='red'>"+value+"</font></a>";
            }
        },
        {
            field: 'billamountNotax',
            title: '不含税金额'
        },
        {
            field: 'taxRate',
            title: '税率'
        },
        {
            field: 'billamountTaxamount',
            title: '税金'
        },
        {
            field: 'lossAmount',
            title: '电损'
        },
        {
            field: 'otherAmount',
            title: '其它费用'
        },
        {
            field: 'billAmountActual',
            title: '报账金额'
        },
        ],
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
 * 查看明细 弹窗
 */
function detailPopup(row){ 
	var ids = $('#tb') .bootstrapTable('getAllSelections') ;

	if (ids.length <= 0) {
		alert("至少选择一条操作数据!");
		return false;
	}
	
	if(ids && ids.length >1){
		 alert("最多选择一条进行操作");
		 return;
	 }
	
	localStorage.setItem("billaccountPaymentInfo" , JSON.stringify(ids[0]));
	window.location.href="manage_detail.html";
}

/**
 * 获取用户所有权限 的地市 区县信息
 * 
 * @param title 名称 例如：供应商地市，传入title为供应商
 */
function getAddress(){
	$.ajax({
		type : "post",
		url : "../../common/region/getAddressElec",
		
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		success : function(value) {
			if(value != null){
				sysReguins = value.obj;
				if(sysReguins!=null){
					$('#city').empty();
					$('#region').empty();
					var strCity = "<option value=''>-选择地市-</option>";
					$.each(sysReguins, function (i, item){
						strCity += "<option value='" +item.regId+"'>"+item.regName+ "</option>";
						
					});

					$("#city").append(strCity);

					var strReg = "<option value=''>-选择区县-</option>";
					$("#region").append(strReg);
					//绑定联动事件 修改人zhujj
					$("#city").change(function(){
						$("#region").empty();
						strReg = "<option value=''>-选择区县-</option>";
						if($("#city").val()!=""){
							$.each(sysReguins, function (i, item){
								if(item.regId==$("#city").val()){
									$.each(item.child, function (j, itemchild){
										strReg += "<option value='" + itemchild.regId+"'>"+itemchild.regName+ "</option>";
									});
								}
							});
						}
						$("#region").append(strReg);
					});
					
				}
			}
		},
		error : function(data) {
			alertModel('获取用户地区信息失败!');
		}
	});
}
