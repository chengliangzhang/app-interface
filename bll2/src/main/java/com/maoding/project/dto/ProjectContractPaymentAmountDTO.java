package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.util.StringUtil;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectContractPaymentAmountDTO
 * 类描述：项目统计(合同回款统计）
 * 作    者：MaoSF
 */
public class ProjectContractPaymentAmountDTO extends BaseDTO {

    private String projectName;

    private String signDate;
    /**
     * 合同总额
     */
    private double totalContractAmount;
    /**
     *
     */
    private double receivableMoney;
    /**
     * 已开票
     */
    private double invoiceMoney;

    /**
     * 已收款
     */
    private double payedMoney;

    /**
     * 未收
     */
    private double notReceive;

    /**
     * 应收未收
     */
    private double accruedMoney;

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

    public double getTotalContractAmount() {
        return totalContractAmount;
    }

    public void setTotalContractAmount(double totalContractAmount) {
        this.totalContractAmount = totalContractAmount;
    }

    public double getReceivableMoney() {
        return receivableMoney;
    }

    public void setReceivableMoney(double receivableMoney) {
        this.receivableMoney = receivableMoney;
    }

    public double getInvoiceMoney() {
        return invoiceMoney;
    }

    public void setInvoiceMoney(double invoiceMoney) {
        this.invoiceMoney = invoiceMoney;
    }

    public double getPayedMoney() {
        return payedMoney;
    }

    public void setPayedMoney(double payedMoney) {
        this.payedMoney = payedMoney;
    }

    /**
     * 此处用合同总金额-已收=未收
     * @return
     */
    public double getNotReceive() {
        if(!StringUtil.isNullOrEmpty(this.totalContractAmount)){
            if(!StringUtil.isNullOrEmpty(this.payedMoney))
            {
                return  this.totalContractAmount - this.payedMoney;
            }
            return this.totalContractAmount - 0;
        }

        return notReceive;
    }

    public void setNotReceive(double notReceive) {
        this.notReceive = notReceive;
    }

    public double getAccruedMoney() {
        return accruedMoney;
    }

    public void setAccruedMoney(double accruedMoney) {
        this.accruedMoney = accruedMoney;
    }
}
