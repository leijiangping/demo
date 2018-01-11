//已显示表格list
var showTableList = null;
var ipageCounts=10;
var operate_type = 1;// 1 新增，2 修改
var allCalObj;
var flag=true;
$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	ipageCount=10;
	curPageNum = 1;
	getAddress(title="运营商");
	// 查询默认条件表单数据
	loadTableData();
}
function loadTableData(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryAllCalParam", // 获取数据的地址
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
		queryParams : function queryParams(params) {
			var param = {
					pageSize:params.pageSize,
					pageNumber:params.pageNumber,
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
            field: 'calcparameterName',
            title: '名称'
        }, {
            field: 'regName',
            title: '地市'
        },{
            field: 'calcparameterCode',
            title: '关键字'
        },{
            field: 'calcparameterValue',
            title: '参数值'
        },{
            field: 'calcparameterState',
            title: '是否启用',
            formatter:function(value,row,index){
            	switch(value){
            		case 0:return '启用';
            		case 9:return '停用';
            	}
            	return value;
            }
        },{
            field: 'calcparameterNote',
            title: '备注'
        },],
		/*onLoadSuccess : function() { // 加载成功时执行
			console.log("加载成功");
		},*/
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success != "1"){
		            alertModel(res.msg);
				}
				showTableList = res.Obj.result;
			}
	        return {
	            "total": res.Obj.total,//总页数
	            "rows": res.Obj.result //数据
	         };
		}
	});
	
}

function getAddress1(title){
	$.ajax({
		type : "post",
		url : projectName+"/rent/common/getAddressRent",
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		success : function(value) {
			if(value != null){
				sysReguins = value.obj;
				if(sysReguins!=null){
					$('#City1').empty();
					$('#Village1').empty();
					var strCity = "<option value=''>-选择"+(title?title:"")+"地市-</option>";
					var strReg = "<option value=''>-选择"+(title?title:"")+"区县-</option>";
					$.each(sysReguins, function (i, item){
						strCity += "<option value='" +item.regId+"'>"+item.regName+ "</option>";
					});

					$("#City1").append(strCity);
					$("#City1 option:nth-child(2)").attr('selected',true);
					$("#Village1").append(strReg);
					$("#City1").change(function(){
						$("#Village1").empty();
						$.each(sysReguins, function (i, item){
							if(item.regId==$("#City1").val()){
								$.each(item.child, function (j, itemchild){
									strReg += "<option value='" + itemchild.regId+"'>"+itemchild.regName+ "</option>";
								});
							}
						});
						$("#Village1").append(strReg);
					});
					
				}
			}
			changeC();
		},
		error : function(data) {
			alertModel('获取用户地区信息失败!');
		}
	});

}
//设定
function dispatch(){
	getAddress1("运营商");
	var checkName=[];
	if(showTableList!=null && showTableList.length>0){
		for(var i=0;i<showTableList.length;i++){
			checkName.push(showTableList[i].calcparameterName);
		}
		
	}
	$.ajax({
		data : {
			calcparameterName:checkName.join(",")
		},
		type : "post",
		url : "queryParamObj",
		dataType : 'json',
		success : function(res) {
			var list = res.Obj;
			var opts = "";  
			for(var i = 0; i < list.length; i++){
				var data = list[i];
				opts += "<option value='"+data.calcparameterId+"'>"+data.calcparameterName+"</option>";  
			}
			$("#calcparameterName").empty();
			$("#calcparameterName").append(opts);
//			$("#selcalcparameterName").empty();
//			$("#selcalcparameterName").append(opts);
			var data=res.Obj;
			$("#tb1 tbody").empty();
			allCalObj = data;
			for(var i=0;i<data.length;i++){
				var s=
				'<tr>'+
				'<td style="display:none;"><input type="text" name="calcparameterId" value='+ data[i].calcparameterId +'> </td>'+
                '<td><input type="text" readonly  style="text-align:center;" name="calcparameterName" value='+ data[i].calcparameterName +'> </td>'+
                '<td><input type="text" readonly  style="text-align:center;" name="calcparameterCode" value='+ data[i].calcparameterCode +'> </td>'+
                '<td><input type="text" readonly  style="text-align:center;" name="regId" value=""> </td>'+
                '<td><input type="text" name="calcparameterValue" value="" > </td>'+
                '</tr>';
				$("#tb1 tbody").append(s);
			}
			changeC();
		}
		});
	$('#EditPanel1').modal();
	
}

