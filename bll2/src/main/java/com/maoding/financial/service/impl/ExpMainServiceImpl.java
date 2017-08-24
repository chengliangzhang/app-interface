package com.maoding.financial.service.impl;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.constant.NetFileType;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;
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
import com.maoding.message.entity.MessageEntity;
import com.maoding.message.service.MessageService;
import com.maoding.mytask.service.MyTaskService;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.dto.CompanyDTO;
import com.maoding.org.dto.CompanyUserDataDTO;
import com.maoding.org.dto.DepartDTO;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.org.service.CompanyService;
import com.maoding.org.service.CompanyUserService;
import com.maoding.org.service.DepartService;
import com.maoding.project.dao.ProjectSkyDriverDao;
import com.maoding.project.dto.ProjectDTO;
import com.maoding.project.entity.ProjectSkyDriveEntity;
import com.maoding.project.service.ProjectService;
import com.maoding.project.service.ProjectSkyDriverService;
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
    private ExpCategoryDao expCategoryDao;//数据字典
    @Autowired
    private CompanyService companyService;
    @Autowired
    private DepartService departService;
    @Autowired
    private CompanyUserService companyUserService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private CompanyUserDao companyUserDao;
    @Autowired
    private MyTaskService myTaskService;
    @Autowired
    private ProjectSkyDriverDao projectSkyDriverDao;
    @Autowired
    private ProjectSkyDriverService projectSkyDriverService;
    @Autowired
    private MessageService messageService;

    @Override
    public AjaxMessage saveOrUpdateCategoryBaseData(ExpTypeOutDTO dto, String companyId) throws Exception {
        List<ExpTypeDTO> listAll = dto.getExpTypeDTOList();
        ExpCategoryEntity entity = null;
        if (StringUtil.isNullOrEmpty(listAll.get(0).getParent().getCompanyId())) {
            for (int i = 0; i < listAll.size(); i++) {
                String pid = StringUtil.buildUUID();
                entity = listAll.get(i).getParent();
                entity.setUpdateBy(dto.getAccountId());
                entity.setId(pid);
                entity.setCompanyId(companyId);
                expCategoryDao.insert(entity);
                List<ExpCategoryEntity> list = listAll.get(i).getChild();
                for (ExpCategoryEntity expCategoryDTO : list) {
                    BaseDTO.copyFields(expCategoryDTO, entity);
                    entity.setPid(pid);
                    entity.setId(StringUtil.buildUUID());
                    entity.setCompanyId(companyId);
                    if (StringUtil.isNullOrEmpty(expCategoryDTO.getStatus())) {
                        entity.setStatus("0");
                    }
                    expCategoryDao.insert(entity);
                }
            }

            return new AjaxMessage().setCode("0").setInfo("保存成功").setData(dto);

        } else {

            //需要删除的数据，逻辑删除
            if (dto != null && !CollectionUtils.isEmpty(dto.getDeleteExpTypeList())) {
                for (ExpCategoryEntity expCategoryDTO : dto.getDeleteExpTypeList()) {
                    expCategoryDTO.setStatus("1");
                    expCategoryDao.updateById(expCategoryDTO);
                }
            }

            //所有要更新的数据。
            for (int i = 0; i < listAll.size(); i++) {

                entity = listAll.get(i).getParent();
                String pid = entity.getId();
                entity.setUpdateBy(dto.getAccountId());
                expCategoryDao.updateById(entity);

                //删除子条目
                Map<String, Object> mapParams = new HashMap<String, Object>();
                mapParams.put("companyId", companyId);
                mapParams.put("pid", pid);
                // expCategoryDao.deleteByPId(mapParams);
                List<ExpCategoryEntity> list = listAll.get(i).getChild();
                for (ExpCategoryEntity child : list) {
                    child.setStatus("0");
                    child.setPid(pid);
                    child.setCompanyId(companyId);
                    if (StringUtil.isNullOrEmpty(child.getId())) {
                        ExpCategoryEntity categoryEntity = expCategoryDao.selectByName(pid, child.getName());
                        if (categoryEntity != null) {
                            categoryEntity.setExpTypeMemo(child.getExpTypeMemo());
                            categoryEntity.setStatus("0");
                            expCategoryDao.updateById(categoryEntity);
                            child.setId(categoryEntity.getId());
                        } else {
                            child.setId(StringUtil.buildUUID());
                            expCategoryDao.insert(child);
                        }
                    } else {
                        expCategoryDao.updateById(child);
                    }
                }
            }
        }
        return new AjaxMessage().setCode("0").setInfo("保存成功").setData(dto);
    }

    /**
     * 方法描述：报销基础数据
     * 作   者：LY
     * 日   期：2016/7/27 17:59
     */
    public AjaxMessage getExpBaseData(String companyId, String userId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("expTypeList", getExpTypeList(companyId));
        map.put("projectList", getProjectList(companyId));
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("companyId", companyId);
        mapParams.put("userId", userId);
        map.put("departList", departService.getDepartByUserIdContentCompany(mapParams));

        CompanyDTO company = companyService.getCompanyById(companyId);

        List<DepartDTO> list = departService.getDepartByCompanyId(mapParams);
        DepartDTO dto = new DepartDTO();
        dto.setId(companyId);
        dto.setDepartName(company.getCompanyName());
        list.add(0, dto);
        map.put("departListByCompanyId", list);
        map.put("companyUserList", companyUserService.getCompanyUserByCompanyId(companyId));
        return new AjaxMessage().setCode("0").setData(map);
    }

    /**
     * 方法描述：报销类别基础数据
     * 作   者：LY
     * 日   期：2016/7/27 17:59
     */
    public AjaxMessage getCategoryBaseData(String companyId, String userId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        List<ExpTypeDTO> expTypes = new ArrayList<ExpTypeDTO>();
        Map param = new HashMap();
        param.put("pid", "");
        param.put("companyId", companyId);
        List<ExpCategoryEntity> list = expCategoryDao.getDataByParemeter(param);
        //如果该公司的报销类别尚未初始化数据。则初始化
        if (list == null || list.size() == 0) {
            param.clear();
            param.put("pid", "");
            param.put("companyId", "");
            list = expCategoryDao.getDataByParemeter(param);
            //初始化本公司的报销类别基础数据
            if (!CollectionUtils.isEmpty(list)) {
                for (ExpCategoryEntity expCategoryEntity : list) {
                    String oldId = expCategoryEntity.getId();//旧id
                    expCategoryEntity.setId(StringUtil.buildUUID());
                    expCategoryEntity.setCompanyId(companyId);
                    expCategoryDao.insert(expCategoryEntity);
                    param.clear();
                    param.put("pid", oldId);
                    List<ExpCategoryEntity> childList = expCategoryDao.getDataByParemeter(param);
                    if (!CollectionUtils.isEmpty(childList)) {
                        for (ExpCategoryEntity child : childList) {
                            child.setId(StringUtil.buildUUID());
                            child.setPid(expCategoryEntity.getId());
                            child.setCompanyId(companyId);
                            expCategoryDao.insert(child);
                        }
                    }
                }
            }
        }
        //重新获取数据
        map.put("expTypeList", getExpTypeList(companyId));
        return new AjaxMessage().setCode("0").setData(map);
    }

    /**
     * 方法描述：得到当前公司和当前组织下面人员
     * 作   者：LY
     * 日   期：2016/8/3 17:17
     * @param companyId 公司Id  orgId 组织Id
     */
    public List<CompanyUserDataDTO> getUserList(String companyId, String orgId) throws Exception {
        Map<String, Object> mapParams = new HashMap<String, Object>();
        mapParams.put("companyId", companyId);
        if (!companyId.equals(orgId)) {
            mapParams.put("orgId", orgId);
        }
        return companyUserService.getUserByOrgId(mapParams);
    }

    /**
     * 方法描述：查询报销类型
     * 用于我要报销界面，报销类型选择
     * 作        者：MaoSF
     * 日        期：2015年12月7日-上午11:21:49
     */
    @Override
    public List<ExpTypeDTO> getExpTypeList(String companyId) {
        List<ExpTypeDTO> expTypes = new ArrayList<ExpTypeDTO>();
        Map param = new HashMap();
        // param.put("code", "bx");
        param.put("pid", "");
        // param.put("isLike", "true");
        param.put("companyId", companyId);
        List<ExpCategoryEntity> list = expCategoryDao.getDataByParemeter(param);
        if (list == null || list.size() == 0) {
            param.clear();
            param.put("pid", "");
            param.put("companyId", "");
            list = expCategoryDao.getDataByParemeter(param);
        }
        if (!CollectionUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                ExpTypeDTO typeBean = new ExpTypeDTO();
                typeBean.setParent(list.get(i));
                param.clear();
                param.put("pid", list.get(i).getId());
                param.put("status", "0");
                List<ExpCategoryEntity> child = expCategoryDao.getDataByParemeter(param);
                typeBean.setChild(child);
                expTypes.add(typeBean);
            }
        }

        return expTypes;
    }

    /**
     * 方法描述：根据companyId查询所有有效项目(我要报销 选择项目下拉框 )
     * 作   者：LY
     * 日   期：2016/7/27 17:39
     */
    public List<ProjectDTO> getProjectList(String companyId) {
        ProjectDTO dto = new ProjectDTO();
        dto.setCompanyId(companyId);
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        return projectService.getProjectListByCompanyId(map);
    }

    /**
     * 方法描述：我的报销列表
     * 作   者：LY
     * 日   期：2016/7/28 16:34
     * @param param 查询条件
     */
    public List<ExpMainDTO> getExpMainPage(Map<String, Object> param) {
        if (null != param.get("pageNumber")) {
            int page = (Integer) param.get("pageNumber");
            int pageSize = (Integer) param.get("pageSize");
            param.put("startPage", page * pageSize);
            param.put("endPage", pageSize);
        }
        List<ExpMainDTO> list = expMainDao.getExpMainPage(param);
//        for(ExpMainDTO expMainDTO:list){
//            List<ExpDetailEntity> detailList =expDetailDao.selectByMainId(expMainDTO.getId());
//            expMainDTO.setDetailCount(detailList.size());
//            for(ExpDetailEntity d:detailList){
//                expMainDTO.setExpSumAmount(expMainDTO.getExpSumAmount().add(d.getExpAmount()));
//            }8 = {HashMap$Node@6541} "userId" -> "c7a113c4d00e4ed09b4c15a77fdf5d09"
//            expMainDTO.setExpUse(detailList.get(0).getExpUse());
//        }
        return list;
    }

    /**
     * 方法描述：我的报销列表Interface
     * 作   者：LY
     * 日   期：2016/7/28 16:34
     * @param param 查询条件
     */
    public List<ExpMainDTO> getExpMainPageInterface(Map<String, Object> param) {
        if (null != param.get("pageNumber")) {
            int page = (Integer) param.get("pageNumber");
            int pageSize = (Integer) param.get("pageSize");
            param.put("startPage", page * pageSize);
            param.put("endPage", pageSize);
        }
        List<ExpMainDTO> list = expMainDao.getExpMainPageInterface(param);
//        for(ExpMainDTO expMainDTO:list){
//            List<ExpDetailEntity> detailList =expDetailDao.selectByMainId(expMainDTO.getId());
//            expMainDTO.setDetailCount(detailList.size());
//            for(ExpDetailEntity d:detailList){
//                expMainDTO.setExpSumAmount(expMainDTO.getExpSumAmount().add(d.getExpAmount()));
//            }8 = {HashMap$Node@6541} "userId" -> "c7a113c4d00e4ed09b4c15a77fdf5d09"
//            expMainDTO.setExpUse(detailList.get(0).getExpUse());
//        }
        return list;
    }

    public int getExpMainPageCount(Map<String, Object> param) {
        return expMainDao.getExpMainPageCount(param);
    }


    /**
     * 方法描述：撤回报销
     * 作   者：LY
     * 日   期：2016/7/29 11:01
     * @param id--报销单id type--状态(3撤回)
     */
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
        this.sendMessageForType20(dto.getMainId(), entity.getCompanyId(), entity.getUserId());

        return expMainDao.updateById(entity);
    }

    private void sendMessageForType20(String mainId, String companyId, String userId) throws Exception {

        CompanyUserEntity companyUserEntity = this.companyUserDao.selectById(userId);
        if (companyUserEntity != null) {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setTargetId(mainId);
            messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_20);
            messageEntity.setCompanyId(companyId);
            messageEntity.setUserId(companyUserEntity.getUserId());
            this.messageService.sendMessage(messageEntity);
        }
    }

    /**
     * 方法描述：报销详情
     * 作   者：LY
     * 日   期：2016/7/29 10:53
     */
    public ExpMainDTO getExpMainPageDetail(String id) throws Exception {
        ExpMainDTO dto = new ExpMainDTO();
        ExpMainEntity entity = expMainDao.selectById(id);

        BaseDTO.copyFields(entity, dto);
        ExpAuditDTO auditDto = expAuditDao.selectAuditPersonByMainId(id);
        dto.setAuditPerson(auditDto.getAuditPerson());
        dto.setAuditPersonName(auditDto.getAuditPersonName());
        List<ExpDetailDTO> list = expDetailDao.selectDetailDTOByMainId(id);
        dto.setDetailList(list);

        return dto;
    }

    /**
     * 方法描述：删除报销
     * 作   者：LY
     * 日   期：2016/7/29 10:53
     */
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
    public int agreeExpMain(String id, String companyUserId, String versionNum) throws Exception {
        int i = recallExpMain(id, versionNum, "1");
        //处理我的任务
        ExpMainEntity mainEntity = this.expMainDao.selectById(id);
        if (mainEntity != null) {
            this.myTaskService.handleMyTask(id, companyUserId, "1");//处理我的任务

            //给报销人推送消息
            CompanyUserEntity companyUserEntity = this.companyUserDao.selectById(mainEntity.getUserId());
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setCompanyId(mainEntity.getCompanyId());
            messageEntity.setTargetId(mainEntity.getId());
            messageEntity.setUserId(companyUserEntity.getUserId());
            messageEntity.setMessageType(SystemParameters.MESSAGE_TYPE_22);
            this.messageService.sendMessage(messageEntity);
        }


        return i;
    }

    /**
     * 方法描述：同意报销并转移审批人
     * 作   者：LY
     * 日   期：2016/8/1 15:08
     * @param id--报销单id auditPerson--新审批人  userId用户Id
     */
    public int agreeAndTransAuditPerExpMain(String id, String companyUserId, String auditPerson, String versionNum, String accountId) throws Exception {
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
        List<ExpAuditEntity> auditEntities = expAuditDao.selectByMainId(id);
        if (!CollectionUtils.isEmpty(auditEntities)) {
            parentId = auditEntities.get(0).getId();
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
        auditEntity.setApproveStatus("5");
        auditEntity.setAuditPerson(auditPerson);
        auditEntity.set4Base(accountId, accountId, new Date(), new Date());
        expAuditDao.insert(auditEntity);

        //更新报销主表审批状态
        ExpMainEntity entity = this.expMainDao.selectById(id);
        //指派任务
        this.myTaskService.saveMyTask(entity.getId(), SystemParameters.EXP_AUDIT, entity.getCompanyId(), auditPerson);
        //处理任务
        this.myTaskService.handleMyTask(id, companyUserId, "1");

        //修改状态
        entity.setApproveStatus("5");
        return expMainDao.updateById(entity);
    }

    /**
     * 方法描述：报销详情与审批记录
     * 作   者：LY
     * 日   期：2016/8/2 14:13
     */
    public Map<String, Object> getExpMainDetail(String id) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        ExpMainEntity expMainEntity = expMainDao.selectById(id);


        if (null != expMainEntity) {
            Map<String, Object> mapParam = new HashMap<String, Object>();
            mapParam.put("mainId", id);
            List<ExpAuditEntity> auditList = expAuditDao.selectByParam(mapParam);
            CompanyUserEntity companyUser = companyUserService.selectById(auditList.get(0).getAuditPerson());
            Map<String, String> exp = new HashMap<>();

            CompanyUserEntity companyUserEntity = companyUserDao.selectById(expMainEntity.getUserId());
            exp.put("expFlag", expMainEntity.getExpFlag() + "");
            exp.put("auditCompanyUserId", companyUser.getId());
            exp.put("auditUserName", companyUser.getUserName());
            exp.put("submitter", companyUserEntity.getUserName());
            exp.put("submittime", DateUtils.formatTimeSlash(expMainEntity.getCreateDate()));
            exp.put("remark", expMainEntity.getRemark());
            exp.put("approveStatus", expMainEntity.getApproveStatus());
            exp.put("versionNum", expMainEntity.getVersionNum() + "");
            exp.put("expNo", expMainEntity.getExpNo());
            mapParam.clear();
            mapParam.put("mainId", id);
            mapParam.put("approveStatus", 2);
            List<ExpAuditEntity> listExpAuditEntity = expAuditDao.selectByParam(mapParam);
            if (listExpAuditEntity.size() > 0) {
                exp.put("sendBackReason", listExpAuditEntity.get(0).getAuditMessage());
            } else {
                exp.put("sendBackReason", "");
            }
            map.put("exp", exp);
        }

        List<ExpDetailDTO> detailList = expDetailDao.selectDetailDTOByMainId(id);

        map.put("detailList", detailList);
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("fastdfsUrl", this.fastdfsUrl);
        param.put("id", id);
        List<ExpMainDTO> auditList = new ArrayList<>();
        ExpMainDTO expMainDTO = expMainDao.selectByIdWithUserName(param);
        expMainDTO.setApproveStatusName("发起申请");
        auditList.add(expMainDTO);

        List<ExpMainDTO> list = expAuditDao.selectAuditDetailByMainId(param);
        for (ExpMainDTO dto : list) {
            dto.setApproveStatusName(getApproveStatusName(dto.getApproveStatus(), dto.getIsNew()));
        }
        auditList.addAll(list);
        map.put("auditList", auditList);

        param.clear();
        param.put("targetId", id);
        param.put("type", NetFileType.EXPENSE_ATTACH);
        List<ProjectSkyDriveEntity> expAttachList = this.projectSkyDriverService.getNetFileByParam(param);
        map.put("expAttachList", expAttachList);

        map.put("totalExpAmount", detailList.get(0).getTotalExpAmount());

        return map;
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
        }
        return null;
    }

    /**
     * 方法描述：报销汇总List
     * 作   者：LY
     * 日   期：2016/7/28 16:34
     * @param param 查询条件
     */
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
     * 方法描述：报销汇总ListInterface
     * 作   者：LY
     * 日   期：2016/7/28 16:34
     * @param param 查询条件
     */
    public List<ExpMainDTO> getExpMainPageForSummaryInterface(Map<String, Object> param) {
        if (null != param.get("pageNumber")) {
            int page = (Integer) param.get("pageNumber");
            int pageSize = (Integer) param.get("pageSize");
            param.put("startPage", page * pageSize);
            param.put("endPage", pageSize);
        }
        List<ExpMainDTO> list = expMainDao.getExpMainPageForSummaryInterface(param);
        return list;
    }


    /**
     * 方法描述：报销汇总数量
     * 作   者：LY
     * 日   期：2016/7/28 16:34
     * @param param 查询条件
     */
    public int getExpMainPageForSummaryCount(Map<String, Object> param) {
        return expMainDao.getExpMainPageForSummaryCount(param);
    }


    /**
     * 方法描述：根据companyId查询所有有效项目(我要报销 选择项目下拉框 )app
     * 作   者：LY
     * 日   期：2016/7/27 17:39
     */
    public List<ProjectDTO> getProjectListWS(Map<String, Object> param) {
        return projectService.getProjectListByCompanyId(param);
    }


    /**
     * 方法描述:根据id获取报销详情app
     * 作   者：LY
     * 日   期：2016/7/27 17:39
     */
    public ExpMainDTO selectExpMainDetail(String mainId) {
        ExpMainDTO expMainDTO = expMainDao.getExpMainDetail(mainId);
        ExpAuditDTO auditDto = expAuditDao.selectAuditPersonByMainId(mainId);
        expMainDTO.setAuditPerson(auditDto.getAuditPerson());
        expMainDTO.setAuditPersonName(auditDto.getAuditPersonName());
        List<ExpDetailDTO> list = expDetailDao.selectDetailDTOByMainId(mainId);
        expMainDTO.setDetailList(list);
        return expMainDTO;
    }


    @Override
    public AjaxMessage deleteCategoryBaseData(String id) throws Exception {
        expCategoryDao.deleteById(id);
        return new AjaxMessage().setCode("0").setInfo("操作成功");
    }

    @Override
    public AjaxMessage initInsertCategory(String companyId) throws Exception {

        expCategoryDao.initInsertCategory(companyId);
        return new AjaxMessage().setCode("0").setInfo("操作成功");
    }

    //====================================新接口2.0=====================================================

    /**
     * 方法描述：我的报销列表
     * 作   者：CZJ
     * 日   期：2016/12/27
     */
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

    /**
     * v2
     * 方法描述：报销增加或者修改
     * 作   者：CZJ
     * 日   期：2016/12/27
     */
    public AjaxMessage v2SaveOrUpdateExpMainAndDetail(V2ExpMainDTO dto, String userId, String companyId) throws Exception {


        //报销明细
        List<ExpDetailDTO> detailList = dto.getDetailList();
        //是增加true还是修改false操作
        boolean flag = StringUtil.isNullOrEmpty(dto.getId());

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
        // 验证end

        //保存报销主表
        ExpMainEntity entity = new ExpMainEntity();
        BaseDTO.copyFields(dto, entity);
        //
        entity.setApproveStatus("0");

        if (StringUtil.isNullOrEmpty(dto.getId())) {//插入
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

            //判断是否退回后的编辑
            if (!StringUtil.isNullOrEmpty(dto.getPid())) {
                ExpMainEntity exp = expMainDao.selectById(dto.getPid());
                exp.setExpFlag(1);
                entity.setExpFlag(2);
                expMainDao.updateById(exp);

                //新开
                entity.setId(StringUtil.buildUUID());

                //复制原来的附件记录
                Map<String, Object> param = new HashMap<>();
                param.put("targetId", dto.getPid());
                param.put("type", NetFileType.EXPENSE_ATTACH);
                List<ProjectSkyDriveEntity> attatchs = projectSkyDriverService.getNetFileByParam(param);

                for (ProjectSkyDriveEntity attatch : attatchs) {
                    attatch.setId(StringUtil.buildUUID());
                    attatch.setTargetId(entity.getId());
                    projectSkyDriverDao.insert(attatch);
                }
            } else {
                entity.setExpFlag(0);
                entity.setId(dto.getTargetId());
            }
            dto.setExpNo(expNo);
            entity.setExpNo(expNo);
            entity.set4Base(userId, userId, new Date(), new Date());
            entity.setCompanyId(companyId);
            expMainDao.insert(entity);
            //第三方操作
            //tagetId,companyId
            //dealThirdParty(dto.getAuditPerson(),companyId);
        } else {//保存
            entity.set4Base(null, userId, null, new Date());
            //版本控制

            int result = expMainDao.updateById(entity);
            //第三方操作
            //tagetId,companyId
            //dealThirdParty(dto.getAuditPerson(),companyId);

            if (result == 0) {
                return new AjaxMessage().setCode("0").setInfo("保存失败").setData(dto);
            }
        }
        //主表Id
        String id = entity.getId();

        //保存报销明细表
        //按照MainId先删除原来明细
        expDetailDao.deleteByMainId(id);
        ExpDetailEntity detailEntity = null;
        int seq = 1;
        for (ExpDetailDTO detailDTO : detailList) {
            detailEntity = new ExpDetailEntity();
            BaseDTO.copyFields(detailDTO, detailEntity);
//            if(StringUtil.isNullOrEmpty(detailDTO.getId())){
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
        }

        //保存报销审核表
        ExpAuditEntity auditEntity = new ExpAuditEntity();
        //parent_id approve_date audit_message
        auditEntity.setId(StringUtil.buildUUID());

        auditEntity.setMainId(id);
        auditEntity.setApproveStatus("0");
        auditEntity.setAuditPerson(dto.getAuditPerson());
        auditEntity.set4Base(userId, userId, new Date(), new Date());
        List<ExpAuditEntity> auditEntities = expAuditDao.selectByMainId(id);
        if (!flag) {//修改
            //查询原来为“Y"的Id并把它插入到当前auditEntity的parentId

            if (!CollectionUtils.isEmpty(auditEntities)) {
                auditEntity.setParentId(auditEntities.get(0).getId());
            }
            //把原来所有的is_new改为“N"
            expAuditDao.updateIsNewByMainId(id);
        }
        //每次都新生成记录(包括多次编辑和多次驳回)
        if (null != auditEntities && auditEntities.size() > 0) {
            ExpAuditEntity expAuditEntity = auditEntities.get(0);
            if (auditEntities.get(0).getApproveStatus().equals("0")) {
                String oldAuditPerson = expAuditEntity.getAuditPerson();
                expAuditEntity.setAuditPerson(dto.getAuditPerson());
                expAuditEntity.setIsNew("Y");
                expAuditDao.updateById(expAuditEntity);
                if (!dto.getAuditPerson().equals(oldAuditPerson)) {//如果修改的时候，改变了社会人。则发送任务
                    //忽略以前发给其他人的任务
                    this.myTaskService.ignoreMyTask(entity.getId(), SystemParameters.EXP_AUDIT, oldAuditPerson);
                    //指派任务
                    this.myTaskService.saveMyTask(entity.getId(), SystemParameters.EXP_AUDIT, companyId, dto.getAuditPerson());
                }
            } else {
                auditEntity.setIsNew("Y");
                expAuditDao.insert(auditEntity);
                //指派任务，此处为。被打回后。重新修改。启动审批
                this.myTaskService.saveMyTask(entity.getId(), SystemParameters.EXP_AUDIT, companyId, dto.getAuditPerson());
            }
        } else {
            auditEntity.setIsNew("Y");
            expAuditDao.insert(auditEntity);
            //指派任务 ，新增报销，发送任务
            this.myTaskService.saveMyTask(entity.getId(), SystemParameters.EXP_AUDIT, companyId, dto.getAuditPerson());
        }

        //利用卯丁组手发送信息
        //sendExpMessage(dto);

        return new AjaxMessage().setCode("0").setInfo("保存成功").setData(dto);
    }

    /*private void sendExpMessage(V2ExpMainDTO dto) {
        String userId = dto.getUserId();
        String auditPerson = dto.getAuditPerson();
        String companyId = dto.getCompanyId();
        //报销人
        CompanyUserEntity  expCompanyUserEntity = companyUserDao.selectById(userId);
        //审批人
        CompanyUserEntity  auditCompanyUserEntity = companyUserDao.selectById(auditPerson);

        if(expCompanyUserEntity != null && auditCompanyUserEntity !=null){


            Map<String,Object> map = new HashMap<>();
            map.put("userName",expCompanyUserEntity.getUserName()+"进行了报销操作，需要你的审核");
            map.put("userId",auditCompanyUserEntity.getUserId());
            try {
                 producerService.sendMessage(sendMessageDestination, map);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }*/


    /**
     * 方法描述：获取最大组织expNo + 1
     * 作   者：ZhujieChen
     * 日   期：2016/12/22
     */
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
}

