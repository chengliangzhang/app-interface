package com.maoding.task.dao.impl;


import com.maoding.core.base.dao.GenericDao;
import com.maoding.core.util.DateUtils;
import com.maoding.task.dao.ProjectTaskDao;
import com.maoding.task.dto.*;
import com.maoding.task.entity.ProjectTaskEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectTaskEntity
 * 类描述：项目任务
 * 作    者：MaoSF
 * 日    期：2016年12月28日-上午10:13:28
 */
@Service("projectTaskDao")
public class ProjectTaskDaoImpl extends GenericDao<ProjectTaskEntity> implements ProjectTaskDao {


    /**
     * 方法描述：根据taskPath设定节点的状态
     * 作者：MaoSF
     * 日期：2016/12/30
     *
     * @param taskEntity
     * @param:
     * @return:
     */
    @Override
    public int updateProjectTaskStatus(ProjectTaskEntity taskEntity) {
        return this.sqlSession.update("ProjectTaskEntityMapper.updateProjectTaskStatus",taskEntity) ;
    }

    /**
     * 方法描述：项目任务列表（任务分配界面 （项目详情界面，签发组织板块数据））
     * 作者：MaoSF
     * 日期：2016/12/31
     *
     * @param projectId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectIssueDataDTO> getTaskIssueData(String projectId) {
        return this.sqlSession.selectList("GetIssueCompanyMapper.getTaskIssueData",projectId) ;
    }

    /**
     * 方法描述：查询项目根任务
     * 作者：MaoSF
     * 日期：2016/12/31
     *
     * @param projectId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectTaskDTO> getProjectTaskRootData(String projectId) {
        return this.sqlSession.selectList("GetProjectTaskMapper.getProjectTaskRootData",projectId) ;
    }

    /**
     * 方法描述： 合作方进入经营界面的时候查询任务所在的根节点
     * 作者：MaoSF
     * 日期：2017/3/1
     *
     * @param map@param:
     * @return:
     */
    @Override
    public List<ProjectTaskDTO> getProjectTaskRootOfOperater(Map<String, Object> map) {

        return this.sqlSession.selectList("GetProjectTaskMapper.getProjectTaskRootOfOperater",map) ;
    }

    /**
     * 方法描述： 经营任务详情界面
     * 作者：MaoSF
     * 日期：2017/3/1
     *
     * @param
     * @return:
     */
    @Override
    public List<ProjectTaskDTO> getRelationProjectTaskByCompanyOfOperater(String projectId,String companyId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("projectId",projectId);
        map.put("companyId",companyId);
        return this.sqlSession.selectList("GetProjectTaskMapper.getRelationProjectTaskByCompanyOfOperater",map) ;
    }

    /**
     * 方法描述：查询项目根任务
     * 作者：MaoSF
     * 日期：2016/12/31
     *
     * @param map@param:
     * @return:
     */
    @Override
    public List<ProjectTaskDTO> getProjectTaskByCompanyId(Map<String, Object> map) {
        return this.sqlSession.selectList("GetProjectTaskMapper.getProjectTaskByCompanyId",map) ;
    }

    /**
     * 方法描述：项目任务列表（任务分配界面 （项目详情界面，我的任务，设，校，审，定四种任务所在的任务））
     * 作者：MaoSF
     * 日期：2016/12/31
     *
     * @param projectId
     * @param companyUserId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectTaskDTO> getProjectProcessTaskByCompanyUserId(String projectId, String companyUserId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("projectId",projectId);
        map.put("companyUserId",companyUserId);
        return this.sqlSession.selectList("GetProjectTaskMapper.getProjectProcessTaskByCompanyUserId",map) ;
    }

    /**
     * 方法描述：经营界面，查询子任务
     * 作者：MaoSF
     * 日期：2017/3/2
     *
     * @param:
     * @return:
     */
    @Override
    public List<ProjectTaskDTO> getProjectTaskChildOfOperater(Map<String, Object> map) {
        return this.sqlSession.selectList("GetProjectTaskMapper.getProjectTaskChildOfOperater",map) ;
    }

