package com.fushionbaby.web.controller.membercenter;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.dto.MemberCommentDto;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.order.OrderLineDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.GetIpAddress;
import com.fushionbaby.core.model.OrderLineUser;
import com.fushionbaby.core.service.OrderLineUserService;
import com.fushionbaby.facade.biz.common.member.MemberCommentFacade;
import com.fushionbaby.facade.biz.common.member.MemberCommentImageFacade;
import com.fushionbaby.facade.image.ImageProcess;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberSkuComment;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.member.service.MemberSkuCommentService;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.service.SkuService;

/***
 * 会员评论
 * @author xupeijun
 *
 */
@Controller
@RequestMapping("/memberComment")
public class MemberSkuCommentController {
	
	/** 订单行*/
	@Autowired
	private OrderLineUserService<OrderLineUser> soSoLineService;
   /** 获得图片*/
	@Autowired
	private ImageProcess imageProcess;
	/** 商品*/
	@Autowired
	private SkuService<Sku> skuService;
	
	@Autowired
	private MemberCommentFacade commentFacade;
	
	@Autowired
	private MemberService<Member> memberService;
	
	@Autowired
	private MemberCommentImageFacade commentImageFacade;
	
	/** 会员评论图片*/
	@Autowired
	private MemberSkuCommentService<MemberSkuComment> memberCommentService;
	
	private static final Log logg=LogFactory.getLog(MemberSkuCommentController.class);
	/***
	 * 获取商品对应的订单信息
	 */
	@RequestMapping("/getSoLineDetail")
	public String getSoLineDetail(HttpServletRequest request,
			@RequestParam(value = "soLineId") Long soLineId,
			@RequestParam(value = "soId") Long soId, ModelMap model) {
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		if (StringUtils.isEmpty(sid)) {
			return "common/404";
		}
		OrderLineUser soSoLine = null;
		OrderLineDto orderLineDto = null;
		try {
			//订单行
			soSoLine = soSoLineService.findById(soLineId);
			orderLineDto = new OrderLineDto();
			String img_path = imageProcess.getThumImagePath(soSoLine.getSkuCode(),ImageStandardConstant.IMAGE_STANDARD_WEB_65);
			orderLineDto.setImg_path(img_path);	
			orderLineDto.setCommentCount(memberCommentService.getPagetotal(soSoLine.getSkuCode()));
		} catch (DataAccessException e) {
			logg.error("获得评价商品失败", e);	
			}
		model.addAttribute("orderLineDto", orderLineDto);
		model.addAttribute("soSoLine", soSoLine);
		return "person/evaluating";
		
	}
	/***
	 * 会员进行商品评论
	 * @param memberCommentDto
	 * @param filerequest
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/submitComment")
	public Object submitComment(MemberCommentDto memberCommentDto,MultipartHttpServletRequest filerequest,	HttpServletRequest request,HttpServletResponse response) {
		List<MultipartFile>  fileList=filerequest.getFiles("files");
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		if (StringUtils.isEmpty(sid)) {
			return "common/404";
		}
		UserDto userDto = (UserDto) SessionCache.get(sid);
	       memberCommentDto.setSourceCode(SourceConstant.WEB_CODE);
		   Long comment_id = commentFacade.addMemberComment(memberCommentDto,userDto,GetIpAddress.getIpAddr(request));
			if(ObjectUtils.notEqual(comment_id, null)){
				this.commentImageFacade.addCommentImageByCommentId(comment_id, fileList, SourceConstant.WEB_CODE);
				return "person/center";
			}
			else{
				return "common/404";
			}
	}
	
	
	
}			
	
		
		
		
	

	

	
	

