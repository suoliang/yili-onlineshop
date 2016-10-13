package com.fushionbaby.cms.controller.sku;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aladingshop.sku.cms.dto.SkuInventoryDto;
import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuInventory;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.aladingshop.sku.cms.service.SkuInventoryService;
import com.aladingshop.store.model.Store;
import com.aladingshop.store.service.StoreService;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.util.BasePagination;

/***
 * 
 * @author King 索亮
 * 
 */
@Controller
@RequestMapping("/sku")
public class SkuInventoryController extends BaseController {

	@Autowired
	private SkuInventoryService<SkuInventory> skuInventoryService;
	@Autowired
	private SkuInfoService skuInfoService;
	@Autowired
	private StoreService<Store> storeService;

	private static final Log logger = LogFactory.getLog(SkuInventoryController.class);

	/***
	 * 库存列表
	 * 
	 * @param skuCode
	 * @param page
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/skuInventoryList")
	public String findList(SkuInventoryDto skuInventoryDto,BasePagination page, ModelMap model) {
		Long time=new Date().getTime();
		logger.info("商品库存的查询开始时间为："+time);
		try {
			if (page == null) {
				page = new BasePagination();
			}
			/** 分页参数封装 */
			Map<String, String> params = new HashMap<String, String>();
			params.put("skuNo", skuInventoryDto.getSkuNo());
			params.put("storeCode",  CommonConstant.STORE_CODE_SHOP);
			params.put("productCode", skuInventoryDto.getProductCode());
			params.put("skuName", skuInventoryDto.getSkuName());
			params.put("createTimeFrom", skuInventoryDto.getCreateTimeFrom());
			params.put("createTimeTo", skuInventoryDto.getCreateTimeTo());
			params.put("storeCode", skuInventoryDto.getStoreCode());
			page.setParams(params);
			/** 分页对象 */
			page = skuInventoryService.getListPage(page);
			/** 分页结果集 */
			List<SkuInventory> skuInventoryList = (List<SkuInventory>) page.getResult();
			
			List<SkuInventoryDto> skuInventoryDtos = new ArrayList<SkuInventoryDto>();
			for (SkuInventory skuInventory : skuInventoryList) {
				 Sku sku = skuInfoService.queryByUniqueCode(skuInventory.getSkuCode());
				 SkuInventoryDto dto = new SkuInventoryDto();
				if(sku != null){
					dto.setSkuCode(sku.getUniqueCode());
					dto.setBarCode(sku.getBarCode());
					dto.setSkuNo(sku.getSkuNo());
					dto.setAvailableQty(skuInventory.getAvailableQty());
					dto.setCreateTime(skuInventory.getCreateTime());
					dto.setProductCode(skuInventory.getProductCode());
					dto.setSkuName(skuInventory.getSkuName());
					dto.setSkuColor(skuInventory.getSkuColor());
					dto.setSkuSize(skuInventory.getSkuSize());
					Store store=this.storeService.findByCode(skuInventory.getStoreCode());
				    if(store!=null)
					    	 dto.setStoreName(store.getName());
					skuInventoryDtos.add(dto);
				}
			}
			page.setResult(skuInventoryList);

			model.addAttribute("skuInventoryList",skuInventoryDtos);
			model.addAttribute("page", page);
			model.addAttribute("storeList", storeService.findAll());
			model.addAttribute("skuInventoryDto", skuInventoryDto);

			logger.info("商品库存的查询时间结束为"+new Date().getTime()+"。总查询耗时为："+(new Date().getTime()-time)/1000+"秒。");
			
		} catch (Exception e) {
			logger.error("商品库存列表加载失败", e);
		}

		return "models/sku/skuInventory/inventoryList";
	}

	/***
	 * 修改时加载数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/loadSkuInventory/{skuCode}",method=RequestMethod.GET)
	public String loadSkuInventory(@PathVariable String skuCode, ModelMap model) {
		SkuInventory skuInventory = null;
		SkuInventoryDto skuInventoryDto = new SkuInventoryDto();
		try {
			skuInventory = skuInventoryService.findBySkuCode(skuCode);
			
			Sku sku = skuInfoService.queryByUniqueCode(skuInventory.getSkuCode());
			skuInventoryDto.setBarCode(sku.getBarCode());
			skuInventoryDto.setSkuNo(sku.getSkuNo());
			skuInventoryDto.setSkuCode(sku.getUniqueCode());
			skuInventoryDto.setAvailableQty(skuInventory.getAvailableQty());
		} catch (Exception e) {
			logger.error("商品库存加载失败", e);
		}
		model.addAttribute("skuInventoryDto",skuInventoryDto);
		return "models/sku/skuInventory/inventoryModify";
	}

	
	
	/***
	 * 修改操作
	 * 
	 * @param skuInventory
	 * @param response
	 */
	@RequestMapping("modifyInventory/{skuCode}/{quantity}")
	public void modifyInventory(@PathVariable String skuCode,@PathVariable Integer quantity, HttpServletResponse response) {
		skuInventoryService.updateInventoryQuantity(skuCode, quantity);;
	}

}
