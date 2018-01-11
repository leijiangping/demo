var billaccountId ;
var regId ;
var billaccountInfo;

var curPageNum = 1;//当前页码：1
var ipageCount = 10;//每页条数

//初始化
$(function(){
	
	 billaccountInfo = localStorage.getItem("billaccountInfo");
	 billaccountInfo = JSON.parse(billaccountInfo);
	 
	 billaccountId = billaccountInfo.billaccountId;
	 regId = billaccountInfo.regId;
	 loadRelationedContractTableData(billaccountId);
	 loadRelationedResourceTableData(billaccountId , regId);
	 checkInfo(regId);
	 queryCurUser();
	 initModifyCityCallback();
});

function initModifyCityCallback(){
	initData();
}

function initData(){
	 
	 $("#billaccountId").val(billaccountInfo.billaccountId);
	 $("#billaccountCode").val(billaccountInfo.billaccountCode);
	 $("#billaccountName").val(billaccountInfo.billaccountName);
	 $("#billaccountState").val(billaccountInfo.billaccountState);
	 
	 $("#billaccountType").val(billaccountInfo.billaccountType);
	 $("#planDate").val(billaccountInfo.planDate);
	 $("#calcMulti").val(billaccountInfo.calcMulti);
	 $("#billaccountNote").val(billaccountInfo.billaccountNote);
	 
	 $("#modifyRegion").html("");
	 $('#entry_information input').attr('readonly','readonly');
	 $('#entry_information select').attr('disabled','disabled');
	 getAddressUpdate(billaccountInfo.pregId,billaccountInfo.regId);
}

//已关联合同列表
function loadRelationedContractTableData(billaccountId) {
	
	// 先销毁表格
	$('#contracttb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#contracttb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryContractInfo", // 获取数据的地址
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
	            billaccountId :	billaccountId
			};
			return param;
		},
		columns: [{
            radio: true
        }, {
            field: 'contractCode',
            title: '合同编码',
            formatter:function(value,row,index){
            	return "<a><font color='red'>"+value+"</font></a>";
            }
        }, {
            field: 'contractName',
            title: '合同名称'
        }, {
            field: 'pregName',
            title: '所属地市'
        }, {
            field: 'regName',
            title: '所属区县'
        }, {
            field: 'contractStartdate',
            title: '合同期始'
        }, {
            field: 'contractEnddate',
            title: '合同期终'
        },{
            field: 'contractState',
            title: '合同状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 0:return '正常';
            		case 9:return '停用';
            	}
            	return value;
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
				showTableList = res.obj.list;
			}
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.list //数据
	         };
		}
	});
}

