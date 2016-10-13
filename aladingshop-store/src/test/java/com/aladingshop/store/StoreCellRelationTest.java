package com.aladingshop.store;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.store.model.Store;
import com.aladingshop.store.model.StoreCellRelation;
import com.aladingshop.store.service.StoreCellRelationService;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;

public class StoreCellRelationTest {

	private StoreCellRelationService storeCellRelationService;

	@SuppressWarnings("resource")
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-store-mybatis.xml" });
		storeCellRelationService = (StoreCellRelationService) ac.getBean("storeCellRelationServiceImpl");
	}

	
	public void add() {
		StoreCellRelation storeCellRelation = new StoreCellRelation();
		storeCellRelation.setId(MyTestConstant.ID);
		storeCellRelation.setCellCode(MyTestConstant.ID+"");
		storeCellRelation.setStoreCode(MyTestConstant.ID+"");
		storeCellRelation.setCreateTime(new Date());
		storeCellRelation.setCreateId(1l);
		storeCellRelationService.add(storeCellRelation);
	}

	@Test
	public void testAdd() {
		add();
	}

	@After
	public void testDeleteById() {
		storeCellRelationService.deleteById(MyTestConstant.ID);
	}
	

	@Test
	public void testFindByStoreCode() {
		add();
		storeCellRelationService.findByStoreCode(MyTestConstant.ID+"");
	}
	
	@Test
	public void testFindByAreaCode() {
		add();
		storeCellRelationService.findByAreaCode(MyTestConstant.ID+"");
	}

}
