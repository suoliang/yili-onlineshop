package com.fushionbaby.core.web;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.core.model.OrderMemberAddressUser;
import com.fushionbaby.core.service.OrderMemberAddressUserService;

/**
 * @author 张明亮
 *
 */
public class SoSoMemberWebUserTest {
	
	private OrderMemberAddressUserService<OrderMemberAddressUser> soSoMemberWebService;
	
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{MyTestConstant.TEST_SPRING_CONFIG
				,"classpath:conf/spring-*-mybatis.xml"});
		soSoMemberWebService = (OrderMemberAddressUserService<OrderMemberAddressUser>)context.getBean("soSoMemberWebUserServiceImpl");
	}
	
	@After
	public void deleteById(){
		soSoMemberWebService.deleteById(MyTestConstant.ID);
	}
	
	/**
     * 根据订单编码查询,订单收货地址
     */
	@Test
	public void testFindByOrderCode(){
		this.add();
		Assert.assertNotNull(soSoMemberWebService.getOrderAddress(""+MyTestConstant.ID));
	}
	
	public void add(){
		OrderMemberAddressUser soSoMember = new OrderMemberAddressUser();
		soSoMember.setId(MyTestConstant.ID);
		soSoMember.setMemberId(MyTestConstant.ID);
		soSoMember.setOrderCode(""+MyTestConstant.ID);
		soSoMemberWebService.add(soSoMember);
	}
}
