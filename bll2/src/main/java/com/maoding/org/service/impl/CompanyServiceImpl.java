package com.maoding.org.service.impl;

import com.maoding.conllaboration.service.CollaborationService;
import com.maoding.conllaboration.service.CompanyDiskService;
import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.component.sms.SmsSender;
import com.maoding.core.component.sms.bean.Sms;
import com.maoding.core.constant.NetFileType;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.MD5Helper;
import com.maoding.core.util.StringUtil;
import com.maoding.core.util.StringUtils;
import com.maoding.financial.service.ExpAuditService;
import com.maoding.hxIm.service.ImService;
import com.maoding.org.dao.*;
import com.maoding.org.dto.*;
import com.maoding.org.entity.*;
import com.maoding.org.service.*;
import com.maoding.partner.dao.PartnerDao;
import com.maoding.partner.entity.PartnerEntity;
import com.maoding.project.dto.NetFileDTO;
import com.maoding.project.service.ProjectSkyDriverService;
import com.maoding.projectmember.service.ProjectMemberService;
import com.maoding.system.dao.DataDictionaryDao;
import com.maoding.system.entity.DataDictionaryEntity;
import com.maoding.user.dao.AccountDao;
import com.maoding.user.entity.AccountEntity;
import com.maoding.user.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：CompanyServiceImpl
 * 类描述：组织（公司）ServiceImpl
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午4:24:38
 */
@Service("companyService")
public class CompanyServiceImpl extends GenericService<CompanyEntity> implements CompanyService {

    @Value("${server.url}")
    protected String serverUrl;
    @Value("${android.url}")
    protected String androidUrl;
    @Value("${ios.url}")
    protected String iosUrl;
    @Value("${fastdfs.url}")
    protected String fastdfsUrl;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private DepartDao departDao;
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private CompanyRelationDao companyRelationDao;
    @Autowired
    private CompanyRelationAuditDao companyRelationAuditDao;
    @Autowired
    private SubCompanyDao subCompanyDao;
    @Autowired
    private CompanyUserDao companyUserDao;
    @Autowired
    private TeamOperaterDao teamOperaterDao;
    @Autowired
    private BusinessPartnerDao businessPartnerDao;
    @Autowired
    private DataDictionaryDao dataDictionaryDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrgUserDao orgUserDao;
    @Autowired
    private SmsSender smsSender;
    @Autowired
    private ExpAuditService expAuditService;
    @Autowired
    private TeamOperaterService teamOperaterService;
    @Autowired
    private CompanyRelationAuditService companyRelationAuditService;
    @Autowired
    private CollaborationService collaborationService;
    @Autowired
    private CompanyDiskService companyDiskService;
    @Autowired
    private ProjectSkyDriverService projectSkyDriverService;
    @Autowired
    private OrgAuthDao orgAuthDao;

    @Autowired
    private CompanyInviteDao companyInviteDao;

    @Autowired
    private CompanyRelationService companyRelationService;

    @Autowired
    private CompanyUserService companyUserService;

    @Autowired
    private ImService imService;

    @Autowired
    private ProjectMemberService projectMemberService;

    @Autowired
    private PartnerDao partnerDao;


    /**
     * 方法描述：验证公司信息
     * 作者：MaoSF
     * 日期：2016/8/19
     * @param:
     * @return:
     */
    public AjaxMessage validateSaveOrUpdateCompany(CompanyDTO dto) throws Exception {

        if (StringUtil.isNullOrEmpty(dto.getCompanyName())) {
            return new AjaxMessage().setCode("1").setInfo("公司名不能为空");
        }
       /* if (StringUtil.isNullOrEmpty(dto.getCompanyShortName())) {
            return new AjaxMessage().setCode("1").setInfo("公司简称不能为空");
        }*/
       /* CompanyEntity company = companyDao.getCompanyByCompanyName(dto.getCompanyName());
        CompanyEntity company2 = companyDao.getCompanyByCompanyShortName(dto.getCompanyShortName());
        if(StringUtil.isNullOrEmpty(dto.getId())){
            if (company!= null) {
                return new AjaxMessage().setCode("1").setInfo(dto.getCompanyName() + ":公司名已经被注册过");
            }
            if (company2 != null) {
                return new AjaxMessage().setCode("1").setInfo(dto.getCompanyShortName() + ":公司简称已经被占用");
            }
        }else {
            if(company!=null && !dto.getId().equals(company.getId())){
                if (dto.getCompanyName().equals(company.getCompanyName())) {
                    return new AjaxMessage().setCode("1").setInfo(dto.getCompanyName() + ":公司名已经被注册过");
                }
            }
            if(company2!=null && !dto.getId().equals(company2.getId())){
                if (dto.getCompanyShortName().equals(company2.getCompanyShortName())) {
                    return new AjaxMessage().setCode("1").setInfo(dto.getCompanyShortName() + ":公司简称已经被占用");
                }
            }
        }*/
        return new AjaxMessage().setCode("0");
    }

    /**
     * 保存公司(创建组织)
     */
    public String saveCompany(CompanyEntity company, String userName, String orgManagerId, String currUserId) throws Exception {
        String companyId = company.getId();
        if (StringUtils.isBlank(companyId)) {
            companyId = StringUtil.buildUUID();
            company.setId(companyId);
        }
        company.setCreateBy(currUserId);
        this.companyDao.insert(company);

        OrgAuthEntity orgAuthentication = new OrgAuthEntity();
        orgAuthentication.setId(companyId);
        orgAuthentication.setAuthenticationStatus(0);
        orgAuthentication.setApplyDate(new Date());
        orgAuthentication.setExpiryDate(DateUtils.getDateAfter2(orgAuthentication.getApplyDate(), 30));
        this.orgAuthDao.insert(orgAuthentication);

        //复杂权限
//        this.rolePermissionDao.deleteByCompanyId(company.getId());
//        List<RolePermissionEntity> initData = this.rolePermissionDao.getAllDefaultPermission();
//        if (!CollectionUtils.isEmpty(initData)) {
//            for (RolePermissionEntity rolePermission : initData) {
//                rolePermission.setId(StringUtil.buildUUID());
//                rolePermission.setCompanyId(company.getId());
//                this.rolePermissionDao.insert(rolePermission);
//            }
//        }

        //保存OrgEntity,组织基础表
        OrgEntity org = new OrgEntity();
        org.setId(companyId);//基础表id和公司表id一致
        org.setOrgType("0");
        org.setOrgStatus("0");
        org.setCreateBy(currUserId);
        orgDao.insert(org);

        //添加公司成员信息
        CompanyUserEntity companyUserEntity = new CompanyUserEntity();
        String companyUserId = StringUtil.buildUUID();
        companyUserEntity.setId(companyUserId);
        companyUserEntity.setCompanyId(companyId);
        companyUserEntity.setUserId(orgManagerId);
        companyUserEntity.setAuditStatus("1");//自己创建直接通过
        companyUserEntity.setRelationType("1");
        companyUserEntity.setCreateBy(currUserId);
        companyUserEntity.setUserName(userName);
        companyUserDao.insert(companyUserEntity);

        //保存OrgEntity,组织基础表
        OrgEntity orgUserEntity = new OrgEntity();
        orgUserEntity.setId(companyUserId);//基础表id和人员表id一致
        orgUserEntity.setOrgType("4");
        orgUserEntity.setOrgStatus("0");
        orgUserEntity.setCreateBy(currUserId);
        orgDao.insert(orgUserEntity);

        //人加入组织中间表
        OrgUserEntity orgUser = new OrgUserEntity();
        orgUser.setId(StringUtil.buildUUID());
        orgUser.setOrgId(companyId);
        orgUser.setCompanyId(companyId);
        orgUser.setCuId(companyUserId);
        orgUser.setUserId(orgManagerId);
        orgUser.setCreateBy(currUserId);
        orgUserDao.insert(orgUser);

        //创建团队管理员
        TeamOperaterEntity teamOperaterEntity = new TeamOperaterEntity();
        teamOperaterEntity.setUserId(orgManagerId);
        teamOperaterEntity.setCompanyId(companyId);
        teamOperaterEntity.setCreateBy(currUserId);
        teamOperaterService.saveSystemManage(teamOperaterEntity);

        //生成二维码
        projectSkyDriverService.createCompanyQrcode(companyId);

        //第五步，创建群组
        imService.createImGroup(company.getId(), orgManagerId, company.getCompanyName(), 0);

        //通知协同
        this.collaborationService.pushSyncCMD_CU(companyId);

        this.companyDiskService.initDisk(companyId);

        return companyId;
    }


