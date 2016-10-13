package com.fushionbaby.config.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fushionbaby.config.dao.SysmgrAlabaoConfigDao;
import com.fushionbaby.config.model.SysmgrAlabaoConfig;
import com.fushionbaby.config.service.SysmgrAlabaoConfigService;

/***
 * @description 如意消费卡余额和折扣的关联Service实现
 * @author 索亮
 * @date 2016年2月26日下午3:52:38
 */
@Service
public class SysmgrAlabaoConfigServiceImpl implements SysmgrAlabaoConfigService<SysmgrAlabaoConfig> {

	@Autowired
	private SysmgrAlabaoConfigDao sysmgrAlabaoConfigDao;
	
	public SysmgrAlabaoConfig findByMinMaxValue(String value) {
		Map<String, Object> mapParams = new HashMap<String, Object>();
		mapParams.put("value", value);
		return sysmgrAlabaoConfigDao.findByMinMaxValue(mapParams);
	}

}
