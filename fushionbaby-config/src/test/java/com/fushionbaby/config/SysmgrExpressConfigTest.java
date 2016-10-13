package com.fushionbaby.config;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.config.model.SysmgrExpressConfig;
import com.fushionbaby.config.service.SysmgrExpressConfigService;

/***
 * 测试用例  快递物流信息配置
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月3日上午10:16:35
 */
public class SysmgrExpressConfigTest {

	private SysmgrExpressConfigService sacs;

	@Before
	public void before() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-config-mybatis.xml" });
		sacs = (SysmgrExpressConfigService) context
				.getBean("sysmgrExpressConfigServiceImpl");
	}

	public void add() {
		SysmgrExpressConfig sac = new SysmgrExpressConfig();
		sac.setExpressCompanyCode("yuantong");
		sac.setExpressCompanyName("圆通快递");
		sacs.add(sac);
	}

	@Test
	public void testAdd() {
		this.add();
	}

	@Test
	public void testFindAll() {
		this.add();
	}

//	@Test
//	public void testlistpage() {
//		this.add();
//		BasePagination page = new BasePagination();
//		Assert.assertNotNull(sacs.getListPage(page));
//	}

	@Test
	public void testFindByCode() {
		//this.add();
		SysmgrExpressConfig sys=sacs.findByExpressCompanyName("圆通快递");
		System.out.println(sys.getExpressCompanyName());
	}
	
	@Test
	public void update(){
		SysmgrExpressConfig sysmgrExpressConfig = new SysmgrExpressConfig();
		sysmgrExpressConfig.setId(2L);
		
		//sas.update(sysmgrStoreApply);
	}

}
