package com.maoding.task.dto;

import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskDTO
 * 类描述：任务数据
 * 作    者：MaoSF
 * 日    期：2016年12月31日-上午10:13:28
 */
public class ProjectTask2DTO {


    /**
     * 任务id
     */
    private String id;

    /**
     *项目id
     */
    private String projectId;


    /**
     *任务所属公司id
     */
    private String companyId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 备注
     */
    private String taskRemark;

    /**
     * 任务父Id
     */
    private String taskPid;

    /**
     * 状态（）
     */
    private int taskState;

    /**
     *任务类型
     */
    private int taskType;

    /**
     *开始时间
     */
    private String startTime;

    /**
     *结束时间
     */
    private String endTime;


    /**
     *负责人（可以是人，可以是公司）
     */
    private String manager;

    /**
     * 是否是签发给公司为 0：自己做，1:签发给其他公司
     */
    private int flag;

    /**
     *状态字符串
     */
    private String stateHtml;

    /**
     * 层级
     */
    private int taskLevel;

    private int relationStatus;

    /**
     *完成时间
     */
    private String completeTime;
    private List<ProjectTask2DTO> children = new ArrayList<ProjectTask2DTO>();

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

    public String getTaskPid() {
        return taskPid;
    }

    public void setTaskPid(String taskPid) {
        this.taskPid = taskPid;
    }

    public int getTaskState() {
        return taskState;
    }

    public void setTaskState(int taskState) {
        this.taskState = taskState;
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

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getStateHtml() {
        stateHtml = "";
        if(StringUtil.isNullOrEmpty(this.completeTime)){
            if(!StringUtil.isNullOrEmpty(endTime)){
                int overDate = DateUtils.daysOfTwo(endTime, DateUtils.date2Str(DateUtils.date_sdf));
                if(overDate<0){//超时
                    stateHtml+="超时"+Math.abs(overDate)+"天";
                }else {
                    stateHtml+="剩余"+(overDate+1)+"天";
                }
            }
        }else {
            if(!StringUtil.isNullOrEmpty(this.startTime)){
                int overDate = DateUtils.daysOfTwo(startTime, this.completeTime)+1;
                stateHtml+="用时"+(overDate+1)+"天";
            }
        }

        return stateHtml;
    }

    public void setStateHtml(String stateHtml) {
        this.stateHtml = stateHtml;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public int getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(int taskLevel) {
        this.taskLevel = taskLevel;
    }

    public List<ProjectTask2DTO> getChildren() {
        return children;
    }

    public void setChildren(List<ProjectTask2DTO> children) {
        this.children = children;
    }

    public int getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(int relationStatus) {
        this.relationStatus = relationStatus;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getTaskRemark() {
        return taskRemark;
    }

    public void setTaskRemark(String taskRemark) {
        this.taskRemark = taskRemark;
    }
}