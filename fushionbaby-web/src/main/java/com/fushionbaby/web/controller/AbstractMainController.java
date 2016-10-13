package com.fushionbaby.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.ImageTypeConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.FocusPicDto;
import com.fushionbaby.sku.model.SkuImage;
import com.fushionbaby.sku.model.SkuImageStandardConfig;
import com.fushionbaby.sku.service.SkuImageService;
import com.fushionbaby.sku.service.SkuImageStandardConfigService;
import com.fushionbaby.sysmgr.model.SysmgrAdvertisement;
import com.fushionbaby.config.model.SysmgrAdvertisementConfig;
import com.fushionbaby.config.service.SysmgrAdvertisementConfigService;
import com.fushionbaby.sysmgr.service.SysmgrAdvertisementService;
import com.fushionbaby.web.util.ImageConstantWeb;

@Controller
@RequestMapping("/")
public abstract class AbstractMainController {

	@Autowired
	protected SkuImageService<SkuImage> skuImageService;
	@Autowired
	protected SkuImageStandardConfigService<SkuImageStandardConfig> skuImageStandardService;
	@Autowired
	protected SysmgrAdvertisementService<SysmgrAdvertisement> sysAdvertisementService;
	@Autowired
	protected SysmgrAdvertisementConfigService<SysmgrAdvertisementConfig> sysAdvertisementConfigService;

	/**
	 * 获取缩略图集合
	 * 
	 * @param skuCode
	 *            图片编号
	 * @param imageStandard
	 *            图片尺寸
	 * @return
	 */
	protected List<String> getThumImageList(String skuCode, String imageStandard) {
		SkuImageStandardConfig skuImageStandard = skuImageStandardService
				.findByCode(imageStandard);
		String imageSize = skuImageStandard.getSize();
		List<SkuImage> skuImages = skuImageService
				.findBySkuCodeByImageTypeCode(skuCode,
						ImageTypeConstant.IMAGE_TYPE_WEB_4);
		List<String> imageListBottom = new ArrayList<String>();
		int i = 0;
		for (SkuImage image : skuImages) {
			if (i == WebConstant.SKU_DETAIL_IMG_COUNT) {
				break;
			}
			String imgUrl = image.getImgUrl();
			String prefix = imgUrl.substring(0, imgUrl.lastIndexOf("."));
			String suffix = imgUrl.substring(imgUrl.lastIndexOf("."));
			imageListBottom.add(ImageConstantWeb.SKU_IMAGE_SERVER_PATH + "/"
					+ skuCode + "/" + prefix + imageSize + suffix);
			i++;
		}

		return imageListBottom;
	}

	/**
	 * 获取原始图片集合
	 * 
	 * @param skuCode
	 *            商品编号
	 * @param imageTypeCode
	 *            图片类型编号
	 * @return
	 */

	protected List<String> getOrigImageList(String skuCode, String imageTypeCode) {
		List<SkuImage> skuImages = skuImageService
				.findBySkuCodeByImageTypeCode(skuCode, imageTypeCode);
		List<String> image_list_top = new ArrayList<String>();
		int i = 0;
		for (SkuImage image : skuImages) {
			if (i == WebConstant.SKU_DETAIL_IMG_COUNT) {
				break;
			}
			image_list_top.add(ImageConstantWeb.SKU_IMAGE_SERVER_PATH + "/"
					+ skuCode + "/" + image.getImgUrl());
			i++;
		}

		return image_list_top;
	}

	/**
	 * 获得首页产品列表尺寸的图片（单个）
	 * 
	 * @param skuCode
	 *            商品编号
	 * @param imageTypeCode
	 *            图片类型编号
	 * @return
	 */
	protected String getOrigImagePath(String skuCode, String imageTypeCode) {

		List<String> image_list = this.getOrigImageList(skuCode, imageTypeCode);
		if (CollectionUtils.isEmpty(image_list)) {
			return StringUtils.EMPTY;
		}
		return image_list.get(0);
	}

	/**
	 * 获取小图
	 * 
	 * @param skuCode
	 *            商品编号
	 * @param imageStandard
	 *            图片尺寸
	 * @return
	 */
	protected String getThumImagePath(String skuCode, String imageStandard) {

		List<String> image_list = this.getThumImageList(skuCode, imageStandard);
		if (CollectionUtils.isEmpty(image_list)) {
			return StringUtils.EMPTY;
		}
		return image_list.get(0);

	}

	/**
	 * 查询web端轮播图片
	 * 
	 * @param ad_code
	 *            广告编号
	 * @return
	 */

	protected List<FocusPicDto> getPicList(String ad_code) {
		List<FocusPicDto> list = new ArrayList<FocusPicDto>();
		SysmgrAdvertisementConfig config = sysAdvertisementConfigService
				.findByAdCode(ad_code);
		if (config != null && config.getIsUse().equals(CommonConstant.YES)) {
			List<SysmgrAdvertisement> results = this
					.getAdvertisementList(ad_code);
			for (SysmgrAdvertisement ad : results) {
				FocusPicDto dto = new FocusPicDto();
				try {
					if (!StringUtils.isBlank(ad.getPicturePath())) {
						dto.setPicture_path(ImageConstantWeb.SYS_AD_PICTURE_SERVER
								+ "/"
								+ URLEncoder.encode(ad.getPicturePath(),
										"utf-8"));
					} else {
						dto.setPicture_path(StringUtils.EMPTY);
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				dto.setUrl(ad.getUrl());
				list.add(dto);
			}
		}
		return list;
	}

	/**
	 * 查询web端要显示的图片列表
	 * 
	 * @param ad_code
	 *            广告编号
	 * @param size
	 *            显示图片的数量
	 * 
	 *            0129需求新变化，去掉数量限制
	 * 
	 * @return
	 */
	protected List<SysmgrAdvertisement> getAdvertisementList(String ad_code) {
		return sysAdvertisementService.getListByAdCode(ad_code);
	}
}
