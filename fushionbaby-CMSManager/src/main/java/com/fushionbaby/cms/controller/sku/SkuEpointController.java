package com.fushionbaby.cms.controller.sku;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuEpoint;
import com.aladingshop.sku.cms.service.SkuEpointService;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.common.util.BasePagination;


@Controller
@RequestMapping("/skuEpoint")
public class SkuEpointController extends BaseController {

		private static final Log LOGGER = LogFactory.getLog(SkuEpointController.class);
		@Autowired
		private SkuEpointService<SkuEpoint> skuEpointService;
		@Autowired
		private SkuInfoService skuInfoService;
		
		
		@SuppressWarnings("unchecked")
		@RequestMapping("skuEpointList")
		public String skuEpointList(BasePagination page,ModelMap model,String labelCode){
			try {
				if(page == null){
					page = new BasePagination();
				}
				Map<String,String> params = new HashMap<String, String>();
				params.put("labelCode", labelCode);
				page.setParams(params);
				page = skuEpointService.getListPage(page);
				List<SkuEpoint> list = (List<SkuEpoint>) page.getResult();
				model.addAttribute("list", list);
				model.addAttribute("page", page);
			} catch (Exception e) {
				LOGGER.error("查询商品积分表出错！", e);
				return "";
			}
			return "models/sku/skuEpoint/skuEpointList";
		}
		
		
		
		
		@SuppressWarnings("unchecked")
		@RequestMapping("goAddEpointSku")
		public String goAddSkuEpoint(@RequestParam(value="skuNo",defaultValue="")String skuNo,
									 @RequestParam(value="skuName",defaultValue="")String skuName,
									 BasePagination page,ModelMap model){
				try {
					if(page == null){
						page = new BasePagination();
					}
					List<Sku> list = new ArrayList<Sku>();
					/**为防止加载的商品过多，在没有搜索条件的情况下，加载空值到页面*/
					if(StringUtils.isNotBlank(skuNo) || StringUtils.isNotBlank(skuName)){
						
						Map<String, String> params = new HashMap<String, String>();
						params.put("skuNo", skuNo);
						params.put("name", skuName);
						page.setParams(params);
						page = skuInfoService.getPageList(page);
						list = (List<Sku>) page.getResult();
					}
					model.addAttribute("list", list);
					model.addAttribute("page", page);
					model.addAttribute("skuNo", skuNo);
					model.addAttribute("skuName", skuName);
				} catch (Exception e) {
					LOGGER.error("添加积分商品表出错！", e);
					return "";
				}
				
				return "models/sku/skuEpoint/addEpointSku";
			}
			
		
		
		@RequestMapping("addEpointSku/{uniqueCode}/{time}")
		public void addSku(@PathVariable(value="uniqueCode")String uniqueCode){
			
			Sku sku = skuInfoService.queryByUniqueCode(uniqueCode);
			SkuEpoint skuEpoint = skuEpointService.findBySkuCode(uniqueCode);
			
			if(skuEpoint != null){
				LOGGER.error("添加的积分商品已存在！");
				return ;
			}else{
				skuEpoint = new SkuEpoint();
				skuEpoint.setSkuCode(uniqueCode);
				skuEpoint.setSkuName(sku.getName());
				skuEpoint.setNeedEpoint(BigDecimal.ZERO);
				skuEpointService.add(skuEpoint);
			}
			
		}
		
		@RequestMapping("delEpointSku/{id}/{time}")
		public String deleteEpointSku(@PathVariable(value="id")Long id){
			
			skuEpointService.deleteById(id);
			
			return "redirect:"+Global.getAdminPath()+"/skuEpoint/skuEpointList";
		}
		
		@RequestMapping("editEpointSku/{id}/{time}")
		public String editEpointSku(@PathVariable(value="id")Long id,ModelMap model){
			
			SkuEpoint skuEpoint = skuEpointService.findById(id);
			model.addAttribute("skuEpoint", skuEpoint);
			return "models/sku/skuEpoint/updateEpointSku";
			
		}
		
		@RequestMapping("updateSkuEpoint")
		public String update(SkuEpoint skuEpoint){
			
			if(skuEpoint.getId() != null){
				skuEpointService.update(skuEpoint);
				
			}
			return "redirect:"+Global.getAdminPath()+"/skuEpoint/skuEpointList";
			
		}
}
