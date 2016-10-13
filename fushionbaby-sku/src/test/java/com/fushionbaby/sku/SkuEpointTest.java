package com.fushionbaby.sku;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.sku.model.SkuEpoint;
import com.fushionbaby.sku.service.SkuEpointService;

public class SkuEpointTest {

	private SkuEpointService s;

	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sku-mybatis.xml" });
		s = (SkuEpointService) ac
				.getBean("skuEpointServiceImpl");
	}
	
	@Test
	public void add(){
		SkuEpoint se = new SkuEpoint();
		se.setSkuCode("232");
		se.setNeedEpoint(BigDecimal.ONE);
		se.setCreateTime(new Date());
		//s.add(se);
		
	}
	
	
}