    @Override
    public AjaxMessage saveOrUpdateCompany(CompanyDTO dto) throws Exception {
        //简称=全称
        dto.setCompanyShortName(dto.getCompanyName());
        AjaxMessage ajaxMessage = validateSaveOrUpdateCompany(dto);
        if (!"0".equals(ajaxMessage.getCode())) {
            return ajaxMessage;
        }
        CompanyEntity entity = null;
        if (StringUtil.isNullOrEmpty(dto.getId())) {
            entity = new CompanyEntity();
            BaseDTO.copyFields(dto, entity);

            AccountEntity account = this.accountDao.selectById(dto.getAccountId());
            if (account == null) {
                return AjaxMessage.failed("操作失败");
            }
            String id = this.saveCompany(entity, dto.getAccountId(), account.getUserName(), dto.getAccountId());
            dto.setId(id);


        } else {
            entity = new CompanyEntity();
            BaseDTO.copyFields(dto, entity);
            entity.setUpdateBy(dto.getAccountId());
            companyDao.updateById(entity);

//            Map<String,Object> map = new HashMap<String,Object>();
//            map.put("companyId",dto.getId());
//            map.put("fileType",4);
//            List<CompanyAttachEntity> companyAttachList = companyAttachDao.getCompanyAttachByParamer(map);
//            if (!StringUtil.isNullOrEmpty(dto.getFilePath())) {
//                if(companyAttachList.size()>0){
//                    CompanyAttachEntity companyAttachEntity= companyAttachList.get(0);
//                    companyAttachEntity.setFilePath(dto.getFilePath());
//                    companyAttachEntity.setFileGroup(dto.getFileGroup());
//                    companyAttachEntity.setFileName(dto.getFileName());
//                    companyAttachService.updateById(companyAttachEntity);
//                }else {
//                    CompanyAttachEntity companyAttachEntity = new CompanyAttachEntity();
//                    companyAttachEntity.setId(StringUtil.buildUUID());
//                    companyAttachEntity.setCompanyId(dto.getId());
//                    companyAttachEntity.setFileGroup(dto.getFileGroup());
//                    companyAttachEntity.setFilePath(dto.getFilePath());
//                    companyAttachEntity.setFileName(dto.getFileName());
//                    companyAttachEntity.setFileType("4");
//                    companyAttachEntity.setCreateDate(new Date());
//                    companyAttachEntity.setCreateBy(dto.getAccountId());
//                    companyAttachDao.insert(companyAttachEntity);
//                }
//            }
//
//            //修改群组
//            ImGroupEntity group = new ImGroupEntity();
//            group.setOrgId(entity.getId());
//            group.setName(entity.getCompanyShortName());
//            if (!StringUtil.isNullOrEmpty(dto.getFilePath())) {
//                group.setImg(dto.getFileGroup()+"/"+dto.getFilePath());
//            }
//            try {
//                producerService.sendGroupMessage(updateGroupDestination, group);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
        return new AjaxMessage().setCode("0").setInfo("保存成功").setData(dto);
    }

    public AjaxMessage validateCompany(SubCompanyDTO dto) throws Exception {
       /* if(!dto.getPid().equals(dto.getCompanyId())){
            return new AjaxMessage().setCode("1").setInfo("分公司的根父结点必须是当前公司");
        }*/
        if (StringUtil.isNullOrEmpty(dto.getCompanyName())) {
            return new AjaxMessage().setCode("1").setInfo("组织名称不能为空");//20161215组织
        }

       /* if (StringUtil.isNullOrEmpty(dto.getCompanyShortName())) {
            return new AjaxMessage().setCode("1").setInfo("组织简称不能为空");//20161215组织
        }

        if (StringUtil.isNullOrEmpty(dto.getProvince())) {
            return new AjaxMessage().setCode("1").setInfo("组织地址不能为空");//20161215组织
        }*/
/*
        if (this.getCompanyDtoByCompanyName(dto.getCompanyName()) != null) {
            return new AjaxMessage().setCode("1").setInfo(dto.getCompanyName()+":组织名称已经存在");//20161215组织
        }
        if (companyDao.getCompanyByCompanyShortName(dto.getCompanyShortName()) != null) {
            return new AjaxMessage().setCode("1").setInfo(dto.getCompanyShortName() + ":公司简称已经被占用");
        }*/
        if (StringUtil.isNullOrEmpty(dto.getCellphone())) {
            return new AjaxMessage().setCode("1").setInfo(" 负责人手机号码不能为空");
        }
        if (StringUtil.isNullOrEmpty(dto.getUserName())) {
            return new AjaxMessage().setCode("1").setInfo(" 负责人不能为空");
        }
        return new AjaxMessage().setCode("0");
    }

    @Override
    public AjaxMessage createSubCompany(SubCompanyDTO dto) throws Exception {
        //简称=全称
        dto.setCompanyShortName(dto.getCompanyName());

        AjaxMessage ajax = validateCompany(dto);
        if ("1".equals(ajax.getCode())) {//如果信息有误，直接返回
            return ajax;
        }
        //第一步，获取当前用户名
        String accountName = "";
        CompanyUserEntity companyUser = companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(), dto.getCompanyId());
        if (companyUser != null) {
            accountName = companyUser.getUserName();
        }
        String pcompanyName = "";
        CompanyEntity pcompany = companyDao.selectById(dto.getCompanyId());

        if (pcompany != null) {
            pcompanyName = pcompany.getCompanyName();
        }

        String msg = "";
        //第二步，判断手机号码是否存在，如果不存在，则创建账号
        AccountEntity account = accountDao.getAccountByCellphoneOrEmail(dto.getCellphone());

        //短信信息（默认有账号，使用模板2）
        msg = StringUtil.format(SystemParameters.CREATE_SUB_COMPANY_MSG_2, accountName, pcompanyName,
                dto.getCompanyName());
        //, dto.getClearlyAdminPassword()
        if (account == null) {
            //创建账号
            account = this.accountService.createAccount(dto.getUserName(), MD5Helper.getMD5For32("123456"), dto.getCellphone());

            //短信内容（模板1内容）
            msg = StringUtil.format(SystemParameters.CREATE_SUB_COMPANY_MSG_1, accountName, pcompanyName,
                    dto.getCompanyName(), dto.getCellphone(), "123456", serverUrl);
        } else if (!"0".equals(account.getStatus())) {//存在更新，设置为有效，
            account.setStatus("0");
            account.setActiveTime(DateUtils.date2Str(DateUtils.datetimeFormat));
            accountDao.updateById(account);
        }

        //第三步，创建团队（分支机构首先是个团队（公司））
        CompanyEntity company = new CompanyEntity();
        BaseDTO.copyFields(dto, company);
        //保存公司信息
        String id = this.saveCompany(company, account.getUserName(), account.getId(), dto.getAccountId());


        //第四步，把当前的公司和创建的分支机构，挂上分支机构的关系
        String subOrgId = this.createCompanyRelation(dto.getAccountId(), company.getId(), dto.getCompanyId(), dto.getType() + "", dto.getRoleType());
        if (dto.getType() == 2) {//邀请分公司
            SubCompanyEntity subCompany = new SubCompanyEntity();
            subCompany.setId(subOrgId);//此id设置和org的一样
            subCompany.setNickName(null);
            subCompany.setCreateBy(dto.getAccountId());
            subCompanyDao.insert(subCompany);
        } else if (dto.getType() == 3) {//邀请事业合伙人
            BusinessPartnerEntity businessPartner = new BusinessPartnerEntity();
            businessPartner.setId(subOrgId);//此id设置和org的一样
            businessPartner.setNickName(null);
            businessPartner.setCreateBy(dto.getAccountId());
            businessPartner.setCompanyId(company.getId());
            businessPartner.setType(3);
            businessPartnerDao.insert(businessPartner);
        }

        //第五步，发送短信通知负责人
        this.sendMsg(dto.getCellphone(), msg);

        //第五步，返回信息
        dto.setId(id);
        OrgTreeDTO tree = new OrgTreeDTO();
        tree.setRealId(id);
        tree.setId(id);
        tree.setCompanyId(id);
        tree.setText(dto.getCompanyShortName());
        tree.setType("subCompany");
        tree.setTreeEntity(company);

        return new AjaxMessage().setCode("0").setInfo("保存成功").setData(tree);
    }

    /**
     * 方法描述：创建公司直接的关联
     * 作者：MaoSF
     * 日期：2016/11/29
     * @param:orgType:2分支机构，3合作伙伴
     * @return:
     */
    private String createCompanyRelation(String createBy, String orgId, String orgPid, String orgType,String roleType) throws Exception {
        OrgEntity subOrg = new OrgEntity();
        String subOrgId = StringUtil.buildUUID();
        subOrg.setId(subOrgId);
        subOrg.setOrgStatus("0");
        subOrg.setOrgType(orgType);//分支机构
        subOrg.setCreateBy(createBy);
        orgDao.insert(subOrg);

        CompanyRelationEntity relationEntity = new CompanyRelationEntity();
        relationEntity.setId(subOrgId);//此id设置和org的一样
        relationEntity.setOrgPid(orgPid);
        relationEntity.setOrgId(orgId);
        relationEntity.setTypeId(roleType);
        relationEntity.setCreateBy(createBy);
        companyRelationDao.insert(relationEntity);

        //审核表中也保存一份数据
        CompanyRelationAuditEntity relationAuditEntity = new CompanyRelationAuditEntity();
        relationAuditEntity.setId(subOrgId);//此id设置和org的一样
        relationAuditEntity.setOrgPid(orgPid);
        relationAuditEntity.setOrgId(orgId);
        relationAuditEntity.setCreateBy(createBy);
        relationAuditEntity.setAuditStatus("0");//已通过状态
        relationAuditEntity.setType(orgType);
        companyRelationAuditDao.insert(relationAuditEntity);

        return subOrgId;
    }

