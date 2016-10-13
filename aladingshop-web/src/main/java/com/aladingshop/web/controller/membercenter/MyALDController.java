package com.aladingshop.web.controller.membercenter;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.condition.member.MemberCommentQueryCondition;
import com.fushionbaby.common.condition.sku.SkuCollectQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.MemberCommentDto;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.facade.biz.common.member.MemberCommentFacade;
import com.fushionbaby.facade.biz.common.sku.MemberSkuCollectFacade;

/***
 * 
 * @description 类描述...个人中心的我的阿拉丁
 * @author 徐培峻
 * @date 2015年8月19日下午3:11:01
 */
@Controller
@RequestMapping("/myAld")
public class MyALDController {
	private static final Log ALDLogin=LogFactory.getLog(MyALDController.class);
	
	@Autowired
	private MemberSkuCollectFacade memberSkuCollectFacade;
	
	@Autowired
	private MemberCommentFacade memberCommentFacade;
	
	
	/***
	 * 我的收藏  外部链接
	 * @return
	 */
	@RequestMapping("/collectList")
	public ModelAndView collect(){
		return new ModelAndView("redirect:/myAld/collect.htm/"+1+"/"+new Date().getTime());
	}
	
	/***
	 * 我的收藏 分页翻页
	 * @param currPage
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/collect.htm/{currPage}/{time}")
	public String myCollection(@PathVariable("currPage") Integer currPage, HttpServletRequest request,Model model){
		try {
		String sid = CookieUtil.getCookieValue(request,CookieConstant.COOKIE_SID);
		UserDto user = (UserDto) SessionCache.get(sid);
		if (user == null) {
			return  "login/login";
		}
		if(currPage<2)
			currPage=1;
		SkuCollectQueryCondition collectionCondition=new SkuCollectQueryCondition();
		collectionCondition.setMemberId(user.getMemberId());
		collectionCondition.setIsAttention(CommonConstant.YES);
		Integer total=memberSkuCollectFacade.findCollect(collectionCondition).size();
		collectionCondition.setStart((currPage-1)*3);
		collectionCondition.setLimit(3);
	    List<SkuDto> collectionSkuList=memberSkuCollectFacade.findCollect(collectionCondition);
	    model.addAttribute("collectionSkuList", collectionSkuList);
	    model.addAttribute("pageSize", (Integer)(total+2)/3);
	    model.addAttribute("currPage", currPage);
	    /**我的收藏页面选中*/
	    model.addAttribute("marker", "collect");
		} catch (Exception e) {
			ALDLogin.error("web:MyALDController.java 我的收藏查询异常", e);
		}
		return "membercenter/ald/myCollect";
	}
	
	/***
	 * 取消收藏
	 * @param skuCode
	 * @param currPage
	 * @param request
	 * @return
	 */
	@RequestMapping("/cancelCollect.htm/{skuCode}/{currPage}")
	public ModelAndView cancelCollect(@PathVariable("skuCode") String skuCode,@PathVariable("currPage") Integer currPage, HttpServletRequest request){
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto user = (UserDto) SessionCache.get(sid);
			if (user == null) {
				return new ModelAndView("redirect:/login/login");
			}
			if(currPage<2)
				currPage=1;
			SkuCollectQueryCondition collectionCondition=new SkuCollectQueryCondition();
			collectionCondition.setMemberId(user.getMemberId());
			collectionCondition.setIsAttention(CommonConstant.YES);
			Integer total=memberSkuCollectFacade.findCollect(collectionCondition).size();
			memberSkuCollectFacade.dropCollectBySkuCode(skuCode, user);
			if(currPage>(total+1)/3)
				currPage=(total+1)/3;
			return new ModelAndView("redirect:/myAld/collect.htm/"+currPage+"/"+new Date().getTime());
		} catch (Exception e) {
			return new ModelAndView("redirect:/login/login");
		}
		
	}
	
	
	
	
	
	/***
	 * 我的评论 （已完成的评论）外部链接
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/commentList")
	public ModelAndView commentList(HttpServletRequest request,Model model){
		return new ModelAndView("redirect:/myAld/comment.htm/"+1+"/"+new Date().getTime());
	}
	
	
	/***
	 * 我的评论 （已完成的评论） 分页翻页
	 * @param currPage
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/comment.htm/{currPage}/{time}")
	public String  myComment(@PathVariable("currPage") Integer currPage,HttpServletRequest request,Model model){
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		UserDto user = (UserDto) SessionCache.get(sid);
		if (user == null) {
			return  "login/login";
		}
		if(currPage<2)
			currPage=1;
		MemberCommentQueryCondition condition=new MemberCommentQueryCondition();
		condition.setMemberId(user.getMemberId());
		Integer total=memberCommentFacade.getMyCommentList(condition).size();
		condition.setStart((currPage-1)*3);
		condition.setLimit(3);
		List<MemberCommentDto> memberCommentList=memberCommentFacade.getMyCommentList(condition);
		model.addAttribute("memberCommentList", memberCommentList);
		model.addAttribute("pageSize", (Integer)(total+2)/3);
	    model.addAttribute("currPage", currPage);
	    /**我的评论页面选中*/
	    model.addAttribute("marker", "comment");
		return "membercenter/ald/myComment";
	}
	
	
	
}
