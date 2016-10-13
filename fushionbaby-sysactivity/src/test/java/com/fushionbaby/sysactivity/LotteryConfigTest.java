package com.fushionbaby.sysactivity;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.sysactivity.model.LotteryConfig;
import com.fushionbaby.sysactivity.service.LotteryConfigService;

/**
 * 
 * @author cyla
 * 
 */
public class LotteryConfigTest {

	private LotteryConfigService lotteryConfigService;

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sysactivity-mybatis.xml" });
		lotteryConfigService = ac.getBean(LotteryConfigService.class);
	}

	@Test
	public void testAdd() {
		this.add();
	}

	@Ignore
	public void add() {
		LotteryConfig lotteryConfig = new LotteryConfig();
		lotteryConfig.setId(MyTestConstant.ID);
		lotteryConfig.setDisable("n");
		lotteryConfig.setEndTime(new Date());
		lotteryConfig.setStartTime(new Date());
		lotteryConfig.setLotteryCode("test");
		lotteryConfigService.add(lotteryConfig);
	}

	@After
	public void testDeleteById() {
		lotteryConfigService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testUpdate() {
		this.add();
		LotteryConfig lotteryConfig = lotteryConfigService
				.findById(MyTestConstant.ID);
		lotteryConfig.setDisable("y");
		lotteryConfigService.update(lotteryConfig);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(lotteryConfigService.findById(MyTestConstant.ID));
	}

	@Test
	public void testFindCode() {
		this.add();
		Assert.assertNotNull(lotteryConfigService.findByLotteryCode("test"));
	}

	@Test
	public void testFindAll() {
		this.add();
		Assert.assertNotNull(lotteryConfigService.findAll());
	}

}
