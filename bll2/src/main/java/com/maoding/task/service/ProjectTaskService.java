package com.maoding.task.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.ResponseBean;
import com.maoding.task.dto.*;
import com.maoding.task.entity.ProjectTaskEntity;

import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskService
 * 类描述：项目任务
 * 作    者：MaoSF
 * 日    期：2016年12月28日-下午5:28:54
 */
public interface ProjectTaskService extends BaseService<ProjectTaskEntity>{

    /**
     * 方法描述：设计阶段数据
     * 作者：MaoSF
     * 日期：2017/6/16
     */
    List<ProjectTaskEntity> listProjectTaskContent(String projectId);

    /**
     * 方法描述：项目签发数据
     * 作者：MaoSF
     * 日期：2016/12/31
     */
    List<ProjectIssueDataDTO> getTaskIssueData(String projectId) throws Exception;

    /**
     * 方法描述：项目任务列表（任务分配界面 （项目详情界面，签发组织板块数据））
     * 作者：MaoSF
     * 日期：2016/12/31
     */
    ProjectTaskDataDTO getProjectTask(Map<String, Object> map) throws Exception;

    /**
     * 方法描述：项目任务列表（任务分配界面 （项目详情界面，签发组织板块数据））
     * 作者：MaoSF
     * 日期：2016/12/31
     */
    ProjectTaskDataDTO getProjectTaskDataForOperater(Map<String, Object> map) throws Exception;

    /**
     * 方法描述：查询项目根任务
     * 作者：MaoSF
     * 日期：2016/12/31
     */
    List<ProjectTaskDTO> getProjectTaskRootData(String projectId, String companyId)throws Exception;

    /**
     * 方法描述：经营界面任务查询
     * 作者：MaoSF
     * 日期：2016/12/31
     */
    List<ProjectTaskDTO> getProjectTaskForOperater(String projectId, String companyId)throws Exception;

    /**
     * 方法描述：经营界面任务详情
     * 作者：MaoSF
     * 日期：2016/12/31
     * @param:
     * @return:
     */
    ProjectTaskDetailDTO getProjectTaskDetailForOperater(String id,String companyId,String accountId)throws Exception;

    /**
     * 方法描述：是否可以设计经营负责人和设计负责人权限
     * 作者：MaoSF
     * 日期：2017/4/20
     * @param:type=1:经营负责人权限，type=2:设计负责人权限
     */
    int isProjectManagerEditRole(String projectId, String companyId, String accountId, int type) throws Exception;

    /**
     * 方法描述：非立项方（项目任务数据）
     * 作者：MaoSF
     * 日期：2016/12/31
     */
    List<ProjectTaskDTO> getProjectTaskByCompanyId(String projectId, String companyId)throws Exception;

    /**
     * 方法描述：项目任务列表（任务分配界面 （项目详情界面，我的任务，设，校，审，定四种任务所在的任务））
     * 作者：MaoSF
     * 日期：2016/12/31
     */
    List<ProjectTaskDTO> getProjectProcessTaskByCompanyId(String projectId, String companyId, String accountId)throws Exception;

    /**
     * 方法描述：项目任务列表（任务分配界面），1：全部任务，2，我团队的任务，3，我的任务
     * 作者：MaoSF
     * 日期：2017/1/3
     */
    List<ProjectTaskDTO> getProjectTaskByType(Map<String, Object> map)throws Exception;

    /**
     * 方法描述：获取当前项目其他合作团队的经营人和项目负责人
     * 作者：MaoSF
     * 日期：2017/1/3
     */
    ResponseBean getProjectPartner(Map<String, Object> map)throws Exception;

    /**
     * 方法描述：删除任务
     * 作者：MaoSF
     * 日期：2017/1/3
     */
    ResponseBean deleteProjectTaskById(String id, String accountId) throws Exception;

    /**
     * 方法描述：删除设计阶段的时候调用
     * 作者：MaoSF
     * 日期：2017/4/8
     */
    ResponseBean deleteProjectTaskByIdForDesignContent(String id, String accountId) throws Exception;

    /**
     * 方法描述：获取任务详情及子任务
     * 作者：MaoSF
     * 日期：2016/12/31
     */
    ProjectTaskDetailDTO getProjectTaskById(String id, String companyId, String accountId) throws Exception;