function calOnChange(){
	
	var name=$("#calcparameterName option:selected").val();
	$("#tb1 tbody").empty();
	for(var i=0;i<allCalObj.length;i++){
		if(name == allCalObj[i].calcparameterId){
			var s=
				'<tr>'+
				'<td style="display:none;"><input type="text" name="calcparameterId" value='+ allCalObj[i].calcparameterId +'> </td>'+
                '<td><input type="text" readonly  style="text-align:center;" name="calcparameterName" value='+allCalObj[i].calcparameterName +'> </td>'+
                '<td><input type="text" readonly  style="text-align:center;" name="calcparameterCode" value='+ allCalObj[i].calcparameterCode +'> </td>'+
                '<td><input type="text" readonly  style="text-align:center;" name="regId"  value=""> </td>'+
                '<td><input type="text" name="calcparameterValue" value="" > </td>'+
                '</tr>';
				$("#tb1 tbody").append(s);
		}
	}
	changeC();
}

function hadCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}
//加载分压类型
function loadwindList(){
	var sendName=[];
	var tbNum=$("#tb").find('tr');
	for(var i=1;i<tbNum.length;i++){
		var oversel=$(tbNum[i]).hasClass('selected');
		if(oversel==false){
			var overName=$(tbNum[i]).children().eq(1).text();
			sendName.push(overName)
		}
	}
	if(flag){
	 $.ajax({
			url : 'queryParamObj',
//			data : {
//				calcparameterName:sendName.join(",")
//			},
			type : 'post',
			dataType : 'json',
			async:false, 
			success : function(back) {
				console.log(back);
				if (back != null) {
					if(back.success=="1"){
						var list = back.Obj;
						var opts = "";  
						for(var i = 0; i < list.length; i++){
							var data = list[i];
							opts += "<option value='"+data.calcparameterId+"'>"+data.calcparameterName+"</option>";  
						}
						/*$("#calcparameterName").empty();
						$("#calcparameterName").append(opts);*/
						$("#selcalcparameterName").empty();
						$("#selcalcparameterName").append(opts);
						
					}
					else{
						alertModel(back.msg);
					}
				}
			},
			error : function() {
				alertModel("请求失败！");
			}
		 });
	}
	flag=false;
}

function formSubmit1(){	
    var data=$('#tableForm').serializeArray();
    var result = [];
    hasContent = false;
    for(var i = 0;i<data.length;i+=5){
    	if(data[i+4].value != null && data[i+4].value != ''){
    		hasContent = true;
    		var obj = {};
    		obj.calcparameterName = data[i+1].value;
    		obj.calcparameterCode = data[i+2].value;
    		obj.regId = $('#pregid').val();
    		obj.calcparameterValue = data[i+4].value;
    		obj.calcparameterId=data[i].value;
    		result.push(obj);
    	}
    }
	var datas=result;
	$("#saveSet").attr("disabled",true);	
$.ajax({
		    url:'insertCalParam',
		    data:JSON.stringify(datas),
	 		type : 'post',
		    cache:false,
		    dataType : 'json',
			contentType : "application/json;charset=UTF-8",
		    async:true,
		    success:function(result){
		        //请求成功时
		    	if(result!=null){
	    			alertModel(result.msg);
	    			loadTableData();
		    	}
    			$('#EditCost').modal('hide');
    			$("#saveSet").attr("disabled",false);
		    },
		    error:function(){
		    	alertModel("请求失败！");
		    	$("#saveSet").attr("disabled",false);
		    }
		});
}


