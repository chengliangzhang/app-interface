package com.maoding.mytask.service.impl;

import com.maoding.conllaboration.SyncCmd;
import com.maoding.conllaboration.service.CollaborationService;
import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.dto.BaseShowDTO;
import com.maoding.core.base.dto.QueryDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.PermissionConst;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.*;
import com.maoding.deliver.dao.DeliverDao;
import com.maoding.deliver.dto.DeliverDTO;
import com.maoding.deliver.entity.DeliverEntity;
import com.maoding.deliver.service.DeliverService;
import com.maoding.dynamic.service.DynamicService;
import com.maoding.financial.dao.ExpMainDao;
import com.maoding.financial.dto.ExpMainDTO;
import com.maoding.financial.service.ExpMainService;
import com.maoding.message.entity.MessageEntity;
import com.maoding.message.service.MessageService;
import com.maoding.mytask.dao.MyTaskDao;
import com.maoding.mytask.dto.*;
import com.maoding.mytask.entity.MyTaskEntity;
import com.maoding.mytask.service.MyTaskService;
import com.maoding.org.dao.CompanyDao;
import com.maoding.org.dao.CompanyRelationAuditDao;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.dto.CompanyUserDataDTO;
import com.maoding.org.entity.CompanyEntity;
import com.maoding.org.entity.CompanyRelationAuditEntity;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.org.service.CompanyService;
import com.maoding.org.service.CompanyUserService;
import com.maoding.project.dao.ProjectDao;
import com.maoding.project.dao.ProjectProcessNodeDao;
import com.maoding.project.dto.ProjectSkyDriverQueryDTO;
import com.maoding.project.entity.ProjectEntity;
import com.maoding.project.entity.ProjectProcessNodeEntity;
import com.maoding.project.entity.ProjectSkyDriveEntity;
import com.maoding.project.service.ProjectProcessService;
import com.maoding.project.service.ProjectSkyDriverService;
import com.maoding.projectcost.dao.ProjectCostPaymentDetailDao;
import com.maoding.projectcost.dao.ProjectCostPointDao;
import com.maoding.projectcost.dao.ProjectCostPointDetailDao;
import com.maoding.projectcost.dto.ProjectCostPaymentDetailDTO;
import com.maoding.projectcost.dto.ProjectCostPointDataForMyTaskDTO;
import com.maoding.projectcost.entity.ProjectCostEntity;
import com.maoding.projectcost.entity.ProjectCostPaymentDetailEntity;
import com.maoding.projectcost.entity.ProjectCostPointDetailEntity;
import com.maoding.projectcost.entity.ProjectCostPointEntity;
import com.maoding.projectcost.service.ProjectCostService;
import com.maoding.projectmember.dto.ProjectMemberDTO;
import com.maoding.projectmember.entity.ProjectMemberEntity;
import com.maoding.projectmember.service.ProjectMemberService;
import com.maoding.role.service.PermissionService;
import com.maoding.task.dao.ProjectTaskDao;
import com.maoding.task.dto.ApproveCount;
import com.maoding.task.dto.HomeDTO;
import com.maoding.task.dto.ProjectTaskDTO;
import com.maoding.task.dto.ProjectTaskDetailDTO;
import com.maoding.task.entity.ProjectTaskEntity;
import com.maoding.task.service.ProjectTaskService;
import com.maoding.v2.project.dto.ProjectSkyDriveListDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：MyTaskServiceImpl
 * 类描述：我的任务ServiceImpl
 * 作    者：MaoSF
 * 日    期：2016年11月30日-下午3:10:45
 */
@Service("myTaskService")
public class MyTaskServiceImpl extends GenericService<MyTaskEntity> implements MyTaskService {

    @Autowired
    private MyTaskDao myTaskDao;

    @Autowired
    private DeliverDao deliverDao;

    @Autowired
    private DeliverService deliverService;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectTaskDao projectTaskDao;

    @Autowired
    private ProjectProcessNodeDao projectProcessNodeDao;

    @Autowired
    private ExpMainDao expMainDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ProjectCostPaymentDetailDao projectCostPaymentDetailDao;

    @Autowired
    private ProjectCostPointDao projectCostPointDao;

    @Autowired
    private ProjectMemberService projectMemberService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ProjectCostPointDetailDao projectCostPointDetailDao;

    @Autowired
    private CompanyRelationAuditDao companyRelationAuditDao;

    @Autowired
    private ProjectSkyDriverService projectSkyDriverService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ProjectTaskService projectTaskService;

    @Autowired
    private ProjectCostService projectCostService;

    @Autowired
    private CollaborationService collaborationService;

    @Autowired
    private ProjectProcessService projectProcessService;

    @Autowired
    private ExpMainService expMainService;

    @Autowired
    private DynamicService dynamicService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private CompanyUserService companyUserService;

    private String sperate = "<br/>";

    /**
     * 方法描述：根据参数查询我的任务（companyId,companyUserId)
     * 作者：MaoSF
     * 日期：2016/12/1
     */
    @Override
    public List<MyTaskEntity> getMyTaskByParam(Map<String, Object> param) throws Exception {
        if (param.get("status") == null) {//如果没有给status，则默认查询未处理的数据
            param.put("status", "0");
        }
        return this.myTaskDao.getMyTaskByParam(param);
    }


    /**
     * 方法描述：我的任务列表（companyId,accountId)
     * 作者：MaoSF
     * 日期：2016/12/1
     *
     * @param param
     * @param:
     * @return:
     */
    @Override
    public List<MyTaskEntity> getMyTask(Map<String, Object> param) throws Exception {
        CompanyUserEntity companyUser = this.companyUserDao.getCompanyUserByUserIdAndCompanyId((String) param.get("accountId"), (String) param.get("appOrgId"));
        if (companyUser != null) {
            param.put("handlerId", companyUser.getId());
        }
        if (null != param.get("pageIndex")) {
            int page = (Integer) param.get("pageIndex");
            int pageSize = (Integer) param.get("pageSize");
            param.put("startPage", page * pageSize);
            param.put("endPage", pageSize);
        }
        if (param.get("status") == null) {//如果没有给status，则默认查询未处理的数据
            param.put("status", "0");
        }

        if (!StringUtil.isNullOrEmpty(param.get("viewType"))) {
            List<String> typeList = new ArrayList<String>();
            String viewType = (String) param.get("viewType");

            if ("1".equals(viewType)) {
                typeList.add("1");
                typeList.add("14");
            }
            if ("2".equals(viewType)) {
                typeList.add("2");
                typeList.add("13");
                typeList.add("15");
                typeList.add("22");
            }
            if ("3".equals(viewType)) {
                typeList.add("3");
            }
            if ("4".equals(viewType)) {
                typeList.add("4");
                typeList.add("5");
                typeList.add("6");
                typeList.add("7");
                typeList.add("16");
                typeList.add("18");
                typeList.add("20");
            }
            if ("5".equals(viewType)) {
                typeList.add("8");
                typeList.add("9");
                typeList.add("10");
                typeList.add("17");
                typeList.add("19");
                typeList.add("21");
            }
            if ("6".equals(viewType)) {
                typeList.add("11");
                typeList.add("12");
            }
            if (!CollectionUtils.isEmpty(typeList)) {
                param.put("typeList", typeList);
            }
        }

        return this.getMyTaskByParam(param);
    }

    /**
     * 方法描述：查询任务列表
     * 作者：MaoSF
     * 日期：2017/05/04
     *
     * @param param
     * @param:
     * @return:
     */
    @Override
    public List<MyTaskListDTO> getMyTaskList(Map<String, Object> param) throws Exception {
        this.initParam(param);
        return this.myTaskDao.getMyTaskList(param);
    }

    /**
     * 初始化参数
     */
    private Map<String, Object> initParam(Map<String, Object> param) throws Exception{
        String companyId = (String)param.get("appOrgId");
        String userId = (String)param.get("accountId");
        CompanyUserEntity companyUser = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(userId, companyId);
        if (companyUser == null) {
            throw new Exception("参数错误");
        }
        param.put("handlerId", companyUser.getId());
        if (null != param.get("pageIndex")) {
            int page = (Integer) param.get("pageIndex");
            int pageSize = (Integer) param.get("pageSize");
            param.put("startPage", page * pageSize);
            param.put("endPage", pageSize);
        }
        param.put("permissionCodes",permissionService.getPermissionCodeByUserId(companyId,userId));

//        //当前成员是否是财务成员
//        if(permissionService.isFinancial(companyId,userId)){
//            param.put("isHandler","1");
//        }
//        //当前成员是否是财务成员
//        if(permissionService.isFinancialReceive(companyId,userId)){
//            param.put("isHandlerReceive","1");
//        }
        return param;
    }

    /**
     * 方法描述：根据参数查询我的任务（companyId,companyUserId)
     * 作者：MaoSF
     * 日期：2016/12/1
     *
     * @param param
     * @param:
     * @return:
     */
    @Override
    public ResponseBean getMyTaskDTO(Map<String, Object> param) throws Exception {
        String companyId = (String) param.get("appOrgId");
        if (StringUtil.isNullOrEmpty(companyId)) {
            return ResponseBean.responseSuccess("查询成功").addData("myTaskList", new ArrayList<>()).addData("count", 0);
        }
        List<MyTaskEntity> list = this.getMyTask(param);
        List<MyTaskDTO> dtoList = this.convertMyTask(list);
        return ResponseBean.responseSuccess("查询成功").addData("myTaskList", dtoList).addData("count", list.size());
    }

    private List<MyTaskDTO> convertMyTask(List<MyTaskEntity> data) throws Exception {
        List<MyTaskDTO> dtoList = new ArrayList<MyTaskDTO>();
        for (MyTaskEntity dto : data) {
            dtoList.add(convertMyTask(dto, null, dto.getCompanyId()));
        }
        return dtoList;
    }


    /**
     * 方法描述：myTaskEntity重新查找数据组装成dto
     * 作者：MaoSF
     * 日期：2017/1/6
     */
    private MyTaskDTO convertMyTask(MyTaskEntity entity, String accountId, String companyId) throws Exception {
        int taskType = entity.getTaskType();

        //暂时未处理
        switch (taskType) {
            case 1:
                return convertMyTask1(entity);
            case 2:
                return convertMyTask2(entity);
            case 3:
                return convertMyTask3(entity);
            case 4:
            case 5:
            case 6:
            case 7:
                return convertMyTask4(entity);
            case 8:
            case 9:
                return convertMyTask8(entity);
            case 10:
            case 20:
            case 21:
                return convertMyTask10(entity);
            case 11:
                return convertMyTask11(entity);
            case 12:
                return convertMyTask12(entity);
            case 13:
                return convertMyTask13(entity);
            case 14:
                return convertMyTask14(entity);
            case 15:
                return convertMyTask15(entity);
            case 16:
            case 17:
            case 18:
            case 19:
                return convertMyTask16(entity);
            case 22:
                return convertMyTask22(entity);
            default:
                return null;
        }
    }


    /**
     * 方法描述：任务经营人，签发
     * 作者：MaoSF
     * 日期：2017/1/9
     */
    private MyTaskDTO convertMyTask1(MyTaskEntity entity) throws Exception {
        MyTaskDTO dto = new MyTaskDTO();
        BaseDTO.copyFields(entity, dto);
        dto.setTaskTitle("签发任务");
        return dto;
    }

    /**
     * 方法描述：任务经营人，签发
     * 作者：MaoSF
     * 日期：2017/1/9
     */
    private MyTaskDTO convertMyTask2(MyTaskEntity entity) throws Exception {
        MyTaskDTO dto = new MyTaskDTO();
        BaseDTO.copyFields(entity, dto);
        ProjectEntity projectEntity = this.projectDao.selectById(entity.getProjectId());
        dto.setTaskTitle("生产安排");
        if (projectEntity != null) {
            dto.setTaskContent(projectEntity.getProjectName());
            if (!entity.getCompanyId().equals(projectEntity.getCompanyId())) {
                dto.setTaskMemo("合作设计项目");
            }
        }

        return dto;
    }

    /**
     * 方法描述：任务经营人，签发
     * 作者：MaoSF
     * 日期：2017/1/9
     *
     * @param:
     * @return:
     */
    private MyTaskDTO convertMyTask12(MyTaskEntity entity) throws Exception {
        MyTaskDTO dto = new MyTaskDTO();
        BaseDTO.copyFields(entity, dto);
        return dto;
    }