    /**
     * 方法描述：修改分公司
     * 作        者：MaoSF
     * 日        期：2016年7月9日-上午11:15:52
     */
    @Override
    public AjaxMessage updateSubCompany(SubCompanyDTO dto) throws Exception {
        //简称=全称
        dto.setCompanyShortName(dto.getCompanyName());

        CompanyDTO companyDTO = new CompanyDTO();
        BaseDTO.copyFields(dto, companyDTO);
        AjaxMessage ajaxMessage = this.validateSaveOrUpdateCompany(companyDTO);
        if (!"0".equals(ajaxMessage.getCode())) {
            return ajaxMessage;
        }

        CompanyEntity companyEntity = companyDao.selectById(dto.getId());
        BaseDTO.copyFields(dto, companyEntity);
        companyDao.updateById(companyEntity);

        //修改群组
        imService.updateImGroup(companyEntity.getId(), companyEntity.getCompanyName(), 0);

        OrgTreeDTO tree = new OrgTreeDTO();
        tree.setRealId(dto.getId());
        tree.setId(dto.getId());
        tree.setCompanyId(dto.getId());
        tree.setText(dto.getCompanyShortName());
        tree.setTreeEntity(companyEntity);
        tree.setType("subCompany");
        return new AjaxMessage().setCode("0").setInfo("保存成功").setData(tree);
    }

    /**
     * 方法描述：删除分公司
     * 作        者：MaoSF
     * 日        期：2016年7月9日-上午11:15:52
     */
    @Override
    public AjaxMessage deleteSubCompany(String orgPid, String orgId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orgPid", orgPid);
        map.put("orgId", orgId);
        List<CompanyRelationDTO> list = companyRelationDao.getCompanyRelationByParam(map);
        CompanyRelationDTO dto = null;
        if (list != null && list.size() == 1) {
            dto = list.get(0);
            companyRelationDao.deleteById(dto.getId());
            companyRelationAuditDao.deleteById(dto.getId());
            subCompanyDao.deleteById(dto.getId());
            orgDao.deleteById(dto.getId());
            return new AjaxMessage().setCode("0").setInfo("删除成功");
        }
        return new AjaxMessage().setCode("1").setInfo("删除失败");
    }

    /**
     * 方法描述：添加分支机构校验方法
     * 作者：MaoSF
     * 日期：2016/11/29
     */
    public ResponseBean validatecreateBusinessPartner(BusinessPartnerDTO dto) throws Exception {

        if (StringUtil.isNullOrEmpty(dto.getCompanyName())) {
            return ResponseBean.responseError("团队名称不能为空");
        }

        if (StringUtil.isNullOrEmpty(dto.getUserId())) {
            if (StringUtil.isNullOrEmpty(dto.getCellphone())) {
                return  ResponseBean.responseError(" 负责人手机号码不能为空");
            }
            if (StringUtil.isNullOrEmpty(dto.getUserName())) {
                return  ResponseBean.responseError(" 负责人不能为空");
            }
            if (StringUtil.isNullOrEmpty(dto.getAdminPassword())) {
                return  ResponseBean.responseError(" 密码不能为空");
            }
        }
        return null;
    }

    /**
     * 方法描述：增加合伙人
     * 作        者：MaoSF
     * 日        期：2016年7月9日-上午11:15:52
     */
    @Override
    public ResponseBean createBusinessPartner(BusinessPartnerDTO dto) throws Exception {
        //简称=全称
        dto.setCompanyShortName(dto.getCompanyName());
        ResponseBean ajax = validatecreateBusinessPartner(dto);
        if (ajax!=null) {//如果信息有误，直接返回
            return ajax;
        }
        CompanyInviteEntity companyInviteEntity = this.companyInviteDao.selectById(dto.getInviteId());
        if (companyInviteEntity == null) {
            return ResponseBean.responseError("申请失败");
        }

        //第一步.判断手机号码是否存在，如果不存在，则创建账号
        AccountEntity account = accountDao.getAccountByCellphoneOrEmail(dto.getCellphone());

        if (account == null) {
            account = this.accountService.createAccount(dto.getUserName(), MD5Helper.getMD5For32(dto.getAdminPassword()), dto.getCellphone());
        } else if (!"0".equals(account.getStatus())) {//存在更新，设置为有效，
            account.setStatus("0");
            account.setActiveTime(DateUtils.date2Str(DateUtils.datetimeFormat));
            accountDao.updateById(account);
        }

        //第二步，创建团队（分支机构首先是个团队（公司））
        CompanyEntity company = new CompanyEntity();
        BaseDTO.copyFields(dto, company);
        String id = this.saveCompany(company, account.getUserName(), account.getId(), dto.getAccountId());

        //第三步，把当前的公司和创建的合作伙伴，挂上合作伙伴的关系
        String subOrgId = null;
        if ("1".equals(companyInviteEntity.getType())) {//邀请分公司
            subOrgId = this.createCompanyRelation(dto.getAccountId(), company.getId(), companyInviteEntity.getCompanyId(), "2",dto.getRoleType());
            SubCompanyEntity subCompany = new SubCompanyEntity();
            subCompany.setId(subOrgId);//此id设置和org的一样
            subCompany.setNickName(null);
            subCompany.setCreateBy(dto.getAccountId());
            subCompanyDao.insert(subCompany);
        } else if ("2".equals(companyInviteEntity.getType())) {//邀请事业合伙人
            subOrgId = this.createCompanyRelation(dto.getAccountId(), company.getId(), companyInviteEntity.getCompanyId(), "3",dto.getRoleType());
            BusinessPartnerEntity businessPartner = new BusinessPartnerEntity();
            businessPartner.setId(subOrgId);//此id设置和org的一样
            businessPartner.setNickName(null);
            businessPartner.setCreateBy(dto.getAccountId());
            businessPartner.setCompanyId(company.getId());
            businessPartner.setType(3);
            businessPartnerDao.insert(businessPartner);
        } else {//外部合作伙伴产生生效数据
            PartnerEntity partnerEntity = new PartnerEntity();
            partnerEntity.setCompanyId(id);
            partnerEntity.setId(companyInviteEntity.getId());
            partnerDao.updateById(partnerEntity);
        }
        //第四步，删除邀请信息
        this.companyInviteDao.deleteById(dto.getInviteId());
        return ResponseBean.responseSuccess("保存成功");
    }

