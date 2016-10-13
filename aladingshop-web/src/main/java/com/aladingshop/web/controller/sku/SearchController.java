/**
 * 
 */
package com.aladingshop.web.controller.sku;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.common.condition.sku.SkuSearchQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.sku.CategoryDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.dto.sysmgr.SearchKeywordsDto;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.facade.biz.common.order.dto.SkuSearchResultDto;
import com.fushionbaby.facade.biz.common.sku.BrandFacade;
import com.fushionbaby.facade.biz.common.sku.CategoryFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;

/**
 * @description 商品搜素
 * @author 孟少博
 * @date 2015年8月3日上午11:34:47
 */
@Controller
@RequestMapping("/search")
public class SearchController {

	
	@Autowired
	private SkuFacade skuFacade;
	@Autowired
	private BrandFacade brandFacade;
	@Autowired
	private CategoryFacade categoryFacade;
	
	
	
	@RequestMapping("category")
	public Object skuByCategory(String code,
			@RequestParam(value="curPage",defaultValue="1") Integer curPage,
			Model model){
		
		return this.searchSku(null,code, null, null, null, null,"category",curPage, model);
	}
	
	@RequestMapping("brand")
	public Object skuByBrand(String code,
			@RequestParam(value="curPage",defaultValue="1") Integer curPage,
			Model model){
		return this.searchSku(null, null, code, null, null, null,"brand",curPage,model);
	}
	
	@RequestMapping("/q")
	public Object skuBySearch(String keywords,
			@RequestParam(value="curPage",defaultValue="1") Integer curPage,
			Model model){
		return this.searchSku(keywords, null,null, null, null, null, "keyword",curPage,model);
	}
	
	
	/**
	 * 商品搜索
	 * @param keywords 搜索关键字
	 * @param categoryCode 分类编号 
	 * @param brandCode 品牌编号
	 * @param price 价格
	 * @param sortParam 排序参数（价格，销量）
	 * @param sortType 排序类型（升序，降序）
	 * @param model
	 * @return
	 */
	@RequestMapping("skuSearch")
	public Object searchSku(@RequestParam(value="keywords",defaultValue="") String keywords,
			@RequestParam(value="categoryCode",defaultValue="") String categoryCode,
			@RequestParam(value="brandCode",defaultValue="") String brandCode,
			@RequestParam(value="price",defaultValue="") String price,
			@RequestParam(value="sortParam",defaultValue="") String sortParam,
			@RequestParam(value="sortType",defaultValue="") String sortType,
			@RequestParam(value="index",defaultValue="") String index,
			@RequestParam(value="curPage",defaultValue="1") Integer curPage,
			Model model){
		
		String searchKey="";
		if(StringUtils.isNotBlank(keywords)){
			try {
				byte bb[] = keywords.getBytes("ISO-8859-1");
				searchKey = new String(bb,"UTF-8");
				SearchKeywordsDto searchKeywordsDto = new SearchKeywordsDto();
				searchKeywordsDto.setKeyword(StringUtils.trim(searchKey));
				searchKeywordsDto.setSourceCode(SourceConstant.WEB_CODE);
				skuFacade.insertSearchKey(searchKeywordsDto);
				
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
				return "search/search-result-none";
			}
		}
		SkuSearchQueryCondition queryCondition = new SkuSearchQueryCondition();
		queryCondition.setStart(curPage);
		queryCondition.setLimit(WebConstant.SKU_LIST_SIZE);
		queryCondition.setSortParam(sortParam);
		queryCondition.setSortType(sortType);
		queryCondition.setPrice(price);
		queryCondition.setBrandCode(brandCode);
		queryCondition.setCategoryCode(categoryCode);
		queryCondition.setSearchKey(searchKey);
		queryCondition.setImageStandardCode(ImageStandardConstant.IMAGE_STANDARD_PC_300);
		SkuSearchResultDto skuSearchResultDto = skuFacade.skuListBysearchKey(queryCondition);
		
		if(skuSearchResultDto.getPageSet().getAmount() <=0){
			model.addAttribute("hotSkuList", this.searchResultModelHotSkuList());
			return "search/search-result-none";
		}
		if(StringUtils.equals(index, "category")){
			CategoryDto parentCategory = categoryFacade.getParentCategoryDto(StringUtils.EMPTY,categoryCode);
			model.addAttribute("parentCategory", parentCategory);
		}
		
		model.addAttribute("index", index);
		model.addAttribute("sortParam", sortParam);
		model.addAttribute("sortType", sortType);
		model.addAttribute("price", price);
		model.addAttribute("keywords", searchKey);
//		model.addAttribute("selcategory",
//				StringUtils.isBlank(categoryCode)?new CategoryDto():
//					categoryFacade.getCategoryDto(StringUtils.EMPTY,categoryCode));
//		model.addAttribute("selbrand", 
//				StringUtils.isBlank(brandCode)?new BrandDto():
//					brandFacade.getBrandDto(brandCode));
		
		//model.addAttribute("brandList",skuSearchResultDto.getBrandList());
		//model.addAttribute("categoryList", skuSearchResultDto.getCategoryList());
		model.addAttribute("pageSet", skuSearchResultDto.getPageSet());
		return "search/search-result";
	} 

	/**
	 * 随机拿去分类下面热销的商品
	 * @return
	 */
	private List<SkuDto> searchResultModelHotSkuList(){
		SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
		List<CategoryDto> categoryList = categoryFacade.findAllCategory(StringUtils.EMPTY,false,CommonConstant.YES);
		Collections.shuffle(categoryList);
		queryCondition.setCategoryCode(categoryList.get(0).getCode());
		queryCondition.setStart(0);
		queryCondition.setLimit(10);
		queryCondition.setImageStandardCode(ImageStandardConstant.IMAGE_STANDARD_PC_300);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		List<SkuDto> skuList = skuFacade.getHotSkuList(queryCondition);
		return skuList;
	}
	
	/**
	 * 搜索框 自动补全
	 * @param q 搜索关键字
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="suggest",method=RequestMethod.POST)
	public Object suggest(String q){
		
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		
		results = skuFacade.queryTerm(StringUtils.trim(q), 10);
		
		List<String> suggests = new ArrayList<String>();
		
		for (Map<String,Object> map : results) {
			Long count = (Long)map.get("count");
			String name = (String)map.get("name");
			if(count > 0){
//				suggests.add(name+"-------约"+count+"个结果");
				suggests.add(name);
			}
		}
		
		return suggests;
	}
}