    /**
     * 方法描述：获取当前项目其他参与团队
     * 作者：MaoSF
     * 日期：2017/1/3
     *
     * @param projectId
     * @param:companyId
     * @return:
     */
    @Override
    public List<String> getOtherPartnerCompany(String projectId,String companyId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("projectId",projectId);
        map.put("companyId",companyId);
        return this.sqlSession.selectList("GetProjectTaskMapper.getOtherPartnerCompany",map) ;
    }

    /**
     * 方法描述：项目参与组织
     * 作者：MaoSF
     * 日期：2017/1/3
     */
    @Override
    public List<Map<String, Object>> getProjectTaskCompany(String projectId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("projectId",projectId);
        return this.sqlSession.selectList("GetProjectTaskMapper.getProjectTaskCompany",map) ;
    }

    /**
     * 方法描述：根据id查询
     * 作者：MaoSF
     * 日期：2017/1/4
     *
     * @param id
     * @param companyId
     * @param:
     * @return:
     */
    @Override
    public ProjectTaskDTO getProjectTaskById(String id, String companyId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        map.put("companyId",companyId);
        return this.sqlSession.selectOne("GetProjectTaskMapper.getProjectTaskById",map) ;
    }



    /**
     * 方法描述：根据id查询
     * 作者：MaoSF
     * 日期：2017/1/4
     *
     * @param id
     * @param companyId
     * @param:
     * @return:
     */
    @Override
    public ProjectTaskDTO getProjectTaskByIdForOperator(String id, String companyId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        map.put("companyId",companyId);
        return this.sqlSession.selectOne("GetProjectTaskMapper.getProjectTaskByIdForOperator",map) ;
    }

    /**
     * 方法描述：根据taskPid查询
     * 作者：MaoSF
     * 日期：2017/1/4
     *
     * @param taskPid
     * @param companyId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectTaskDTO> getProjectTaskByPid(String taskPid, String companyId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("taskPid",taskPid);
        map.put("companyId",companyId);
        return this.sqlSession.selectList("GetProjectTaskMapper.getProjectTaskByPid",map) ;
    }

    /**
     * 方法描述：根据taskPid查询
     * 作者：MaoSF
     * 日期：2017/1/4
     */
    @Override
    public List<ProjectTaskDTO> getProjectTaskByPidForProduct(String taskPid) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("taskPid",taskPid);
        return this.sqlSession.selectList("GetProjectTaskMapper.getProjectTaskByPidForProduct",map) ;
    }

    /**
     * 方法描述：根据taskPid查询
     * 作者：MaoSF
     * 日期：2017/1/4
     */
    @Override
    public List<ProjectTaskEntity> getProjectTaskByPid2(String taskPid) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("taskPid",taskPid);
        return this.selectByParam(map);
    }


    /**
     * 方法描述：根据taskPid查询
     * 作者：MaoSF
     * 日期：2017/1/4
     */
    @Override
    public List<ProjectTaskEntity> listChildTaskForPublish(String taskPid) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("taskPid",taskPid);
        return this.sqlSession.selectList("ProjectTaskEntityMapper.listChildTaskForPublish",map);
    }

    /**
     * 方法描述：根据taskPath查询所有的子任务
     * 作者：MaoSF
     * 日期：2017/1/4
     *
     * @param taskPath
     * @param:
     * @return:
     */
    @Override
    public List<ProjectTaskEntity> getProjectTaskByTaskPath(String taskPath) {

        return this.getProjectTaskByTaskPath(taskPath,null);
    }

    /**
     * 方法描述：根据taskPath查询所有的子任务
     * 作者：MaoSF
     * 日期：2017/1/4
     *
     * @param taskPath
     * @param companyId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectTaskEntity> getProjectTaskByTaskPath(String taskPath, String companyId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("taskPath",taskPath);
        map.put("companyId",companyId);
        return  this.selectByParam(map);
    }

    /**
     * 方法描述：获取任务的父级的名称
     * 作者：MaoSF
     * 日期：2017/1/4
     *
     * @param id
     * @param:
     * @return:
     */
    @Override
    public String getTaskParentName(String id) {
        return this.sqlSession.selectOne("GetProjectTaskMapper.getTaskParentName",id) ;
    }




