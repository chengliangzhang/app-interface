package com.maoding.task.dao;


import com.maoding.core.base.dao.BaseDao;
import com.maoding.task.dto.ProjectManagerDTO;
import com.maoding.task.entity.ProjectManagerEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by Idccapp21 on 2016/10/18.
 */
public interface ProjectManagerDao extends BaseDao<ProjectManagerEntity> {
    /**
     * 根据参数查询负责人（经营负责人，项目负责人）
     * @param param
     * @return
     * @throws Exception
     */
    public List<ProjectManagerEntity> getProjectManagerByParam(Map<String,Object> param) throws  Exception;

    /**
     * 方法描述：根据projectId,companyId查询经营负责人和项目负责人
     * 作者：MaoSF
     * 日期：2016/12/31
     * @param:
     * @return:
     */
    List<ProjectManagerDTO> getProjectManager(String projectId, String companyId, String fastdfsUrl);


    /**
     * 方法描述：根据projectId,companyId查询经营负责人和项目负责人
     * 作者：MaoSF
     * 日期：2016/12/31
     * @param:
     * @return:
     */
    ProjectManagerEntity getProjectOperaterManager(String projectId, String companyId);


    /**
     * 方法描述：selectByParam
     * 作者：MaoSF
     * 日期：2016/12/31
     * @param:
     * @return:
     */
    List<ProjectManagerEntity> selectByParam(Map<String, Object> map);


    /**
     * 方法描述：根据projectId,companyId项目负责人
     * 作者：MaoSF
     * 日期：2016/12/31
     * @param:
     * @return:
     */
    ProjectManagerEntity getProjectDesignManager(String projectId, String companyId);

    /**
     * 方法描述：删除公司在该项目的经营负责人及企业负责人（用于删除任务，更换任务组织）使用
     * 作者：MaoSF
     * 日期：2017/3/14
     * @param:
     * @return:
     */
    int deleteProjectManage(String projectId,String companyId);

}
