package com.fushionbaby.facade.biz.common.member.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import util.EmptyValidUtils;
import util.ImageConstantFacade;

import com.fushionbaby.common.condition.member.MemberCommentQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.dto.MemberCommentDto;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.core.model.OrderLineUser;
import com.fushionbaby.core.service.OrderLineUserService;
import com.fushionbaby.facade.biz.AbstactSkuFacade;
import com.fushionbaby.facade.biz.common.member.MemberCommentFacade;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberSkuComment;
import com.fushionbaby.member.model.MemberSkuCommentImage;
import com.fushionbaby.member.model.MemberSkuCommentReply;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.member.service.MemberSkuCommentImageService;
import com.fushionbaby.member.service.MemberSkuCommentReplyService;
import com.fushionbaby.member.service.MemberSkuCommentService;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuImage;
import com.fushionbaby.sku.service.SkuImageService;
import com.fushionbaby.sku.service.SkuService;

/***
 * 会员评论
 * 
 * @author xupeijun
 *
 */
@Service
public class MemberCommentFacadeImpl extends AbstactSkuFacade implements MemberCommentFacade {

	@Autowired
	private OrderLineUserService<OrderLineUser> soSoLineService;
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private MemberSkuCommentImageService<MemberSkuCommentImage> commentImageService;
	/** 会员评论 */
	@Autowired
	private MemberSkuCommentService<MemberSkuComment> memberCommentService;
	/** 会员评论回复 */
	@Autowired
	private MemberSkuCommentReplyService<MemberSkuCommentReply> commentReplyService;

	@Autowired
	private SkuService skuService;

	@Autowired
	private SkuImageService<SkuImage> skuImageService;

	private static final Log logg = LogFactory.getLog(MemberCommentFacadeImpl.class);

