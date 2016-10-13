package com.aladingshop.web.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.fushionbaby.common.util.ConfigUtil;

/***
 * 读取web的 envi.properties
 * 
 * @author xupeijun
 *
 */
public class EnvironmentConstant {
	protected final static Logger logg = LoggerFactory.getLogger(ConfigUtil.class);
	static {
		try {
			Properties properties = PropertiesLoaderUtils.loadAllProperties("conf/envi.properties");
			/** 发布版本 */
			DEPLOY_VERSION = properties.getProperty("deploy_version");
		} catch (IOException e) {
			logg.error("init image.path error.", e);
		}

	}
	/** 发布版本 */
	public static String DEPLOY_VERSION;

	public static void main(String[] args) {
		System.err.println(DEPLOY_VERSION);
	}
}
