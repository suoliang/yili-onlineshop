package com.aladingshop.sku.cms;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.sku.cms.model.SkuEpoint;
import com.aladingshop.sku.cms.service.SkuEpointService;
import com.fushionbaby.common.constants.MyTestConstant;


public class SkuEpointTest {

	
    private SkuEpointService skuEpointService;

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sku-cms-mybatis.xml" });
		skuEpointService = (SkuEpointService) ac.getBean("skuEpointServiceImpl");
	}
	@After
	public void testdeleteBySkuCode() {

		this.skuEpointService.deleteBySkuCode(MyTestConstant.ID+"");;
	}

	
	
	@Test
	public void testadd() {
		this.add();
	}
	
	@Test
	public void testupdate() {
		this.add();
		SkuEpoint skuEpoint = this.skuEpointService.findBySkuCode(MyTestConstant.ID+"");
		skuEpoint.setId(MyTestConstant.ID);
		skuEpoint.setNeedEpoint(new BigDecimal("100"));
		skuEpoint.setSkuCode(MyTestConstant.ID+"");
		this.skuEpointService.update(skuEpoint);

	}


	@Test
	public void testfind() {
		this.add();
		Assert.assertNotNull(this.skuEpointService.findBySkuCode(MyTestConstant.ID+""));
	}




	public void add() {
		SkuEpoint	skuEpoint=new SkuEpoint();
		skuEpoint.setId(MyTestConstant.ID);
		skuEpoint.setNeedEpoint(new BigDecimal("100"));
		skuEpoint.setSkuCode(MyTestConstant.ID+"");
		this.skuEpointService.add(skuEpoint);
	}


}
