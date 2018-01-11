$.ajaxSetup({
	 beforeSend: function () {
	    $("#loading").html("<img src=\""+projectName+"/asserts/image/loading-b.gif\"/>");
	 },
	 complete: function () {
	    $("#loading").html("");
	},
	error: function () {
	    $("#loading").html("");
	}
});
var billaccountId ;
var regId ;
var billaccountInfo;
var nwArr=[];//保存已有电表id
var souArr=[];//保存已有资源数据id
var curPageNum = 1;//当前页码：1
var ipageCount = 10;//每页条数
var contractType = '';//合同类型 0非包干 1是包干
var containElec = false;//判断有无电表
var priceType;
var isIncludeAll;//是否包干合同  
var calcMultiCount;//计量倍数

//初始化
$(function(){
	/*billaccountInfo = localStorage.getItem("billaccountInfo");
	 billaccountInfo = JSON.parse(billaccountInfo);
	 billaccountId = billaccountInfo.billaccountId;
	 regId = billaccountInfo.regId;*/
	 billaccountId = getParameter("billaccountId");
	 regId = getParameter("regId");
	 initModifyCityCallback(billaccountId,regId);
	 getcalcMulti();
	 
});

function initModifyCityCallback(billaccountId,regId){
	/* $("#billaccountId").val(billaccountInfo.billaccountId);
	 $("#billaccountCode").val(billaccountInfo.billaccountCode);
	 $("#billaccountName").val(billaccountInfo.billaccountName);
	 $("#billaccountState").val(billaccountInfo.billaccountState);
	 $("#billaccountType").val(billaccountInfo.billaccountType);
	 $("#planDate").val(billaccountInfo.planDate);*/
//	 calcMultiCount=;
//	 $("#calcMulti").val(billaccountInfo.calcMulti);
/*	 $("#billaccountNote").val(billaccountInfo.billaccountNote);

$("#modifyRegion").html("");
getAddressUpdate(billaccountInfo.pregId,billaccountInfo.regId);
	*/
	initData(billaccountId,regId);
}

function initData(billaccountId,regId){
	queryBillaccountById(billaccountId,regId);
	 loadRelationedContractTableData(billaccountId);//关联合同信息
	 loadRelationedResourceTableData(billaccountId , regId);
}

function queryBillaccountById(billaccountId,regId){
	$.ajax({
		  type : "post",
		  url : 'queryBillaccountById',
		  data:{
	          billaccountId:billaccountId,
	          regId:regId
	            },
		  dataType : 'json',
		  cache : false,
		  success : function(res) {
			  billaccountInfo = res.obj;
        if(billaccountInfo.length> 0){
        	for(var i=0;i<billaccountInfo.length;i++){
        		 $("#billaccountId").val(billaccountInfo[i].billaccountId);
            	 $("#billaccountCode").val(billaccountInfo[i].billaccountCode);
            	 $("#billaccountName").val(billaccountInfo[i].billaccountName);
            	 $("#billaccountState").val(billaccountInfo[i].billaccountState);
            	 
            	 $("#billaccountType").val(billaccountInfo[i].billaccountType);
            	 $("#planDate").val(billaccountInfo[i].planDate);
            	 $("#calcMulti").val(billaccountInfo[i].calcMulti);
            	 $("#billaccountNote").val(billaccountInfo[i].billaccountNote);
        	}
        	$("#modifyRegion").html("");
        	getAddressUpdate(billaccountInfo[0].pregId,billaccountInfo[0].regId);
			  }
		  }
	 });
}
var elebillaccountcontractId=null;
function loadRelationedContractTableData(billaccountId) {
	$.ajax({
		  type : "POST",
		  url : 'queryContractInfo',
		  async: false,
		  data:{
			  "pageNumber": 1,    
	          "pageSize": 10,
	          "billaccountId":billaccountId
	            },
		  dataType : 'json',
		  cache : false,
		  success : function(res) {
			  list = res.obj.list;
			  if(list!=null && list.length>0){
				  if(list[0].priceType){
					  priceType=list[0].priceType;
					  $("#priceType").val(list[0].priceType);
				  }
				  
	          if(list.length> 0){
	        	  $("#priceType").val(list[0].priceType);
	        	  elebillaccountcontractId=list[0].elebillaccountcontractId;
	        	  var startDate=getTime(list[0].contractStartdate);
	        	  var endDate=getTime(list[0].contractEnddate);
	        	  var state=list[0].contractState;
				  contractType = list[0].isIncludeAll;
				  $("#isIncludeAll").val(list[0].isIncludeAll);
	        	  switch (state)
	        	  {
	        	  case 0:
	        	    state="正常";
	        	    break;
	        	  case 9:
	        		  state="停用";
	        	    break;
	        	  }
	        	  $("#contracttb tbody").html("");
				    var s ='<tr style=\'text-align: center;\'><input type=\'hidden\' name=\'getRegname\' id="getRegname" value="'
				    	+ list[0].regName +'"/><input type=\'hidden\' name=\'getPregname\' id="getPregname" value="'+list[0].pregName+'"/ ><td  style=\'display: none;\'><input type=\'hidden\' name=\'checkbox\'/></td><td color="red">'
						+ list[0].contractCode + '</td><td>'
				    	+ list[0].contractName + '</td><td >'
				    	+ (list[0].pregName == null ? "-" : list[0].pregName) +'</td ><td >'
				    	+ (list[0].regName == null ? "-" : list[0].regName) +'</td ><td>'
				    	+startDate +'</td><td>'
				    	+endDate +'</td><td>'
				        +state  +'</td></tr>';
				        $('#contracttb').append(s);
				  }
			  }
		  }
	 });
	//zyj
	var contract =$("#contracttb").find('tr td').eq(1).text();
	 if(contract == ""){
 		
 		/*$("#chose_Btn").attr('disabled',true);
 		$("#cancle_Btn").attr('disabled',true);*/
		 $("#chose_Btn").removeAttr('href');
	 	 $("#chose_Btn").removeAttr('onclick');
	 	 $("#cancle_Btn").removeAttr('href');
	 	 $("#cancle_Btn").removeAttr('onclick');
 		$("#chose_Btn,#cancle_Btn ").css("background-color","#ccc");
 	}else{
 		 $("#chose_Btn").attr('href');
	 	 $("#chose_Btn").attr('onclick','selresourcePopup()');
	 	 $("#cancle_Btn").attr('href');
	 	 $("#cancle_Btn").attr('onclick','deselresourcePopup()');
	 	$("#chose_Btn,#cancle_Btn ").css("background-color","#006dcc");
 	}
}



