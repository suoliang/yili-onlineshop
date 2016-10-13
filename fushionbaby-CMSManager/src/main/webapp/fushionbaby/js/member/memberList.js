function updateMemberDisabled(root,msg,memberId,disabled){
	    var result;
		var submit =  function(v,h,f){
  			if(v=="ok"){
  				$.ajax({
  					type : "get",
  					url : root+"/member/changeMemberDisable",
  					dataType:"text",
  					data : {
  						memberId: memberId, 
  						disabled: disabled
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