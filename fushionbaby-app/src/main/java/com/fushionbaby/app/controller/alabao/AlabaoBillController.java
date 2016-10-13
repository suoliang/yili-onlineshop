package com.fushionbaby.app.controller.alabao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import util.ImageConstantFacade;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.model.AlabaoConsumeRecord;
import com.aladingshop.alabao.model.AlabaoIncomeRecord;
import com.aladingshop.alabao.model.AlabaoRollOffRecord;
import com.aladingshop.alabao.model.AlabaoShiftToRecord;
import com.aladingshop.alabao.model.AlabaoTurnOut;
import com.aladingshop.alabao.model.AlabaoTurnToAlabao;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.service.AlabaoConsumeRecordService;
import com.aladingshop.alabao.service.AlabaoIncomeRecordService;
import com.aladingshop.alabao.service.AlabaoRollOffRecordService;
import com.aladingshop.alabao.service.AlabaoShiftToRecordService;
import com.aladingshop.alabao.service.AlabaoTurnOutService;
import com.aladingshop.alabao.service.AlabaoTurnToAlabaoService;
import com.aladingshop.alabao.vo.AlabaoBillDto;
import com.aladingshop.alabao.vo.AlabaoBillGroupDto;
import com.fushionbaby.app.util.SortGroupBillUtil;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.enums.alabao.AlabaoConsumeDetailImageEnum;
import com.fushionbaby.common.enums.alabao.AlabaoIncomeDetailImageEnum;
import com.fushionbaby.common.enums.alabao.AlabaoShiftDetailImageEnum;
import com.fushionbaby.common.enums.alabao.AlabaoShiftTypeEnum;
import com.fushionbaby.common.enums.alabao.AlabaoTurnDetailImageEnum;
import com.fushionbaby.common.enums.alabao.AlabaoTurnOutEnum;
import com.fushionbaby.common.enums.alabao.AlabaoTurnToBankEnum;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Controller
@RequestMapping("/alabao")
public class AlabaoBillController {
	
	private static final Log LOGGER = LogFactory.getLog(AlabaoBillController.class);
	
	@Autowired
	private AlabaoShiftToRecordService<AlabaoShiftToRecord> shiftToRecordService;
	@Autowired
	private AlabaoIncomeRecordService<AlabaoIncomeRecord> alabaoIncomeRecordService;
	@Autowired
	private AlabaoConsumeRecordService<AlabaoConsumeRecord> alabaoConsumeRecordService;
	@Autowired
	private AlabaoRollOffRecordService<AlabaoRollOffRecord> alabaoRollOffRecordService;
	@Autowired
	private AlabaoTurnToAlabaoService<AlabaoTurnToAlabao> alabaoTurnToAlabaoService;
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	@Autowired
	private AlabaoTurnOutService<AlabaoTurnOut> alabaoTurnOutService;
	
	
	
	
    private static final String BALANCE="暂无数据";
    
    private static final String PRE_BALANCE="余额:";
    
