package com.fushionbaby.cms.controller.sku.category;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.sku.cms.model.SkuCategory;
import com.aladingshop.sku.cms.model.SkuCategoryImage;
import com.aladingshop.sku.cms.model.SkuImageType;
import com.aladingshop.sku.cms.service.SkuCategoryImageService;
import com.aladingshop.sku.cms.service.SkuCategoryService;
import com.aladingshop.sku.cms.service.SkuImageTypeService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.auth.service.AuthUserService;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.cms.util.ImagePathUtil;
import com.fushionbaby.common.util.BasePagination;

/***
 * 
 * @description 类描述... 商品分类图片
 * @author 徐培峻
 * @date 2015年8月6日下午5:42:25
 */
@RequestMapping("/skuCategory")
@Controller
public class SkuCategoryImageController extends BaseController{

	@Autowired
	private SkuCategoryImageService<SkuCategoryImage> skuCategoryImageService;
	
	@Autowired
	private AuthUserService<AuthUser> authUserService;
	
	@Autowired
	private SkuCategoryService<SkuCategory> skuCategoryService;
	@Autowired
	private SkuImageTypeService<SkuImageType> imageTypeService;
	
	private static final Log logger=LogFactory.getLog(SkuCategoryImageController.class);
	
	private static final String RETURN_LIST="redirect:"+Global.getAdminPath()+"/skuCategory/skuCategoryImageList";
	/**登陆页面*/
	private static final String PRE_="models/sku/skuCategory/";
	
	@RequestMapping("skuCategoryImageList")
	public String CategoryImageList(
			@RequestParam(value="imageTypeCode",defaultValue="") String imageTypeCode,
			@RequestParam(value="categoryCode",defaultValue="") String categoryCode,
			BasePagination page,Model model){
	try {
		if (page == null) {
			page = new BasePagination();
		}
		// 分页参数封装
		Map<String, String> params = new HashMap<String, String>();
		params.put("imageTypeCode", imageTypeCode.trim());
		params.put("categoryCode", categoryCode.trim());
		
		
		page.setParams(params);
		// 分页对象
		page = this.skuCategoryImageService.getListPage(page);
		// 分页结果
		@SuppressWarnings("unchecked")
		List<SkuCategoryImage> skuCategoryImageList = (List<SkuCategoryImage>) page.getResult();
		setCreateAndUpdateUserName(skuCategoryImageList);
		
		
		String categoryName = skuCategoryService.findByCode(categoryCode, null).getName();
		
		model.addAttribute("SkuCategoryImageList", skuCategoryImageList);
		model.addAttribute("page", page);
		model.addAttribute("imageTypeCode", imageTypeCode);
		model.addAttribute("categoryCode", categoryCode);
		model.addAttribute("categoryName", categoryName);
		model.addAttribute("adPath",Global.getImagePath());
       	return PRE_+"skuCategoryImageList";
		 }catch(Exception e){
			 logger.error("cms:SkuCategoryImageController.java商品分类列表查询失败", e);
		return "";
	}
	
	
	
}
	/***
	 * 设置  创建者和更新者的用户名（由id到name）
	 * @param skuCategoryImageList
	 */
	private void setCreateAndUpdateUserName(List<SkuCategoryImage> skuCategoryImageList) {
		for (SkuCategoryImage skuCategoryImage : skuCategoryImageList) {
			AuthUser createUser=this.authUserService.findById(skuCategoryImage.getCreateId());
			if(createUser!=null)
			skuCategoryImage.setCreateUser(createUser.getLoginName());
			AuthUser updateUser=this.authUserService.findById(skuCategoryImage.getUpdateId());
			if(updateUser!=null)
			skuCategoryImage.setUpdateUser(updateUser.getLoginName());
		}
		
	}
	/***
	 * 跳转到添加页面
	 * @param model
	 * @return
	 */
    @RequestMapping("goCategoryImageAdd/{categoryCode}")	
	public String goAdd(@PathVariable("categoryCode") String categoryCode,Model model,HttpSession session){
	    AuthUser user=CMSSessionUtils.getSessionUser(session);
    	model.addAttribute("user", user);
    	model.addAttribute("categoryCode", categoryCode);
    	List<SkuImageType> imageTypeList=this.imageTypeService.findAll();
    	model.addAttribute("imageTypeList", imageTypeList);
		return PRE_+"skuCategoryImageAdd";
	}
    
