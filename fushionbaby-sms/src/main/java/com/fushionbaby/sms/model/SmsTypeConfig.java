package com.fushionbaby.sms.model;
/**
 * 
 * @author King
 *
 */
public class SmsTypeConfig {
    private Long id;

    private String smsTemplate;

    private String smsName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSmsTemplate() {
        return smsTemplate;
    }

    public void setSmsTemplate(String smsTemplate) {
        this.smsTemplate = smsTemplate == null ? null : smsTemplate.trim();
    }

    public String getSmsName() {
        return smsName;
    }

    public void setSmsName(String smsName) {
        this.smsName = smsName == null ? null : smsName.trim();
    }

	@Override
	public String toString() {
		return "SmsType [id=" + id + ", smsTemplate=" + smsTemplate
				+ ", smsName=" + smsName + "]";
	}
}