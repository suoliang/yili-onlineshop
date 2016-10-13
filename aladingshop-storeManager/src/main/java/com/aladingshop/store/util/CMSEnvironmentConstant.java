package com.aladingshop.store.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.fushionbaby.common.util.ConfigUtil;

/***
 * 读取后台的 envi.properties配置文件
 * @author xupeijun
 *
 */
public class CMSEnvironmentConstant {
	protected final static Logger logg = LoggerFactory.getLogger(ConfigUtil.class);
	static {
		try {
			Properties properties = PropertiesLoaderUtils.loadAllProperties("envi.properties");
			/**系统运行环境*/
			ENVIRONMENT = properties.getProperty("system_environment");
		} catch (IOException e) {
			logg.error("init image.path error.", e);
		}

	}
	/** 系统运行环境,用于区分是开发环境还是发布环境 */
	public static String ENVIRONMENT;
	
	public static void main(String[] args) {
		
		System.out.println(ENVIRONMENT);
	}
}
