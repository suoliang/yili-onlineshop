package util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fushionbaby.cache.SessionCache;
import com.fushionbaby.common.constants.CartConstant;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CookieConstant;
import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.enums.ShoppingCartCaptChaEnum;
import com.fushionbaby.common.util.CookieUtil;

/**
 * @author 张明亮
 */
public class CartUtils {
	/**记录日志*/
	private static final Log logger = LogFactory.getLog(CartUtils.class);
	
	public static UserDto getUser(String sid) {
		UserDto user = null;
		if (StringUtils.isNotBlank(sid)){
			user = (UserDto) SessionCache.get(sid);
		}
		return user;
	}
	
	public static String checkSidAndVisitKey(HttpServletRequest request) {
		String sid = null;
		String visitKey = null;
		try {
			sid = getClientSid(request);
			visitKey = getClientVisitKey(request);
		} catch (Exception e){
			logger.error("购物车获取cookie有误" + e);
			e.printStackTrace();
			return ShoppingCartCaptChaEnum.SHOPPING_CART_COOKIE_ERROR.getCode();
		}
		if (StringUtils.isBlank(sid) && StringUtils.isBlank(visitKey)) {
			return ShoppingCartCaptChaEnum.SID_AND_VISITKEY_ISNULL.getCode();
		}
		return ShoppingCartCaptChaEnum.SUCCESS.getCode();
	}

	public static String getClientVisitKey(HttpServletRequest request) {
		String visitKey = CookieUtil.getCookieValue(request, CartConstant.COOKIE_VISIT_KEY);
		return visitKey;
	}

	public static String getClientSid(HttpServletRequest request) {
		String sid = CookieUtil.getCookieValue(request, CookieConstant.COOKIE_SID);
		return sid;
	}
	
	public static String checkQuantity(String quantity) {
		if (StringUtils.isBlank(quantity)) {
			//return Jsonp.paramError("购买数量quantity参数不能为空!");
			return ShoppingCartCaptChaEnum.QUANTITY_ISNULL.getCode();
		}
		try {
			Integer.valueOf(quantity);
		} catch (NumberFormatException ne) {
			logger.error("购买数量quantity参数内容有误,必须是整数!" + ne);
			//return Jsonp.paramError("购买数量quantity参数内容有误,必须是整数!");
			return ShoppingCartCaptChaEnum.QUANTITY_FORMAT_EXCEPTION.getCode();
		}
		return ShoppingCartCaptChaEnum.SUCCESS.getCode();
	}
	public static int getQuantity(String quantity) {
		int quantityNumber = Integer.valueOf(quantity);
		quantityNumber = quantityNumber < 1 ? 1 : quantityNumber;
		return quantityNumber;
	}
	
	public static String checkIsSelected(String isSelected) {
		if (StringUtils.isBlank(isSelected)) {
			//return Jsonp.paramError("isSelected参数不能为空");
			return ShoppingCartCaptChaEnum.SELECT_PARAMS_ISNULL.getCode();
		}
		if (!(StringUtils.equals(CommonConstant.YES, isSelected) || 
				StringUtils.equals(CommonConstant.NO, isSelected))) {
			//return Jsonp.paramError("isSelected的值只能是y/n两个值");
			return ShoppingCartCaptChaEnum.SELECT_PARAMS_ERROR.getCode();
		}
		return ShoppingCartCaptChaEnum.SUCCESS.getCode();
	}
	
	public static List<String> getSkuCodes(String skuCodes) {
		String skuCodesArr[] = skuCodes.split(",");
		List<String> skuCodeList = new ArrayList<String>();
		for (int i = 0; i < skuCodesArr.length; i++) {
			skuCodeList.add(skuCodesArr[i]);
		}
		return skuCodeList;
	}
}
