package com.fushionbaby.cms.util.bean;

import java.util.List;

/**
 * 检测结果封装类
 * 
 * @author suntao
 *
 */
public class BatchUploadValidResult {
	/** 校验通过的文件全路径集合 */
	private List<String> sucResults;

	/** 异常文件全路径集合 */
	private List<UploadResult> faiResults;

	public BatchUploadValidResult(List<String> sucResults,
			List<UploadResult> faiResults) {
		this.sucResults = sucResults;
		this.faiResults = faiResults;
	}

	public List<String> getSucResults() {
		return sucResults;
	}

	public void setSucResults(List<String> sucResults) {
		this.sucResults = sucResults;
	}

	public List<UploadResult> getFaiResults() {
		return faiResults;
	}

	public void setFaiResults(List<UploadResult> faiResults) {
		this.faiResults = faiResults;
	}

}
