package com.maoding.process.service;


import com.maoding.financial.dto.AuditDTO;
import com.maoding.financial.dto.AuditEditDTO;
import com.maoding.financial.dto.SaveExpMainDTO;
import com.maoding.org.dto.CompanyUserAppDTO;
import com.maoding.process.dto.ActivitiDTO;
import com.maoding.process.dto.TaskDTO;
import com.maoding.process.dto.UserTaskNodeDTO;

import java.util.List;
import java.util.Map;

public interface ProcessService {

    /**
     * 启动流程实例
     * @param dto -> processKey:act_re_procdef 中的key_
     * @param dto -> businessKey:业务表中的id（比如中报销审批启动流程，businessKey = expMain 表中的id)
     * @param dto -> param:启动流程的时候，携带的参数，可以是空
     */
    void startProcessInstance(ActivitiDTO dto) throws Exception;

    /**
     * 是否需要启动流程
     */
    boolean isNeedStartProcess(ActivitiDTO dto) throws Exception;
    /**
     * 任务签收
     */
    void claimTask(TaskDTO dto) throws Exception;

    /**
     * 任务完成(直接完成activiti中的任务，id 为ru_task 中的id)
     */
    void completeTask(TaskDTO dto) throws Exception;

    /**
     * 任务完成(直接完成审核表中的任务（audit），id 为exp_audit 中的id)
     */
    boolean completeTask2(TaskDTO dto) throws Exception;

    /**
     * id:审批记录的id
     */
    Map<String,Object> getCurrentProcess(AuditEditDTO dto);

    Map<String,Object> getCurrentTaskUser(AuditEditDTO dto,List<AuditDTO> auditList,String value) throws Exception;

    List<UserTaskNodeDTO> getUserListForAudit(AuditEditDTO dto);

    /**
     * 流程挂起
     * 用于单据撤销
     */
    int suspendProcess(SaveExpMainDTO dto);
}
