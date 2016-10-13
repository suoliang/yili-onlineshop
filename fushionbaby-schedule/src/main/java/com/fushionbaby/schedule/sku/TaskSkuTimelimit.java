package com.fushionbaby.schedule.sku;
/**
 * 
 *//*
package com.fushionbaby.schedule;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuTimelimit;
import com.fushionbaby.sku.service.FindSkuService;
import com.fushionbaby.sku.service.SkuTimelimitService;

*//**
 * @author mengshaobo
 *
 *//*
@Service
public class TaskSkuTimelimit {
	private static boolean runFlag = false;
	//日志
	private static final Log logger = LogFactory.getLog(TaskSkuTimelimit.class);
	@Autowired
	private SkuTimelimitService<SkuTimelimit> skuTimelimitService;
	@Autowired
	private SkuService<Sku> skuService;
	@Autowired
	private FindSkuService findSkuService;
	
	public void excute() {

		if (runFlag) {
			logger.info("TaskSkuTimelimit正在运行");
			return;
		}
		runFlag = true;

		logger.info("TaskSkuTimelimit开始运行");
		this.run();
		logger.info("TaskSkuTimelimit运行结束");
		runFlag = false;
	}
	
	*//**
	 * @see 1.找到所有上架的限时抢购商品；
	 * @see 2.当前时间大于或等于下架时间时，把该限时抢购的商品改为下架
	 *//*
	@Transactional
	private void run() {
		 
		try {
			List<SkuTimelimit> list = skuTimelimitService.findByStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
			for(SkuTimelimit timelimit : list){
				if(timelimit !=null){
					Long differTime = System.currentTimeMillis() - timelimit.getOffshelvestime().getTime();
					if(differTime >= 0){
						timelimit.setSkuStatus(SkuStatusEnum.SKU_STATUS_DOWN.getStrVlue());
						skuTimelimitService.update(timelimit);
						
						Sku skuEntry = findSkuService.queryBySkuCode(timelimit.getSkuCode()) ;
						if(skuEntry !=null){
							skuEntry.setCurrentPrice(timelimit.getLimitPrice());
							skuService.update(skuEntry);
						}
					}
				}
			}
		
		} catch (Exception e) {
			logger.error("限时抢购定时修改下架状态出错");
			e.printStackTrace();
		}
	}
	
}
*/