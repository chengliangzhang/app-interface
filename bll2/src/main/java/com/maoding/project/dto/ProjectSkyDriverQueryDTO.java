package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

/**
 * 深圳市卯丁技术有限公司
 *
 * @author : 张成亮
 * @date : 2018/7/19
 * @description :
 */
public class ProjectSkyDriverQueryDTO extends BaseDTO {
    /** 相关交付任务编号 */
    private String deliverId;
    /** 直接父目录节点编号 */
    private String pid;
    /** 间接父目录节点编号 */
    private String parentId;
    /** 要查询的节点是否文件 */
    private String isFile;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIsFile() {
        return isFile;
    }

    public void setIsFile(String isFile) {
        this.isFile = isFile;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }
}
