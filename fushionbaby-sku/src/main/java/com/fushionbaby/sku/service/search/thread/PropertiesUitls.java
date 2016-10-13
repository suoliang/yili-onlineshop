package com.fushionbaby.sku.service.search.thread;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

/**
 * Properties文件帮助
 * 
 * @author admin
 * @version 1.0
 */
public class PropertiesUitls {

	private static Properties props = null;

	static {
		if (props == null) {
			InputStream is = PropertiesUitls.class
					.getResourceAsStream("/solr.properties");
			props = new Properties();
			try {
				props.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getValByKey(String key) {
		return props.getProperty(key);
	}
}
