package com.fushionbaby.cms.controller.sysmgr;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.model.SysmgrSfFreightConfig;
import com.fushionbaby.config.service.SysmgrSfFreightConfigService;

/**
 * @author xpf
 * 
 */

@Controller
@RequestMapping("/sfFreight")
public class SysmgrSfFreightController {

	@Autowired
	private SysmgrSfFreightConfigService<SysmgrSfFreightConfig> sysmgrSfFreightConfigService;
	/**
	 * 分页查询功能
	 * 
	 * @param productCode
	 *            产品编号
	 * @param model
	 * @param page
	 *            分页
	 * @return 显示页面
	 */
	@RequestMapping("/findAll")
	public String query(ModelMap model, BasePagination page) {
		if (page == null) {
			page = new BasePagination();
		}
		// 分页参数封装
		// 分页对象
		page = sysmgrSfFreightConfigService.getListPage(page);
		model.addAttribute("page", page);
		return "models/sysmgr/sfFreightList";
	}

	/**
	 * 修改产品图片显示顺序
	 * 
	 * @param id
	 * @param sortOrder  产品图片显示顺序          
	 * @return 操作结果
	 */
	@RequestMapping("/updateAmount")
	@ResponseBody
	public JsonResponseModel updateAmount(
			@RequestParam(value = "areaCode") String areaCode,
			@RequestParam(value = "amount", defaultValue = "0") BigDecimal amount) {
		JsonResponseModel jsonModel = new JsonResponseModel();

		try {
			SysmgrSfFreightConfig sysmgrSfFreightConfig=new SysmgrSfFreightConfig();
			sysmgrSfFreightConfig.setAmount(amount);
			sysmgrSfFreightConfig.setAreaCode(areaCode);
			this.sysmgrSfFreightConfigService.updateByAreaCode(sysmgrSfFreightConfig);
			jsonModel.Success("运费配置修改成功!");
		} catch (Exception e) {
			jsonModel.Fail("运费配置修改失败!");
		}
		return jsonModel;
	}
}
