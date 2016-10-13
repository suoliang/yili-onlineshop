package com.aladingshop.wap.controller.membercenter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.condition.sku.SkuCollectQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.facade.biz.common.sku.MemberSkuCollectFacade;

/***
 * 
 * @description 类描述...个人中心的我的阿拉丁
 * @author 徐培峻
 * @date 2015年8月19日下午3:11:01
 */
@Controller
@RequestMapping("/collect")
public class CollectController {
	private static final Log log=LogFactory.getLog(CollectController.class);
	
	@Autowired
	private MemberSkuCollectFacade memberSkuCollectFacade;
	@Autowired
	private UserFacade userFacade;
	
	/***
	 * 我的收藏  外部链接
	 * @return
	 */
	@RequestMapping("/collectList")
	public String collectList( HttpServletRequest request,Model model){
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto user = userFacade.getLatestUserBySid(sid);
			if(user == null || user.getMemberId() == null){
				return "redirect:/login/index" ;
			}
			
			SkuCollectQueryCondition collectionCondition=new SkuCollectQueryCondition();
			collectionCondition.setMemberId(user.getMemberId());
			collectionCondition.setIsAttention(CommonConstant.YES);
			List<SkuDto> collectionSkuList=memberSkuCollectFacade.findCollect(collectionCondition);
			model.addAttribute("collectionSkuList", collectionSkuList);
		} catch (DataAccessException e) {
			e.printStackTrace();
			log.error("查询收藏列表失败", e);
		}
		return "membercenter/collection";
	}
	
	
	/**
	 * 删除当前用户 指定的地址
	 * @param addressId 地址编号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteCollect")
	public Object deleteAddress(String skuCode,HttpServletRequest request){
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto user = userFacade.getLatestUserBySid(sid);
			memberSkuCollectFacade.dropCollectBySkuCode(skuCode, user);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return Jsonp.error();
		}
		return Jsonp.success();
	}
	
	
}

