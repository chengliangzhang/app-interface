package com.maoding.attach.dto;

import com.maoding.core.util.StringUtil;

/**
 * 文件路径，如果是fastdfs,fastdfsUrl为fastdfs服务器的地址
 */
public class FilePathDTO {

    private String fileFullPath;

    private String crtFilePath;//缩略图路径

    public String getFileFullPath() {
        if(!StringUtil.isNullOrEmpty(this.fileFullPath)){
            if(!this.fileFullPath.contains(FastdfsUrlServer.fastdfsUrl)){
                fileFullPath = FastdfsUrlServer.fastdfsUrl+this.fileFullPath;
            }
        }
        return fileFullPath;
    }

    public void setFileFullPath(String fileFullPath) {
        this.fileFullPath = fileFullPath;
    }

    public String getCrtFilePath() {
        if(!StringUtil.isNullOrEmpty(this.crtFilePath)){
            if(!this.crtFilePath.contains(FastdfsUrlServer.fastdfsUrl)){
                crtFilePath = FastdfsUrlServer.fastdfsUrl+this.crtFilePath;
            }
        }
        return crtFilePath;
    }

    public void setCrtFilePath(String crtPath) {
        this.crtFilePath = crtPath;
    }
}
