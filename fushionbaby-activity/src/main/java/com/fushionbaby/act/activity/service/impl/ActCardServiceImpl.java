package com.fushionbaby.act.activity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.fushionbaby.act.activity.dao.ActCardDao;
import com.fushionbaby.act.activity.dao.ActCardTypeDao;
import com.fushionbaby.act.activity.model.ActCard;
import com.fushionbaby.act.activity.model.ActCardType;
import com.fushionbaby.act.activity.service.ActCardService;
import com.fushionbaby.common.constants.PromotionConstant;
import com.fushionbaby.common.dto.CouponCardDto;
import com.fushionbaby.common.enums.MemberCouponTypeEnum;
import com.fushionbaby.common.util.BasePagination;

/**
 * 
 * @author cyla
 * 
 */

@Service
public class ActCardServiceImpl implements ActCardService<ActCard> {
	@Autowired
	private ActCardDao actCardDao;
	
	@Autowired
	private ActCardTypeDao actCardTypeDao;

	public void add(ActCard sysmgrActCard) {
		actCardDao.add(sysmgrActCard);
	}

	public void deleteById(Long id) {
		actCardDao.deleteById(id);
	}

	public void update(ActCard sysmgrActCard) {
		actCardDao.update(sysmgrActCard);
	}

	public ActCard findById(Long id) throws DataAccessException {
		return this.actCardDao.findById(id);
	}
	//通过代金券卡号获得代金券对象
	public ActCard findActCardByCardNo(String cardNo) {
		return actCardDao.findActCardByCardNo(cardNo);
	}
	
	public List<ActCard> findAll() throws DataAccessException {
		return this.actCardDao.findAll();
	}


	public CouponCardDto getAmountByCardNo(String cardNo ) {
		CouponCardDto couponCardDto = new CouponCardDto();
		couponCardDto.setCardType("0");
		couponCardDto.setConditionSkuPriceAbove("0");//设置满多少钱才可用
		//ActCard actCart = this.findByCardNoAndPassword(cardNo);
		ActCard actCart = this.actCardDao.findActCardByCardNo(cardNo);
		if(ObjectUtils.equals(actCart, null)){
			return couponCardDto;
		}
		ActCardType cardType = this.actCardTypeDao.findById(actCart.getCardTypeId());
		if(ObjectUtils.equals(cardType, null)){
			return couponCardDto;
		}
		/**加入和会员相关的代金券使用  begin*/
		if(MemberCouponTypeEnum.IsMemberCoupon(cardNo)){
			Date time=new Date();
			if(actCart.getStartTime().after(time) || actCart.getStopTime().before(time) || actCart.getUsedTime()!=null){
				return couponCardDto;
			}
			couponCardDto.setCardType(cardType.getCardType());
			couponCardDto.setCodeList(cardType.getIdList());
			couponCardDto.setConditionSkuPriceAbove(cardType.getConditionSkuPriceAbove());
			couponCardDto.setDiscountMoney(cardType.getDiscountMoney());
			return couponCardDto;
		}else{
		/**加入和会员相关的代金券使用  end*/
//		if(!getCardTypeIsCanUseByTime(cardType)||CommonConstant.YES.equals(cardType.getDisable())){
//			return couponCardDto;
//		}
		/** 如果该卡是无限次使用的卡 则直接返回该卡对应的优惠金额 */
		if (PromotionConstant.CARD_USE_TYPE_UNLIMIT.equals(actCart.getUseType())) {
			couponCardDto = cardIsAvailable(couponCardDto, cardType);
		} else if (PromotionConstant.CARD_USE_TYPE_ONCE.equals(actCart.getUseType())) {
			couponCardDto=getMoneyWhenCardTypeIsOnce(actCart.getUseCount(),cardType,couponCardDto);
		} else {
			couponCardDto=getMoneyWhenCardTypeIsMore(actCart.getUseCount(),cardType,couponCardDto);
		}
		return couponCardDto;
		}
	}

	/***
	 * 判断该优惠券 所在的优惠券类型 可用
	 * @param cardType
	 * @return
	 */
//	private boolean getCardTypeIsCanUseByTime(ActCardType cardType) {
//		boolean result=true;
//		if(cardType.getBeginTime().getTime()>new Date().getTime()||new Date().getTime() >cardType.getEndTime().getTime())
//			result=false;
//		return result;
//	}

	/***
	 * 得到卡的优惠金额，该卡是一次使用的卡，
	 * @param useCount 使用次数
	 * @param cardType  卡的类型对象
	 * @param cardVo
	 * @return
	 */
	
