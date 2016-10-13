package com.aladingshop.store.controller.pay;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.aladingshop.store.controller.BaseController;
import com.aladingshop.store.dto.StoreSponsorsApplySettleDto;
import com.aladingshop.store.dto.StoreSponsorsDailyDetailsDto;
import com.aladingshop.store.model.StoreSponsorsApplySettle;
import com.aladingshop.store.model.StoreSponsorsDailyDetails;
import com.aladingshop.store.model.StoreSponsorsSettlementDetails;
import com.aladingshop.store.service.StoreSponsorsApplySettleService;
import com.aladingshop.store.service.StoreSponsorsDailyDetailsService;
import com.aladingshop.store.service.StoreSponsorsSettlementDetailsService;
import com.fushionbaby.common.constants.SessionKeyConstant;
import com.fushionbaby.common.util.BasePagination;
/***
 * 结算申请
 * @author chenyingtao
 *
 *
 */
@Controller
@RequestMapping("/storeSponorsApplySettle")
public class StoreSponsorsApplySettleController extends BaseController {
	
	@Autowired
	private StoreSponsorsApplySettleService<StoreSponsorsApplySettle> storeSponsorsApplySettleService;
	@Autowired
	private StoreSponsorsDailyDetailsService<StoreSponsorsDailyDetails> storeSponsorsDailyDetailsService;
	@Autowired
	private StoreSponsorsSettlementDetailsService<StoreSponsorsSettlementDetails> storeSponsorsSettlementDetailsService;
	
	private static final Log LOGGER = LogFactory.getLog(StoreSponsorsApplySettleController.class);
	
	@SuppressWarnings("unchecked")
	@RequestMapping("storeApplyList")
	public String getApplySettleList(StoreSponsorsApplySettleDto storeSponsorsApplySettleDto,HttpSession session,
			BasePagination page,ModelMap model){
		
		try {
			if(page == null){
				page = new BasePagination();
			}
			//通过session拿到商户的storeCode
			StoreAuthUser authUser = (StoreAuthUser) session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
			if(authUser == null){
				return "models/authorization/login";
			}
			storeSponsorsApplySettleDto.setStoreCode(authUser.getStoreCode());
			storeSponsorsApplySettleDto.setSettlementStatus("2");
			
			Map<String,String> param = this.setSearchParams(storeSponsorsApplySettleDto);
			page.setParams(param);
			
			page = storeSponsorsApplySettleService.getListPage(page);
			List<StoreSponsorsApplySettle> storeSponsorsApplySettleList = (List<StoreSponsorsApplySettle>) page.getResult();
			List<StoreSponsorsApplySettleDto> list = setResultParams(storeSponsorsApplySettleList);
			
			model.addAttribute("list", list);
			model.addAttribute("page", page);
			
		} catch (Exception e) {
			LOGGER.error("查询商户申请结算列表失败！", e);
			return "";
		}
		
		return "models/pay/storeApplyList";
	}
	
	
	public Map<String,String> setSearchParams(StoreSponsorsApplySettleDto storeSponsorsApplySettleDto){
		Map<String,String> param = new HashMap<String, String>();
		param.put("storeCode", storeSponsorsApplySettleDto.getStoreCode());
		param.put("applyNumber", storeSponsorsApplySettleDto.getApplyNumber());
		param.put("settlementStatus", storeSponsorsApplySettleDto.getSettlementStatus());
		param.put("createTimeFrom", storeSponsorsApplySettleDto.getCreateTimeFrom());
		param.put("createTimeTo", storeSponsorsApplySettleDto.getCreateTimeTo());
		return param;
	}
	
