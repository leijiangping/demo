
var operate_type = 1;// 1 新增，2 修改
var curPageNum = 1;
var taskId = null; 
var listData = null;
var table=null;
var edit_validate;

$(document).ready(function() {
initialize();

edit_validate= bindformvalidate("#taskConfig", {
	ftpUrl : {
		required : true,
		maxlength : 100,
	},
	ftpPort : {
		required : true,
	},
	ftpFilepath : {
		required : true,
		maxlength : 200,
	},
	collectionUrl : {
		required : true,
		maxlength : 200,
	},
	ftpUser : {
		required : true,
		maxlength : 100,
	},
	ftpPassword : {
		required : true,
		maxlength : 100,
	}
}, {
	ftpUrl : {
		required : "FTP地址不能为空",
		maxlength: "FTP地址最大长度100"
	},
	ftpPort : {
		required : "FTP端口不能为空"
	},
	ftpFilepath : {
		required : "FTP文件路径不能为空",
		maxlength: "FTP文件路径最大长度200"
	},
	collectionUrl : {
		required : "任务路径不能为空",
		maxlength: "任务路径最大长度200"
	},
	ftpUser : {
		required : "FTP账号不能为空",
		maxlength: "FTP账号最大长度100"
	},
	ftpPassword : {
		required : "FTP密码不能为空",
		maxlength: "FTP密码最大长度100"
	}
});

});

/**
 * 初始化加页面
 */
function initialize() {
	findTask();
	findFtpConfig();
	 $('#tb').bootstrapTable('hideColumn', 'fileId');
}

function findTask() {  
    $.ajax({  
        url: 'task/collectiontype/1',  
        type: 'GET',   
        timeout: 1000,  
        cache: false, 
        async:false,
        beforeSend: LoadFunction, //加载执行方法    
        error: erryFunction,  //错误执行方法    
        success: succFunction //成功执行方法    
    })  
    function LoadFunction() {  
        $("#list").html('加载中...');  
    }  
    function erryFunction() {  
        alert("error");  
    }  
    function succFunction(ret) {  

		var json = eval(ret); // 数组
		if (json.status == 0) {
			var data = eval(json.data);
			taskId = data.taskId;
			initTaskProperty(data);
		}
    }  
}


function initTaskProperty(data){
	
	 $("#ftpUrl").val(data.ftpUrl);
	 $("#ftpPort").val(data.ftpPort);
	 $("#ftpFilepath").val(data.ftpFilepath);
	 $("#collectionUrl").val(data.collectionUrl);
	 $("#ftpUser").val(data.ftpUser);
	 $("#ftpPassword").val(data.ftpPassword);
	 $("#taskTime").val(data.taskTime);
	 $("#taskId").val(data.taskId);
	 $("#taskPeriod").val(data.taskPeriod);
	 $("#taskPeriod1").val(data.taskPeriod);
	 
	 if(data.status==0){
		 $("#saveButton").attr("disabled",false); 
		 $("#startButton").show();
		 $("#stopButton").hide();
		 $("#runButton").show();
		 $("#ftpUrl").attr("disabled", false);
		$("#ftpPort").attr("disabled", false);
		$("#ftpFilepath").attr("disabled", false);
		$("#collectionUrl").attr("disabled", false);
		$("#ftpUser").attr("disabled", false);
		$("#ftpPassword").attr("disabled", false);
		$("#taskTime").attr("disabled", false); 
		 
	 }
	 else if(data.status==1){
		 $("#saveButton").attr("disabled",true); 
		 $("#startButton").hide();
		 $("#stopButton").show();
		 $("#runButton").hide();
		 $("#ftpUrl").attr("disabled", true);
			$("#ftpPort").attr("disabled", true);
			$("#ftpFilepath").attr("disabled", true);
			$("#collectionUrl").attr("disabled", true);
			$("#ftpUser").attr("disabled", true);
			$("#ftpPassword").attr("disabled", true);
			$("#taskTime").attr("disabled", true); 
	 }
}

function save(){  
	
	if (edit_validate.form()) {
		$.ajax({  
	        url: 'task/edittask',  
	        type: 'post', 
	        async:false,
	        cache: false,
	        data : $("#taskConfig").serialize(),
	        beforeSend: LoadFunction, //加载执行方法    
	        error: erryFunction,  //错误执行方法    
	        success: succFunction //成功执行方法        
	    });
	}
	
    function LoadFunction() {  
        $("#list").html('加载中...');  
    }  
    function erryFunction() {  
        alert("操作失败!");  
    }  
    function succFunction(ret) {  

		var json = eval(ret); // 数组
		if (json.status == 0) {
			var json = eval(ret); 
   		    alert(json.message);
   		    findTask();
		}
    } 
}

