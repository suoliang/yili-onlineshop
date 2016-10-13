package com.fushionbaby.facade.biz.common.sku.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import util.ImageConstantFacade;

import com.fushionbaby.cache.service.GlobalConfig;
import com.fushionbaby.common.condition.QueryCondition;
import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.dto.AttrDto;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.sku.SelectedSkuDto;
import com.fushionbaby.common.dto.sku.SkuDetailDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.dto.sku.app.SkuBrowseHistoriesDto;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.facade.biz.AbstactSkuFacade;
import com.fushionbaby.facade.biz.common.sku.SkuDetailFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.member.model.MemberSkuShareContent;
import com.fushionbaby.member.service.MemberSkuShareContentService;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuBrand;
import com.fushionbaby.sku.model.SkuBrowseHistories;
import com.fushionbaby.sku.model.SkuCategory;
import com.fushionbaby.sku.model.SkuExtraInfo;
import com.fushionbaby.sku.model.SkuImage;
import com.fushionbaby.sku.model.SkuInventory;
import com.fushionbaby.sku.model.SkuKeyWord;
import com.fushionbaby.sku.model.SkuLinkSkusRelation;
import com.fushionbaby.sku.model.SkuPrice;
import com.fushionbaby.sku.model.SkuProductAttr;
import com.fushionbaby.sku.model.SkuProductImage;
import com.fushionbaby.sku.service.SkuBrandService;
import com.fushionbaby.sku.service.SkuBrowseHistoriesService;
import com.fushionbaby.sku.service.SkuCategoryService;
import com.fushionbaby.sku.service.SkuCombinationItemService;
import com.fushionbaby.sku.service.SkuCombinationService;
import com.fushionbaby.sku.service.SkuImageService;
import com.fushionbaby.sku.service.SkuInventoryService;
import com.fushionbaby.sku.service.SkuKeyWordService;
import com.fushionbaby.sku.service.SkuLinkSkusRelationService;
import com.fushionbaby.sku.service.SkuProductAttrService;

/**
 * @author mengshaobo
 *
 */
@Service
public class SkuDetailFacadeImpl extends AbstactSkuFacade implements SkuDetailFacade {

	@Autowired
	private SkuInventoryService<SkuInventory> skuInventoryService;
	@Autowired
	private SkuBrandService<SkuBrand> skuBrandService;
	@Autowired
	private SkuLinkSkusRelationService<SkuLinkSkusRelation> skuLinkSkusService;
	@Autowired
	private SkuCombinationItemService skuCombinationItemService;
	@Autowired
	private SkuCombinationService skuCombinationService;
	@Autowired
	private SkuProductAttrService<SkuProductAttr> attrService;
	@Autowired
	private SkuImageService<SkuImage> skuImageService;
	@Autowired
	private GlobalConfig globalConfig;
	@Autowired
	private SkuBrowseHistoriesService skuBrowseHistoriesService;
	@Autowired
	private MemberSkuShareContentService memberSkuShareService;
	@Autowired
	private SkuCategoryService<SkuCategory> categoryService;
	@Autowired
	private SkuKeyWordService<SkuKeyWord> skuKeyWordService;
	@Autowired
	private SkuFacade skuFacade;
	

