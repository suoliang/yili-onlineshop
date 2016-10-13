
package com.fushionbaby.schedule;

import java.io.File;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.sku.cms.dto.SkuSearchDto;
import com.aladingshop.sku.cms.service.SkuSearchService;
import com.fushionbaby.common.util.HttpRequest;
import com.fushionbaby.schedule.util.DomainConstant;

/**
 * @author mengshaobo
 *定时刷新静态化
 */
@Service
public class TaskRemoveStaticize {
	private static boolean runFlag = false;
	//日志
	private static final Log logger = LogFactory.getLog(TaskRemoveStaticize.class);
	@Autowired
	private SkuSearchService skuService;
	
	
	public void excute() {

		if (runFlag) {
			logger.info("TaskRemoveStaticize正在运行");
			return;
		}
		runFlag = true;

		logger.info("TaskRemoveStaticize开始运行");
		this.delIndex();
		this.delSkuDetail();
		logger.info("TaskRemoveStaticize运行结束");
		runFlag = false;
	}
	/**
	 * 每天0点和12点刷新静态页面
	 */
	private void delIndex() {
		
		try {
			String delPath	= DomainConstant.DOMAIN_PATH + File.separator + "html" + File.separator+"home.html";
			File file = new File(delPath);
			if(file.exists()){
				file.delete();
			}
			int i = 0;
			while(!file.exists()){
				i++;
				HttpRequest.sendGet(DomainConstant.INDEX_ACTION_URL, "");
				if(i > 10){
					break;
				}

			}
		} catch (Exception e) {
			logger.error("静态化删除出错");
			e.printStackTrace();
		}
	}
	
	private void delSkuDetail(){
		
		try {
			List<SkuSearchDto> skuList = skuService.queryBySameDayOperateing();
			for (SkuSearchDto skuSearchDto : skuList) {
				String delPath	= DomainConstant.DOMAIN_PATH + File.separator + "detail" + File.separator+ skuSearchDto.getSkuCode()+ ".html";
				File file = new File(delPath);
				if(file.exists()){
					file.delete();
				}
				
			}
		} catch (Exception e) {
			logger.error("静态化删除出错");
			e.printStackTrace();
		}
		
		
	}
}
