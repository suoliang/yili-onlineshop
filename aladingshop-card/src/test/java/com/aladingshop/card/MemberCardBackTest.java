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

import com.aladingshop.card.model.MemberCardBack;
import com.aladingshop.card.service.MemberCardBackService;
import com.fushionbaby.common.constants.MyTestConstant;

public class MemberCardBackTest {
	@Autowired
	private MemberCardBackService<MemberCardBack> membercardBackService;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				MyTestConstant.TEST_SPRING_CONFIG, "classpath:conf/spring-*-mybatis.xml" });
		membercardBackService = (MemberCardBackService<MemberCardBack>) context
				.getBean("memberCardBackServiceImpl");
	}

	@After
	public void testdeleteById() {
		this.membercardBackService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testAdd() {
		for (int i = 0; i < 12; i++) 
		this.add();
	}

	@Test
	public void testFindAll(){
		System.out.println(this.membercardBackService.findAll().size());
	}
	
	@Test
	public void testUpdate() {/*
							 * BasePagination page = new BasePagination(); if
							 * (page == null) { page = new BasePagination(); }
							 * Map<String, String> params = new HashMap<String,
							 * String>(); page.setParams(params); page =
							 * this.membercardBackService.getListPage(page);
							 * List<MemberCardBack> listFindAll =
							 * (List<MemberCardBack>) page.getResult();
							 */
		this.add();
		List<MemberCardBack> cardConfigs = this.membercardBackService.findAll();
		System.out.println(cardConfigs == null ? 0 : cardConfigs.size());
		MemberCardBack gift = this.membercardBackService.findById(MyTestConstant.ID);
		this.membercardBackService.update(gift);
	}

	public void add() {
		MemberCardBack actActivityGift = new MemberCardBack();
		
		actActivityGift.setUpdateId(300l);
		actActivityGift.setUpdateTime(new Date());
		actActivityGift.setCreateTime(new Date());
		actActivityGift.setAcount("ruyibao678");
		actActivityGift.setBackStatus("2");
		actActivityGift.setBankCardHolder("yingangchika123");
		actActivityGift.setBankCardNo("yinhangkahao321");
		actActivityGift.setBankName("zhongguoyinhang");
		actActivityGift.setCardNo("kahao123");
		actActivityGift.setMemberId(200l);
		actActivityGift.setMoney(new BigDecimal(100));
		this.membercardBackService.add(actActivityGift);
	}
}
