package com.aladingshop.card;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.card.model.MemberCardOrder;
import com.aladingshop.card.service.MemberCardOrderService;
import com.fushionbaby.common.constants.MyTestConstant;
/***
 * 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年9月30日下午1:06:21
 */
public class MemberCardOrderTest {
	@Autowired
	private MemberCardOrderService<MemberCardOrder> memberCardOrderService;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				MyTestConstant.TEST_SPRING_CONFIG, "classpath:conf/spring-*-mybatis.xml" });
		memberCardOrderService = (MemberCardOrderService<MemberCardOrder>) context.getBean("memberCardOrderServiceImpl");
	}

	@After
	public void testdeleteById() {
		this.memberCardOrderService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testAdd() {
		this.add();
	}

	@Test
	public void testUpdate() {
		this.add();
		MemberCardOrder card = this.memberCardOrderService.findById(MyTestConstant.ID);
		List<MemberCardOrder> MemberCardOrders = this.memberCardOrderService.findAll();
		System.out.println(MemberCardOrders == null ? 0 : MemberCardOrders.size());
		card.setMemberId((long) 50);
		this.memberCardOrderService.update(card);
		
		
	}
@Test
public void testUpdateByMember(){
	this.add();
	this.memberCardOrderService.findByMemberIdOrderCode(MyTestConstant.ID, "123456789");
}
	/*
	 * @Test public void testAll() { this.add();
	 * //Assert.assertNotNull(this.actActivityGiftService.findAll());
	 * System.out.println(this.MemberCardOrderRateIncomeService.findAll().size());
	 * 
	 * }
	 */

	public void add() {
		MemberCardOrder card = new MemberCardOrder();
		card.setId(MyTestConstant.ID);
		card.setCreateTime(new Date());
		card.setFinanceStatus("y");
		card.setMemberId(20l);
		card.setMemberName("test");
		card.setOrderCode("123456789");
		card.setOrderStatus("2");
		card.setPayCompleteTime(new Date());
		card.setSourceCode("1");		

		this.memberCardOrderService.add(card);
	}
}
