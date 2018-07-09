package com.maoding.task.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.mytask.dto.QueryMyTaskDTO;
import com.maoding.task.dto.*;
import com.maoding.task.entity.ProjectTaskEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskEntity
 * 类描述：项目任务
 * 作    者：MaoSF
 * 日    期：2016年12月28日-上午10:13:28
 */
public interface ProjectTaskDao extends BaseDao<ProjectTaskEntity> {

    /**
     * 方法描述：根据taskPath设定节点的状态
     * 作者：MaoSF
     * 日期：2016/12/30
     */
    int updateProjectTaskStatus(ProjectTaskEntity taskEntity);

    /**
     * 方法描述：项目任务列表（任务分配界面 （项目详情界面，签发组织板块数据））
     * 作者：MaoSF
     * 日期：2016/12/31
     */
    List<ProjectIssueDataDTO> getTaskIssueData(String projectId);

    /**
     * 方法描述：查询项目根任务
     * 作者：MaoSF
     * 日期：2016/12/31
     */
    List<ProjectTaskDTO> getProjectTaskRootData(String projectId,String companyId);

    /**
     * 方法描述： 经营界面的时候查询任务所在的根节点
     * 作者：MaoSF
     * 日期：2017/3/1
     * @param map(projectId,companyId)
     */
    List<ProjectTaskDTO> getProjectTaskRootOfOperater(Map<String, Object> map);

    /**
     * 方法描述： 经营任务详情界面
     * 作者：MaoSF
     * 日期：2017/3/1
     */
    List<ProjectTaskDTO> getRelationProjectTaskByCompanyOfOperater(String projectId,String companyId);

    /**
     * 方法描述：查询项目根任务
     * 作者：MaoSF
     * 日期：2016/12/31
     */
    List<ProjectTaskDTO> getProjectTaskByCompanyId(Map<String, Object> map);

    /**
     * 方法描述：项目任务列表（任务分配界面 （项目详情界面，我的任务，设，校，审，定四种任务所在的任务））
     * 作者：MaoSF
     * 日期：2016/12/31
     */
    List<ProjectTaskDTO> getProjectProcessTaskByCompanyUserId(String projectId, String companyUserId);

    /**
     * 方法描述：经营界面，查询子任务
     * 作者：MaoSF
     * 日期：2017/3/2
     */
    List<ProjectTaskDTO> getProjectTaskChildOfOperater(Map<String,Object> map);

    /**
     * 方法描述：获取当前项目其他参与团队
     * 作者：MaoSF
     * 日期：2017/1/3
     */
    List<String> getOtherPartnerCompany(String projectId, String companyId);

    /**
     * 方法描述：获取当前项目其他参与团队
     * 作者：MaoSF
     * 日期：2017/1/3
     */
    List<Map<String, Object>> getProjectTaskCompany(String projectId);

    /**
     * 方法描述：根据id查询
     * 作者：MaoSF
     * 日期：2017/1/4
     */
    ProjectTaskDTO getProjectTaskById(String id, String companyId);

    /**
     * 方法描述：根据id查询（经营详情）
     * 作者：MaoSF
     * 日期：2017/1/4
     */
    ProjectTaskDTO getProjectTaskByIdForOperator(String id, String companyId);

    /**
     * 方法描述：根据taskPid查询
     * 作者：MaoSF
     * 日期：2017/1/4
     */
    List<ProjectTaskDTO> getProjectTaskByPid(String taskPid, String companyId);

    /**
     * 方法描述：根据taskPid查询
     * 作者：MaoSF
     * 日期：2017/1/4
     */
    List<ProjectTaskEntity> getProjectTaskByPid2(String taskPid);


    /**
     * 方法描述：根据taskPath查询所有的子任务
     * 作者：MaoSF
     * 日期：2017/1/4
     */
    List<ProjectTaskEntity> getProjectTaskByTaskPath(String taskPath);

    /**
     * 方法描述：根据taskPath查询所有的子任务
     * 作者：MaoSF
     * 日期：2017/1/4
     */
    List<ProjectTaskEntity> getProjectTaskByTaskPath(String taskPath,String companyId);

    /**
     * 方法描述：获取任务的父级的名称
     * 作者：MaoSF
     * 日期：2017/1/4
     */
    String getTaskParentName(String id);
    /**
     * 方法描述：获取任务的父级的名称(除当前节点)
     * 作者：郭志彬
     * 日期：2017/9/19
     */
    String getTaskParentNameExceptOwn(String id);


    /**
     * 方法描述：根据taskPid查询生产分解的任务（用于 经营分解时，判断父任务是否进行了生产）
     * 作者：MaoSF
     * 日期：2017/2/27
     */
    List<ProjectTaskEntity> getProjectTaskByPidOfProductTask(String taskPid);

    /**
     * 方法描述：根据项目id和公司id查询任务（用于经营分解时，判断是否存在已经签发给该公司的记录）
     * 作者：MaoSF
     * 日期：2017/2/27
     */
    List<ProjectTaskEntity> getProjectTaskByCompanyIdOfOperater(String projectId, String companyId);

    /**
     * 方法描述：把部门字段设置为null,用于更换任务的组织
     * 作者：MaoSF
     * 日期：2017/3/20
     */
    int updateTaskOrgId(String id);

    /**
     * 方法描述：获取我是任务负责人的任务
     * 作者：MaoSF
     * 日期：2017/3/22
     * @param:map(projectId,companyUserId)
     */
    List<ProjectTaskForDesignerDTO> getMyProjectTask(Map<String,Object> map);

