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

import com.aladingshop.card.model.MemberCardConfig;
import com.aladingshop.card.service.MemberCardConfigService;
import com.fushionbaby.common.constants.MyTestConstant;

public class MemberCardConfigTest {
	@Autowired
	private MemberCardConfigService<MemberCardConfig> membercardconfigService;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				MyTestConstant.TEST_SPRING_CONFIG, "classpath:conf/spring-*-mybatis.xml" });
		membercardconfigService = (MemberCardConfigService<MemberCardConfig>) context
				.getBean("memberCardConfigServiceImpl");
	}

	@After
	public void testdeleteById() {
		this.membercardconfigService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testAdd() {
		this.add();
	}

	@Test
	public void testUpdate() {/*
							 * BasePagination page = new BasePagination(); if
							 * (page == null) { page = new BasePagination(); }
							 * Map<String, String> params = new HashMap<String,
							 * String>(); page.setParams(params); page =
							 * this.membercardconfigService.getListPage(page);
							 * List<MemberCardConfig> listFindAll =
							 * (List<MemberCardConfig>) page.getResult();
							 */
		this.add();
		List<MemberCardConfig> cardConfigs = this.membercardconfigService.findAll();
		System.out.println(cardConfigs == null ? 0 : cardConfigs.size());
		List<MemberCardConfig> cardConfigs2 = this.membercardconfigService.findByType("1");
		System.out.println(cardConfigs2 == null ? 0 : cardConfigs.size());
		MemberCardConfig gift = this.membercardconfigService.findById(MyTestConstant.ID);
		gift.setLowestMoney(new BigDecimal(50));
		this.membercardconfigService.update(gift);
	}

	public void add() {
		MemberCardConfig actActivityGift = new MemberCardConfig();
		actActivityGift.setId(MyTestConstant.ID);
		actActivityGift.setIsDisabled("n");
		actActivityGift.setLowestMoney(new BigDecimal(20));
		actActivityGift.setRebate(new BigDecimal(55));
		actActivityGift.setTimeLine(1);
		actActivityGift.setType("1");
		actActivityGift.setUpdateId(MyTestConstant.ID);
		actActivityGift.setUpdateTime(new Date());
		actActivityGift.setCreateId(1L);
		actActivityGift.setCreateTime(new Date());
		this.membercardconfigService.add(actActivityGift);
	}
}
