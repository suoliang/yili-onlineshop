package com.aladingshop.web.controller.sku;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import util.CartUtils;

import com.fushionbaby.common.dto.UserDto;
import com.fushionbaby.common.dto.sku.SkuDto;
import com.fushionbaby.common.util.jsonp.Jsonp_data;

/**
 * 
 * @description  猜你喜欢，我的关注，浏览记录之类的页面附带商品显示
 * @author 孟少博
 * @date 2015年8月21日下午4:50:38
 */
@Controller
@RequestMapping("/link")
public class SkuLinkController {
	
	@ResponseBody
	@RequestMapping(value="/likeSku",method=RequestMethod.POST)
	public Object likeSku(HttpServletRequest request){
		
		UserDto user = CartUtils.getUser(CartUtils.getClientSid(request));
		
		//String visitKey = CartUtils.getClientVisitKey(request);
		
		List<SkuDto> skuList = new ArrayList<SkuDto>();
		
		if(user == null){
			
		}else{
			
		}
		
		
		return Jsonp_data.success(skuList);
	}
	
	
}
