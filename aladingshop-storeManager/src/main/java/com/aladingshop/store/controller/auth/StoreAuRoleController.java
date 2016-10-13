package com.aladingshop.store.controller.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.store.auth.model.StoreAuthPrivilege;
import com.aladingshop.store.auth.model.StoreAuthRole;
import com.aladingshop.store.auth.model.StoreAuthRolePrivilege;
import com.aladingshop.store.auth.model.StoreAuthUserRole;
import com.aladingshop.store.auth.service.StoreAuthPrivilegeService;
import com.aladingshop.store.auth.service.StoreAuthRolePrivilegeService;
import com.aladingshop.store.auth.service.StoreAuthRoleService;
import com.aladingshop.store.auth.service.StoreAuthUserRoleService;
import com.aladingshop.store.config.Global;
import com.aladingshop.store.controller.BaseController;
import com.aladingshop.store.util.CMSSessionUtils;

/***
 * 
 * @author xupeijun
 * 
 */
@Controller
@RequestMapping("/role")
public class StoreAuRoleController extends BaseController {

	@Autowired
	private StoreAuthRoleService<StoreAuthRole> auRoleService;
	@Autowired
	private StoreAuthPrivilegeService<StoreAuthPrivilege> privilegeService;
	@Autowired
	private StoreAuthUserRoleService<StoreAuthUserRole> authUserRoleService;
	@Autowired
	private StoreAuthRolePrivilegeService<StoreAuthRolePrivilege> authRolePrivilegeService;

	
	
	private  static final String PRE_="models/authorization/role/";
	private  static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/role/roleList";
	
	
	private static final Log logger = LogFactory.getLog(StoreAuRoleController.class);

	/***
	 * 角色列表
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("roleList")
	public String findAll(Map<String, Object> map,HttpSession session) {
		try {
			String storeCode=CMSSessionUtils.getSessionUser(session).getStoreCode();
			List<StoreAuthRole> listAllRole = auRoleService.findAll(storeCode);
			map.put("list", listAllRole);
		} catch (Exception e) {
			logger.error("角色列表查询失败", e);
		}
		return PRE_+"roleList";
	}

	/***
	 * 到添加的页面
	 * @param map
	 * @return
	 */
	@RequestMapping("goAdd")
	public String goAdd(ModelMap map){
		List<StoreAuthPrivilege> first = this.privilegeService.findByLevel(1);
		map.put("first", first);
		return PRE_+"roleAdd";
	}
	
	/***
	 * 角色添加，还要加上对应的权限
	 * 
	 * @param authRole
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("addRole")
	public String addRole(StoreAuthRole authRole, HttpServletRequest request,RedirectAttributes redirectAttributes) {
		try {
			String[] first = request.getParameterValues("access");
			if(first != null && first.length > 0){
				authRole.setStoreCode((CMSSessionUtils.getSessionUser(request.getSession()).getStoreCode()));
				this.auRoleService.add(authRole);
				addPrivilegesToRole(first,authRole);
			}
			addMessage(redirectAttributes, "角色添加成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "角色添加失败");
		}
		
		return REDIRECT_LIST;
	}
	/***
	 * 跳转到修改页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping("goEdit/{id}/{time}")
	public String goEdit(@PathVariable("id") String id,ModelMap map){
		StoreAuthRole role=this.auRoleService.findById(Long.valueOf(id));
		map.put("role", role);
		List<Long> privilegeIds = this.authRolePrivilegeService.findByRoleId(Long.valueOf(id));
		List<StoreAuthPrivilege> allFirst = this.privilegeService.findByLevel(1);
		allFirst=	setPrivilegeSelectedByRolePrivilegeIds(privilegeIds,allFirst);
		map.put("allFirst", allFirst);
		return PRE_+"roleEdit";
	}
	
	/**
	 * 如果用户拥有权限的第一级，则也拥有第二级的权限
	 * 
	 * @param authRole
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("updateRole")
	public String updateRole(StoreAuthRole authRole, HttpServletRequest request,RedirectAttributes redirectAttributes) {
		
		try {
			String[] first = request.getParameterValues("access");
			List<Long> allid = new ArrayList<Long>();
			if (first != null && first.length > 0) {
				allid = this.privilegeService.getallidsByFirsts(first);
				if (allid.size() > 0) {
					Long currentUserId=CMSSessionUtils.getSessionUser(request.getSession()).getId();
					authRole.setUpdateId(currentUserId);
					this.auRoleService.update(authRole);
					this.authRolePrivilegeService.updateRole(authRole.getId(),allid,currentUserId); 
					addMessage(redirectAttributes, "角色修改成功");
				}
			}
		} catch (Exception e) {
			logger.error("角色修改失败", e);
			addMessage(redirectAttributes, "角色修改失败");
		}
		return REDIRECT_LIST;
		
		
	}

	/***
	 * 角色删除（角色关联权限）
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/deleteRole/{id}/{time}",method=RequestMethod.GET)
	public String deleteRoleById(@PathVariable("id") String id,RedirectAttributes redirectAttributes){
		List<StoreAuthUserRole> authUserRoleList = this.authUserRoleService.findByRoleId(Long.valueOf(id));
		if (authUserRoleList.size() != 0) {
		   addMessage(redirectAttributes, "该角色下还有人员未解除，不可删除");
		} else {
			this.authRolePrivilegeService.deleteByRoleId(Long.valueOf(id));
			auRoleService.deleteById(Long.valueOf(id));
			addMessage(redirectAttributes, "删除角色成功");
		  }
		return REDIRECT_LIST ;
	}

	
	/***
	 * 给角色添加权限
	 * @param first
	 * @param authRole
	 */
	private void addPrivilegesToRole(String[] first, StoreAuthRole authRole) {
		List<Long> allid = new ArrayList<Long>();
		allid = this.privilegeService.getallidsByFirsts(first);
		if (allid != null && allid.size() > 0) {
			for (int i = 0; i < allid.size(); i++) {
				StoreAuthRolePrivilege entity = new StoreAuthRolePrivilege();
				entity.setPrivilegeId(allid.get(i));
				entity.setRoleId(authRole.getId());
				this.authRolePrivilegeService.add(entity);
			}
		} 
	}

	/***
	 * 通過角色下的权限id集合,与系统的一级权限作比较，在页面上给角色的权限，设置选中
	 * @param privilegeIds
	 * @param allFirst
	 * @return
	 */
	private List<StoreAuthPrivilege> setPrivilegeSelectedByRolePrivilegeIds(List<Long> privilegeIds, List<StoreAuthPrivilege> allFirst) {
	   if( privilegeIds==null || privilegeIds.size()<=0)
		   return allFirst;
				for (StoreAuthPrivilege allAuth : allFirst) {
					if (privilegeIds.contains(allAuth.getId())) {
						allAuth.setSelect(true);
					}
				}
       return allFirst;
	}
}
