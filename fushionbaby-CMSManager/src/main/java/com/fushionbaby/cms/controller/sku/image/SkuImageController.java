package com.fushionbaby.cms.controller.sku.image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.model.SkuImage;
import com.aladingshop.sku.cms.service.SkuImageService;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.SkuImageDto;
import com.fushionbaby.cms.util.BatchUploadFiles;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.cms.util.ImagePathUtil;
import com.fushionbaby.cms.util.ImageUploadUtil;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @author xupeijun
 * 
 */
@Controller
@RequestMapping("/skuImage")
public class SkuImageController extends BaseController {
	private static final String FTP_PATH = "/userfiles/images/sku/products";

	@Autowired
	private SkuImageService<SkuImage> skuImageService;

	@Autowired
	private SkuInfoService skuService;

	private static final Log logger = LogFactory.getLog(SkuImageController.class);

	/***
	 * 得到该对象 如果该对象已经添加过图片 则跳转到图片列表，否则直接跳转到添加页面
	 * 
	 * @param skuCode
	 * @param model
	 * @param anchorPage
	 *            返回记录页
	 * @author mengshaobo 2015-07-01
	 * @return
	 */
	@RequestMapping(value = "/findSkuImage/{uniqueCode}", method = RequestMethod.GET)
	public String findSkuImage(@PathVariable String uniqueCode,
			@RequestParam(value = "anchorPage", defaultValue = "1") Integer anchorPage, HttpSession session,
			RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) {

		try {
			Sku skuEntry = this.skuService.queryByUniqueCode(uniqueCode);

			List<SkuImage> imageList = this.skuImageService.findBySkuCode(uniqueCode);
			List<SkuImageDto> imageDtoList = new ArrayList<SkuImageDto>();
			for (SkuImage skuImage : imageList) {
				SkuImageDto s = new SkuImageDto();
				s.setSortOrder(skuImage.getSortOrder());
				s.setImgUrl(Global.getImagePath() + skuImage.getImgUrl());
				s.setCreateTime(DateFormatUtils.format(skuImage.getCreateTime(), "yyyy-MM-dd hh:mm:ss"));
				s.setId(skuImage.getId());
				imageDtoList.add(s);
			}
			StringBuffer picsb = new StringBuffer();
			for (SkuImageDto dto : imageDtoList) {
				picsb.append("|" + dto.getImgUrl());
			}
			model.addAttribute("picsb", picsb);
			model.addAttribute("sku", skuEntry);
			model.addAttribute("skuImageList", imageDtoList);
			model.addAttribute("anchorPage", anchorPage);
			model.addAttribute("user", CMSSessionUtils.getSessionUser(session));

		} catch (Exception e) {
			logger.error("加载商品图片页面失败", e);
		}
		return "/models/sku/skuImageAdd";
	}

	/**
	 * 上传商品图片
	 * 
	 * @param skuImage
	 *            商品图片DTO
	 * @param session
	 * @param model
	 * @author mengshaobo 2015-07-01
	 * @return
	 */
	@RequestMapping(value = "/uploadSkuImage", method = RequestMethod.POST)
	public String uploadSkuImage(SkuImageDto skuImage, HttpSession session, ModelMap model,
			RedirectAttributes redirectAttributes, HttpServletRequest request) {
		addMessage(redirectAttributes, "商品图片操作成功！");
		if (skuImage == null) {
			addMessage(redirectAttributes, "商品图片信息出现异常，上传失败！");
			return "redirect:" + Global.getAdminPath() + "/sku/findSkuList";
		}
		if (StringUtils.isBlank(skuImage.getSkuCode())) {
			addMessage(redirectAttributes, "商品编码不存在，上传失败！");
			return "redirect:" + Global.getAdminPath() + "/sku/findSkuList";
		}
		String imageUrls = skuImage.getImgUrl();
		try {
			AuthUser user = CMSSessionUtils.getSessionUser(session);
			List<String> imageList = ImagePathUtil.getImageNameList(imageUrls);
			List<SkuImage> sis = skuImageService.findBySkuCode(skuImage.getSkuCode());
			if (CollectionUtils.isEmpty(sis)) {
				for (String si : imageList) {
					SkuImage s = new SkuImage();
					s.setCreateId(user.getId());
					s.setSkuCode(skuImage.getSkuCode());
					s.setSortOrder(skuImage.getSortOrder() == null ? 0 : skuImage.getSortOrder());
					s.setImgUrl(si);
					skuImageService.add(s);
				}

			} else {
				this.updateUploadImage(sis, imageList, skuImage, user);
			}
		} catch (Exception e) {
			logger.error("上传图片异常," + e.getMessage());
			e.printStackTrace();
			addMessage(redirectAttributes, "当前图片出现异常，请联系开发人员");
		}
		return "redirect:" + Global.getAdminPath() + "/skuImage/findSkuImage/" + skuImage.getSkuCode();
	}

