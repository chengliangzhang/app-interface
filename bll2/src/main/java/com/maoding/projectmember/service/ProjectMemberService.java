package com.maoding.projectmember.service;

import com.maoding.project.dto.ProjectTaskProcessNodeDTO;
import com.maoding.projectmember.dto.ProjectMemberDTO;
import com.maoding.projectmember.dto.ProjectMemberGroupDTO;
import com.maoding.projectmember.dto.UserPositionDTO;
import com.maoding.projectmember.entity.ProjectMemberEntity;

import java.util.List;

/**
 * Created by Idccapp22 on 2017/6/6.
 */

public interface ProjectMemberService {

    /**
     * 方法描述：保存项目成员
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    ProjectMemberEntity saveProjectMember(String projectId,String companyId,String companyUserId,String accountId,Integer memberType,String targetId,String nodeId,Integer seq,String createBy,boolean isSendMessage) throws Exception;

    /**
     * 方法描述：保存项目成员（未发布版本的数据：status=2）
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    ProjectMemberEntity saveProjectMember(String projectId,String companyId,String companyUserId,String targetId,Integer memberType,Integer seq,String createBy)  throws Exception;


    /**
     * 方法描述：保存项目成员
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    ProjectMemberEntity saveProjectMember(String projectId,String companyId,String companyUserId,String accountId,Integer memberType,String createBy)  throws Exception;

    /**
     * 方法描述：保存项目成员
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    ProjectMemberEntity saveProjectMember(String projectId,String companyId,String companyUserId,String accountId,Integer memberType,String targetId, String createBy,boolean isSendMessage)  throws Exception;
    /**
     * 方法描述：保存项目成员
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    ProjectMemberEntity saveProjectMember(String projectId,String companyId,String companyUserId,String accountId,Integer memberType,String createBy,boolean isSendMessage)  throws Exception;

      /**
     * 方法描述：保存项目成员(只推送消息，不发送任务)
     * 作者：MaoSF
     * 日期：2017/6/6
     */
      ProjectMemberEntity saveProjectMember(String projectId,String companyId,String companyUserId,Integer memberType,String createBy,boolean isSendMessage)  throws Exception;


    /**
     * 方法描述：更新成员
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    void updateProjectMember(ProjectMemberEntity projectMember,String companyUserId,String accountId,String updateBy,boolean isSendMessage) throws Exception;

    /**
     * 方法描述：更新成员状态
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    void updateProjectMember(String projectId,String companyId,String companyUserId,Integer memberType,String targetId)  throws Exception;


    /**
     * 方法描述：删除成员
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    void deleteProjectMember(String projectId,String companyId,Integer memberType,String targetId) throws Exception;

    /**
     * 方法描述：删除成员
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    void deleteProjectMember(Integer memberType,String targetId) throws Exception;

    /**
     * 方法描述：删除成员
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    void deleteProjectMember(String id) throws Exception;

    /**
     * 方法描述：发布任务，处理当前任务下所有的人员
     * 作者：MaoSF
     * 日期：2017/6/21
     */
    void publishProjectMember(String projectId,String companyId,String taskId,String modifyTaskId,String accountId) throws Exception;

    /**
     * 方法描述：copy 任务下所有的人员
     * 作者：MaoSF
     * 日期：2017/6/21
     */
    void copyProjectMember(String taskId,String newTaskId) throws Exception;

    /**
     * 方法描述：获取成员信息
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    List<ProjectMemberEntity> listProjectMember(String projectId, String companyId, Integer memberType, String targetId) throws Exception;

    /**
     * 方法描述：获取成员信息
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    ProjectMemberEntity getProjectMember(String projectId, String companyId, Integer memberType, String targetId) throws Exception;

    /**
     * 方法描述：获取成员信息(带有个人资料信息)
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    List<ProjectMemberDTO> listProjectMemberByParam(String projectId, String companyId, Integer memberType, String targetId);

    /**
     * 方法描述：获取成员信息(带有个人资料信息)
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    ProjectMemberDTO getProjectMemberByParam(String projectId, String companyId, Integer memberType, String targetId);

    /**
     * 方法描述：项目成员
     * 作者：MaoSF
     * 日期：2017/6/9
     */
    List<ProjectMemberGroupDTO> listProjectMember(String projectId, String currentCompanyId, String accountId);

    /**
     * 方法描述：经营负责人
     * 作者：MaoSF
     * 日期：2017/6/12
     */
    ProjectMemberEntity getOperatorManager(String projectId,String companyId) throws Exception;

    /**
     * 方法描述：设计负责人
     * 作者：MaoSF
     * 日期：2017/6/12
     */
    ProjectMemberEntity getDesignManager(String projectId,String companyId) throws Exception;

    /**
     * 方法描述：获取成员信息(社校审)
     * 作者：MaoSF
     * 日期：2017/6/6
     */
    ProjectMemberEntity getProjectMember(String companyUserId, Integer memberType, String targetId) throws Exception;

    /**
     * 方法描述：立项人
     * 作者：MaoSF
     * 日期：2017/6/12
     */
    ProjectMemberDTO getProjectCreatorDTO(String projectId,String companyId) throws Exception;

    /**
     * 方法描述：经营负责人
     * 作者：MaoSF
     * 日期：2017/6/12
     */
    ProjectMemberDTO getOperatorManagerDTO(String projectId,String companyId) throws Exception;

    /**
     * 方法描述：设计负责人
     * 作者：MaoSF
     * 日期：2017/6/12
     */
    ProjectMemberDTO getDesignManagerDTO(String projectId,String companyId) throws Exception;

    /**
     * 方法描述：任务负责人
     * 作者：MaoSF
     * 日期：2017/6/12
     */
    ProjectMemberDTO getTaskDesignerDTO(String taskId) throws Exception;

    /**
     * 方法描述：经营负责人+设计负责人
     * 作者：MaoSF
     * 日期：2017/6/12
     */
    List<ProjectMemberDTO> listProjectManager(String projectId,String companyId) throws Exception;

    /**
     * 方法描述：成员角色
     * 作者：MaoSF
     * 日期：2017/6/13
     */
    List<UserPositionDTO> listUserPositionForProject(String projectId, String companyId, String accountId);

    /**
     * 方法描述：获取设计人员
     * 作者：MaoSF
     * 日期：2017/6/24
     */
    List<ProjectTaskProcessNodeDTO> listDesignUser(String taskId) throws Exception;

    /**
     * 方法描述：获取设计人员
     * 作者：MaoSF
     * 日期：2017/6/24
     */
    String getDesignUserByTaskId(String taskId);
}
