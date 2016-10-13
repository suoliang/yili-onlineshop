package com.fushionbaby.facade.biz.common.express;

import com.fushionbaby.common.dto.express.ExpressReqDto;
import com.fushionbaby.common.dto.express.ExpressResDto;

/**
 * 快递100的对接API(目前使用的是免费查询接口，接口文档网址：http://www.kuaidi100.com/openapi/api_post.
 * shtml)
 * 接口调用地址：http://api.kuaidi100.com/api?id=[]&com=[]&nu=[]&valicode=[]&show
 * =[0|1|2|3]&muti=[0|1]&order=[desc|asc]
 * 
 * @author suntao
 *
 */
public interface ExpressFacade {
	/** 获取物流信息 */
	public ExpressResDto getExpressInfo(ExpressReqDto reqDto);
}
