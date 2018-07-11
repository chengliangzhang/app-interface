package com.maoding.project.dto;

import java.util.List;
import java.util.Map;

/**
 * 深圳市卯丁技术有限公司
 *
 * @author : 张成亮
 * @date : 2018/7/11
 * @description :
 */
public class ProjectContractInfoDTO {
    /** 项目合同附件 */
    private List<Map<String, String>> contractAttachList;
    /** 项目设计任务 */
    private List<ProjectDesignContentDTO> projectDesignContentList;

    public List<Map<String, String>> getContractAttachList() {
        return contractAttachList;
    }

    public void setContractAttachList(List<Map<String, String>> contractAttachList) {
        this.contractAttachList = contractAttachList;
    }

    public List<ProjectDesignContentDTO> getProjectDesignContentList() {
        return projectDesignContentList;
    }

    public void setProjectDesignContentList(List<ProjectDesignContentDTO> projectDesignContentList) {
        this.projectDesignContentList = projectDesignContentList;
    }
}
