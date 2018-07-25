package com.maoding.v2.project.dto;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectSkyDriveListDTO
 * 类描述：项目网络磁盘实体
 * 作    者：MaoSF
 * 日    期：2016年12月18日-上午10:13:28
 */
public class ProjectSkyDriveListDTO{


    private String id;
    /**
     * 项目id
     */
    private String projectId;

    /**
     * 父目录id
     */
    private String pid;

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
     * 子文件个数大小
     */
    private int fileCount;

    /**
     * 上传时间
     */
    private String uploadDate;

    /**
     * 上传人员
     */
    private String uploadUserName;

    /**
     *文件分组
     */
    private String fileGroup;

    /**
     *0:自定义，1：系统默认   文件/文件夹
     */
    private int isCustomize;

    private String createBy;

    private int editFlag;

    private String taskId;

    private String companyName;

    private String sendResults;

    private boolean childId;

    /** 文件链接地址 */
    private String fileFullPath;

    public String getFileFullPath() {
        return fileFullPath;
    }

    public void setFileFullPath(String fileFullPath) {
        this.fileFullPath = fileFullPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getUploadUserName() {
        return uploadUserName;
    }

    public void setUploadUserName(String uploadUserName) {
        this.uploadUserName = uploadUserName;
    }

    public String getFileGroup() {
        return fileGroup;
    }

    public void setFileGroup(String fileGroup) {
        this.fileGroup = fileGroup;
    }

    public int getIsCustomize() {
        return isCustomize;
    }

    public void setIsCustomize(int isCustomize) {
        this.isCustomize = isCustomize;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public int getEditFlag() {
        return editFlag;
    }

    public void setEditFlag(int editFlag) {
        this.editFlag = editFlag;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getSendResults() {
        return sendResults;
    }

    public void setSendResults(String sendResults) {
        this.sendResults = sendResults;
    }

    public boolean isChildId() {
        return childId;
    }

    public void setChildId(boolean childId) {
        this.childId = childId;
    }
}