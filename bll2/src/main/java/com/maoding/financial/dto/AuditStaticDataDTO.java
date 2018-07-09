package com.maoding.financial.dto;

public class AuditStaticDataDTO {

    /**
     * 待审核的数目
     */
    private int auditCount;

    /**
     * 报销
     */
    private AuditStaticDTO expStatic = new AuditStaticDTO("报销金额");

    /**
     * 费用申请
     */
    private AuditStaticDTO costApplyStatic = new AuditStaticDTO("费用金额");

    /**
     * 请假
     */
    private AuditStaticDTO leaveStatic = new AuditStaticDTO("请假情况");

    /**
     * 出差
     */
    private AuditStaticDTO  businessTripStatic = new AuditStaticDTO("出差情况");

    public int getAuditCount() {
        return auditCount;
    }

    public void setAuditCount(int auditCount) {
        this.auditCount = auditCount;
    }

    public AuditStaticDTO getExpStatic() {
        return expStatic;
    }

    public void setExpStatic(AuditStaticDTO expStatic) {
        this.expStatic = expStatic;
    }

    public AuditStaticDTO getCostApplyStatic() {
        return costApplyStatic;
    }

    public void setCostApplyStatic(AuditStaticDTO costApplyStatic) {
        this.costApplyStatic = costApplyStatic;
    }

    public AuditStaticDTO getLeaveStatic() {
        return leaveStatic;
    }

    public void setLeaveStatic(AuditStaticDTO leaveStatic) {
        this.leaveStatic = leaveStatic;
    }

    public AuditStaticDTO getBusinessTripStatic() {
        return businessTripStatic;
    }

    public void setBusinessTripStatic(AuditStaticDTO businessTripStatic) {
        this.businessTripStatic = businessTripStatic;
    }
}
