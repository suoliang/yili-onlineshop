package com.fushionbaby.cache;

import com.fushionbaby.cache.analyze.MemcachedConstant;
import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

public class MemcachedProxyBySession {

	private static MemCachedClient mcc;
	// 设置服务器信息
	private String serverIP = MemcachedConstant.MEMCACHEDS_SERVERURL;
	Integer[] weights = MemcachedConstant.MEMCACHEDS_WEIGHTS;
	// 设置初始连接数、最小和最大连接数以及最大处理时间
	private Integer initConn = MemcachedConstant.MEMCACHEDS_INITCONN_NUMBER;
	private Integer minConn = MemcachedConstant.MEMCACHEDS_MINCONN_NUMBER;
	private Integer maxConn = MemcachedConstant.MEMCACHEDS_MAXCONN_NUMBER;
	private Integer maxIdle = MemcachedConstant.MEMCACHEDS_MAXIDLE;
	// 设置主线程的睡眠时间
	private Integer maintSleep = MemcachedConstant.MEMCACHEDS_MAINTSLEEP_NUMBER;
	// 设置TCP的参数，连接超时等
	private boolean nagle = MemcachedConstant.MEMCACHEDS_NAGLE;
	private Integer socketTO = MemcachedConstant.MEMCACHEDS_SOCKETTO_PORT;

	private static String SESSION_POOL = MemcachedConstant.MEMCACHEDS_SESSION_POOL;

	public void init() {
		
		// 192.168.8.33:11211,192.168.8.33:11212,192.168.8.33:11213
		String[] servers = serverIP.split(",");

		SockIOPool pool = SockIOPool.getInstance(SESSION_POOL);

		// 向连接池设置服务器和权重
		pool.setServers(servers);
		// 设置服务器权重
		pool.setWeights(weights);

		pool.setInitConn(initConn);
		pool.setMinConn(minConn);
		pool.setMaxConn(maxConn);
		pool.setMaxIdle(maxIdle * 60 * 60);
		pool.setMaintSleep(maintSleep);
		pool.setNagle(nagle);
		pool.setSocketTO(socketTO);
		pool.setSocketConnectTO(0);
		// 初始化连接池
		pool.initialize();
	}

	public static MemCachedClient getInstance() {
		if (mcc == null) {
			mcc = new MemCachedClient(SESSION_POOL);
			MemcachedProxyBySession m = new MemcachedProxyBySession();
			m.init();
		}
		return mcc;
	}

	public static void main(String[] args) {
		MemCachedClient mcc = MemcachedProxyBySession.getInstance();
		for (int i = 3; i < 6; i++) {
			boolean success = mcc.set("mem_" + i, "Hello!" + i);
			String result = (String) mcc.get("mem_" + i);
			System.out.println(String.format("set( %d ): %s", i, success));
			System.out.println(String.format("get( %d ): %s", i, result));
		}

		System.out.println("\n\t -- sleeping --\n");
		try {
			Thread.sleep(10000);
		} catch (Exception ex) {
		}

		for (int i = 0; i < 1000; i++) {
			// boolean success = mcc.set("" + i, "Hello!");
			String result = (String) mcc.get("mem_" + i);
			// System.out.println(String.format("set( %d ): %s", i, success));
			System.out.println(String.format("get( %d ): %s", i, result));
		}
	}
}