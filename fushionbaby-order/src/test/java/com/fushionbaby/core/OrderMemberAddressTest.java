package com.fushionbaby.core;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.order.model.OrderMemberAddress;
import com.fushionbaby.order.service.OrderMemberAddressService;

/**
 * @author 张明亮
 *
 */
public class OrderMemberAddressTest {
	
	private OrderMemberAddressService<OrderMemberAddress> orderMemberAddressService;
	
	
	@Before
	public void before(){                                                                    
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{MyTestConstant.TEST_SPRING_CONFIG
				,"classpath:conf/spring-*-mybatis.xml"});
		orderMemberAddressService = (OrderMemberAddressService<OrderMemberAddress>) context.getBean("orderMemberAddressServiceImpl");
	}
	
	@After
	public void deleteById(){
		orderMemberAddressService.deleteById(MyTestConstant.ID);
	}
	
	
	@Test
	public void testAdd(){
		this.add();
		Assert.assertNotNull(orderMemberAddressService.findByOrderCode("d"));
	}
	
	/**
     * 根据订单编码查询,订单收货地址
     */
	@Test
	public void testFindByOrderCode(){
		this.add();
		Assert.assertNotNull(orderMemberAddressService.findByOrderCode("d"));
	}
	
	@Test
	public void testFindByMemberIdAndOrderCode(){
		this.add();
		Assert.assertNotNull(orderMemberAddressService.findByMemberIdAndOrderCode(MyTestConstant.ID, "d"));
	}
	
	/**
	 * 更新订单收货地址
	 * 根据订单orderId 订单id
	 */
	@Test
    public void testUpdateBySoCodeAddress(){
		this.add();
    	OrderMemberAddress orderMemberAddress = new OrderMemberAddress();
		orderMemberAddress.setMemberEmail("254147699@qq.com");
		orderMemberAddress.setId(MyTestConstant.ID);
		orderMemberAddress.setMemberId(MyTestConstant.ID);
		orderMemberAddress.setAddress("d");
		orderMemberAddress.setCity("d");
		orderMemberAddress.setContactPerson("d");
		orderMemberAddress.setCountry("d");
		orderMemberAddress.setDistrict("d");
		orderMemberAddress.setMemberId(MyTestConstant.ID);
		orderMemberAddress.setMemberName("d");
		orderMemberAddress.setOrderCode("d");
		orderMemberAddress.setProvince("d");
		orderMemberAddress.setReceiver("d");
		orderMemberAddress.setReceiverMobile("125787");
		orderMemberAddress.setReceiverPhone("231465");
		orderMemberAddress.setStatus(0);
		orderMemberAddress.setVersion(new Date());
		orderMemberAddress.setZipcode("d");
		orderMemberAddressService.updateByOrderCodeAddress(orderMemberAddress);
    }
	
	public void add(){
		OrderMemberAddress orderMemberAddress = new OrderMemberAddress();
		orderMemberAddress.setId(MyTestConstant.ID);
		orderMemberAddress.setMemberId(MyTestConstant.ID);
		orderMemberAddress.setAddress("d");
		orderMemberAddress.setCity("d");
		orderMemberAddress.setContactPerson("d");
		orderMemberAddress.setCountry("d");
		orderMemberAddress.setDistrict("d");
		orderMemberAddress.setMemberEmail("d");
		orderMemberAddress.setMemberId(MyTestConstant.ID);
		orderMemberAddress.setMemberName("d");
		orderMemberAddress.setOrderCode("d");
		orderMemberAddress.setProvince("d");
		orderMemberAddress.setReceiver("d");
		orderMemberAddress.setReceiverMobile("125787");
		orderMemberAddress.setReceiverPhone("231465");
		orderMemberAddress.setStatus(0);
		orderMemberAddress.setVersion(new Date());
		orderMemberAddress.setZipcode("d");
		orderMemberAddressService.add(orderMemberAddress);
	}
}
