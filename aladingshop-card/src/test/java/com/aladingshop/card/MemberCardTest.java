package com.aladingshop.card;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.card.model.MemberCard;
import com.aladingshop.card.service.MemberCardService;
import com.fushionbaby.common.constants.MyTestConstant;

public class MemberCardTest {
	@Autowired
	private MemberCardService<MemberCard> memberCardService;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				MyTestConstant.TEST_SPRING_CONFIG, "classpath:conf/spring-*-mybatis.xml" });
		memberCardService = (MemberCardService<MemberCard>) context.getBean("memberCardServiceImpl");
	}

//	@After
//	public void testdeleteById() {
//		this.memberCardService.deleteById(MyTestConstant.ID);
//	}

	@Test
	public void testAdd() {
		for(int i=0;i<10;i++)
		this.add();
	}

	@Test
	public void testUpdate() {
		this.add();
		MemberCard card = this.memberCardService.findById(MyTestConstant.ID);
		List<MemberCard> memberCards = this.memberCardService.findAll();
		System.out.println(memberCards == null ? 0 : memberCards.size());
		card.setMemberId((long) 50);
		this.memberCardService.update(card);
	}

	/*
	 * @Test public void testAll() { this.add();
	 * //Assert.assertNotNull(this.actActivityGiftService.findAll());
	 * System.out.println(this.memberCardRateIncomeService.findAll().size());
	 * 
	 * }
	 */

	public void add() {
		MemberCard card = new MemberCard();
		//card.setId(MyTestConstant.ID);
		card.setCardConfigId(MyTestConstant.ID);
		card.setCardNo("62111233002");
		card.setCode("1122000");
		card.setMemberId(1L);
		card.setAcount("123456");
		card.setPassword("NSFFAWERDFD12");
		card.setTotalCorpus(new BigDecimal(55.2));
		this.memberCardService.add(card);
	}
}
