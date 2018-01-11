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
	/*initCity();
	$("#city").change(function () {
		regionBind();
    });
	$("#pregId").change(function () {
		regIdBind(null);
    });*/
	initDate();
	findMeter();
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

function initDate(){  
	
	var year = document.getElementById("YYYY");
	var month = document.getElementById("MM");
	var day = document.getElementById("DD");

	//先给年下拉框赋内容  
	var y = new Date().getFullYear();  
	for (var i = (y-30); i < (y+30); i++) //以今年为准，前30年，后30年  
	{ 
		year.options.add(new Option(" " + i, i));
	}    
	  
	//赋月份的下拉框  
	for (var i = 1; i < 13; i++)  
	{  
		month.options.add(new Option(" " + i, i));
	}   
	  
	$("#YYYY").value = y;  
	$("#MM").value = new Date().getMonth() + 1;  
	var n = MonHead[new Date().getMonth()];  
	if (new Date().getMonth() ==1 && IsPinYear(YYYYvalue)) n++;  
	writeDay(n); //赋日期下拉框  
	$("#DD").value = new Date().getDate();  
	}  
	function YYYYMM(str) //年发生变化时日期发生变化(主要是判断闰平年)  
	{  
		var month = document.getElementById("MM");
	     var monthSelect=month.selectedIndex;
		var MMvalue = month.options[monthSelect].value;  
	if (MMvalue == ""){DD.outerHTML = strDD; return;}  
	var n = MonHead[MMvalue - 1];  
	if (MMvalue ==2 && IsPinYear(str)) n++;  
	writeDay(n)  
	}  
	function MMDD(str) //月发生变化时日期联动  
	{  
		var year = document.getElementById("YYYY");
		var yearSelect=year.selectedIndex;
	var YYYYvalue = year.options[yearSelect].value;  
	if (str == ""){DD.outerHTML = strDD; return;}  
	var n = MonHead[str - 1];  
	if (str ==2 && IsPinYear(YYYYvalue)) n++;  
	writeDay(n)  
	}  
	function writeDay(n) //据条件写日期的下拉框  
	{  
		var day = document.getElementById("DD");
	for (var i=1; i<(n+1); i++)  
		day.options.add(new Option(" " + i, i));
	}  
	function IsPinYear(year)//判断是否闰平年  
	{ return(0 == year%4 && (year%100 !=0 || year%400 == 0))}  

/**
 * 列表查询
 */
