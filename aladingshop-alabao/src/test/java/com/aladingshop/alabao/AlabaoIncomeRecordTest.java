package com.aladingshop.alabao;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.alabao.model.AlabaoConsumeRecord;
import com.aladingshop.alabao.model.AlabaoIncomeRecord;
import com.aladingshop.alabao.service.AlabaoIncomeRecordService;
import com.fushionbaby.common.constants.MyTestConstant;

public class AlabaoIncomeRecordTest {
	private AlabaoIncomeRecordService<AlabaoIncomeRecord> alabaoIncomeRecordService;
	
	@SuppressWarnings({"resource", "unchecked"})
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-alabao-mybatis.xml" });
		alabaoIncomeRecordService = (AlabaoIncomeRecordService<AlabaoIncomeRecord>) ac.getBean("alabaoIncomeRecordServiceImpl");
	}
	
	public void add() {
		AlabaoIncomeRecord alabaoIncomeRecord = new AlabaoIncomeRecord();
		alabaoIncomeRecord.setIncomeMoney(new BigDecimal("12"));
		alabaoIncomeRecord.setAccount("12121");
		alabaoIncomeRecord.setMemberId(2L);
		alabaoIncomeRecordService.add(alabaoIncomeRecord);
	}
	
	@Test
	public void testAdd() {
		add();
	}


	@Test
	public void testFindByMemberId() {
		add();
		List<AlabaoIncomeRecord> list = alabaoIncomeRecordService.findAllByMemberId((long)2);
		System.out.println(list.size());
	}
	
	@Test
	public void testFindByAccount() {
		add();
		List<AlabaoIncomeRecord> list = alabaoIncomeRecordService.findAllByAccount("12121");

		System.out.println(list.size());
	}
	
}