//修改 
function updateCal() {
	if (!hadCheckedRowData()) {
		return;
	}
	$("#EditCost form")[0].reset();
	debugger;
	loadwindList();
	
	
	$("#selcalcparameterName option").removeAttr("selected");
	$("#selcalcparameterName option[value='"+rowschecked[0].calcparameterId+"']").attr("selected",true);//根据值让option选中
	$("#calcparameterValue").val(rowschecked[0].calcparameterValue);
//	console.log(rowschecked[0]);
//	$("#selcalcparameterName option").val(rowschecked[0].calcparameterName);
	$("#calcparameterNote").val(rowschecked[0].calcparameterNote);
	$("#calcparameterregionId").val(rowschecked[0].calcparameterregionId);
	$("#selcalcparameterId").val(rowschecked[0].calcparameterCode);
	$("#selcalcparameterName").attr("disabled","true")
	$('#EditCost').modal();
}


function formSubmit() {
	$("#saveSet1").attr("disabled",true);
	$.ajax({
		url : 'updateCalParam',
		data : {
			calcparameterName : $('#selcalcparameterName option:selected').val(),
			calcparameterId : $('#selcalcparameterName option:selected').attr("value"),
			calcparameterValue : $('#calcparameterValue').val(),
			calcparameterNote : $('#calcparameterNote').val(),
			calcparameterregionId :$('#calcparameterregionId').val()
		},
		type : 'post',
		cache : false,
		async : true,
		success : function(result) {
			// 请求成功时
			if (result != null) {
				// alertModel(result.msg);
				loadTableData();
			}
			$('#EditCost').modal('hide');
			$("#saveSet1").attr("disabled",false);
		},
		error : function() {
			alertModel("请求失败！");
			$("#saveSet1").attr("disabled",false);
		}
	});
}
//查询
function searchFuncMenus(){
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryCalParamByCondition", // 获取数据的地址
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
					calcparameterState : $("#calpter_state option:selected").val(),
					pregId : $("#City option:selected").val(),
					calcparameterName : $("#calpter_name").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        }, {
            field: 'calcparameterName',
            title: '名称'
        }, {
            field: 'regName',
            title: '地市'
        },{
            field: 'calcparameterCode',
            title: '关键字'
        },{
            field: 'calcparameterValue',
            title: '参数值'
        },{
            field: 'calcparameterState',
            title: '是否启用',
            formatter:function(value,row,index){
            	switch(value){
            		case 0:return '启用';
            		case 9:return '停用';
            	}
            	return value;
            }
        },{
            field: 'calcparameterNote',
            title: '备注'
        },],
		/*onLoadSuccess : function() { // 加载成功时执行
			console.log("加载成功");
		},*/
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success != "1"){
		            alertModel(res.msg.result);
				}
				loadTableData = res.Obj.result;
			}
	        return {
	            "total": res.Obj.total,//总页数
	            "rows": res.Obj.result, //数据
	         };
		}
	});
}

/*
 * 删除数据
 * 
 */

function deleteCal() {
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var deleteuseObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		deleteuseObjs.push(row.calcparameterregionId);
	}
	if (confirm("确定删除所选项目?")) {
		$.ajax({
		
			type : "post",
			url : "deleteCalParam",
			data : JSON.stringify(deleteuseObjs),
			dataType : 'json',
			contentType : "application/json;charset=UTF-8",
			success : function(data) {
				alertModel('删除成功!');
				loadTableData();
			},
			error : function(data) {
				alertModel('删除失败!');
			}
		});
	}
};

//启停用
function openOrstart(type) {
	if(!isChecked()){
		alertModel("请先选择一条数据再操作");
		return;
	}
	var openOrstartObjs = new Array();
	// 从选中行中挑出可以启用的item
	for (var s = 0; s < rowschecked.length; s++) {
		var row = rowschecked[s];
		openOrstartObjs.push(row.calcparameterregionId);
	}
	$.ajax({
		url : 'startOrStopCalParam?calcparameterState='+type,
		data : JSON.stringify(openOrstartObjs),
		type : 'post',
		cache : false,
		dataType : 'json',
		contentType : "application/json;charset=utf-8",
		success : function(feedback) {
			alert(feedback.msg);
			loadTableData();
			
		},
	error : function() {
		alert("请求异常ss");
	}
});		
}
 //筛选
function changeC(){
	var seleval=$('#City1 option:selected').val();
	$('#pregid').val(seleval);
	var seleC=$('#City1 option:selected').text();
	$('#tableForm table input[name="regId"]').val(seleC);
}
