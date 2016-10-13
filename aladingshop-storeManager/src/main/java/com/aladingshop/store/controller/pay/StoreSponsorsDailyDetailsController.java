package com.aladingshop.store.controller.pay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aladingshop.store.auth.model.StoreAuthUser;
import com.aladingshop.store.config.Global;
import com.aladingshop.store.controller.BaseController;
import com.aladingshop.store.model.StoreSponsorsApplySettle;
import com.aladingshop.store.model.StoreSponsorsDailyDetails;
import com.aladingshop.store.model.StoreSponsorsSettlementDetails;
import com.aladingshop.store.service.StoreSponsorsApplySettleService;
import com.aladingshop.store.service.StoreSponsorsDailyDetailsService;
import com.aladingshop.store.service.StoreSponsorsSettlementDetailsService;
import com.fushionbaby.common.constants.SessionKeyConstant;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.GetSerialNumUtil;

/**
 * 商户待结算业务
 * @author chenyingtao
 *
 *
 */
@Controller
@RequestMapping("/storeDailyInfo")
public class StoreSponsorsDailyDetailsController extends BaseController {
	
	@Autowired
	private StoreSponsorsDailyDetailsService<StoreSponsorsDailyDetails> storeSponsorsDailyDetailsService;
	@Autowired
	private StoreSponsorsSettlementDetailsService<StoreSponsorsSettlementDetails> storeSponsorsSettlementDetailsService;
	@Autowired
	private StoreSponsorsApplySettleService<StoreSponsorsApplySettle> storeSponsorsApplySettleService;
	
	private static final Log LOGGER = LogFactory.getLog(StoreSponsorsDailyDetailsController.class);
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("storeDailyList")
	public String storeDailyList(HttpSession session,BasePagination page,ModelMap model){
			try {
				if(page == null){
					page = new BasePagination();
				}
				//通过session拿到商户的storeCode
				StoreAuthUser authUser = (StoreAuthUser) session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
				if(authUser == null){
					return "models/authorization/login";
				}
				
				String storeCode = authUser.getStoreCode();
				Map<String,String> params = new HashMap<String, String>();
				params.put("storeCode", storeCode);
				params.put("settlementStatus", "1");
						
				
				page.setParams(params);
				page = storeSponsorsDailyDetailsService.getListPage(page);
				List<StoreSponsorsDailyDetails> list = (List<StoreSponsorsDailyDetails>) page.getResult();
				
				model.addAttribute("page", page);
				model.addAttribute("list", list);
			} catch (Exception e) {
				LOGGER.error("查询商户结算列表失败！", e);
				return "";
			}
			
		return "models/pay/storeDailyTotal";
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("dailyOrderList/{id}/{time}")
	public String dailyOrderDetails(@PathVariable(value="id")Long id,BasePagination page,ModelMap model){
		
		try {
			StoreSponsorsDailyDetails storeSponsorsDailyDetails=storeSponsorsDailyDetailsService.findById(id);
			String dailyNumber = storeSponsorsDailyDetails.getDailyNumber();
			String storeCode = storeSponsorsDailyDetails.getStoreCode();
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("dailyNumber", dailyNumber);
			param.put("storeCode", storeCode);
			if(page == null){
				page = new BasePagination();
			}
			page.setParams(param);
			page = storeSponsorsSettlementDetailsService.getListPage(page);
			List<StoreSponsorsSettlementDetails> list = (List<StoreSponsorsSettlementDetails>) page.getResult();
			
			Map<String, String> paymentType = PaymentTypeEnum.getPaymentTypeMap();
			
			model.addAttribute("id", id);
			model.addAttribute("time", new Date().getTime());
			model.addAttribute("list", list);
			model.addAttribute("page", page);
			model.addAttribute("paymentType", paymentType);
			
		} catch (Exception e) {
			LOGGER.error("查询商户结算列表失败！", e);
			return "";
		}
		return "models/pay/storeDailyOrders";
	}
	
	@RequestMapping("storeApplySettlement/{dailyNumber}")
	public String storeApplySettlement(@PathVariable(value="dailyNumber")String dailyNumber,HttpSession session){
		
		try {
			StoreAuthUser auUser = (StoreAuthUser) session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
			if(auUser == null){
				return "models/authorization/login";
			}
			String applyNumber = GetSerialNumUtil.generateSerialNum();
			BigDecimal applyTotalAmount = new BigDecimal(0);
			String storeCode = "";
			
			String[] arr = dailyNumber.split(",");
			for (int i = 1; i < arr.length; i++) {
				StoreSponsorsDailyDetails storeSponsorsDailyDetails = storeSponsorsDailyDetailsService.findByDailyNumber(arr[i]);
				storeSponsorsDailyDetails.setApplyNumber(applyNumber);
				//更新结算状态为“申请中”
				storeSponsorsDailyDetails.setSettlementStatus("2");
				storeSponsorsDailyDetails.setUpdateId(auUser.getId());
				storeSponsorsDailyDetailsService.update(storeSponsorsDailyDetails);
				//将申请的项的金额相加得到申请的总金额
				applyTotalAmount = storeSponsorsDailyDetails.getRealIncomeAmount().add(applyTotalAmount);
				storeCode = storeSponsorsDailyDetails.getStoreCode();
			}
			
			StoreSponsorsApplySettle storeSponsorsApplySettle = new StoreSponsorsApplySettle();
			storeSponsorsApplySettle.setStoreCode(storeCode);
			storeSponsorsApplySettle.setApplyNumber(applyNumber);
			storeSponsorsApplySettle.setSettlementStatus("2");
			storeSponsorsApplySettle.setApplyTotalAmount(applyTotalAmount);
			storeSponsorsApplySettleService.add(storeSponsorsApplySettle);
		} catch (Exception e) {
			LOGGER.error("查询商户结算列表失败！", e);
		}
		
		return "redirect:"+Global.getAdminPath()+"/storeDailyInfo/storeDailyList";
	}
	
	
	/**
	 * 已结算列表
	 * @param session
	 * @param page
	 * @param model
	 * @return
	 */
	 

}
