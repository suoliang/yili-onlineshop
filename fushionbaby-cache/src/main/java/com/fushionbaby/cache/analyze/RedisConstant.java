package com.fushionbaby.cache.analyze;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fushionbaby.cache.util.PropertiesUitls;

/***
 * 
 * @author King 索亮
 *
 */
public class RedisConstant {
	/** 读取配置文件 redis.properties */
	protected final static Logger log = LoggerFactory.getLogger(RedisConstant.class);
	
	private static Properties props = null;
	public static String REDIS_SERVER;
	public static Integer REDIS_PORT;
	public static String REDIS_MASTER;
	public static String REDIS_DELKEY;
	public static Integer REDIS_MAXACTIVE;
	public static Integer REDIS_MAXIDLE;
	public static Long REDIS_MAXWAIT;
	public static Boolean REDIS_TESTONBORROW;
	
	static{		
		if(props ==null) {
			InputStream is = PropertiesUitls.class.getClassLoader().getResourceAsStream("conf/redis.properties");
			props = new Properties();
			try {
				props.load(is);
				REDIS_SERVER = props.getProperty("redis.server");
				REDIS_PORT = Integer.valueOf(props.getProperty("redis.port"));
				REDIS_MASTER = props.getProperty("redis.master");
				REDIS_DELKEY = props.getProperty("redis.delKey");
				REDIS_MAXACTIVE = Integer.valueOf(props.getProperty("redis.MaxActive"));
				REDIS_MAXIDLE = Integer.valueOf(props.getProperty("redis.MaxIdle"));
				REDIS_MAXWAIT = Long.valueOf(props.getProperty("redis.MaxWait"));
				REDIS_TESTONBORROW = Boolean.valueOf(props.getProperty("redis.TestOnBorrow"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

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
