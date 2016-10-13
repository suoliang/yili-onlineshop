package com.fushionbaby.cms.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fushionbaby.common.enums.VerificationResCodeEnum;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.VerificationRecord;
import com.fushionbaby.member.service.VerificationRecordService;

/**
 * 用户认证身份证和银行卡记录表
 * @author chenyingtao
 *
 */
@Controller
@RequestMapping("verifiRecord")
public class VerificationRecordController {
	
	@Autowired
	private VerificationRecordService<VerificationRecord> verificationRecordService;
	
	private static final Log LOGGER = LogFactory.getLog(VerificationRecordController.class);

	
	@SuppressWarnings("unchecked")
	@RequestMapping("verifiRecordList")
	public String verifiRecordList(VerificationRecord verificationRecord,BasePagination page,ModelMap model){
		
		try {
			if(page == null){
				page = new BasePagination();
			}
			/**设置搜索参数*/
			Map<String, String> params = new HashMap<String, String>();
			params.put("trueName", verificationRecord.getTrueName());
			params.put("responseStatus", verificationRecord.getResponseStatus());
			params.put("verificationStatus", verificationRecord.getVerificationStatus());
			
			page.setParams(params);
			page = verificationRecordService.getListPage(page);
			
			List<VerificationRecord> list = (List<VerificationRecord>) page.getResult();
			
			Map<String, String> verificationResCodeEnumMap = VerificationResCodeEnum.getVerificationResCodeEnumMap();
			
			model.addAttribute("page", page);
			model.addAttribute("verificationRecord", verificationRecord);
			model.addAttribute("list", list);
			model.addAttribute("verificationResCodeEnumMap", verificationResCodeEnumMap);
		} catch (DataAccessException e) {
			LOGGER.error("查询用户认证身份证和银行卡记录表失败！", e);
			return "";
		}
		return "models/member/memVerifiRecordList";
		
	}
}
