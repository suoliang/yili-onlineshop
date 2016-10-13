//我的订单
$(document).ready(function(){
	/***确定删除订单的'确定'按钮**/
	$("#pay_btn_enter").click(function(){
		//iframe跳转页面取值
		var iframeObj = $(window.frames["iframepage"].document);
		//用户点击的是不是待评价按钮
		var eval_type = iframeObj.find("#eval_type").val();
		
		if(eval_type == "04"){/***待评价 -- 删除订单的'确定'按钮操作**/
			
			var evaluateOrder_id = iframeObj.find("#evaluateOrder_id").val();
			$.ajax({
				type:"post",
				url: _ContextPath +"/order/deleteOrderById.do",
				async:false,
				data:"order_id=" + evaluateOrder_id,
				dataType:"json",
				success:function(data){
					if (data.responseCode == "0") {
						$(".de-order").hide();//弹出层的隐藏
						window.parent.document.getElementById("iframepage").src = _ContextPath + "/membercenter/evaluate.do?time="+new Date().getTime();
					}
				}
			});//ajax 结束
		} else {//全部订单 -- 删除
			var order_id = iframeObj.find("#order_code").val();
			$.ajax({
				type:"post",
				url: _ContextPath +"/order/deleteOrderById.do",
				async:false,
				data:"order_id=" + order_id,
				dataType:"json",
				success: function(data){
					if (data.responseCode == "0") {
						$(".de-order").hide();//弹出层的隐藏
						window.parent.document.getElementById("iframepage").src = _ContextPath + "/membercenter/order.do?time="+new Date().getTime();
					}
				}
			});
		}
		
	});
	
	/***'确认收货'按钮事件**/
	$("#confirm_getorder").click(function(){
		//iframe跳转页面取值
		var iframeObj = $(window.frames["iframepage"].document);
		//用户点击的是不是全部订单按钮
		var order_type = iframeObj.find("#order_type").val();
		if(order_type == "01"){//全部订单
			var order_code = iframeObj.find("#order_code").val();
			$.ajax({
				type:"post",
				url: _ContextPath +"/order/confirmReceipt.do",
				async:false,
				data:"order_code=" + order_code,
				dataType:"json",
				success:function(data){
					if (data.responseCode == "0") {
						window.parent.document.getElementById("iframepage").src = _ContextPath + "/membercenter/order.do?time="+new Date().getTime();
					}
				}
			});
		} else if(order_type == "03") {//待收货 -- 
			var order_code = iframeObj.find("#confirmOrder_id").val();
			$.ajax({
				type:"post",
				url: _ContextPath +"/order/confirmReceipt.do",
				async:false,
				data:"order_code=" + order_code,
				dataType:"json",
				success:function(data){
					if (data.responseCode == "0") {
						window.parent.document.getElementById("iframepage").src = _ContextPath + "/membercenter/receive.do?time="+new Date().getTime();
					}
				}
			});
		} else {//订单详情页 - 点击'确认收货'
			var order_code = iframeObj.find("#orderDetails_id").val();
			$.ajax({
				type:"post",
				url: _ContextPath +"/order/confirmReceipt.do",
				async:false,
				data:"order_code=" + order_code,
				dataType:"json",
				success:function(data){
					if (data.responseCode == "0") {
						window.parent.document.getElementById("iframepage").src = _ContextPath + "/order/orderDetail.do?order_code="+ order_code+"&time="+new Date().getTime();
					}
				}
			});
		}//if 结束
	});//ajax 结束
	
	/**会员取消订单*/
	$("#cancelOrder").click(function(){
		//iframe跳转页面取值
		var iframeObj = $(window.frames["iframepage"].document);
		//用户点击的是不是全部订单按钮
		var order_type = iframeObj.find("#order_type").val();
		var order_code = iframeObj.find("#order_code").val();
		if(order_type == "01"){//全部订单
			$.ajax({
				type:"post",
				url: _ContextPath +"/order/cancelOrder.do",
				async:false,
				data:"order_code=" + order_code,
				dataType:"json",
				success:function(data){
					if (data.responseCode == "0") {
						$(".pop-up-black",parent.document).fadeOut();
						window.parent.document.getElementById("iframepage").src = _ContextPath + "/membercenter/order.do?time="+new Date().getTime();
					}
				}
			});
		} else {//待付款
			$.ajax({
				type:"post",
				url: _ContextPath +"/order/cancelOrder.do",
				async:false,
				data:"order_code=" + order_code,
				dataType:"json",
				success:function(data){
					if (data.responseCode == "0") {
						$(".pop-up-black",parent.document).fadeOut();
						window.parent.document.getElementById("iframepage").src = _ContextPath + "/membercenter/obligation.do?time="+new Date().getTime();
					}
				}
			});
		}
	});
});
