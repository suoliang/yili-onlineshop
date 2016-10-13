package com.aladingshop.wap.controller.store;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.dto.areaconfig.AreaAllJsonDto;
import com.fushionbaby.common.dto.areaconfig.AreaDto;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.sysmgr.model.SysmgrStoreApply;
import com.fushionbaby.sysmgr.service.SysmgrStoreApplyService;
import com.google.gson.Gson;

/***
 * @description 门店开店申请操作
 * @author 索亮
 * @date 2015年12月24日下午1:49:03
 */
@Controller
@RequestMapping("/store")
public class StoreApplyShopController {
	
	@Autowired
	private SysmgrStoreApplyService<SysmgrStoreApply> storeApplyService;
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaConfigService;
	
	@RequestMapping("toOpenShop")
	public String toOpenShop(){
		return "store/openShop";
	}
	
	/***
	 * @description 提交开店申请，可重复提交
	 * @param name
	 * @param phone
	 * @param area
	 * @param areaDetail
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="commitOpenShop",method=RequestMethod.POST)
	public Object commitOpenShop(String name,String phone,String area,String areaDetail){
		SysmgrStoreApply storeApply = new SysmgrStoreApply();
		storeApply.setName(name);
		storeApply.setPhone(phone);
		storeApply.setCity(area);
		storeApply.setAddress(areaDetail);
		storeApply.setIsDeal(CommonConstant.NO);
		storeApply.setSourceCode(SourceConstant.APP_CODE);
		storeApplyService.add(storeApply);
		return Jsonp.success();
	}
	
	/***
	 * 获取json格式的地域时使用
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getAllJsonArea")
	public Object getAllJsonArea(){
		List<AreaDto> areaDtoList = memberAreaConfigService.findByParentAreaCode("0");
		List<AreaAllJsonDto> jsonDtos = new ArrayList<AreaAllJsonDto>();
		for (AreaDto areaDto : areaDtoList) {
			AreaAllJsonDto allJsonDto = new AreaAllJsonDto();
			allJsonDto.setId(areaDto.getCode());
			allJsonDto.setName(areaDto.getName());
			allJsonDto.setChild(getChildArea(areaDto.getCode()));
			jsonDtos.add(allJsonDto);
		}
		System.out.println(new Gson().toJson(jsonDtos));
		return Jsonp_data.success(jsonDtos);
	}
	
	private List<AreaAllJsonDto> getChildArea(String areaCode){
		List<AreaDto> areaDtoList = memberAreaConfigService.findByParentAreaCode(areaCode);
		List<AreaAllJsonDto> jsonDtos = new ArrayList<AreaAllJsonDto>();
		for (AreaDto areaDto : areaDtoList) {
			AreaAllJsonDto allJsonDto = new AreaAllJsonDto();
			allJsonDto.setId(areaDto.getCode());
			allJsonDto.setName(areaDto.getName());
			allJsonDto.setChild(getChildArea(areaDto.getCode()));
			jsonDtos.add(allJsonDto);
		}
		return jsonDtos;
	}
	
	
}
