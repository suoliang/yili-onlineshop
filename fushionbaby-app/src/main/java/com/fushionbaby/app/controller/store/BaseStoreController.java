/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月11日下午1:49:52
 */
package com.fushionbaby.app.controller.store;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.aladingshop.store.model.Store;
import com.aladingshop.store.model.StoreCell;
import com.aladingshop.store.model.StoreConfig;
import com.aladingshop.store.service.StoreCellService;
import com.aladingshop.store.service.StoreConfigService;
import com.aladingshop.store.service.StoreService;
import com.fushionbaby.app.controller.home.HomeController;
import com.fushionbaby.app.dto.store.StoreConfigDto;
import com.fushionbaby.app.dto.store.StoreDto;
import com.fushionbaby.app.dto.store.StoreSkuDto;
import com.fushionbaby.common.constants.ImageStandardConstant;
import com.fushionbaby.common.constants.store.StoreStatusConstant;
import com.fushionbaby.common.util.BeanNullConverUtil;
import com.fushionbaby.common.util.NumberFormatUtil;
import com.fushionbaby.facade.image.ImageProcess;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAreaConfigService;
import com.fushionbaby.sku.model.Sku;
import com.fushionbaby.sku.model.SkuInventory;
import com.fushionbaby.sku.model.SkuPrice;
import com.fushionbaby.sku.service.SkuInventoryService;
import com.fushionbaby.sku.service.SkuPriceService;
import com.fushionbaby.sku.service.SkuService;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年12月11日下午1:49:52
 */
@Controller
public abstract class BaseStoreController extends HomeController {

	
	@Autowired
	protected StoreService<Store> storeService;
	
	@Autowired
	protected MemberAreaConfigService<MemberAreaConfig> memberAreaConfigService;
	
	@Autowired
	protected StoreCellService<StoreCell> storeCellService;
	
	@Autowired
	protected SkuService skuService;
	
	@Autowired
	protected ImageProcess imageProcess;
	
	@Autowired
	private SkuInventoryService<SkuInventory> skuInventoryService;
	
	@Autowired
	protected SkuPriceService<SkuPrice> skuPriceService;
	
	
	@Autowired
	protected StoreConfigService<StoreConfig> storeConfigService;
	

	
	
	/**
	 * 获取门店配置信息
	 * @param storeCode
	 * @return
	 */
	protected StoreConfigDto getStoreConfigDto(String storeCode){
		
		StoreConfig storeConfig = storeConfigService.findByStoreCode(storeCode);
		String shopHours = "00:00 - 23:59";
		StoreConfigDto storeConfigDto = new StoreConfigDto();
		if(storeConfig!=null && StringUtils.isNotBlank(storeConfig.getBusinessStartTime()) && StringUtils.isNotBlank(storeConfig.getBusinessEndTime())){
			String startTime  = storeConfig.getBusinessStartTime();
			String endTime = storeConfig.getBusinessEndTime();
			shopHours = startTime.substring(0,startTime.lastIndexOf(":")) +" - "+ endTime.substring(0,endTime.lastIndexOf(":"));
		}
		
		storeConfigDto.setShopHours(shopHours);
		if(storeConfig!=null ){
			storeConfigDto.setFreightAmount(storeConfig.getFreightAmount()==null ? 0:storeConfig.getFreightAmount());
			storeConfigDto.setFreeFreightAmount(storeConfig.getFreeFreightAmount()==null? 0 :storeConfig.getFreeFreightAmount());
		}
		
		BeanNullConverUtil.nullConver(storeConfigDto);
		
		return storeConfigDto;
		
	}
	
	
	/**
     * 获取门店商品DTO
     * @param sku
     * @return
     */
	protected StoreSkuDto getStoreSkuDto(Sku sku){
    	StoreSkuDto storeSkuDto = new StoreSkuDto();
		storeSkuDto.setSkuNo(sku.getSkuNo());
		storeSkuDto.setSkuCode(sku.getUniqueCode());
		storeSkuDto.setName(sku.getName());
		SkuInventory skuInventory = skuInventoryService.findBySkuCode(sku.getUniqueCode());
		Integer inventoryNum = (skuInventory!=null && skuInventory.getAvailableQty()!=null) ? skuInventory.getAvailableQty() : 0;
		storeSkuDto.setInventory(inventoryNum);
//		storeSkuDto.setImgUrl(imageProcess.getThumImagePath(sku.getUniqueCode(),ImageStandardConstant.IMAGE_STANDARD_APP_640 ));
		storeSkuDto.setImgUrls(imageProcess.getThumImageList(sku.getUniqueCode(),ImageStandardConstant.IMAGE_STANDARD_APP_640 ));
		
		SkuPrice skuPrice = skuPriceService.findBySkuCode(sku.getUniqueCode());
		BigDecimal currentPrice = (skuPrice !=null && skuPrice.getCurrentPrice()!=null) ? skuPrice.getCurrentPrice() : BigDecimal.ZERO;
		storeSkuDto.setCurrentPrice (NumberFormatUtil.numberFormat( currentPrice));
		
		return storeSkuDto;
    }
	
