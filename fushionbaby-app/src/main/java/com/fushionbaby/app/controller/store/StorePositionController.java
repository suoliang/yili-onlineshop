/**
 * app.aladingshop.com 上海一里网络科技有限公司
 */
package com.fushionbaby.app.controller.store;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.store.model.Store;
import com.aladingshop.store.querycondition.StoreQueryCondition;
import com.fushionbaby.app.dto.store.StoreCityDto;
import com.fushionbaby.app.dto.store.StoreCountryDto;
import com.fushionbaby.app.dto.store.StoreDto;
import com.fushionbaby.app.dto.store.StoreProvinceDto;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.constants.CommonConstant.CommonMessage;
import com.fushionbaby.common.constants.store.gaode.GaodeReqDto;
import com.fushionbaby.common.security.MD5Util;
import com.fushionbaby.common.util.CheckIsEmpty;
import com.fushionbaby.common.util.jsonp.Jsonp;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.facade.biz.common.store.StorePositionFacade;
import com.fushionbaby.sysmgr.model.SysmgrStoreApply;
import com.fushionbaby.sysmgr.service.SysmgrStoreApplyService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 
 * @description 类描述...
 * @author 孟少博
 * @date 2016年1月12日下午3:43:52
 */
@Controller
@RequestMapping("store/position")
public class StorePositionController extends BaseStoreController {

	private static final Log LOGGER = LogFactory.getLog(StorePositionController.class);
	
	@Autowired
	private SysmgrStoreApplyService<SysmgrStoreApply> sysmgrStoreApplyService;
	
	@Autowired
	private StorePositionFacade storePositionFacade;
	
	/**
	 * 下载地址
	 * @param data (timeStamp)
	 * 
	 * @param mac
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getProvinceList")
	public Object getProvince(	
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac){
		
		if(StringUtils.isNotBlank(data) && StringUtils.isNotBlank(mac)){
			LOGGER.info("邀请码注册接口--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
		}
		
		StoreQueryCondition queryCondition = new StoreQueryCondition();
		queryCondition.setIsDeleted(CommonConstant.NO);
		
		Set<String> provinceSet = new HashSet<String>();
		List<StoreProvinceDto> provinceList = new ArrayList<StoreProvinceDto>();
		List<Store> storeAllList = storeService.queryByCondition(queryCondition);
		for (Store store : storeAllList) {
			if(provinceSet.contains(store.getProvinceCode())){
				continue;
			}
			StoreProvinceDto provinceDto = new StoreProvinceDto();
			provinceDto.setCode(store.getProvinceCode());
			provinceDto.setName(StringUtils.trimToEmpty(store.getProvinceName()));
//			List<StoreCityDto> cityDtoList = new ArrayList<StoreCityDto>();
//			List<StoreCityDto> cityAreaList = this.queryByProvinceCode(store.getProvinceCode());
//			for (StoreCityDto cityArea : cityAreaList) {
//				StoreCityDto cityDto = new StoreCityDto();
//				cityDto.setCode(cityArea.getCode());
//				cityDto.setName(StringUtils.trimToEmpty(cityArea.getName()));
//				cityDtoList.add(cityDto);
//			}
//			provinceDto.setCityList(cityDtoList);
			provinceList.add(provinceDto);
			provinceSet.add(store.getProvinceCode());
		}
		
		return   Jsonp_data.success(provinceList) ;
	}
	/**
	 *  获取城市列表
	 * @param provinceCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getCityList")
	public Object getCity(String provinceCode){
		
		if(StringUtils.isBlank(provinceCode)){
			return Jsonp_data.success(new Object()) ;
		}
		
		List<StoreCityDto> cityList = this.queryByProvinceCode(provinceCode);
		
		return  Jsonp_data.success(cityList) ;
	}
	
	/**
	 * 获取小区
	 * @param countryCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getCountryList")
	public Object getCountryList(String cityCode){
		
		if(StringUtils.isBlank(cityCode)){
			return Jsonp_data.success(new Object()) ;
		}
		
		 List<StoreCountryDto> countryList = this.queryByCityCode(cityCode);
		 
		 return  Jsonp_data.success(countryList) ;
		 
	}
	
	/**
	 * 通过省编号查询城市信息
	 * @param provinceCode 省份编号
	 * @return
	 */
	private List<StoreCityDto> queryByProvinceCode(String provinceCode){
		
		Set<String> cityCodeList = new HashSet<String>();
		StoreQueryCondition queryCondition = new StoreQueryCondition();
		queryCondition.setIsDeleted(CommonConstant.NO);
		queryCondition.setProvinceCode(provinceCode);
		List<Store> cityStoreList = storeService.queryByCondition(queryCondition);
		
		List<StoreCityDto> cityDtoList = new ArrayList<StoreCityDto>();
		for (Store store : cityStoreList) {
			
			if(cityCodeList.contains(store.getCityCode())){
				continue;
			}
			StoreCityDto cityDto = new StoreCityDto();
			cityDto.setCode(store.getCityCode());
			cityDto.setName(StringUtils.trimToEmpty(store.getCityName()));
			cityDtoList.add(cityDto);
			
			cityCodeList.add(store.getCityCode());
		}
		
		return cityDtoList;
	}
	
