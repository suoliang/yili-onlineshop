package com.fushionbaby.member;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.member.model.MemberDevice;
import com.fushionbaby.member.service.MemberDeviceService;

public class MemberDeviceTest {
	
	private MemberDeviceService<MemberDevice> memberDeviceService;
	
	@SuppressWarnings("unchecked")
	@Before
	public void before(){
		
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-member-mybatis.xml"
		});
		memberDeviceService = (MemberDeviceService<MemberDevice>) ac.getBean("memberDeviceServiceImpl");
	}
	
	@Test
	public void add(){
		MemberDevice memberDevice = new MemberDevice();
		memberDevice.setPhone("123456");
		memberDevice.setSourceCode("IOS");
		memberDevice.setType("1");
		memberDeviceService.add(memberDevice);
	}
	
	@Test
	public void findById(){
		Long id = 1L;
		MemberDevice memberDevice = memberDeviceService.findById(id);
		System.out.println(memberDevice.getSourceCode());
	}
	
	@Test
	public void update(){
		MemberDevice memberDevice = memberDeviceService.findById(1L);
		memberDevice.setPhone("789123456");
		memberDeviceService.update(memberDevice);
	}
}