	private CouponCardDto getMoneyWhenCardTypeIsOnce(int useCount,ActCardType cardType, CouponCardDto cardVo) {
		if (useCount < 1) {
			cardVo = cardIsAvailable(cardVo, cardType);
		} else {
			cardVo.setCardType("0");
		}
		return cardVo;
	}

	/***
	 * 得到卡的优惠金额，该卡是多次使用的卡，
	 * @param useCount 使用次数
	 * @param cardType  卡的类型对象
	 * @param cardVo  
	 * @return
	 */
	private CouponCardDto getMoneyWhenCardTypeIsMore(int useCount,	ActCardType cardType, CouponCardDto cardVo) {
		int all_use_count = cardType.getUseCountLimit();
		if (useCount < all_use_count) {
			cardVo = cardIsAvailable(cardVo, cardType);
		}
		return cardVo;
	}

	/***
	 * 如果该卡可使用，走下面这个方法
	 * @param cardVo
	 * @param cardType
	 */
	private CouponCardDto cardIsAvailable(CouponCardDto couponCardDto,ActCardType cardType){
		couponCardDto.setCardType(cardType.getCardType());
		couponCardDto.setDiscountMoney(cardType.getDiscountMoney());
		couponCardDto.setCodeList(cardType.getIdList());
		couponCardDto.setConditionSkuPriceAbove(cardType.getConditionSkuPriceAbove());
		return couponCardDto;
	}
	
	
	
	public BasePagination getListPage(BasePagination page) {

		Integer total = this.actCardDao.getTotal(page.getSearchParamsMap());

		page.setCurrentTotal(total);

		if (total > 0) {
			List<ActCard> list = this.actCardDao.getListPage(page
					.getSearchParamsMap());
			page.setResult(list);
		} else {
			page.setResult(new ArrayList<ActCard>());
		}
		return page;
	}

	/***
	 * 根据优惠卡号来更新卡信息(加运算)，在使用过优惠卡之后，创建订单的时候使用
	 * 
	 * @param cardNo
	 */
	public void updateByCardNo(String cardNo) {
		ActCard card = this.actCardDao.findActCardByCardNo(cardNo);
		Date useTime = card.getUsedTime();
		card.setUsedTime(new Date());
		ActCardType cardType = this.actCardTypeDao.findById(card.getCardTypeId());
		int count_limit = cardType.getUseCountLimit();
		if (PromotionConstant.CARD_USE_TYPE_ONCE.equals(card.getUseType())) {
			card.setUseCount(1);
		}
		else if (PromotionConstant.CARD_USE_TYPE_OTHER.equals(card.getUseType())) {
			card=updateCardWhenCardTypeIsMore(card,count_limit,useTime);
		}
		else {
			card.setUseCount(card.getUseCount() + 1);
		}
		this.actCardDao.update(card);
	}
	
	/***
	 * 通过卡号，更新卡的信息（使用情况），该卡的类型为 多次使用的卡
	 * @param card
	 * @param count_limit
	 * @param useTime
	 * @return
	 */
	private ActCard updateCardWhenCardTypeIsMore(ActCard card, int count_limit,
			Date useTime) {
		if (card.getUseCount() < count_limit) {
		    card.setUseCount(card.getUseCount() + 1);
		} else {
			card.setUseCount(count_limit);
			card.setUsedTime(useTime);
		}
		return card;
	}

	/***
	 * 系统定时任务更新卡信息(减运算)
	 * 使用时间不做减运算，有可能其他用户使用了(多次)
	 * @param cardno
	 */
	public void updateBySysByCardNo(String cardno) {
		ActCard actCard = actCardDao.findActCardByCardNo(cardno);
		if (actCard != null && actCard.getUseCount() > 0) {
			//使用次数减1
			int useCount = actCard.getUseCount() - 1;
			actCard.setUseCount(useCount);
		}
		actCardDao.update(actCard);
	}

	public void deleteByTypeId(Long id) {
		this.actCardDao.deleteByTypeId(id);
	}

	public List<ActCard> findByMemberId(Long memberId) {
		return this.actCardDao.findByMemberId(memberId);
	}

	public ActCard findByCardNoAndPassword(String cardNo, String password) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("cardNo", cardNo==null?"":cardNo);
		map.put("password", password==null?"":password);
		return this.actCardDao.findByCardNoAndPassword(map);
	}
	
}
