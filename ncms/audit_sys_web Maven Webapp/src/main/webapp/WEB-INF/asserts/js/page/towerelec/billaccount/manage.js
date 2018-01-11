//已显示表格list
var showTableList = null;
         
var contracttbIds;
var resourcetbIds;
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

//报账点列表
function loadTableData() {
    // 先销毁表格
    $('#tb').bootstrapTable('destroy');
    // 初始化表格,动态从服务器加载数据
    $("#tb").bootstrapTable({
        method : "post",
        contentType : "application/x-www-form-urlencoded",
        url : "queryReimburseInfo", // 获取数据的地址
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
                billaccountCode :   $('#billaccountCodeQ').val(),
                billaccountName :   $("#billaccountNameQ").val(),
                pregId :    $("#city").val(),
                regId : $("#region").val(),
                billaccountState :  $("#billaccountStateQ").val(),
                auditingState : $("#auditingStateQ").val()
            };
            return param;
        },
        columns: [{
            checkbox: true
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

function selectClick(index){
    var isChecked = $("#ckb"+index).prop('checked');
    if(isChecked){
        $("#tb").bootstrapTable('check', index);
    }else{
        $("#tb").bootstrapTable('uncheck', index);
    }
}

//合同列表
function loadContractTableData(billaccountId) {
    
    $.ajax({
        type: 'POST',
        url: "queryContractInfo", // 获取数据的地址,
        dataType:'json',
        async : false, 
        data:{
            pageNumber: 1,    
            pageSize: 10,
            billaccountId : billaccountId
        },
        success: function(data) {    
            if(data !="" ){   
                console.log(data)
                contracttbIds= data.obj.list;  
            }else{    
                 
            }    
         },    
         error : function() {    
              alert("异常！");    
         }     
        
        
    });
}

//资源列表
function loadResourceTableData(billaccountId , regId) {
    $.ajax({
        type: 'POST',
        url: "queryResourceTree", // 获取数据的地址,queryResourceTree
        dataType:'json',
        async : false, 
        data:{
            billaccountId : billaccountId,
            regId : regId
        },
        success: function(data) {    
            if(data !="" ){  
                console.log(data)
                resourcetbIds= data.obj;  
            }else{    
                 
            }    
         },    
         error : function() {    
              alert("异常！");    
         }     
    });
}


/**
 * 增加信息
 */
function addPopup(){
    window.location.href="add.html";
}

function tuopoPopup(billaccountId){
    localStorage.setItem("billaccountId" , billaccountId);
    window.location.href = "tuopo.html";
}

function back(){
	localStorage.removeItem('billaccountId');
	localStorage.removeItem('billaccountInfo');
    javascript:history.back(-1);
}

function showPopup(row){
    
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
     getAddressUpdate(row.pregId,row.regId);
    $("#addOrModifyForm input").attr("disabled","disabled");
    $("#addOrModifyForm select").attr("disabled","disabled");
    $(".modal-header button").attr("disabled","disabled");
    $("#cancelBtn").removeAttr("disabled");
}

/**
 * 修改信息
 */
function updatePopup(){
     var ids = $('#tb') .bootstrapTable('getAllSelections') ;

    if (ids.length <= 0) {
        alert("至少选择一条操作数据!");
        return false;
    }
        
     if(ids && ids.length >1){
         alert("最多选择一条进行修改操作");
         return;
     }
     
     for (var i = 0; i < ids.length; i++) {
        if(ids[0].auditingState == 9){
            alertModel("审核中的数据，不能修改！");
            return false;
        }
        if(ids[0].auditingState == 0){
            alertModel("已经审核通过的数据，不能修改！");
            return false;
        }
     }
     
     var billaccountId=ids[0].billaccountId;
     var regId=ids[0].regId;
	 window.location.href = 'update.html?billaccountId='+billaccountId+'&regId='+regId+'';   
}

/**
 * 删除信息
 */
function deletePopup(){
    var rows=$("#tb").bootstrapTable('getSelections');
    if (rows.length <= 0) {
        alert("至少选择一条操作数据!");
        return false;
    }
    
    for (var i = 0; i < rows.length; i++) {
        if(rows[0].auditingState == 9){
            alertModel("审核中的数据，无法删除！");
            return false;
        }
        if(rows[0].auditingState == 0){
            alertModel("已经审核通过的数据，无法删除！");
            return false;
        }
        delete rows[i]["0"];
    }
    
    if (confirm("确定删除所选报账点?")){
        var relationArrays = [];
        $.each(rows,function(i,item){
             relationArrays.push(item);
        });
        
         $.ajax({
              type : "POST",
              url : 'del',
              data:{"params":JSON.stringify(relationArrays)},
              dataType : 'json',
              cache : false,
              success : function(data) {
                  if (data != null) {
                        alertModel(data.msg);
                        loadTableData();
                    }
              }
         });
    }
}

/**
 * 导入信息
 */
function importPopup(){
    $("#EditPanel").modal();
}

/**
 * 导出信息
 */
function exportPopup(){
    exportFormSubmit();
}

function exportFormSubmit() 
{ 
    var form = $("<form>");  
    form.attr('style', 'display:none');  
    form.attr('target', '');  
    form.attr('method', 'post');  
    form.attr('action', 'export');  
    
    $('body').append(form);  
    

    form.submit();  
    form.remove();
} 
/**
 * 提交审批xup
 * 
 */
function checkInfo(){
    
     var ids = $('#tb') .bootstrapTable('getAllSelections') ;
     var billaccountId=ids[0].billaccountId;
     var regId=ids[0].regId;
     console.log(regId)
    loadContractTableData(billaccountId) ;
    //console.log(contracttbIds)
    //判断是否有合同
    if (contracttbIds == "" || contracttbIds == null || contracttbIds == undefined) {
        alertModel("请先关联一条合同数据");
        return false;
    }
    //判断是否有资源
    /*var resourcetbIds = $('#resourcetb') .bootstrapTable('getAllSelections') ;
    if (resourcetbIds.length <= 0) {
        alertModel("请先关联一条资源数据");
        return false;
    }*/
    loadResourceTableData(billaccountId , regId);
    if (resourcetbIds == "" || resourcetbIds == null || resourcetbIds == undefined) {
        alertModel("请先关联一条资源数据");
        return false;
    }
/*    if(contracttbIds[0].isIncludeAll==0){
    	 for(var i=0;i<resourcetbIds.length;i++){
    		 co=false;
    	    	if(resourcetbIds[i].parentId==null){
    	    		for(var j=0;j<resourcetbIds.length;j++){
    	    			if(resourcetbIds[i].nodeId==resourcetbIds[j].parentId){
    	    				co=true;
    	    			}
    	    		}
    	    		if(co==false){
	    				 alertModel("合同为未包干合同，有资源未关联电表，请关联电表");
    			        return false;
	    			}
    	    	}
    	    	
    	 }
    }*/
    if(contracttbIds[0].isIncludeAll==0){
    	 co=false;
	   	 for(var i=0;i<resourcetbIds.length;i++){
	   	    	if(resourcetbIds[i].parentId==null){
	   	    				co=true;
	   	    				i=resourcetbIds.length;
	   	    	}
	   	 }
		if(co==false){
			 alertModel("合同为未包干合同，有资源未关联电表，请关联电表");
	        return false;
		}
   }
    

    var ids = $('#tb') .bootstrapTable('getAllSelections') ;
    if (ids.length <= 0) {
        alertModel("请先选择一条操作数据");
        return false;
    }
    submitApproval();
}

function submitApproval(){
    var ids = $('#tb') .bootstrapTable('getAllSelections') ;
    if(ids[0].auditingState==9){
        alertModel("该数据正在审核");
        return false;
    }else if(ids[0].auditingState == 8){
        alertModel("审核未通过的合同，无法提交审核。需修改信息后重新提交审核");
        return;
    }else if(ids[0].auditingState == 0){
        alertModel("审核通过的无法提交审核");
        return;
    }
    queryFirstUsersByProcDefKey(EleBillaccount.procDefKey,"formSubmit",ids[0].regId);   
}

//提交流程
function formSubmit(){
    var ids = $('#tb') .bootstrapTable('getAllSelections') ;
    var billaccountIds=new Array();
    for(var i=0;i<ids.length;i++){
        billaccountIds.push(ids[0].billaccountId)
    }
    var nextUserId=$("input[name='userChecked']:checked").val();
    var datasObjs = new Array();
    for(var i = 0; i < billaccountIds.length; i++){
        var id = billaccountIds[i];
        var obj={"billaccountId":id,"nextUserId":nextUserId};
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

function save(){
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

    $.post("saveOrUpdate" , {"billaccountId":billaccountId,"billaccountCode":billaccountCode,
        "billaccountName":billaccountName,"pregId":pregId,"regId":regId,
        "billaccountType":billaccountType,"planDate":planDate,"calcMulti":calcMulti,
        "billaccountNote":billaccountNote,"billaccountState":billaccountState
    },function(data){
        loadTableData();
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
        "billaccountNote":billaccountNote,"billaccountState":billaccountState
    },function(data){
        loadTableData();
    });
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

function importbillaccount(){
	// "报账电信息导入" 弹出框名称
	// _EleBillaccount 功能标识
	// formSubmit 回调方法
	// true 导出模板
	// url 模板下载路径
	var url = projectName + "/asserts/files-disk/temp/ELEBILLACCOUNT.xlsx";
	importModel("报账点信息导入","_EleBillaccount","submitForm", true, url);//弹出导入框
}

/**
 * 上传文件并保存到数据库
 * @param suffix 模块标识
 */
function submitForm(suffix){
	var formData = new FormData($("#importDataForm")[0]); 
	$.ajax({
		url:'importFile',
		type : 'post',
		data : formData,
	    async: true,  
     	cache: false,  
        contentType: false,  
        processData: false, 
 		beforeSend:function(){//启动a
 			startTimeJob(suffix);
        },  
 		success : function(result){
			stopTimeJob();//停止进度条
 			if(result != null&&result.success=="1"){
 				loadTableData();
 			} else if(result != null&&result.success=="0"){
 				//出错
 			}
				alertModel(result.msg);
 		},
        complete:function(){//ajax请求完成时执行    
			stopTimeJob();//停止进度条
        },
 		error : function() {
			stopTimeJob();//停止进度条
			alertModel("请求失败！");
 		}
	});
}

function importModel(title,suffix,func,isExportTemple,url){
	//导入弹出页面
	var str='';
	str+='<div id="importModel" class="modal hide fade importModel" tabindex="-1">';
		str+='<div class="modal-dialog" role="document">';
			str+='<div class="modal-content">';
				str+='<div class="modal-header">';
					str+='<button class="close" type="button" data-dismiss="modal">×</button>';
					str+='<h4 id="myModalLabel">'+title+'</h4>';
				str+='</div>';
				str+='<div class="modal-body">';
					str+='<form class="form-horizontal" id="importDataForm" action="importFile" method="post" enctype="multipart/form-data">';			
						str+='<div class="form-group">'
							str+='<input type="file" multiple="multiple" accept=".xls,.XLS,.xlsx,.XLSX" id="file" name="file" />';
							str+='<input type="hidden" name="suffix" value="'+suffix+'"/>';
						str+='</div>';
						if(isExportTemple){//判断是否显示模板
							str+='<div class="form-group">';
								str+='<a href="' + url +'">下载导入模板</a>';
							str+='</div>';
						}
					str+='</form>';
				str+='</div>';
				str+='<div class="modal-footer">';
					str+='<a href="#" class="btn" data-dismiss="modal">关闭</a>';
					str+='<a href="#" class="btn btn-primary" onclick="'+func+'(\''+suffix+'\');">保存</a>';
				str+='</div>';
			str+='</div>';
		str+='</div>';
	str+='</div>';
	$('body').append(str);
	$('#importModel').modal('show');
}
