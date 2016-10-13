package com.fushionbaby.common.constants;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.fushionbaby.common.util.ConfigUtil;

/***
 * 
 * 
 * @author  cyla
 * 
 */
public class RedisConstant {
	/** 读取配置文件 image.properties*/
	protected final static Logger log = LoggerFactory
			.getLogger(ConfigUtil.class);
	static {
		try {
			Properties properties = PropertiesLoaderUtils
					.loadAllProperties("redis.properties");
			REDIS_SERVER = properties.getProperty("redis.server");
			REDIS_PORT = Integer.valueOf(properties.getProperty("redis.port"));
			REDIS_MASTER = properties.getProperty("redis.master");
			REDIS_DELKEY = properties.getProperty("redis.delKey");
			REDIS_MAXACTIVE = Integer.valueOf(properties.getProperty("redis.MaxActive"));
			REDIS_MAXIDLE = Integer.valueOf(properties.getProperty("redis.MaxIdle"));
			REDIS_MAXWAIT = Long.valueOf(properties.getProperty("redis.MaxWait"));
			REDIS_TESTONBORROW = Boolean.valueOf(properties.getProperty("redis.TestOnBorrow"));
		} catch (IOException e) {
			e.printStackTrace();
			log.error("init image.path error.", e);
		}
	}
	public static String REDIS_SERVER;
	public static Integer REDIS_PORT;
	public static String REDIS_MASTER;
	public static String REDIS_DELKEY;
	public static Integer REDIS_MAXACTIVE;
	public static Integer REDIS_MAXIDLE;
	public static Long REDIS_MAXWAIT;
	public static Boolean REDIS_TESTONBORROW;
	
	public static void main(String[] args) {
	
		System.out.println(REDIS_SERVER);
		System.out.println(REDIS_PORT);
		System.out.println(REDIS_MASTER);
		System.out.println(REDIS_DELKEY);
		System.out.println(REDIS_MAXACTIVE);
		System.out.println(REDIS_MAXIDLE);
		System.out.println(REDIS_MAXWAIT);
		System.out.println(REDIS_TESTONBORROW);
	}

}
