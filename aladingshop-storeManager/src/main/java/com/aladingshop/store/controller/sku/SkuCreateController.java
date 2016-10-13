/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月8日上午10:03:10
 */
package com.aladingshop.store.controller.sku;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuExtraInfo;
import com.aladingshop.sku.cms.model.SkuImage;
import com.aladingshop.sku.cms.model.SkuInventory;
import com.aladingshop.sku.cms.model.SkuPrice;
import com.aladingshop.store.auth.model.StoreAuthUser;
import com.aladingshop.store.config.Global;
import com.aladingshop.store.dto.SkuDto;
import com.aladingshop.store.util.FileUploadTools;
import com.fushionbaby.common.constants.SessionKeyConstant;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.RandomNumUtil;


/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月8日上午10:03:10
 */
@Controller
@RequestMapping("sku/create")
public class SkuCreateController extends AbstractSkuInfoController {
	/**
	 * 打开编辑页
	 * @param model
	 * @return
	 */
	@RequestMapping("open")
	public String open(Model model,HttpSession session){
		
		
		StoreAuthUser auUser = (StoreAuthUser)session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		if(auUser==null){
			return "redirect:" +Global.getAdminPath() + "/login/index";
		}
		
		model.addAttribute("allCategoryJson", this.getCategoryJson(auUser.getStoreCode(),null));
		return "models/sku/createSku";
	}
	/**
	 * 
	 * 添加门店商品
	 * @param skuNo
	 * @param skuName
	 * @param showorder
	 * @param currentPrice
	 * @param quotaNum
	 * @param categoryCode
	 * @param availableQty
	 * @param skuImage1
	 * @param skuImage2
	 * @param session
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("addStoreSku")
	public Object addStoreSku(
			SkuDto skuDto,
			MultipartFile skuImage1,
			HttpSession session,Model model,RedirectAttributes redirectAttributes) {
		
		StoreAuthUser auUser = (StoreAuthUser)session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		if(auUser==null){
			return "redirect:" +Global.getAdminPath() + "/login/index";
		}
		File image1 = null;
		String imgPath1 = null;
		try {
			if(skuImage1!=null && StringUtils.isNotBlank(skuImage1.getOriginalFilename())){
				 image1 = FileUploadTools.addFile(skuImage1,  IMG_PATH + auUser.getStoreCode());
				 imgPath1 = StringUtils.replace( image1.getPath(), "\\", "/");
				 
				 imgPath1 = StringUtils.replace(imgPath1, "/userfiles/images", "");
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "添加失败！");
		} catch (IOException e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "添加失败！");
		}
		
		Sku sku = new Sku();
		sku.setUniqueCode(RandomNumUtil.getCharacterAndNumber(20));
		sku.setStoreCode(auUser.getStoreCode());
		sku.setSkuNo(skuDto.getSkuNo()  );
		sku.setName(skuDto.getSkuName());
		sku.setQuotaNum(StringUtils.isBlank(skuDto.getQuotaNum()) ? 0 : Integer.valueOf(skuDto.getQuotaNum()));
		sku.setShoworder(StringUtils.isBlank(skuDto.getShoworder()) ? 0 : Integer.valueOf(skuDto.getShoworder()));
		sku.setCategoryCode(skuDto.getCategoryCode());
		sku.setStatus(SkuStatusEnum.SKU_STATUS_INIT.getStrVlue());
		SkuPrice skuPrice = new SkuPrice();
		skuPrice.setCurrentPrice(skuDto.getCurrentPrice());
		skuPrice.setStoreCode(auUser.getStoreCode());
		skuPrice.setSkuCode(sku.getUniqueCode());	
		SkuInventory skuInventory = new SkuInventory();
		skuInventory.setAvailableQty(skuDto.getAvailableQty()==null ? 0 : Integer.valueOf(skuDto.getAvailableQty()));
		skuInventory.setStoreCode(auUser.getStoreCode());
		skuInventory.setSkuCode(sku.getUniqueCode());
		skuInventory.setProductCode(sku.getProductCode());
		SkuImage skuImage = new SkuImage();
		skuImage.setSkuCode(sku.getUniqueCode());
		skuImage.setStoreCode(auUser.getStoreCode());
		skuImage.setSortOrder(1);
		skuImage.setImgUrl(imgPath1);
		SkuExtraInfo skuExtraInfo = new SkuExtraInfo();
		skuExtraInfo.setSkuCode(sku.getUniqueCode());
		skuExtraInfo.setStoreCode(auUser.getStoreCode());
		skuInfoService.addOrUpdate(sku);
		skuPriceService.add(skuPrice);
		skuInventoryService.add(skuInventory);
		skuImageService.add(skuImage);
		skuExtraInfoService.add(this.initExtraInfo(skuExtraInfo));
		
		addMessage(redirectAttributes, "添加成功！");
		return "redirect:" +Global.getAdminPath() + "/sku/create/open";
	}
	

}
