package com.fushionbaby.cms.controller.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.CollectionUtils;
import com.aladingshop.alabao.vo.AlabaoBillDto;
import com.aladingshop.alabao.vo.AlabaoBillGroupDto;
import com.fushionbaby.common.util.date.DateFormat;

/***
 * @description 排序和分组账单信息记录
 * 此类实现了集合按某种规则排序和分组的算法 
 * @author 索亮
 * @date 2015年11月30日上午11:59:32
 */
@SuppressWarnings("rawtypes")
public class SortGroupBillUtil implements Comparator {
	
	/**
	 * 按照时间降序排序
	 * @param arg0
	 * @param arg1
	 * return
	 */
	public int compare(Object arg0,Object arg1){
		AlabaoBillDto alabaoBillDto1 = (AlabaoBillDto)arg0;
		AlabaoBillDto alabaoBillDto2 = (AlabaoBillDto)arg1;
		int flag = alabaoBillDto2.getCreateTime().compareTo(alabaoBillDto1.getCreateTime());
		return flag;
	}

	/** 
     * 分组 
     *  
     * @param list 
     *         <code>List<AlabaoBillDto></code>待分组的集合 
     * @return <code>List<AlabaoBillGroupDto></code>分组完成后的容器对象集合 
     */  
    public static List<AlabaoBillGroupDto> groupBill(List<AlabaoBillDto> list) {  
        List<AlabaoBillGroupDto> result = new ArrayList<AlabaoBillGroupDto>();  
        
        if (CollectionUtils.isEmpty(list)) {
			return result;
		}
        
        for (int i = 0; i <= list.size(); i++) {
        	AlabaoBillDto alabaoBillDto = list.get(0);
        	list.remove(alabaoBillDto);// 必须从这里移除，否则会丢失数据
        	AlabaoBillGroupDto billGroupDto = new AlabaoBillGroupDto();
        	billGroupDto.getAlabaoBillList().add(alabaoBillDto);
        	Calendar cal1 = Calendar.getInstance();
 	        cal1.setTime(DateFormat.stringToDAYDate(alabaoBillDto.getCreateTime()));
 	        int month1 = cal1.get(Calendar.MONTH) + 1; //注意月份是从0开始的,比如当前7月，获得的month为6
        	billGroupDto.setMonth(month1+"月");
        	for (int j = 0; j < list.size();) {
        		AlabaoBillDto _myObject = list.get(j);
        		Calendar cal2 = Calendar.getInstance();
     	        cal2.setTime(DateFormat.stringToDAYDate(_myObject.getCreateTime()));
     	        int month2 = cal2.get(Calendar.MONTH) + 1;//注意月份是从0开始的,比如当前7月，获得的month为6
        		// 相同，分组，并加入到组容器集合   -- 按月进行分组
        		if (ObjectUtils.equals(month1, month2)) {
        			billGroupDto.getAlabaoBillList().add(_myObject);
        			list.remove(_myObject);
        		} else {
        			j++;
        		}
        	}
        	result.add(billGroupDto);
		}
        return result;  
    } 
	
}
