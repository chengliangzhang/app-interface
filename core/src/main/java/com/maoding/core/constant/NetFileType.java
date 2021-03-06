package com.maoding.core.constant;

/**
 * Created by Wuwq on 2017/2/15.
 */
public class NetFileType {
    /**
     * 目录
     */
    public static final Integer DIRECTORY = 0;

    /**
     * 文件
     */
    public static final Integer FILE = 1;

    /**
     * 项目附件（除项目文档库以为的附件） 附件类型(3.合同附件)
     */
    public static final Integer PROJECT_CONTRACT_ATTACH = 3;

    /**
     * 组织附件类型(4.公司logo,6.移动端上传轮播图片,7:公司邀请二维码)
     */
    public static final Integer COMPANY_LOGO_ATTACH = 4;

    /**
     * 组织附件类型(6.移动端上传轮播图片)
     */
    public static final Integer COMPANY_BANNER_ATTACH = 6;

    /**
     * 组织附件类型(7:公司邀请二维码)
     */
    public static final Integer COMPANY_QR_CODE_ATTACH = 7;

    /**
     * 报销附件类型（20）
     */
    public static final Integer EXPENSE_ATTACH = 20;

    /**
     * 通知公告附件类型（21）
     */
    public static final Integer NOTICE_ATTACH = 21;

    /**
     * 30：成果文件
     */
    public static final Integer DIRECTORY_ACHIEVEMENT = 30;

    /**
     * 40：归档通知(文件夹)
     */
    public static final Integer DIRECTORY_ARCHIVE_NOTICE = 40;

    /**
     * 50：发送归档通知(文件夹)
     */
    public static final Integer DIRECTORY_SEND_ARCHIVE_NOTICE = 50;

    /**
     * 60：会议，日程文件
     */
    public static final Integer SCHEDULE_ATTACH = 60;

    /**
     * 70：项目费用关联的合同
     */
    public static final Integer PROJECT_FEE_ARCHIVE = 70;

    /**
     * 100：个人(文件夹)
     */
    private static final Integer DIRECTORY_PERSONALLY = 100;

    /**
     * 101：个人(文件)
     */
    private static final Integer FILE_PERSONALLY = 101;

}
