package com.fushionbaby.core.card.web;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.core.model.ShoppingCartSku;
import com.fushionbaby.core.service.ShoppingCartSkuUserService;

/**
 * @author 张明亮
 *
 */
public class SoShoppingCartSkuWebUserTest {

	private ShoppingCartSkuUserService<ShoppingCartSku> soShoppingCartSkuWebService;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
				MyTestConstant.TEST_SPRING_CONFIG, "classpath:conf/spring-*-mybatis.xml" });
		soShoppingCartSkuWebService = (ShoppingCartSkuUserService<ShoppingCartSku>) context
				.getBean("soShoppingCartSkuWebUserServiceImpl");
	}


	/**
	 * 购物车行增加,为购物车添加一行商品
	 */
	@Test
	public void testAdd() {
		this.add();
	}

	/**
	 * 根据购物车ID,和行ID获得具体的行信息
	 */
	@Test
	public void testfindByCartIdAndItemId() {
		ShoppingCartSku soShoppingCartSku = soShoppingCartSkuWebService.findBySkuCodeAndMemberId("1", 1L);
		System.out.println(soShoppingCartSku);
	}

	@Test
	public void testFindById() {
		this.add();
		ShoppingCartSku soShoppingCartSku = soShoppingCartSkuWebService.findById(30L);
		System.out.println(soShoppingCartSku);
		Assert.assertNotNull(soShoppingCartSkuWebService.findById(MyTestConstant.ID));
	}


	public void add() {
		ShoppingCartSku soShoppingCartSku = new ShoppingCartSku();
		soShoppingCartSku.setId(MyTestConstant.ID);
		soShoppingCartSkuWebService.add(soShoppingCartSku);
	}
}
