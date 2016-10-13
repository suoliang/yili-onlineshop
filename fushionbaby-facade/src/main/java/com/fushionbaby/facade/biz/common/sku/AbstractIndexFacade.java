/**
 * 
 */
package com.fushionbaby.facade.biz.common.sku;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fushionbaby.common.condition.sku.SkuLabelRelationQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.facade.biz.AbstactSkuFacade;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuBrand;
import com.fushionbaby.sku.model.SkuCategory;
import com.fushionbaby.sku.model.SkuLabel;
import com.fushionbaby.sku.service.SkuBrandService;
import com.fushionbaby.sku.service.SkuCategoryService;
import com.fushionbaby.sku.service.SkuLabelRelationService;
import com.fushionbaby.sku.service.SkuLabelService;

/**
 * @author mengshaobo
 *
 */
@Service
public abstract class AbstractIndexFacade extends AbstactSkuFacade {
	@Autowired
	private SkuCategoryService<SkuCategory> skuCategoryImpl;
	@Autowired
	private SkuBrandService<SkuBrand> skuBrandService;
	@Autowired
	private SkuLabelService<SkuLabel> skuLabelService;
	@Autowired
	private SkuLabelRelationService skuLabelRelationService;
	
	/**
	 * 暂无售价
	 */
	//private static final String LIMIT_PRICCE_NULL = "暂无售价";
	
	
/*	public List<SkuTimelimitDto> getTimelimit(Integer size) {
		List<SkuTimelimit> list = skuTimelimitService.findByTop(size);

		List<SkuTimelimitDto> dtoList = new ArrayList<SkuTimelimitDto>();
		if(CollectionUtils.isEmpty(list)){
			return dtoList;
		}
		for(SkuTimelimit skuTimelimit : list){
			SkuPrice skuPrice = skuPriceService.findBySkuCode(skuTimelimit.getSkuCode());
			SkuTimelimitDto skuTimelimitDto = new SkuTimelimitDto();
			if(skuPrice !=null){
				skuTimelimitDto.setLimitPrice(skuPrice.getCurrentPrice().toString());
			}else{
				skuTimelimitDto.setLimitPrice(LIMIT_PRICCE_NULL);
			}
			Integer placeNum = skuTimelimit.getPlaceNum();
			skuTimelimitDto.setPlaceNum( placeNum!=null ? String.valueOf(placeNum) : StringUtils.EMPTY);
			skuTimelimitDto.setSkuCode(skuTimelimit.getSkuCode());
			skuTimelimitDto.setSkuName(skuTimelimit.getSkuName());
			skuTimelimitDto.setOffshelvestime(String.valueOf(skuTimelimit.getOffshelvestime().getTime()));
			dtoList.add(skuTimelimitDto);
		}
		return dtoList;
	}
	*/
	
	public List<SkuDto> getLabelSkuList(SkuLabelRelationQueryCondition queryCondition) {
		
		List<String> relUnCodes = skuLabelRelationService.querySkuUnCodeListByLabel(queryCondition);
		if (CollectionUtils.isEmpty(relUnCodes)) {
			return null;
		}
		List<Sku> skus = skuService.querySkuListByLabelRel(relUnCodes,null);
		List<SkuDto> skuList = this.getSkuDtoList(skus);
		if(CollectionUtils.isEmpty(skuList)){
			return new ArrayList<SkuDto>();
		}
		
		for (int i = 0 ; i < skuList.size();i++) {
			SkuDto skuDto = skuList.get(i);
			if(i==0 && StringUtils.equals(queryCondition.getLabelCode(),GlobalConfigConstant.JPTJ) 
					&& StringUtils.equalsIgnoreCase(queryCondition.getAppHome().toString(), CommonConstant.YES) ){
				skuDto.setImgPath(imageProcess.getThumImagePath(skuDto.getSkuCode(), ImageStandardConstant.IMAGE_STANDARD_APP_190));
			}else{
				skuDto.setImgPath(imageProcess.getThumImagePath(skuDto.getSkuCode(), queryCondition.getImageStandardCode()));
			}
		}
		
		return skuList;
	}
}
