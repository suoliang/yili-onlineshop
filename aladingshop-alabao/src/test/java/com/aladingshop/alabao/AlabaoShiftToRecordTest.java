package com.aladingshop.alabao;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.alabao.model.AlabaoIncomeRecord;
import com.aladingshop.alabao.model.AlabaoShiftToRecord;
import com.aladingshop.alabao.service.AlabaoShiftToRecordService;
import com.fushionbaby.common.constants.MyTestConstant;

public class AlabaoShiftToRecordTest {

	private AlabaoShiftToRecordService<AlabaoShiftToRecord> alabaoShiftToRecordService;
	
	@SuppressWarnings({"resource", "unchecked"})
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-alabao-mybatis.xml" });
		alabaoShiftToRecordService = (AlabaoShiftToRecordService<AlabaoShiftToRecord>) ac.getBean("alabaoShiftToRecordServiceImpl");
	}
	
	public void add(){
		AlabaoShiftToRecord alabaoShiftToRecord = new AlabaoShiftToRecord();
		alabaoShiftToRecord.setAccount("3423");
		alabaoShiftToRecord.setMemberId(2L);
		alabaoShiftToRecord.setShiftToAccountType("1");
		alabaoShiftToRecord.setTransferMoney(new BigDecimal("22332"));
		alabaoShiftToRecordService.add(alabaoShiftToRecord);
	}
	
	@Test
	public void testAdd() {
		add();
	}
	

	@Test
	public void testFindByMemberId() {
		add();
		List<AlabaoShiftToRecord> list = alabaoShiftToRecordService.findAllByMemberId((long)2);
		System.out.println(list.size());
	}
	
	@Test
	public void testFindByAccount() {
		add();
		List<AlabaoShiftToRecord> list = alabaoShiftToRecordService.findAllByAccount("3423");

		System.out.println(list.size());
	}

}
