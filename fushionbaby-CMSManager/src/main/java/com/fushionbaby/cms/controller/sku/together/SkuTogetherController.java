package com.fushionbaby.cms.controller.sku.together;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuTogether;
import com.aladingshop.sku.cms.model.SkuTogetherStatus;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.aladingshop.sku.cms.service.SkuTogetherService;
import com.aladingshop.sku.cms.service.SkuTogetherStatusService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.SkuDto;
import com.fushionbaby.cms.dto.SkuTogetherDto;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.cms.util.constant.Constant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAreaConfigService;

/**
 * @author glc 团购
 */
@Controller
@RequestMapping("/skuTogether")
public class SkuTogetherController extends BaseController {

	@Autowired
	private SkuTogetherService<SkuTogether> skuTogetherService;
	@Autowired
	private SkuTogetherStatusService<SkuTogetherStatus> skuTogetherStatusService;
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> areaConfigService;
	@Autowired
	private SkuInfoService skuService;

	private static final Log LOGGER = LogFactory
			.getLog(SkuTogetherController.class);

	/**
	 * 团购列表查询
	 * 
	 * @param productDto
	 *            团购信息
	 * @param page
	 *            分页
	 * @param request
	 * @param model
	 * @version 2015-6-15
	 * @return
	 */
	@RequestMapping(value = "/skuTogetherList")
	public String findList(SkuTogetherDto skuTogetherDto, BasePagination page,
			HttpServletRequest request, ModelMap model) {
		try {
			BasePagination basePage = new BasePagination();
			if (page != null) {
				basePage = page;
			}
			Map<String, String> params = new HashMap<String, String>();
			if (skuTogetherDto.getSkuCode() != null)
				params.put("skuCode", skuTogetherDto.getSkuCode().trim());
			if (skuTogetherDto.getSkuName() != null)
				params.put("skuName", skuTogetherDto.getSkuName().trim());
			params.put("beginTimeFrom", skuTogetherDto.getBeginTimeFrom());
			params.put("endTimeFrom", skuTogetherDto.getEndTimeFrom());
			params.put("beginTimeTo", skuTogetherDto.getBeginTimeTo());
			params.put("endTimeTo", skuTogetherDto.getEndTimeTo());
			params.put("province", skuTogetherDto.getProvince());
			params.put("city", skuTogetherDto.getCity());
			params.put("district", skuTogetherDto.getDistrict());
			basePage.setParams(params);

			basePage = skuTogetherService.getListPage(basePage);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			@SuppressWarnings("unchecked")
			List<SkuTogether> togetherList = (List<SkuTogether>) basePage
					.getResult();
			List<SkuTogetherDto> togetherDtoList = new ArrayList<SkuTogetherDto>();
			for (SkuTogether skuTogether : togetherList) {
				SkuTogetherDto togetherDto = new SkuTogetherDto();
				String province = areaConfigService.getNameByAreaCode(skuTogether
						.getProvince());
				String city = areaConfigService.getNameByAreaCode(skuTogether
						.getCity());
				String district = areaConfigService.getNameByAreaCode(skuTogether
						.getDistrict());
				togetherDto.setProvince(province);
				togetherDto.setCity(city);
				togetherDto.setDistrict(district);
				togetherDto.setBeginTime(format.format(
						skuTogether.getBeginTime()).toString());
				togetherDto.setEndTime(format.format(skuTogether.getEndTime())
						.toString());
				togetherDto.setSkuCode(skuTogether.getSkuCode());
				togetherDto.setSkuName(skuTogether.getSkuName());
				togetherDto.setId(skuTogether.getId());
				togetherDtoList.add(togetherDto);
			}
			basePage.setResult(togetherDtoList);
			model.addAttribute("page", basePage);
			model.addAttribute("skuTogetherDto", skuTogetherDto);
		} catch (Exception e) {
			LOGGER.error("团购列表加载失败", e);
		}
		return "models/together/skuTogetherList";
	}

	/**
	 * 团购商品详情
	 * 
	 * @param productDto
	 *            团购信息
	 * @param page
	 *            分页
	 * @param request
	 * @param model
	 * @version 2015-6-15
	 * @return
	 */
	@RequestMapping(value = "/togetherDetail/{id}")
	public String skuTogetherDetail(@PathVariable Long id, ModelMap model) {
		SkuTogether skuTogether = skuTogetherService.findById(id);
		SkuTogetherDto togetherDto = new SkuTogetherDto();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String province = areaConfigService.getNameByAreaCode(skuTogether
				.getProvince());
		String city = areaConfigService.getNameByAreaCode(skuTogether.getCity());
		String district = areaConfigService.getNameByAreaCode(skuTogether
				.getDistrict());
		togetherDto.setProvince(province);
		togetherDto.setCity(city);
		togetherDto.setDistrict(district);
		togetherDto.setBeginTime(format.format(skuTogether.getBeginTime())
				.toString());
		togetherDto.setEndTime(format.format(skuTogether.getEndTime())
				.toString());
		togetherDto.setSkuCode(skuTogether.getSkuCode());
		togetherDto.setSkuName(skuTogether.getSkuName());
		SkuTogetherStatus skuTogetherStatus = skuTogetherStatusService
				.findBySkuTogetherId(id);
		model.addAttribute("skuTogetherDto", togetherDto);
		model.addAttribute("skuTogetherStatus", skuTogetherStatus);
		return "models/together/skuTogetherDetail";
	}

