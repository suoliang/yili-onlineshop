package com.fushionbaby.cms.controller.authorization;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fushionbaby.auth.model.AuthUserOrderRelation;
import com.fushionbaby.auth.service.AuthUserOrderRelationService;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.AuthUserOrderRelationDto;
import com.fushionbaby.common.util.BasePagination;

/***
 * 
 * @author chenyingtao
 *
 */


@Controller
@RequestMapping("/userOrder")
public class AuUserOrderRelationController extends BaseController{
	
	@Autowired
	private AuthUserOrderRelationService<AuthUserOrderRelation> authUserOrderRelationService;
	
	/** 日志 */
	private static final Log logger = LogFactory.getLog(AuUserOrderRelationController.class);
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("findUserOrderRelation")
	public String findUserOrderRelationByUserNameAndOrderCode(AuthUserOrderRelationDto authUserOrderRelationDto,
			BasePagination page,ModelMap model){
		

		try {
				if(page == null){
					page = new BasePagination();
			}
			
			Map<String, String> params = this.setParamByAuthUserOrderRelation(authUserOrderRelationDto);
			page.setParams(params);
			page = authUserOrderRelationService.getListPage(page);
			
			List<AuthUserOrderRelation> authUserOrderRelationList = (List<AuthUserOrderRelation>) page.getResult();
			
			model.addAttribute("authUserOrderRelationList", authUserOrderRelationList);
			model.addAttribute("page", page);
			
			return "models/member/authUserOrderRelationList";
		} catch (Exception e) {
			logger.error("会员订单关系表加载失败", e);
			return "";
		}
		
		
	}

	private Map<String, String> setParamByAuthUserOrderRelation(AuthUserOrderRelationDto authUserOrderRelationDto) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", authUserOrderRelationDto.getUserName());
		params.put("orderCode", authUserOrderRelationDto.getOrderCode());
		return params;
	}
	
}
