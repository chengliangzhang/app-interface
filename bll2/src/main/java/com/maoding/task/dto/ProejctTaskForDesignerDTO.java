package com.maoding.task.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Idccapp21 on 2016/12/30.
 */
public class ProejctTaskForDesignerDTO {

    /**
     * 项目名称
     */
    private String rootId;


    private int seq;


    private List<ProejctTaskForDesignerListDTO> childTaskList=new ArrayList<ProejctTaskForDesignerListDTO>();


    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public List<ProejctTaskForDesignerListDTO> getChildTaskList() {
        return childTaskList;
    }

    public void setChildTaskList(List<ProejctTaskForDesignerListDTO> childTaskList) {
        this.childTaskList = childTaskList;
    }
}
