package com.maoding.project.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.project.dto.ProjectProcessDataDTO;
import com.maoding.project.dto.ProjectProcessUserDTO;
import com.maoding.project.entity.ProjectProcessEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by Wuwq on 2016/10/27.
 */
public interface ProjectProcessDao extends BaseDao<ProjectProcessEntity> {

     /**
      * 方法描述：根据任务id查询流程列表
      * 作者：MaoSF
      * 日期：2016/12/5
      * @param:
      * @return:
      */
     List<ProjectProcessDataDTO> selectByTaskManageId(String taskManageId);

     /**
      * 方法描述：根据任务id查询流程列表
      * 作者：MaoSF
      * 日期：2016/12/5
      * @param:
      * @return:
      */
     List<ProjectProcessDataDTO> selectByTaskManageId(String taskManageId,String processId);

     /**
      * 方法描述：根据流程节点id查询流程列表
      * 作者：MaoSF
      * 日期：2016/12/5
      * @param:
      * @return:
      */
     ProjectProcessDataDTO selectByNodeId(String nodeId);

     /**
      * 方法描述：根据任务id查询设计人的名字
      * 作者：MaoSF
      * 日期：2016/12/5
      * @param:
      * @return:
      */
     String selectDesignNameByTaskManageId(String taskManageId);

     /**
      * 方法描述：我的任务列表的数据
      * 作者：MaoSF
      * 日期：2016/12/5
      * @param:
      * @return:
      */
     List<ProjectProcessUserDTO> selectProessByUserId(Map<String, Object> paraMap);

     /**
      * 方法描述：我的任务列表总条数
      * 作者：MaoSF
      * 日期：2016/12/5
      * @param:
      * @return:
      */
     int selectProessCountByUserId(Map<String, Object> paraMap);


     /**
      * 方法描述：根据任务id批量修改流程的状态
      * 作者：MaoSF
      * 日期：2016/12/5
      * @param:
      * @return:
      */
     int updateByTaskManageId(Map<String, Object> paraMap);

     /**
      * 方法描述：我的任务项目
      * 作者：TangY
      * 日期：2016/10/31
      * @param:
      * @return:
      */
     List<ProjectProcessUserDTO> selectProessByUserIdGroupByProjectId(Map<String, Object> param);

     /**
      * 方法描述：根据任务id查询最大的seq值
      * 作者：MaoSF
      * 日期：2016/12/5
      * @param:
      * @return:
      */
     int getProcessMaxSeq(String taskManageId);


}
