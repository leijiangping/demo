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
	 
	 //billaccountId = "09287f0bb378429f9b7853ae4448e8f7";
	 //regId = "310105";
	 
	 
	 
	 loadRelationedContractTableData(billaccountId);
	 loadRelationedResourceTableData(billaccountId , regId);
	
	//loadContractTableData('09287f0bb378429f9b7853ae4448e8f7');
	//loadResourceTableData('09287f0bb378429f9b7853ae4448e8f7' , '310105');
	 initData();

	histoicFlowList(EleBillaccount.tableName,billaccountId);
	
});

function initModifyCityCallback(){
	initData();
}

function initData(){
	 
	 $("#billaccountId").val(billaccountInfo.billaccountId);
	 $("#billaccountCode").text(billaccountInfo.billaccountCode);
	 $("#billaccountName").text(billaccountInfo.billaccountName);
	 switch(billaccountInfo.billaccountState){
		case 0:
			billaccountState = "正常";
			break;
		case 9: 
			billaccountState = "停用";
			break;
		case 10: 
			billaccountState = "终止";
			break;
		default:
			baseresourceCategorys ="";
			break;
		}
	 switch(billaccountInfo.billaccountType){
		case 0:
			billaccountType = "租费报账点";
			break;
		case 1: 
			billaccountType = "电费报账点";
			break;
		case 2: 
			billaccountType = "二";
			break;
		default:
			billaccountType ="";
			break;
		}
		
	 $("#billaccountState").text(billaccountState);
	 
	 $("#billaccountType").text(billaccountType);
	 $("#planDate").text(billaccountInfo.planDate == null ? "" : billaccountInfo.planDate);
	 $("#calcMulti").text(billaccountInfo.calcMulti == null ? "" : billaccountInfo.calcMulti);
	 $("#billaccountNote").text(billaccountInfo.billaccountNote == null ? "" : billaccountInfo.billaccountNote);
	 $.ajax({
	        type: "GET",
	        url: "../../regions",
	        data: {"cityId": billaccountInfo.pregId},
	        dataType: "JSON",
	        success: function (data) {
	            $("#modifyCity").text(billaccountInfo.pregName);
	            $("#modifyRegion").text(billaccountInfo.regName);

	        },
	        error: function () { alert("获取区县信息异常！"); }
	    });
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
            		case -2:return '无效';
            		case -1:return '删除';
            		case 0:return '正常';
            		case 1:return '起草';
            		case 2:return '履行完毕';
            		case 3:return '审批中';
            		case 4:return '审批完毕';
            		case 8:return '合同异动';
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
		$.each(data.Obj,function(i,item){
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
			switch(item.baseresource_state){
			case 1:
				baseresource_state= '在网';
				break;
			case 2:
				baseresource_state= '工程';
				break;
			case 3:
				baseresource_state= '退网';
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
				str += '<td><a href="#" class="add">关联电表</a></td>';
			}else{
				str += '<td><a href="#" class="del">取消关联电表</a></td>';
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
			deselElectricmeterPopup(meterId , baseresourceId , baseresourceelectricmeterId);
		}).on("click",".add",function(e){
			e.preventDefault();
			var baseresourceId = $(this).closest("tr").data("node-id");		

			selElectricmeterPopup(billaccountInfo.billaccountId , billaccountInfo.regId ,  baseresourceId);
			
		});
	});
	
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
            title: '资源状态',
            formatter:function(value,row,index){
    			switch(value){
	    			case 1:return '在网';
	    			case 2:return '工程';
	        		case 3:return '退网';
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
	localStorage.removeItem("billaccountInfo");
	javascript:history.back(-1);
}


