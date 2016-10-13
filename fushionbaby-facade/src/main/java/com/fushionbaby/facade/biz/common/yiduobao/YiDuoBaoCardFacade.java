/**
 * 
 */
package com.fushionbaby.facade.biz.common.yiduobao;

import java.util.List;

import com.aladingshop.card.dto.req.YiDuoBaoCardBackRepDto;
import com.aladingshop.card.dto.res.YiDuoBaoCardBackResDto;
import com.aladingshop.card.dto.res.YiDuoBaoCardResDto;
import com.aladingshop.card.dto.res.YiDuoBaoConfigResDto;
import com.fushionbaby.common.dto.alabao.AlabaoUserDto;

/**
 * @description 益多宝门面接口
 * @author 孙涛
 * @date 2015年9月21日上午9:25:22
 */
public interface YiDuoBaoCardFacade {
	/** 益多宝购卡生成订单 
	 * @param memberId */
	public Object createOrder(String configId, String faceValue,AlabaoUserDto alabaoUserDto, String sourceCode);

	/** 获取当前用户可用益多宝卡列表 */
	public List<YiDuoBaoCardResDto> getCardListByMember(String sid);

	/** 创建益多宝卡 */
	//public MemberCard createMemberCard(Long memberId, String orderCode);

	/** 获取所有的卡配置 */
	public List<YiDuoBaoConfigResDto> getAllConfig();

	/** 益多宝退卡初始化 */
	public YiDuoBaoCardBackResDto backCardInit(String cardNo, Long memberId);

	/** 益多宝退卡提交 
	 * @param reDto */
	public Object backCardCommit(String alabaoSid, YiDuoBaoCardBackRepDto reDto);

	/***
	 * 删除会员的阿拉丁卡
	 * @param cardNo
	 * @param memberId
	 * @return
	 */
	public Object delCard(String cardNo, Long memberId);
}
