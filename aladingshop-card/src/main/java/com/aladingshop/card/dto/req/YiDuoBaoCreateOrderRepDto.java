/**
 * 
 */
package com.aladingshop.card.dto.req;

import com.aladingshop.card.dto.BaseReqDto;

/**
 * @description 益多宝购卡下订单请求dto
 * @author 孙涛
 * @date 2015年9月21日下午1:42:21
 */
public class YiDuoBaoCreateOrderRepDto extends BaseReqDto {
	/** 卡配置ID */
	private String configId;
	/** 充值金额 */
	private String faceValue;

	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getFaceValue() {
		return faceValue;
	}

	public void setFaceValue(String faceValue) {
		this.faceValue = faceValue;
	}
}