//已关联资源列表
function loadRelationedResourceTableData(billaccountId , regId) {
	
	$.post("queryResourceTree" , {"billaccountId":billaccountId , "regId":regId} , function(data){
		var str = '';
		$.each(data.obj,function(i,item){
			
			if(item.parentId){
				switch(item.baseresource_type){
	    		case 1:
	    			baseresource_type= '普通表';
					break;
	    		case 2:
	    			baseresource_type= '平峰谷表';
					break;
				}
			}else{
				switch(item.baseresource_type){
				case 0:
					baseresource_type= '机房';
					break;
	    		case 1:
	    			baseresource_type= '资源点';
					break;
	    		case 2:
	    			baseresource_type= '热点';
					break;
				}
			}
	/*		switch(item.baseresource_type){
				case 0:
					baseresource_type="机房";
					break;
				case 1:
					baseresource_type="资源点";
					break;
				case 2:
					baseresource_type="热点";
					break;
			}*/
			switch(item.baseresource_state){
			case 1:
				baseresource_state="在网";
				break;
			case 2:
				baseresource_state="工程";
				break;
			case 3:
				baseresource_state="退网";
				break;
		}
			str += '<tr data-node-id="' + item.nodeId + '"';
			if(!item.parentId){
				str += '  data-tt-branch="true"><td><span class="folder"></span><input type="checkbox" name="resourceCk" value="'+item.id+'"/></td>';
			}else{
				str += ' data-parent-id="'+item.parentId +'" data-tt-branch="false"><td><span class="file"></span><input type="hidden" name="relId"  value="'+item.id+'"></td>';
			}
			str += 
					'<td>'+baseresource_type+'</td>' +
					'<td>'+item.baseresource_code+'</td>' +
					'<td>'+item.baseresource_name+'</td>' +
					'<td>'+baseresource_state+'</td>' ;
			if(!item.parentId){
				str += '<td></td>';
			}else{
				str += '<td></td>';
			}
			str += '</tr>';
		});
		
		$("#resourcetreetable tbody").html(str);

		$("#resourcetreetable").treetable({
			nodeIdAttr: "nodeId",
			parentIdAttr: "parentId",
			stringCollapse: "收起",
			stringExpand: "展开",
			expandable: true,
			
			//展开
			onNodeExpand: function(){
				var node = this;
				//是否已经加载
				if(node.children && !node.children.length ){
					
				}
				
			}
		}).on("click",".del",function(e){
			e.preventDefault();
			var meterId = $(this).closest("tr").data("node-id");		
			var baseresourceId = $(this).closest("tr").data("parent-id");	
			var baseresourceelectricmeterId = $(this).closest("tr").find("input[name='relId']").val();
			console.log("meterId:"+meterId +" baseresourceId:"+baseresourceId+" baseresourceelectricmeterId:"+baseresourceelectricmeterId);
			deselElectricmeterPopup(meterId , baseresourceId , baseresourceelectricmeterId);
		}).on("click",".add",function(e){
			e.preventDefault();
			var baseresourceId = $(this).closest("tr").data("node-id");		
			console.log("baseresourceId:"+baseresourceId +" billaccountId:"+billaccountInfo.billaccountId +" regId:"+billaccountInfo.regId );

			selElectricmeterPopup(billaccountInfo.billaccountId , billaccountInfo.regId ,  baseresourceId);
			
		});
	});
	
//	// 先销毁表格
//	$('#resourcetb').bootstrapTable('destroy');
//	// 初始化表格,动态从服务器加载数据
//	$("#resourcetb").bootstrapTable({
//		method : "post",
//		contentType : "application/x-www-form-urlencoded",
//		url : "queryResourceInfo", // 获取数据的地址
//		striped : true, // 表格显示条纹
//		pagination : true, // 启动分页
//		pageSize : ipageCount, // 每页显示的记录数
//		pageNumber : curPageNum, // 当前第几页
//		minimumCountColumns: 1,  //最少允许的列数
//		clickToSelect: false,  //是否启用点击选中行
//		pageList : [10, 25, 50, 100, 500], // 记录数可选列表
//		search : false, // 是否启用查询
//		sidePagination : "server", // 表示服务端请求
//		// 设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
//		// 设置为limit可以获取limit, offset, search, sort, order
//		queryParamsType : "undefined",
//		queryParams : function queryParams(params) { // 设置查询参数
//			var param = {
//					pageNumber: params.pageNumber,    
//		            pageSize: params.pageSize,
//	            billaccountId :	billaccountId,
//	            regId :	regId
//			};
//			return param;
//		},
//		columns: [{
//            checkbox: true
//        }, {
//            field: 'baseresourceType',
//            title: '资源类型',
//            formatter:function(value,row,index){
//            	//资源类别（0：机房  1：资源点  2：热点）
//            	switch(value){
//            		case '0':return '机房';
//            		case '1':return '资源点';
//            		case '2':return '热点';
//            	}
//            	return value;
//            }
//        },{
//            field: 'baseresourceCode',
//            title: '资源编码',
//            formatter:function(value,row,index){
//            	return "<a><font color='red'>"+value+"</font></a>";
//            }
//        }, {
//            field: 'baseresourceName',
//            title: '资源名称'
//        },{
//            field: 'baseresourceState',
//            title: '资源状态'
//        }, {
//            field: '',
//            title: '关联电表',
//            formatter:function(value,row,index){
//            	return "<a onclick='selElectricmeterPopup(\""+row["billaccountId"]+"\",\""+row["regId"]+"\",\""+row["baseresourceId"]+"\");'>关联电表</a><div id='"+row["baseresourceId"]+"_div'></div>";
//            }
//        },  ],
//		onLoadError : function() { // 加载失败时执行
//			console.log("请求失败！");
//		},
//		responseHandler: function(res) {
//			if(res != null){//报错反馈
//				if(res.success != "1"){
//		            alertModel(res.msg);
//				}
//				showTableList = res.obj.list;
//			}
//	        return {
//	            "total": res.obj.total,//总页数
//	            "rows": res.obj.list //数据
//	         };
//		},
//		//无线循环取子表，直到子表里面没有记录
//        onExpandRow: function (index, row, $Subdetail) {
//        	var parentid = row.MENU_ID;
//            var cur_table = $detail.html('<table></table>').find('table');
//            alert(parentid);
//            $.ajax({
//        		  type : "POST",
//        		  url : 'queryElectricmeterInfo',
//        		  data:{"baseresourceId":row["baseresourceId"] , "billaccountId":row["billaccountId"] , "regId":row["regId"]},
//        		  dataType : 'json',
//        		  cache : false,
//        		  success : function(data) {
//        			  alert(data.length);
//        			  var trhtml = "<tr><td>";
//        			  for(var i=0;i<data.length;i++){
//        				  trhtml += "<span>"+data[i].meterCode+"</span>"
//        			  }
//        			  trhtml += "</td></tr>";
//        			  $(cur_table).append(trhtml);
//        		  }
//        	 });
//        }
//	});
}


