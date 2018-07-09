package com.maoding.financial.dto;

import java.math.BigDecimal;

/**
 * Created by sandy on 2017/9/8.
 */
public class ExpAmountDTO {

    /**
     * 未审批金额
     */
    private BigDecimal notApprovalAmount;

    /**
     * 本月已审批金额
     */
    private BigDecimal approvalAmount;

    /**
     * 需要我审批的条目
     */
    private int auditCount;

    public BigDecimal getNotApprovalAmount() {
        return notApprovalAmount;
    }

    public void setNotApprovalAmount(BigDecimal notApprovalAmount) {
        this.notApprovalAmount = notApprovalAmount;
    }

    public BigDecimal getApprovalAmount() {
        return approvalAmount;
    }

    public void setApprovalAmount(BigDecimal approvalAmount) {
        this.approvalAmount = approvalAmount;
    }

    public int getAuditCount() {
        return auditCount;
    }

    public void setAuditCount(int auditCount) {
        this.auditCount = auditCount;
    }
}
