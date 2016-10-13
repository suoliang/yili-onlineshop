/**
 * 
 */
package com.fushionbaby.cms.controller.refund;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.model.AlabaoConsumeRecord;
import com.aladingshop.alabao.model.AlabaoShiftToRecord;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.service.AlabaoConsumeRecordService;
import com.aladingshop.alabao.service.AlabaoShiftToRecordService;
import com.aladingshop.finance.model.FinanceRefundApply;
import com.aladingshop.finance.service.FinanceRefundApplyService;
import com.aladingshop.periodactivity.model.ActivityRedEnvlopeUseRecord;
import com.aladingshop.periodactivity.model.ActivityShareMember;
import com.aladingshop.periodactivity.service.ActivityRedEnvlopeUseRecordService;
import com.aladingshop.periodactivity.service.ActivityShareMemberService;
import com.aladingshop.refund.model.Refund;
import com.aladingshop.refund.service.RefundService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.common.constants.AlabaoPayTypeConstant;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.config.model.SysmgrDictConfig;
import com.fushionbaby.config.service.SysmgrDictConfigService;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.order.model.OrderBase;
import com.fushionbaby.order.model.OrderFinance;
import com.fushionbaby.order.model.OrderTrace;
import com.fushionbaby.order.service.OrderBaseService;
import com.fushionbaby.order.service.OrderFinanceService;
import com.fushionbaby.order.service.OrderTraceService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @description 类描述...
 * @author 徐鹏飞
 * @date 2015年8月17日下午1:44:35
 */

@Controller
@RequestMapping("/alabaoRefund")
public class AlabaoRefundController extends BaseController{
	
	
	private static final Log LOGGER=LogFactory.getLog(AlabaoRefundController.class);
	@Autowired
	private com.aladingshop.refund.service.RefundService aladingRefundService;
	@Autowired
	private OrderFinanceService<OrderFinance> orderFinanceService;
	@Autowired
	private OrderBaseService<OrderBase> orderBaseService;
	@Autowired
	private OrderTraceService<OrderTrace> orderTraceService;
	@Autowired
	private ActivityShareMemberService<ActivityShareMember> activityShareMemberService;
	@Autowired
	private ActivityRedEnvlopeUseRecordService<ActivityRedEnvlopeUseRecord> activityRedEnvlopeUseRecordService;
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	@Autowired
	private AlabaoConsumeRecordService<AlabaoConsumeRecord> alabaoConsumeRecordService;

	@Autowired
	private FinanceRefundApplyService financeRefundApplyService;

	/**全局字典配置*/
	@Autowired
	private SysmgrDictConfigService<SysmgrDictConfig> sysmgrDictConfigService;
/** 阿拉宝转账记录*/
	@Autowired
	private AlabaoShiftToRecordService<AlabaoShiftToRecord> alabaoShiftToRecordService;
	/**退款记录*/
	@Autowired
	private RefundService refundService;
	/**订单追踪表*/
	@Autowired
	private OrderTraceService<OrderTrace> orderTranceService;
	

	private static final Log log=LogFactory.getLog(AlabaoRefundController.class);


