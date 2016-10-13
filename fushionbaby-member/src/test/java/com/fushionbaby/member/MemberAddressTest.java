package com.fushionbaby.member;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.member.model.MemberAddress;
import com.fushionbaby.member.service.MemberAddressService;
/**
 * 
 * @author King
 *
 */
public class MemberAddressTest {
	
	private MemberAddressService memberAddressService;

	private MemberAddress memberAddress;

	@After
	public void testDeleteById() {
		memberAddressService.deleteById(MyTestConstant.ID);
	}
	@Before
	public void before(){
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-member-mybatis.xml"
		});
		memberAddressService = (MemberAddressService) ac.getBean("memberAddressServiceImpl");
	}
	
	@Test
	public void testAdd() {
		this.add();
	}


	@Test
	public void testUpdate() {
		this.add();
		memberAddress = (MemberAddress) memberAddressService.findById(MyTestConstant.ID);
		memberAddress.setVersion(new Date());
		memberAddress.setCreateTime(new Date());
		memberAddress.setAddress("上海市闵行区");
		memberAddress.setCity("恒西路");
		memberAddressService.update(memberAddress);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(memberAddressService.findById(MyTestConstant.ID));
	}

	

	private void add(){
		memberAddress = new MemberAddress();
		memberAddress.setId(MyTestConstant.ID);
		memberAddress.setVersion(new Date());
		memberAddress.setCreateTime(new Date());
		memberAddressService.add(memberAddress);
	}
}
