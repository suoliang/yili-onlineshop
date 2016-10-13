package com.fushionbaby.sysactivity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.sysactivity.model.SysActivityWill;
import com.fushionbaby.sysactivity.service.SysActivityWillService;

/**
 * 
 * @author cyla
 * 
 */
public class SysActivityWillTest {
	private SysActivityWillService sacts;

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sysactivity-mybatis.xml" });
		sacts = (SysActivityWillService) context
				.getBean("sysActivityWillServiceImpl");
	}

	public void add() {
		SysActivityWill sac = new SysActivityWill();
		sac.setId(MyTestConstant.ID);
		sac.setWebBannerUrl("154");
		sac.setAppBannerUrl("241545");
		sac.setWebArticleUrl("654646");
		sac.setAppArticleUrl("45457");
		sacts.add(sac);
	}

	@Test
	public void testAdd() {
		this.add();
	}

	@After
	public void testDeleteById() {
		sacts.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testFindAll() {
		this.add();
		Assert.assertNotNull(this.sacts.findAll());
	}
	@Test
	public void testUpdate() {
		add();
		SysActivityWill sac = (SysActivityWill) this.sacts
				.findById(MyTestConstant.ID);
		sac.setId(MyTestConstant.ID);
		sac.setWebBannerUrl("24154");
		sac.setAppBannerUrl("15454");
		sac.setWebArticleUrl("4878");
		sac.setAppArticleUrl("545456");
		sacts.update(sac);

	}

}
