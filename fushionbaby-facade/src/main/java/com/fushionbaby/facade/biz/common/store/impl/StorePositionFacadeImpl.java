package com.fushionbaby.facade.biz.common.store.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.DistanceCompareUtil;

import com.aladingshop.store.model.Store;
import com.aladingshop.store.service.StoreService;
import com.fushionbaby.common.constants.store.StoreStatusConstant;
import com.fushionbaby.common.constants.store.gaode.GaodeMapApiConstant;
import com.fushionbaby.common.constants.store.gaode.GaodeReqDto;
import com.fushionbaby.common.constants.store.gaode.GaodeResDataMap;
import com.fushionbaby.common.constants.store.gaode.GaodeResDataModel;
import com.fushionbaby.common.constants.store.gaode.GaodeResDto;
import com.fushionbaby.common.util.HttpRequest;
import com.fushionbaby.common.util.date.DateFormat;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.store.StorePositionFacade;
import com.google.gson.Gson;

/***
 *  门店位置（定位）实现
 * @description 类描述...
 * @author 徐培峻
 * @date 2016年2月29日下午5:08:39
 */
@Service
public class StorePositionFacadeImpl  implements StorePositionFacade{

	@Autowired
	private StoreService<Store>  storeService;
	
	private static final Log Logger=LogFactory.getLog(StorePositionFacadeImpl.class);
	
	/***
	 * 获得返回数据（通过经纬度搜索的结果）
	 * @param req
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public Object getResData(GaodeReqDto req) {
	
   try {
		String url=GaodeMapApiConstant.url;
		String key=GaodeMapApiConstant.key;
		req.setR(GaodeMapApiConstant.R);
		String center=req.getX()+","+req.getY();
		String param="tableid="+GaodeMapApiConstant.table_id+"&keywords=&center="+center+"&radius="+req.getR()+"&filter=&limit=&page=1&key="+key;
		Logger.info(DateFormat.dateToString(new Date())+"定位信息传递参数为：" + param);
		String json = HttpRequest.sendPost(url, param);
		Logger.info(DateFormat.dateToString(new Date())+"定位信息查询结果为：" + json);
		
		Gson gson = new Gson();
		GaodeResDataMap data = gson.fromJson(json, GaodeResDataMap.class);
		
		if(GaodeMapApiConstant.LOAD_FAIL.equals(data.getStatus())){
			return Jsonp.error("真糟糕，定位失败");
		}
		List<GaodeResDto> resList=new ArrayList<GaodeResDto>();
		List<GaodeResDataModel>	 modelList=data.getDatas();
		if(modelList==null||modelList.size()<1){
			return Jsonp_data.success(resList);//Jsonp.error("真糟糕，没有找到附近门店");
		}
		for (GaodeResDataModel model : modelList) {
			GaodeResDto res=new GaodeResDto();
			res.setAddress(model.get_address());
			res.setDistance(model.get_distance());
			res.setLocation(model.get_location());
			//res.setStoreCode(model.getCode());
			/**由于 地图数据上面只能存数字类型数据   所以这里的code 实际是 门店的 number*/
			//Store store=this.storeService.findByCode(model.getCode());
			Store store=this.storeService.findByNumber(model.getCode());
			Logger.info(DateFormat.dateToString(new Date())+"定位信息通过code"+model.getCode()+"查询门店结果为：" +store);
			if(store!=null){
				res.setProvinceCode(StringUtils.trimToEmpty(store.getProvinceCode()));
				res.setProvinceName(StringUtils.trimToEmpty(store.getProvinceName()));
				res.setCityCode(StringUtils.trimToEmpty(store.getCityCode()));
				res.setCityName(StringUtils.trimToEmpty(store.getCityName()));
				res.setCountryCode(StringUtils.trimToEmpty(store.getCountryCode()));
				res.setCountryName(StringUtils.trimToEmpty(store.getCountryName()));
				res.setCellCode(StringUtils.trimToEmpty(store.getCellCode()));
				res.setCellName(StringUtils.trimToEmpty(store.getCellName()));
				res.setStoreCode(StringUtils.trimToEmpty(store.getCode()));
				res.setStoreName(StringUtils.trimToEmpty(store.getName()));
				
				String storeStatus = null;
				if(store.getStatus()==null || store.getStatus() !=StoreStatusConstant.OPEN){
					storeStatus = StoreStatusConstant.StoreStatusDto.NOT_OPERATED;
				}else{
					storeStatus = StoreStatusConstant.StoreStatusDto.OPEN;
				}
				res.setStoreStatus(storeStatus);
			}
			
			resList.add(res);
		}
		Collections.sort(resList, new DistanceCompareUtil());
		  return Jsonp_data.success(resList);
		} catch (Exception e) {
			Logger.error("StorePositionFacadeImpl.java 定位异常", e);
			return Jsonp.error("定位出错");
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		GaodeReqDto req=new GaodeReqDto();
		req.setR("5000");
		req.setX("116.225866");
		req.setY("39.905820");
		String url=GaodeMapApiConstant.url;
		String key=GaodeMapApiConstant.key;
		req.setR(GaodeMapApiConstant.R);
		String center=req.getX()+","+req.getY();
		String param="tableid="+GaodeMapApiConstant.table_id+"&keywords=&center="+center+"&radius="+req.getR()+"&filter=&limit=&page=1&key="+key;
		Logger.info(DateFormat.dateToString(new Date())+"定位信息传递参数为：" + param);
		String json = HttpRequest.sendPost(url, param);
		Logger.info(DateFormat.dateToString(new Date())+"定位信息查询结果为：" + json);
		
		Gson gson = new Gson();
		GaodeResDataMap data = gson.fromJson(json, GaodeResDataMap.class);
		
		List<GaodeResDataModel>	 modelList=data.getDatas();
		List<GaodeResDto> resList=new ArrayList<GaodeResDto>();
		for (GaodeResDataModel model : modelList) {
			GaodeResDto res=new GaodeResDto();
			res.setAddress(model.get_address());
			res.setDistance(model.get_distance());
			res.setLocation(model.get_location());
			res.setStoreCode(model.getCode());
			resList.add(res);
		}
		Collections.sort(resList, new DistanceCompareUtil());
		for (GaodeResDto gaodeResDto : resList) {
			System.out.println(gaodeResDto.getAddress()+"**********"+gaodeResDto.getDistance());
		}
	}
	
}
