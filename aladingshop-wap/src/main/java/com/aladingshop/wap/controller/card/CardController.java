/**
 * 
 */
package com.aladingshop.wap.controller.card;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import util.ImageConstantFacade;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.card.dto.res.YiDuoBaoCardResDto;
import com.aladingshop.card.dto.res.YiDuoBaoConfigResDto;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.core.model.OrderFinanceUser;
import com.fushionbaby.core.service.OrderFinanceUserService;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.facade.biz.common.yiduobao.YiDuoBaoCardFacade;

/**
 * @description 益多宝储蓄卡control
 * @author 孙涛
 * @date 2015年9月8日下午1:12:31
 */
@Controller
@RequestMapping("/card")
public class CardController {
	@Autowired
	private YiDuoBaoCardFacade yiDuoBaoCardFacade;

	@Autowired
	private UserFacade userFacade;

	@Autowired
	private OrderFinanceUserService<OrderFinanceUser> orderFinanceUserService;

	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	/** 如意宝账户 */
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAcountService;

	/***
	 * 跳转到阿拉丁卡首页
	 * 
	 * @param currPage
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/cardIndex")
	public ModelAndView cardIndexRequest(HttpServletRequest request) {
		String sid = CookieUtil.getCookieValue(request,
				CookieConstant.COOKIE_SID);
		if (StringUtils.isBlank(sid)) {
			return new ModelAndView("redirect:/login/index");
		}

		List<YiDuoBaoCardResDto> memberCards = yiDuoBaoCardFacade
				.getCardListByMember(sid);
		ModelAndView model = new ModelAndView();
		model.addObject("cardList", memberCards);
		return new ModelAndView("card/spe-card");
	}

	/**
	 * 初始化卡产品首页
	 * 
	 * @return
	 */
	@RequestMapping("/cardShow")
	public ModelAndView initCardIndexReq() {
		List<YiDuoBaoConfigResDto> resDtos = yiDuoBaoCardFacade.getAllConfig();
		ModelAndView model = new ModelAndView();
		model.addObject("imgPath", ImageConstantFacade.IMAGE_SERVER_ROOT_PATH);
		model.addObject("memberConfigList", resDtos);
		model.setViewName("card/spe-card-buy");
		return model;

	}

}
