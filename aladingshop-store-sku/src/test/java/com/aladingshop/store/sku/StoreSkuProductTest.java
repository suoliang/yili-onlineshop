package com.aladingshop.store.sku;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.store.sku.model.StoreSkuProduct;
import com.aladingshop.store.sku.service.StoreSkuProductService;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;

public class StoreSkuProductTest {

	private StoreSkuProductService storeSkuProductService;

	@SuppressWarnings("resource")
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-store-sku-mybatis.xml" });
		storeSkuProductService = (StoreSkuProductService) ac.getBean("storeSkuProductServiceImpl");
	}

	
	public void add() {
		StoreSkuProduct storeSkuProduct = new StoreSkuProduct();
		storeSkuProduct.setId(MyTestConstant.ID);
		storeSkuProduct.setIsSupport("y");
		storeSkuProduct.setSkuCode(MyTestConstant.ID+"");
		storeSkuProduct.setStatus("1");
		storeSkuProduct.setStoreCode(MyTestConstant.ID+"");
		storeSkuProduct.setUpdateId(MyTestConstant.ID);
		storeSkuProductService.add(storeSkuProduct);
	}

	@Test
	public void testAdd() {
		add();
	}

	@After
	public void testDeleteById() {
		storeSkuProductService.deleteById(MyTestConstant.ID);
	}
	
	@Test
	public void testDeleteByStoreCodeAndSkuCode() {
		storeSkuProductService.deleteByStoreCodeAndProductCode(MyTestConstant.ID+"", MyTestConstant.ID+"");
	}

	@Test
	public void testFindByStoreCodeAndSkuCode() {
		add();
		storeSkuProductService.findByStoreCodeAndProductCode(MyTestConstant.ID+"", MyTestConstant.ID+"");
	}
	
	@Test
	public void testUpdate() {
		add();
		StoreSkuProduct storeSkuProduct = new StoreSkuProduct();
		storeSkuProduct.setId(MyTestConstant.ID);
		storeSkuProduct.setIsSupport("y");
		storeSkuProduct.setSkuCode(MyTestConstant.ID+"");
		storeSkuProduct.setStatus("1");
		storeSkuProduct.setStoreCode(MyTestConstant.ID+"");
		storeSkuProduct.setUpdateId(MyTestConstant.ID);
		storeSkuProductService.updateById(storeSkuProduct);
		storeSkuProductService.updateByStoreCodeAndProductCode(storeSkuProduct);
	}


	@Test
	public void testFindByList() {
		add();
		BasePagination pageParams=new BasePagination();
		storeSkuProductService.getListPage(pageParams);
	}

	@Test
	public void testFindById() {
		add();
		storeSkuProductService.findById(MyTestConstant.ID);
	}

}
