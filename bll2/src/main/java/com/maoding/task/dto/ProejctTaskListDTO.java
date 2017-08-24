package com.maoding.task.dto;

/**
 * Created by Idccapp21 on 2016/12/30.
 */
public class ProejctTaskListDTO {

    /**
     * 任务id
     */
    private String id;

    private String taskName;

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