    /**
     * 方法描述：删除事业合伙人
     * 作        者：MaoSF
     * 日        期：2016年7月9日-上午11:15:52
     */
    @Override
    public AjaxMessage deleteBusinessPartner(String orgPid, String orgId) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orgPid", orgPid);
        map.put("orgId", orgId);
        List<CompanyRelationDTO> list = companyRelationDao.getCompanyRelationByParam(map);
        CompanyRelationDTO dto = null;
        if (list != null && list.size() == 1) {
            dto = list.get(0);
            companyRelationDao.deleteById(dto.getId());
            companyRelationAuditDao.deleteById(dto.getId());
            businessPartnerDao.deleteById(dto.getId());
            orgDao.deleteById(dto.getId());
            return new AjaxMessage().setCode("0").setInfo("删除成功");
        }
        return new AjaxMessage().setCode("1").setInfo("删除失败");
    }

    /**
     * 方法描述：修改合伙人
     * 作        者：MaoSF
     * 日        期：2016年7月9日-上午11:15:52
     */
    @Override
    public AjaxMessage updateBusinessPartner(BusinessPartnerDTO dto) throws Exception {
        //简称=全称
        dto.setCompanyShortName(dto.getCompanyName());

        CompanyDTO companyDTO = new CompanyDTO();
        BaseDTO.copyFields(dto, companyDTO);
        AjaxMessage ajaxMessage = this.validateSaveOrUpdateCompany(companyDTO);
        if (!"0".equals(ajaxMessage.getCode())) {
            return ajaxMessage;
        }

        CompanyEntity companyEntity = companyDao.selectById(dto.getId());
        BaseDTO.copyFields(dto, companyEntity);
        companyDao.updateById(companyEntity);
        //修改群组
        imService.updateImGroup(companyEntity.getId(), companyEntity.getCompanyName(), 0);

        OrgTreeDTO tree = new OrgTreeDTO();
        tree.setRealId(dto.getId());
        tree.setId(dto.getId());
        tree.setCompanyId(dto.getId());
        tree.setText(dto.getCompanyShortName());
        tree.setTreeEntity(companyEntity);
        tree.setType("partner");
        return new AjaxMessage().setCode("0").setInfo("保存成功").setData(tree);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public CompanyDTO getCompanyById(String id) throws Exception {
        CompanyDTO dto = this.selectCompanyById(id);
        if(dto==null){
            return dto;
        }
        if (!StringUtil.isNullOrEmpty(dto.getServerType())) {
            Map map = new HashMap();
            map.put("idList", dto.getServerType().split(","));
            List<DataDictionaryEntity> list = dataDictionaryDao.getDataByParemeter(map);
            for (DataDictionaryEntity d : list) {
                map = new HashMap();
                map.put("id", d.getId());
                map.put("name", d.getName());
                dto.getServerTypeList().add(map);
            }
        }
        //公司logo
        dto.setFilePath(projectSkyDriverService.getCompanyFileByType(id, NetFileType.COMPANY_LOGO_ATTACH, true));
        //查询公司二维码
        String qrCodePath = projectSkyDriverService.getCompanyFileByType(id, NetFileType.COMPANY_QR_CODE_ATTACH, true);
        if (!StringUtil.isNullOrEmpty(qrCodePath)) {
            dto.setQrcodePath(qrCodePath);
        } else {//若无，则生成二维码
            dto.setQrcodePath(this.projectSkyDriverService.createCompanyQrcode(id));
        }
        return dto;
    }

    @Override
    public CompanyDTO selectCompanyById(String id) throws Exception {
        CompanyEntity company = this.selectById(id);
        if (company == null) {
            return null;
        }
        CompanyDTO dto = new CompanyDTO();
        BaseDTO.copyFields(company, dto);
        return dto;
    }

    @Override
    public List<CompanyDTO> getCompanyByUserId(String userId) {
        return companyDao.getCompanyByUserId(userId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public OrgTreeDTO getOrgTree(Map<String, Object> map) throws Exception {
        String companyId = (String) map.get("companyId");
        String rootId = companyId;

        //把每个节点都保存到nodeMap中
        Map<String, OrgTreeDTO> nodeMap = new HashMap<String, OrgTreeDTO>();

        //所有的公司节点
        List<CompanyEntity> companyNodes = new ArrayList<CompanyEntity>();
        List<Map<String, Object>> companyEdges = new ArrayList<Map<String, Object>>();
        String selectId = (String) map.get("selectId");

        //type不为空则查询当前公司组织树
        if (map.get("type") != null && "1".equals(map.get("type"))) {
            CompanyEntity company = companyDao.selectById(companyId);
            if (company != null) {
                companyNodes.add(company);
            }
        } else {
            if (map.get("type") != null) {
                rootId = companyId;//查询当前组织下面的所有子公司，合作伙伴
            } else {
                //先获取根节点
                rootId = getRootCompanyId(companyId);
            }
            //查询根节点
            companyNodes.add(this.selectById(rootId));
            //查询子公司
            companyNodes.addAll(this.getAllChilrenCompany(rootId));
            List<String> companyIds = new ArrayList<String>();
            for (CompanyEntity c : companyNodes) {
                companyIds.add(c.getId());
            }
            map.clear();
            map.put("companyIds", companyIds);
            companyEdges = companyDao.selectAllCompanyEdges(map);
        }

        List<String> companyIds = new ArrayList<String>();
        for (CompanyEntity c : companyNodes) {
            companyIds.add(c.getId());
            OrgTreeDTO tree = new OrgTreeDTO();
            tree.setText(c.getCompanyShortName());
            tree.setType("company");
            tree.setId(c.getId());
            tree.setCompanyId(c.getId());//当前节点是公司，companyId=id
            tree.setRealId(c.getId());
            tree.setTreeEntity(c);
            if ("2".equals(c.getCompanyType())) {//如果是分公司
                tree.setType("subCompany");
            }
            if ("3".equals(c.getCompanyType())) {//如果是分公司
                tree.setType("partner");
            }
            //如果是当前公司，则展示
            if ((c.getId() != null && c.getId().equals(companyId)) || (selectId != null && c.getId().equals(selectId))) {
                Map<String, Object> tmapMap = new HashMap<String, Object>();
                tmapMap.put("opened", true);
                tmapMap.put("selected", true);
                tree.setState(tmapMap);
            }
            nodeMap.put(c.getId(), tree);
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("companyIds", companyIds.toArray());
        List<DepartEntity> departNodes = departDao.selectDepartNodesByCompanyIds(param);
        for (DepartEntity d : departNodes) {
            OrgTreeDTO tree = new OrgTreeDTO();
            tree.setText(d.getDepartName());
            tree.setId(d.getId());
            tree.setCompanyId(d.getCompanyId());//当前节点是部门，companyId为当前节点的companyId
            tree.setRealId(d.getId());
            tree.setType("depart");
            tree.setTreeEntity(d);
            if (selectId != null && d.getId().equals(selectId)) {
                Map<String, Object> tmapMap = new HashMap<String, Object>();
                tmapMap.put("opened", true);
                tmapMap.put("selected", true);
                tree.setState(tmapMap);
            }
            nodeMap.put(d.getId(), tree);
        }

        List<Map<String, Object>> departEdges = departDao.selectDepartEdgesByCompanyIds(param);
        for (Map<String, Object> e : departEdges) {
            String from = (String) e.get("from");
            String to = (String) e.get("to");
            String too = (String) e.get("too");

            OrgTreeDTO fromNode = nodeMap.get(from);//部门id
            OrgTreeDTO toNode = nodeMap.get(to);//部门的公司
            OrgTreeDTO tooNode = nodeMap.get(too);//部门pid

            if (tooNode != null) {
                //部门上级为公司ID
                if (too.equals(to)) {
                    if (toNode != null) {
                        toNode.getChildren().add(fromNode);
                    }
                    //部门上级为部门
                } else {
                    tooNode.getChildren().add(fromNode);
                }
            }
        }

        //把分公司和合作伙伴加上
      /*  for(CompanyEntity c:companyNodes){
            companyIds.add(c.getId());
            OrgTreeDTO tree =  nodeMap.get(c.getId());
            //创建分公司，
            OrgTreeDTO subCompanyTree = new OrgTreeDTO();
            subCompanyTree.setText("分公司");
            subCompanyTree.setType("subCompanyContainer");
            subCompanyTree.setId(c.getId()+"subCompanyId");
            subCompanyTree.setRealId(c.getId()+"subCompanyId");
            tree.getChildren().add(subCompanyTree);
            nodeMap.put(c.getId()+"subCompanyId", subCompanyTree);
            //事业合伙人的节点
            OrgTreeDTO partnerTree = new OrgTreeDTO();
            partnerTree = new OrgTreeDTO();
            partnerTree.setText("事业合伙人");
            partnerTree.setType("partnerContainer");
            partnerTree.setId(c.getId()+"partnerId");
            partnerTree.setRealId(c.getId()+"partnerId");
            nodeMap.put(c.getId()+"partnerId", partnerTree);
            tree.getChildren().add(partnerTree);
        }*/

        for (Map<String, Object> e : companyEdges) {
            String from = (String) e.get("from");
            String to = (String) e.get("to");
            String type = (String) e.get("type");
            OrgTreeDTO toNode = nodeMap.get(to);
            if (toNode != null) {
                //分公司关系
                if ("2".equals(type)) {
                    if (toNode.getChildById(to + "subCompanyId") == null) {
                        OrgTreeDTO subCompanyTree = new OrgTreeDTO();
                        subCompanyTree.setText("分公司");
                        subCompanyTree.setType("subCompanyContainer");
                        subCompanyTree.setId(toNode.getId() + "subCompanyId");
                        subCompanyTree.setRealId(toNode.getId() + "subCompanyId");
                        toNode.getChildren().add(subCompanyTree);
                        nodeMap.put(toNode.getId() + "subCompanyId", subCompanyTree);
                    }
                    toNode.getChildById(to + "subCompanyId").getChildren().add(nodeMap.get(from));
                    //合作伙伴关系
                } else if ("3".equals(type)) {
                    if (toNode.getChildById(to + "partnerId") == null) {
                        //事业合伙人的节点
                        OrgTreeDTO partnerTree = new OrgTreeDTO();
                        partnerTree = new OrgTreeDTO();
                        partnerTree.setText("事业合伙人");
                        partnerTree.setType("partnerContainer");
                        partnerTree.setId(toNode.getId() + "partnerId");
                        partnerTree.setRealId(toNode.getId() + "partnerId");
                        nodeMap.put(toNode.getId() + "partnerId", partnerTree);
                        toNode.getChildren().add(partnerTree);
                    }
                    toNode.getChildById(to + "partnerId").getChildren().add(nodeMap.get(from));
                }
            }
        }

        return nodeMap.get(rootId);
    }

    @Override
    public List<CompanyDTO> getCompanyFilterbyParam(Map<String, Object> param) throws Exception {
        if (null != param.get("pageNumber")) {
            int page = (Integer) param.get("pageNumber");
            int pageSize = (Integer) param.get("pageSize");
            param.put("startPage", page * pageSize);
            param.put("endPage", pageSize);
        }
        return companyDao.getCompanyFilterbyParam(param);
    }

    @Override
    public int getCompanyFilterbyParamCount(Map<String, Object> map) throws Exception {
        return companyDao.getCompanyFilterbyParamCount(map);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Map getCompanyFilterbyParamPage(Map<String, Object> map)
            throws Exception {
        if (null != map.get("pageNumber")) {
            int page = (Integer) map.get("pageNumber");
            int pageSize = (Integer) map.get("pageSize");
            map.put("startPage", page * pageSize);
            map.put("endPage", pageSize);
        }
        Map returnMap = new HashMap();
        List<CompanyDTO> list = this.getCompanyFilterbyParam(map);
        for (CompanyDTO companyDTO : list) {
            Map<String, Object> companyAttachMap = new HashMap<>();
            companyAttachMap.put("fileType", "6");
            companyAttachMap.put("companyId", companyDTO.getId());
            List<NetFileDTO> returnList = projectSkyDriverService.getNetFileByParam(companyAttachMap);
            if (returnList.size() > 0) {
                if (!StringUtil.isNullOrEmpty(returnList.get(0).getFilePath())) {
                    companyDTO.setFilePath(fastdfsUrl + returnList.get(0).getFileGroup() + "/" + returnList.get(0).getFilePath());
                    companyDTO.setFileGroup(returnList.get(0).getFileGroup());
                }
            }
        }

        returnMap.put("data", list);
        returnMap.put("total", this.getCompanyFilterbyParamCount(map));
        return returnMap;
    }

    /**
     * 方法描述：查询从未挂靠的组织（包含被邀请没有同意的）
     * 作        者：MaoSF
     * 日        期：2016年7月11日-下午10:49:56
     */
    @Override
    public Map<String, Object> getCompanyFilterbyParamForInvit(Map<String, Object> map) throws Exception {
        if (null != map.get("pageNumber")) {
            int page = (Integer) map.get("pageNumber");
            int pageSize = (Integer) map.get("pageSize");
            map.put("startPage", page * pageSize);
            map.put("endPage", pageSize);
        }
        Map<String, Object> returnMap = new HashMap<String, Object>();
        List<CompanyDTO> list = this.companyDao.getCompanyFilterbyParamForInvit(map);
        returnMap.put("data", list);
        returnMap.put("total", this.companyDao.getCompanyFilterbyParamForInvitCount(map));
        return returnMap;
    }


    @Override
    public AjaxMessage disbandCompany(String companyId) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("cid", companyId);
        int count = companyRelationService.getCompanyRelationByParamCount(map);
        if (count > 0) {
            return new AjaxMessage().setCode("1").setInfo("你已与其他组织存在关系,不能解散");
        }

        //查询组织所有的一级部门，对一级部门群组进行删除
        Map<String, Object> params = new HashMap<>();
        params.put("companyId", companyId);
        //查询部门，如果是一级部门，则删除群组成员
        List<DepartEntity> departEntityList = departDao.selectDepartByParam(params);
        for (DepartEntity departEntity : departEntityList) {
            if ("1".equals(departEntity.getDepartLevel())) {
                imService.deleteImGroup(departEntity.getId(), 1);
            }
        }
        // TODO Auto-generated method stub
        CompanyEntity entity = new CompanyEntity();
        entity.setId(companyId);
        entity.setStatus("1");
        int i = companyDao.updateById(entity);


        //删除与之有关的组织关系
        companyRelationDao.deleteCompanyRelationByOrgId(companyId);
        companyRelationAuditDao.deleteCompanyRelationByOrgId(companyId);
        //修改员工的默认企业
        accountDao.updatedeAllfaultCompanyId(companyId);

        //删除群组
        imService.deleteImGroup(companyId, 1);
        if (i == 1) {
            return new AjaxMessage().setCode("0").setInfo("解散当前组织成功");
        }
        return new AjaxMessage().setCode("1").setInfo("解散当前组织失败");
    }

    @Override
    public List<CompanyEntity> getAllParentCompanyList(String id) throws Exception {
        List<CompanyEntity> list = new ArrayList<CompanyEntity>();

        CompanyEntity companyEntity = companyDao.getParentCompany(id);
        if (companyEntity == null) {
            return list;
        } else {
            list.add(companyEntity);
            getAllParentCompanyList(companyEntity.getId());
        }

        return list;
    }

    @Override
    public List<CompanyEntity> getAllChilrenCompany(String id) {
        List<CompanyEntity> list = new ArrayList<CompanyEntity>();
        getChildrenCompany2(id, list);
        return list;
    }

    @Override
    public List<CompanyEntity> getAllOrg(String id) throws Exception {
        String rootId = this.getRootCompanyId(id);
        List<CompanyEntity> list = this.getAllChilrenCompany(rootId);
        list.add(0, this.companyDao.selectById(rootId));
        return list;
    }

    @Override
    public List<CompanyEntity> getOwnAndChildOrg(String id) throws Exception {
        List<CompanyEntity> list = this.getAllChilrenCompany(id);
        list.add(0, this.companyDao.selectById(id));
        return list;
    }


    /**
     * 递归获取子公司
     */
    public void getChildrenCompany2(String id, List<CompanyEntity> list) {
        List<CompanyEntity> cList = companyDao.getChilrenCompany(id);
        if (cList != null && cList.size() > 0) {
            list.addAll(cList);
            for (int i = 0; i < cList.size(); i++) {
                getChildrenCompany2(cList.get(i).getId(), list);
            }
        }
    }

    /**
     * 递归获取子公司
     */
    public void getChilrenCompany2Ws(String id, List<CompanyEntity> list) {
        List<CompanyEntity> cList = companyDao.getChilrenCompany(id);
        if (cList != null && cList.size() > 0) {
            list.addAll(cList);
        }
    }

    /**
     * 获取某节点公司的根节点id
     */
    public String getRootCompanyId(String id){
        CompanyEntity companyEntity = companyDao.getParentCompany(id);
        if (companyEntity == null) {
            return id;
        } else {
            return getRootCompanyId(companyEntity.getId());
        }
    }

    /**
     * 方法描述：查询线上公司
     * 作者：MaoSF
     * 日期：2016/11/11
     * @param:
     * @return:
     */
    public CompanyDTO getLineCompanyByCompanyId(String id) throws Exception {
        CompanyEntity companyEntity = companyDao.getParentCompany(id);
        if (null != companyEntity) {
            CompanyDTO companyDTO = new CompanyDTO();
            BaseDTO.copyFields(companyEntity, companyDTO);
            return companyDTO;
        }
        return null;
    }

    /**
     * 方法描述：发送短信【此方法不是接口】
     * 作        者：MaoSF
     * 日        期：2016年7月11日-下午8:05:44
     */
    public void sendMsg(String cellphone, String msg) {
        Sms sms = new Sms();
        sms.addMobile(cellphone);
        sms.setMsg(msg);
        smsSender.send(sms);
    }

    /**
     * 方法描述：返回选择团队列表（分公司，合作伙伴，所有公司），任务转发给其他公司，团队选择
     * 作者：MaoSF
     * 日期：2017/1/5
     */
    @Override
    public Map<String, Object> getCompanyForSelect(String id, String projectId) throws Exception {
        List<CompanyEntity> list = companyDao.getChilrenCompany(id);
        List<CompanyDataDTO> subCompanyList = new ArrayList<CompanyDataDTO>();//分公司
        List<CompanyDataDTO> partnerCompanyList = new ArrayList<CompanyDataDTO>();//合作伙伴
        if (!CollectionUtils.isEmpty(list)) {
            for (CompanyEntity entity : list) {
                CompanyDataDTO dataDTO = new CompanyDataDTO();
                BaseDTO.copyFields(entity, dataDTO);
                if ("2".equals(entity.getCompanyType())) {//分公司
                    subCompanyList.add(dataDTO);
                }
                if ("3".equals(entity.getCompanyType())) {//合作伙伴
                    partnerCompanyList.add(dataDTO);
                }
            }
        }

        List<CompanyDataDTO> allCompanyList = new ArrayList<CompanyDataDTO>();
        //查询自己及父公司以及rootId的公司
        CompanyEntity companyEntity = this.companyDao.selectById(id);
        CompanyDataDTO ownCompany = new CompanyDataDTO();
        BaseDTO.copyFields(companyEntity, ownCompany);
        allCompanyList.add(ownCompany);

        //查询父公司
        CompanyEntity parentCompany = this.companyDao.getParentCompany(id);
        if (parentCompany != null) {
            CompanyDataDTO parentDataDTO = new CompanyDataDTO();
            BaseDTO.copyFields(parentCompany, parentDataDTO);
            allCompanyList.add(parentDataDTO);

            String rootId = this.getRootCompanyId(id);
            if (!id.equals(rootId) && !parentCompany.getId().equals(rootId)) {
                CompanyEntity rootCompany = this.companyDao.selectById(rootId);
                CompanyDataDTO rootDTO = new CompanyDataDTO();
                BaseDTO.copyFields(rootCompany, rootDTO);
                partnerCompanyList.add(0, rootDTO);//乙方放在事业合伙人中
            }

            //查询父级的子节点
            list = companyDao.getChilrenCompany(parentCompany.getId());
            List<CompanyDataDTO> allCompany = BaseDTO.copyFields(list, CompanyDataDTO.class);
            if (!CollectionUtils.isEmpty(allCompany)) {
                for (CompanyDataDTO dataDTO : allCompany) {
                    if (dataDTO.getId().equals(id)) {
                        allCompany.remove(dataDTO);
                        break;
                    }
                }
            }
            partnerCompanyList.addAll(allCompany);
        }

        //查询外部合作合伙
        List<CompanyDataDTO> outerCooperatorCompanyList = new ArrayList<>();
        if (!StringUtil.isNullOrEmpty(projectId)) {
            List<CompanyDataDTO> outerCooperatorCompanyList2 = BaseDTO.copyFields(this.companyDao.getOuterCooperatorCompany(id, projectId), CompanyDataDTO.class);
            if (CollectionUtils.isEmpty(outerCooperatorCompanyList2)) {//去重，如果是外部合作关系，又是事业合伙人，则取事业和人数据，此情况几乎不会存在
                List<CompanyDataDTO> allList = new ArrayList<>();
                allList.addAll(allCompanyList);
                allList.addAll(subCompanyList);
                allList.addAll(partnerCompanyList);
                for (CompanyDataDTO company : outerCooperatorCompanyList2) {
                    for (CompanyDataDTO dataDTO : allList) {
                        if (dataDTO.getId().equals(company.getId())) {
                            continue;
                        }
                    }
                    outerCooperatorCompanyList.add(company);
                }
            }
        }
        //获取所有公司
        //   List<CompanyDataDTO> allCompanyList = BaseDTO.copyFields(this.getAllCompany(),CompanyDataDTO.class);
        //返回数据
        Map<String, Object> returnData = new HashMap<String, Object>();
        returnData.put("allCompanyList", allCompanyList);
        returnData.put("subCompanyList", subCompanyList);
        returnData.put("partnerCompanyList", partnerCompanyList);
        returnData.put("outerCooperatorCompanyList", outerCooperatorCompanyList);
        return returnData;
    }

    /**
     * 方法描述：返回选择团队列表（分支机构，合作伙伴，所有公司），任务转发给其他公司，团队选择
     * 作者：MaoSF
     * 日期：2017/1/5
     */
    public List<CompanyDataDTO> getIssueCompanyForSelect(String id, String projectId) throws Exception {
        List<CompanyEntity> list = companyDao.getChilrenCompany(id);
        List<CompanyDataDTO> allCompanyList = BaseDTO.copyFields(list, CompanyDataDTO.class);
        if (!CollectionUtils.isEmpty(allCompanyList)) {
            for (CompanyDataDTO dataDTO : allCompanyList) {
                if (dataDTO.getId().equals(id)) {
                    allCompanyList.remove(dataDTO);
                    break;
                }
            }
        }

        //查询父公司
        CompanyEntity parentCompany = this.companyDao.getParentCompany(id);
        if (parentCompany != null) {
            CompanyDataDTO parentDataDTO = new CompanyDataDTO();
            BaseDTO.copyFields(parentCompany, parentDataDTO);
            allCompanyList.add(0, parentDataDTO);

            String rootId = this.getRootCompanyId(id);
            if (!id.equals(rootId) && !parentCompany.getId().equals(rootId)) {
                CompanyEntity rootCompany = this.companyDao.selectById(rootId);
                CompanyDataDTO rootDTO = new CompanyDataDTO();
                BaseDTO.copyFields(rootCompany, rootDTO);
                allCompanyList.add(1, rootDTO);
            }

            //查询父级的子节点
            list = companyDao.getChilrenCompany(parentCompany.getId());
            List<CompanyDataDTO> allCompany = BaseDTO.copyFields(list, CompanyDataDTO.class);
            if (!CollectionUtils.isEmpty(allCompany)) {
                for (CompanyDataDTO dataDTO : allCompany) {
                    if (dataDTO.getId().equals(id)) {
                        allCompany.remove(dataDTO);
                        break;
                    }
                }
            }
            allCompanyList.addAll(allCompany);
        }

        //查询自己及父公司以及rootId的公司
        CompanyEntity companyEntity = this.companyDao.selectById(id);
        CompanyDataDTO ownCompany = new CompanyDataDTO();
        BaseDTO.copyFields(companyEntity, ownCompany);
        allCompanyList.add(0, ownCompany);

        //查询外部合作合伙
        List<CompanyEntity> outerCooperatorCompanyList = this.companyDao.getOuterCooperatorCompany(id, projectId);
        if (!CollectionUtils.isEmpty(outerCooperatorCompanyList)) {
            for (CompanyEntity company : outerCooperatorCompanyList) {
                CompanyDataDTO outerCompany = new CompanyDataDTO();
                BaseDTO.copyFields(company, outerCompany);
                outerCompany.setIsOuterCooperator(1);
                allCompanyList.add(outerCompany);
            }
        }

        //返回数据
        Map<String, Object> returnData = new HashMap<String, Object>();
        returnData.put("allCompanyList", allCompanyList);
        return allCompanyList;
    }

    /**
     * 方法描述：组织架构数据
     * 作者：MaoSF
     * 日期：2017/1/13
     */
    @Override
    public Map<String, Object> getLinkPeopleAndGroup(Map<String, Object> mapass) throws Exception {

        Map<String, Object> returnMap = new HashMap<>();//返回对象
        //第一，获取公司根目录下的人员
        if (StringUtil.isNullOrEmpty(mapass.get("companyId"))) {
            return mapass;//返回
        }
        String companyId = (String) mapass.get("companyId");
        //挂靠公司
        String rootId = this.getRootCompanyId(companyId);
        if (!StringUtil.isNullOrEmpty(rootId) && !rootId.equals(companyId)) {
            CompanyEntity companyEntity = companyDao.getCompanyMsgById(rootId);
            List<Object> headOfficeCompanyList = new ArrayList<>();
            if (companyEntity != null) {
                CompanyDataDTO dataDTO = new CompanyDataDTO();
                BaseDTO.copyFields(companyEntity, dataDTO);
                dataDTO.setCompanyShortName(companyEntity.getAliasName());
                dataDTO.setCompanyShortName(companyEntity.getAliasName());
                if (!StringUtil.isNullOrEmpty(companyEntity.getFilePath())) {
                    dataDTO.setFilePath(this.fastdfsUrl + companyEntity.getFilePath());
                }
                if(companyUserDao.getCompanyUserByUserIdAndCompanyId((String)mapass.get("accountId"),companyEntity.getId())!=null){
                    dataDTO.setIsOwnCompany(1);
                }
                headOfficeCompanyList.add(dataDTO);
            }
            returnMap.put("headOfficeCompanyList", headOfficeCompanyList);//总公司
        }

        Map<String, Object> paramCompanyUser = new HashMap<String, Object>();
        paramCompanyUser.put("orgId", companyId);
        paramCompanyUser.put("auditStatus", "1");
        paramCompanyUser.put("fastdfsUrl", fastdfsUrl);//公司人员
        List<CompanyUserDataDTO> rootCompanyUserList = this.companyUserDao.getUserByOrgId(paramCompanyUser);
        returnMap.put("rootCompanyUserList", rootCompanyUserList);

        //获取父节点为公司的部门
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("departLevel", "1");
        List<DepartEntity> departList = this.departDao.getDepartByCompanyIdWS(map);
        returnMap.put("departList", departList);

        //获取分公司及事业合伙人
        List<CompanyEntity> list = companyDao.getChilrenCompany(companyId);
        List<CompanyDataDTO> companyFilialeList = new ArrayList<CompanyDataDTO>();//分公司
        List<CompanyDataDTO> businessPartnerList = new ArrayList<CompanyDataDTO>();//合作伙伴
        if (!CollectionUtils.isEmpty(list)) {
            for (CompanyEntity entity : list) {
                CompanyDataDTO dataDTO = new CompanyDataDTO();
                BaseDTO.copyFields(entity, dataDTO);
                dataDTO.setCompanyShortName(entity.getAliasName());
                //查询是否存在该公司
                if(companyUserDao.getCompanyUserByUserIdAndCompanyId((String)mapass.get("accountId"),entity.getId())!=null){
                    dataDTO.setIsOwnCompany(1);
                }
                if (!StringUtil.isNullOrEmpty(dataDTO.getFilePath())) {
                    dataDTO.setFilePath(this.fastdfsUrl + dataDTO.getFilePath());
                }
                if ("2".equals(entity.getCompanyType())) {//分公司
                    companyFilialeList.add(dataDTO);
                }
                if ("3".equals(entity.getCompanyType())) {//合作伙伴
                    businessPartnerList.add(dataDTO);
                }
            }
        }
        returnMap.put("companyFilialeList", companyFilialeList);//分公司
        returnMap.put("businessPartnerList", businessPartnerList);

        //查询我所在一级部门的id
        if (null != mapass.get("accountId")) {
            map.clear();
            map.put("companyId", companyId);
            map.put("userId", mapass.get("accountId"));
            returnMap.put("firstLeveDepartIds", this.departDao.getOwnDepartGroupId(map));
        }

        return returnMap;
    }


    /**
     * 查询当前组织下的成员及部门 （组织为companyId or departId）
     */


    @Override
    public Map<String, Object> getInviteAbout(Map<String, Object> map) throws Exception {

        Map<String, Object> resultMap = new HashMap<String, Object>();

        String companyId = (String) map.get("companyId");
        CompanyEntity companyEntity = companyDao.selectById(map.get("companyId"));
        resultMap.put("companyName", companyEntity.getCompanyName());

        //查询公司logo
        resultMap.put("filePath", this.projectSkyDriverService.getCompanyLogo(companyId));
        //查询企业管理员
        List<TeamOperaterEntity> teamOperaterList = this.teamOperaterDao.selectTeamOperaterByCompanyId(companyId);
        if (!CollectionUtils.isEmpty(teamOperaterList)) {
            TeamOperaterEntity operater = teamOperaterList.get(0);
            CompanyUserEntity companyUserEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(operater.getUserId(), operater.getCompanyId());
            resultMap.put("systemManager", companyUserEntity.getUserName());
        }
        //获取邀请码
        // resultMap.put("inviteCode",this.getInviteCode(map));
        return resultMap;
    }


    /**
     * 方法描述：事业合伙人申请（确认身份后，请求数据）
     * 作者：MaoSF
     * 日期：2017/4/1
     * @param:
     * @return:
     */
    @Override
    public Map<String, Object> getCompanyPrincipal(Map<String, Object> map) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String cellphone = (String) map.get("cellphone");
        if (StringUtil.isNullOrEmpty(cellphone)) {
            resultMap.put("companyList", new ArrayList<>());
            return resultMap;
        }
        AccountEntity accountEntity = this.accountDao.getAccountByCellphoneOrEmail(cellphone);
        if (accountEntity == null) {
            resultMap.put("companyList", new ArrayList<>());
            return resultMap;
        }
        CompanyInviteEntity companyInviteEntity = this.companyInviteDao.selectById(map.get("id"));
        //查询企业管理员
        map.put("userId", accountEntity.getId());
        map.put("fastdfsUrl", this.fastdfsUrl);
        map.put("permissionId", "50");//企业负责人具有权限的权限id
        List<CompanyDataDTO> companyList = this.companyDao.getHasSystemManagerCompany(map);
        List<CompanyDataDTO> issueCompanyList = null;
        if ("3".equals(companyInviteEntity.getType())) {//如果是项目外部团队邀请
            //获取可以签发的团队信息
            issueCompanyList = this.getIssueCompanyForSelect(companyInviteEntity.getCompanyId(), companyInviteEntity.getProjectId());
        }
        for (CompanyDataDTO dto : companyList) {
            if ("3".equals(companyInviteEntity.getType())) {//如果是项目外部团队邀请
                setMemoForGetCompanyPrincipal3(dto, issueCompanyList);
            } else {
                //查询是否已经存在父公司
                map.clear();
                map.put("orgId", dto.getId());
                //理论上存在0 or 1条信息
                List<CompanyRelationDTO> relationList = this.companyRelationDao.getCompanyRelationByParam(map);
                if (!CollectionUtils.isEmpty(relationList)) {
                    if ("2".equals(relationList.get(0).getCompanyType())) {
                        dto.setMemo("你已经是" + relationList.get(0).getpCompanyName() + "的分公司");
                    } else {
                        dto.setMemo("你已经是" + relationList.get(0).getpCompanyName() + "的事业合伙人");
                    }
                    dto.setFlag(1);
                }
            }
        }
        resultMap.put("companyList", companyList);
        resultMap.put("userId", accountEntity.getId());
        return resultMap;
    }

    /**
     * 方法描述：外部团队邀请，设置memo，前端不可邀请的提示语
     * 作者：MaoSF
     * 日期：2017/5/8
     * @param:
     * @return:
     */
    private void setMemoForGetCompanyPrincipal3(CompanyDataDTO dto, List<CompanyDataDTO> issueCompanyList) {
        if (!CollectionUtils.isEmpty(issueCompanyList)) {
            for (CompanyDataDTO dataDTO : issueCompanyList) {
                if (dto.getId().equals(dataDTO.getId())) {
                    if (dataDTO.getIsOuterCooperator() == 1) {
                        dto.setMemo(dataDTO.getCompanyName() + "已经该项目的外部合作组织");
                    } else {
                        dto.setMemo(dataDTO.getCompanyName() + "已经在本组织体系中");
                    }
                    dto.setFlag(1);
                    break;
                }
            }
        }
    }

    /**
     * 方法描述：验证code是否生效
     * 作        者：chenzhujie
     * 日        期：2017/2/27
     */
    @Override
    public ResponseBean applayBusinessCompany(Map<String, Object> map) throws Exception {
        String appOrgId = (String) map.get("appOrgId");
        //code ,companyId
        CompanyInviteEntity companyInviteEntity = companyInviteDao.selectByParam(map);
        if (companyInviteEntity != null && DateUtils.getMillis(companyInviteEntity.getEffectiveTime()) >= System.currentTimeMillis()) {
            //给对方管理员发送一条任务
            map.clear();
            map.put("companyId", companyInviteEntity.getCompanyId());
            List<TeamOperaterEntity> teamOperaterList = this.teamOperaterDao.selectByParam(map);
            if (!CollectionUtils.isEmpty(teamOperaterList)) {
                CompanyUserEntity companyUserEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(teamOperaterList.get(0).getUserId(), teamOperaterList.get(0).getCompanyId());
                if (companyUserEntity != null) {
                    AjaxMessage ajaxMessage = companyRelationAuditService.applicationOrInvitation(appOrgId, companyInviteEntity.getCompanyId(), (String) map.get("accountId"));
                    if ("0".equals(ajaxMessage.getCode())) {
                        // this.myTaskService.saveMyTask((String) ajaxMessage.getData(), SystemParameters.COOPERATIVE_AUDIT, companyInviteEntity.getCompanyId(), companyUserEntity.getId());
                        return ResponseBean.responseSuccess("发送申请成功");
                    }
                    return ResponseBean.responseError(ajaxMessage.getInfo().toString());
                }
            }
        }
        return ResponseBean.responseError("邀请码已失效，请向邀请方重新获取。");
    }

    public ResponseBean validateInviteParent(InvitatParentDTO dto) throws Exception {
        for(String cellphone:dto.getCellphoneList()){
            if(!StringUtil.isMobileNO(cellphone)){
                return ResponseBean.responseError("手机号："+cellphone+"格式不对");
            }
        }
        return null;
    }
    /**
     * 方法描述：邀请事业合伙人
     * 作者：MaoSF
     * 日期：2017/4/1
     */
    @Override
    public ResponseBean inviteParent(InvitatParentDTO dto) throws Exception {
        //手机号验证
        ResponseBean responseBean = validateInviteParent(dto);
        if(responseBean!=null){
            return responseBean;
        }
        CompanyUserEntity companyUser = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(), dto.getAppOrgId());
        CompanyEntity companyEntity = this.companyDao.selectById(dto.getAppOrgId());
        if (companyUser == null || companyEntity == null) {
            return ResponseBean.responseError("操作失败");
        }
        if ("3".equals(dto.getType())) {//外部合作组织
            if (StringUtil.isNullOrEmpty(dto.getProjectId())) {//验证数据,项目ID必传
                return ResponseBean.responseError("操作失败");
            }
        }
        CompanyInviteEntity companyInviteEntity = new CompanyInviteEntity();
        companyInviteEntity.setCompanyId(dto.getAppOrgId());
        companyInviteEntity.setCreateBy(dto.getAccountId());
        companyInviteEntity.setType(dto.getType());
        companyInviteEntity.setProjectId(dto.getProjectId());
        for (String cellphone : dto.getCellphoneList()) {
            companyInviteEntity.setInviteCellphone(cellphone);
            companyInviteEntity.setId(StringUtil.buildUUID());
            String url = this.serverUrl + "/na/bPartner/invited/" + companyInviteEntity.getId();//点击的url地址
            companyInviteEntity.setUrl(url);
            this.companyInviteDao.insert(companyInviteEntity);
            //发送信息
            if ("1".equals(dto.getType())) {
                this.sendMsg(cellphone, StringUtil.format(SystemParameters.INVITE_PARENT_MSG, companyUser.getUserName(), companyEntity.getCompanyName(), url));
            }
            if ("2".equals(dto.getType())) {
                this.sendMsg(cellphone, StringUtil.format(SystemParameters.INVITE_PARENT_MSG2, companyUser.getUserName(), companyEntity.getCompanyName(), url));
            }
            if ("3".equals(dto.getType())) {

                //插入外部合作组织记录
                PartnerEntity partnerEntity = new PartnerEntity();
                partnerEntity.setFromCompanyId(dto.getCurrentCompanyId());
                partnerEntity.setFromUserId(dto.getAccountId());
                partnerEntity.setCreateBy(dto.getAccountId());
                partnerEntity.setCreateDate(new Date());
                partnerEntity.setType(Integer.valueOf(dto.getType()));//合伙人类别=3
                partnerEntity.setNickName(companyUser.getUserName());
                partnerEntity.setProjectId(dto.getProjectId());
                partnerEntity.setPhone(cellphone);
                partnerEntity.setId(companyInviteEntity.getId());//此ID与companyInviteEntity的ID同步
                partnerDao.insert(partnerEntity);
                //发送信息
                this.sendMsg(cellphone, StringUtil.format(SystemParameters.INVITE_PARENT_MSG3, companyUser.getUserName(), companyEntity.getCompanyName(), url));
            }
        }

        return ResponseBean.responseSuccess("邀请发送成功");
    }

    /**
     * 方法描述：设置事业合伙人的别名
     * 作者：MaoSF
     * 日期：2017/4/18
     */
    @Override
    public ResponseBean setBusinessPartnerNickName(BusinessPartnerDTO dto) throws Exception {
        CompanyRelationDTO dto1 = this.companyRelationDao.getCompanyRelationByOrgId(dto.getCompanyId());
        if (dto1 != null) {
            if ("3".equals(dto1.getCompanyType())) {
                BusinessPartnerEntity businessPartner = new BusinessPartnerEntity();
                businessPartner.setId(dto1.getId());
                businessPartner.setNickName(dto.getCompanyName());
                this.businessPartnerDao.updateById(businessPartner);
            }
            if ("2".equals(dto1.getCompanyType())) {
                SubCompanyEntity subCompany = new SubCompanyEntity();
                subCompany.setId(dto1.getId());
                subCompany.setNickName(dto.getCompanyName());
                this.subCompanyDao.updateById(subCompany);
            }
            return ResponseBean.responseSuccess("操作成功");
        }
        return ResponseBean.responseError("操作失败");
    }

    /**
     * 方法描述：项目讨论区的@和特别提醒人员选择
     * 作者：zcl
     * 日期：2018/4/23
     * @param: companyId 组织id
     * @param: accountId 查询者用户id
     * @param: projectId 要查询的项目id
     * @param: ignoreUserId 在结果中忽略掉的用户Id
     * @return: 查询到的用户列表和公司信息
     */
    @Override
    public List<CompanyUserGroupDTO> getCompanyForCustomGroupSelect(QueryCompanyUserDTO query) throws Exception {
        String projectId = query.getProjectId();
        //重新组装数据并查询人员
        List<CompanyUserGroupDTO> allCompanyUserList = new ArrayList<>();
        //如果projectId不为null，则是创建项目群返回数据
        if(!StringUtil.isNullOrEmpty(projectId)){
            CompanyUserGroupDTO projectMember = new CompanyUserGroupDTO();
            projectMember.setCompanyName("项目参与人员");
            projectMember.setUserList(projectMemberService.listProjectMember(projectId));
            allCompanyUserList.add(0,projectMember);
        }
        return allCompanyUserList;
    }

    @Override
    public List<CompanyUserGroupDTO> getLinkman(QueryCompanyUserDTO query) throws Exception {
        List<CompanyUserGroupDTO> allCompanyUserList = new ArrayList<>();
        List<CompanyEntity> list = this.getAllOrg(query.getAppOrgId());
        for (CompanyEntity mCompanyEntity : list) {
            query.setCompanyId(mCompanyEntity.getId());
            allCompanyUserList.add(getOrgUser(query,mCompanyEntity));
        }
        return allCompanyUserList;
    }

    @Override
    public List<CompanyUserGroupDTO> getOrgForSchedule(QueryCompanyUserDTO query) throws Exception {
        //查询项目参与人员
        List<CompanyUserGroupDTO> result =this.getCompanyForCustomGroupSelect(query);
        //再查询组织架构
        List<CompanyEntity> list = this.getAllOrg(query.getAppOrgId());
        list.stream().forEach(c->{
            CompanyUserGroupDTO dto = new CompanyUserGroupDTO();
            dto.setCompanyId(c.getId());
            dto.setCompanyName(c.getCompanyName());
            result.add(dto);
        });

        return result;
    }

    @Override
    public List<CompanyUserGroupDTO> getCostAuditMember(QueryCompanyUserDTO query) throws Exception {
        query.setIgnoreUserId(expAuditService.getAuditPerson(query.getExpMainId(),query.getAccountId()));
        return getLinkman(query);
    }

    @Override
    public List<CompanyUserGroupDTO> getCostCopyMember(QueryCompanyUserDTO query) throws Exception {
        return getLinkman(query);
    }

    @Override
    public List<CompanyUserAppDTO>  getLeaveAuditMember(QueryCompanyUserDTO query) throws Exception {
        List<CompanyUserAppDTO> list = new ArrayList<>();
        CompanyEntity company = this.companyDao.selectById(query.getAppOrgId());
        if(company!=null){
            query.setIgnoreUserId(expAuditService.getAuditPerson(query.getExpMainId(),query.getAccountId()));
            return getOrgUser(query,company).getUserList();
        }
        return list;
    }

    @Override
    public List<CompanyUserAppDTO> getLeaveCopyMember(QueryCompanyUserDTO query) throws Exception {
        List<CompanyUserAppDTO> list = new ArrayList<>();
        CompanyEntity company = this.companyDao.selectById(query.getAppOrgId());
        if(company!=null){
            return getOrgUser(query,company).getUserList();
        }
        return list;
    }

    private CompanyUserGroupDTO getOrgUser(QueryCompanyUserDTO query,CompanyEntity mCompanyEntity) throws Exception{
        CompanyUserGroupDTO orgUser = new CompanyUserGroupDTO();
        orgUser.setCompanyId(mCompanyEntity.getId());
        orgUser.setCompanyName(mCompanyEntity.getCompanyName());
        query.setCompanyId(mCompanyEntity.getId());
        orgUser.setUserList(this.companyUserService.getCompanyUserExceptMe(query));
        return orgUser;
    }

    /**
     * 方法描述：获取与当前组织相关的组织人员（本公司、分公司、事业合伙人）并按最后操作时间倒序排序
     * 作者：zcl
     * 日期：2018/4/23
     *
     * @param query 查询条件
     * @return: 查询到的companyUser列表
     */
    @Override
    public List<CompanyUserAppDTO> listCompanyUserForCustomGroupSelect(QueryCompanyUserDTO query) throws Exception {
        //重新组装数据并查询人员
        List<CompanyUserAppDTO> userList = new ArrayList<>();
        CompanyUserEntity user = companyUserDao.getCompanyUserByUserIdAndCompanyId(query.getAccountId(),query.getAppOrgId());
        if(user==null){
            return userList;
        }
        query.setCompanyUserId(user.getId());
        query.setIgnoreUserId(expAuditService.getAuditPerson(query.getExpMainId(),query.getAccountId()));
        return companyUserService.listCompanyUser(query);
    }

    @Override
    public String getOrgTypeId(String companyId) {
        if(StringUtil.isNullOrEmpty(companyId)){
            return null;
        }
        CompanyRelationDTO relation = this.companyRelationDao.getCompanyRelationByOrgId(companyId);
        if(relation!=null){
            return relation.getTypeId();
        }
        return null;
    }

    @Override
    public String getFinancialHandleCompanyId(String companyId) {
        CompanyRelationDTO relation = this.companyRelationDao.getCompanyRelationByOrgId(companyId);
        if(relation==null || StringUtil.isNullOrEmpty(relation.getTypeId()) || !"3".equals(relation.getTypeId())){
            return companyId;
        }else {
            return getFinancialHandleCompanyId(relation.getOrgPid());
        }
    }

    @Override
    public List<Map<String, String>> getUsedPartB(String companyId) {
        String parentId = this.getRootCompanyId(companyId);
        List<CompanyEntity> companyEntityList = new ArrayList<>();

        CompanyEntity companyEntity1 = this.selectById(companyId);
        if (!StringUtil.isNullOrEmpty(parentId) && !parentId.equals(companyId)) {
            CompanyEntity companyEntity = this.selectById(parentId);
            companyEntityList.add(companyEntity);
        }
        companyEntityList.add(companyEntity1);
        List<Map<String, String>> companyList = new ArrayList<>();
        for (CompanyEntity entity : companyEntityList) {
            Map<String, String> company = new HashMap<String, String>();
            company.put("id", entity.getId());
            company.put("companyName", entity.getCompanyName());
            companyList.add(company);
        }
        return companyList;
    }

}