function start(){  
    $.ajax({  
        url: 'task/start/'+taskId,  
        type: 'post', 
        async:false,
        cache: false,
        beforeSend: LoadFunction, //加载执行方法    
        error: erryFunction,  //错误执行方法    
        success: succFunction //成功执行方法        
    });
    function LoadFunction() {  
        $("#list").html('加载中...');  
    }  
    function erryFunction() {  
        alert("操作失败!");  
    }  
    function succFunction(ret) {  

		var json = eval(ret); // 数组
		if (json.status == 0) {
			var json = eval(ret); 
   		    alert(json.message);
   		    findTask();
		}
    } 
}

function stop(){  
    $.ajax({  
        url: 'task/stop/'+taskId,  
        type: 'post',  
        async:false,
        cache: false,
        beforeSend: LoadFunction, //加载执行方法    
        error: erryFunction,  //错误执行方法    
        success: succFunction //成功执行方法      
    });  
    function LoadFunction() {  
        $("#list").html('加载中...');  
    }  
    function erryFunction() {  
        alert("操作失败!");  
    }  
    function succFunction(ret) {  

		var json = eval(ret); // 数组
		if (json.status == 0) {
			var json = eval(ret); 
   		    alert(json.message); 
   		    findTask();
		}
    } 
}

function runnow(){  
    $.ajax({  
        url: 'task/runnow/'+taskId,  
        type: 'post',  
        async:false,
        cache: false,
        beforeSend: LoadFunction, //加载执行方法    
        error: erryFunction,  //错误执行方法    
        success: succFunction //成功执行方法      
    });  
    function LoadFunction() {  
        $("#list").html('加载中...');  
    }  
    function erryFunction() {  
        alert("操作失败!");  
    }  
    function succFunction(ret) {  

		var json = eval(ret); // 数组
		if (json.status == 0) {
			var json = eval(ret); 
   		    alert(json.message); 
		}
    } 
}


/**
 * 列表查询
 */
function findFtpConfig() {
	
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "task/ftpfileconfig", // 获取数据的地址
		striped : true, // 表格显示条纹
		  async:false,
		pagination : false, // 启动分页
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
					taskid : taskId
			};
			return param;
		},
		columns: [{
            field: 'Number',
            title: '序号',
            formatter: function (value, row, index) {
                   return index+1;
            }
        }, {
            field: 'fileId',
            title: 'fileId'
        }, {
            field: 'fileDataType',
            title: '数据类型'
        }, {
            field: 'filePerfix',
            title: '文件前缀'
        }, {
            field: 'fieldConfig',
            title: '字段配置'
        },{
            field: 'operater',
            title: '操作',
            align: 'center',
            valign:'middle',
            width: 150,
            formatter: operateFormatter,
        }],
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.status != "0"){
					listData = res.data;
		            alertModel(res.message);
				}
			}
	        return {
	            "rows": res.data //数据
	         };
		}
	});
}

function operateFormatter(value, row, index) {
    return [
 '<a href="javascript:;" data-toggle="modal"  onclick="modifyRow(\''+row.fileId+'\',\''+row.fileDataType+'\',\''+row.filePerfix+'\',\''+row.fieldConfig+'\')">修改 | </a>',
 '<a href="javascript:;" data-toggle="modal"  onclick="delRow(\''+row.fileId+'\',\''+row.fileDataType+'\',\''+row.filePerfix+'\',\''+row.fieldConfig+'\')">删除</a>'      
   ].join('');
}

function setPerfix(value){
	
	$("#filePerfix").val(value);
	var text=$("#fileDataType1").find("option:selected").text();
	$("#fileDataType").val(text);
}

/**
 * 增加FTP文件配置信息
 */
function add() {
	
	operate_type = 1;
	$("#dataForm")[0].reset(); // 清空表单
	$("#editTaskId").val(taskId);
	addFileDataType();
	$('#EditPanel').modal(); // 弹出表单
}

function addFileDataType(){
	
	var fileDataType = document.getElementById("fileDataType1");
	var filePerfix = document.getElementById("filePerfix");
	$.ajax({  
        url: 'task/ftpfiletype/1',  
        type: 'get',  
        async:false,
        cache: false,
        beforeSend: LoadFunction, //加载执行方法    
        error: erryFunction,  //错误执行方法    
        success: succFunction //成功执行方法      
    });  
    function LoadFunction() {  
        $("#list").html('加载中...');  
    }  
    function erryFunction() {  
        alert("操作失败!");  
    }  
    function succFunction(ret) {  

		var json = eval(ret); // 数组
		if (json.status == 0) {
			var data=json.data;
			fileDataType.options.length=0;
			 for(var i in data){ 
				 
				fileDataType.options.add(new Option(data[i].typeName, data[i].typeCode));
			}   
			 filePerfix.value = data[0].typeCode;
		}
    }   
}

