/**
 * 
 */
package com.fushionbaby.cms.controller.refund;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.model.AlabaoConsumeRecord;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.service.AlabaoConsumeRecordService;
import com.aladingshop.finance.model.FinanceRefundApply;
import com.aladingshop.finance.service.FinanceRefundApplyService;
import com.aladingshop.periodactivity.model.ActivityRedEnvlopeUseRecord;
import com.aladingshop.periodactivity.model.ActivityShareMember;
import com.aladingshop.periodactivity.service.ActivityRedEnvlopeUseRecordService;
import com.aladingshop.periodactivity.service.ActivityShareMemberService;
import com.aladingshop.refund.model.Refund;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.order.model.OrderBase;
import com.fushionbaby.order.model.OrderFinance;
import com.fushionbaby.order.model.OrderTrace;
import com.fushionbaby.order.service.OrderBaseService;
import com.fushionbaby.order.service.OrderFinanceService;
import com.fushionbaby.order.service.OrderTraceService;

/**
 * @description 类描述...
 * @author 徐鹏飞
 * @date 2015年8月17日下午1:44:35
 */

@Controller
@RequestMapping("/zeroRefund")
public class ZeroRefundController extends BaseController{
	
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
	private static final Log log=LogFactory.getLog(ZeroRefundController.class);


    /**
     * 调用退款业务逻辑
     * @param refundReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 业务逻辑可能走到的结果分支，需要商户处理
     * @throws Exception
     */
  
    
    @RequestMapping("zeroRefund/{memberId}/{orderCode}/{pageFrom}")
	public String alabaoRefund(@PathVariable Long memberId,@PathVariable String orderCode,@PathVariable String pageFrom,
			HttpSession session,RedirectAttributes redirectAttributes) throws UnsupportedEncodingException{
    		
    		String batch_no = new String(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).getBytes("ISO-8859-1"),"UTF-8");
	    	AuthUser user=CMSSessionUtils.getSessionUser(session);
			OrderBase orderBase=orderBaseService.findByMemberIdAndOrderCode(memberId, orderCode);
			Refund refund=new Refund();
			refund.setBatchNo(batch_no);
			refund.setCreateId(user.getId());
			refund.setMemberId(memberId);
			refund.setOrderCode(orderCode);
			refund.setPaymentType(PaymentTypeEnum.PAYMENT_ZERO.getCode());
			refund.setSettleAmount("0.00");
			refund.setSourceCode(orderBase.getSourceCode());
			refund.setRefundStatus("refundSuccess");
			aladingRefundService.add(refund);
			
			orderBase.setOrderStatus(OrderConfigServerEnum.USER_REFUND.getCode());
			orderBaseService.update(orderBase);
			
			OrderTrace orderTrace=new OrderTrace();
			orderTrace.setMemberId(memberId);
			orderTrace.setOrderCode(orderCode);
			orderTrace.setOrderStatus(OrderConfigServerEnum.USER_REFUND.getCode());
			orderTraceService.updateByMemberIdAndOrderCode(orderTrace);
			
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
	

}
