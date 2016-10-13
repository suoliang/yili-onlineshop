/**
 * 
 */
package com.fushionbaby.app.controller.sku;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.app.controller.home.HomeController;
import com.fushionbaby.app.dto.AppMemberCommentDto;
import com.fushionbaby.app.util.ShareConstantApp;
import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.condition.member.MemberCommentQueryCondition;
import com.fushionbaby.common.condition.sku.SkuEntryQueryCondition;
import com.fushionbaby.common.condition.sku.SkuSearchQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.PageConstant;
import com.fushionbaby.common.constants.SortTypeConstant;
import com.fushionbaby.common.constants.SourceConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.dto.sku.web.PageSetDto;
import com.fushionbaby.common.dto.sysmgr.SearchKeywordsDto;
import com.fushionbaby.common.enums.CommentLevelEnum;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.member.MemberCommentFacade;
import com.fushionbaby.facade.biz.common.order.dto.SkuSearchResultDto;
import com.fushionbaby.facade.biz.common.sku.SkuDetailFacade;
import com.fushionbaby.facade.biz.common.sku.SkuFacade;
import com.fushionbaby.facade.image.ImageProcess;
import com.fushionbaby.member.model.MemberSkuComment;
import com.fushionbaby.member.model.MemberSkuShareContent;
import com.fushionbaby.member.service.MemberSkuCommentService;
import com.fushionbaby.member.service.MemberSkuShareContentService;

/**
 * 商品信息
 * 
 * @author mengshaobo
 * 
 */
@Controller
@RequestMapping("/sku")
public class SkuInfoController  {
	@Autowired
	private SkuFacade skuFacade;
	@Autowired
	private MemberCommentFacade memberCommentFacade;
	@Autowired
	private SkuDetailFacade skuDetailFacade;
	@Autowired
	private MemberSkuShareContentService memberSkuShareService;
	@Autowired
	private ImageProcess imageProcess;
	
	/** 会员评论 */
	@Autowired
	private MemberSkuCommentService<MemberSkuComment> memberCommentService;
	
	
	private static final Log LOGGER = LogFactory.getLog(HomeController.class);

