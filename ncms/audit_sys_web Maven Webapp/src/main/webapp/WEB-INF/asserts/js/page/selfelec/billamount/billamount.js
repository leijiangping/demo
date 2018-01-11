$(function(){
	loadSearch();
});
//加载查询条件
function loadSearch(){
	loadTableData();
	getAddress();
}
//租费合同汇总
function loadTableData() {
	var billamountDateStart=$("#billamountDateStart").val();
	var billamountDateEnd=$("#billamountDateEnd").val();
	if(billamountDateStart>billamountDateEnd){
		alertModel("开始日期不能大于结束日期");
		return false;
	}
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryVEleBillamount", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		pageSize : ipageCount, // 每页显示的记录数
		pageNumber : curPageNum, // 当前第几页
		minimumCountColumns: 1,  //最少允许的列数
		clickToSelect: false,  //是否启用点击选中行
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
	            
	            billamountCode:$('#billamountCode').val(),
		        pregId:$("#city").val(),
		        regId:$("#region").val(),
		        billamountDateStart:billamountDateStart,
		        billamountDateEnd:billamountDateEnd,

		        billamountState:$("#billamountState").val(),
		        contractCode:$("#rentcontractCode").val(),//合同名称或者合同代码
		        supplierCode:$("#supplierCode").val()//供应商代码或者名称
		        //,billaccountCode:$("#billaccountCode").val()/报账点代码或者名称
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
            field: 'billamountCode',
            title: '汇总单编码',
            formatter : billamountCodeFormatter
        }, {
            field: 'billamountDate',
            title: '汇总日期',
            formatter : function(value){
            	return new Date(value).format("yyyy-MM-dd");
            }
        }, {
        	field: 'billamountState',
            title: '推送状态',
            formatter:function(value){
            	value = value+"";
            	switch(value){
            		case "-1":return '未推送';
            		case "0":return '已推送';
            		case "1":return '已报账';
            		case "2":return '已审核';
            		case "8":return '已撤销';
            	}
            	return value;
            }
        }, {

        	field: 'detailCnt',
            title: '付款数量'
        }, {

            field: 'billamountWithtax',
            title: '汇总报账金额'
        }, {
            field: 'pregName',
            title: '地市'
        }, {
            field: 'regName',
            title: '区域'
        }, {
            field: 'supplierCode',
            title: '供应商编码'
        }, {
            field: 'supplierName',
            title: '供应商名称'
        }
        ],
        
       /* onClickRow: function (row, td) {
        	var msg = row.rentcontractId;
        	window.location.href='rentcontractDetail.html';
        },*/	
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
function billamountDetailNum(billamountDetails){
	var num=0;
	$.each(billamountDetails,function(i,v){
		num+=v.billamountNumber;
	});
	return num;
}
//生成汇总单
function createBillamount(){
	location.href="create_summary_sheet.html?v=2";
	
}
//推送汇总单
function pushBillamount(){
	var rows= $("#tb").bootstrapTable('getSelections');
	if(rows.length==0){
		alertModel("请选择至少一条数据");
		return;
	}
	var billamountStateFlag=false;
	var billamountCode;
	var ids=new Array();
	$.each(rows,function(i,row){
		if(rows.billamountState==0){//0是已推送，-1未推送
			billamountStateFlag=true;
			billamountCode=rows.billamountCode;
			return;
		}else{
			ids.push(row.billamountId);
		}
	});
	if(billamountStateFlag){
		alertModel("汇总单编码："+billamountCode+"为已经推送的数据");
		return;
	}
	 $.ajax({
		  type : "POST",
		  url : 'pushBillamount',
		  data:{"params":JSON.stringify(ids)},
		  dataType : 'json',
		  cache : false,
		  success : function(result) {
			   if (result != null&&result.success=="1") {
				   alertModel(result.msg);
				   loadTableData();
				}else{
					alertModel(result.msg);
				}
		  }
	 });
}
//查看待汇总缴费
function viewBillamount(){
	location.href="create_summary_sheet.html?oper=view&v=2";
	
}

//删除选中的未推送
function deleteBillamount(){
	var rows= $("#tb").bootstrapTable('getSelections');
	if(rows.length==0){
		alertModel("请选择至少一条数据");
		return;
	}
	
	var flag=confirm("确认删除么？")
	if(flag==false){
		return;
	}

	var ids=new Array();
	$.each(rows,function(i,row){
		//过滤出未推送 和已撤销的可以删除，
		if(row.billamountState=="-1"||row.billamountState=="8"){
			ids.push(row.billamountId);
		}
	});
	
	if(ids.length == 0){
		alertModel("请选择至少一条未推送数据");
		return;
	}
	
	 $.ajax({
		  type : "POST",
		  url : 'deleteBillamount',
		  data:{"params":JSON.stringify(ids)},
		  cache : false,
		  success : function(result) {
			   if (result != null&&result.success=="1") {
				   alertModel(result.msg,loadTableData);
				   //loadTableData();
				}else{
					 alertModel(result.msg);
				}
		  }
	 });
}

function billamountCodeFormatter(value, row, index){
	return '<a href="summary_sheet.html?billamountId='+row.billamountId+'">'+value+'</a>';
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
					$('#city').empty();
					$('#region').empty();
					var strCity = "<option value=''>--选择地市--</option>";
					$.each(sysReguins, function (i, item){
						strCity += "<option value='" +item.regId+"' "+(citySelect&&citySelect==item.regId?"selected='true'":"")+">"+item.regName+ "</option>";
					});

					$("#city").append(strCity);

					var strReg = "<option value=''>--选择区县--</option>";
					$("#region").append(strReg);
					//绑定联动事件 修改人zhujj
					$("#city").change(function(){
						$("#region").empty();
						strReg = "<option value=''>--选择区县--</option>";
						if($("#city").val()!=""){
							$.each(sysReguins, function (i, item){
								if(item.regId==$("#city").val()){
									$.each(item.child, function (j, itemchild){
										strReg += "<option value='" + itemchild.regId+"' "+(villageSelect&&villageSelect==itemchild.regId?"selected='true'":"")+">"+itemchild.regName+ "</option>";
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