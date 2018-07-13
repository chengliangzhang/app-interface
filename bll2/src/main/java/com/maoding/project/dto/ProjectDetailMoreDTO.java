package com.maoding.project.dto;

import com.maoding.core.util.BeanUtilsEx;

import java.util.List;
import java.util.Map;

/**
 * 深圳市卯丁技术有限公司
 *
 * @author : 张成亮
 * @date : 2018/7/13
 * @description : 包含了更多信息的项目基本信息
 */
public class ProjectDetailMoreDTO extends ProjectDTO {
    /** 合同附件 */
    private List<Map<String, String>> contractAttachList;

    public List<Map<String, String>> getContractAttachList() {
        return contractAttachList;
    }

    public void setContractAttachList(List<Map<String, String>> contractAttachList) {
        this.contractAttachList = contractAttachList;
    }

    public ProjectDetailMoreDTO(){}
    public ProjectDetailMoreDTO(Object src){
        BeanUtilsEx.copyProperties(src,this);
    }
}
