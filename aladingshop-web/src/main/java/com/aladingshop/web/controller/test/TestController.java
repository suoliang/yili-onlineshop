package com.aladingshop.web.controller.test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.cache.util.RedisUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.service.SkuService;
import com.fushionbaby.sku.service.search.SkuSearchService;


/**
 * 
 * @description 类描述...
 * @author 孟少博
 * @date 2015年8月24日上午9:38:00
 */
@Controller
@RequestMapping("/webtest")
public class TestController {
	private static final Log LOGGER = LogFactory.getLog(TestController.class);
	
	
	private static RedisUtil redis = new RedisUtil();
	private static final String PARAM = "webparam";
	private static final String KEY = "webtestKey";
	
	@Autowired
	private SkuSearchService skuSearchService;
	
	@Autowired
	private SkuService skuService;
	
	
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
	/**
	 * 打开清理静态化页面
	 * @return
	 */
	@RequestMapping("cleanPage")
	public String cleanPage(){
		return "other/cleanPage";
	}
	
	/**
	 * 只删除首页静态化
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delIndexStatic")
	public String delIndexStatic(HttpServletRequest request){
		
		String	htmlPath = request.getSession().getServletContext().getRealPath("");
		String delPath	= htmlPath + File.separator + "html"+ File.separator +"home.html";
		return this.delStatus(delPath);
	}
	/***
	 * 删除指定商品编号的静态化
	 * @param skuCode 商品编号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delSkuDetailStatic")
	public String delSkuDetailStatic(String skuCode,HttpServletRequest request){
		Sku sku = skuService.queryBySkuCode(skuCode);
		if(sku == null){
			return "该页面不存在，请检查商品编号是否输入正确！";
		}
		String	htmlPath = request.getSession().getServletContext().getRealPath("");
		
		String delPath	= htmlPath + File.separator + "detail"+ File.separator + skuCode+".html";
		
		return this.delStatus(delPath);
	}
	
	/**
	 * 删除全部静态化
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("delAllStatic")
	public String delStatic(HttpServletRequest request){
		
		String	htmlPath = request.getSession().getServletContext().getRealPath("");
		String delPath	= htmlPath + File.separator + "html";
		return this.delStatus(delPath);
	}
	private String delStatus(String delPath){
		if(this.deletefile(delPath)){
			return "success";
		}
		return "error";
	}
	
	public  boolean deletefile(String delPath){
		
		try {
			File file = new File(delPath);
			if(!file.isDirectory()){
				file.delete();
				return true;
			}
			String[] filelist = file.list();
			for(int i = 0 ; i < filelist.length;i++){
				File delfile = new File(delPath+File.separator+filelist[i]);
				if(!delfile.isDirectory()){
					delfile.delete();
					System.out.println("删除文件成功");
					
				}else if(delfile.isDirectory()){
					this.deletefile(delPath+File.separator+filelist[i]);
				}
			}
			file.delete();
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
			System.out.println("deletefile() Exception:" + e.getMessage());
			return false;
		}
		return true;
		
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

}
