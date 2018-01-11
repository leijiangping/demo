//已显示表格list
var showTableList = null;

$(document).ready(function() {

	initCurrentPage();
});

function initCurrentPage(){
	curPageNum = 1;
	getAddress();
	loadTableData();
}
/**
 * 获取用户所有权限 的地市 区县信息
 * @param title 名称 例如：供应商地市，传入title为供应商
 */
function getAddress(title){
	$.ajax({
		type : "post",
		url : "getAddressPay",
		
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
		url : "queryPaymentContract", // 获取数据的地址
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
	            billAccountName:$("billAccountName").val(),
	            pregId :$("#City option:selected").val(),
	            regId :	$("#Village option:selected").val(),
	            billState :	$("#billState option:selected").val(),
	            paymentState :	$("#submitState option:selected").val()
			};
			return param;
		},
		columns: [{
            checkbox:true

        },{
        	field: 'billaccountCode',
            title: '报账点编码'
        },
        {
        	field: 'billaccountName',
            title: '报账点名称'
        },
        {
        	field: 'contractCode',
            title: '合同编码'
        },{
        	field: 'contractName',
            title: '合同名称'
        },{
//            field: 'auditState',
        	field: 'paymentFlowstate',
            title: '审批状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 0:return '审核完成';
            		case 8:return '审核未通过';
            		case 9:return '审核中';
            		case -1:return '未提交';
            		default:return '/';
            	}
            	return value;
            }
        },{
            field: 'sysRegionVO.pRegName',
            title: '所属地市'
        }, {
            field: 'sysRegionVO.regName',
            title: '所属区县'
        },{
        	field: 'paymentDate',
            title: '缴费申请日期',
            formatter : function(value){
            	return new Date(value).format("yyyy-MM-dd");
            }
        },{
        	field: 'paymentStartdate',
            title: '缴费期始',
            formatter : function(value){
            	return new Date(value).format("yyyy-MM-dd");
            }
        },{
        	field: 'paymentEnddate',
            title: '缴费期终',
            formatter : function(value){
            	return new Date(value).format("yyyy-MM-dd");
            }
        },{
        	field: 'includeTax',
            title: '是否含税',
            formatter : function(value){
            	switch(value){
        		case 0:return '否';
        		case 1:return '是';
        		default:return '/';
        	}
        	return value;
            }
        },{
        	field: 'billamountTaxratio',
            title: '税率'
        },{
        	field: 'billamountTaxamount',
            title: '税金'
        },{
        	field: 'dueamount',
            title: '不含税金额'
        },{
        	field: 'payActamount',
            title: '报账金额'
        },{
        	field: 'paymentNote',
            title: '缴费备注'
        }],
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

function checkInfo(){
	if(!hadCheckedRowData()){
		return false;
	}
	localStorage.clear();
	localStorage.setItem("paymentId",rowschecked[0].paymentId);
	submitApproval();
}
function hadCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}
//提交审批
function submitApproval(){
	if(rowschecked[0].paymentFlowstate==9){
		alertModel("该数据正在审核");
		return false;
	}
	queryFirstUsersByProcDefKey(RentBillAccount.procDefKey,"formSubmit",rowschecked[0].sysRegionVO.regId);	
}
//修改
function revamp(){
	if(!hadCheckedRowData()){
		return false;
	}
	if(rowschecked[0].paymentFlowstate==9){
		alertModel("审核中数据不能修改");
		return false;
	}
	var paymentId=rowschecked[0].paymentId;
	localStorage.setItem("billaccountId",rowschecked[0].billaccountId);
	localStorage.setItem("billaccountcontractId",rowschecked[0].billaccountcontractId);
	window.location.href='revampData.html?paymentId='+paymentId+'';
}

//提交流程
function formSubmit(){
//	var paymentId=localStorage.getItem("paymentId");
	var paymentIds=new Array();
	for(var i=0;i<rowschecked.length;i++){
		paymentIds.push(rowschecked[0].paymentId)
	}
	var userLoginname=$("input[name='userChecked']:checked").val();
	var datasObjs = new Array();
	for(var i = 0; i < paymentIds.length; i++){
		var id = paymentIds[i];
		var obj={"paymentId":id,"nextUserId":userLoginname};
		datasObjs.push(obj);
	}
	$.ajax({
		url : 'commitProcess',// 跳转到 action    
		data : JSON.stringify(datasObjs),
		type : 'post',
		dataType : 'json',
		contentType : 'application/json;charset=utf-8',
		success : function(res) {
			if (res != null) {
				alertModel(res.msg);
				loadTableData();
			}
			$('#firstUsers').modal('hide');
			
		},
		error : function() {
			alertModel("请求失败");
		}
	});
}

function goBack(){
	localStorage.clear();
	javascript:history.back(-1);
}

