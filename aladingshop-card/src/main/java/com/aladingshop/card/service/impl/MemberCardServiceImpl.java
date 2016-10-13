package com.aladingshop.card.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.aladingshop.card.dao.MemberCardDao;
import com.aladingshop.card.model.MemberCard;
import com.aladingshop.card.model.MemberCardConfig;
import com.aladingshop.card.service.MemberCardConfigService;
import com.aladingshop.card.service.MemberCardService;
import com.fushionbaby.common.enums.MemberCardYiDuoBaoEnum;
import com.fushionbaby.common.util.BasePagination;
import com.fushionbaby.common.util.date.DateFormat;

/**
 * @author xinlangtao
 * 
 */
@Service
public class MemberCardServiceImpl implements MemberCardService<MemberCard> {

	@Autowired
	private MemberCardDao membercardDao;

	@Autowired
	private MemberCardConfigService<MemberCardConfig> memberCardConfigService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fushionbaby.common.service.BaseService#add(java.lang.Object)
	 */
	public void add(MemberCard card) throws DataAccessException {
		membercardDao.add(card);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fushionbaby.common.service.BaseService#deleteById(java.lang.Long)
	 */
	public void deleteById(Long id) throws DataAccessException {
		membercardDao.deleteById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fushionbaby.common.service.BaseService#update(java.lang.Object)
	 */
	public void update(MemberCard card) throws DataAccessException {
		membercardDao.update(card);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.fushionbaby.common.service.BaseService#findById(java.lang.Long)
	 */
	public MemberCard findById(Long id) throws DataAccessException {
		return membercardDao.findById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aladingshop.card.service.MemberCardService#findAll()
	 */
	public List<MemberCard> findAll() {
		return membercardDao.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aladingshop.card.service.MemberCardService#getListPage(com.fushionbaby
	 * .common.util.BasePagination)
	 */
	public BasePagination getListPage(BasePagination page) {
		Integer total = membercardDao.getTotal(page.getSearchParamsMap());
		page.setCurrentTotal(total);
		if (total > 0) {
			List<MemberCard> list = membercardDao.getListPage(page.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<MemberCard>());
		}
		return page;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aladingshop.card.service.MemberCardService#findByMemberId(java.lang
	 * .Long)
	 */
	public List<MemberCard> findByMemberId(Long id) {
		List<MemberCard> cardList = membercardDao.findByMemberId(id);
		for (MemberCard memberCard : cardList) {
			MemberCardConfig cardConfig = this.memberCardConfigService.findById(memberCard.getCardConfigId());
			memberCard.setCardName(MemberCardYiDuoBaoEnum.getTypeName(cardConfig==null?"":cardConfig.getType()));
			memberCard.setShowTime(DateFormat.dateTimeToDateString(memberCard.getCreateTime()));
		}
		return cardList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aladingshop.card.service.MemberCardService#findByMemberIdCardNo(com
	 * .aladingshop.card.model.MemberCard)
	 */
	public MemberCard findByMemberIdCardNo(MemberCard card) {
		return membercardDao.findByMemberIdCardNo(card);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aladingshop.card.service.MemberCardService#findTaskCardList()
	 */
	public List<MemberCard> findTaskCardList() {
		return membercardDao.findTaskCardList();
	}

	public MemberCard findByCardCode(String cardCode) {
		return membercardDao.findByCardCode(cardCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.aladingshop.card.service.MemberCardService#findAllByMemberId(java
	 * .lang.Long)
	 */
	public List<MemberCard> findAllByMemberId(Long id) {
		List<MemberCard> cardList = membercardDao.findAllByMemberId(id);
		for (MemberCard memberCard : cardList) {
			MemberCardConfig cardConfig = this.memberCardConfigService.findById(memberCard.getCardConfigId());
			memberCard.setCardName(MemberCardYiDuoBaoEnum.getTypeName(cardConfig.getType()));
			memberCard.setShowTime(DateFormat.dateToString(memberCard.getCreateTime()));
		}
		return cardList;
	}

	public void updateRebate(MemberCard memberCard) {
		this.membercardDao.updateRebate(memberCard);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.aladingshop.card.service.MemberCardService#findTaskExpire()
	 */
	public List<MemberCard> findTaskExpire() {
		return membercardDao.findTaskExpire();
	}
}
