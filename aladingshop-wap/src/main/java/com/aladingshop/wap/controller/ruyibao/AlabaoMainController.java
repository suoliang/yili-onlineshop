package com.aladingshop.wap.controller.ruyibao;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.vo.MainAlabaoDto;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.NumberFormatUtil;

/**
 * @description 阿拉宝主信息展示
 * @author 索亮
 * @date 2015年9月9日下午2:57:22
 */
@Controller
@RequestMapping("/ruyibao")
public class AlabaoMainController {
	private static final Log LOGGER = LogFactory
			.getLog(AlabaoMainController.class);

	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;

	/***
	 * @param alabaoSid
	 * @return
	 */
	@RequestMapping("mainShow")
	public ModelAndView mainShow(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("error");
		try {
			String sid = CookieUtil.getCookieValue(request,
					CookieConstant.COOKIE_SID);
			if (ObjectUtils.equals(SessionCache.get(sid), null)) {
				return new ModelAndView("redirect:/login/index");
			}
			
			String alabaoSid = CookieUtil.getCookieValue(request,
					CookieConstant.ALABAO_SID);
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache
					.get(alabaoSid);
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return new ModelAndView("redirect:/ruyibao/index");
			}
			AlabaoAccount alabaoAccount = alabaoAccountService
					.findById(alabaoUserDto.getAlabaoId());
			if (ObjectUtils.equals(alabaoAccount, null)) {
				modelAndView.addObject("errorMsg", "请求出错，请重试");
				return modelAndView;
			}
			MainAlabaoDto mainAlabaoDto = new MainAlabaoDto();
			mainAlabaoDto.setBalance(NumberFormatUtil
					.numberFormat(alabaoAccount.getBalance()));
			mainAlabaoDto.setYesterdayIncome(NumberFormatUtil
					.numberFormat(alabaoAccount.getYesterdayIncome()));
			mainAlabaoDto.setTotalIncome(NumberFormatUtil
					.numberFormat(alabaoAccount.getTotalIncome()));
			BeanNullConverUtil.nullConver(mainAlabaoDto);

			modelAndView.setViewName("ruyibao/spe-ruyi-main");
			modelAndView.addObject("result", mainAlabaoDto);
			return modelAndView;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("主显出错", e);
			modelAndView.addObject("errorMsg", "请求出错，请重试");
			return modelAndView;
		}
	}

}