	/**
	 * 根据城市编号查询区县
	 * @param cityCode
	 * @return
	 */
	private List<StoreCountryDto> queryByCityCode(String cityCode){
		
		StoreQueryCondition queryCondition = new StoreQueryCondition();
		queryCondition.setIsDeleted(CommonConstant.NO);
		queryCondition.setCityCode(cityCode);
		List<Store> countryStoreList = storeService.queryByCondition(queryCondition);
		
		Set<String> countryCodeList = new HashSet<String>();
		List<StoreCountryDto> countryList = new ArrayList<StoreCountryDto>();
		for (Store store : countryStoreList) {
			if(countryCodeList.contains(store.getCountryCode())){
				continue;
			}
			StoreCountryDto countryDto = new StoreCountryDto();
			countryDto.setCode(store.getCountryCode());
			countryDto.setName(StringUtils.trimToEmpty(store.getCountryName()));
			countryList.add(countryDto);
			countryCodeList.add(store.getCountryCode());
		}
		
		return countryList;
	}
	
	
	/**
	 * 获取 门店列表
	 * @param countryCode 区县编号
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getStoreCellList")
	public Object getStoreCellList(
			@RequestParam(value="countryCode",defaultValue="")String countryCode,
			@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac ){
		
		if(StringUtils.isNotBlank(data) && StringUtils.isNotBlank(mac)){
			LOGGER.info("邀请码注册接口--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			JsonElement json = new JsonParser().parse(data);
			countryCode = json.getAsJsonObject().get("countryCode").getAsString();
		}
		
		if(StringUtils.isBlank(countryCode)){
			return Jsonp_data.success(new ArrayList<StoreDto>());
		}
		
		List<StoreDto> storeList = this.getStoreDtoList(countryCode);
		if(storeList==null){
			storeList = new ArrayList<StoreDto>();
		}
	
		return Jsonp_data.success(storeList);
	}
	
	
	/**
	 * 获取门店
	 * @param countryCode 区县编号
	 * @return
	 */
	
	private List<StoreDto> getStoreDtoList(String countryCode){
		
		StoreQueryCondition queryCondition = new StoreQueryCondition();
		queryCondition.setIsDeleted(CommonConstant.NO);
		queryCondition.setCountryCode(countryCode);
		List<Store> countryStoreList = storeService.queryByCondition(queryCondition);
		List<StoreDto> storeDtoList = new ArrayList<StoreDto>();
		for (Store store : countryStoreList) {
			StoreDto storeDto = this.getStoreDto(store);
			
			storeDtoList.add(storeDto);
		}
		
		return storeDtoList;
		
	}
	
	/**
	 * 搜索小区
	 * @param searchKey
	 * @return
	 */
	@ResponseBody
	@RequestMapping("searchCell")
	public Object searchCell(String searchKey){
		
		StoreQueryCondition queryCondition = new StoreQueryCondition();
		queryCondition.setCellName(searchKey);
		queryCondition.setIsDeleted(CommonConstant.NO);
		List<Store> storeList = storeService.queryByCondition(queryCondition);
		List<StoreDto> storeDtoList = new ArrayList<StoreDto>();
		for (Store store : storeList) {
			storeDtoList.add(this.getStoreDto(store));
		}
		
		return Jsonp_data.success(storeDtoList);
				
	}
	
	
	
	
	/**
	 * 
	 * @param sid
	 * @param name 名称
	 * @param phone 电话
	 * @param address 地址
	 * @param sourceCode 来源
	 * @return
	 */
	@ResponseBody
	@RequestMapping("applyForOpen")
	public Object applyForOpen(String name,String phone,String city,String address,String sourceCode){
		
		try {
			SysmgrStoreApply storeApply = new SysmgrStoreApply();
			storeApply.setAddress(address);
			storeApply.setCity(city);
			storeApply.setName(name);
			storeApply.setPhone(phone);
			storeApply.setSourceCode(sourceCode);
			sysmgrStoreApplyService.add(storeApply);
		} catch (Exception e) {
			e.printStackTrace();
			return Jsonp.error();
		}
		
		return Jsonp.success();
	}
	
	
	
	
	/***
	 * 定位  经纬(XY)度  以及 周围半径 R
	 * @param data
	 * @param mac
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping("load")
	public Object storePositionLoad(@RequestParam(value="data",defaultValue="")String data,
			@RequestParam(value="mac",defaultValue="")String mac ){
		
		    LOGGER.info("定位门店接口--请求报文：{" + data + "}");
			if (!MD5Util.validMd5(data, MD5Util.getKey(), mac)) {
				return Jsonp.error_md5();
			}
			Gson gson = new Gson();
			GaodeReqDto req = gson.fromJson(data, GaodeReqDto.class);
			if (CheckIsEmpty.isEmpty(req.getX(),req.getY())) {
				return Jsonp.paramError(CommonMessage.PARAM_ERROR_MESSAGE);
			}
	     	return storePositionFacade.getResData(req);
	}
}
