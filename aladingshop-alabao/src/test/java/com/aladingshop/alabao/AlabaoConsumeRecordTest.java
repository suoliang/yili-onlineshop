package com.aladingshop.alabao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.alabao.model.AlabaoConsumeRecord;
import com.aladingshop.alabao.service.AlabaoConsumeRecordService;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;

public class AlabaoConsumeRecordTest {

	private AlabaoConsumeRecordService<?> alabaoConsumeRecordService;

	@SuppressWarnings("resource")
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-alabao-mybatis.xml" });
		alabaoConsumeRecordService = (AlabaoConsumeRecordService) ac.getBean("alabaoConsumeRecordServiceImpl");
	}

	
	public void add() {
		AlabaoConsumeRecord alabao = new AlabaoConsumeRecord();
		alabao.setAccount(MyTestConstant.ID+"");
		alabao.setConsumeMoney(BigDecimal.valueOf(12.5));
		alabao.setCreateTime(new Date());
		alabao.setId(MyTestConstant.ID);
		alabao.setIsSuccess("y");
		alabao.setMemberId(MyTestConstant.ID);
		alabao.setOrderCode(MyTestConstant.ID+"");
	
		alabaoConsumeRecordService.add(alabao);
	}

	@Test
	public void testAdd() {
		add();
	}

	@After
	public void testDeleteById() {
		alabaoConsumeRecordService.deleteById(MyTestConstant.ID);
	}
	
	@Test
	public void testUpdate() {
		add();
		AlabaoConsumeRecord alabao = new AlabaoConsumeRecord();
		alabao.setAccount(MyTestConstant.ID+"");
		alabao.setConsumeMoney(BigDecimal.valueOf(12.5));
		alabao.setCreateTime(new Date());
		alabao.setId(MyTestConstant.ID);
		alabao.setIsSuccess("y");
		alabao.setMemberId(MyTestConstant.ID);
		alabao.setOrderCode(MyTestConstant.ID+"");
		alabaoConsumeRecordService.updateByAccount(alabao);
	}


	@Test
	public void testFindByList() {
		add();
		BasePagination pageParams=new BasePagination();
		alabaoConsumeRecordService.getListPage(pageParams);
	}

	@Test
	public void testFindById() {
		add();
		alabaoConsumeRecordService.findById(MyTestConstant.ID);
		alabaoConsumeRecordService.findByAccount(MyTestConstant.ID+"");
	}
	

	@Test
	public void testFindByMemberId() {
		add();
		List<AlabaoConsumeRecord> list = alabaoConsumeRecordService.findAllByMemberId(MyTestConstant.ID);
		System.out.println(list.size());
	}
	
	@Test
	public void testFindByAccount() {
		add();
		List<AlabaoConsumeRecord> list = alabaoConsumeRecordService.findAllByAccount(MyTestConstant.ID.toString());

		System.out.println(list.size());
	}

}
