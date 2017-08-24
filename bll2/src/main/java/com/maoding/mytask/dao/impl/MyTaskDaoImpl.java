package com.maoding.mytask.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.mytask.dao.MyTaskDao;
import com.maoding.mytask.dto.MyTaskListDTO;
import com.maoding.mytask.dto.MyTaskQueryDTO;
import com.maoding.mytask.entity.MyTaskEntity;
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
     *
     * @param targetId
     * @param:
     * @return:
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

    /**
     * 作用：根据任务ID获取个人任务
     * 作者：ZCL
     * 日期：2017-5-20
     *
     * @param dto
     */
    @Override
    public MyTaskEntity getMyTaskByTaskId(MyTaskQueryDTO dto) throws Exception {
        Map<String,Object> query = new HashMap<>();
        query.put("handlerId",dto.getUserId());
        query.put("companyId",dto.getCompanyId());
        query.put("targetId",dto.getTaskId());
        query.put("status",dto.getStatus());
        query.put("taskType",13);
        List<MyTaskEntity> list = sqlSession.selectList("GetMyTaskByPage.getMyTaskByParam",query);
        if (list.size() > 0){
            return list.get(0);
        } else {
            return null;
        }
    }
}
