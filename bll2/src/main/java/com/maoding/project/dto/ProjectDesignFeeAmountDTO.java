package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.util.StringUtil;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskDTO
 * 类描述：合作设计费统计总金额DTO
 * 作    者：MaoSF
 * 日    期：2015年8月15日-上午10:13:28
 */
public class ProjectDesignFeeAmountDTO extends BaseDTO {

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
    private double designFee;

    /**
     * 应收或应付的钱
     */
    private double receivableMoney;

    /**
     * 已付或已收
     */
    private double paidDesignFee;

    /**
     * 未付或未收
     */
    private double designNotPayFee;

    /**
     * 应收未收
     */
    private double accruedMoney;

    public double getDesignFee() {
        return designFee;
    }

    public void setDesignFee(double designFee) {
        this.designFee = designFee;
    }

    public double getPaidDesignFee() {
        return paidDesignFee;
    }

    public void setPaidDesignFee(double paidDesignFee) {
        this.paidDesignFee = paidDesignFee;
    }

    public double getDesignNotPayFee() {
        if(!StringUtil.isNullOrEmpty(this.designFee)){
            designNotPayFee = this.designFee - 0;
            if(!StringUtil.isNullOrEmpty(this.paidDesignFee))
            {
                designNotPayFee = this.designFee - this.paidDesignFee;
            }
        }

        return designNotPayFee;
    }

    public void setDesignNotPayFee(double designNotPayFee) {
        this.designNotPayFee = designNotPayFee;
    }



    public double getAccruedMoney() {
        if(!StringUtil.isNullOrEmpty(this.receivableMoney)){
            if(!StringUtil.isNullOrEmpty(this.paidDesignFee))
            {
                return  this.receivableMoney - this.paidDesignFee;
            }
            return this.receivableMoney - 0;
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