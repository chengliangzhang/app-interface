package com.maoding.task.service.impl;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.util.BeanUtilsEx;
import com.maoding.core.util.StringUtil;
import com.maoding.dynamic.dao.ZInfoDAO;
import com.maoding.dynamic.dto.ZTaskDTO;
import com.maoding.dynamic.service.DynamicService;
import com.maoding.message.service.MessageService;
import com.maoding.mytask.service.MyTaskService;
import com.maoding.task.dao.ProjectProcessTimeDao;
import com.maoding.task.dao.ProjectTaskDao;
import com.maoding.task.dto.ProjectProcessTimeDTO;
import com.maoding.task.dto.SaveProjectTaskDTO;
import com.maoding.task.dto.TaskWithFullNameDTO;
import com.maoding.task.entity.ProjectProcessTimeEntity;
import com.maoding.task.entity.ProjectTaskEntity;
import com.maoding.task.service.ProjectProcessTimeService;
import com.maoding.task.service.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectProcessTimeServiceImpl
 * 类描述：项目变更
 * 作    者：MaoSF
 * 日    期：2016年12月28日-下午5:28:54
 */
@Service("projectProcessTimeService")
public class ProjectProcessTimeServiceImpl extends GenericService<ProjectProcessTimeEntity>  implements ProjectProcessTimeService {

    @Autowired
    private ProjectProcessTimeDao projectProcessTimeDao;

    @Autowired
    private ProjectTaskDao projectTaskDao;

    @Autowired
    private DynamicService dynamicService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ZInfoDAO zInfoDAO;

    @Autowired
    private ProjectTaskService projectTaskService;

    /**
     * 方法描述：保存变更
     * 作者：MaoSF
     * 日期：2016/12/30
     */
    @Override
    public ResponseBean saveProjectProcessTime(ProjectProcessTimeDTO dto) throws Exception {
        String taskId = dto.getTargetId();
        if(StringUtil.isNullOrEmpty(dto.getStartTime())){
            return ResponseBean.responseError("开始时间不能为空");
        }
        if(StringUtil.isNullOrEmpty(dto.getStartTime())){
            return ResponseBean.responseError("结束时间不能为空");
        }
        ProjectTaskEntity taskEntity = this.projectTaskDao.selectById(taskId);
        if(taskEntity==null){
            return ResponseBean.responseError("没有对应的任务");
        }
        //保存原有任务
        TaskWithFullNameDTO origin = zInfoDAO.getTaskByTaskId(taskId);
        //修改任务时间
        taskEntity.setStartTime(dto.getStartTime());
        taskEntity.setEndTime(dto.getEndTime());
        this.projectTaskService.updateById(taskEntity);
        if ((origin != null) && (origin.getId() == null)) origin = null;
        ProjectProcessTimeEntity entity = new ProjectProcessTimeEntity();
        BaseDTO.copyFields(dto,entity);
        if (StringUtil.isNullOrEmpty(dto.getId())) {
            entity.setId(StringUtil.buildUUID());
            entity.setCompanyId(dto.getAppOrgId());
            entity.setCreateBy(dto.getAccountId());
            entity.setCreateDate(new Date());
            entity.setTargetId(taskId);
            this.projectProcessTimeDao.insert(entity);
        }else {
            entity.setUpdateBy(dto.getAccountId());
            projectProcessTimeDao.updateById(entity);
        }

        //如果是阶段或生产任务，更改时间时直接发送消息，签发任务在发布时发送消息
        TaskWithFullNameDTO targetEx = (origin != null) ? ((TaskWithFullNameDTO) origin.clone()) : zInfoDAO.getTaskByTask(taskEntity);
        targetEx.setTaskPeriod(zInfoDAO.getPeriodByTime(entity));
        //TODO 发送消息给相关人员
        messageService.sendMessage(origin,targetEx,null,targetEx.getProjectId(),entity.getCompanyId(),dto.getAccountId());

        //添加项目动态
        TaskWithFullNameDTO target;
        if (origin != null){
            target = new TaskWithFullNameDTO();
            BeanUtilsEx.copyProperties(origin,target);
            target.setTaskPeriod(zInfoDAO.getPeriodByTime(entity));
        } else {
            target = zInfoDAO.getTaskByTime(entity);
            if ((target != null) && (target.getId() == null)) target = null;
        }
        dynamicService.addDynamic(origin,target,dto.getCompanyId(),dto.getAccountId());
        return ResponseBean.responseSuccess("保存成功");
    }

