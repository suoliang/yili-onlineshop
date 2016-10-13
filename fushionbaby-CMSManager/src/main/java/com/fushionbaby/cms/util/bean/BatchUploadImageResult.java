package com.fushionbaby.cms.util.bean;

import java.util.List;

/**
 * 批量上传图片结果封装类
 * 
 * @author sun tao 2015年7月8日
 */
public class BatchUploadImageResult {
	public BatchUploadImageResult() {
	}

	public BatchUploadImageResult(Integer count, Integer sucCount,
			Integer falCount, List<UploadResult> falResults) {
		this.count = count;
		this.successCount = sucCount;
		this.failureCount = falCount;
		this.failureResults = falResults;
	}

	public BatchUploadImageResult(String error) {
		this.otherError = error;
	}

	/** 总条数 */
	private Integer count;
	/** 成功数 */
	private Integer successCount;
	/** 失败数 */
	private Integer failureCount;
	/** 失败集合 */
	private List<UploadResult> failureResults;
	/** 其他失败原因 **/
	private String otherError;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	public Integer getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(Integer failureCount) {
		this.failureCount = failureCount;
	}

	public List<UploadResult> getFailureResults() {
		return failureResults;
	}

	public void setFailureResults(List<UploadResult> failureResults) {
		this.failureResults = failureResults;
	}

	public String getOtherError() {
		return otherError;
	}

	public void setOtherError(String otherError) {
		this.otherError = otherError;
	}

}
