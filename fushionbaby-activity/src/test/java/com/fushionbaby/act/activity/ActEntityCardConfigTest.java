package com.fushionbaby.act.activity;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.act.activity.model.ActEntityCard;
import com.fushionbaby.act.activity.model.ActEntityCardConfig;
import com.fushionbaby.act.activity.service.ActEntityCardConfigService;
import com.fushionbaby.act.activity.service.ActEntityCardService;
import com.fushionbaby.common.constants.MyTestConstant;

/***
 * 
 * @author xupeijun
 * 
 */
public class ActEntityCardConfigTest {
	@Autowired
	private ActEntityCardConfigService<ActEntityCardConfig> ActEntityCardConfigService;
	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-*-mybatis.xml" });
		ActEntityCardConfigService = (ActEntityCardConfigService) context
				.getBean("actEntityCardConfigServiceImpl");
	}

	@After
	public void testdeleteById() {
		this.ActEntityCardConfigService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testAdd() {
		for (int i = 0; i < 15; i++) 
		this.add();
	}

	@Test
	public void testUpdate() {
		this.add();
		ActEntityCardConfig gift = this.ActEntityCardConfigService.findById(MyTestConstant.ID);
		this.ActEntityCardConfigService.update(gift);
	}

//	@Test
//	public void testAll() {
//		this.add();
//		Assert.assertNotNull(this.ActEntityCardService.findAll());
//		// System.out.println(this.ActEntityCardService.findAll().size());
//
//	}

	public void add() {
		ActEntityCardConfig actActivityGift = new ActEntityCardConfig();
		//actActivityGift.setId(MyTestConstant.ID);
	
		actActivityGift.setCreateTime(new Date());
		actActivityGift.setFaceMoney(new BigDecimal(200));
		actActivityGift.setBeginTime(new Date());
		actActivityGift.setExpiration(new Date());
		actActivityGift.setIsDisabled("n");
		actActivityGift.setSellMoney(new BigDecimal(190));
		this.ActEntityCardConfigService.add(actActivityGift);
	}

}
