package com.fushionbaby.facade.biz.common.member.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fushionbaby.facade.biz.common.member.MemberCommentImageFacade;
import com.fushionbaby.member.model.MemberSkuCommentImage;
import com.fushionbaby.member.service.MemberSkuCommentImageService;

/***
 * 
 * @author xupeijun
 *
 */
@Service
public class MemberCommentImageFacadeImpl implements MemberCommentImageFacade{

	private static final Log logg=LogFactory.getLog(MemberCommentImageFacadeImpl.class);
	
	@Autowired 
	private  MemberSkuCommentImageService<MemberSkuCommentImage> memberCommentImageService;
	
	public void addCommentImageByCommentId(Long commentId,  List<MultipartFile> fileList,String source) {
		try { 
			Integer index=1;
            if(fileList!=null&&fileList.size()>0){  
            	for (MultipartFile multipartFile : fileList) {
                       if(multipartFile.getSize()>0)
                         {
                      //     File addFile = FileUploadTools.addFile(multipartFile, ImageConstantFacade.MEMBER_COMMENT_PICTURE_PATH);
                           MemberSkuCommentImage commentImage=new MemberSkuCommentImage();
                           commentImage.setCommentId(commentId);
                        //   commentImage.setImgUrl(addFile.getName());
                           commentImage.setIndex(index++);
                           commentImage.setCreateTime(new Date());
                           commentImage.setSourceCode(Integer.valueOf(source));
                           memberCommentImageService.add(commentImage);
                         }
              	} } 
				} catch (IllegalStateException e) {
					logg.error("评论图片添加的时候数据出错", e);
				} 
//					catch (IOException e) {
//					logg.error("评论图片添加的时候出错", e);
//
//				}
	}

}
