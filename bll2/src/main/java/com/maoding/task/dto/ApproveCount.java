package com.maoding.task.dto;

public class ApproveCount {

    /**
     * 报销
     */
    private int expCount;

    /**
     * 费用
     */
    private int costCount;

    /**
     * 请假
     */
    private int leaveCount;

    /**
     * 出差
     */
    private int businessTripCount;

    /**
     * 工时
     */
    private int laborHourCount;

    public int getExpCount() {
        return expCount;
    }

    public void setExpCount(int expCount) {
        this.expCount = expCount;
    }

    public int getCostCount() {
        return costCount;
    }

    public void setCostCount(int costCount) {
        this.costCount = costCount;
    }

    public int getLeaveCount() {
        return leaveCount;
    }

    public void setLeaveCount(int leaveCount) {
        this.leaveCount = leaveCount;
    }

    public int getBusinessTripCount() {
        return businessTripCount;
    }

    public void setBusinessTripCount(int businessTripCount) {
        this.businessTripCount = businessTripCount;
    }

    public int getLaborHourCount() {
        return laborHourCount;
    }

    public void setLaborHourCount(int laborHourCount) {
        this.laborHourCount = laborHourCount;
    }
}
