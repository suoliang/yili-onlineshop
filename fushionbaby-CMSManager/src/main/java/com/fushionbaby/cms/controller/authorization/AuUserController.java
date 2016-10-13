package com.fushionbaby.cms.controller.authorization;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fushionbaby.auth.model.AuthPrivilege;
import com.fushionbaby.auth.model.AuthRole;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.auth.model.AuthUserRole;
import com.fushionbaby.auth.service.AuthPrivilegeService;
import com.fushionbaby.auth.service.AuthRoleService;
import com.fushionbaby.auth.service.AuthUserRoleService;
import com.fushionbaby.auth.service.AuthUserService;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.AuthUserDto;
import com.fushionbaby.cms.dto.CmsUserDto;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.log.model.LogCmsLogin;
import com.fushionbaby.log.service.LogCmsLoginService;

/***
 * 
 * 后台管理用户
 * 
 * @author xupeijun
 * 
 */
@Controller
@RequestMapping("/user")
public class AuUserController extends BaseController{
	/** 用户 */
	@Autowired
	private AuthUserService<AuthUser> authUserService;
	/** 角色 */
	@Autowired
	private AuthRoleService<AuthRole> authRoleService;
	/** 用户角色关联 */
	@Autowired
	private AuthUserRoleService<AuthUserRole> authUserRoleService;
	/** 登录日志 */
	@Autowired
	private LogCmsLoginService<LogCmsLogin> cmsLoginService;
	
	/** 权限的服务*/
	@Autowired
	private AuthPrivilegeService<AuthPrivilege> authPrivilegeService;
	
	/** 日志 */
	private static final Log logger = LogFactory.getLog(AuUserController.class);
	/** 返回到登陆页面*/
	//private static final String RESULT="models/authorization/login";
	/**页面user的目录*/
	private static final String PRE_="models/authorization/user/";
	/** model 中存放角色集合的string*/
	private static final String AUTHROLELIST="authRoleList";
	/** 复选框选中的值*/
	private static final String ALLCHECKBOX="access";
	
	/**列表页面的url*/
	private  static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/user/authUserList";

	/***
	 * 用户登录
	 * 
	 * @param username
	 * @param password
	 * @param session
	 * @param map
	 * @return
	 */
/*	@RequestMapping("login")
	public String login(
			@RequestParam(value = "userName", defaultValue = "") String username,
			@RequestParam(value = "password", defaultValue = "") String password,
			HttpSession session, Map<String, Object> map) {
		List<Map<AuthPrivilege, List<AuthPrivilege>>> menuPrivilegeList = new ArrayList<Map<AuthPrivilege, List<AuthPrivilege>>>();
		try {
			if(StringUtils.isBlank(username)||StringUtils.isBlank(password)) {
				return RESULT;
			}
			AuthUser auUser = this.authUserService.findAuthUserByUserName(username);
			if (auUser != null && password.equals(auUser.getPassword())) {
				session.setAttribute(SessionKeyConstant.CMS_AUTH_USER, auUser);
				if (this.authUserService.isSystemUser(auUser.getId()) != null) {
					menuPrivilegeList = this.authUserService.loginAsSystem();
				} else {
					menuPrivilegeList = this.authUserService.loginAsNormalUser(auUser);
				}
				map.put("menuPrivilegeList", menuPrivilegeList);
				this.cmsLoginService.addLoginMessage(auUser.getLoginName(),CommonConstant.YES);
				return "include/leftMenu";
			} else {
				String message = "登录密码错误或则用户名不存在，请确认后重试";
				map.put("errorMessage", message);
				this.cmsLoginService.addLoginMessage(username,CommonConstant.NO);
				return RESULT;
			}
		} catch (Exception e) {
			logger.error("用户登录失败", e);
			return "";
		}

	}*/

