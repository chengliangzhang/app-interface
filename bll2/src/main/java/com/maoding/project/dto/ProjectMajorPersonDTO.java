package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectMajorPersonDTO
 * 类描述：添加专业技术人
 * 作    者：MaoSF
 * 日    期：2016年7月19日-下午4:04:50
 */
public class ProjectMajorPersonDTO extends BaseDTO {

    private String majorId;//专业id

    private String majorName;//专业名称


    List<ProjectTaskResponsibleDTO> majorPersonList = new ArrayList<ProjectTaskResponsibleDTO>();

    public String getMajorId() {
        return majorId;
    }

    public void setMajorId(String majorId) {
        this.majorId = majorId;
    }

    public List<ProjectTaskResponsibleDTO> getMajorPersonList() {
        return majorPersonList;
    }

    public void setMajorPersonList(List<ProjectTaskResponsibleDTO> majorPersonList) {
        this.majorPersonList = majorPersonList;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }
}
