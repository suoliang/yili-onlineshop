package com.aladingshop.store.controller.auth;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.store.auth.model.StoreAuthUser;
import com.aladingshop.store.auth.service.StoreAuthUserService;
import com.aladingshop.store.config.Global;
import com.aladingshop.store.controller.BaseController;
import com.aladingshop.store.util.CMSSessionUtils;

/***
 * 
 * 用户密码修改
 * 
 * @author xinlangtao
 * 
 */
@Controller
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController  {
	/** 用户 */
	@Autowired
	private StoreAuthUserService<StoreAuthUser> authUserService;
	
	/**页面user的目录*/
	private static final String PRE_="models/authorization/user/";
	
	/**列表页面的url*/
	private  static final String REDIRECT="redirect:"+Global.getAdminPath()+"/userInfo/userPwdModify";
	
	
	/***
	 * 跳转到密码修改页面
	 * @param userDto
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("userPwdModify")
	public String userModifyPwd(Model model, HttpSession session) {
		StoreAuthUser auUser = CMSSessionUtils.getSessionUser(session);
		model.addAttribute("loginName", auUser.getName());
		return PRE_+"userPwdModify";
	}
	
	/***
	 * 用户密码修改
	 * @param userDto
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("/authUserModify")
	public String authUserModifyPwd( String oldpassword, String newpassword, String newpasswords, RedirectAttributes redirectAttributes, HttpSession session) {
		StoreAuthUser auUser = CMSSessionUtils.getSessionUser(session);
		try{
			if(auUser.getPassword().endsWith(oldpassword)){
				if(newpassword.endsWith(newpasswords)){
					auUser.setPassword(newpassword);
					authUserService.update(auUser);
					addMessage(redirectAttributes, "密码修改成功");
				}else{
					addMessage(redirectAttributes, "两次密码不相同");
				}
			}else{
				addMessage(redirectAttributes, "原始密码错误");
			}
		}catch(Exception e){
			addMessage(redirectAttributes, "密码修改失败");
		}
		return REDIRECT;
	}
}
