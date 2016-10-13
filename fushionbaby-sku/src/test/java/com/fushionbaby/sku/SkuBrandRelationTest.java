package com.fushionbaby.sku;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.sku.model.SkuBrandRelation;
import com.fushionbaby.sku.service.SkuBrandRelationService;

public class SkuBrandRelationTest {

	private SkuBrandRelationService<SkuBrandRelation> slss;

	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sku-mybatis.xml" });
		slss = (SkuBrandRelationService<SkuBrandRelation>) ac
				.getBean("skuBrandRelationServiceImpl");
	}

}
