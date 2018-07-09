package com.maoding.schedule.dto;

import com.maoding.core.util.StringUtil;

import java.util.Date;

public class ScheduleAndLaborDTO {

    private String id;

    /**
     * 会议标题
     */
    private String title;

    /**
     * 会议、日程 内容，工时的项目名称
     */
    private String content;

    /**
     * 会议、日程 开始时间
     */
    private Date startTime;

    /**
     * 会议、日程 结束时间
     */
    private Date endTime;

    /**
     * 1=日程，2=会议,3=工时
     */
    private Integer scheduleType;

    /**
     * 工时里面的小时
     */
    private String laborHours;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
//        if(scheduleType==2){
//            if(!StringUtil.isNullOrEmpty(title)){ //会议展示的标题
//                content = title;
//            }
//        }
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }


    public Integer getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(Integer scheduleType) {
        this.scheduleType = scheduleType;
    }

    public String getLaborHours() {
        return laborHours;
    }

    public void setLaborHours(String laborHours) {
        this.laborHours = laborHours;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
