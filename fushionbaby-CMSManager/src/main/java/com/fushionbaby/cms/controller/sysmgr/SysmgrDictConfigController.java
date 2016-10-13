package com.fushionbaby.cms.controller.sysmgr;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.SysmgrDictConfigDto;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.config.model.SysmgrDictConfig;
import com.fushionbaby.config.service.SysmgrDictConfigService;

/**
 * @author glc 商品字典
 */
@Controller
@RequestMapping("/sysmgr")
public class SysmgrDictConfigController extends BaseController {

	@Autowired
	private SysmgrDictConfigService<SysmgrDictConfig> sysmgrDictConfigService;
	
	private static final Log LOGGER = LogFactory.getLog(SysmgrDictConfigController.class);
/**
 * 字典列表查询
 * @param sysmgrDictConfigDto 字典信息
 * @param page 分页
 * @param request
 * @param model
 * @version 2015-6-15
 * @return
 */
	@RequestMapping(value="/dictConfigList")
	public String findList(	SysmgrDictConfigDto sysmgrDictConfigDto,
			BasePagination page, HttpServletRequest request, ModelMap model) {
		try {
			BasePagination basePage = new BasePagination();
			if (page != null) {
				basePage = page;
			}
			Map<String, String> params = new HashMap<String, String>();
			if (sysmgrDictConfigDto.getLabel() != null)
				params.put("label", sysmgrDictConfigDto.getLabel().trim());
			if (sysmgrDictConfigDto.getDescription() != null)
				params.put("description", sysmgrDictConfigDto.getDescription().trim());
			basePage.setParams(params);

			basePage = sysmgrDictConfigService.getListPage(basePage);
			model.addAttribute("page", basePage);
			model.addAttribute("sysmgrDictConfigDto", sysmgrDictConfigDto);
		} catch (Exception e) {
			LOGGER.error("商品字典列表加载失败", e);
		}
		return "models/dict/dictList";
	}

	
	/**
	 *  字典添加页面
	 * @param model
	 * @version 2015-6-15
	 * @return
	 */
	@RequestMapping(value="/dictAddPage",method=RequestMethod.GET)
	public String productAddPage(ModelMap model){
		return "models/dict/dictDetail";
	}
	/**
	 * 字典详情
	 * @param id 字典编号
	 * @param model
	 * @version 2015-6-15
	 * @return
	 */
	@RequestMapping(value="/dictDetail/{id}",method=RequestMethod.GET)
	public String skuProductDetail(@PathVariable Long id,ModelMap model){
		SysmgrDictConfig sysmgrDictConfig = sysmgrDictConfigService.findById(id);
		model.addAttribute("sysmgrDictConfig", sysmgrDictConfig);
		return "models/dict/dictDetail";
	}
	
	@RequestMapping(value="/saveDict",method=RequestMethod.POST)
	public String saveDict(@RequestParam(value="id",defaultValue = "") String id,@RequestParam("label")String label,
			@RequestParam("value")String value,@RequestParam("type") String type,
			@RequestParam("description")String description,@RequestParam("sort") Integer sort,
			RedirectAttributes redirectAttributes){
		
		SysmgrDictConfig sysDictConfig=new SysmgrDictConfig();
		sysDictConfig.setLabel(label);
		sysDictConfig.setValue(value);
		sysDictConfig.setType(type);
		sysDictConfig.setDescription(description);
		sysDictConfig.setSort(sort);
		if(ObjectUtils.equals("", id)){
			sysmgrDictConfigService.add(sysDictConfig);
			addMessage(redirectAttributes, "添加成功");
		}else{
			sysDictConfig.setId(Long.parseLong(id));
			sysmgrDictConfigService.update(sysDictConfig);
			addMessage(redirectAttributes, "修改成功");
		}
		
		
		return "redirect:"+Global.getAdminPath()+"/sysmgr/dictConfigList" ;
	}
	

	
	@RequestMapping(value="/deleteDict/{id}",method=RequestMethod.GET)
	public String delDict(@PathVariable(value="id") String id,
		RedirectAttributes redirectAttributes){
		sysmgrDictConfigService.deleteById(Long.parseLong(id));
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/sysmgr/dictConfigList" ;
	}


}
