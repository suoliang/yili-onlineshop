package com.fushionbaby.sysactivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.sysactivity.model.LotteryPrize;
import com.fushionbaby.sysactivity.service.LotteryPrizeService;

public class LotteryPrizeTest {
	
	private LotteryPrizeService lotteryPrizeService;
	
	@Before
	public void before(){
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]
				{MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-sysactivity-mybatis.xml"});
		lotteryPrizeService = (LotteryPrizeService) ac.getBean("lotteryPrizeServiceImpl");
	}

	@Test
	public void getByLevel(){
		LotteryPrize lotteryPrize = lotteryPrizeService.getByLevel(4L);
		Assert.assertNotNull(lotteryPrize);
	}
}
