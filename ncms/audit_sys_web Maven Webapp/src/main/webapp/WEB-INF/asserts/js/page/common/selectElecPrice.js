var pageType = "0"; //0:非平峰谷   1:平峰谷
var priceNum = 1;
var sitelist = null;
var siteData = null;
$(document).ready(function() {
	initSelectElecPrice();
	
	
});
/**
 * 初始化加页面
 */
function initSelectElecPrice() {
	elecPriceBind(pageType);
	$("#selectElecPrice").load("../../common/selectElecPrice.html");
	$("#selectElecPrice").css({position:'relative',zIndex:'999998'});
	
}

/**
 * 弹出电费单价录入窗口
 */
function elecPriceBind(type) {
	if(type == "1"){
		$('#ElecPricePanel1').modal(); // 弹出平峰谷单价表单
		getPunishCausePrice2();
	}else{
		$('#ElecPricePanel').modal(); // 弹出平峰谷单价表单
		getPunishCausePrice1();
	}
	
}
function getPunishCausePrice2(){
	$("#flatPriceSet").keyup(function () {
	    var reg = $(this).val().match(/\d+\.?\d{0,6}/);
	    var txt = '';
	    if (reg != null) {
	        txt = reg[0];
	    }
	    $(this).val(txt);
	}).change(function () {
	    $(this).keypress();
	    var v = $(this).val();
	    if (/\.$/.test(v))
	    {
	        $(this).val(v.substr(0, v.length - 1));
	    }
	});
	$("#peakPriceSet").keyup(function () {
	    var reg = $(this).val().match(/\d+\.?\d{0,6}/);
	    var txt = '';
	    if (reg != null) {
	        txt = reg[0];
	    }
	    $(this).val(txt);
	}).change(function () {
	    $(this).keypress();
	    var v = $(this).val();
	    if (/\.$/.test(v))
	    {
	        $(this).val(v.substr(0, v.length - 1));
	    }
	});
	$("#valleyPriceSet").keyup(function () {
	    var reg = $(this).val().match(/\d+\.?\d{0,6}/);
	    var txt = '';
	    if (reg != null) {
	        txt = reg[0];
	    }
	    $(this).val(txt);
	}).change(function () {
	    $(this).keypress();
	    var v = $(this).val();
	    if (/\.$/.test(v))
	    {
	        $(this).val(v.substr(0, v.length - 1));
	    }
	});
	$("#TopPrice").keyup(function () {
	    var reg = $(this).val().match(/\d+\.?\d{0,6}/);
	    var txt = '';
	    if (reg != null) {
	        txt = reg[0];
	    }
	    $(this).val(txt);
	}).change(function () {
	    $(this).keypress();
	    var v = $(this).val();
	    if (/\.$/.test(v))
	    {
	        $(this).val(v.substr(0, v.length - 1));
	    }
	});
}