function findMeter() {
	var resourcename = $("#resourcename").val();
	var city = $("#city option:selected").val();
	var region = $("#region option:selected").val();
	var resourcetype = $("#resourcetype option:selected").val();
	var year = $("#YYYY option:selected").val();
	var month = $("#MM option:selected").val();
	var day = $("#DD option:selected").val();
	
	var citye = document.getElementById("city");
	var regione = document.getElementById("region");
	var YYYY = document.getElementById("YYYY");
	var MM = document.getElementById("MM");
	var DD = document.getElementById("DD");
	if(YYYY.selectedIndex==0) 
		year="";
	if(MM.selectedIndex==0) 
		month="";
	if(DD.selectedIndex==0) 
		day="";
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
		url : "queryMeterPerf", // 获取数据的地址
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
					resourcename : resourcename,
					resourcetype : resourcetype,
					year:year,
					month:month,
					day:day,
					cur_page_num: params.pageNumber,    
					page_count: params.pageSize
			};
			return param;
		},
		columns: [ {
            field: 'Number',
            title: '序号',
            formatter: function (value, row, index) {
                   return index+1;
            }
        }, {
            field: 'resourceType',
            title: '资源类型',
            formatter:function(value,row,index){
            	switch(value){
            		case 10005:return '基站机房';
            		case 10006:return'机房';
            	}
            }
        }, {
            field: 'resourceName',
            title: '资源名称'
        }, {
            field: 'resourceCode',
            title: '资源标识'
        }, {
            field: 'codeType',
            title: '标识类型',
            formatter:function(value,row,index){
            	var str=value;
            	if(1==value){
            		str='CID';
            	}
            	else if(2==value){
            		str='编码';
            	}
            	return str;
            }
        }, {
            field: 'pregName',
            title: '所属地市'
        }, {
            field: 'regName',
            title: '所属区县'
        }, {
            field: 'startTime',
            title: '记录起始时间',
            formatter:function(value,row,index){
            	return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
            }
        }, {
            field: 'stopTime',
            title: '记录结束时间',
            formatter:function(value,row,index){
            	return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
            }
        }, {
            field: 'totalDegree',
            title: '总表读数'
        }, {
            field: 'totalState',
            title: '总电表状态位',
            formatter:function(value,row,index){
            	var str=value;
            	if(0==value){
            		str='正常';
            	}
            	else if(1==value){
            		str='换表';
            	}
            	else if(2==value){
            		str='清零';
            	}
            	return str;
            }
        }, {
            field: 'equipmentDegree',
            title: '主设备电表读数'
        }, {
            field: 'equipmentState',
            title: '主设备电表状态位',
            formatter:function(value,row,index){
            	var str=value;
            	if(0==value){
            		str='正常';
            	}
            	else if(1==value){
            		str='换表';
            	}
            	else if(2==value){
            		str='清零';
            	}
            	return str;
            }
        },  {
            field: 'acDegree',
            title: '空调系统电表读数'
        }, {
            field: 'acState',
            title: '空调系统电表状态位',
            formatter:function(value,row,index){
            	var str=value;
            	if(0==value){
            		str='正常';
            	}
            	else if(1==value){
            		str='换表';
            	}
            	else if(2==value){
            		str='清零';
            	}
            	return str;
            }
        }, {
            field: 'exTempreture',
            title: '基站室外日平均温度'
        }, {
            field: 'inTempreture',
            title: '基站室内日平均温度'
        },],
      /*  onClickRow: function (row, td) {
        	var msg = row.rentcontractId;
        	localStorage.setItem("item1",msg);
        	window.location.href='rentcontractDetail.html';
        },	*/
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
	
	
	/*$.ajax({
		url : 'queryMeterPerf',// 跳转到 action
		data : {
			city : city,
			region : region,
			resourcename : resourcename,
			resourcetype : resourcetype,
			year:year,
			month:month,
			day:day,
			cur_page_num : curPageNum,
			page_count : ipageCount
		},
		type : 'get',
		dataType : 'json',
		success : function(back) {
			$('#tb tr:gt(0)').remove();// 删除之前的数据
			if (back != null) {
				if (back.status == "0") {
					var page = back.data;
					listData = list = page.result;
				}
				if (list == "") {
					alert("无数据!");
					return false;
				}
				var s = '';
				$.each(page.result, function(i, item){
					
					var restype="";
					if(item.resourceType=="10005"){
						restype="基站机房";
					}
					else if(item.resourceType=="10006"){
						restype="机房";
					}
					
					s = "<tr style='text-align: center;'><td>"
						+ (i + 1)
						+ "<td>"
						+ restype
						+ "</td><td>"
						+ item.resourceName
						+ "</td><td>"
						+ item.resourceCode
						+ "</td><td>"
						+ item.codeType
						+ "</td><td>"
						+ item.pregName
						+ "</td><td>"
						+ item.regName
						+ "</td><td>"
						+ new Date(item.startTime).Format("yyyy-MM-dd hh:mm:ss")
						+ "</td><td>"
						+ new Date(item.stopTime).Format("yyyy-MM-dd hh:mm:ss")
						+ "</td><td>"
						+ item.totalDegree
						+ "</td><td>"
						+ item.totalState
						+ "</td><td>"
						+ item.equipmentDegree
						+ "</td><td>"
						+ item.equipmentState
						+ "</td><td>"
						+ item.acDegree
						+ "</td><td>"
						+ item.acState
						+ "</td><td>"
						+ item.exTempreture
						+ "</td><td>"
						+ item.inTempreture
						s += "</td></tr>";
						$('#tb').append(s);
				});
				$("#page_ctr li").remove();
				createPageNum(page.pages);
			}
		},
		error : function() {
			alert("查询数据异常");
		}
	});*/
}


//导出
function exportExcel(data){
	var para="resourcename="+$("#resourcename").val()+"&city="+$("#city option:selected").val()+"&region="+$('#region option:selected').val();	
	para+="&resourcetype="+$("#resourcetype option:selected").val()+"&year="+$("#YYYY option:selected").val();
	para+="&month="+$("#MM option:selected").val()+"&day="+$("#DD option:selected").val();
	window.open("exportMeterPerf?"+para);
}

/*function exportExcel(){
    
	var resourcename = $("#resourcename").val();
	var city = $("#city option:selected").val();
	var region = $("#region option:selected").val();
	var resourcetype = $("#resourcetype option:selected").val();
	var year = $("#YYYY option:selected").val();
	var month = $("#MM option:selected").val();
	var day = $("#DD option:selected").val();
	
	var citye = document.getElementById("city");
	var regione = document.getElementById("region");
	var YYYY = document.getElementById("YYYY");
	var MM = document.getElementById("MM");
	var DD = document.getElementById("DD");
	if(YYYY.selectedIndex==0) 
		year="";
	if(MM.selectedIndex==0) 
		month="";
	if(DD.selectedIndex==0) 
		day="";
	if(citye.selectedIndex==0) 
		city="";
	if(regione.selectedIndex==0) 
		region="";
	
	$.ajax({  
        url: 'exportMeterPerf',  
        type: 'post', 
        async:false,
        cache: false,
        data : {
			city : city,
			region : region,
			resourcename : resourcename,
			resourcetype : resourcetype,
			year:year,
			month:month,
			day:day
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
}*/

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
 * 
 * @param title 名称 例如：供应商地市，传入title为供应商
 */
