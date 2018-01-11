var type = 1;// 1 添加，2 修改
$(function(){ 
	if(type==2){
		$('#Village option:selected').val(operatorRegId);
		$('#datetimepicker').val(datetimepicker);
		$('#debitName').val(debitName);
		$('#debitCost').val(debitCost);
		$('#debitProposer').val(debitProposer);
		$('#debitNote').val(debitNote);
	}
});	

function newsFormSubmit(){	
	var operatorRegId=$('#Village option:selected').val();
	var accountPeroid=$('#datetimepicker').val();
	var debitName=$('#debitName').val();
	var debitCost=$('#debitCost').val();
	var debitProposer=$('#debitProposer').val();
	var debitNote=$('#debitNote').val();
	
	if(type==1){
		$.ajax({
		    url:'xxxx',
		    data: {
		    	operatorRegId:operatorRegId,
		    	accountPeroid:accountPeroid,
		    	debitName:debitName,
		    	debitCost:debitCost,
		    	debitProposer:debitProposer,
		    	debitNote:debitNote
		    },
	 		type : 'post',
		    cache:false,
		    async:true,
		    success:function(result){
		    	console.log(result);
		        //请求成功时
		    	if(result!=null){
	    			alertModel(result.msg);
	    			loadTableData();
		    	}
		    	window.location.href="towerBilICreate.html";
		    },
		    error:function(){
		    	alertModel("请求失败！");
		    }
		});
	}
	else if(type==2){
		
		$.ajax({
		    url:'xxxx',
		    data: {
		    	operatorRegId:$('#Village option:selected').val(),
		    	accountPeroid:$('#datetimepicker').val(),
		    	debitName:$('#debitName').val(),
		    	debitCost:$('#debitCost').val(),
		    	debitProposer:$('#debitProposer').val(),
		    	debitNote:$('#debitNote').val()
		    },
	 		type : 'post',
		    cache:false,
		    async:true,
		    success:function(result){
		        //请求成功时
		    	if(result!=null){
	    			alertModel(result.msg);
	    			loadTableData();
		    	}
		    	window.location.href="towerBilICreate.html";
    			
		    },
		    error:function(){
				alertModel("请求失败！");
		    }
		});
	}	
}