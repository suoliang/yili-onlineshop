package com.fushionbaby.cms.controller.store;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.sku.cms.model.SkuCategory;
import com.aladingshop.sku.cms.service.SkuInfoService;
import com.aladingshop.store.auth.model.StoreAuthUser;
import com.aladingshop.store.auth.service.StoreAuthUserService;
import com.aladingshop.store.model.Store;
import com.aladingshop.store.model.StoreCell;
import com.aladingshop.store.model.StoreConfig;
import com.aladingshop.store.querycondition.StoreCellQueryCondition;
import com.aladingshop.store.service.StoreCellService;
import com.aladingshop.store.service.StoreConfigService;
import com.aladingshop.store.service.StoreService;
import com.fushionbaby.cache.util.RedisUtil;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.store.StoreDto;
import com.fushionbaby.cms.util.CMSSessionUtils;
import com.fushionbaby.cms.util.excel.ImportExcel;
import com.fushionbaby.common.constants.CommonConstant;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.RandomNumUtil;
import com.fushionbaby.member.model.MemberAreaConfig;
import com.fushionbaby.member.service.MemberAreaConfigService;

/***
 * 门店（身边阿拉丁）
 * @description 类描述...
 * @author 徐培峻
 * @date 2016年1月8日上午9:19:25
 */
@Controller
@RequestMapping("/store")
public class StoreController extends BaseController{

    /**门店服务*/
	@Autowired
	private StoreService<Store> storeService;
	/**门店权限*/
	@Autowired
	private StoreAuthUserService<StoreAuthUser> storeAuthUserService;
	/**地域信息*/
	@Autowired
	private MemberAreaConfigService<MemberAreaConfig> areaConfigService;
	/**门店小区*/
	@Autowired
	private StoreCellService<StoreCell> storeCellService;
	/**门店配置*/
	@Autowired
	private StoreConfigService<StoreConfig> storeConfigService;
	/**商品*/
	@Autowired
	protected SkuInfoService skuService;
	/** 日志 */
	private static final Log logger = LogFactory.getLog(StoreController.class);
	/** 返回到登陆页面*/
	//private static final String RESULT="authorization/login";
	/**页面user的目录*/
	private static final String PRE_="models/store/";
	
	/**门店的编码开始序号*/
	//private static final Long STORE_PRE=1000l;
	
	/**列表页面的url*/
	private  static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/store/storeList";