    /**
     * 方法描述：任务负责人的任务，设计人提交流程，项目经营人,配置权限进入的界面(taskType=3)
     * 作者：MaoSF
     * 日期：2017/1/9
     *
     * @param:
     * @return:
     */
    private MyTaskDTO convertMyTask3(MyTaskEntity entity) throws Exception {
        MyTaskDTO dto = new MyTaskDTO();
        BaseDTO.copyFields(entity, dto);
        //保存的时候就把任务的id设置在param1字段中
        ProjectTaskDTO taskDTO = this.projectTaskDao.getProjectTaskById(entity.getParam1(), entity.getCompanyId());
        if (taskDTO != null) {
            dto.setTaskTitle(taskDTO.getTaskName() + " — " + entity.getTaskContent());
            if (!StringUtil.isNullOrEmpty(taskDTO.getStartTime())) {
                this.projectTaskService.setTaskState(taskDTO);
                dto.setTaskState(taskDTO.getTaskState());
            }
        }
        return dto;
    }

    /**
     * 方法描述：技术审查费付款确认,合作设计费付款确认(type=4,5,6,7)
     * 作者：MaoSF
     * 日期：2017/1/9
     *
     * @param:
     * @return:
     */
    private MyTaskDTO convertMyTask4(MyTaskEntity entity) throws Exception {
        MyTaskDTO dto = new MyTaskDTO();
        BaseDTO.copyFields(entity, dto);
        if (entity.getTaskType() == 4 || entity.getTaskType() == 5) {
            dto.setTaskTitle("技术审查费,付款 ");
        } else {
            dto.setTaskTitle("合作设计费,付款");
        }
        return dto;
    }

    /**
     * 方法描述：技术审查费付款确认,合作设计费付款确认(type=8,9,10)
     * 作者：MaoSF
     * 日期：2017/1/9
     *
     * @param:
     * @return:
     */
    private MyTaskDTO convertMyTask8(MyTaskEntity entity) throws Exception {
        MyTaskDTO dto = new MyTaskDTO();
        BaseDTO.copyFields(entity, dto);
        ProjectEntity projectEntity = this.projectDao.selectById(entity.getProjectId());
        if (entity.getTaskType() == 8) {
            dto.setTaskTitle("到款 技术审查费:");
        }
        if (entity.getTaskType() == 9) {
            dto.setTaskTitle("到款 合作设计费:");
        }
        if (projectEntity != null) {
            dto.setTaskContent(projectEntity.getProjectName());
        }

        if ("1".equals(entity.getStatus())) {
            //完成的任务给个taskMemo

            String taskMemo = "";
            ProjectCostPointDetailEntity detailEntity = this.projectCostPointDetailDao.selectById(entity.getTargetId());
            if (detailEntity != null) {
                ProjectCostPointEntity pointEntity = this.projectCostPointDao.selectById(detailEntity.getPointId());
                if (pointEntity != null) {
                    taskMemo += pointEntity.getFeeDescription();
                    taskMemo += " · " + StringUtil.getRealData(detailEntity.getFee()) + "万元";
                    dto.setTaskMemo(taskMemo);
                }
            }
        }
        return dto;
    }

    /**
     * 方法描述：合同回款，其他费用到款，付款(type=10，20,21)
     * 作者：MaoSF
     * 日期：2017/1/9
     *
     * @param:
     * @return:
     */
    private MyTaskDTO convertMyTask10(MyTaskEntity entity) throws Exception {
        MyTaskDTO dto = new MyTaskDTO();
        BaseDTO.copyFields(entity, dto);
        if (entity.getTaskType() == 10) {
            dto.setTaskTitle("合同回款,确认回款");
        }
        if (entity.getTaskType() == 20) {
            dto.setTaskTitle("其他费用,确认付款");
        }
        if (entity.getTaskType() == 21) {
            dto.setTaskTitle("其他费用,确认收款");
        }
        return dto;
    }

    /**
     * 方法描述：报销单
     * 作者：MaoSF
     * 日期：2017/1/9
     *
     * @param:
     * @return:
     */
    private MyTaskDTO convertMyTask11(MyTaskEntity entity) throws Exception {
        MyTaskDTO dto = new MyTaskDTO();
        BaseDTO.copyFields(entity, dto);
        return dto;
    }

    /**
     * 方法描述：任务负责人的任务
     * 作者：MaoSF
     * 日期：2017/1/9
     *
     * @param:
     * @return:
     */
    private MyTaskDTO convertMyTask13(MyTaskEntity entity) throws Exception {
        MyTaskDTO dto = new MyTaskDTO();
        BaseDTO.copyFields(entity, dto);
        ProjectTaskEntity projectTaskEntity = this.projectTaskDao.selectById(entity.getTargetId());
        dto.setTaskTitle("生产安排");
        if (projectTaskEntity != null) {
            dto.setTaskTitle(projectTaskEntity.getTaskName() + " — 生产安排");
        }
        return dto;
    }

    /**
     * 方法描述：安排设计负责人的任务
     * 作者：MaoSF
     * 日期：2017/1/9
     *
     * @param:
     * @return:
     */
    private MyTaskDTO convertMyTask14(MyTaskEntity entity) throws Exception {
        MyTaskDTO dto = new MyTaskDTO();
        BaseDTO.copyFields(entity, dto);
        ProjectEntity projectEntity = this.projectDao.selectById(entity.getProjectId());
        dto.setTaskTitle("签发");
        if (projectEntity != null) {
            dto.setTaskContent(projectEntity.getProjectName());
        }

        return dto;
    }

    /**
     * 方法描述：安排设计负责人的任务
     * 作者：MaoSF
     * 日期：2017/1/9
     *
     * @param:
     * @return:
     */
    private MyTaskDTO convertMyTask15(MyTaskEntity entity) throws Exception {
        MyTaskDTO dto = new MyTaskDTO();
        BaseDTO.copyFields(entity, dto);
        ProjectEntity projectEntity = this.projectDao.selectById(entity.getProjectId());
        dto.setTaskTitle("生产安排");
        if (projectEntity != null) {
            dto.setTaskContent(projectEntity.getProjectName());
        }
        String pathName = this.projectTaskDao.getTaskParentName(entity.getTargetId());
        dto.setTaskMemo(pathName);
        return dto;
    }

    /**
     * 方法描述：技术审查费，合作设计费，财务--到款，付款
     * 作者：MaoSF
     * 日期：2017/1/9
     *
     * @param:
     * @return:
     */
    private MyTaskDTO convertMyTask16(MyTaskEntity entity) throws Exception {
        MyTaskDTO dto = new MyTaskDTO();
        BaseDTO.copyFields(entity, dto);
        if (dto.getTaskType() == 16) {
            dto.setTaskTitle("技术审查费,确认付款");
        }
        if (dto.getTaskType() == 17) {
            dto.setTaskTitle("技术审查费,确认收款");
        }
        if (dto.getTaskType() == 18) {
            dto.setTaskTitle("合作设计费,确认付款");
        }
        if (dto.getTaskType() == 19) {
            dto.setTaskTitle("合作设计费,确认收款");
        }
        return dto;
    }

    /**
     * 方法描述：type=22，设计任务完成后，给设计负责人推送的任务
     * 作者：MaoSF
     * 日期：2017/1/9
     */
    private MyTaskDTO convertMyTask22(MyTaskEntity entity) throws Exception {
        MyTaskDTO dto = new MyTaskDTO();
        BaseDTO.copyFields(entity, dto);
        ProjectTaskEntity taskEntity = this.projectTaskDao.selectById(dto.getTargetId());
        if (taskEntity != null) {
            dto.setTaskContent(taskEntity.getTaskName() + "已完成");
        }

        return dto;
    }

    /**
     * 方法描述：保存我的任务（直接推送给人的）（1.设置流程后，发送给设计人。2.报销单审核后，推送审核任务。）
     * 作者：MaoSF
     * 日期：2016/12/21
     */
    @Override
    public ResponseBean saveMyTask(String targetId, int taskType, String companyId, String companyUserId, String createBy,String currentCompanyId) throws Exception {
        return saveMyTask(targetId, taskType, companyId, companyUserId, false, createBy,currentCompanyId);
    }

    @Override
    public ResponseBean saveMyTask(String targetId, int taskType, String companyId, String companyUserId, boolean isSendMessage, String createBy,String currentCompanyId) throws Exception {
        if (taskType == SystemParameters.PRODUCT_TASK_DESIGN) {//目前屏蔽技术负责人的任务
            return null;
        }
        MyTaskEntity taskEntity = this.getMyTaskEntity(targetId, taskType);
        if (null == taskEntity) {
            taskEntity = new MyTaskEntity();
        }
        taskEntity.setTargetId(targetId);
        taskEntity.setTaskType(taskType);
        taskEntity.setCompanyId(companyId);
        taskEntity.setHandlerId(companyUserId);
        taskEntity.setId(StringUtil.buildUUID());
        taskEntity.setCreateBy(createBy);
        taskEntity.setSendCompanyId(currentCompanyId);
        this.myTaskDao.insert(taskEntity);
        if (isSendMessage) {
            sendMessageForMyTask(taskEntity,currentCompanyId);
        }
        //推送生产安排的任务
        saveProductTask(taskEntity, taskType);
        return null;
    }

    /**
     * 方法描述：推送生产安排的任务，如果已经存在生产安排，则不推送
     * 作者：MaoSF
     * 日期：2017/6/30
     */
    private void saveProductTask(MyTaskEntity taskEntity, int taskType) throws Exception {
        if (taskType == SystemParameters.PRODUCT_TASK_RESPONSE) {//生产安排的任务，任务负责人的任务
            Map<String, Object> map = new HashMap<>();
            map.put("projectId", taskEntity.getProjectId());
            map.put("handlerId", taskEntity.getHandlerId());
            map.put("taskType", SystemParameters.PRODUCT_TASK);
            if (CollectionUtils.isEmpty(this.getMyTaskByParam(map))) {
                this.ignoreMyTask(taskEntity.getTargetId(), SystemParameters.PRODUCT_TASK, null);
                taskEntity.setId(StringUtil.buildUUID());
                taskEntity.setTaskType(SystemParameters.PRODUCT_TASK);
                this.myTaskDao.insert(taskEntity);
            }
        }
    }

    private void sendMessageForMyTask(MyTaskEntity taskEntity,String currentCompanyId) throws Exception {
        MessageEntity msg = getMessage(taskEntity,currentCompanyId);
        if (msg.getMessageType() == SystemParameters.MESSAGE_TYPE_5 || msg.getMessageType() == SystemParameters.MESSAGE_TYPE_6) {
            Map<String, Object> map = new HashMap<>();
            map.put("projectId", taskEntity.getTargetId());
            map.put("companyId", taskEntity.getCompanyId());
            List<ProjectTaskDTO> taskList = projectTaskDao.getProjectTaskByCompanyId(map);
            if ((taskList != null) && (taskList.size() > 0)) {
                for (ProjectTaskDTO task : taskList) {
                    msg.setTargetId(task.getId());
                    messageService.sendMessage(msg);
                }
            } else {
                messageService.sendMessage(msg);
            }
        } else {
            messageService.sendMessage(msg);
        }
    }

    /**
     * 方法描述：技术审查费付款确认，合作技术费付款确认（taskType=4 or 6）
     * 作者：MaoSF
     * 日期：2017/3/6
     *
     * @param targetId
     * @param taskType
     * @param companyId
     * @param:
     * @return:
     */
    @Override
    public ResponseBean saveMyTask(String targetId, int taskType, String companyId, String createBy,String currentCompanyId) throws Exception {
        ProjectCostPaymentDetailEntity detailEntity = this.projectCostPaymentDetailDao.selectById(targetId);
        if (detailEntity != null) {
            if (taskType == 4 || taskType == 6) {
                this.saveMyTaskFor4Or6(targetId, taskType, companyId, detailEntity.getProjectId(), createBy,currentCompanyId);
            }

            if (taskType == 10 || taskType == 20 || taskType == 21) {
                this.saveMyTask(targetId, companyId, taskType, null, detailEntity.getProjectId(), createBy,currentCompanyId);
            }

        }
        return null;
    }

    /**
     * 方法描述：taskType = 4 or 6
     * 作者：MaoSF
     * 日期：2017/5/2
     *
     * @param:
     * @return:
     */
    private void saveMyTaskFor4Or6(String targetId, int taskType, String companyId, String projectId, String createBy,String currentCompanyId) throws Exception {
        List<ProjectMemberEntity> managerList = this.getProjectOperatorManager(projectId, companyId);
        if (!CollectionUtils.isEmpty(managerList)) {
            String companyUserIds = "";
            for (ProjectMemberEntity managerEntity : managerList) {
                if(!companyUserIds.contains(managerEntity.getCompanyUserId())) {
                    companyUserIds+=managerEntity.getCompanyUserId()+",";
                    MyTaskEntity taskEntity = this.getMyTaskEntity(targetId, taskType);
                    taskEntity.setId(StringUtil.buildUUID());
                    taskEntity.setCompanyId(companyId);
                    taskEntity.setTaskType(taskType);
                    taskEntity.setHandlerId(managerEntity.getCompanyUserId());
                    taskEntity.setCreateBy(createBy);
                    taskEntity.setSendCompanyId(currentCompanyId);
                    this.myTaskDao.insert(taskEntity);
                    this.messageService.sendMessage(this.getMessage(taskEntity,currentCompanyId));
                }
            }
        }
    }

