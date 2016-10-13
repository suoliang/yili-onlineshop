package com.fushionbaby.facade.biz.common.express.impl;

import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.dto.express.ExpressReqDto;
import com.fushionbaby.common.dto.express.ExpressResDto;
import com.fushionbaby.common.util.HttpRequest;
import com.fushionbaby.facade.biz.common.express.ExpressFacade;
import com.google.gson.Gson;

/***
 * 无文档，直接用的快递100后台java和前端交互的请求
 * @description 类描述...
 * @author 徐培峻
 * @date 2015年11月2日下午2:49:21
 */
@Service
public class ExpressFacadeImpl implements ExpressFacade {

	
	private static final Log LOGGER=LogFactory.getLog(ExpressFacadeImpl.class);
	
	public ExpressResDto getExpressInfo(ExpressReqDto reqDto) {
		String json = HttpRequest.sendGet("http://www.kuaidi100.com/query",
				"type=" + reqDto.getCom() + "&postid=" + reqDto.getNu()
						+ "&temp=" + new Random().nextInt(6));
		LOGGER.info("待查询的快递公司为："+reqDto.getCom()+".快递单号为："+reqDto.getNu()+"快递查询的返回信息json:"+json);
		return new Gson().fromJson(json, ExpressResDto.class);
	}
	
	
	
	public static void main(String[] args) {
		ExpressReqDto req=new ExpressReqDto();
//		req.setCom("yuantong");
//		req.setNu("880350384879600241");
		req.setCom("shentong");
		req.setNu("229481074593");
		
		
//		req.setCom("zhongtong");
//		req.setNu("761678967270");
		ExpressResDto data=new ExpressFacadeImpl().getExpressInfo(req);
		System.out.println(data.getMessage());
		System.out.println(data.getCom());
		System.out.println(data.getNu());
		System.out.println(data.getState());
		System.out.println(data.getData());
		System.out.println(data.getName());
	}
}
