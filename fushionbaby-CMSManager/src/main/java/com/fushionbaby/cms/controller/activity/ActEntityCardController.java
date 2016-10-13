package com.fushionbaby.cms.controller.activity; 

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.store.model.Store;
import com.aladingshop.store.service.StoreService;
import com.fushionbaby.act.activity.dto.EntityCardQueryDto;
import com.fushionbaby.act.activity.dto.EntityCardUseRecordDto;
import com.fushionbaby.act.activity.model.ActEntityCard;
import com.fushionbaby.act.activity.model.ActEntityCardConfig;
import com.fushionbaby.act.activity.model.ActEntityCardUseRecord;
import com.fushionbaby.act.activity.service.ActEntityCardConfigService;
import com.fushionbaby.act.activity.service.ActEntityCardService;
import com.fushionbaby.act.activity.service.ActEntityCardUseRecordService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.controller.activity.excel.EntityUseRecordPrintExcelReport;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.common.constants.CmsConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;

@Controller
@RequestMapping("/actEntityCard")
public class ActEntityCardController extends BaseController { 


	@Autowired
	private ActEntityCardService<ActEntityCard> actEntityCardService;
	
	@Autowired
	private ActEntityCardConfigService<ActEntityCardConfig> actEntityCardConfigService;
	
	@Autowired
	private ActEntityCardUseRecordService<ActEntityCardUseRecord> actEntityCardUseRecordService;

	/**门店*/
	@Autowired
	private StoreService<Store> storeService;
	
	private static final Log logger = LogFactory.getLog(ActEntityCardController.class);
	
	private static final String PRE_="models/entity/"; 
	
	/**列表页*/
	private static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/actEntityCard/actEntityCardList";
	
	@SuppressWarnings("unchecked")
	@RequestMapping("actEntityCardList")
	public String findAll(EntityCardQueryDto queryDto,BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("configId", queryDto.getConfigId());
			params.put("status", queryDto.getStatus());
			params.put("cardNo", queryDto.getCardNo());
			params.put("faceMoney", queryDto.getFaceMoney());
			params.put("storeCode", queryDto.getStoreCode());
			page.setParams(params);
			page = this.actEntityCardService.getListPage(page);
			List<ActEntityCard> listFindAlls = (List<ActEntityCard>) page.getResult();
			for (ActEntityCard actEntityCard : listFindAlls) {
				ActEntityCardConfig actEntityCardConfig = actEntityCardConfigService.findById(actEntityCard.getConfigId());
				   actEntityCard.setConfigName(actEntityCardConfig==null?"":actEntityCardConfig.getName());
				Store store=this.storeService.findByCode(actEntityCard.getStoreCode());
				   actEntityCard.setStoreName(store==null?"":store.getName());
			}
			model.addAttribute("list", listFindAlls);
			model.addAttribute("page", page);
		    model.addAttribute("queryDto",queryDto);
		    model.addAttribute("storeList", this.storeService.findAll());
		    List<ActEntityCardConfig> configList=this.actEntityCardConfigService.findAll();
		    model.addAttribute("configList", configList);
			return PRE_+"actEntityCardList";
		} catch (Exception e) {
			logger.error("查询列表失败", e);
			return "";
		}
	}
	
	/***
	 * 跳转到实体卡生成页面
	 * xupeijun
	 * @param model
	 * @return
	 */
	@RequestMapping("createCard")
	public String creatCard(Model model) {
		try {
			List<ActEntityCardConfig> cardConfigList = this.actEntityCardConfigService.findAll();
			model.addAttribute("cardConfigList", cardConfigList);
		    List<Store> storeList=this.storeService.findAll();
		    model.addAttribute("storeList", storeList);
		} catch (Exception e) {
			logger.error("跳转到生成实体卡页面失败", e);
		}
		return PRE_+"createEntityCard";
	}

	/***
	 * 实体卡生成  xupeijun
	 * @param id
	 * @param num
	 * @param redirectAttributes
	 * @param session
	 * @return
	 */
	@RequestMapping("actEntityCardAdd/{id}/{num}/{storeCode}/{time}")
	public String actEntityCardAdd(@PathVariable("id") Long id,
			                       @PathVariable("num") Integer num,
			                       @PathVariable("storeCode") String storeCode,
			   RedirectAttributes redirectAttributes, HttpSession session) {
		try {
			ActEntityCardConfig configCard=this.actEntityCardConfigService.findById(id);
			for (int i = 1; i <= num; i++) {
				ActEntityCard card = new ActEntityCard();
				/** 生成的优惠券编号 */
				card.setCardNo(CmsConstant.ACT_CARD_PRFFIX+ RandomNumUtil.getRandom(RandomNumUtil.NUM, 8));
				card.setChargePwd(RandomNumUtil.getRandom(RandomNumUtil.NUM, 5));
				card.setCode(RandomNumUtil.getCharacterAndNumber(20));
				card.setConfigId(id);
				card.setCreateTime(new Date());
				card.setFaceMoney(configCard.getFaceMoney());
				card.setConfigName(configCard.getName());
				card.setStatus("1");
				card.setStoreCode(storeCode);
				card.setSurplusMoney(configCard.getFaceMoney());
				this.actEntityCardService.add(card);
		  }
			addMessage(redirectAttributes, "实体卡生成成功");
			return REDIRECT_LIST;
		} catch (Exception e) {
			logger.error("实体卡生成异常", e);
			addMessage(redirectAttributes, "实体卡生成失败");
			return "";
		}
	}
	
	
	
	
	
