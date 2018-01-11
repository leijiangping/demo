/**
 * 基础拓扑 JS
 */
var nodeWidth = 30;
var nodeHeight = 30;
var bgImg = "";
var proImg = "img/node/pac_37.png";
var cloudImg = "img/node/cloud.png";
var instanceImg = "img/node/container.jpg";
var roomImg = "img/node/room_blue.png";
var provinceImg = "img/node/province.png";
var hostImg = "img/node/b_device.png";

function initCanvas(){
	return document.getElementById('canvas');
}
function initStage(canvas,needEye){
	stage = new JTopo.Stage(canvas);
	if(needEye){
		stage.eagleEye.visible = true;
	}
 	stage.click(function(event){
		if(event.button == 0){//左键
			// 关闭弹出菜单（div）
			$("#rightMenu").hide();
		}
	});
 	return stage;
}
function addToolbar(stage){
	showJTopoToobar(stage);
}
function initScene(stage){
	scene = new JTopo.Scene(stage);
    scene.background = bgImg;
    return scene;
}
//创建节点		坐标x, 坐标y, 宽w, 高h, 名称text, 图标类型, 布局类型type, 布局尺度laySize,告警级别alarm,层级layLevel
function newNode(scene, x, y, w, h, text, img, type, laySize,layheight, alarm,layLevel){
	var node = new JTopo.Node();
	node.text = text;
	//node.textOffsetY = -37; // 文本偏移量（向上37像素） 
	node.fillColor = '0,133,208'; // 填充颜色 
	node.font = ' bold  18px/1.5em  "宋体",Arial,sans-serif';
	node.fontColor = '0,133,208';
	if(type == "circle"){
		node.layout = {type: 'circle', radius: laySize};
	}else if(type == "circleNode" && laySize != null){
		node.layout = {type: 'circle', radius: laySize};
	}else if(type == "tree"){
		node.layout = {type: type, width: laySize, height: layheight};
	}else{
		
	}
	if(alarm != null && alarm != "0"){
		setAlarm(node,alarm);
	}
	node.setLocation(x, y);
	node.setSize(w==null?nodeWidth:w, h==null?nodeHeight:h);
//	node.setImage(getImg(img));
	if(layLevel != null){
		node.addEventListener('mouseup',function(event){
			curNode = this;
			rClickHandler(this,event);
		});
		node.addEventListener('dbclick',function(event){
	    	dbClickHandler(layLevel,this,scene,event);
	    });
	}
	scene.add(node);
	return node;
}
//简单连线
function newLink(nodeA, nodeZ, text, color, dashedPattern){
	var link = new JTopo.FoldLink(nodeA, nodeZ, text);		
	link.lineWidth = 1; // 线宽
	link.dashedPattern = dashedPattern; // 虚线
	link.arrowsRadius = 5; //箭头大小
	link.bundleOffset = 60; // 折线拐角处的长度
	link.bundleGap = 20; // 线条之间的间隔
	link.textOffsetY = 3; //文本偏移量（向下3个像素）
	link.strokeColor = color;//'0,200,255';
	scene.add(link);
	return link;
}
function setAlarm(node,alarm){
	if(alarm == "1"){
		node.alarm = "紧急告警";
		node.alarmColor = "255,0,0"
		node.alarmAlpha = 0.9;
	}else if(alarm == "2"){
		node.alarm = "重要告警";
		node.alarmColor = "255,0,100"
		node.alarmAlpha = 0.9;
	}else if(alarm == "3"){
		node.alarm = "次要告警";
		node.alarmColor = "255,100,0"
		node.alarmAlpha = 0.9;
	}else if(alarm == "4"){
		node.alarm = "一般告警";
		node.alarmColor = "255,150,150"
		node.alarmAlpha = 0.9;
	}else{
		
	}
}
function getImg(img){
	var url = "";
	if(img == 0){
		url = proImg;
	}else if(img == 1){
		url = cloudImg;
	}else if(img == 2){
		url = roomImg;
	}else if(img == 3){
		url = instanceImg;
	}else if(img == 10){
		url = provinceImg;
	}else if(img == 11){
		url = roomImg;
	}else if(img == 12){
		url = cloudImg;
	}else if(img == 13){
		url = hostImg;
	}
	return url;
}