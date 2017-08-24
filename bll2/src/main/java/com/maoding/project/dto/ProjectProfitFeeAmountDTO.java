package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectProfitFeeAmountDTO
 * 类描述：项目收支总览DTO
 * 作    者：MaoSF
 * 日    期：2016年10月10日-下午5:50:14
 */
public class ProjectProfitFeeAmountDTO extends BaseDTO {


    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 项目所在公司id
     */
    private String companyId;
    /**
     * 合同总金额
     */
    private String totalContractAmount;
    /**
     * 合同回款已收
     */
    private String contractAmount;
    /**
     * 合作设计费
     */
    private String designFee;
    /**
     * 合作设计费收支
     */
    private String profitDesignFee;
    /**
     * 技术审查费
     */
    private String manageFee;
    /**
     * 技术审查费收支
     */
    private String profitManageFee;

    /**
     * 合计
     */
    private String amount;

    /**
     * 为了app显示项目创建时间
     */
    private String createDate;

    public String getTotalContractAmount() {
        return totalContractAmount;
    }

    public void setTotalContractAmount(String totalContractAmount) {
        this.totalContractAmount = totalContractAmount;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getDesignFee() {
        return designFee;
    }

    public void setDesignFee(String designFee) {
        this.designFee = designFee;
    }

    public String getProfitDesignFee() {
        return profitDesignFee;
    }

    public void setProfitDesignFee(String profitDesignFee) {
        this.profitDesignFee = profitDesignFee;
    }

    public String getManageFee() {
        return manageFee;
    }

    public void setManageFee(String manageFee) {
        this.manageFee = manageFee;
    }

    public String getProfitManageFee() {
        return profitManageFee;
    }

    public void setProfitManageFee(String profitManageFee) {
        this.profitManageFee = profitManageFee;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
