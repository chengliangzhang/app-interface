package com.maoding.financial.service.impl;

import com.maoding.attach.dto.FileDataDTO;
import com.maoding.commonModule.dto.QueryCopyRecordDTO;
import com.maoding.commonModule.dto.QueryRelationRecordDTO;
import com.maoding.commonModule.dto.SaveCopyRecordDTO;
import com.maoding.commonModule.dto.SaveRelationRecordDTO;
import com.maoding.commonModule.service.CopyRecordService;
import com.maoding.commonModule.service.RelationRecordService;
import com.maoding.companybill.dto.SaveCompanyBillDTO;
import com.maoding.companybill.service.CompanyBillService;
import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.CompanyBillType;
import com.maoding.core.constant.CopyTargetType;
import com.maoding.core.constant.NetFileType;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;
import com.maoding.core.util.StringUtils;
import com.maoding.enterprise.dto.EnterpriseSearchQueryDTO;
import com.maoding.enterprise.service.EnterpriseService;
import com.maoding.financial.dao.ExpAuditDao;
import com.maoding.financial.dao.ExpCategoryDao;
import com.maoding.financial.dao.ExpDetailDao;
import com.maoding.financial.dao.ExpMainDao;
import com.maoding.financial.dto.*;
import com.maoding.financial.entity.ExpAuditEntity;
import com.maoding.financial.entity.ExpCategoryEntity;
import com.maoding.financial.entity.ExpDetailEntity;
import com.maoding.financial.entity.ExpMainEntity;
import com.maoding.financial.service.ExpMainService;
import com.maoding.message.dto.SendMessageDTO;
import com.maoding.message.entity.MessageEntity;
import com.maoding.message.service.MessageService;
import com.maoding.mytask.service.MyTaskService;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.dto.CompanyUserDataDTO;
import com.maoding.org.dto.CompanyUserDetailDTO;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.org.service.CompanyService;
import com.maoding.org.service.CompanyUserService;
import com.maoding.project.constDefine.EnterpriseServer;
import com.maoding.project.dto.ProjectDTO;
import com.maoding.project.service.ProjectService;
import com.maoding.project.service.ProjectSkyDriverService;
import com.maoding.task.dto.ApproveCount;
import com.maoding.task.dto.HomeDTO;
import com.maoding.user.service.UserAttachService;
import com.maoding.v2.financial.dto.V2ExpDetailDTO;
import com.maoding.v2.financial.dto.V2ExpMainDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 深圳市设计同道技术有限公司
 * 类    名 : ExpMainServiceImpl
 * 描    述 : 报销主表ServiceImpl
 * 作    者 : LY
 * 日    期 : 2016/7/26-16:07
 */

