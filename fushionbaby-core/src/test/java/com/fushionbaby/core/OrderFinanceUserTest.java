package com.fushionbaby.core;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.service.OrderFinanceUserService;

/**
 * @author 张明亮
 *
 */
public class OrderFinanceUserTest {
	
	private OrderFinanceUserService orderFinanceUserService;
	
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{MyTestConstant.TEST_SPRING_CONFIG
				,"classpath:conf/spring-*-mybatis.xml"});
		orderFinanceUserService = (OrderFinanceUserService) context.getBean("orderFinanceUserServiceImpl");
	}
	
	
	@Test
	public void testAdd(){
		this.add();
	}
	public void add(){
		OrderFinanceUser orderFinanceUser = new OrderFinanceUser();
		orderFinanceUser.setId(MyTestConstant.ID);
		orderFinanceUser.setMemberId(MyTestConstant.ID);
		orderFinanceUser.setOrderCode("d");
		orderFinanceUser.setFinanceStatus("n");
		orderFinanceUser.setInvoiceTitle("d");
		orderFinanceUser.setInvoiceType(1);
		orderFinanceUser.setIsInvoice("n");
		orderFinanceUser.setPaymentCompleteTime(new Date());
		orderFinanceUser.setPaymentType("d");
		orderFinanceUserService.addOrderFinanceUser(orderFinanceUser);
	}
	
	@Test
	public void testFindByMemberIdAndOrderCode(){
		OrderFinanceUser financeUser = orderFinanceUserService.findByMemberIdAndOrderCode(80L, "5287775");
		System.out.println(financeUser);
	}
}
