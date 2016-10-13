
package com.fushionbaby.sysactivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.sysactivity.model.LotteryDetail;
import com.fushionbaby.sysactivity.service.LotterDetailService;

public class LotteryDetailTest {

	private LotterDetailService<LotteryDetail> LotteryDetailService;

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]
				{MyTestConstant.TEST_SPRING_CONFIG,"classpath:conf/spring-sysactivity-mybatis.xml"});
		LotteryDetailService = (LotterDetailService)ac.getBean("lotterDetailServiceImpl");
	}


	@After
	public void TestDeleteById() {
		this.LotteryDetailService.deleteById(MyTestConstant.ID);
	}


	@Test
	public void Testadd() {
		this.add();
	}
	public void add() {
		LotteryDetail l = new LotteryDetail();
		l.setId(MyTestConstant.ID);
		l.setLoginName("zhangsan");
		l.setMemberId(24l);
		l.setPrizeId(25l);
		this.LotteryDetailService.add(l);

	}


}
