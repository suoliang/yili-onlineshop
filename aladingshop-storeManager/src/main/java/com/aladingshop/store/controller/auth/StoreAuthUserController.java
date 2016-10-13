package com.aladingshop.store.controller.auth;
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

import com.aladingshop.store.auth.model.StoreAuthPrivilege;
import com.aladingshop.store.auth.model.StoreAuthRole;
import com.aladingshop.store.auth.model.StoreAuthUser;
import com.aladingshop.store.auth.model.StoreAuthUserRole;
import com.aladingshop.store.auth.service.StoreAuthPrivilegeService;
import com.aladingshop.store.auth.service.StoreAuthRoleService;
import com.aladingshop.store.auth.service.StoreAuthUserRoleService;
import com.aladingshop.store.auth.service.StoreAuthUserService;
import com.aladingshop.store.config.Global;
import com.aladingshop.store.controller.BaseController;
import com.aladingshop.store.dto.CmsUserDto;
import com.aladingshop.store.dto.StoreAuthUserDto;
import com.aladingshop.store.model.Store;
import com.aladingshop.store.service.StoreService;
import com.aladingshop.store.util.CMSSessionUtils;
import com.fushionbaby.common.util.BasePagination;

/***
 * 
 * 后台管理用户
 *  
 * @author xupeijun
 * 
 */
@Controller
@RequestMapping("/user")
public class StoreAuthUserController extends BaseController{
	/** 用户 */
	@Autowired
	private StoreAuthUserService<StoreAuthUser> authUserService;
	/** 角色 */
	@Autowired
	private StoreAuthRoleService<StoreAuthRole> authRoleService;
	/** 用户角色关联 */
	@Autowired
	private StoreAuthUserRoleService<StoreAuthUserRole> authUserRoleService;

	
	@Autowired
	private StoreService<Store> storeService;
	
	/** 权限的服务*/
	@Autowired
	private StoreAuthPrivilegeService<StoreAuthPrivilege> authPrivilegeService;
	
	/** 日志 */
	private static final Log logger = LogFactory.getLog(StoreAuthUserController.class);
	/** 返回到登陆页面*/
	//private static final String RESULT="authorization/login";
	/**页面user的目录*/
	private static final String PRE_="models/authorization/user/";
	/** model 中存放角色集合的string*/ 
	private static final String AUTHROLELIST="authRoleList";
	/** 复选框选中的值*/
	private static final String ALLCHECKBOX="access";
	
	/**列表页面的url*/
	private  static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/user/authUserList";

