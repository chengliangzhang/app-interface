package com.maoding.project.dto;
/**
 * Created by Wuwq on 2016/10/27.
 */
public class ProjectProcessNodeDataDTO{

    private String id;

    private String processId;

    /**
     *节点名
     */
    private String nodeName;

    private int seq;

    /**
     * 节点在流程中所排的顺序
     */
    private int nodeSeq;

    /**
     *company_user_id
     */
    private String companyUserId;

    /**
     * 节点人员账户id
     */
    private String userId;

    /**
     *姓名
     */
    private String userName;


    private int status;//2:未完成，1：完成


    /**
     *电话号码
     */
    private String cellphone;

    /**
     * 邮箱
     */
    private String email;

    /**
     *头像地址
     */
    private String imgUrl;

    private String completeTime;//完成时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(String companyUserId) {
        this.companyUserId = companyUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getNodeSeq() {
        return nodeSeq;
    }

    public void setNodeSeq(int nodeSeq) {
        this.nodeSeq = nodeSeq;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
