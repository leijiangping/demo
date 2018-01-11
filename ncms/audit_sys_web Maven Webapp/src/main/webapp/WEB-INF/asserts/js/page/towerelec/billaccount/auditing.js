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
	getAddress();
	curPageNum = 1;
	// 查询默认条件表单数据
	loadTableData();
}

//条件合同  xup
function loadTableData() {
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryReimburseInfoVO", // 获取数据的地址
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
	            billaccountState :	$("#billaccountStateQ").val(),
	            taskDefKey:$("#taskDefKey").val()
			};
			return param;
		},
		columns: [{
			field: '',
            title: '',
            formatter:function(value,row,index){
            	if(row["auditingState"] == 9){
            		return "<input type='checkbox' name='ckb' id=\"ckb"+index+"\" onclick=\"selectClick("+index+")\"/>";
            	}else{
            		return "";
            	}
            }
        }, {
            field: 'auditingState',
            title: '审核状态',
            formatter:function(value,row,index){
            	// 0:待审核 1:审核中 2:审核通过 3:审核不通过
            	switch(value){
	            	case 0:return '审核通过';
	        		case 8:return '审核未通过';
	        		case 9:return '审核中';
	        		case -1:return '未提交';
            	}
            	return value;
            }
        },{
            field: 'billaccountCode',
            title: '报账点编码',
            formatter:function(value,row,index){
            	return "<a><font color='red'>"+value+"</font></a>";
            }
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
            		case 10:return '终止';
            	}
            	return value;
            }
        }, {
            field: '',
            title: '报账点资源关系',
            formatter:function(value,row,index){
            	return "<a onclick='tuopoPopup(\""+row["billaccountId"]+"\");'>报账点资源关系</a>";
            }
        }, ],
        onClickCell: function (field, value, row, $element) {
        	if(field == "billaccountCode"){
        		detailPopup(row);
        	}
        },
		onLoadError : function() { // 加载失败时执行s
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				showTableList = res.obj.list;
				unique(showTableList);
			}
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.list //数据
	         };
		}
	});
}

function selectClick(index){
	var isChecked = $("#ckb"+index).prop('checked');
	if(isChecked){
		$("#tb").bootstrapTable('check', index);
	}else{
		$("#tb").bootstrapTable('uncheck', index);
	}
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

function show(row){
	
	$("#billaccountId").val(row.billaccountId);
	 $("#billaccountCode").val(row.billaccountCode);
	 $("#billaccountName").val(row.billaccountName);
	 $("#billaccountState").val(row.billaccountState);
	 $("#modifyCity").val(row.pregId);
	 $("#billaccountType").val(row.billaccountType);
	 $("#planDate").val(row.planDate);
	 $("#calcMulti").val(row.calcMulti);
	 $("#billaccountNote").val(row.billaccountNote);
	 
	 $("#modifyRegion").html("");
	 var str = "<option value=''>-选择区县-</option>";
	 $.ajax({
	        type: "GET",
	        url: "../../regions",
	        data: {"cityId": row.pregId},
	        dataType: "JSON",
	        success: function (data) {
	            //从服务器获取数据进行绑定
	            $.each(data.Obj, function (i, item) {
	                str += "<option value=" + item.regId + ">" + item.regName + "</option>";
	            });
	            //将数据添加到省份这个下拉框里面
	            $("#modifyRegion").append(str);
	            
	            $("#modifyRegion").val(row.regId);
	       	 
	       	 
	        	$("#reviewDiv").modal("show");
	        },
	        error: function () { alert("获取区县信息异常！"); }
	    });
	
}

/**
 * 导出信息
 */
function exportPopup(){
	//$("#queryInfoDiv #subsysSel").attr("disabled",false);
	//$("#queryInfoDiv #isTrunkSel").attr("disabled",false);
	//$("#queryInfoDiv #saveConfigBtn").attr("disabled",false);
	var form = $("<form>");  
    form.attr('style', 'display:none');  
    form.attr('target', '');  
    form.attr('method', 'post');  
    form.attr('action', 'export');  
    
    $('body').append(form);  

    form.submit();  
    form.remove();
}

function showPopup(row){
	show(row);
	
	$("#reviewFormDiv").hide();
	$("#billaccountNote").attr("disabled" , "disabled");
}

function reviewPopup(){
	var ids = $('#tb') .bootstrapTable('getAllSelections') ;
	if (ids.length <= 0) {
		alert("至少选择一条操作数据!");
		return false;
	}
	 if(ids && ids.length >1){
		 alert("最多选择一条进行修改操作");
		 return;
	 }
	 var taskId=ids[0].act.taskId;
	 localStorage.setItem("billaccountInfo" , JSON.stringify(ids[0]));
	 window.location.href='auditing_check.html?taskId='+taskId;
}


/**
 * 查看明细 弹窗
 */
function detailPopup(row){ 
	localStorage.setItem("billaccountInfo" , JSON.stringify(row));
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

function tuopoPopup(billaccountId){
	localStorage.setItem("billaccountId" , billaccountId);
	window.location.href = "tuopo.html";
}