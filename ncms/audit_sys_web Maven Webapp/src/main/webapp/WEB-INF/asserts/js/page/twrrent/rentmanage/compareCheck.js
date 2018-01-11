//已显示表格list
var showTableList = null;

var operate_type = 1;// 1 新增，2 修改

$(document).ready(function() {
	initCurrentPage();
});
function initCurrentPage(){
	curPageNum = 1;
	getAddress();
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
		url : "queryTowerResourceCheckInfo", // 获取数据的地址
		striped : true, // 表格显示条纹
		pagination : true, // 启动分页
		pageSize : ipageCount, // 每页显示的记录数
		pageNumber : curPageNum, // 当前第几页
		minimumCountColumns: 1,  //最少允许的列数
		fixedColumns:true,
		fixedNumber:5,
		clickToSelect: true,  //是否启用点击选中行
		pageList : [10, 25, 50, 100, 500], // 记录数可选列表
		search : false, // 是否启用查询
		sidePagination : "server", // 表示服务端请求
		// 设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
		// 设置为limit可以获取limit, offset, search, sort, order
		queryParamsType : "undefined",
		queryParams : function queryParams(params) { // 设置查询参数
			var param = {
				pageNumber		:	params.pageNumber,    
				pageSize		: 	params.pageSize,
				//checkState 		: 	$("#checkState").val(),
				pRegId 			: 	$("#City").val(),
				regId	 		: 	$("#Village").val()
				//taskDefKey		:	$("#taskDefKey").val()
			};
			return param;
		},
		columns: [{
            checkbox: true
        },{
            field: 'sysRegionVO.pRegName',
            title: '地市'
        },{
            field: 'sysRegionVO.regName',
            title: '区县'
        },{
        	field: 'businessConfirmNumber',
            title: '业务确认单号'
        },{
            field: 'towerStationCode',
            title: '铁塔公司站址编码'
        },{
            field: 'operatorPhyStationName',
            title: '运营商自有物理站址名称'
        },{
            field: 'operatorPhyStationCode',
            title: '运营商自有物理站址编码'
        },{
            field: 'detailAddress',
            title: '详细地址'
        },{
            field: 'longitude',
            title: '经度'
        },{
            field: 'longitude',
            title: '纬度'
        },{
            field: 'checkRentinfotowerState',
            title: '审核状态',
            formatter:function(value,row,index){
            	switch(value){
            	case -1:return "未提交";
            	case 0:return "审核通过";
            	case 9:return "审核中";
            	}
            }
        },],
		/*onLoadSuccess : function() { // 加载成功时执行
			console.log("加载成功");
		},*/
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.obj!=null&&res.success == "1"){
					showTableList = res.obj.result;
					unique(showTableList);
					return {
			            "total": res.obj.total,//总页数
			            "rows": res.obj.result //数据
				    };
				}else{
					//alertModel(res.msg);
					 return {
			            "total": 0,//总页数
			            "rows": 0 //数据
				     };
				}
			}
		}
	});
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
/**
 * 获取用户所有权限 的地市 区县信息
 * @param title 名称 例如：供应商地市，传入title为供应商
 */
function getAddress(title){
	$.ajax({
		type : "post",
		url : "getAddressComCheck",
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		success : function(value) {
			if(value != null){
				sysReguins = value.obj;
				if(sysReguins!=null){
					$('#City').empty();
					$('#Village').empty();
					var strCity = "<option value=''>-选择"+(title?title:"")+"地市-</option>";
					$.each(sysReguins, function (i, item){
						strCity += "<option value='" +item.regId+"'>"+item.regName+ "</option>";
					});

					$("#City").append(strCity);

					var strReg = "<option value=''>-选择"+(title?title:"")+"区县-</option>";
					$("#Village").append(strReg);
					//绑定联动事件 修改人zhujj
					$("#City").change(function(){
						$("#Village").empty();
						strReg = "<option value=''>-选择"+(title?title:"")+"区县-</option>";
						if($("#City").val()!=""){
							$.each(sysReguins, function (i, item){
								if(item.regId==$("#City").val()){
									$.each(item.child, function (j, itemchild){
										strReg += "<option value='" + itemchild.regId+"'>"+itemchild.regName+ "</option>";
									});
								}
							});
						}
						$("#Village").append(strReg);
					});
					
				}
			}
		},
		error : function(data) {
			alertModel('获取用户地区信息失败!');
		}
	});
}
function checkInfo(){
	if(!hadCheckedRowData()){
		return false;
	}
	window.location.href="towerAudit.html?rentinformationtowerId="+rowschecked[0].rentinformationtowerId+'&taskId='+rowschecked[0].act.taskId;
}
function hadCheckedRowData(){
	if(showTableList==null || !isChecked()){
		alertModel("请先选择一条操作数据");
		return false;
	}
	return true;
}
//导出
function exportBill(){
	confirmModel('您确定要导出吗？','execRxport');
} 
function execRxport(){
	var para="&checkState="+ $("#checkState").val();
	para+="&pRegId="+ $("#City").val();
	para+="&regId="+ $("#Village").val();
	para+="&taskDefKey="+ $("#taskDefKey").val();
	
	window.open("exportTowerResourceCheckInfo?"+para,"_blank");
}