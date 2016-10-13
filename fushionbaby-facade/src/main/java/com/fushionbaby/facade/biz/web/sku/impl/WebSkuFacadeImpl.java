///**
// * 
// */
//package com.fushionbaby.facade.biz.web.sku.impl;
//
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import util.ImageConstantFacade;
//
//import com.fushionbaby.common.condition.sku.SkuLabelRelationQueryCondition;
//import com.fushionbaby.common.constants.AdvertisementConfigConstant;
//import com.fushionbaby.common.constants.ImageStandardConstant;
//import com.fushionbaby.common.constants.ImageTypeConstant;
//import com.fushionbaby.common.constants.SortTypeConstant;
//import com.fushionbaby.common.constants.SourceConstant;
//import com.fushionbaby.common.constants.WebConstant;
//import com.fushionbaby.common.dto.sku.IndexDto;
//import com.fushionbaby.common.dto.sku.ProductDetailImageDto;
//import com.fushionbaby.common.dto.sku.SkuBrandDto;
//import com.fushionbaby.common.dto.sku.SkuBrandGraphemeDto;
//import com.fushionbaby.common.dto.sku.SkuDetailDto;
//import com.fushionbaby.common.dto.sku.SkuDto;
//import com.fushionbaby.common.dto.sku.SkuHotDto;
//import com.fushionbaby.common.dto.sku.SkuTimelimitDto;
//import com.fushionbaby.common.dto.sku.web.WebSkuDetailDto;
//import com.fushionbaby.common.enums.SkuStatusEnum;
//import com.fushionbaby.common.util.BeanCopyUtil;
//import com.fushionbaby.facade.biz.common.sku.SkuFacade;
//import com.fushionbaby.sku.model.SkuProductImage;
//import com.fushionbaby.sku.service.SkuProductImageService;
//
///**
// * @author mengshaobo
//// *
// */
//@Service
//public class WebSkuFacadeImpl extends AbstractIndexFacade {
//	/** 记录日志 */
//	private static final Log logger = LogFactory.getLog(WebSkuFacadeImpl.class);
//	@Autowired
//	private SkuFacade skuFacade;
//	@Autowired
//	private SkuProductImageService<SkuProductImage> skuProductImageService;
//	
//	
//	public IndexDto findIndexAll() {
//		IndexDto index = new IndexDto();
//		
//		try {
//			index.setFocusPictureList(skuFacade.getFocusPic(AdvertisementConfigConstant.WEB_INDEX_FOCUS));
//		} catch (Exception e) {
//			logger.error("banner图片出错");
//		}
//		try {
//			index.setCategoryList(this.findIndexAll());
//		} catch (Exception e) {
//			logger.error("分类列表出错");
//		}
//		
//		try {
//			Integer skuBrandCount = configService.getConfigValueByCode(WebConstant.WEB_SKU_BRAND_COUNT);
//			index.setBrandList(this.findBrandList(WebConstant.START_INDEX, skuBrandCount,SourceConstant.WEB_CODE));
//		} catch (Exception e) {
//			logger.error("在线商品品牌出错");
//		}
////		index.setSkuTimelimitList(this.getSkuTimelimit());
////		index.setGlobalSkuList(this.getGlobalSkuList());
////		index.setDiscountList(this.getDiscountSkus());
////		Integer skuHotCount = configService.getConfigValueByCode(WebConstant.WEB_SKU_HOT_COUNT_INDEX);
////		index.setSkuHotList(this.getSkuHot(skuHotCount));
//		return index;
//	}
//	
//	private List<SkuTimelimitDto> getSkuTimelimit(){
//		List<SkuTimelimitDto> skuTimelimitDtos = null;
//		try {
//			skuTimelimitDtos = this.getTimelimit(WebConstant.SKU_TIMELIMIT_SIZE);
//			for(SkuTimelimitDto dto : skuTimelimitDtos){
//				dto.setImgPath(this.imageProcess.getOrigImagePath(dto.getSkuCode(),
//						ImageTypeConstant.IMAGE_TYPE_WEB_6));
//			}
//		} catch (Exception e) {
//			logger.error("限时抢购出错" + e);
//		}
//		return skuTimelimitDtos;
//	}
//	
//	private List<SkuDto> getGlobalSkuList(){
//		SkuLabelRelationQueryCondition queryCondition = new SkuLabelRelationQueryCondition();		
//		queryCondition.setStart(WebConstant.START_INDEX);
//		queryCondition.setLimit(configService.getConfigValueByCode(WebConstant.WEB_SKU_GLOBAL_COUNT));
//		queryCondition.setSortParam(SortTypeConstant.DEFAULT);
//		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
//		queryCondition.setCode(WebConstant.GLOBAL_LABEL_CODE);
//		List<SkuDto> labelSkus = null;
//		try {
//			labelSkus = this.getLabelSkuList(queryCondition);
//			for(SkuDto dto : labelSkus){
//				dto.setImgPath(this.imageProcess.getOrigImagePath(dto.getCode(), ImageTypeConstant.IMAGE_TYPE_WEB_5));
//			}
//			
//		} catch (Exception e) {
//			logger.error("海外同步专场出错");
//		}
//		return labelSkus;
//	}
//	
//	private List<SkuDto> getDiscountSkus(){
//		SkuLabelRelationQueryCondition queryCondition = new SkuLabelRelationQueryCondition();				
//		queryCondition.setStart(WebConstant.START_INDEX);
//		queryCondition.setLimit(configService.getConfigValueByCode(WebConstant.WEB_SKU_PREFERENCE_COUNT));
//		queryCondition.setSortParam(SortTypeConstant.DEFAULT);
//		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
//		queryCondition.setCode(WebConstant.Preferential_SKU_LABEL_CODE);
//		List<SkuDto> discountSkus = null;
//		try {
//			
//			discountSkus = this.getLabelSkuList(queryCondition);
//			for(SkuDto dto : discountSkus){
//				dto.setImgPath(this.imageProcess.getOrigImagePath(dto.getCode(), ImageTypeConstant.IMAGE_TYPE_WEB_5));
//			}
//		} catch (Exception e) {
//			logger.error("特惠专区出错");
//		}
//		return discountSkus;
//	}
//	
//	public List<SkuHotDto> getSkuHot(Integer count){
//		List<SkuHotDto> skuHots =null;
//		try {
//			skuHots = skuFacade.findSkuHotDtos(count);
//			for(SkuHotDto dto : skuHots){
//				dto.setImgUrl(imageProcess.getOrigImagePath(dto.getSkuCode(),
//						ImageTypeConstant.IMAGE_TYPE_WEB_6));
//			}
//		} catch (Exception e) {
//			logger.error("热销商品出错");
//		}
//		return skuHots;
//	}
///*
// * (non-Javadoc)
// * @see com.fushionbaby.facade.biz.web.sku.WebSkuFacade#getWebSkuDetail(com.fushionbaby.common.dto.sku.SkuDetailDto)
// */
//	public WebSkuDetailDto getWebSkuDetail(SkuDetailDto skuDetail) {
//		WebSkuDetailDto webSkuDetail = new WebSkuDetailDto();
//		try {
//			BeanCopyUtil.copyProperty(skuDetail, webSkuDetail, null);
//		} catch (Exception e) {
//			logger.error("bean copy error" + e);
//		}
//		
//		
//		String skuCode =  webSkuDetail.getSelectedSku().getSkuCode();
//		webSkuDetail.setSkuImagesBig(imageProcess.getOrigImageList(skuCode,ImageTypeConstant.IMAGE_TYPE_WEB_4));
//		webSkuDetail.setSkuImagesSmall(imageProcess.getThumImageList(skuCode,ImageStandardConstant.IMAGE_STANDARD_WEB_65));
//		webSkuDetail.setProductDetailImgs(this.getProductDetailImages(webSkuDetail.getProductCode()));
//		return webSkuDetail;
//	}
//	/**
//	 * 通过产品编号获取图片详情集合
//	 * 
//	 * @param productId
//	 *            产品编号
//	 * @return
//	 */
//	private List<ProductDetailImageDto> getProductDetailImages(String productId) {
//		List<ProductDetailImageDto> imgsDtos = new ArrayList<ProductDetailImageDto>();
//		List<SkuProductImage> productImgs = skuProductImageService.findBySkuProductCode(productId);
//		if (!CollectionUtils.isEmpty(productImgs) && productImgs.size() > 0) {
//			for (SkuProductImage img : productImgs) {
//				ProductDetailImageDto imgDto = new ProductDetailImageDto();
//				try {
//					String  path = ImageConstantFacade.SKU_DETAIL_PRODUCT_IMAGE_SERVICE_PATH + "/"+ img.getProductCode() + "/";
//					imgDto.setImgPath(path + URLEncoder.encode(img.getImgPath(), "utf-8"));
//				} catch (UnsupportedEncodingException e) {
//					logger.debug("encoder error" + e);
//					return null;
//				}
//				imgDto.setImgUrl(img.getImgPath());
//				imgsDtos.add(imgDto);
//			}
//		}
//		
//		return imgsDtos;
//	}
//
//	
//	public List<SkuBrandGraphemeDto> getSkuBrandGraphemes(List<SkuBrandDto> list) {
//		
//		List<SkuBrandGraphemeDto> skuBrandGraphemeDtos = new ArrayList<SkuBrandGraphemeDto>();
//		
//		Set<Character> prefixSet = new HashSet<Character>();
//		
//		for(SkuBrandDto skuBrand : list){
//			prefixSet.add(skuBrand.getBrandPrefix().toUpperCase().charAt(0));
//		}
//		StringBuffer sb = new StringBuffer();
//		for (Character prefix : prefixSet) {
//			sb.append(prefix);
//		}
//		char[] source = (sb.toString()).toCharArray();
//		Arrays.sort(source);
//		for(char c : source){
//			SkuBrandGraphemeDto skuBrandGraphemeDto = new SkuBrandGraphemeDto();
//			skuBrandGraphemeDto.setGrapheme( c + "");
//			skuBrandGraphemeDtos.add(skuBrandGraphemeDto);
//		}
//		for (SkuBrandGraphemeDto skuBrandGraphemeDto : skuBrandGraphemeDtos) {
//			List<SkuBrandDto> skuBrandDtos = new ArrayList<SkuBrandDto>();
//			for(SkuBrandDto skuBrand : list){
//				if(skuBrandGraphemeDto.getGrapheme().equalsIgnoreCase(skuBrand.getBrandPrefix())){
//					skuBrandDtos.add(skuBrand);
//				}
//			}
//			skuBrandGraphemeDto.setSkuBrandList(skuBrandDtos);
//		}
//		
//		
//		return skuBrandGraphemeDtos;
//	}
//	
//
//}
