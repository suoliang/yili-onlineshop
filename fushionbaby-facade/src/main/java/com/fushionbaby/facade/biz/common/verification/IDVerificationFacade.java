package com.fushionbaby.facade.biz.common.verification;

import java.io.UnsupportedEncodingException;

import com.fushionbaby.common.dto.verification.IDVerificationReqDto;
import com.fushionbaby.common.dto.verification.VerificationResDto;

/***
 * 身份校验 
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月23日上午11:10:06
 */
public interface IDVerificationFacade {
	/**获取校验信息
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public VerificationResDto getIDVerificationInfo(IDVerificationReqDto reqDto) throws UnsupportedEncodingException;
}
