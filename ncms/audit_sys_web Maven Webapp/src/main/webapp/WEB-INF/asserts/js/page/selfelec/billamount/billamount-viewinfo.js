/**
 * 租费报账汇总明细
 */
$(function(){
	loadInit();
});

//获取路径传值
var billamountId=null;
//加载查询条件
function loadInit(){
	//获取路径传值
	billamountId=getParameter("billamountId");
	searchBillamount();//加载租费报账汇总
}
//根据租费汇总ID获取租费报账汇总数据
function searchBillamount(){
	$.ajax({
		url : 'queryElecBillamountById', 
		type : 'post',
		cache : false,
		data:{billamountId:billamountId},
		dataType : 'json',
		success : function(result) {
			if (result != null&&result.success=="1") {
				setBillamountInfo(result.obj)
			}else{
				alertModel(result.msg);
			}
		},
		error : function() {
			alertModel("请求异常");
		}
	});
}
function setBillamountInfo(obj){
	$("#billamountCode").html(obj.billamountCode);
	$("#billamountDate").html(obj.billamountDate);
	$("#billamountWithtax").html(obj.billamountWithtax);
	$("#supplierCode").html(obj.supplierCode);
	$("#supplierName").html(obj.supplierName);
	$("#depositBank").html(obj.depositBank);
	$("#bankAccount").html(obj.bankAccount);

	$("#billamountDetail").html(obj.detailCnt);
	$("#billType").html(obj.amountType);
	$("#City").html(obj.pregName);
	$("#Village").html(obj.regName);
	$("#userRealName").html(obj.userRealname);
	
	loadTableData();//加载汇总单明细表
}
//汇总单明细表
function loadTableData(){
	// 先销毁表格
	$('#billamountDetailTable').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#billamountDetailTable").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryPaymentDetail", // 获取数据的地址
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
	            
	            billamountId: billamountId
		           // pregId:$("#City").val(),
		           // regId:$("#Village").val(),
		           // billamountDateStart:$("#billamountDateStart").val(),
		           // billamountDateEnd:$("#billamountDateEnd").val(),

		           // billamountState:$("#billamountState").val(),
		           // contractCode:$("#rentcontractCode").val(),//合同名称或者合同代码
		           // supplierCode:$("#supplierCode").val()//供应商代码或者名称
			};
			return param;
		},
		columns: [{
            checkbox: true
        },{
            field: 'contractCode',
            title: '合同编号'
        },{

            field: 'billaccountName',
            title: '报账点名称'
        },{

            field: 'billamountNotax',
            title: '不含税金额'
        },{

            field: 'taxRate',
            title: '税率',
            formatter : function(value){
            	return value+"%";
            }
        },{

            field: 'billamountTaxamount',
            title: '税金'
        },{

            field: 'detailCnt',
            title: '电表缴费数量'
        },{

            field: 'totalDegreeActual',
            title: '用电度数'
        },{
            field: 'billAmountActual',
            title: '实际报账金额'
        },{
            field: 'billamountStartdate',
            title: '缴费期始',
            formatter : function(value){
            	return new Date(value).format("yyyy-MM-dd");
            }
        }, {
            field: 'billamountEnddate',
            title: '缴费期终',
            formatter : function(value){
            	return new Date(value).format("yyyy-MM-dd");
            }
        }, {
            field: 'billamountDate',
            title: '缴费日期',
            formatter : function(value){
            	return new Date(value).format("yyyy-MM-dd");
            }
        }
        /*, {
            field: 'paymentUser',
            title: '缴费经办人'
        }, {
            field: 'paymentUsertel',
            title: '经办人电话'
        }*/
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
//打印汇总单
function printBillamountPDF(){
	
}
//导出PDF汇总单
function exportBillamount(){
	//标题为：地市+区县+电费报账汇总单
	
	var title = $("#City").html()+$("#Village").html()+"电费报账汇总单";
//	window.open("exportPdf?imgUrl="+"aaacc"+"&title="+title,"_blank");
	
	html2canvas($("#pdfcanvas"),{
		onrendered: function (canvas){
			var imgUrl = canvas.toDataURL();//获取截图的Base64编码
//			$("#imgurl").val(imgUrl);
//						$.post("exportPdf",{"imgUrl":imgUrl , "title":"aa"} , function(){
//				
//				alert("chenggong");
//			});
			
			//给表单参数赋值提交
			$("#imgUrl").val(imgUrl);
			$("#title").val(title);
			$("#pdfform").submit();
		}
	});
	
	

}
//导出缴费明细
function exportPaymentDatil(){
	window.open("exportPaymentDatil?billamountId="+billamountId,"_blank"); 
}
//完成
function finishBillamount(){

	location.href="billamount.html";
}