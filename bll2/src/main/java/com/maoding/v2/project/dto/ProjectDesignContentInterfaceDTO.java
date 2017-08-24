package com.maoding.v2.project.dto;

/**
 * 方法描述：项目立项-设计阶段，
 * 作者：MaoSF
 * 日期：2016/12/7
 * @param:
 * @return:
 */
public class ProjectDesignContentInterfaceDTO {

    /**
     * 设计阶段的id（数据字段的id）
     */
    private String id;

    /**
     * 设计阶段名称（数据字段的name)
     */
    private String desingContentName;

    /**
     * 合同进度开始时间
     */
    private String contractProgressStarttime;
    /**
     * 合同进度结束时间
     */
    private String contractProgressEndtime;

    public String getContractProgressStarttime() {
        return contractProgressStarttime;
    }

    public void setContractProgressStarttime(String contractProgressStarttime) {
        this.contractProgressStarttime = contractProgressStarttime;
    }

    public String getContractProgressEndtime() {
        return contractProgressEndtime;
    }

    public void setContractProgressEndtime(String contractProgressEndtime) {
        this.contractProgressEndtime = contractProgressEndtime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesingContentName() {
        return desingContentName;
    }

    public void setDesingContentName(String desingContentName) {
        this.desingContentName = desingContentName;
    }


}