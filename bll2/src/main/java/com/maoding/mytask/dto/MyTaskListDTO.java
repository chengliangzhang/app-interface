package com.maoding.mytask.dto;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：MyTaskDTO
 * 类描述：我的任务列表
 * 作    者：MaoSF
 * 日    期：2017年05月04日-下午3:10:45
 */
public class MyTaskListDTO {

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 项目名称（报销也用这个）
     */
    private String projectName;

    /**
     * 任务总数据量
     */
    private int totalNum;

    /**
     * 已完成的任务数量
     */
    private int completeNum;

    /**
     * 未完成的数量
     */
    private int unCompleteNum;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getCompleteNum() {
        return completeNum;
    }

    public void setCompleteNum(int completeNum) {
        this.completeNum = completeNum;
    }

    public int getUnCompleteNum() {
        unCompleteNum = totalNum - completeNum;
        return unCompleteNum;
    }

    public void setUnCompleteNum(int unCompleteNum) {
        this.unCompleteNum = unCompleteNum;
    }
}