    @RequestMapping("addSkuCategoryImage")
    public String addSkuCategoryImage(SkuCategoryImage skuCategoryImage,RedirectAttributes redirectAttributes){
    	skuCategoryImage.setImgUrl(ImagePathUtil.getImageName(skuCategoryImage.getImgUrl()));
    	SkuImageType imageType=this.imageTypeService.findByCode(skuCategoryImage.getImageTypeCode());
    	skuCategoryImage.setImageTypeName(imageType.getName());
    	this.skuCategoryImageService.add(skuCategoryImage);
    	addMessage(redirectAttributes, "恭喜您，商品分类图片添加成功");
    	return RETURN_LIST+"?categoryCode="+skuCategoryImage.getCategoryCode();
    }
    
	@RequestMapping("deleteSkuCategoryImage/{id}/{code}/{time}")
	public String deleteSkuCategoryImage(@PathVariable("id") String id,@PathVariable("code") String code,RedirectAttributes redirectAttributes){
		try {
			this.skuCategoryImageService.deleteById(Long.valueOf(id));
			addMessage(redirectAttributes, "删除成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "删除失败");
		}
		return RETURN_LIST+"?categoryCode="+code;
	}
	
	
	/***
	 * 分类（code）下的图片类型列表
	 * @param catgoryCode
	 * @param model
	 * @return
	 */
//	@RequestMapping("/skuCategoryImageTypeList/{categoryCode}")
//	public String findCategoryImageTypeList(@PathVariable("categoryCode") String categoryCode,Model model){
//		Map<String, List<SkuCategoryImage>> categoryList=new HashMap<String, List<SkuCategoryImage>>();
//		List<SkuCategoryImage> skuCategoryImageList=this.skuCategoryImageService.findByCategoryCode(categoryCode);
//		if(skuCategoryImageList.size()>0){
//		for (SkuCategoryImage skuCategoryImage : skuCategoryImageList) {
//			String imageTypeCode=skuCategoryImage.getCategoryCode();
//			if(StringUtils.isNotBlank(imageTypeCode)){
//				List<SkuCategoryImage> categoryImageList=this.skuCategoryImageService.findByImageTypeAndCategoryCode(imageTypeCode ,categoryCode);
//				categoryList.put(imageTypeCode, categoryImageList);
//			}
//		}
//		model.addAttribute("categoryImageMap", categoryList);
//		}
//		return "";
//		
//	}
	
	/***
	 * 修改分类图片的顺序，修改完之后跳转回列表页
	 * @param sortOrder  原顺序
	 * @param id       分类图片id
	 * @param type     排序类型（上升 up，下降 down）
	 * @param code     分类code
	 * @param session
	 * @return
	 */
	@RequestMapping("updateSortOrder/{sortOrder}/{id}/{type}/{code}/{time}")
	public String updateSortOrder(@PathVariable("sortOrder") Integer sortOrder,
			@PathVariable("id") Long id,
			@PathVariable("type") String type,
			@PathVariable("code") String code,
			HttpSession session ){
		AuthUser user=CMSSessionUtils.getSessionUser(session);
		SkuCategoryImage categoryImage=this.skuCategoryImageService.findById(id);
		if("down".equals(type)){
			sortOrder=sortOrder+1;
		}if("up".equals(type))
			sortOrder=sortOrder-1<0?0:sortOrder-1;
		categoryImage.setSortOrder(sortOrder);
		categoryImage.setUpdateId(user.getId());
		categoryImage.setUpdateTime(new Date());
		this.skuCategoryImageService.update(categoryImage);
		return RETURN_LIST+"?categoryCode="+code;
	}
	
	
	
}