@Service("expMainService")
public class ExpMainServiceImpl extends GenericService<ExpMainEntity> implements ExpMainService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${fastdfs.url}")
    protected String fastdfsUrl;
    @Autowired
    private ExpMainDao expMainDao;
    @Autowired
    private ExpDetailDao expDetailDao;
    @Autowired
    private ExpAuditDao expAuditDao;
    @Autowired
    private CompanyUserService companyUserService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CompanyUserDao companyUserDao;
    @Autowired
    private MyTaskService myTaskService;
    @Autowired
    private ProjectSkyDriverService projectSkyDriverService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserAttachService userAttachService;
    @Autowired
    private CompanyBillService companyBillService;
    @Autowired
    private CopyRecordService copyRecordService;
    @Autowired
    private EnterpriseServer enterpriseServer;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private RelationRecordService relationRecordService;

    /**
     * 方法描述：我的报销列表
     * 作   者：LY
     * 日   期：2016/7/28 16:34
     * @param param 查询条件
     */
    @Override
    public List<ExpMainDTO> getExpMainPage(Map<String, Object> param) {
        if (null != param.get("pageNumber")) {
            int page = (Integer) param.get("pageNumber");
            int pageSize = (Integer) param.get("pageSize");
            param.put("startPage", page * pageSize);
            param.put("endPage", pageSize);
        }
        List<ExpMainDTO> list = expMainDao.getExpMainPage(param);
        return list;
    }

    /**
     * 方法描述：我的报销列表Interface
     * 作   者：LY
     * 日   期：2016/7/28 16:34
     * @param param 查询条件
     */
    @Override
    public List<ExpMainDTO> getExpMainPageInterface(Map<String, Object> param) {
        if (null != param.get("pageNumber")) {
            int page = (Integer) param.get("pageNumber");
            int pageSize = (Integer) param.get("pageSize");
            param.put("startPage", page * pageSize);
            param.put("endPage", pageSize);
        }
        List<ExpMainDTO> list = expMainDao.getExpMainPageInterface(param);
        return list;
    }

    @Override
    public int getExpMainPageCount(Map<String, Object> param) {
        return expMainDao.getExpMainPageCount(param);
    }


    /**
     * 方法描述：撤回报销
     * 作   者：LY
     * 日   期：2016/7/29 11:01
     * @param id--报销单id type--状态(3撤回)
     */
    @Override
    public int recallExpMain(String id, String versionNum, String type) throws Exception {
        ExpMainEntity entity = new ExpMainEntity();
        entity.setId(id);
        entity.setApproveStatus(type);
        if (versionNum != null) {
            entity.setVersionNum(Integer.parseInt(versionNum));
        }
        ExpAuditEntity auditEntity = new ExpAuditEntity();
        auditEntity.setMainId(id);
        auditEntity.setApproveStatus(type);
        if (versionNum != null) {
            auditEntity.setVersionNum(Integer.parseInt(versionNum));
        }
        expAuditDao.updateByMainId(auditEntity);

        return expMainDao.updateById(entity);
    }

    /**
     * 方法描述：退回报销
     * 作   者：LY
     * 日   期：2016/7/29 11:01
     * @param dto -- mainId--报销单id  approveStatus--状态(2.退回) auditMessage审批意见
     */
    @Override
    public int recallExpMain(ExpAuditDTO dto) throws Exception {
        ExpAuditEntity auditEntity = new ExpAuditEntity();
        auditEntity.setMainId(dto.getMainId());
        auditEntity.setApproveStatus(dto.getApproveStatus());
        auditEntity.setAuditMessage(dto.getAuditMessage());
        expAuditDao.updateByMainId(auditEntity);
        ExpMainEntity entity = this.expMainDao.selectById(dto.getMainId());
        entity.setApproveStatus(dto.getApproveStatus());
        //处理任务
        this.myTaskService.handleMyTask(dto.getMainId(), dto.getCompanyUserId(), "2");

        //发送消息
        this.sendMessageForAudit(dto.getMainId(), dto.getAppOrgId(), entity.getCompanyUserId(),entity.getType(),dto.getAccountId(),null,"2");//拒绝消息

        return expMainDao.updateById(entity);
    }

    public void sendMessageForAudit(String mainId, String companyId, String companyUserId,Integer type,String accountId,String auditId,String approveStatus) throws Exception {
        CompanyUserEntity companyUserEntity = this.companyUserDao.selectById(companyUserId);
        if (companyUserEntity != null) {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setTargetId(mainId);
            //报销
            if(type==1 && "0".equals(approveStatus)){//提交
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_19);
            }
            if(type==1 && "1".equals(approveStatus)){//同意
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_22);
            }
            if(type==1 && "2".equals(approveStatus)){//拒绝
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_20);
            }
            if(type==1 && "3".equals(approveStatus)){//同意并转交
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_221);
            }
            if(type==1 && "6".equals(approveStatus)){//拨款
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_234);
            }
            //费用
            if(type==2 && "0".equals(approveStatus)){//提交
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_222);
            }
            if(type==2 && "1".equals(approveStatus)){//同意
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_224);
            }
            if(type==2 && "2".equals(approveStatus)){//拒绝
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_223);
            }
            if(type==2 && "3".equals(approveStatus)){
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_225);
            }
            if(type==2 && "6".equals(approveStatus)){
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_235);//拨款
            }

            //请假
            if(type==3 && "0".equals(approveStatus)){//提交
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_226);
            }
            if(type==3 && "1".equals(approveStatus)){//同意
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_228);
            }
            if(type==3 && "2".equals(approveStatus)){//拒绝
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_227);
            }
            if(type==3 && "3".equals(approveStatus)){//同意并转交
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_229);
            }
            //出差
            if(type==4 && "0".equals(approveStatus)){//提交
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_230);
            }
            if(type==4 && "1".equals(approveStatus)){//同意
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_232);
            }
            if(type==4 && "2".equals(approveStatus)){//拒绝
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_231);
            }
            if(type==4 && "3".equals(approveStatus)){//同意并转交
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_233);
            }

            //抄送
            if(type==1 && "7".equals(approveStatus)){//
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_236);
            }
            if(type==2 && "7".equals(approveStatus)){//提交
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_237);
            }
            if(type==3 && "7".equals(approveStatus)){//提交
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_238);
            }
            if(type==4 && "7".equals(approveStatus)){//提交
                messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_239);
            }

            //把最新的审批记录的id关联上
            messageEntity.setParam1(auditId);
            messageEntity.setCompanyId(companyUserEntity.getCompanyId());
            messageEntity.setUserId(companyUserEntity.getUserId());
            messageEntity.setSendCompanyId(companyId);
            messageEntity.setCreateBy(accountId);
            this.messageService.sendMessage(messageEntity);
        }
    }

    @Override
    public HomeDTO getApproveDataForHome(Map<String, Object> param) throws Exception {
        HomeDTO data = new HomeDTO();
        String companyId = (String)param.get("appOrgId");
        String accountId = (String)param.get("accountId");
        CompanyUserEntity userEntity = companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId,companyId);
        if(userEntity==null){
            return data;
        }
        //审批数据
        //待审批的
        ApproveCount leaveApproveCount = expMainDao.getMyApproveLeaveCount(userEntity.getId());
        data.setApproveCount(leaveApproveCount);
        //我提交的
        ApproveCount leaveMySubmitCount = expMainDao.getMySubmitLeaveCount(userEntity.getId());
        data.setMySubmitCount(leaveMySubmitCount);
        //已完成的
        data.setMyApprovedCount(expMainDao.getMyApprovedLeaveCount(userEntity.getId()));
        return data;
    }

    @Override
    public AuditStaticDataDTO getAuditStaticData(QueryAuditDTO query)throws Exception{
        AuditStaticDataDTO data = new AuditStaticDataDTO();
        CompanyUserEntity user = companyUserDao.getCompanyUserByUserIdAndCompanyId(query.getAccountId(),query.getAppOrgId());
        if(user==null){
            throw new Exception("参数错误");
        }
        if(StringUtil.isNullOrEmpty(query.getMonth())){
            query.setMonth(DateUtils.date2Str(DateUtils.date_sdf_ym));
        }
        data.setAuditCount(expMainDao.getMyAuditCount(user.getId()));
        query.setCompanyUserId(user.getId());
        TotalDTO total = expMainDao.getMyApplyData(query);
        if(total!=null){
            data.getExpStatic().setData(total.getTotal1(),total.getTotal9(),total.getTotal2());
            data.getCostApplyStatic().setData(total.getTotal3(),total.getTotal10(),total.getTotal4());
            data.getLeaveStatic().setData(total.getTotal5(),total.getTotal6());
            data.getBusinessTripStatic().setData(total.getTotal7(),total.getTotal8());
        }
        return data;
    }

    /**
     * 方法描述：删除报销
     * 作   者：LY
     * 日   期：2016/7/29 10:53
     */
    @Override
    public int deleteExpMain(String id, String versionNum) throws Exception {
        int i = recallExpMain(id, versionNum, "4");
        System.out.print("i==" + i);
        if (i > 0) {
            System.out.print("测试");
            // 把相关的任务设置为无效
            this.myTaskService.ignoreMyTask(id);
            this.messageService.deleteMessage(id);
        }
        return i;
    }

    /**
     * 方法描述：同意报销
     * 作   者：LY
     * 日   期：2016/8/1 15:08
     */
    @Override
    public int agreeExpMain(SaveExpMainDTO dto) throws Exception {
        if(dto.getVersionNum()==null){
            return 0;
        }
        String id = dto.getId();
        String versionNum = dto.getVersionNum().toString();
        String companyUserId = dto.getCompanyUserId();
        String currentCompanyId = dto.getAppOrgId();
        String currentUserId = dto.getAccountId();
        //查询最新的这条记录
        ExpAuditEntity audit= this.expAuditDao.selectLastAudit(dto.getId());
        if(audit==null || !audit.getAuditPerson().equals(companyUserId)){
            return 0;
        }
        int i = recallExpMain(id, versionNum, "1");
        //如果有抄送人，保存抄送人
        this.saveCopy(dto.getCcCompanyUserList(),companyUserId,id,audit.getId());
        //处理我的任务
        ExpMainEntity mainEntity = this.expMainDao.selectById(id);
        if (mainEntity != null) {
            this.myTaskService.handleMyTask(id, companyUserId, "1");//处理我的任务
//            //给报销人推送消息
            sendMessageForAudit(mainEntity.getId(),currentCompanyId,mainEntity.getCompanyUserId(),mainEntity.getType(),currentUserId,null,"1");
        }

        if(mainEntity.getType()>2){
            //推送
            //推送抄送消息
            SendMessageDTO m = new SendMessageDTO();
            m.setTargetId(id);
            m.setCurrentCompanyId(currentCompanyId);
            m.setAccountId(currentUserId);
            int type = mainEntity.getType();
            if(type==3 ){//
                m.setMessageType(SystemParameters.MESSAGE_TYPE_238);
            }
            if(type==4){//提交
                m.setMessageType(SystemParameters.MESSAGE_TYPE_239);
            }
            this.messageService.sendMessageForCopy(m);
        }
        return i;
    }

    /**
     * 方法描述：同意报销并转移审批人
     * 作   者：LY
     * 日   期：2016/8/1 15:08
     */
    @Override
    public int agreeAndTransAuditPerExpMain(SaveExpMainDTO dto) throws Exception {

        String id = dto.getId();
        String versionNum = dto.getVersionNum().toString();
        String companyUserId = dto.getCompanyUserId();
        String currentCompanyId = dto.getAppOrgId();
        String accountId = dto.getAccountId();
        String auditPerson = dto.getAuditPerson();

        //版本控制，只为了方便
        ExpMainEntity entityVersion = new ExpMainEntity();
        entityVersion.setId(id);
        entityVersion.setApproveStatus("5");
        entityVersion.setVersionNum(Integer.parseInt(versionNum));
        int result = expMainDao.updateById(entityVersion);
        if (result == 0) {
            return result;
        }

        //根据报销单id查询最新审批记录id
        String parentId = null;
        ExpAuditEntity audit = expAuditDao.selectLastAudit(id);
        if (audit!=null) {
            parentId = audit.getId();
        }

        //把当前自己的审批记录改为同意并且is_new为N
        ExpAuditEntity auditEntity = new ExpAuditEntity();
        auditEntity.setMainId(id);
        auditEntity.setApproveStatus("1");
        expAuditDao.transAuditPer(auditEntity);

        //插入新审批人审批记录
        auditEntity = new ExpAuditEntity();
        // approve_date audit_message
        auditEntity.setId(StringUtil.buildUUID());
        auditEntity.setIsNew("Y");
        auditEntity.setMainId(id);
        auditEntity.setParentId(parentId);
        auditEntity.setApproveStatus("0");
        auditEntity.setAuditPerson(auditPerson);
        auditEntity.setSubmitAuditId(companyUserId);
        auditEntity.set4Base(accountId, accountId, new Date(), new Date());
        expAuditDao.insert(auditEntity);
        //保存抄送人
        this.saveCopy(dto.getCcCompanyUserList(),companyUserId,id,audit.getId());
        //更新报销主表审批状态
        ExpMainEntity entity = this.expMainDao.selectById(id);
        if(entity.getType()<3){
            //指派任务
            this.myTaskService.saveMyTask(entity.getId(), getMyTaskType(entity), entity.getCompanyId(), auditPerson,accountId,currentCompanyId);
            //处理任务
            this.myTaskService.handleMyTask(id, companyUserId, "1");
        }
        //推送消息
        this.sendMessageForAudit(id,currentCompanyId,auditPerson,entity.getType(),accountId,auditEntity.getId(),"3");//同意并转交
        //修改状态
        entity.setApproveStatus("5");
        return expMainDao.updateById(entity);
    }

    private void saveCopy(List<String> ccCompanyUserList,String sendCompanyUserId,String targetId,String operateRecordId) throws Exception{
        //保存抄送人
        SaveCopyRecordDTO copyDTO = new SaveCopyRecordDTO();
        copyDTO.setCompanyUserList(ccCompanyUserList);
        //当前人为发送人，在数据库中的字段为sendCompanyUserId
        copyDTO.setSendCompanyUserId(sendCompanyUserId);
        copyDTO.setOperateRecordId(operateRecordId);
        copyDTO.setTargetId(targetId);
        if(targetId.equals(operateRecordId)){
            copyDTO.setRecordType(CopyTargetType.EXP_MAIN);
        }else {
            copyDTO.setRecordType(CopyTargetType.EXP_AUDIT);
        }
        copyDTO.setRecordType(CopyTargetType.EXP_AUDIT);
        this.copyRecordService.saveCopyRecode(copyDTO);

    }

    private Integer getMyTaskType(ExpMainEntity entity){
        if(entity.getType()==2){
            return SystemParameters.COST_AUDIT;
        }
        if(entity.getType()==3){
            return SystemParameters.LEAVE_AUDIT;
        }
        if(entity.getType()==4){
            return SystemParameters.EVECTION_AUDIT;
        }
        return SystemParameters.EXP_AUDIT;
    }
    /**
     * 方法描述：报销详情与审批记录
     * 作   者：LY
     * 日   期：2016/8/2 14:13
     */
    @Override
    public Map<String, Object> getExpMainDetail(String id) throws Exception {
        ExpDetailDataDTO result = new ExpDetailDataDTO();
        CompanyUserEntity companyUserEntity = null;
        Map<String, Object> map = new HashMap<>();
        ExpMainEntity expMainEntity = expMainDao.selectById(id);
        if (null == expMainEntity) {
            return map;
        }
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("mainId", id);
        List<ExpAuditEntity> auditEntityList = expAuditDao.selectByParam(mapParam);
        CompanyUserDetailDTO companyUser = companyUserService.selectCompanyUserById(auditEntityList.get(0).getAuditPerson());
        Map<String, String> exp = new HashMap<>();
        companyUserEntity = companyUserDao.selectById(expMainEntity.getCompanyUserId());
        if(companyUserEntity!=null){
            exp.put("submitter", companyUserEntity.getUserName());
        }
        if(companyUser!=null){
            exp.put("auditCompanyUserId", companyUser.getId());
            exp.put("auditUserName", companyUser.getUserName());
            exp.put("auditCompanyName", companyUser.getCompanyName());
        }
        exp.put("expFlag", expMainEntity.getExpFlag() + "");
        exp.put("submittime", DateUtils.formatTimeSlash(expMainEntity.getCreateDate()));
        exp.put("remark", expMainEntity.getRemark());
        exp.put("approveStatus", expMainEntity.getApproveStatus());
        exp.put("versionNum", expMainEntity.getVersionNum() + "");
        exp.put("expNo", expMainEntity.getExpNo());

        mapParam.clear();
        ExpAuditEntity recallAudit = this.expAuditDao.selectLastRecallAudit(id);
        if (recallAudit!=null) {
            exp.put("sendBackReason", recallAudit.getAuditMessage());
        } else {
            exp.put("sendBackReason", "");
        }
        map.put("exp", exp);

        List<ExpDetailDTO> detailList = expDetailDao.selectDetailDTOByMainId(id);
        for(ExpDetailDTO detail:detailList){
            detail.setRelationRecordData( this.relationRecordService.getRelationList(new QueryRelationRecordDTO(id,detail.getId())));
        }
        map.put("detailList", detailList);

        Map<String, Object> param = new HashMap<>();
        param.put("fastdfsUrl", this.fastdfsUrl);
        param.put("id", id);
        List<ExpMainDataDTO> auditList = new ArrayList<>();
        ExpMainDataDTO expMainDTO = expMainDao.selectByIdWithUserName(param);
        expMainDTO.setApproveStatusName("发起申请");
        expMainDTO.setFileFullPath(userAttachService.getHeadImgNotFullPath(expMainDTO.getAccountId()));
        expMainDTO.setApproveDate(expMainEntity.getExpDate());
        auditList.add(expMainDTO);

        List<ExpMainDataDTO> list = expAuditDao.selectAuditDetailByMainId(param);
        for (ExpMainDataDTO dto : list) {
            dto.setApproveStatusName(getApproveStatusName(dto.getApproveStatus(), dto.getIsNew()));
        }
        auditList.addAll(list);

        //报销拨款
        if (!StringUtil.isNullOrEmpty(expMainEntity.getAllocationDate())) {
            ExpMainDataDTO expAllocationDTO = expMainDao.selectAllocationUser(param);
            expAllocationDTO.setId(expMainDTO.getId());
            expAllocationDTO.setApproveStatusName(getApproveStatusName(expAllocationDTO.getApproveStatus(), "Y"));
            expAllocationDTO.setIsNew("Y");
            expAllocationDTO.setApproveDate(expMainEntity.getAllocationDate());
            //头像
            CompanyUserEntity user = companyUserDao.selectById(expMainEntity.getAllocationUserId());
            if(user!=null){
                expAllocationDTO.setFileFullPath(userAttachService.getHeadImgNotFullPath(user.getUserId()));
            }
            auditList.add(expAllocationDTO);
        }else if("1".equals(expMainEntity.getApproveStatus())){
            ExpMainDataDTO expAllocationDTO = new ExpMainDataDTO();
            expAllocationDTO.setId(expMainEntity.getId());
            expAllocationDTO.setApproveStatusName("等待财务拨款");
            auditList.add(expAllocationDTO);
        }
        map.put("auditList", auditList);
        param.clear();
        param.put("targetId", id);
        param.put("type", NetFileType.EXPENSE_ATTACH);
        List<FileDataDTO> attachList = this.projectSkyDriverService.getAttachDataList(param);
        map.put("attachList", attachList);
        if(!CollectionUtils.isEmpty(detailList)){
            map.put("totalExpAmount", detailList.get(0).getTotalExpAmount());//每条记录中都记录了总金额
        }else {
            map.put("totalExpAmount", 0);
        }
        //填充外部组织名称
        if (!StringUtil.isNullOrEmpty(expMainEntity.getEnterpriseId())) {
            map.put("enterpriseName",enterpriseService.getEnterpriseName(expMainEntity.getEnterpriseId()));
            map.put("enterpriseId",expMainEntity.getEnterpriseId());
        }
        //查询抄送人
        map.put("ccCompanyUserList",copyRecordService.getCopyRecode(new QueryCopyRecordDTO(id)));
        return map;
    }

    @Override
    public Map<String, Object> getExpMainDetail2(ExpMainDTO expMainDTO) throws Exception {
        Map<String, Object> result = this.getExpMainDetail(expMainDTO.getId());
        if(result!=null && result.containsKey("auditList")){
            List<ExpMainDataDTO> auditList = (List<ExpMainDataDTO>)result.get("auditList");
            for(ExpMainDataDTO audit:auditList){
                if(expMainDTO.getAppOrgId().equals(audit.getCompanyId())){
                    audit.setCompanyName("");
                }
            }
        }
        if(result!=null && result.containsKey("exp")){
            Map<String,String> exp = ((Map<String,String>)result.get("exp"));
            if(exp!=null){
                if(exp.containsKey("auditCompanyId") && expMainDTO.getAppOrgId().equals(exp.get("auditCompanyId"))){
                    exp.put("auditCompanyName","");
                }
            }
        }
        return result;
    }

    /**
     * 方法描述：状态码得到状态名称
     * 作   者：LY
     * 日   期：2016/8/2 15:42
     * @param approveStatus 状态码
     * @return 审批状态(0:待审批，1:同意，2，退回, 3:撤回, 4:删除, 5.审批中）
     */
    private String getApproveStatusName(String approveStatus, String isNew) {
        if ("0".equals(approveStatus)) {
            return "待审批";
        } else if ("1".equals(approveStatus)) {
            if ("Y".equals(isNew)) {
                return "已审批(完成)";
            } else {
                return "已审批";
            }
        } else if ("2".equals(approveStatus)) {
            return "退回";
        } else if ("3".equals(approveStatus)) {
            return "撤回";
        } else if ("4".equals(approveStatus)) {
            return "删除";
        } else if ("5".equals(approveStatus)) {
            return "审批中";
        }else if ("6".equals(approveStatus)) {
            return "已拨款";
        }
        return null;
    }

    /**
     * 方法描述：报销汇总List
     * 作   者：LY
     * 日   期：2016/7/28 16:34
     * @param param 查询条件
     */
    @Override
    public List<ExpMainDTO> getExpMainPageForSummary(Map<String, Object> param) {
        if (null != param.get("pageNumber")) {
            int page = (Integer) param.get("pageNumber");
            int pageSize = (Integer) param.get("pageSize");
            param.put("startPage", page * pageSize);
            param.put("endPage", pageSize);
        }
        List<ExpMainDTO> list = expMainDao.getExpMainPageForSummary(param);
        return list;
    }



    /**
     * 方法描述：报销汇总数量
     * 作   者：LY
     * 日   期：2016/7/28 16:34
     * @param param 查询条件
     */
    @Override
    public int getExpMainPageForSummaryCount(Map<String, Object> param) {
        return expMainDao.getExpMainPageForSummaryCount(param);
    }


    /**
     * 方法描述：根据companyId查询所有有效项目(我要报销 选择项目下拉框 )app
     * 作   者：LY
     * 日   期：2016/7/27 17:39
     */
    @Override
    public List<ProjectDTO> getProjectListWS(Map<String, Object> param) {

        String companyId = (String)param.get("appOrgId");
        String accountId = (String)param.get("accountId");
        CompanyUserEntity userEntity = companyUserDao.getCompanyUserByUserIdAndCompanyId(accountId,companyId);
        if(userEntity==null){
            return new ArrayList<>();
        }
        param.put("companyId",companyId);
        param.put("type","1");//只查询自己参与的
        param.put("companyUserId",userEntity.getId());
        return projectService.getProjectListByCompanyId(param);
    }


    /**
     * 方法描述:根据id获取报销详情app
     * 作   者：LY
     * 日   期：2016/7/27 17:39
     */
    @Override
    public ExpMainDTO selectExpMainDetail(String mainId) {
        ExpMainDTO expMainDTO = expMainDao.getExpMainDetail(mainId);
        ExpAuditDTO auditDto = expAuditDao.selectAuditPersonByMainId(mainId);
        expMainDTO.setAuditPerson(auditDto.getAuditPerson());
        expMainDTO.setAuditPersonName(auditDto.getAuditPersonName());
        List<ExpDetailDTO> list = expDetailDao.selectDetailDTOByMainId(mainId);
        expMainDTO.setDetailList(list);
        return expMainDTO;
    }

    //====================================新接口2.0=====================================================

    /**
     * 方法描述：我的报销列表
     * 作   者：CZJ
     * 日   期：2016/12/27
     */
    @Override
    public List<V2ExpMainDTO> v2GetExpMainPage(Map<String, Object> param) throws Exception {
        if (null != param.get("pageIndex")) {
            int page = (Integer) param.get("pageIndex");
            int pageSize = (Integer) param.get("pageSize");
            param.put("startPage", page * pageSize);
            param.put("endPage", pageSize);
        }

        List<V2ExpMainDTO> list = expMainDao.v2GetExpMainPage(param);
        for (V2ExpMainDTO expMainDTO : list) {
            List<ExpDetailEntity> detailList = expDetailDao.selectByMainId(expMainDTO.getId());
            List<V2ExpDetailDTO> v2DetailList = BaseDTO.copyFields(detailList, V2ExpDetailDTO.class);
            for (V2ExpDetailDTO dto : v2DetailList) {
                dto.setExpAmountStr(StringUtil.getRealData(dto.getExpAmount()));
            }
            expMainDTO.setV2DetailList(v2DetailList);
        }
        return list;
    }

    AjaxMessage validateExpMainAndDetail(V2ExpMainDTO dto){
        List<ExpDetailDTO> detailList = dto.getDetailList();
        if (StringUtil.isNullOrEmpty(dto.getAuditPerson())) {
            return new AjaxMessage().setCode("1").setInfo("审批人不能为空");
        }
        if (CollectionUtils.isEmpty(detailList)) {
            return new AjaxMessage().setCode("1").setInfo("报销明细不能为空");
        }
        if (!StringUtil.isNullOrEmpty(dto.getRemark()) && dto.getRemark().length() > 255) {
            return new AjaxMessage().setCode("1").setInfo("备注长度过长");
        }
        for (ExpDetailDTO detailDTO : detailList) {
            if (detailDTO.getExpAmount() == null) {
                return new AjaxMessage().setCode("1").setInfo("报销金额不能为空");
            } else if (StringUtil.isNullOrEmpty(detailDTO.getExpType())) {
                return new AjaxMessage().setCode("1").setInfo("报销类别不能为空");
            } else if (StringUtil.isNullOrEmpty(detailDTO.getExpUse())) {
                return new AjaxMessage().setCode("1").setInfo("用途说明不能为空");
            }
        }
        return null;
    }
    /**
     * v2
     * 方法描述：报销增加或者修改
     * 作   者：CZJ
     * 日   期：2016/12/27
     */
    @Override
    public AjaxMessage v2SaveOrUpdateExpMainAndDetail(V2ExpMainDTO dto, String userId, String companyId) throws Exception {
        AjaxMessage ajaxMessage = this.validateExpMainAndDetail(dto);
        if(ajaxMessage!=null){
            return ajaxMessage;
        }
        CompanyUserEntity auditPerson = companyUserDao.selectById(dto.getAuditPerson());
        if(auditPerson==null){
            return AjaxMessage.failed("数据错误");
        }
        CompanyUserEntity currentCompanyUser = companyUserDao.getCompanyUserByUserIdAndCompanyId(userId,companyId);
        if(currentCompanyUser==null){
            return AjaxMessage.failed("数据错误");
        }
        //保存收款方信息
        if (!StringUtils.isEmpty(dto.getEnterpriseName()) && StringUtils.isEmpty(dto.getEnterpriseId())){
            EnterpriseSearchQueryDTO query = new EnterpriseSearchQueryDTO();
            query.setCompanyId(companyId);
            query.setName(dto.getEnterpriseName());
            query.setSave(true);
            ResponseBean response = enterpriseService.getRemoteData(enterpriseServer.getQueryFull(),query);
            if (response != null && response.getData() != null) {
                Map<String,Object> data = response.getData();
                Map<String,Object> enterpriseDo = (Map<String,Object>) data.get("enterpriseDO");
                if (enterpriseDo != null) {
                    dto.setEnterpriseId((String)enterpriseDo.get("id"));
                }
            }
        }

        //是增加true还是修改false操作
        boolean flag = false;
        //保存报销主表
        ExpMainEntity entity = new ExpMainEntity();
        BaseDTO.copyFields(dto, entity);
        entity.setApproveStatus("0");
        if (StringUtil.isNullOrEmpty(dto.getId())) {//插入
            saveExpMain(entity,dto,userId,companyId);
            flag= true;
        }  else {//保存
            int result = 0;
            ExpMainEntity exp = expMainDao.selectById(dto.getId());
            if(exp!=null && "2".equals(exp.getApproveStatus())){
                if(!StringUtil.isNullOrEmpty(dto.getTargetId()) && dto.getId().equals(dto.getTargetId())){
                    return AjaxMessage.failed("参数错误");
                }
                //判断是否退回后的编辑
                exp.setExpFlag(1);
                entity.setExpFlag(2);
                expMainDao.updateById(exp);
                //dto.setTargetId(null); //此处为了防止前端 更新的时候传递了targetId过来(前端生成)
                //新开一个新的报销单
                saveExpMain(entity,dto,userId,companyId);
                //复制原来的附件记录
                projectSkyDriverService.copyFileToNewObject(entity.getId(),dto.getId(),NetFileType.EXPENSE_ATTACH,dto.getDeleteAttachList());
                flag = true;
            }else {
                entity.set4Base(null, userId, null, new Date());
                //版本控制
                entity.setExpDate(DateUtils.getDate());
                result = expMainDao.updateById(entity);
                if (result == 0) {
                    return new AjaxMessage().setCode("0").setInfo("保存失败").setData(dto);
                }
            }
        }
        //主表Id
        String id = entity.getId();
        //处理图片
        if(!CollectionUtils.isEmpty(dto.getDeleteAttachList())){
            projectSkyDriverService.deleteSysDrive(dto.getDeleteAttachList(),dto.getAccountId(),id);
        }
        //保存报销明细表
        this.saveExpDetail(dto,id,userId,currentCompanyUser.getId());
        //处理审核记录
        Integer myTaskType = this.getMyTaskType(entity);
        if(flag){
            //保存报销审核表
            ExpAuditEntity auditEntity = new ExpAuditEntity();
            //parent_id approve_date audit_message
            auditEntity.setId(StringUtil.buildUUID());
            auditEntity.setMainId(id);
            auditEntity.setApproveStatus("0");
            auditEntity.setAuditPerson(dto.getAuditPerson());
            auditEntity.setSubmitAuditId(entity.getCompanyUserId());
            auditEntity.set4Base(userId, userId, new Date(), new Date());
            //如果是新增
            auditEntity.setIsNew("Y");
            expAuditDao.insert(auditEntity);
            this.myTaskService.saveMyTask(entity.getId(), myTaskType, auditPerson.getCompanyId(), dto.getAuditPerson(),false,userId,companyId);
            //推送消息
            this.sendMessageForAudit(entity.getId(),companyId, dto.getAuditPerson(),entity.getType(),userId,auditEntity.getId(),"0");//提交
        }else {
            List<ExpAuditEntity> auditEntities = expAuditDao.selectByMainId(id);
            ExpAuditEntity expAuditEntity = auditEntities.get(0);
            String oldAuditPerson = expAuditEntity.getAuditPerson();
            if (!dto.getAuditPerson().equals(oldAuditPerson)) {//如果修改的时候，改变了社会人。则发送任务
                //忽略以前发给其他人的任务
                this.myTaskService.ignoreMyTask(entity.getId(), myTaskType, oldAuditPerson);
                //指派任务
                this.myTaskService.saveMyTask(entity.getId(), myTaskType, auditPerson.getCompanyId(), dto.getAuditPerson(),false,userId,companyId);
                expAuditEntity.setAuditPerson(dto.getAuditPerson());
                expAuditDao.updateById(expAuditEntity);
                //推送消息
                this.sendMessageForAudit(entity.getId(),companyId, dto.getAuditPerson(),entity.getType(),userId,expAuditEntity.getId(),"0");//提交
            }
        }
        //处理抄送
        this.saveCopy(dto.getCcCompanyUserList(),currentCompanyUser.getId(),id,id);
        return new AjaxMessage().setCode("0").setInfo("保存成功").setData(dto);
    }

    //保存报销明细表
    private void saveExpDetail(V2ExpMainDTO dto,String id,String userId,String currentCompanyUserId) throws Exception{
        //按照MainId先删除原来明细
        expDetailDao.deleteByMainId(id);
        ExpDetailEntity detailEntity = null;
        SaveRelationRecordDTO relation = new SaveRelationRecordDTO();
        relation.setAccountId(userId);
        relation.setTargetId(id);
        int seq = 1;
        for (ExpDetailDTO detailDTO : dto.getDetailList()) {
            detailEntity = new ExpDetailEntity();
            BaseDTO.copyFields(detailDTO, detailEntity);
            detailDTO.setId(StringUtil.buildUUID());
            detailEntity.setId(detailDTO.getId());
            detailEntity.setMainId(id);
            detailEntity.setSeq(seq++);
            if (detailEntity.getExpAllName() != null) {
                String[] allName = detailEntity.getExpAllName().split("-");
                detailEntity.setExpPName(allName[0]);
                detailEntity.setExpName(allName[1]);
            }
            detailEntity.set4Base(userId, userId, new Date(), new Date());
            expDetailDao.insert(detailEntity);

            if(detailDTO.getRelationRecord()!=null && !StringUtil.isNullOrEmpty(detailDTO.getRelationRecord().getRelationId())){
                detailDTO.getRelationRecord().setOperateRecordId(detailEntity.getId());
                relation.getRelationList().add(detailDTO.getRelationRecord());
            }
        }
        //处理关联项
        this.relationRecordService.saveRelationRecord(relation);
    }

    private String getExpNo(String companyId){
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        String expNo = expMainDao.getMaxExpNo(map);
        String yyMMdd = DateUtils.date2Str(DateUtils.yyyyMMdd);
        if ("1001".equals(expNo)) {
            //自动生成一个
            expNo = yyMMdd + "0001";
        } else if (yyMMdd.equals(expNo.substring(0, expNo.length() - 4))) {

        } else {
            expNo = yyMMdd + "0001";
        }
        return expNo;
    }

    private ExpMainEntity saveExpMain(ExpMainEntity entity,V2ExpMainDTO dto,String userId,String companyId){
        String expNo = this.getExpNo(companyId);
        entity.setExpFlag(0);
        if(StringUtil.isNullOrEmpty(dto.getTargetId())){
            entity.setId(StringUtil.buildUUID());
        }else {
            entity.setId(dto.getTargetId());
        }
        dto.setExpNo(expNo);
        if(StringUtil.isNullOrEmpty(dto.getType())){
            entity.setType(1); //默认为报销费用
        }
        entity.setExpNo(expNo);
        entity.set4Base(userId, userId, new Date(), new Date());
        entity.setCompanyId(companyId);
        expMainDao.insert(entity);
        return entity;
    }
    /**
     * 方法描述：获取最大组织expNo + 1
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
    @Override
    public Map<String, Object> getMaxExpNo(Map<String, Object> param) throws Exception {

        boolean flag = true;
        while (flag) {
            int num = new Random().nextInt(999) + 1 + 1000;
            String expNo = DateUtils.date2Str(DateUtils.yyyymmddhhmmss);
            expNo = expNo + num;
            param.put("expNo", expNo);
            List<ExpMainEntity> list = expMainDao.selectByParam(param);
            flag = list.size() > 0;
        }
        param.put("maxExpNo", param.get("expNo"));
        return param;
    }

    /**
     * type: (type=1:我提交的，type=2：待审核的,type=3:我已经审核的 type=4:我提交并审批完成的,type=5:我提交的正处于审核中的,type=6:我提交未审核的,7:抄送列表)
     */
    @Override
    public List<AuditDataDTO> getAuditDataDTO(QueryAuditDTO query) throws Exception {
        String type = query.getType();
        query.setCompanyId(query.getAppOrgId());
        CompanyUserEntity user = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(query.getAccountId(),query.getAppOrgId());
        if(user==null){
            return new ArrayList<>();
        }

        if(StringUtil.isNullOrEmpty(type) || "1".equals(type)  || "4".equals(type)  || "5".equals(type) || "6".equals(type)){
            query.setCompanyUserId(user.getId());
        }
        if ("2".equals(type) || "3".equals(type)){
            query.setAuditPerson(user.getId());
        }
        if ("7".equals(type)){//抄送
            query.setCcCompanyUserId(user.getId());
        }
        List<AuditDataDTO> list = expMainDao.getAuditData(query);
        if ("1".equals(type) || "4".equals(type)  || "5".equals(type) || "6".equals(type)){
            for(AuditDataDTO dto:list){
                if(1==dto.getType() || 2==dto.getType()){
                    List<ExpDetailEntity> detailList = expDetailDao.selectByMainId(dto.getId());
                    dto.setCostList(BaseDTO.copyFields(detailList,CostDetailDTO.class));
                }
            }
        }
        return list;
    }

    @Override
    public List<AuditDataDTO> getCcAuditData(QueryAuditDTO query) throws Exception {
        query.setType("7");
        List<AuditDataDTO> list = this.getAuditDataDTO(query);
        return list;
    }

    @Override
    public List<AuditDataDTO> getPassAuditData(QueryAuditDTO query) throws Exception {
        query.setIgnoreRecall("1");//忽略 退回的记录
        return getAuditDataDTO(query);
    }

    /**
     * 财务拨款
     */
    @Override
    public int financialAllocation(String id, String currentCompanyUserId, String accountId) throws Exception{
        //版本控制，只为了方便
        ExpMainEntity entity = this.expMainDao.selectById(id);
        if(entity==null){
            return 0;
        }
        entity.setApproveStatus("6");
        entity.setAllocationDate(new Date());
        entity.setAllocationUserId(currentCompanyUserId);
        entity.setUpdateBy(accountId);
        int i= expMainDao.updateById(entity);
        financialAccount(entity);
        //发送消息
        sendMessageForAudit(id,entity.getCompanyId(),currentCompanyUserId,entity.getType(),accountId,null,entity.getApproveStatus());
        return i;
    }

    private void financialAccount(ExpMainEntity entity) throws Exception{
        SaveCompanyBillDTO billDTO = new SaveCompanyBillDTO();
        billDTO.setCompanyId(entity.getCompanyId());
        billDTO.setFromCompanyId(entity.getCompanyId());
        if(entity.getType()==1){
            billDTO.setFeeType(CompanyBillType.FEE_TYPE_EXPENSE);
        }else {
            billDTO.setFeeType(CompanyBillType.FEE_TYPE_EXP_APPLY);
        }
        billDTO.setPayType(CompanyBillType.DIRECTION_PAYER);
        billDTO.setOperatorId(entity.getAllocationUserId());
        billDTO.setPaymentDate(DateUtils.formatDate(entity.getAllocationDate()));
        billDTO.setTargetId(entity.getId());
        companyBillService.saveCompanyBill(billDTO);
    }
}

