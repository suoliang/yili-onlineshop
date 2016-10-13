/*全部订单*/
$(function(){
	/*  显示弹出窗  */
	$(".received_btn").click(function() {
		$('.public_modal_backup').fadeIn();
	});
	/*  取消按钮：隐藏弹出窗  */
	$(".modal_cancel").click(function() {
		$('.modal_confirm').removeClass("deleteOrder confirmReceipt");
		$('.public_modal_backup').fadeOut();
	});
	/*  确定按钮  */
});

/*删除订单*/
function delOrder(orderId){
	$("#orderOnlyMarker").val(orderId);
	$('.modal_confirm').addClass('deleteOrder');
	$('.modal_body').text("是否确定删除？");
	$('.public_modal_backup').fadeIn();
}
/*  删除操作的确定按钮  */
$(".deleteOrder").live("click",function() {
	$('.modal_confirm').removeClass('deleteOrder');
	$('.public_modal_backup').fadeOut();
	/*  do something  */
	var orderId = $("#orderOnlyMarker").val();
	$.ajax({
		type:"post",
		url: _ContextPath +"/order/deleteOrderById.do",
		async:false,
		data:"orderId=" + orderId,
		dataType:"json",
		success: function(data){
			if (data.responseCode == "0") {
				window.location.href = _ContextPath + "/membercenter/order.do?time="+new Date().getTime();
			}
		}
	});
});
	
/*  确认收货 */
function confirmOrder(orderCode){
	$("#orderOnlyMarker").val(orderCode);
	$('.modal_confirm').addClass('confirmReceipt');
	$('.modal_body').text("是否确定收货？");
	$('.public_modal_backup').fadeIn();
	
}
/*  确认收货的确定按钮  */
$(".confirmReceipt").live("click",function() {
	$('.public_modal_backup').fadeOut();
	$('.modal_confirm').removeClass('confirmReceipt');
	/*  do something  */
	var orderCode = $("#orderOnlyMarker").val();
	$.ajax({
		type:"post",
		url: _ContextPath +"/order/confirmReceipt.do",
		async:false,
		data:"orderCode=" + orderCode,
		dataType:"json",
		success:function(data){
			if (data.responseCode == "0") {
				window.location.href = _ContextPath + "/membercenter/order.do?time="+new Date().getTime();
			}
		}
	});
});

