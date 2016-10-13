package com.fushionbaby.sku;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.sku.model.SkuBrowseHistories;
import com.fushionbaby.sku.service.SkuBrowseHistoriesService;

public class SkuBrowseHistoriesTest {
	
	private SkuBrowseHistoriesService s;

	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sku-mybatis.xml" });
		s = (SkuBrowseHistoriesService) ac
				.getBean("skuBrowseHistoriesServiceImpl");
	}

	public void add() {
		SkuBrowseHistories skuBrowseHistories = new SkuBrowseHistories();
		skuBrowseHistories.setId(MyTestConstant.ID);
		skuBrowseHistories.setMemberId(MyTestConstant.ID);
		skuBrowseHistories.setSkuCode("d");
		s.add(skuBrowseHistories);
	}
	

	@Test
	public void testAdd() {
		this.add();
		SkuBrowseHistories skuBrowseHistories = new SkuBrowseHistories();
		skuBrowseHistories.setMemberId(MyTestConstant.ID);
		skuBrowseHistories.setSkuCode("d");
		Assert.assertNotNull(s.findByMemberIdAndSkuCode(skuBrowseHistories));
	}

	@Test
	public void testAddOrUpdateAdd() {
		SkuBrowseHistories skuBrowseHistories = new SkuBrowseHistories();
		skuBrowseHistories.setMemberId(MyTestConstant.ID);
		skuBrowseHistories.setSkuCode("d2");
		s.addOrUpdateBrowseHistories(skuBrowseHistories);
	}

	@Test
	public void testAddOrUpdateUpdate() {
		SkuBrowseHistories skuBrowseHistories = new SkuBrowseHistories();
		skuBrowseHistories.setMemberId(MyTestConstant.ID);
		skuBrowseHistories.setSkuCode("d");
		s.addOrUpdateBrowseHistories(skuBrowseHistories);
	}

	@Test
	public void testFindTop6() {
		
		System.out.println(s.findTopNByMemberId(MyTestConstant.ID, 6).size());
		Assert.assertNotNull(s.findTopNByMemberId(MyTestConstant.ID, 6));
	}
}
