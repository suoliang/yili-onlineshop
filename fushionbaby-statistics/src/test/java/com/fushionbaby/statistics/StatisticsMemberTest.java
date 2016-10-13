package com.fushionbaby.statistics;

import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.statistics.model.StatisticsMember;
import com.fushionbaby.statistics.service.StatisticsMemberService;
/**
 * 
 * @author cyla
 *
 */
public class StatisticsMemberTest {
	
	private StatisticsMemberService<StatisticsMember> statisticsMemberService;
	
	@SuppressWarnings("unchecked")
	@Before
	public void before(){
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-statistics-mybatis.xml"
		});
		statisticsMemberService = (StatisticsMemberService<StatisticsMember>) ac.getBean("statisticsMemberServiceImpl");
	}
	
	@Ignore
	public void add(){
		StatisticsMember statisticsMember = new StatisticsMember();
		statisticsMember.setId(MyTestConstant.ID);
		statisticsMember.setAndroidCode("0");
		statisticsMember.setCmsCode("1");
		statisticsMember.setCreateTime(new Date());
		statisticsMember.setDefaultChannel("3");
		statisticsMember.setIosCode("4");
		statisticsMember.setQqChannel("5");
		statisticsMember.setSinaChannel("6");
		statisticsMember.setTodayMemberNumber(1L);
		statisticsMember.setTotalNumber(2L);
		statisticsMember.setWebCode("7");
		statisticsMember.setWxChannel("8");
		statisticsMemberService.add(statisticsMember);
	}
	
	@Test
	public void testAdd() {
		this.add();
	}
	
	
}
