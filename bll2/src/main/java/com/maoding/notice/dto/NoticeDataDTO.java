package com.maoding.notice.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Idccapp22 on 2017/3/10.
 */
public class NoticeDataDTO {

    private String dateTime;
    private Date createDate;

    List<NoticeDTO> noticeList = new ArrayList<>();

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public List<NoticeDTO> getNoticeList() {
        return noticeList;
    }

    public void setNoticeList(List<NoticeDTO> noticeList) {
        this.noticeList = noticeList;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }


}
