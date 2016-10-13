package com.fushionbaby.app.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.fushionbaby.common.util.ConfigUtil;
/**
 * 
 * @description 红包转入如意消费卡
 * @author 孟少博
 * @date 2015年11月2日下午3:04:54
 */
public class RedToAlbConstantApp {
	/** 读取配置文件 image.properties */
	protected final static Logger log = LoggerFactory.getLogger(ConfigUtil.class);
	static {
		try {
			Properties properties = PropertiesLoaderUtils.loadAllProperties("redToAlb.properties");
			RULE_CONTENT = properties.getProperty("rule.content");
		} catch (IOException e) {
			log.error("init evi.path error.", e);
		}
	}
	
	public static String RULE_CONTENT;

}
