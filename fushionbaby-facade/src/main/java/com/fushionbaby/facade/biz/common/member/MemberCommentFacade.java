
package com.fushionbaby.facade.biz.common.member;

import java.util.List;

import com.fushionbaby.common.condition.member.MemberCommentQueryCondition;
import com.fushionbaby.common.dto.MemberCommentDto;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.member.model.MemberSkuComment;

/***
 * 会员评论
 * @author xupeijun
 *
 */
public interface MemberCommentFacade {
	/**
	 * 通过商品编号获取会员评论信息
	 * 
	 * @param skuId 商品号
	 * @return
	 */
	List<MemberCommentDto> getMemberComments(MemberCommentQueryCondition queryCondition);
	
	
	/***
	 *  添加会员评价
	 * @param memberCommentDto 会员评价信息
	 * @param userDto
	 * @param ip  
	 * @return 返回 评论id
	 */
	Long addMemberComment(MemberCommentDto memberCommentDto,UserDto userDto,String ip);


	/***
	 * 我的评论  已经评论过的
	 * @param condition
	 * @return
	 */
	List<MemberCommentDto> getMyCommentList(MemberCommentQueryCondition condition);

	/***
	 * 提交评价
	 * @param skuComment
	 */
	void submitComment(MemberSkuComment skuComment);
	
	
	
	
	
}
