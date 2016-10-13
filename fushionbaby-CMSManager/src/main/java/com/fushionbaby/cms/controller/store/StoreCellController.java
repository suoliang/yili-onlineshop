package com.fushionbaby.cms.controller.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aladingshop.store.model.StoreCell;
import com.aladingshop.store.service.StoreCellService;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.dto.store.StoreCellDto;
import com.fushionbaby.common.constants.AreaConstant;
import com.fushionbaby.common.dto.areaconfig.AreaDto;
import com.fushionbaby.common.dto.areaconfig.ProvinceDto;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.jsonp.Jsonp_data;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAreaConfigService;

/**
 * @description 小区管理
 * @author 孟少博
 * @date 2015年12月19日下午2:12:27
 */
@Controller
@RequestMapping("store/cell")
public class StoreCellController {

	@Autowired
	private StoreCellService<StoreCell> storeCellService;
	
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> memberAreaService;
	
	/**
	 * 查询小区集合
	 * @param storeCell
	 * @param page
	 * @param model
	 * @return
	 */
	@RequestMapping("findCellList")
	public Object findCellList(StoreCellDto storeCell,
			BasePagination page,Model model){
		
		if(page==null){
			 page = new BasePagination();
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("countryCodes", this.findCountryCode(storeCell));
		params.put("name", storeCell.getCellName());
		params.put("code", storeCell.getCellCode());
		params.put("isDisable", storeCell.getIsDisable());
		page.setParams(params);
		page = storeCellService.getPageList(page);
		@SuppressWarnings("unchecked")
		List<StoreCell> cellList = (List<StoreCell>)page.getResult();
		List<StoreCellDto> storeCellDtoList = new ArrayList<StoreCellDto>();
		for (StoreCell storeCell2 : cellList) {
			storeCellDtoList.add(this.getStoreCellDto(storeCell2));
		}
		model.addAttribute("storeCellDtoList", storeCellDtoList);
		model.addAttribute("page", page);
		model.addAttribute("storeCell", storeCell);
		List<ProvinceDto> provinceList = memberAreaService.findAll();
		model.addAttribute("provinceList", provinceList);
		return "models/store/cell/storeCellList";
	}
	
	
	private StoreCellDto getStoreCellDto(StoreCell storeCell2){
		
		StoreCellDto storeCellDto = new StoreCellDto();
		storeCellDto.setId(storeCell2.getId());
		storeCellDto.setCellCode(storeCell2.getCode());
		storeCellDto.setCellName(storeCell2.getName());
		storeCellDto.setCountryCode(storeCell2.getCountryCode());
		storeCellDto.setShowOrder(storeCell2.getShowOrder() );
		storeCellDto.setIsDisable(storeCell2.getIsDisable());
		MemberAreaConfig countryArea = memberAreaService.getAreaCode(storeCell2.getCountryCode());
		String countryName = countryArea==null ? "" : countryArea.getCity();
		storeCellDto.setCountryName(StringUtils.isBlank(countryName) ? "" : countryName);
		String cityCode = countryArea==null ? "" :StringUtils.trimToEmpty( countryArea.getParentAreaCode());
		MemberAreaConfig cityArea = memberAreaService.getAreaCode(cityCode);
		storeCellDto.setCityCode(cityCode);
		storeCellDto.setCityName(cityArea==null ? "" :StringUtils.trimToEmpty(cityArea.getCity()));
		storeCellDto.setProvinceCode(cityArea==null ? "" :StringUtils.trimToEmpty(cityArea.getParentAreaCode()));
		storeCellDto.setProvinceName(cityArea == null ? "" : StringUtils.trimToEmpty(memberAreaService.getNameByAreaCode(cityArea.getParentAreaCode())));
		return storeCellDto;
	}
	
	
	/***
	 * 获取区县集合
	 * @param storeCell
	 * @return
	 */
	private String findCountryCode(StoreCellDto storeCell){
		
		List<String> countryCodeList = new ArrayList<String>();
		
		if(StringUtils.isNotBlank(storeCell.getCountryCode())){
			countryCodeList.add(storeCell.getCountryCode());
		}else if(StringUtils.isNotBlank(storeCell.getCityCode()) && StringUtils.isBlank(storeCell.getCountryCode())){
			List<AreaDto> areaList = memberAreaService.findByParentAreaCode(storeCell.getCityCode());
			for (AreaDto areaDto : areaList) {
				countryCodeList.add(areaDto.getCode());
			}
		}else if(StringUtils.isNotBlank(storeCell.getProvinceCode()) && StringUtils.isBlank(storeCell.getCountryCode()) && 
				StringUtils.isBlank(storeCell.getCityCode())){
			List<AreaDto> cityList = memberAreaService.findByParentAreaCode(storeCell.getProvinceCode());
			for (AreaDto cityDto : cityList) {
				List<AreaDto> countryList = memberAreaService.findByParentAreaCode(cityDto.getCode());
				for (AreaDto countryDto : countryList) {
					countryCodeList.add(countryDto.getCode());
				}
			}
		}
		
		return StringUtils.join(countryCodeList, ",");
		
	}
/**
 * 进入编辑页面	
 * @param id
 * @param model
 * @return
 */
	@RequestMapping("editPage")
	public Object editPage(@RequestParam(value="id",defaultValue="") Long id,Model model){
		StoreCellDto storeCellDto = new StoreCellDto(); 
		if(id!=null){
			StoreCell storeCell	 = storeCellService.findById(id);
			if(storeCell!=null){
				storeCellDto = this.getStoreCellDto(storeCell);
			}
		}
		model.addAttribute("storeCell", storeCellDto);
		
		return "models/store/cell/storeCellEdit";
	}
	
	/**
	 * 
	 * @param id
	 * @param countryCode 门店编号
	 * @param cellName 小区名称
	 * @param isDisable 是否禁用
	 * @param showOrder 显示顺序
	 * @return
	 */
	@RequestMapping("editStoreCell")
	public Object editStoreCell(@RequestParam(value="id",defaultValue="") Long id,
			String countryCode,String cellName,String isDisable,Integer showOrder,HttpServletRequest request){
		StoreCell storeCell	 = null;
		if(id!=null){
			storeCell	 = storeCellService.findById(id);
			storeCell.setCountryCode(countryCode);
			storeCell.setIsDisable(isDisable);
			storeCell.setShowOrder(showOrder);
			storeCell.setName(cellName);
			storeCellService.update(storeCell);
		}else{
			storeCell = new StoreCell();
			storeCell.setCountryCode(countryCode);
			storeCell.setIsDisable(isDisable);
			storeCell.setName(cellName);
			storeCell.setShowOrder(showOrder);
			storeCellService.add(storeCell);
			storeCell.setCode(countryCode+storeCell.getId());
			storeCellService.update(storeCell);
			saveMoreCell(countryCode, isDisable, showOrder, request);
		}
		return "redirect:"+Global.getAdminPath() + "/store/cell/findCellList";
	}

/***
 * 保存多个小区
 * @param countryCode
 * @param isDisable
 * @param showOrder
 * @param request
 */
	private void saveMoreCell(String countryCode, String isDisable,Integer showOrder, HttpServletRequest request) {
		/**添加多个小区*/
		String [] cellName2=request.getParameterValues("cellName2");
		for (int i = 0; i < cellName2.length; i++) {
			if(StringUtils.isNotBlank(cellName2[i])){
				StoreCell	storeCell2 = new StoreCell();
				storeCell2.setCountryCode(countryCode);
				storeCell2.setIsDisable(isDisable);
				storeCell2.setName(cellName2[i]);
				storeCell2.setShowOrder(showOrder+i+1);
				storeCellService.add(storeCell2);
				storeCell2.setCode(countryCode+storeCell2.getId());
				storeCellService.update(storeCell2);
			}
		}
	}
	
	
	@ResponseBody
	@RequestMapping("uploadAddress")
	public Object uploadAddress(){
		List<ProvinceDto> areaList = memberAreaService.findAll();
		return Jsonp_data.success(areaList);
	}
	
	/**
	 * 下载省
	 * @return
	 */
	@ResponseBody
	@RequestMapping("uploadProvince")
	public Object uploadProvince(){
		
		List<AreaDto>  provinceList =  memberAreaService.findByParentAreaCode(AreaConstant.PROVINCE_CODE) ;
		
		return provinceList;
	}
	/**
	 * 下载 城市，地区
	 * @param parentCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("uploadArea")
	public Object uploadArea(String parentCode){
		
		List<AreaDto>  areaList =  memberAreaService.findByParentAreaCode(parentCode) ;
		
		return areaList;
	}
	
}
