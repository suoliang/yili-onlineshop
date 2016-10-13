package com.fushionbaby.cms.controller.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.sku.cms.model.Sku;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.fushionbaby.common.model.JsonResponseModel;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.member.model.MemberSkuComment;
import com.fushionbaby.member.service.MemberSkuCommentService;
/***
 * 会员评论
 * 
 * @author xupeijun
 *
 */
@Controller
@RequestMapping("/member")
public class MemberCommentController {

	@Autowired
	private MemberSkuCommentService<MemberSkuComment> memberCommentService;
	@Autowired
	private SkuInfoService skuInfoService;
	
	private static final Log logg=LogFactory.getLog(MemberCommentController.class);

	/***
	 * 商品评论列表
	 * @param memberName
	 * @param skuCode
	 * @param page
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("memberCommentList")
	public String skuCommentList(
			@RequestParam(value = "memberName", defaultValue = "") String memberName,
			@RequestParam(value = "skuNo", defaultValue = "") String skuNo,
			BasePagination page, ModelMap model) {
		try {
			
			if (page == null) {
				page = new BasePagination();
			}
			model.addAttribute("page", page);
			model.addAttribute("memberName", memberName);
			model.addAttribute("skuNo", skuNo);
			Map<String, String> params = new HashMap<String, String>();
			params.put("memberName", memberName);
			if(StringUtils.isNotEmpty(skuNo)){
				Sku sku=skuInfoService.queryBySkuNo(skuNo,null);
				if(ObjectUtils.equals(null, sku)){
					model.addAttribute("memberCommentList", new ArrayList<MemberSkuComment>());
					return "models/member/memberCommentList";
				}
				String uniqueCode=sku.getUniqueCode();
				params.put("skuCode", uniqueCode);
			}
			
			page.setParams(params);
			/** 分页对象 */
			page = memberCommentService.getListPage(page);
			/** 分页结果集 */
			List<MemberSkuComment> memberCommentList = (List<MemberSkuComment>) page
					.getResult();
			for(MemberSkuComment memberSkuComment : memberCommentList){
				Sku skuInfo=skuInfoService.queryByUniqueCode(memberSkuComment.getSkuCode());
				if(skuInfo!=null)
				memberSkuComment.setSkuCode(skuInfo.getSkuNo());
			}
			model.addAttribute("memberCommentList", memberCommentList);

		} catch (Exception e) {
			logg.error("商品评论列表加载失败", e);		}
		return "models/member/memberCommentList";
	}
	
/***
 * 改变商品评论disable的状态 
 * @param id
 * @param disable
 * @param response
 */
	@ResponseBody
	@RequestMapping("changeDisable")
	public JsonResponseModel changeDisable(@RequestParam("id")String id,@RequestParam("disable") String disable) {
		JsonResponseModel jsonModel = new JsonResponseModel();

		try {
			memberCommentService.updateDisable(Long.valueOf(id), disable);
			jsonModel.Success("商品评论状态改变成功!");

		} catch (Exception e) {
			jsonModel.Fail("商品评论状态改变失败!");
			logg.error("商品评论状态改变失败!", e);
		}
	return jsonModel;
	}
	
	/***
	 * 改变商品评论Status的状态 
	 * @param id
	 * @param disable
	 * @param response
	 */
		@ResponseBody
		@RequestMapping("changeStatus")
		public JsonResponseModel changeStatus(@RequestParam("id")String id,@RequestParam("status") String status) {
			JsonResponseModel jsonModel = new JsonResponseModel();

			try {
				memberCommentService.updateStatus(Long.valueOf(id), status);
				jsonModel.Success("商品评论审核状态改变成功!");

			} catch (Exception e) {
				jsonModel.Fail("商品评论审核状态改变失败!");
				logg.error("商品评论审核状态改变失败!", e);
			}
		return jsonModel;
		}
}
