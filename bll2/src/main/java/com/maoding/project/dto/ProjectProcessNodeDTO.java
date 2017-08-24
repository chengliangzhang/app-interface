package com.maoding.project.dto;
import com.maoding.core.base.dto.BaseDTO;


/**
 * Created by Wuwq on 2016/10/27.
 */
public class ProjectProcessNodeDTO extends BaseDTO {

    private String processId;

    /**
     *节点名
     */
    private String nodeName;

    /**
     * 序号
     */
    private int seq;

    /**
     * 节点在流程中所排的顺序
     */
    private int nodeSeq;

    /**
     *company_user_id
     */
    private String companyUserId;


    private int status;//2:未完成，1：完成



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

}