	/***
	 * 用户列表查询
	 * @param userDto
	 * @param page
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("authUserList")
	public String findList(CmsUserDto userDto,BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			List<AuthRole> authRoleList = authRoleService.findAll();
			model.addAttribute("authRoleList",authRoleList);
			
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("loginName", userDto.getLoginName());
			params.put("phone", userDto.getPhone());
			params.put("email", userDto.getEmail());
			params.put("status", userDto.getStatus());
			String roleIdtmp=userDto.getRoleId();
			if(StringUtils.isNotBlank(roleIdtmp)){
				List<AuthUserRole> authUserRoleListtmp=authUserRoleService.findByRoleId(Long.parseLong(roleIdtmp));
				String includeIds="";
				for(AuthUserRole authUserRoletmp: authUserRoleListtmp){
					includeIds+=","+authUserRoletmp.getUserId();
				}
				includeIds=includeIds.substring(1);
				params.put("includeIds", includeIds);
			}
			
			page.setParams(params);
			page = authUserService.getListPage(page);
			List<AuthUser> authUserList = (List<AuthUser>) page.getResult();
			
			List<AuthUserDto> authUserDtoList = new ArrayList<AuthUserDto>();
			for(AuthUser authUsertmp: authUserList){
				AuthUserDto authUserDto = new AuthUserDto();
				authUserDto.setCreateTime(authUsertmp.getCreateTime());
				authUserDto.setEmail(authUsertmp.getEmail());
				authUserDto.setId(authUsertmp.getId());
				authUserDto.setLoginName(authUsertmp.getLoginName());
				authUserDto.setMemo(authUsertmp.getMemo());
				authUserDto.setPassword(authUsertmp.getPassword());
				authUserDto.setPhone(authUsertmp.getPhone());
				List<AuthUserRole> authUserRoleList=authUserRoleService.findByUserId(authUsertmp.getId());
				String roleName="";
				for(int i=0;i<authUserRoleList.size();i++){
					AuthUserRole authUserRoletmp=authUserRoleList.get(i);
					Long roleId=authUserRoletmp.getRoleId();
					roleName=roleName+authRoleService.findById(roleId).getName();
					if(i<authUserRoleList.size()-1){
						roleName+="  ,  ";
					}
				}
				authUserDto.setRoleName(roleName);
				authUserDto.setStatus(authUsertmp.getStatus());
				authUserDto.setUpdateTime(authUsertmp.getUpdateTime());
				authUserDto.setUserType(authUsertmp.getUserType());
				authUserDto.setVersion(authUsertmp.getVersion());
				authUserDtoList.add(authUserDto);
			}
			
			model.addAttribute("authUserDtoList", authUserDtoList);
			model.addAttribute("page", page);
			model.addAttribute("userDto", userDto);
			
		} catch (Exception e) {
			logger.error("后台用户列表加载失败", e);
		}
		return PRE_+"userList";
	}

	/***
	 * 跳转到添加的页面
	 * @param model
	 * @return
	 */
	@RequestMapping("goAdd")
	public String toAdd(Model model) {
			List<AuthRole> roleList = this.authRoleService.findAll();
			model.addAttribute(AUTHROLELIST, roleList);
		return PRE_+"userAdd";
	}
	/***
	 * 添加用户（角色关联添加）
	 * @param user
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("addUser")
	public String addUser(AuthUser user,HttpServletRequest request,RedirectAttributes redirectAttributes){
	    try {
	    	String[] checkboxStr = request.getParameterValues(ALLCHECKBOX);
	    	user.setLevel(1);
			authUserService.add(user);
			saveUserRolesBySelectedCheckBox(checkboxStr,user);
			addMessage(redirectAttributes, "添加用户成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "添加用户失败");
		}
		return REDIRECT_LIST;
	}
	/***
	 * 到修改的页面
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping("goEdit/{id}/{time}")
	public String goEdit(@PathVariable("id") String id,ModelMap map){
		List<AuthRole> roleList = this.authRoleService.findAll();
		AuthUser user = this.authUserService.findById(Long.valueOf(id));
		map.put("authUser", user);
		List<AuthUserRole> userRoleList = this.authUserRoleService.findByUserId(Long.valueOf(id));
		List<Long>	allRoleIdList=	getRoleIdListByUserRoleList(userRoleList);
		if (userRoleList != null && userRoleList.size() > 0) {
			roleList=setRoleSelectedByRoleList(roleList,allRoleIdList);
		}
		map.put(AUTHROLELIST, roleList);
		return PRE_+"userEdit";
	}
	/***
	 * 用户修改（要删除原来用户角色关联，再添加新的用户角色关联）
	 * @param user
	 * @param redirectAttributes
	 * @param request
	 * @return
	 */
	@RequestMapping("updateUser")
	public String updateUser(AuthUser user,RedirectAttributes redirectAttributes,HttpServletRequest request){
		
		try {
			this.authUserRoleService.deleteByUserId(user.getId());
			authUserService.update(user);
			String[] checkboxStr = request.getParameterValues(ALLCHECKBOX);
			saveUserRolesBySelectedCheckBox(checkboxStr,user);
			addMessage(redirectAttributes, "用户信息修改成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "用户信息修改失败");
		}
		return REDIRECT_LIST;
	}
	
	
	/***
	 * 用户删除（删除用户角色关联）
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("deleteUser/{id}/{time}")
	public String deleteUser(@PathVariable("id") String id,RedirectAttributes redirectAttributes){
		try {
			this.authUserRoleService.deleteByUserId(Long.valueOf(id));
			authUserService.deleteById(Long.valueOf(id));
			addMessage(redirectAttributes, "删除用户成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "删除用户失败");
		}
		return REDIRECT_LIST; 
	}
	
	
	@RequestMapping("updateUserStatus/{id}/{time}")
	public String updateUserStatus(@PathVariable("id") String id,RedirectAttributes redirectAttributes){
		try {
			AuthUser user=this.authUserService.findById(Long.valueOf(id));
			Integer status=user.getStatus();
			status=status==1?2:1;
			this.authUserService.updateStatus(Long.valueOf(id), status);
			addMessage(redirectAttributes, "用户"+user.getLoginName()+"状态修改成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "用户状态修改失败");
		}
		return REDIRECT_LIST;
	}
	
	/***
	 * 添加用户角色中间表数据
	 * @param checkboxStr
	 * @param authUser
	 */
	private void saveUserRolesBySelectedCheckBox(String[] checkboxStr,
			AuthUser authUser) {
		if (checkboxStr.length > 0) {
			for (String roleId : checkboxStr) {
				AuthUserRole authUserRole = new AuthUserRole();
				authUserRole.setRoleId(Long.valueOf(roleId));
				authUserRole.setUserId(authUser.getId());
				this.authUserRoleService.add(authUserRole);
			}
		}
	}
	
	/***
	 * 设置用户的角色选中
	 * @param roleList
	 * @param allRoleIdList
	 * @return
	 */
	private List<AuthRole> setRoleSelectedByRoleList(List<AuthRole> roleList,
			List<Long> allRoleIdList) {
		if (roleList != null && roleList.size() > 0) {
			for (AuthRole authRole : roleList) {
				if (allRoleIdList.contains(authRole.getId())) {
					authRole.setSelect(true);
				}
			}
		}
		return roleList;
	}
	
	/***
	 * 用户角色关联的集合获得角色的id集合
	 * @param userRoleList
	 * @return
	 */
	private List<Long> getRoleIdListByUserRoleList(List<AuthUserRole> userRoleList) {
		List<Long> allRoleIdList = new ArrayList<Long>();
		if (userRoleList != null && userRoleList.size() > 0) {
			for (int i = 0; i < userRoleList.size(); i++) {
				allRoleIdList.add(userRoleList.get(i).getRoleId());
			}
		}
		return allRoleIdList;
	}
	
	/***
	 * 用户姓名校验
	 * @param name
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("userUnique")
	public boolean userUnique(@RequestParam("name") String name,@RequestParam(value="id",defaultValue="") String id) {
		if(StringUtils.isBlank(id)) {
			id="0";
		}
		return this.authUserService.isUniqueByUserName(name,Long.valueOf(id));
	}
	/***
	 * 左边菜单栏的加载页面
	 * @param session
	 * @param map
	 * @return
	 */
	@RequestMapping("/getAllMenu")
	public String getAllMenu(HttpSession session,ModelMap map){
		List<Map<AuthPrivilege, List<AuthPrivilege>>> menuPrivilegeList = new ArrayList<Map<AuthPrivilege, List<AuthPrivilege>>>();
		AuthUser user=CMSSessionUtils.getSessionUser(session);
		if(ObjectUtils.equals(user, null)){
			return "models/authorization/login";
		}
			 if (this.authUserService.isSystemUser(user.getId()) != null) {
					menuPrivilegeList = this.authUserService.loginAsSystem();
				} else {
					menuPrivilegeList = this.authUserService.loginAsNormalUser(user.getId());
				}
				map.put("userName",user.getLoginName());
				map.put("menuPrivilegeList", menuPrivilegeList);
				return "include/leftMenu";
		 
	}
	
	
	
}
