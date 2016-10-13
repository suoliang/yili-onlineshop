/**
 * 
 */
package com.fushionbaby.pay.controller.app.wx;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import com.fushionbaby.pay.controller.util.HttpRequest;

/**
 * @description 类描述...
 * @author 索亮
 * @date 2015年8月12日下午5:20:03
 */
public class Test {
	
	public Map<String,Object> toMap(){
        Map<String,Object> map = new HashMap<String, Object>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            Object obj;
            try {
                obj = field.get(this);
                if(obj!=null){
                    map.put(field.getName(), obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

	
	public static void main(String[] args) {
		new Test().aaaa();
		new Test().bbbbb();
		String a = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", 
			"<xml>"
			   +"<appid>wx58cd00ca05fec2cf</appid>"
			   +"<attach>支付测试</attach>"
			   +"<body>阿拉丁订单号:1606400</body>"
			   +"<mch_id>1254479001</mch_id>"
			   +"<nonce_str>9c693b040f150014937c0072d90c00db</nonce_str>"
			   +"<notify_url>http://pay2.aladingshop.com/appwx/notify.do</notify_url>"
			   +"<out_trade_no>20150810182026511141</out_trade_no>"
			   +"<spbill_create_ip>112.65.201.60</spbill_create_ip>"
			   +"<total_fee>1</total_fee>"
			   +"<trade_type>APP</trade_type>"
			   +"<sign>CF1BC14A12DC32A75C63E99A976A58AA</sign>"
			+"</xml>"
				);
		System.out.println(a);
		String cc = "29sM05vF2o4as9q83432bF2Lqs3wX48s";
		System.out.println(cc.length());
	}
	/** 官网测试 */
	private void aaaa() {
		String a = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", 
		"<xml>"+
		   "<appid>wx2421b1c4370ec43b</appid>"+
		   "<attach>支付测试</attach>"
		   +"<body>JSAPI支付测试</body>"
		   +"<mch_id>10000100</mch_id>"
		   +"<nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>"
		   +"<notify_url>http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php</notify_url>"
		   +"<openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid>"
		   +"<out_trade_no>1415659990</out_trade_no>"
		   +"<spbill_create_ip>14.23.150.211</spbill_create_ip>"
		   +"<total_fee>1</total_fee>"
		   +"<trade_type>JSAPI</trade_type>"
		   +"<sign>0CB01533B8C1EF103065174F50BCA001</sign>"
		   +"</xml>");
		System.out.println(a);
	}
	/** web测试 */
	private void bbbbb() {
		String a = HttpRequest.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", 
				"<xml>"
					+"<appid>wx716f382875f56552</appid>"
					+"<attach>支付测试</attach>"
					+"<body>阿拉丁订单号:1606400</body>"
					+"<mch_id>1252740101</mch_id>"
					+"<nonce_str>9c693b040f150014937c0072d90c00db</nonce_str>"
					+"<notify_url>http://pay2.aladingshop.com/appwx/notify.do</notify_url>"
					+"<out_trade_no>20150810182026511141</out_trade_no>"
					+"<spbill_create_ip>112.65.201.60</spbill_create_ip>"
					+"<total_fee>1</total_fee>"
					+"<product_id>2588466666555</product_id>"
					+"<trade_type>NATIVE</trade_type>"
					+"<sign>2BEFB7B677695E210D40F20623F39176</sign>"
				+"</xml>");
		System.out.println(a);
	}
}
