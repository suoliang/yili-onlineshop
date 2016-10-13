package com.fushionbaby.cms.controller.alabao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aladingshop.alabao.model.AlabaoAccount;
import com.aladingshop.alabao.model.AlabaoTurnToAlabao;
import com.aladingshop.alabao.service.AlabaoAccountService;
import com.aladingshop.alabao.service.AlabaoTurnToAlabaoService;
import com.fushionbaby.cms.controller.BaseController;
import com.fushionbaby.cms.dto.AlabaoTurnToAlabaoDto;
import com.fushionbaby.common.util.BasePagination;


@Controller
@RequestMapping("/alaTurnToAla")
public class AlabaoTurnToAlabaoController extends BaseController {
	
	@Autowired
	private AlabaoTurnToAlabaoService<AlabaoTurnToAlabao> alabaoTurnToAlabaoService;
	@Autowired
	private AlabaoAccountService<AlabaoAccount> alabaoAccountService;
	
	private static final Log LOGGER = LogFactory.getLog(AlabaoTurnToAlabaoController.class);
	
	@SuppressWarnings("unchecked")
	@RequestMapping("transferList")
	public String transferList(AlabaoTurnToAlabaoDto alabaoTurnToAlabaoDto,ModelMap model,BasePagination page){
		
		try {
			if(null == page){
				page = new BasePagination();
			}
			Map<String,String> params = this.setSearchParams(alabaoTurnToAlabaoDto);
			page.setParams(params);
			
			page = alabaoTurnToAlabaoService.getListPage(page);
			List<AlabaoTurnToAlabao> alabaoTurnToAlabaolist = (List<AlabaoTurnToAlabao>) page.getResult();
			List<AlabaoTurnToAlabaoDto> list = this.getTrueName(alabaoTurnToAlabaolist);
			
			
			model.addAttribute("alabaoTurnToAlabaoDto", alabaoTurnToAlabaoDto);
			model.addAttribute("page", page);
			model.addAttribute("list", list);
		} catch (Exception e) {
			LOGGER.error("查询如意宝转出到如意宝账户出错", e);
			return "";
		}
		
		return "models/alabao/turnOut/alabaoTurnToAlabaoList";
	}
	
	
	public Map<String,String> setSearchParams(AlabaoTurnToAlabaoDto alabaoTurnToAlabaoDto){
		
		Map<String,String> params = new HashMap<String, String>();
		
		params.put("account", alabaoTurnToAlabaoDto.getAccount());
		params.put("otherAccount", alabaoTurnToAlabaoDto.getOtherAccount());
		params.put("createTimeFrom", alabaoTurnToAlabaoDto.getCreateTimeFrom());
		params.put("createTimeTo", alabaoTurnToAlabaoDto.getCreateTimeTo());
		params.put("turnOutStatus", alabaoTurnToAlabaoDto.getTurnOutStatus());
		return params;
	}
	
	public List<AlabaoTurnToAlabaoDto> getTrueName(List<AlabaoTurnToAlabao> list){
		
		List<AlabaoTurnToAlabaoDto> alaTurnToAlaList = new ArrayList<AlabaoTurnToAlabaoDto>();
		if(list != null && list.size() != 0){
			for(AlabaoTurnToAlabao alabaoTurnToAlabao:list){
				AlabaoAccount alabaoAccount = alabaoAccountService.findByAccount(alabaoTurnToAlabao.getAccount());
				AlabaoTurnToAlabaoDto alabaoTurnToAlabaoDto = new AlabaoTurnToAlabaoDto();
				
				alabaoTurnToAlabaoDto.setAccount(alabaoTurnToAlabao.getAccount());
				alabaoTurnToAlabaoDto.setOtherAccount(alabaoTurnToAlabao.getOtherAccount());
				alabaoTurnToAlabaoDto.setSerialNum(alabaoTurnToAlabao.getSerialNum());
				alabaoTurnToAlabaoDto.setTransferMoney(alabaoTurnToAlabao.getTransferMoney());
				alabaoTurnToAlabaoDto.setMemo(alabaoTurnToAlabao.getMemo());
				alabaoTurnToAlabaoDto.setTurnOutStatus(alabaoTurnToAlabao.getTurnOutStatus());
				alabaoTurnToAlabaoDto.setCreateTime(alabaoTurnToAlabao.getCreateTime());
				alabaoTurnToAlabaoDto.setTrueName(alabaoAccount.getTrueName());
				
				alaTurnToAlaList.add(alabaoTurnToAlabaoDto);
			}
		}
		
		
		return alaTurnToAlaList;
	}
}
