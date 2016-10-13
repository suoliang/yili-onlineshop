/**
 * 
 */
package com.fushionbaby.sku;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.sku.model.SkuExtraInfo;
import com.fushionbaby.sku.service.SkuExtraInfoService;
import com.fushionbaby.sku.service.SkuProductImageService;

/**
 * @author cyla
 * 
 */
public class SkuExtraInfoTest {

	@Autowired
	private SkuExtraInfoService skuExtraInfoService;
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sku-mybatis.xml" });
		skuExtraInfoService = (SkuExtraInfoService) ac.getBean("skuExtraInfoServiceImpl");
	}

	public void add() {
		SkuExtraInfo skuExtraInfo = new SkuExtraInfo();
		skuExtraInfo.setSkuCode("dd");
		skuExtraInfo.setActualSalesVolume(1);
		skuExtraInfo.setCommentCount(2);
		skuExtraInfo.setCreateId(1L);
		skuExtraInfo.setHasDiscount("n");
		skuExtraInfo.setHasGift("n");
		skuExtraInfo.setIsGift("n");
		skuExtraInfo.setIsVideo("n");
		skuExtraInfo.setOffshelvestime(new Date());
		skuExtraInfo.setOnshelvestime(new Date());
		skuExtraInfo.setSalesVolume(1);
		skuExtraInfo.setVideoUrl("c:/");
		skuExtraInfo.setUpdateId(1L);
		skuExtraInfo.setUpdateTime(new Date());
		skuExtraInfoService.add(skuExtraInfo);
	}
	

	@Test
	public void testFindBySkuCode() {
		this.add();
		Assert.assertNotNull(skuExtraInfoService.findBySkuCode("dd"));
	}

	@Test
	public void testUpdate() {
		this.add();
		SkuExtraInfo skuExtraInfo = new SkuExtraInfo();
		skuExtraInfo.setSkuCode("dd");
		skuExtraInfo.setActualSalesVolume(1);
		skuExtraInfo.setCommentCount(2);
		skuExtraInfo.setCreateId(1L);
		skuExtraInfo.setHasDiscount("n");
		skuExtraInfo.setHasGift("n");
		skuExtraInfo.setIsGift("n");
		skuExtraInfo.setIsVideo("n");
		skuExtraInfo.setOffshelvestime(new Date());
		skuExtraInfo.setOnshelvestime(new Date());
		skuExtraInfo.setSalesVolume(1);
		skuExtraInfo.setVideoUrl("c:/");
		skuExtraInfo.setUpdateId(1L);
		skuExtraInfo.setUpdateTime(new Date());
		skuExtraInfoService.update(skuExtraInfo);
		skuExtraInfoService.deleteBySkuCode("dd");
	}


	@Test
	public void testDeleteBySkuCode() {
		this.add();
		skuExtraInfoService.deleteBySkuCode("dd");
	}
}
