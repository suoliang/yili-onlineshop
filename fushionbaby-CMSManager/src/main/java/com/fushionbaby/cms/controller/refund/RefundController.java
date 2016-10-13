/**
 * 
 */
package com.fushionbaby.cms.controller.refund;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.FinanceRefundApplyDto;
import com.fushionbaby.cms.dto.RefundDto;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.model.SysmgrSourceConfig;
import com.fushionbaby.config.service.SysmgrSourceConfigService;
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
@RequestMapping("/refund")
public class RefundController extends BaseController{
	
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
	private SysmgrSourceConfigService<SysmgrSourceConfig> sysmgrSourceService;
	@Autowired
	private FinanceRefundApplyService financeRefundApplyService;

	
	private static final Log log=LogFactory.getLog(RefundController.class);


    /**
     * 调用退款业务逻辑
     * @param refundReqData 这个数据对象里面包含了API要求提交的各种数据字段
     * @param resultListener 业务逻辑可能走到的结果分支，需要商户处理
     * @throws Exception
     */
  
    
    @SuppressWarnings("unchecked")
	@RequestMapping("refundList")
	public String alabaoRefund(RefundDto refundDto,BasePagination page, Model model){
    	try {
    		model.addAttribute("refundDto", refundDto);
    		model.addAttribute("paymentStateMap",PaymentTypeEnum.getPaymentTypeMap());
    		model.addAttribute("sourceMap",SourceConstant.getSourceArray());
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			
			params.put("orderCode",refundDto.getOrderCode());
			params.put("paymentType", refundDto.getPaymentType());
			params.put("sourceCode",refundDto.getSourceCode());
			params.put("createTimeFrom", refundDto.getCreateTimeFrom());
			params.put("createTimeTo", refundDto.getCreateTimeTo());
			
			page.setParams(params);
			// 分页对象
			page = aladingRefundService.getListPage(page);
			List<Refund> refundList=(List<Refund>) page.getResult();
			List<RefundDto> refundDtoList=new ArrayList<RefundDto>();
			for(Refund refund:refundList){
				RefundDto refundDtotmp=new RefundDto();
				Long memberId=refund.getMemberId();
				Member membertmp=memberService.findById(memberId);
				if(membertmp!=null)
					refundDtotmp.setMemberName(membertmp.getLoginName());
				refundDtotmp.setMemberId(memberId);
				refundDtotmp.setBatchNo(refund.getBatchNo());	
				refundDtotmp.setCreateId(refund.getCreateId());
				refundDtotmp.setCreateTime(refund.getCreateTime());
				refundDtotmp.setOrderCode(refund.getOrderCode());
				refundDtotmp.setPaymentType(refund.getPaymentType());
				refundDtotmp.setRedEnvlopeAmount(refund.getRedEnvlopeAmount());
				refundDtotmp.setSettleAmount(refund.getSettleAmount());
				refundDtotmp.setSourceCode(refund.getSourceCode());
				refundDtoList.add(refundDtotmp);
			}
			page.setResult(refundDtoList);
			model.addAttribute("page", page);
			return  "models/refund/refundList";
		} catch (Exception e) {
			log.error("退款列表加载失败", e);
			return "";
		}
	}	
    
    @SuppressWarnings("unchecked")
	@RequestMapping("refundApplyList")
	public String refundApplyList(HttpSession session,FinanceRefundApplyDto refundApplyDto,BasePagination page, Model model) {
    	try {
    		model.addAttribute("refundApplyDto", refundApplyDto);
    		model.addAttribute("paymentStateMap",PaymentTypeEnum.getPaymentTypeMap());
    		model.addAttribute("sourceMap",SourceConstant.getSourceArray());
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			
			params.put("orderCode",refundApplyDto.getOrderCode());
			params.put("status",refundApplyDto.getStatus());
			params.put("orderPayType", refundApplyDto.getOrderPayType());
			params.put("orderSource",refundApplyDto.getOrderSource());
			params.put("createTimeFrom", refundApplyDto.getCreateTimeFrom());
			params.put("createTimeTo", refundApplyDto.getCreateTimeTo());
			
			page.setParams(params);
			// 分页对象
			page = financeRefundApplyService.getListPage(page);
			List<FinanceRefundApply> refundApplyList=(List<FinanceRefundApply>) page.getResult();
			List<FinanceRefundApplyDto> refundApplyDtoList=new ArrayList<FinanceRefundApplyDto>();
			for(FinanceRefundApply refundApply:refundApplyList){
				FinanceRefundApplyDto refundDtotmp=new FinanceRefundApplyDto();
				Long memberId=refundApply.getMemberId();
				Member membertmp=memberService.findById(memberId);
				refundDtotmp.setMemberName(membertmp==null?"":membertmp.getLoginName());
				refundDtotmp.setDealId(refundApply.getDealId());
				refundDtotmp.setCreateTime(refundApply.getCreateTime());
				refundDtotmp.setOrderCode(refundApply.getOrderCode());
				refundDtotmp.setOrderPayType(refundApply.getOrderPayType());
				refundDtotmp.setDealName(CMSSessionUtils.getSessionUser(session).getLoginName());
				refundDtotmp.setDealTime(refundApply.getDealTime());
				refundDtotmp.setMemberId(memberId);
				refundDtotmp.setOrderSource(refundApply.getOrderSource());
				refundDtotmp.setRefundReason(refundApply.getRefundReason());
				refundDtotmp.setStatus(refundApply.getStatus());
				refundDtotmp.setUpdateTime(refundApply.getUpdateTime());
				OrderFinance ordertmp=orderFinanceService.findByMemberIdAndOrderCode(memberId, refundApply.getOrderCode());
				String paymentTotalActual=ordertmp.getPaymentTotalActual().setScale(2, BigDecimal.ROUND_HALF_UP).toString();
				refundDtotmp.setPaymentTotalActual(paymentTotalActual);
				refundApplyDtoList.add(refundDtotmp);
			}
			page.setResult(refundApplyDtoList);
			model.addAttribute("page", page);
			return  "models/refund/refundApplyList";
		} catch (Exception e) {
			log.error("退款申请列表加载失败", e);
			return "";
		}
	}	
    
}