	/***
	 * 显示的所有账单
	 * @param alabaoSid
	 * @param mac
	 * @return
	 */
	@ResponseBody
	@RequestMapping("mainBill")
	@SuppressWarnings("unchecked")
	public Object mainBill(
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac){
		try {
			LOGGER.info("查看账单如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String alabaoSid = json.getAsJsonObject().get("alabaoSid").getAsString();
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("未登录或重新登录");
			}
			String accountUser = alabaoUserDto.getAccount();
			List<AlabaoShiftToRecord> shiftToRecordList = shiftToRecordService.findBillRecordByTime(accountUser);
			List<AlabaoRollOffRecord> rollOffRecordList = alabaoRollOffRecordService.findBillRecordByTime(accountUser);
			List<AlabaoConsumeRecord> consumeRecordList = alabaoConsumeRecordService.findBillRecordByTime(accountUser);
			List<AlabaoIncomeRecord> incomeRecordList = alabaoIncomeRecordService.findBillRecordByTime(accountUser);
			List<AlabaoBillDto> billList = new ArrayList<AlabaoBillDto>();
			/**转入账单记录*/
			for (AlabaoShiftToRecord alabaoShiftToRecord : shiftToRecordList) {
				AlabaoBillDto alabaoBillDto = new AlabaoBillDto();
				String account = "";
				String memo = "";
				if (StringUtils.equalsIgnoreCase(alabaoShiftToRecord.getShiftToAccountType(), AlabaoShiftTypeEnum.ALABAO.getCode())) {
					AlabaoTurnToAlabao turnToAlabao = alabaoTurnToAlabaoService.findBySerialNum(alabaoShiftToRecord.getSerialNum());
					if (ObjectUtils.notEqual(turnToAlabao, null)) {
						AlabaoAccount otherAccount = alabaoAccountService.findByAccount(turnToAlabao.getAccount());
						account = otherAccount.getTrueName()+"("+turnToAlabao.getAccount()+")";
						memo = turnToAlabao.getMemo();
					}
				}
				alabaoBillDto.setAccount(account);
				alabaoBillDto.setCreateTime(DateFormat.dateTimeToDateString(alabaoShiftToRecord.getCreateTime()));
				alabaoBillDto.setFullTime(DateFormat.dateToString(alabaoShiftToRecord.getCreateTime()));
				alabaoBillDto.setTradeMoney("+"+alabaoShiftToRecord.getTransferMoney());
				alabaoBillDto.setTradeStatus("转入成功");
				alabaoBillDto.setTradeType(AlabaoShiftTypeEnum.parseCode(alabaoShiftToRecord.getShiftToAccountType()));
				alabaoBillDto.setDetailImage(ImageConstantFacade.IMAGE_SERVER_ROOT_PATH+AlabaoShiftDetailImageEnum.parseCode(alabaoShiftToRecord.getShiftToAccountType()));
				alabaoBillDto.setTurnOutType(StringUtils.EMPTY);
				alabaoBillDto.setReason(StringUtils.EMPTY);
				alabaoBillDto.setRemark(memo);
				
				String balance=BALANCE;
				if(alabaoShiftToRecord.getAfterChangeMoney()!=null){
					balance=PRE_BALANCE+NumberFormatUtil.numberFormat(alabaoShiftToRecord.getAfterChangeMoney());
				}
				alabaoBillDto.setBalance(balance);
				billList.add(alabaoBillDto);
			}
			/**转出账单记录-银行卡和如意消费卡*/
			for (AlabaoRollOffRecord alabaoRollOffRecord : rollOffRecordList) {
				AlabaoBillDto alabaoBillDto = new AlabaoBillDto();
				String account = "";
				String tradeStatus = "转出成功";
				String turnOutType = "";
				String memo = "";
				if (StringUtils.equalsIgnoreCase(alabaoRollOffRecord.getRollOffAccountType(), AlabaoShiftTypeEnum.ALABAO.getCode())) {
					AlabaoTurnToAlabao turnToAlabao = alabaoTurnToAlabaoService.findBySerialNum(alabaoRollOffRecord.getSerialNum());
					if (ObjectUtils.notEqual(turnToAlabao, null)) {
						AlabaoAccount otherAccount = alabaoAccountService.findByAccount(turnToAlabao.getOtherAccount());
						account = otherAccount.getTrueName()+"("+turnToAlabao.getOtherAccount()+")";
						turnOutType = AlabaoTurnOutEnum.TURN_TO_ALABAO.getCode();
						memo = turnToAlabao.getMemo();
					}
				} else {
					AlabaoTurnOut alabaoTurnOut = alabaoTurnOutService.findBySerialNum(alabaoRollOffRecord.getSerialNum());
					if (ObjectUtils.notEqual(alabaoTurnOut, null)) {
						String cardNo = alabaoTurnOut.getCardNo();
						account = alabaoTurnOut.getCardHolder()+"("+alabaoTurnOut.getBankName()+cardNo.substring(cardNo.length()-4,cardNo.length())+")";
						tradeStatus = AlabaoTurnToBankEnum.parseCode(alabaoTurnOut.getTurnOutStatus());
						turnOutType = AlabaoTurnOutEnum.TURN_TO_BANK.getCode();
						memo = alabaoTurnOut.getMemo();
					}
				}
				alabaoBillDto.setAccount(account);
				alabaoBillDto.setCreateTime(DateFormat.dateTimeToDateString(alabaoRollOffRecord.getCreateTime()));
				alabaoBillDto.setFullTime(DateFormat.dateToString(alabaoRollOffRecord.getCreateTime()));
				alabaoBillDto.setTradeMoney("-"+alabaoRollOffRecord.getTransferMoney());
				alabaoBillDto.setTradeStatus(tradeStatus);
				alabaoBillDto.setTradeType("转出");
				alabaoBillDto.setTurnOutType(AlabaoTurnOutEnum.parseCode(turnOutType));
				String detailImage = "";
				if (!StringUtils.equalsIgnoreCase(null, turnOutType)) {
					detailImage = ImageConstantFacade.IMAGE_SERVER_ROOT_PATH+AlabaoTurnDetailImageEnum.parseCode(turnOutType);
				}
				alabaoBillDto.setDetailImage(detailImage);
				alabaoBillDto.setReason(StringUtils.EMPTY);
				alabaoBillDto.setRemark(memo);
				String balance=BALANCE;
				if(alabaoRollOffRecord.getAfterChangeMoney()!=null){
					balance=PRE_BALANCE+NumberFormatUtil.numberFormat(alabaoRollOffRecord.getAfterChangeMoney());
				}
				alabaoBillDto.setBalance(balance);
				
				billList.add(alabaoBillDto);
			}
			/**消费记录*/
			for (AlabaoConsumeRecord alabaoConsumeRecord : consumeRecordList) {
				AlabaoBillDto alabaoBillDto = new AlabaoBillDto();
				alabaoBillDto.setAccount("");
				alabaoBillDto.setCreateTime(DateFormat.dateTimeToDateString(alabaoConsumeRecord.getCreateTime()));
				alabaoBillDto.setFullTime(DateFormat.dateToString(alabaoConsumeRecord.getCreateTime()));
				alabaoBillDto.setTradeMoney("-"+alabaoConsumeRecord.getConsumeMoney());
				alabaoBillDto.setTradeStatus("消费成功");
				alabaoBillDto.setTradeType("消费");
				alabaoBillDto.setDetailImage(ImageConstantFacade.IMAGE_SERVER_ROOT_PATH+AlabaoConsumeDetailImageEnum.ALABAO_CONSUME.getName());
				alabaoBillDto.setTurnOutType(StringUtils.EMPTY);
				alabaoBillDto.setReason(StringUtils.EMPTY);
				alabaoBillDto.setRemark("订单号("+alabaoConsumeRecord.getOrderCode()+")");
				String balance=BALANCE;
				if(alabaoConsumeRecord.getAfterChangeMoney()!=null){
					balance=PRE_BALANCE+NumberFormatUtil.numberFormat(alabaoConsumeRecord.getAfterChangeMoney());
				}
				alabaoBillDto.setBalance(balance);
				billList.add(alabaoBillDto);
			}
			/**收益记录*/
			for (AlabaoIncomeRecord alabaoIncomeRecord : incomeRecordList) {
				AlabaoBillDto alabaoBillDto = new AlabaoBillDto();
				alabaoBillDto.setAccount("");
				alabaoBillDto.setCreateTime(DateFormat.dateTimeToDateString(alabaoIncomeRecord.getCreateTime()));
				alabaoBillDto.setFullTime(DateFormat.dateToString(alabaoIncomeRecord.getCreateTime()));
				alabaoBillDto.setTradeMoney("+"+alabaoIncomeRecord.getIncomeMoney());
				alabaoBillDto.setTradeStatus("收券");
				alabaoBillDto.setTradeType("收券");
				alabaoBillDto.setDetailImage(ImageConstantFacade.IMAGE_SERVER_ROOT_PATH+AlabaoIncomeDetailImageEnum.ALABAO_INCOME.getName());
				alabaoBillDto.setTurnOutType(StringUtils.EMPTY);
				alabaoBillDto.setReason(StringUtils.EMPTY);
				alabaoBillDto.setRemark(StringUtils.EMPTY);
				String balance=BALANCE;
				if(alabaoIncomeRecord.getAfIncomeMoney()!=null){
					balance=PRE_BALANCE+NumberFormatUtil.numberFormat(alabaoIncomeRecord.getAfIncomeMoney());
				}
				alabaoBillDto.setBalance(balance);
				billList.add(alabaoBillDto);
			}
			Collections.sort(billList,new SortGroupBillUtil());
	        List<AlabaoBillGroupDto> group = SortGroupBillUtil.groupBill(billList);
			return Jsonp_data.success(group);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("账单查询出错", e);
			return Jsonp.error("账单查询出错");
		}
	}
	
}
