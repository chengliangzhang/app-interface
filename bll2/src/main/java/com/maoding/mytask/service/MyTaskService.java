package com.maoding.mytask.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.ResponseBean;
import com.maoding.mytask.dto.MyTaskListDTO;
import com.maoding.mytask.entity.MyTaskEntity;

import java.util.List;
import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：NoticeService
 * 类描述：我的任务Service
 * 作    者：MaoSF
 * 日    期：2016年11月30日-下午3:10:45
 */
public interface MyTaskService extends BaseService<MyTaskEntity> {
    /**
     * 方法描述：根据参数查询我的任务（companyId,companyUserId)（此方法，不查询财务数据）
     * 作者：MaoSF
     * 日期：2016/12/1
     * @param:
     * @return:
     */
    List<MyTaskEntity> getMyTaskByParam(Map<String, Object> param) throws Exception;


    /**
     * 方法描述：我的任务列表（companyId,accountId)
     * 作者：MaoSF
     * 日期：2016/12/1
     */
    List<MyTaskEntity> getMyTask(Map<String, Object> param) throws Exception;

    /**
     * 方法描述：查询任务列表
     * 作者：MaoSF
     * 日期：2017/05/04
     */
    List<MyTaskListDTO> getMyTaskList(Map<String, Object> param) throws Exception;


    /**
     * 方法描述：根据参数查询我的任务（companyId,companyUserId)
     * 作者：MaoSF
     * 日期：2016/12/1
     */
    ResponseBean getMyTaskDTO(Map<String, Object> param) throws Exception;

    /**
     * 方法描述：根据参数查询我的任务（companyId,companyUserId，projectId)
     * 作者：MaoSF
     * 日期：2016/12/1
     */
    ResponseBean getMyTaskByProjectId(Map<String, Object> param) throws Exception;


    /**
     * 方法描述：保存我的任务（发送给指定的人）
     * 作者：MaoSF
     * 日期：2016/12/21
     */
    ResponseBean saveMyTask(String targetId, int taskType, String companyId, String companyUserId) throws Exception;

    /**
     * 方法描述：保存我的任务（发送给指定的人）
     * 作者：MaoSF
     * 日期：2016/12/21
     */
    ResponseBean saveMyTask(String targetId, int taskType, String companyId, String companyUserId,boolean isSendMessage) throws Exception;

    /**
     * 方法描述：技术审查费付款确认，合作技术费付款确认（taskType=4 or 6）
     * 作者：MaoSF
     * 日期：2017/3/6
     */
    ResponseBean saveMyTask(String targetId, int taskType, String companyId) throws Exception;


    /**
     * 方法描述：保存我的任务（更换系统中某特定职务的人之后，把所有的任务移交给新人）
     * 作者：MaoSF
     * 日期：2016/12/21
     */
    ResponseBean saveMyTask(MyTaskEntity entity, boolean isSendMessage) throws Exception;

    /**
     * 方法描述：处理我的任务(taskType==0)
     * 作者：MaoSF
     * 日期：2016/12/21
     */
    ResponseBean handleMyTask(String id, String result, String status,String accountId) throws Exception;

    /**
     * 方法描述：处理我的任务
     * 作者：MaoSF
     * 日期：2016/12/21
     */
    ResponseBean handleMyTask(String id, String result, String status, String accountId, String paidDate) throws Exception;


    /**
     * 方法描述：处理我的任务
     * 作者：MaoSF
     * 日期：2016/12/21
     * @param:
     * @return:
     */
    ResponseBean handleMyTask(String targetId, int taskType, String companyUserId) throws Exception;

    /**
     * 方法描述：处理我的任务(专门处理报销任务)
     * 作者：MaoSF
     * 日期：2016/12/21
     * @param:
     * @return:
     */
    ResponseBean handleMyTask(String targetId,  String companyUserId,String param2) throws Exception;

    /**
     * 方法描述：忽略我的任务
     * 作者：MaoSF
     * 日期：2016/12/21
     * @param:
     * @return:
     */
    ResponseBean ignoreMyTask(String targetId, int taskType, String companyUserId) throws Exception;

    /**
     * 方法描述：忽略我的任务（删除了该节点后，任务全部忽略）
     * 作者：MaoSF
     * 日期：2016/12/21
     * @param:
     * @return:
     */
    ResponseBean ignoreMyTask(String targetId) throws Exception;


    /**
     * 方法描述：查询我的任务详情
     * 作者：MaoSF
     * 日期：2017/1/11
     * @param:
     * @return:
     */
    ResponseBean getMyTaskDetail(String id,String accountId) throws Exception;

    /**
     * 作用：激活已完成的任务
     * 作者：ZCL
     * 日期：2017-5-20
     * @param entity 激活申请
     * @return 0：正常激活，-1：无法激活
     * @throws Exception
     */
    int activeMyTask(MyTaskEntity entity,String accountId) throws Exception;
}
