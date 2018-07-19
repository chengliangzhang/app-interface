package com.maoding.mytask.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.core.base.dto.QueryDTO;
import com.maoding.mytask.dao.MyTaskDao;
import com.maoding.mytask.dto.*;
import com.maoding.mytask.entity.MyTaskEntity;
import com.maoding.project.dto.ProjectProgressDTO;
import com.maoding.task.dto.ApproveCount;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：MyTaskDaoImpl
 * 类描述：我的任务DaoImpl
 * 作    者：MaoSF
 * 日    期：2016年11月30日-下午3:10:45
 */
@Service("myTaskDao")
public class MyTaskDaoImpl extends GenericDao<MyTaskEntity> implements MyTaskDao {


    /**
     * 方法描述：根据参数查询我的任务（companyId,companyUserId)
     * 作者：MaoSF
     * 日期：2016/12/1
     */
    @Override
    public List<MyTaskEntity> getMyTaskByParam(Map<String, Object> param) {
        return this.sqlSession.selectList("MyTaskEntityMapper.getMyTaskByParam",param);
    }

    /**
     * 方法描述：获取任务的总条数
     * 作者：MaoSF
     * 日期：2017/5/4
     */
    @Override
    public int getMyTaskCount() {
        return this.sqlSession.selectOne("MyTaskEntityMapper.getMyTaskCount");
    }

    /**
     * 方法描述：更改任务的状态
     * 作者：MaoSF
     * 日期：2017/1/6
     */
    @Override
    public int updateStatesByTargetId(MyTaskEntity entity) {
        return this.sqlSession.update("MyTaskEntityMapper.updateByTargetId2",entity);
    }

    /**
     * 方法描述：根据targetId修改状态
     * 作者：MaoSF
     * 日期：2016/12/1
     */
    @Override
    public int updateStatesByTargetId(Map<String,Object> paraMap){
        return this.sqlSession.update("MyTaskEntityMapper.updateByTargetId",paraMap);
    }

    /**
     * 方法描述：更改param4的值（用于删除项目）
     * 作者：MaoSF
     * 日期：2017/3/29
     */
    @Override
    public int deleteProjectTask(String targetId) {
        return this.sqlSession.update("MyTaskEntityMapper.deleteProjectTask",targetId);
    }

    /**
     * 方法描述：更改param4的值（忽略任务使用）
     * 作者：MaoSF
     * 日期：2017/3/29
     */
    @Override
    public int deleteMyTask(Map<String, Object> param) {
        return this.sqlSession.update("MyTaskEntityMapper.deleteMyTask",param);
    }

    /**
     * 方法描述：任务列表
     * 作者：MaoSF
     * 日期：2017/5/4
     */
    @Override
    public List<MyTaskListDTO> getMyTaskList(Map<String, Object> param) {
        return this.sqlSession.selectList("GetMyTaskByPageMapper.getMyTaskList",param);
    }


    /**
     * 方法描述：根据项目id任务列表
     * 作者：MaoSF
     * 日期：2017/5/4
     */
    @Override
    public List<MyTaskEntity> getMyTaskByProjectId(Map<String, Object> param) {
        return this.sqlSession.selectList("GetMyTaskByPageMapper.getMyTaskByProjectId",param);
    }

    @Override
    public List<MyTaskEntity> getMyTaskByProjectId2(Map<String, Object> param) {
        return this.sqlSession.selectList("GetMyTaskByPageMapper.getMyTaskByProjectId2",param);
    }

    /**
     * 方法描述：根据项目id任务列表
     * 作者：MaoSF
     * 日期：2017/5/4
     */
    @Override
    public List<MyTaskEntity> getMyExpTask(Map<String, Object> param) {
        return this.sqlSession.selectList("GetMyTaskByPageMapper.getMyExpTask",param);
    }

    @Override
    public MyTaskCountDTO selectMyTaskCount(Map<String, Object> param) {
        MyTaskCountDTO result =this.getMyTaskCount(param);
        MyTaskCountDTO overCount = this.getOvertimeCount(param);
        if(result==null){
            result = new MyTaskCountDTO();
        }
        if(overCount!=null){
            result.setOvertimeCount(overCount.getOvertimeCount());
        }
        return  result;
    }

    @Override
    public MyTaskCountDTO getMyTaskCount(Map<String, Object> param) {
        return this.sqlSession.selectOne("GetMyTaskByPageMapper.getMyTaskCount",param);
    }

    @Override
    public MyTaskCountDTO getOvertimeCount(Map<String, Object> param) {
        return this.sqlSession.selectOne("GetMyTaskByPageMapper.getOvertimeCount",param);
    }

    public List<MyTaskDTO> getOvertimeTask(Map<String, Object> param) {
        return this.sqlSession.selectList("GetMyTaskByPageMapper.getOvertimeTask",param);
    }


    public List<MyTaskDTO> getDueTask(Map<String, Object> param) {
        return this.sqlSession.selectList("GetMyTaskByPageMapper.getDueTask",param);
    }

    @Override
    public ProjectProgressDTO getProjectTaskCount(Map<String, Object> param) {
        return this.sqlSession.selectOne("GetMyTaskByPageMapper.getProjectTaskCount",param);
    }

    @Override
    public ApproveCount getTaskCount(Map<String, Object> param) {
        return this.sqlSession.selectOne("GetMyTaskByPageMapper.getTaskCount",param);
    }

    @Override
    public List<TaskDataDTO> getMySubmitTask(QueryDTO dto) {
        return this.sqlSession.selectList("GetMyTaskByPageMapper.getMySubmitTask",dto);
    }

    @Override
    public TaskDetailDTO getMySubmitTaskById(String id) {
        return this.sqlSession.selectOne("GetMyTaskByPageMapper.getMySubmitTaskById",id);
    }

    /**
     * 查询个人任务
     *
     * @param query
     */
    @Override
    public List<MyTaskDTO> listMyTask(QueryMyTaskDTO query) {
        return this.sqlSession.selectList("GetMyTaskByPageMapper.listMyTask",query);
    }

    /**
     * @param myTask 要修改的字段，如果为null则不修改
     * @param query  要修改的条件
     * @author 张成亮
     * @date 2018/7/18
     * @description 更新个人任务
     **/
    @Override
    public void updateByQuery(MyTaskEntity myTask, MyTaskQueryDTO query) {
        Map<String,Object> param = new HashMap<>();
        param.put("myTask",myTask);
        param.put("query",query);
        sqlSession.update("MyTaskEntityMapper.updateByQuery",param);
    }

    /**
     * @param query 查询的条件
     * @return 个人任务列表
     * @author 张成亮
     * @date 2018/7/18
     * @description 查询个人任务
     **/
    @Override
    public List<MyTaskEntity> listByQuery(MyTaskQueryDTO query) {
        return sqlSession.selectList("MyTaskEntityMapper.listByQuery",query);
    }
}
