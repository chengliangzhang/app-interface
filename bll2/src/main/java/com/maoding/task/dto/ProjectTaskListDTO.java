package com.maoding.task.dto;

public class ProjectTaskListDTO {

    private String id;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 状态(1:正常进行，2：超时进行，3：正常完成，4.超时完成,5:未开始，6，剩余多少天，10,无状态)
     */
    private int taskState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskState() {
        return taskState;
    }

    public void setTaskState(int taskState) {
        this.taskState = taskState;
    }
}