    /**
     * 方法描述：保存变更
     * 作者：MaoSF
     * 日期：2016/12/30
     *
     * @param dto
     * @param:
     * @return:
     */
    public ResponseBean saveProjectProcessTime_publish(ProjectProcessTimeDTO dto) throws Exception {
        String taskId = dto.getTargetId();
        if(StringUtil.isNullOrEmpty(dto.getStartTime())){
            return ResponseBean.responseError("开始时间不能为空");
        }
        if(StringUtil.isNullOrEmpty(dto.getStartTime())){
            return ResponseBean.responseError("结束时间不能为空");
        }
        ProjectTaskEntity taskEntity = this.projectTaskDao.selectById(taskId);
        if(taskEntity==null){
            return ResponseBean.responseError("没有对应的任务");
        }
        //修改任务时间
        taskEntity.setStartTime(dto.getStartTime());
        taskEntity.setEndTime(dto.getEndTime());
        projectTaskDao.updateById(taskEntity);

        //把任务改为未发布状态(生产的时间修改经过此方法)
        if(taskEntity.getTaskType()!=1){
            if(taskEntity.getTaskType()!= 3 && taskEntity.getTaskType()!= 4){//则新增一条被修改的记录
                taskId = projectTaskService.copyProjectTask(new SaveProjectTaskDTO(),taskEntity);
            }
            taskEntity.setId(taskId);
            taskEntity.setTaskStatus("2");
        }

        this.projectTaskService.updateById(taskEntity);

        //保存原有任务
        TaskWithFullNameDTO origin = zInfoDAO.getTaskByTaskId(taskId);
        if ((origin != null) && (origin.getId() == null)) origin = null;

        ProjectProcessTimeEntity entity = new ProjectProcessTimeEntity();
        BaseDTO.copyFields(dto,entity);
        if (StringUtil.isNullOrEmpty(dto.getId())){
            entity.setId(StringUtil.buildUUID());
            entity.setCompanyId(dto.getAppOrgId());
            entity.setCreateBy(dto.getAccountId());
            entity.setCreateDate(new Date());
            entity.setTargetId(taskId);
            this.projectProcessTimeDao.insert(entity);
        }else {
            this.projectProcessTimeDao.updateById(entity);
        }
        return ResponseBean.responseSuccess("保存成功");
    }

    /**
     * 方法描述：获取任务的变更时间（计划时间）
     * 作者：MaoSF
     * 日期：2016/12/30
     */
    @Override
    public ResponseBean getProjectProcessTime(Map<String,Object> map) throws Exception {

        ProjectTaskEntity taskEntity = this.projectTaskDao.selectById(map.get("targetId"));
        List<ProjectProcessTimeEntity> projectProcessTimeList = projectProcessTimeDao.getProjectProcessTime(map);
        return ResponseBean.responseSuccess("").addData("projectProcessTimeList",projectProcessTimeList)
                .addData("taskName",taskEntity.getTaskName());
    }

    /**
     * 方法描述：删除变更
     * 作者：MaoSF
     * 日期：2016/12/31
     */
    @Override
    public ResponseBean deleteProjectProcessTime(String id) throws Exception {
        projectProcessTimeDao.deleteById(id);
        return ResponseBean.responseSuccess("删除成功");
    }
}
