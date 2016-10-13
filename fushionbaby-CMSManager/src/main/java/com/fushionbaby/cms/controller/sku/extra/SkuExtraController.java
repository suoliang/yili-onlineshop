/**aladingshop.com/ 上海一里网络科技有限公司
 */
package com.fushionbaby.cms.controller.sku.extra;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuCategory;
import com.aladingshop.sku.cms.model.SkuExtraInfo;
import com.aladingshop.sku.cms.model.SkuProductImage;
import com.aladingshop.sku.cms.service.SkuCategoryService;
import com.aladingshop.sku.cms.service.SkuExtraInfoService;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.aladingshop.sku.cms.service.SkuProductImageService;
import com.aladingshop.store.model.Store;
import com.aladingshop.store.service.StoreService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.config.FileUtils;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.sku.SkuBaseController;
import com.fushionbaby.cms.dto.SkuDto;
import com.fushionbaby.cms.util.BatchUploadFiles;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.cms.util.ImagePathUtil;
import com.fushionbaby.cms.util.ImageUploadUtil;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.util.BasePagination;

/**
 * @description 
 * @author 孟少博
 * @date 2015年10月27日下午1:45:00
 */
@Controller
@RequestMapping("sku/extra")
public class SkuExtraController extends SkuBaseController {
	
	
	private static final String FTP_PATH = "/userfiles/images/sku/products";
	
	@Autowired
	private SkuInfoService skuService;
	@Autowired
	private SkuExtraInfoService<SkuExtraInfo> skuExtraInfoService;
	@Autowired
	private SkuCategoryService<SkuCategory> skuCategoryService;
	@Autowired
	private SkuProductImageService<SkuProductImage> skuProductImgService;
	@Autowired
	private StoreService<Store> StoreService;
	
	private static final Log LOGGER=LogFactory.getLog(SkuExtraController.class);
	/**
	 * 分页查询商品列表
	 * 
	 * @param skuDto
	 *            商品信息
	 * @param page
	 *            分页信息
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findSkuList")
	public String findSkuList(SkuDto skuDto, BasePagination page, Model model) {

		Long time= new Date().getTime();
		LOGGER.info("商品额外信息表的查询结果  开始时间："+time);
		
		BasePagination basePage = new BasePagination();
		if (ObjectUtils.notEqual(page, null)) {
			basePage = page;
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put("productCode", skuDto.getProductCode());
		params.put("barCode", skuDto.getBarCode());
		params.put("skuNo", skuDto.getSkuNo());
		params.put("categoryCode", skuDto.getCategoryCode());
		params.put("name", skuDto.getSkuName());
		if(StringUtils.isNotBlank(skuDto.getIsMemberSku())){
			params.put("isMemberSku", skuDto.getIsMemberSku());
		}
		params.put("createTimeFrom", skuDto.getCreateTimeFrom());
		params.put("createTimeTo", skuDto.getCreateTimeTo());
		params.put("storeCode", skuDto.getStoreCode());
		basePage.setParams(params);
		BasePagination pageResult = skuExtraInfoService.getPageList(basePage);
		
		List<SkuDto> skuDtos = new ArrayList<SkuDto>();
		
		for (SkuExtraInfo skuExtraInfo : (List<SkuExtraInfo>)pageResult.getResult()) {
			SkuDto dto = new SkuDto();
			dto.setIsMemberSku(skuExtraInfo.getIsMemberSku());
			skuDtos.add(this.getSkuDto(skuExtraInfo.getSkuCode()));
			
		}
		
		List<SkuCategory> categoryList  = skuCategoryService.getCategoryByLevel(3,null);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("storeList", StoreService.findAll());
		model.addAttribute("skuDtos", skuDtos);
		model.addAttribute("page", pageResult);
		model.addAttribute("skuDto", skuDto);
		
		LOGGER.info("商品额外信息查询结束时间："+new Date().getTime()+"。总耗时为："+(new Date().getTime()-time)/1000 +"秒。");
		return "models/sku/extra/skuExtraList";
	}
	
	/**
	 * 编辑会员商品
	 * @param selectedSkuList 选择的商品列表
	 * @param isMemberSku 是否是会员 商品
	 * @return
	 */
	@ResponseBody
	@RequestMapping("editMemberSku")
	public Object editMemberSku(String selectedSkuList,String isMemberSku){
		
		String[] skuCodes = selectedSkuList.split(",");
		if(skuCodes==null || skuCodes.length <= 0){
			return false;
		}
		for (String skuCode : skuCodes) {
			SkuExtraInfo skuExtraInfo = skuExtraInfoService.findBySkuCode(skuCode);
			if(skuExtraInfo==null){
				continue;
			}
			skuExtraInfo.setIsMemberSku(isMemberSku);
			skuExtraInfoService.update(skuExtraInfo);
		}
		
		return true;
	}
	/**
	 * 图文详情页
	 * @param productCode
	 * @param model
	 * @return
	 */
	@RequestMapping("goGraphicDetails")
	public String goGraphicDetails(String productCode,Model model){
		
		List<SkuProductImage> images = this.getProductImage(productCode);
		StringBuffer picsb = new StringBuffer();
		for (SkuProductImage skuProductImage : images) {
			picsb.append("|" + skuProductImage.getImgPath());
		}
		
		
		List<String> skuNoList = new ArrayList<String>();
		List<String> skuNameList = new ArrayList<String>();
		List<Sku> skuList = skuService.queryByProductCode(productCode);
		for (Sku sku : skuList) {
			skuNoList.add(sku.getSkuNo());
			skuNameList.add(sku.getName());
		}
		model.addAttribute("picsb", picsb.toString());/* 编辑页使用 */
		model.addAttribute("productImages", images);/* 查看详情页使用 */
		model.addAttribute("skuNos", StringUtils.join(skuNoList, ","));
		model.addAttribute("skuNames", StringUtils.join(skuNameList, ","));
		model.addAttribute("productCode", productCode);
		
		return "models/sku/extra/skuGraphicDetailsEdit";
		
	}
	
