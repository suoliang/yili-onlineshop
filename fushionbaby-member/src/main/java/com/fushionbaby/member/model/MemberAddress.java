package com.fushionbaby.member.model;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 
 * @author King
 *
 */
public class MemberAddress {
	
    private Long id;

    private String abbreviation;

    private String address;

    private String city;

    private String contactor;

    private String country;

    private Date createTime;

    private String district;

    private Integer gender;

    private String mobile;

    private String province;

    private String telephone;

    private Date version;

    private String zipcode;

    private Long memberId;

    private String email;
    
    private String showTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation == null ? null : abbreviation.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getContactor() {
        return contactor;
    }

    public void setContactor(String contactor) {
        this.contactor = contactor == null ? null : contactor.trim();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode == null ? null : zipcode.trim();
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
    

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}
	public String getShowTime() {
		if(createTime != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			setShowTime(sdf.format(createTime));
		}
		return showTime;
	}
	@Override
	public String toString() {
		return "MaMemberAddress [id=" + id + ", abbreviation=" + abbreviation
				+ ", address=" + address + ", city=" + city + ", contactor="
				+ contactor + ", country=" + country + ", createTime="
				+ createTime + ", district=" + district + ", gender=" + gender
				+ ", mobile=" + mobile + ", province=" + province
				+ ", telephone=" + telephone + ", version=" + version
				+ ", zipcode=" + zipcode + ", memberId=" + memberId
				+ ", email=" + email + ",showTime=" + showTime + "]";
	}
}