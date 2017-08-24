package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.util.StringUtil;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskDTO
 * 类描述：技术审查费统计总金额DTO
 * 作    者：MaoSF
 * 日    期：2015年8月15日-上午10:13:28
 */
public class ProjectManageFeeAmountDTO extends BaseDTO {

    /**
     * 项目名称
     */
    private String projectName;

    private String totalContractAmount;

    /**
     * 签订时间
     */
    private String signDate;

    /**
     * 到款
     */
    private double paidFee;

    /**
     * 总费用
     */
    private double manageFee;

    /**
     * 应收或应付的钱
     */
    private double receivableMoney;

    /**
     * 已付或已收
     */
    private double managePaidFee;

    /**
     * 未付或未收
     */
    private double manageNotPayFee;

    /**
     * 应收未收
     */
    private double accruedMoney;

    public double getManageFee() {
        return manageFee;
    }

    public void setManageFee(double manageFee) {
        this.manageFee = manageFee;
    }

    public double getManagePaidFee() {
        return managePaidFee;
    }

    public void setManagePaidFee(double managePaidFee) {
        this.managePaidFee = managePaidFee;
    }

    public double getManageNotPayFee() {
        if(!StringUtil.isNullOrEmpty(this.manageFee)){
            manageNotPayFee = this.manageFee - 0;
            if(!StringUtil.isNullOrEmpty(this.managePaidFee))
            {
                manageNotPayFee = this.manageFee - this.managePaidFee;
            }

        }

        return manageNotPayFee;
    }

    public void setManageNotPayFee(double manageNotPayFee) {
        this.manageNotPayFee = manageNotPayFee;
    }

    public double getAccruedMoney() {
        if(!StringUtil.isNullOrEmpty(this.receivableMoney)){
            accruedMoney = this.receivableMoney - 0;
            if(!StringUtil.isNullOrEmpty(this.managePaidFee))
            {
                accruedMoney =  this.receivableMoney - this.managePaidFee;
            }
        }
        return accruedMoney;
    }

    public void setAccruedMoney(double accruedMoney) {
        this.accruedMoney = accruedMoney;
    }

    public double getReceivableMoney() {
        return receivableMoney;
    }

    public void setReceivableMoney(double receivableMoney) {
        this.receivableMoney = receivableMoney;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public double getPaidFee() {
        return paidFee;
    }

    public void setPaidFee(double paidFee) {
        this.paidFee = paidFee;
    }

    public String getTotalContractAmount() {
        return totalContractAmount;
    }

    public void setTotalContractAmount(String totalContractAmount) {
        this.totalContractAmount = totalContractAmount;
    }
}