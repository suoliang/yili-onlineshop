 package com.fushionbaby.app.controller.cart;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.condition.ShoppingCartBo;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonCode;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.PageConstant;
import com.fushionbaby.common.constants.WebConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.cart.CartDto;
import com.fushionbaby.common.dto.cart.CartModifyAfterDto;
import com.fushionbaby.common.enums.ShoppingCartCaptChaEnum;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.CheckObjectIsNull;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.core.condition.ShoppingCartQueryCondition;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartAddFacade;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartModifyFacade;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartQueryFacade;
import com.fushionbaby.facade.biz.common.cart.ShoppingCartRemoveFacade;

/**
 * @author 张明亮 购物车controller
 */
@Controller
@RequestMapping("/cart")
public class ShoppingCartController  {
	private static final Log LOGGER = LogFactory.getLog(ShoppingCartController.class);
	@Autowired
	private ShoppingCartModifyFacade modifyFacade;
	@Autowired
	private ShoppingCartQueryFacade queryFacade;
	@Autowired
	private ShoppingCartAddFacade addFacade;
	@Autowired
	private ShoppingCartRemoveFacade removeFacade;
	

	/**
	 * 返回到客户端一个唯一key值
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getVisitKey")
	public Object getUUID() {
		String visitKey = RandomNumUtil.getUUIDString() + RandomNumUtil.getUUIDString();
		return Jsonp_data.success(visitKey);
	}
	/**
	 * 购物车商品数量
	 * @param visitKey
	 * @param sid
	 * @param storeCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("cartSkuCount")
	public Object cartSkuCount(@RequestParam(value="visitKey",defaultValue="")String visitKey,
			@RequestParam(value="sid",defaultValue="") String sid,
			@RequestParam(value="storeCode",defaultValue="") String storeCode){
		if (StringUtils.isBlank(visitKey) && StringUtils.isBlank(sid)) {
			return Jsonp.paramError("visitKey和sid最少写一个参数!");
		}
		UserDto user = null;
		if (StringUtils.isNotBlank(sid)){
			user = (UserDto) SessionCache.get(sid);
		}
		Integer pnum = 0;
		if (StringUtils.isNotBlank(visitKey) && ObjectUtils.equals(null, user)) {
			ShoppingCartBo shoppingCartBo = new ShoppingCartBo();
			shoppingCartBo.setStoreCode(storeCode);
			shoppingCartBo.setVisitKey(visitKey);
			pnum = addFacade.getRedisCount(shoppingCartBo);
		} else if (ObjectUtils.notEqual(null, user)) {
			
			ShoppingCartQueryCondition cartQueryCondition = new ShoppingCartQueryCondition();
			cartQueryCondition.setIsSelected(CommonConstant.YES);
			cartQueryCondition.setMemberId(user.getMemberId());
			cartQueryCondition.setStoreCode(storeCode);
			
			pnum = addFacade.getSelectedCartSkuCountByMemberId(cartQueryCondition);
		}
		return Jsonp_data.success(pnum);
	}
	
/**
 * 购物车查询
 * @param visitKey
 * @param sid
 * @param pageIndex
 * @param pageSize
 * @return
 */
	@ResponseBody
	@RequestMapping("cartList")
	public Object cartList(@RequestParam(value="visitKey",defaultValue="") String visitKey,
			@RequestParam(value="sid",defaultValue="") String sid,
			@RequestParam(value="storeCode",defaultValue="") String storeCode,
			@RequestParam(value="pageIndex",defaultValue=PageConstant.APP_PAGE_INDEX+"")Integer curPage,
			@RequestParam(value="pageSize",defaultValue=PageConstant.APP_PAGE_SIZE+"")Integer limit) {
		if (StringUtils.isBlank(visitKey) && StringUtils.isBlank(sid)) {
			return Jsonp.paramError("visitKey和sid最少写一个参数!");
		}
		CartDto cartDto = new CartDto();
		try {
			UserDto user = null;
			if (StringUtils.isNotBlank(sid)){
				user = (UserDto) SessionCache.get(sid);
				if (CheckObjectIsNull.isNull(user)) {
					return Jsonp.error("请先登录或重新登录");
				}
			}
			ShoppingCartBo shoppingCartBo = new ShoppingCartBo();
			shoppingCartBo.setStoreCode(storeCode);
			shoppingCartBo.setUser(user);
			shoppingCartBo.setVisitKey(visitKey);
			shoppingCartBo.setStart(curPage);
			shoppingCartBo.setLimit(limit);
			shoppingCartBo.setImageType(ImageStandardConstant.IMAGE_STANDARD_PC_300);
			String shoppingCartSid = RandomNumUtil.getCharacterAndNumber(WebConstant.UNIQUE_CODE);
			shoppingCartBo.setShoppingCartSid(shoppingCartSid);
			cartDto = queryFacade.queryCartDto(shoppingCartBo);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("查询购物车时出现异常" + e);
			return Jsonp.error();
		}
		BeanNullConverUtil.nullConver(cartDto);
		return Jsonp_data.success(cartDto);
	}
	
/**
 * 购物车添加
 * @param visitKey
 * @param sid
 * @param skuId
 * @param quantity
 * @return
 */
	@ResponseBody
	@RequestMapping("addToCart")
	public Object cartAddCommodity(@RequestParam(value="visitKey",defaultValue="")String visitKey,
			@RequestParam(value="sid",defaultValue="") String sid, 
			@RequestParam(value="storeCode",defaultValue="") String storeCode, 
			@RequestParam("skuCode") String skuCode,
			@RequestParam(value="quantity",defaultValue="1") String quantity) {
		
		String resultCode = CommonCode.SUCCESS_CODE;
		if (StringUtils.isBlank(sid) && StringUtils.isBlank(visitKey)) {
			//return Jsonp.paramError("visitKey和sid最少写一个参数!");
			resultCode = ShoppingCartCaptChaEnum.SID_AND_VISITKEY_ISNULL.getCode();
			return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
		}
		if (ObjectUtils.equals(null, skuCode)) {
			//return Jsonp.paramError("商品code不能为空!");
			resultCode = ShoppingCartCaptChaEnum.SKU_CODE_ISNULL.getCode();
			return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
		}
		if (StringUtils.isBlank(quantity)) {
			//return Jsonp.paramError("购买数量quantity参数不能为空!");
			resultCode = ShoppingCartCaptChaEnum.QUANTITY_ISNULL.getCode();
			return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
		}
		int quantityNumber = 0;
		try {
			quantityNumber = Integer.valueOf(quantity);
			quantityNumber = quantityNumber < 1 ? 1 : quantityNumber;
		} catch (NumberFormatException ne) {
			resultCode = ShoppingCartCaptChaEnum.QUANTITY_FORMAT_EXCEPTION.getCode();
			return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
		}
		String checkSkuInventoryNum = addFacade.checkSkuInventoryNum(quantityNumber,skuCode);
		if(ObjectUtils.notEqual(CommonCode.SUCCESS_CODE, checkSkuInventoryNum)){
			resultCode = checkSkuInventoryNum;
			return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
		}
		
		UserDto user = null;
		if (StringUtils.isNotBlank(sid)) {
			user = (UserDto) SessionCache.get(sid);
			if (CheckObjectIsNull.isNull(user)) {
				return Jsonp.error("请先登录或重新登录");
			}
		}	
		ShoppingCartBo shoppingCartBo = new ShoppingCartBo();
		
		shoppingCartBo.setSkuCode(skuCode);
		shoppingCartBo.setStoreCode(storeCode);
		shoppingCartBo.setQuantity(quantityNumber);
		Jsonp_data checkResult = null;
		int pnum = 0;
		if (ObjectUtils.notEqual(null, user)) {
			shoppingCartBo.setUser(user);
			checkResult = addFacade.checkHasLoginCartSize(shoppingCartBo);
			if(!StringUtils.equals(checkResult.getResponseCode(), CommonCode.SUCCESS_CODE)){
				return checkResult;
			}
		
			Jsonp addResult = addFacade.hasLoginCartAdd(shoppingCartBo);
			if(!StringUtils.equals(addResult.getResponseCode(), CommonCode.SUCCESS_CODE)){
				return Jsonp.error();
			}
			ShoppingCartQueryCondition cartQueryCondition = new ShoppingCartQueryCondition(); 
			cartQueryCondition.setIsSelected(CommonConstant.YES);
			cartQueryCondition.setMemberId(user.getMemberId());
			cartQueryCondition.setStoreCode(storeCode);
			
			pnum = addFacade.getSelectedCartSkuCountByMemberId(cartQueryCondition);
		} else if(StringUtils.isNotBlank(visitKey)){
			shoppingCartBo.setVisitKey(visitKey);
			checkResult =  addFacade.checkNoLoginCartSize(shoppingCartBo);
			if(!StringUtils.equals(checkResult.getResponseCode(), CommonCode.SUCCESS_CODE)){
				return checkResult;
			}
			addFacade.noLoginCartAdd(shoppingCartBo);
			pnum = addFacade.getRedisCount(shoppingCartBo);
		}
		
		return Jsonp_data.success(pnum);
	}
	

