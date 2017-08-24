package com.maoding.project.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.ResponseBean;
import com.maoding.project.dto.ProjectTaskResponsibleDTO;
import com.maoding.project.entity.ProjectTaskResponsiblerEntity;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectService
 * 类描述：Service
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午5:28:54
 */
public interface ProjectTaskResponsibleService extends BaseService<ProjectTaskResponsiblerEntity>{


    /**
     * 方法描述：保存设计负责人（新增）
     * 作者：MaoSF
     * 日期：2017/5/20
     * @param:
     * @return:
     */
    ResponseBean insertTaskResponsible(String projectId,String companyId,String targetId,String taskId,String accountId,String currentCompanyId) throws Exception;

    /**
     * 方法描述：保存设计负责人（新增）-- 未发布版本
     * 作者：MaoSF
     * 日期：2017/5/20
     * @param:
     * @return:
     */
    ResponseBean insertTaskResponsible(String projectId, String companyId, String companyUserId, String taskId, String accountId) throws Exception;


    /***********************v2接口************************/
    /**
     * 方法描述：保存项目负责人
     * 作者：MaoSF
     * 日期：2017/1/3
     * @param:
     * @return:
     */
    ResponseBean saveTaskResponsible(ProjectTaskResponsibleDTO dto) throws Exception;

    /**
     * 任务负责人移交(未发布状态)
     */
    ResponseBean transferTaskResponse(ProjectTaskResponsibleDTO dto, boolean isPublish) throws Exception;
}
