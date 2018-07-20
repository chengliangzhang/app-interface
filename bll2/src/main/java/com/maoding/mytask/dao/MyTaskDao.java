package com.maoding.mytask.dao;


import com.maoding.core.base.dao.BaseDao;
import com.maoding.core.base.dto.QueryDTO;
import com.maoding.mytask.dto.*;
import com.maoding.mytask.entity.MyTaskEntity;
import com.maoding.project.dto.ProjectProgressDTO;
import com.maoding.task.dto.ApproveCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Idccapp21 on 2016/10/18.
 */
public interface MyTaskDao extends BaseDao<MyTaskEntity> {

     /**
      * 方法描述：根据参数查询我的任务（companyId,companyUserId)
      * 作者：MaoSF
      * 日期：2016/12/1
      */
     List<MyTaskEntity> getMyTaskByParam(Map<String, Object> param);

    /**
     * 方法描述：获取任务的总条数
     * 作者：MaoSF
     * 日期：2017/5/4
     */
     int getMyTaskCount();

     /**
      * 方法描述：更改任务的状态
      * 作者：MaoSF
      * 日期：2017/1/6
      */
     int updateStatesByTargetId(MyTaskEntity entity);

    /**
     * 方法描述：更改任务的状态
     * 作者：MaoSF
     * 日期：2017/1/6
     */
    int updateStatesByTargetId(Map<String,Object> param);

     /**
      * 方法描述：更改param4的值（用于删除项目）
      * 作者：MaoSF
      * 日期：2017/3/29
      */
     int deleteProjectTask(String targetId);


    /**
     * 方法描述：更改param4的值（忽略任务使用）
     * 作者：MaoSF
     * 日期：2017/3/29
     */
    int deleteMyTask(Map<String,Object> param);

     //--------------------------------

     /**
      * 方法描述：任务列表
      * 作者：MaoSF
      * 日期：2017/5/4
      */
     List<MyTaskListDTO> getMyTaskList(Map<String, Object> param);

    /**
     * 方法描述：根据项目id任务列表
     * 作者：MaoSF
     * 日期：2017/5/4
     */
    List<MyTaskEntity> getMyTaskByProjectId(Map<String, Object> param);

    /**
     * 新接口
     */
    List<MyTaskEntity> getMyTaskByProjectId2(Map<String, Object> param);
    /**
     * 方法描述：获取我的报销任务列表
     * 作者：MaoSF
     * 日期：2017/5/4
     */
    List<MyTaskEntity> getMyExpTask(Map<String, Object> param);

    /**
     *
     * @param param(handlerId,isHandler)
     * 获取我的任务的总数，已完成数。已超时数
     */
    MyTaskCountDTO selectMyTaskCount(Map<String, Object> param);

    /**
     *
     * @param param(handlerId,isHandler)
     * 获取我的任务的总数，已完成数
     */
    MyTaskCountDTO getMyTaskCount(Map<String, Object> param);

    /**
     *
     * @param param(handlerId)
     */
    MyTaskCountDTO getOvertimeCount(Map<String, Object> param);

    /**
     * 超时任务
     */
    List<MyTaskDTO> getOvertimeTask(Map<String, Object> param);

    /**
     * 超时任务
     */
    List<MyTaskDTO> getDueTask(Map<String, Object> param);

    /**
     * 项目任务统计 （projectId，companyId，isCooperator：是否是合作组织，可选项）
     */
    ProjectProgressDTO getProjectTaskCount(Map<String, Object> param);

    ApproveCount getTaskCount(Map<String,Object> param);

    /**
     * 我提交的轻量型任务
     */
    List<TaskDataDTO> getMySubmitTask(QueryDTO dto);

    TaskDetailDTO getMySubmitTaskById(String id);

    /** 查询个人任务 */
    List<MyTaskDTO> listMyTask(QueryMyTaskDTO query);

    /**
     * @author  张成亮
     * @date    2018/7/18
     * @description     更新个人任务
     * @param   myTask 要修改的字段，如果为null则不修改
     * @param   query 要修改的条件
     **/
    void updateByQuery(@Param("myTask") MyTaskEntity myTask, @Param("query") MyTaskQueryDTO query);

    /**
     * @author  张成亮
     * @date    2018/7/18
     * @description     查询个人任务
     * @param   query 查询的条件
     * @return  个人任务列表
     **/
    List<MyTaskEntity> listEntityByQuery(MyTaskQueryDTO query);
}
