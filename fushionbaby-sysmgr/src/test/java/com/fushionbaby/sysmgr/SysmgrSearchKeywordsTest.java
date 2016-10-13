package com.fushionbaby.sysmgr;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.sysmgr.model.SysmgrSearchKeywords;
import com.fushionbaby.sysmgr.service.SysmgrSearchKeywordsService;

/**
 * 
 * @author xupeijun
 * 
 */
public class SysmgrSearchKeywordsTest {

	private SysmgrSearchKeywordsService ssks;

	@After
	public void testDeleteById() {
		ssks.deleteById(MyTestConstant.ID);
	}

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sysmgr-mybatis.xml" });
		ssks = (SysmgrSearchKeywordsService) ac
				.getBean("sysmgrSearchKeywordsServiceImpl");
	}

	@Test
	public void testAdd() {
		this.add();
	}

	private void add() {
		SysmgrSearchKeywords ssk = new SysmgrSearchKeywords();
		ssk.setId(MyTestConstant.ID);
		ssk.setIp("192.169.0.123");
		ssk.setDate(new Date());
		ssk.setKeyword("你好我种地哦啊了");
		ssk.setCount(1);
		ssks.add(ssk);
	}
}
