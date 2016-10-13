package com.fushionbaby.sms.model;

import java.util.Date;
/**
 * 
 * @author King suoliang
 *
 */
public class Sms {
	/**短信类型对象*/
	private SmsTypeConfig smsTypeConfig;
	
    private Long id;

	private String memberName;
    
    private String mobile;

    private Long smsTypeId;

    private String smsContent;

    private Date planTime;

	private Date sendTime;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Long createId;

    private Long updateId;

    private Integer sendLevel;

    private String sourceCode;

    private String isDeleted;

    private String failureReason;

    /**短信服务商的标志  1、表示 亿美 2、表示美联软通*/
    private Integer smsSendFlag;



	public SmsTypeConfig getSmsTypeConfig() {
		return smsTypeConfig;
	}

	public void setSmsTypeConfig(SmsTypeConfig smsTypeConfig) {
		this.smsTypeConfig = smsTypeConfig;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Long getSmsTypeId() {
        return smsTypeId;
    }

    public void setSmsTypeId(Long smsTypeId) {
        this.smsTypeId = smsTypeId;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent == null ? null : smsContent.trim();
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Long getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Long updateId) {
        this.updateId = updateId;
    }

    public Integer getSendLevel() {
        return sendLevel;
    }

    public void setSendLevel(Integer sendLevel) {
        this.sendLevel = sendLevel;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason == null ? null : failureReason.trim();
    }

    public Integer getSmsSendFlag() {
        return smsSendFlag;
    }

    public void setSmsSendFlag(Integer smsSendFlag) {
        this.smsSendFlag = smsSendFlag;
    }

	@Override
	public String toString() {
		return "Sms [id=" + id + ", memberName="
				+ memberName + ", mobile=" + mobile + ", smsTypeId="
				+ smsTypeId + ", smsContent=" + smsContent + ", planTime="
				+ planTime + ", sendTime=" + sendTime + ", status=" + status
				+ ", createTime=" + createTime + ", updateTime=" + updateTime
				+ ", createId=" + createId + ", updateId=" + updateId
				+ ", sendLevel=" + sendLevel + ", sourceCode=" + sourceCode
				+ ", isDeleted=" + isDeleted + ", failureReason="
				+ failureReason + ", smsSendFlag=" + smsSendFlag + "]";
	}
    
}