package com.fushionbaby.schedule.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.fushionbaby.common.constants.WebConstant;

public class DomainConstant {
	/** 读取配置文件 domain.properties */
	protected final static Logger log = LoggerFactory.getLogger(WebConstant.class);
	static{
		try {
			Properties properties = PropertiesLoaderUtils.loadAllProperties("conf/domain.properties");
			DOMAIN_PATH = properties.getProperty("domain.path");
			INDEX_ACTION_URL = properties.getProperty("indexUrl.path");
		} catch (IOException e) {
			log.error("init domain.path error.", e);
		}
		
	}
	/** 项目目录*/
	public static String DOMAIN_PATH;
	/** 首页激活地址*/
	public static String INDEX_ACTION_URL;
	
	public static void main(String[] args) {
		System.out.println(DOMAIN_PATH);
		System.out.println(INDEX_ACTION_URL);
	}
	
}
