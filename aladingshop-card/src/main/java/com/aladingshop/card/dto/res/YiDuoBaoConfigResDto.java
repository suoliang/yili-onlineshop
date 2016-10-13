/**
 * 
 */
package com.aladingshop.card.dto.res;

import java.math.BigDecimal;

/**
 * @description 益多宝app响应dto
 * @author 孙涛
 * @date 2015年9月21日上午11:31:52
 */
public class YiDuoBaoConfigResDto {
	/** 卡配置ID */
	private String id;
	/** 卡配置类型[1.季卡 2.半年卡 3.年卡] */
	private String type;
	/** 当前配置最低起充金额 */
	private BigDecimal lowestMoney;
	/** 卡图片路径 */
	private String imgUrl;
	/** 卡的图文详情 */
	private String detailImgUrl;
	/** 卡图文详情高度 */
	private String detailImgHeight;
	/** 类型名称 */
	private String typeName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getLowestMoney() {
		return lowestMoney;
	}

	public void setLowestMoney(BigDecimal lowestMoney) {
		this.lowestMoney = lowestMoney;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDetailImgUrl() {
		return detailImgUrl;
	}

	public void setDetailImgUrl(String detailImgUrl) {
		this.detailImgUrl = detailImgUrl;
	}

	public String getDetailImgHeight() {
		return detailImgHeight;
	}

	public void setDetailImgHeight(String detailImgHeight) {
		this.detailImgHeight = detailImgHeight;
	}

}