//已关联电表列表
function loadRelationedElectricmeterData(billaccountId , regId , resourceId) {
	$.ajax({
		  type : "POST",
		  url : 'queryElectricmeterInfo',
		  data:{"baseresourceId":resourceId , "billaccountId":billaccountId , "regId":regId},
		  dataType : 'json',
		  cache : false,
		  success : function(data) {
			  
		  }
	 });
}


//合同列表
function loadContractTableData(billaccountId) {
	
	// 先销毁表格
	$('#relationcontracttb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#relationcontracttb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryUnrelationContractInfo", // 获取数据的地址
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
	           // contractName : 	$('#contractName').val(),
	            //prvId	:	$("#Province option:selected").val(),
	            billaccountId :	billaccountId
			};
			return param;
		},
		columns: [{
            radio: true
        }, {
            field: 'contractCode',
            title: '合同编码',
            formatter:function(value,row,index){
            	return "<a><font color='red'>"+value+"</font></a>";
            }
        }, {
            field: 'contractName',
            title: '合同名称'
        }, {
            field: 'pregName',
            title: '所属地市'
        }, {
            field: 'regName',
            title: '所属区县'
        }, {
            field: 'contractStartdate',
            title: '合同期始'
        }, {
            field: 'contractEnddate',
            title: '合同期终'
        },{
            field: 'contractState',
            title: '合同状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 0:return '正常';
            		case 9:return '停用';
            	}
            	return value;
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
				showTableList = res.obj.list;
			}
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.list //数据
	         };
		}
	});
}

//资源列表
function loadResourceTableData(billaccountId , regId) {
	// 先销毁表格
	$('#relationresourcetb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#relationresourcetb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryUnrelationResourceInfo", // 获取数据的地址
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
	           // contractName : 	$('#contractName').val(),
	            //prvId	:	$("#Province option:selected").val(),
	            billaccountId :	billaccountId,
	            regId :	regId
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
            field: 'baseresourceType',
            title: '资源类型',
            formatter:function(value,row,index){
            	//资源类别（0：机房  1：资源点  2：热点）
            	switch(value){
            		case 0:return '机房';
            		case 1:return '资源点';
            		case 2:return '热点';
            	}
            	return value;
            }
        },{
            field: 'baseresourceCode',
            title: '资源编码',
            formatter:function(value,row,index){
            	return "<a><font color='red'>"+value+"</font></a>";
            }
        }, {
            field: 'baseresourceName',
            title: '资源名称'
        },{
            field: 'baseresourceState',
            title: '资源状态'
        },  ],
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


