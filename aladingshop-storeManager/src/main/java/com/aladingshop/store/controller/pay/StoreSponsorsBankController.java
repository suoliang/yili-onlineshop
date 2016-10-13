package com.aladingshop.store.controller.pay;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.store.auth.model.StoreAuthUser;
import com.aladingshop.store.config.Global;
import com.aladingshop.store.controller.BaseController;
import com.aladingshop.store.model.StoreSponsorsBank;
import com.aladingshop.store.service.StoreSponsorsBankService;
import com.fushionbaby.common.constants.AreaConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.SessionKeyConstant;
import com.fushionbaby.common.constants.VerificationConstant;
import com.fushionbaby.common.dto.areaconfig.AreaDto;
import com.fushionbaby.common.dto.verification.BankCardVerificationReqDto;
import com.fushionbaby.common.dto.verification.VerificationResDto;
import com.fushionbaby.common.util.HttpRequest;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.model.VerificationRecord;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.member.service.VerificationRecordService;
import com.fushionbaby.other.util.json.JSONObject;
import com.google.gson.Gson;


@Controller
@RequestMapping("/storeSpoBank")
public class StoreSponsorsBankController extends BaseController {
	
	@Autowired
	private StoreSponsorsBankService<StoreSponsorsBank> storeSponsorsBankService;
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaService;
	
	private static final Log LOGGER = LogFactory.getLog(StoreSponsorsBankController.class);
	
	/**验证信息记录*/
	@Autowired
	private VerificationRecordService<VerificationRecord> verificationRecordService;
	
	@RequestMapping("addBankCard")
	public String addBankcard(StoreSponsorsBank storeSponsorsBank,HttpServletRequest request,ModelMap model){
		
		try {
			StoreAuthUser auUser = (StoreAuthUser) request.getSession().getAttribute(SessionKeyConstant.CMS_AUTH_USER);
			if(auUser == null){
				return "redirect:"+Global.getAdminPath()+"/login/index";
			}
			StoreSponsorsBank stoSpoBank = storeSponsorsBankService.findByStoreCode(auUser.getStoreCode());
			if(stoSpoBank != null &&stoSpoBank.getCardNo() != null){
				model.addAttribute("message", "您已经提交过银行卡号，无法再次提交！");
				return "models/pay/bankInfo";
			}
			storeSponsorsBank.setStoreCode(auUser.getStoreCode());
			storeSponsorsBank.setUpdateId(auUser.getId());
			/**认证银行信息  begin*/
			storeSponsorsBank.setIsValidate(VerificationConstant.IS_VALIDATE_STATUS_NULL);
			if(StringUtils.isNotBlank(storeSponsorsBank.getCardHolder())&&StringUtils.isNotBlank(storeSponsorsBank.getCardNo()))
			{
				BankCardVerificationReqDto  req=new BankCardVerificationReqDto(storeSponsorsBank.getCardHolder(), storeSponsorsBank.getCardNo());
				VerificationResDto res=this.verificationBankCard(req);
				if(!VerificationConstant.VALIDATE_SUCCESS_RESPONSECODE.equals(res.getCode()))
				{
					model.addAttribute("message", "添加的银行卡和持有者验证失败");
					return "models/pay/addCardInfo";
				}else{
					model.addAttribute("message", "恭喜您！银行卡验证成功");
					storeSponsorsBank.setIsValidate(VerificationConstant.IS_VALIDATE_STATUS_SUCCESS);
				}
			}
			/**认证银行信息  end */
			storeSponsorsBankService.add(storeSponsorsBank);
			
		} catch (Exception e) {
			LOGGER.error("添加银行卡出错！", e);
			model.addAttribute("message", "服务器繁忙，请稍后重试！");
			return "models/pay/addCardInfo";
		}
		model.addAttribute("message", "添加成功！");
		return "models/pay/bankInfo";
	}
	
