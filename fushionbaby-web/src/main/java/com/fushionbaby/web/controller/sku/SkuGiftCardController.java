package com.fushionbaby.web.controller.sku;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.common.constants.ImageTypeConstant;
import com.fushionbaby.common.constants.SortTypeConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.sku.SelectedSkuDto;
import com.fushionbaby.common.dto.sku.SkuDetailDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.dto.sku.web.WebSkuDetailDto;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.facade.biz.common.sku.SkuDetailFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.facade.biz.web.sku.WebSkuFacade;
import com.fushionbaby.sku.model.SkuGiftCard;
import com.fushionbaby.sku.model.SkuInventory;
import com.fushionbaby.sku.service.SkuGiftCardService;
import com.fushionbaby.sku.service.SkuInventoryService;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;

@Controller
@RequestMapping("/giftcard")
public class SkuGiftCardController {
	
	private static final Log logg = LogFactory.getLog(SkuGiftCardController.class);
	
	@Autowired
	private SkuGiftCardService<SkuGiftCard> giftCardService;
	
	@Autowired
	private SkuInventoryService<SkuInventory> skuInventoryService;
	
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	
	@Autowired
	private SkuFacade skuFacade;
	
	@Autowired
	private SkuDetailFacade skuDetailFacade;
	
	@Autowired
	private WebSkuFacade webSkuFacade;
	
	@RequestMapping("search-list")
	public String searchList(ModelMap model) {
		SkuEntryQueryCondition condition = new SkuEntryQueryCondition();
		condition.setName("礼品卡");
		condition.setSortParam(SortTypeConstant.PRICE);
		condition.setSortType(SortTypeConstant.asc);
		condition.setStart(0);
		condition.setLimit(WebConstant.SKU_LIST_SIZE);
		condition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		condition.setImageType(ImageTypeConstant.IMAGE_TYPE_WEB_5);
		List<SkuDto> cardList = skuFacade.searchSkuList(condition).getSkuList();
		model.addAttribute("cardTypeList", cardList);
		return "giftcard-list";
	}
	
	@RequestMapping("giftCardDetail")
	public String skuDetail(@RequestParam(value = "skuId", defaultValue = "") String skuId,
			ModelMap model) {		
		Long nSkuId = null;
		try {
			nSkuId = Long.valueOf(skuId);
		} catch (NumberFormatException e) {
			logg.error("数字转换出错"+e.getMessage());
			return "common/404";
		}
		SelectedSkuDto selectedSkuDto = new SelectedSkuDto();
		selectedSkuDto.setSkuId(nSkuId);
		SkuDetailDto skuDetail = skuDetailFacade.getSkuDetailModel(selectedSkuDto);
		if(ObjectUtils.equals(null, skuDetail)) {
			return "common/none";
		}	
		WebSkuDetailDto webSkuDetailDto = webSkuFacade.getWebSkuDetail(skuDetail);
		model.addAttribute("skuDetail", webSkuDetailDto);
		return "giftcard-detail";
	}

}