	/**
	 * 删除购物车中一件商品
	 * 
	 * @param visitKey
	 * @param sid
	 * @param skuId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("cartDeleteCommodity")
	public Object cartDeleteCommodity(@RequestParam(value="visitKey",defaultValue="")String visitKey,
			@RequestParam(value="sid",defaultValue="") String sid, 
			@RequestParam("skuCode") String skuCode,
			@RequestParam("shoppingCartSid") String shoppingCartSid) {

		String resultCode = CommonCode.SUCCESS_CODE;
		if (StringUtils.isBlank(sid) && StringUtils.isBlank(visitKey)) {
			resultCode = ShoppingCartCaptChaEnum.SID_AND_VISITKEY_ISNULL.getCode();
			return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
		}
		if (StringUtils.isBlank(shoppingCartSid)) {
			resultCode = ShoppingCartCaptChaEnum.SHOPPING_CART_SID_ISNULL.getCode();
			return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
		}
		if (StringUtils.isBlank(skuCode)) {
			resultCode = ShoppingCartCaptChaEnum.SKU_CODE_ISNULL.getCode();
			return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
		}
		UserDto user = null;
		if (StringUtils.isNotBlank(sid)) {
			user = (UserDto) SessionCache.get(sid);
			if (CheckObjectIsNull.isNull(user)) {
				return Jsonp.error("请先登录或重新登录");
			}
		}
		CartDto cartDto = new CartDto();	
		try {
			String[] skuCodes = new String[]{skuCode};
			ShoppingCartBo shoppingCartBo = new ShoppingCartBo();
			shoppingCartBo.setUser(user);
			shoppingCartBo.setVisitKey(visitKey);
			shoppingCartBo.setSkuCodeArray(skuCodes);
			shoppingCartBo.setShoppingCartSid(shoppingCartSid);
			if (ObjectUtils.notEqual(null, user)) {
				cartDto =	removeFacade.hasLoginRemoveBath(shoppingCartBo);
			} else {
				cartDto =	removeFacade.noLoginRemoveBath( shoppingCartBo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("ShoppingCartRemoveController.removeBath购物车删除商品,系统出现异常" + e);
			return Jsonp.error();
		}
		BeanNullConverUtil.nullConver(cartDto);
		
		return Jsonp_data.success(cartDto);
	}

	/**
	 * 修改购物车的商品购买数量
	 * 
	 * @param cart_rows_id
	 * @param quantity
	 */
	@ResponseBody
	@RequestMapping("modifyItemQuantity")
	public Object updateCartItemQuantity(
			@RequestParam(value="visitKey",defaultValue="")String visitKey,
			@RequestParam(value="sid",defaultValue="") String sid, 
			@RequestParam("skuCode")String skuCode,
			@RequestParam("quantity") Integer quantity,
			@RequestParam("shoppingCartSid") String shoppingCartSid) {		
		CartModifyAfterDto cartDto = new CartModifyAfterDto();
		try {
			String resultCode = CommonCode.SUCCESS_CODE;
			
			if (StringUtils.isBlank(visitKey) && StringUtils.isBlank(sid)) {
				resultCode = ShoppingCartCaptChaEnum.SID_AND_VISITKEY_ISNULL.getCode();
				return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
			}
			if (CheckIsEmpty.isEmpty(shoppingCartSid)) {
				resultCode = ShoppingCartCaptChaEnum.SHOPPING_CART_SID_ISNULL.getCode();
				return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
			}
			if (StringUtils.isBlank(skuCode)) {
				resultCode = ShoppingCartCaptChaEnum.SKU_CODE_ISNULL.getCode();
				return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
			}

			if (ObjectUtils.equals(null, quantity)) {
				resultCode = ShoppingCartCaptChaEnum.QUANTITY_ISNULL.getCode();
				return Jsonp.newInstance(resultCode, ShoppingCartCaptChaEnum.getTitle(resultCode));
			}
			int	quantityNumber = quantity < 1 ? 1 : quantity;
			UserDto user = null;
			if (StringUtils.isNotBlank(sid)) {
				user = (UserDto) SessionCache.get(sid);
				if (CheckObjectIsNull.isNull(user)) {
					return Jsonp.error("请先登录或重新登录");
				}
			}
			ShoppingCartBo queryCondition = new ShoppingCartBo();
			queryCondition.setQuantity(quantityNumber);
			queryCondition.setSkuCode(skuCode);	
			queryCondition.setShoppingCartSid(shoppingCartSid);

			if (ObjectUtils.notEqual(null, user)) {
				queryCondition.setUser(user);
				cartDto = modifyFacade.hasLoginOperation(queryCondition);
			} else {
				queryCondition.setVisitKey(visitKey);
				cartDto = modifyFacade.noLoginOpertation(queryCondition);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("修改购物车时"+e.getMessage());
			return Jsonp.error();
		}
		return Jsonp_data.success(cartDto);
	}

/**
 * 商品选中接口 修改购物车行选中状态
 * @param visitKey
 * @param sid
 * @param skuIds
 * @param isSelected
 * @param shoppingCartSid
 * @return
 */
	@ResponseBody
	@RequestMapping("modifyItemSelected")
	public Object updateSelect(
			@RequestParam(value="visitKey",defaultValue="")String visitKey,
			@RequestParam(value="sid",defaultValue="") String sid, 
			@RequestParam("skuCodes") String skuCodes, @RequestParam("isSelected") String isSelected,
			@RequestParam("shoppingCartSid") String shoppingCartSid) {
		CartModifyAfterDto cartModifyAfterDto = new CartModifyAfterDto();
		try {
			if (StringUtils.isBlank(sid) && StringUtils.isBlank(visitKey)) {
				return Jsonp.paramError("visitKey和sid必须写一个!");
			}
			if (StringUtils.isBlank(skuCodes)) {
				return Jsonp.paramError("购物车,商品skuCodes不能为空!");
			}
			if (CheckIsEmpty.isEmpty(shoppingCartSid)) {
				return Jsonp.paramError("shoppingCartSid不能为空");
			}
			if (StringUtils.isBlank(isSelected)) {
				return Jsonp.paramError("isSelected参数不能为空");
			}
			if (!StringUtils.equals(CommonConstant.YES, isSelected) && !StringUtils.equals(CommonConstant.NO,isSelected) ) {
				return Jsonp.paramError("isSelected的值只能是y/n两个值");
			}

			String skuCodesArr[] = skuCodes.split(",");
			String[] skuCodesArrStr = new String[skuCodesArr.length];
			try {
				for (int i = 0; i < skuCodesArr.length; i++) {
					skuCodesArrStr[i] = skuCodesArr[i];
				}
			} catch (NumberFormatException ne) {
				return Jsonp.paramError("skuCodes参数传递有误");
			}

			UserDto user = null;
			if (StringUtils.isNotBlank(sid)) {
				user = (UserDto) SessionCache.get(sid);
				if (CheckObjectIsNull.isNull(user)) {
					return Jsonp.error("请先登录或重新登录");
				}
			}
			ShoppingCartBo queryCondition = new ShoppingCartBo();
			queryCondition.setSkuCodeArray(skuCodesArrStr);		
			queryCondition.setShoppingCartSid(shoppingCartSid);
			queryCondition.setIsSelected(isSelected);
			if (ObjectUtils.notEqual(null, user)) {
				queryCondition.setUser(user);
				cartModifyAfterDto = modifyFacade.hasLoginModifySelect(queryCondition);
			} else {
				queryCondition.setVisitKey(visitKey);
				cartModifyAfterDto = modifyFacade.noLoginModifySelect(queryCondition);
			}
		} catch (Exception e) {
			LOGGER.debug("修改购物车选中状态时异常" + e);
			e.printStackTrace();
			return Jsonp.error();
		}
		BeanNullConverUtil.nullConver(cartModifyAfterDto);
		return Jsonp_data.success(cartModifyAfterDto);
	}
}
