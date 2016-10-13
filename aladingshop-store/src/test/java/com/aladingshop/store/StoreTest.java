package com.aladingshop.store;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.store.model.Store;
import com.aladingshop.store.service.StoreService;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;

public class StoreTest {

	private StoreService storeService;

	@SuppressWarnings("resource")
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-store-mybatis.xml" });
		storeService = (StoreService) ac.getBean("storeServiceImpl");
	}

	
	public void add() {
		Store store = new Store();
		store.setId(MyTestConstant.ID);
		store.setCreateId(MyTestConstant.ID);
		store.setCreateTime(new Date());
		store.setIsDeleted("y");
		store.setLatitude(12.5f);
		store.setLongitude(12.5f);
		store.setName(MyTestConstant.ID+"");
		store.setNumber(MyTestConstant.ID+"");
		store.setStatus(1);
//		store.setUpdateId(MyTestConstant.ID);
//		store.setUpdateTime(new Date());
		storeService.add(store);
	}

	@Test
	public void testAdd() {
		add();
	}

	@After
	public void testDeleteById() {
		storeService.deleteById(MyTestConstant.ID);
	}
	

	@Test
	public void testUpdate() {
		add();
		Store store = new Store();
		store.setId(MyTestConstant.ID);
		store.setCreateId(MyTestConstant.ID);
		store.setCreateTime(new Date());
		store.setIsDeleted("y");
		store.setLatitude(12.5f);
		store.setLongitude(12.5f);
		store.setName(MyTestConstant.ID+"");
		store.setNumber(MyTestConstant.ID+"");
		store.setStatus(1);
		store.setUpdateId(MyTestConstant.ID);
		store.setUpdateTime(new Date());
	}


	@Test
	public void testFindByList() {
		add();
		BasePagination pageParams=new BasePagination();
		storeService.getListPage(pageParams);
	}

	@Test
	public void testFindById() {
		add();
		storeService.findById(MyTestConstant.ID);
	}

}
