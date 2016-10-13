package com.fushionbaby.member.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.common.constants.AreaConstant;
import com.fushionbaby.common.dto.areaconfig.AreaDto;
import com.fushionbaby.common.dto.areaconfig.CityDto;
import com.fushionbaby.common.dto.areaconfig.ProvinceDto;
import com.fushionbaby.member.dao.MemberAreaConfigDao;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAreaConfigService;
/**
 * 
 * @author King
 *
 */
@Service
public class MemberAreaConfigServiceImpl implements MemberAreaConfigService<MemberAreaConfig> {
	
	private static Map<String,String> cache = new HashMap<String,String>();
	
	private static Map<String,List<AreaDto>> cacheList = new HashMap<String,List<AreaDto>>();

	@Autowired
	private MemberAreaConfigDao sysAreaDao;

	public List<AreaDto> findByParentAreaCode(String code) throws DataAccessException {
		List<AreaDto> list = cacheList.get(code);
		if(list!=null && list.size() > 0) {
			return list;
		}
		List<MemberAreaConfig>  areaConfigList = sysAreaDao.findByParentAreaCode(code);
		
		if(list ==null){
			list = new ArrayList<AreaDto>();
		}
		
		for (MemberAreaConfig memberAreaConfig : areaConfigList) {
			AreaDto areaDto = new AreaDto();
			areaDto.setCode(memberAreaConfig.getAreaCode());
			areaDto.setName(memberAreaConfig.getCity());
			list.add(areaDto);
		}
		
		cacheList.put(code, list);
		return list;
	}

	public MemberAreaConfig getAreaCode(String areaCode) throws DataAccessException {
		
		return sysAreaDao.findByAreaCode(areaCode);
	}
	
	public String getNameByAreaCode(String areaCode) throws DataAccessException {
		String str = null;
		str = cache.get(areaCode);
		if(str!=null) {
			return str;
		}
		str = sysAreaDao.getNameByAreaCode(areaCode);
		cache.put(areaCode,str);
		return str;
	}

	public List<ProvinceDto> findAll() {
		
		
		List<AreaDto> provinceParentCode = this.findByParentAreaCode(AreaConstant.PROVINCE_CODE);
		List<ProvinceDto> provinceDtoList = this.getProvinceDtoList(provinceParentCode);
		
		for (ProvinceDto province : provinceDtoList) {
			List<CityDto> cityDtoList = this.getCityDtoList(this.findByParentAreaCode(province.getCode()));
			province.setCityList(cityDtoList);
			for (CityDto city : cityDtoList) {
				List<AreaDto> contactorList = this.findByParentAreaCode(city.getCode());
				city.setAreaList(contactorList);
			}
		}
		
		return provinceDtoList;
	}
	
	private List<CityDto> getCityDtoList(List<AreaDto> cityList){
		List<CityDto> cityDtoList = new ArrayList<CityDto>();
		
		for (AreaDto areaDto : cityList) {
			CityDto city = new CityDto();
			city.setCode(areaDto.getCode());
			city.setName(areaDto.getName());
			cityDtoList.add(city);
		}
		return cityDtoList;
		
	}
	
	private List<ProvinceDto> getProvinceDtoList(List<AreaDto> provinceList){
		List<ProvinceDto> provinceDtoList= new ArrayList<ProvinceDto>();
		
		for (AreaDto areaDto : provinceList) {
			ProvinceDto city = new ProvinceDto();
			city.setCode(areaDto.getCode());
			city.setName(areaDto.getName());
			provinceDtoList.add(city);
		}
		return provinceDtoList;
	}


	
}
