package com.fushionbaby.cms.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 张明亮
 * t_order_line
 * 销售订单行，行中记录有当时销售的单价和数量情况，如果和某个促销匹配，还会记录对应的促销信息。
 */
public class OrderLineDto {
    private Long id;

    private Date createTime;

    private String lineType;

    private String remark;

    private String size;

    private String color;

    private String skuCode;

    private BigDecimal costPrice;

    private String skuName;

    private BigDecimal totalActual;

    private BigDecimal totalPrice;

    private Integer quantity;

    private BigDecimal skuRetailPrice;

    private BigDecimal unitPrice;

    private Date version;

    private String orderCode;
    
    private Long memberId;
    
    private String isComment;
    
    private Date updateTime; 

    private String skuNo;
    
    private String barCode;
    
    private String createTimeFrom;
	
	private String createTimeTo;
	
	public String getCreateTimeFrom() {
		return createTimeFrom;
	}

	public void setCreateTimeFrom(String createTimeFrom) {
		this.createTimeFrom = createTimeFrom;
	}

	public String getCreateTimeTo() {
		return createTimeTo;
	}

	public void setCreateTimeTo(String createTimeTo) {
		this.createTimeTo = createTimeTo;
	}

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

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode == null ? null : skuCode.trim();
    }
    
    public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName == null ? null : skuName.trim();
    }

    public BigDecimal getTotalActual() {
        return totalActual;
    }

    public void setTotalActual(BigDecimal totalActual) {
        this.totalActual = totalActual;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getSkuRetailPrice() {
        return skuRetailPrice;
    }

    public void setSkuRetailPrice(BigDecimal skuRetailPrice) {
        this.skuRetailPrice = skuRetailPrice;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }


	public String getIsComment() {
		return isComment;
	}

	public void setIsComment(String isComment) {
		this.isComment = isComment;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getSkuNo() {
		return skuNo;
	}

	public void setSkuNo(String skuNo) {
		this.skuNo = skuNo;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	
}