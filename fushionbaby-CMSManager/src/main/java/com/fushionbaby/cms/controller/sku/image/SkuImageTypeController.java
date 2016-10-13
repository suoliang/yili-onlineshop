package com.fushionbaby.cms.controller.sku.image;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.sku.cms.model.SkuImageType;
import com.aladingshop.sku.cms.service.SkuImageTypeService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.auth.service.AuthUserService;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.util.BasePagination;
/***
 * 
 * @description 类描述... 图片类型
 * @author 徐培峻
 * @date 2015年8月12日上午10:54:21
 */
@RequestMapping("/skuImageType")
@Controller
public class SkuImageTypeController extends BaseController{
	
	@Autowired
	private AuthUserService<AuthUser> authUserService;
	
	@Autowired
	private SkuImageTypeService<SkuImageType> imageTypeService;
	
	private static final Log imageTypeLog=LogFactory.getLog(SkuImageTypeController.class);
	
	private static final String RETURN_LIST="redirect:"+Global.getAdminPath()+"/skuImageType/skuImageTypeList";
	/**登陆页面*/
	private static final String PRE_="models/sku/image/";
	
	@RequestMapping("skuImageTypeList")
	public String CategoryImageList(@RequestParam(value="name",defaultValue="") String name,
			@RequestParam(value="code",defaultValue="") String code,
			BasePagination page,Model model){
	try {
		if (page == null) {
			page = new BasePagination();
		}
		// 分页参数封装
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name.trim());
		params.put("code", code.trim());
		page.setParams(params);
		// 分页对象
		page = this.imageTypeService.getListPage(page);
		// 分页结果
		@SuppressWarnings("unchecked")
		List<SkuImageType> skuImageTypeList = (List<SkuImageType>) page.getResult();
		model.addAttribute("skuImageTypeList", skuImageTypeList);
		model.addAttribute("page", page);
		model.addAttribute("code", code);
		model.addAttribute("name", name);
       	return PRE_+"skuImageTypeList";
		 }catch(Exception e){
			 imageTypeLog.error("cms:SkuImageTypeController.java商品图片类型列表查询失败", e);
		return "";
	}
}
	/***
	 * 跳转添加页面
	 * @return
	 */
    @RequestMapping("goImageTypeAdd")	
	public String goAdd(){
		return PRE_+"skuImageTypeAdd";
	}
    
    @RequestMapping("addSkuImageType")
    public String addskuImageType(SkuImageType skuImageType,RedirectAttributes redirectAttributes){
    	this.imageTypeService.add(skuImageType);
    	addMessage(redirectAttributes, "恭喜您，图片类型添加成功");
    	return RETURN_LIST;
    }
    
    
    
	@RequestMapping("deleteSkuImageType/{id}/{time}")
	public String deleteskuImageType(@PathVariable("id") String id,RedirectAttributes redirectAttributes){
		try {
			this.imageTypeService.deleteById(Long.valueOf(id));
			addMessage(redirectAttributes, "删除成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "删除失败");
		}
		return RETURN_LIST;
	}
	
	@RequestMapping("/updateStatus/{id}/{status}/{time}")
	public String updateStatus(@PathVariable("id") Long id,@PathVariable("status") String status){
		status=CommonConstant.NO.equals(status)?CommonConstant.YES:CommonConstant.NO;
		this.imageTypeService.updateStatus(id,status);
		return RETURN_LIST;
	}
	
	
	
	
}
