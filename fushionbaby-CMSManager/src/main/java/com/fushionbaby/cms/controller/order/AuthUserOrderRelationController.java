package com.fushionbaby.cms.controller.order;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fushionbaby.auth.constant.CMSUserOrderConstant;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.auth.model.AuthUserOrderRelation;
import com.fushionbaby.auth.service.AuthUserOrderRelationService;
import com.fushionbaby.auth.service.AuthUserService;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.order.model.OrderBase;
import com.fushionbaby.order.service.OrderBaseService;
/***
 * 后台会员订单关联
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月28日下午3:59:05
 */
@Controller
@RequestMapping("/userOrder")
public class AuthUserOrderRelationController extends BaseController {

	private static final Log LOGGER = LogFactory.getLog(AuthUserOrderRelationController.class);
	/**订单基础*/
	@Autowired
	private OrderBaseService<OrderBase> orderBaseService;
	
    /** 用户和订单关联*/
    @Autowired
    private AuthUserOrderRelationService<AuthUserOrderRelation> authUserOrderRelationService;
    /** 后台登陆用户*/
    @Autowired
    private AuthUserService<AuthUser> authUserService;
    
    
    
    /***
     * 分配要处理的订单
     * @param memberId
     * @param orderCode
     * @param model
     * @return
     */
    @RequestMapping("distribution/{memberId}/{orderCode}")
	public String distributionOrder(@PathVariable("memberId") Long memberId,@PathVariable("orderCode")String orderCode,ModelMap model){
    	OrderBase orderBase = orderBaseService.findByMemberIdAndOrderCode(memberId, orderCode);
		model.addAttribute("orderBase", orderBase);
		List<AuthUser> userList=authUserService.findDistributionUser(CMSUserOrderConstant.LEVEL_TWO);
		model.addAttribute("userList", userList);
		return "models/order/distribution/orderDistributionCheck";
	}
	
    @RequestMapping("submitDistribution")
    @ResponseBody
    public Object submitDistribution(@RequestParam("memberId")Long memberId,@RequestParam("orderCode")String orderCode,@RequestParam("userId") Long userId,HttpSession session){
    	boolean flag=false;
    	try {
    		AuthUser userLogin=CMSSessionUtils.getSessionUser(session);
        	AuthUser user=this.authUserService.findById(userId);
        	AuthUserOrderRelation orderRelation=new AuthUserOrderRelation();
        	orderRelation.setCreateTime(new Date());
        	orderRelation.setMemberId(memberId);
        	orderRelation.setUpdateId(userLogin.getId());
        	orderRelation.setOrderCode(orderCode);
        	orderRelation.setUpdateTime(new Date());
        	orderRelation.setUserId(userId);
        	orderRelation.setUserName(user.getLoginName());
        	this.authUserOrderRelationService.add(orderRelation);
        	
        	OrderBase orderBase=this.orderBaseService.findByMemberIdAndOrderCode(memberId, orderCode);
        	/**设置订单分配状态为已分配*/
        	orderBase.setDistribution(CMSUserOrderConstant.ORDER_DISTRIBUTION_STATUS_TWO);
        	orderBase.setDistributionTime(new Date());
        	this.orderBaseService.updateOrderDistributionWithdraw(orderBase);
        	flag=true;
		} catch (Exception e) {
			LOGGER.error("cms:AuthUserOrderRelationController.java 订单分配给部分客服异常", e);
		}
    	return flag;
    }
	
    /***
     * 订单撤回请求
     * @param memberId
     * @param orderCode
     * @param session
     * @return
     */
    @RequestMapping("withdrawOrder/{memberId}/{orderCode}/{time}")
    public String withdrawOrder(@PathVariable("memberId") Long memberId,@PathVariable("orderCode") String orderCode,HttpSession session,RedirectAttributes redirectAttributes){
    	try {
    		AuthUser user=CMSSessionUtils.getSessionUser(session);
    		OrderBase orderBase=this.orderBaseService.findByMemberIdAndOrderCode(memberId, orderCode);
    		this.authUserOrderRelationService.deleteByOrderCodeAndUserId(user.getId(),orderCode);
    		orderBase.setWithdrawTime(new Date());
        	orderBase.setDistribution(CMSUserOrderConstant.ORDER_DISTRIBUTION_STATUS_THREE);
        	this.orderBaseService.updateOrderDistributionWithdraw(orderBase);
	        addMessage(redirectAttributes, "订单撤回成功");
    	} catch (Exception e) {
    		addMessage(redirectAttributes, "订单撤回失败");
            LOGGER.error("cms:AuthUserOrderRelationController.java   withdrawOrder订单撤回异常", e);
    	}
    	return "redirect:"+Global.getAdminPath()+"/order/queryOrderBaseList";
    }
	
	
}