    /**
     * 方法描述：获取任务详情及子任务
     * 作者：MaoSF
     * 日期：2016/12/31
     */
    ProjectTaskDetailDTO getProductTaskForEdit(String id, String companyId, String accountId) throws Exception;

    /**
     * 方法描述：经营任务分解
     * 作者：CHENZHUJIE
     * 日期：2017/2/24
     */
    ResponseBean saveProjectTask2(SaveProjectTaskDTO dto)throws Exception;

    /**
     * 方法描述：处理任务完成时间
     * 作者：MaoSF
     * 日期：2017/3/12
     */
    ResponseBean handleProjectTaskCompleteDate(String taskId, String companyUserId) throws Exception;

    /**
     * 方法描述：移交设计负责人请求数据
     * 作者：MaoSF
     * 日期：2017/3/22
     */
    ResponseBean getProjectTaskForChangeDesigner(Map<String,Object> map) throws Exception;

    /**
     * 方法描述：
     * 作者：MaoSF
     * 日期：2017/3/17
     * @param:map(projectId,toCompanyId(签发给这个组织的id)
     */
    ResponseBean validateIssueTaskCompany(Map<String, Object> map) throws Exception;

    /**
     * 方法描述:获取任务签发组织
     * 作者：MaoSF
     * 日期：2017/3/17
     */
    ResponseBean getIssueTaskCompany(String id, String companyId) throws Exception;

    /**
     * 方法描述：设置任务状态
     * 作者：MaoSF
     * 日期：2017/4/6
     */
    void setTaskState(ProjectTaskDTO dto) throws Exception;

    /**
     * 方法描述：签发的任务（我的任务，签发任务获取）
     * 作者：MaoSF
     * 日期：2017/5/5
     */
    ResponseBean getIssueTaskForMyTask(String projectId, String companyId) throws Exception;

    /**
     * 方法描述：设置设计负责人（我的任务）获取的数据
     * 作者：MaoSF
     * 日期：2017/5/5
     */

    ResponseBean getArrangeDesignManagerForMyTask(String projectId, String companyId) throws Exception;

    /**
     * 方法描述：设置任务负责人（我的任务）获取的数据
     * 作者：MaoSF
     * 日期：2017/5/5
     */

    ResponseBean getArrangeTaskResponseForMyTask(String projectId, String taskId, String companyId) throws Exception;

    /**
     * 方法描述：设置任务负责人（我的任务）获取的数据
     * 作者：MaoSF
     * 日期：2017/5/5
     */
    ResponseBean getHandProcessDataForMyTask(String projectId, String taskId, String companyId) throws Exception;

    /**
     * 进行签发
     */
    ResponseBean publishIssueTask(List<String> idList, String currentCompanyUserId, String currentCompanyId) throws Exception;

    /**
     * 进行生产任务发布
     */
    ResponseBean publishProductTask(List<String> idList, String currentCompanyUserId, String currentCompanyId) throws Exception;

    /**
     * 方法描述：经营板块的数据
     * 作者：MaoSF
     * 日期：2017/5/15
     */
    ResponseBean getOperatorTaskList(String projectId,String companyId)  throws Exception;

    /**
     * 方法描述：经营界面任务详情
     * 作者：MaoSF
     * 日期：2016/12/31
     */
    ProjectTaskForEditDTO getProjectTaskDetailForEdit(String id,String companyId,String accountId)throws Exception;

    /**
     * 方法描述：完成生产任务
     * 作者：MaoSF
     * 日期：2017/3/12
     */
    ResponseBean completeProductTask(String projectId, String taskId, String companyId, String accountId) throws Exception;

    /**
     * 方法描述：对象信息复制（用于数据记录更新的时候，不存在被修改的记录，则产生一条永不修改的记录数据）
     * 作者：MaoSF
     * 日期：2017/6/21
     */
    String copyProjectTask(SaveProjectTaskDTO dto, ProjectTaskEntity issuedEntity) throws Exception;

    /**
     * 方法描述：修改任务（改变任务的状态：未发布的状态）
     * 作者：MaoSF
     * 日期：2017/6/26
     */
    ResponseBean updateByTaskIdStatus(String id, String taskStatus) throws Exception;
}
