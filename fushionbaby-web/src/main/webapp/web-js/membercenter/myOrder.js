//我的订单
function gotoAllOrder(){
	window.location.href=_ContextPath+"/membercenter/order.do?time="+new Date().getTime();
}
function gotoObligation(){
	window.location.href=_ContextPath+"/membercenter/obligation.do?time="+new Date().getTime();
}
function gotoReceive(){
	window.location.href=_ContextPath+"/membercenter/receive.do?time="+new Date().getTime();
}
function gotoEvaluate(){
	window.location.href=_ContextPath+"/membercenter/evaluate.do?time="+new Date().getTime();
}