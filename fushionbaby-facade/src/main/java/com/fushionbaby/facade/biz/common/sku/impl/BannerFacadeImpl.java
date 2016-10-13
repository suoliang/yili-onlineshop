/**
 * 
 */
package com.fushionbaby.facade.biz.common.sku.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import util.ImageConstantFacade;

import com.fushionbaby.common.condition.sku.SkuLabelQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.dto.FocusPicDto;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.config.model.SysmgrAdvertisementConfig;
import com.fushionbaby.config.service.SysmgrAdvertisementConfigService;
import com.fushionbaby.facade.biz.common.sku.BannerFacade;
import com.fushionbaby.sku.model.SkuCategoryImage;
import com.fushionbaby.sku.model.SkuLabelImage;
import com.fushionbaby.sku.service.SkuCategoryImageService;
import com.fushionbaby.sku.service.SkuLabelImageService;
import com.fushionbaby.sysmgr.model.SysmgrAdvertisement;
import com.fushionbaby.sysmgr.service.SysmgrAdvertisementService;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年7月29日下午3:32:42
 */
@Service
public class BannerFacadeImpl implements BannerFacade {
	
	@Autowired
	private SysmgrAdvertisementService<SysmgrAdvertisement> sysAdvertisementService;
	
	@Autowired
	private SysmgrAdvertisementConfigService<SysmgrAdvertisementConfig> sysAdvertisementConfigService;
	
	
	@Autowired
	private SkuLabelImageService<SkuLabelImage> skuLabelImageService;
	
	@Autowired
	private SkuCategoryImageService<SkuCategoryImage> skuCategoryImageService;

	public List<FocusPicDto> getFocusPic(String adCode) {
		List<FocusPicDto> list = new ArrayList<FocusPicDto>();
		SysmgrAdvertisementConfig config = sysAdvertisementConfigService
				.findByAdCode(adCode);
		if (config != null
				&& StringUtils.equals(config.getIsUse(), CommonConstant.YES)) {
			List<SysmgrAdvertisement> results = sysAdvertisementService
					.getListByAdCode(adCode);
			if (CollectionUtils.isEmpty(results)) {
				return list;
			}
			for (SysmgrAdvertisement ad : results) {
				FocusPicDto dto = new FocusPicDto();
				String picPath = ad.getPicturePath();
				dto.setUrl(ad.getUrl());
				if (!StringUtils.isBlank(picPath)) {
					String picPathName = picPath.substring((picPath.lastIndexOf("/")+1));
					String picPrefix = picPath.substring(0,(picPath.lastIndexOf("/"))+1);
					dto.setPicturePath( ImageConstantFacade.IMAGE_SERVER_ROOT_PATH+"/"+picPrefix+picPathName);
				} else {
					dto.setPicturePath(StringUtils.EMPTY);
				}
				
				list.add(this.getFocusPicDto(dto));
			}
		}
		return list;
	}

	
	private FocusPicDto getFocusPicDto(FocusPicDto dto){
		
		try {
			URL	url = new URL(dto.getPicturePath());
			InputStream 	is = url.openStream();
			BufferedImage 	img = ImageIO.read(is);
			dto.setHeight(img.getHeight() + "");
			is.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BeanNullConverUtil.nullConver(dto);
		
		return dto;
	}
	
	
	
	public FocusPicDto getLabelBanner(String labelCode, String imageType) {
		FocusPicDto pic = new FocusPicDto();
		BeanNullConverUtil.nullConver(pic);		
		SkuLabelQueryCondition queryCondition = new SkuLabelQueryCondition();
		queryCondition.setImageType(imageType);
		queryCondition.setCode(labelCode);
		
		List<SkuLabelImage>  labelImageList = skuLabelImageService.findByCondition(queryCondition);
		if(labelImageList==null || CollectionUtils.isEmpty(labelImageList)){
			return pic;
		}
		try {
			String picturePath = ImageConstantFacade.IMAGE_SERVER_ROOT_PATH + labelImageList.get(0).getImgUrl();
			URL	url = new URL(picturePath);
			InputStream is = url.openStream();
			BufferedImage img = ImageIO.read(is);
			pic.setHeight(img.getHeight() + "");
			pic.setPicturePath(picturePath);
			pic.setUrl(labelImageList.get(0).getLinkUrl());
			is.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return pic;
		
	}

	public FocusPicDto getCategoryBanner(String categoryCode,String imageType) {
		FocusPicDto pic = new FocusPicDto();
		BeanNullConverUtil.nullConver(pic);		
		List<SkuCategoryImage>  categoryImageList = skuCategoryImageService.findByCategoryCodeAndImageType(categoryCode, imageType);
		if(categoryImageList==null || CollectionUtils.isEmpty(categoryImageList)){
			return pic;
		}
		
		try {
			String picturePath = ImageConstantFacade.IMAGE_SERVER_ROOT_PATH + categoryImageList.get(0).getImgUrl();
			URL	url = new URL(picturePath);
			InputStream is = url.openStream();
			BufferedImage img = ImageIO.read(is);
			pic.setHeight(img.getHeight() + "");
			pic.setPicturePath(picturePath);
			pic.setUrl(categoryImageList.get(0).getLinkUrl());
			is.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return pic;
	}

	
}
