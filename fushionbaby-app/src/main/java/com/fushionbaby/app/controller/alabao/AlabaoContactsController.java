package com.fushionbaby.app.controller.alabao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.model.AlabaoContacts;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.service.AlabaoContactsService;
import com.fushionbaby.app.dto.AlabaoContactsDto;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Controller
@RequestMapping("/alabaoContacts")
public class AlabaoContactsController {

	private static final Log LOGGER = LogFactory.getLog(AlabaoContactsController.class);
	
	@Autowired
	private AlabaoContactsService<AlabaoContacts> alabaoContactsService;
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	@Autowired
	private SysmgrGlobalConfigService sysmgrGlobalConfigService;
	
	/***
	 * 获取如意宝常用联系人
	 * @param data 如意宝登录标记号alabaoSid
	 * @param mac
	 * @return 常用联系人的姓名，账户（电话号码）
	 */
	@ResponseBody
	@RequestMapping("alabaoContactsList")
	public Object getAlabaoContacts(
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac){
		
		try {
			LOGGER.info("注册如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String alabaoSid = json.getAsJsonObject().get("alabaoSid").getAsString();
			String pageSize = sysmgrGlobalConfigService.findByCode("Contacts_Page_Size").getValue();
			
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("未登录或重新登录");
			}
			
			Map<String,String> params = new HashMap<String, String>();
			
			params.put("account", alabaoUserDto.getAccount());
			BasePagination page = new BasePagination();
			page.setLimit(Integer.valueOf(pageSize));
			page.setParams(params);
			
			page = alabaoContactsService.getListPage(page);
			@SuppressWarnings("unchecked")
			List<AlabaoContacts> alabaoContactsList = (List<AlabaoContacts>) page.getResult();
			
			List<AlabaoContactsDto> list = new ArrayList<AlabaoContactsDto>();
			for (AlabaoContacts alabaoContacts : alabaoContactsList) {
				AlabaoContactsDto alabaoContactsDto = new AlabaoContactsDto();
				AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(alabaoContacts.getLinkAccount());
				alabaoContactsDto.setLinkAccount(alabaoContacts.getLinkAccount());
				alabaoContactsDto.setTrueName(alabaoAccount.getTrueName());
				list.add(alabaoContactsDto);
			}
			
			return Jsonp_data.success(list);
		} catch (Exception e) {
			LOGGER.error("查询如意宝常用联系人出错！", e);
			return Jsonp.error("查询如意宝常用联系人出错");
		} 
		
	}
}