	private static RedisUtil redis = new RedisUtil();
	
	
	/***
	 * 门店查询列表
	 * @param userDto
	 * @param page
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("storeList")
	public String findList(Store store,BasePagination page, Model model,HttpSession session) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("number", store.getNumber());
			params.put("name", store.getName());
			params.put("cellName", store.getCellName());
			params.put("cityName", store.getCityName());
			params.put("countryName", store.getCountryName());
			if(store.getStatus() != null){
				params.put("status", store.getStatus().toString());
			}
			
			page.setParams(params);
			page = storeService.getListPage(page);
			List<Store> storeList=(List<Store>)page.getResult();
			List<StoreDto> storeDtoList=new ArrayList<StoreDto>();
			for(Store storetmp: storeList){
				StoreDto storeDtotmp = getStoreDtoTmp(storetmp);
				storeDtoList.add(storeDtotmp);
			}
			model.addAttribute("storeDtoList", storeDtoList);
			model.addAttribute("page", page);
			model.addAttribute("store", store);
			
		} catch (Exception e) {
			logger.error("后台门店列表加载失败", e);
		}
		return PRE_+"storeList";
	}

	private StoreDto getStoreDtoTmp(Store storetmp) {
		StoreDto storeDtotmp =new StoreDto();
		storeDtotmp.setProvinceCode(storetmp.getProvinceCode());
		storeDtotmp.setProvinceName(storetmp.getProvinceName());
		storeDtotmp.setCityCode(storetmp.getCityCode());
		storeDtotmp.setCityName(storetmp.getCityName());
		storeDtotmp.setCountryCode(storetmp.getCountryCode());
		storeDtotmp.setCountryName(storetmp.getCountryName());
		storeDtotmp.setId(storetmp.getId());
		storeDtotmp.setCode(storetmp.getCode());
		storeDtotmp.setIsDeleted(storetmp.getIsDeleted());
		storeDtotmp.setLatitude(storetmp.getLatitude());
		storeDtotmp.setLongitude(storetmp.getLongitude());
		storeDtotmp.setName(storetmp.getName());
		storeDtotmp.setNumber(storetmp.getNumber());
		storeDtotmp.setStatus(storetmp.getStatus());
		storeDtotmp.setCellName(storetmp.getCellName());
		storeDtotmp.setCellCode(storetmp.getCellCode());
		StoreAuthUser storeAuthUser = this.storeAuthUserService.findSysUserByStoreCode(storetmp.getCode());
		boolean isSetSysUser = ObjectUtils.equals(null, storeAuthUser)?false:true;
		storeDtotmp.setSetSysUser(isSetSysUser);
		/**拿到该店所有的商品种类*/
		List<SkuCategory> list = skuService.queryByStroeCode(storetmp.getCode());
		storeDtotmp.setSkuNum(list.size());
		return storeDtotmp;
	}
	/***
	 * 添加修改的页面
	 * @param code  默认为北京市东城区
	 * @return
	 */
	@RequestMapping("goEdit")
	public String goEdit(@RequestParam(value="code",defaultValue="") String code, Model model,HttpSession session){
		
		String countryCode = "110101";
		if(StringUtils.isNotBlank(code)){
			Store store = storeService.findByCode(code);
			countryCode = store.getCountryCode();
			model.addAttribute("store", store);
		}
		
		StoreCellQueryCondition queryCondition = new StoreCellQueryCondition();
		queryCondition.setCountryCode(countryCode);
		List<StoreCell> list = storeCellService.queryByCondition(queryCondition);
		model.addAttribute("cellList", list);
		
		return PRE_+"storeEdit";
	}
	
	
	/***
	 * 添加修改
	 * @param id
	 * @param store
	 * @param redirectAttributes
	 * @param session
	 * @return
	 */
	@RequestMapping("editStore")
	public String updateUser(Long id,Store store,RedirectAttributes redirectAttributes,HttpSession session){
		try {
			Store findStore = this.storeService.findByCode(store.getCode());
			if(store.getId()!=null && findStore==null){
				addMessage(redirectAttributes, "修改门店失败");
				return REDIRECT_LIST;
			}
			StoreCellQueryCondition queryCondition = new StoreCellQueryCondition();
			queryCondition.setCode(store.getCellCode());
			List<StoreCell> list = storeCellService.queryByCondition(queryCondition);
			store.setCellName((list!=null && list.size()>0 )?list.get(0).getName():"");
	    	Long currentUserId = CMSSessionUtils.getSessionUser(session).getId();
	    	store.setUpdateId(currentUserId);
	    	if(store.getId()!=null){
	    		store.setId(findStore.getId());
	    		store.setIsDeleted(findStore.getIsDeleted());
	    		storeService.update(store);
	    	}else{
	    		String number=String.valueOf(Integer.valueOf(this.storeService.findLastOne().getNumber())+1);
	    		//store.setCode(number);
	    		store.setNumber(number);
	    		store.setCreateId(currentUserId);
	    		store.setCreateTime(new Date());
	    		store.setIsDeleted(CommonConstant.NO);
	    		storeService.add(store);
	    		saveStoreUser(currentUserId, number);
	    		saveStoreConfig(number);
	    		
	    	}
			addMessage(redirectAttributes, "编辑门店成功");
		} catch (Exception e) {
			logger.error("编辑门店失败", e);
			addMessage(redirectAttributes, "编辑门店失败");
		}
		return REDIRECT_LIST;
	}

	/***
	 * 保存门店配置
	 * @param number
	 */
	private void saveStoreConfig(String number) {
		String code=this.storeService.findByNumber(number).getCode();
		StoreConfig storeConfig=new StoreConfig();
		storeConfig.setBusinessEndTime("8:00:00");
		storeConfig.setBusinessStartTime("22:00:00");
		storeConfig.setFreeFreightAmount(0);
		storeConfig.setFreightAmount(0);
		storeConfig.setStoreCode(code);
		storeConfigService.add(storeConfig);
	}

	/***
	 * 为新开店的门店添加一个系统用户
	 * @param currentUserId
	 * @param number
	 */
	private void saveStoreUser(Long currentUserId, String number) {
		StoreAuthUser user=new StoreAuthUser();
		user.setCreateId(currentUserId);
		user.setCreateTime(new Date());
		user.setIsDisabled(CommonConstant.NO);
		user.setMemo("门店为："+number+"的系统用户的账号");
		user.setName(number+RandomNumUtil.getNumber(2));
		user.setPassword(RandomNumUtil.getNumber(6));
		user.setStoreNumber(number);
		Store storeTemp=this.storeService.findByNumber(number);
		user.setStoreCode(storeTemp.getCode());
		user.setUserType(1);
		this.storeAuthUserService.add(user);
	}
	/***
	 * 禁用 门店
	 * @param code
	 * @param redirectAttributes
	 * @param session
	 * @return
	 */
	@RequestMapping("disableStore/{code}/{time}")
	public String disableStore(@PathVariable("code") String code,RedirectAttributes redirectAttributes,HttpSession session){
		try {
			Store store=this.storeService.findByCode(code);
			String isDeleted=store.getIsDeleted();
			isDeleted=CommonConstant.YES.equals(isDeleted)?CommonConstant.NO:CommonConstant.YES;
			store.setIsDeleted(isDeleted);
			Long currentUserId = CMSSessionUtils.getSessionUser(session).getId();
	    	store.setUpdateId(currentUserId);
			storeService.update(store);
			addMessage(redirectAttributes, "操作成功");
		} catch (Exception e) {
			logger.error("操作失败", e);
			addMessage(redirectAttributes, "操作失败");
		}
		return REDIRECT_LIST; 
	}
	
	/***
	 * 跳转到添加的页面
	 * @param model
	 * @return
	 */
	@RequestMapping("goAddSysUser/{code}")
	public String toAddSysUser(Model model,@PathVariable("code") String code,HttpSession session) {
		Store store=this.storeService.findByCode(code);
		model.addAttribute("store", store);
		return PRE_+"storeAddSysUser";
	}
	/***
	 * 添加门店
	 * @param user	
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping("addStoreSysUser")
	public String addStoreSysUser(StoreAuthUser storeAuthUser,HttpSession session,RedirectAttributes redirectAttributes){
	    try {
	    	Long currentUserId = CMSSessionUtils.getSessionUser(session).getId();
	    	storeAuthUser.setCreateId(currentUserId);
	    	storeAuthUser.setEmail(storeAuthUser.getEmail());
	    	storeAuthUser.setIsDisabled(CommonConstant.NO);
	    	storeAuthUser.setMemo(storeAuthUser.getMemo());
	    	storeAuthUser.setName(storeAuthUser.getName());
	    	storeAuthUser.setPassword(storeAuthUser.getPassword());
	    	storeAuthUser.setPhone(storeAuthUser.getPhone());
	    	storeAuthUser.setStoreCode(storeAuthUser.getStoreCode());
	    	storeAuthUser.setStoreNumber(storeAuthUser.getStoreNumber());
	    	storeAuthUser.setUserType(1);
	    	storeAuthUserService.add(storeAuthUser);
			addMessage(redirectAttributes, "添加门店管理员成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "添加门店管理员失败");
		}
		return REDIRECT_LIST;
	}
	
	@RequestMapping("goUpdateSysUser/{code}")
	public String goUpdateSysUser(Model model,@PathVariable("code") String code,HttpSession session) {
		Store store=this.storeService.findByCode(code);
		model.addAttribute("store", store);
		
		StoreAuthUser storeAuthUser=this.storeAuthUserService.findSysUserByStoreCode(code);
		model.addAttribute("storeAuthUser", storeAuthUser);
		return PRE_+"storeUpdateSysUser";
	}

	@RequestMapping("updateStoreSysUser")
	public String updateStoreAuthUser(StoreAuthUser storeAuthUser,RedirectAttributes redirectAttributes,HttpSession session){
		try {
			Long currentUserId = CMSSessionUtils.getSessionUser(session).getId();
			StoreAuthUser findUser = storeAuthUserService.findById(storeAuthUser.getId());
			findUser.setUpdateId(currentUserId);
			findUser.setEmail(storeAuthUser.getEmail());
			findUser.setIsDisabled(CommonConstant.NO);
			findUser.setMemo(storeAuthUser.getMemo());
			findUser.setName(storeAuthUser.getName());
			findUser.setPassword(storeAuthUser.getPassword());
			findUser.setPhone(storeAuthUser.getPhone());
			findUser.setStoreCode(storeAuthUser.getStoreCode());
			findUser.setStoreNumber(storeAuthUser.getStoreNumber());
			findUser.setUserType(1);
	    	storeAuthUserService.update(findUser);
			addMessage(redirectAttributes, "修改门店管理员成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "修改门店管理员失败");
		}
		return REDIRECT_LIST;
	}
	
	/**
	 * 查询小区列表
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findCellList",method=RequestMethod.POST)
	public List<StoreCell> findCellList(String code){
		StoreCellQueryCondition queryCondition = new StoreCellQueryCondition();
		queryCondition.setCountryCode(code);
		List<StoreCell> list=storeCellService.queryByCondition(queryCondition);
		return list;
	}

	/***
	 * 用户姓名校验
	 * @param name
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("storeUnique")
	public boolean userUnique(@RequestParam("number") String number,@RequestParam("numbertmp") String numbertmp,
			@RequestParam(value="id",defaultValue="") Long id,HttpSession session) {
 		if(id!=null){
 			if(StringUtils.equals(number, numbertmp)){
 	 			return true;
 	 		}
		}
 		
 		Store store = storeService.findByNumber(number);
		
 		return ObjectUtils.equals(null, store)?true:false;
	}
	
	/***
	 * 用户姓名校验
	 * @param name
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("storeSysUserUnique")
	public boolean storeSysUserUnique(@RequestParam("storeCode") String storeCode,@RequestParam("userName") String userName,HttpSession session) {
		StoreAuthUser storeAuthUser = storeAuthUserService.findStoreAuthUserByUserNameAndStoreCode(userName, storeCode);
		return ObjectUtils.equals(null, storeAuthUser)?true:false;
	}

	/***
	 * 批量导入门店
	 */
	@RequestMapping("showImportStore")
	public String showImportMember() {
		return "models/store/importStore";
	}
	
	/**
	 * 批量导入小区
	 * @return
	 */
	@RequestMapping("importCell")
	public String importCell(){
		return "models/store/storeCellExcel";
	}
	
	@RequestMapping("importCellExcel")
	public String importCellExcel(MultipartFile excelPath, ModelMap model, HttpSession session){
		
			List<StoreCell> storeCellList = new ArrayList<StoreCell>();
		
			try {
				ImportExcel ie = new ImportExcel(excelPath, 0, 0);
				int count = 0;
				for(int i = ie.getDataRowNum(); i<= ie.getLastDataRowNum(); i++){
					Row row = ie.getRow(i);
					
					StoreCell storeCell = new StoreCell();
					
					//从Excel表中获取各行的参数
					String name = ObjectUtils.toString(ie.getCellValue(row, 0));
					String code = ObjectUtils.toString(ie.getCellValue(row, 1));
					String countryCode = ObjectUtils.toString(ie.getCellValue(row, 2));
					if(StringUtils.isBlank(name) || StringUtils.isBlank(code) || StringUtils.isBlank(countryCode)){
							count++;
							continue;
					}
					storeCell.setName(name);
					storeCell.setCode(code);
					storeCell.setCountryCode(countryCode);
					storeCellList.add(storeCell);
					}
					for (StoreCell storeCell : storeCellList) {
						storeCellService.add(storeCell);
					}
					
					model.addAttribute("rowNum", ie.getLastDataRowNum());
					model.addAttribute("storeCellList", storeCellList);
					model.addAttribute("count", count);
					
				}  catch (Exception e) {
					
					logger.error("批量导入小区失败", e);
				}
				return "models/store/importCellSuccess";
		}
	/**
	 * 刷新APP
	 * @param storeCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("refurbishApp")
	public Object refurbishApp(String storeCode){
		long delNum  = 0;
		try {

			if(StringUtils.isNotBlank(storeCode)){
				delNum = redis.hdel("STORE_APP_HOME", "STORE_CATEGORY_KEY_".toUpperCase() +storeCode);
				delNum = delNum+redis.hdel("STORE_APP_HOME", "STORE_HOME_KEY_".toUpperCase() +storeCode) ;
			}else{
				delNum = redis.del("STORE_APP_HOME");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
}