//    /**
//     * 方法描述：主任务列表的关系（新版本）
//     * 作   者：MaoSF
//     * 日   期：2016/9/20 15:34
//     *
//     * @param projectId
//     */
//    @Override
//    public List<Map<String, String>> getTastRelation(String projectId) {
//        return this.sqlSession.selectList("GetProjectTaskMapper.getTastRelation", projectId);
//    }

    /**
     * 方法描述：根据taskPid查询生产分解的任务（用于 经营分解时，判断父任务是否进行了生产）
     * 作者：MaoSF
     * 日期：2017/2/27
     *
     * @param taskPid
     * @param:
     * @return:
     */
    @Override
    public List<ProjectTaskEntity> getProjectTaskByPidOfProductTask(String taskPid) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("taskPid",taskPid);
        map.put("taskType",0);
        return this.selectByParam(map);
    }

    /**
     * 方法描述：根据项目id和公司id查询任务（用于经营分解时，判断是否存在已经签发给该公司的记录）
     * 作者：MaoSF
     * 日期：2017/2/27
     *
     * @param projectId
     * @param companyId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectTaskEntity> getProjectTaskByCompanyIdOfOperater(String projectId, String companyId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("projectId",projectId);
        map.put("companyId",companyId);
        return this.selectByParam(map);
    }

//    /**
//     * 方法描述：查询当前组织在当前项目下的任务的路径
//     * 作者：MaoSF
//     * 日期：2017/3/1
//     *
//     * @param projectId
//     * @param companyId
//     * @param:
//     * @return:
//     */
//    @Override
//    public List<String> getProjectTaskPath(String projectId, String companyId) {
//        Map<String,Object> map = new HashMap<String,Object>();
//        map.put("projectId",projectId);
//        map.put("companyId",companyId);
//        return  this.sqlSession.selectList("GetProjectTaskMapper.getProjectTaskPath",map) ;
//    }

    /**
     * 方法描述：把部门字段设置为null,用于更换任务的组织
     * 作者：MaoSF
     * 日期：2017/3/20
     *
     * @param id
     * @param:
     * @return:
     */
    @Override
    public int updateTaskOrgId(String id) {
        return this.sqlSession.update("ProjectTaskEntityMapper.updateTaskOrgId",id);
    }

    /**
     * 方法描述：获取我是任务负责人的任务
     * 作者：MaoSF
     * 日期：2017/3/22
     *
     * @param map
     * @param:map(projectId,targetId:companyUserId)
     * @return:
     */
    @Override
    public List<ProejctTaskForDesignerDTO> getMyProjectTask(Map<String, Object> map) {
        return this.sqlSession.selectList("GetProjectTaskMapper.getMyProjectTask",map) ;
    }

