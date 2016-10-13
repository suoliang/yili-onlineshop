/**
 * 
 */
package com.fushionbaby.app.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.fushionbaby.common.util.ConfigUtil;

/**
 * @description 分享的链接常量数据
 * @author 孟少博
 * @date 2015年8月10日下午1:45:06
 */
public class ShareConstantApp {
	/** 读取配置文件 image.properties */
	protected final static Logger log = LoggerFactory.getLogger(ConfigUtil.class);
	static {
		try {
			Properties properties = PropertiesLoaderUtils.loadAllProperties("evi.properties");
			UPLOAD_URL_ANDROID = properties.getProperty("title.url.android");
			UPLOAD_URL_IOS = properties.getProperty("title.url.ios");
			URL = properties.getProperty("title.url");
			TEXT = properties.getProperty("title.text");
			TITLE = properties.getProperty("title.content");
			FREE_FREIGHT = properties.getProperty("free.confirm");
		} catch (IOException e) {
			log.error("init evi.path error.", e);
		}
	}
	
	public static String UPLOAD_URL_ANDROID;
	public static String UPLOAD_URL_IOS;
	public static String URL;
	public static String TEXT;
	public static String TITLE;
	public static String FREE_FREIGHT;
	
}