//电表列表
function loadElectricmeterTableData(billaccountId , regId , resourceId) {
	
	// 先销毁表格
	$('#relationElectricmetertb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#relationElectricmetertb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryUnrelationElectricmeterInfo", // 获取数据的地址
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
	            "baseresourceId":resourceId , 
	            "billaccountId":billaccountId , 
	            "regId":regId
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
            field: 'meterCode',
            title: '电表编码'
        },{
            field: 'meterType',
            title: '电表类型',
            formatter:function(value,row,index){
            	//电表类型 1:普通表；2：平峰谷表
            	switch(value){
            		case 1:return '普通表';
            		case 2:return '平峰谷表';
            	}
            	return value;
            }
        }, {
            field: 'pregName',
            title: '所属地市'
        },{
            field: 'regName',
            title: '所属区县'
        }, {
            field: 'initialValue',
            title: '电表初始值'
        },{
            field: 'upperValue',
            title: '电表上限值'
        },{
            field: 'meterState',
            title: '电表状态',
            formatter:function(value,row,index){
            	switch(value){
            		case 0:return '正常';
            		case 9:return '停用';
            	}
            	return value;
            }
        },  ],
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


function back(){
	localStorage.removeItem('billaccountInfo');
	localStorage.removeItem('billaccountId');
	javascript:history.back(-1);
}

/**
 * 弹出 关联合同
 */
function selcontractPopup(){
	$("#selRelationContractDiv").modal("show");
	
	loadContractTableData(billaccountId);
}

/**
 * 取消 关联合同
 */
function deselcontractPopup(){
	var ids = $('#contracttb') .bootstrapTable('getAllSelections') ;
	
	if (ids.length <= 0) {
		alert("至少选择一条操作数据!");
		return false;
	}
		
	 if(ids && ids.length >1){
		 alert("最多选择一条进行取消操作");
		 return;
	 }
	 var contractId = ids[0].elecontractId;
	 var elebillaccountcontractId = ids[0].elebillaccountcontractId;
	$.ajax({
		  type : "POST",
		  url : 'relationContractCancel',
		  data:{"elebillaccountcontractId": elebillaccountcontractId , "elecontractId":contractId , "billaccountId":billaccountId},
		  dataType : 'json',
		  cache : false,
		  success : function(data) {
			  loadRelationedContractTableData(billaccountId);
		  }
	 });
}

/**
 * 弹出 关联资源
 */
function selresourcePopup(){
	$("#selRelationResourceDiv").modal("show");
	loadResourceTableData(billaccountId , regId);
}

/**
 * 取消 关联资源
 */
function deselresourcePopup(){
	
	var ids = [];
	$("input[type=checkbox][name='resourceCk']:checked").each(function(){ //由于复选框一般选中的是多个,所以可以循环输出选中的值  
	    ids.push($(this).val());  
	    //$(this).parentNode.parent.attr("data-node-id");
	});
	
	var billaccountbaseresource_ids =  ids.join(",");
	$.post("relationResourceCancel" , {"billaccountbaseresourceId":billaccountbaseresource_ids , "billaccountId":billaccountId} , function(data){
		loadRelationedResourceTableData(billaccountId , regId);
	});
	
//	var ids = $('#contracttb') .bootstrapTable('getAllSelections') ;
//	
//	if (ids.length <= 0) {
//		alert("至少选择一条操作数据!");
//		return false;
//	}
//		
//	 if(ids && ids.length >1){
//		 alert("最多选择一条进行取消操作");
//		 return;
//	 }
//	 var contractId = ids[0].contractId;
//	$.ajax({
//		  type : "POST",
//		  url : 'relationContractCancel',
//		  data:{"elecontractId":contractId , "billaccountId":billaccountId},
//		  dataType : 'json',
//		  cache : false,
//		  success : function(data) {
//			  loadRelationedContractTableData(billaccountId);
//		  }
//	 });
}

/**
 * 弹出 关联电表
 */
