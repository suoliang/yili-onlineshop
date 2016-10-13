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
import com.fushionbaby.sku.model.SkuTogether;
import com.fushionbaby.sku.service.SkuTogetherService;
import com.fushionbaby.sku.service.SkuProductImageService;

/**
 * @author cyla
 * 
 */
public class SkuTogetherTest {

	@Autowired
	private SkuTogetherService skuTogetherService;
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sku-mybatis.xml" });
		skuTogetherService = (SkuTogetherService) ac.getBean("skuTogetherServiceImpl");
	}

	public void add() {
		SkuTogether skuTogether = new SkuTogether();
		skuTogether.setCity("d");
		skuTogether.setCreateId(MyTestConstant.ID);
		skuTogether.setDistrict("d");
		skuTogether.setBeginTime(new Date());
		skuTogether.setEndTime(new Date());
		skuTogether.setProvince("d");
		skuTogether.setSkuName("d");
		skuTogether.setSkuCode("dd");
		skuTogether.setUpdateId(MyTestConstant.ID);
		skuTogether.setUpdateTime(new Date());
		skuTogetherService.add(skuTogether);
	}
	
	@Test
	public void testAdd(){
		add();
	}
	
	@Test
	public void testUpdateBySkuCode(){
		add();
		SkuTogether skuTogether = new SkuTogether();
		skuTogether.setCity("d");
		skuTogether.setCreateId(MyTestConstant.ID);
		skuTogether.setDistrict("d");
		skuTogether.setBeginTime(new Date());
		skuTogether.setEndTime(new Date());
		skuTogether.setProvince("d");
		skuTogether.setSkuName("d");
		skuTogether.setSkuCode("dd");
		skuTogether.setUpdateId(MyTestConstant.ID);
		skuTogether.setUpdateTime(new Date());
		skuTogetherService.updateBySkuCode(skuTogether);
	}
	
}
