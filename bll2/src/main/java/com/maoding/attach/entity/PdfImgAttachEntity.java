package com.maoding.attach.entity;

import com.maoding.core.base.entity.BaseEntity;


public class PdfImgAttachEntity extends BaseEntity{

    private String attachId;

    private String orginalFile;

    private String fileName;

    private String fileType;

    private String filePath;

    private int seq;

    public String getAttachId() {
        return attachId;
    }

    public void setAttachId(String attachId) {
        this.attachId = attachId == null ? null : attachId.trim();
    }

    public String getOrginalFile() {
        return orginalFile;
    }

    public void setOrginalFile(String orginalFile) {
        this.orginalFile = orginalFile == null ? null : orginalFile.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}