package com.fushionbaby.common.constants;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class MemcachedConstant {

	protected static Logger log = LoggerFactory
			.getLogger(MemcachedConstant.class);
	public static Integer[] MEMCACHEDS_WEIGHTS;

	// 设置初始连接数、最小和最大连接数以及最大处理时间
	public static String MEMCACHEDS_SERVERURL;
	public static Integer MEMCACHEDS_INITCONN_NUMBER;
	public static Integer MEMCACHEDS_MINCONN_NUMBER;
	public static Integer MEMCACHEDS_MAXCONN_NUMBER;
	public static Integer MEMCACHEDS_MAXIDLE;
	// 设置主线程的睡眠时间
	public static Integer MEMCACHEDS_MAINTSLEEP_NUMBER;
	// 设置TCP的参数，连接超时等
	public static Integer MEMCACHEDS_SOCKETTO_PORT;
	public static boolean MEMCACHEDS_NAGLE;
	public static String MEMCACHEDS_DATA_POOL;
	public static String MEMCACHEDS_SESSION_POOL;

	public static Integer MEMCACHEDS_SCONNECTTO_NUMBER;
	static {
		try {
			Properties properties = PropertiesLoaderUtils
					.loadAllProperties("memcached.properties");
			MEMCACHEDS_SERVERURL = properties.getProperty("memcacheds.server");
			MEMCACHEDS_INITCONN_NUMBER = Integer.valueOf(properties
					.getProperty("memcacheds.initConn"));
			MEMCACHEDS_MINCONN_NUMBER = Integer.valueOf(properties
					.getProperty("memcacheds.minConn"));
			MEMCACHEDS_MAXCONN_NUMBER = Integer.valueOf(properties
					.getProperty("memcacheds.maxConn"));
			MEMCACHEDS_MAXIDLE = Integer.valueOf(properties
					.getProperty("memcacheds.maxidle"));

			MEMCACHEDS_MAINTSLEEP_NUMBER = Integer.valueOf(properties
					.getProperty("memcacheds.maintsleep"));
			MEMCACHEDS_SOCKETTO_PORT = Integer.valueOf(properties
					.getProperty("memcacheds.socketto"));
			MEMCACHEDS_NAGLE = Boolean.valueOf(properties
					.getProperty("memcacheds.nagle"));
			MEMCACHEDS_DATA_POOL = String.valueOf(properties
					.getProperty("memcacheds.data"));
			MEMCACHEDS_SESSION_POOL = String.valueOf(properties
					.getProperty("memcacheds.session"));

			MEMCACHEDS_SCONNECTTO_NUMBER = Integer.valueOf(properties
					.getProperty("memcacheds.sconnectto"));
			String temp[] = properties.getProperty("memcacheds.weights").split(
					",");
			MEMCACHEDS_WEIGHTS = new Integer[temp.length];
			int index = 0;
			for (String str : temp) {
				MEMCACHEDS_WEIGHTS[index++] = Integer.valueOf(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error("init image.path error.", e);
		}
	}

	public static void main(String[] args) {
		System.out.println(MEMCACHEDS_SERVERURL);
		System.out.println(MEMCACHEDS_INITCONN_NUMBER);
		System.out.println(MEMCACHEDS_MINCONN_NUMBER);
		System.out.println(MEMCACHEDS_MAXCONN_NUMBER);
		System.out.println(MEMCACHEDS_MAXIDLE);
		System.out.println(MEMCACHEDS_MAINTSLEEP_NUMBER);
		System.out.println(MEMCACHEDS_SOCKETTO_PORT);
		System.out.println(MEMCACHEDS_NAGLE);
		System.out.println(MEMCACHEDS_DATA_POOL);
		System.out.println(MEMCACHEDS_SESSION_POOL);
		System.out.println(MEMCACHEDS_SCONNECTTO_NUMBER);
		for (int i=0;i<MEMCACHEDS_WEIGHTS.length;i++) {
			System.out.print(MEMCACHEDS_WEIGHTS[i]+",");
		}
		
	}

}
