package com.maoding.task.dto;

/**
 * 深圳市卯丁技术有限公司
 * 作    者 : 张成亮
 * 日    期 : 2018/4/18 18:49
 * 描    述 :
 */
public class TaskSimpleDTO {
    /** 任务id */
    private String id;
    /** 任务名称 */
    private String taskName;
    /** 开始时间 */
    private String startTime;
    /** 结束时间 */
    private String endTime;
    /** 项目名称 */
    private String projectName;
    /** 时间字符串 */
    private String timeText;

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getProjectName() {
        return (projectName != null) ? projectName : "未填写";
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTimeText() {
        String s = timeText;
        if (s == null) {
            if (startTime != null) {
                s = startTime.replace("-","/");
            } else {
                s = "";
            }
            if (startTime != null || endTime != null){
                s += " — ";
            }
            if (endTime != null) {
                s += endTime.replace("-", "/");
            }
        }
        return s;
    }

    public void setTimeText(String timeText) {
        this.timeText = timeText;
    }
}
