/**
 * 
 */
package com.fushionbaby.facade.biz;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import util.EmptyValidUtils;
import util.ImageConstantFacade;

import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.dto.BrandDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.config.service.SysmgrGlobalConfigService;
import com.fushionbaby.facade.image.ImageProcess;
import com.fushionbaby.member.model.MemberSkuComment;
import com.fushionbaby.member.service.MemberSkuCommentService;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuBrand;
import com.fushionbaby.sku.model.SkuExtraInfo;
import com.fushionbaby.sku.model.SkuImage;
import com.fushionbaby.sku.model.SkuPrice;
import com.fushionbaby.sku.model.SkuProductImage;
import com.fushionbaby.sku.service.SkuBrandService;
import com.fushionbaby.sku.service.SkuExtraInfoService;
import com.fushionbaby.sku.service.SkuImageService;
import com.fushionbaby.sku.service.SkuPriceService;
import com.fushionbaby.sku.service.SkuProductImageService;
import com.fushionbaby.sku.service.SkuService;

/**
 * @author mengshaobo
 *
 */
public abstract class AbstactSkuFacade {
	@Autowired
	protected SkuService skuService;
	@Autowired
	protected MemberSkuCommentService<MemberSkuComment> memberCommentService;
	@Autowired
	protected ImageProcess imageProcess;
	@Autowired
	protected SysmgrGlobalConfigService configService;
	@Autowired
	protected SkuPriceService<SkuPrice> skuPriceService;
	@Autowired
	protected SkuExtraInfoService<SkuExtraInfo> skuExtraInfoService;
	@Autowired
	protected SkuProductImageService<SkuProductImage> skuProductImageService;
	@Autowired
	private SkuImageService<SkuImage> skuImageService;
	@Autowired
	private SkuBrandService<SkuBrand> skuBrandService;

	/**
	 * 获得商品列表
	 * 
	 * @param skus
	 *            通过商品信息列表获取页面显示的商品列表
	 * @return
	 */
	protected List<SkuDto> getSkuDtoList(List<Sku> skus) {

		List<SkuDto> skuList = new ArrayList<SkuDto>();
		if (CollectionUtils.isEmpty(skus)) {
			return skuList;
		}
		for (Sku sku : skus) {
			if(sku==null){
				continue;
			}
			String unCode = sku.getUniqueCode();
			SkuDto dto = new SkuDto();
			dto.setDescription(sku.getDescription());
			dto.setSkuCode(unCode);
			dto.setColor(StringUtils.trimToEmpty(sku.getColor()));
			dto.setSize(StringUtils.trimToEmpty(sku.getSize()));
			dto.setName(sku.getName());
			dto.setProductCode(sku.getProductCode());
			SkuPrice skuPrice = skuPriceService.findBySkuCode(unCode);
			if (skuPrice != null) {
				dto.setPriceOld(ObjectUtils.toString(skuPrice.getRetailPrice(),"0"));
				dto.setPriceNew(ObjectUtils.toString(skuPrice.getCurrentPrice(), "0"));
			} else {
				dto.setPriceNew("0");
				dto.setPriceOld("0");
			}

//			double discount = (Double.valueOf(dto.getPriceNew()) / Double.valueOf(dto.getPriceOld())) * 10D;
//			dto.setDiscount(new DecimalFormat("#.##").format(discount));

			Integer commentCount = memberCommentService.getPagetotal(unCode);
			dto.setCommentCount(commentCount == null ? 0 : commentCount);

			SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(unCode);
			if (skuExtraInfo != null) {
				dto.setHasGift(skuExtraInfo.getHasGift());
				String isMemberSku = skuExtraInfo.getIsMemberSku();
				dto.setIsMemberSku(StringUtils.isBlank(isMemberSku) ? CommonConstant.YES : isMemberSku);
				dto.setPriceVip(skuPrice==null ? "0" : ObjectUtils.toString(skuPrice.getAladingPrice(),"0"));
			}

			/** 获取图片路径 */
			List<SkuImage> images = skuImageService.findBySkuCode(sku.getUniqueCode());
			if (!EmptyValidUtils.arrayIsEmpty(images)) {
				dto.setImgPath(ImageConstantFacade.IMAGE_SERVER_ROOT_PATH + images.get(0).getImgUrl());
			}

			/** 设置品牌名*/
			dto.setBrandName(skuBrandService.getNameByCode(sku.getBrandCode()));
			
			
			skuList.add(dto);
		}

		return skuList;
	}

	protected BrandDto assBrandDto(SkuBrand brand) {
		BrandDto brandDto = new BrandDto();
		brandDto.setBrandCode(brand.getBrandCode());
		brandDto.setBrandName(brand.getBrandName());
		brandDto.setBrandDesc(brand.getBrandDesc());
		brandDto.setBrandSiteUrl(brand.getBrandSiteUrl());
		brandDto.setBrandLogoAppUrl(StringUtils.isBlank(brand.getBrandLogoAppUrl()) ? StringUtils.EMPTY
				: ImageConstantFacade.IMAGE_SERVER_ROOT_PATH + brand.getBrandLogoAppUrl());
		brandDto.setBrandLogoWebUrl(StringUtils.isBlank(brand.getBrandLogoWebUrl()) ? StringUtils.EMPTY
				: ImageConstantFacade.IMAGE_SERVER_ROOT_PATH + brand.getBrandLogoWebUrl());
		return brandDto;
	}

}
