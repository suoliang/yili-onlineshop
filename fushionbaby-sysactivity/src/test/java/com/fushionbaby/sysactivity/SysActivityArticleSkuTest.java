package com.fushionbaby.sysactivity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.sysactivity.model.SysActivityArticleSku;
import com.fushionbaby.sysactivity.service.SysActivityArticleSkuService;

/**
 * 
 * @author xupeijun
 * 
 */
public class SysActivityArticleSkuTest {

	private SysActivityArticleSkuService sass;
	private SysActivityArticleSku sas;

	@After
	public void testDeleteById() {
		sass.deleteById(MyTestConstant.ID);

	}

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sysactivity-mybatis.xml" });
		sass = (SysActivityArticleSkuService) ac
				.getBean("sysActivityArticleSkuServiceImpl");
	}

	@Test
	public void testAdd() {
		this.add();
	}

	@Test
	public void testUpdate() {
		this.add();
		sas = new SysActivityArticleSku();
		sas.setSkuId(11l);
		sas.setArticleId(11l);
		sass.update(sas);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(sass.findById(MyTestConstant.ID));
	}

	@Test
	public void testFindAll() {
		this.add();
		Assert.assertNotNull(sass.findAll().size());
	}

	/*
	 * @Test public void testlistSkuIdByArticleId() { this.add();
	 * Assert.assertNotNull(this.sass.listSkuIdByArticleId(107l));
	 * System.out.println(this.sass.listSkuIdByArticleId(107l).size()); }
	 */

	private void add() {
		sas = new SysActivityArticleSku();
		sas.setId(MyTestConstant.ID);
		sas.setSkuId(3l);
		sas.setArticleId(3l);
		sass.add(sas);
	}
}
