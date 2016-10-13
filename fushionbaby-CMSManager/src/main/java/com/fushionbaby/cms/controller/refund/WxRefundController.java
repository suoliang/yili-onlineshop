/**
 * 
 */
package com.fushionbaby.cms.controller.refund;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
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
import com.fushionbaby.common.util.GetIpAddress;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.order.model.OrderBase;
import com.fushionbaby.order.model.OrderFinance;
import com.fushionbaby.order.model.OrderTrace;
import com.fushionbaby.order.service.OrderBaseService;
import com.fushionbaby.order.service.OrderFinanceService;
import com.fushionbaby.order.service.OrderTraceService;
import com.fushionbaby.payment.model.PaymentAppWx;
import com.fushionbaby.payment.service.PaymentAppWxService;
import com.tencent.common.Configure;
import com.tencent.common.Signature;
import com.tencent.common.Util;
import com.tencent.common.report.ReporterFactory;
import com.tencent.common.report.protocol.ReportReqData;
import com.tencent.common.report.service.ReportService;
import com.tencent.protocol.refund_protocol.RefundReqData;
import com.tencent.protocol.refund_protocol.RefundResData;
import com.tencent.service.RefundService;

/**
 * @description 类描述...
 * @author 徐鹏飞
 * @date 2015年8月17日下午1:44:35
 */

@Controller
@RequestMapping("/wxRefund")
public class WxRefundController extends BaseController{
	
	@Autowired
	private PaymentAppWxService<PaymentAppWx> appWxService;
	
	@Autowired
	private com.aladingshop.refund.service.RefundService aladingRefundService;
	@Autowired
	private OrderFinanceService<OrderFinance> orderFinanceService;
	@Autowired
	private OrderBaseService<OrderBase> orderBaseService;
	@Autowired
	private OrderTraceService<OrderTrace> orderTraceService;
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	@Autowired
	private ActivityShareMemberService<ActivityShareMember> activityShareMemberService;
	@Autowired
	private ActivityRedEnvlopeUseRecordService<ActivityRedEnvlopeUseRecord> activityRedEnvlopeUseRecordService;
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private FinanceRefundApplyService financeRefundApplyService;
	private static final Log log=LogFactory.getLog(WxRefundController.class);


    /**
     * 调用退款业务逻辑
     * @param refundReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 业务逻辑可能走到的结果分支，需要商户处理
     * @throws Exception
     */
    @RequestMapping("wxRefund/{memberId}/{orderCode}/{pageFrom}")
    public String refund(@PathVariable Long memberId,@PathVariable String orderCode,@PathVariable String pageFrom,HttpServletRequest request,HttpSession session,
    		 RedirectAttributes redirectAttributes) throws Exception {

        //--------------------------------------------------------------------
        //构造请求“退款API”所需要提交的数据
    	PaymentAppWx paymentAppWx=appWxService.getByMemberIdAndOrderCode(memberId, orderCode);
    	Integer totalFee=Integer.parseInt(paymentAppWx.getSettleAmount());
    	Integer refundFee=totalFee;
    	//批次号
    	String wxOrderCode=paymentAppWx.getOrderNumber();
    	String outRefundNo = new String(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).getBytes("ISO-8859-1"),"UTF-8");
    	RefundReqData refundReqData=new RefundReqData(wxOrderCode, outRefundNo, totalFee, refundFee);
        //--------------------------------------------------------------------

        //API返回的数据
        String refundServiceResponseString;

        long costTimeStart = System.currentTimeMillis();

        RefundService refundService = new RefundService();
        log.info("退款查询API返回的数据如下：");
        refundServiceResponseString = refundService.request(refundReqData);

        long costTimeEnd = System.currentTimeMillis();
        long totalTimeCost = costTimeEnd - costTimeStart;
        log.info("api请求总耗时：" + totalTimeCost + "ms");

        log.info(refundServiceResponseString);

        //将从API返回的XML数据映射到Java对象
        RefundResData refundResData = (RefundResData) Util.getObjectFromXML(refundServiceResponseString, RefundResData.class);

