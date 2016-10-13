package com.fushionbaby.facade.biz.common.verification.impl;

import java.net.URLDecoder;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.VerificationConstant;
import com.fushionbaby.common.dto.verification.BankCardVerificationReqDto;
import com.fushionbaby.common.dto.verification.VerificationResDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.HttpRequest;
import com.fushionbaby.facade.biz.common.verification.BankCardVerificationFacade;
import com.fushionbaby.member.model.VerificationRecord;
import com.fushionbaby.member.service.VerificationRecordService;
import com.fushionbaby.other.util.json.JSONObject;
import com.google.gson.Gson;
/***
 * 身份校验的实现
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月23日上午11:16:06
 */
@Service
public class BankCardVerificationFacadeImpl implements BankCardVerificationFacade {

	
	
	private static final Log LOGGER=LogFactory.getLog(BankCardVerificationFacadeImpl.class);
	@Autowired
	private VerificationRecordService<VerificationRecord> verificationRecordService;
	
	public VerificationResDto getBankCardVerificationInfo(BankCardVerificationReqDto reqDto) {
		   VerificationResDto resDto=new VerificationResDto();
			resDto.setStatus("2002");
		    String url="http://121.41.42.121:8080/v3/card2-server";
		    String param="mall_id="+reqDto.getMallId()+"&realname="+reqDto.getRealName()+"&cardnum="+reqDto.getCardNum()+"&tm="+reqDto.getTm()+"&sign="+reqDto.getSign();
		    LOGGER.info("银行卡和姓名验证请求参数："+param);
		    String json = HttpRequest.sendGet(url,param);
		    LOGGER.info("银行卡和姓名验证返回结果："+json);
			JSONObject jo;
			try {
				jo = new JSONObject(json);
				String  data = jo.getString("data");
				String  status = jo.getString("status");
				resDto=new Gson().fromJson(data, VerificationResDto.class);
				resDto.setStatus(status);
				resDto.setData(data);
				String trueName = URLDecoder.decode(reqDto.getRealName(), "UTF-8");
				reqDto.setRealName(trueName);
			} catch (Exception e) {
				e.printStackTrace();
			    LOGGER.info("银行卡和姓名验证返回结果解析异常"+json);
			} 
			saveVerificationRecord(reqDto, resDto);
			return resDto;
}

	
	/***
	 * 保存验证信息
	 * @param reqDto
	 * @param name
	 * @param resDto
	 */
	private void saveVerificationRecord(BankCardVerificationReqDto reqDto,VerificationResDto resDto){
		VerificationRecord record=new VerificationRecord();
         record.setBankCardNo(reqDto.getCardNum());
		record.setCreateTime(new Date());
		record.setResponseStatus(resDto.getCode());
		record.setTrueName(reqDto.getRealName());
		record.setVerificationStatus(VerificationConstant.VALIDATE_SUCCESS_RESPONSECODE.equals(resDto.getCode())?CommonConstant.YES:CommonConstant.NO);
		record.setVerificationType(VerificationConstant.VERIFICATION_TYPE_BANK);
		this.verificationRecordService.add(record);
	}
	
	public static void main(String[] args) {

		BankCardVerificationReqDto req=new BankCardVerificationReqDto("徐培峻", "6217580800001636898");
		req.setSign(MD5Util.md5Verification(req.getMallId()+req.getRealName()+req.getCardNum()+req.getTm()+"6150d00ec8997d6a43b7f6dced4bb5f0"));
		System.out.println("请求的待加密数据："+req.getMallId()+req.getRealName()+req.getCardNum()+req.getTm()+"6150d00ec8997d6a43b7f6dced4bb5f0");
		System.out.println("MD5加密之后的签名"+req.getSign());
		VerificationResDto resDto=new BankCardVerificationFacadeImpl().getBankCardVerificationInfo(req);
        System.out.println(resDto.toString());
        System.out.println(resDto);
		System.out.println(resDto.getStatus());
		System.out.println(resDto.getData());
	
	}


}
