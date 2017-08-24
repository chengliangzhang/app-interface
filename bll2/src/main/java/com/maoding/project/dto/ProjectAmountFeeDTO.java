package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.util.StringUtil;

import java.math.BigDecimal;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskDTO
 * 类描述：签发DTO
 * 作    者：MaoSF
 * 日    期：2015年8月15日-上午10:13:28
 */
public class ProjectAmountFeeDTO extends BaseDTO {


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
}