	/**
	 * 添加和修改上传图片
	 * 
	 * @param newImages
	 *            新图片集合
	 * @param skuImageDto
	 *            图片信息DTO
	 * @param user
	 * 
	 * @author mengshaobo 2015-07-01
	 */
	private void updateUploadImage(List<SkuImage> beforeImages, List<String> newImages, SkuImageDto skuImageDto,
			AuthUser user) {

		if (CollectionUtils.isEmpty(newImages)) {
			this.deleteImage(beforeImages);/* 如果新上传图片是空的，就删除。 */
			return;
		}
		List<String> beforeImageUrls = new ArrayList<String>();
		for (SkuImage bi : beforeImages) {
			beforeImageUrls.add(bi.getImgUrl());
		}
		List<String> eqImages = ImageUploadUtil.getEqImages(beforeImageUrls, newImages);
		List<String> removeImages = ImageUploadUtil.removeImages(beforeImageUrls, eqImages);
		for (String ri : removeImages) {
			for (SkuImage bi : beforeImages) {
				if (StringUtils.equals(ri, bi.getImgUrl())) {
					SkuImage i = skuImageService.findById(bi.getId());
					if (ObjectUtils.notEqual(null, i)) {
						skuImageService.deleteById(i.getId());
						BatchUploadFiles.delFtpImage(BatchUploadFiles.getFileName(i.getImgUrl()), FTP_PATH); // FTP删除
					}
				}
			}
		}

		Set<String> newImageSet = ImageUploadUtil.getNewImageSet(newImages, eqImages);
		if (CollectionUtils.isEmpty(newImageSet)) {
			return;
		}
		for (String n : newImageSet) {
			SkuImage s = new SkuImage();
			s.setCreateId(user.getId());
			s.setSkuCode(skuImageDto.getSkuCode());
			s.setSortOrder(skuImageDto.getSortOrder() == null ? 0 : skuImageDto.getSortOrder());
			s.setImgUrl(n);
			skuImageService.add(s);
		}
	}

	/**
	 * 删除图片
	 * 
	 * @param beforeImages
	 */
	private void deleteImage(List<SkuImage> beforeImages) {
		for (SkuImage skuImage : beforeImages) {
			SkuImage s = skuImageService.findById(skuImage.getId());
			if (ObjectUtils.notEqual(null, s)) {
				skuImageService.deleteById(skuImage.getId());/* 如果数据库存在的图片和新图片不相同就删除 */
				BatchUploadFiles.delFtpImage(BatchUploadFiles.getFileName(skuImage.getImgUrl()), FTP_PATH); // FTP删除
			}
		}
	}

	/**
	 * 商品图片显示顺序 窗口
	 * 
	 * @param id
	 *            要修改的图片ID
	 * @param sortOrder
	 *            显示顺序
	 * @param model
	 * @return
	 */
	@RequestMapping("/editSortOrderModel/{id}")
	public String editSortOrderModel(@PathVariable Long id, String sortOrder, ModelMap model,
			RedirectAttributes redirectAttributes) {
		SkuImage image = skuImageService.findById(id);

		if (image == null) {
			addMessage(redirectAttributes, "出现异常！");
			return "redirect:" + Global.getAdminPath() + "/sku/findSkuList";
		}
		Sku sku = skuService.queryByUniqueCode(image.getSkuCode());
		if (sku == null) {
			addMessage(redirectAttributes, "出现异常！");
			return "redirect:" + Global.getAdminPath() + "/sku/findSkuList";
		}
		model.addAttribute("id", id);
		model.addAttribute("sortOrder", sortOrder);
		model.addAttribute("skuCode", image.getSkuCode());
		return "/models/sku/skuImageEditSortOrder";
	}