function selElectricmeterPopup(billaccountId , regId ,  baseresourceId){
	
	//loadElectricmeterTableData('09287f0bb378429f9b7853ae4448e8f7' , '310105' , 'resource-1498908773940');
	
	loadElectricmeterTableData(billaccountId , regId , baseresourceId);
	
	$("#relationElectricmeterResourceId").val(baseresourceId);
	
	$("#selRelationElectricmeterDiv").modal("show");
}

/**
 * 取消 关联电表
 */
function deselElectricmeterPopup(meterId , baseresourceId , baseresourceelectricmeterId){
	
	$.post("relationElectricmeterCancel" , {"baseresourceelectricmeterId":baseresourceelectricmeterId , "baseresourceId":baseresourceId , "meterId":meterId} , function(data){
		loadRelationedResourceTableData(billaccountId , regId);
	});
}

/**
 * 关联合同 保存
 */
function relationContractSubmit(){
	var ids = $('#relationcontracttb') .bootstrapTable('getAllSelections') ;

	if (ids.length <= 0) {
		alert("至少选择一条操作数据!");
		return false;
	}
	
	if(ids && ids.length >1){
		 alert("最多选择一条进行操作");
		 return;
	 }
	
	var contractId = ids[0].elecontractId;
	
	$.ajax({
		  type : "POST",
		  url : 'relationContractSave',
		  data:{"elecontractId":contractId , "billaccountId":billaccountId},
		  dataType : 'json',
		  cache : false,
		  success : function(data) {
			  loadRelationedContractTableData(billaccountId);
		  }
	 });
	
	
}

/**
 * 关联资源保存
 */
function relationResourceSubmit(){
	var ids = $('#relationresourcetb') .bootstrapTable('getAllSelections') ;

	if (ids.length <= 0) {
		alert("至少选择一条操作数据!");
		return false;
	}
	
	var baseresourceIds = [];
	for(var i=0;i<ids.length;i++){
		baseresourceIds.push(ids[i].baseresourceId);
	}
	
	
	var baseresourceId = baseresourceIds.join(",");
	
	$.ajax({
		  type : "POST",
		  url : 'relationResourceSave',
		  data:{"baseresourceId":baseresourceId , "billaccountId":billaccountId},
		  dataType : 'json',
		  cache : false,
		  success : function(data) {
			  loadRelationedResourceTableData(billaccountId , regId);
		  }
	 });
}

/**
 * 关联电表保存
 */
function relationElectricmeterSubmit(){
	var ids = $('#relationElectricmetertb') .bootstrapTable('getAllSelections') ;

	if (ids.length <= 0) {
		alert("至少选择一条操作数据!");
		return false;
	}
	
	var meterIds = [];
	for(var i=0;i<ids.length;i++){
		meterIds.push(ids[i].meterId);
	}
	
	var meterId = meterIds.join(",");
	var baseresourceId = $("#relationElectricmeterResourceId").val();
	
	$.ajax({
		  type : "POST",
		  url : 'relationElectricmeterSave',
		  data:{"baseresourceId":baseresourceId , "billaccountId":billaccountId , "meterId" : meterId},
		  dataType : 'json',
		  cache : false,
		  success : function(data) {
			  loadRelationedResourceTableData(billaccountId , regId);
		  }
	 });
}
/*function auditingsave(){
	var userId = "3";
	var state = $("#checkState").val();
	
	var relationArrays = [billaccountInfo];
	
	 $.ajax({
		  type : "POST",
		  url : 'review',
		  data:{"params":JSON.stringify(relationArrays) , "userId":userId , "state":state},
		  dataType : 'json',
		  cache : false,
		  success : function(data) {
			  window.location="auditing.html";
		  }
	 });
}*/

