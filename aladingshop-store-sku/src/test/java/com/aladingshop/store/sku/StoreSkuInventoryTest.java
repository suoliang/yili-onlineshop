package com.aladingshop.store.sku;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.store.sku.model.StoreSkuInventory;
import com.aladingshop.store.sku.service.StoreSkuInventoryService;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;

public class StoreSkuInventoryTest {

	private StoreSkuInventoryService storeSkuInventoryService;

	@SuppressWarnings("resource")
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-store-sku-mybatis.xml" });
		storeSkuInventoryService = (StoreSkuInventoryService) ac.getBean("storeSkuInventoryServiceImpl");
	}

	
	public void add() {
		StoreSkuInventory storeSkuInventory = new StoreSkuInventory();
		storeSkuInventory.setId(MyTestConstant.ID);
		storeSkuInventory.setProductCode(MyTestConstant.ID+"");
		storeSkuInventory.setSkuColor(MyTestConstant.ID+"");
		storeSkuInventory.setSkuSize(MyTestConstant.ID+"");
		storeSkuInventory.setSkuCode(MyTestConstant.ID+"");
		storeSkuInventory.setAvailableQty(1);
		storeSkuInventory.setStoreCode(MyTestConstant.ID+"");
		storeSkuInventory.setUpdateId(MyTestConstant.ID);
		storeSkuInventoryService.add(storeSkuInventory);
	}

	@Test
	public void testAdd() {
		add();
	}

	@After
	public void testDeleteById() {
		storeSkuInventoryService.deleteById(MyTestConstant.ID);
	}
	

	@Test
	public void testUpdate() {
		add();
		StoreSkuInventory storeSkuInventory = new StoreSkuInventory();
		storeSkuInventory.setId(MyTestConstant.ID);
		storeSkuInventory.setProductCode(MyTestConstant.ID+"");
		storeSkuInventory.setSkuColor(MyTestConstant.ID+"");
		storeSkuInventory.setSkuSize(MyTestConstant.ID+"");
		storeSkuInventory.setSkuCode(MyTestConstant.ID+"");
		storeSkuInventory.setAvailableQty(1);
		storeSkuInventory.setStoreCode(MyTestConstant.ID+"");
		storeSkuInventory.setUpdateId(MyTestConstant.ID);
		storeSkuInventoryService.updateById(storeSkuInventory);
	}


	@Test
	public void testFindByList() {
		add();
		BasePagination pageParams=new BasePagination();
		storeSkuInventoryService.getListPage(pageParams);
	}

	@Test
	public void testFindById() {
		add();
		storeSkuInventoryService.findById(MyTestConstant.ID);
	}

}
