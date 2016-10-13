package com.fushionbaby.cms.controller.authorization;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.store.auth.model.StoreAuthPrivilege;
import com.aladingshop.store.auth.service.StoreAuthPrivilegeService;
import com.aladingshop.store.model.Store;
import com.aladingshop.store.service.StoreService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.common.util.BasePagination;


/***
 * 
 * @author chenyingtao
 *
 *
 */
@Controller
@RequestMapping("/storePrivilege")
public class StoreAuthPrivilegeController extends BaseController {
	
	@Autowired
	private StoreAuthPrivilegeService<StoreAuthPrivilege> auPrivilegeService;
	@Autowired
	private StoreService<Store> StoreService;
	
	private static final Log logger = LogFactory.getLog(StoreAuthPrivilegeController.class);

	private static final String PRE_="models/authorization/privilege/";
	
	private static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/storePrivilege/privilegeList";
	
	@SuppressWarnings("unchecked")
	@RequestMapping("privilegeList")
	public String storeAuthPrivilegeList(@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "storeCode", defaultValue = "") String storeCode,
			BasePagination page, ModelMap model){
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("name", name.trim());
			params.put("storeCode", storeCode);
			page.setParams(params);
			
			page = this.auPrivilegeService.getListPage(page);
			List<StoreAuthPrivilege> listFindAll = (List<StoreAuthPrivilege>) page.getResult();
			model.addAttribute("list", listFindAll);
			model.addAttribute("storeCode", storeCode);
			model.addAttribute("storeList", StoreService.findAll());
			model.addAttribute("page", page);
			model.addAttribute("name", name);
			return PRE_+"storePrivilegeList";
		} catch (Exception e) {
			logger.error("查询权限列表失败", e);
			return "";
		}
	}
	
	/***
	 * 跳转到门店权限添加页面
	 * @param map
	 * @return
	 */
	@RequestMapping("goAdd/{time}")
	public String goAdd(ModelMap model) {
		
		List<StoreAuthPrivilege> firstList=this.auPrivilegeService.findAllFirst();
		model.addAttribute("firstList", firstList);
		
		return PRE_+"storePrivilegeAdd";
	}
	
	/***
	 * 门店权限添加
	 * @param auPrivilege
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("addPrivilege")
	public String addPrivilege(StoreAuthPrivilege auPrivilege,RedirectAttributes redirectAttributes,HttpSession session) {
		try {
			auPrivilege.setCreateId(CMSSessionUtils.getSessionUser(session).getId());
			
			this.auPrivilegeService.add(auPrivilege);
			addMessage(redirectAttributes, "权限添加成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "权限添加失败");
			logger.error("权限添加失败",e);
		}
		return 	REDIRECT_LIST;
	}
	
	
	/***
	 * 到修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping("goEdit/{id}/{time}")
	public String add(@PathVariable("id") String id,ModelMap map) {
		StoreAuthPrivilege privilege=this.auPrivilegeService.findById(Long.valueOf(id));
			map.put("privilege", privilege);
			List<StoreAuthPrivilege> firstList=this.auPrivilegeService.findAllFirst();
			map.put("firstList", firstList);
		    return PRE_+"storePrivilegeEdit";
	}
	/***
	 * 权限的修改
	 * 
	 * @param auPrivilege
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("updatePrivilege")
	public String update(StoreAuthPrivilege auPrivilege,RedirectAttributes redirectAttributes,HttpSession session) {
		try {
			StoreAuthPrivilege storePrivilege = this.auPrivilegeService.findById(auPrivilege.getId());
			storePrivilege.setName(auPrivilege.getName());
			storePrivilege.setParentId(auPrivilege.getParentId());
			storePrivilege.setSortNo(auPrivilege.getSortNo());
			AuthUser user= CMSSessionUtils.getSessionUser(session);
			storePrivilege.setUpdateId(user.getId());
			storePrivilege.setUpdateTime(new Date());
			storePrivilege.setUrl(auPrivilege.getUrl());
			auPrivilegeService.update(storePrivilege);
			addMessage(redirectAttributes, "权限修改成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "权限修改失败");
		}
		return 	REDIRECT_LIST;
	}

	/***
	 * 删除权限
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
    @RequestMapping("deletePrivilege/{id}/{time}")
	public String deletePrivilege(@PathVariable("id") String id,RedirectAttributes redirectAttributes){
			try {
				StoreAuthPrivilege privilege = this.auPrivilegeService.findById(Long.valueOf(id));
				if(privilege.getLevel() != 1){
					this.auPrivilegeService.deleteById(Long.valueOf(id));
					addMessage(redirectAttributes, "二级权限删除成功");
				}else{
					deleteFirstPrivilegeById(id, redirectAttributes);
				}
			} catch (Exception e) {
				addMessage(redirectAttributes, "权限删除失败");
			}
		return REDIRECT_LIST;
	}

	/***
	 * 删除一级的权限，要判断该权限是否还有二级权限
	 * @param id
	 * @param redirectAttributes
	 */
	private void deleteFirstPrivilegeById(String id,RedirectAttributes redirectAttributes) {
		List<StoreAuthPrivilege> secondList=	this.auPrivilegeService.findByParentId(Long.valueOf(id));
		if(secondList.size()==0){
			this.auPrivilegeService.deleteById(Long.valueOf(id));
			addMessage(redirectAttributes, "一级权限删除成功");
		}	else{
			addMessage(redirectAttributes, "该权限下还有子权限，删除失败");
		}
	}
	
	
	
	
}
