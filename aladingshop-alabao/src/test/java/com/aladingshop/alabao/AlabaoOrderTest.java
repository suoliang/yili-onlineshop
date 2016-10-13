package com.aladingshop.alabao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.alabao.model.AlabaoOrder;
import com.aladingshop.alabao.service.AlabaoOrderService;
import com.fushionbaby.common.constants.MyTestConstant;

public class AlabaoOrderTest {

	@Autowired
	private AlabaoOrderService<AlabaoOrder> alabaoOrderService;
	
	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-*-mybatis.xml" });
		alabaoOrderService = (AlabaoOrderService<AlabaoOrder>) context
				.getBean("alabaoOrderServiceImpl");
	}
	

	@After
	public void testdeleteById() {
		//this.alabaoOrderService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testAdd() {
		this.add();
	}
	
	

	@Test
	public void testUpdate() {/*
		BasePagination page = new BasePagination();
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			page.setParams(params);
			page = this.membercardconfigService.getListPage(page);
			List<MemberCardConfig> listFindAll = (List<MemberCardConfig>) page.getResult();*/
		//this.add();
		AlabaoOrder gift = this.alabaoOrderService.findById(MyTestConstant.ID);
		gift.setMemberName("xlts");
		this.alabaoOrderService.updateByMemberIdOrderCode(gift);
		AlabaoOrder gifts = this.alabaoOrderService.findById(MyTestConstant.ID);
		System.out.println(gifts.getMemberName());
	}

	/*@Test
	public void testAll() {
		this.add();
		Assert.assertNotNull(this.alabaoOrderService.findByType("1"));
		
		Assert.assertNotNull(this.alabaoOrderService.findAllType());
		// System.out.println(this.actActivityGiftService.findAll().size());

	}*/

	public void add() {
		AlabaoOrder alabaoOrder = new AlabaoOrder();
		alabaoOrder.setId(MyTestConstant.ID);
		alabaoOrder.setAlabaoStatus("123");
		alabaoOrder.setCreateTime(new Date());
		alabaoOrder.setMemberId(MyTestConstant.ID);
		alabaoOrder.setMemberName("456");
		alabaoOrder.setOrderCode("789");
		alabaoOrder.setPaymentType("111");
		alabaoOrder.setSourceCode("213");
		alabaoOrder.setTotalActual(new BigDecimal(141));
		alabaoOrder.setUpdateId((long)141);
		alabaoOrder.setUpdateTime(new Date());
		this.alabaoOrderService.add(alabaoOrder);
	}
	
}
