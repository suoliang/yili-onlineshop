/**
 * 
 */
package com.fushionbaby.cms.controller.refund;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.refund.model.Refund;
import com.fushionbaby.cms.controller.BaseController;
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

/**
 * @description 类描述...
 * @author 徐鹏飞
 * @date 2015年8月17日下午1:44:35
 */

@Controller
@RequestMapping("/unionRefund")
public class UnoinRefundController extends BaseController{
	
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
	private AppUnionRefundService appUnionRefundService;
	
	private static final Log log=LogFactory.getLog(UnoinRefundController.class);

    /**
     * 调用退款业务逻辑
     * @param refundReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 业务逻辑可能走到的结果分支，需要商户处理
     * @throws Exception
     */
    @RequestMapping("unionRefund/{memberId}/{orderCode}/{pageFrom}")
    public void refund(@PathVariable Long memberId,@PathVariable String orderCode,@PathVariable String pageFrom,HttpServletRequest request) throws Exception {
    	try {
    		appUnionRefundService.refund(request,memberId,orderCode);
    	} catch(Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}	
    }
    
    /**
	 * app后台异步通知
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/backNotify")
	public void notify(HttpServletRequest request) {
		try {
			appUnionRefundService.notifyBack(request);
		} catch(Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}		
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
