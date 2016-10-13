package com.fushionbaby.member;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.member.model.MemberSkuCollect;
import com.fushionbaby.member.service.MemberSkuCollectService;

/**
 * 
 * @author King
 * 
 */
public class MemberSkuCollectTest {

	private MemberSkuCollectService<MemberSkuCollect> memberSkuCollectService;

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-member-mybatis.xml" });
		memberSkuCollectService = (MemberSkuCollectService) ac
				.getBean("memberSkuCollectServiceImpl");
	}

	@Test
	public void testAdd() {
		MemberSkuCollect skuCollect = new MemberSkuCollect();
		BigDecimal bigDecimal = new BigDecimal("123.234");

		skuCollect.setId(MyTestConstant.ID);
		skuCollect.setMemberId(1l);
		skuCollect.setSkuCode("1");
		skuCollect.setCurrentPrice(bigDecimal);
		skuCollect.setRetailPrice(new BigDecimal("120.20"));
		skuCollect.setIsAttention(CommonConstant.YES);
		skuCollect.setAddTime(new Date());

		System.out.println("before test insert");
		memberSkuCollectService.add(skuCollect);
		System.out.println("after  test insert");
	}

	@Test
	public void testUpdate() {
		MemberSkuCollect skuCollect = new MemberSkuCollect();
		BigDecimal bigDecimal = new BigDecimal("103.234");
		skuCollect.setMemberId(1l);

		skuCollect.setSkuCode("1L");
		skuCollect.setCurrentPrice(bigDecimal);
		skuCollect.setIsAttention(CommonConstant.YES);
		skuCollect.setAddTime(new Date());

		System.out.println("before test update ");
		memberSkuCollectService.update(skuCollect);
		System.out.println("after  test update");
	}

	@Test
	public void testFindById() {

		System.out.println("before test find one ");
		System.out.println(memberSkuCollectService.findById(MyTestConstant.ID));
		System.out.println("after  test find one ");
	}

	@Test
	public void testDeleteById() {

		System.out.println("before test delete by id ");
		memberSkuCollectService.deleteById(MyTestConstant.ID);
		System.out.println("after  test delete by id");
	}
}
