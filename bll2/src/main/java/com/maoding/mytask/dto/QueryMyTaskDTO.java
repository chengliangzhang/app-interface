package com.maoding.mytask.dto;

import com.maoding.core.base.dto.QueryDTO;

import java.util.Date;

/**
 * 深圳市卯丁技术有限公司
 * 作    者 : 张成亮
 * 日    期 : 2018/4/18 11:49
 * 描    述 :
 */
public class QueryMyTaskDTO extends QueryDTO{
    private String companyUserId;
    private String taskType;
    private String deleted;
    private String createBy;
    private Date minCompleteDate;

    public Date getMinCompleteDate() {
        return minCompleteDate;
    }

    public void setMinCompleteDate(Date minCompleteDate) {
        this.minCompleteDate = minCompleteDate;
    }

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
