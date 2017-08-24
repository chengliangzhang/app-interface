package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.math.BigDecimal;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectFeeDTO
 * 类描述：项目合同节点DTO
 * 作    者：MaoSF
 * 日    期：2015年8月15日-上午10:13:28
 */
public class ProjectFeeDTO extends BaseDTO {

	/**
	 * 项目id
	 */
    private String projectId;

    /**
     * 节点id
     */
    private String pointId;

    /**
     * 节点名称
     */
    private String pointName;

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

    /********projectPoint中的数据********/
    /**
     * 应收金额
     */
    private BigDecimal receivableMoney;

    /**
     * 已付金额
     */
    private BigDecimal payedMoney;

    /**
     * 开票金额
     */
    private BigDecimal invoiceMoney;

    /**
     * 设计阶段的id（新增，修改，合作设计费，技术审查费）
     */
    private String designContentId;

    private BigDecimal manageFee;

    private BigDecimal managePaidFee;

    /**
     * 管理费付款
     */
    private BigDecimal managePayFee;


    private BigDecimal contractDesignFee;

    private BigDecimal contractDesignPaidFee;

    /**
     * 设计费到款
     */
    private BigDecimal contractDesignPayFee;

    private String fromCompany;

    private String toCompany;

    /**
     * 签发次数
     */
    private String taskLevel;

    /**
     * 合同回款创建者
     */
    private String createName;

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

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public BigDecimal getReceivableMoney() {
        return receivableMoney;
    }

    public void setReceivableMoney(BigDecimal receivableMoney) {
        this.receivableMoney = receivableMoney;
    }

    public BigDecimal getPayedMoney() {
        return payedMoney;
    }

    public void setPayedMoney(BigDecimal payedMoney) {
        this.payedMoney = payedMoney;
    }

    public BigDecimal getInvoiceMoney() {
        return invoiceMoney;
    }

    public void setInvoiceMoney(BigDecimal invoiceMoney) {
        this.invoiceMoney = invoiceMoney;
    }

    public String getDesignContentId() {
        return designContentId;
    }

    public void setDesignContentId(String designContentId) {
        this.designContentId = designContentId;
    }

    public BigDecimal getManageFee() {
        return manageFee;
    }

    public void setManageFee(BigDecimal manageFee) {
        this.manageFee = manageFee;
    }

    public BigDecimal getManagePaidFee() {
        return managePaidFee;
    }

    public void setManagePaidFee(BigDecimal managePaidFee) {
        this.managePaidFee = managePaidFee;
    }

    public BigDecimal getContractDesignFee() {
        return contractDesignFee;
    }

    public void setContractDesignFee(BigDecimal contractDesignFee) {
        this.contractDesignFee = contractDesignFee;
    }

    public BigDecimal getContractDesignPaidFee() {
        return contractDesignPaidFee;
    }

    public void setContractDesignPaidFee(BigDecimal contractDesignPaidFee) {
        this.contractDesignPaidFee = contractDesignPaidFee;
    }

    public BigDecimal getManagePayFee() {
        return managePayFee;
    }

    public void setManagePayFee(BigDecimal managePayFee) {
        this.managePayFee = managePayFee;
    }

    public BigDecimal getContractDesignPayFee() {
        return contractDesignPayFee;
    }

    public void setContractDesignPayFee(BigDecimal contractDesignPayFee) {
        this.contractDesignPayFee = contractDesignPayFee;
    }

    public String getFromCompany() {
        return fromCompany;
    }

    public void setFromCompany(String fromCompany) {
        this.fromCompany = fromCompany;
    }

    public String getToCompany() {
        return toCompany;
    }

    public void setToCompany(String toCompany) {
        this.toCompany = toCompany;
    }

    public String getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(String taskLevel) {
        this.taskLevel = taskLevel;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }
}