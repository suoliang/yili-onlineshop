package com.fushionbaby.sysactivity;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.sysactivity.model.SysActivityActivitiesApply;
import com.fushionbaby.sysactivity.service.SysActivityActivitiesApplyService;

/**
 * 
 * @author xupeijun
 * 
 */
public class SysActivityActivitiesApplyTest {
	
	private SysActivityActivitiesApplyService sysActivitiesApplyService;

	private SysActivityActivitiesApply sysActivitiesApply;
	
	@Before
	public void before(){
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]
				{MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-sysactivity-mybatis.xml"});
		sysActivitiesApplyService = (SysActivityActivitiesApplyService) ac
				.getBean("sysActivityActivitiesApplyServiceImpl");
	}

	@Test
	public void testAdd() {
		this.add();
	}
	
	@Ignore
	public void add(){
		sysActivitiesApply = new SysActivityActivitiesApply();
		sysActivitiesApply.setId(MyTestConstant.ID);
		sysActivitiesApply.setActivitiesId(MyTestConstant.ID);
		sysActivitiesApply.setActivitiesName("可以1");
		sysActivitiesApply.setMemberId(MyTestConstant.ID);
		sysActivitiesApply.setActivitiesId(MyTestConstant.ID);
		sysActivitiesApply.setActivitiesName("好的1");
		sysActivitiesApply.setApplyNumber(1);
		sysActivitiesApply.setMemberName("用户名1");
		sysActivitiesApply.setMemberPhone("15645967876");
		sysActivitiesApplyService.add(sysActivitiesApply);
	}

	@After
	public void testDeleteById() {
		sysActivitiesApplyService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testUpdate() {
		this.add();
		sysActivitiesApply = (SysActivityActivitiesApply) sysActivitiesApplyService.findById(MyTestConstant.ID);
		sysActivitiesApply.setActivitiesName("修改");
		sysActivitiesApply.setMemberId(MyTestConstant.ID);
		sysActivitiesApply.setActivitiesId(MyTestConstant.ID);
		sysActivitiesApply.setActivitiesName("改好了");
		sysActivitiesApply.setApplyNumber(2);
		sysActivitiesApply.setMemberName("修改名");
		sysActivitiesApply.setMemberPhone("13545967876");
		sysActivitiesApplyService.update(sysActivitiesApply);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(sysActivitiesApplyService.findById(MyTestConstant.ID));
	}

	@Test
	public void testFindAll() {
		this.add();
		Assert.assertNotNull(sysActivitiesApplyService.findAll());
	}

	@Test
	public void testQuery() {
		this.add();
		Assert.assertNotNull(sysActivitiesApplyService.query(MyTestConstant.ID, MyTestConstant.ID));
		//System.out.println(sysActivitiesApplyService.query(MyTestConstant.ID, MyTestConstant.ID).getActivitiesName());
	}
}