	/***
	 * 用户列表查询
	 * @param userDto
	 * @param page
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("authUserList")
	public String findList(CmsUserDto userDto,BasePagination page, Model model,HttpSession session) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			String storeCode=CMSSessionUtils.getSessionUser(session).getStoreCode();
			List<StoreAuthRole> authRoleList = authRoleService.findAll(storeCode);
			model.addAttribute("authRoleList",authRoleList);
			
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("storeCode", storeCode);
			params.put("loginName", userDto.getLoginName());
			params.put("phone", userDto.getPhone());
			params.put("status", userDto.getStatus());
			String roleIdtmp=userDto.getRoleId();
			if(StringUtils.isNotBlank(roleIdtmp)){
				List<StoreAuthUserRole> authUserRoleListtmp=authUserRoleService.findByRoleId(Long.parseLong(roleIdtmp));
				String includeIds="";
				for(StoreAuthUserRole authUserRoletmp: authUserRoleListtmp){
					includeIds+=","+authUserRoletmp.getUserId();
				}
				includeIds=includeIds.substring(1);
				params.put("includeIds", includeIds);
			}
			
			page.setParams(params);
			page = authUserService.getListPage(page);
			List<StoreAuthUser> authUserList = (List<StoreAuthUser>) page.getResult();
			
			List<StoreAuthUserDto> authUserDtoList = new ArrayList<StoreAuthUserDto>();
			for(StoreAuthUser authUsertmp: authUserList){
				StoreAuthUserDto authUserDto = new StoreAuthUserDto();
				authUserDto.setCreateTime(authUsertmp.getCreateTime());
				authUserDto.setEmail(authUsertmp.getEmail());
				authUserDto.setId(authUsertmp.getId());
				authUserDto.setLoginName(authUsertmp.getName());
				authUserDto.setMemo(authUsertmp.getMemo());
				authUserDto.setPassword(authUsertmp.getPassword());
				authUserDto.setPhone(authUsertmp.getPhone());
				authUserDto.setIsDisabled(authUsertmp.getIsDisabled());
				List<StoreAuthUserRole> authUserRoleList=authUserRoleService.findByUserId(authUsertmp.getId());
				String roleName="";
				for(int i=0;i<authUserRoleList.size();i++){
					StoreAuthUserRole authUserRoletmp=authUserRoleList.get(i);
					Long roleId=authUserRoletmp.getRoleId();
					StoreAuthRole role=authRoleService.findById(roleId);
					if(role!=null)
					roleName=roleName+role.getName();
					if(i<authUserRoleList.size()-1){
						roleName+="  ,  ";
					}
				}
				authUserDto.setRoleName(roleName);
				authUserDto.setUpdateTime(authUsertmp.getUpdateTime());
				authUserDto.setUserType(authUsertmp.getUserType());
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
	public String toAdd(Model model,HttpSession session) {
			String storeCode=CMSSessionUtils.getSessionUser(session).getStoreCode();
			List<StoreAuthRole> roleList = this.authRoleService.findAll(storeCode);
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
	public String addUser(StoreAuthUser user,HttpServletRequest request,RedirectAttributes redirectAttributes){
	    try {
	    	String[] checkboxStr = request.getParameterValues(ALLCHECKBOX);
	    	String storeCode=CMSSessionUtils.getSessionUser(request.getSession()).getStoreCode();
	    	String storeNumber=storeService.findByCode(storeCode).getNumber();
	    	user.setStoreCode(storeCode);
	    	user.setStoreNumber(storeNumber);
	    	user.setIsDisabled("n");
	    	user.setCreateId(CMSSessionUtils.getSessionUser(request.getSession()).getId());
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
	public String goEdit(@PathVariable("id") String id,ModelMap map,HttpSession session){
		String storeCode=CMSSessionUtils.getSessionUser(session).getStoreCode();
		List<StoreAuthRole> roleList = this.authRoleService.findAll(storeCode);
		StoreAuthUser user = this.authUserService.findById(Long.valueOf(id));
		map.put("authUser", user);
		List<StoreAuthUserRole> userRoleList = this.authUserRoleService.findByUserId(Long.valueOf(id));
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
	public String updateUser(StoreAuthUser user,RedirectAttributes redirectAttributes,HttpServletRequest request){
		
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
			StoreAuthUser user=this.authUserService.findById(Long.valueOf(id));
			String isDisabled=user.getIsDisabled();
			isDisabled="y".equals(isDisabled)?"n":"y";
			this.authUserService.updateIsDisabled(Long.valueOf(id), isDisabled);
			addMessage(redirectAttributes, "用户"+user.getName()+"状态修改成功");
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
			StoreAuthUser authUser) {
		if (checkboxStr.length > 0) {
			for (String roleId : checkboxStr) {
				StoreAuthUserRole authUserRole = new StoreAuthUserRole();
				authUserRole.setUpdateId(authUser.getCreateId());
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
	private List<StoreAuthRole> setRoleSelectedByRoleList(List<StoreAuthRole> roleList,
			List<Long> allRoleIdList) {
		if (roleList != null && roleList.size() > 0) {
			for (StoreAuthRole authRole : roleList) {
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
	private List<Long> getRoleIdListByUserRoleList(List<StoreAuthUserRole> userRoleList) {
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
	public boolean userUnique(@RequestParam("name") String name,@RequestParam(value="id",defaultValue="") String id,HttpSession session) {
		if(StringUtils.isBlank(id)) {
			id="0";
		}
		String storeCode=CMSSessionUtils.getSessionUser(session).getStoreCode();
		return this.authUserService.isUniqueByUserNameAndStoreCode(name,storeCode,Long.valueOf(id));
	}
	/***
	 * 左边菜单栏的加载页面
	 * @param session
	 * @param map
	 * @return
	 */
	@RequestMapping("/getAllMenu")
	public String getAllMenu(HttpSession session,ModelMap map){
		List<Map<StoreAuthPrivilege, List<StoreAuthPrivilege>>> menuPrivilegeList = new ArrayList<Map<StoreAuthPrivilege, List<StoreAuthPrivilege>>>();
		StoreAuthUser user=CMSSessionUtils.getSessionUser(session);
		if(ObjectUtils.equals(user, null)){
			return "models/authorization/login";
		}
		 if (this.authUserService.isSystemUser(user.getId()) != null) {
				menuPrivilegeList = this.authUserService.loginAsSystem();
			} else {
				menuPrivilegeList = this.authUserService.loginAsNormalUser(user.getId());
			}
			map.put("userName",user.getName());
			map.put("menuPrivilegeList", menuPrivilegeList);
			return "include/leftMenu";
		 
	}
	
	
	
}