    /**
     * 方法描述：获取父级所以的companyId
     * 作者：MaoSF
     * 日期：2017/4/8
     */
    String getParentTaskCompanyId(Map<String,Object> map);

    /**
     * 方法描述：根据taskPid,taskName获取（用于重名处理）
     * 作者：MaoSF
     * 日期：2017/4/21
     */
    ProjectTaskEntity getProjectTaskByPidAndTaskName(String projectId,String taskPid,String taskName);

    /**
     * 方法描述：获取本团队负责的任务，经营签发给自己的任务
     * 作者：MaoSF
     * 日期：2017/5/5
     */
    List<ProjectTaskDTO> getResponsibleTaskForMyTask(String projectId,String companyId);

    /**
     * 方法描述：根据ID列表获取任务数据
     * 作者：ZCL
     * 日期：2017/5/16
     */
    List<ProjectTaskEntity> getTaskByIdList(List<String> idList);

    /**
     * 方法描述：根据taskPid获取任务ID及未发布的信息记录
     * 作者：MaoSF
     * 日期：2017/5/16
     */
    List<ProjectIssueTaskDTO> getTaskByTaskPidForChangeProcessTime(String taskPid);

    /**
     * 方法描述：经营板块的数据
     * 作者：MaoSF
     * 日期：2017/5/15
     */
    List<ProjectIssueTaskDTO> getOperatorTaskList(QueryProjectTaskDTO dto);

    /**
     * 方法描述：获取任务基本信息（用于编辑）
     * 作者：MaoSF
     * 日期：2017/5/18
     */
    ProjectTaskDTO getProjectTaskByIdForEdit(String id,int type);

    /**
     * 方法描述：根据参数查询
     * 作者：MaoSF
     * 日期：2017/5/16
     */
    List<ProjectTaskEntity> selectByParam(Map<String ,Object> map);

    /**
     * 作用：复位已完成状态
     * 作者：ZCL
     * 日期：2017-5-20
     */
    void resetTaskCompleteStatus(String taskId) throws Exception;
    void resetProcessNodeCompleteStatus(String processNodeId) throws Exception;

    /**
     * 方法描述：获取某项目内某承包方的任务
     * 作者：张成亮
     * 日期：2017/4/11
     */
    List<String> getProjectTaskOfToCompany(String projectId, String companyId);

    /**
     * 方法描述：获取最大的seq
     * 作者：MaoSF
     * 日期：2017/6/9
     */
    int getProjectTaskMaxSeq(String projectId,String taskPid);

    /**
     * 作用：获取任务状态
     * 作者：ZCL
     * 日期：2017-5-24
     */
    int getTaskState(String taskId);
    int getTaskState(String taskId,String projectId);
    String getStateText(Integer taskState, Date startTime, Date endTime, Date completeDate);
    String getStateText(Integer taskState,String startTime,String endTime,String completeDate);

    /**
     * 方法描述：发布当前记录后，修改该任务下的子集的taskPid，taskPath(重新设置为正式记录的taskPid)
     * param：publishId:被发布记录的ID，taskPid：新的taskPid,新的taskPath=parentPath+id
     * 作者：MaoSF
     * 日期：2017/6/23
     */
    int updateModifyTaskPid(String publishId,String taskPid,String parentPath);


    /**
     * 方法描述：获取设计内容（立项方（type=1）：设计阶段，合作方（type=2 and companyId），签发的数据）
     * 作者：MaoSF
     * 日期：2017/6/28
     */
    String getIssueTaskName(String projectId,String companyId,int type);
    /**
     * 方法描述：获取设计内容（立项方（type=1）：设计阶段，合作方（type=2 and companyId），签发的数据）
     * 作者：MaoSF
     * 日期：2017/6/28
     */
    String getIssueTaskName(String projectId,String companyId,int type,String fromCompanyId);

    /**
     * 方法描述：获取我负责的任务
     * 作者：MaoSF
     * 日期：2017/6/30
     * @param:notComplete可以为null，其他不可以为null
     */
    List<ProjectTaskEntity> getMyResponsibleTask(String projectId,String companyId,String companyUserId,String notComplete);

    /**
     * 方法描述：查找未完成的子任务
     * 作者：ZhangChengliang
     * 日期：2017/6/22
     */
    List<ProjectTaskEntity> listUnCompletedTask(String taskPid);

    /**
     * 获取所有当前公司生产的根任务
     */
    String getProductRootTaskName(String projectId,String companyId);

    /**
     * 项目综合信息--签发板块数据
     */
    List<ProjectIssueTaskDTO> getOperatorTaskListByCompanyId(String projectId,String companyId);

    /**
     * 项目综合信息--签发板块数据
     */
    List<ProjectIssueTaskDTO> getProductTaskListByCompanyId(String projectId,String companyId);

    /**
     * 签发数据(tree数据)（v3)
     */
    List<ProjectIssueTaskDTO> listOperatorTaskList(QueryProjectTaskDTO query);


    /**
     * 生产安排数据(tree数据)（v3)
     */
    List<ProjectIssueTaskDTO> getProductTaskList(QueryProjectTaskDTO query);

    /**
     * 方法描述：查找未完成的子任务(查询A-->B组织下的任务是否全部完成)
     */
    List<ProjectTaskEntity> listUnCompletedTaskByCompany(String projectId,String fromCompanyId,String toCompanyId);

    /**
     * 方法描述：使用QueryMyTaskDTO过滤条件查找任务
     */
    List<ProjectTaskDetailDTO> listTaskByMyTaskQuery(QueryMyTaskDTO query);
}