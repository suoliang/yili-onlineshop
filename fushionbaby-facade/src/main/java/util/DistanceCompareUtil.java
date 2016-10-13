package util;

import java.util.Comparator;

import com.fushionbaby.common.constants.store.gaode.GaodeResDto;

@SuppressWarnings("rawtypes")
public class DistanceCompareUtil implements Comparator{

	public int compare(Object o1, Object o2) {
		GaodeResDto resDto1 = (GaodeResDto)o1;
		GaodeResDto resDto2 = (GaodeResDto)o2;
		int flag = resDto1.getDistance().compareTo(resDto2.getDistance());
		return flag;
		
	}

	

}
