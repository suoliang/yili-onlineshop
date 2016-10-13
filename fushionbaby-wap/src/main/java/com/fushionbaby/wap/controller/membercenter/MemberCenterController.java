package com.fushionbaby.wap.controller.membercenter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.MemberAddressDto;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.order.OrderDto;
import com.fushionbaby.common.dto.order.OrderLineDto;
import com.fushionbaby.common.dto.sku.web.PageSetDto;
import com.fushionbaby.common.enums.OrderConfigClientEnum;
import com.fushionbaby.common.util.CookieUtil;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.model.SoSalesOrderUser;
import com.fushionbaby.core.model.SoSoLineUser;
import com.fushionbaby.core.service.SoSalesOrderWebUserService;
import com.fushionbaby.core.service.SoSoLineUserService;
import com.fushionbaby.log.model.LogMemberMoney;
import com.fushionbaby.log.service.LogMemberMoneyService;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.model.MemberAddress;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.model.MemberExtraInfo;
import com.fushionbaby.member.service.MemberAddressService;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.member.service.MemberExtraInfoService;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.sku.model.SkuGiftCard;
import com.fushionbaby.sku.service.SkuGiftCardService;
import com.fushionbaby.wap.controller.sku.AbstractSkuController;
import com.fushionbaby.wap.dto.UserExtraInfoDto;
import com.fushionbaby.wap.util.ImageConstantWap;
/**
 * @author
 * 
 */
@Controller
@RequestMapping("/membercenter")
public class MemberCenterController extends AbstractSkuController {
	/** 记录日志 */
	private static final Log logger = LogFactory.getLog(MemberCenterController.class);

	/*** 注入会员信息 ***/
	@Autowired
	private MemberService<Member> memberService;

	/*** 注入额外会员信息 ***/
	@Autowired
	private MemberExtraInfoService<MemberExtraInfo> memberExtraInfoService;

	/**
	 * 订单操作service
	 */
	@Autowired
	private SoSalesOrderWebUserService<SoSalesOrderUser> soSalesOrderWebService;

	/**
	 * 订单行信息查询service
	 */
	@Autowired
	private SoSoLineUserService<SoSoLineUser> soSoLineService;

	/**
	 * 地区字典service
	 */
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaService;

	/**
	 * 会员可选地址service这里记录了会员常用的收货地址
	 */
	@Autowired
	private MemberAddressService<MemberAddress> memberAddressService;

	/*** 礼品卡service ***/
	@Autowired
	private SkuGiftCardService<SkuGiftCard> skuGiftCardService;
	
	/*** 会员账户余额日志 ***/
	@Autowired
	private LogMemberMoneyService logMemberMoneyService;

	
	@RequestMapping("/center")
	public String center(Long time,ModelMap model) {
		time = new Date().getTime();// 创建一个时间戳
		model.addAttribute("time", time);
		return "center/person-center";
	}
	
	
	@RequestMapping("deposit")
	public String userCard(HttpServletRequest request, ModelMap model){
		
		return "deposit";
	}
	
	@RequestMapping("myMoney")
	public String myMoney(HttpServletRequest request, ModelMap model) {
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		UserDto userDto = (UserDto) SessionCache.get(sid);
		Integer[] money = memberService.queryMemberWallet(userDto.getMember_id());
		model.addAttribute("availableMoney", money[1]);
		model.addAttribute("epoints", money[2]);
		return "myMoney";
	}
	
