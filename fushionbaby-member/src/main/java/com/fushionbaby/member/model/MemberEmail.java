package com.fushionbaby.member.model;

import java.util.Date;

public class MemberEmail {
    private Long id;

    private Date createTime;

    private Long memberId;

    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

	@Override
	public String toString() {
		return "MaMemberEmail [id=" + id + ", createTime=" + createTime
				+ ", memberId=" + memberId + ", email=" + email + "]";
	}
}