package com.fushionbaby.wap.controller.order;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.OrderConstant;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.order.GotoOrderDto;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.core.model.SoShoppingCartSkuUser;
import com.fushionbaby.core.service.SoShoppingCartSkuWebUserService;
import com.fushionbaby.facade.biz.common.member.UserFacade;
import com.fushionbaby.facade.biz.common.order.GotoOrderFacade;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.service.FindSkuService;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.wap.controller.GotoAndCreteOrderCommon;

@Controller
@RequestMapping("/order")
public class GotoOrderController extends GotoAndCreteOrderCommon{
	

	private static final Log logg=LogFactory.getLog(GotoOrderController.class);

	@Autowired
	private SoShoppingCartSkuWebUserService<SoShoppingCartSkuUser> soShoppingCartSkuWebService;
	@Autowired
	private FindSkuService findSkuService;
	@Autowired
	private GotoOrderFacade gotoOrderFacade;
	@Autowired
	private UserFacade userFacade;
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	
	/**
	 * gotoOrder方法之前校验
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("goto_order_check")
	public Object gotoOrderCheck(HttpServletRequest request) {
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			boolean checkUserLogin = userFacade.checkUserLogin(sid);
			if(!checkUserLogin){
				return Jsonp.noLoginError("请先登录!");
			}
			
			UserDto user = userFacade.getLatestUserBySid(sid);
			
			List<SoShoppingCartSkuUser> cartItemList  = soShoppingCartSkuWebService.findSelectedCartSkuByMemberId(user.getMember_id());
			if (CollectionUtils.isEmpty(cartItemList)) {
				return Jsonp.paramError("亲!请选择您要购买的商品!在结算!");
			}
			Object checkCartItems = checkCartItemsSkuIsExist(cartItemList);
			if(checkCartItems != null){
				return checkCartItems;
			}
		} catch (Exception e) {
			logg.error("生成订单之前的订单校验出错", e);
			return Jsonp.error();
		}
		return Jsonp.success();
	}

	private Object checkCartItemsSkuIsExist(List<SoShoppingCartSkuUser> cartItemList) {
		for (SoShoppingCartSkuUser cartSkuItem : cartItemList) {
			Sku skuEntry = findSkuService.queryBySkuCode(cartSkuItem.getSkuCode());// 根据商品code拿到对应的商品
			if (skuEntry == null) {
				// return Jsonp.paramError("亲!您购买的商品在系统没有找到!");
				return Jsonp.error();
			}
		}
		return null;
	}

	/**
	 * 为下订单页面准备数据 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("goto_order")
	public String gotoOrder(ModelMap model, HttpServletRequest request) {
		GotoOrderDto gotoOrderDto = new GotoOrderDto();
		String sid = "";
		try {
			this.checkUserLogin(request);
			UserDto user = this.getUser(request);
			this.checkCart(user);
			List<SoShoppingCartSkuUser> cartItemList = soShoppingCartSkuWebService.findSelectedCartSkuByMemberId(user.getMember_id());
			this.checkCartItemsSkuIsExist(cartItemList);
			gotoOrderDto = gotoOrderFacade.initGotoOrderDto(user, cartItemList);
			sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			model.put("sid", sid);
		} catch (Exception e) {
              logg.error("下订单准备数据出错", e);
		}
		model.put("gotoOrderDto", gotoOrderDto);
		model.put("addressList", gotoOrderFacade.addressList(sid));
		model.put("payZfbJsdzUrl", sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.PAYZFBJSDZURL).getValue());
		model.put("payYlUrl", sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.PAYYLURL).getValue());
		model.put("gotoType", OrderConstant.GOTO_CART_PAYMENT);
		return "checkout-list";
	}

	
	
}