	/** 匿名用户 */
	// private static final String MEMBER_NAME = "匿名用户";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.biz.common.sku.SkuDetailFacade#getSkuComments(
	 * java.lang.Long)
	 */
	public List<MemberCommentDto> getMemberComments(MemberCommentQueryCondition queryCondition) {
		List<MemberCommentDto> commentList = new ArrayList<MemberCommentDto>();

		Sku sku = skuService.queryBySkuCode(queryCondition.getSkuCode());
		if (sku == null) {
			return commentList;
		}
		
		List<MemberSkuComment> commentCollection = memberCommentService.queryByCondition(queryCondition);

		if (CollectionUtils.isEmpty(commentCollection)) {
			return commentList;
		}
		for (MemberSkuComment comment : commentCollection) {
			MemberCommentDto memberCommentDto = new MemberCommentDto();
			memberCommentDto.setCommentContent(comment.getCommentContent());
			// memberCommentDto.setCommentId(comment.getId());
			memberCommentDto.setMemberId(comment.getMemberId());
			memberCommentDto.setMemberName(comment.getMemberName());
			memberCommentDto.setScore(comment.getScore());
			memberCommentDto.setCommentTime(DateFormatUtils.format(comment.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			// memberCommentDto.setSourceCode(String.valueOf(comment.getSourceCode()));
			// memberCommentDto.setPraiseCount(comment.getPraiseCount());
			memberCommentDto.setSkuColor(comment.getSkuColor());
			memberCommentDto.setSkuSize(comment.getSkuSize());
			memberCommentDto.setSkuCode(comment.getSkuCode());
			memberCommentDto.setCommentLevel(comment.getCommentLevel());
			memberCommentDto.setIsAnonymous(comment.getIsAnonymous());
			// memberCommentDto.setImgUrlList(this.getImgUrlList(comment.getId()));
			// List<MemberCommentReplyDto> memberSkuCommentReplyList =
			// this.getMemberCommentReplyDto(comment.getId());
			// memberCommentDto.setCommentReplyList(memberSkuCommentReplyList);
			// memberCommentDto.setReplyCount(memberSkuCommentReplyList.size());
			BeanNullConverUtil.nullConver(memberCommentDto);
			commentList.add(memberCommentDto);
		}
		return commentList;
	}

	//
	// private List<String> getImgUrlList(Long commentId) {
	// List<String> urlList = new ArrayList<String>();
	// List<MemberSkuCommentImage> commentImageList =
	// commentImageService.findByCommentId(commentId);
	// if (!CollectionUtils.isEmpty(commentImageList) && commentImageList.size()
	// > 0) {
	// for (MemberSkuCommentImage memberCommentImage : commentImageList) {
	// urlList.add(ImageConstantFacade.MEMBER_COMMENT_PICTURE_SERVER_PATH + "/"
	// + memberCommentImage.getImgUrl());
	// }
	// }
	//
	// return urlList;
	// }

	// private List<MemberCommentReplyDto> getMemberCommentReplyDto(Long
	// commentId) {
	// List<MemberSkuCommentReply> commentReplyList =
	// commentReplyService.findByCommentId(commentId);
	// List<MemberCommentReplyDto> memberCommentReplyDtoList = new
	// ArrayList<MemberCommentReplyDto>();
	// for (MemberSkuCommentReply commentReply : commentReplyList) {
	// MemberCommentReplyDto memberCommentReplyDto = new
	// MemberCommentReplyDto();
	// memberCommentReplyDto.setReplyContent(commentReply.getReplyContent());
	// memberCommentReplyDto.setCreateTime(DateFormatUtils.format(commentReply.getCreateTime(),
	// "yyyy-MM-dd HH:mm:ss"));
	// String memberName = null;
	// if (StringUtils.equalsIgnoreCase(commentReply.getIsAnonymous(), "n")) {
	// memberName = commentReply.getMemberName();
	// }
	// memberCommentReplyDto.setMemberName(StringUtils.isBlank(memberName) ?
	// MEMBER_NAME : memberName);
	// memberCommentReplyDto.setIsAnonymous(commentReply.getIsAnonymous());
	// BeanNullConverUtil.nullConver(memberCommentReplyDto);
	// memberCommentReplyDtoList.add(memberCommentReplyDto);
	// }
	// return memberCommentReplyDtoList;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.facade.biz.common.sku.SkuCommentFacade#addSkuComment(
	 * com.fushionbaby.common.dto.sku.SkuCommentDto)
	 */
	public Long addMemberComment(MemberCommentDto memberCommentDto, UserDto userDto, String ip) {
		MemberSkuComment memberComment = new MemberSkuComment();
		try {
			// BeanCopyUtil.copyProperty(memberCommentDto, memberComment, null);
			memberComment.setCommentContent(memberCommentDto.getCommentContent()); // 评论内容
			memberComment.setScore(memberCommentDto.getScore());// 评论分数
			memberComment.setSkuCode(memberCommentDto.getSkuCode());// sku_code
			// memberComment.setSkuColor(memberCommentDto.getSkuColor());//
			// sku_color
			memberComment.setSkuCode(memberCommentDto.getSkuCode());// sku_id
			memberComment.setSkuSize(memberCommentDto.getSkuSize());// sku_size
			// memberComment.setOrderLineId(memberCommentDto.getSoLineId());
			// memberComment.setSourceCode(Integer.valueOf(memberCommentDto.getSourceCode()));
			// memberComment.setOrderCode(memberCommentDto.getSoCode());// 订单号
			memberComment.setMemberId(userDto.getMemberId());// 评论者id
			memberComment.setMemberName(userDto.getLoginName());// 评论人名字
			memberComment.setCreateTime(new Date());// 评论创建时间
			memberComment.setIpAddress(ip);// 评论IP
			memberComment.setStatus(CommonConstant.YES);// 评论状态
			memberComment.setVersion(new Date());// 版本时间
			memberComment.setDisable(CommonConstant.YES);
			memberComment.setPraiseCount(0);
			memberCommentService.add(memberComment);
			soSoLineService.updateIsComment(memberComment.getOrderLineId(), CommonConstant.YES);
		} catch (DataAccessException e) {
			logg.error("商品评论出错" + e);
			return null;
		} catch (Exception e) {
			logg.error("商品评论出错" + e);
			return null;
		}
		return memberComment.getId();
	}

	public List<MemberCommentDto> getMyCommentList(MemberCommentQueryCondition condition) {
		List<MemberSkuComment> memberCommentList = memberCommentService.getMyCommentList(condition);
		List<MemberCommentDto> memberCommentDtoList = new ArrayList<MemberCommentDto>();
		if (memberCommentList == null) {
			return memberCommentDtoList;
		}
		for (MemberSkuComment memberSkuComment : memberCommentList) {
			MemberCommentDto memberCommentDto = new MemberCommentDto();
			memberCommentDto.setCommentContent(memberSkuComment.getCommentContent());// 评论内容
			memberCommentDto.setScore(memberSkuComment.getScore());// 分数
			memberCommentDto.setSkuSize(memberSkuComment.getSkuSize());// 类型
			memberCommentDto.setCommentTime(DateFormat.dateToString(memberSkuComment.getCreateTime()));// 显示时间
			memberCommentDto.setSkuColor(memberSkuComment.getSkuColor());// yanse
			memberCommentDto.setSkuCode(memberSkuComment.getSkuCode());// skuCode
			memberCommentDto.setMemberId(memberSkuComment.getMemberId());
			/** 获取图片路径 */
			List<SkuImage> images = skuImageService.findBySkuCode(memberSkuComment.getSkuCode());
			if (!EmptyValidUtils.arrayIsEmpty(images)) {
				memberCommentDto.setSkuImg(ImageConstantFacade.IMAGE_SERVER_ROOT_PATH + images.get(0).getImgUrl());
			}
			memberCommentDtoList.add(memberCommentDto);
		}
		return memberCommentDtoList;
	}

	public void submitComment(MemberSkuComment skuComment) {
		this.memberCommentService.add(skuComment);
	}

}
