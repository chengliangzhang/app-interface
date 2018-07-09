package com.maoding.project.dao;


import com.maoding.core.base.dao.BaseDao;
import com.maoding.project.dto.NetFileDTO;
import com.maoding.project.entity.ProjectSkyDriveEntity;
import com.maoding.v2.project.dto.ProjectSkyDriveListDTO;

import java.util.List;
import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名 : ProjectAttachDao
 * 描    述 : 项目附件（dao）
 * 作    者 : LY
 * 日    期 : 2016/7/22 16:50
 */
public interface ProjectSkyDriverDao extends BaseDao<ProjectSkyDriveEntity> {


    /**
     * 方法描述：根据项目id，父级id，文件名查询
     * 作者：MaoSF
     * 日期：2016/12/18
     */
    ProjectSkyDriveEntity getSkyDriveByPidAndFileName(String pid, String fileName, String projectId);

    /**
     * 方法描述：查询文件id为pid的所有文件及文件夹
     * 作者：MaoSF
     * 日期：2016/12/18
     */
    List<ProjectSkyDriveEntity> getSkyDriveByPid(String pid, String projectId);


    /**
     * 方法描述：根据项目id，父级id，文件名查询
     * 作者：MaoSF
     * 日期：2016/12/18
     */
    ProjectSkyDriveEntity getSkyDriveByTaskId(String taskId);


    /**
     * 方法描述：查询文件(包含子文件个数)
     * 作者：MaoSF
     * 日期：2016/12/18
     */
    List<ProjectSkyDriveListDTO> getSkyDrive(Map<String, Object> map);

    /**
     * 方法描述：获取所有
     * 作者：MaoSF
     * 日期：2017/1/16
     */
    List<ProjectSkyDriveListDTO> getProjectUploadFile(String projectId);

    /**
     * 方法描述：查询公司是否存在“设计成果”中的文件
     * 作者：MaoSF
     * 日期：2017/4/12
     */
    List<ProjectSkyDriveEntity> getProjectSkyByCompanyId(String projectId,String companyId);

    /**
     * 方法描述：更改状态（用于删除）
     * 作者：MaoSF
     * 日期：2017/4/21
     * @param:map(skyDrivePath)
     */
    int updateSkyDriveStatus(Map<String,Object> map);

    /**
     * 方法描述：获取远程文件
     * 作者：MaoSF
     * 日期：2017/6/1
     */
    List<NetFileDTO> getNetFileByParam(Map<String, Object> map);

    /**
     * 获取成果文件发给甲方
     */
    ProjectSkyDriveEntity getOwnerProject(Map<String, Object> map);

    /**
     * 查询文件夹顺序
     */
    List<ProjectSkyDriveEntity> getDirectoryDTOList(Map<String, Object> map);
}
