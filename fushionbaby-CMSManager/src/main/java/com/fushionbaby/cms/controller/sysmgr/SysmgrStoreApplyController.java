package com.fushionbaby.cms.controller.sysmgr;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.SysmgrStoreApplyDto;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.SessionKeyConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.sysmgr.model.SysmgrStoreApply;
import com.fushionbaby.sysmgr.service.SysmgrStoreApplyService;


@RequestMapping("/sysStoreApp")
@Controller
public class SysmgrStoreApplyController extends BaseController {
	
	private final static Log LOGGER = LogFactory.getLog(SysmgrStoreApplyController.class);
	
	@Autowired
	private SysmgrStoreApplyService<SysmgrStoreApply> sysmgrStoreApplyService;
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaConfigService;
	
	/***
	 * 开店申请列表
	 * @param sysmgrStoreApplyDto
	 * @param model
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("sysStoreAppList")
	public String sysStoreAppList(SysmgrStoreApplyDto sysmgrStoreApplyDto,ModelMap model,BasePagination page){
		
		try {
			if(null == page){
				page = new BasePagination();
			}
			Map<String,String> params = new HashMap<String,String>();
			params = this.setSearchParams(sysmgrStoreApplyDto);
			page.setParams(params);
			page = sysmgrStoreApplyService.getListPage(page);
			List<SysmgrStoreApply> list = (List<SysmgrStoreApply>) page.getResult();
			//根据获取的城市的areaCode字符串获取城市信息
			for (SysmgrStoreApply sysmgrStoreApply : list) {
				String areaCode = sysmgrStoreApply.getCity();
				String arr[] = areaCode.split(",");
				String city = "";
					for(int i=0; i < arr.length;i++){
						city = city + memberAreaConfigService.getNameByAreaCode(arr[i]);
					}
				sysmgrStoreApply.setCity(city);
			}
			
			Map<String,String> sourceArray = SourceConstant.getSourceArray();
			
			model.addAttribute("list", list);
			model.addAttribute("sourceArray", sourceArray);
			model.addAttribute("page", page);
			model.addAttribute("sysmgrStoreApplyDto", sysmgrStoreApplyDto);
			return "/models/store/apply/sysStoreAppList";
		} catch (Exception e) {
			LOGGER.error("查询商家注册列表失败" , e);
			return "";
		}
	}

	private Map<String, String> setSearchParams(SysmgrStoreApplyDto sysmgrStoreApplyDto) {
		Map<String,String> searchParams = new HashMap<String,String>();
		searchParams.put("city", sysmgrStoreApplyDto.getCity());
		searchParams.put("isDeal", sysmgrStoreApplyDto.getIsDeal());
		searchParams.put("phone", sysmgrStoreApplyDto.getPhone());
		searchParams.put("name", sysmgrStoreApplyDto.getName());
		searchParams.put("createTimeFrom", sysmgrStoreApplyDto.getCreateTimeFrom());
		searchParams.put("createTimeTo", sysmgrStoreApplyDto.getCreateTimeTo());
		return searchParams;
	}
	
	
	/***
	 * 更改是否联系过状态
	 * @param id
	 * @param session
	 * @return
	 */
	@RequestMapping("updateIsDeal/{id}/{time}")
	public String updateIsDeal(@PathVariable String id,HttpSession session){
		
		AuthUser user = (AuthUser) session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
		if(user == null){
			return "redirect:"+Global.getAdminPath()+"/login/index";
		}
		SysmgrStoreApply sysmgrStoreApply =sysmgrStoreApplyService.findById(Long.parseLong(id));
		sysmgrStoreApply.setIsDeal(CommonConstant.YES);
		sysmgrStoreApply.setDealName(user.getLoginName());
		sysmgrStoreApplyService.update(sysmgrStoreApply);
		return "redirect:"+Global.getAdminPath()+"/sysStoreApp/sysStoreAppList" ;
	}
	
	/***
	 * 开店申请单 处理
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/updateById/{id}/{time}")
	public String updateById(@PathVariable("id") Long id,Model model){
		SysmgrStoreApply storeApply=this.sysmgrStoreApplyService.findById(id);
		model.addAttribute("storeApply", storeApply);
		return "/models/store/apply/applyDeal";
	}
	
	/***
	 * 开店申请 处理更新
	 * @param id   操作的申请记录id
	 * @param memo  说明备注
	 * @param intention  开店的意向
	 * @param session   
	 * @return
	 */
	@RequestMapping("submitUpdate")
	@ResponseBody
	public Object update(@RequestParam("id")Long id,@RequestParam("memo") String memo,@RequestParam("intention") String intention,HttpSession session){
		try {
			SysmgrStoreApply apply = this.sysmgrStoreApplyService.findById(id);
			AuthUser user=CMSSessionUtils.getSessionUser(session);
			if(ObjectUtils.equals(apply, null)||ObjectUtils.equals(user, null))
					return false;
			apply.setDealName(user.getLoginName());
			apply.setDealTime(new Date());
			apply.setIsDeal(CommonConstant.YES);
			apply.setMemo(memo);
			apply.setIntention(intention);
			this.sysmgrStoreApplyService.update(apply);
			return true;
		} catch (Exception e) {
			LOGGER.error("开店申请处理异常  cms ：SysmgrStoreApplyController.java   update", e);
			return false;
		}
		
	}
	
	
}
