/**
 * 
 */
package com.fushionbaby.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.cache.util.RedisUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.sku.service.SkuService;
import com.fushionbaby.sku.service.search.SkuSearchService;
import com.fushionbaby.sysmgr.model.SysmgrSearchKeywords;
import com.fushionbaby.sysmgr.service.SysmgrSearchKeywordsService;

/**
 * @author mengshaobo
 * 
 *         测试服务启动状况查看页面
 */
@Controller
@RequestMapping("/app")
public class TestController {

	private static RedisUtil redis = new RedisUtil();
	private static final String PARAM = "param";
	private static final String KEY = "testKey";
	@Autowired
	private SkuService skuService;
	@Autowired
	private SkuSearchService skuSearchService;
	@Autowired
	private SysmgrSearchKeywordsService<SysmgrSearchKeywords> sysmgrSearchKeywordsService;

	
	/**
	 * 修改搜索索引
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateSolr")
	public String addSkuDocument(){
		skuSearchService.addSkuDocument();
		return "success"; 
	}
	
	
	@ResponseBody
	@RequestMapping("delHomeCatch")
	public String delHomeCatch(){
		long delNum = redis.del("APP_HOME");
		
		if(delNum> 0){
			return "success";
		}
		return "fail";
	}
	
	
	
	@ResponseBody
	@RequestMapping("test")
	public Object testApp() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("redis", testRedis());
		map.put("memcached", testSessionCache());
		return Jsonp_data.success(map);
	}

	/**
	 * redis验证
	 * 
	 * @return
	 */
	private String testRedis() {
		if (!redis.hexists(KEY, PARAM)) {
			redis.hset(KEY, PARAM, RandomNumUtil.getUUIDString());
		}
		String value = redis.hget(KEY, PARAM);

		if (StringUtils.isNotBlank(value)) {
			redis.del("testKey");
			return "y";
		}
		return "n";

	}

	/**
	 * SessionCache 验证
	 * 
	 * @return
	 */
	private String testSessionCache() {

		Object value = "";
		SessionCache.put(KEY, PARAM);

		value = SessionCache.get(KEY);

		if (!ObjectUtils.equals(null, value)) {
			SessionCache.remove(KEY);
			return "y";
		}
		return "n";

	}

//	@ResponseBody
//	@RequestMapping("solrTest")
//	public Object solrTest() {
//
//		List<Sku> skuList = skuService
//				.queryByCondition(new SkuEntryQueryCondition());
//		Map<String, Map<String, String>> data = new HashMap<String, Map<String, String>>();
//		Map<String, String> map = new HashMap<String, String>();
//		for (Sku sku : skuList) {
//			map.put("skuName", sku.getName());
//			map.put("skuDescription", sku.getDescription());
//			map.put("content", sku.getName() + sku.getDescription());
//			data.put("sku" + sku.getId(), map);
//		}
//
//		Thread thread = new Thread(new EngineSynThredRun(data), "synEngineThread");
//		thread.start();
//
////		Map<String, Map<String, String>> results = new EngineSerach()
////				.getDocumentsByKeyWord("米");
//
//		return Jsonp_data.success("oh year");
//	}

}
