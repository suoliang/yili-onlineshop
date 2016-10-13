package com.aladingshop.wap.controller.ruyibao;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.model.AlabaoRollOffRecord;
import com.aladingshop.alabao.model.AlabaoRolloffConfig;
import com.aladingshop.alabao.model.AlabaoShiftToRecord;
import com.aladingshop.alabao.model.AlabaoTurnToAlabao;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.service.AlabaoRollOffRecordService;
import com.aladingshop.alabao.service.AlabaoRolloffConfigService;
import com.aladingshop.alabao.service.AlabaoShiftToRecordService;
import com.aladingshop.alabao.service.AlabaoTurnToAlabaoService;
import com.aladingshop.alabao.vo.AlabaoToAlabaoDto;
import com.fushionbaby.cache.util.AlabaoSessionCache;
import com.fushionbaby.common.constants.AlabaoPayTypeConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.GlobalConfigConstant;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Controller
@RequestMapping("/alabao")
public class AlabaoTurnToAlabaoController {
	
	private static final Log LOGGER = LogFactory.getLog(AlabaoTurnToAlabaoController.class);
	
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	@Autowired
	private AlabaoRolloffConfigService<AlabaoRolloffConfig> alabaoRolloffConfigService;
	@Autowired
	private AlabaoRollOffRecordService<AlabaoRollOffRecord> alabaoRollOffRecordService;
	@Autowired
	private AlabaoTurnToAlabaoService<AlabaoTurnToAlabao> turnToAlabaoService;
	@Autowired
	private AlabaoShiftToRecordService<AlabaoShiftToRecord> alabaoShiftToRecordService;
	
	/**
	 * @description 验证转出账户有效性
	 * @param alabaoSid
	 * @param otherAccount
	 * @param mac
	 * @return
	 */
	@ResponseBody
	@RequestMapping("inputAccount")
	public Object inputAccount(
			@RequestParam(value="data")String data,
			@RequestParam(value="mac")String mac){
		try {
			LOGGER.info("验证转出有效性如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String alabaoSid = json.getAsJsonObject().get("alabaoSid").getAsString();
			String otherAccount = json.getAsJsonObject().get("otherAccount").getAsString();
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("未登录或重新登录");
			}
			/**不能自己转给自己*/
			if (StringUtils.equalsIgnoreCase(otherAccount, alabaoUserDto.getAccount())) {
				return Jsonp.error("请不要转给自己");
			}
			/**对方账户信息*/
			AlabaoAccount otherAlaAccount = alabaoAccountService.findByAccount(otherAccount);
			if (ObjectUtils.equals(null, otherAlaAccount)) {
				return Jsonp.error("该账户不存在");
			}
			AlabaoToAlabaoDto alabaoToAlabaoDto = new AlabaoToAlabaoDto();
			alabaoToAlabaoDto.setName(otherAlaAccount.getTrueName()==null?"":otherAlaAccount.getTrueName());
			alabaoToAlabaoDto.setOtherAccount(otherAlaAccount.getAccount());
			
			return Jsonp_data.success(alabaoToAlabaoDto);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("验证转出账户有效性接口出错", e);
			return Jsonp.error("转出到账户出错");
		}
	}
	
