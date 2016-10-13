$(function(){

    /*点击删除角色按钮，显示确认框*/
    $('.delete-role').click(function(){
        $('#modalDeleteRole').modal('show');
    })

})

function updateOrderStatus(root,msg,state, memberId,orderCode){
	    var result;
		var submit =  function(v,h,f){
  			if(v=="ok"){
  				$.ajax({
  					type : "get",
  					url : root+"/order/orderAuditSuccess",
  					dataType:"text",
  					data : {
  						state: state, 
  						memberId: memberId, 
  						orderCode: orderCode
  					},
  					success : function(data) {
  						result  = data;
  						
  						if(result == "success"){
  							location.reload();
  		  					jBox.tip("操作成功", 'info');
  		  				}else{
  		  					jBox.tip("操作失败", 'info');
  		  	  			}
  					},
  					error : function() {
  						alert("请求后台数据异常");
  					}
  				});
  				
  				
  			}
  			return true;
  		} 
		
		$.jBox.confirm(msg, "操作提示",submit);

}