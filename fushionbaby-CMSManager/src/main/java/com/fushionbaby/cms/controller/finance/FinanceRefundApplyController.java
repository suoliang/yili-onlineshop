package com.fushionbaby.cms.controller.finance;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aladingshop.finance.model.FinanceRefundApply;
import com.aladingshop.finance.service.FinanceRefundApplyService;
import com.fushionbaby.auth.model.AuthRolePrivilege;
import com.fushionbaby.auth.service.AuthRolePrivilegeService;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.common.util.BasePagination;
/***
 * 退款申请
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月7日上午9:52:33
 */
@Controller
@RequestMapping("/finance")
public class FinanceRefundApplyController  extends BaseController{

	/**退款申请*/
	@Autowired
	private FinanceRefundApplyService financeReundApplyService;


	private static final Log logger = LogFactory.getLog(FinanceRefundApplyController.class);

	private static final String PRE_="models/finance/";
	
	@Autowired
	private AuthRolePrivilegeService<AuthRolePrivilege> authRolePrivilegeService;
	/***
	 * 权限的列表
	 * 
	 * @param name
	 * @param page
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("refundApplyList")
	public String findAll(BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			page.setParams(params);
			page = this.financeReundApplyService.getListPage(page);
			List<FinanceRefundApply> listFindAll = (List<FinanceRefundApply>) page.getResult();
			model.addAttribute("list", listFindAll);
			model.addAttribute("page", page);
			return PRE_+"refundApplyList";
		} catch (Exception e) {
			logger.error("查询权限列表失败", e);
			return "";
		}
	}
	
}
