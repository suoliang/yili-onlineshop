package com.fushionbaby.cms.controller.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aladingshop.store.model.Store;
import com.aladingshop.store.model.StoreExtraInfo;
import com.aladingshop.store.service.StoreExtraInfoService;
import com.aladingshop.store.service.StoreService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.dto.store.StoreExtraInfoDto;
import com.fushionbaby.common.constants.SessionKeyConstant;
import com.fushionbaby.common.util.BasePagination;


@Controller
@RequestMapping("/storeExtraInfo")
public class StoreExtraInfoController {

	@Autowired
	private StoreExtraInfoService<StoreExtraInfo> storeExtraInfoService;
	@Autowired
	private StoreService<Store> storeService;
	
	private static final Log LOGGER = LogFactory.getLog(StoreExtraInfoController.class);
	
	@SuppressWarnings("unchecked")
	@RequestMapping("storeExtraInfoList")
	public String storeExtraInfoList(StoreExtraInfoDto storeExtraInfoDto,
					BasePagination page,ModelMap model){
		try {
			if(page == null){
				page = new BasePagination();
			}
			Map<String,String> params = new HashMap<String, String>();
			params.put("linkMan", storeExtraInfoDto.getLinkMan());
			params.put("telephone", storeExtraInfoDto.getTelephone());
			page.setParams(params);
			page = storeExtraInfoService.getListPage(page);
			
			List<StoreExtraInfo> storeExtraInfolist = (List<StoreExtraInfo>) page.getResult();
			List<StoreExtraInfoDto> list = new ArrayList<StoreExtraInfoDto>();
			for (StoreExtraInfo storeExtraInfo : storeExtraInfolist) {
				Store store = storeService.findByCode(storeExtraInfo.getStoreCode());
				StoreExtraInfoDto storeExInfoDto = this.setStoreExtraInfoDto(storeExtraInfo);
				storeExInfoDto.setStoreName(store.getName());
				storeExInfoDto.setStoreNumber(store.getNumber());
				
				list.add(storeExInfoDto);
			}
			
			model.addAttribute("storeExtraInfoDto", storeExtraInfoDto);
			model.addAttribute("page", page);
			model.addAttribute("list", list);
		} catch (Exception e) {
			LOGGER.error("查询门店额外信息失败！", e);
			return "";
		}
		return "models/store/storeExtraInfoList";
		
	}
	
	@RequestMapping("editStoreExtraInfo/{storeCode}/{time}")
	public String editStoreExtraInfo(@PathVariable(value="storeCode")String storeCode,ModelMap model){
		StoreExtraInfo storeExtraInfo = storeExtraInfoService.findByStoreCode(storeCode);
		if(storeExtraInfo == null){
			storeExtraInfo = new StoreExtraInfo();
			storeExtraInfo.setStoreCode(storeCode);
		}
		StoreExtraInfoDto storeExInfoDto = this.setStoreExtraInfoDto(storeExtraInfo);
		Store store = storeService.findByCode(storeExtraInfo.getStoreCode());
		storeExInfoDto.setStoreName(store.getName());
		storeExInfoDto.setStoreNumber(store.getNumber());
		
		model.addAttribute("storeExInfoDto", storeExInfoDto);
		return "models/store/editStoreExtraInfo";
	}
	
	@RequestMapping("updateStoreExtraInfo")
	public String update(StoreExtraInfo storeExtraInfo,HttpSession session){
		AuthUser auUser = (AuthUser) session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		if(auUser == null){
			return "redirect:"+Global.getAdminPath()+"/login/index";
		}
		storeExtraInfo.setUpdateId(auUser.getId());
		storeExtraInfoService.update(storeExtraInfo);
		return "redirect:"+Global.getAdminPath()+"/storeExtraInfo/storeExtraInfoList";
	}
	
	/**将实体类中的参数注入到Dto中*/
	public StoreExtraInfoDto setStoreExtraInfoDto(StoreExtraInfo storeExtraInfo){
		
		StoreExtraInfoDto storeExInfoDto = new StoreExtraInfoDto();
		storeExInfoDto.setId(storeExtraInfo.getId());
		storeExInfoDto.setAddress(storeExtraInfo.getAddress());
		storeExInfoDto.setDescription(storeExtraInfo.getDescription());
		storeExInfoDto.setLinkMan(storeExtraInfo.getLinkMan());
		storeExInfoDto.setMobile(storeExtraInfo.getMobile());
		storeExInfoDto.setTelephone(storeExtraInfo.getTelephone());
		storeExInfoDto.setStoreCode(storeExtraInfo.getStoreCode());
		storeExInfoDto.setZip(storeExtraInfo.getZip());
		storeExInfoDto.setPicture(storeExtraInfo.getPicture());
		storeExInfoDto.setTrafficRoutes(storeExtraInfo.getTrafficRoutes());
		storeExInfoDto.setUpdateId(storeExtraInfo.getUpdateId());
		storeExInfoDto.setUpdateTime(storeExtraInfo.getUpdateTime());
		storeExInfoDto.setIdentityCardNo(storeExtraInfo.getIdentityCardNo());
		return storeExInfoDto;
		
	}
}
