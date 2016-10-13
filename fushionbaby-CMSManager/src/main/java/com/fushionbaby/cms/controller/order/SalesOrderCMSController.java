package com.fushionbaby.cms.controller.order;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.cms.controller.order.excel.OrderExcelReport;
import com.fushionbaby.cms.dto.OrderDto;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.OrderConstant;
import com.fushionbaby.common.constants.PayInterfaceConstants;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.enums.OrderConfigServerEnum;
import com.fushionbaby.common.enums.PaymentTypeEnum;
import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.HttpRequest;
import com.fushionbaby.log.model.LogSendGoodsConfirm;
import com.fushionbaby.log.service.LogSendGoodsConfirmService;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberAddress;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAddressService;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.order.dto.OrderBaseDto;
import com.fushionbaby.order.dto.SoSalesOrderDto;
import com.fushionbaby.order.model.OrderBase;
import com.fushionbaby.order.model.OrderLine;
import com.fushionbaby.order.model.OrderMemberAddress;
import com.fushionbaby.order.model.OrderTrace;
import com.fushionbaby.order.model.OrderTrans;
import com.fushionbaby.order.service.OrderBaseService;
import com.fushionbaby.order.service.OrderLineService;
import com.fushionbaby.order.service.OrderMemberAddressService;
import com.fushionbaby.order.service.OrderTraceService;
import com.fushionbaby.order.service.OrderTransService;
import com.fushionbaby.sms.model.Sms;
import com.fushionbaby.sms.service.SmsService;

/**
 * @author 张明亮
 */
@Controller
@RequestMapping("/order")
public class SalesOrderCMSController {

	private static final Log LOGGER = LogFactory.getLog(SalesOrderCMSController.class);
	
	@Autowired
	private OrderBaseService<OrderBase> orderBaseService;

	@Autowired
	private OrderTransService<OrderTrans> orderTransService;
	@Autowired
	private OrderTraceService<OrderTrace> orderTraceService;
	/**
	 * 订单行信息查询service
	 */
	@Autowired
	private OrderLineService<OrderLine> soSoLineService;

	/**
	 * 订单地址信息操作service
	 */
	@Autowired
	private OrderMemberAddressService<OrderMemberAddress> soSoMemberManageService;

	/** 注入member */
	@Autowired
	private MemberAddressService<MemberAddress> memberAddressService;

	/** 注入短信 */
	@Autowired
	private SmsService<Sms> smsService;

	/** 注入地区 */
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaService;

	/** 确认发货日志 */
	@Autowired
	private LogSendGoodsConfirmService logSendGoodsConfirmService;
	

	@Autowired
	private MemberService<Member> memberService;

