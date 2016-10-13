package com.fushionbaby.act.activity;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.act.activity.model.ActEntityCardUseRecord;
import com.fushionbaby.act.activity.service.ActEntityCardUseRecordService;
import com.fushionbaby.common.constants.MyTestConstant;

/***
 * 
 * @author xupeijun
 * 
 */
public class ActEntityCardUseRecordTest {
	@Autowired
	private ActEntityCardUseRecordService<ActEntityCardUseRecord> ActEntityCardUseRecordService;
	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-*-mybatis.xml" });
		ActEntityCardUseRecordService = (ActEntityCardUseRecordService) context
				.getBean("actEntityCardUseRecordServiceImpl");
	}

	@After
	public void testdeleteById() {
		this.ActEntityCardUseRecordService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testAdd() {
		for (int i = 0; i < 15; i++) 
		this.add();
	}

	@Test
	public void testUpdate() {
		this.add();
		ActEntityCardUseRecord gift = this.ActEntityCardUseRecordService.findById(MyTestConstant.ID);
		this.ActEntityCardUseRecordService.update(gift);
	}

//	@Test
//	public void testAll() {
//		this.add();
//		Assert.assertNotNull(this.ActEntityCardService.findAll());
//		// System.out.println(this.ActEntityCardService.findAll().size());
//
//	}

	public void add() {
		ActEntityCardUseRecord actActivityGift = new ActEntityCardUseRecord();
		//actActivityGift.setId(MyTestConstant.ID);
		actActivityGift.setCreateTime(new Date());
		actActivityGift.setCardNo("123456");
		actActivityGift.setMoney(new BigDecimal(250));
		actActivityGift.setOrderCode("123456789");
		actActivityGift.setUpdateId(10l);
		actActivityGift.setUpdateName("test");
		actActivityGift.setUseSource("2");
		actActivityGift.setUseType("1");
		this.ActEntityCardUseRecordService.add(actActivityGift);
	}

}
