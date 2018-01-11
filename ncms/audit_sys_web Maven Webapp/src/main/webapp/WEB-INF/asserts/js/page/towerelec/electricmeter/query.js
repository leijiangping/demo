var curPageNum = 1;
var datalist = null;
var listData = null;
var cityList = null;
var regionList = null;
var showTableList = null;
var MonHead = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];  
$(document).ready(function() {
	initialize();
});
/**
 * 初始化加页面
 */
function initialize() {
	
	getAddress();
	findElectricMeter();
}

//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
 var o = {
     "M+": this.getMonth() + 1, //月份 
     "d+": this.getDate(), //日 
     "h+": this.getHours(), //小时 
     "m+": this.getMinutes(), //分 
     "s+": this.getSeconds(), //秒 
     "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
     "S": this.getMilliseconds() //毫秒 
 };
 if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
 for (var k in o)
 if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
 return fmt;
}

/**
 * 列表查询
 */
function findElectricMeter() {
	
	var meterCode = $("#meterCode").val();
	var city = $("#city option:selected").val();
	var region = $("#region option:selected").val();
	var meterState = $("#meterState option:selected").val();
	var meterType = $("#meterType option:selected").val();
	var relateResState = $("#relateResState option:selected").val();
	
	var citye = document.getElementById("city");
	var regione = document.getElementById("region");
	var meterState1 = document.getElementById("meterState");
	var meterType1 = document.getElementById("meterType");
	var relateResState1 = document.getElementById("relateResState");
	if(meterState1.selectedIndex==0) 
		meterState="";
	if(meterType1.selectedIndex==0) 
		meterType="";
	if(relateResState1.selectedIndex==0) 
		relateResState="";
	if(citye.selectedIndex==0) 
		city="";
	if(regione.selectedIndex==0) 
		region="";
	
	// 先销毁表格
	$('#tb').bootstrapTable('destroy');
	// 初始化表格,动态从服务器加载数据
	$("#tb").bootstrapTable({
		method : "post",
		contentType : "application/x-www-form-urlencoded",
		url : "queryElectricMeter", // 获取数据的地址
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
					city : city,
					region : region,
					meterCode : meterCode,
					meterState : meterState,
					meterType:meterType,
					relateResState:relateResState,
					cur_page_num: params.pageNumber,    
					page_count: params.pageSize
			};
			return param;
		},
		columns: [ {
            field: 'meterCode',
            title: '电表编码',
            formatter:function(value,row,index){
            	var meterId = row.meterId;
            	var supplierLink = '<a href="javascript:void(0)"  onclick=viewRecord("'+meterId+'") style="white-space:nowrap">'+ value + '</a>';
            	return  supplierLink;
            }
        }, {
            field: 'meterType',
            title: '电表类型',
            formatter:function(value,row,index){
            	var str=value;
            	if(1==value){
            		str='普通表';
            	}
            	else if(2==value){
            		str='平峰谷表';
            	}
            	return str;
            }
        }, {
            field: 'pregName',
            title: '所属地市'
        }, {
            field: 'regName',
            title: '所属县区'
        }, {
            field: 'meterState',
            title: '电表状态',
            formatter:function(value,row,index){
            	var str=value;
            	if(0==value){
            		str='正常';
            	}
            	else if(9==value){
            		str='停用';
            	}
            	return str;
            }
        }, {
            field: 'accountNumber',
            title: '户号'
        }, {
            field: 'electricmeterMultiply',
            title: '电表倍率'
        }, {
            field: 'isShare',
            title: '多机房公用',
            formatter:function(value,row,index){
            	var str=value;
            	if(0==value){
            		str='否';
            	}
            	else if(1==value){
            		str='是';
            	}
            	return str;
            }
        },{
            field: 'installDate',
            title: '安装日期',
            formatter:function(value,row,index){
            	return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
            }
        }],
		onLoadError : function() { // 加载失败时执行
			console.log("请求失败！");
		},
		responseHandler: function(res) {
			if(res != null){//报错反馈
				if(res.status != "0"){
		            alertModel(res.message);
				}
				showTableList = res.data.list;
			}
	        return {
	            "total": res.data.total,//总页数
	            "rows": res.data.list //数据
	         };
		}
	});
}

function exportExcel(){
    
	var meterCode = $("#meterCode").val();
	var city = $("#city option:selected").val();
	var region = $("#region option:selected").val();
	var meterState = $("#meterState option:selected").val();
	var meterType = $("#meterType option:selected").val();
	var relateResState = $("#relateResState option:selected").val();
	
	var citye = document.getElementById("city");
	var regione = document.getElementById("region");
	var meterState1 = document.getElementById("meterState");
	var meterType1 = document.getElementById("meterType");
	var relateResState1 = document.getElementById("relateResState");
	if(meterState1.selectedIndex==0) 
		meterState="";
	if(meterType1.selectedIndex==0) 
		meterType="";
	if(relateResState1.selectedIndex==0) 
		relateResState="";
	if(citye.selectedIndex==0) 
		city="";
	if(regione.selectedIndex==0) 
		region="";
	
	$.ajax({  
        url: 'exportElectricMeter',  
        type: 'post', 
        async:false,
        cache: false,
        data : {
        	city : city,
			region : region,
			meterCode : meterCode,
			meterState : meterState,
			meterType:meterType,
			relateResState:relateResState
		},
        beforeSend: LoadFunction, //加载执行方法    
        error: erryFunction,  //错误执行方法    
        success: succFunction //成功执行方法        
    });
    function LoadFunction() {  
        $("#list").html('导出中...');  
    }  
    function erryFunction() {  
        alert("操作失败!");  
    }  
    function succFunction(ret) {  

		var json = eval(ret); // 数组
		if (json.status == 0) {
			downLoadExecl(json.data);
		}
		else{
			alert(json.message);
		}
    } 
}

