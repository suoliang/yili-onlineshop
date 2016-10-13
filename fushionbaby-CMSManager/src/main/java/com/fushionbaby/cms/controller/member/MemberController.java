package com.fushionbaby.cms.controller.member;

import java.io.File;
import java.io.FileInputStream;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.cache.util.RedisUtil;
import com.fushionbaby.cms.controller.order.excel.MemberExcelReport;
import com.fushionbaby.cms.util.FileUploadTools;
import com.fushionbaby.cms.util.ImageConstantCms;
import com.fushionbaby.common.constants.AppConstant;
import com.fushionbaby.common.constants.ChannelConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.config.model.SysmgrSourceConfig;
import com.fushionbaby.config.service.SysmgrSourceConfigService;
import com.fushionbaby.member.dto.MemberDto;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberAddress;
import com.fushionbaby.member.model.MemberEmail;
import com.fushionbaby.member.model.MemberExtraInfo;
import com.fushionbaby.member.model.MemberTelephone;
import com.fushionbaby.member.service.MemberAddressService;
import com.fushionbaby.member.service.MemberEmailService;
import com.fushionbaby.member.service.MemberExtraInfoService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.member.service.MemberTelephoneService;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;

/**
 * 
 * @author King
 * 
 */
@Controller
@RequestMapping("/member")
public class MemberController {

	/** 记录日志 */
	private static final Log LOGGER = LogFactory.getLog(Member.class);
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private MemberTelephoneService<MemberTelephone> memberTelephoneService;
	@Autowired
	private MemberEmailService<MemberEmail> memberEmailService;
	@Autowired
	private MemberExtraInfoService<MemberExtraInfo> memberExtraInfoService;
	/** 短信表 */
	@Autowired
	private SmsService<Sms> smsService;
	/** 来源表 */
	@Autowired
	private SysmgrSourceConfigService<SysmgrSourceConfig> sourceService;

	@Autowired
	private MemberAddressService<MemberAddress> memberAddressService;

