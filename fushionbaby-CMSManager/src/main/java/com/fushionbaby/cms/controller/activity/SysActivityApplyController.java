package com.fushionbaby.cms.controller.activity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.sysactivity.model.SysActivityActivitiesApply;
import com.fushionbaby.sysactivity.service.SysActivityActivitiesApplyService;
import com.fushionbaby.config.model.SysmgrSourceConfig;
import com.fushionbaby.config.service.SysmgrSourceConfigService;

/***
 * 
 * @author xupeijun
 * 
 */

@Controller
@RequestMapping("/activity_apply")
public class SysActivityApplyController {
	@Autowired
	private SysActivityActivitiesApplyService<SysActivityActivitiesApply> SysActivitiesApplyService;
	/** 注入来源 */
	@Autowired
	private SysmgrSourceConfigService<SysmgrSourceConfig> sourceService;


	private static final Log logger = LogFactory.getLog(SysActivityApplyController.class);

	@ResponseBody
	@RequestMapping("deleteById")
	public JsonResponseModel deleteById(@RequestParam("id") String id,
			HttpServletResponse response) {
		JsonResponseModel json = new JsonResponseModel();
		try {
			if (StringUtils.isNotBlank(id)) {
				this.SysActivitiesApplyService.deleteById(Long.valueOf(id));
				json.Success("活动删除成功");
			} else {
				json.Fail("选择活动不正确，删除失败");
			}
		} catch (Exception e) {
			json.Fail("活动删除失败");
			logger.error("活动删除失败", e);
		}
		return json;
	}

	/*
	 * 
	 * 
	 * 分页
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("findAll")
	public String findAll(
			@RequestParam(value = "activitiesName", defaultValue = "") String activitiesName,
			@RequestParam(value = "memberName", defaultValue = "") String memberName,
			BasePagination page, ModelMap model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			// 分页参数封装
			Map<String, String> params = new HashMap<String, String>();
			params.put("activitiesName", activitiesName.trim());
			params.put("memberName", memberName.trim());
			page.setParams(params);
			// 分页对象
			page = this.SysActivitiesApplyService.getListPage(page);
			// 分页结果
			/** 申请活动列表 */
			List<SysActivityActivitiesApply> SysActivitiesApplyList = (List<SysActivityActivitiesApply>) page
					.getResult();
			setSourceNameForApplyList(SysActivitiesApplyList);
			model.addAttribute("SysActivitiesApplyList", SysActivitiesApplyList);
			model.addAttribute("page", page);
			model.addAttribute("activitiesName", activitiesName);
			model.addAttribute("memberName", memberName);
			return "activity/activity_apply_list";
		} catch (Exception e) {
			logger.error("查询活动列表失败", e);
			return "authorization/login";
		}
	}

	
	/***
	 * 重构20150203
	 * 申请活动列表，设置上来源名称
	 * @param SysActivitiesApplyList
	 */
	private void setSourceNameForApplyList(List<SysActivityActivitiesApply> SysActivitiesApplyList){
		for (int i = 0; i < SysActivitiesApplyList.size(); i++) {
			if (StringUtils.isNotBlank(SysActivitiesApplyList.get(i).getSourceCode())) {
				String sourceName=getSourceNameBySourceCode(SysActivitiesApplyList.get(i));
				SysActivitiesApplyList.get(i).setSourceName(sourceName);
			}
            else {
				   SysActivitiesApplyList.get(i).setSourceName("未知来源");
		    	}
		}
		
	}
	/***
	 * 重构20150203
	 * 通过sourceCode得到来源名称
	 * @param sysActivityActivitiesApply
	 * @return
	 */
	private String getSourceNameBySourceCode(SysActivityActivitiesApply sysActivityActivitiesApply){
		String name=this.sourceService.findByCode(sysActivityActivitiesApply.getSourceCode());
		return name;
	}
	
	
	
	
	/***
	 * 跳转到修改的页面
	 * 
	 * @param id
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "edit")
	public String edit(@RequestParam("id") Long id, Map<String, Object> map) {
		try {
			if (id != null) {
				SysActivityActivitiesApply SysActivitiesApply = this.SysActivitiesApplyService
						.findById(id);
				map.put("SysActivitiesApply", SysActivitiesApply);
			}
			return "activity/activity_edit";
		} catch (Exception e) {
			logger.error("返回到修改活动申请页面出错", e);
			return "";
		}
	}

	/***
	 * 改变活动的是否联系状态
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("change_touch")
	public JsonResponseModel changeTouch(@RequestParam(value = "id") Long id) {
		JsonResponseModel jsonMode = new JsonResponseModel();
		try {
			this.SysActivitiesApplyService.changeIsTouch(id);
			jsonMode.Success("改变是否联系状态成功");
		} catch (Exception e) {
			jsonMode.Fail("修改是否联系状态失败");
			logger.error("改变是否联系状态失败", e);
		}
		return jsonMode;
	}

	/***
	 * 改变参加活动是否确认
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("change_ok")
	public JsonResponseModel changeOk(@RequestParam(value = "id") Long id) {
		JsonResponseModel jsonMode = new JsonResponseModel();
		try {
			this.SysActivitiesApplyService.changeIsOk(id);
			jsonMode.Success("改变状态成功");
		} catch (Exception e) {
			jsonMode.Fail("状态改变失败");
			logger.error("改变是否确认状态失败", e);
		}
		return jsonMode;
	}

}