    private List<ProjectMemberEntity> getProjectOperatorManager(String projectId, String companyId) throws Exception {
        List<ProjectMemberEntity> list = new ArrayList<>();
        ProjectMemberEntity member = this.projectMemberService.getOperatorManager(projectId, companyId);
        ProjectMemberEntity assistant = this.projectMemberService.getOperatorAssistant(projectId, companyId);
        if(member!=null){
            list.add(member);
        }
        if(assistant!=null){
            list.add(assistant);
        }
        return list;
    }
    /**
     * 方法描述：保存我的任务(合同回款发起，给财务任务,合作设计费，技术审查费：经营负责人确认付款后，给财务人员推送到款，付款的任务)
     * filed1:为技术审查费，合作设计费，财务到款，付款的保存的maoding_web_project_cost_point中的id，方便查询
     * 作者：MaoSF
     * 日期：2017/3/6
     */
    private ResponseBean saveMyTask(String targetId, String companyId, int taskType, String pointId, String projectId, String createBy,String currentCompanyId) throws Exception {
        String type = getFinanceCode(taskType);
        companyId = companyService.getFinancialHandleCompanyId(companyId);
        List<CompanyUserDataDTO> companyUserList = getFinanceUser(companyId,taskType);;
//        if(taskType==SystemParameters.TECHNICAL_REVIEW_FEE_FOR_PAY
//                || taskType==SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAY
//                || taskType==SystemParameters.OTHER_FEE_FOR_PAY){
//            companyUserList = this.companyUserService.getFinancialManager(companyId);
//            type = MyTaskRole.FINANCE_PAY;
//        }
//        if(taskType==SystemParameters.CONTRACT_FEE_PAYMENT_CONFIRM
//                || taskType==SystemParameters.TECHNICAL_REVIEW_FEE_FOR_PAID
//                || taskType==SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAID
//                || taskType==SystemParameters.OTHER_FEE_FOR_PAID){
//            companyUserList = this.companyUserService.getFinancialManagerForReceive(companyId);
//            type = MyTaskRole.FINANCE_RECEIVE;
//        }

        MyTaskEntity taskEntity = this.getMyTaskEntity(targetId, taskType);
        //插入空的数据
        taskEntity.setId(StringUtil.buildUUID());
        taskEntity.setProjectId(projectId);
        taskEntity.setCompanyId(companyId);
        taskEntity.setTaskType(taskType);
        taskEntity.setParam1(pointId);//保存所在收款节点，便于后面查询
        taskEntity.setParam3(type);//保存所处理的权限点
        taskEntity.setCreateBy(createBy);
        taskEntity.setSendCompanyId(currentCompanyId);
        this.myTaskDao.insert(taskEntity);
        if (!CollectionUtils.isEmpty(companyUserList)) {
            for (CompanyUserDataDTO dto : companyUserList) {
                taskEntity.setHandlerId(dto.getId());
                this.messageService.sendMessage(this.getMessage(taskEntity,currentCompanyId));
            }
        }
        return ResponseBean.responseSuccess().addData("companyId",companyId);
    }

    /**
     * 方法描述：保存我的任务（更换系统中某特定职务的人之后，把所有的任务移交给新人）
     * 作者：MaoSF
     * 日期：2016/12/21
     */
    @Override
    public ResponseBean saveMyTask(MyTaskEntity entity, boolean isSendMessage) throws Exception {
        entity.setId(StringUtil.buildUUID());
        this.myTaskDao.insert(entity);
        if (isSendMessage) {
            this.sendMessageForMyTask(entity,entity.getCompanyId());
        }
        return null;
    }

    @Override
    public ResponseBean saveMyTask(SaveMyTaskDTO dto) throws Exception {
        if(EmojiFilter.containsEmoji(dto.getTaskContent())){
            return ResponseBean.responseError("任务内容包含非法字符");
        }
        MyTaskEntity entity = new MyTaskEntity();
        BaseDTO.copyFields(dto,entity);
        int i = 0 ;
        if(StringUtil.isNullOrEmpty(dto.getId())){
            entity.initEntity();
            if(!StringUtil.isNullOrEmpty(dto.getUuid())){
                entity.setId(dto.getUuid());
            }
            entity.setTaskType(SystemParameters.CUSTOM_TASK);
            entity.setCreateBy(dto.getAccountId());
            entity.setCompanyId(dto.getAppOrgId());
            entity.setSendCompanyId(dto.getAppOrgId());
            i = myTaskDao.insert(entity);
            //发送消息
            MessageEntity m = this.getMessage(entity,dto.getAppOrgId());
            if(m!=null){
                m.setTargetId(entity.getId());//此处设置为轻量任务的id
                messageService.sendMessage(m);
            }
        }else {
            i = myTaskDao.updateById(entity);
        }
        return i==0?ResponseBean.responseError("操作失败"):ResponseBean.responseSuccess("保存成功");
    }

    /************************任务类型(1.签发：经营负责人,2.生产安排（项目设计负责人）.
     * 3.设计（设计，校对，审核），
     * 4.付款（技术审查费-确认付款款（经营负责人）），5.付款（技术审查费-确认付款款（企业负责人）），6.付款（合作设计费-付款确认（经营负责人）），7.付款（合作设计费-付款确认（企业负责人）），
     * 8.到款（技术审查费-确认到款），9.到款（合作设计费-到款确认）10.到款（合同回款-到款确认）
     11.报销单审核,12.同意邀请合作伙伴)***********************/

    private MyTaskEntity getMyTaskEntity(String targetId, int taskType) throws Exception {
        switch (taskType) {
            case 1:
            case 2:
            case 13:
            case 14:
            case 15:
            case 22:
                return taskEntity1(targetId);
            case 3:
                return taskEntity3(targetId);
            case 4:
            case 5:
            case 6:
            case 7:
                return taskEntity4(targetId);
            case 8:
            case 9:
            case 10:
            case 20:
            case 21:
                return taskEntity8(targetId);
            case 11:
            case 23:
                return taskEntity11(targetId);
            case 12:
                return taskEntity12(targetId);
            case 16:
            case 17:
            case 18:
            case 19:
                return taskEntity16(targetId);
            default:
                return null;
        }
    }


    /**
     * 方法描述：签发任务（项目立项，签发时候给经营负责人推送任务）2.生产安排（项目设计负责人）（targetId:projectId or taskId)
     * 作者：MaoSF
     * 日期：2016/12/22
     *
     * @param:type=1 or type = 2 or type =13 or type=14
     * @return:
     */
    private MyTaskEntity taskEntity1(String targetId) {
        MyTaskEntity myTask = new MyTaskEntity();
        ProjectEntity project = this.projectDao.selectById(targetId);
        if (project != null) {
            myTask.setProjectId(project.getId());
            // myTask.setTaskTitle(project.getProjectName()); 此处的字段联动查询
            myTask.setTargetId(targetId);
            //  myTask.setTaskContent(project.getProjectName());
            return myTask;
        } else {
            ProjectTaskEntity projectTaskEntity = this.projectTaskDao.selectById(targetId);
            project = this.projectDao.selectById(projectTaskEntity.getProjectId());
            //  myTask.setTaskTitle(project.getProjectName());
            myTask.setProjectId(project.getId());
            myTask.setTargetId(targetId);
            //myTask.setTaskContent(project.getProjectName()+"-"+projectTaskEntity.getTaskName());
            return myTask;
        }
    }


    /**
     * 方法描述：targetId(为流程节点的id)（新建流程时候，对设计人发送任务） 比如：（设计：卯丁科技大厦 方案设计），设计，校对，审定
     * 作者：MaoSF
     * 日期：2016/12/21
     *
     * @param:
     * @return:
     */
    private MyTaskEntity taskEntity3(String targetId) {
        MyTaskEntity myTask = new MyTaskEntity();
        ProjectProcessNodeEntity projectProcessNode = this.projectProcessNodeDao.selectById(targetId);
        ProjectTaskEntity projectTask = this.projectTaskDao.selectById(projectProcessNode.getProcessId());//目前兼容一下。后期移除process，taskId直接关联到node记录中
        if (projectTask != null) {
            ProjectEntity project = this.projectDao.selectById(projectTask.getProjectId());
            if (project != null) {
                myTask.setProjectId(project.getId());
                myTask.setTaskTitle(project.getProjectName());
                myTask.setTargetId(targetId);
                myTask.setTaskContent(projectProcessNode.getNodeName());
                myTask.setParam1(projectTask.getId());//保存所在任务的id，便于后面查询
                return myTask;
            }
        }
        return null;
    }

    /**
     * 方法描述：付款（技术审查费-确认付款款（经营负责人））
     * 作者：MaoSF
     * 日期：2016/12/22
     *
     * @param:gargetId为maoding_web_project_cost_detail表中的id
     * @return: type=4 or type =5; type = 6 or type = 7
     */
    private MyTaskEntity taskEntity4(String targetId) {
        MyTaskEntity myTask = new MyTaskEntity();
        ProjectCostPointDetailEntity costDetailEntity = projectCostPointDetailDao.selectById(targetId);
        if (costDetailEntity != null) {
            ProjectCostPointEntity pointEntity = this.projectCostPointDao.selectById(costDetailEntity.getPointId());
            if (pointEntity != null) {
                myTask.setProjectId(costDetailEntity.getProjectId());
                myTask.setTargetId(targetId);
                //myTask.setTaskTitle(project.getProjectName());
                myTask.setTaskContent(pointEntity.getFeeDescription() + sperate + "金额：" + StringUtil.getRealData(costDetailEntity.getFee()) + "万元");
                return myTask;
            }
        }
        return null;
    }


    /**
     * 方法描述：付款（技术审查费-确认付款款（经营负责人））
     * 作者：MaoSF
     * 日期：2016/12/22
     *
     * @param:gargetId为maoding_web_project_cost_detail表中的id
     * @return: type=8 or type =9; type = 10
     */
    private MyTaskEntity taskEntity8(String targetId) {
        MyTaskEntity myTask = new MyTaskEntity();
        ProjectCostPointDetailEntity costDetailEntity = projectCostPointDetailDao.selectById(targetId);
        if (costDetailEntity != null) {
            ProjectCostPointEntity pointEntity = this.projectCostPointDao.selectById(costDetailEntity.getPointId());
            if (pointEntity != null) {
                myTask.setProjectId(costDetailEntity.getProjectId());
                myTask.setTargetId(targetId);
                myTask.setTaskContent(pointEntity.getFeeDescription() + sperate + "金额：" + StringUtil.getRealData(costDetailEntity.getFee()) + "万元，收到款项后请及时确认");
                return myTask;
            }
        }
        return null;
    }


    /**
     * 方法描述：targetId(为报销单的id)`
     * 作者：MaoSF
     * 日期：2016/12/21
     */
    private MyTaskEntity taskEntity11(String targetId) {
        MyTaskEntity myTask = new MyTaskEntity();

        ExpMainDTO expMainDTO = this.expMainDao.getByMainIdForMyTask(targetId);
        if (expMainDTO != null) {

            myTask.setTaskTitle(expMainDTO.getUserName() + "的报销单");
            myTask.setTaskContent(expMainDTO.getExpTypeName() + " 总金额：" + StringUtil.getRealData(expMainDTO.getExpSumAmount()) + "元");
            return myTask;
        }
        return null;
    }


    /**
     * 方法描述：申请加入成为合作伙伴
     * 作者：MaoSF
     * 日期：2016/12/21
     *
     * @param:
     * @return:
     */
    private MyTaskEntity taskEntity12(String targetId) {
        MyTaskEntity myTask = new MyTaskEntity();
        CompanyRelationAuditEntity companyRelationAuditEntity = this.companyRelationAuditDao.selectById(targetId);
        if (null != companyRelationAuditEntity) {
            CompanyEntity companyEntity = this.companyDao.selectById(companyRelationAuditEntity.getOrgId());
            myTask.setTaskTitle(companyEntity.getCompanyName());
            myTask.setTaskContent("事业合伙人申请");
            return myTask;
        }
        return null;
    }