	/**
	 * 获取产品图片
	 * 
	 * @param pcode
	 * @return
	 */
	private List<SkuProductImage> getProductImage(String pcode) {

		List<SkuProductImage> images = skuProductImgService.findBySkuProductCode(pcode);
		for (SkuProductImage skuProductImage : images) {
			skuProductImage.setImgPath(Global.getImagePath() + skuProductImage.getImgPath());
		}
		return images;
	}
	
	@ResponseBody
	@RequestMapping("editGraphicDetailsSort")
	public Object editGraphicDetailsSort(Long id,Integer sortOrder,Model model){
		
		
		try {
			SkuProductImage skuProductImage	 = skuProductImgService.findById(id);
			
			if(skuProductImage!=null){
				skuProductImage.setSortOrder(sortOrder);
				skuProductImgService.update(skuProductImage);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			return false;
		}
		
		return true;
		
	}
	
	
	/**
	 * 上传图文详情
	 * @param productCode 产品编号
	 * @param graphicDetails 图片详情集合
	 * @param session 
	 * @param model
	 * @param redirectAttributes
	 * @param request
	 * @return
	 */
	@RequestMapping("uploadGraphicDetails")
	public Object uploadGraphicDetails(String productCode,String graphicDetails, HttpSession session, ModelMap model,
			RedirectAttributes redirectAttributes, HttpServletRequest request){
		
		if(StringUtils.isBlank(productCode)){
			addMessage(redirectAttributes, "该产品编号不存在");
		}
		AuthUser user = CMSSessionUtils.getSessionUser(session);
		try {
			this.updateProductImage(graphicDetails, productCode, user);
		} catch (Exception e) {
			logger.error("上传图片异常," + e.getMessage());
			e.printStackTrace();
			addMessage(redirectAttributes, "出现异常");
		}
		return "redirect:" + Global.getAdminPath() + "/sku/extra/goGraphicDetails?productCode="+productCode;
	}
	
	/**
	 * 修改图片
	 * 
	 * @param graphicDetails
	 *            图片集合
	 * @param pCode
	 *            产品编号
	 */
	private void updateProductImage(String graphicDetails, String productCode, AuthUser user) {
		if (StringUtils.isBlank(graphicDetails)) {
			List<SkuProductImage> pimgs = skuProductImgService.findAllBySkuProductCode(productCode);
			for (SkuProductImage skuProductImage : pimgs) {
				FileUtils.deleteQuietly(new File(Global.getImagePath() + skuProductImage.getImgPath())); // 删除图片
			}
			skuProductImgService.deleteByProductCode(productCode);
			return;
		}
		List<String> newImages = ImagePathUtil.getImageNameList(graphicDetails);/* 上传的新图片 */
		List<SkuProductImage> beforeImages = skuProductImgService.findBySkuProductCode(productCode);/* 之前的图片 */
		if(beforeImages==null){
			beforeImages = new ArrayList<SkuProductImage>();
		}
		List<String> beforeImageUrls = new ArrayList<String>();
		for (SkuProductImage bi : beforeImages) {
			beforeImageUrls.add(bi.getImgPath());
		}
		List<String> eqImages = ImageUploadUtil.getEqImages(beforeImageUrls, newImages);
		List<String> removeImages = ImageUploadUtil.removeImages(beforeImageUrls, eqImages);
		for (String ri : removeImages) {
			for (SkuProductImage bi : beforeImages) {
				if (StringUtils.equals(ri, bi.getImgPath())) {
					SkuProductImage i = skuProductImgService.findById(bi.getId());
					if (ObjectUtils.notEqual(null, i)) {
						skuProductImgService.deleteById(i.getId());
						// FileUtils.deleteQuietly(new
						// File(Global.IMAGE_PHPS_PATH+beforeImage.getImgUrl()));
						// //删除图片
						BatchUploadFiles.delFtpImage(BatchUploadFiles.getFileName(i.getImgPath()), FTP_PATH + "/"
								+ BatchUploadFiles.getFileBeforDic(i.getImgPath())); // FTP删除
					}
				}
			}
		}

		Set<String> newImageSet = ImageUploadUtil.getNewImageSet(newImages, eqImages);
		if (CollectionUtils.isEmpty(newImageSet)) {
			return;
		}
		for (String n : newImageSet) {
			SkuProductImage productImage = new SkuProductImage();
			productImage.setProductCode(productCode);
			productImage.setImgPath(n);
			productImage.setIsDisable(CommonConstant.YES);/* 默认为显示 */
			productImage.setCreateId(user.getId());
			productImage.setSortOrder(0);
			
			skuProductImgService.add(productImage);
		}

	}

}
