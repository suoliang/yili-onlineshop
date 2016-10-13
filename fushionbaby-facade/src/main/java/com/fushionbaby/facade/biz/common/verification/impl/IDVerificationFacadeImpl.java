package com.fushionbaby.facade.biz.common.verification.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.VerificationConstant;
import com.fushionbaby.common.dto.verification.IDVerificationReqDto;
import com.fushionbaby.common.dto.verification.VerificationResDto;
import com.fushionbaby.common.util.HttpRequest;
import com.fushionbaby.facade.biz.common.verification.IDVerificationFacade;
import com.fushionbaby.member.model.VerificationRecord;
import com.fushionbaby.member.service.VerificationRecordService;
import com.fushionbaby.other.util.json.JSONException;
import com.fushionbaby.other.util.json.JSONObject;
import com.google.gson.Gson;
/***
 * 身份校验的实现
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月23日上午11:16:06
 */
@Service
public class IDVerificationFacadeImpl implements IDVerificationFacade {

	private static final Log LOGGER=LogFactory.getLog(IDVerificationFacadeImpl.class);
	/**验证的记录表*/
	@Autowired
	private VerificationRecordService<VerificationRecord> verificationRecordService;
	
	public VerificationResDto getIDVerificationInfo(IDVerificationReqDto reqDto) throws UnsupportedEncodingException {
		    String 	name=URLEncoder.encode(reqDto.getRealName(),"UTF-8");
		    reqDto.setRealName(name);
		    String url="http://121.41.42.121:8080/v2/id-server";
		    String param="mall_id="+reqDto.getMallId()+"&realname="+name+"&idcard="+reqDto.getIdCard()+"&tm="+reqDto.getTm()+"&sign="+reqDto.getSign();
		    LOGGER.info("IDVerificationFacadeImpl 身份证验证 请求数据 param="+param);
		    String json = HttpRequest.sendGet(url,param);
		    LOGGER.info("IDVerificationFacadeImpl 身份证验证 返回json数据 json="+json);
			JSONObject jo;
			VerificationResDto resDto=new VerificationResDto();
			resDto.setStatus("2002");
			try {
				jo = new JSONObject(json);
				String  data = jo.getString("data");
				String  status = jo.getString("status");
				resDto=new Gson().fromJson(data, VerificationResDto.class);
				resDto.setStatus(status);
				resDto.setData(data);
			} catch (JSONException e) {
				e.printStackTrace();
			} 
			reqDto.setRealName(URLDecoder.decode(reqDto.getRealName(), "UTF-8"));
		    saveVerificationRecord(reqDto, resDto);
			return resDto;
}

	/***
	 * 保存验证信息
	 * @param reqDto
	 * @param name
	 * @param resDto
	 */
	private void saveVerificationRecord(IDVerificationReqDto reqDto, VerificationResDto resDto) {
		VerificationRecord record=new VerificationRecord();
		record.setCreateTime(new Date());
		record.setIdentityCardNo(reqDto.getIdCard());
		record.setResponseStatus(resDto.getCode());
		record.setTrueName(reqDto.getRealName());
		record.setVerificationStatus(VerificationConstant.VALIDATE_SUCCESS_RESPONSECODE.equals(resDto.getCode())?CommonConstant.YES:CommonConstant.NO);
		record.setVerificationType(VerificationConstant.VERIFICATION_TYPE_ID);
		this.verificationRecordService.add(record);
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		
		String name="杨柳";
		IDVerificationReqDto req=new IDVerificationReqDto(name , "320830199110080839");
		VerificationResDto resDto=new IDVerificationFacadeImpl().getIDVerificationInfo(req);
        System.out.println(resDto.getStatus());
        System.out.println(resDto.getData());
		System.out.println(req.getTm());
		System.out.println(req.getSign());
	}

}
