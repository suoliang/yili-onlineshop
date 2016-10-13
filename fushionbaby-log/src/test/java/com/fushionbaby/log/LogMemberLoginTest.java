package com.fushionbaby.log;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.log.model.LogMemberLogin;
import com.fushionbaby.log.service.LogMemberLoginService;
/**
 * 
 * @author King suoliang
 *
 */
public class LogMemberLoginTest {

	private LogMemberLoginService lmls;
	
	@Before
	public void before(){
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-log-mybatis.xml"
		});
		lmls = (LogMemberLoginService) ac.getBean("logMemberLoginServiceImpl");
	}
	
	@Ignore
	public void add(){
		LogMemberLogin lml = new LogMemberLogin();
		lml.setId(MyTestConstant.ID);
		lml.setMemberId(MyTestConstant.ID);
		lml.setLoginTime(new Date());
		lml.setLoginStatus("y");
		lmls.add(lml);
	}
	
	@Test
	public void testAdd() {
		this.add();
	}

	@After
	public void testDeleteById() {
		lmls.deleteById(MyTestConstant.ID);
	}
	
	@Test
	public void testFindByMemberIdAndLoginTime(){
		this.add();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(new Date());
		Assert.assertNotNull(lmls.findByMemberIdAndLoginTime(MyTestConstant.ID, date));
	}

}
