package com.fushionbaby.pay.controller.web.wx.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @description WEB微信支付配置
 * @author 索亮
 * @date 2015年8月27日下午3:10:59
 */
public class WebWxConfig {
	/** WEB微信APPID,公众账号ID */
	public static String appId = "";
	/** WEB微信商户号码，请改成自己的商户号 */
	public static String mchId = "";
	/** WEB微信生成签名key值 */
	public static String apiKey = "";
	/** WEB微信密钥 */
	public static String appSecret = "";
	/** WEB微信支付统一下单请求URL地址 */
	public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	private static Properties props = null;

	static {
		if (props == null) {
			InputStream is = WebWxConfig.class.getClassLoader().getResourceAsStream(
					"conf/web_wx.properties");
			props = new Properties();
			try {
				props.load(is);
				appId = props.getProperty("appId");
				mchId = props.getProperty("mchId");
				apiKey = props.getProperty("apiKey");
				appSecret = props.getProperty("appSecret");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
