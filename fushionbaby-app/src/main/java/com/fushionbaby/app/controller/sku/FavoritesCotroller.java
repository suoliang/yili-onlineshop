package com.fushionbaby.app.controller.sku;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.condition.sku.SkuCollectQueryCondition;
import com.fushionbaby.common.constants.AppConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.PageConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.enums.SkuStatusEnum;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.sku.MemberSkuCollectFacade;
import com.fushionbaby.facade.image.ImageProcess;

/**
 * 该Cotroller主要实现功能有： 1、将商品添加到用户收藏夹。 2、将商品从用户收藏夹移除。 3、查看用户收藏夹列表
 * 
 * @author cyla
 */

@Controller
@RequestMapping("/favorites")
public class FavoritesCotroller  {
	@Autowired
	private MemberSkuCollectFacade collectFacade;
	@Autowired
	private ImageProcess imageProcess;
	/**
	 * 添加商品到用户收藏夹
	 * 
	 * @param userId
	 *            用户Id
	 * @param skuId
	 *            商品Id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addFavorites")
	public Object addFavorites(@RequestParam(value = "sid") String sid,
			@RequestParam(value = "skuCode", defaultValue = "") String skuCode){
		if (StringUtils.isEmpty(sid)) {
			return Jsonp.paramError();
		}
		UserDto user = (UserDto) SessionCache.get(sid);
		int addResult = collectFacade.addSkuCollect(skuCode, user);
		if (addResult == AppConstant.SUCCESS_ADD) {
			return Jsonp.success();
		}else if(addResult == AppConstant.HAS_ADD){
			return Jsonp.newInstance(ObjectUtils.toString(addResult), "该商品已经加入收藏.");
		}else if(addResult == AppConstant.NOT_LOGIN){
			return Jsonp.noLoginError("用户未登录.");
		}
		return Jsonp.newInstance("100", "该商品不存在");
	}

	/**
	 * 从用户收藏夹中删除一件商品
	 * 
	 * @param userId
	 *            用户Id
	 * @param skuId
	 *            商品Id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteFavorites")
	public Object deleteFavorites(@RequestParam(value = "sid") String sid,
			@RequestParam(value = "skuCode", defaultValue = "") String skuCode)
		{
		if (StringUtils.isEmpty(sid)) {
			return Jsonp.paramError();
		}
		UserDto user = (UserDto) SessionCache.get(sid);
		if (ObjectUtils.equals(null, user)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}
		Boolean delResult  = collectFacade.dropCollectBySkuCode(skuCode, user);
		if(ObjectUtils.equals(null, delResult)){
			return Jsonp.paramError();
		}else if (ObjectUtils.equals(delResult, Boolean.FALSE)) {
			return Jsonp.newInstance(ObjectUtils.toString(delResult), "该商品已经加入收藏.");
		}
		return Jsonp.success();

	}

	/**
	 * 全部收藏列表,分页
	 * 
	 * @param sid
	 *            用户id
	 * @param page_index
	 *            页码
	 * @param page_size
	 *            每页显示多少条
	 * @return
	 * 
	 */
	@ResponseBody
	@RequestMapping("/favoritesList")
	public Object favoritesList(@RequestParam("sid")String sid,
			@RequestParam(value="pageIndex",defaultValue=PageConstant.APP_PAGE_INDEX+"")Integer curPage,
			@RequestParam(value="pageSize",defaultValue=PageConstant.APP_PAGE_SIZE+"")Integer limit) {

		if (StringUtils.isEmpty(sid)) {
			return Jsonp.paramError();
		}
		UserDto user = (UserDto) SessionCache.get(sid);
		if (ObjectUtils.equals(null, user)) {
			return Jsonp.noLoginError(CommonMessage.NO_LOGIN);
		}
		SkuCollectQueryCondition queryCondition = new SkuCollectQueryCondition();
		queryCondition.setMemberId(user.getMemberId());
		queryCondition.setStart((curPage - 1) * limit);
		queryCondition.setLimit(limit);
		queryCondition.setStatus(SkuStatusEnum.SKU_STATUS_TOP.getStrVlue());
		queryCondition.setIsAttention(CommonConstant.YES);
		
		
		List<SkuDto> skuDtoList= collectFacade.findCollect(queryCondition);
		if(ObjectUtils.equals(null, skuDtoList)){
			return Jsonp.error();
		}
		for(SkuDto skuDto : skuDtoList){
			skuDto.setImgPath( imageProcess.getThumImagePath(skuDto.getSkuCode(), ImageStandardConstant.IMAGE_STANDARD_APP_150) );
		}
		if (CollectionUtils.isEmpty(skuDtoList)) {
			return Jsonp_data.success(Collections.EMPTY_LIST);
		}
		return Jsonp_data.success(skuDtoList);

	}

}
