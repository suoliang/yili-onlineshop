package com.fushionbaby.log;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.log.model.LogCmsLogin;
import com.fushionbaby.log.service.LogCmsLoginService;

/**
 * 
 * @author King suoliang
 * 
 */
public class LogCmsLoginTest {

	private LogCmsLoginService lcls;

	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-log-mybatis.xml" });
		lcls = (LogCmsLoginService) ac.getBean("logCmsLoginServiceImpl");
	}

	@Ignore
	public void add() {
		LogCmsLogin lcl = new LogCmsLogin();
		lcl.setId(MyTestConstant.ID);
		lcl.setLoginName("用户名");
		lcl.setLoginStatus("y");
		lcls.add(lcl);
	}

	@Test
	public void testAdd() {
		this.add();
	}

	@After
	public void testDeleteById() {
		lcls.deleteById(MyTestConstant.ID);
	}

}
