package com.fushionbaby.cms.controller.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.card.model.MemberCard;
import com.aladingshop.card.model.MemberCardConfig;
import com.aladingshop.card.service.MemberCardConfigService;
import com.aladingshop.card.service.MemberCardService;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.MemberCardDto;
import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.Member;
import com.fushionbaby.member.service.MemberService;
import com.fushionbaby.order.model.OrderBase;


/***
 * 会员储蓄卡 益多宝
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年9月7日下午1:22:09
 */
@Controller
@RequestMapping("/memberCard")
public class MemberCardController extends BaseController{
	

	private static final Log Member_Card_log=LogFactory.getLog(MemberCardController.class);
	
	@Autowired
	private MemberCardService<MemberCard> memberCardService;
	
	@Autowired
	private MemberService<Member> memberService;
	
	@Autowired
	private MemberCardConfigService<MemberCardConfig> memberCardConfigService;
	
	private static final String PRE_="models/card/";
	
	//private static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/memberCard/memberCardList";
	
	/***
	 * 会员购卡记录表
	 * 会员账户
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("memberCardList")
	public String memberCardRecordList(MemberCardDto memberCardDto,	BasePagination page,Model model){
		Member_Card_log.info("cms：MemberCardController 生成会员储蓄卡开始");
		if (page == null) {
			page = new BasePagination();
		}
		Map<String, String> params = new HashMap<String, String>();
		Member member=this.memberService.findByLoginName(memberCardDto.getMemberName());
		if(member != null){
			params.put("memberId", member.getId().toString());
		}else if(memberCardDto.getMemberName() != ""){
			params.put("memberId", "");
		}
		params.put("createTimeFrom", memberCardDto.getCreateTimeFrom());
		params.put("createTimeTo", memberCardDto.getCreateTimeTo());
		params.put("cardNo", memberCardDto.getCardNo());
		params.put("acount", memberCardDto.getAcount());
		page.setParams(params);
		page = this.memberCardService.getListPage(page);
		List<MemberCard> MemberCardlist = (List<MemberCard>) page.getResult();
		List<MemberCardDto> memberCardDtoList=new ArrayList<MemberCardDto>();
			for (MemberCard memberCard : MemberCardlist) {
				Member member1 = memberService.findById(memberCard.getMemberId());
				MemberCardDto cardDto=new MemberCardDto();
				if(member1!=null)	
					cardDto.setMemberName(member1.getLoginName());
				cardDto.setCardNo(memberCard.getCardNo());
				cardDto.setFaceValue(memberCard.getTotalCorpus());
				cardDto.setCreateTime(memberCard.getCreateTime());
				cardDto.setCode(memberCard.getCode());
				cardDto.setAcount(memberCard.getAcount());
				cardDto.setTotalRebate(memberCard.getTotalRebate());
				cardDto.setType(memberCard.getType());
				MemberCardConfig memberCardConfig=memberCardConfigService.findById(memberCard.getCardConfigId());
				if(memberCardConfig!=null){
					cardDto.setCardType(memberCardConfig.getType());
				}
				memberCardDtoList.add(cardDto);
			}
		model.addAttribute("memberCardDto", memberCardDto);
		model.addAttribute("list", memberCardDtoList);
		model.addAttribute("page", page);
		return PRE_+"memberCardList";
	}

	@ResponseBody
	@RequestMapping(value = "/updateType", method = RequestMethod.POST)
	public Object updateType(@RequestParam String code,@RequestParam String type,
			RedirectAttributes redirectAttributes,ModelMap model) {
		JsonResponseModel jsonModel = new JsonResponseModel();
		try {
			MemberCard memberCard = memberCardService.findByCardCode(code);
			memberCard.setType(type);
			memberCardService.update(memberCard);
			jsonModel.Success("订单类型修改成功!");
		} catch (Exception e) {
			jsonModel.Fail("订单类型修改失败!");
		}
		return jsonModel;
	}

}
