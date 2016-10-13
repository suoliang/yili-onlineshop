package com.fushionbaby.cms.controller.store.settlement;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aladingshop.store.model.Store;
import com.aladingshop.store.model.StoreSponsorsApplySettle;
import com.aladingshop.store.model.StoreSponsorsBank;
import com.aladingshop.store.model.StoreSponsorsDailyDetails;
import com.aladingshop.store.model.StoreSponsorsSettlementDetails;
import com.aladingshop.store.service.StoreService;
import com.aladingshop.store.service.StoreSponsorsApplySettleService;
import com.aladingshop.store.service.StoreSponsorsBankService;
import com.aladingshop.store.service.StoreSponsorsDailyDetailsService;
import com.aladingshop.store.service.StoreSponsorsSettlementDetailsService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.StoreSponsorsApplySettleDto;
import com.fushionbaby.common.constants.SessionKeyConstant;
import com.fushionbaby.common.util.BasePagination;

/***
 * 门店结算申请
 * @author chenyingtao
 *
 */

@Controller
@RequestMapping("/storeSettleApply")
public class StoreSponsorsApplySettleController extends BaseController {
	
	@Autowired
	private StoreSponsorsApplySettleService<StoreSponsorsApplySettle> storeSponsorsApplySettleService;
	@Autowired
	private StoreSponsorsDailyDetailsService<StoreSponsorsDailyDetails> storeSponsorsDailyDetailsService;
	@Autowired
	private StoreSponsorsSettlementDetailsService<StoreSponsorsSettlementDetails> storeSponsorsSettlementDetailsService;
	@Autowired
	private StoreService<Store> storeService;
	@Autowired
	private StoreSponsorsBankService<StoreSponsorsBank> storeSponsorsBankService;
	
