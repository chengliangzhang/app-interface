package com.maoding.projectmember.dto;

public class UserPositionDTO {

    private Integer memberType;

    private String title;

    private String content;

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public String getTitle() {
        switch (memberType){
            case 0:
                return "立项人";
            case 1:
                return "经营负责人";
            case 2:
                return "设计负责人";
            case 3:
                return "任务负责人";
            case 4:
                return "设计人";
            case 5:
                return "校对人";
            case 6:
                return "审核人";
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}