//已关联资源列表
function loadRelationedResourceTableData(billaccountId , regId) {
	$.post("queryResourceTree" , {"billaccountId":billaccountId , "regId":regId} , function(data){
		/*var newcalcMultiCount=0;//计量倍数
		var newResourceCount=0;
		var newMeterCount=0;*/
		
		var str = '';
		$.each(data.Obj,function(i,item){
			/*if(item.parentId==null){
				newResourceCount++;
				for(var i=0;i<data.Obj.length;i++){
					if(item.nodeId==data.Obj[i].parentId){
						newMeterCount++;
						i=data.Obj.length;
					}
					
				}
			}*/
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
			case 0:
				baseresource_state= '正常';
				break;
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
			//初始化加载资源列表 返回的数据里有id这个字段
			if(!item.parentId){
				souArr.push(item.nodeId);
				str += '  data-tt-branch="true" class="init"><td><span class="folder"></span><input  type="checkbox" name="resourceCk" value="'+item.id+'"/></td>';
				str += 
					'<td>'+baseresource_type+'</td>' +
					'<td>'+item.baseresource_code+'</td>' +
					'<td>'+item.baseresource_name+'</td>' +
					'<td>'+baseresource_state+'</td>' ;
				$('#calcMulti').removeAttr('disabled');
				$('#calcbtn').css('background-color','#006dcc');
				$('#calcbtn').attr('onclick','getcalnum()');
			}else{
				str += ' data-parent-id="'+item.parentId +'" data-tt-branch="false"><td><span class="file"></span><input type="hidden" name="relId"  value="'+item.id+'"></td>';
				str += 
					'<td>'+baseresource_type+'</td>' +
					'<td>'+item.baseresource_code+'</td>' +
					'<td>电表</td>' +
					'<td>'+baseresource_state+'</td>' ;
				$('#calcMulti').removeAttr('disabled');
				$('#calcbtn').css('background-color','#006dcc');
				$('#calcbtn').attr('onclick','getcalnum()');
			}
			
			if(!item.parentId){
				str += '<td><a href="#" class="add">关联电表</a></td>';
			}else{
				
				str += '<td><a href="#" class="del" onClick="deselElectricmeterPopup(this)">取消关联电表</a></td>';
			}
			str += '</tr>';
			
		});
		/*if(newMeterCount>0){
			newcalcMultiCount=newResourceCount/newMeterCount;
			
		}
		$("#calcMulti").val(Number(newcalcMultiCount));*/
		$("#resourcetreetable tbody").html(str);
		//console.log('souArr');
		//console.log(souArr);
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
			$('#resourceId').val(baseresourceId);
			
			nwArr=[];//全局变量 存已有的子集电表id
			var overEleid=$("#resourcetreetable tr[data-parent-id="+baseresourceId+"]");
			
			if(overEleid.length>0){
				for(var i=0;i<overEleid.length;i++){
					var overlid=$(overEleid[i]).attr('data-node-id');
					nwArr.push(overlid);
				}
			}else{
				nwArr=[];
			}
			selElectricmeterPopup(billaccountInfo.billaccountId , billaccountInfo.regId ,  baseresourceId);
		});
	});
}
// 添加资源列表
var ResourceIds=null;
var sourceFlag;

