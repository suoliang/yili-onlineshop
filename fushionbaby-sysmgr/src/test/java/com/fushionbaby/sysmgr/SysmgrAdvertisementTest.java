package com.fushionbaby.sysmgr;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysmgr.model.SysmgrAdvertisement;
import com.fushionbaby.sysmgr.service.SysmgrAdvertisementService;

/**
 * 
 * @author xupeijun
 * 
 */
public class SysmgrAdvertisementTest {

	private SysmgrAdvertisementService<SysmgrAdvertisement> sas;

	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sysmgr-mybatis.xml" });
		sas = (SysmgrAdvertisementService<SysmgrAdvertisement>) ac
				.getBean("sysmgrAdvertisementServiceImpl");
	}

	@Test
	public void testAdd() {
		this.add();
	}

	public void add() {
		SysmgrAdvertisement sa = new SysmgrAdvertisement();
		sa.setId(MyTestConstant.ID);
		sa.setShowOrder(10);
		sa.setRemark("shazio");
		sa.setIsShow("y");
		sa.setPicturePath("d:/picture");
		sa.setUrl("http://baidu");
		sa.setAdCode("1");
		sas.add(sa);
	}

	@After
	public void testDeleteById() {
		sas.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testUpdate() {
		this.add();
		SysmgrAdvertisement sa = sas.findById(MyTestConstant.ID);
		sa.setPicturePath("e:\\pic");
		sa.setUrl("https://google???");
		sas.update(sa);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(sas.findById(MyTestConstant.ID));
	}

	@Test
	public void testlistpage() {
		this.add();
		BasePagination page = new BasePagination();
		Assert.assertNotNull(sas.getListPage(page));
	}
}