	/***
	 * 银行卡和真实姓名的认证
	 * @param req
	 * @return
	 */
	private VerificationResDto verificationBankCard(BankCardVerificationReqDto req) {
		VerificationResDto resDto=new VerificationResDto();
		resDto.setStatus("2002");
	    String url="http://121.41.42.121:8080/v3/card2-server";
	    String param="mall_id="+req.getMallId()+"&realname="+req.getRealName()+"&cardnum="+req.getCardNum()+"&tm="+req.getTm()+"&sign="+req.getSign();
	    LOGGER.info("store  StoreSponsorsBankController.java 银行卡和姓名验证请求参数："+param);
	    String json = HttpRequest.sendGet(url,param);
	    LOGGER.info("store  StoreSponsorsBankController.java 银行卡和姓名验证返回结果："+json);
		JSONObject jo;
		try {
			jo = new JSONObject(json);
			String  data = jo.getString("data");
			String  status = jo.getString("status");
			resDto=new Gson().fromJson(data, VerificationResDto.class);
			resDto.setStatus(status);
			resDto.setData(data);
			String trueName = URLDecoder.decode(req.getRealName(), "UTF-8");
			req.setRealName(trueName);
		} catch (Exception e) {
			e.printStackTrace();
		    LOGGER.info("store  StoreSponsorsBankController.java 银行卡和姓名验证返回结果解析异常"+json);
		} 
		saveVerificationRecord(req, resDto);
		return resDto;
	}
	/***
	 * 保存验证信息
	 * @param reqDto
	 * @param name
	 * @param resDto
	 */
	private void saveVerificationRecord(BankCardVerificationReqDto reqDto,VerificationResDto resDto){
		VerificationRecord record=new VerificationRecord();
        record.setBankCardNo(reqDto.getCardNum());
		record.setCreateTime(new Date());
		record.setResponseStatus(resDto.getCode());
		record.setTrueName(reqDto.getRealName());
		record.setVerificationStatus(VerificationConstant.VALIDATE_SUCCESS_RESPONSECODE.equals(resDto.getCode())?CommonConstant.YES:CommonConstant.NO);
		record.setVerificationType(VerificationConstant.VERIFICATION_TYPE_BANK);
		this.verificationRecordService.add(record);
	}
	
	
	@RequestMapping("goAddCard")
	public String goAddCard(ModelMap model){
		List<AreaDto>  provinceList =  memberAreaService.findByParentAreaCode(AreaConstant.PROVINCE_CODE) ;
		model.addAttribute("list", provinceList);
		return "models/pay/addCardInfo";
	}
	
	
	@ResponseBody
	@RequestMapping("uploadArea")
	public Object uploadArea(@RequestParam(value="provinceCode") String provinceCode){
		List<AreaDto>  cityList =  memberAreaService.findByParentAreaCode(provinceCode) ;
		return cityList;
	}
	
	@RequestMapping("myBankInfo")
	public String myBankInfo(StoreSponsorsBank storeSponsorsBank,HttpServletRequest request,ModelMap model){
		try {
			StoreAuthUser auUser = (StoreAuthUser) request.getSession().getAttribute(SessionKeyConstant.CMS_AUTH_USER);
			if(auUser == null){
				return "redirect:"+Global.getAdminPath()+"/login/index";
			}
			storeSponsorsBank = storeSponsorsBankService.findByStoreCode(auUser.getStoreCode());
			
			model.addAttribute("storeSponsorsBank", storeSponsorsBank);
		} catch (Exception e) {
			LOGGER.error("查询个人银行卡信息出错！", e);
			return "";
		}
		
		return "models/pay/bankInfo";
	}
	
	@RequestMapping("updateBank/{id}")
	public String updateMyBankInfo(@PathVariable String id,ModelMap model){
		
		try {
			StoreSponsorsBank storeSponsorsBank = storeSponsorsBankService.findById(id);
			model.addAttribute("storeSponsorsBank", storeSponsorsBank);
		} catch (Exception e) {
			LOGGER.error("查询个人银行卡信息出错！", e);
			return "";
		}
		
		return "models/pay/updateCardInfo";
		
	}
	
	
	
	@RequestMapping("updateBankCard")
	public String updateBankInfo(StoreSponsorsBank storeSponsorsBank,HttpServletRequest request,ModelMap model){
		try {
			storeSponsorsBankService.update(storeSponsorsBank);
			storeSponsorsBank = storeSponsorsBankService.findById(storeSponsorsBank.getId().toString());
		} catch (Exception e) {
			LOGGER.error("更新银行卡信息出错！", e);
			model.addAttribute("message", "服务器繁忙，请稍后重试！");
			return "models/pay/updateCardInfo";
		}
		model.addAttribute("storeSponsorsBank", storeSponsorsBank);
		model.addAttribute("message", "更新银行卡成功！");
		return "models/pay/bankInfo";
	}
	
}
