package com.fushionbaby.cms.controller.alabao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aladingshop.alabao.dto.AlabaoOrderCMSDto;
import com.aladingshop.alabao.model.AlabaoOrder;
import com.aladingshop.alabao.service.AlabaoOrderService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.auth.service.AuthUserService;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.controller.alabao.excel.AlabaoOrderExcelReport;
import com.fushionbaby.cms.dto.AlabaoOrderPaymentDto;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.payment.model.PaymentAppAlabao;
import com.fushionbaby.payment.model.PaymentAppUnion;
import com.fushionbaby.payment.model.PaymentAppWx;
import com.fushionbaby.payment.model.PaymentAppZfb;
import com.fushionbaby.payment.model.PaymentWapZfbJsdz;
import com.fushionbaby.payment.model.PaymentWebUnion;
import com.fushionbaby.payment.model.PaymentWebWx;
import com.fushionbaby.payment.model.PaymentWebZfbJsdz;
import com.fushionbaby.payment.service.PaymentAppAlabaoService;
import com.fushionbaby.payment.service.PaymentAppUnionService;
import com.fushionbaby.payment.service.PaymentAppWxService;
import com.fushionbaby.payment.service.PaymentAppZfbService;
import com.fushionbaby.payment.service.PaymentWapZfbJsdzService;
import com.fushionbaby.payment.service.PaymentWebUnionService;
import com.fushionbaby.payment.service.PaymentWebWxService;
import com.fushionbaby.payment.service.PaymentWebZfbDbjyService;
import com.fushionbaby.payment.service.PaymentWebZfbJsdzService;

@Controller
@RequestMapping("alabaoOrder")
public class AlabaoOrderController extends BaseController {

	@Autowired
	private AlabaoOrderService<AlabaoOrder> alabaoOrderService;
	
	@Autowired
	private AuthUserService<AuthUser> authUserService;
	
	@Autowired
	private MemberService<Member> memberService;
	@Autowired
	private PaymentWebZfbJsdzService<PaymentWebZfbJsdz> paymentWebZfbJsdzService;
	@Autowired
	private PaymentWapZfbJsdzService<PaymentWapZfbJsdz> paymentWapZfbJsdzService;
	
	@Autowired
	private PaymentAppZfbService<PaymentAppZfb> paymentAppZfbService;
	
	@Autowired
	private PaymentWebUnionService<PaymentWebUnion> paymentWebUnionService;
	
	@Autowired
	private PaymentWebZfbDbjyService paymentWebZfbDbjyService;
	
	@Autowired
	private PaymentAppUnionService<PaymentAppUnion> paymentAppUnionService;
	
	@Autowired
	private PaymentWebWxService<PaymentWebWx> paymentWebWxService;
	
	@Autowired
	private PaymentAppWxService<PaymentAppWx> paymentAppWxService;

	@Autowired
	private PaymentAppAlabaoService<PaymentAppAlabao> paymentAppAlabaoService;
	
	
	
	private static final Log logger = LogFactory.getLog(AlabaoOrderController.class);

	private static final String PRE_="models/alabao/order/";

	@SuppressWarnings("unchecked")
	@RequestMapping("orderList")
	public String findAll(AlabaoOrderCMSDto alabaoOrderCMSDto,BasePagination page, Model model) {
		try {
				if (page == null) {
					page = new BasePagination();
				}
				
				Map<String, String> params = this.setParamByOrderDto(alabaoOrderCMSDto);
				page.setParams(params);
				
				page = this.alabaoOrderService.getListPage(page);
				String listPageTotalActual = this.alabaoOrderService.getListPageTotalActual(page);
				List<AlabaoOrder> alabaoOrderLists = (List<AlabaoOrder>) page.getResult();
				List<AlabaoOrder> alabaoOrderList = new ArrayList<AlabaoOrder>();
				for (AlabaoOrder alabaoOrder : alabaoOrderLists) {
					if(alabaoOrder.getUpdateId() != null){
						AuthUser authUser = authUserService.findById(alabaoOrder.getUpdateId());
						alabaoOrder.setUpdateName(authUser.getLoginName());
					}
					alabaoOrderList.add(alabaoOrder);
				}
				model.addAttribute("alabaoOrderCMSDto",alabaoOrderCMSDto);
				model.addAttribute("paymentStateMap",PaymentTypeEnum.getPaymentTypeMap());
				model.addAttribute("sourceMap",SourceConstant.getSourceArray());
				model.addAttribute("list", alabaoOrderList);
				model.addAttribute("page", page);
				model.addAttribute("listPageTotalActual", listPageTotalActual);
				return PRE_+"orderList";
			} catch (Exception e) {
				logger.error("查询阿拉宝订单列表失败", e);
				return "";
			}
	}
	
	

