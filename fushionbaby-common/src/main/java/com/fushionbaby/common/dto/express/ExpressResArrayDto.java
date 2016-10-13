package com.fushionbaby.common.dto.express;

/**
 * 快递100返回数组Dto
 * 
 * @author suntao
 *
 */
public class ExpressResArrayDto {
	/** 每条跟踪信息的时间 */
	private String time;
	/** 每条跟综信息的描述 */
	private String context;
	/**备用字段*/
	private String ftime;

	public String getFtime() {
		return ftime;
	}

	public void setFtime(String ftime) {
		this.ftime = ftime;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Override
	public String toString() {
		return "ExpressResArrayDto [time=" + time + ", context=" + context
				+ ", ftime=" + ftime + "]";
	}
	
}