function changeSave(){
	if($("#nextUsers option").length > 1 && $("#checkState option:selected").val()=='0'){
		if($('#nextUsers option:selected').val() == ""){
			$("#nextUsers").next("#err").html("<img src=\"../../../image/error.png\"/>必选！");
			$("#nextUsers").next("#err").css({"color":"red","float":"left"});
			flag=true;
		}else{
			$("#nextUsers").next("#err").html("<img src=\"../../../image/right_1.png\"/>验证成功");
			$("#nextUsers").next("#err").css({"color":"green","float":"left"});
			flag=false;
		}
	}else{
		flag=false;
	}
	if($("#checkState option:selected").val()==8){
		if($("#comment").val()==""){
			$("#comment").next("#err").html("<img src=\"../../../image/error.png\"/>必填！");
			$("#comment").next("#err").css({"color":"red"});
			flag=true;
		}else{
			$("#comment").next("#err").html("<img src=\"../../../image/right_1.png\"/>验证成功");
			$("#comment").next("#err").css({"color":"green"});
			flag=false;
		}		
	}
}

function auditingsave(){
	changeSave();
	if(flag){
		return false;
	}	
	
	var state=$("#checkState option:selected").val();
	var	comment=$("#comment").val();
	var taskId=getParameter("taskId");
	var	leader=$("#nextUsers option:selected").val();	
	var billAccountIds = new Array();
	// 从选中行中挑出可以启用的item
	var obj={
			"state":state,
			"comment":comment,
			"leader":leader,
			"billaccountId":billaccountInfo.billaccountId,
			"taskId":taskId,
    };
	billAccountIds.push(obj);
	
	 $.ajax({
		  type : "POST",
		  url : 'reviewBillAccountAudit',
		  data:JSON.stringify(billAccountIds),
		  dataType : 'json',
		  contentType : 'application/json;charset=utf-8',
		  cache : false,
		  success : function(data) {
			  window.location="auditing.html";
		  }
	 });
}
function checkInfo(regId){
	var billaccountId=billaccountInfo.billaccountId;
	histoicFlowList(EleBillaccount.tableName,billaccountId);
	findUsersByRoleIds(EleBillaccount.tableName,billaccountId,regId);
}

function queryCurUser(){
	$.ajax({
		url:'queryCurUser',
		data:{
			conutn:1
		},
		type:'get',
		dataType:'json',
		success:function(back){
			if (back != null) {
				item=back.obj;
				if(back.success=="1"){
					$("#curUser").text(item[0]);
				}
			}
		},
		error:function(){
			alert("请求异常");
		}
	})
}
function getAddressUpdate(citySelect,villageSelect){
	$.ajax({
		type : "post",
		url :"../../common/region/getAddressElec",
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		success : function(value) {
			if(value != null){
				sysReguins = value.obj;
				if(sysReguins!=null){
					$('#modifyCity').empty();
					$('#modifyRegion').empty();
					var strCity = "<option value=''>--选择地市--</option>";
					$.each(sysReguins, function (i, item){
						strCity += "<option value='" +item.regId+"' "+(citySelect&&citySelect==item.regId?"selected='true'":"")+">"+item.regName+ "</option>";
					});

					$("#modifyCity").append(strCity);
					$("#modifyRegion").empty();
					var strReg = "<option value=''>--选择区县--</option>";
					if($("#modifyCity").val()!=""){
						$.each(sysReguins, function (i, item){
							if(item.regId==$("#modifyCity").val()){
								$.each(item.child, function (j, itemchild){
									strReg += "<option value='" + itemchild.regId+"' "+(villageSelect&&villageSelect==itemchild.regId?"selected='true'":"")+">"+itemchild.regName+ "</option>";
								});
							}
						});
					}
					$("#modifyRegion").append(strReg);
					
					//绑定联动事件 修改人zhujj
					$("#modifyCity").change(function(){
						$("#modifyRegion").empty();
						var strReg = "<option value=''>--选择区县--</option>";
						if($("#modifyCity").val()!=""){
							$.each(sysReguins, function (i, item){
								if(item.regId==$("#modifyCity").val()){
									$.each(item.child, function (j, itemchild){
										strReg += "<option value='" + itemchild.regId+"' "+(villageSelect&&villageSelect==itemchild.regId?"selected='true'":"")+">"+itemchild.regName+ "</option>";
									});
								}
							});
						}
						$("#modifyRegion").append(strReg);
					});
				}
			}
		},
		error : function(data) {
			alertModel('获取用户地区信息失败!');
		}
	});
}