    /**
     * 调用退款业务逻辑
     * @param refundReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 业务逻辑可能走到的结果分支，需要商户处理
     * @throws Exception
     */
  
    
    @RequestMapping("alabaoRefund/{memberId}/{orderCode}/{pageFrom}")
	public String alabaoRefund(@PathVariable Long memberId,@PathVariable String orderCode,@PathVariable String pageFrom,
			HttpSession session,RedirectAttributes redirectAttributes) throws UnsupportedEncodingException{
    	AlabaoConsumeRecord alabaoConsumeRecord=alabaoConsumeRecordService.findByOrderCode(orderCode);
    	String settleAmount = "0.00";
    	if(!ObjectUtils.equals(null, alabaoConsumeRecord)){
    		DecimalFormat df = new DecimalFormat("0.00");
    		settleAmount =df.format(alabaoConsumeRecord.getConsumeMoney());
    		BigDecimal settle_amount=new BigDecimal(settleAmount);
    		
    		String batch_no = DateFormat.dateToSerialNo(new Date());
	    	AuthUser user=CMSSessionUtils.getSessionUser(session);
			OrderBase orderBase=orderBaseService.findByMemberIdAndOrderCode(memberId, orderCode);
			
			Refund refund = saveRefund(memberId, orderCode,settleAmount,batch_no, user, orderBase);
			
			orderBase.setOrderStatus(OrderConfigServerEnum.USER_REFUND.getCode());
			orderBaseService.update(orderBase);

			saveOrderTrace(memberId, orderCode);
			
			AlabaoAccount alabaoAccount = updateAlabaoBanlance(alabaoConsumeRecord, settle_amount);
			/** 阿拉宝账户的退款收入*/
			saveShiftRecord(memberId, settle_amount, batch_no,alabaoAccount);
			
			FinanceRefundApply financeRefundApply=financeRefundApplyService.findByMemberIdAndOrderCode(memberId, orderCode);
			financeRefundApply.setStatus("2");
			financeRefundApplyService.updateByMemberIdAndOrderCode(financeRefundApply);

			/**退红包*/
			ActivityRedEnvlopeUseRecord activityRedEnvlopeUseRecord =activityRedEnvlopeUseRecordService.findByOrderCode(orderCode);
			if(!ObjectUtils.equals(null, activityRedEnvlopeUseRecord)){
				BigDecimal redEnvelopeAmount=activityRedEnvlopeUseRecord.getRedEnvelopeAmount();
				activityRedEnvlopeUseRecord.setUseStatus(2);
				activityRedEnvlopeUseRecordService.updateUseStatus(activityRedEnvlopeUseRecord);
				ActivityShareMember activityShareMember=activityShareMemberService.findByMemberId(memberId);
				BigDecimal nowRedEnvelopeAmount=activityShareMember.getExistingRedEnvelope().add(redEnvelopeAmount);
				activityShareMember.setExistingRedEnvelope(nowRedEnvelopeAmount);
				activityShareMemberService.update(activityShareMember);
				
				refund.setRedEnvlopeAmount(redEnvelopeAmount.toString());
				aladingRefundService.updateByMemberIdAndOrderCode(refund);
			}
			/**退积分*/
//			orderBase=orderBaseService.findByMemberIdAndOrderCode(memberId, orderCode);
//			if(!ObjectUtils.equals(null, orderBase.getTotalPointUsed())){
//				BigDecimal totalPointUsed=orderBase.getTotalPointUsed();
//				Member member=memberService.findById(memberId);
//				member.setEpoints(member.getEpoints().add(totalPointUsed));
//				memberService.update(member);
//			}
			
			
	        log.info("退款成功");
	        addMessage(redirectAttributes, "退款成功");
    	}
    	if("refundApplyList".equals(pageFrom)){
    		return  "redirect:" + Global.getAdminPath()+ "/refund/refundApplyList";
    	}else if("orderRefundList".equals(pageFrom)){
    		return  "redirect:" + Global.getAdminPath()+ "/order/orderRefundList";
    	}
    	return  "redirect:" + Global.getAdminPath()+ "/order/queryOrderBaseList";
    	 
	}	
    
	@ResponseBody
	@RequestMapping("checkRefund/{memberId}/{orderCode}")
	public String checkRefund(@PathVariable Long memberId,@PathVariable String orderCode){
		Refund exitRefund=aladingRefundService.findByMemberIdAndOrderCode(memberId, orderCode);
		if(ObjectUtils.equals(null, exitRefund)){
			return "false";
		}else if("refundSuccess".equals(exitRefund.getRefundStatus())){
			return "true";
		}
		return "false";
		
		
	}	
	
	
	/***
	 * 退款前的校验（密码）
	 * @param memberId
	 * @param orderCode
	 * @param model
	 * @return
	 */
	@RequestMapping("refundCheck")
	public String gotoRefund(Long memberId,String orderCode,ModelMap model){
		OrderBase orderBase = orderBaseService.findByMemberIdAndOrderCode(memberId, orderCode);
		model.addAttribute("orderBase", orderBase);
		OrderFinance finance=this.orderFinanceService.findByMemberIdAndOrderCode(memberId, orderCode);
		orderBase.setPaymentTotalActual(finance.getPaymentTotalActual());
		return "models/order/refund/orderRefundCheck";
	}
	
