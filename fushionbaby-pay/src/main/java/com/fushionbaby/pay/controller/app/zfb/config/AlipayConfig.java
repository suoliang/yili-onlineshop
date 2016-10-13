package com.fushionbaby.pay.controller.app.zfb.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 */
public class AlipayConfig {

	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "";
	// 商户的私钥
	public static String private_key = "";
	// 支付宝的公钥
	public static String ali_public_key = "";

	public static String log_path = "D:\\";

	public static String input_charset = "utf-8";

	public static String sign_type = "RSA";

	public static String seller_id = "609086789@qq.com";

	public static String return_url = "http://m.alipay.com";

	private static Properties props = null;

	static {
		if (props == null) {
			InputStream is = AlipayConfig.class.getClassLoader().getResourceAsStream(
					"conf/app_zfb.properties");
			props = new Properties();
			try {
				props.load(is);
				partner = props.getProperty("partner");
				private_key = props.getProperty("private_key");
				ali_public_key = props.getProperty("ali_public_key");
				seller_id = props.getProperty("seller_id");
				return_url = props.getProperty("return_url");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