	/** 记录日志 */
	private static final Log LOGGER = LogFactory.getLog(SkuFacadeImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.biz.common.sku.SkuDetailFacade#getSkuDetailModel
	 * (com.fushionbaby.common.dto.sku.SelectedSkuDto)
	 */
	public SkuDetailDto getSkuDetailModel(SelectedSkuDto selectedSkuDto) {
		try {
			if (StringUtils.isBlank(selectedSkuDto.getProductCode())) {
				SkuDetailDto skuDetail = this.skuDetailBySkuCode(selectedSkuDto.getSkuCode());
				return skuDetail;
			}
			SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();

			queryCondition.setSkuCode(selectedSkuDto.getSkuCode());
			queryCondition.setProductCode(selectedSkuDto.getProductCode());

			if (StringUtils.isNotBlank(selectedSkuDto.getColor())) {
				String color = URLDecoder.decode(selectedSkuDto.getColor(), "utf-8");
				queryCondition.setColor(color);
			}

			if (StringUtils.isNotBlank(selectedSkuDto.getSize())) {
				String size = URLDecoder.decode(selectedSkuDto.getSize(), "utf-8");
				queryCondition.setSize(size);
			}
			SkuDetailDto skuDetail = this.skuDetailByCondition(queryCondition);

			return skuDetail;
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("尺寸字符串转码失败" + e.getMessage());
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 通过条件查询商品详情
	 * 
	 * @param queryCondition
	 * @return
	 */
	private SkuDetailDto skuDetailByCondition(SkuEntryQueryCondition queryCondition) {
		String productCode = queryCondition.getProductCode();
		if (StringUtils.isBlank(productCode)) {
			return null;
		}
		List<Sku> skus = this.skuService.queryByProductCode(productCode);
		if (CollectionUtils.isEmpty(skus)) {
			return null;
		}

		String skuUnCode = queryCondition.getSkuCode();
		String selectedSize = queryCondition.getSize();
		String selectedColor = queryCondition.getColor();
		Sku currentSku = null;
		for (Sku skuIn : skus) {
			String size = skuIn.getSize();
			String color = skuIn.getColor();
			if (StringUtils.trimToEmpty(selectedColor).equals(StringUtils.trimToEmpty(color))
					&& StringUtils.trimToEmpty(selectedSize).equals(StringUtils.trimToEmpty(size))) {
				currentSku = skuIn;
				break;
			}
		}
		if (ObjectUtils.notEqual(null, currentSku)) {
			return this.getSkuDetailByCurrentSku(currentSku);
		}
		SkuDetailDto skuDetail = this.skuDetailBySkuCode(skuUnCode);
		SelectedSkuDto selectedSkuDto = this.getSelectedSkuDto(currentSku, skus, selectedColor, selectedSize);
		if (selectedSkuDto == null) {
			return null;
		}
		skuDetail.setSelectedSku(selectedSkuDto);
		return skuDetail;

	}

	/**
	 * 获取当前选中的商品
	 * 
	 * @param currentSkuInventory
	 *            当前的商品库存
	 * @param inventorysByProductId
	 *            产品下的所以库存商品
	 * @param color
	 *            选择的颜色
	 * @param size
	 *            选择的尺寸
	 * @return
	 */
	private SelectedSkuDto getSelectedSkuDto(Sku currentSku, List<Sku> skus, String color, String size) {
		SelectedSkuDto selectedSku = new SelectedSkuDto();
		Sku skuEntry = skuService.queryBySkuCode(currentSku.getUniqueCode());
		selectedSku.setSkuCode(skuEntry.getUniqueCode());
		for (Sku skuIn : skus) {
			if (StringUtils.equals(StringUtils.trimToEmpty(color), StringUtils.trimToEmpty(skuIn.getColor()))) {
				selectedSku.setColor(skuIn.getColor());
				break;
			}
			if (StringUtils.equals(StringUtils.trimToEmpty(size), StringUtils.trimToEmpty(skuIn.getSize()))) {
				selectedSku.setSize(skuIn.getSize());
				break;
			}
		}
		SkuInventory inventory = skuInventoryService.findBySkuCode(selectedSku.getSkuCode());
		if (inventory == null) {
			LOGGER.error("当前商品：" + selectedSku.getSkuCode() + " 对应的库存信息不存在！");
			return null;
		}
		inventory.setAvailableQty(inventory.getAvailableQty());
		return selectedSku;
	}

	/**
	 * 通过当前库存信息获得当前选中的商品
	 * 
	 * @param currentSkuInventory
	 *            当前商品库存
	 * @return
	 */
	private SkuDetailDto getSkuDetailByCurrentSku(Sku currentSku) {
		if (ObjectUtils.equals(null, currentSku)) {
			return null;
		}
		Sku skuEntry = skuService.queryBySkuCode(currentSku.getUniqueCode());
		if (ObjectUtils.equals(null, skuEntry)) {
			return null;
		}
		List<Sku> skuList = skuService.queryByProductCode(currentSku.getProductCode());
		if (ObjectUtils.equals(null, skuList) || skuList.size() == 0) {
			return null;
		}
		SkuInventory inventory = skuInventoryService.findBySkuCode(currentSku.getUniqueCode());
		if (inventory == null) {
			return null;
		}
		SelectedSkuDto selectedSku = new SelectedSkuDto();
		selectedSku.setSkuCode(skuEntry.getUniqueCode());
		selectedSku.setProductCode(skuEntry.getProductCode());
		selectedSku.setColor(StringUtils.trimToEmpty(currentSku.getColor()));
		selectedSku.setSize(StringUtils.trimToEmpty(currentSku.getSize()));
		selectedSku.setAvailableQty(inventory.getAvailableQty());
		SkuDetailDto skuDetail = this.getSkuDetail(skuEntry);
		skuDetail.setSelectedSku(selectedSku);
		return skuDetail;
	}

	/**
	 * 通过商品编号查询商品详情
	 * 
	 * @param skuCode
	 *            商品序号
	 * @return
	 */
	private SkuDetailDto skuDetailBySkuCode(String skuUnCode) {
		Sku skuEntry = skuService.queryBySkuCode(skuUnCode);
		if (skuEntry == null) {
			return null;
		}
		SkuInventory inventory = skuInventoryService.findBySkuCode(skuUnCode);
		if (inventory == null) {
			return null;
		}
		List<Sku> skuList = skuService.queryByProductCode(skuEntry.getProductCode());
		if (skuList == null) {
			return null;
		}
		SkuDetailDto skuDetail = new SkuDetailDto();

		SelectedSkuDto selectedSku = new SelectedSkuDto();
		selectedSku.setSkuCode(skuEntry.getUniqueCode());
		selectedSku.setProductCode(skuEntry.getProductCode());
		selectedSku.setAvailableQty(inventory.getAvailableQty());
		selectedSku.setSize(StringUtils.trimToEmpty(skuEntry.getSize()));
		selectedSku.setColor(StringUtils.trimToEmpty(skuEntry.getColor()));
		skuDetail = this.getSkuDetail(skuEntry);
		skuDetail.setSelectedSku(selectedSku);
		return skuDetail;
	}

	/***
	 * 获得页面显示的商品详情
	 * 
	 * @param entry
	 *            商品详情表
	 * @return
	 */
	private SkuDetailDto getSkuDetail(Sku sku) {

		SkuDetailDto skuDetail = null;
		try {
			String skuCode = sku.getUniqueCode();
			String puCode = sku.getProductCode();
			List<AttrDto> attrList = this.queryAttrListByPid(puCode);
			SkuBrand brand = skuBrandService.findBySkuCode(skuCode);
			if (ObjectUtils.notEqual(null, brand)) {
				AttrDto attrBrand = new AttrDto();
				attrBrand.setName("品牌");
				attrBrand.setValue(StringUtils.trimToEmpty(brand.getBrandName()));
				attrList.add(attrBrand);
			}

			List<Sku> skuList = this.skuService.queryByProductCode(puCode);
			List<String> colorList = this.getColorList(skuList);
			List<String> sizeList = this.getSizeList(skuList);
			skuDetail = new SkuDetailDto();
			skuDetail.setCategoryCode(sku.getCategoryCode());
			skuDetail.setQuotaNum(sku.getQuotaNum());
			skuDetail.setAttr(attrList);
			skuDetail.setColorList(colorList);
			skuDetail.setSizeList(sizeList);
			skuDetail.setProductCode(puCode);
			skuDetail.setCategoryCode(sku.getCategoryCode());
			skuDetail.setSkuNo(StringUtils.trimToEmpty(sku.getSkuNo()));
			skuDetail.setName(sku.getName());
			/** 获取相同产品下的商品列表 */
			skuDetail.setSameCategorySkus(this.getSkuDtoList(skuList));
			SkuPrice skuPrice = skuPriceService.findBySkuCode(skuCode);
			if (skuPrice != null) {
				skuDetail.setPriceNew(ObjectUtils.toString(skuPrice.getCurrentPrice(), "0"));
				skuDetail.setPriceOld(ObjectUtils.toString(skuPrice.getRetailPrice(), "0"));
			} else {
				skuDetail.setPriceNew("0");
				skuDetail.setPriceOld("0");
			}
			
			if(StringUtils.equals(sku.getStatus(), SkuStatusEnum.SKU_STATUS_TOP.getStrVlue())) {
				skuDetail.setUsableSku(CommonConstant.YES);
			}else{
				skuDetail.setUsableSku(CommonConstant.NO);
			}

			SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(skuCode);

			if (skuExtraInfo != null ) {
				if(StringUtils.equalsIgnoreCase(skuExtraInfo.getIsVideo(), CommonConstant.YES)){
					skuDetail.setVideoUrl(skuExtraInfo.getVideoUrl());
				}
				
				String isMemberSku = skuExtraInfo.getIsMemberSku();
				skuDetail.setIsMemberSku(StringUtils.isBlank(isMemberSku) ? CommonConstant.YES : isMemberSku);
				skuDetail.setPriceVip(skuPrice==null ? "0" : ObjectUtils.toString(skuPrice.getAladingPrice(),"0"));
			}

			List<String> graphicList = this.getGraphicDetails(puCode);
			List<Map<String, String>> grapMapList = new ArrayList<Map<String, String>>();
			for (String grap : graphicList) {
				BufferedImage image = this.getBufferedImage(grap);
				if (image == null) {
					continue;
				}
				Map<String, String> map = new HashMap<String, String>();
				map.put("url", grap);
				map.put("height", ObjectUtils.toString(image.getHeight()));
				grapMapList.add(map);
			}

			skuDetail.setGraphicDetails(grapMapList);

		} catch (MalformedURLException urlException) {
			LOGGER.warn("当前商品图文详情有误：" + urlException);
		} catch (IOException ioException) {
			LOGGER.warn("当前商品图文详情有误：" + ioException);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return skuDetail;
	}

	/**
	 * @param imgUrl
	 *            图片地址
	 * @return
	 * */
	private BufferedImage getBufferedImage(String imgUrl) throws Exception {
		URL url = null;
		InputStream is = null;
		BufferedImage img = null;
		url = new URL(imgUrl);
		is = url.openStream();
		img = ImageIO.read(is);
		is.close();

		return img;
	}

	/** 产品详情 */
	private List<String> getGraphicDetails(String productCode) {
		List<String> graphicDetails = new ArrayList<String>();
		List<SkuProductImage> productImage = skuProductImageService.findBySkuProductCode(productCode);
		for (SkuProductImage skuProductImage : productImage) {
			if (StringUtils.equals(skuProductImage.getIsDisable(), CommonConstant.YES)) {
				graphicDetails.add(ImageConstantFacade.IMAGE_SERVER_ROOT_PATH + skuProductImage.getImgPath());
			}
		}
		return graphicDetails;
	}

	/** 通过产品号查询商品名称和值 集合 */
	private List<AttrDto> queryAttrListByPid(String productCode) {
		List<AttrDto> attrDtoList = new ArrayList<AttrDto>();
		List<SkuProductAttr> attrs = attrService.findByProductCode(productCode);

		if (CollectionUtils.isEmpty(attrs)) {
			return attrDtoList;
		}
		for (SkuProductAttr a : attrs) {
			AttrDto attr = new AttrDto();
			attr.setValue(a.getAttrValue());
			attr.setName(a.getAttrName());
			attrDtoList.add(attr);
		}
		return attrDtoList;
	}

	/** 获得颜色集合 */
	private List<String> getColorList(List<Sku> skuList) {

		Set<String> colorList = new HashSet<String>();
		for (Sku sku : skuList) {
			if (StringUtils.isNotBlank(sku.getColor())) {
				colorList.add(sku.getColor());
			}
		}

		return new ArrayList<String>(colorList);
	}

	/** 获得尺寸集合 */
	private List<String> getSizeList(List<Sku> skuList) {
		Set<String> sizeList = new HashSet<String>();
		for (Sku sku : skuList) {
			if (StringUtils.isNotBlank(sku.getSize())) {
				sizeList.add(sku.getSize());
			}
		}
		return new ArrayList<String>(sizeList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.biz.common.sku.SkuDetailFacade#getLinkSkus(java
	 * .lang.String)
	 */
	public List<SkuDto> getLinkSkus(String skuCode) {
		String count = globalConfig.findByCode(GlobalConfigConstant.LINK_COUNT);
		Sku sku = skuService.queryBySkuCode(skuCode);
		if (sku == null) {
			return null;
		}
		String categoryCode = sku.getCategoryCode();
		if (StringUtils.isBlank(categoryCode)) {
			return new ArrayList<SkuDto>();
		}
		SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
		queryCondition.setCategoryCode(categoryCode);
		queryCondition.setStart(0);
		queryCondition.setLimit(NumberUtils.toInt(count));
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		List<Sku> skus = skuService.queryByCondition(queryCondition);
		for (Sku s : skus) {
			if (StringUtils.equals(s.getUniqueCode(), skuCode)) {
				skus.remove(s);
				break;
			}
		}

		List<SkuDto> skuDtos = this.getSkuDtoList(skus);
		for (SkuDto skuDto : skuDtos) {
			skuDto.setImgPath(imageProcess.getThumImagePath(skuDto.getSkuCode(),
					ImageStandardConstant.IMAGE_STANDARD_APP_175));
		}
		return skuDtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.biz.common.sku.SkuDetailFacade#getSkuBrowseHistorises
	 * (com.fushionbaby.common.dto.UserDto)
	 */
	public List<SkuBrowseHistoriesDto> getSkuBrowseHistorises(QueryCondition queryCondtion) {
		List<SkuBrowseHistoriesDto> browseHistoriesDtoList = new ArrayList<SkuBrowseHistoriesDto>();
		try {
			List<SkuBrowseHistories> browseHistoriesList = skuBrowseHistoriesService.findByCondition(queryCondtion);
			for (SkuBrowseHistories skuBrowseHistories : browseHistoriesList) {
				SkuBrowseHistoriesDto skuBrowseHistoriesDto = new SkuBrowseHistoriesDto();
				skuBrowseHistoriesDto.setBrowseCounts(skuBrowseHistories.getBrowseCounts());
				skuBrowseHistoriesDto.setMemberId(skuBrowseHistories.getMemberId());
				
				SkuDto skuDto = skuFacade.findBySkuCode(skuBrowseHistories.getSkuCode());
				skuBrowseHistoriesDto.setSkuDto(skuDto);
				
				skuBrowseHistoriesDto.setBrowseTime(DateFormatUtils.format(skuBrowseHistories.getBrowseTime(),
						"yyyy-MM-dd HH:mm:ss"));
				browseHistoriesDtoList.add(skuBrowseHistoriesDto);
			}
			return browseHistoriesDtoList;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("商品浏览历史显示异常" + e.getMessage());
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fushionbaby.facade.biz.common.sku.SkuDetailFacade#
	 * addOrUpdateBrowseHistories(java.lang.String, java.lang.String)
	 */
	public void addOrUpdateBrowseHistories(UserDto user, String skuCode) {
		SkuBrowseHistories skuBrowseHistories = new SkuBrowseHistories();
		skuBrowseHistories.setMemberId(user.getMemberId());
		skuBrowseHistories.setSkuCode(skuCode);
		skuBrowseHistoriesService.addOrUpdateBrowseHistories(skuBrowseHistories);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.biz.common.sku.SkuDetailFacade#addmemberSkuShare
	 * (com.fushionbaby.member.model.MemberSkuShareContent)
	 */
	public String addmemberSkuShare(UserDto user, MemberSkuShareContent memberSkuShare) {
		memberSkuShare.setMemberId(user.getMemberId());
		memberSkuShareService.add(memberSkuShare);
		return CommonConstant.YES;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.biz.common.sku.SkuDetailFacade#findByProductCode
	 * (java.lang.String)
	 */
	public SkuKeyWord findByProductCode(String productCode) {
		return skuKeyWordService.findByProductCode(productCode);
	}
}
