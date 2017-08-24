package com.maoding.mytask.dao;


import com.maoding.core.base.dao.BaseDao;
import com.maoding.mytask.dto.MyTaskListDTO;
import com.maoding.mytask.dto.MyTaskQueryDTO;
import com.maoding.mytask.entity.MyTaskEntity;

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
      * @param:
      * @return:
      */
     List<MyTaskEntity> getMyTaskByParam(Map<String, Object> param);

    /**
     * 方法描述：获取任务的总条数
     * 作者：MaoSF
     * 日期：2017/5/4
     * @param:
     * @return:
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
      * @param:
      * @return:
      */
     List<MyTaskListDTO> getMyTaskList(Map<String, Object> param);

    /**
     * 方法描述：根据项目id任务列表
     * 作者：MaoSF
     * 日期：2017/5/4
     */
    List<MyTaskEntity> getMyTaskByProjectId(Map<String, Object> param);

    /**
     * 作用：根据任务ID获取个人任务
     * 作者：ZCL
     * 日期：2017-5-20
     *
     */
    MyTaskEntity getMyTaskByTaskId(MyTaskQueryDTO dto) throws Exception;
}
