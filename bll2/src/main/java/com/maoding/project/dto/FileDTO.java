package com.maoding.project.dto;

import com.maoding.core.base.dto.BaseDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：FileDTO
 * 类描述：甲方DTO
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午5:42:50
 */
public class FileDTO extends BaseDTO {

    /**
     * 名称
     */
    private String fileName;

    private String filePath;

    private String fastdfsUrl;

    private String pathUrl;

    private Map<String,String> param = new HashMap<String,String>();

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFastdfsUrl() {
        return fastdfsUrl;
    }

    public void setFastdfsUrl(String fastdfsUrl) {
        this.fastdfsUrl = fastdfsUrl;
    }

    public String getPathUrl() {
        return pathUrl;
    }

    public void setPathUrl(String pathUrl) {
        this.pathUrl = pathUrl;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }
}