	/**
	 * 获得门店DTO
	 * @param store
	 * @return
	 */
	protected StoreDto getStoreDto(Store store){
		
		StoreDto storeDto = new StoreDto();
		storeDto.setProvinceCode(StringUtils.trimToEmpty(store.getProvinceCode()));
		storeDto.setProvinceName(StringUtils.trimToEmpty(store.getProvinceName()));
		storeDto.setCityCode(StringUtils.trimToEmpty(store.getCityCode()));
		storeDto.setCityName(StringUtils.trimToEmpty(store.getCityName()));
		storeDto.setCountryCode(StringUtils.trimToEmpty(store.getCountryCode()));
		storeDto.setCountryName(StringUtils.trimToEmpty(store.getCountryName()));
		storeDto.setCellCode(StringUtils.trimToEmpty(store.getCellCode()));
		storeDto.setCellName(StringUtils.trimToEmpty(store.getCellName()));
		storeDto.setStoreCode(StringUtils.trimToEmpty(store.getCode()));
		storeDto.setStoreName(StringUtils.trimToEmpty(store.getName()));
		
		String storeStatus = null;
		if(store.getStatus()==null || store.getStatus() !=StoreStatusConstant.OPEN){
			storeStatus = StoreStatusConstant.StoreStatusDto.NOT_OPERATED;
		}else{
			storeStatus = StoreStatusConstant.StoreStatusDto.OPEN;
		}
		storeDto.setStoreStatus(storeStatus);
		
		return storeDto;
		
	}
	
	
	/**
	 * 是否在运营时间内
	 * @param store
	 * @return
	 */
	protected String isTimeInterval(Store store){
		
		String storeStatus = null;
		
		StoreConfig storeConfig	= storeConfigService.findByStoreCode(store.getCode());
		if(storeConfig==null){
			return storeStatus;
		}
			
		Long currentTime = Calendar.getInstance().getTimeInMillis();  
			
		if( storeConfig.getBusinessStartTime()!=null && storeConfig.getBusinessEndTime() !=null){
			Long startTime = this.getItemTime(storeConfig.getBusinessStartTime().toString());
			Long endTime   = this.getItemTime(storeConfig.getBusinessEndTime().toString());
			if(startTime!=null && endTime !=null && (currentTime< startTime|| currentTime > endTime)){
				storeStatus =  StoreStatusConstant.StoreStatusDto.NOT_TIME_INTERVAL;
			}
		}
			
		return storeStatus;
		
	}
	
	
	
	private Long getItemTime(String timeStr){
		
		String itemTimeStr  = DateFormatUtils.format(new Date(), "yyyy-MM-dd") +" "+timeStr.split(" ")[1];
		try {
			Long itemTime = DateUtils.parseDate(itemTimeStr, "yyyy-MM-dd HH:mm:ss.S").getTime();
			return itemTime;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
