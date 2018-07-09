package com.maoding.project.service;

import com.maoding.attach.dto.FileDataDTO;
import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.ResponseBean;
import com.maoding.project.dto.NetFileDTO;
import com.maoding.project.dto.ProjectSkyDriveDTO;
import com.maoding.project.entity.ProjectEntity;
import com.maoding.project.entity.ProjectSkyDriveEntity;
import com.maoding.task.entity.ProjectTaskEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectSkyDriverService
 * 类描述：项目文件磁盘
 * 作    者：MaoSF
 * 日    期：2016/12/18 16:50
 */
public interface ProjectSkyDriverService extends BaseService<ProjectSkyDriveEntity>{

    /**************************************文档库*****************************************/
    /**
     * 方法描述：保存文件夹
     * 作者：MaoSF
     * 日期：2016/12/18
     */
    ResponseBean saveOrUpdateFileMaster(ProjectSkyDriveDTO dto) throws Exception;

    /**
     * 方法描述：上传文件
     * 作者：MaoSF
     * 日期：2016/12/18
     */
    ResponseBean uploadFile(MultipartFile imgObj, String pid, String projectId, String companyId, String userId) throws Exception;

    /**
     * 方法描述：删除文件或文件夹（单个删除、若文件夹下有其他的文件夹或文件，不可删除）
     * 作者：MaoSF
     * 日期：2016/12/18
     */
    ResponseBean deleteSysDrive(String id, String accountId) throws Exception;

    /**
     * 方法描述：查询文件
     * 作者：MaoSF
     * 日期：2016/12/18
     */
    List<NetFileDTO> getNetFileByParam(Map<String, Object> map) throws Exception;

    /**
     * 方法描述：查询文件(包含子文件个数)
     * 作者：MaoSF
     * 日期：2016/12/18
     */
    ResponseBean getSkyDrive(Map<String, Object> map);
    ResponseBean getSkyDriveByParamList(Map<String, Object> map) throws Exception;

    /**
     * 方法描述：项目立项时候，创建项目文档库
     * 作者：MaoSF
     * 日期：2016/12/26
     */
    void createProjectFile(ProjectEntity projectEntity) throws Exception;

    /**
     * 方法描述：签发的时候，给该公司创建默认的文件（设计成果中的文件）
     * 作者：MaoSF
     * 日期：2017/4/12
     */
    void createFileMasterForIssueTask(String projectId, String companyId);

    /**
     * 方法描述：签发的时候，给该公司创建默认的文件（设计成果中的文件）
     * 作者：MaoSF
     * 日期：2017/4/12
     */
    void createFileMasterForTask(ProjectTaskEntity taskEntity);

    /**
     * 方法描述：根据项目id，父级id，文件名查询
     * 作者：MaoSF
     * 日期：2016/12/18
     */
    ProjectSkyDriveEntity getSkyDriveByTaskId(String taskId);

    /**
     * 方法描述：
     * 作者：MaoSF
     * 日期：2017/4/21
     */
    ResponseBean updateSkyDriveStatus(String taskId, String accountId) throws Exception;

    /**
     * 方法描述：批量删除
     * 作者：MaoSF
     * 日期：2016/12/18
     */
    ResponseBean deleteSysDrive(List<String> attachIdList,String accountId,String targetId) throws Exception;

    /********************************公司附件**********************************/
    /**
     * 方法描述：获取组织logo地址(完整的url地址)
     * 作者：MaoSF
     * 日期：2017/6/2
     */
    String getCompanyLogo(String companyId) ;

    /**
     * 方法描述：获取组织logo地址（不包含轮播图）
     * 作者：MaoSF
     * 日期：2017/6/2
     */
    String getCompanyFileByType(String companyId,Integer type,boolean isHasFastdfsUrl) throws Exception;

    /**
     * 方法描述：生成公司二维码
     * 作者：MaoSF
     * 日期：2016/11/25
     */
    String createCompanyQrcode(String companyId) throws Exception;

    /********************************项目合同附件***************************************/
    /**
     * 方法描述：项目合同附件
     * 作者：MaoSF
     * 日期：2017/6/2
     */
    List<Map<String,String>> getProjectContractAttach(String projectId) throws Exception;

    /**
     * 获取公司轮播图
     */
    List<NetFileDTO> getCompanyBanner(String companyId) throws Exception;
    List<String> getCompanyBannerImg(String companyId) throws Exception;

    /**
     * 复制文件数据
     */
    void copyFileToNewObject(String targetId,String oldTarget,Integer type) throws Exception;
    void copyFileToNewObject(String targetId,String oldTarget,Integer type,List<String> deleteAttachList) throws Exception;

    List<String> getAttachList(Map<String, Object> map) throws Exception;

    List<FileDataDTO> getAttachDataList(Map<String, Object> map) throws Exception;
    /**
     * 获取个人文件
     */
    List<FileDataDTO> getPersonalFile(Map<String, Object> map) throws Exception;
}
