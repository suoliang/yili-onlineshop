package com.fushionbaby.sku;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sku.model.SkuImage;
import com.fushionbaby.sku.service.SkuImageService;

/**
 * 
 * @author xupeijun
 * 
 */
public class SkuImageTest {
	@Autowired
	private SkuImageService<SkuImage> skuImageService;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sku-mybatis.xml" });
		skuImageService = (SkuImageService<SkuImage>) ac.getBean("skuImageServiceImpl");
	}

	@After
	public void testdeleteById() {

		this.skuImageService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testadd() {
		this.add();
	}

	@Test
	public void testupdate() {
		this.add();
		SkuImage skuImage = this.skuImageService.findById(MyTestConstant.ID);
		skuImage.setSkuCode("22222222222222");
		skuImage.setImageTypeCode("33333333333");
		skuImage.setImgUrl("fushionbaby.com");
		this.skuImageService.update(skuImage);

	}


	@Test
	public void testfindById() {
		this.add();
		Assert.assertNotNull(this.skuImageService.findById(MyTestConstant.ID));
	}

	
	 @Test 
	 public void testfindBySkuId() {
		   this.add();
	    Assert.assertNotNull(this.skuImageService.findBySkuCode("3l"));
	}
	

	
	 @Test 
	 public void testgetListPage() { 
		 BasePagination page = new	  BasePagination();
		 Assert.assertNotNull(this.skuImageService.getListPage(page));
	 }
	

	public void add() {
		SkuImage skuImage = new SkuImage();
		skuImage.setId(MyTestConstant.ID);
		skuImage.setImageTypeCode("123456789");
		skuImage.setImageTypeId(123456789l);
		skuImage.setSkuCode("123456789");
		skuImage.setImgUrl("www.baidu.com");
		this.skuImageService.add(skuImage);
	}

}
