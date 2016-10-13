/**
 * 
 */
package com.fushionbaby.wap.controller.sku;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fushionbaby.common.condition.member.MemberCommentQueryCondition;
import com.fushionbaby.common.condition.sku.SkuLinkSkusQueryCondition;
import com.fushionbaby.common.constants.ImageTypeConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.sku.SelectedSkuDto;
import com.fushionbaby.common.dto.sku.SkuDetailDto;
import com.fushionbaby.common.dto.sku.web.WebSkuDetailDto;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.facade.biz.common.member.MemberCommentFacade;
import com.fushionbaby.facade.biz.common.sku.SkuDetailFacade;
import com.fushionbaby.facade.biz.web.sku.WebSkuFacade;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.service.FindSkuService;

/**
 * @author mengshaobo
 * 
 */
@Controller
@RequestMapping("/product")
public class SkuDetailController{

	@Autowired
	private FindSkuService findSkuService;
	@Autowired
	private SkuDetailFacade skuDetailFacade;
	@Autowired
	private MemberCommentFacade memberCommentFacade;
	@Autowired
	private WebSkuFacade  webSkuFacade;
	
	/**记录日志*/
	private static final Log logger = LogFactory.getLog(SkuDetailController.class);
	
	/**
	 * 通过限时抢购商品的编号查询商品详情
	 * @param skuCode 商品编号
	 * @param model
	 * @return
	 */
	@RequestMapping("skuDetailByTimelimit")
	public String skuDetailByTimelimit(@RequestParam("skuCode") String skuCode,ModelMap model){
		Sku sku = findSkuService.queryBySkuCode(skuCode);
		if(sku != null && StringUtils.equals(sku.getStatus(), SkuStatusEnum.SKU_STATUS_TOP.getStrVlue())){
			Long skuId = sku.getId();
			SelectedSkuDto selectedSkuDto = new SelectedSkuDto();
			selectedSkuDto.setSkuId(skuId);
			SkuDetailDto skuDetail =  skuDetailFacade.getSkuDetailModel(selectedSkuDto);
			if(skuDetail == null){
				return "common/404";
			}
			try {
				model.addAttribute("skuDetail", webSkuFacade.getWebSkuDetail(skuDetail));
				MemberCommentQueryCondition skuCommentQueryCondition = new MemberCommentQueryCondition();
				skuCommentQueryCondition.setSkuCode(skuCode);
				skuCommentQueryCondition.setStart(WebConstant.START_INDEX);
				skuCommentQueryCondition.setLimit(WebConstant.LINK_SKU_SIZE);				
				model.addAttribute("skuComments", memberCommentFacade.getMemberComments(skuCommentQueryCondition));
				SkuLinkSkusQueryCondition skuLinkSkusQueryCondition = new SkuLinkSkusQueryCondition();
				skuLinkSkusQueryCondition.setSkuCode(skuCode);
				skuLinkSkusQueryCondition.setStart(WebConstant.START_INDEX);
				skuLinkSkusQueryCondition.setLimit(WebConstant.LINK_SKU_SIZE);
				skuLinkSkusQueryCondition.setImageType(ImageTypeConstant.IMAGE_TYPE_WEB_7);
				model.addAttribute("linkSkuList", skuDetailFacade.getLinkSkus(skuLinkSkusQueryCondition));
				return "detail";
			} catch (Exception e) {
				logger.error("商品详情信息出错");
				return "common/404";
			}
		}
		return "common/404";
	}
	/**
	 * 查询商品详情信息
	 * 
	 * @param skuId
	 * @param productId
	 *            产品编号
	 * @param color
	 *            颜色
	 * @param size
	 *            尺寸
	 * @param model
	 * @return
	 */
	@RequestMapping("skuDetail")
	public String skuDetail(@RequestParam(value = "skuCode", defaultValue = "") String skuCode,
			@RequestParam(value = "productCode", defaultValue = "") String productCode,
			@RequestParam(value = "color", defaultValue = "") String color,
			@RequestParam(value = "size", defaultValue = "") String size, ModelMap model) {		
		String nSkuCode = skuCode;
		SelectedSkuDto selectedSkuDto = new SelectedSkuDto();
		selectedSkuDto.setSkuCode(nSkuCode);
		selectedSkuDto.setProductCode(productCode);
		selectedSkuDto.setColor(color);
		selectedSkuDto.setSize(size);
		SkuDetailDto skuDetail = skuDetailFacade.getSkuDetailModel(selectedSkuDto);
		if(skuDetail == null){
			return "common/none";
		}	
		WebSkuDetailDto webSkuDetailDto =	webSkuFacade.getWebSkuDetail(skuDetail);
		model.addAttribute("skuDetail", webSkuDetailDto);
		MemberCommentQueryCondition skuCommentQueryCondition = new MemberCommentQueryCondition();
		skuCommentQueryCondition.setSkuCode(nSkuCode);
		skuCommentQueryCondition.setStart(WebConstant.START_INDEX);
		skuCommentQueryCondition.setLimit(WebConstant.LINK_SKU_SIZE);				
		model.addAttribute("skuComments", memberCommentFacade.getMemberComments(skuCommentQueryCondition));
		SkuLinkSkusQueryCondition skuLinkSkusQueryCondition = new SkuLinkSkusQueryCondition();
		skuLinkSkusQueryCondition.setSkuCode(nSkuCode);
		skuLinkSkusQueryCondition.setStart(WebConstant.START_INDEX);
		skuLinkSkusQueryCondition.setLimit(WebConstant.LINK_SKU_SIZE);
		model.addAttribute("linkSkuList", skuDetailFacade.getLinkSkus(skuLinkSkusQueryCondition));
		return "detail";
	}
}
