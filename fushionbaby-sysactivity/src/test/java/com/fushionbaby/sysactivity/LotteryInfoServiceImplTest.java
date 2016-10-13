package com.fushionbaby.sysactivity;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.sysactivity.model.LotteryInfo;
import com.fushionbaby.sysactivity.model.SysActivityActivitiesApply;
import com.fushionbaby.sysactivity.service.LotteryInfoService;
import com.fushionbaby.sysactivity.service.SysActivityActivitiesApplyService;

public class LotteryInfoServiceImplTest {
	
	private LotteryInfoService lotteryInfoService;

	private LotteryInfo lotteryInfo;
	
	@Before
	public void before(){
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]
				{MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-sysactivity-mybatis.xml"});
		lotteryInfoService = (LotteryInfoService) ac.getBean("lotteryInfoServiceImpl");
	}
	
	@Test
	public void testAdd() {
		this.add();
	}
	
	@Ignore
	public void add(){
		LotteryInfo lotteryInfo= new LotteryInfo();
		lotteryInfo.setId(MyTestConstant.ID);
		lotteryInfo.setMemberId(MyTestConstant.ID);
		lotteryInfo.setLoginName("123");
		lotteryInfo.setNum(1);
		lotteryInfoService.add(lotteryInfo);
	}

	@After
	public void testDeleteById() {
		lotteryInfoService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testFindByMemberId() {
		this.add();
		Assert.assertNotNull(lotteryInfoService.findByMemberId(MyTestConstant.ID));
	}

	@Test
	public void testFindByLoginName() {
		this.add();
		Assert.assertNotNull(lotteryInfoService.findByLoginName("123"));
	}

}
