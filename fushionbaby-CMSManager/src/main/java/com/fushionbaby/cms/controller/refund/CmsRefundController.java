/**
 * 
 */
package com.fushionbaby.cms.controller.refund;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.finance.model.FinanceRefundApply;
import com.aladingshop.finance.service.FinanceRefundApplyService;
import com.aladingshop.periodactivity.model.ActivityRedEnvlopeUseRecord;
import com.aladingshop.periodactivity.model.ActivityShareMember;
import com.aladingshop.periodactivity.service.ActivityRedEnvlopeUseRecordService;
import com.aladingshop.periodactivity.service.ActivityShareMemberService;
import com.aladingshop.refund.model.Refund;
import com.aladingshop.refund.service.RefundService;
import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.config.model.SysmgrDictConfig;
import com.fushionbaby.config.service.SysmgrDictConfigService;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.order.model.OrderBase;
import com.fushionbaby.order.model.OrderFinance;
import com.fushionbaby.order.model.OrderTrace;
import com.fushionbaby.order.service.OrderBaseService;
import com.fushionbaby.order.service.OrderFinanceService;
import com.fushionbaby.order.service.OrderTraceService;
import com.fushionbaby.payment.model.PaymentAppZfb;
import com.fushionbaby.payment.service.PaymentAppZfbService;

/**
 * @description 类描述...
 * @author 徐鹏飞
 * @date 2015年8月17日下午1:44:35
 */

@Controller
@RequestMapping("/cmsRefund")
public class CmsRefundController {
	
	@Autowired
	private PaymentAppZfbService<PaymentAppZfb> appZfbService;
	@Autowired
	private RefundService refundService;
	@Autowired
	private OrderFinanceService<OrderFinance> orderFinanceService;
	@Autowired
	private OrderBaseService<OrderBase> orderBaseService;
	@Autowired
	private OrderTraceService<OrderTrace> orderTraceService;
	@Autowired
	private SysmgrImportanceConfigService sysmgrImportanceConfigService;
	@Autowired
	ActivityShareMemberService<ActivityShareMember> activityShareMemberService;
	@Autowired
	ActivityRedEnvlopeUseRecordService<ActivityRedEnvlopeUseRecord> activityRedEnvlopeUseRecordService;
	@Autowired
	MemberService<Member> memberService;
	@Autowired
	private FinanceRefundApplyService financeRefundApplyService;
	
	/**系统字典配置*/
	@Autowired
	private SysmgrDictConfigService<SysmgrDictConfig> sysmgrDictConfigService;
	
	private static final Log log=LogFactory.getLog(CmsRefundController.class);
	
