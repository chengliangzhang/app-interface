package com.maoding.task.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Idccapp21 on 2016/12/30.
 */
public class ProjectTaskForDesignerDTO {

    /**
     * 项目名称
     */
    private String rootId;


    private int seq;


    private List<ProjectTaskForDesignerListDTO> childTaskList=new ArrayList<ProjectTaskForDesignerListDTO>();


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

    public List<ProjectTaskForDesignerListDTO> getChildTaskList() {
        return childTaskList;
    }

    public void setChildTaskList(List<ProjectTaskForDesignerListDTO> childTaskList) {
        this.childTaskList = childTaskList;
    }
}
