package com.fushionbaby.common.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;


public class ConfigUtil {
	protected final static Logger log = LoggerFactory.getLogger(ConfigUtil.class);
	
	public ConfigUtil() {
	}
	
	private static Properties properties;
	
	static {
		try {
			properties = PropertiesLoaderUtils.loadAllProperties("config.properties");
		} catch (IOException e) {
			log.error("init ConfigUitl error.",e);
		}
	}
	
	
	public static String getSmsKey(){
		return properties.getProperty("sms.key");
	}
	
	public static String getSmsSoftwareSerialNo(){
		return properties.getProperty("sms.softwareSerialNo");
	}
	
	public static String getSmsUri(){
		return properties.getProperty("sms.uri");
	}
	
	
}
