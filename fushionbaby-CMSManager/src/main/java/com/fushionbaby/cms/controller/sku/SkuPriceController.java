/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月3日下午5:25:28
 */
package com.fushionbaby.cms.controller.sku;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuPrice;
import com.aladingshop.store.model.Store;
import com.aladingshop.store.service.StoreService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.dto.SkuDto;
import com.fushionbaby.cms.dto.SkuPriceDto;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.util.BasePagination;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月3日下午5:25:28
 */
@Controller
@RequestMapping("sku")
public class SkuPriceController extends SkuBaseController {

	
	private static final Log LOGGER=LogFactory.getLog(SkuPriceController.class);
	@Autowired
	private StoreService<Store> StoreService;
	/**
	 * 分页查询商品价格列表
	 * 
	 * @param skuDto
	 *            商品价格信息
	 * @param page
	 *            分页信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/findSkuPriceList")
	public String findSkuPriceList(SkuDto skuDto, BasePagination page, Model model) {
		Long time=new Date().getTime();
		LOGGER.info("商品价格的查询开始时间为："+time);
		
		BasePagination basePage = new BasePagination();
		if (ObjectUtils.notEqual(page, null)) {
			basePage = page;
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("productCode", skuDto.getProductCode());
		params.put("skuNo", skuDto.getSkuNo());
		params.put("storeCode", CommonConstant.STORE_CODE_SHOP);
		params.put("barCode", skuDto.getBarCode());
		params.put("name", skuDto.getSkuName());
		params.put("status", skuDto.getStatus());
		params.put("createTimeFrom", skuDto.getCreateTimeFrom());
		params.put("createTimeTo", skuDto.getCreateTimeTo());
		params.put("storeCode",skuDto.getStoreCode());
		basePage.setParams(params);
		BasePagination pageResult = skuService.getPageList(basePage);
		@SuppressWarnings("unchecked")
		List<SkuDto> skuList = this.getSkuDtoList((List<Sku>)pageResult.getResult());
		pageResult.setResult(skuList);
		model.addAttribute("page", pageResult);
	
		model.addAttribute("storeList", StoreService.findAll());
		model.addAttribute("skuDto", skuDto);
		LOGGER.info("商品价格的查询时间结束为"+new Date().getTime()+"。总查询耗时为："+(new Date().getTime()-time)/1000+"秒。");
		return "models/sku/skuPriceList";
	}
	
	
	
	/**
	 * 商品管理中，修改商品价格；
	 * 
	 * @param scode
	 *            商品编号
	 * @param model
	 * @version 2015-6-16
	 * @return
	 */
	@RequestMapping(value = "/skuPriceModify", method = RequestMethod.GET)
	public String skuPriceModify( String uniqueCode, ModelMap model, RedirectAttributes redirectAttributes) {

		SkuDto skuDto = this.getSkuDto(uniqueCode);
		
		model.addAttribute("sku", skuDto);
		return "models/sku/skuPriceModify";
	}
	
	
	
	/**
	 * 修改商品信息
	 * 
	 * @param id
	 * @param redirectAttributes
	 * @version 2015-6-16
	 * @return
	 */
	@RequestMapping(value = "/modifySkuPrice", method = RequestMethod.POST)
	public String modifySkuPrice(SkuDto skuDto,SkuPriceDto skuPriceDto, RedirectAttributes redirectAttributes, HttpSession session) {

		AuthUser user = CMSSessionUtils.getSessionUser(session);
		if (user == null) {
			return "redirect:" + Global.getAdminPath() + "/login/login";
		}

		Sku sku = skuService.queryByUniqueCode(skuDto.getUniqueCode());
		if (sku == null) {
			addMessage(redirectAttributes, "修改失败，该商品数据异常");
			return "redirect:" + Global.getAdminPath() + "/sku/findSkuPriceList";
		}

		SkuPrice skuPrice = skuPriceService.findBySkuCode(sku.getUniqueCode());
		if (skuPrice == null) {
			skuPrice = new SkuPrice();
		}
		skuPrice.setUpdateId(user.getId());
		
		skuPrice.setCostPrice(skuPriceDto.getCostPrice());
		skuPrice.setCurrentPrice(skuPriceDto.getCurrentPrice());
		skuPrice.setAladingPrice(skuPriceDto.getAladingPrice());
		skuPrice.setSkuCode(sku.getUniqueCode());
		if (skuPrice.getId() == null) {
			skuPriceService.add(skuPrice);
		} else {
			skuPriceService.update(skuPrice);
		}
		skuService.update(sku);
		addMessage(redirectAttributes, "修改成功");
		return "redirect:" + Global.getAdminPath() + "/sku/findSkuPriceList";
	}

}
