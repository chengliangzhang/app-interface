package com.maoding.project.entity;

import com.maoding.core.base.entity.BaseEntity;
/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectSkyDriveEntity
 * 类描述：项目网络磁盘实体
 * 作    者：MaoSF
 * 日    期：2016年12月18日-上午10:13:28
 */
public class ProjectSkyDriveEntity extends BaseEntity {


    /**
     * 项目id
     */
    private String projectId;

    /**
     * 父目录id
     */
    private String pid;

    /**
     * 文件/文件夹的文件路径id-id
     */
    private String skyDrivePath;

    /**
     * 文件/文件夹名
     */
    private String fileName;

    /**
     * 若是文件，文件在fastdfs中的路径
     */
    private String filePath;

    /**
     * 文件所属公司
     */
    private String companyId;

    /**
     *类型：文件目录：0，文件：1
     */
    private  int type;

    /**
     *文件大小
     */
    private long fileSize;

    /**
     *上传的分组
     */
    private String fileGroup;

    /**
     *0，自定义，1，默认 文件、文件夹
     */
    private int isCustomize;

    /**
     *预留字段3
     */
    private String fileExtName;

    /**
     *预留字段4(排序字段)
     */
    private int param4;

    /**
     * 关联任务的id
     */
    private String taskId;

    /**
     * 状态（0：有效，1：无效（删除））
     */
    private String status;

    /**
     * 目标记录的id
     */
    private String targetId;

    /**
     * oss 中的 bucket
     */
    private String bucket;

    /**
     * oss中的地址（只有bucket为公有访问的时候，该地址才有效
     */
    private String ossFilePath;

    /**
     * oos 中对应对象的key值
     */
    private String objectKey;

    private String fromCompanyId;

    public String getFileGroup() {
        return fileGroup;
    }

    public void setFileGroup(String fileGroup) {
        this.fileGroup = fileGroup;
    }



    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getSkyDrivePath() {
        return skyDrivePath;
    }

    public void setSkyDrivePath(String skyDrivePath) {
        this.skyDrivePath = skyDrivePath == null ? null : skyDrivePath.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public int getParam4() {
        return param4;
    }

    public void setParam4(int param4) {
        this.param4 = param4;
    }

    public int getIsCustomize() {
        return isCustomize;
    }

    public void setIsCustomize(int isCustomize) {
        this.isCustomize = isCustomize;
    }

    public String getFileExtName() {
        return fileExtName;
    }

    public void setFileExtName(String fileExtName) {
        this.fileExtName = fileExtName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getOssFilePath() {
        return ossFilePath;
    }

    public void setOssFilePath(String ossFilePath) {
        this.ossFilePath = ossFilePath;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getFromCompanyId() {
        return fromCompanyId;
    }

    public void setFromCompanyId(String fromCompanyId) {
        this.fromCompanyId = fromCompanyId;
    }
}