	/***
	 * 阿拉宝退款操作(金额可选)
	 * @param json
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("submitRefund")
	public Object submitRefund(String json,HttpSession session){
		Boolean result=false;
		// 解析data
		Gson gson = new Gson();
		OrderBase orderBase = gson.fromJson(json,new TypeToken<OrderBase>() {}.getType());
		String password=orderBase.getMemo();
		List<SysmgrDictConfig> dictConfig=sysmgrDictConfigService.findByLabelValueType(null, password, "ALABAO_REFUND_PASSWORD");
		if(dictConfig!=null&&dictConfig.size()>0)
		{
		 result=alabaoRefund(orderBase.getMemberId(), orderBase.getOrderCode(), session,orderBase.getPaymentTotalActual());
		}
		return result;
	}

		/***
		 * 
		 * @param memberId  
		 * @param orderCode
		 * @param session
		 * @param money  要退的金额（为空或则有误则退全部）
		 * @return
		 */
	    @Transactional(propagation=Propagation.REQUIRED,readOnly = false,rollbackFor={Exception.class})
		private boolean alabaoRefund(Long memberId, String orderCode,HttpSession session,BigDecimal money) {
			boolean result=false;
			try {
				AlabaoConsumeRecord alabaoConsumeRecord=alabaoConsumeRecordService.findByOrderCode(orderCode);
				String settleAmount = "0.00";
			    	if(ObjectUtils.equals(null, alabaoConsumeRecord))
			    		return result;
		    	
		    		DecimalFormat df = new DecimalFormat("0.00");
		    		settleAmount =df.format(money==null?alabaoConsumeRecord.getConsumeMoney():money);
		    		BigDecimal settle_amount=new BigDecimal(settleAmount);
		    		
		    		String batch_no = DateFormat.dateToSerialNo(new Date());
			    	AuthUser user=CMSSessionUtils.getSessionUser(session);
					OrderBase orderBase=orderBaseService.findByMemberIdAndOrderCode(memberId, orderCode);
					Refund refund = saveRefund(memberId, orderCode,	settleAmount, batch_no, user, orderBase);
					
					orderBase.setOrderStatus(OrderConfigServerEnum.USER_REFUND.getCode());
					orderBaseService.update(orderBase);
					
					saveOrderTrace(memberId, orderCode);
					
					FinanceRefundApply refundApply=this.financeRefundApplyService.findByMemberIdAndOrderCode(memberId, orderCode);
					if(refundApply!=null){
						    refundApply.setStatus("2");
							financeRefundApplyService.updateByMemberIdAndOrderCode(refundApply);
					}
					 
					
					/**退阿拉宝*/
					AlabaoAccount alabaoAccount = updateAlabaoBanlance(alabaoConsumeRecord, settle_amount);
					/** 阿拉宝账户的退款收入*/
					saveShiftRecord(memberId, settle_amount, batch_no,alabaoAccount);
					
					
					/**退红包*/
					ActivityRedEnvlopeUseRecord activityRedEnvlopeUseRecord =activityRedEnvlopeUseRecordService.findByOrderCode(orderCode);
					if(!ObjectUtils.equals(null, activityRedEnvlopeUseRecord))
					{
						BigDecimal redEnvelopeAmount=activityRedEnvlopeUseRecord.getRedEnvelopeAmount();
						activityRedEnvlopeUseRecord.setUseStatus(2);
						activityRedEnvlopeUseRecordService.updateUseStatus(activityRedEnvlopeUseRecord);
						ActivityShareMember activityShareMember=activityShareMemberService.findByMemberId(memberId);
						BigDecimal nowRedEnvelopeAmount=activityShareMember.getExistingRedEnvelope().add(redEnvelopeAmount);
						activityShareMember.setExistingRedEnvelope(nowRedEnvelopeAmount);
						activityShareMemberService.update(activityShareMember);
						
						refund.setRedEnvlopeAmount(redEnvelopeAmount.toString());
						aladingRefundService.updateByMemberIdAndOrderCode(refund);
					}
						result=true;
			} catch (Exception e) {
				LOGGER.error("如意消费卡退款异常", e);
			}
			return result;
		}

