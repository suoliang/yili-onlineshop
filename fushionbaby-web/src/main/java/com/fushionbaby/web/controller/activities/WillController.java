package com.fushionbaby.web.controller.activities;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fushionbaby.common.constants.AdvertisementConfigConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.dto.FocusPicDto;
import com.fushionbaby.sysactivity.model.SysActivityWill;
import com.fushionbaby.sysactivity.service.SysActivityWillService;
import com.fushionbaby.sysmgr.model.SysmgrAdvertisement;
import com.fushionbaby.config.model.SysmgrAdvertisementConfig;
import com.fushionbaby.config.service.SysmgrAdvertisementConfigService;
import com.fushionbaby.sysmgr.service.SysmgrAdvertisementService;
import com.fushionbaby.web.util.ImageConstantWeb;

/***
 * 风尚宝贝志（活动）
 * 
 * @author glc 2014-11-19
 */
@Controller
@RequestMapping("/will")
public class WillController {
	/** 广告service */
	@Autowired
	private SysmgrAdvertisementService<SysmgrAdvertisement> sysAdvertisementService;
	/** 广告配置service */
	@Autowired
	private SysmgrAdvertisementConfigService<SysmgrAdvertisementConfig> sysAdvertisementConfigService;
	/** 风尚宝贝service */
	@Autowired
	private SysActivityWillService<SysActivityWill> sysActivityWillService;
	/** 日志 */
	private static final Log log = LogFactory.getLog(WillController.class);

	/***
	 * 获取web页面显示的专题列表
	 */
	@RequestMapping("/list")
	public String getList(HttpServletRequest request, ModelMap model) {
		try {
			List<FocusPicDto> pics = getPicList(AdvertisementConfigConstant.WEB_WILL);
			List<SysActivityWill> willList = sysActivityWillService
					.findWillShowList();
			if (pics != null && pics.size() >= 1) {
				model.addAttribute("pics", pics.get(0));
			}
			if (willList != null && willList.size() >= 1) {
				model.addAttribute("willList", willList);
				model.addAttribute("span",
						ImageConstantWeb.SYS_ACTIVITY_FUSHIONBABY_SERVER_PATH);
			}
		} catch (DataAccessException e) {
			log.error("风尚宝贝列表加载失败", e);
		}
		return "will/fs";
	}

	/***
	 * 风尚宝贝志专题详情页
	 */
	@RequestMapping("/willDetail")
	public String willDetail(
			@RequestParam(value = "web_article_url") String web_article_url,
			ModelMap model) {
		if (web_article_url == null || web_article_url.length() <= 0) {
			web_article_url = "http://www.fushionbaby.com";
		}
		model.addAttribute("web_article_url", web_article_url);
		return "will/fs-baby-art";
	}

	/***
	 * 获得图片的列表
	 * 
	 * @param ad_code
	 * @return
	 */
	private List<FocusPicDto> getPicList(String ad_code) {
		List<FocusPicDto> list = new ArrayList<FocusPicDto>();
		SysmgrAdvertisementConfig config = sysAdvertisementConfigService
				.findByAdCode(ad_code);
		if (config != null && config.getIsUse().equals(CommonConstant.YES)) {
			List<SysmgrAdvertisement> results = this
					.getAdvertisementList(ad_code);
			for (SysmgrAdvertisement ad : results) {
				FocusPicDto dto = new FocusPicDto();
				dto.setPicture_path(ImageConstantWeb.SYS_AD_PICTURE_SERVER
						+ "/" + ad.getPicturePath());
				dto.setUrl(ad.getUrl());
				list.add(dto);
			}
		}
		return list;
	}

	/***
	 * 通过ad_code获得广告列表，数量不限
	 * 
	 * @param ad_code
	 * @return
	 */
	private List<SysmgrAdvertisement> getAdvertisementList(String ad_code) {
		return sysAdvertisementService.getListByAdCode(ad_code);
	}

	/***
	 * 返回到该图片的HTML页面
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("htmlList")
	public String test(@RequestParam("id") Long id, Model model) {
		SysActivityWill will = this.sysActivityWillService.findById(id);
		String result = will.getWebArticleUrl();
		model.addAttribute("result", result);
		return result + "";
	}

}
