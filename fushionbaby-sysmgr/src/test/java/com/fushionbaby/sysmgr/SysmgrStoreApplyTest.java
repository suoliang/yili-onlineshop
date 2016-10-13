package com.fushionbaby.sysmgr;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fushionbaby.common.constants.MyTestConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysmgr.model.SysmgrStoreApply;
import com.fushionbaby.sysmgr.service.SysmgrStoreApplyService;

public class SysmgrStoreApplyTest {
	
	private SysmgrStoreApplyService<SysmgrStoreApply> sas;
	
	@Before
	@SuppressWarnings("unchecked")
	public void before() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[] { MyTestConstant.TEST_SPRING_CONFIG,
						"classpath:conf/spring-sysmgr-mybatis.xml" });
		sas = (SysmgrStoreApplyService<SysmgrStoreApply>) ac.getBean("sysmgrStoreApplyServiceImpl");
	}
	
	@Test
	public void add(){
		SysmgrStoreApply sysmgrStoreApply = new SysmgrStoreApply();
		sysmgrStoreApply.setName("张三2");
		sysmgrStoreApply.setCity("上海2");
		sysmgrStoreApply.setAddress("minhangqusanlinglu2");
		sysmgrStoreApply.setApplyTime(new Date());
		sysmgrStoreApply.setDealName("lisi");
		sysmgrStoreApply.setIsDeal("y");
		sysmgrStoreApply.setPhone("15827777777");
		
		sas.add(sysmgrStoreApply);
	}
	
	
	@Test
	public void update(){
		SysmgrStoreApply sysmgrStoreApply = new SysmgrStoreApply();
		sysmgrStoreApply.setId(2L);
		sysmgrStoreApply.setName("张三333");
		sysmgrStoreApply.setCity("上海333");
		sysmgrStoreApply.setAddress("minhangqusanlinglu333");
		sysmgrStoreApply.setApplyTime(new Date());
		sysmgrStoreApply.setDealName("lisi");
		sysmgrStoreApply.setIsDeal("y");
		sysmgrStoreApply.setPhone("15827777777");
		sas.update(sysmgrStoreApply);
	}
	
	@Test
	public void findById(){
		SysmgrStoreApply sys = sas.findById(3L);
		System.out.println(sys);
		
	}
	
	@Test
	public void delete(){
		sas.deleteById(2L);
	}
	
	@Test
	public void findAll(){
		List<SysmgrStoreApply> list = sas.findAll();
		System.out.println(list);
	}
	
	
	@Test
	public void listPage(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("phone", "15827777777");
		BasePagination page = new BasePagination();
		page.setParams(map);
		page =sas.getListPage(page);
		List<SysmgrStoreApply> list = (List<SysmgrStoreApply>) page.getResult();
		System.out.println(list);
	}
	
}