        String ipAddress=GetIpAddress.getIpAddr(request);
        ReportReqData reportReqData = new ReportReqData(
                refundResData.getDevice_info(),
                Configure.REFUND_API,
                (int) (totalTimeCost),//本次请求耗时
                refundResData.getReturn_code(),
                refundResData.getReturn_msg(),
                refundResData.getResult_code(),
                refundResData.getErr_code(),
                refundResData.getErr_code_des(),
                refundResData.getOut_trade_no(),
                ipAddress
        );

        long timeAfterReport;
        if(Configure.isUseThreadToDoReport()){
            ReporterFactory.getReporter(reportReqData).run();
            timeAfterReport = System.currentTimeMillis();
            Util.log("pay+report总耗时（异步方式上报）："+(timeAfterReport-costTimeStart) + "ms");
        }else{
            ReportService.request(reportReqData);
            timeAfterReport = System.currentTimeMillis();
            Util.log("pay+report总耗时（同步方式上报）："+(timeAfterReport-costTimeStart) + "ms");
        }

        if (refundResData == null || refundResData.getReturn_code() == null) {
        	log.error("Case1:退款API请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问");
            addMessage(redirectAttributes, "退款API请求逻辑错误，请仔细检测传过去的每一个参数是否合法，或是看API能否被正常访问");
            return  "redirect:" + Global.getAdminPath()+ "/order/queryOrderBaseList";
        }

        //Debug:查看数据是否正常被填充到scanPayResponseData这个对象中
        //Util.reflect(refundResData);

        if (refundResData.getReturn_code().equals("FAIL")) {
            ///注意：一般这里返回FAIL是出现系统级参数错误，请检测Post给API的数据是否规范合法
        	log.error("Case2:退款API系统返回失败，请检测Post给API的数据是否规范合法");
            addMessage(redirectAttributes, "退款API系统返回失败，请检测Post给API的数据是否规范合法");
        } else {
            log.info("退款API系统成功返回数据");
            //--------------------------------------------------------------------
            //收到API的返回数据的时候得先验证一下数据有没有被第三方篡改，确保安全
            //--------------------------------------------------------------------

            if (!Signature.checkIsSignValidFromResponseString(refundServiceResponseString)) {
            	log.error("退款请求API返回的数据签名验证失败，有可能数据被篡改了");
                addMessage(redirectAttributes, "退款请求API返回的数据签名验证失败，有可能数据被篡改了");
                return  "redirect:" + Global.getAdminPath()+ "/order/queryOrderBaseList";
            }

            if (refundResData.getResult_code().equals("FAIL")) {
                log.error("出错，错误码：" + refundResData.getErr_code() + "  错误信息：" + refundResData.getErr_code_des());
                addMessage(redirectAttributes, "退款失败,错误码：" + refundResData.getErr_code());
                //退款失败时再怎么延时查询退款状态都没有意义，这个时间建议要么再手动重试一次，依然失败的话请走投诉渠道进行投诉
            } else {
                //退款成功
            	String batch_no=refundResData.getOut_refund_no();
            	DecimalFormat df = new DecimalFormat("0.00");
            	String settleAmount = df.format(new BigDecimal(refundResData.getRefund_fee()).multiply(BigDecimal.valueOf(0.01)));
            	AuthUser user=CMSSessionUtils.getSessionUser(session);
        		OrderFinance orderFinance = orderFinanceService.findByMemberIdAndOrderCode(memberId, orderCode);
        		OrderBase orderBase=orderBaseService.findByMemberIdAndOrderCode(memberId, orderCode);
        		Refund refund=new Refund();
        		refund.setBatchNo(batch_no);
        		refund.setCreateId(user.getId());
        		refund.setMemberId(memberId);
        		refund.setOrderCode(orderCode);
        		refund.setPaymentType(orderFinance.getPaymentType());
        		refund.setSettleAmount(settleAmount);
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
//    			orderBase=orderBaseService.findByMemberIdAndOrderCode(memberId, orderCode);
//    			if(!ObjectUtils.equals(null, orderBase.getTotalPointUsed())){
//    				BigDecimal totalPointUsed=orderBase.getTotalPointUsed();
//    				Member member=memberService.findById(memberId);
//    				member.setEpoints(member.getEpoints().add(totalPointUsed));
//    				memberService.update(member);
//    			}
    			
    			
                log.info("退款成功");
                addMessage(redirectAttributes, "退款成功");
                
            }
            
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
	

}
