package com.maoding.activiti.dto;


import com.maoding.core.base.dto.CoreQueryDTO;

import java.util.Date;

/**
 * @author  张成亮
 * @date    2018/7/27
 * @description     执行任务查询条件
 **/
public class WorkTaskQueryDTO extends CoreQueryDTO {
    /** id: 工作流执行任务编号 */

    /** 执行人员的编号 */
    private String userId;
    /** 签发任务编号 */
    private String issueId;
    /** 生产任务编号 */
    private String taskId;
    /** 最小起始时间 */
    private Date startMinTime;
    /** 最大终止时间 */
    private Date endMaxTime;

    /**
     * 启动流程的业务表的id
     */
    private String businessKey;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIssueId() {
        return issueId;
    }

    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Date getStartMinTime() {
        return startMinTime;
    }

    public void setStartMinTime(Date startMinTime) {
        this.startMinTime = startMinTime;
    }

    public Date getEndMaxTime() {
        return endMaxTime;
    }

    public void setEndMaxTime(Date endMaxTime) {
        this.endMaxTime = endMaxTime;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }
}