	@ResponseBody
	@RequestMapping("chargeFund")
	public Object chargeFund(@RequestParam(value ="cardNo") String cardNo,
			@RequestParam(value ="cardPassword") String cardPassword,
			HttpServletRequest request) {
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto userDto = (UserDto) SessionCache.get(sid);
			SkuGiftCard card = skuGiftCardService.useCard(cardNo);
			if(card == null) {
				return Jsonp_data.success("礼品卡卡号不存在或已充值！");
			} else if(!card.getChargePwd().equals(cardPassword)) {
				return Jsonp_data.success("充值密码错误！");
			} 
			card.setChargeTime(new Date());
			card.setStatus("2");
			card.setMemberId(userDto.getMember_id());
			skuGiftCardService.update(card);
			memberService.updateMemberWallet(userDto.getMember_id(), Integer.valueOf(card.getFaceValue()));
			LogMemberMoney log = new LogMemberMoney();
			log.setMemberId(userDto.getMember_id());
			log.setAmount(new BigDecimal(Integer.valueOf(card.getFaceValue())));
			log.setCurrentAmount(memberService.findById(userDto.getMember_id()).getWalletMoney());
			log.setTransType(1);
			log.setTransTime(new Date());
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
			log.setDescription(sdf.format(new Date()) + " 向账户充值 " + card.getFaceValue() + "元");
			logMemberMoneyService.add(log);
			return Jsonp_data.success("礼品卡充值成功,面值:"+card.getFaceValue()+"元");
		} catch (Exception e) {
			logger.error("充值失败", e);
			return Jsonp.error("系统异常，充值未成功，请稍后再试!");
		} 
	
	}

	@RequestMapping("/address")
	public String address(HttpServletRequest request, ModelMap model) {
		List<MemberAddressDto> address_list = new ArrayList<MemberAddressDto>();

		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		if (StringUtils.isEmpty(sid)) {
			model.addAttribute("addressList", address_list);
		}
		UserDto userDto = (UserDto) SessionCache.get(sid);
		long member_id = userDto.getMember_id();

		List<MemberAddress> list = memberAddressService.getListPage(member_id,
				WebConstant.DEFAULT_PAGE_INDEX, WebConstant.DEFAULT_PAGE_SIZE);
		if (CollectionUtils.isEmpty(list)) {
			model.addAttribute("addressList", address_list);
		}

		String province_name = null;
		String city_name = null;
		String district_name = null;
		for (MemberAddress tmp : list) {
			MemberAddressDto dto = new MemberAddressDto();
			dto.setAddress_id(tmp.getId());
			dto.setName(tmp.getContactor());
			dto.setPhone(tmp.getMobile());
			dto.setProvince_id(tmp.getProvince());
			dto.setCity_id(tmp.getCity());
			dto.setDistrict_id(tmp.getDistrict());
			province_name = memberAreaService.getByAreaCode(dto.getProvince_id());
			city_name = memberAreaService.getByAreaCode(dto.getCity_id());
			district_name = memberAreaService.getByAreaCode(dto.getDistrict_id());
			dto.setProvince_name(province_name);
			dto.setCity_name(city_name);
			dto.setDistrict_name(district_name);
			dto.setAddress_info(tmp.getAddress());
			dto.setIsDefault(tmp.getIsDefault());
			address_list.add(dto);
		}
		model.addAttribute("addressList", address_list);
		return "person/address";
	}

	@RequestMapping("/person")
	public String person(ModelMap model, HttpServletRequest request) {
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		if (StringUtils.isNotEmpty(sid)) {
			UserDto userDto = (UserDto) SessionCache.get(sid);
			model.addAttribute("user", userDto);
		}
		return "person/person";
	}

	@RequestMapping("/pass_mod")
	public String passMod(ModelMap model) {
		return "person/pass_mod";
	}

	/*****
	 * 我的订单 -- 全部订单
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/order")
	public String order(
			@RequestParam(value = "cur_page", defaultValue = WebConstant.DEFAULT_PAGE_INDEX + "") Integer curPage,
			String order_status, String sid, String is_comment, Long time,
			HttpServletRequest request, ModelMap model) {
		try {
			findOrderList(curPage, order_status, sid, is_comment, time, request, model);// 调用查询订单的公共方法
		} catch (Exception e) {
			logger.error("MemberCenterController我的订单--全部订单查询有误" + e);
		}
		model.addAttribute("type", "01");
		return "center/my-order";
	}

	/***
	 * 我的订单 -- 待付款
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/obligation")
	public String obligation(
			@RequestParam(value = "cur_page", defaultValue = WebConstant.DEFAULT_PAGE_INDEX + "") Integer curPage,
			String order_status, String sid, String is_comment, Long time,
			HttpServletRequest request, ModelMap model) {
		try {
			order_status = OrderConfigClientEnum.WAITING_PAYMENT.getCode();
			;// 等待付款状态
			findOrderList(curPage, order_status, sid, is_comment, time, request, model);// 调用查询订单的公共方法
		} catch (Exception e) {
			logger.error("MemberCenterController我的订单--待付款查询有误" + e);
		}
		model.addAttribute("type", "02");
		return "center/my-order";
	}

	/***
	 * 我的订单 -- 待收货
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/receive")
	public String receive(
			@RequestParam(value = "cur_page", defaultValue = WebConstant.DEFAULT_PAGE_INDEX + "") Integer curPage,
			String order_status, String sid, String is_comment, Long time,
			HttpServletRequest request, ModelMap model) {
		try {
			order_status = OrderConfigClientEnum.SUCCESS_SHIPPED.getCode();// 已发货状态
			findOrderList(curPage, order_status, sid, is_comment, time, request, model);// 调用查询订单的公共方法
		} catch (Exception e) {
			logger.error("MemberCenterController我的订单--待收货查询有误" + e);
		}
		model.addAttribute("type", "03");
		return "person/receive";
	}

	/***
	 * 我的订单 -- 待评价
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/evaluate")
	public String evaluate(@RequestParam(value ="cur_page", defaultValue=WebConstant.DEFAULT_PAGE_INDEX+"") Integer curPage,
			String order_status,String sid,Long time,
			HttpServletRequest request,
			ModelMap model) {
		try {
			if(sid == null)
				sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto userDto = (UserDto) SessionCache.get(sid);
			time = new Date().getTime();//创建一个时间戳
			if (!ObjectUtils.equals(userDto, null)) {
				Map<String, Object> params = new HashMap<String, Object>();
				
				Long member_id = userDto.getMember_id();
				params.put("memberId",String.valueOf(member_id));
				
				params.put("start", String.valueOf((curPage-1)* 3));// pagesize = 3 
				params.put("limit", String.valueOf(3));// 每页显示3个
				//分页集合
				List<SoSalesOrderUser> list=soSalesOrderWebService.getListPageEvaluate(params);
				Integer total = soSalesOrderWebService.getTotalEvaluate(params);
				//订单集合
				List<OrderDto> orderList=new ArrayList<OrderDto>();
				for(SoSalesOrderUser o:list){
					OrderDto order=new OrderDto();
					order.setId(String.valueOf(o.getId()));
					order.setCode(o.getCode());
					order.setTotal_actual(o.getTotalActual()==null?0:o.getTotalActual().doubleValue());
					order.setOrder_status(o.getOrderStatus());//订单状态
					order.setOrder_information(OrderConfigClientEnum.parseCode(o.getOrderStatus()));//订单互联网状态信息显示
					order.setCreate_time(o.getCreateTime()==null?"":DateFormat.dateToMinuteString(o.getCreateTime()));
					//物流
					order.setTrans_code(StringUtils.isEmpty(o.getTransCode()) ? StringUtils.EMPTY : o.getTransCode());
					order.setTrans_name(StringUtils.isEmpty(o.getTransName()) ? StringUtils.EMPTY : o.getTransName());
					//订单审核没通过原因
					order.setAuditFailReason(StringUtils.isEmpty(o.getAuditFailReason()) ? StringUtils.EMPTY : o.getAuditFailReason());
					//查询订单的购买明细列表
					List<SoSoLineUser> soSoLineList = soSoLineService.findByOrderCode(o.getCode(),CommonConstant.NO);
					//订单行集合
					List<OrderLineDto> orderLineList = new ArrayList<OrderLineDto>();//订单行
					for (SoSoLineUser ssl : soSoLineList) {
						OrderLineDto orderLine = new OrderLineDto();
						String img_path = getThumImagePath(ssl.getSkuCode(), ImageStandardConstant.IMAGE_STANDARD_WEB_65);
						orderLine.setImg_path(img_path);//图片 -- 65X65
						orderLine.setSku_name(StringUtils.isEmpty(ssl.getSkuName()) ? StringUtils.EMPTY : ssl.getSkuName());
						orderLine.setQuantity(ObjectUtils.equals(ssl.getQuantity(), null) ? 0 : ssl.getQuantity());
						orderLine.setIs_comment(StringUtils.isEmpty(ssl.getIsComment()) ? StringUtils.EMPTY : ssl.getIsComment());
						orderLine.setUnit_price(ObjectUtils.equals(ssl.getUnitPrice(), null) ? new BigDecimal(0) : ssl.getUnitPrice());
						orderLine.setSku_id(ssl.getSkuId());
						orderLine.setId(ssl.getId());
						orderLine.setSku_size(StringUtils.isEmpty(ssl.getSize()) ? StringUtils.EMPTY : ssl.getSize());
						orderLine.setSku_color(StringUtils.isEmpty(ssl.getColor()) ? StringUtils.EMPTY : ssl.getColor());
						orderLine.setRowsPriceTotal(orderLine.getUnit_price().multiply(new BigDecimal(orderLine.getQuantity())));//总计
						orderLineList.add(orderLine);
					}
					order.setItems(orderLineList);//订单行集合
					orderList.add(order);
				}
				//分页对象
				PageSetDto pageSet = new PageSetDto();
				pageSet.setOrderList(orderList);
				pageSet.setAmount(total);
				pageSet.setCurPage(curPage);
				if(total > 0){
					pageSet.setTotalPage( (total - 1) /  3 + 1);//每页三条
				}
				model.addAttribute("pageset", pageSet);
				model.addAttribute("sid",sid );
				model.addAttribute("time",time );
			}
		} catch (Exception e) {
			logger.error("MemberCenterController我的订单--待评价查询有误" + e);
		}
		model.addAttribute("type", "04");
		return "person/evaluate";
	}

	/**
	 * 共有方法
	 * 
	 * 查询订单,根据会员id查询,如果订单状态条件不空,则and 订单状态查询,筛选掉了is_delete=Y的订单,已经删除的订单不查询
	 * 
	 * @param member_id
	 *            会员id
	 * @param page_index
	 * @param page_size
	 * @param order_status
	 * @param is_comment
	 * @return
	 */
	private void findOrderList(
			@RequestParam(value = "cur_page", defaultValue = WebConstant.DEFAULT_PAGE_INDEX + "") Integer curPage,
			String order_status, String sid, String is_comment, Long time,
			HttpServletRequest request, ModelMap model) {
		if (sid == null)
			sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		UserDto userDto = (UserDto) SessionCache.get(sid);
		time = new Date().getTime();// 创建一个时间戳
		if (!ObjectUtils.equals(userDto, null)) {
			// page_index=(page_index==null || page_index<1) ? 1 : page_index;
			// page_size=(page_size==null || page_size<1) ? 10 : page_size;
			Map<String, Object> params = new HashMap<String, Object>();
			Long member_id = userDto.getMember_id();
			params.put("memberId", String.valueOf(member_id));
			if (order_status != null) {
				params.put("orderStatus", order_status);
			}
			params.put("start", String.valueOf((curPage - 1) * 3));// pagesize= 3
			params.put("limit", String.valueOf(3));// 每页显示3个
			// 分页集合
			List<SoSalesOrderUser> list = soSalesOrderWebService.getListPage(params);
			Integer total = soSalesOrderWebService.getTotal(params);
			// 订单集合
			List<OrderDto> orderList = new ArrayList<OrderDto>();
			for (SoSalesOrderUser o : list) {
				OrderDto order = new OrderDto();
				order.setId(String.valueOf(o.getId()));
				order.setCode(o.getCode());
				order.setTotal_actual(o.getTotalActual() == null ? 0 : o.getTotalActual()
						.doubleValue());
				order.setOrder_status(o.getOrderStatus());// 订单状态
				order.setOrder_information(OrderConfigClientEnum.parseCode(o.getOrderStatus()));// 订单互联网状态信息显示
				order.setCreate_time(o.getCreateTime() == null ? "" : DateFormat
						.dateToMinuteString(o.getCreateTime()));
				// 物流
				order.setTrans_code(StringUtils.isEmpty(o.getTransCode()) ? StringUtils.EMPTY : o
						.getTransCode());
				order.setTrans_name(StringUtils.isEmpty(o.getTransName()) ? StringUtils.EMPTY : o
						.getTransName());
				// 订单审核没通过原因
				order.setAuditFailReason(StringUtils.isEmpty(o.getAuditFailReason()) ? StringUtils.EMPTY
						: o.getAuditFailReason());
				// 查询订单的购买明细列表
				List<SoSoLineUser> soSoLineList = soSoLineService.findByOrderCode(o.getCode(),
						is_comment);
				// 订单行集合
				List<OrderLineDto> orderLineList = new ArrayList<OrderLineDto>();// 订单行
				for (SoSoLineUser ssl : soSoLineList) {
					OrderLineDto orderLine = new OrderLineDto();
					String img_path = getThumImagePath(ssl.getSkuCode(),
							ImageStandardConstant.IMAGE_STANDARD_WEB_65);
					orderLine.setImg_path(img_path);// 图片 -- 65X65
					orderLine
							.setSku_name(StringUtils.isEmpty(ssl.getSkuName()) ? StringUtils.EMPTY
									: ssl.getSkuName());
					orderLine.setQuantity(ObjectUtils.equals(ssl.getQuantity(), null) ? 0 : ssl
							.getQuantity());
					orderLine
							.setIs_comment(StringUtils.isEmpty(ssl.getIsComment()) ? StringUtils.EMPTY
									: ssl.getIsComment());
					orderLine
							.setUnit_price(ObjectUtils.equals(ssl.getUnitPrice(), null) ? new BigDecimal(
									0) : ssl.getUnitPrice());
					orderLine.setSku_id(ssl.getSkuId());
					orderLine.setId(ssl.getId());
					orderLine.setSku_size(StringUtils.isEmpty(ssl.getSize()) ? StringUtils.EMPTY
							: ssl.getSize());
					orderLine.setSku_color(StringUtils.isEmpty(ssl.getColor()) ? StringUtils.EMPTY
							: ssl.getColor());
					orderLine.setRowsPriceTotal(orderLine.getUnit_price().multiply(
							new BigDecimal(orderLine.getQuantity())));// 总计
					orderLineList.add(orderLine);
				}
				order.setItems(orderLineList);// 订单行集合
				orderList.add(order);
			}
			// 分页对象
			PageSetDto pageSet = new PageSetDto();
			pageSet.setOrderList(orderList);
			pageSet.setAmount(total);
			pageSet.setCurPage(curPage);
			if (total > 0) {
				pageSet.setTotalPage((total - 1) / 3 + 1);// 每页三条
			}
			model.addAttribute("pageset", pageSet);
			model.addAttribute("sid", sid);
			model.addAttribute("time", time);
		}
	}

	/***
	 * 索亮 -- 个人中心基础信息显示
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/user_info")
	public String userInfo(HttpServletRequest request, ModelMap model) {
		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto userDto = (UserDto) SessionCache.get(sid);
			if (!ObjectUtils.equals(userDto, null)) {
				Long member_id = userDto.getMember_id();
				MemberExtraInfo memberExtraInfo = memberExtraInfoService.findByMemberId(member_id);// 会员额外信息
				Member member = memberService.findById(member_id);// 会员信息
				UserExtraInfoDto userExtraInfo = new UserExtraInfoDto();
				userExtraInfo
						.setNickname(StringUtils.isEmpty(memberExtraInfo.getNickname()) ? StringUtils.EMPTY
								: memberExtraInfo.getNickname());
				userExtraInfo.setBaby_fm(ObjectUtils.equals(memberExtraInfo.getBabyFm(), null) ? 1
						: memberExtraInfo.getBabyFm());// 为空显示宝爸
				userExtraInfo.setBaby_gender(ObjectUtils.equals(memberExtraInfo.getBabyGender(),
						null) ? 1 : memberExtraInfo.getBabyGender());// 为空显示王子
				userExtraInfo.setBaby_birthday(ObjectUtils.equals(
						memberExtraInfo.getBabyBirthday(), null) ? StringUtils.EMPTY : DateFormat
						.dateTimeToDateString(memberExtraInfo.getBabyBirthday()));
				userExtraInfo
						.setImg_path(StringUtils.isEmpty(member.getImgPath()) ? StringUtils.EMPTY
								: member.getImgPath());
				userExtraInfo.setWeixin_no(StringUtils.isEmpty(memberExtraInfo.getWeixinNo())? StringUtils.EMPTY : memberExtraInfo.getWeixinNo());
				model.addAttribute("userExtraInfo", userExtraInfo);
			}
		} catch (Exception e) {
			logger.error("MemberCenterController个人中心--基础信息显示有误" + e);
		}
		return "person/user-info";
	}

	/***
	 * 索亮 -- 个人中心基础信息修改
	 * 
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("save_user_info")
	public Object saveUserInfo(
			@RequestParam(value = "nickname", defaultValue = "") String nickname,
			@RequestParam(value = "baby_fm", defaultValue = "") int baby_fm,
			@RequestParam(value = "baby_gender", defaultValue = "") int baby_gender,
			@RequestParam(value = "baby_birthday", defaultValue = "") String baby_birthday,
			@RequestParam(value = "imgUrl", defaultValue = "") String imgUrl,
			@RequestParam(value = "weixinNo",defaultValue = "") String weixinNo,
			HttpServletRequest request) {

		try {
			String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
			UserDto userDto = (UserDto) SessionCache.get(sid);
			if (!ObjectUtils.equals(userDto, null)) {
				Long member_id = userDto.getMember_id();
				MemberExtraInfo memberExtraInfo = new MemberExtraInfo();// 会员额外信息
				Member member = new Member();// 会员信息
				memberExtraInfo.setMemberId(member_id);
				memberExtraInfo.setNickname(StringUtils.isEmpty(nickname) ? StringUtils.EMPTY
						: nickname);
				memberExtraInfo.setBabyFm(baby_fm);
				memberExtraInfo.setBabyGender(baby_gender);
				if (baby_birthday.length() != 0) {// 日期为空不进行修改
					memberExtraInfo.setBabyBirthday(DateFormat.stringToDAYDate(baby_birthday));
				}
				memberExtraInfo.setWeixinNo(StringUtils.isEmpty(weixinNo) ? StringUtils.EMPTY : weixinNo);
				memberExtraInfoService.updateByMemberId(memberExtraInfo);
				if (StringUtils.isNotBlank(imgUrl)) {
					member.setId(member_id);
					member.setImgPath(imgUrl);// 设置图片
					memberService.update(member);// 修改
				}
			}
		} catch (Exception e) {
			logger.error("MemberCenterController个人中心--基础信息修改有误" + e);
		}
		return Jsonp.success();
	}

	/**
	 * 图片的 -- 上传
	 * 
	 * @param file
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping("loadPhoto")
	public void loadPhoto(@RequestParam("photo") MultipartFile file,
			//@RequestParam(value = "login_name", defaultValue = "") String login_name,
			PrintWriter out, HttpServletRequest request) throws Exception {

		if (!file.isEmpty()) {
			InputStream input = null;
			/** 得到选择文件的输入流 */
			try {
				input = file.getInputStream();
				/** 以会员头像作为文件名 */
				String photoDir = ImageConstantWap.MEMBER_IMAGE_PATH;
				/** 存放文件的路径 */
				File rootPath = new File(photoDir);
				if (!rootPath.exists()) {
					rootPath.mkdirs();
				}
				/** 文件名唯一 */
				String fileName = file.getOriginalFilename();
				/** 图片的文件名 -- 登录名 + uuid 做图片名 */
				fileName = UUID.randomUUID()//login_name + 如果第三方登陆出现中文就会有问题
						+ fileName.substring(fileName.lastIndexOf("."));
				/** 根据路径和名字创建文件 */
				File uploadFile = new File(rootPath, fileName);
				/** 将input 文件copy */
				FileUtils.copyInputStreamToFile(input, uploadFile);
				out.print(fileName);
			} catch (IOException e) {
				logger.error("MemberCenterController头像上传图片失败", e);
			} finally {
				if (input != null) {
					input.close();
				}
			}
		}
	}

	/***
	 * 图片上传之后进行展示
	 * 
	 * @param fileName
	 * @param out
	 */
	@RequestMapping("showPhoto")
	public void show_image(@RequestParam("fileName") String fileName, OutputStream out) {
		try {
			if (StringUtils.isNotBlank(fileName)) {
				FileUtils.copyFile(new File(ImageConstantWap.MEMBER_IMAGE_PATH, fileName), out);
			}
		} catch (IOException e) {
			logger.error("MemberCenterController图片加载失败", e);
		}
	}

}
