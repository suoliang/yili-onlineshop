package com.fushionbaby.cms.controller.order;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.model.SysmgrExpressConfig;
import com.fushionbaby.config.service.SysmgrExpressConfigService;
/***
 * 后台 快递物流公司的管理
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月4日上午11:55:57
 */
@Controller
@RequestMapping("/order")
public class OrderExpressConfigController  extends BaseController{


	private static final Log LOGGER = LogFactory.getLog(OrderExpressConfigController.class);

	private static final String PRE_="models/order/express/";
	
	/** 快递公司服务 */
    @Autowired
    private SysmgrExpressConfigService sysmgrExpressConfigService;
	/***
	 * 快递公司列表
	 * @param page
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("orderExpressList")
	public String findAll(BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			page.setParams(params);
			page = this.sysmgrExpressConfigService.getListPage(page);
			List<SysmgrExpressConfig> listFindAll = (List<SysmgrExpressConfig>) page.getResult();
			model.addAttribute("list", listFindAll);
			model.addAttribute("page", page);
			return PRE_+"orderExpressList";
		} catch (Exception e) {
			LOGGER.error("查询快递公司信息出错", e);
			return "";
		}
	}
	

}
