package com.fushionbaby.pay.controller.app.zfb.model;

import com.fushionbaby.pay.controller.app.zfb.config.AlipayConfig;

public class AlipayModel {

	private String service = "mobile.securitypay.pay";
	private String partner = AlipayConfig.partner;
	private String _input_charset = "utf-8";
	private String sign = "";
	private String sign_type = "RSA";
	private String notify_url = "";
	private String app_id = "";
	private String appenv = "system=android^version=3.0.1.2";
	private String out_trade_no = "";
	private String subject = "";
	private String payment_type = "1";
	private String seller_id = "609086789@qq.com";
	private String total_fee = "";
	private String body = "";
	private String it_b_pay = "";
	private String paymethod = "";
	private String return_url = "http://m.alipay.com";

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getPartner() {
		return partner;
	}

	public String get_input_charset() {
		return _input_charset;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSign_type() {
		return sign_type;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getAppenv() {
		return appenv;
	}

	public void setAppenv(String appenv) {
		this.appenv = appenv;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getIt_b_pay() {
		return it_b_pay;
	}

	public void setIt_b_pay(String it_b_pay) {
		this.it_b_pay = it_b_pay;
	}

	public String getPaymethod() {
		return paymethod;
	}

	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}

	public String getReturn_url() {
		return return_url;
	}

	// /**
	// * create the order info
	// */
	// public String getOrderInfo(String subject, String body, String price) {
	// //合作者身份ID
	// String orderInfo = "partner=" + "\"" + AlipayConfig.partner + "\"";
	// //卖家支付宝账号
	// orderInfo += "&seller_id=\"dream.shen@fushionbaby.com\"";
	// //商户网站唯一订单号
	// //orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";
	// //商品名称
	// orderInfo += "&subject=" + "\"" + subject + "\"";
	// //商品详情
	// orderInfo += "&body=" + "\"" + body + "\"";
	// //商品金额
	// orderInfo += "&total_fee=" + "\"" + price + "\"";
	// //服务器异步通知页面路径
	// orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"+
	// "\"";
	// //接口名称， 固定值
	// orderInfo += "&service=\"mobile.securitypay.pay\"";
	// //支付类型， 固定值(默认商品购买)
	// orderInfo += "&payment_type=\"1\"";
	// //参数编码， 固定值
	// orderInfo += "&_input_charset=\"utf-8\"";
	// //设置未付款交易的超时时间
	// //默认30分钟，一旦超时，该笔交易就会自动被关闭。
	// //取值范围：1m～15d。
	// //m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
	// //该参数数值不接受小数点，如1.5h，可转换为90m。
	// orderInfo += "&it_b_pay=\"30m\"";
	// //支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
	// orderInfo += "&return_url=\"m.alipay.com\"";
	// //调用银行卡支付，需配置此参数，参与签名， 固定值
	// //orderInfo += "&paymethod=\"expressGateway\"";
	// return orderInfo;
	// }
}