	/**将查询出来的结果放入到Dto中*/
	public List<StoreSponsorsApplySettleDto> setResultParams(List<StoreSponsorsApplySettle> list){
		List<StoreSponsorsApplySettleDto> result = new ArrayList<StoreSponsorsApplySettleDto>();
		for (StoreSponsorsApplySettle storeSponsorsApplySettle : list) {
			StoreSponsorsApplySettleDto storeSponsorsApplySettleDto = new StoreSponsorsApplySettleDto();
			
			String applyNumber =storeSponsorsApplySettle.getApplyNumber();
			Integer applyDayNumber = storeSponsorsDailyDetailsService.getApplyDayNumber(applyNumber);
			storeSponsorsApplySettleDto.setApplyDayNumber(applyDayNumber);
			
			storeSponsorsApplySettleDto.setApplyNumber(storeSponsorsApplySettle.getApplyNumber());
			storeSponsorsApplySettleDto.setApplyTime(storeSponsorsApplySettle.getApplyTime());
			storeSponsorsApplySettleDto.setApplyTotalAmount(storeSponsorsApplySettle.getApplyTotalAmount());
			storeSponsorsApplySettleDto.setSettlementStatus(storeSponsorsApplySettle.getSettlementStatus());
			storeSponsorsApplySettleDto.setSettleMethod(storeSponsorsApplySettle.getSettleMethod());
			
			result.add(storeSponsorsApplySettleDto);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("storeApplyDetails/{applyNumber}/{time}")
	public String storeApplyDetails(@PathVariable(value="applyNumber")String applyNumber,
			BasePagination page,ModelMap model){
		
		try {
			if(page == null){
				page = new BasePagination();
			}
			
			Map<String,String> param = new HashMap<String, String>();
			param.put("applyNumber", applyNumber);
			page.setParams(param);
			page = storeSponsorsDailyDetailsService.getListPage(page);
			
			List<StoreSponsorsDailyDetails> list = (List<StoreSponsorsDailyDetails>) page.getResult();
			model.put("applyNumber", applyNumber);
			model.put("time", new Date().getTime());
			model.put("list", list);
			model.put("page", page);
		} catch (Exception e) {
			LOGGER.error("查询商户申请结算列表明细失败！", e);
			return "";
		}
		
		return "models/pay/storeApplyDetails";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("storeApplyOrderInfo/{dailyNumber}/{time}")
	public String storeApplyOrderDetails(@PathVariable(value="dailyNumber")String dailyNumber,
			BasePagination page,ModelMap model){
		
		if(page == null){
			page = new BasePagination();
		}
		
		Map<String,String> param = new HashMap<String, String>();
		param.put("dailyNumber", dailyNumber);
		page.setParams(param);
		page = storeSponsorsSettlementDetailsService.getListPage(page);
		List<StoreSponsorsSettlementDetails> list = (List<StoreSponsorsSettlementDetails>) page.getResult();
		
		model.addAttribute("list", list);
		model.addAttribute("dailyNumber", dailyNumber);
		model.addAttribute("time", new Date().getTime());
		model.addAttribute("page", page);
		
		return "models/pay/storeApplyDailyOrders";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("storeSettlementList")
	public String storeSettlementList(StoreSponsorsDailyDetailsDto storeSponsorsDailyDetailsDto,HttpSession session,BasePagination page,ModelMap model){
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
			params.put("createTimeFrom", storeSponsorsDailyDetailsDto.getCreateTimeFrom());
			params.put("createTimeTo", storeSponsorsDailyDetailsDto.getCreateTimeTo());
			params.put("settlementStatus", "3");
					
			
			page.setParams(params);
			page = storeSponsorsDailyDetailsService.getListPage(page);
			List<StoreSponsorsDailyDetails> storeSponsorsDailyDetailslist = (List<StoreSponsorsDailyDetails>) page.getResult();
			
			List<StoreSponsorsDailyDetailsDto> list = new ArrayList<StoreSponsorsDailyDetailsDto>();
			
			//得出总的订单数以及结算的总金额
			Integer totalOrderNum = 0;
			BigDecimal totalAmount = new BigDecimal(0);
			for (StoreSponsorsDailyDetails storeSponsorsDailyDetails : storeSponsorsDailyDetailslist) {
				
				StoreSponsorsDailyDetailsDto dailyDetails = new StoreSponsorsDailyDetailsDto();
				//通过ApplyNumber拿到相关的结算信息，将结算信息注入到Dto中
				StoreSponsorsApplySettle storeSponsorsApplySettle = storeSponsorsApplySettleService.findByApplyNumber(storeSponsorsDailyDetails.getApplyNumber());
				dailyDetails.setSettleMethod(storeSponsorsApplySettle.getSettleMethod());
				dailyDetails.setSettlementTime(storeSponsorsApplySettle.getSettlementTime());
				
				dailyDetails.setId(storeSponsorsDailyDetails.getId());
				dailyDetails.setSettlePeriod(storeSponsorsDailyDetails.getSettlePeriod());
				dailyDetails.setRealIncomeAmount(storeSponsorsDailyDetails.getRealIncomeAmount());
				dailyDetails.setSettleOrderCount(storeSponsorsDailyDetails.getSettleOrderCount());
				
				totalOrderNum = totalOrderNum + storeSponsorsDailyDetails.getSettleOrderCount();
				totalAmount = storeSponsorsDailyDetails.getRealIncomeAmount().add(totalAmount);
				
				list.add(dailyDetails);
			}
			
			model.addAttribute("totalOrderNum", totalOrderNum);
			model.addAttribute("totalAmount", totalAmount);
			model.addAttribute("page", page);
			model.addAttribute("list", list);
		} catch (Exception e) {
			LOGGER.error("查询商户已结算列表失败！", e);
			return "";
		}
		
		return "models/pay/storeSettlementList";
	}
}
