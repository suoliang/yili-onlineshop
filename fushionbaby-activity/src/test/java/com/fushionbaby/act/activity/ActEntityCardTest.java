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
import com.fushionbaby.act.activity.service.ActEntityCardService;
import com.fushionbaby.common.constants.MyTestConstant;

/***
 * 
 * @author xupeijun
 * 
 */
public class ActEntityCardTest {
	@Autowired
	private ActEntityCardService<ActEntityCard> ActEntityCardService;
	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-*-mybatis.xml" });
		ActEntityCardService = (ActEntityCardService) context
				.getBean("actEntityCardServiceImpl");
	}

	@After
	public void testdeleteById() {
		this.ActEntityCardService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testAdd() {
		for (int i = 0; i < 15; i++)
		this.add();
	}

	@Test
	public void testUpdate() {
		this.add();
		ActEntityCard gift = this.ActEntityCardService.findById(MyTestConstant.ID);
		this.ActEntityCardService.update(gift);
	}

//	@Test
//	public void testAll() {
//		this.add();
//		Assert.assertNotNull(this.ActEntityCardService.findAll());
//		// System.out.println(this.ActEntityCardService.findAll().size());
//
//	}

	public void add() {
		ActEntityCard actActivityGift = new ActEntityCard();
		//actActivityGift.setId(MyTestConstant.ID);
		actActivityGift.setCardNo("123456");
		actActivityGift.setChargePwd("123456");
		actActivityGift.setCode("111111");
		actActivityGift.setConfigId(20l);
		actActivityGift.setConfigName("test");
		actActivityGift.setCreateTime(new Date());
		actActivityGift.setFaceMoney(new BigDecimal(200));
		actActivityGift.setStatus("1");
		actActivityGift.setSurplusMoney(new BigDecimal(100));
		this.ActEntityCardService.add(actActivityGift);
	}

}