	/**
	 * 这里传了page对象,但程序中没有使用page,因为jsp的form中存在page的隐藏参数,为了不报错;
	 * jsp查询的form和导出excel的form公用的,只是用js修改了action提交地址
	 * @param orderDto
	 * @param page
	 */
	@RequestMapping("export_excel_order_list")
	public void exportExcelOrderList(OrderBaseDto orderBaseDto,
			BasePagination page, HttpServletResponse response, ModelMap model){
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = setParamByOrderDto(orderBaseDto);
			page.setParams(params);
			page = orderBaseService.findOrderBaseListByPage(page);
			model.addAttribute("page", page);
			model.addAttribute("orderBaseDto", orderBaseDto);
			// 获取到订单状态Map
			Map orderStateMap=OrderConfigServerEnum.getOrderConfigServerMap();
			model.addAttribute("orderStateMap",orderStateMap);
			// 获取到支付状态Map
			Map paymentStateMap=PaymentTypeEnum.getPaymentTypeMap();
			model.addAttribute("paymentStateMap",paymentStateMap);
			ArrayList<OrderBase> orderBaseList=(ArrayList<OrderBase>) page.getResult();
			model.addAttribute("orderBaseList",orderBaseList);
			List<OrderDto> orderList=getOrderDtoList(orderBaseList,orderStateMap,paymentStateMap);
			OrderExcelReport report = new OrderExcelReport();
			String dateString = new String(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()).getBytes("ISO-8859-1"),"UTF-8");
			report.getReport("订单导出Excel"+dateString, orderList, response);
		} catch (Exception e) {
			LOGGER.error("订单列表导出Excel失败"+e.getMessage(), e);
		}
	}


	
	private ArrayList<OrderDto> getOrderDtoList(ArrayList<OrderBase> orderBaseList,Map<String,String> orderStateMap,Map<String,String> paymentStateMap){
		ArrayList<OrderDto> orderDtoList=new  ArrayList<OrderDto>(); 
		for(OrderBase orderBase : orderBaseList){
			OrderDto orderDto=new OrderDto();
			orderDto.setMemberId(orderBase.getMemberId());
			orderDto.setMemberName(orderBase.getMemberName());
			orderDto.setOrderCode(orderBase.getOrderCode());
			orderDto.setCreateTime(orderBase.getCreateTime());
			orderDto.setPaymentTotalActual(orderBase.getPaymentTotalActual());
			orderDto.setIsInvoice("y".equals(orderBase.getIsInvoice())?"需要发票":"不需要发票");
			orderDto.setOrderStatus(orderStateMap.get(orderBase.getOrderStatus()));
			orderDto.setFinanceStatus("y".equals(orderBase.getFinanceStatus())?"已付款":"未付款");
			orderDto.setTransStatus("y".equals(orderBase.getTransStatus())?"已发货":"未发货");
			orderDto.setReceiver(orderBase.getReceiver());
			orderDto.setReceiverMobile(orderBase.getReceiverMobile());
			orderDto.setMemo(orderBase.getMemo());
			OrderMemberAddress soSoMember = soSoMemberManageService.findByOrderCode(orderBase.getOrderCode());
			if(soSoMember==null){
				continue;
			}
			// 地区编码转换成实际的地址
			String province = memberAreaService.getNameByAreaCode(soSoMember.getProvince());
			String city = memberAreaService.getNameByAreaCode(soSoMember.getCity());
			String district = memberAreaService.getNameByAreaCode(soSoMember.getDistrict());
			
			String address = province+city+district+soSoMember.getAddress();
			orderDto.setCity(city);
			orderDto.setProvince(province);
			orderDto.setDistrict(district);
			orderDto.setAddress(address);
			orderDtoList.add(orderDto);
		}
		return orderDtoList;
	}
	


	/***
	 *   批量更新订单状态
	 * 
	 * @param code   页面选中的订单编号
	 * @param orderStatus    订单状态
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="updateOrdersStatus", method = RequestMethod.POST)
	public JsonResponseModel updateOrdersStatus(@RequestParam("orderStatus")String status,
			@RequestParam("tempMemberIds")String tempMemberIds,
			@RequestParam("tempOrderCodes")String tempOrderCodes,HttpServletRequest request){
		JsonResponseModel jsonModel = new JsonResponseModel();
		try {
			String[] memberIds=tempMemberIds.split(",");
			String[] orderCodes=tempOrderCodes.split(",");
			Set<String> requireTransOrderCodes = new HashSet<String>();
			for(int i=0;i<orderCodes.length;i++){
				Long memberId=Long.parseLong(memberIds[i]);
				String orderCode=orderCodes[i];
				
				if(status.equals(OrderConfigServerEnum.SUCCESS_SHIPPED.getCode())){
					OrderTrans orderTrans=orderTransService.findByMemberIdAndOrderCode(memberId, orderCode);
					if(ObjectUtils.equals(null, orderTrans)||StringUtil.isBlank(orderTrans.getTransCode())
							||StringUtil.isBlank(orderTrans.getTransName())){
						requireTransOrderCodes.add(orderCode);
						continue;
					}
					OrderMemberAddress orderMemberAddress=soSoMemberManageService.findByMemberIdAndOrderCode(memberId, orderCode);
					Member member = memberService.findById(memberId);
					if(ObjectUtils.notEqual(null, orderMemberAddress)&&ObjectUtils.notEqual(null, member)){
						smsService.sendSmsDelivery(orderCode, orderTrans.getTransName(), orderTrans.getTransCode(), orderMemberAddress.getReceiverMobile(), SourceConstant.CMS_CODE, member.getLoginName());
					}
				}
				
				OrderBase orderBase=new OrderBase();
				orderBase.setMemberId(memberId);
				orderBase.setOrderCode(orderCode);
				orderBase.setOrderStatus(status);
				orderBaseService.update(orderBase);
				
				OrderTrace orderTrace=new OrderTrace();
				orderTrace.setMemberId(memberId);
				orderTrace.setOrderCode(orderCode);
				orderTrace.setOrderStatus(status);
				orderTraceService.updateByMemberIdAndOrderCode(orderTrace);
				
			}
			if(requireTransOrderCodes.size()>0){
				String requireTransOrderCodesStr="";
				for (String str : requireTransOrderCodes) {  
					requireTransOrderCodesStr=requireTransOrderCodesStr+str+",";  
				}  
				jsonModel.Success("订单号："+requireTransOrderCodesStr+" 没有设置快递信息");
			}else{
				jsonModel.Success("操作成功!");
			}
			
		} catch (Exception e) {
			jsonModel.Fail("操作出错!");
			LOGGER.error("操作出错", e);
		}
		return jsonModel;
	}
	
	@RequestMapping("showImportTrans")
	public String showImportTrans() {
		return "models/order/importTrans";
	}
	/***
	 * 批量导入快递单号
	 *
	 * @param transCodes
	 * @param transNames
	 * @return
	 */
	
	@RequestMapping("importExcel")
	public String importExcel(
			@RequestParam(value="member_excel",required=false)MultipartFile member_excel,
			SoSalesOrderDto orderDto, BasePagination page,
			HttpServletRequest request,HttpSession session,
			ModelMap model){
		List<OrderTrans> orders = new ArrayList<OrderTrans>();
		String possibleErrorTransCode="";
		int totalSize=0;
		int blankSize=0;
		try {
			if (member_excel != null && member_excel.getSize() > 0 ) {
				// 进行Excel解析
				// 1、 工作薄对象
				HSSFWorkbook hssfWorkbook = new HSSFWorkbook(member_excel.getInputStream());
				// 解析工作薄
				hssfWorkbook.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK); // 避免空指针异常
				// 2、 获得Sheet
				HSSFSheet sheet = hssfWorkbook.getSheetAt(0); // 获得第一个sheet
				// 3、解析Sheet中每一行
				for (Row row : sheet) {
					// 进行解析 ， 每一行数据，对应 会员手机信息
					if (row.getRowNum() == 0) {// 第一行（表头，无需解析）
						continue;
					}
					totalSize++;
					//从第二行开始解析
					String transCode = row.getCell(17).toString(); 
					transCode = transCode.trim();
					if(transCode.isEmpty()) {
						LOGGER.info("此快递单号为空" + transCode);
						blankSize++;
						continue;
					}
					possibleErrorTransCode=transCode;
					String transName = row.getCell(18).toString();
					transName = transName.trim();
					String orderCode = row.getCell(3).toString();
					orderCode = orderCode.trim();
					OrderTrans orderTrans = new OrderTrans();
					orderTrans.setOrderCode(orderCode);
					orderTrans.setTransCode(transCode);
					orderTrans.setTransName(transName);
					orderTrans.setTransStatus("y");
					AuthUser user=CMSSessionUtils.getSessionUser(session);
					orderTrans.setUpdateId(user.getId());
					orderTransService.updateByMemberIdAndOrderCode(orderTrans);
				}
			}
			model.addAttribute("totalSize",totalSize);
			model.addAttribute("blankSize",blankSize);
			model.addAttribute("successSize",totalSize-blankSize);
			model.addAttribute("info", "success");
			return "models/order/batch-success";
		} catch (Exception e) {
			model.addAttribute("totalSize",totalSize-1);
			model.addAttribute("blankSize",blankSize);
			model.addAttribute("successSize",totalSize-blankSize-1);
			model.addAttribute("possibleErrorTransCode",possibleErrorTransCode);
			model.addAttribute("info", "error");
			LOGGER.error("快递单号导入出错", e);
			return "models/order/batch-success";
		}
		
	}

	/***
	 * 设置查询参数
	 * 
	 * @param orderBaseDto
	 * @return
	 */
	private Map<String, String> setParamByOrderDto(OrderBaseDto orderBaseDto) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("orderCode", orderBaseDto.getOrderCode());
		params.put("memberName", orderBaseDto.getMemberName());
		params.put("orderStatus", orderBaseDto.getOrderStatus());
		params.put("createTimeFrom", orderBaseDto.getCreateTimeFrom());
		params.put("createTimeTo", orderBaseDto.getCreateTimeTo());
		return params;
	}
}
