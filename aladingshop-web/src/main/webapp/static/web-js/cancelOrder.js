function cancelOrder(orderCode){
	var url = contextPath + "/order/cancelOrder";
	$.post(url,{
		orderCode : orderCode,
		time : new Date().getTime()},
		function(data){
			if(data.responseCode==0){
				window.location.href=location.href+"&t="+new Date().getTime();
			}else{
				alert(data.msg);
			}
	});
}