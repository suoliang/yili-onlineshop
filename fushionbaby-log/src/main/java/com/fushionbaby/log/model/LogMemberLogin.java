package com.fushionbaby.log.model;

import java.util.Date;
/**
 * 
 * @author King suoliang
 *
 */
public class LogMemberLogin {
    private Long id;

    private String ipAddress;

    private Long memberId;

    private String memberName;

    private Date loginTime;

    private String loginStatus;//登录状态 y成功  n失败

    public Long getId() {
        return id;
    }

	public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }
    
    @Override
	public String toString() {
		return "LogMemberLogin [id=" + id + ", ipAddress=" + ipAddress + ", memberId=" + memberId
				+ ", memberName=" + memberName + ", loginTime=" + loginTime + ", loginStatus="
				+ loginStatus + "]";
	}
}