//    /**
//     * 方法描述：获取某个任务下面非根任务的任务（非根节点，非签发的任务）
//     * 作者：MaoSF
//     * 日期：2017/4/6
//     *
//     * @param taskPath
//     * @param:
//     * @return:
//     */
//    @Override
//    public int getNotRootProjectTask(String taskPath) {
//        return this.sqlSession.selectOne("GetProjectTaskMapper.getNotRootProjectTask",taskPath) ;
//    }


    /**
     * 方法描述：获取没有设置任务负责人的任务（用于设置设计负责人--推送安排任务负责人）
     * 作者：MaoSF
     * 日期：2017/4/7
     *
     * @param projectId
     * @param companyId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectTaskEntity> getNotSetResponsibleTask(String projectId, String companyId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("projectId",projectId);
        map.put("companyId",companyId);
        return  this.sqlSession.selectList("ProjectTaskEntityMapper.getNotSetResponsibleTask",map) ;
    }

    @Override
    public String getParentTaskCompanyId(Map<String,Object> map)  {
        return this.sqlSession.selectOne("GetProjectTaskMapper.getParentTaskCompanyId",map) ;
    }

    /**
     * 方法描述：根据taskPid,taskName获取（用于重名处理）
     * 作者：MaoSF
     * 日期：2017/4/21
     *
     * @param projectId
     * @param taskPid
     * @param taskName
     * @param:
     * @return:
     */
    @Override
    public ProjectTaskEntity getProjectTaskByPidAndTaskName(String projectId, String taskPid, String taskName) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("taskPid",taskPid);
        map.put("taskName",taskName);
        List<ProjectTaskEntity> list = this.sqlSession.selectList("ProjectTaskEntityMapper.getProjectTaskByPidAndTaskName",map) ;
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 方法描述：获取本团队负责的任务，经营签发给自己的任务
     * 作者：MaoSF
     * 日期：2017/5/5
     *
     * @param projectId
     * @param companyId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectTaskDTO> getResponsibleTaskForMyTask(String projectId, String companyId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("projectId",projectId);
        map.put("companyId",companyId);
        return  this.sqlSession.selectList("GetProjectTaskMapper.getResponsibleTaskForMyTask",map) ;
    }

    /**
     * 方法描述：根据ID列表获取任务数据
     * 作者：ZCL
     * 日期：2017/5/16
     *
     * @param idList 任务的id列表
     */
    @Override
    public List<ProjectTaskEntity> getTaskByIdList(List<String> idList) {
        Map<String,Object> map = new HashMap<>();
        map.put("idList",idList);
        return sqlSession.selectList("ProjectTaskEntityMapper.getTaskByIdList",map);
    }

    /**
     * 方法描述：根据taskPid获取任务ID及未发布的信息记录
     * 作者：MaoSF
     * 日期：2017/5/16
     *
     * @param taskPid
     * @param:
     * @return:
     */
    @Override
    public List<ProjectIssueTaskDTO> getTaskByTaskPidForChangeProcessTime(String taskPid) {
        return sqlSession.selectList("GetProjectTaskForOperatorMapper.getTaskByTaskPidForChangeProcessTime",taskPid);
    }

    /**
     * 方法描述：经营板块的数据
     * 作者：MaoSF
     * 日期：2017/5/15
     *
     * @param dto {companyId ,projectId}
     * @return
     */
    @Override
    public List<ProjectIssueTaskDTO> getOperatorTaskList(QueryProjectTaskDTO dto) {
        return this.sqlSession.selectList("GetProjectTaskForOperatorMapper.getOperatorTaskList",dto) ;
    }

    /**
     * 方法描述：获取任务基本信息（用于编辑）
     * 作者：MaoSF
     * 日期：2017/5/18
     *
     * @param id
     * @param:type
     * @return:
     */
    @Override
    public ProjectTaskDTO getProjectTaskByIdForEdit(String id,int type) {
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("type",type);
        return  this.sqlSession.selectOne("GetProjectTaskMapper.getProjectTaskByIdForEdit",map) ;
    }

    /**
     * 方法描述：根据参数查询
     * 作者：MaoSF
     * 日期：2017/5/16
     *
     * @param map
     */
    @Override
    public List<ProjectTaskEntity> selectByParam(Map<String, Object> map) {
        return this.sqlSession.selectList("ProjectTaskEntityMapper.selectByParam",map);
    }

    /**
     * 作用：复位已完成状态
     * 作者：ZCL
     * 日期：2017-5-20
     *
     * @param taskId
     */
    @Override
    public void resetTaskCompleteStatus(String taskId) throws Exception {
        sqlSession.selectList("ProjectTaskEntityMapper.resetTaskCompleteStatus",taskId);
    }
    @Override
    public void resetProcessNodeCompleteStatus(String processNodeId) throws Exception {
        sqlSession.selectList("ProjectTaskEntityMapper.resetProcessNodeCompleteStatus",processNodeId);
    }

    /**
     * 方法描述：获取某项目内某承包方的任务
     * 作者：张成亮
     * 日期：2017/4/11
     */
    @Override
    public List<String> getProjectTaskOfToCompany(String projectId, String companyId) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("companyId",companyId);
        return this.sqlSession.selectList("GetProjectTaskMapper.getProjectTaskOfToCompany",map) ;
    }


    /**
     * 方法描述：获取最大的seq
     * 作者：MaoSF
     * 日期：2017/6/9
     */
    @Override
    public int getProjectTaskMaxSeq(String projectId, String taskPid) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("taskPid",taskPid);
        return this.sqlSession.selectOne("ProjectTaskEntityMapper.getProjectTaskMaxSeq",map);
    }

    /**
     * 作用：获取任务状态
     * 作者：ZCL
     * 日期：2017-5-24
     *
     * @param taskId 任务编号
     */
    @Override
    public int getTaskState(String taskId) {
        Integer state = sqlSession.selectOne("ProjectTaskEntityMapper.getTaskState",taskId);
        return state==null?0:state.intValue();
    }
    @Override
    public String getStateText(Integer taskState, Date startTime, Date endTime, Date completeDate){
        return getStateText(taskState, DateUtils.date2Str(startTime,DateUtils.date_sdf),
                DateUtils.date2Str(endTime,DateUtils.date_sdf),DateUtils.date2Str(completeDate,DateUtils.date_sdf));
    }

    @Override
    public String getStateText(Integer taskState,String startTime,String endTime,String completeDate){
        if ((taskState==null) || (endTime==null)) return null;
        String stateStr;
        switch (taskState) {
            case 1:
                stateStr = "进行中";
                break;
            case 2:
                stateStr = "超时进行"; // + (DateUtils.daysOfTwo(DateUtils.date2Str(DateUtils.date_sdf),endTime)) + "天";
                break;
            case 3:
                /*if ((completeDate!=null) && (startTime!=null)){
                    stateStr = "用时" + (DateUtils.daysOfTwo(completeDate,startTime)+1) + "天完成";
                } else*/ {
                stateStr = "已完成";
            }
            break;
            case 4:
                /*if (completeDate!=null){
                    stateStr = "超时" + (DateUtils.daysOfTwo(completeDate,endTime)) + "天完成";
                }else*/ {
                stateStr = "超时完成";
            }
            break;
            case 5:
                stateStr = "未开始";
                break;
            case 6:
                //stateStr = "剩余" + (DateUtils.daysOfTwo(endTime,DateUtils.date2Str(DateUtils.date_sdf))+1) + "天";
                stateStr = "进行中";
                break;
            case 7:
                stateStr = "未发布";
                break;
            default:
                stateStr = "--";
        }
        return stateStr;
    }

    /**
     * 方法描述：发布当前记录后，修改该任务下的子集的taskPid，taskPath(重新设置为正式记录的taskPid)
     * param：publishId:被发布记录的ID，taskPid：新的taskPid,新的taskPath=parentPath+id
     * 作者：MaoSF
     * 日期：2017/6/23
     */
    @Override
    public int updateModifyTaskPid(String publishId, String taskPid, String parentPath) {
        Map<String,Object> map = new HashMap<>();
        map.put("publishId",publishId);
        map.put("taskPid",taskPid);
        map.put("parentPath",parentPath);
        return this.sqlSession.update("ProjectTaskEntityMapper.updateModifyTaskPid",map);
    }

    /**
     * 方法描述：生产安排（数据请求）
     * 作者：MaoSF
     * 日期：2017/6/28
     */
    @Override
    public ProjectTaskDTO getProductTaskForEdit(String id) {
        return this.sqlSession.selectOne("GetProjectTaskMapper.getProductTaskForEdit",id);
    }

    /**
     * 方法描述：获取设计内容（立项方（type=1）：设计阶段，合作方（type=2 and companyId），签发的数据）
     * 作者：MaoSF
     * 日期：2017/6/28
     */
    @Override
    public String getIssueTaskName(String projectId, String companyId, int type) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("companyId",companyId);
        map.put("type",type);
        return this.sqlSession.selectOne("GetProjectTaskMapper.getIssueTaskName",map);
    }

    /**
     * 方法描述：获取我负责的任务
     * 作者：MaoSF
     * 日期：2017/6/30
     * @param:notComplete可以为null，其他不可以为null
     */
    @Override
    public List<ProjectTaskEntity> getMyResponsibleTask(String projectId, String companyId, String companyUserId, String notComplete) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("companyId",companyId);
        map.put("companyUserId",companyUserId);
        map.put("notComplete",notComplete);
        return sqlSession.selectList("ProjectTaskEntityMapper.getMyResponsibleTask",map);
    }

    /**
     * 方法描述：查找未完成的子任务
     * 作者：ZhangChengliang
     * 日期：2017/6/22
     *
     * @param taskPid 任务ID
     */
    @Override
    public List<ProjectTaskEntity> listUnCompletedTask(String taskPid) {
        return sqlSession.selectList("ProjectTaskEntityMapper.listUnCompletedTask",taskPid);
    }
}