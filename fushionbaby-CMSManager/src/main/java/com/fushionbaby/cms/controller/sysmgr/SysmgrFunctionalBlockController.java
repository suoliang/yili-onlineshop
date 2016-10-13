/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月29日下午3:53:22
 */
package com.fushionbaby.cms.controller.sysmgr;

import java.net.URLDecoder;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.util.BatchUploadFiles;
import com.fushionbaby.cms.util.ImagePathUtil;
import com.fushionbaby.cms.util.constant.Constant;
import com.fushionbaby.sysmgr.model.SysmgrFunctionalBlock;
import com.fushionbaby.sysmgr.service.SysmgrFunctionalBlockService;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * @description 类描述...
 * @author 孟少博
 * @date 2015年9月29日下午3:53:22
 */
@Controller
@RequestMapping("sysmgr/functionalBlock")
public class SysmgrFunctionalBlockController {
	
	
	private static final String FTP_PATH = "/userfiles/images/functional";
	
	@Autowired
	private SysmgrFunctionalBlockService<SysmgrFunctionalBlock> sysmgrFunctionalBlockService;
/**
 * 查询全部的功能栏
 * @param model
 * @return
 */
	@RequestMapping("findAll")
	public String findAll(Model model){
		
		List<SysmgrFunctionalBlock> results = sysmgrFunctionalBlockService.findAllByDisable(null);
		
		model.addAttribute("results", results);
		model.addAttribute("imagePath", Global.getImagePath());
		
		return "models/sysmgr/functionalBlockList";
	}
	/**
	 * 编辑页面
	 * @param code
	 * @param model
	 * @return
	 */
	@RequestMapping("editPage")
	public String editPage(String code,Model model){
		SysmgrFunctionalBlock result = null;
		if(StringUtils.isNotBlank(code)){
			result = sysmgrFunctionalBlockService.findByCode(code);
			result.setIcon(Global.getImagePath() + result.getIcon());
		}
		model.addAttribute("result", result);
		
		return "models/sysmgr/editFunctionalBlockPage";
	}
	
	
	/**
	 * 该编号是否存在
	 * @param code 编号
	 * @param id 序号
	 * @return
	 */
	@ResponseBody
	@RequestMapping("existCode")
	public Object existCode(String code,Long id){
		if(id!=null){
			return true;
		}
		
		SysmgrFunctionalBlock result = sysmgrFunctionalBlockService.findByCode(code);
		if(result == null){
			return true;
		}
		return false;
	}
	
	/**
	 * 编辑
	 * @param jsonStr json 字符串格式的功能栏目属性类
	 * @param oldIcon 旧的图标
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping("edit")
	public String edit(@RequestParam("jsonStr") String jsonStr,String oldIcon){
		
		try {
			String convertStr = URLDecoder.decode(jsonStr, "utf-8");
			SysmgrFunctionalBlock sysmgrFunctionalBlock = 	jsonConvertSysmgrFunctionalBlock(convertStr);
			
			// 判断用户是否更新图片
			if (!StringUtils.equals(sysmgrFunctionalBlock.getIcon(),oldIcon)) { // APP端图片
				
				BatchUploadFiles.delFtpImage(BatchUploadFiles.getFileName(sysmgrFunctionalBlock.getIcon()), FTP_PATH + "/"
						+ BatchUploadFiles.getFileBeforDic(sysmgrFunctionalBlock.getIcon())); // FTP删除
			}
			if(StringUtils.isNotBlank(sysmgrFunctionalBlock.getIcon())){
				sysmgrFunctionalBlock.setIcon( ImagePathUtil.getImageName((sysmgrFunctionalBlock.getIcon())));
			}
			if(sysmgrFunctionalBlock.getId()!=null){
				sysmgrFunctionalBlockService.update(sysmgrFunctionalBlock);
			}else{
				sysmgrFunctionalBlockService.add(sysmgrFunctionalBlock);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Constant.FAILURE;
		} 
			
		
		return Constant.SUCCESS;
	}
	
	private SysmgrFunctionalBlock jsonConvertSysmgrFunctionalBlock(String jsonStr) {

		SysmgrFunctionalBlock sysmgrFunctionalBlock = null;
		try {
			JsonParser jsonParser = new JsonParser();
			JsonElement json = jsonParser.parse(jsonStr);
			sysmgrFunctionalBlock = new SysmgrFunctionalBlock();
			
			sysmgrFunctionalBlock.setId(StringUtils.isBlank(json.getAsJsonObject().get("id").getAsString()) ? null : 
				Long.valueOf(json.getAsJsonObject().get("id").getAsString()));
			sysmgrFunctionalBlock.setCode(json.getAsJsonObject().get("code").getAsString() );
			sysmgrFunctionalBlock.setDisable(json.getAsJsonObject().get("disable").getAsString() );
			sysmgrFunctionalBlock.setIcon(json.getAsJsonObject().get("icon").getAsString());
			sysmgrFunctionalBlock.setLinkUrl(json.getAsJsonObject().get("linkUrl").getAsString());
			sysmgrFunctionalBlock.setName( json.getAsJsonObject().get("name").getAsString());
			sysmgrFunctionalBlock.setSortOrder(StringUtils.isBlank(json.getAsJsonObject().get("sortOrder").getAsString() ) ? null : 
				Integer.valueOf(json.getAsJsonObject().get("sortOrder").getAsString()));
			sysmgrFunctionalBlock.setStartTime(StringUtils.isBlank(json.getAsJsonObject().get("startTime").getAsString()) ? null:
					DateUtils.parseDate(json.getAsJsonObject().get("startTime").getAsString(), "yyyy-MM-dd"));
			sysmgrFunctionalBlock.setEndTime(StringUtils.isBlank(json.getAsJsonObject().get("endTime").getAsString()) ? null:
				DateUtils.parseDate(json.getAsJsonObject().get("endTime").getAsString(), "yyyy-MM-dd"));
			
			sysmgrFunctionalBlock.setUseType(Integer.valueOf(json.getAsJsonObject().get("useType").getAsString()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		
		return sysmgrFunctionalBlock;
		
	}

}