    /**
     * 方法描述：type=16-19
     * 作者：MaoSF
     * 日期：2016/12/21
     *
     * @param:
     * @return:
     */
    private MyTaskEntity taskEntity16(String targetId) {
        MyTaskEntity myTask = new MyTaskEntity();
        myTask.setTargetId(targetId);
        ProjectCostPaymentDetailEntity projectCostPaymentDetailEntity = this.projectCostPaymentDetailDao.selectById(targetId);
        if (projectCostPaymentDetailEntity != null) {
            myTask.setProjectId(projectCostPaymentDetailEntity.getProjectId());
        }
        return myTask;
    }

    /**
     * 方法描述：处理我的任务(报销任务处理)
     * 作者：MaoSF
     * 日期：2016/12/21
     */
    @Override
    public ResponseBean handleMyTask(String targetId, String companyUserId, String param2) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("targetId", targetId);
        map.put("status", "0");
        map.put("handlerId", companyUserId);
        List<MyTaskEntity> myTaskList = this.getMyTaskByParam(map);
        if (!CollectionUtils.isEmpty(myTaskList)) {
            MyTaskEntity entity = myTaskList.get(0);
            entity.setStatus("1");
            entity.setParam2(param2);
            this.myTaskDao.updateById(entity);
        }
        return null;
    }


    /**
     * 方法描述：忽略我的任务
     * 作者：MaoSF
     * 日期：2016/12/21
     */
    @Override
    public ResponseBean ignoreMyTask(String targetId, int taskType, String companyUserId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("targetId", targetId);
        map.put("taskType", taskType);
        map.put("handlerId", companyUserId);
        this.myTaskDao.deleteMyTask(map);
        return ResponseBean.responseSuccess();
    }

    /**
     * 方法描述：忽略我的任务（删除了该节点后，任务全部忽略）
     * 作者：MaoSF
     * 日期：2016/12/21
     *
     * @param targetId
     * @param:
     * @return:
     */
    @Override
    public ResponseBean ignoreMyTask(String targetId) throws Exception {
        this.myTaskDao.deleteProjectTask(targetId);//此处做兼容处理，把param4设置为1
        return null;
    }

    private ResponseBean finishMyTask(String id){
        return finishMyTask(id,"1");
    }

    private ResponseBean finishMyTask(String id,String param2){
        if(StringUtil.isNullOrEmpty(id)){
            return ResponseBean.responseError("操作失败");
        }
        MyTaskEntity entity = new MyTaskEntity();
        entity.setId(id);
        entity.setStatus("1");
        if(StringUtil.isNullOrEmpty(param2)){
            entity.setParam2("1");
        }else {
            entity.setParam2(param2);
        }

        entity.setCompleteDate(new Date());
        myTaskDao.updateById(entity);
        return ResponseBean.responseSuccess("操作成功");
    }

    /**
     * 方法描述：处理我的任务
     * 作者：MaoSF
     * 日期：2016/12/21
     */
    @Override
    public ResponseBean handleMyTask(HandleMyTaskDTO dto) throws Exception {
        MyTaskEntity myTaskEntity = this.selectById(dto.getId());
        if (null != myTaskEntity) {
            if("1".equals(myTaskEntity.getParam4())){
                return ResponseBean.responseError("该任务已无效");
            }
            if("1".equals(dto.getStatus()) && "1".equals(myTaskEntity.getStatus())){
                return ResponseBean.responseError("该任务已被处理，请刷新界面");
            }
            String status = dto.getStatus();
            String accountId = dto.getAccountId();
            String result = dto.getResult();
            String paidDate = dto.getPaidDate();
            int taskType = myTaskEntity.getTaskType();
            switch (taskType) {
                case 1:
                case 2:
                case 3:
                    return handleType1(myTaskEntity, status, accountId);
                case 13:
                    return handleType13(myTaskEntity, status, accountId,dto.getResult(),dto.getPaidDate());
                case 4:
                case 6:
                    return handleType4(myTaskEntity, result, accountId);
                case 22: //确认签发完成
                    return handleType22(myTaskEntity, result, accountId);
                case 100: //确认轻量任务完成
                    return handleType100(myTaskEntity,status,accountId);
                case 10:
                case 20:
                case 21:
                    return handleType10(myTaskEntity, result, status, accountId, paidDate);
                case 16:
                case 17:
                case 18:
                case 19:
                    return handleType16(myTaskEntity, result, status, accountId, paidDate);
                case MyTaskEntity.DELIVER_CONFIRM_FINISH:
                    handleMyTaskDeliverResponse(myTaskEntity,dto);
                    return ResponseBean.responseSuccess();
                default:
                    return ResponseBean.responseError("任务信息错误");
            }
        }
        return null;
    }

    /**
     * @author  张成亮
     * @date    2018/7/18
     * @description     处理交付负责人任务
     * @param   myTask 要处理的个人任务
     * @param   param 处理任务时的参数
     **/
    private void handleMyTaskDeliverResponse(MyTaskEntity myTask, HandleMyTaskDTO param){
        if (isComplete(param)){
            completeMyTaskDeliverResponse(myTask,param);
        }
    }

    //判断处理任务参数是激活还是完成
    private boolean isComplete(HandleMyTaskDTO param){
        return "1".equals(param.getStatus());
    }

    //完成负责人的交付任务
    private void completeMyTaskDeliverResponse(MyTaskEntity myTask, HandleMyTaskDTO param){
        //标记此所有任务完成
        finishMyTask(myTask);

        //如果已经此交付目录没有负责人要从事的交付任务了
        MyTaskQueryDTO query = new MyTaskQueryDTO();
        query.setCompanyId(myTask.getCompanyId());
        query.setTaskId(myTask.getTargetId());
        query.setMyTaskType(MyTaskEntity.DELIVER_CONFIRM_FINISH);
        query.setStatus(0);
        List<MyTaskEntity> list = myTaskDao.listEntityByQuery(query);
        if (ObjectUtils.isEmpty(list) && !StringUtils.isEmpty(myTask.getTargetId())) {
            //标记交付任务完成
            DeliverEntity deliver = deliverDao.selectById(myTask.getTargetId());
            if (deliver != null) {
                completeMyTaskDeliver(deliver);
            }
        }
    }


    //完成总的交付任务
    private void completeMyTaskDeliver(DeliverEntity deliver){
        //标记交付任务完成
        deliver.setStatus("1");
        deliverDao.updateById(deliver);

        //标记所有交付任务的所有子任务完成 （包括所有属于此交付任务的负责人确认任务和执行交付任务，targetId等于此交付任务的id)
        MyTaskEntity changed = new MyTaskEntity();
        changed.setStatus("1"); //完成标志
        MyTaskQueryDTO changedQuery = new MyTaskQueryDTO();
        changedQuery.setCompanyId(deliver.getCompanyId());
        changedQuery.setTaskId(deliver.getId());
        myTaskDao.updateByQuery(changed,changedQuery);
    }

    private List<CompanyUserDataDTO> getFinanceUser(String companyId,Integer taskType){
        switch (taskType){
            case SystemParameters.TECHNICAL_REVIEW_FEE_FOR_PAY:
                return companyUserService.getFinancialManagerPayForTechnical(companyId);
            case SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAY:
                return companyUserService.getFinancialManagerPayForCooperation(companyId);
            case SystemParameters.OTHER_FEE_FOR_PAY:
                return companyUserService.getFinancialManagerPayForOther(companyId);
            case SystemParameters.CONTRACT_FEE_PAYMENT_CONFIRM:
                return companyUserService.getFinancialManagerReceiveForContract(companyId);
            case SystemParameters.TECHNICAL_REVIEW_FEE_FOR_PAID:
                return companyUserService.getFinancialManagerReceiveForTechnical(companyId);
            case SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAID:
                return companyUserService.getFinancialManagerReceiveForCooperation(companyId);
            case SystemParameters.OTHER_FEE_FOR_PAID:
                return companyUserService.getFinancialManagerReceiveForOther(companyId);
            default:return null;
        }
    }

    private String getFinanceCode(Integer taskType){
        switch (taskType){
            case SystemParameters.TECHNICAL_REVIEW_FEE_FOR_PAY:
                return "1";
            case SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAY:
                return "1";
            case SystemParameters.OTHER_FEE_FOR_PAY:
                return "1";
            case SystemParameters.CONTRACT_FEE_PAYMENT_CONFIRM:
                return "2";
            case SystemParameters.TECHNICAL_REVIEW_FEE_FOR_PAID:
                return "2";
            case SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAID:
                return "2";
            case SystemParameters.OTHER_FEE_FOR_PAID:
                return "2";
            default:return null;
        }
    }

    private String getFinanceCode_new(Integer taskType){
        switch (taskType){
            case SystemParameters.TECHNICAL_REVIEW_FEE_FOR_PAY:
                return PermissionConst.TECHNICAL_PAY;
            case SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAY:
                return PermissionConst.COOPERATION_PAY;
            case SystemParameters.OTHER_FEE_FOR_PAY:
                return PermissionConst.OTHER_PAY;
            case SystemParameters.CONTRACT_FEE_PAYMENT_CONFIRM:
                return PermissionConst.CONTRACT_RECEIVE;
            case SystemParameters.TECHNICAL_REVIEW_FEE_FOR_PAID:
                return PermissionConst.TECHNICAL_RECEIVE;
            case SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAID:
                return PermissionConst.COOPERATION_RECEIVE;
            case SystemParameters.OTHER_FEE_FOR_PAID:
                return PermissionConst.OTHER_RECEIVE;
            default:return null;
        }
    }

    private ResponseBean validateIdentity(MyTaskEntity myTask,String accountId){
        String companyId = myTask.getCompanyId();
        boolean isFinancial = false;
        switch (myTask.getTaskType()){
            case SystemParameters.TECHNICAL_REVIEW_FEE_FOR_PAY:
                isFinancial = permissionService.isFinancialManagerPayForTechnical(companyId,accountId);
                break;
            case SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAY:
                isFinancial = permissionService.isFinancialManagerPayForCooperation(companyId,accountId);
                break;
            case SystemParameters.OTHER_FEE_FOR_PAY:
                isFinancial = permissionService.isFinancialManagerPayForOther(companyId,accountId);
                break;
            case SystemParameters.CONTRACT_FEE_PAYMENT_CONFIRM:
                isFinancial = permissionService.isFinancialManagerReceiveForContract(companyId,accountId);
                break;
            case SystemParameters.TECHNICAL_REVIEW_FEE_FOR_PAID:
                isFinancial = permissionService.isFinancialManagerReceiveForTechnical(companyId,accountId);
                break;
            case SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAID:
                isFinancial = permissionService.isFinancialManagerReceiveForCooperation(companyId,accountId);
                break;
            case SystemParameters.OTHER_FEE_FOR_PAID:
                isFinancial = permissionService.isFinancialManagerReceiveForOther(companyId,accountId);
                break;
            default:return null;
        }
        if(!isFinancial) {
            return ResponseBean.responseError("你无权限操作");
        }
        return null;
    }


    /**
     * 方法描述：处理type =1,2,3（完成，开始按钮操作，直接改变状态）
     * 作者：MaoSF
     * 日期：2017/1/17
     *
     * @param:
     * @return:
     */
    private ResponseBean handleType1(MyTaskEntity myTask, String status, String accountId) throws Exception {
        if (myTask.getTaskType() == 3) {
            //处理我的任务
            this.finishMyTask(myTask.getId());
            return this.handleProjectProcess(myTask, status, accountId);
        }
        return ResponseBean.responseSuccess("操作成功");
    }

    /**
     * 方法描述：处理任务完成 type = 13
     * 作者：MaoSF
     * 日期：2017/3/12
     *
     * @param:
     * @return:
     */
    private ResponseBean handleType13(MyTaskEntity myTask, String status, String accountId,String result,String paidDate) throws Exception {
        if ("1".equals(status)) { //如果是完成
            ResponseBean responseBean = this.projectTaskService.completeProductTask(myTask.getProjectId(), myTask.getTargetId(), myTask.getCompanyId(), accountId,result,paidDate);
            if (responseBean != null && "0".equals(responseBean.getError())) {
                //处理我的任务
                this.finishMyTask(myTask.getId());
                //判断，当前人所负责的任务是否全部完成，如果完成，则把生产安排给完成
                if (CollectionUtils.isEmpty(projectTaskDao.getMyResponsibleTask(myTask.getProjectId(), myTask.getCompanyId(), myTask.getHandlerId(), "1"))) {
                    //重新使用myTask对象,把生产安排的任务设置为完成状态
                    myTask.setStatus("1");
                    myTask.setParam2("1");
                    myTask.setTargetId(null);
                    myTask.setTaskType(SystemParameters.PRODUCT_TASK);
                    myTaskDao.updateStatesByTargetId(myTask);
                }
                return ResponseBean.responseSuccess("操作成功");
            }
            return responseBean;
        } else {
            //如果是激活
            int data = this.activeMyTask(myTask, accountId);
            if (data == 0) {
                return ResponseBean.responseSuccess("操作成功");
            }
            return ResponseBean.responseError("激活失败，请稍后再试");
        }
    }

    /**
     * 方法描述：处理流程完成
     * 作者：MaoSF
     * 日期：2017/3/12
     *
     * @param:
     * @return:
     */
    private ResponseBean handleProjectProcess(MyTaskEntity myTask, String status, String accountId) throws Exception {
        if ("1".equals(status)) { //如果是完成
            ResponseBean responseBean = this.projectProcessService.completeProjectProcessNode(myTask.getProjectId(), myTask.getCompanyId(), myTask.getTargetId(), myTask.getParam1(), accountId);
            if (responseBean != null && "0".equals(responseBean.getError())) {
                this.finishMyTask(myTask.getId());
                return ResponseBean.responseSuccess("操作成功");
            }
            return responseBean;
        } else {
            //如果是激活
            int data = this.activeMyTask3(myTask, accountId);
            if (data == 0) {
                return ResponseBean.responseSuccess("操作成功");
            }
            return ResponseBean.responseError("激活失败，请稍后再试");
        }
    }


    /**
     * 方法描述：经营负责人处理技术审查费（合作设计费）付款确认 type=4，6
     * 作者：MaoSF
     * 日期：2017/1/17
     *
     * @param:
     * @return:
     */
    private ResponseBean handleType4(MyTaskEntity myTask, String result, String accountId) throws Exception {
        //新增记录,调用费控模块的接口，新增收款（付款）记录
        ProjectCostPaymentDetailDTO detailDTO = new ProjectCostPaymentDetailDTO();
        detailDTO.setPointDetailId(myTask.getTargetId());
        detailDTO.setCurrentCompanyUserId(myTask.getHandlerId());
        detailDTO.setFee(new BigDecimal(result));
        detailDTO.setAccountId(accountId);
        detailDTO.setCurrentCompanyId(myTask.getCompanyId());
        detailDTO.setTaskType(myTask.getTaskType());
        ResponseBean responseBean = this.projectCostService.saveCostPaymentDetail(detailDTO);
        if ("1".equals(responseBean.getError())) {//如果失败，则返回
            return responseBean;
        }

        //2。给双方财务推送付款，到款的任务
        Map<String, Object> data = responseBean.getData();
        if (data != null) {
            ProjectCostEntity costEntity = this.projectCostService.selectById(data.get("costId"));
            String pointId = (String) data.get("pointId");
            //技术审查费推送任务
            if ("2".equals(costEntity.getType())) {
                responseBean = this.saveMyTask((String) data.get("paymentDetailId"), costEntity.getToCompanyId(), SystemParameters.TECHNICAL_REVIEW_FEE_FOR_PAID, pointId, costEntity.getProjectId(),accountId,myTask.getCompanyId());
                String handCompanyId = (String)responseBean.getData().get("companyId");
                if(!companyService.getFinancialHandleCompanyId(costEntity.getToCompanyId()).equals(handCompanyId)){
                    this.saveMyTask((String) data.get("paymentDetailId"), costEntity.getFromCompanyId(), SystemParameters.TECHNICAL_REVIEW_FEE_FOR_PAY, pointId, costEntity.getProjectId(),accountId,myTask.getCompanyId());
                }
            }
            //合作设计费推送任务
            if ("3".equals(costEntity.getType())) {
                String handleFromCompanyId = companyService.getFinancialHandleCompanyId(costEntity.getFromCompanyId());
                String handleToCompanyId = companyService.getFinancialHandleCompanyId(costEntity.getToCompanyId());
                if(handleFromCompanyId.equals(handleToCompanyId)){
                    if(handleToCompanyId.equals(costEntity.getToCompanyId())){
                        this.saveMyTask((String) data.get("paymentDetailId"), handleToCompanyId, SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAID, pointId, costEntity.getProjectId(), accountId, myTask.getCompanyId());
                    }else {
                        this.saveMyTask((String) data.get("paymentDetailId"), handleFromCompanyId, SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAY, pointId, costEntity.getProjectId(),accountId,myTask.getCompanyId());
                    }
                }else {
                    this.saveMyTask((String) data.get("paymentDetailId"), handleFromCompanyId, SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAY, pointId, costEntity.getProjectId(),accountId,myTask.getCompanyId());
                    this.saveMyTask((String) data.get("paymentDetailId"), handleToCompanyId, SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAID, pointId, costEntity.getProjectId(), accountId, myTask.getCompanyId());
                }
            }
        }
        //查询是否已经收款完全
        double sumFee = this.projectCostPaymentDetailDao.getSumFee(myTask.getTargetId());
        ProjectCostPointDetailEntity pointDetail = this.projectCostPointDetailDao.selectById(myTask.getTargetId());
        //如果已经收款完成，则把任务取消
        if (pointDetail != null && CommonUtil.doubleCompare(pointDetail.getFee().doubleValue(), sumFee) <= 0) {
            finishMyTask(myTask);
        }
        return ResponseBean.responseSuccess("操作成功");
    }

    /**
     * 方法描述：结束我的任务，合并原有的重复代码生成
     * 设置任务状态为1，并把相关任务的状态设置为2
     * 作者：ZCL
     * 日期：2017/5/5
     */
    public void finishMyTask(MyTaskEntity myTask) {
        //3.处理我的任务
        this.finishMyTask(myTask.getId());
        //忽略其他人员的任务
        MyTaskEntity myTask2 = new MyTaskEntity();
        myTask2.setParam4("1");
        myTask2.setTargetId(myTask.getTargetId());
        myTask2.setTaskType(myTask.getTaskType());
        myTask2.setProjectId(myTask.getProjectId());
        myTask2.setCompanyId(myTask.getCompanyId());
        this.myTaskDao.updateStatesByTargetId(myTask2);
    }

    /**
     * 方法描述：合同回款，其他费用财务到款、付款确认 type=10，20,21
     * 作者：MaoSF
     * 日期：2017/1/17
     */
    private ResponseBean handleType10(MyTaskEntity myTask, String result, String status, String accountId, String paidDate) throws Exception {
        //1.验证身份
        ResponseBean responseBean = this.validateIdentity(myTask,accountId);
        if(responseBean!=null){
            return responseBean;
        }
        CompanyUserEntity handler = companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId,myTask.getCompanyId());
        if(handler==null){
            return ResponseBean.responseError("参数错误");
        }

        //新增记录,调用
        ProjectCostPaymentDetailDTO detailDTO = new ProjectCostPaymentDetailDTO();
        detailDTO.setPointDetailId(myTask.getTargetId());
        if (null != myTask && myTask.getTaskType() == SystemParameters.OTHER_FEE_FOR_PAY) {//其他费用－付款
            detailDTO.setPayDate(paidDate);
        } else {
            detailDTO.setPaidDate(paidDate);
        }
        detailDTO.setCurrentCompanyUserId(handler.getId());
        detailDTO.setFee(new BigDecimal(result));
        detailDTO.setTaskType(myTask.getTaskType());
        detailDTO.setAccountId(accountId);
        detailDTO.setCurrentCompanyId(myTask.getCompanyId());
        responseBean = this.projectCostService.saveCostPaymentDetail(detailDTO);
        if ("1".equals(responseBean.getError())) {//如果失败，则返回
            return responseBean;
        }
        //查询是否已经收款完全
        double sumFee = this.projectCostPaymentDetailDao.getSumFee(myTask.getTargetId());
        ProjectCostPointDetailEntity pointDetail = this.projectCostPointDetailDao.selectById(myTask.getTargetId());
        //如果已经收款完成，则把任务取消
        if (pointDetail != null && CommonUtil.doubleCompare(pointDetail.getFee().doubleValue(), sumFee) <= 0) {
            myTask.setHandlerId(handler.getId());
            finishMyTask(myTask);
        }
        return ResponseBean.responseSuccess("操作成功");
    }

    /**
     * 方法描述：财务到款确认 type=16-19（合作设计费，技术审查费，双方财务付款到款操作）
     * 作者：MaoSF
     * 日期：2017/1/17
     */
    private ResponseBean handleType16(MyTaskEntity myTask, String result, String status, String accountId, String paidDate) throws Exception {
        ResponseBean responseBean = validateIdentity(myTask,accountId);
        if(responseBean!=null){
            return responseBean;
        }
        CompanyUserEntity handler = companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId,myTask.getCompanyId());
        if(handler==null){
            return ResponseBean.responseError("参数错误");
        }

        ProjectCostPaymentDetailDTO detailDTO = new ProjectCostPaymentDetailDTO();
        detailDTO.setId(myTask.getTargetId());

        detailDTO.setCurrentCompanyUserId(handler.getId());
        if (myTask.getTaskType() == 16 || myTask.getTaskType() == 18) {
            detailDTO.setPayDate(paidDate);
        }
        if (myTask.getTaskType() == 17 || myTask.getTaskType() == 19) {
            detailDTO.setPaidDate(paidDate);
        }
        detailDTO.setProjectId(myTask.getProjectId());
        detailDTO.setCurrentCompanyId(myTask.getCompanyId());
        detailDTO.setAccountId(accountId);
        detailDTO.setTaskType(myTask.getTaskType());
        responseBean = this.projectCostService.saveCostPaymentDetail(detailDTO);
        if ("1".equals(responseBean.getError())) {//如果失败，则返回
            return responseBean;
        }

        //3.处理我的任务
        myTask.setHandlerId(handler.getId());
        this.finishMyTask(myTask);
        return ResponseBean.responseSuccess("操作成功");

    }

    /**
     * 方法描述：完成签发任务
     * 作者：ZCL
     * 日期：2017/5/21
     */
    private ResponseBean handleType22(MyTaskEntity myTask, String status, String accountId) throws Exception {
        //// TODO: 2017/5/21 完成签发任务
        //处理我的任务
        this.finishMyTask(myTask.getId());
        this.projectTaskService.handleProjectTaskCompleteDate(myTask.getTargetId(), accountId,myTask.getCompanyId());
        return ResponseBean.responseSuccess("操作成功");
    }

    /**
     * 方法描述：轻量任务处理
     * 作者：MaoSF
     * 日期：2017/5/21
     */
    private ResponseBean handleType100(MyTaskEntity myTask, String status, String accountId) throws Exception {
        //处理我的任务
        if("1".equals(status)){
            this.finishMyTask(myTask.getId());
            //发送消息
            MessageEntity m = this.getMessage(myTask,myTask.getCompanyId());
            if(m!=null){
                m.setTargetId(myTask.getId());//此处设置为轻量任务的id
                m.setUserId(myTask.getCreateBy());
                m.setCreateBy(accountId);
                m.setMessageType(SystemParameters.MESSAGE_TYPE_902);//完成时间，发送任务的人推送消息
                messageService.sendMessage(m);
            }
        }else {
            myTask.setStatus("0");
            myTask.setParam2("0");
            myTask.setUpdateBy(accountId);
            myTaskDao.updateById(myTask);
        }
        return ResponseBean.responseSuccess("操作成功");
    }

    /**
     * 方法描述：查询我的任务详情
     * 作者：MaoSF
     * 日期：2017/1/11
     */
    @Override
    public ResponseBean getMyTaskDetail_old(String id,String accountId) throws Exception {
        MyTaskEntity myTaskEntity=this.selectById(id);
        ResponseBean r = ResponseBean.responseError("查询失败");
        if(myTaskEntity!=null){
            Object o = null;
            int type = myTaskEntity.getTaskType();
            if(type==1){//签发
                r =  this.projectTaskService.getOperatorTaskList(myTaskEntity.getProjectId(),myTaskEntity.getCompanyId());
            }
            if(type==3){//设校审
                r =  this.projectTaskService.getHandProcessDataForMyTask(myTaskEntity.getProjectId(),myTaskEntity.getParam1(),myTaskEntity.getCompanyId())
                        .addData("title",myTaskEntity.getTaskContent());
            }
            if(type == 12 || type == 13 ){//生产安排
                ProjectTaskDetailDTO detailDTO = this.projectTaskService.getProjectTaskById(myTaskEntity.getTargetId(),myTaskEntity.getCompanyId(),accountId);
                r = ResponseBean.responseSuccess().addData("task",detailDTO);
            }
            if(type==22){
                ProjectTaskDetailDTO detailDTO = this.projectTaskService.getProjectTaskById(myTaskEntity.getTargetId(),myTaskEntity.getCompanyId(),accountId);
                r = ResponseBean.responseSuccess().addData("task",detailDTO);
            }
            if(type==14){//设置设计负责人
                r =  this.projectTaskService.getArrangeDesignManagerForMyTask(myTaskEntity.getProjectId(),myTaskEntity.getCompanyId());
            }
            if(type==15){//设置任务责人
                r =  this.projectTaskService.getArrangeTaskResponseForMyTask(myTaskEntity.getProjectId(),myTaskEntity.getTargetId(),myTaskEntity.getCompanyId());
            }
            if(type==4 || type==5 || type==6 || type==7 || type==10 || type==20 || type==21){//合同回款收款，合作设计费付款，技术审查费付款，其他费用收款，其他费用付款
                r =  this.projectCostService.getProjectCostPointDetailForMyTask(null,myTaskEntity.getTargetId(),myTaskEntity);
            }
            if(type>15 && type<20){//合作设计费，技术审查费  财务付款，到款，

                r =  this.projectCostService.getProjectCostPointDetailForMyTask(myTaskEntity.getTargetId(),null,myTaskEntity);
            }
            if(type==11){//报销
                o = expMainService.getExpMainDetail(myTaskEntity.getTargetId());
                r =  ResponseBean.responseSuccess().addData("detail",o);
            }
            r.addData("status",myTaskEntity.getStatus());
            r.addData("tips",this.getTaskTitle(type));
            r.addData("role","任务角色："+getRole(type,myTaskEntity.getTaskContent()));
        }
        return r;
    }

    /**
     * 方法描述：查询我的任务详情
     * 作者：MaoSF
     * 日期：2017/1/11
     */
    @Override
    public ResponseBean getMyTaskDetail(String id, String accountId) throws Exception {
        MyTaskEntity myTaskEntity = this.selectById(id);
        ResponseBean r = ResponseBean.responseError("查询失败");
        if (myTaskEntity != null) {
            Object o = null;
            int type = myTaskEntity.getTaskType();
            if (type == 1) {//签发
                r = this.projectTaskService.getOperatorTaskList(myTaskEntity.getProjectId(), myTaskEntity.getCompanyId());
            }
            if (type == 3 ) {//设校审
                r = this.projectTaskService.getHandProcessDataForMyTask(myTaskEntity.getProjectId(), myTaskEntity.getParam1(), myTaskEntity.getCompanyId())
                        .addData("title", myTaskEntity.getTaskContent());
            }
            if (type == 13 ) {//任务负责人的任务
                r =  this.projectTaskService.getHandProcessDataForMyTask(myTaskEntity.getProjectId(),myTaskEntity.getTargetId(),myTaskEntity.getCompanyId());
            }
            if (type == 12 ) {//生产安排
                //ProjectTaskDetailDTO detailDTO = this.projectTaskService.getProjectTaskById(myTaskEntity.getTargetId(), myTaskEntity.getCompanyId(), accountId);
                ProjectTaskDTO task = this.projectTaskService.getTaskForMyTask(myTaskEntity.getProjectId(),myTaskEntity.getTargetId(),myTaskEntity.getCompanyId());
                r = ResponseBean.responseSuccess().addData("projectTask", task);
            }
            if (type == 22) {
                r =  this.projectTaskService.getHandProcessDataForMyTask(myTaskEntity.getProjectId(),myTaskEntity.getTargetId(),myTaskEntity.getCompanyId());
//                ProjectTaskDetailDTO detailDTO = this.projectTaskService.getProjectTaskById(myTaskEntity.getTargetId(), myTaskEntity.getCompanyId(), accountId);
//                r = ResponseBean.responseSuccess().addData("task", detailDTO);
            }
            if (type == 14) {//设置设计负责人
                r = this.projectTaskService.getArrangeDesignManagerForMyTask(myTaskEntity.getProjectId(), myTaskEntity.getCompanyId());
            }
            if (type == 15) {//设置任务责人
                r = this.projectTaskService.getArrangeTaskResponseForMyTask(myTaskEntity.getProjectId(), myTaskEntity.getTargetId(), myTaskEntity.getCompanyId());
            }
            if (type == 4 || type == 5 || type == 6 || type == 7 || type == 10 || type == 20 || type == 21) {//合同回款收款，合作设计费付款，技术审查费付款，其他费用收款，其他费用付款
                r = this.projectCostService.getProjectCostPointDetailForMyTask(null, myTaskEntity.getTargetId(), myTaskEntity);
            }
            if (type > 15 && type < 20) {//合作设计费，技术审查费  财务付款，到款，

                r = this.projectCostService.getProjectCostPointDetailForMyTask(myTaskEntity.getTargetId(), null, myTaskEntity);
            }
            if (type == 11) {//报销
                o = expMainService.getExpMainDetail(myTaskEntity.getTargetId());
                r = ResponseBean.responseSuccess().addData("detail", o);
            }
            if(type == 100) { //自定义
                r =  ResponseBean.responseSuccess().addData("detail", getMyTaskById(id,accountId));
            }
            if (type == MyTaskEntity.DELIVER_CONFIRM_FINISH) {
                r = ResponseBean.responseSuccess().addData("detail", getDeliverByMyTask(myTaskEntity));
            }
            r.addData("status", myTaskEntity.getStatus());
            r.addData("tips", this.getTaskTitle(type));
            r.addData("role", "任务角色：" + getRole(type, myTaskEntity.getTaskContent()));
            r.addData("myTaskId",myTaskEntity.getId());
        }
        return r;
    }

    //获取确认交付任务的详情, 包括交付名称，节点信息，交付说明，截止时间，负责人，相关目录已提交的文件列表（包含文件名，文件上传时间，上传人，文件链接地址）。
    private DeliverDTO getDeliverByMyTask(MyTaskEntity myTask){
        //查询交付信息
        MyTaskQueryDTO deliverQuery = new MyTaskQueryDTO();
        deliverQuery.setId(myTask.getTargetId());
        List<DeliverDTO> deliverList = deliverService.listDeliver(deliverQuery);
        if (ObjectUtils.isEmpty(deliverList)){
            return null;
        }
        DeliverDTO deliver = deliverList.get(0);

        //查询已交付文件
        ProjectSkyDriverQueryDTO fileQuery = new ProjectSkyDriverQueryDTO();
        fileQuery.setPid(deliver.getTargetId());
        fileQuery.setIsFile("1");
        List<ProjectSkyDriveListDTO> fileList = projectSkyDriverService.listSkyDriver(fileQuery);
        deliver.setUploadedList(fileList);

        return deliver;
    }

    @Override
    public TaskDetailDTO getMyTaskById(String id, String accountId) throws Exception {
        TaskDetailDTO result = this.myTaskDao.getMySubmitTaskById(id);
        if(result!=null){
            result.setAttachList(projectSkyDriverService.getAttachDataList( MapUtil.objectMap("targetId",id)));
        }
        return result;
    }

    public String getTaskTitle(int taskType) {
        //暂时未处理
        switch (taskType) {
            case 1:
                return "任务签发";
            case 3://（设计，校对，审核）任务完成
                return "确定设计任务已完成";
            case 4:
                return "技术审查费 - 确认付款金额";
            case 6:
                return "合作设计费 - 确认付款金额";
            case 10:
                return "合同回款 - 确认到账金额及日期";
            case 11:
                return "审批任务";
            case 12: // 任务负责人，生产安排任务
                return "生产安排";
            case 13: //任务负责人所负责的任务完成，13完成，同时触发12完成
                return "确定设计任务已完成";
            case 14:
                return "设置设计负责人";
            case 15:
                return "设置任务负责人";
            case 16:
                return "技术审查费 - 确认付款日期";
            case 17:
                return "技术审查费 - 确认到账日期";
            case 18:
                return "合作设计费 - 确认付款日期";
            case 19:
                return "合作设计费 - 确认到账日期";
            case 20:
                return "其他费用 - 确认付款金额及日期";
            case 21:
                return "其他费用 - 确认到账金额及日期";
            case 22://所有设计任务已完成，给组织的设计负责人推送任务
                return "确定设计任务已完成";
            default:
                return null;
        }
    }

    public String getRole(int taskType, String taskContent) {
        //暂时未处理
        switch (taskType) {
            case 1:
            case 14:
            case 4:
            case 6:
                return "经营负责人";
            case 3:
                return taskContent + "人";
            case 11:
                return "审批";
            case 12:
            case 13:
                return "任务负责人";
            case 10:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
                return "财务";
            case 15:
            case 22:
                return "设计负责人";
            case MyTaskEntity.DELIVER_CONFIRM_FINISH:
                return "任务负责人";
            case MyTaskEntity.DELIVER_EXECUTE:
                return "任务执行人";
            default:
                return null;
        }
    }

    private MessageEntity getMessage(MyTaskEntity task,String currentCompanyId) {
        CompanyUserEntity userEntity = this.companyUserDao.selectById(task.getHandlerId());
        if (userEntity == null || userEntity.getUserId().equals(task.getCreateBy())) {
            return null;
        }
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setProjectId(task.getProjectId());
        messageEntity.setCompanyId(task.getCompanyId());
        messageEntity.setTargetId(task.getTargetId());
        messageEntity.setParam1(task.getParam1());
        messageEntity.setUserId(userEntity.getUserId());
        messageEntity.setCreateBy(task.getCreateBy());
        messageEntity.setCreateDate(task.getCreateDate());
        messageEntity.setSendCompanyId(currentCompanyId);
        switch (task.getTaskType()) {
            case 1:
            case 2:
                getMessageType1(messageEntity, task.getTaskType());
                break;
            case 3:
                messageEntity.setTargetId(task.getParam1());//保存任务的id到 targetId中
                messageEntity.setParam1(task.getTargetId());
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_10);
                break;
            case 13:
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_7);
                break;
            case 4:
            case 5:
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_11);
                break;
            case 6:
            case 7:
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_14);
                break;
            case 8:
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_12);
                break;
            case 9:
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_15);
                break;
            case 10:
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_17);
                break;
            case 11:
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_19);
                break;
            case 23:
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_222);
                break;
            case 16:
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_23);
                break;
            case 17:
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_24);
                break;
            case 18:
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_25);
                break;
            case 19:
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_26);
                break;
            case 20:
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_31);
                break;
            case 21:
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_32);
                break;
            case 100:
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_901);
                break;
        }
        return messageEntity;
    }

    private void getMessageType1(MessageEntity messageEntity, int taskType) {
        int messageType = 0;
        ProjectEntity projectEntity = this.projectDao.selectById(messageEntity.getProjectId());
        if (1 == taskType) {//移交经营负责人
            if (projectEntity.getCompanyId().equals(messageEntity.getCompanyId())) {
                messageType = SystemParameters.MESSAGE_TYPE_301;
            } else if (!StringUtil.isNullOrEmpty(projectEntity.getCompanyBid())
                    && !projectEntity.getCompanyId().equals(projectEntity.getCompanyBid())
                    && projectEntity.getCompanyBid().equals(messageEntity.getCompanyId())) {
                messageType = SystemParameters.MESSAGE_TYPE_1;
            } else {
                messageType = SystemParameters.MESSAGE_TYPE_5;
            }

        } else {
            if (projectEntity.getCompanyId().equals(messageEntity.getCompanyId())) {
                messageType = SystemParameters.MESSAGE_TYPE_4;
            } else if (!StringUtil.isNullOrEmpty(projectEntity.getCompanyBid())
                    && !projectEntity.getCompanyId().equals(projectEntity.getCompanyBid())
                    && projectEntity.getCompanyBid().equals(messageEntity.getCompanyId())) {
                messageType = SystemParameters.MESSAGE_TYPE_2;
            } else {
                messageType = SystemParameters.MESSAGE_TYPE_6;
            }
        }
        messageEntity.setMessageType(messageType);
        messageEntity.setMessageContent(projectEntity.getProjectName());
    }

    /**
     * 作用：激活已完成的任务
     * 作者：ZCL
     * 日期：2017-5-20
     *
     * @param entity 激活申请
     * @return 0：正常激活，-1：无法激活
     * @throws Exception
     */
    @Override
    public int activeMyTask(MyTaskEntity entity, String accountId) throws Exception {
        if (entity == null) return -2;
        CompanyUserEntity companyUser = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId, entity.getCompanyId());
        if (companyUser == null) return -2;
        ProjectTaskEntity task = projectTaskDao.selectById(entity.getTargetId());

        if (task == null) return -2;
        ProjectTaskEntity parent = projectTaskDao.selectById(task.getTaskPid());
        if (parent != null) {
            if (parent.getCompleteDate() != null) return -1;
        }
        ProjectMemberDTO responsibler = this.projectMemberService.getTaskDesignerDTO(task.getId());
        if (responsibler == null) return -2;
        String taskResponsiblerId = responsibler.getCompanyUserId();
        if ((!taskResponsiblerId.equals(companyUser.getId()))) {
            return -1;
        }
        projectTaskDao.resetTaskCompleteStatus(task.getId());
        //设置修改过的数据
        ProjectTaskEntity targetTask = new ProjectTaskEntity();
        BeanUtilsEx.copyProperties(task, targetTask);
        targetTask.setCompleteDate(null);
        //设置情况为null
        targetTask.setCompletion(null);
        //保存项目动态
        dynamicService.addDynamic(task, targetTask, companyUser.getCompanyId(), companyUser.getUserId());

        entity.setStatus("0");
        entity.setParam2("0");
        myTaskDao.updateById(entity);

        this.saveProductTask(entity, entity.getTaskType());

        //如果是生产的根任务激活，如果存在
        if (task.getTaskType() == 2 && !StringUtil.isNullOrEmpty(task.getTaskPid())) {
            this.ignoreMyTask(task.getTaskPid(), SystemParameters.TASK_COMPLETE, null);
        }

        //通知协同
        this.collaborationService.pushSyncCMD_PT(task.getProjectId(), task.getTaskPath(), SyncCmd.PT2);

        return 0;
    }

    @Override
    public MyTaskCountDTO selectMyTaskCount(String companyId, String accountId) throws Exception{
        return myTaskDao.selectMyTaskCount(this.initParam(companyId,accountId));
    }

    private Map<String,Object> initParam(String companyId,String accountId) throws Exception{
        CompanyUserEntity user = companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId, companyId);
        if (user == null) {
            throw new Exception("参数错误");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("handlerId", user.getId());
        map.put("permissionCodes",permissionService.getPermissionCodeByUserId(companyId,accountId));
//        if (permissionService.isFinancial(companyId,accountId)) {
//            map.put("isHandler", "1");
//        }
//        if (permissionService.isFinancialReceive(companyId,accountId)) {
//            map.put("isHandlerReceive", "2");
//        }
        map.put("companyId",companyId);
        return map;
    }

    public List<MyTaskDTO> getOvertimeTask(Map<String, Object> param) {
        List<MyTaskDTO> list = myTaskDao.getOvertimeTask(param);
        for (MyTaskDTO dto : list) {
            dto.setRole(this.getRole(dto.getTaskType(), dto.getTaskContent()));
        }
        return list;
    }

    public List<MyTaskDTO> getDueTask(Map<String, Object> param) {
        List<MyTaskDTO> list = myTaskDao.getDueTask(param);
        for (MyTaskDTO dto : list) {
            dto.setRole(this.getRole(dto.getTaskType(), dto.getTaskContent()));
        }
        return list;
    }

    @Override
    public ApproveCount getTaskCount(String companyId, String handlerId,String accountId) {
        Map<String, Object> map = new HashMap<>();
        map.put("handlerId", handlerId);
        map.put("companyId", companyId);
        map.put("sendUserId", accountId);
        return myTaskDao.getTaskCount(map);
    }

    @Override
    public List<TaskDataDTO> getMySubmitTask(QueryDTO dto) {
        dto.setCompanyId(dto.getAppOrgId());
        return myTaskDao.getMySubmitTask(dto);
    }

    @Override
    public ResponseBean deleteMyTask(SaveMyTaskDTO dto) {
        //忽略其他人员的任务
        MyTaskEntity myTask = new MyTaskEntity();
        myTask.setId(dto.getId());
        myTask.setParam4("1");
        int i = myTaskDao.updateById(myTask);
        if(i==1){
            return ResponseBean.responseSuccess("删除成功");
        }
        return ResponseBean.responseError("删除失败");
    }

    /**
     * 首页数据查询（我的任务模块）
     */
    @Override
    public HomeDTO getTaskForHome(Map<String, Object> param) throws Exception {
        HomeDTO data = new HomeDTO();
        String companyId = (String)param.get("appOrgId");
        String accountId = (String)param.get("accountId");
        CompanyUserEntity userEntity = companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId,companyId);
        if(userEntity==null){
            return data;
        }
        //我的任务统计
        MyTaskCountDTO myTaskCountData = this.selectMyTaskCount(companyId,accountId);
        int total = 0;
        if(myTaskCountData != null) {
            data.getMyTaskCount().setCompleteCount(myTaskCountData.getCompleteCount());
            data.getMyTaskCount().setOvertimeCount(myTaskCountData.getOvertimeCount());
            total = myTaskCountData.getTotalCount();
        }
        //即将到期的任务
        Map<String,Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("companyUserId",userEntity.getId());
        map.put("handlerId",userEntity.getId());
        data.setTaskList(this.getDueTask(map));
        data.getMyTaskCount().setDueTimeCount(data.getTaskList().size());
        //正在进行中的任务
        data.getMyTaskCount().setProgressCount(total==0?0:(total-data.getMyTaskCount().getCompleteCount()
                        //  -data.getMyTaskCount().getOvertimeCount()-data.getMyTaskCount().getDueTimeCount()
                )
        );
        return data;
    }

    @Override
    public  Map<String,Object>  myTaskCountForNotHandle(Map<String, Object> param) throws Exception {
        String companyId = (String)param.get("appOrgId");
        String accountId = (String)param.get("accountId");
        Map<String,Object> map = this.initParam(companyId,accountId);
        map.put("isComplete",0);
        MyTaskCountDTO myTaskCountData = myTaskDao.getMyTaskCount(map);
        int notCompleteCount = 0;
        if(myTaskCountData!=null){
            notCompleteCount = myTaskCountData.getNotCompleteCount();
        }
        int auditCount = expMainDao.getMyAuditCount((String)map.get("handlerId"));
        param.clear();
        param.put("myTaskNotCompleteCount",notCompleteCount);
        param.put("auditCount",auditCount);
        return param;
    }

    /**
     * 方法描述：激活设校审
     * 作者：MaoSF
     * 日期：2017/5/24
     */
    public int activeMyTask3(MyTaskEntity entity, String accountId) throws Exception {
        if (entity == null) return -2;
        CompanyUserEntity companyUser = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId, entity.getCompanyId());
        if (companyUser == null) return -2;
        ProjectTaskEntity task = projectTaskDao.selectById(entity.getParam1());
        ProjectProcessNodeEntity nodeEntity = this.projectProcessNodeDao.selectById(entity.getTargetId());
        if (task == null || nodeEntity == null) return -2;
        if (task.getCompleteDate() != null) return -1;
        if (!nodeEntity.getCompanyUserId().equals(companyUser.getId())) return -1;
        projectTaskDao.resetProcessNodeCompleteStatus(entity.getTargetId());
        //设置修改过的数据
        ProjectProcessNodeEntity targetNode = new ProjectProcessNodeEntity();
        BeanUtilsEx.copyProperties(nodeEntity, targetNode);
        targetNode.setCompleteTime(null);
        //保存项目动态
        dynamicService.addDynamic(nodeEntity, targetNode, companyUser.getCompanyId(), companyUser.getUserId());

        entity.setStatus("0");
        entity.setParam2("0");
        myTaskDao.updateById(entity);

        //通知协同
        this.collaborationService.pushSyncCMD_PT(task.getProjectId(), task.getTaskPath(), SyncCmd.PT2);

        return 0;
    }

    /**
     * 方法描述：根据参数查询我的任务（companyId,companyUserId，projectId)
     * 作者：MaoSF
     * 日期：2016/12/1
     */
    public ResponseBean getMyTaskByProjectId_old( Map<String, Object> param) throws Exception{
        String projectId = (String)param.get("projectId");
        param.put("companyId",param.get("appOrgId"));
        //此处这样处理的原因是。处理报销之外的任务，全部是跟项目相关的任务，如果projectId 为null，则是报销任务
        if(!StringUtil.isNullOrEmpty(projectId)){
            param.put("projectId",projectId);
        }else {
            param.put("taskType","11"); // 报销任务的taskType = 11
            param.put("status","0");//只查询有效的
        }
        this.initParam(param);
        List<MyTaskEntity> list = this.myTaskDao.getMyTaskByProjectId(param);
        int total = this.myTaskDao.getMyTaskCount();//此语句必须紧跟在 List<MyTaskEntity> list = this.myTaskDao.getMyTaskByParam(param); 后面
        List<MyTaskDTO> dtoList = new ArrayList<>();
        for(MyTaskEntity entity:list){
            if(entity.getTaskType()==12){
                total--;
                continue;
            }
            if(entity.getTaskType()==11){
                dtoList.add(getExpTask(entity));
                continue;
            }
            MyTaskDTO dto = new MyTaskDTO();
            BaseDTO.copyFields(entity,dto);
            dto.setTaskTitle(this.getMyTaskTitle(entity));
            dto.setRole("任务角色："+this.getRole(entity.getTaskType(),entity.getTaskContent()));
            dto.setTaskState(this.getMyTaskState(entity));
            dtoList.add(dto);
        }
        return  ResponseBean.responseSuccess("查询成功").addData("myTaskList",dtoList).addData("total",total);
    }

    /**
     * 方法描述：根据参数查询我的任务（companyId,companyUserId，projectId)
     * 作者：MaoSF
     * 日期：2016/12/1
     */
    @Override
    public ResponseBean getMyTaskByProjectId(Map<String, Object> param) throws Exception {
        String projectId = (String) param.get("projectId");
        String isProjectTask = (String) param.get("isProjectTask");
        if(StringUtil.isNullOrEmpty(isProjectTask)){
            return this.getMyTaskByProjectId_old(param); //老版本的接口，老版本中无
        }
        param.put("companyId", param.get("appOrgId"));
        //此处这样处理的原因是。处理报销之外的任务，全部是跟项目相关的任务，如果projectId 为null，则是报销任务
        if (!StringUtil.isNullOrEmpty(projectId)) {
            param.put("projectId", projectId);
        }
        this.initParam(param);
        long time1 = System.currentTimeMillis();
        List<MyTaskEntity> list = this.myTaskDao.getMyTaskByProjectId2(param);
        int total = this.myTaskDao.getMyTaskCount();//此语句必须紧跟在 List<MyTaskEntity> list = this.myTaskDao.getMyTaskByParam(param); 后面
        long time2 = System.currentTimeMillis();
        List<MyTaskDTO> dtoList = new ArrayList<>();
        for (MyTaskEntity entity : list) {
            if (entity.getTaskType() == 11) {
                dtoList.add(getExpTask(entity));
                continue;
            }
            MyTaskDTO returnDto = new MyTaskDTO();
            BaseDTO.copyFields(entity, returnDto);
            if ("1".equals(isProjectTask)) {
                 returnDto = this.getMyTaskTitleByProject(entity);
                if (!StringUtil.isNullOrEmpty(returnDto.getEndDate())) {
                    returnDto.setEndDate(returnDto.getEndDate().replaceAll("-", "/"));
                }
                returnDto.setRole(this.getRole(entity.getTaskType(), entity.getTaskContent()));
                returnDto.setTaskState(this.getMyTaskState(entity));
                dtoList.add(returnDto);
            } else {
                returnDto.setTaskTitle(this.getMyTaskTitle(entity));
                returnDto.setRole("任务角色：" + this.getRole(entity.getTaskType(), entity.getTaskContent()));
                returnDto.setTaskState(this.getMyTaskState(entity));
                dtoList.add(returnDto);
            }
        }
        long time3 = System.currentTimeMillis();
        System.out.println("getMyTaskByProjectId2耗时："+(time2 - time1));
        System.out.println("组装数据耗时："+(time3 - time2));
        return ResponseBean.responseSuccess("查询成功").addData("myTaskList", dtoList).addData("total", total);
    }

    private boolean isContainAll(Map<String,Object> map){
        if(map.containsKey("taskStatus1")
                && map.containsKey("taskStatus2")
                && map.containsKey("taskStatus3")
                && map.containsKey("taskStatus4")
                && map.containsKey("taskStatus5")
                && map.containsKey("taskStatus6")){
            return true;
        }

        if(!map.containsKey("taskStatus1")
                && !map.containsKey("taskStatus2")
                && !map.containsKey("taskStatus3")
                && !map.containsKey("taskStatus4")
                && !map.containsKey("taskStatus5")
                && !map.containsKey("taskStatus6")){
            return true;
        }
        return false;
    }
    private MyTaskDTO getExpTask(MyTaskEntity entity) throws Exception {
        MyTaskDTO dto = new MyTaskDTO();
        BaseDTO.copyFields(entity, dto);
        ExpMainDTO expMainDTO = this.expMainDao.getByMainIdForMyTask(entity.getTargetId());
        if (expMainDTO != null) {
            dto.setTaskTitle("报销总额：" + StringUtil.toNumberStr(expMainDTO.getExpSumAmount().doubleValue()) + "元");
            dto.setMoney(StringUtil.toNumberStr(expMainDTO.getExpSumAmount().doubleValue()));
            dto.setVersionNum(expMainDTO.getVersionNum() + "");
            dto.setExpNo(expMainDTO.getExpNo());
        }
        dto.setTaskContent(expMainDTO.getUserName());
        dto.setRole("任务角色：" + this.getRole(entity.getTaskType(), entity.getTaskContent()));
        return dto;
    }


    /**
     * 方法描述：myTaskEntity重新查找数据组装成dto
     * 作者：GuoZB
     * 日期：2017/1/6
     */
    private MyTaskDTO getMyTaskTitleByProject(MyTaskEntity entity) throws Exception {

        MyTaskDTO dto = new MyTaskDTO();
        BaseDTO.copyFields(entity, dto);
        ProjectCostPointDataForMyTaskDTO dataDTO = null;
        ProjectTaskEntity projectTask = null;
        int taskType = entity.getTaskType();
        ProjectEntity projectEntity = this.projectDao.selectById(entity.getProjectId());
        //暂时未处理
        switch (taskType) {
            case 1:
                if (projectEntity != null) {
                    //任务描述
                    dto.setTaskTitle(projectEntity.getProjectName());
                    dto.setProjectName(projectEntity.getProjectName());
                    if (projectEntity.getCompanyId().equals(entity.getCompanyId())) {//立项方
                        dto.setTaskContent(this.projectTaskDao.getIssueTaskName(entity.getProjectId(), entity.getCompanyId(), 1));
                    } else {//合作方
                        dto.setTaskContent(this.projectTaskDao.getIssueTaskName(entity.getProjectId(), entity.getCompanyId(), 2));
                    }
                }
                return dto;
            case 3:
            case 12:
            case 13:

                String targetId = "";
                if (taskType == 3) {
                    targetId = entity.getParam1();
                } else {
                    targetId = entity.getTargetId();
                }
                projectTask = this.projectTaskDao.selectById(targetId);
                if (!StringUtil.isNullOrEmpty(this.projectTaskDao.getTaskParentNameExceptOwn(targetId))) {
                    dto.setTaskTitle(projectEntity.getProjectName() + "/" + this.projectTaskDao.getTaskParentNameExceptOwn(targetId));
                } else {
                    dto.setTaskTitle(projectEntity.getProjectName());
                }
                dto.setTaskContent(projectTask.getTaskName());
                dto.setTaskMemo(projectTask.getTaskRemark());
                dto.setEndDate(projectTask.getEndTime());
                if (!StringUtil.isNullOrEmpty(projectTask.getEndTime())) {
                    int days = DateUtils.daysOfTwo(projectTask.getEndTime(), DateUtils.date2Str(DateUtils.date_sdf));
                    int day = Math.abs(days);
                    dto.setOverTime((days>=0?((day+1)):day)+"");
                }
                dto.setProjectName(projectEntity.getProjectName());
                return dto;
            /*******************************************************************************/
            case 22://确定所有设计任务完成
                //查询所有签发的任务(当前公司)
                projectTask = this.projectTaskDao.selectById(entity.getTargetId());
                dto.setProjectName(projectEntity.getProjectName());
                dto.setTaskTitle(projectEntity.getProjectName());
                if(entity.getCompanyId().equals(projectEntity.getCompanyId())){
                    dto.setTaskContent(this.projectTaskDao.getIssueTaskName(entity.getProjectId(),entity.getCompanyId(),1));
                }else {
                    dto.setTaskContent(this.projectTaskDao.getIssueTaskName(entity.getProjectId(),entity.getCompanyId(),2));
                }
                dto.setEndDate(projectTask.getEndTime());
                return dto;
            case 4: //"技术审查费 - 确认付款金额";
                if(StringUtil.isNullOrEmpty(dto.getTaskTitle())) {
                    dto.setTaskTitle("技术审查费·" + projectEntity.getProjectName());
                }
            case 6: //"合作设计费 - 确认付款金额";
                if(StringUtil.isNullOrEmpty(dto.getTaskTitle())) {
                    dto.setTaskTitle("合作设计费·" + projectEntity.getProjectName());
                }
            case 10: //"合同回款 - 确认到账金额及日期";
                if(StringUtil.isNullOrEmpty(dto.getTaskTitle())) {
                    dto.setTaskTitle("合同回款·" + projectEntity.getProjectName());
                }

            case 20: //"其他费用 - 确认付款金额及日期";
                if(StringUtil.isNullOrEmpty(dto.getTaskTitle())) {
                    dto.setTaskTitle("其他费用·" + projectEntity.getProjectName());
                }
            case 21: //"其他费用 - 确认到账金额及日期";
                if(StringUtil.isNullOrEmpty(dto.getTaskTitle())) {
                    dto.setTaskTitle("其他费用·" + projectEntity.getProjectName());
                }
                dto.setTaskMemo(this.projectCostPointDao.getPointNameByDetailId(entity.getTargetId()));
                //查询子节点的总金额
                dataDTO = this.projectCostService.getProjectCostPointForMyTask(null, entity.getTargetId(), entity);
                //计划收款，付款
                dto.setPlanFee((dataDTO != null ? dataDTO.getPointDetailFee() != null ? dataDTO.getPointDetailFee(): new BigDecimal("0") :  new BigDecimal("0")));
                dto.setProjectName(projectEntity.getProjectName());
                //查询应收未收，应付未付
                dto.setUnPayFee(dataDTO.getUnpaid());
                dto.setTaskContent(StringUtil.toNumberStr6(dataDTO.getTotalUnPaymentFee()==null?0:dataDTO.getTotalUnPaymentFee().doubleValue()));
                return dto;
                /*******************************************************************************/
            case 16: //"技术审查费 - 确认付款日期";
                if(StringUtil.isNullOrEmpty(dto.getTaskTitle())) {
                    dto.setTaskTitle("技术审查费·" + projectEntity.getProjectName());
                }
            case 17: //"技术审查费 - 确认到账日期";
                if(StringUtil.isNullOrEmpty(dto.getTaskTitle())) {
                    dto.setTaskTitle("技术审查费·" + projectEntity.getProjectName());
                }
            case 18: //"合作设计费 - 确认付款日期";
                if(StringUtil.isNullOrEmpty(dto.getTaskTitle())) {
                    dto.setTaskTitle("合作设计费·" + projectEntity.getProjectName());
                }
            case 19: //"合作设计费 - 确认到账日期";
                if(StringUtil.isNullOrEmpty(dto.getTaskTitle())) {
                    dto.setTaskTitle("合作设计费·" + projectEntity.getProjectName());
                }
                ProjectCostPaymentDetailEntity paymentFee = projectCostPaymentDetailDao.selectById(entity.getTargetId());
                dto.setTaskMemo(this.projectCostPointDao.getPointNameByDetailId(entity.getTargetId()));
              //  dataDTO = this.projectCostService.getProjectCostPointForMyTask(entity.getTargetId(), null,taskType, entity.getCompanyId());
                dto.setTaskContent(StringUtil.toNumberStr6(paymentFee.getFee().doubleValue()));
                //dto.setTaskContent(dataDTO != null ? StringUtil.toNumberStr(dataDTO.getPaymentFee() != null ? dataDTO.getPaymentFee().doubleValue() : 0) : "0");
                dto.setProjectName(projectEntity.getProjectName());
//                dto.setUnPayFee(dataDTO.getUnpaid());
//                dto.setPlanFee(dataDTO.getPaymentFee());
                return dto;
            case 100:
                if(entity.getDeadline()!=null){
                    dto.setEndDate(DateUtils.date2Str(entity.getDeadline(),DateUtils.date_sdf2));
                    if("0".equals(entity.getStatus())){
                        int days = DateUtils.daysOfTwo(DateUtils.date2Str(entity.getDeadline(),DateUtils.date_sdf),DateUtils.date2Str(DateUtils.date_sdf)) ;
                        int day = Math.abs(days);
                        dto.setOverTime((days>=0?((day+1)):day)+"");
                    }
                }
                return dto;
            case MyTaskEntity.DELIVER_CONFIRM_FINISH:
                dto.setTaskTitle("交付确认");
                dto.setTaskContent(entity.getTaskTitle());
                if(entity.getDeadline()!=null) {
                    dto.setEndDate(DateUtils.date2Str(entity.getDeadline(), DateUtils.date_sdf2));
                }
                return dto;
            case MyTaskEntity.DELIVER_EXECUTE:
                dto.setTaskTitle("交付执行");
                dto.setTaskContent(entity.getTaskTitle());
                if(entity.getDeadline()!=null) {
                    dto.setEndDate(DateUtils.date2Str(entity.getDeadline(), DateUtils.date_sdf2));
                }
                //填充相关目录编号和目录名
                BaseShowDTO dir = getDirInfo(entity.getTargetId());
                dto.setTaskMemo(dir.getId() + "," + dir.getName());
                return dto;
            default:
                dto.setTaskTitle("");
                dto.setTaskContent("");
                dto.setTaskMemo("");
                dto.setProjectName(projectEntity != null?projectEntity.getProjectName():"");
                return dto;
        }
    }

    /**
     * @author  张成亮
     * @date    2018/7/19
     * @description     查找交付任务的相关目录信息
     * @param   deliverId 交付任务的编号
     * @return  目录信息
     **/
    private BaseShowDTO getDirInfo(String deliverId){
        BaseShowDTO result = new BaseShowDTO("","");
        if (!StringUtils.isEmpty(deliverId)) {
            //查找与交付任务相关的目录
            ProjectSkyDriverQueryDTO query = new ProjectSkyDriverQueryDTO();
            query.setDeliverId(deliverId);
            ProjectSkyDriveEntity dir = projectSkyDriverService.getEntityByQuery(query);
            if (dir != null) {
                result.setId(dir.getId());
                result.setName(dir.getFileName());
            }
        }
        return result;
    }

    /**
     * 方法描述：myTaskEntity重新查找数据组装成dto
     * 作者：MaoSF
     * 日期：2017/1/6
     */
    private String getMyTaskTitle(MyTaskEntity entity) throws Exception {
        int taskType = entity.getTaskType();
        //暂时未处理
        switch (taskType) {
            case 1:
                ProjectEntity projectEntity = this.projectDao.selectById(entity.getProjectId());
                if (projectEntity != null) {
                    if (projectEntity.getCompanyId().equals(entity.getCompanyId())) {//立项方
                        return this.projectTaskDao.getIssueTaskName(entity.getProjectId(), entity.getCompanyId(), 1);
                    } else {//合作方
                        return this.projectTaskDao.getIssueTaskName(entity.getProjectId(), entity.getCompanyId(), 2);
                    }
                }
            case 3:
                //
                return this.projectTaskDao.getTaskParentName(entity.getParam1());
            case 12:
            case 13:
            case 22:
                return this.projectTaskDao.getTaskParentName(entity.getTargetId());
            case 4:
            case 5:
            case 6:
            case 7:
            case 10:
            case 20:
            case 21:
                return this.projectCostPointDao.getPointNameByDetailId(entity.getTargetId());
            case 11:
                ExpMainDTO expMainDTO = this.expMainDao.getByMainIdForMyTask(entity.getTargetId());
                if (expMainDTO != null) {
                    return "报销总额：" + expMainDTO.getExpSumAmount();
                }
                return entity.getTaskTitle();
            case 16:
            case 17:
            case 18:
            case 19:
                return this.projectCostPointDao.getPointNameByPaymentId(entity.getTargetId());
            default:
                return null;
        }
    }

    /**
     * 方法描述：获取任务状态
     * 作者：MaoSF
     * 日期：2017/1/6
     */
    private int getMyTaskState(MyTaskEntity entity) throws Exception {
        int taskType = entity.getTaskType();
        switch (taskType) {
            case 3:
                return this.projectTaskDao.getTaskState(entity.getParam1(),entity.getProjectId());
            case 12:
            case 13:
            case 22:
                return this.projectTaskDao.getTaskState(entity.getTargetId(),entity.getProjectId());
            case 100:
                if(entity.getDeadline()!=null && "0".equals(entity.getStatus())){
                    if(DateUtils.datecompareDate(DateUtils.date2Str(entity.getDeadline(),DateUtils.date_sdf),DateUtils.date2Str(DateUtils.date_sdf))>=0){
                        return 1;
                    }else {
                        return 2;
                    }
                }
                return 1;
            default:
                return 0;
        }
    }

}
