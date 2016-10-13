package com.fushionbaby.member;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.member.model.MemberSkuView;
import com.fushionbaby.member.service.MemberSkuViewService;

public class MemberSkuViewTest {
	
	private MemberSkuViewService<MemberSkuView> svs;
	
	@Before
	public void before(){
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-member-mybatis.xml" 
		});
		svs = (MemberSkuViewService<MemberSkuView>) ac.getBean("memberSkuViewServiceImpl");
	}
	
	@Ignore
	public void add(){
		MemberSkuView sv = new MemberSkuView();
		sv.setId(MyTestConstant.ID);
		sv.setCreateTime(new Date());
		sv.setIp("192.168.1.234");
		sv.setSourceCode("好的");
		sv.setSkuCode("sds");
		sv.setLoginName("我不会");
		svs.add(sv);
	}
	
	
	@Test
	public void testAdd() {
		this.add();
	}

	@After
	public void testDeleteById() {
		svs.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testUpdate() {
		this.add();
		
		MemberSkuView sv = svs.findById(MyTestConstant.ID);
		sv.setLoginName("我还不会");
		sv.setSourceCode("好的");
		svs.update(sv);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(svs.findById(MyTestConstant.ID));
	}

}
