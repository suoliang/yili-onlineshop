package com.fushionbaby.act.activity.service.impl; 


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fushionbaby.act.activity.dao.ActEntityCardDao;
import com.fushionbaby.act.activity.dto.EntityCardReqDto;
import com.fushionbaby.act.activity.dto.EntityCardResDto;
import com.fushionbaby.act.activity.model.ActEntityCard;
import com.fushionbaby.act.activity.model.ActEntityCardConfig;
import com.fushionbaby.act.activity.model.ActEntityCardUseRecord;
import com.fushionbaby.act.activity.service.ActEntityCardConfigService;
import com.fushionbaby.act.activity.service.ActEntityCardService;
import com.fushionbaby.act.activity.service.ActEntityCardUseRecordService;
import com.fushionbaby.common.util.BasePagination;

@Service
public class ActEntityCardServiceImpl implements ActEntityCardService<ActEntityCard>  { 
	
	@Autowired
	private ActEntityCardDao objectDao;
	
	/**实体卡的使用记录*/
	@Autowired
	private ActEntityCardUseRecordService<ActEntityCardUseRecord> useRecordService;
	/** 实体卡类型*/
	@Autowired
	private ActEntityCardConfigService<ActEntityCardConfig> cardConfigService;
	
	public void add(ActEntityCard object) {
		objectDao.add(object);
		
	}
	
	public ActEntityCard findById(Long id) {
		return objectDao.findById(id);
	}
	
	public void update(ActEntityCard object) {
		objectDao.update(object);
		
	}
	
	public List<ActEntityCard> findAll() {
		return objectDao.findAll();
		
	}
	
	public void deleteById(Long id) {
		objectDao.deleteById(id);
	}


	public BasePagination getListPage(BasePagination page) {
		Integer total = objectDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<ActEntityCard> list = objectDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<ActEntityCard>());
		}
		return page;
	}

	public ActEntityCard findByCardNoPwd(String cardNo, String pwd) {
		Map<String, Object> map =  new HashMap<String, Object>();
		map.put("cardNo", cardNo);
		map.put("chargePwd", pwd);
		return objectDao.findByCardNoPwd(map);
	}

	public void updatesurplusMoneyById(ActEntityCard object) {
		objectDao.updatesurplusMoneyById(object);
	}

	public ActEntityCard findByCardNo(String cardNo) {
		return objectDao.findByCardNo(cardNo);
	}

	public EntityCardResDto getCardInfoByCode(String entityCode) {
		EntityCardResDto resDto=new EntityCardResDto();
		resDto.setStatus("2");//设置不可用
		ActEntityCard entityCard= this.objectDao.findByCardNo(entityCode);
		ActEntityCardConfig cardConfig=this.cardConfigService.findById(entityCard.getConfigId());
		if(entityCardIsCanUse(entityCard, cardConfig))
			return resDto;
		resDto.setStatus("1");
		resDto.setEntityCardMoney(entityCard.getSurplusMoney()==null?new BigDecimal(0): entityCard.getSurplusMoney());
		resDto.setEntityCardPassword(entityCard.getChargePwd()==null?"":entityCard.getChargePwd());
		resDto.setFaceMoney(entityCard.getFaceMoney()==null?new BigDecimal(0):entityCard.getFaceMoney());
		return resDto;
	}

	/***
	 * 判断 该实体卡不可用  返回true
	 * @param entityCard
	 * @param cardConfig
	 * @return
	 */
	private boolean entityCardIsCanUse(ActEntityCard entityCard,ActEntityCardConfig cardConfig) {
		boolean result=false;
		if(entityCard==null||cardConfig==null||entityCard.getStatus().equals("2"))
			result=true;
		if(cardConfig.getBeginTime().after(new Date())||cardConfig.getExpiration().before(new Date())/*||CommonConstant.YES.equals(cardConfig.getIsDisabled())*/)
			result=true;
	   return result;
	}

	public EntityCardResDto useEntityCard(EntityCardReqDto reqDto) {
		Map<String, Object> map =  new HashMap<String, Object>();
		map.put("cardNo", reqDto.getEntityCode());
		map.put("chargePwd", reqDto.getPassword());
		ActEntityCard card=objectDao.findByCardNoPwd(map);
		EntityCardResDto resDto=new EntityCardResDto();
		if(card!=null){
			resDto.setStatus("1");
			resDto.setEntityCardMoney(card.getSurplusMoney()==null?new BigDecimal(0): card.getSurplusMoney());
		    resDto.setEntityCardPassword(card.getChargePwd()==null?"":card.getChargePwd());
		    resDto.setFaceMoney(card.getFaceMoney()==null?new BigDecimal(0):card.getFaceMoney());
		   
		    card.setSurplusMoney(new BigDecimal(0));
			card.setStatus("2");
			this.objectDao.update(card);
		}
		else 
			resDto.setStatus("2");
		
		return resDto;
	}


}
