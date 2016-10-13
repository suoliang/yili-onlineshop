/**
 * 
 */
package com.fushionbaby.sku;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.sku.model.SkuPrice;
import com.fushionbaby.sku.service.SkuPriceService;
import com.fushionbaby.sku.service.SkuProductImageService;

/**
 * @author cyla
 * 
 */
public class SkuPriceTest {

	@Autowired
	private SkuPriceService skuPriceService;
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sku-mybatis.xml" });
		skuPriceService = (SkuPriceService) ac.getBean("skuPriceServiceImpl");
	}

	public void add() {
		SkuPrice skuPrice = new SkuPrice();
		skuPrice.setCostPrice(new BigDecimal(1000));;
		skuPrice.setCurrentPrice(new BigDecimal(1));
		skuPrice.setMarketPrice(new BigDecimal(10));
		skuPrice.setPrePrice(new BigDecimal(100));
		skuPrice.setRetailPrice(new BigDecimal(10000));
		skuPrice.setSkuCode("dd");
		skuPriceService.add(skuPrice);
	}
	

	@Test
	public void testFindBySkuCode() {
		this.add();
		System.out.println(1);
		System.out.println(skuPriceService.findBySkuCode("dd").getSkuCode());
		Assert.assertNotNull(skuPriceService.findBySkuCode("dd"));
	}

	@Test
	public void testFindAll() {
		this.add();
		Assert.assertNotNull(skuPriceService.findAll());
	}

	@Test
	public void testDeleteById() {
		this.add();
		skuPriceService.deleteById(Long.parseLong("1"));
	}
	@Test
	public void testDeleteBySkuCode() {
		this.add();
		skuPriceService.deleteBySkuCode("dd");
	}
}
