package com.aladingshop.sku.cms;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.aladingshop.sku.cms.model.SkuCategoryImage;
import com.aladingshop.sku.cms.service.SkuCategoryImageService;
import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;

/***
 * 商品分类图片
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月6日下午1:14:12
 */
public class SkuCategoryImageTest {

	
    private SkuCategoryImageService<SkuCategoryImage> skuCategoryImageService;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sku-cms-mybatis.xml" });
		skuCategoryImageService = (SkuCategoryImageService<SkuCategoryImage>) ac.getBean("skuCategoryImageServiceImpl");
	}
	@After
	public void testdeleteById() {

		this.skuCategoryImageService.deleteById(MyTestConstant.ID);
	}

	@Test
	public void testadd() {
		this.add();
	}
	
	@Test
	public void testupdate() {
		this.add();
		SkuCategoryImage skuImage = this.skuCategoryImageService.findById(MyTestConstant.ID);
		skuImage.setCategoryCode("002");
		this.skuCategoryImageService.update(skuImage);

	}


	@Test
	public void testfindById() {
		this.add();
		Assert.assertNotNull(this.skuCategoryImageService.findById(MyTestConstant.ID));
	}



	
	 @Test 
	 public void testgetListPage() { 
		 BasePagination page = new	  BasePagination();
		 Assert.assertNotNull(this.skuCategoryImageService.getListPage(page));
	 }
	

	public void add() {
		SkuCategoryImage image=new SkuCategoryImage();
		image.setCategoryCode("001");
		image.setId(MyTestConstant.ID);
		this.skuCategoryImageService.add(image);
	}


}
