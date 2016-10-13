/**
 * 
 */
package com.fushionbaby.schedule.sku;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aladingshop.sku.cms.service.SkuSearchService;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年7月22日上午10:04:33
 */

@Service
public class TaskSkuBuffer {
	
	
	private static final Log logger = LogFactory.getLog(TaskSkuBuffer.class);
	
	
	@Autowired
	private SkuSearchService skuSearchService;
	
	
	private static boolean runFlag = false;
	public void excute() {
		if (runFlag) {
			logger.info("TaskSkuBuffer正在运行");
			return;
		}
		runFlag = true;

		logger.info("TaskSkuBuffer开始运行");
		this.addSkuDocument();
		logger.info("TaskSkuBuffer运行结束");
		runFlag = false;
	}
	
	
	@Transactional
	public void addSkuDocument() {
		skuSearchService.addSkuDocument();
		//HttpRequest.sendGet("www.aladingshop.com/webtest/updatesolr", StringUtils.EMPTY);
	}

}
