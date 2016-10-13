package com.aladingshop.alabao.vo;

import java.util.ArrayList;
import java.util.List;

/***
 * @description 分组后的容器,一个此对象代表一组数据 
 * @author 索亮
 * @date 2015年11月30日上午11:16:59
 */
public class AlabaoBillGroupDto {
	/**哪个月的记录--本月，9月，8月...*/
	private String month;
	/**哪个月的记录集合--和month对应*/
	private List<AlabaoBillDto> alabaoBillList = new ArrayList<AlabaoBillDto>();

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public List<AlabaoBillDto> getAlabaoBillList() {
		return alabaoBillList;
	}

	public void setAlabaoBillList(List<AlabaoBillDto> alabaoBillList) {
		this.alabaoBillList = alabaoBillList;
	}

}
