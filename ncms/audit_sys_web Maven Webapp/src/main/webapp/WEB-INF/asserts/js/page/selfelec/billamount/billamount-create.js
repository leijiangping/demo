$(function(){
	loadSearch();
});
//加载查询条件
function loadSearch(){
	var oper=getParameter("oper");//如果是查看待缴费信息，则隐藏按钮
	if(oper&&oper=="view"){
		$("#createBillamount").css("display","none");
	}
//	loadTableData();
	getAddress();
	//querDictBillType();//加载报账类型
	//querDictPaymenyMethod();//加载付款方式
	
}
var showTableList;
////验证查询条件
function validateSeachParam(){
	var prvId= $("#city").val();
	var pregId=$("#region").val();
	var supplierId=$("#supplierId").val();
	var billType=$("#billType").val();
	if(prvId==null||prvId==""||!prvId){
		alertModel("请选择地市！");
		return false;
	}
	if(pregId==null||pregId==""||!pregId){
		alertModel("请选择县区!");
		return false;
	}
	return true;
}
//电费未缴费汇总
function loadTableData(){
	if(!validateSeachParam()){
		return;
	}
	
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryVEleBillamountPayment", // 获取数据的地址
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
	            auditingState:0,
	            pregId:$("#city").val(),
		        regId:$("#region").val(),
	            supplierId:$('#genamountform #supplierId').val(),
	            amountType:$("#genamountform #billType").val(),
		        paymentMethod:$("#genamountform #paymentMethod").val(),
		        billamountDate:$("#genamountform #paymentDate").val(),
		        contractCode:$("#genamountform #contractcode").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        },{
            field: 'contractCode',
            title: '合同编号'
        }, {

            field: 'billaccountName',
            title: '报账点名称'
        },{

            field: 'billamountNotax',//billamountNotaxSys
            title: '不含税金额'
        },{

            field: 'taxRate',
            title: '税率'
        },{

            field: 'billamountTaxamount',//billamountTaxamountSys
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
			console.log("获取参数异常！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.success != "1"){
		            alertModel(res.msg);
				}
				showTableList = res.obj.list;

				queryDatSupplierByPrvID();//查询待汇总电费缴费对应的供应商
			}
	        return {
	            "total": res.obj.total,//总页数
	            "rows": res.obj.list //数据
	         };
		},
		onCheck: function (row) {
			tableCheckedChange(row);
        },
        onUncheck: function (row) {
        	tableCheckedChange(row);
	    },
	    onCheckAll: function (row) {
	    	tableCheckedChange(row);
		},
		onUncheckAll: function (row) {
			tableCheckedChange(row);
		}
	});	
}
//查询省份对应的供应商
function queryDatSupplierByPrvID(){
	var str = "<option value=''>--请选择供应商--</option>";
	var supplierIdsArr=new Array();
	$.each(showTableList,function(i,v){
		supplierIdsArr.push(v.supplierId)
	});
	var datas=JSON.stringify(supplierIdsArr);
	$.ajax({
        type: "post",
        url: "../../suppliers",
        data:datas,
        dataType: "JSON",
		contentType : 'application/json;charset=utf-8',
        success: function (data) {
        	$("#supplierId").empty();
            //从服务器获取数据进行绑定
            $.each(data.Obj, function (i, item) {
                str += "<option value=" + item.supplierId + ">" + item.supplierName + "</option>";
            });
            //将数据添加到省份这个下拉框里面
            $("#supplierId").append(str);
        },
        error: function () { }
    });
	
}
//加载报账类型
function querDictBillType(){
	var billType=queryDictType("BILL_TYPE");
	appendSelect("billType",billType,"dict_id","dict_name","","报账类型");
	
}
//加载付款方式
function querDictPaymenyMethod(){
	var paymenyMethod=queryDictType("PAYMENY_METHOD");
	appendSelect("paymentMethod",paymenyMethod,"dict_id","dict_name","","付款方式");
	
}

function tableCheckedChange(){
	var rows= $("#tb").bootstrapTable('getSelections');
	$("#checkedNum").html(rows.length);
	var checkedAmount=0;
	$.each(rows,function(i,row){
		checkedAmount+=row.billAmountActual;
	});
	
	$("#checkedAmount").html(checkedAmount);
}
//生成汇总单
function createBillamount(){
	var rows= $("#tb").bootstrapTable('getSelections');
	
	if(rows.length==0||!rows[0].billaccountpaymentdetailId){
		alertModel("请选则至少一条数据");
		return false;
	}
	var ids=new Array();
	var supplierId=rows[0].supplierId;
	var flag=false;
	$.each(rows,function(i,row){
		if(supplierId!=row.supplierId){
			flag=true;
			return false;
		}
		ids.push(row.billaccountpaymentdetailId);
	});
	if(flag){
		alertModel("选择的待汇总记录所属供应商必须一致！");
		return false;
	}
	 $.ajax({
		  type : "POST",
		  url : 'saveAmountInfo',
		  data:{"params":JSON.stringify(ids)},
		  dataType : 'json',
		  cache : false,
		  success : function(result) {
			   if (result != null&&result.success=="1") {
				    //localStorage.setItem("billamountId" , JSON.stringify(ids[0]));
					location.href="summary_sheet.html?billamountId="+result.obj;
				}else{
					alertModel(result.msg);
				}
		  }
	 });
}
//完成
function finishBillamount(){

	location.href="billamount.html";
	
}

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