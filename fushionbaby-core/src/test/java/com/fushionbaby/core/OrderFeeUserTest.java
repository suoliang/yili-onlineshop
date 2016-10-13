package com.fushionbaby.core;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.core.model.OrderFeeUser;
import com.fushionbaby.core.service.OrderFeeUserService;

/**
 * @author 张明亮
 *
 */
public class OrderFeeUserTest {
	
	private OrderFeeUserService orderFeeUserService;
	
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{MyTestConstant.TEST_SPRING_CONFIG
				,"classpath:conf/spring-*-mybatis.xml"});
		orderFeeUserService = (OrderFeeUserService) context.getBean("orderFeeUserServiceImpl");
	}
	
	
	@Test
	public void testAdd(){
		this.add();
	}
	public void add(){
		OrderFeeUser orderFeeUser = new OrderFeeUser();
		orderFeeUser.setId(MyTestConstant.ID);
		orderFeeUser.setActualTransferFee(new BigDecimal(1));
		orderFeeUser.setCardAmount(new BigDecimal(1));
		orderFeeUser.setCardno("cx");
		orderFeeUser.setEpointsRedeemMoney(new BigDecimal(1));
		orderFeeUser.setMemberId(MyTestConstant.ID);
		orderFeeUser.setOrderCode("d");
		orderFeeUser.setPayableTransferFee(new BigDecimal(1));
		orderFeeUser.setTotalActual(new BigDecimal(1));
		orderFeeUser.setTotalAfDiscount(new BigDecimal(1));
		orderFeeUser.setUseWalletMoney(new BigDecimal(1));
		orderFeeUserService.addOrderFeeUser(orderFeeUser);
	}
}
