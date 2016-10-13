package com.fushionbaby.cms.controller.member;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.dto.MemberDto;
import com.fushionbaby.member.model.MemberEmail;
import com.fushionbaby.member.service.MemberEmailService;
import com.fushionbaby.sms.model.Email;
import com.fushionbaby.sms.send.SendMailCode;
import com.fushionbaby.sms.service.EmailService;

/***
 * 
 * @author King 索亮
 *
 */
@Controller
@RequestMapping("/memberEmail")
public class MemberEmailController {
	/**注入会员邮箱表*/
	@Autowired
	private MemberEmailService<MemberEmail> memberEmailService;
	/**注入邮件*/
	@Autowired
	private EmailService<Email> emailService;
	
	/**分页*/
	@RequestMapping("memberEmailList")
	public String memberEmailList(MemberDto memberDto,
			BasePagination page,
			ModelMap model){
		try {
			if (page == null) {
				page = new BasePagination();
			}
			//分页参数封装
			Map<String, String> params = new HashMap<String, String>();
			params.put("emailDistinguishExportList", "1");
			params.put("email", memberDto.getEmail());
			params.put("createTimeFrom", memberDto.getCreateTimeFrom());
			params.put("createTimeTo", memberDto.getCreateTimeTo());
			page.setParams(params);
			page = memberEmailService.getListPage(page);
			@SuppressWarnings("unchecked")
			List<MemberEmail> emailList = (List<MemberEmail>) page.getResult();
			model.addAttribute("page", page);
			model.addAttribute("memberEmailDto", memberDto);
			model.addAttribute("emailList", emailList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "models/member/memberEmailList";
	}
	
	@RequestMapping("/sendEmail")
	public String sendEmail(MemberDto memberDto,String emailSubject,String emailContent,BasePagination page,ModelMap model){
		try {
			//分页参数封装
			Map<String, String> params = new HashMap<String, String>();
			params.put("emailDistinguishExportList", "2");
			params.put("email", memberDto.getEmail());
			params.put("createTimeFrom", memberDto.getCreateTimeFrom());
			params.put("createTimeTo", memberDto.getCreateTimeTo());
			Map<String, Object> map = new HashMap<String, Object>();
			map.putAll(params);
			List<MemberEmail> memberEmailList = memberEmailService.getListPage(map);
			for (int i = 0; i < memberEmailList.size(); i++) {
				String receiverEmail = memberEmailList.get(i).getEmail();
//				Email email = new Email();//保存发送的邮件
//				email.setReceiverEmail(receiverEmail);
//				email.setMemberName(receiverEmail);
//				email.setSourceCode(SourceConstant.CMS_CODE);//来源
//				email.setEmailType(WebConstant.EMAIL_PUSH_TYPE);//邮件类型
//				email.setEmailContent(emailContent);
//				emailService.add(email);
				SendMailCode.sendHtmlEmail(WebConstant.SENDER_EMAIL, receiverEmail, emailSubject, emailContent);//发送邮件
			}
			model.addAttribute("emailPrompt", "邮件发送成功!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return memberEmailList(memberDto, page, model);
	}
	
}
