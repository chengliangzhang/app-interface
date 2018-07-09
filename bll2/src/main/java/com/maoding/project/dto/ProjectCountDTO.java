package com.maoding.project.dto;

/**
 * Created by sandy on 2017/9/8.
 */
public class ProjectCountDTO {

    /**
     * 我参与的项目总数
     */
    private int myProjectCount;

    /**
     * 我关注的项目总数
     */
    private int attentionCount;

    public int getMyProjectCount() {
        return myProjectCount;
    }

    public void setMyProjectCount(int myProjectCount) {
        this.myProjectCount = myProjectCount;
    }

    public int getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(int attentionCount) {
        this.attentionCount = attentionCount;
    }
}
