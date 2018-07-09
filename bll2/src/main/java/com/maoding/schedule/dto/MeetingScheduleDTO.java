package com.maoding.schedule.dto;

import java.util.ArrayList;
import java.util.List;

public class MeetingScheduleDTO {

    private Integer type;

    private Integer total;

    private List<ScheduleMemberDTO> memberList = new ArrayList<>();

    public MeetingScheduleDTO(Integer type){
        this.type = type;
        total = 0;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<ScheduleMemberDTO> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<ScheduleMemberDTO> memberList) {
        this.memberList = memberList;
    }
}