	// 分页查询
	@SuppressWarnings("unchecked")
	@RequestMapping("memberList")
	public String findList(MemberDto memberDto, BasePagination page,
			HttpServletRequest request, ModelMap model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = setParamByMemberDto(memberDto);
			page.setParams(params);
			// 分页对象
			page = memberService.getListPage(page);
			// 分页结果对象
			List<Member> memberList = (List<Member>) page.getResult();

			for (int i = 0; i < memberList.size(); i++) {
				if (StringUtils.isNotBlank(memberList.get(i).getSourceCode())) {
					memberList.get(i).setSourceName(
							this.sourceService.findByCode(memberList.get(i)
									.getSourceCode()));
				} else {
					memberList.get(i).setSourceName("未知来源");
				}

			}
			// 获取渠道map
			model.addAttribute("channelMap", ChannelConstant.getChannelArray());
			// 获取注册来源集合
			model.addAttribute("sourceList", sourceService.findAll());
			model.addAttribute("memberList", memberList);
			model.addAttribute("page", page);
			model.addAttribute("memberDto", memberDto);
			return "models/member/memberList";
		} catch (Exception e) {
			LOGGER.error("会员列表加载失败", e);
			return "";
		}
	}

	/** 添加 */
	@ResponseBody
	@RequestMapping("addMember")
	public JsonResponseModel add(
			@RequestParam("member_name") String member_name,
			@RequestParam(value = "member_password", defaultValue = "abc123") String member_password,
			HttpServletRequest request) {
		JsonResponseModel jsonModel = new JsonResponseModel();
		try {
			Member m = memberService.findByLoginName(member_name);
			if (m != null) {
				jsonModel.Fail("此手机号已注册,不能重复添加");
				return jsonModel;
			}

			Member member = memberService.addMember(member_name,
					member_password, SourceConstant.CMS_CODE, request);

			memberTelephoneService.addMemberPhone(member.getId(), member_name);

			memberExtraInfoService.addMemberExtraInfo(member.getId());

			sendSmsRegisterMessage(member_name, member_password);

			jsonModel.Success("会员添加成功!");
		} catch (Exception e) {
			jsonModel.Fail("会员添加失败!--进入到异常块了");
			LOGGER.error("会员添加失败", e);
			return jsonModel;
		}
		return jsonModel;
	}

	/**
	 * 前端更新会员状态请求处理
	 * 
	 * @param state
	 * @param memberId
	 * @param orderCode
	 */
	@ResponseBody
	@RequestMapping(value = "/changeMemberDisable", method = RequestMethod.POST)
	public Object change_member_disable(
			@RequestParam("memberId") Long memberId,
			@RequestParam("disabled") String disabled) {

		try {
			memberService.updateMemberDisable(memberId, disabled);
			removeDisableMember(memberId);
		} catch (Exception e) {
			LOGGER.error("响应前端页面更新会员状态失败，失败原因：" + e);
			return "error";
		}
		return "success";
	}

	/***
	 * 清除 缓存中 禁用账户的sid 
	 * @param memberId
	 */
	private void removeDisableMember(Long memberId) {
		Member member=this.memberService.findById(memberId);
		String key=member.getOpenId();
		if(StringUtils.isBlank(key))
			key=member.getLoginName();
		if(CommonConstant.YES.equals(member.getDisable())){
			LOGGER.info("该禁用账户的app查询 sid的key值 为："+AppConstant.APP_SID+key);
			
			String sid= new RedisUtil().get(AppConstant.APP_SID+key);
			String alabaoSid= new RedisUtil().get(AppConstant.APP_ALABAO_SID+key);
			LOGGER.info("现在时间为："+DateFormat.dateToString(new Date())+"开始执行 删除app中账户的sid 和 alabaoSid 。该禁用账户的app 中sid 为："+sid);
			LOGGER.info("清除禁用账号的sid 信息开始+++++++++++++++     begin     +++++++++++++++");
			if(StringUtils.isNotBlank(sid))
			   SessionCache.remove(sid);
			SessionCache.remove(AppConstant.APP_ALABAO_SID+key);
			LOGGER.info("清除禁用账号的sid 信息开始+++++++++++++++     end     +++++++++++++++");
			
			LOGGER.info("该禁用账户的app 中alabaosid 为："+AppConstant.APP_ALABAO_SID+sid);
			LOGGER.info("清除禁用账号的alabaosid 信息开始+++++++++++++++     begin     +++++++++++++++");
			if(StringUtils.isNotBlank(alabaoSid))
				SessionCache.remove(alabaoSid);
			SessionCache.remove(AppConstant.APP_ALABAO_SID+key);
			LOGGER.info("现在时间为："+DateFormat.dateToString(new Date())+" 删除app中账户的sid 和 alabaoSid 完成 。清除禁用账号的alabaosid 信息开始+++++++++++++++     end     +++++++++++++++");
			
		}
	}

	@SuppressWarnings("resource")
	@RequestMapping("importExcel")
	public String importExcel(
			@RequestParam(value = "member_excel", required = false) MultipartFile member_excel,
			String isNeedSendMessage, MemberDto memberDto, BasePagination page,
			HttpServletRequest request, ModelMap model) {
		try {
			
			Set<String> exitTelephoneCodes = new HashSet<String>();
			Set<String> errorTelephoneCodes = new HashSet<String>();
			
			int totalSize=0;
			
			if (member_excel != null && member_excel.getSize() > 0) {
				File addFile = FileUploadTools.addFile(member_excel,
						ImageConstantCms.PATH + "cms_upload_files" + "/"
								+ "import_member_excel");
				// 进行Excel解析
				// 1、 工作薄对象
				HSSFWorkbook hssfWorkbook = new HSSFWorkbook(
						new FileInputStream(new File(ImageConstantCms.PATH
								+ "cms_upload_files" + "/"
								+ "import_member_excel" + "/"
								+ addFile.getName())));
				// 解析工作薄
				hssfWorkbook.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK); // 避免空指针异常
				// 2、 获得Sheet
				HSSFSheet sheet = hssfWorkbook.getSheetAt(0); // 获得第一个sheet
				
				// 3、解析Sheet中每一行
				for (Row row : sheet) {
					totalSize++;
					// 进行解析 ， 每一行数据，对应 会员手机信息
					if (row.getRowNum() == 0) {// 第一行（表头，无需解析）
						continue;
					}
					// 从第二行开始解析
					String telephone = row.getCell(0).toString(); // 获得第一个单元格信息
					telephone = telephone.trim();
					
					Pattern pTelephone = Pattern.compile("^1\\d{10}");     
			        Matcher mTelephone = pTelephone.matcher(telephone);     
					if (!mTelephone.find()) {
						LOGGER.info("此手机号不合法" + telephone);
						errorTelephoneCodes.add(telephone);
						continue;
					}
					Member m = memberService.findByLoginName(telephone);
					// 是否存在该记录
					if (m != null) {
						LOGGER.info("此手机号已存在" + telephone);
						exitTelephoneCodes.add(telephone);
						continue;
					}

					Member member = memberService.addMember(telephone,
							"abc123", SourceConstant.CMS_CODE, request);

					memberTelephoneService.addMemberPhone(member.getId(),
							telephone);

					memberExtraInfoService.addMemberExtraInfo(member.getId());
					if (StringUtils.equalsIgnoreCase(isNeedSendMessage,
							CommonConstant.YES)) {
						sendSmsRegisterMessage(telephone, "abc123");
					}
					
				}
				
			}
			totalSize=totalSize-1;
			int successSize=totalSize-errorTelephoneCodes.size()-exitTelephoneCodes.size();
			model.addAttribute("totalSize", totalSize);
			model.addAttribute("successSize", successSize);
			model.addAttribute("errorTelephoneCodes", errorTelephoneCodes);
			model.addAttribute("exitTelephoneCodes", exitTelephoneCodes);
			model.addAttribute("info", "导入成功");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("info", "导入失败");
		}
		return "models/member/batch-success";
		
	}

	/***
	 * cms后台给用户发送的注册信息
	 * 
	 * @param telephone
	 * @param password
	 */
	private Object sendSmsRegisterMessage(String telephone, String password) {
		JsonResponseModel jsonModel = new JsonResponseModel();
		try {
			smsService.sendSmsRegisterMessage(telephone, password,
					SourceConstant.CMS_CODE);
		} catch (RemoteException e) {
			LOGGER.error("短信服务器响应超时", e);
			jsonModel.Fail("短信服务器响应超时");
			e.printStackTrace();
			return jsonModel;
		}
		return null;
	}

	/***
	 * 导出会员列表
	 * 
	 * @param orderDto
	 * @param response
	 * @param page
	 */
	@RequestMapping("export_excel_member_list")
	public void exportExcelOrderList(MemberDto memberDto,
			HttpServletResponse response, BasePagination page) {
		try {
			Map<String, String> params = setParamByMemberDto(memberDto);
			Map<String, Object> map = new HashMap<String, Object>();
			map.putAll(params);
			//map.put("exportExcel", 2);// 是导出excel,只用作,业务判断
			List<Member> memberList = memberService.getListPage(map);
			MemberExcelReport report = new MemberExcelReport();
			report.getReport("会员列表Excel", memberList, response);
		} catch (Exception e) {
			LOGGER.error("会员列表导出Excel失败", e);
		}
	}

	/***
	 * 设置查询参数
	 * 
	 * @param orderDto
	 * @return
	 */
	private Map<String, String> setParamByMemberDto(MemberDto memberDto) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("loginName", memberDto.getLoginName());
		params.put("telephone", memberDto.getTelephone());
		params.put("email", memberDto.getEmail());
		params.put("channelCode", memberDto.getChannelCode());
		params.put("sourceCode", memberDto.getSourceCode());
		params.put("createTimeFrom", memberDto.getCreateTimeFrom());
		params.put("createTimeTo", memberDto.getCreateTimeTo());
		params.put("disable",memberDto.getDisable());
		return params;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("memberDeleteList")
	public String findDeleteList(MemberDto memberDto, BasePagination page,
			HttpServletRequest request, ModelMap model) {
		try {
			BasePagination basePage = new BasePagination();
			if (ObjectUtils.notEqual(page, null)) {
				basePage = page;
			}
			Map<String, String> params = setParamByMemberDto(memberDto);
			basePage.setParams(params);
			// 分页对象
			BasePagination pageResult = memberService.getListPage(page);
			// 分页结果对象
			
			List<Member> memberList = (List<Member>) pageResult.getResult();

			for (int i = 0; i < memberList.size(); i++) {
				if (StringUtils.isNotBlank(memberList.get(i).getSourceCode())) {
					memberList.get(i).setSourceName(
							this.sourceService.findByCode(memberList.get(i)
									.getSourceCode()));
				} else {
					memberList.get(i).setSourceName("未知来源");
				}
				
				memberList.get(i).setRegisterType("isOther");
				String loginName=memberList.get(i).getLoginName();
				if(!"".equals(loginName)&&null!=loginName){
						memberList.get(i).setRegisterType(checkRegisterType(loginName));
				}
				
			}
			
			
			// 获取渠道map
			model.addAttribute("channelMap", ChannelConstant.getChannelArray());
			// 获取注册来源集合
			model.addAttribute("sourceList", sourceService.findAll());
			model.addAttribute("memberList", memberList);
			model.addAttribute("page", pageResult);
			model.addAttribute("memberDto", memberDto);
			return "models/member/memberDeleteList";
		} catch (Exception e) {
			LOGGER.error("会员删除列表加载失败", e);
			return "";
		}
	}
	
	/** 删除 */
	/***
	 * 会员删除的时候要删除跟该会员相关的所有信息（谨慎操作）
	 * 
	 * @param memberId
	 * @param registerType
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("deleteMember")
	public String deleteMember(@RequestParam("memberId") Long memberId,
			@RequestParam("registerType") String registerType,
			HttpServletRequest request) {
		try {
			memberService.deleteById(memberId);
			memberExtraInfoService.deleteByMemberId(memberId);
			if("isTelephone".equals(registerType)){
				memberTelephoneService.deleteByMemberId(memberId);
			}else if("isEmail".equals(registerType)){
				memberEmailService.deleteByMemberId(memberId);
			}
			return "success";
		} catch (Exception e) {
			LOGGER.error("会员删除失败", e);
			return "error";
		}
	}
	
	private String checkRegisterType(String telephoneOrEmail){
		Pattern pEmail = Pattern
				.compile("([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,}");
		Matcher mEmail = pEmail.matcher(telephoneOrEmail);
		
		Pattern pTelephone = Pattern.compile("^1\\d{10}");     
        Matcher mTelephone = pTelephone.matcher(telephoneOrEmail);     
		if (mEmail.find()) {
			return "isEmail";
		} else if (mTelephone.find()) {
			return "isTelephone";
		}else{
			return "isOther";
		}
	}
	
	/***
	 * 批量导入会员
	 * 
	 * @param skuBrandDto
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("showImportMember")
	public String showImportMember() {
		return "models/member/importMember";
	}
	
	@RequestMapping("showAddMember")
	public String selectSkuBrandList() {
		return "models/member/memberAdd";
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateMemberType", method = RequestMethod.POST)
	public Object updateOrderDetails(@RequestParam Long memberId,@RequestParam String memberType,
			RedirectAttributes redirectAttributes,ModelMap model) {
		JsonResponseModel jsonModel = new JsonResponseModel();
		try {
			Member orderBase = memberService.findById(memberId);
			orderBase.setMemberType(memberType);
			memberService.update(orderBase);
			jsonModel.Success("用户类型修改成功!");
		} catch (Exception e) {
			jsonModel.Fail("用户类型修改失败!");
		}
		return jsonModel;
	}
}
