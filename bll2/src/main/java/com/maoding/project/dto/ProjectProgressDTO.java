package com.maoding.project.dto;

/**
 * Created by sandy on 2017/9/8.
 */
public class ProjectProgressDTO {

    private String id;

    private String projectName;

    private int taskCount;

    private int completeCount;

    /**
     * 1:我参与的，2：我关注的
     */
    private int flag;

    /**
     * 立项方：1，乙方：2，合作组织：3
     */
    private int relationType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(int taskCount) {
        this.taskCount = taskCount;
    }

    public int getCompleteCount() {
        return completeCount;
    }

    public void setCompleteCount(int completeCount) {
        this.completeCount = completeCount;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getRelationType() {
        return relationType;
    }

    public void setRelationType(int relationType) {
        this.relationType = relationType;
    }
}
