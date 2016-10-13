package com.fushionbaby.cms.controller.sku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuProductImage;
import com.aladingshop.sku.cms.service.SkuProductImageService;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.dto.SkuProductImageDto;
import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;

/**
 * @author cyla
 * 
 */

@Controller
@RequestMapping("/productImage")
public class SkuProductImageController  extends SkuBaseController {

	@Autowired
	private SkuProductImageService<SkuProductImage> skuProductImageService;
	/**
	 * 分页查询功能
	 * 
	 * @param productCode
	 *            产品编号
	 * @param model
	 * @param page
	 *            分页
	 * @return 显示页面
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/findAll")
	public String query(
			@RequestParam(value = "productCode", defaultValue = "") String productCode,
			@RequestParam(value = "productName", defaultValue = "") String productName,
			ModelMap model, BasePagination page) {
		if (page == null) {
			page = new BasePagination();
		}
		// 分页参数封装
		Map<String, String> params = new HashMap<String, String>();
		params.put("productCode", productCode);
		params.put("productName", productName);
		page.setParams(params);
		// 分页对象
		page = skuProductImageService.findBySkuProductCodeAndProductName(page);
		// 分页结果
		List<SkuProductImage> skuProductImageList = (List<SkuProductImage>) page
				.getResult();
		List<SkuProductImageDto> skuProductImageDtoList= new ArrayList<SkuProductImageDto>();
		for(SkuProductImage skuProductImage:skuProductImageList){
			List<Sku> skuList = skuService.queryByProductCode(skuProductImage.getProductCode());
			String productNametmp = skuList==null ? StringUtils.EMPTY : skuList.get(0).getName();
			SkuProductImageDto productImageDto=new SkuProductImageDto();
			productImageDto.setCreateId(skuProductImage.getCreateId());
			productImageDto.setCreateTime(skuProductImage.getCreateTime());
			productImageDto.setId(skuProductImage.getId());
			productImageDto.setImgPath(skuProductImage.getImgPath());
			productImageDto.setIsDisable(skuProductImage.getIsDisable());
			productImageDto.setProductCode(skuProductImage.getProductCode());
			productImageDto.setProductName(productNametmp);
			productImageDto.setSortOrder(skuProductImage.getSortOrder());
			productImageDto.setUpdateId(skuProductImage.getUpdateId());
			productImageDto.setUpdateTime(skuProductImage.getUpdateTime());
			productImageDto.setVersion(skuProductImage.getVersion());
			skuProductImageDtoList.add(productImageDto);
		}
		model.addAttribute("skuProductImageDtoList", skuProductImageDtoList);
		model.addAttribute("page", page);
		model.addAttribute("productCode", productCode);
		model.addAttribute("productName", productName);
		model.addAttribute("product_path", Global.getImagePath()  + "/");
		return "models/img/productImgList";
	}

	/**
	 * 通过id删除产品图片功能
	 * 
	 * @param id
	 * @return 操作结果
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public JsonResponseModel deleteSkuProductImage(
			@RequestParam(value = "id") String id) {
		JsonResponseModel jsonModel = new JsonResponseModel();
		try {
			if (StringUtils.isNotBlank(id)) {
				skuProductImageService.deleteById(Long.valueOf(id));
				jsonModel.Success("产品图片删除成功!");
			} else {
				jsonModel.Fail("id不能为空!");
			}
		} catch (Exception e) {
			jsonModel.Fail("产品图片删除失败!");
		}
		return jsonModel;
	}

	/**
	 * 修改产品图片显示顺序
	 * 
	 * @param id
	 * @param sortOrder  产品图片显示顺序          
	 * @return 操作结果
	 */
	@RequestMapping("/updateById")
	@ResponseBody
	public JsonResponseModel updateById(
			@RequestParam(value = "id") String id,
			@RequestParam(value = "sortOrder", defaultValue = "0") Integer sortOrder) {
		JsonResponseModel jsonModel = new JsonResponseModel();

		try {
			if (StringUtils.isNotBlank(id)) {
				SkuProductImage skuProductImage = new SkuProductImage();
				skuProductImage.setSortOrder(sortOrder);
				skuProductImage.setId(Long.valueOf(id));
				skuProductImageService.update(skuProductImage);
				jsonModel.Success("产品图片显示顺序修改成功!");
			} else {
				jsonModel.Fail("id不能为空!");
			}
		} catch (Exception e) {
			jsonModel.Fail("产品图片显示顺序修改失败!");
		}
		return jsonModel;
	}
}
