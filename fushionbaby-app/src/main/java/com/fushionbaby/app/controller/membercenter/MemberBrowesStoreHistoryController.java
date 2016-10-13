package com.fushionbaby.app.controller.membercenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.store.model.Store;
import com.aladingshop.store.service.StoreService;
import com.fushionbaby.app.dto.MemberBrowesStoreHistoryDto;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.member.model.MemberBrowesStoreHistory;
import com.fushionbaby.member.service.MemberBrowesStoreHistoryService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 会员浏览门店记录
 * @author chenyingtao
 *
 */

@Controller
@RequestMapping("/browesHistory")
public class MemberBrowesStoreHistoryController {
	
	private static final Log LOGGER=LogFactory.getLog(MemberBrowesStoreHistoryController.class);
	@Autowired
	private MemberBrowesStoreHistoryService<MemberBrowesStoreHistory> memberBrowesStoreHistoryService;
	@Autowired
	private StoreService<Store> storeService;
	
	@ResponseBody
	@RequestMapping("browesStoreList")
	public Object getBrowesHistory(@RequestParam("data")String data, @RequestParam("mac")String mac){
		
		try {
			LOGGER.info("获取会员浏览门店的记录 接口action：请求报文{" + data + "}");
			
			if(!MD5Util.validMd5(data, MD5Util.getKey(), mac)){
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String sid = json.getAsJsonObject().get("sid").getAsString();
			
			UserDto userDto = (UserDto) SessionCache.get(sid);
			if(userDto == null){
				return Jsonp.noLoginError("未登录或重新登录");
			}
			Map<String,String> params = new HashMap<String, String>();
			
			params.put("memberId", userDto.getMemberId().toString());
			params.put("memberAccount", userDto.getAccount());
			
			BasePagination page = new BasePagination();
			page.setParams(params);
			
			page = memberBrowesStoreHistoryService.getListPage(page);
			@SuppressWarnings("unchecked")
			List<MemberBrowesStoreHistory> memberBrowesStoreHistoryList = (List<MemberBrowesStoreHistory>) page.getResult();
			List<MemberBrowesStoreHistoryDto> list = new ArrayList<MemberBrowesStoreHistoryDto>();
			for (MemberBrowesStoreHistory memberBrowesStoreHistory : memberBrowesStoreHistoryList) {
				MemberBrowesStoreHistoryDto memberBrowesStoreHistoryDto = new MemberBrowesStoreHistoryDto();
				memberBrowesStoreHistoryDto.setStoreCode(memberBrowesStoreHistory.getStoreCode());
				memberBrowesStoreHistoryDto.setStoreName(storeService.findByCode(memberBrowesStoreHistory.getStoreCode()).getName());
				list.add(memberBrowesStoreHistoryDto);
			}
			return Jsonp_data.success(list);
		} catch (Exception e) {
			LOGGER.error("查询浏览门店记录出错！", e);
			return Jsonp.error("查询浏览门店记录出错");
		}
		
	}
	
}
