package com.fushionbaby.member.model;

import java.util.Date;
/**
 * 
 * @author King
 *
 */
public class MemberTelephone {
    private Long id;

    private Long memberId;

    private String telephone;

    private Date createTime;

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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
	public String toString() {
		return "MaMemberTelephone [id=" + id + ", memberId=" + memberId
				+ ", telephone=" + telephone + ", createTime=" + createTime
				+ "]";
	}
}