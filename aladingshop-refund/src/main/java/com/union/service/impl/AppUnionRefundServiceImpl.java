/**
 * 
 */
package com.union.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aladingshop.finance.model.FinanceRefundApply;
import com.aladingshop.finance.service.FinanceRefundApplyService;
import com.aladingshop.periodactivity.model.ActivityRedEnvlopeUseRecord;
import com.aladingshop.periodactivity.model.ActivityShareMember;
import com.aladingshop.periodactivity.service.ActivityRedEnvlopeUseRecordService;
import com.aladingshop.periodactivity.service.ActivityShareMemberService;
import com.aladingshop.refund.model.Refund;
import com.fushionbaby.common.constants.config.ImportanceConfigConstant;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.config.service.SysmgrImportanceConfigService;
import com.fushionbaby.order.model.OrderBase;
import com.fushionbaby.order.model.OrderFinance;
import com.fushionbaby.order.model.OrderTrace;
import com.fushionbaby.order.service.OrderBaseService;
import com.fushionbaby.order.service.OrderFinanceService;
import com.fushionbaby.order.service.OrderTraceService;
import com.fushionbaby.payment.model.PaymentAppUnion;
import com.fushionbaby.payment.service.PaymentAppUnionService;
import com.union.service.AppUnionRefundService;
import com.union.service.DemoBase;
import com.unionpay.acp.sdk.LogUtil;
import com.unionpay.acp.sdk.SDKConfig;
import com.unionpay.acp.sdk.SDKConstants;
import com.unionpay.acp.sdk.SDKUtil;

/**
 * @description 类描述...
 * @author 索亮
 * @date 2015年8月11日下午6:13:42
 */
@Service
public class AppUnionRefundServiceImpl extends DemoBase implements AppUnionRefundService {

	@Autowired
	private PaymentAppUnionService<PaymentAppUnion> appUnionService;
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
	private FinanceRefundApplyService financeRefundApplyService;
	
	public void refund(HttpServletRequest request, Long memberId,
			String orderCode) {
		PaymentAppUnion paymentAppUnion=appUnionService.getByMemberIdAndOrderCode(memberId, orderCode);
		Integer totalFee=Integer.parseInt(paymentAppUnion.getSettleAmount());
		Integer refundFee=totalFee;
		/**
		 * 参数初始化
		 * 在java main 方式运行时必须每次都执行加载
		 * 如果是在web应用开发里,这个方写在可使用监听的方式写入缓存,无须在这出现
		 */
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件

		/**
		 * 组装请求报文
		 */
		Map<String, String> data = new HashMap<String, String>();
		// 版本号
		data.put("version", "5.0.0");
		// 字符集编码 默认"UTF-8"
		data.put("encoding", "UTF-8");
		// 签名方法 01 RSA
		data.put("signMethod", "01");
		// 交易类型 
		data.put("txnType", "04");
		// 交易子类型 
		data.put("txnSubType", "00");
		// 业务类型
		data.put("bizType", "000201");
		// 渠道类型，07-PC，08-手机
		data.put("channelType", "08");
		// 前台通知地址 ，控件接入方式无作用
		data.put("frontUrl", "");
		// 后台通知地址
		String backUrl=sysmgrImportanceConfigService.findByCode(ImportanceConfigConstant.CMSYLREFUNDBACKNOTIFYURL).getValue();
		data.put("backUrl", backUrl);
		// 接入类型，商户接入填0 0- 商户 ， 1： 收单， 2：平台商户
		data.put("accessType", "0");
		// 商户号码，请改成自己的商户号
		data.put("merId", "898320548160279");
		//原消费的queryId，可以从查询接口或者通知接口中获取
		String origQryId=paymentAppUnion.getTn();
		data.put("origQryId", origQryId);
		// 商户订单号，8-40位数字字母，重新产生，不同于原消费
		String orderId=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		data.put("orderId",orderId);
		// 订单发送时间，取系统时间
		data.put("txnTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		// 交易金额
		data.put("txnAmt", refundFee.toString());
		// 交易币种
		data.put("currencyCode", "156");
		// 请求方保留域，透传字段，查询、通知、对账文件中均会原样出现
		data.put("reqReserved", orderCode+","+memberId.toString());
		data = signData(data);

		// 交易请求url 从配置文件读取
		String url = SDKConfig.getConfig().getBackRequestUrl();

		Map<String, String> resmap = submitUrl(data, url);
		if (resmap != null && resmap.get("respCode") != null && resmap.get("respCode").equals("00")) {
			OrderFinance orderFinance = orderFinanceService.findByMemberIdAndOrderCode(memberId, orderCode);
			OrderBase orderBase=orderBaseService.findByMemberIdAndOrderCode(memberId, orderCode);
			DecimalFormat df = new DecimalFormat("0.00");
        	String settleAmount = df.format(new BigDecimal(refundFee.toString()).multiply(BigDecimal.valueOf(0.01)));
			Refund refund=new Refund();
    		refund.setBatchNo(orderId);
    		refund.setMemberId(memberId);
    		refund.setOrderCode(orderCode);
    		refund.setPaymentType(orderFinance.getPaymentType());
    		refund.setSettleAmount(settleAmount);
    		refund.setSourceCode(orderBase.getSourceCode());
    		refund.setRefundStatus("refunding");
    		aladingRefundService.add(refund);
		}
		// resmap 里返回报文中
		// 银联订单号 tn 商户推送订单后银联移动支付系统返回该流水号，商户调用支付控件时使用
		System.out.println("请求报文=["+data.toString()+"]");
		System.out.println("应答报文=["+resmap.toString()+"]");

	}
	
	public void notifyBack(HttpServletRequest req) throws Exception {
		
		SDKConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
		
		LogUtil.writeLog("BackRcvResponse接收后台通知开始");

		req.setCharacterEncoding("ISO-8859-1");
		String encoding = req.getParameter(SDKConstants.param_encoding);
		// 获取请求参数中所有的信息
		Map<String, String> reqParam = getAllRequestParam(req);
		// 打印请求报文
		LogUtil.printRequestLog(reqParam);

		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap<String, String>(reqParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				value = new String(value.getBytes("ISO-8859-1"), encoding);
				valideData.put(key, value);
			}
		}

		// 验证签名
		if (!SDKUtil.validate(valideData, encoding)) {
			LogUtil.writeLog("验证签名结果[失败].");
			
		} else {
			LogUtil.writeLog("验证签名结果[成功].");
			
			String reqReserved=valideData.get("reqReserved");
			String[] reqReservedArray=reqReserved.split(",");
			String orderCode=reqReservedArray[0];
			Long memberId=Long.parseLong(reqReservedArray[1]);

			Refund refund=aladingRefundService.findByMemberIdAndOrderCode(memberId, orderCode);
    		refund.setRefundStatus("refundSuccess");
    		aladingRefundService.updateByMemberIdAndOrderCode(refund);
    		
    		OrderBase orderBase=orderBaseService.findByMemberIdAndOrderCode(memberId, orderCode);
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
    		LogUtil.writeLog("退款成功.");
            
        }
		

		LogUtil.writeLog("BackRcvResponse接收后台通知结束");
	
		
	}

	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				//在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				//System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}


}
