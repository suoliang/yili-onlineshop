package com.fushionbaby.member.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.config.model.SysmgrGlobalConfig;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.member.dao.MemberDeviceDao;
import com.fushionbaby.member.model.MemberDevice;
import com.fushionbaby.member.service.MemberDeviceService;

@Service
public class MemberDeviceServiceImpl implements MemberDeviceService<MemberDevice> {
	
	@Autowired
	private MemberDeviceDao memberDeviceDao;
	@Autowired
	private SysmgrGlobalConfigService globalConfigService;
	
	public void add(MemberDevice entity) throws DataAccessException {
		this.memberDeviceDao.add(entity);
		
	}

	public void deleteById(Long id) throws DataAccessException {
		this.memberDeviceDao.deleteById(id);
	}

	public void update(MemberDevice entity) throws DataAccessException {
		this.memberDeviceDao.update(entity);
		
	}

	public MemberDevice findById(Long id) throws DataAccessException {
		return this.memberDeviceDao.findById(id);
	}

	public BasePagination getListPage(BasePagination page) {
		Map<String,Object> params = new HashMap<String, Object>();
		params = page.getSearchParamsMap();
		Integer total = memberDeviceDao.getTotal(params);
		page.setCurrentTotal(total);
		if(total > 0){
			List<MemberDevice> result = memberDeviceDao.getPageList(params);
			page.setResult(result);
		}else{
			page.setResult(new ArrayList<MemberDevice>());
		}
		return page;
	}

	public List<MemberDevice> findByMac(String mac) {
		return this.memberDeviceDao.findByMac(mac);
	}

	public String getMemDevRegisterInfo(String mac) {
		String msg = "";
		List<MemberDevice> list = memberDeviceDao.findByMac(mac);
		List<MemberDevice> todayList = new ArrayList<MemberDevice>();
		//查询总的设定的次数限制
		SysmgrGlobalConfig config1 = globalConfigService.findByCode(GlobalConfigConstant.MAX_COUNT_TOTAL);
		Integer totalRegisterCount = 10;
		if(config1 != null && StringUtils.isNotBlank(config1.getValue())){
			totalRegisterCount = Integer.valueOf(config1.getValue());
		}
		if(list.size() >= totalRegisterCount){
			msg = "该设备注册次数超过"+totalRegisterCount+"次！";
			return msg;
		}
		//得到当天注册设备记录
		for (MemberDevice memberDevice : list) {
			if(DateFormat.dateTimeToDateString(new Date()).equals(DateFormat.dateTimeToDateString(memberDevice.getCreateTime()))){
				todayList.add(memberDevice);
			}
		}
		
		SysmgrGlobalConfig config2 = globalConfigService.findByCode(GlobalConfigConstant.MAX_COUNT_ONEDAY);
		Integer oneDayRegisterCount = 3;
		if(config2 != null && StringUtils.isNotBlank(config2.getValue())){
			oneDayRegisterCount = Integer.valueOf(config2.getValue());
		}
		if(todayList.size() >= oneDayRegisterCount){
			msg = "该设备注册次数超过"+oneDayRegisterCount+"次！";
			return msg;
		}
		return msg;
	}

	
}