/*	@RequestMapping("actEntityCardEditJsp/{id}/{time}")
	public String actEntityCardEditJsp(@PathVariable("id") Long id, Model model) {
		try {
			ActEntityCard actEntityCard = this.actEntityCardService.findById(id);
			model.addAttribute("actEntityCard", actEntityCard);
			return PRE_+"actEntityCardEdit";
		} catch (Exception e) {
			logger.error("修改失败", e);
			return "";
		}
	}*/
	
	
	@RequestMapping("actEntityCardEdit")
	public String actEntityCardEdit(ActEntityCard actEntityCard, RedirectAttributes redirectAttributes, HttpSession session) {
		try {
			this.actEntityCardService.update(actEntityCard);
			addMessage(redirectAttributes, "修改实体卡配置成功");
			return REDIRECT_LIST;
		} catch (Exception e) {
			logger.error("修改失败", e);
			return "";
		}
	}

/*	@RequestMapping("actEntityCardDel/{id}/{time}")
	public String actEntityCardDel(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		try {
			this.actEntityCardService.deleteById(id);
			addMessage(redirectAttributes, "删除成功");
			return REDIRECT_LIST;
		} catch (Exception e) {
			logger.error("删除失败", e);
			return "";
		}
	}*/
	
	
	@RequestMapping("actEntityCardUseJsp")
	public String actEntityCardUseJsp(Model model, RedirectAttributes redirectAttributes) {
		try {
			model.addAttribute("actEntityCard", new ActEntityCard());
			model.addAttribute("actEntityCardConfig", new ActEntityCardConfig());
			return PRE_+"actEntityCardUse";
		} catch (Exception e) {
			logger.error("修改失败", e);
			return "";
		}
	}
	
	@RequestMapping(value = "findByCardNo/{cardNo}")
	public String findByCardNo(RedirectAttributes redirectAttributes,
			@PathVariable("cardNo") String cardNo,Model model) {
		try {
			ActEntityCard actEntityCard = actEntityCardService.findByCardNo(cardNo);
			if(actEntityCard != null){
				ActEntityCardConfig actEntityCardConfig = actEntityCardConfigService.findById(actEntityCard.getConfigId());
				model.addAttribute("actEntityCardConfig", actEntityCardConfig);
			}
			model.addAttribute("actEntityCard", actEntityCard);
			model.addAttribute("cardNo", cardNo);
			addMessage(redirectAttributes, "实体卡不存在！");
			return PRE_+"actEntityCardUse";
		} catch (Exception e) {
			logger.error("修改失败", e);
			return "";
		}
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/actEntityCardUse", method = RequestMethod.POST)
	public Object actEntityCardUse(RedirectAttributes redirectAttributes, HttpSession session,
			@RequestParam(value = "money", defaultValue = "") float money,
			@RequestParam(value = "cardNo", defaultValue = "") String cardNo,
			@RequestParam(value = "chargePwd", defaultValue = "") String chargePwd,
			@RequestParam(value = "memo", defaultValue = "") String memo) {
		try {
			ActEntityCard actEntityCard = actEntityCardService.findByCardNoPwd(cardNo, chargePwd);
			if(actEntityCard==null){
				 return Jsonp.error("卡号不存在或密码错误！");
			}
			if(actEntityCard.getStatus().endsWith("0")){
				return Jsonp.error("此卡未出售！不可使用");
			}
			ActEntityCardConfig actEntityCardConfig = actEntityCardConfigService.findById(actEntityCard.getConfigId());
			Date date = new Date();
			if(actEntityCardConfig.getBeginTime().getTime()> date.getTime()||actEntityCardConfig.getExpiration().getTime() < date.getTime())
				return Jsonp.error("此卡已失效！不可用");
			if(actEntityCardConfig.getIsDisabled().endsWith("y"))
				return Jsonp.error("此卡已被禁用！不可使用");
			if(actEntityCard.getSurplusMoney().floatValue() < money)
				return Jsonp.error("余额不足,剩余金额为："+actEntityCard.getSurplusMoney());
			actEntityCard.setSurplusMoney(new BigDecimal(actEntityCard.getSurplusMoney().floatValue()-money));
			actEntityCardService.updatesurplusMoneyById(actEntityCard);
			AuthUser auUser = CMSSessionUtils.getSessionUser(session);
			ActEntityCardUseRecord actEntityCardUseRecord = new ActEntityCardUseRecord();
			actEntityCardUseRecord.setCardNo(actEntityCard.getCardNo());
			actEntityCardUseRecord.setMoney(new BigDecimal(money));
			actEntityCardUseRecord.setUseSource("0");
			actEntityCardUseRecord.setUseType("2");
			actEntityCardUseRecord.setUpdateId(auUser.getId());
			actEntityCardUseRecord.setCreateTime(new Date());
			actEntityCardUseRecord.setMemo(memo);
			actEntityCardUseRecord.setSerialNo(new String(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).getBytes("ISO-8859-1"),"UTF-8")+RandomNumUtil.getNumber(4));
			actEntityCardUseRecord.setStoreCode(actEntityCard.getStoreCode());
			actEntityCardUseRecordService.add(actEntityCardUseRecord);
			return Jsonp_data.success(NumberFormatUtil.numberFormat(actEntityCard.getSurplusMoney()));
			
		} catch (Exception e) {
			logger.error("使用卡失败！", e);
			return false;
		} 
	
	}
	
	@RequestMapping("print")
	public String print(@RequestParam("id") Long id,Model model) {
		try {
			ActEntityCard actEntityCard=this.actEntityCardService.findById(id);
			List<ActEntityCardUseRecord> useRecordList=this.actEntityCardUseRecordService.findByCode(actEntityCard.getCardNo());
			if(ObjectUtils.equals(actEntityCard, null)||useRecordList==null||useRecordList.size()<1||ObjectUtils.equals(useRecordList.get(0), null)){
				return "";
			}
			model.addAttribute("entityCard", actEntityCard);
			model.addAttribute("entityCardUseRecord", useRecordList.get(0));
		} catch (Exception e) {
			logger.error("实体卡使用打印异常", e);
		}
		return PRE_+"entityCardPrint";
	}
	
	@RequestMapping("export")
	public void print(@RequestParam("id") Long id,HttpServletResponse response) {
		try {
			ActEntityCard actEntityCard=this.actEntityCardService.findById(id);
			List<ActEntityCardUseRecord> useRecordList=this.actEntityCardUseRecordService.findByCode(actEntityCard.getCardNo());
			if(!(ObjectUtils.equals(actEntityCard, null)||useRecordList==null||useRecordList.size()<1||ObjectUtils.equals(useRecordList.get(0), null))){
				ActEntityCardUseRecord actEntityCardUseRecord = useRecordList.get(0);
				EntityCardUseRecordDto record=new  EntityCardUseRecordDto();
				 record.setCardNo("实体卡号:"+actEntityCardUseRecord.getCardNo());
				 record.setBalance("卡内余额:"+actEntityCard.getSurplusMoney());
				 record.setMoney("消费金额:"+actEntityCardUseRecord.getMoney());
				 record.setCreateTime("使用时间:"+DateFormat.dateToString(actEntityCardUseRecord.getCreateTime()));
				 record.setSerialNo("流水单号:"+actEntityCardUseRecord.getSerialNo());
				 record.setMemo("消费说明:"+actEntityCardUseRecord.getMemo());
				EntityUseRecordPrintExcelReport report = new EntityUseRecordPrintExcelReport();
				String dateString = new String(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).getBytes("ISO-8859-1"),"UTF-8");
				report.getReport(dateString, record, response);
			}
		} catch (Exception e) {
			logger.error("导出小票部分异常", e);
		}
	}

}
