package com.fushionbaby.member.model;
/**
 * 
 * @author King
 *
 */
public class MemberAreaConfig {
    private Long id;

    private String city;

    private String areaCode;

    private String parentAreaCode;

    private String telephoneCode;

    private Integer areaLevel;

    private String areaTypeName;

	private String isDisable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getParentAreaCode() {
        return parentAreaCode;
    }

    public void setParentAreaCode(String parentAreaCode) {
        this.parentAreaCode = parentAreaCode == null ? null : parentAreaCode.trim();
    }

    public String getTelephoneCode() {
        return telephoneCode;
    }

    public void setTelephoneCode(String telephoneCode) {
        this.telephoneCode = telephoneCode == null ? null : telephoneCode.trim();
    }

    public Integer getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(Integer areaLevel) {
        this.areaLevel = areaLevel;
    }

    public String getAreaTypeName() {
        return areaTypeName;
    }

    public void setAreaTypeName(String areaTypeName) {
        this.areaTypeName = areaTypeName == null ? null : areaTypeName.trim();
    }

	public String getIsDisable() {
		return isDisable;
	}

	public void setIsDisable(String isDisable) {
		this.isDisable = isDisable;
	}
}