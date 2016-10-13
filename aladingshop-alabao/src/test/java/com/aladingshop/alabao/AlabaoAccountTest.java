package com.aladingshop.alabao;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;

public class AlabaoAccountTest {

	private AlabaoAccountService alabaoAccountService;

	@SuppressWarnings("resource")
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-alabao-mybatis.xml" });
		alabaoAccountService = (AlabaoAccountService) ac.getBean("alabaoAccountServiceImpl");
	}

	
	public void add() {
		AlabaoAccount alabao = new AlabaoAccount();
		alabao.setAccount(MyTestConstant.ID+"");
		alabao.setBalance(BigDecimal.valueOf(12.5));
		alabao.setId(MyTestConstant.ID);
		alabao.setIdentityCardNo(MyTestConstant.ID+"");
		alabao.setLockedBalance(BigDecimal.valueOf(12.5));
		alabao.setLoginPassword(MyTestConstant.ID+"");
		alabao.setMemberId(MyTestConstant.ID);
		alabao.setMobilePhone(MyTestConstant.ID+"");
		alabao.setPayPassword(MyTestConstant.ID+"");
		alabao.setStatus("1");
		alabao.setTotalIncome(BigDecimal.valueOf(12.5));
		alabao.setUpdateTime(new Date());
		alabao.setYesterdayIncome(BigDecimal.valueOf(12.5));
		alabaoAccountService.add(alabao);
		alabao.setStatus("2");
		alabaoAccountService.updateByMemberId(alabao);
	}

	@Test
	public void testAdd() {
		add();
	}

	@After
	public void testDeleteById() {
		alabaoAccountService.deleteById(MyTestConstant.ID);
	}
	
	@Test
	public void testDeleteByAccount() {
		alabaoAccountService.deleteByAccount(MyTestConstant.ID+"");;
	}

	@Test
	public void testUpdate() {
		add();
		AlabaoAccount alabao = new AlabaoAccount();
		alabao.setAccount(MyTestConstant.ID+"");
		alabao.setBalance(BigDecimal.valueOf(12.5));
		alabao.setId(MyTestConstant.ID);
		alabao.setIdentityCardNo(MyTestConstant.ID+"");
		alabao.setLockedBalance(BigDecimal.valueOf(12.5));
		alabao.setLoginPassword(MyTestConstant.ID+"");
		alabao.setMemberId(MyTestConstant.ID);
		alabao.setMobilePhone(MyTestConstant.ID+"");
		alabao.setPayPassword(MyTestConstant.ID+"");
		alabao.setStatus("1");
		alabao.setTotalIncome(BigDecimal.valueOf(12.5));
		alabao.setUpdateTime(new Date());
		alabao.setYesterdayIncome(BigDecimal.valueOf(12.5));
		alabao = alabaoAccountService.findByMemberId(MyTestConstant.ID);
		alabaoAccountService.updateByAccount(alabao);
	}


	@Test
	public void testFindByList() {
		add();
		BasePagination pageParams=new BasePagination();
		alabaoAccountService.getListPage(pageParams);
	}

	@Test
	public void testFindById() {
		add();
		alabaoAccountService.findById(MyTestConstant.ID);
		alabaoAccountService.findByAccount(MyTestConstant.ID+"");
	}

}
