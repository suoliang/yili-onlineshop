package com.fushionbaby.cms.controller.sku.label;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.sku.cms.model.SkuImageType;
import com.aladingshop.sku.cms.model.SkuLabelImage;
import com.aladingshop.sku.cms.service.SkuImageTypeService;
import com.aladingshop.sku.cms.service.SkuLabelImageService;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.util.ImagePathUtil;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @description 标签图片
 * @author 孟少博
 * @date 2015年11月4日上午10:52:55
 */
@Controller
@RequestMapping("skuLabel/image")
public class SkuLabelImageController  extends BaseController{
	
	
	@Autowired
	private SkuLabelImageService<SkuLabelImage> skuLabelImageService;
	@Autowired
	private SkuImageTypeService<SkuImageType> imageTypeService;
/**
 * 查询标签图片列表
 * @param imageTypeCode 图片类型
 * @param labelCode 标签编号
 * @param page
 * @param model
 * @return
 */
	@RequestMapping("skuLabelImageList")
	public String skuLabelImageList(
			@RequestParam(value="imageTypeCode",defaultValue="") String imageTypeCode,
			@RequestParam(value="labelCode",defaultValue="") String labelCode,
			BasePagination page,Model model){
		if (page == null) {
			page = new BasePagination();
		}
		// 分页参数封装
		Map<String, String> params = new HashMap<String, String>();
		params.put("imageTypeCode", StringUtils.trim(imageTypeCode));
		params.put("labelCode", StringUtils.trim(labelCode));
		page.setParams(params);
		// 分页对象
		page = this.skuLabelImageService.getListPage(page);
		// 分页结果
		@SuppressWarnings("unchecked")
		List<SkuLabelImage> skuCategoryImageList = (List<SkuLabelImage>) page.getResult();
		
		
		model.addAttribute("skuLabelImageList", skuCategoryImageList);
		model.addAttribute("page", page);
		
		model.addAttribute("imageTypeCode", imageTypeCode);
		model.addAttribute("labelCode", labelCode);
		model.addAttribute("adPath",Global.getImagePath());
       	return "models/sku/skuLabel/image/skuLabelImageList";
	}
	/***
	 * 去添加图片页
	 * @param labelCode
	 * @param model
	 * @return
	 */
	@RequestMapping("goLabelImageAdd")
	public String goLabelImageAdd(String labelCode,Model model){
    	List<SkuImageType> imageTypeList = imageTypeService.findAll();
    	model.addAttribute("labelCode", labelCode);
    	model.addAttribute("imageTypeList", imageTypeList);
    	
    	return "models/sku/skuLabel/image/skuLabelImageAdd";
	}
	/**
	 * 添加图片
	 * @param skuLabelImage
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("addSkuLabelImage")
	public String addSkuLabelImage(SkuLabelImage skuLabelImage ,RedirectAttributes redirectAttributes){
		if(StringUtils.isNotBlank(skuLabelImage.getImgUrl())){
			skuLabelImage.setImgUrl(ImagePathUtil.getImageName(skuLabelImage.getImgUrl()));
		}
    	SkuImageType imageType = imageTypeService.findByCode(skuLabelImage.getImageTypeCode());
    	skuLabelImage.setImageTypeName(imageType.getName());
    	skuLabelImageService.add(skuLabelImage);
    	addMessage(redirectAttributes, "图片添加成功");
    	return "redirect:"+Global.getAdminPath()+"/skuLabel/image/skuLabelImageList?labelCode="+skuLabelImage.getLabelCode();
	}
	/**
	 * 删除图片 
	 * @param id
	 * @param labelCode
	 * @param redirectAttributes
	 * @return
	 */
	
	@RequestMapping("deleteLabelImage")
	public String deleteImage(String id,String labelCode,RedirectAttributes redirectAttributes){
		
		try {
			skuLabelImageService.deleteById(Long.valueOf(id));
			addMessage(redirectAttributes, "删除成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "删除失败");
		}
		return "redirect:"+Global.getAdminPath()+"/skuLabel/image/skuLabelImageList?labelCode="+labelCode;
	}
	
	
	/***
	 * 修改图片的顺序，修改完之后跳转回列表页
	 * @param sortOrder  原顺序
	 * @param id       id
	 * @param type     排序类型（上升 up，下降 down）
	 * @param labelCode    标签编号
	 * @return
	 */
	@RequestMapping("updateSortOrder")
	public String updateSortOrder(Integer sortOrder, Long id, String type, String labelCode){
		SkuLabelImage skuLabelImage=  skuLabelImageService.findById(id);
		if("down".equals(type)){
			sortOrder=sortOrder+1;
		}if("up".equals(type)){
			sortOrder=sortOrder-1<0?0:sortOrder-1;
		}
		skuLabelImage.setSortOrder(sortOrder);
		skuLabelImage.setUpdateTime(new Date());
		skuLabelImageService.update(skuLabelImage);
		return "redirect:"+Global.getAdminPath()+"/skuLabel/image/skuLabelImageList?labelCode="+labelCode;

	}
	
}
