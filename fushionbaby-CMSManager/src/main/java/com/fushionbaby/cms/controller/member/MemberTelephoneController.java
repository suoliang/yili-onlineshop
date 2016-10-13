package com.fushionbaby.cms.controller.member;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cms.dto.SmsDto;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.member.dto.MemberTelephoneDto;
import com.fushionbaby.member.model.MemberTelephone;
import com.fushionbaby.member.service.MemberTelephoneService;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;

/**
 * 会员手机表
 * @author King 索亮
 *
 */
@Controller
@RequestMapping("/memberTelephone")
public class MemberTelephoneController {
	/**注入会员手机表*/
	@Autowired
	private MemberTelephoneService<MemberTelephone> memberTelephoneService;
	/**注入短信service*/
	@Autowired
	private SmsService<Sms> smsService;
	
	/**分页*/
	@RequestMapping(value = "/memberTelephoneList")
	public String memberTelephoneList(MemberTelephoneDto memberTelephoneDto,
			BasePagination page,
			HttpServletRequest request,
			ModelMap model){
		try {
			if (page == null) {
				page = new BasePagination();
			}
			//分页参数封装
			Map<String, String> params = new HashMap<String, String>();
			params.put("telephone", memberTelephoneDto.getTelephone());
			params.put("createTimeFrom", memberTelephoneDto.getCreateTimeFrom());
			params.put("createTimeTo", memberTelephoneDto.getCreateTimeTo());
			page.setParams(params);
			page = memberTelephoneService.getListPage(page);
			@SuppressWarnings("unchecked")
			List<MemberTelephone> telephoneList = (List<MemberTelephone>) page.getResult();
			/**查询出来的结果集*/
			List<MemberTelephone> list = memberTelephoneService.findByCondition(memberTelephoneDto);
			model.addAttribute("page", page);
			model.addAttribute("memberTelephoneDto", memberTelephoneDto);
			model.addAttribute("telephoneList", telephoneList);
			request.getSession().setAttribute("sessionTelephone", list);
			request.getSession().setAttribute("smsLength", list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "models/member/memberTelephoneList";
	}
	/***
	 * 促销短信
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("sms_promotion")
	public Object sms_promotion(
			@RequestParam(value="content",defaultValue="")String content,
			HttpServletRequest request,ModelMap model){
		List<MemberTelephone> list =(List<MemberTelephone>) request.getSession().getAttribute("sessionTelephone");
		if (CollectionUtils.isEmpty(list)) {
			return Jsonp.error("没有找到你搜索的查询数据或重新查询");
		}
		System.out.println(list.size());
		SmsDto smsDto = new SmsDto();
		int j = 0;//判断发送出去多少条
		for (int i = 0; i < list.size(); i++) {
			try {
				smsService.sendSmsPromotion(list.get(i).getTelephone(),content,SourceConstant.CMS_CODE);//
				j++;
			} catch (RemoteException e) {
				e.printStackTrace();
				return Jsonp.error("短信服务器响应超时");
			}
		}
		smsDto.setSendSuccess(j);//发送成功
		smsDto.setSendFail(list.size() - j);
		return Jsonp_data.success(smsDto);
	}
	
	/***
	 * 定时短信发送
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("sms_Scheduled")
	public Object sms_Scheduled(
			String content,
			String time,
			HttpServletRequest request,ModelMap model){
		List<MemberTelephone> list =(List<MemberTelephone>) request.getSession().getAttribute("sessionTelephone");
		if (CollectionUtils.isEmpty(list)) {
			return Jsonp.error("没有找到你搜索的查询数据或重新查询");
		}
		if (time.length() != 14) {
			return Jsonp.error("请输入正确的时间格式，yyyyMMddHHmmss");
		}
		Date date = DateFormat.yyyyMMddHHmmssToDate(time);
		Long timediffer = date.getTime() - System.currentTimeMillis();
		if (timediffer < 0) {
			return Jsonp.error("时间请输入未来时间");
		}
		//时间小于30分钟
		if (timediffer < 30*60*1000) {
			return Jsonp.error("时间间隔请不少于30分钟");
		}
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			try {
				smsService.sendSmsScheduled(list.get(i).getTelephone(),content,SourceConstant.CMS_CODE,time);//Remote
			} catch (RemoteException e) {
				e.printStackTrace();
				return Jsonp.error("短信服务器响应超时");
			}
		}
		return Jsonp.success();
	}
	/***
	 * 自定义短信发送
	 * @param telephone
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping("sms_userDefined")
	public Object sms_userDefined(
			String telephone,
			String content){
		try {
			smsService.sendSmsUserDefined(telephone, content, SourceConstant.CMS_CODE);
		} catch (RemoteException e) {
			e.printStackTrace();
			return Jsonp.error("短信服务器响应超时");
		}

		return Jsonp.success();
	}
	
	
	
	@RequestMapping(value = "/toSendSmsUserDefined")
	public String toSendSmsUserDefined(){
		return "models/member/sendSmsUserDefined";
	}
	
	@RequestMapping(value = "/toSendSmsPromotion")
	public String toSendSmsPromotion(){
		return "models/member/sendSmsPromotion";
	}
	
	@RequestMapping(value = "/toSendSmsOnTime")
	public String toSendSmsOnTime(){
		return "models/member/sendSmsOnTime";
	}
}
