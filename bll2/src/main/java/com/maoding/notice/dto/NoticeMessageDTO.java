package com.maoding.notice.dto;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.constant.SystemParameters;

import java.util.List;

/**
 * Created by Administrator on 2017/3/28.
 */
public class NoticeMessageDTO extends BaseDTO {
    /**
     * 公告标题
     */
    String noticeTitle;
    /**
     * 公告类型，目前只有"notice"
     */
    String messageType;
    /**
     * 接收者列表，元素为用户ID
     */
    List<String> receiverList;

    public String getNoticeTitle() {
        return (noticeTitle != null) ? noticeTitle : "无标题";
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getMessageType() {
        return (messageType != null) ? messageType : SystemParameters.NOTICE_TYPE;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public List<String> getReceiverList() {
        return receiverList;
    }

    public void setReceiverList(List<String> receiverList) {
        this.receiverList = receiverList;
    }
}