function downLoadExecl(Url){
	
    var f = document.createElement("form");
    document.body.appendChild(f);
    var i = document.createElement("input");
    i.type = "hidden"; f.appendChild(i);
    i.value = "5";
    i.name = "price";
    f.action = Url;
    f.submit();
}

/**
 * 获取用户所有权限 的地市 区县信息
 * @param title 名称 例如：供应商地市，传入title为供应商
 * @param async 同步异步设置,默认：false-异步;true-同步。不传则默认异步
 * @param CitySelect 选中地市 例如：回显选中，传入地市ID，不传则不选中
 * @param VillageSelect 选中县区 例如：回显选中，传入区县ID，不传则不选中
 */
function getAddress(title,async,citySelect,villageSelect){
	$.ajax({
		type : "post",
		url :"../../common/region/getAddressElec",
		dataType : 'json',
		contentType : "application/json;charset=UTF-8",
		async:(async?true:false),
		success : function(value) {
			if(value != null){
				sysReguins = value.obj;
				if(sysReguins!=null){
					$('#city').empty();
					$('#region').empty();
					var strCity = "<option value=''>--选择"+(title?title:"")+"地市--</option>";
					$.each(sysReguins, function (i, item){
						strCity += "<option value='" +item.regId+"' "+(citySelect&&citySelect==item.regId?"selected='true'":"")+">"+item.regName+ "</option>";
					});

					$("#city").append(strCity);

					var strReg = "<option value=''>--选择"+(title?title:"")+"区县--</option>";
					$("#region").append(strReg);
					//绑定联动事件 修改人zhujj
					$("#city").change(function(){
						$("#region").empty();
						strReg = "<option value=''>--选择"+(title?title:"")+"区县--</option>";
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

function gopage(i) {
	if (curPageNum == i)
		return;
	curPageNum = i;
	findElectricMeter();
}

/**
 * 查看用户信息
 */
function viewRecord(obj) {
	$('#dataFormView')[0].reset();
	$.ajax({
		url : 'queryOneMeter',
		data : {
			elecMeterId : obj
		},
		type : 'get',
		dataType : 'json',
		success : function(list) {
			if(list == null){
				alertModel("数据无法获取!");
				return false;
			}
			// 请求成功时
			var item = list.Obj;
			$("input[name='meterCode']").val(item.meterCode);//电表编码
			//电表类型 1普通表 2平峰谷表
			var mtrType = item.meterType == 1 ? "普通表" : "平峰谷表";
			$("input[name='meterType']").val(mtrType);       //电表类型
			$("input[name='pregName']").val(item.pregName);//所属地市
			$("input[name='regName']").val(item.regName);//所属县区
			//电表状态 0正常 9停用
			var mtrState = item.meterState == 0 ? "正常" : "停用";
			$("input[name='meterState']").val(mtrState);     //电表状态
			$("input[name='electricmeterMultiply']").val(item.electricmeterMultiply);//电表倍率
			var mtrShare = item.isShare == 0 ? "否" : "是";
			$("input[name='isShare']").val(mtrShare);  //多机房公用
			$("input[name='accountNumber']").val(item.accountNumber);//电表户号
			var unixTimestamp = new Date(item.installDate) ;
			commonTime = unixTimestamp.toLocaleString();
			if(item.installDate==null){
				$("input[name='installDate']").val("");
			}else{
				$("input[name='installDate']").val(commonTime);
			}
			$("input[name='meterNote']").val(item.meterNote);//备注信息
			if(item.meterType ==1){     //电表类型为普通表时
				$("input[name='initialValue']").val(item.initialValue);//电表初始值
				$("input[name='upperValue']").val(item.upperValue);
				$("input[name='flatValue']").val("");
				$("input[name='flatUpperValue']").val("");
				$("input[name='peakValue']").val("");
				$("input[name='peakUpperValue']").val("");
				$("input[name='valleyValue']").val("");
				$("input[name='valleyUpperValue']").val("");
				$("input[name='topValue']").val("");
				$("input[name='topUpperValue']").val("");
			}else{
				$("input[name='initialValue']").val("");
				$("input[name='upperValue']").val("");
				$("input[name='flatValue']").val(item.flatValue);
				$("input[name='flatUpperValue']").val(item.flatUpperValue);
				$("input[name='peakValue']").val(item.peakValue);
				$("input[name='peakUpperValue']").val(item.peakUpperValue);
				$("input[name='valleyValue']").val(item.valleyValue);
				$("input[name='valleyUpperValue']").val(item.valleyUpperValue);
				$("input[name='topValue']").val(item.topValue);
				$("input[name='topUpperValue']").val(item.topUpperValue);
				$("input[name='meterNote']").val(item.meterNote);
				$("input[name='accountNumber']").val(item.accountNumber);
			}
			$("#ViewPanel input").attr("readonly","true");
			$('#ViewPanel').modal();
		},
		error : function() {
			alertModel("请求异常");
		}
	})
}