	    /***
	     * 保存退款记录
	     * @param memberId
	     * @param orderCode
	     * @param settleAmount
	     * @param batch_no
	     * @param user
	     * @param orderBase
	     * @return
	     */
		private Refund saveRefund(Long memberId, String orderCode,String settleAmount, String batch_no, AuthUser user,OrderBase orderBase) {
			
			Refund refund=refundService.findByMemberIdAndOrderCode(memberId, orderCode);
			if(refund!=null)
				return refund;
			refund=new Refund();
			refund.setBatchNo(batch_no);
			refund.setCreateId(user.getId());
			refund.setMemberId(memberId);
			refund.setOrderCode(orderCode);
			refund.setPaymentType(PaymentTypeEnum.PAYMENT_ALABAO_APP.getCode());
			refund.setSettleAmount(settleAmount);
			refund.setSourceCode(orderBase.getSourceCode());
			refund.setRefundStatus("refundSuccess");
			aladingRefundService.add(refund);
			return refund;
		}

	    /***
	     * 订单跟踪记录表
	     * @param memberId
	     * @param orderCode
	     */
		private void saveOrderTrace(Long memberId, String orderCode) {
			
			OrderTrace orderTrace=orderTranceService.findByOrderCode(orderCode);
			if(orderTrace==null)
				orderTrace=new OrderTrace();
			orderTrace.setMemberId(memberId);
			orderTrace.setOrderCode(orderCode);
			orderTrace.setOrderStatus(OrderConfigServerEnum.USER_REFUND.getCode());
			orderTraceService.updateByMemberIdAndOrderCode(orderTrace);
		}

	    /***
	     * 更新阿拉宝的余额信息
	     * @param alabaoConsumeRecord
	     * @param settleAmount
	     * @return
	     */
		private AlabaoAccount updateAlabaoBanlance(AlabaoConsumeRecord alabaoConsumeRecord, BigDecimal settleAmount) {
			AlabaoAccount alabaoAccount=alabaoAccountService.findByAccount(alabaoConsumeRecord.getAccount());
			BigDecimal balance=alabaoAccount.getBalance();
			BigDecimal nowBalance=balance.add(settleAmount);
			alabaoAccount.setBalance(nowBalance);
			alabaoAccountService.updateByAccount(alabaoAccount);
			return alabaoAccount;
		}

	    /***
	     *  保存退款记录  ---账户转入
	     * @param memberId
	     * @param settleAmount
	     * @param batch_no
	     * @param alabaoAccount
	     */
		private void saveShiftRecord(Long memberId, BigDecimal settleAmount,String batch_no, AlabaoAccount alabaoAccount) {
			AlabaoShiftToRecord shiftRecord=new AlabaoShiftToRecord();
			shiftRecord.setAccount(alabaoAccount.getAccount());
			shiftRecord.setBatchNum(batch_no);
			shiftRecord.setCreateTime(new Date());
			shiftRecord.setMemberId(memberId);
			shiftRecord.setSerialNum(batch_no);
			shiftRecord.setShiftToAccountType(AlabaoPayTypeConstant.ALABAO_APP_REFUND);
			shiftRecord.setTransferMoney(settleAmount);
			
			shiftRecord.setAfterChangeMoney(alabaoAccount.getBalance().add(settleAmount));
			shiftRecord.setBeforeChangeMoney(alabaoAccount.getBalance());
			
			alabaoShiftToRecordService.add(shiftRecord);
		}
	

}
