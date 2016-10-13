package com.fushionbaby.sku;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.sku.service.SkuCategoryImageService;

/***
 * 商品分类图片
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年8月6日下午1:14:12
 */
public class SkuCategoryImageTest {

	
    private SkuCategoryImageService skuCategoryImageService;

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sku-mybatis.xml" });
		skuCategoryImageService = (SkuCategoryImageService) ac.getBean("skuCategoryImageServiceImpl");
	}

@Test
    public void testfindByCategoryCode(){
	    Assert.assertNotNull(this.skuCategoryImageService.findByCategoryCode("001"));
    }

}
