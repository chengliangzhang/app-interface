package com.maoding.schedule.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScheduleGroupDataDTO {

    private String date;

    private List<ScheduleDTO> scheduleList = new ArrayList<>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<ScheduleDTO> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<ScheduleDTO> scheduleList) {
        this.scheduleList = scheduleList;
    }
}