function saveFileConfig(){
	
	var url='task/ftpconfig/add';
	if(operate_type==1){
		url='task/ftpconfig/add';
	}
	else if(operate_type==2){
		url='task/ftpconfig/edit';
	}
	$("#saveSet").attr("disabled",true);
    $.ajax({  
        url: url,  
        type: 'post', 
        async:false,
        cache: false,
        data : $("#dataForm").serialize(),
        beforeSend: LoadFunction, //加载执行方法    
        error: erryFunction,  //错误执行方法    
        success: succFunction //成功执行方法        
    });
    function LoadFunction() {  
        $("#list").html('加载中...');  
    }  
    function erryFunction() {  
        alert("操作失败!");  
        $("#saveSet").attr("disabled",false);
    }  
    function succFunction(ret) {  

		var json = eval(ret); 
		alert(json.message);
		if (json.status == 0) {
			$('#EditPanel').modal('hide');
			findFtpConfig();
			$('#tb').bootstrapTable('hideColumn', 'fileId');
				
		}
		$("#saveSet").attr("disabled",false);
    } 

}

/**
 * 修改FTP文件配置信息
 */
function modify() {
	
	var rows=$("#tb").bootstrapTable('getSelections');
	//var checkedNum = $("input[name='checkbox']:checked").length;
	if (rows.length > 1) {
		alert("修改只能选择一项数据!");
		return false;
	} else if (rows.length <= 0) {
		alert("至少选择一条操作数据!");
		return false;
	}
	
	operate_type = 2;
	var obj = rows[0];
	
	$("#dataForm")[0].reset(); // 清空表单
	addFileDataType();
	$("#editTaskId").val(taskId);
	$("#fileId").val(obj.fileId);
	$("#filePerfix").val(obj.filePerfix);
	$("#fileDataType").val(obj.fileDataType);
	$("#fileDataType1").val(obj.filePerfix);
	$("#fieldConfig").val(obj.fieldConfig);
	$('#EditPanel').modal(); // 弹出表单
}


/**
 * 修改FTP文件配置信息
 */
function modifyRow(fileId,fileDataType,filePerfix,fieldConfig) {
	
	operate_type = 2;
	
	$("#dataForm")[0].reset(); // 清空表单
	addFileDataType();
	$("#editTaskId").val(taskId);
	$("#fileId").val(fileId);
	$("#filePerfix").val(filePerfix);
	$("#fileDataType").val(fileDataType);
	$("#fileDataType1").val(filePerfix);
	$("#fieldConfig").val(fieldConfig);
	$('#EditPanel').modal(); // 弹出表单
}

function del(){
	
	var rows=$("#tb").bootstrapTable('getSelections');
	if (rows.length <= 0) {
		alert("至少选择一条操作数据!");
		return false;
	}
	
	if (confirm("确定删除所选FTP文件类型?")) {
		  
		var ids= [];
		$.each(rows, function (i, n){
			ids[ids.length] = n.fileId;
		});
		
	    $.ajax({  
	        url: 'task/ftpconfig/del',  
	        type: 'post',  
	        async:false,
	        cache: false,
	        data : "ids="+ids,
	        beforeSend: LoadFunction, //加载执行方法    
	        error: erryFunction,  //错误执行方法    
	        success: succFunction //成功执行方法      
	    });  
	    function LoadFunction() {  
	        $("#list").html('加载中...');  
	    }  
	    function erryFunction() {  
	        alert("操作失败!");  
	    }  
	    function succFunction(ret) {  

			var json = eval(ret);
			alert(json.message); 
			if (json.status == 0) {
	   		 findFtpConfig();
			 $('#tb').bootstrapTable('hideColumn', 'fileId');
			}
	    } 

	}
}

function delRow(fileId,fileDataType,filePerfix,fieldConfig){
	
	if (confirm("确定删除所选FTP文件类型?")) {
		
	    $.ajax({  
	        url: 'task/ftpconfig/del',  
	        type: 'post',  
	        async:false,
	        cache: false,
	        data : "ids="+fileId,
	        beforeSend: LoadFunction, //加载执行方法    
	        error: erryFunction,  //错误执行方法    
	        success: succFunction //成功执行方法      
	    });  
	    function LoadFunction() {  
	        $("#list").html('加载中...');  
	    }  
	    function erryFunction() {  
	        alert("操作失败!");  
	    }  
	    function succFunction(ret) {  

			var json = eval(ret);
			alert(json.message); 
			if (json.status == 0) {
	   		 findFtpConfig();
			 $('#tb').bootstrapTable('hideColumn', 'fileId');
			}
	    } 

	}
}