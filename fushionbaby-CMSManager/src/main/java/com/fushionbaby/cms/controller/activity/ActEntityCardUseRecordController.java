package com.fushionbaby.cms.controller.activity; 

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aladingshop.store.model.Store;
import com.aladingshop.store.service.StoreService;
import com.fushionbaby.act.activity.dto.EntityCardQueryDto;
import com.fushionbaby.act.activity.model.ActEntityCardUseRecord;
import com.fushionbaby.act.activity.service.ActEntityCardUseRecordService;
import com.fushionbaby.auth.model.AuthUser;
import com.fushionbaby.auth.service.AuthUserService;
import com.fushionbaby.cms.config.Global;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.common.util.BasePagination;

@Controller
@RequestMapping("/actEntityCardUseRecord")
public class ActEntityCardUseRecordController extends BaseController { 


	@Autowired
	private ActEntityCardUseRecordService<ActEntityCardUseRecord> actEntityCardUseRecordService;
	
	@Autowired
	private AuthUserService<AuthUser> authUserService;

	/**门店*/
	@Autowired
	private StoreService<Store> storeService;
	
	private static final Log logger = LogFactory.getLog(ActEntityCardUseRecordController.class);
	
	private static final String PRE_="models/entity/"; 
	
	/**列表页*/
	private static final String REDIRECT_LIST="redirect:"+Global.getAdminPath()+"/actEntityCardUseRecord/actEntityCardUseRecordList";
	
	@SuppressWarnings("unchecked")
	@RequestMapping("actEntityCardUseRecordList")
	public String findAll(EntityCardQueryDto queryDto,
//			@RequestParam(value = "useType", defaultValue = "") String useType,
//			@RequestParam(value = "useSource", defaultValue = "") String useSource,
//			@RequestParam(value = "orderCode", defaultValue = "") String orderCode,
//			@RequestParam(value = "cardNo", defaultValue = "") String cardNo,
			BasePagination page, Model model) {
		try {
			if (page == null) {
				page = new BasePagination();
			}
			Map<String, String> params = new HashMap<String, String>();
			params.put("cardNo", queryDto.getCardNo());
			params.put("useType", queryDto.getUseType());
			params.put("useSource",queryDto.getUseSource());
			page.setParams(params);
			page = this.actEntityCardUseRecordService.getListPage(page);
			List<ActEntityCardUseRecord> listFindAlls = (List<ActEntityCardUseRecord>) page.getResult();
			for (ActEntityCardUseRecord actEntityCardUseRecord : listFindAlls) {
				if(actEntityCardUseRecord.getUpdateId() != null){
					AuthUser authUser = authUserService.findById(actEntityCardUseRecord.getUpdateId());
					actEntityCardUseRecord.setUpdateName(authUser==null?"":authUser.getLoginName());
					
					Store store=this.storeService.findByCode(actEntityCardUseRecord.getStoreCode());
					actEntityCardUseRecord.setStoreName(store==null?"":store.getName());
				}
			}
			model.addAttribute("list", listFindAlls);
			model.addAttribute("page", page);
			model.addAttribute("queryDto", queryDto);
			model.addAttribute("storeList", this.storeService.findAll());
			return PRE_+"actEntityCardUseRecordList";
		} catch (Exception e) {
			logger.error("查询权限列表失败", e);
			return "";
		}
	}
	

	@RequestMapping("actEntityCardUseRecordAddJsp")
	public String actEntityCardUseRecordAddJsp(Model model) {
		/*try {
			addMessage(redirectAttributes, "添加实体卡配置成功");
			return PRE_+"actEntityCardUseRecordAdd";
		} catch (Exception e) {
			logger.error("添加配置失败", e);
			return "";
		}*/
		return PRE_+"actEntityCardUseRecordAdd";
	}
	
	@RequestMapping("actEntityCardUseRecordAdd")
	public String actEntityCardUseRecordAdd(ActEntityCardUseRecord actEntityCardUseRecord, RedirectAttributes redirectAttributes, HttpSession session) {
		try {
			this.actEntityCardUseRecordService.add(actEntityCardUseRecord);
			addMessage(redirectAttributes, "添加实体卡配置成功");
			return REDIRECT_LIST;
		} catch (Exception e) {
			logger.error("添加配置失败", e);
			return "";
		}
	}
	
	@RequestMapping("actEntityCardUseRecordEditJsp/{id}/{time}")
	public String actEntityCardUseRecordEditJsp(@PathVariable("id") Long id, Model model) {
		try {
			ActEntityCardUseRecord actEntityCardUseRecord = this.actEntityCardUseRecordService.findById(id);
			model.addAttribute("actEntityCardUseRecord", actEntityCardUseRecord);
			return PRE_+"actEntityCardUseRecordEdit";
		} catch (Exception e) {
			logger.error("修改配置失败", e);
			return "";
		}
	}
	
	
	@RequestMapping("actEntityCardUseRecordEdit")
	public String actEntityCardUseRecordEdit(ActEntityCardUseRecord actEntityCardUseRecord, RedirectAttributes redirectAttributes, HttpSession session) {
		try {
			this.actEntityCardUseRecordService.update(actEntityCardUseRecord);
			addMessage(redirectAttributes, "修改实体卡配置成功");
			return REDIRECT_LIST;
		} catch (Exception e) {
			logger.error("修改配置失败", e);
			return "";
		}
	}

	@RequestMapping("actEntityCardUseRecordDel/{id}/{time}")
	public String actEntityCardUseRecordDel(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		try {
			this.actEntityCardUseRecordService.deleteById(id);
			addMessage(redirectAttributes, "删除实体卡配置成功");
			return REDIRECT_LIST;
		} catch (Exception e) {
			logger.error("删除配置失败", e);
			return "";
		}
	}
}
