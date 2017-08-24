package com.maoding.project.entity;

import com.maoding.core.base.entity.BaseEntity;

import java.math.BigDecimal;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectFeeEntity
 * 类描述：项目合同节点
 * 作    者：MaoSF
 * 日    期：2015年8月15日-上午10:13:28
 */
public class ProjectFeeEntity extends BaseEntity {

	/**
	 * 项目id
	 */
    private String projectId;

    /**
     * 节点id
     */
    private String pointId;

    /**
     * 类型（1合同回款开票,2合同回款收款,3合作设计费付款，4合作设计费到款，5技术审查费付款，6技术审查费到款）
     */
    private String type;

    /**
     * 金额
     */
    private BigDecimal fee;

    /**
     * 发票号
     */
    private String invoiceNumber;

    /**
     * 日期
     */
    private String feeDate;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态：0：生效，1：不生效（删除）
     */
    private String status;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId == null ? null : pointId.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber == null ? null : invoiceNumber.trim();
    }

    public String getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(String feeDate) {
        this.feeDate = feeDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}