	/**
	 * @description 转出到如意宝账户 -- 主操作
	 * @param alabaoSid		如意宝登录标识
	 * @param otherAccount  对方账户
	 * @param turnOutAmount 转出金额
	 * @param password      支付密码
	 * @param mac
	 * @return
	 */
	@ResponseBody
	@RequestMapping("turnToAlabao")
	public Object turnToAlabao(
			@RequestParam(value="data")String data,
			@RequestParam(value="mac")String mac){
		try {
			LOGGER.info("验证转出有效性如意宝接口action--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(data);
			String alabaoSid = json.getAsJsonObject().get("alabaoSid").getAsString();
			String otherAccount = json.getAsJsonObject().get("otherAccount").getAsString();
			String turnOutAmount = json.getAsJsonObject().get("turnOutAmount").getAsString();
			String password = json.getAsJsonObject().get("password").getAsString();
			AlabaoUserDto alabaoUserDto = (AlabaoUserDto) AlabaoSessionCache.get(alabaoSid);
			if (ObjectUtils.equals(alabaoUserDto, null)) {
				return Jsonp.noLoginError("未登录或重新登录");
			}
			/**不能自己转给自己*/
			if (StringUtils.equalsIgnoreCase(otherAccount, alabaoUserDto.getAccount())) {
				return Jsonp.error("请不要转给自己");
			}
			/**对方账户信息*/
			AlabaoAccount otherAlaAccount = alabaoAccountService.findByAccount(otherAccount);
			if (ObjectUtils.equals(null, otherAlaAccount)) {
				return Jsonp.error("对方账户不存在");
			}
			
			Long memberId = alabaoUserDto.getMemberId();
			String account = alabaoUserDto.getAccount();
			/**账户信息*/
			AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(account);
			if (!StringUtils.equals(alabaoAccount.getPayPassword(), MD5Util.MD5(password+MD5Util.getPasswordkey()))) {
				return Jsonp.acountFailureError("密码输入错误");
			}
			if (alabaoAccount.getBalance().compareTo(new BigDecimal(turnOutAmount))<0) {
				return Jsonp.error("您当前金额不足");
			}
			AlabaoRolloffConfig alabaoRolloffConfig = alabaoRolloffConfigService.findByRollOffCode(GlobalConfigConstant.ROLLOFFCODE);
			/**暂不操作如意宝转如意宝每天的转出限制*/
			//List<AlabaoRollOffRecord> list = alabaoRollOffRecordService.findByMemberIdTime(memberId, new Date());
			if (ObjectUtils.notEqual(alabaoRolloffConfig, null)) {
				// 小于0表示前面数小后面数大--超出转出限额，不能转
				if(alabaoRolloffConfig.getMaxMoneyLimit().compareTo(new BigDecimal(turnOutAmount))<0){
					return Jsonp.error("超出限额"+alabaoRolloffConfig.getMaxMoneyLimit());
				}
				//if (!CollectionUtils.isEmpty(list) && list.size() >= alabaoRolloffConfig.getMaxNumberLimit()) {
					//return Jsonp.error("超出次数"+alabaoRolloffConfig.getMaxNumberLimit());
				//}
			}
			BigDecimal transfer_money=new BigDecimal(turnOutAmount);
			BigDecimal old_balance=alabaoAccount.getBalance();
			String seriNo=DateFormat.dateToSerialNo(new Date());
			/**本方转出记录*/
			saveRollOffRecord(memberId, account, transfer_money, old_balance,seriNo);
			/**本方转出方记录*/
			saveTurnTo(otherAccount, memberId, account, transfer_money,seriNo);
			/**对方转入记录*/
			saveShiftToRecord(otherAccount, otherAlaAccount, transfer_money,seriNo);
			
			/**转出时余额做改变*/
			alabaoAccount.setBalance(alabaoAccount.getBalance().subtract(transfer_money));
			alabaoAccountService.updateByAccount(alabaoAccount);
			/**对方余额做增加改变操作*/
			otherAlaAccount.setBalance(otherAlaAccount.getBalance().add(transfer_money));
			alabaoAccountService.updateByAccount(otherAlaAccount);
			return Jsonp.success();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("转出到如意宝接口出错", e);
			return Jsonp.error("出错");
		}
	}

	private void saveShiftToRecord(String otherAccount,
			AlabaoAccount otherAlaAccount, BigDecimal transfer_money,String serialNum) {
		AlabaoShiftToRecord alabaoShiftToRecord = new AlabaoShiftToRecord();
		alabaoShiftToRecord.setAccount(otherAccount);
		alabaoShiftToRecord.setMemberId(otherAlaAccount.getMemberId());
		alabaoShiftToRecord.setTransferMoney(transfer_money);
		alabaoShiftToRecord.setShiftToAccountType(AlabaoPayTypeConstant.ALABAO);
		alabaoShiftToRecord.setAfterChangeMoney(otherAlaAccount.getBalance().add(transfer_money));
		alabaoShiftToRecord.setBeforeChangeMoney(otherAlaAccount.getBalance());
		alabaoShiftToRecord.setSerialNum(serialNum);
		alabaoShiftToRecordService.add(alabaoShiftToRecord);
	}

	private void saveTurnTo(String otherAccount, Long memberId, String account,
			BigDecimal transfer_money,String serialNum) {
		AlabaoTurnToAlabao turnToAlabao = new AlabaoTurnToAlabao();
		turnToAlabao.setMemberId(memberId);
		turnToAlabao.setAccount(account);
		turnToAlabao.setOtherAccount(otherAccount);
		turnToAlabao.setTransferMoney(transfer_money);
		turnToAlabao.setTurnOutStatus(CommonConstant.YES);
		turnToAlabao.setSerialNum(serialNum);
		turnToAlabaoService.add(turnToAlabao);
	}

	private void saveRollOffRecord(Long memberId, String account,
			BigDecimal transfer_money, BigDecimal old_balance,String seriNo) {
		AlabaoRollOffRecord alabaoRollOffRecord = new AlabaoRollOffRecord();
		alabaoRollOffRecord.setAccount(account);
		alabaoRollOffRecord.setMemberId(memberId);
		alabaoRollOffRecord.setIsSuccess(CommonConstant.YES);
		alabaoRollOffRecord.setTransferMoney(transfer_money);
		alabaoRollOffRecord.setRollOffAccountType("1");
		alabaoRollOffRecord.setSerialNum(seriNo);
		
		alabaoRollOffRecord.setAfterChangeMoney(old_balance.subtract(transfer_money));
		alabaoRollOffRecord.setBeforeChangeMoney(old_balance);
		alabaoRollOffRecordService.add(alabaoRollOffRecord);
	}
	
}
