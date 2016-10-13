package com.fushionbaby.member.model;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 会员意见反馈
 * @author King suoliang
 *
 */
public class MemberFeedbackInfo {
    private Long id;
    /** 会员id*/
    private Long memberId;
    /** 反馈内容*/
    private String content;
    /** 来源编码*/
    private String sourceCode;
    /** 联系方式*/
    private String contactInformation;
    /** 添加时间*/
    private Date addTime;
    
    private String showDate;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getContactInformation() {
		return contactInformation;
	}

	public void setContactInformation(String contactInformation) {
		this.contactInformation = contactInformation;
	}

	public String getShowDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.showDate = sdf.format(addTime);
		return this.showDate;
	}
}