function getPunishCausePrice1(){
	$("#price1").keyup(function () {
	    var reg = $(this).val().match(/\d+\.?\d{0,6}/);
	    var txt = '';
	    if (reg != null) {
	        txt = reg[0];
	    }
	    $(this).val(txt);
	}).change(function () {
	    $(this).keypress();
	    var v = $(this).val();
	    if (/\.$/.test(v))
	    {
	        $(this).val(v.substr(0, v.length - 1));
	    }
	});
}
//提交平峰谷单价
function submit1(){
	
	if($("#flatPriceSet").val()==''&&$("#peakPriceSet").val()==''
		&&$("#valleyPriceSet").val()==''&&$("#TopPrice").val()==''){
		$('#err1').show();
		$('#err2').hide();
	}else{
		$('#err1').hide();
		$('#err2').show();
		var price = $("#flatPriceSet").val()+"|"+$("#peakPriceSet").val()+"|"
		           +$("#valleyPriceSet").val()+"|"+$("#TopPrice").val();
		backElecPrice(price);
		$("#ElecPricePanel1").modal('hide');
	}
}
function blurF(obj){
	var curId=$(obj).attr('id');
if(curId=='flatPriceSet'){
	if($(obj).val()!==''||$("#peakPriceSet").val()!==''||$("#valleyPriceSet").val()!==''||$('#ElecPriceForm1 #TopPrice').val()!==''){
		$('#err1').hide();
		$('#err2').show();
	}else if($(obj).val()==''&&$("#peakPriceSet").val()!==''||$(obj).val()==''&&$("#valleyPriceSet").val()!==''||$(obj).val()==''&&$('#ElecPriceForm1 #TopPrice').val()!==''){
		$('#err1').hide();
		$('#err2').show();
	}else if($(obj).val()==''&&$("#peakPriceSet").val()==''&&$("#valleyPriceSet").val()==''&&$('#ElecPriceForm1 #TopPrice').val()==''){
		$('#err1').show();
		$('#err2').hide();
	}
	
}

	if(curId=='peakPriceSet'){
	if($(obj).val()!==''||$("#flatPriceSet").val()!==''||$("#valleyPriceSet").val()!==''||$('#ElecPriceForm1 #TopPrice').val()!==''){
		$('#err1').hide();
		$('#err2').show();
	}else if($(obj).val()==''&&$("#flatPriceSet").val()!==''||$(obj).val()==''&&$("#valleyPriceSet").val()!==''||$(obj).val()==''&&$('#ElecPriceForm1 #TopPrice').val()!==''){
		$('#err1').hide();
		$('#err2').show();
	}else if($(obj).val()==''&&$("#flatPriceSet").val()==''&&$("#valleyPriceSet").val()==''&&$('#ElecPriceForm1 #TopPrice').val()==''){
		$('#err1').show();
		$('#err2').hide();
	}
	
}

	if(curId=='valleyPriceSet'){
	if($(obj).val()!==''||$("#peakPriceSet").val()!==''||$("#flatPriceSet").val()!==''||$('#ElecPriceForm1 #TopPrice').val()!==''){
		$('#err1').hide();
		$('#err2').show();
	}else if($(obj).val()==''&&$("#peakPriceSet").val()!==''||$(obj).val()==''&&$("#flatPriceSet").val()!==''||$(obj).val()==''&&$('#ElecPriceForm1 #TopPrice').val()!==''){
		$('#err1').hide();
		$('#err2').show()
	}else if($(obj).val()==''&&$("#peakPriceSet").val()==''&&$("#valleyPriceSet").val()==''&&$('#ElecPriceForm1 #TopPrice').val()==''){
		$('#err1').show();
		$('#err2').hide();
	}
	
}
	
	if(curId=='TopPrice'){
		if($(obj).val()!==''||$("#peakPriceSet").val()!==''||$("#flatPriceSet").val()!==''||$("#valleyPriceSet").val()!==''){
			$('#err1').hide();
			$('#err2').show();
		}else if($(obj).val()==''&&$("#peakPriceSet").val()!==''||$(obj).val()==''&&$("#flatPriceSet").val()!==''||$(obj).val()==''&&$("#valleyPriceSet").val()!==''){
			$('#err1').hide();
			$('#err2').show();
		}else if($(obj).val()==''&&$("#peakPriceSet").val()==''&&$("#valleyPriceSet").val()==''&&$("#valleyPriceSet").val()==''){
			$('#err1').show();
			$('#err2').hide();
		}
		
	}
}
function elecPriceF(){
	var ele_validate= bindformvalidate("#ElecPriceForm", {
		price1 : {
			required : true,
		}
	}, {
		price1:{
			required : "请输入电费单价",
			
		}
	});
  return ele_validate;
}

function closeM(){
	$("#ElecPriceForm1")[0].reset(); // 清空表单
	$("#ElecPriceForm")[0].reset(); // 清空表单
	$("#ElecPricePanel1").modal('hide');
	$("#ElecPricePanel").modal('hide');
}
//提交非平峰谷单价
function submit(){

	
	if(elecPriceF().form()){
//	执行操作
		var price = "";
		$("#ElecPriceTb").find("tr").each(function(){
			var tdArr = $(this).children();
			var tdStr = tdArr.eq(1).find("input").val();
			if(tdStr != "" && tdStr != undefined){
				price += tdStr + "|"
			}
		});
		price = price.substr(0,price.length-1);
		backElecPrice(price);
		$("#ElecPricePanel").modal('hide');
	}
}

function add(){
	priceNum++;
	var td = "<tr><td>电费单价"+priceNum+"</td>" +
			"<td><input class='form-control' type='text' style='float:left;'/><span style='float:left;margin-left:8px;'>元</span></td>" +
			"<td><a href='#' class='btn' onclick='remove(this)'>删除</a></td></tr>";
	$("#ElecPriceTb").append(td); 
}

function remove(t){
	if($("#ElecPriceTb").find("tr").length <= 2){
		return;
	}
	$("#ElecPriceTb tr:last").remove();
	priceNum--;
}

/*function closes(){
	$("#ElecPriceForm1")[0].reset(); // 清空表单
	$("#ElecPriceForm")[0].reset(); // 清空表单
}*/