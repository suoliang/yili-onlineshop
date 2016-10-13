package com.fushionbaby.cms.controller.authorization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fushionbaby.auth.model.AuthPrivilege;
import com.fushionbaby.auth.model.AuthRole;
import com.fushionbaby.auth.model.AuthRolePrivilege;
import com.fushionbaby.auth.model.AuthUserRole;
import com.fushionbaby.auth.service.AuthPrivilegeService;
import com.fushionbaby.auth.service.AuthRolePrivilegeService;
import com.fushionbaby.auth.service.AuthRoleService;
import com.fushionbaby.auth.service.AuthUserRoleService;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.AuthPrivilegeDto;
import com.fushionbaby.common.util.StringTools;
import com.google.gson.Gson;

/***
 * 
 * @author xupeijun
 * 
 */
@Controller
@RequestMapping("/role")
public class AuRoleController extends BaseController {

	@Autowired
	private AuthRoleService<AuthRole> auRoleService;
	@Autowired
	private AuthPrivilegeService<AuthPrivilege> privilegeService;
	@Autowired
	private AuthUserRoleService<AuthUserRole> authUserRoleService;
	@Autowired
	private AuthRolePrivilegeService<AuthRolePrivilege> authRolePrivilegeService;

	
	
	private  static final String PRE_="models/authorization/role/";
	private  static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/role/roleList";
	
	
	private static final Log logger = LogFactory.getLog(AuRoleController.class);

	/***
	 * 角色列表
	 * 
	 * @param map
	 * @return
	 */
	@RequestMapping("roleList")
	public String findAll(Map<String, Object> map) {
		try {
			List<AuthRole> listAllRole = auRoleService.findAll();
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
		List<AuthPrivilege> allPrivilegeList=this.privilegeService.findAll();
		List<AuthPrivilegeDto> privilegeList=new ArrayList<AuthPrivilegeDto>();
		for (AuthPrivilege privilege : allPrivilegeList) {
			AuthPrivilegeDto privilegeDto=new AuthPrivilegeDto();
			privilegeDto.setId(String.valueOf(privilege.getId()));
			privilegeDto.setpId(StringUtils.isBlank(String.valueOf(privilege.getParentId()))?"0":String.valueOf(privilege.getParentId()));
			privilegeDto.setCode(String.valueOf(privilege.getId()));
			privilegeDto.setName(privilege.getName());
			privilegeDto.setChecked(false);
			privilegeList.add(privilegeDto);
		}
		Gson gson=new Gson();
		map.put("privilegeJson", gson.toJson(privilegeList));
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
	public String addRole(AuthRole authRole,@RequestParam("privilegeIds") String ids,RedirectAttributes redirectAttributes) {
		try {
			this.auRoleService.add(authRole);
			List<String> codeList = StringTools.splitStringToList(ids, ",");
			for (String privilegeId : codeList) {
				if(privilegeId!=null){
					AuthRolePrivilege rolePrivilege=new AuthRolePrivilege();
					rolePrivilege.setPrivilegeId(Long.valueOf(privilegeId));
					rolePrivilege.setRoleId(authRole.getId());
					this.authRolePrivilegeService.add(rolePrivilege);
				}
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
		AuthRole role=this.auRoleService.findById(Long.valueOf(id));
		map.put("role", role);
		List<Long> privilegeIds = this.authRolePrivilegeService.findByRoleId(Long.valueOf(id));
		List<AuthPrivilege> allPrivilegeList=this.privilegeService.findAll();
		List<AuthPrivilegeDto> privilegeList=new ArrayList<AuthPrivilegeDto>();
		for (AuthPrivilege privilege : allPrivilegeList) {
			AuthPrivilegeDto privilegeDto=new AuthPrivilegeDto();
			privilegeDto.setId(String.valueOf(privilege.getId()));
			privilegeDto.setpId(StringUtils.isBlank(String.valueOf(privilege.getParentId()))?"0":String.valueOf(privilege.getParentId()));
			privilegeDto.setCode(String.valueOf(privilege.getId()));
			privilegeDto.setName(privilege.getName());
			if(privilegeIds.contains(privilege.getId()))
			  privilegeDto.setChecked(true);
			else
			  privilegeDto.setChecked(false);
			
			privilegeList.add(privilegeDto);
		}
		Gson gson=new Gson();
		map.put("privilegeJson", gson.toJson(privilegeList));
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
	public String updateRole(AuthRole authRole,@RequestParam("privilegeIds") String ids,RedirectAttributes redirectAttributes) {
	try {
		/** 更新角色*/
		this.auRoleService.update(authRole);
		/** 删除角色权限关联*/
		this.authRolePrivilegeService.deleteByRoleId(authRole.getId());
		/** 添加角色权限关联*/
		List<String> codeList = StringTools.splitStringToList(ids, ",");
		for (String privilegeId : codeList) {
			if(privilegeId!=null){
				AuthRolePrivilege rolePrivilege=new AuthRolePrivilege();
				rolePrivilege.setPrivilegeId(Long.valueOf(privilegeId));
				rolePrivilege.setRoleId(authRole.getId());
				this.authRolePrivilegeService.add(rolePrivilege);
			}
		}
		addMessage(redirectAttributes, "角色修改成功");
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
		List<AuthUserRole> authUserRoleList = this.authUserRoleService.findByRoleId(Long.valueOf(id));
		if (authUserRoleList.size() != 0) {
		   addMessage(redirectAttributes, "该角色下还有人员未解除，不可删除");
		} else {
			this.authRolePrivilegeService.deleteByRoleId(Long.valueOf(id));
			auRoleService.deleteById(Long.valueOf(id));
			addMessage(redirectAttributes, "删除角色成功");
		  }
		return REDIRECT_LIST ;
	}

}
