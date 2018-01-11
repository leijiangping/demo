/**
 * 服务拓扑 JS
 */
var curNode = null;
//创建产品top
function createProductData(scene,data){
	var pro = data.data;
	for(var p=0;p<pro.length;p++){
		var proNode = newNode(scene,50+p%10*120,100+parseInt(p/10)*150,null,null,pro[p].name,1,"",null,null,pro[p].alarm,211);
	}
}
//创建服务top
function createServiceData(scene,data){
	if(data == null){
		return;
	}
	
	for(var i=0;i<data.length;i++){
		var centerNode = newNode(scene,500,150,null,null,data[i].NAME,1,"tree",180,100,null,null);
		scene.add(centerNode);
		if(data[i].children){
			diguiShow(centerNode , data[i].children);
		}
	}
	
	JTopo.layout.layoutNode(scene, centerNode, true);
    scene.addEventListener('mouseup', function(e){
		if(e.target && e.target.layout){
			JTopo.layout.layoutNode(scene, e.target, true);	
		}				
	});
}

function diguiShow(centerNode , data){
	for(var i=0;i<data.length;i++){
		var childNode = newNode(scene,scene.width*Math.random(),scene.height*Math.random(),null,null,data[i].NAME,1,"tree",100,100,null,null);
		scene.add(newLink(centerNode,childNode,''));
		if(data[i].children){
			diguiShow(childNode , data[i].children);
		}
	}
}

function initRightEvent(scene,curNode){
	$("#rightMenu a").click(function(){
		var text = $(this).text();
		if(text == '删除'){
			scene.remove(curNode);
			curNode = null;
		}
		if(text == '放大'){
			curNode.scaleX += 0.2;
			curNode.scaleY += 0.2;
		}else if(text == '缩小'){
			curNode.scaleX -= 0.2;
			curNode.scaleY -= 0.2;
		}
		$("#rightMenu").hide();
	});
}
//右键事件
function rClickHandler(node,event){
	if(event.button == 2){
		$("#rightMenu").css({
			top: event.pageY,
			left: event.pageX
		});
		$("#rightMenu").show();
	}
}
//双击事件
function dbClickHandler(layLevel,node,scene,event){
	if(layLevel == 211){
		window.location.href="serviceTop.jsp";
	}
}