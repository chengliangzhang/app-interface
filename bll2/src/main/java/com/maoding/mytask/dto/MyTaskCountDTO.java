package com.maoding.mytask.dto;

/**
 * Created by sandy on 2017/9/8.
 */
public class MyTaskCountDTO {

    /**
     * 任务总数
     */
    private int totalCount;

    /**
     * 已完成数
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

    /**
     * 未完成数
     */
    private int notCompleteCount;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
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

    public int getNotCompleteCount() {
        notCompleteCount = this.totalCount - this.completeCount;
        return notCompleteCount;
    }

    public void setNotCompleteCount(int notCompleteCount) {
        this.notCompleteCount = notCompleteCount;
    }
}