	/**
	 * 团购商品详情
	 * 
	 * @param productDto
	 *            团购信息
	 * @param page
	 *            分页
	 * @param request
	 * @param model
	 * @version 2015-6-15
	 * @return
	 */
	@RequestMapping(value = "/togetherModify/{id}")
	public String skuTogetherModify(@PathVariable Long id, ModelMap model) {
		SkuTogether skuTogether = skuTogetherService.findById(id);
		SkuTogetherStatus skuTogetherStatus = skuTogetherStatusService
				.findBySkuTogetherId(id);
		model.addAttribute("skuTogether", skuTogether);
		model.addAttribute("skuTogetherStatus", skuTogetherStatus);
		return "models/together/skuTogetherModify";
	}

	/**
	 * 团购商品详情添加
	 * 
	 * @param productDto
	 *            团购信息
	 * @param page
	 *            分页
	 * @param request
	 * @param model
	 * @version 2015-6-15
	 * @return
	 */
	@RequestMapping(value = "/toAddSkuTogether")
	public String skuTogetherToAdd() {
		return "models/together/skuTogetherAdd";
	}

	/**
	 * 团购商品详情修改
	 * 
	 * @param productDto
	 *            团购信息
	 * @param page
	 *            分页
	 * @param request
	 * @param model
	 * @version 2015-6-15
	 * @return
	 */
	@RequestMapping(value = "/updateTogether", method = RequestMethod.POST)
	public String skuTogetherUpdate(SkuTogether skuTogether,
			SkuTogetherStatus skuTogetherStatus, HttpSession session) {
		AuthUser user = CMSSessionUtils.getSessionUser(session);
		skuTogether.setUpdateId(user.getId());
		skuTogetherService.updateById(skuTogether);
		skuTogetherStatus.setSkuTogetherId(skuTogether.getId());
		skuTogetherStatus.setUpdateId(user.getId());
		skuTogetherStatusService.updateBySkuTogetherId(skuTogetherStatus);
		return "redirect:" + Global.getAdminPath()
				+ "/skuTogether/skuTogetherList";
	}

	/**
	 * 团购商品详情删除
	 * 
	 * @param productDto
	 *            团购信息
	 * @param page
	 *            分页
	 * @param request
	 * @param model
	 * @version 2015-6-15
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/togetherDelete/{id}")
	public String skuTogetherDelete(@PathVariable Long id) {
		try {
			skuTogetherService.deleteById(id);
			skuTogetherStatusService.deleteBySkuTogetherId(id);
		} catch (DataAccessException e) {
			LOGGER.error(e.getMessage());
			return Constant.FAILURE;
		}
		return Constant.SUCCESS;
		
	}
	/**
	 * 团购商品详情添加
	 * 
	 * @param productDto
	 *            团购信息
	 * @param page
	 *            分页
	 * @param request
	 * @param model
	 * @version 2015-6-15
	 * @return
	 */
	@RequestMapping(value = "/addTogether", method = RequestMethod.POST)
	public String skuTogetherAdd(SkuTogether skuTogether,
			SkuTogetherStatus skuTogetherStatus, HttpSession session) {
		AuthUser user = CMSSessionUtils.getSessionUser(session);
		Long userId = user.getId();
		skuTogether.setCreateId(userId);
		skuTogetherStatus.setCreateId(userId);
		skuTogetherService.add(skuTogether);
		skuTogetherStatus.setSkuTogetherId(skuTogether.getId());
		skuTogetherStatusService.add(skuTogetherStatus);
		return "redirect:" + Global.getAdminPath()
				+ "/skuTogether/skuTogetherList";
	}

	@ResponseBody
	@RequestMapping(value = "/getSkuNameByCode/{skuNo}", method = RequestMethod.POST)
	public Object findSkuBySkuCode(@PathVariable String skuNo) {
		Sku sku = skuService.queryBySkuNo(skuNo,null);
		if (ObjectUtils.equals(null, sku)) {
			return StringUtils.EMPTY;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("skuName", sku.getName());

		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/togetherExit", method = RequestMethod.POST)
	public Boolean checkTogetherExit(String skuCode, String district,Date beginTime,Date endTime) {
		List<SkuTogether> skuTogetherList = skuTogetherService.findBySkuCodeAndDistrict(
				skuCode, district);
		if (skuTogetherList.size()>0) {
			for(SkuTogether skuTogether:skuTogetherList){
				Date beginTimeTogether=skuTogether.getBeginTime();
				Date endTimeTogether=skuTogether.getEndTime();
				if((beginTime.compareTo(beginTimeTogether)>=0&&beginTime.compareTo(endTimeTogether)<=0)
						||(endTime.compareTo(beginTimeTogether)>=0&&endTime.compareTo(endTimeTogether)<=0)
						||(beginTime.compareTo(beginTimeTogether)<=0&&endTime.compareTo(endTimeTogether)>=0)){
					return true;
				}
				
			}
			
		}
		return false;
	}
	
	
	/**
	 * 分页选择商品列表
	 * 
	 * @param skuDto
	 *            商品信息
	 * @param page
	 *            分页信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/selectSkuList")
	public String findSkuList(SkuDto skuDto, BasePagination page, Model model) {

		BasePagination basePage = new BasePagination();
		if (ObjectUtils.notEqual(page, null)) {
			basePage = page;
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("skuNo", skuDto.getSkuNo());
		params.put("name", skuDto.getSkuName());
		params.put("createTimeFrom", skuDto.getCreateTimeFrom());
		params.put("createTimeTo", skuDto.getCreateTimeTo());
		basePage.setParams(params);
		BasePagination pageResult = skuService.getPageList(basePage);
		model.addAttribute("page", pageResult);
		model.addAttribute("skuDto", skuDto);
		return "models/together/selectSkuTogetherList";
	}

}
