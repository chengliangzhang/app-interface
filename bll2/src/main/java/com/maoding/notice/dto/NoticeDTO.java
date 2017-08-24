package com.maoding.notice.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：NoticeEntity
 * 类描述：通知公告实体
 * 作    者：MaoSF
 * 日    期：2016年11月30日-下午3:10:45
 */
public class NoticeDTO extends BaseDTO {

    /**
     * 公告标题
     */
    private String noticeTitle;

    /**
     * 公告编码
     */
    private String noticeNo;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 组织id集合（需要发送的组织的id集合）
     */
    private List<String> orgList = new ArrayList<String>();

    /**
     * 公告状态
     */
    private String noticeStatus;

    /**
     *是否推送消息（0：不推送，1：推送）
     */
    private int isSendMsg;

    /**
     * 是否发布
     */
    private String noticeIsPush;

    /**
     * 发送的公司id
     */
    private String companyId;

    /**
     * 发送的公司名
     */
    private String companyName;

    /**
     * 发送的公司简称名
     */
    private String companyShortName;


    /**
     * 发布人
     */
    private String noticePublisher;

    /**
     * 发布人名
     */
    private String noticePublisherName;

    /**
     * 发布日期
     */
    private String noticePublishdate;

    /**
     * 发布日期全格式
     */
    private Date createDate;


    /**
     * 获取单条数据的时候，取的发送的组织信息
     */
    private List<NoticeOrgDTO> noticeOrgList = new ArrayList<NoticeOrgDTO>();

    /**发布范围*/
    private String noticeOrgNames;

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeNo() {
        return noticeNo;
    }

    public void setNoticeNo(String noticeNo) {
        this.noticeNo = noticeNo;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public List<String> getOrgList() {
        return orgList;
    }

    public void setOrgList(List<String> orgList) {
        this.orgList = orgList;
    }

    public String getNoticeStatus() {
        return noticeStatus;
    }

    public void setNoticeStatus(String noticeStatus) {
        this.noticeStatus = noticeStatus;
    }

    public int getIsSendMsg() {
        return isSendMsg;
    }

    public void setIsSendMsg(int isSendMsg) {
        this.isSendMsg = isSendMsg;
    }

    public String getNoticeIsPush() {
        return noticeIsPush;
    }

    public void setNoticeIsPush(String noticeIsPush) {
        this.noticeIsPush = noticeIsPush;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public String getNoticePublisher() {
        return noticePublisher;
    }

    public void setNoticePublisher(String noticePublisher) {
        this.noticePublisher = noticePublisher;
    }

    public String getNoticePublisherName() {
        return noticePublisherName;
    }

    public void setNoticePublisherName(String noticePublisherName) {
        this.noticePublisherName = noticePublisherName;
    }

    public String getNoticePublishdate() {
        return noticePublishdate;
    }

    public void setNoticePublishdate(String noticePublishdate) {
        this.noticePublishdate = noticePublishdate;
    }

    public List<NoticeOrgDTO> getNoticeOrgList() {
        return noticeOrgList;
    }

    public void setNoticeOrgList(List<NoticeOrgDTO> noticeOrgList) {
        this.noticeOrgList = noticeOrgList;
    }

    public String getNoticeOrgNames() {
        return noticeOrgNames;
    }

    public void setNoticeOrgNames(String noticeOrgNames) {
        this.noticeOrgNames = noticeOrgNames;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}