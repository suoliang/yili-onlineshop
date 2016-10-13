package com.aladingshop.card.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.card.dao.MemberCardConfigDao;
import com.aladingshop.card.model.MemberCardConfig;
import com.aladingshop.card.service.MemberCardConfigService;
import com.fushionbaby.common.enums.MemberCardYiDuoBaoEnum;
import com.fushionbaby.common.util.BasePagination;

/**
 * @author xinlangtao
 * 
 */
@Service
public class MemberCardConfigServiceImpl implements MemberCardConfigService<MemberCardConfig> {

	@Autowired
	private MemberCardConfigDao membercardconfigDao;

	public void deleteById(Long id) {
		membercardconfigDao.deleteById(id);
	}

	public void add(MemberCardConfig record) {
		membercardconfigDao.add(record);
	}

	public MemberCardConfig findById(Long id) {
		MemberCardConfig memberCardConfig=membercardconfigDao.findById(id);
		if(memberCardConfig!=null)
		memberCardConfig.setTypeName(MemberCardYiDuoBaoEnum.getTypeName(memberCardConfig.getType()));
		return memberCardConfig;
	}

	public List<MemberCardConfig> findAll() {
		List<MemberCardConfig> cardConfigList=membercardconfigDao.findAll();
		for (MemberCardConfig memberCardConfig : cardConfigList) {
			memberCardConfig.setTypeName(MemberCardYiDuoBaoEnum.getTypeName(memberCardConfig.getType()));
		}
		return cardConfigList;
	}
	
	public void update(MemberCardConfig record) {
		membercardconfigDao.update(record);
	}

	/**
	 * 分页 xinlangtao
	 */
	public BasePagination getListPage(BasePagination page) throws DataAccessException {
		Integer total = membercardconfigDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<MemberCardConfig> list = membercardconfigDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<MemberCardConfig>());
		}
		return page;
	}

	public void updateIsDisabled(MemberCardConfig memberCardConfig) {
		membercardconfigDao.updateIsDisabled(memberCardConfig);
	}

	public List<MemberCardConfig> findByType(String type) {

		return this.membercardconfigDao.findByType(type);
	}
}
