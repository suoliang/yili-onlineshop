package com.fushionbaby.pay.controller.web.union.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/***
 * @description WEB银联支付配置
 * @author 索亮
 * @date 2015年8月24日下午5:18:46
 */
public class WebUnionConfig {
	// WEB银联 商户号码，请改成自己的商户号
	public static String merId = "";

	private static Properties props = null;

	static {
		if (props == null) {
			InputStream is = WebUnionConfig.class.getClassLoader().getResourceAsStream(
					"conf/web_union.properties");
			props = new Properties();
			try {
				props.load(is);
				merId = props.getProperty("merId");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
