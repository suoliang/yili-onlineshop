/**
 * 
 */
package com.aladingshop.wap.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description 分享的链接常量数据
 * @author 孟少博
 * @date 2015年8月10日下午1:45:06
 */
public class ShareConstantWap {
	/** 读取配置文件 image.properties */
	protected final static Logger log = LoggerFactory
			.getLogger(ShareConstantWap.class);
	static {
		try {
			InputStream fis = ShareConstantWap.class.getClassLoader()
					.getResourceAsStream("conf/envi.properties");
			PropertyResourceBundle properties = new PropertyResourceBundle(fis);
			URL = properties.getString("title.url");
			TEXT = properties.getString("title.text");
			TITLE = properties.getString("title.content");
			FREE_FREIGHT = properties.getString("free.confirm");
			SHARECONTENT = properties.getString("share.content");
		} catch (IOException e) {
			log.error("init evi.path error.", e);
		}
	}

	public static String URL;
	public static String TEXT;
	public static String TITLE;
	public static String FREE_FREIGHT;
	public static String SHARECONTENT;
}
