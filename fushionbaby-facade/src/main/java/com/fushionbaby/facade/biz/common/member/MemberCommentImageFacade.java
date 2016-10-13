package com.fushionbaby.facade.biz.common.member;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/***
 * 处理评论图片
 * @author xupeijun
 *
 */
public interface MemberCommentImageFacade {
	/***
	 * 添加评论图片
	 * @param commentId
	 * @param files
	 * @param source
	 */
   public void addCommentImageByCommentId(Long commentId, List<MultipartFile> fileList,String source);
	
	
}
