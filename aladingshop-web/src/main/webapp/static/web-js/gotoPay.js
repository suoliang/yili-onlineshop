/**去付款*/
function gotoPay(sid,payWay,orderCode){
	var temp = document.createElement("form");
	temp.action = contextPath + "/order/goPay";
	temp.method = "post";
	temp.style.display = "none";
	
	var optSid = document.createElement("input");
	optSid.name="sid";
	optSid.value=sid;
	temp.appendChild(optSid);
	
	var optPayWay = document.createElement("input");
	optPayWay.name="payWay";
	optPayWay.value=payWay;
	temp.appendChild(optPayWay);
	
	var optOrderCode = document.createElement("input");
	optOrderCode.name="orderCode";
	optOrderCode.value=orderCode;
	temp.appendChild(optOrderCode);
	
	document.body.appendChild(temp);
	temp.submit();
}