	/**
	 * 编辑商品图片显示顺序
	 * 
	 * @param skuImageDto
	 * @return
	 */
	@RequestMapping(value = "/editSortOrder", method = RequestMethod.POST)
	public String editSortOrder(SkuImageDto skuImageDto, HttpSession session, RedirectAttributes redirectAttributes) {
		addMessage(redirectAttributes, "编辑成功！");
		AuthUser user = CMSSessionUtils.getSessionUser(session);
		SkuImage image = skuImageService.findById(skuImageDto.getId());
		if (image == null) {
			addMessage(redirectAttributes, "编辑失败，商品图片不存在！");
			return "redirect:" + Global.getAdminPath() + "/sku/findSkuList";
		}
		Sku sku = skuService.queryByUniqueCode(image.getSkuCode());
		if (sku == null) {
			addMessage(redirectAttributes, "编辑失败，商品不存在！");
			return "redirect:" + Global.getAdminPath() + "/sku/findSkuList";
		}
		image.setSortOrder(skuImageDto.getSortOrder());
		image.setUpdateId(user.getId());
		skuImageService.update(image);
		skuService.update(sku);
		return "redirect:" + Global.getAdminPath() + "/skuImage/findSkuImage/" + image.getSkuCode();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/findAll")
	public String query(@RequestParam(value = "skuNo", defaultValue = "") String skuNo,
			@RequestParam(value = "barCode", defaultValue = "") String barCode,
			@RequestParam(value = "imageTypeCode", defaultValue = "") String imageTypeCode, ModelMap model,
			BasePagination page) {
		model.addAttribute("skuNo", skuNo);
		model.addAttribute("barCode", barCode);
		model.addAttribute("imageTypeCode", imageTypeCode);
		if (page == null) {
			page = new BasePagination();
		}
		String uniqueCode = "";
		if (StringUtils.isNotBlank(skuNo)) {
			uniqueCode = skuService.queryBySkuNo(skuNo,null).getUniqueCode();
		}
		if (StringUtils.isNotBlank(barCode)) {
			uniqueCode = skuService.queryByBarCode(barCode,null).getUniqueCode();
		}
		// 分页参数封装
		Map<String, String> params = new HashMap<String, String>();
		params.put("skuCode", uniqueCode);
		params.put("imageTypeCode", imageTypeCode);
		page.setParams(params);
		// 分页对象
		page = skuImageService.getListPage(page);
		// 分页结果
		List<SkuImage> skuImageList = (List<SkuImage>) page.getResult();
		List<SkuImageDto> skuImageDtoList = new ArrayList<SkuImageDto>();
		for (SkuImage skuImage : skuImageList) {
			Sku sku = skuService.queryByUniqueCode(skuImage.getSkuCode());
			SkuImageDto skuImageDto = new SkuImageDto();
			if (sku != null) {
				skuImageDto.setBarCode(sku.getBarCode());
				skuImageDto.setSkuNo(sku.getSkuNo());
				skuImageDto.setSkuCode(sku.getUniqueCode());
				skuImageDto.setImgUrl(skuImage.getImgUrl());
				skuImageDto.setSortOrder(skuImage.getSortOrder());
				skuImageDto.setImageTypeCode(skuImage.getImageTypeCode());
				skuImageDtoList.add(skuImageDto);
			}
		}
		model.addAttribute("skuImageList", skuImageDtoList);
		model.addAttribute("page", page);

		model.addAttribute("sku_path", Global.getImagePath() + "/");
		return "models/img/skuImgList";
	}

}
