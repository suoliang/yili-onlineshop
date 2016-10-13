package com.aladingshop.store.sku;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.store.sku.model.StoreSku;
import com.aladingshop.store.sku.service.StoreSkuService;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;

public class StoreSkuTest {

	private StoreSkuService storeSkuService;

	@SuppressWarnings("resource")
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-store-sku-mybatis.xml" });
		storeSkuService = (StoreSkuService) ac.getBean("storeSkuServiceImpl");
	}

	
	public void add() {
		StoreSku storeSku = new StoreSku();
		storeSku.setId(MyTestConstant.ID);
		storeSku.setIsSupport("y");
		storeSku.setSkuCode(MyTestConstant.ID+"");
		storeSku.setStatus("1");
		storeSku.setStoreCode(MyTestConstant.ID+"");
		storeSku.setUpdateId(MyTestConstant.ID);
		storeSkuService.add(storeSku);
	}

	@Test
	public void testAdd() {
		add();
	}

	@After
	public void testDeleteById() {
		storeSkuService.deleteById(MyTestConstant.ID);
	}
	
	@Test
	public void testDeleteByStoreCodeAndSkuCode() {
		storeSkuService.deleteByStoreCodeAndSkuCode(MyTestConstant.ID+"", MyTestConstant.ID+"");
	}

	@Test
	public void testFindByStoreCodeAndSkuCode() {
		add();
		storeSkuService.findByStoreCodeAndSkuCode(MyTestConstant.ID+"", MyTestConstant.ID+"");
	}
	
	@Test
	public void testUpdate() {
		add();
		StoreSku storeSku = new StoreSku();
		storeSku.setId(MyTestConstant.ID);
		storeSku.setIsSupport("y");
		storeSku.setSkuCode(MyTestConstant.ID+"");
		storeSku.setStatus("1");
		storeSku.setStoreCode(MyTestConstant.ID+"");
		storeSku.setUpdateId(MyTestConstant.ID);
		storeSkuService.updateById(storeSku);
		storeSkuService.updateByStoreCodeAndSkuCode(storeSku);
	}


	@Test
	public void testFindByList() {
		add();
		BasePagination pageParams=new BasePagination();
		storeSkuService.getListPage(pageParams);
	}

	@Test
	public void testFindById() {
		add();
		storeSkuService.findById(MyTestConstant.ID);
	}

}
