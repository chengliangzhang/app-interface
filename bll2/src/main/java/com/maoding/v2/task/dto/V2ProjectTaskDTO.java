package com.maoding.v2.task.dto;

import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskDTO
 * 类描述：任务数据
 * 作    者：MaoSF
 * 日    期：2016年12月31日-上午10:13:28
 */
public class V2ProjectTaskDTO {

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 项目Id
     */
    private String projectId;

    /**
     * 签发次数级别
     */
    private int taskLevel;

    /**
     * 任务状态 0生效，1不生效
     */
    private String taskStatus;

    /**
     * 备注
     */
    private String taskRemark;
    /**
     * 排序
     */
    private int seq;
    /**
     * 任务完整路径id-id
     */
    private String taskPath;

    /**
     * 父任务的层级
     */
    private int parentLevel;

    private String parentTaskPath;

    /**
     * 设计人
     */
    private String designer;

    /**
     * 根节点下所有的任务关系
     */
    private  int relationCount;

    /**
     * 发包方
     */
    private String toCompanyName;

    /**
     * 接包方
     */
    private String fromCompanyName;

    /**
     * 发包方
     */
    private String toCompany;

    /**
     * 接包方
     */
    private String fromCompany;


    /**
     * 任务id
     */
    private String id;

    /**
     *任务所属公司id
     */
    private String companyId;

    /**
     * 任务名称
     */
    private String taskName;

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

    private int flag;//是否是签发给公司为0：自己做，否，1，则是

    /**
     *状态字符串
     */
    private String stateHtml;

    private String orgId;
    private String departName;

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
        int actualStatus = taskState;
        if(!(actualStatus==1|| actualStatus==2 || actualStatus==7 || actualStatus==8 || actualStatus==9) && !StringUtil.isNullOrEmpty(endTime)){
            int overDate = DateUtils.daysOfTwo(endTime, DateUtils.date2Str(DateUtils.date_sdf));

            if(overDate<0){//超时
                stateHtml+="超时"+Math.abs(overDate)+"天";
            }else {
                stateHtml+="剩余"+(overDate+1)+"天";
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public int getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(int taskLevel) {
        this.taskLevel = taskLevel;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskRemark() {
        return taskRemark;
    }

    public void setTaskRemark(String taskRemark) {
        this.taskRemark = taskRemark;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getTaskPath() {
        return taskPath;
    }

    public void setTaskPath(String taskPath) {
        this.taskPath = taskPath;
    }

    public int getParentLevel() {
        return parentLevel;
    }

    public void setParentLevel(int parentLevel) {
        this.parentLevel = parentLevel;
    }

    public String getParentTaskPath() {
        return parentTaskPath;
    }

    public void setParentTaskPath(String parentTaskPath) {
        this.parentTaskPath = parentTaskPath;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public int getRelationCount() {
        return relationCount;
    }

    public void setRelationCount(int relationCount) {
        this.relationCount = relationCount;
    }

    public String getToCompanyName() {
        return toCompanyName;
    }

    public void setToCompanyName(String toCompanyName) {
        this.toCompanyName = toCompanyName;
    }

    public String getFromCompanyName() {
        return fromCompanyName;
    }

    public void setFromCompanyName(String fromCompanyName) {
        this.fromCompanyName = fromCompanyName;
    }

    public String getToCompany() {
        return toCompany;
    }

    public void setToCompany(String toCompany) {
        this.toCompany = toCompany;
    }

    public String getFromCompany() {
        return fromCompany;
    }

    public void setFromCompany(String fromCompany) {
        this.fromCompany = fromCompany;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }
}