	@RequestMapping("cmsRefund/{memberId}/{orderCode}/{pageFrom}")
	public void cmsRefund(@PathVariable Long memberId,@PathVariable String orderCode,@PathVariable String pageFrom,HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		log.info("cms:CmsRefundController  后台退款处理  cmsRefund  begin *************");
		String notify_url = sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.ZFBREFUNDNOTIFYURL).getValue();
		//卖家支付宝帐户
		String seller_email = AlipayConfig.seller_email;
		//退款当天日期
		String refund_date = new String(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()).getBytes("ISO-8859-1"),"UTF-8");
		//批次号
		String batch_no = new String(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).getBytes("ISO-8859-1"),"UTF-8");
		log.info("cms:CmsRefundController  后台退款处理  批次号为*************"+batch_no);
		//退款笔数
		String batch_num = "1";
		//退款详细数据
		PaymentAppZfb paymentAppZfb=appZfbService.getByMemberIdAndOrderCode(memberId, orderCode);
		String zfbTradeNo=paymentAppZfb.getZfbTradeNo();
		DecimalFormat df = new DecimalFormat("0.00");
		String settleAmount = df.format(new BigDecimal(paymentAppZfb.getSettleAmount())
				.multiply(BigDecimal.valueOf(0.01)));
		String detail_data=zfbTradeNo+"^"+settleAmount+"^协商退款";
		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "refund_fastpay_by_platform_pwd");
        sParaTemp.put("partner", AlipayConfig.partner);
        sParaTemp.put("_input_charset", AlipayConfig.input_charset);
		sParaTemp.put("notify_url", notify_url);
		sParaTemp.put("seller_email", seller_email);
		sParaTemp.put("refund_date", refund_date);
		sParaTemp.put("batch_no", batch_no);
		sParaTemp.put("batch_num", batch_num);
		sParaTemp.put("detail_data", detail_data);
		
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
		refund.setRefundStatus("refunding");
		Refund exitRefund=refundService.findByMemberIdAndOrderCode(memberId, orderCode);
		if(ObjectUtils.equals(null, exitRefund)){
			refundService.add(refund);
		}else{
			refundService.updateByMemberIdAndOrderCode(refund);;
		}
		
		//建立请求
		
		String sHtmlText = AlipaySubmit.buildRequest(sParaTemp,"get","确认");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		String htmlTmp = "<html><head><meta charset=\"UTF-8\"><title>alading refund</title><body>" + sHtmlText + "</body></html>";
		response.getWriter().print(htmlTmp);
	}
	
	@RequestMapping("notify")
	public void notify(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//批次号

		String batch_no = new String(request.getParameter("batch_no").getBytes("ISO-8859-1"),"UTF-8");

		//批量退款数据中转账成功的笔数

	//	String success_num = new String(request.getParameter("success_num").getBytes("ISO-8859-1"),"UTF-8");

		//批量退款数据中的详细信息
	//	String result_details = new String(request.getParameter("result_details").getBytes("ISO-8859-1"),"UTF-8");

		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

		if(AlipayNotify.verify(params)){//验证成功
			//////////////////////////////////////////////////////////////////////////////////////////
			//请在这里加上商户的业务逻辑程序代码

			//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
			Refund refund=refundService.findByBatchNo(batch_no);
			Long memberId=refund.getMemberId();
			String orderCode=refund.getOrderCode();
			refund.setRefundStatus("refundSuccess");
			refundService.updateByBatchNo(refund);
			
			OrderBase orderBase=new OrderBase();
			orderBase.setMemberId(memberId);
			orderBase.setOrderCode(orderCode);
			orderBase.setOrderStatus(OrderConfigServerEnum.USER_REFUND.getCode());
			orderBaseService.update(orderBase);
			
			OrderTrace orderTrace=new OrderTrace();
			orderTrace.setMemberId(memberId);
			orderTrace.setOrderCode(orderCode);
			orderTrace.setOrderStatus(OrderConfigServerEnum.USER_REFUND.getCode());
			orderTraceService.updateByMemberIdAndOrderCode(orderTrace);
			
			
			FinanceRefundApply financeRefundApply=financeRefundApplyService.findByMemberIdAndOrderCode(memberId, orderCode);
			if(financeRefundApply!=null){
				financeRefundApply.setStatus("2");
				financeRefundApplyService.updateByMemberIdAndOrderCode(financeRefundApply);
			}
			
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
				refundService.updateByMemberIdAndOrderCode(refund);
				
			}
			/**退积分*/
//			orderBase=orderBaseService.findByMemberIdAndOrderCode(memberId, orderCode);
//			if(!ObjectUtils.equals(null, orderBase.getTotalPointUsed())){
//				BigDecimal totalPointUsed=orderBase.getTotalPointUsed();
//				Member member=memberService.findById(memberId);
//				member.setEpoints(member.getEpoints().add(totalPointUsed));
//				memberService.update(member);
//			}
			//判断是否在商户网站中已经做过了这次通知返回的处理
				//如果没有做过处理，那么执行商户的业务程序
				//如果有做过处理，那么不执行商户的业务程序
				
			response.getWriter().println("success");	//请不要修改或删除
			//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——

			//////////////////////////////////////////////////////////////////////////////////////////
		}else{//验证失败
			response.getWriter().println("fail");
		}
	}
	
	@ResponseBody
	@RequestMapping("checkRefund/{memberId}/{orderCode}")
	public String checkRefund(@PathVariable Long memberId,@PathVariable String orderCode){
		Refund exitRefund=refundService.findByMemberIdAndOrderCode(memberId, orderCode);
		if(ObjectUtils.equals(null, exitRefund)){
			return "false";
		}else if("refundSuccess".equals(exitRefund.getRefundStatus())){
			return "true";
		}
		return "false";
	}
	
	
	/***
	 * 校验退款的操作密码是否正确
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkRefundPassword")
	public boolean checkRefundPassword(String password){
		boolean result=false;
		List<SysmgrDictConfig> dictConfig=sysmgrDictConfigService.findByLabelValueType(null, password, "ALABAO_REFUND_PASSWORD");
		if(dictConfig!=null&&dictConfig.size()>0)
			{
			 result=true;
			}
		return result;
	}	
	
	
	
	
	
}
