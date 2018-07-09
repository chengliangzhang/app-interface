package com.maoding.task.dto;

public class CountDTO {
    /**
     * 总数
     */
    private int totalCount;

    /**
     * 正在进行中(我的任务中，只要没有完成的，都属于正在进行的）
     */
    private int progressCount;

    /**
     * 已完成的
     */
    private int completeCount;


    /**
     * 已超时
     */
    private int overtimeCount;

    /**
     * 即将到期的任务数
     */
    private int dueTimeCount;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getProgressCount() {
        return progressCount;
    }

    public void setProgressCount(int progressCount) {
        this.progressCount = progressCount;
    }

    public int getCompleteCount() {
        return completeCount;
    }

    public void setCompleteCount(int completeCount) {
        this.completeCount = completeCount;
    }

    public int getOvertimeCount() {
        return overtimeCount;
    }

    public void setOvertimeCount(int overtimeCount) {
        this.overtimeCount = overtimeCount;
    }

    public int getDueTimeCount() {
        return dueTimeCount;
    }

    public void setDueTimeCount(int dueTimeCount) {
        this.dueTimeCount = dueTimeCount;
    }
}
