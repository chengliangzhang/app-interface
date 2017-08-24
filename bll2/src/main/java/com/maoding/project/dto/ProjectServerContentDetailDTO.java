package com.maoding.project.dto;


import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;

import java.util.Date;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectServerContentDetailEntity
 * 类描述：服务内容变更计划时间详情
 * 作    者：MaoSF
 * 日    期：2016年9月6日-上午11:40:31
 */
public class ProjectServerContentDetailDTO extends BaseDTO{

    private String serverContentId;

    private String planProgressStartTime;

    private String planProgressEndTime;

    /**
     * 备忘录
     */
    private String memo;

    /**
     * 一共多少天
     */
    private int allDay;
    public String getServerContentId() {
        return serverContentId;
    }

    public void setServerContentId(String serverContentId) {
        this.serverContentId = serverContentId == null ? null : serverContentId.trim();
    }

    public String getPlanProgressStartTime() {
        return planProgressStartTime;
    }

    public void setPlanProgressStartTime(String planProgressStartTime) {
        this.planProgressStartTime = planProgressStartTime;
    }

    public String getPlanProgressEndTime() {
        return planProgressEndTime;
    }

    public void setPlanProgressEndTime(String planProgressEndTime) {
        this.planProgressEndTime = planProgressEndTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getAllDay() {
        String startTime = this.planProgressStartTime;
        String endTime = this.planProgressEndTime;

        if(StringUtil.isNullOrEmpty(startTime) || StringUtil.isNullOrEmpty(endTime)){
            allDay=0;
        }else {
            Date startDate = DateUtils.str2Date(startTime,DateUtils.date_sdf);
            Date endDate = DateUtils.str2Date(endTime,DateUtils.date_sdf);
            allDay=DateUtils.daysOfTwo(endDate,startDate)+1;
        }
        return allDay;
    }

    public void setAllDay(int allDay) {
        this.allDay = allDay;
    }
}