var ResourceIdList = [];//su
function relationResource(billaccountId , regId) {
	
	ResourceIds = $('#relationresourcetb').bootstrapTable('getAllSelections') ;
	for(var i=0;i<ResourceIds.length;i++){
	//新加电表数据
		if(souArr.length>0){
			for(var j=0;j<souArr.length;j++){
				if(ResourceIds[i].baseresourceId){
					if(ResourceIds[i].baseresourceId==souArr[j]){
						 alertModel('该资源已被关联，请重新选择！');
						return false;
					}
					sourceFlag='ok';
				}
			}
		}else{
			ResourceIdList.push(ResourceIds[i].baseresourceId);
			souArr.push(ResourceIds[i].baseresourceId);
			
			flags='okl';
		}
		if(sourceFlag=='ok'){
			ResourceIdList.push(ResourceIds[i].baseresourceId);
			souArr.push(ResourceIds[i].baseresourceId);
		}
		
	}
if(sourceFlag=='ok' || flags=='okl'){
	$("#ResourceIds").val(ResourceIdList);
	var item=ResourceIds;
	var meterType;
	var meterState;
	for(var i=0;i<item.length;i++){
		var str = '';
		var elechild = '';
		switch(item[i].baseresourceType){
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
		switch(item[i].baseresourceState){
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
		str += '<tr data-node-id="' + item[i].baseresourceId + '"';
		if(item[i].baseresourceId){
			str += ' data-tt-branch="true" class="branch appes expanded"><td><span class="indenter" style="padding-left: 0px;"><a href="#" title="收起">&nbsp;</a></span><span class="folder"></span><input  type="checkbox" name="resourceCk" value="'+item[i].baseresourceId+'"/ ></td>';
			str += 
				'<td>'+baseresource_type+'</td>' +
				'<td>'+item[i].baseresourceCode+'</td>' +
				'<td>'+item[i].baseresourceName+'</td>' +
				'<td>'+baseresource_state+'</td>' ;
		}
		if(item[i].electricMeterVO){
			$('#calcMulti').removeAttr("disabled");
			$('#calcbtn').css('background-color','#006dcc');
			$('#calcbtn').attr('onclick','getcalnum()');
			$.each(item[i].electricMeterVO,function(index,ele){
				switch(ele.meterType){
	    		case 1:
	    			meterType= '普通表';
					break;
	    		case 2:
	    			meterType= '平峰谷表';
					break;
				}
				switch(ele.meterState){
					case 0:
						meterState="正常";
					break;
					case 9:
						meterState="停用";
					break;
				}
					
				elechild += '<tr data-node-id="' + ele.meterId + '" data-parent-id="'+item[i].baseresourceId +'" data-tt-branch="false"><td><span class="indenter" style="padding-left: 19px;"></span><span class="file"></span><input type="hidden" name="relId"  value="'+item[i].baseresourceId+'"></td>';
				elechild += 
					'<td>'+meterType+'</td>' +
					'<td>'+ele.meterCode+'</td>' +
					'<td>电表</td>' +
					'<td>'+meterState+'</td>' +
				'<td><a href="#" class="del" onclick="deselElectricmeterPopup(this)">取消关联电表</a></td>';
			});
			
			
		}
		
		if(item[i].baseresourceId){
			str += '<td><a href="#" class="add">关联电表</a></td>';
		}
		
		str += '</tr>';
		elechild +='</tr>'
	
		$("#resourcetreetable tbody").append(str);
		$("#resourcetreetable tbody").append(elechild);
	}
	
	$('#resourcetreetable tr').each(function(){
		$(this).find('.indenter').on('click',function(){
			var pid=$(this).parents('tr').attr('data-node-id');
			if($("#resourcetreetable tr[data-node-id="+pid+"]").hasClass('appes')){
				if($("#resourcetreetable tr[data-node-id="+pid+"]").hasClass('expanded')){
					$("#resourcetreetable tr[data-node-id="+pid+"]").removeClass('expanded').addClass('collapsed');
					$("#resourcetreetable tr[data-parent-id="+pid+"]").css('display','none');
				}else{
				$("#resourcetreetable tr[data-node-id="+pid+"]").addClass('expanded').removeClass('collapsed');
				$("#resourcetreetable tr[data-parent-id="+pid+"]").css('display','table-row');
				}
			}else{
				if($("#resourcetreetable tr[data-node-id="+pid+"]").hasClass('expanded')){
					$("#resourcetreetable tr[data-node-id="+pid+"]").addClass('expanded').removeClass('collapsed');
					$("#resourcetreetable tr[data-parent-id="+pid+"]").css('display','table-row');
				}else{
				$("#resourcetreetable tr[data-node-id="+pid+"]").removeClass('expanded').addClass('collapsed');
				$("#resourcetreetable tr[data-parent-id="+pid+"]").css('display','none');
				}
			}
		});
		
	});
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
//		e.preventDefault();
//		var meterId = $(this).closest("tr").data("node-id");		
//		var baseresourceId = $(this).closest("tr").data("parent-id");	
//		var baseresourceelectricmeterId = $(this).closest("tr").find("input[name='relId']").val();
//		deselElectricmeterPopup(meterId , baseresourceId , baseresourceelectricmeterId);
	}).on("click",".add",function(e){
		e.preventDefault();
		var baseresourceId = $(this).closest("tr").data("node-id");		
		$('#resourceId').val(baseresourceId);
		selElectricmeterPopup(billaccountInfo.billaccountId , billaccountInfo.regId ,  baseresourceId);
	});
	sourceFlag='no';
	}

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

	var resourcess =$("#resourcetreetable").find('tr td').eq(1).text();//判断有无资源点
	//判断有电表
	var resource =$("#resourcetreetable").find('tr');
	for(var i=0;i<resource.length;i++){
		var overlid=$(resource[i]).attr('data-parent-id');
		if (overlid != null){
			containElec = true;
		}
	}
	
	
	if(resourcess && containElec){
		isIncludeAll=$("#isIncludeAll").val();
			priceType=$("#priceType").val();
	}else if(resourcess && !containElec){
		isIncludeAll=1;
		priceType="";
	}else if(resourcess =="" || resourcess ==null || resourcess ==undefined){
		isIncludeAll="";
		priceType="";
	}
	

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
	            pregId:$("#modifyCity").val(),
	        	regId :$("#modifyRegion").val(),
                contractName : 	$('#contractNameFind').val(),
	            billaccountId :	billaccountId,
	            isIncludeAll:isIncludeAll,
	            priceType:priceType

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
            title: '合同期始',
            formatter:function(value,row,index){
            	return new Date(value).format("yyyy-MM-dd");
            }
        }, {
            field: 'contractEnddate',
            title: '合同期终',
            formatter:function(value,row,index){
            	return new Date(value).format("yyyy-MM-dd");
            }
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
			if(res != null){
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
	            baseresourceName : 	$('#baseresourceName').val(),
//	            billaccountId :	billaccountId,
	            relationState:0, //必须关联正常状态的。因为取消资源关联时只改relationState=9
	            souArr : resourceidList,
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
				//点击选择关联资源列表 无id字段
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
	if(billaccountId == ''){
		billaccountId = $('#billaccountId').val();
	}
	if(regId == ''){
		regId = $("#modifyRegion").val();
	}
	if(resourceId == ''){
		resourceId = $('#resourceId').val();
	}
	/**
	 * 新增根据合同状态判断电费
	 */ 
	 if(contracttbids!=null && contracttbids.length>0){
		 priceType=contracttbids[0].priceType;
	 }
	 priceType = $("#priceType").val();
	 var Objtr=$('#resourcetreetable tr');
	 var meterIds="";
	 for(var i=0;i<Objtr.length;i++){
		 if(typeof(Objtr.eq(i).next().attr('data-parent-id'))!=='undefined'){
				var elec_id=Objtr.eq(i).nextAll().attr('data-node-id');
				meterIds += elec_id+",";
			}
	 }
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
	            "baseresourceId":resourceId , 
	            "billaccountId":billaccountId ,
	            "meterCode":$('#relationElectricmeterCodeFind').val(),
	            "regId":regId,
	            "priceType":priceType,
                "meterIds":meterIds
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
				$("#meterIds").val(res.obj.list);
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
	localStorage.removeItem('billaccountId');
	localStorage.removeItem('billaccountInfo');
	window.location.href = 'manage.html';
}
/**
 * 选择关联合同弹出 
 */
function selcontractPopup(){
	if($("#modifyRegion").val() == ""){
		alertModel("请选择区县信息");
		return;
	}
	$("#selRelationContractDiv").modal("show");
	loadContractTableData(billaccountId);

}
/**
 * 弹出 关联资源
 */
var resourceidList = "";
function selresourcePopup(){
	$("#baseresourceName").val("");
	$("#selRelationResourceDiv").modal("show");
	var pregId = $("#modifyCity").val();
	var regId = $("#modifyRegion").val();
	for(var i = 0 ; i < souArr.length ; i++){
		resourceidList += souArr[i]+",";
	}
	loadResourceTableData(billaccountId , regId);
}

/**
 * 弹出 关联电表
 */
function selElectricmeterPopup(billaccountId , regId ,  baseresourceId){
	var pregId = $("#modifyCity").val();
	var regId = $("#modifyRegion").val();
	$("#relationElectricmeterCodeFind").val("");
	loadElectricmeterTableData(billaccountId , regId , baseresourceId);
	$("#relationElectricmeterResourceId").val(baseresourceId);
	$("#selRelationElectricmeterDiv").modal("show");
}
/**
 * 取消 关联电表
 */
function deselElectricmeter(data){
	var baseresourceId=  new Array();
	var baseresourceelectricmeterId= new Array();
	var meterId= new Array();
	for(var i=0;i<data.length;i++){
		baseresourceId.push(data[i].baseresourceId);
		meterId.push(data[i].meterId);
		baseresourceelectricmeterId.push(data[i].baseresourceelectricmeterId);
	}
	
$.post("relationElectricmeterCancel" , {"billaccountId":billaccountId , "baseresourceelectricmeterId":baseresourceelectricmeterId.join(",") , "baseresourceId":baseresourceId.join(",")  , "meterId":meterId.join(",") } , function(data){
});
}
var delData = new Array();
function deselElectricmeterPopup(obj){
	//获取当前id及父id
	curChildId=$(obj).parents("tr").attr('data-node-id');
	curParId=$(obj).parents("tr").attr('data-parent-id');
	$('#curChikdId').val(curChildId);
	
	$('#curParId').val(curParId);
	$(obj).parents("tr").remove();
	 var baseresourceelectricmeterId = $(obj).closest("tr").find("input[name='relId']").val();
	 	 
	 // 从选中行中挑出可以启用的item  kkkk
	 var objData={
			 "billaccountId":billaccountId,
			 "baseresourceelectricmeterId":baseresourceelectricmeterId,
			 "meterId":curChildId,
			 "baseresourceId":curParId
	 };
	 delData.push(objData);
	 JSON.stringify(delData);
	 var resourceMeterids='';
	 var cur_data;//当前机房
	 if(resourceMeteridList.length > 0){
		 //判断当前取消的电表数据是否在已选中的电表数据列表里
		 for(var i=0;i<resourceMeteridList.length;i++){
			 if(resourceMeteridList[i].baseresourceId==curParId){
				 resourceMeterids=resourceMeteridList[i].meterId.split(",");//当前机房的meterId
				 cur_data=resourceMeteridList[i];//当前机房的数据
			 }
			 
		 }
		 //对当前meterId进行修改
		 for (var i =0;i<resourceMeterids.length; i++) { 
				for(var j =0;j<delData.length; j++){
					 if (resourceMeterids[i] == delData[j].meterId ){
						 var speIdarr=cur_data.meterId.split(",");
						 if(speIdarr.length<=1){
							 removeWithoutCopy(resourceMeteridList,cur_data);
							 removeWithoutCopy(delData,delData[j]);
						 }else{
							 removeWithoutCopy(speIdarr,speIdarr[i]);
							 removeWithoutCopy(delData,delData[j]);
							 console.log(speIdarr)
							 var speIdarrlist="";
							if(speIdarr.length>0){
								for(var i=0;i<speIdarr.length;i++ ){
									speIdarrlist =speIdarr + ",";
								}
							}
							console.log('uuuu');
							
							cur_data.meterId=speIdarrlist;
							 console.log(resourceMeteridList);
						 }
						 j=delData.length;
					 }
				}
				}
//		for (var i =0;i<resourceMeterids.length; i++) { 
//			for(var j =0;j<delData.length; j++){
//				 if (resourceMeterids[i] == delData[j].meterId ){
//					 var speIdarr=resourceMeteridList[i].meterId.split(",");
//					 if(speIdarr.length<=1){
//						 removeWithoutCopy(resourceMeteridList,resourceMeteridList[i]);
//						 removeWithoutCopy(delData,delData[j]);
////						 delData=[];
//						 console.log('000');
//						 console.log(removeWithoutCopy(resourceMeteridList,resourceMeteridList[i]));
//					 }else{
//						 removeWithoutCopy(speIdarr,speIdarr[i]);
//						 removeWithoutCopy(delData,delData[j]);
//						 console.log(speIdarr)
//						 var speIdarrlist="";
//						if(speIdarr.length>0){
//							for(var i=0;i<speIdarr.length;i++ ){
//								speIdarrlist =speIdarr + ",";
//							}
//						}
//						console.log('uuuu');
//						
//						 resourceMeteridList[i].meterId=speIdarrlist;
//						 resourceMeterids
//						 console.log(resourceMeteridList);
//					 }
//					 j=delData.length;
//				 }
//			}
//		}
	 }
}

/**
 * 取消 关联资源
 */
var deselresource_ids = [];
function deselresource(){
    var billaccountbaseresource_ids =  deselresource_ids.join(",");
    $.post("relationResourceCancel" , {"billaccountbaseresourceId":billaccountbaseresource_ids , "billaccountId":billaccountId} , function(data){
    });
}
/**
 * 页面假删除取消 关联资源
 */
function deselresourcePopup(){
	$("input[type=checkbox][name='resourceCk']:checked").each(function(){ //由于复选框一般选中的是多个,所以可以循环输出选中的值  
    	$(this).parents("tr").remove(); 
    	var curNodeid=$(this).parents("tr").attr('data-node-id');
    	$("#resourcetreetable tr[data-parent-id="+curNodeid+"]").remove();
    	deselresource_ids.push($(this).val()); 
    	
    	for(var i=0;i<=ResourceIdList.length;i++){
    		if($(this).val()==ResourceIdList[i]){
    			removeWithoutCopy(ResourceIdList,ResourceIdList[i]);
    		}
    		
    	}
    	$("#ResourceIds").val(ResourceIdList);
    });
	var delNode=$("#resourcetreetable").find('tr td').eq(1).text();
	if(delNode==""){
		getcalcMulti();
		$("#calcMulti").val('0');
	}
}
function removeWithoutCopy(arr, item) {
    for(var i = 0; i < arr.length; i++){
        if(arr[i] == item){
            //splice方法会改变数组长度，当减掉一个元素后，后面的元素都会前移，因此需要相应减少i的值
            arr.splice(i,1);
            i--;
        }
    }
    return arr;
}
//关联合同信息-选择关联合同
var contracttbids =null;
function relationContractSubmitnew(){
	 contracttbids = $('#relationcontracttb') .bootstrapTable('getAllSelections') ;
	 $("#elecontractId").val(contracttbids[0].elecontractId);
    var contractState=contracttbids[0].contractState;
	  switch (contractState)
	  {
	  case 0:
		  contractState="正常";
	    break;
	  case 9:
		  contractState="停用";
	    break;
	  }
    $("#contracttb tbody").html("");
    contractType = contracttbids[0].isIncludeAll;
	$("#priceType").val(contracttbids[0].priceType);

    var s ='<tr style=\'text-align: center;\'><input type=\'hidden\' name=\'checkbox\'/ ><input type=\'hidden\' name=\'getRegname\' id="getRegname" value="'
    	+ contracttbids[0].regName +'"/><input type=\'hidden\' name=\'getPregname\' id="getPregname" value="'+contracttbids[0].pregName+'"/><td style=\'display: none;\'></td><td>'
		+ contracttbids[0].contractCode + '</td><td>'
    	+ contracttbids[0].contractName + '</td><td >'
    	+ (contracttbids[0].pregName == null ? "-":contracttbids[0].pregName) +'</td><td >'
    	+ (contracttbids[0].regName == null ? "-" : contracttbids[0].regName) +'</td><td>'
    	+ getTime(contracttbids[0].contractStartdate) +'</td><td>'
    	+ getTime(contracttbids[0].contractEnddate) +'</td><td>'
        + contractState  +'</td></tr>';
        $('#contracttb').append(s);
        
        //zyj
        var contract =$("#contracttb").find('tr td').eq(1).text();
        if(contract == ""){
     		
     		/*$("#chose_Btn").attr('disabled',true);
     		$("#cancle_Btn").attr('disabled',true);*/
    		 $("#chose_Btn").removeAttr('href');
    	 	 $("#chose_Btn").removeAttr('onclick');
    	 	 $("#cancle_Btn").removeAttr('href');
    	 	 $("#cancle_Btn").removeAttr('onclick');
     		$("#chose_Btn,#cancle_Btn ").css("background-color","#ccc");
     	}else{
     		 $("#chose_Btn").attr('href');
    	 	 $("#chose_Btn").attr('onclick','selresourcePopup()');
    	 	 $("#cancle_Btn").attr('href');
    	 	 $("#cancle_Btn").attr('onclick','deselresourcePopup()');
    	 	$("#chose_Btn,#cancle_Btn ").css("background-color","#006dcc");
     	}
       
}
/**
 * 关联合同 保存
 */
function relationContractSubmit(){
	var contractId =  $("#elecontractId").val();
	if(contractId!=null && contractId!="" && contractId!=undefined ){
		$.ajax({
			type : "POST",
			url : 'relationContractSave',
			data:{"elecontractId":contractId ,"elebillaccountcontractId":elebillaccountcontractId, "billaccountId":billaccountId},
			dataType : 'json',
			cache : false,
			success : function(data) {
			}
		});
	}
}
/**
 * 关联资源保存
 */
function relationResourceSave(){
	var ids =$("#ResourceIds").val();
	if (deselresource_ids!=null) {
		deselresource();
	}
	$.ajax({
		  type : "POST",
		  url : 'relationResourceSave',
		  data:{"baseresourceId":ids , "billaccountId":billaccountId},
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
    
   if (delData!=null && delData.length>0) {
        deselElectricmeter(delData);
  }
   console.log(resourceMeteridList);
    $.ajax({
          type : "POST",
          url : 'relationElectricmeterSave',
          data:JSON.stringify(resourceMeteridList),
          dataType : 'json',
          contentType : 'application/json;charset=utf-8',
          cache : false,
          success : function(data) {
              loadRelationedResourceTableData(billaccountId , regId);
          }
     });
}
//保存资源列表--关联电表选中的结果

var Electricmeterids =null;
var resourceMeteridList = new Array();
var flags;//判断已选点表与所选电表比较的状态

function relationElectricmeter(){
	var meteridList=[];//所选电表id集合   需置空
	
	 Electricmeterids = $('#relationElectricmetertb').bootstrapTable('getAllSelections') ;
	 console.log('Electricmeterids');
	 console.log(Electricmeterids);
	for(var i=0;i<Electricmeterids.length;i++){
//		nwArr全局变量 点击关联电表时 会将之前已有的电表值赋给nwArr
//		nwArr  我所选元素的集合  将新选择的和 之前选择的进行比较
		
		if(nwArr.length>0){
			for(var j=0;j<nwArr.length;j++){
				if(Electricmeterids[i].meterId==nwArr[j]){
					alertModel('该电表已被关联');
					return false;
				}
			}
			flags='ok';
		}else{
			meteridList.push(Electricmeterids[i].meterId);
			nwArr.push(Electricmeterids[i].meterId);
			flags='okl';
			
		}
		if(flags=='ok'){
			meteridList.push(Electricmeterids[i].meterId);
			nwArr.push(Electricmeterids[i].meterId);
		}
	}
	

if(flags=='ok'||flags=='okl'){
	 $("#meterIds").text(meteridList.join());
		var specId=$('#resourceId').val();
		var billaccountId=$("#billaccountId").val();
	    
	    // 从选中行中挑出可以启用的item
	    var obj={
	            "baseresourceId":specId,
	            "billaccountId":billaccountId,
	            "meterId":meteridList.join(","),
	    };
	    //判断resourceMeteridList再次关联电表是否有值。kkkk
	  /*if(resourceMeteridList.length>0){
		   	var useMeterId="";
			   var curMertId=resourceMeteridList[0].meterId.split(',');
			   for(var i=0;i<curMertId.length;i++){
				   if(curMertId[i]==''){
					   
				   }else{
					   useMeterId=curMertId[i]+",";
				   }
			   }
		   resourceMeteridList[0].meterId=meteridList.join(",")+useMeterId;
	   }else{
	    resourceMeteridList.push(obj);
	   }*/
	    if(resourceMeteridList!=null && resourceMeteridList.length>0){
	    	 meterFlag=true;
	    	for(var i=0;i<resourceMeteridList.length;i++){
	    		if(resourceMeteridList[i].baseresourceId==specId){
	    			resourceMeteridList[i].meterId+=meteridList.join(",");
	    			meterFlag=false;
	    		}
	    	}
	    	if(meterFlag==true){
	    		resourceMeteridList.push(obj);
	    	}
	    }else{
	    	resourceMeteridList.push(obj);
	    }
	if (Electricmeterids.length <= 0) {
		alert("至少选择一条操作数据!");
		return false;
	}else{
		var billaccountId=$("#billaccountId").val();
		var html='';
			$.each(Electricmeterids,function(index,ele){
				
				switch(ele.meterType){
					case 1:
					meterType="普通表";
					break;
					case 2:
					meterType="平峰谷表";
					break;
				}
				switch(ele.meterState){
					case 0:
						meterState="正常";
					break;
					case 9:
						meterState="停用";
					break;
				}
				html+='<tr data-node-id="'+ele.meterId+'" data-parent-id='+specId+' data-tt-branch="false" class="leaf collapsed" style="display: table-row;"><td><span class="indenter" style="padding-left: 19px;"></span><span class="file"></span><input type="hidden" name="relId" value='+billaccountId+'></td><td>'+meterType+'</td><td>'+ele.meterCode+'</td><td>电表</td><td>'+meterState+'</td><td><a href="#" class="" onclick="deselElectricmeterPopup(this)">取消关联电表</a></td></tr>';
			});
			
			
		var pid=specId;
		$('#resourcetreetable tr').each(function(){
			if($(this).attr('data-node-id')==pid){
//				放在对应的父级tr之后
				$(this).after(html);
				$("#resourcetreetable tr[data-parent-id="+pid+"]").addClass('collapsed').css('display','table-row');
				$("#resourcetreetable tr[data-node-id="+pid+"]").addClass('expanded').removeClass('collapsed');
				$(this).find('.indenter').on('click',function(){
					if($("#resourcetreetable tr[data-node-id="+pid+"]").hasClass('init')==true){
						if($("#resourcetreetable tr[data-node-id="+pid+"]").hasClass('expanded')){
							$("#resourcetreetable tr[data-node-id="+pid+"]").removeClass('expanded').addClass('collapsed');
							$("#resourcetreetable tr[data-parent-id="+pid+"]").css('display','none');
						}else{
						$("#resourcetreetable tr[data-node-id="+pid+"]").addClass('expanded').removeClass('collapsed');
						$("#resourcetreetable tr[data-parent-id="+pid+"]").css('display','table-row');
						}
					}else{
						if($("#resourcetreetable tr[data-node-id="+pid+"]").hasClass('expanded')){
							$("#resourcetreetable tr[data-node-id="+pid+"]").addClass('expanded').removeClass('collapsed');
							$("#resourcetreetable tr[data-parent-id="+pid+"]").css('display','table-row');
						}else{
						$("#resourcetreetable tr[data-node-id="+pid+"]").removeClass('expanded').addClass('collapsed');
						$("#resourcetreetable tr[data-parent-id="+pid+"]").css('display','none');
						}
					}
				})
			}
		});
	}
	flags='no';
}
}
function save(){
	
	
	
	var resourcess =$("#resourcetreetable").find('tr td').eq(1).text();
	//若关联的合同为非包干合同，则需要判断关联的资源下面必须有关联的电表，但不必所有的资源都关联电表
	//1.判断是否非包干
	if(resourcess!=null && resourcess!=""){//判断是否有资源
		var contract =$("#contracttb").find('tr td').eq(1).text();
		//判断是否关联合同
		if(contract != ""){
			var bzdPreg=$('#modifyCity option:selected').html();//报账点地市
			var bzdReg=$('#modifyRegion option:selected').html();
			var htPreg=$('#getPregname').val();
			var htReg=$('#getRegname').val();//合同区县
			if(htReg!=null && htReg!="null" && htReg!=" " && htReg!=undefined){
				if (bzdPreg !=htPreg || bzdReg !=htReg) {
					alertModel("报账点与关联合同地市不匹配，请重新选择！");
					return false;
				}
			}
			if (contractType == '0'){//非包干 挂电表
				//2.判断资源下边是否关联电表
				var resource =$("#resourcetreetable").find('tr');
				for(var i=0;i<resource.length;i++){
					var overlid=$(resource[i]).attr('data-parent-id');
					if (overlid != null){
						containElec = true;
					}
				}
				//如果不存在data-parent-id说明非包干没有关联电表
				if (!containElec){
					alertModel("至少关联一个电表!");
					return false;
				}
				if($("#calcMulti").val()<1){
					 alertModel("计量倍数不能小于1!");
						return false;
				}
			}/*else{//包干 不挂电表
				var resource =$("#resourcetreetable").find('tr');
				for(var i=0;i<resource.length;i++){
					var overlid=$(resource[i]).attr('data-parent-id');
					if (overlid != null){
						alertModel("包干合同不能关联电表!");
						return false;
					}
				}
			}*/

	   }
}
//


	var billaccountId = $("#billaccountId").val();
	var billaccountCode = $("#billaccountCode").val();
	var billaccountName = $("#billaccountName").val();
	var billaccountState = $("#billaccountState").val();
	var pregId = $("#modifyCity").val();
	var regId = $("#modifyRegion").val();
	var billaccountType = $("#billaccountType").val();
	var planDate = $("#planDate").val();
	var calcMulti = $("#calcMulti").val();
//	var calcMulti=newcalcMultiCount;
//	console.log(calcMulti);
//	return false;
	var billaccountNote = $("#billaccountNote").val();

	relationContractSubmit();
	relationResourceSave();
	relationElectricmeterSubmit();
	$.post("saveOrUpdate" , {"billaccountId":billaccountId,"billaccountCode":billaccountCode,
		"billaccountName":billaccountName,"pregId":pregId,"regId":regId,
		"billaccountType":billaccountType,"planDate":planDate,"calcMulti":calcMulti,
		"billaccountNote":billaccountNote,"billaccountState":billaccountState, "auditingState":-1
	},function(data){
		localStorage.removeItem('billaccountId');
		localStorage.removeItem('billaccountInfo');
		window.location.href="manage.html";
	});
}
function saveAndReview(){
	var billaccountId = $("#billaccountId").val();
	var billaccountCode = $("#billaccountCode").val();
	var billaccountName = $("#billaccountName").val();
	var billaccountState = $("#billaccountState").val();
	var pregId = $("#modifyCity").val();
	var regId = $("#modifyRegion").val();
	var billaccountType = $("#billaccountType").val();
	var planDate = $("#planDate").val();
	var calcMulti = $("#calcMulti").val();
	var billaccountNote = $("#billaccountNote").val();
	$.post("saveAndReview" , {"billaccountId":billaccountId,"billaccountCode":billaccountCode,
		"billaccountName":billaccountName,"pregId":pregId,"regId":regId,
		"billaccountType":billaccountType,"planDate":planDate,"calcMulti":calcMulti,
		"billaccountNote":billaccountNote,"billaccountState":billaccountState, "auditingState":9
	},function(data){
		localStorage.removeItem('billaccountId');
		localStorage.removeItem('billaccountInfo');
		window.location.href="manage.html";
	});
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
//转化时间戳
function getTime(ns){
	var date = new Date(ns);
	var datTime=date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
	return datTime;
}
//判断报账点计量倍数样式
function getcalcMulti(){
	var calc_Multi=$("#calcMulti");
	var calcbtn=$("#calcbtn");
		calc_Multi.attr("disabled","disabled");
		calcbtn.removeAttr('onclick');
		calcbtn.css('background-color','#ccc');
	
}
//判断报账点计量倍数计算
function getcalnum(){
	//所有资源点的条数
	var res_obj=$('#resourcetreetable .branch');
	var res_l=$('#resourcetreetable .branch').length;
	//资源点下面含电表的条数
	var ele_l=$('#resourcetreetable tbody tr').length-res_l;
	var j=0;
	for(var i=0;i<=res_l;i++){
		if(typeof(res_obj.eq(i).next().attr('data-parent-id'))!=='undefined'){
			j=j+1;
		}
	}
	if(j>0){
		var result_cal=Number(res_l/j);
		$("#calcMulti").val(result_cal);
	}else{
		alertModel("请至少关联一个电表！！！");
	}
	
}