	/**
	 * 品牌的商品列表
	 * 
	 * @param brandId
	 *            商品编号
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/brandSkuList")
	public Object brandSkuList(@RequestParam("brandCode") String brandCode,
			@RequestParam(value="pageIndex",defaultValue=PageConstant.APP_PAGE_INDEX+"")Integer curPage,
			@RequestParam(value="pageSize",defaultValue=PageConstant.APP_PAGE_SIZE+"")Integer limit,
			@RequestParam(value = "sortParam", defaultValue = SortTypeConstant.DEFAULT + "") String sortParam,
			@RequestParam(value = "sortType", defaultValue = "") String sortType) {

		if (brandCode == null) {
			return Jsonp_data.success(Collections.EMPTY_LIST);
		}
		SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
		queryCondition.setBrandCode(brandCode);
		queryCondition.setStart((curPage - 1) * limit);
		queryCondition.setLimit(limit);
		queryCondition.setSortParam(sortParam);
		queryCondition.setSortType(sortType);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		queryCondition.setImageStandardCode(ImageStandardConstant.IMAGE_STANDARD_PC_300);
		
		PageSetDto pageSet = skuFacade.searchSkuList(queryCondition);
		if (ObjectUtils.equals(null, pageSet) || CollectionUtils.isEmpty(pageSet.getResults())) {
			return Jsonp_data.success(Collections.EMPTY_LIST);
		}
		
		return Jsonp_data.success(pageSet.getResults());
	}

	
	/**
	 * 搜索商品
	 * 
	 * @param searchKey
	 *            搜索关键字
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/search")
	public Object search(@RequestParam("sourceCode") String source,
			@RequestParam("searchKey") String keywords,
			@RequestParam(value="labelCode",defaultValue="") String labelCode,
			@RequestParam(value="categoryCode",defaultValue="") String categoryCode,
			@RequestParam(value="brandCode",defaultValue="") String brandCode,
			@RequestParam(value="price",defaultValue="") String price,
			@RequestParam(value="pageIndex",defaultValue=PageConstant.APP_PAGE_INDEX+"")Integer curPage,
			@RequestParam(value="pageSize",defaultValue=PageConstant.APP_PAGE_SIZE+"")Integer limit,
			@RequestParam(value = "sortParam", defaultValue = "") String sortParam,
			@RequestParam(value = "sortType", defaultValue = "") String sortType) {

		if (StringUtils.isBlank(StringUtils.trim(keywords))) {
			return Jsonp_data.success(Collections.EMPTY_LIST);
		}
		String searchKey="";
		try {
			
			if(StringUtils.isNotBlank(keywords)){
//				try {
//					byte bb[] = keywords.getBytes("ISO-8859-1");
//					searchKey = new String(bb,"UTF-8");
//				} catch (UnsupportedEncodingException e1) {
//					e1.printStackTrace();
//				}
				searchKey = keywords;
			}
			SearchKeywordsDto searchKeywordsDto = new SearchKeywordsDto();
			searchKeywordsDto.setKeyword(StringUtils.trim(searchKey));
			searchKeywordsDto.setSourceCode(source);
			skuFacade.insertSearchKey(searchKeywordsDto);
		} catch (Exception e) {
			e.printStackTrace();
			return Jsonp_data.success(Collections.EMPTY_LIST);
		}
//		SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
//		queryCondition.setSearchKey(searchKey);
//		queryCondition.setStart((curPage - 1) * limit);
//		queryCondition.setLimit(limit);
//		queryCondition.setSortParam(sortParam);
//		queryCondition.setSortType(sortType);
//		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
//		queryCondition.setImageStandardCode(ImageStandardConstant.IMAGE_STANDARD_PC_300);
//		
//		PageSetDto pageSet = skuFacade.searchSkuList(queryCondition);
//		if (ObjectUtils.equals(null, pageSet) || CollectionUtils.isEmpty(pageSet.getResults())) {
//			return Jsonp_data.success(Collections.EMPTY_LIST);
//		}
//
//		return Jsonp_data.success(pageSet.getResults());
		
		SkuSearchQueryCondition queryCondition = new SkuSearchQueryCondition();
		queryCondition.setSearchKey(searchKey);
		queryCondition.setStart(curPage);
		queryCondition.setLimit(limit);
		queryCondition.setSortParam(sortParam);
		queryCondition.setSortType(sortType);
		queryCondition.setPrice(price);
		queryCondition.setBrandCode(brandCode);
		queryCondition.setCategoryCode(categoryCode);
		queryCondition.setLabelCode(labelCode);
		queryCondition.setImageStandardCode(ImageStandardConstant.IMAGE_STANDARD_PC_300);
		SkuSearchResultDto searchResullt = skuFacade.skuListBysearchKey(queryCondition);
		@SuppressWarnings("unchecked")
		List<SkuDto> skuList = (List<SkuDto>) searchResullt.getPageSet().getResults();
		if (ObjectUtils.equals(null, skuList) || CollectionUtils.isEmpty(skuList)) {
			return Jsonp_data.success(Collections.EMPTY_LIST);
		}
		return Jsonp_data.success(skuList);
		
	}

	/**
	 * 查询评论信息列表 
	 * 
	 * @param skuCode
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/evaluateList")
	public Object queryEvaluateList(@RequestParam("skuCode") String skuCode,
			@RequestParam(value="level",defaultValue="") String commentLevel,
			@RequestParam(value="pageIndex",defaultValue=PageConstant.APP_PAGE_INDEX+"")Integer index,
			@RequestParam(value="pageSize",defaultValue=PageConstant.APP_PAGE_SIZE+"")Integer size) {

		if (StringUtils.isBlank(skuCode)) {
			return Jsonp_data.success(Collections.EMPTY_LIST);
		}
		
		MemberCommentQueryCondition queryCondition = new MemberCommentQueryCondition();
		queryCondition.setSkuCode(skuCode);
		queryCondition.setStart((index - 1) * size);
		queryCondition.setLimit(size);
		if(StringUtils.isNotBlank(commentLevel) && !StringUtils.equals(commentLevel, "0")){
			queryCondition.setLevel(commentLevel);
		}
		
		AppMemberCommentDto commentDto = new AppMemberCommentDto();
		commentDto.setCommentDtoList(memberCommentFacade.getMemberComments(queryCondition));
		queryCondition.setLevel(null);
		commentDto.setAllCount(memberCommentService.getTotalByCondition(queryCondition));
		queryCondition.setLevel(CommentLevelEnum.GOOD.getCode());
		commentDto.setGoodCount(memberCommentService.getTotalByCondition(queryCondition));
		queryCondition.setLevel(CommentLevelEnum.SECONDARY.getCode());
		commentDto.setSecondaryCount(memberCommentService.getTotalByCondition(queryCondition));
		queryCondition.setLevel(CommentLevelEnum.LOWEST.getCode());
		commentDto.setLowestCount(memberCommentService.getTotalByCondition(queryCondition));
		
		return Jsonp_data.success(commentDto);
	}

	/**
	 * 查询出猜你喜欢的商品
	 * 
	 * @param id
	 *            当前商品编号
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/linkList")
	public Object queryLinkList(@RequestParam("skuCode") String skuCode) {
		if (ObjectUtils.equals("",skuCode)) {
			return Jsonp_data.success(Collections.EMPTY_LIST);
		}
		
		List<SkuDto> skuList = skuDetailFacade.getLinkSkus(skuCode);
		if (CollectionUtils.isEmpty(skuList)) {
			return Jsonp_data.success(Collections.EMPTY_LIST);
		}
		return Jsonp_data.success(skuList);

	}
	/**
	 * 通过分类号查询产品列表
	 * 
	 * @param categoryId
	 *            分类编号
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/categorySkuList")
	public Object productListByCategory(
			@RequestParam("categoryCode") String categoryCode,
			@RequestParam(value="storeCode",defaultValue="") String storeCode,
			@RequestParam(value="pageIndex",defaultValue=PageConstant.APP_PAGE_INDEX+"")Integer curPage,
			@RequestParam(value="pageSize",defaultValue=PageConstant.APP_PAGE_SIZE+"")Integer limit,
			@RequestParam(value = "sortParam", defaultValue = SortTypeConstant.DEFAULT + "") String sortParam,
			@RequestParam(value = "sortType", defaultValue = "") String sortType) {
		
		SkuEntryQueryCondition queryCondition = new SkuEntryQueryCondition();
		queryCondition.setStoreCode(storeCode);
		queryCondition.setCategoryCode(categoryCode);
		queryCondition.setStart((curPage - 1) * limit);
		queryCondition.setLimit(limit);
		queryCondition.setSortParam(sortParam);
		queryCondition.setSortType(sortType);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		queryCondition.setImageStandardCode(ImageStandardConstant.IMAGE_STANDARD_PC_300);
		
		PageSetDto pageSet = null;
		try {
			pageSet = skuFacade.searchSkuList(queryCondition);
			if (ObjectUtils.equals(null, pageSet) || CollectionUtils.isEmpty(pageSet.getResults())) {
				return Jsonp_data.success(Collections.EMPTY_LIST);
			}
		} catch (Exception e) {
			return Jsonp.error("查询分类信息出现异常！");
		}
		return Jsonp_data.success(pageSet.getResults());
	}
	
	/**
	 * 商品分享
	 * @param sid
	 * @param skuCode 商品编号
	 * @param shareContent 分享内容
	 * @param sourceCode  来源编号
	 * @param shareUrl  分享链接
	 * @return
	 */
	@ResponseBody
	@RequestMapping("skuShare")
	public Object skuShare(@RequestParam(value="sid",defaultValue="") String sid,
			@RequestParam(value="shareContent",defaultValue="") String shareContent,
			@RequestParam("skuCode") String skuCode,
			@RequestParam("sourceCode") String sourceCode,
			@RequestParam(value="sourceType",defaultValue="") String sourceType){
		try {
			String titleUrl = "";
			if(StringUtils.isBlank(sourceType)){
				if(StringUtils.equals(SourceConstant.ANDROID_CODE, sourceCode)){
					titleUrl = ShareConstantApp.UPLOAD_URL_ANDROID;
					
				}else if(StringUtils.equals(SourceConstant.IOS_CODE, sourceCode)){
					titleUrl = ShareConstantApp.UPLOAD_URL_IOS;
				}
			}else{
				titleUrl = ShareConstantApp.URL+ "/sku/skuDetail?skuCode="+skuCode;
			}
			String title = ShareConstantApp.TITLE;
			String text = ShareConstantApp.TEXT;
			MemberSkuShareContent memberSkuShare = new MemberSkuShareContent();
			memberSkuShare.setSkuCode(skuCode);
			memberSkuShare.setSourceCode(sourceCode);
			memberSkuShare.setShareContent(shareContent);
			memberSkuShare.setShareUrl(titleUrl);
			Map<String, String> result = new HashMap<String, String>();
			String flag = "";
			if(StringUtils.isNotBlank(sid)){
				UserDto user = (UserDto) SessionCache.get(sid);
				memberSkuShare.setMemberId(user.getMemberId());
				flag = skuDetailFacade.addmemberSkuShare(user, memberSkuShare);	
			}
			if(StringUtils.equals(flag, CommonConstant.YES)){
				result.put("status", "1");
			}else{
				result.put("status", "0");
			}
			result.put("title",title);
			result.put("titleUrl", titleUrl);
			result.put("text", text);
			return Jsonp_data.success(result);
		} catch (DataAccessException e) {
			return Jsonp.error();
		}
	}
	
}
