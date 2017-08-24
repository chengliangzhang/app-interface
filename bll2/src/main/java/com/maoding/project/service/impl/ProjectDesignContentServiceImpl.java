package com.maoding.project.service.impl;

import com.maoding.core.base.service.GenericService;
import com.maoding.project.dao.ProjectDao;
import com.maoding.project.dao.ProjectDesignContentDao;
import com.maoding.project.dto.ProjectDesignContentDTO;
import com.maoding.project.entity.ProjectDesignContentEntity;
import com.maoding.project.service.ProjectDesignContentService;
import com.maoding.role.dao.PermissionDao;
import com.maoding.role.dao.RoleDao;
import com.maoding.task.entity.ProjectTaskEntity;
import com.maoding.task.service.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名： ProjectDesignContentServiceImpl
 * 类描述：设计阶段
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午5:28:54
 */
@Service(" projectDesignContentService")
public class ProjectDesignContentServiceImpl extends GenericService< ProjectDesignContentEntity> implements ProjectDesignContentService {

    @Autowired
    private ProjectDesignContentDao projectDesignContentDao;

    @Autowired
    private ProjectTaskService projectTaskService;



    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private RoleDao roleDao;



    @Autowired
    private PermissionDao permissionDao;


    /**
     * 方法描述：根据参数查询设计阶段
     * 作        者：ChenZJ
     * 日        期：2016年7月21日-上午11:28:56
     *
     * @param projectId
     * @return
     */
    @Override
    public List<ProjectDesignContentDTO> getProjectDesignContentByProjectId(String projectId,Map<String,Object> param) throws Exception{

      //  ProjectEntity projectEntity = this.projectDao.selectById(projectId);
        List<ProjectTaskEntity> rootTaskList = this.projectTaskService.listProjectTaskContent(projectId);
        List<ProjectDesignContentDTO> projectDesignContentDTOList=new ArrayList<ProjectDesignContentDTO>();
        for(ProjectTaskEntity dto1:rootTaskList){
            ProjectDesignContentDTO dto = new ProjectDesignContentDTO();
            dto.setId(dto1.getId());
            dto.setCompanyId(dto1.getCompanyId());
            dto.setProjectId(dto1.getProjectId());
            dto.setContentName(dto1.getTaskName());
            dto.setSeq(dto1.getSeq());
            dto.setStartTime(dto1.getStartTime());
            dto.setEndTime(dto1.getEndTime());
            projectDesignContentDTOList.add(dto);
        }
        return projectDesignContentDTOList;
    }




    //========================新接口2.0===================================================================================


//    /**
//     * 方法描述：根据参数查询设计阶段
//     * 作        者：TangY
//     * 日        期：2016年7月21日-上午11:28:56
//     * @param projectId
//     * @return
//     */
//    @Override
//    public  List<ProjectDesignContentEntity> getProjectDesignContentByProjectId(String projectId) throws Exception{
//        List<ProjectDesignContentEntity> projectDesignContentEntityList = projectDesignContentDao.getProjectDesignContentByProjectId(projectId);
//        return  projectDesignContentEntityList;
//    }
//
//    /**
//     * 方法描述：根据参数查询设计阶段
//     * 作        者：ChenZJ
//     * 日        期：2016年12/27
//     */
//    @Override
//    public List<V2ProjectDesignContentDTO> V2GetProjectDesignContentByProjectId(String projectId, Map<String,Object> param) throws Exception{
//        List<ProjectDesignContentEntity> projectDesignContentEntityList = projectDesignContentDao.getProjectDesignContentByProjectId(projectId);
//        List<V2ProjectDesignContentDTO> list = BaseDTO.copyFields(projectDesignContentEntityList,V2ProjectDesignContentDTO.class);
//
//        return list;
//    }
}
