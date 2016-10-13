package com.aladingshop.alabao;

import java.math.BigDecimal;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;
import com.aladingshop.alabao.model.AlabaoConfig;
import com.aladingshop.alabao.service.AlabaoConfigService;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.MyTestConstant;

public class AlabaoConfigTest {

	private AlabaoConfigService<AlabaoConfig> alabaoConfigService;

	@SuppressWarnings({"resource", "unchecked"})
	@Before
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-alabao-mybatis.xml" });
		alabaoConfigService = (AlabaoConfigService<AlabaoConfig>) ac.getBean("alabaoConfigServiceImpl");
	}

	
	public void add() {
		AlabaoConfig alabao = new AlabaoConfig();
		alabao.setId(MyTestConstant.ID);
		alabao.setRate(BigDecimal.valueOf(12.5));
		alabao.setRebateTime(new Date());
		alabao.setUpdateId(MyTestConstant.ID);
		alabao.setUpdateTime(new Date());
		alabaoConfigService.add(alabao);
	}

	@Test
	public void testAdd() {
		add();
	}

	@After
	public void testDeleteById() {
		//alabaoConfigService.deleteById(MyTestConstant.ID);
	}
	

	@Test
	public void testUpdate() {
		add();
		AlabaoConfig alabao = new AlabaoConfig();
		alabao.setId(MyTestConstant.ID);
		alabao.setRate(BigDecimal.valueOf(12.5));
		alabao.setRebateTime(new Date());
		alabao.setUpdateId(MyTestConstant.ID);
		alabao.setUpdateTime(new Date());
		alabaoConfigService.updateById(alabao);
	}

	@Test
	public void testFindById() {
		add();
		Assert.notNull(alabaoConfigService.findById(MyTestConstant.ID));
	}
	
	@Test
	public void testFindRateCode(){
		AlabaoConfig alabaoConfig = alabaoConfigService.findByRateCode(GlobalConfigConstant.RATECODE);
		System.out.println(alabaoConfig.getRate());
	}

}
