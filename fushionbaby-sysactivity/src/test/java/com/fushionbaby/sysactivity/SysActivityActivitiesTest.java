package com.fushionbaby.sysactivity;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysactivity.model.SysActivityActivities;
import com.fushionbaby.sysactivity.service.SysActivityActivitiesService;

/**
 * 
 * @author xupeijun
 * 
 */
public class SysActivityActivitiesTest {

	@Autowired
	private SysActivityActivitiesService<SysActivityActivities> sysActivityActivitiesService;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sysactivity-mybatis.xml" });
		sysActivityActivitiesService = (SysActivityActivitiesService<SysActivityActivities>) ac
				.getBean("sysActivityActivitiesServiceImpl");
	}

	@Test
	public void testAdd() {
		this.add();
	}

	public void add() {
		SysActivityActivities sysActivityActivities = new SysActivityActivities();
		sysActivityActivities.setId(MyTestConstant.ID);
		sysActivityActivities.setIntroduce("ww1");
		sysActivityActivities.setName("dfd1");
		sysActivityActivities.setNumber(1);
		sysActivityActivities.setPlace("dff1");
		sysActivityActivities.setTime(new Date());

		sysActivityActivities.setIsShow("y");
		sysActivityActivities.setPlacePictureUrl("D://nihao/test");
		sysActivityActivities.setAppIntroduceUrl("1254");
		sysActivityActivities.setAppBannerUrl("2222");
		sysActivityActivities.setWebIntroduceUrl("3333");
		sysActivityActivities.setWebBannerUrl("5555");
		sysActivityActivitiesService.add(sysActivityActivities);
	}

	@After
	public void testDeleteById() {
		sysActivityActivitiesService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testUpdate() {
		this.add();
		SysActivityActivities sysActivities = (SysActivityActivities) this.sysActivityActivitiesService
				.findById(MyTestConstant.ID);
		sysActivities.setIntroduce("ww1");
		sysActivities.setName("dfd1");
		sysActivities.setNumber(1);
		sysActivities.setPlace("dff1");

		sysActivities.setIsShow("n");
		sysActivities.setPlacePictureUrl("e://nihao/test");
		sysActivityActivitiesService.update(sysActivities);
	}

	@Test
	public void testFindById() {
		this.add();
		Assert.assertNotNull(sysActivityActivitiesService.findById(MyTestConstant.ID));
	}

	@Test
	public void testFindAll() {
		this.add();
		Assert.assertNotNull(sysActivityActivitiesService.findAll());
	}

	@Test
	public void testpagelist() {
		this.add();
		BasePagination page = new BasePagination();
		Assert.assertNotNull(sysActivityActivitiesService.getListPage(page));
	}

}
