package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectPointChangeSeqDTO
 * 类描述：合同回款节点更换序号DTO
 * 作    者：MaoSF
 * 日    期：2015年8月14日-下午3:50:14
 */
public class ProjectPointChangeSeqDTO extends BaseDTO {


    /**
     * 更换id1
     */
    private String id1;
    private int seq1;


    /**
     * 更换id2
     */
    private String id2;
    private int seq2;

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public int getSeq1() {
        return seq1;
    }

    public void setSeq1(int seq1) {
        this.seq1 = seq1;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public int getSeq2() {
        return seq2;
    }

    public void setSeq2(int seq2) {
        this.seq2 = seq2;
    }
}