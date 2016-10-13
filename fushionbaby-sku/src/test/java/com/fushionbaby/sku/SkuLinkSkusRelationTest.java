package com.fushionbaby.sku;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.sku.model.SkuLinkSkusRelation;
import com.fushionbaby.sku.service.SkuLinkSkusRelationService;

public class SkuLinkSkusRelationTest {

	private SkuLinkSkusRelationService<SkuLinkSkusRelation> slss;

	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sku-mybatis.xml" });
		slss = (SkuLinkSkusRelationService<SkuLinkSkusRelation>) ac
				.getBean("skuLinkSkusRelationServiceImpl");
	}

	@Test
	public void testAdd() {
		this.add();
	}

	@Ignore
	public void add() {
		SkuLinkSkusRelation sls = new SkuLinkSkusRelation();
		sls.setId(MyTestConstant.ID);
		sls.setAdminId(1l);
		sls.setSkuCode("1l");
		sls.setCreateTime(new Date());
		sls.setLinkSkuCode("2l");
		slss.add(sls);
	}

	@After
	public void testDeleteById() {
		slss.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testUpdate() {
		this.add();
		SkuLinkSkusRelation sls = slss.findById(MyTestConstant.ID);
		slss.update(sls);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(slss.findById(MyTestConstant.ID));
	}

}
