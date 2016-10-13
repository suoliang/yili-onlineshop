package com.fushionbaby.facade.biz.common.store;

import com.fushionbaby.common.constants.store.gaode.GaodeReqDto;


/***
 * 门店位置（定位）服务接口
 * @description 类描述...
 * @author 徐培峻
 * @date 2016年2月29日下午5:07:34
 */
public interface StorePositionFacade {

	/***
	 * 获得返回数据（通过经纬度搜索的结果）
	 * @param req
	 * @return
	 */
	Object getResData(GaodeReqDto req);


}
