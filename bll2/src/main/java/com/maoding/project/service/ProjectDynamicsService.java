package com.maoding.project.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.project.dto.*;
import com.maoding.project.entity.*;
import com.maoding.projectcost.dto.ProjectCostDTO;
import com.maoding.projectcost.dto.ProjectCostPaymentDetailDTO;
import com.maoding.projectcost.dto.ProjectCostPointDTO;
import com.maoding.projectcost.dto.ProjectCostPointDetailDTO;
import com.maoding.projectcost.entity.*;
import com.maoding.task.dto.ProjectProcessTimeDTO;
import com.maoding.task.dto.SaveProjectTaskDTO;
import com.maoding.task.dto.TransferTaskDesignerDTO;
import com.maoding.task.entity.ProjectManagerEntity;
import com.maoding.task.entity.ProjectProcessTimeEntity;
import com.maoding.task.entity.ProjectTaskEntity;

import java.util.Map;

/**
 * Created by Idccapp21 on 2017/3/2.
 */
public interface ProjectDynamicsService extends BaseService<ProjectDynamicsEntity> {
    /***
     * 获取项目动态
     * @param paraMap
     * @return
     */
    ResponseBean getProjectDynamicByPage(Map<String, Object> paraMap) throws Exception;

    /**
     * 添加项目动态
     */
    ResponseBean appAddProjectDynamic(InsertProjectDynamicDTO dto);
    AjaxMessage addProjectDynamic(InsertProjectDynamicDTO entity);

    /**
     * 获取DTO
     */
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectDTO dto);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectDTO dto, ProjectEntity entity);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(TransferTaskDesignerDTO dto);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(TransferTaskDesignerDTO dto, ProjectManagerEntity entity);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectEntity entity, String accountId);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(SaveProjectTaskDTO dto);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(SaveProjectTaskDTO dto, ProjectTaskEntity entity);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectTaskEntity entity, String accountId,String currentCompanyId);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectProcessTimeDTO dto);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectProcessTimeDTO dto, ProjectProcessTimeEntity entity);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectTaskResponsibleDTO dto);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectTaskResponsibleDTO dto, ProjectTaskResponsiblerEntity entity);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectProcessDTO dto);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectProcessDTO dto, ProjectProcessEntity entity);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostDTO dto);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostDTO dto, ProjectCostEntity entity);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostPointDTO dto);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostPointDTO dto, ProjectCostPointEntity entity);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostPointEntity entity, String accountId);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostPointDetailDTO dto);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostPointDetailEntity entity, String accountId);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostPointDetailDTO dto, ProjectCostPointDetailEntity entity);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostPaymentDetailDTO dto);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostPaymentDetailDTO dto, ProjectCostPaymentDetailEntity entity);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostPaymentDetailEntity entity, String accountId);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectCostOperaterEntity dto, String fee, String accountId);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectSkyDriveDTO dto);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectSkyDriveDTO dto, ProjectSkyDriveEntity entity);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectSkyDriveRenameDTO dto, ProjectSkyDriveEntity entity);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectSkyDriveEntity entity, String accountId);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectDesignContentDTO  dto);
    InsertProjectDynamicDTO getInsertProjectDynamicDTO(ProjectDesignContentDTO  dto, ProjectDesignContentEntity entity);
}
