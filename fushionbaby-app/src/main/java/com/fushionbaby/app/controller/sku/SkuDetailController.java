/**
 * 
 */
package com.fushionbaby.app.controller.sku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.condition.QueryCondition;
import com.fushionbaby.common.condition.member.MemberCommentQueryCondition;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.PageConstant;
import com.fushionbaby.common.dto.MemberCommentDto;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.sku.SelectedSkuDto;
import com.fushionbaby.common.dto.sku.SkuDetailDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.dto.sku.app.SkuBrowseHistoriesDto;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.member.MemberCommentFacade;
import com.fushionbaby.facade.biz.common.sku.MemberSkuCollectFacade;
import com.fushionbaby.facade.biz.common.sku.SkuDetailFacade;
import com.fushionbaby.facade.image.ImageProcess;

/**
 * @author mengshaobo
 * 
 */
@Controller
@RequestMapping("/sku")
public class SkuDetailController {
	

	/**记录日志*/
	private static final Log LOGGER = LogFactory.getLog(SkuDetailController.class);

	@Autowired
	private MemberSkuCollectFacade memberSkuCollectFacade;
	@Autowired
	private SkuDetailFacade skuDetailFacade;
	@Autowired
	private MemberCommentFacade commentFacade;
	@Autowired
	private ImageProcess imageProcess;
	/**
	 * 查询商品详情信息	
	 * 
	 * @param skuCode 
	 * 			    商品编号
	 * @param productCode
	 *            产品编号
	 * @param color
	 *            颜色
	 * @param size
	 *            尺寸
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("skuDetail")
	public Object skuDetail(@RequestParam(value="sid",defaultValue="") String sid,
			@RequestParam(value = "skuCode", defaultValue = "") String skuCode,
			@RequestParam(value = "productCode", defaultValue = "") String productCode,
			@RequestParam(value = "color", defaultValue = "") String color,
			@RequestParam(value = "size" , defaultValue = "")  String size,
			HttpServletRequest request) {
		
		
		SkuDetailDto skuDetail = null;
		try {
			SelectedSkuDto selectedSkuDto = new SelectedSkuDto();
			selectedSkuDto.setSkuCode(skuCode);
			selectedSkuDto.setProductCode(productCode);
			selectedSkuDto.setColor(color);
			selectedSkuDto.setSize(size);
			skuDetail = skuDetailFacade.getSkuDetailModel(selectedSkuDto);
			if(ObjectUtils.equals(null, skuDetail)){
				 return Jsonp.error();
			}
			skuDetail.setSkuImages(imageProcess.getThumImageList(skuDetail.getSelectedSku().getSkuCode(), ImageStandardConstant.IMAGE_STANDARD_APP_640));
			String selectedSkuCode = skuDetail.getSelectedSku().getSkuCode();
			if(ObjectUtils.equals("", selectedSkuCode)){
				 return Jsonp.error();
			}
			
			MemberCommentQueryCondition queryCondition = new MemberCommentQueryCondition();
			queryCondition.setSkuCode(selectedSkuCode);
			queryCondition.setStart(0);
			queryCondition.setLimit(1);
			MemberCommentDto comment = new MemberCommentDto();
			List<MemberCommentDto> commebtDtos = commentFacade.getMemberComments(queryCondition);
			if(!CollectionUtils.isEmpty(commebtDtos) && commebtDtos.size() >0){
				comment = commebtDtos.get(0);	
			}
			BeanNullConverUtil.nullConver(comment);
			skuDetail.setMemberComment(comment);

			
			List<SkuDto> linkSkuList = skuDetailFacade.getLinkSkus(skuCode);
			if (linkSkuList == null || CollectionUtils.isEmpty(linkSkuList)) {
				skuDetail.setLinkSkus(Collections.<SkuDto> emptyList());
			}else{
				skuDetail.setLinkSkus(linkSkuList);
			}
			skuDetail.setIsCollect(this.getIsCollect(sid, selectedSkuCode));
			UserDto user = (UserDto) SessionCache.get(sid);
			if(user !=null){
				skuDetailFacade.addOrUpdateBrowseHistories(user,skuCode);
			}
			
		} catch (Exception e) {
			LOGGER.error("商品详情异常"+e.getMessage());
			Jsonp.error("商品详情异常");
		}
		BeanNullConverUtil.nullConver(skuDetail);
		return Jsonp_data.success(skuDetail);
	}
	
	/**
	 * 是否已加入收藏
	 * 
	 * @param request
	 * @param skuId
	 *            商品编号
	 * @return
	 */
	private String getIsCollect(String sid, String skuCode) {

		if (StringUtils.isEmpty(sid)) {
			return CommonConstant.NO;
		}
		UserDto user = (UserDto) SessionCache.get(sid);
		if(user == null){
			return CommonConstant.NO;
		}
		return memberSkuCollectFacade.getIsCollect(user, skuCode);
	}

	/**
	 *  商品历史浏览记录列表
	 * @param sid
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("skuBrowseHistories")
	public Object skuBrowseHistories(@RequestParam(value="sid",defaultValue="") String sid,
			@RequestParam(value="pageIndex",defaultValue=PageConstant.APP_PAGE_INDEX+"")Integer curPage,
			@RequestParam(value="pageSize",defaultValue=PageConstant.APP_PAGE_SIZE+"")Integer limit,
			HttpServletRequest request) {
		
		UserDto user = (UserDto) SessionCache.get(sid);
		
		QueryCondition queryCondition = new QueryCondition();
		
		queryCondition.setId(user.getMemberId());
		queryCondition.setStart((curPage - 1) * limit);
		queryCondition.setLimit(limit);
		
		List<SkuBrowseHistoriesDto> browseHistoriesDtoList = skuDetailFacade.getSkuBrowseHistorises( queryCondition);
		if(browseHistoriesDtoList == null){
			return Jsonp_data.success(new ArrayList<SkuBrowseHistoriesDto>());
		}
		
		
		return Jsonp_data.success(browseHistoriesDtoList);
	}
	
}
