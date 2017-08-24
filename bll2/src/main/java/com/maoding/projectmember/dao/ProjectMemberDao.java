package com.maoding.projectmember.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.project.dto.ProjectTaskProcessNodeDTO;
import com.maoding.projectmember.dto.ProjectMemberDTO;
import com.maoding.projectmember.dto.UserPositionDTO;
import com.maoding.projectmember.entity.ProjectMemberEntity;

import java.util.List;

/**
 * Created by Idccapp22 on 2017/6/6.
 */
public interface ProjectMemberDao extends BaseDao<ProjectMemberEntity> {

    /**
     * 方法描述：根据参数查询
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    List<ProjectMemberEntity>  listProjectMember(ProjectMemberEntity projectMember);

    /**
     * 方法描述：根据参数查询(包含个人信息)
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    List<ProjectMemberDTO> listProjectMemberByParam(ProjectMemberEntity projectMember);

    /**
     * 方法描述：项目成员
     * 作者：MaoSF
     * 日期：2017/6/8
     */
    List<ProjectTaskProcessNodeDTO> listProjectMemberGroupByParam(ProjectMemberEntity projectMember);

    /**
     * 方法描述：项目成员
     * 作者：MaoSF
     * 日期：2017/6/8
     */
    List<ProjectTaskProcessNodeDTO> listProjectDesignMember(String taskId,String fastdfsUrl);


    /**
     * 方法描述：更新状态（status:1，职责所承担的任务已完成，0，未完成）
     * 作者：MaoSF
     * 日期：2017/6/7
     */
    int updateProjectMemberStatus(ProjectMemberEntity projectMember);

    /**
     * 方法描述：逻辑删除
     * 作者：MaoSF
     * 日期：2017/6/7
     */
    int deleteProjectMember(ProjectMemberEntity projectMember);

    /**
     * 方法描述：成员角色
     * 作者：MaoSF
     * 日期：2017/6/13
     */
    List<UserPositionDTO> getUserPositionForProject(String projectId, String companyId, String accountId);


    /**
     * 方法描述：获取设计人员
     * 作者：MaoSF
     * 日期：2017/6/24
     */
    String getDesignUserByTaskId(String taskId);


    /**
     * 方法描述：获取任务节点下所有的人员（用于任务copy）
     * 作者：MaoSF
     * 日期：2017/6/21
     */
    List<ProjectMemberEntity> listProjectMemberByTaskId(String taskId);
}