//function getAddress(title){
//	$.ajax({
//		type : "post",
//		url : "getAddressRent",
//		
//		dataType : 'json',
//		contentType : "application/json;charset=UTF-8",
//		success : function(value) {
//			if(value != null){
//				sysReguins = value.obj;
//				if(sysReguins!=null){
//					$('#city').empty();
//					$('#region').empty();
//					var strCity = "<option value=''>-选择"+(title?title:"")+"地市-</option>";
//					$.each(sysReguins, function (i, item){
//						strCity += "<option value='" +item.regId+"'>"+item.regName+ "</option>";
//						
//					});
//
//					$("#city").append(strCity);
//
//					var strReg = "<option value=''>-选择"+(title?title:"")+"区县-</option>";
//					$("#region").append(strReg);
//					//绑定联动事件 修改人zhujj
//					$("#city").change(function(){
//						$("#region").empty();
//						strReg = "<option value=''>-选择"+(title?title:"")+"区县-</option>";
//						if($("#city").val()!=""){
//							$.each(sysReguins, function (i, item){
//								if(item.regId==$("#city").val()){
//									$.each(item.child, function (j, itemchild){
//										strReg += "<option value='" + itemchild.regId+"'>"+itemchild.regName+ "</option>";
//									});
//								}
//							});
//						}
//						$("#region").append(strReg);
//					});
//					
//				}
//			}
//		},
//		error : function(data) {
//			alertModel('获取用户地区信息失败!');
//		}
//	});
//}
//
//function initCity() {
//	$("#city").html("");
//    $("#region").html("");
//    var str = "<option value=''>-选择地市-</option>";
//    $.ajax({
//        type: "GET",
//        url: "citys",
//        dataType: "JSON",
//        success: function (data) {
//        	cityList = data.Obj;
//            //从服务器获取数据进行绑定
//            $.each(data.Obj, function (i, item) {
//                str += "<option value=" + item.regId + ">" + item.regName + "</option>";
//            })
//            //将数据添加到省份这个下拉框里面
//            $("#city").append(str);
//            $("#region").append("<option value=''>-选择区县-</option>");
//        },
//        error: function () { alert("获取地市信息异常！"); }
//    });
//}
//function regionBind(){
//	var city = $("#city option:selected").val();
//    //判断市这个下拉框选中的值是否为空
//    if (city == "") {
//        return;
//    }
//    $("#region").html("");
//    var str = "<option value=''>-选择区县-</option>";
//    //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
//    $.ajax({
//        type: "GET",
//        url: "regions",
//        data: {"cityId": city},
//        dataType: "JSON",
//        success: function (data) {
//            //从服务器获取数据进行绑定
//            $.each(data.Obj, function (i, item) {
//                str += "<option value=" + item.regId + ">" + item.regName + "</option>";
//            });
//            //将数据添加到省份这个下拉框里面
//            $("#region").append(str);
//        },
//        error: function () { alert("获取区县信息异常！"); }
//    });
//}
//
//function pregIdBind(){
//    $("#pregId").html("");
//    $("#regId").html("");
//    var str = "<option>-请选择-</option>";
//    $.ajax({
//        type: "GET",
//        url: "citys",
//        dataType: "JSON",
//        success: function (data) {
//            //从服务器获取数据进行绑定
//            $.each(data.Obj, function (i, item) {
//                str += "<option value=" + item.regId + ">" + item.regName + "</option>";
//            })
//            //将数据添加到省份这个下拉框里面
//            $("#pregId").append(str);
//            $("#regId").append("<option>-请选择-</option>");
//        },
//        error: function () { alert("获取地市信息异常！"); }
//    });
//}
//function regIdBind(id){
//	var city = $("#pregId option:selected").val();
//    //判断市这个下拉框选中的值是否为空
//    if (city == "") {
//        return;
//    }
//    $("#regId").html("");
//    var str = "<option>-请选择-</option>";
//    //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
//    $.ajax({
//        type: "GET",
//        url: "regions",
//        data: {"cityId": city},
//        dataType: "JSON",
//        success: function (data) {
//            //从服务器获取数据进行绑定
//            $.each(data.Obj, function (i, item) {
//                str += "<option value=" + item.regId;
//                if(id != null && id == item.regId){
//                	str += " selected='selected'";
//                }
//                str += ">" + item.regName + "</option>";
//            });
//            //将数据添加到省份这个下拉框里面
//            $("#regId").append(str);
//        },
//        error: function () { alert("获取区县信息异常！"); }
//    });
//}

function gopage(i) {
	if (curPageNum == i)
		return;
	curPageNum = i;
	findMeter();
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
		url : projectName + "/asserts/tpl/common/region/getAddressElec",
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