	/**
	 * 这里传了page对象,但程序中没有使用page,因为jsp的form中存在page的隐藏参数,为了不报错;
	 * jsp查询的form和导出excel的form公用的,只是用js修改了action提交地址
	 * @param orderDto
	 * @param page
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("export_alabao_order")
	public void exportExcelOrderList(AlabaoOrderCMSDto alabaoOrderCMSDto,BasePagination page, HttpServletResponse response, ModelMap model){
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = setParamByOrderDto(alabaoOrderCMSDto);
			page.setParams(params);
			page = alabaoOrderService.getListPage(page);
			model.addAttribute("page", page);
			model.addAttribute("alabaoOrderCMSDto", alabaoOrderCMSDto);
			// 获取到订单状态Map
			model.addAttribute("orderStateMap",OrderConfigServerEnum.getOrderConfigServerMap());
			// 获取到支付状态Map
			model.addAttribute("paymentStateMap",PaymentTypeEnum.getPaymentTypeMap());
			ArrayList<AlabaoOrder> alabaoOrderList=(ArrayList<AlabaoOrder>) page.getResult();
			model.addAttribute("alabaoOrderList",alabaoOrderList);
			List<AlabaoOrderCMSDto> orderList=getAlabaoOrderCMSDtoList(alabaoOrderList);
			AlabaoOrderExcelReport report = new AlabaoOrderExcelReport();
			String dateString = new String(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).getBytes("ISO-8859-1"),"UTF-8");
			report.getReport("导出Excel"+dateString, orderList, response);
		} catch (Exception e) {
			logger.error("导出Excel失败"+e.getMessage(), e);
		}
	}


	/***
	 * 获得要导出的阿拉宝订单信息对象集合
	 * @param alabaoOrderList
	 * @return
	 */
	private List<AlabaoOrderCMSDto> getAlabaoOrderCMSDtoList(ArrayList<AlabaoOrder> alabaoOrderList) {
		
		List<AlabaoOrderCMSDto> AlabaoOrderCMSDtoList=new ArrayList<AlabaoOrderCMSDto>();
		for (AlabaoOrder alabaoOrder : alabaoOrderList) {
			AlabaoOrderCMSDto alabaoOrderCMSDto=new AlabaoOrderCMSDto();
			alabaoOrderCMSDto.setAccount(alabaoOrder.getAccount());
			alabaoOrderCMSDto.setAlabaoStatus(CommonConstant.YES.equals(alabaoOrder.getAlabaoStatus())?"已付款":"未付款");
			alabaoOrderCMSDto.setMemberName(alabaoOrder.getMemberName());
			alabaoOrderCMSDto.setOrderCode(alabaoOrder.getOrderCode());
			alabaoOrderCMSDto.setPaymentType(PaymentTypeEnum.getPaymentTypeMap().get(alabaoOrder.getPaymentType()));
			alabaoOrderCMSDto.setTotalActual(String.valueOf(alabaoOrder.getTotalActual()));
			alabaoOrderCMSDto.setCreateTimeTo(DateFormat.dateToString(alabaoOrder.getCreateTime()));
			AlabaoOrderCMSDtoList.add(alabaoOrderCMSDto);
			
			String paymentType = alabaoOrder.getPaymentType();
			String orderCode = alabaoOrder.getOrderCode();
			Long memberId = alabaoOrder.getMemberId();
			if(PaymentTypeEnum.PAYMENT_ZFB_WEB_JSDZ.getCode().equals(paymentType)){
				PaymentWebZfbJsdz paymentDetailWebZfb=paymentWebZfbJsdzService.queryByMemberIdAndOrderCode(memberId, orderCode);
				alabaoOrderCMSDto.setOrderNumber(paymentDetailWebZfb.getOrderNumber());
				alabaoOrderCMSDto.setTradeNo(paymentDetailWebZfb.getZfbTradeNo());
			}else if(PaymentTypeEnum.PAYMENT_ZFB_APP.getCode().equals(paymentType)){
				PaymentAppZfb paymentDetailAppZfb=paymentAppZfbService.getByMemberIdAndOrderCode(memberId, orderCode);
				alabaoOrderCMSDto.setOrderNumber(paymentDetailAppZfb.getOrderNumber());
				alabaoOrderCMSDto.setTradeNo(paymentDetailAppZfb.getZfbTradeNo());
			}else if(PaymentTypeEnum.PAYMENT_WX_WEB.getCode().equals(paymentType)){
				PaymentWebWx paymentWebWx=paymentWebWxService.getByMemberIdAndOrderCode(memberId, orderCode);
				alabaoOrderCMSDto.setOrderNumber(paymentWebWx.getOrderNumber());
				alabaoOrderCMSDto.setTradeNo(paymentWebWx.getWxTransactionId());
			}else if(PaymentTypeEnum.PAYMENT_WX_APP.getCode().equals(paymentType)){
				PaymentAppWx paymentAppWx=paymentAppWxService.getByMemberIdAndOrderCode(memberId, orderCode);
				alabaoOrderCMSDto.setOrderNumber(paymentAppWx.getOrderNumber());
				alabaoOrderCMSDto.setTradeNo(paymentAppWx.getWxTransactionId());
			}else if(PaymentTypeEnum.PAYMENT_ZXYL_WEB.getCode().equals(paymentType)){
				PaymentWebUnion paymentWebUnion=paymentWebUnionService.getByMemberIdAndOrderCode(memberId, orderCode);
				alabaoOrderCMSDto.setOrderNumber(paymentWebUnion.getOrderNumber());
				alabaoOrderCMSDto.setTradeNo(paymentWebUnion.getTn());
			}else if(PaymentTypeEnum.PAYMENT_ZXYL_APP.getCode().equals(paymentType)){
				PaymentAppUnion paymentAppUnion=paymentAppUnionService.getByMemberIdAndOrderCode(memberId, orderCode);
				alabaoOrderCMSDto.setOrderNumber(paymentAppUnion.getOrderNumber());
				alabaoOrderCMSDto.setTradeNo(paymentAppUnion.getTn());
			}
		}
		return AlabaoOrderCMSDtoList;
	}
	/***
	 * 条件查询
	 * @param alabaoOrderCMSDto
	 * @return
	 */
	private Map<String, String> setParamByOrderDto(AlabaoOrderCMSDto alabaoOrderCMSDto) {
		Map<String, String> map =new HashMap<String, String>();
		map.put("orderCode", alabaoOrderCMSDto.getOrderCode());
		map.put("memberName", alabaoOrderCMSDto.getMemberName());
		map.put("account", alabaoOrderCMSDto.getAccount());
		map.put("createTimeFrom", alabaoOrderCMSDto.getCreateTimeFrom());
		map.put("createTimeTo", alabaoOrderCMSDto.getCreateTimeTo());
		map.put("alabaoStatus",alabaoOrderCMSDto.getAlabaoStatus() );
		return map;
	}
	
	
	@RequestMapping("/orderPayInfo")
	public String orderPayInfo(@RequestParam(value="id",defaultValue= "")Long id,ModelMap model){
		
		try {
			AlabaoOrder alabaoOrder = alabaoOrderService.findById(id);
			String paymentType = alabaoOrder.getPaymentType();
			String orderCode = alabaoOrder.getOrderCode();
			Long memberId = alabaoOrder.getMemberId();
			AlabaoOrderPaymentDto AlabaoOrderPaymentDto = new AlabaoOrderPaymentDto();
			
			AlabaoOrderPaymentDto.setMemberId(memberId);
			AlabaoOrderPaymentDto.setOrderCode(orderCode);
			
			if(PaymentTypeEnum.PAYMENT_ZFB_WEB_JSDZ.getCode().equals(paymentType)){
				PaymentWebZfbJsdz paymentDetailWebZfb=paymentWebZfbJsdzService.queryByMemberIdAndOrderCode(memberId, orderCode);
				
				AlabaoOrderPaymentDto.setId(paymentDetailWebZfb.getId());
				AlabaoOrderPaymentDto.setOrderDes(paymentDetailWebZfb.getOrderDes());
				AlabaoOrderPaymentDto.setOrderNumber(paymentDetailWebZfb.getOrderNumber());
				AlabaoOrderPaymentDto.setRemoteAddr(paymentDetailWebZfb.getRemoteAddr());
				String settleAmount = paymentDetailWebZfb.getSettleAmount();
				settleAmount = settleAmount.substring(0, settleAmount.length()-2)+"."+settleAmount.substring(settleAmount.length()-2, settleAmount.length());
				AlabaoOrderPaymentDto.setSettleAmount(settleAmount);
				AlabaoOrderPaymentDto.setStatus(paymentDetailWebZfb.getZfbStatus());
				AlabaoOrderPaymentDto.setTradeNo(paymentDetailWebZfb.getZfbTradeNo());
				AlabaoOrderPaymentDto.setTradeTime(paymentDetailWebZfb.getTradeTime());
				AlabaoOrderPaymentDto.setUpdateTime(paymentDetailWebZfb.getUpdateTime());
				AlabaoOrderPaymentDto.setCreateTime(paymentDetailWebZfb.getCreateTime());
				
			}else if(PaymentTypeEnum.PAYMENT_ZFB_APP.getCode().equals(paymentType)){
				PaymentAppZfb paymentDetailAppZfb=paymentAppZfbService.getByMemberIdAndOrderCode(memberId, orderCode);
				
				AlabaoOrderPaymentDto.setId(paymentDetailAppZfb.getId());
				AlabaoOrderPaymentDto.setOrderDes(paymentDetailAppZfb.getOrderDes());
				AlabaoOrderPaymentDto.setOrderNumber(paymentDetailAppZfb.getOrderNumber());
				AlabaoOrderPaymentDto.setRemoteAddr(paymentDetailAppZfb.getRemoteAddr());
				String settleAmount = paymentDetailAppZfb.getSettleAmount();
				settleAmount = settleAmount.substring(0, settleAmount.length()-2)+"."+settleAmount.substring(settleAmount.length()-2, settleAmount.length());
				AlabaoOrderPaymentDto.setSettleAmount(settleAmount);
				AlabaoOrderPaymentDto.setStatus(String.valueOf(paymentDetailAppZfb.getZfbStatus()));
				AlabaoOrderPaymentDto.setTradeNo(paymentDetailAppZfb.getZfbTradeNo());
				AlabaoOrderPaymentDto.setTradeTime(paymentDetailAppZfb.getTradeTime());
				AlabaoOrderPaymentDto.setUpdateTime(paymentDetailAppZfb.getUpdateTime());
				AlabaoOrderPaymentDto.setCreateTime(paymentDetailAppZfb.getCreateTime());
				
			}else if(PaymentTypeEnum.PAYMENT_WX_WEB.getCode().equals(paymentType)){
				PaymentWebWx paymentWebWx=paymentWebWxService.getByMemberIdAndOrderCode(memberId, orderCode);
				
				AlabaoOrderPaymentDto.setId(paymentWebWx.getId());
				AlabaoOrderPaymentDto.setOrderDes(paymentWebWx.getOrderDes());
				AlabaoOrderPaymentDto.setOrderNumber(paymentWebWx.getOrderNumber());
				AlabaoOrderPaymentDto.setRemoteAddr(paymentWebWx.getRemoteAddr());
				String settleAmount = paymentWebWx.getSettleAmount();
				settleAmount = settleAmount.substring(0, settleAmount.length()-2)+"."+settleAmount.substring(settleAmount.length()-2, settleAmount.length());
				AlabaoOrderPaymentDto.setSettleAmount(settleAmount);
				AlabaoOrderPaymentDto.setStatus(String.valueOf(paymentWebWx.getWxStatus()));
				AlabaoOrderPaymentDto.setTradeNo(paymentWebWx.getWxTransactionId());
				AlabaoOrderPaymentDto.setTradeTime(paymentWebWx.getTradeTime());
				AlabaoOrderPaymentDto.setUpdateTime(paymentWebWx.getUpdateTime());
				AlabaoOrderPaymentDto.setCreateTime(paymentWebWx.getCreateTime());
				
			}else if(PaymentTypeEnum.PAYMENT_WX_APP.getCode().equals(paymentType)){
				PaymentAppWx paymentAppWx=paymentAppWxService.getByMemberIdAndOrderCode(memberId, orderCode);
				
				AlabaoOrderPaymentDto.setId(paymentAppWx.getId());
				AlabaoOrderPaymentDto.setOrderDes(paymentAppWx.getOrderDes());
				AlabaoOrderPaymentDto.setOrderNumber(paymentAppWx.getOrderNumber());
				AlabaoOrderPaymentDto.setRemoteAddr(paymentAppWx.getRemoteAddr());
				String settleAmount = paymentAppWx.getSettleAmount();
				settleAmount = settleAmount.substring(0, settleAmount.length()-2)+"."+settleAmount.substring(settleAmount.length()-2, settleAmount.length());
				AlabaoOrderPaymentDto.setSettleAmount(settleAmount);
				AlabaoOrderPaymentDto.setStatus(String.valueOf(paymentAppWx.getWxStatus()));
				AlabaoOrderPaymentDto.setTradeNo(paymentAppWx.getWxTransactionId());
				AlabaoOrderPaymentDto.setTradeTime(paymentAppWx.getTradeTime());
				AlabaoOrderPaymentDto.setUpdateTime(paymentAppWx.getUpdateTime());
				AlabaoOrderPaymentDto.setCreateTime(paymentAppWx.getCreateTime());
				
			}else if(PaymentTypeEnum.PAYMENT_ZXYL_WEB.getCode().equals(paymentType)){
				PaymentWebUnion paymentWebUnion=paymentWebUnionService.getByMemberIdAndOrderCode(memberId, orderCode);
				
				AlabaoOrderPaymentDto.setId(paymentWebUnion.getId());
				AlabaoOrderPaymentDto.setOrderDes(paymentWebUnion.getOrderDes());
				AlabaoOrderPaymentDto.setOrderNumber(paymentWebUnion.getOrderNumber());
				AlabaoOrderPaymentDto.setRemoteAddr(paymentWebUnion.getRemoteAddr());
				String settleAmount = paymentWebUnion.getSettleAmount();
				settleAmount = settleAmount.substring(0, settleAmount.length()-2)+"."+settleAmount.substring(settleAmount.length()-2, settleAmount.length());
				AlabaoOrderPaymentDto.setSettleAmount(settleAmount);
				AlabaoOrderPaymentDto.setStatus(String.valueOf(paymentWebUnion.getUnionStatus()));
				AlabaoOrderPaymentDto.setTradeNo(paymentWebUnion.getTn());
				AlabaoOrderPaymentDto.setTradeTime(paymentWebUnion.getTradeTime());
				AlabaoOrderPaymentDto.setUpdateTime(paymentWebUnion.getUpdateTime());
				AlabaoOrderPaymentDto.setCreateTime(paymentWebUnion.getCreateTime());
				
			}else if(PaymentTypeEnum.PAYMENT_ZXYL_APP.getCode().equals(paymentType)){
				PaymentAppUnion paymentAppUnion=paymentAppUnionService.getByMemberIdAndOrderCode(memberId, orderCode);
				
				AlabaoOrderPaymentDto.setId(paymentAppUnion.getId());
				AlabaoOrderPaymentDto.setOrderDes(paymentAppUnion.getOrderDes());
				AlabaoOrderPaymentDto.setOrderNumber(paymentAppUnion.getOrderNumber());
				AlabaoOrderPaymentDto.setRemoteAddr(paymentAppUnion.getRemoteAddr());
				String settleAmount = paymentAppUnion.getSettleAmount();
				settleAmount = settleAmount.substring(0, settleAmount.length()-2)+"."+settleAmount.substring(settleAmount.length()-2, settleAmount.length());
				AlabaoOrderPaymentDto.setSettleAmount(settleAmount);
				AlabaoOrderPaymentDto.setStatus(String.valueOf(paymentAppUnion.getUnionStatus()));
				AlabaoOrderPaymentDto.setTradeNo(paymentAppUnion.getTn());
				AlabaoOrderPaymentDto.setTradeTime(paymentAppUnion.getTradeTime());
				AlabaoOrderPaymentDto.setUpdateTime(paymentAppUnion.getUpdateTime());
				AlabaoOrderPaymentDto.setCreateTime(paymentAppUnion.getCreateTime());
				
			}
			
			model.addAttribute("paymentTypeMap", PaymentTypeEnum.getPaymentTypeMap());
			model.addAttribute("AlabaoOrderPaymentDto", AlabaoOrderPaymentDto);
		} catch (Exception e) {
			logger.error("查询转入日志表错误！",e);
			return "";
		}
		
		return PRE_+"orderPaymentInfo";
	}
	
	
	
}
