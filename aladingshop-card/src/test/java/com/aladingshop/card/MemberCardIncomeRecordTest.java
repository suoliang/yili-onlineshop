package com.aladingshop.card;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.card.model.MemberCardIncomeRecord;
import com.aladingshop.card.service.MemberCardIncomeRecordService;
import com.fushionbaby.common.constants.MyTestConstant;

public class MemberCardIncomeRecordTest {
	@Autowired
	private MemberCardIncomeRecordService<MemberCardIncomeRecord> memberCardIncomeRecordService;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				MyTestConstant.TEST_SPRING_CONFIG, "classpath:conf/spring-*-mybatis.xml" });
		memberCardIncomeRecordService = (MemberCardIncomeRecordService<MemberCardIncomeRecord>) context
				.getBean("memberCardIncomeRecordServiceImpl");
	}

//	@After
//	public void testdeleteById() {
//		this.memberCardIncomeRecordService.deleteById(MyTestConstant.ID);
//	}

	@Test
	public void testAdd() {
		for (int i = 0; i < 10; i++) 
		this.add();
	}

	@Test
	public void testUpdate() {
		this.add();
		MemberCardIncomeRecord gift = this.memberCardIncomeRecordService.findById(MyTestConstant.ID);
		List<MemberCardIncomeRecord> incomeRecords = this.memberCardIncomeRecordService.findAll();
		System.out.println(incomeRecords == null ? 0 : incomeRecords.size());
		gift.setIncomeMemberId((long) 50);
		this.memberCardIncomeRecordService.update(gift);
	}

	/*
	 * @Test public void testAll() { this.add();
	 * //Assert.assertNotNull(this.actActivityGiftService.findAll());
	 * System.out.println(this.memberCardRateIncomeService.findAll().size());
	 * 
	 * }
	 */

	public void add() {
		MemberCardIncomeRecord actActivityGift = new MemberCardIncomeRecord();
		//actActivityGift.setId(MyTestConstant.ID);
		actActivityGift.setCreateTime(new Date());
		actActivityGift.setIncomeMemberId((long) 30);
		actActivityGift.setIncomeAcount("123456");
		actActivityGift.setIncomeMoney(new BigDecimal(20));
		actActivityGift.setIncomeCardCode("123456123456");
		actActivityGift.setIncomeType("1");

		this.memberCardIncomeRecordService.add(actActivityGift);
	}
}