	private static final Log LOGGER = LogFactory.getLog(StoreSponsorsApplySettleController.class);
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("storeApplyList")
	public String getApplySettleList(StoreSponsorsApplySettleDto storeApplySettleDto,
									 HttpSession session,BasePagination page,ModelMap model){
		
		try {
			if(page == null){
				page = new BasePagination();
			}
			/**查询中需要用StoreCode及StoreNumber进行查询，当着2个条件不为空时，需要进行判断，查询的表中没有
			 *StoreNumber字段，需要在store表中根据StoreNumber查出store信息，
			 **/
			if(storeApplySettleDto.getStoreNumber() != null&&storeApplySettleDto.getStoreNumber() != ""){
				Store store = storeService.findByNumber(storeApplySettleDto.getStoreNumber());
				/**
				 * 如果StoreCode也不为空，且根据StoreNumber查询得到的StoreCode和传入的StoreCode不相等，择查询结果为空
				 * */
				if(storeApplySettleDto.getStoreCode() != null && !StringUtils.equals(store.getCode(), storeApplySettleDto.getStoreCode())){
					page.setTotal(0);
					model.addAttribute("storeApplySettleDto", storeApplySettleDto);
					model.addAttribute("page", page);
					return "models/store/settlement/storeApplyList";
				}else{
					storeApplySettleDto.setStoreCode(store.getCode());
				}
			}
			Map<String,String> param = new HashMap<String, String>();
			param.put("settlementStatus", "2");
			param.put("storeCode", storeApplySettleDto.getStoreCode());
			param.put("createTimeFrom", storeApplySettleDto.getCreateTimeFrom());
			param.put("createTimeTo", storeApplySettleDto.getCreateTimeTo());
			page.setParams(param);
			page = storeSponsorsApplySettleService.getListPage(page);
			List<StoreSponsorsApplySettle> storeSponsorsApplySettleList = (List<StoreSponsorsApplySettle>) page.getResult();
			
			List<StoreSponsorsApplySettleDto> list = new ArrayList<StoreSponsorsApplySettleDto>();
			for (StoreSponsorsApplySettle storeSponsorsApplySettle : storeSponsorsApplySettleList) {
				
				StoreSponsorsApplySettleDto storeSponsorsApplySettleDto = new StoreSponsorsApplySettleDto();
				
				String storeCode = storeSponsorsApplySettle.getStoreCode();
				Store store = storeService.findByCode(storeCode);
				
				//将查询出来的结果注入到Dto中
				storeSponsorsApplySettleDto.setId(storeSponsorsApplySettle.getId());
				storeSponsorsApplySettleDto.setStoreName(store.getName());
				storeSponsorsApplySettleDto.setStoreNumber(store.getNumber());
				storeSponsorsApplySettleDto.setStoreCode(storeCode);
				storeSponsorsApplySettleDto.setApplyTotalAmount(storeSponsorsApplySettle.getApplyTotalAmount());
				storeSponsorsApplySettleDto.setSettlementStatus(storeSponsorsApplySettle.getSettlementStatus());
				storeSponsorsApplySettleDto.setApplyTime(storeSponsorsApplySettle.getApplyTime());
				storeSponsorsApplySettleDto.setApplyNumber(storeSponsorsApplySettle.getApplyNumber());
				
				list.add(storeSponsorsApplySettleDto);
			}
			model.addAttribute("page", page);
			model.addAttribute("list", list);
			model.addAttribute("storeApplySettleDto", storeApplySettleDto);
			
			
		} catch (Exception e) {
			LOGGER.error("查询商户申请结算列表失败！", e);
			return "";
		}
		return "models/store/settlement/storeApplyList";
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
	
	
	@RequestMapping("applyDetails/{id}/{time}")
	public String applyDetails(@PathVariable(value="id")String id,ModelMap model){
		
		try {
			StoreSponsorsApplySettle storeSponsorsApplySettle =storeSponsorsApplySettleService.findById(id);
			String applyNumber = storeSponsorsApplySettle.getApplyNumber();
			
			Store store = storeService.findByCode(storeSponsorsApplySettle.getStoreCode());
			
			List<StoreSponsorsDailyDetails> list = storeSponsorsDailyDetailsService.findByApplyNumber(applyNumber);
			
			//订单总金额
			BigDecimal totalAmount = new BigDecimal(0);
			//结算总金额
			BigDecimal totalIncome = new BigDecimal(0);
			
			
			for (StoreSponsorsDailyDetails storeSponsorsDailyDetails : list) {
				totalAmount = storeSponsorsDailyDetails.getSettleOrderAmount().add(totalAmount);
				totalIncome = storeSponsorsDailyDetails.getRealIncomeAmount().add(totalIncome);
			}
			
			StoreSponsorsBank storeSponsorsBank = storeSponsorsBankService.findByStoreCode(storeSponsorsApplySettle.getStoreCode());
			
			model.addAttribute("list", list);
			model.addAttribute("store", store);
			model.addAttribute("applyNumber", applyNumber);
			model.addAttribute("settlementStatus", storeSponsorsApplySettle.getSettlementStatus());
			model.addAttribute("storeSponsorsBank", storeSponsorsBank);
			model.addAttribute("totalAmount", totalAmount);
			model.addAttribute("totalIncome", totalIncome);
		} catch (Exception e) {
			LOGGER.error("查询商户申请结算列表失败！", e);
			return "";
		}
		
		return "models/store/settlement/storeApplyDetails";
	}
	
	
	/**
	 * 平台支付后，更新各表的支付状态
	 * @param applyNumber
	 * @param session
	 * @return
	 */
	@RequestMapping("settlement/{applyNumber}/{time}")
	public String settlement(@PathVariable(value="applyNumber")String applyNumber,HttpSession session,ModelMap model){
		
		try {
			AuthUser auUser = (AuthUser) session.getAttribute(SessionKeyConstant.CMS_AUTH_USER);
			if(auUser == null){
				return "redirect:"+Global.getAdminPath()+"/login/index";
			}
			StoreSponsorsApplySettle storeSponsorsApplySettle = storeSponsorsApplySettleService.findByApplyNumber(applyNumber);
			
			storeSponsorsApplySettle.setSettlementStatus("3");
			storeSponsorsApplySettle.setUpdateId(auUser.getId());
			storeSponsorsApplySettleService.update(storeSponsorsApplySettle);
			
			List<StoreSponsorsDailyDetails> list = storeSponsorsDailyDetailsService.findByApplyNumber(applyNumber);
			for (StoreSponsorsDailyDetails storeSponsorsDailyDetails : list) {
				
				storeSponsorsDailyDetails.setSettlementStatus("3");
				storeSponsorsDailyDetailsService.update(storeSponsorsDailyDetails);
			
			}
			
			model.addAttribute("message", "支付完成！");
		} catch (Exception e) {
			LOGGER.error("更新申请结算列表支付状态失败！", e);
			
			model.addAttribute("message", "支付失败！");
			return "redirect:"+Global.getAdminPath()+"/storeSettleApply/storeApplyList";
		}
		return "models/store/settlement/storeApplyDetails";
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("storeSettlementList")
	public String settlementList(StoreSponsorsApplySettleDto storeApplySettleDto,HttpSession session,BasePagination page,ModelMap model){
		try {
			if(page == null){
				page = new BasePagination();
			}
			/**查询中需要用StoreCode及StoreNumber进行查询，当着2个条件不为空时，需要进行判断，查询的表中没有
			 *StoreNumber字段，需要在store表中根据StoreNumber查出store信息，
			 **/
			if(storeApplySettleDto.getStoreNumber() != null&&storeApplySettleDto.getStoreNumber() != ""){
				Store store = storeService.findByNumber(storeApplySettleDto.getStoreNumber());
				/**
				 * 如果StoreCode也不为空，且根据StoreNumber查询得到的StoreCode和传入的StoreCode不相等，择查询结果为空
				 * */
				if(storeApplySettleDto.getStoreCode() != null && !StringUtils.equals(store.getCode(), storeApplySettleDto.getStoreCode())){
					page.setTotal(0);
					model.addAttribute("storeApplySettleDto", storeApplySettleDto);
					model.addAttribute("page", page);
					return "models/store/settlement/storeSettlementList";
				}else{
					storeApplySettleDto.setStoreCode(store.getCode());
				}
			}
			Map<String,String> param = new HashMap<String, String>();
			param.put("settlementStatus", "3");
			param.put("storeCode", storeApplySettleDto.getStoreCode());
			param.put("createTimeFrom", storeApplySettleDto.getCreateTimeFrom());
			param.put("createTimeTo", storeApplySettleDto.getCreateTimeTo());
			page.setParams(param);
			page = storeSponsorsApplySettleService.getListPage(page);
			List<StoreSponsorsApplySettle> storeSponsorsApplySettleList = (List<StoreSponsorsApplySettle>) page.getResult();
			
			List<StoreSponsorsApplySettleDto> list = new ArrayList<StoreSponsorsApplySettleDto>();
			for (StoreSponsorsApplySettle storeSponsorsApplySettle : storeSponsorsApplySettleList) {
				
				StoreSponsorsApplySettleDto storeSponsorsApplySettleDto = new StoreSponsorsApplySettleDto();
				
				String storeCode = storeSponsorsApplySettle.getStoreCode();
				Store store = storeService.findByCode(storeCode);
				
				//将查询出来的结果注入到Dto中
				storeSponsorsApplySettleDto.setId(storeSponsorsApplySettle.getId());
				storeSponsorsApplySettleDto.setStoreName(store.getName());
				storeSponsorsApplySettleDto.setStoreNumber(store.getNumber());
				storeSponsorsApplySettleDto.setStoreCode(storeCode);
				storeSponsorsApplySettleDto.setApplyTotalAmount(storeSponsorsApplySettle.getApplyTotalAmount());
				storeSponsorsApplySettleDto.setSettlementStatus(storeSponsorsApplySettle.getSettlementStatus());
				storeSponsorsApplySettleDto.setApplyTime(storeSponsorsApplySettle.getApplyTime());
				storeSponsorsApplySettleDto.setApplyNumber(storeSponsorsApplySettle.getApplyNumber());
				
				list.add(storeSponsorsApplySettleDto);
			}
			model.addAttribute("page", page);
			model.addAttribute("list", list);
			model.addAttribute("storeApplySettleDto", storeApplySettleDto);
			
			
		} catch (Exception e) {
			LOGGER.error("查询商户已结算列表失败！", e);
			return "";
		}
		return "models/store/settlement/storeSettlementList";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("export_apply_settlement")
	public void exportApplySettlement(@RequestParam(value="createTimeFrom",defaultValue="")String createTimeFrom,
										 @RequestParam(value="createTimeTo",defaultValue="")String createTimeTo,
										BasePagination page,HttpServletResponse response, ModelMap model){
		
		try {
			if(page == null){
				page = new BasePagination();
			}
			Map<String,String> param = new HashMap<String, String>();
			param.put("settlementStatus", "2");
			param.put("createTimeFrom", createTimeFrom);
			param.put("createTimeTo", createTimeTo);
			page.setParams(param);
			page = storeSponsorsApplySettleService.getListPage(page);
			List<StoreSponsorsApplySettle> storeSponsorsApplySettleList = (List<StoreSponsorsApplySettle>) page.getResult();
			
			List<StoreSponsorsApplySettleDto> list = new ArrayList<StoreSponsorsApplySettleDto>();
			
			for (StoreSponsorsApplySettle storeSponsorsApplySettle : storeSponsorsApplySettleList) {
				
				StoreSponsorsApplySettleDto storeSponsorsApplySettleDto = new StoreSponsorsApplySettleDto();
				
				String storeCode = storeSponsorsApplySettle.getStoreCode();
				Store store = storeService.findByCode(storeCode);
				StoreSponsorsBank storeSponsorsBank = storeSponsorsBankService.findByStoreCode(storeSponsorsApplySettle.getStoreCode());
				
				//将查询出来的结果注入到Dto中
				storeSponsorsApplySettleDto.setId(storeSponsorsApplySettle.getId());
				storeSponsorsApplySettleDto.setStoreName(store.getName());
				storeSponsorsApplySettleDto.setStoreNumber(store.getNumber());
				storeSponsorsApplySettleDto.setStoreCode(storeCode);
				storeSponsorsApplySettleDto.setApplyTotalAmount(storeSponsorsApplySettle.getApplyTotalAmount());
				storeSponsorsApplySettleDto.setSettlementStatus("待结算");
				storeSponsorsApplySettleDto.setApplyTime(storeSponsorsApplySettle.getApplyTime());
				storeSponsorsApplySettleDto.setApplyNumber(storeSponsorsApplySettle.getApplyNumber());
				storeSponsorsApplySettleDto.setBankName(storeSponsorsBank.getBankName());
				storeSponsorsApplySettleDto.setBankBranchName(storeSponsorsBank.getBankBranchName());
				storeSponsorsApplySettleDto.setCardHolder(storeSponsorsBank.getCardHolder());
				storeSponsorsApplySettleDto.setCardNo(storeSponsorsBank.getCardNo());
				
				
				list.add(storeSponsorsApplySettleDto);
			}
			StoreSponsorsApplySettleExcelReport report = new StoreSponsorsApplySettleExcelReport();
			String dateString = new String(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).getBytes("ISO-8859-1"),"UTF-8");
			report.getReport(dateString, list, response);
		} catch (Exception e) {
			LOGGER.error("导出申请结算列表失败", e);
		} 
		
	}
	
}
