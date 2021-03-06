package com.maoding.projectcost.service.impl;

import com.maoding.commonModule.entity.RelationRecordEntity;
import com.maoding.commonModule.service.RelationRecordService;
import com.maoding.companybill.dto.SaveCompanyBillDTO;
import com.maoding.companybill.service.CompanyBalanceService;
import com.maoding.companybill.service.CompanyBillService;
import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.constant.CompanyBillType;
import com.maoding.core.constant.ProcessTypeConst;
import com.maoding.core.constant.ProjectCostConst;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.*;
import com.maoding.dynamic.service.DynamicService;
import com.maoding.enterprise.service.EnterpriseService;
import com.maoding.exception.CustomException;
import com.maoding.financial.dao.ExpMainDao;
import com.maoding.financial.dto.ApplyProjectCostDTO;
import com.maoding.financial.dto.ExpMainDTO;
import com.maoding.financial.entity.ExpMainEntity;
import com.maoding.financial.service.ExpMainService;
import com.maoding.invoice.dto.InvoiceEditDTO;
import com.maoding.invoice.dto.InvoiceInfoDTO;
import com.maoding.invoice.service.InvoiceService;
import com.maoding.message.entity.MessageEntity;
import com.maoding.message.service.MessageService;
import com.maoding.mytask.entity.MyTaskEntity;
import com.maoding.mytask.service.MyTaskService;
import com.maoding.org.dao.CompanyDao;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.dto.CompanyRelationDTO;
import com.maoding.org.dto.CompanyUserDataDTO;
import com.maoding.org.dto.CompanyUserTableDTO;
import com.maoding.org.entity.CompanyEntity;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.org.service.CompanyUserService;
import com.maoding.process.dto.ActivitiDTO;
import com.maoding.process.service.ProcessService;
import com.maoding.project.dao.ProjectDao;
import com.maoding.project.entity.ProjectEntity;
import com.maoding.project.service.ProjectService;
import com.maoding.project.service.ProjectSkyDriverService;
import com.maoding.projectcost.dao.*;
import com.maoding.projectcost.dto.*;
import com.maoding.projectcost.entity.*;
import com.maoding.projectcost.service.ProjectCostService;
import com.maoding.projectmember.entity.ProjectMemberEntity;
import com.maoding.projectmember.service.ProjectMemberService;
import com.maoding.role.service.PermissionService;
import com.maoding.task.dao.ProjectTaskRelationDao;
import com.maoding.v2.project.dto.V2ProjectTableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.maoding.core.util.MapUtil.objectMap;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectCostService
 * 类描述：费用service
 * 作    者：MaoSF
 * 日    期：2016年7月19日-下午5:28:54
 */
@Service("projectCostService")
public class ProjectCostServiceImpl extends GenericService<ProjectCostEntity> implements ProjectCostService {

    @Autowired
    private ProjectCostDao projectCostDao;

    @Autowired
    private ProjectCostPaymentDetailDao projectCostPaymentDetailDao;

    @Autowired
    private ProjectCostOperaterDao projectCostOperaterDao;

    @Autowired
    private ProjectCostPointDao projectCostPointDao;

    @Autowired
    private ProjectCostPointDetailDao projectCostPointDetailDao;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private ProjectMemberService projectMemberService;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ProjectTaskRelationDao projectTaskRelationDao;

    @Autowired
    private MyTaskService myTaskService;

    @Autowired
    private DynamicService dynamicService;

    @Autowired
    private CompanyUserService companyUserService;

    @Autowired
    private CompanyBillService companyBillService;

    @Autowired
    private CompanyBalanceService companyBalanceService;

    @Autowired
    private ProjectSkyDriverService projectSkyDriverService;

    @Autowired
    private EnterpriseService enterpriseService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ExpMainService expMainService;

    @Autowired
    private ProcessService processService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RelationRecordService relationRecordService;

    @Autowired
    private ExpMainDao expMainDao;

    /**
     * 方法描述：设置合同总金额/技术审查费
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    @Override
    public ResponseBean saveOrUpdateProjectCost(ProjectCostEditDTO projectCostDto) throws Exception {
        ProjectCostEntity entity = new ProjectCostEntity();
        BaseDTO.copyFields(projectCostDto, entity);
        //类型1:合同总金额，2：技术审查费,3合作设计费付款 (字符串)，
        entity.setOperateCompanyId(projectCostDto.getAppOrgId());//保存当前操作组织
        //新增
        if (StringUtil.isNullOrEmpty(projectCostDto.getId())) {
            this.setCompany(projectCostDto,entity);//设置组织
            if(StringUtil.isNullOrEmpty(projectCostDto.getFlag())){
                entity.setFlag("1");
            }
            entity.setId(StringUtil.buildUUID());
            entity.setCreateBy(projectCostDto.getAccountId());
            projectCostDao.insert(entity);
            //添加项目动态
            dynamicService.addDynamic(null,entity,projectCostDto.getCurrentCompanyId(),projectCostDto.getAccountId());
        } else {
            updateCostFee(projectCostDto);
        }
        //处理附件
        projectCostDto.setId(entity.getId());
        projectSkyDriverService.saveProjectFeeContractAttach(projectCostDto);
        return ResponseBean.responseSuccess("操作成功").addData("projectCostDTO", projectCostDto);
    }

    private void setCompany(ProjectCostEditDTO projectCostDto,ProjectCostEntity entity){
        if(projectCostDto.getPayType()==CompanyBillType.DIRECTION_PAYEE) {//如果是收款方
            if(StringUtil.isNullOrEmpty(projectCostDto.getFromCompanyId())){
                entity.setFromCompanyId(projectCostDto.getFromCompanyName());
            }
            entity.setToCompanyId(projectCostDto.getCurrentCompanyId());
        }else {
            if(StringUtil.isNullOrEmpty(projectCostDto.getToCompanyId())){
                entity.setToCompanyId(projectCostDto.getToCompanyName());
            }
            entity.setFromCompanyId(projectCostDto.getCurrentCompanyId());
        }
    }

    private void updateCostFee(ProjectCostEditDTO projectCostDto) throws Exception {

        ProjectCostEntity entity = this.selectById(projectCostDto.getId());
        ProjectCostEntity origin = new ProjectCostEntity();
        BeanUtilsEx.copyProperties(entity,origin);
        entity.setFee(projectCostDto.getFee());
        this.projectCostDao.updateById(entity);
        //添加项目动态
        dynamicService.addDynamic(origin,entity,projectCostDto.getCurrentCompanyId(),projectCostDto.getAccountId());
        //更新子节点的fee

        if ("1".equals(projectCostDto.getType())  && projectCostDto.getFee()!=null) {
            this.updateContractFee(projectCostDto.getProjectId(), projectCostDto.getFee(),entity.getFlag());
        }

        if ("2".equals(projectCostDto.getType()) && projectCostDto.getFee()!=null) {
            this.updateTechincalFee(projectCostDto.getProjectId(), projectCostDto.getFee(),entity.getFlag());
        }

        if ("3".equals(projectCostDto.getType())  && projectCostDto.getFee()!=null) {
            this.updateDesignFee(projectCostDto.getId(), projectCostDto.getFee(),entity.getFlag());
        }

    }

    private void updateContractFee(String projectId,BigDecimal amount,String flag)throws Exception{
        //1.查询子节点
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("projectId",projectId);
        map.put("type","1");
        map.put("flag",flag);
        List<ProjectCostPointDTO> list =  projectCostPointDao.selectByParam(map);
        if(!CollectionUtils.isEmpty(list)){
            for(ProjectCostPointDTO dto:list){
                ProjectCostPointEntity entity = new ProjectCostPointEntity();
                entity.setId(dto.getId());
                if(!StringUtil.isNullOrEmpty(dto.getFeeProportion())){
                    setProjectCostFee(dto,amount);
                }
            }
        }
    }


    private void updateTechincalFee(String projectId,BigDecimal amount,String flag)throws Exception {
        //1.查询子节点
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        map.put("type", "2");
        map.put("pidIsNull", "1");//标示，只查父节点
        map.put("flag", flag);//标示
        List<ProjectCostPointDTO> list = projectCostPointDao.selectByParam(map);
        if (!CollectionUtils.isEmpty(list)) {
            for (ProjectCostPointDTO dto : list) {
                if (!StringUtil.isNullOrEmpty(dto.getFeeProportion())) {
                    setProjectCostFee(dto,amount);
                    //查询子节点
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("type", dto.getType());
                    param.put("pid", dto.getId());
                    List<ProjectCostPointDTO> childList = this.projectCostPointDao.selectByParam(param);
                    for (ProjectCostPointDTO dto1 : childList) {
                        if (!StringUtil.isNullOrEmpty(dto1.getFeeProportion())) {
                            setProjectCostFee(dto1,dto.getFee());
                        }
                    }
                }
            }
        }
    }

    private void updateDesignFee(String costId,BigDecimal amount,String flag)throws Exception {
        //1.查询子节点
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("costId",costId);
        map.put("type", "3");
        map.put("pidIsNull", "1");//标示，只查父节点
        map.put("flag", flag);//标示，只查父节点
        List<ProjectCostPointDTO> list = projectCostPointDao.selectByParam(map);
        if (!CollectionUtils.isEmpty(list)) {
            for (ProjectCostPointDTO dto : list) {
                if (!StringUtil.isNullOrEmpty(dto.getFeeProportion())) {
                    setProjectCostFee(dto,amount);
                    //查询子节点
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("type", dto.getType());
                    param.put("pid", dto.getId());
                    List<ProjectCostPointDTO> childList = this.projectCostPointDao.selectByParam(param);
                    for (ProjectCostPointDTO dto1 : childList) {
                        if (!StringUtil.isNullOrEmpty(dto1.getFeeProportion())) {
                            setProjectCostFee(dto1,dto.getFee());
                        }
                    }
                }
            }
        }
    }

    private void setProjectCostFee(ProjectCostPointDTO dto,BigDecimal amount){
        if(amount!=null){
            ProjectCostPointEntity entity = new ProjectCostPointEntity();
            entity.setId(dto.getId());
            double proportion = Double.parseDouble(dto.getFeeProportion());
            BigDecimal decimalProprotion = new BigDecimal(proportion / 100);
            entity.setFee(amount.multiply(decimalProprotion));
            dto.setFee(amount.multiply(decimalProprotion));
            this.projectCostPointDao.updateById(entity);
        }
    }

    /**
     * 方法描述：添加修改收款节点
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    public ResponseBean saveOrUpdateProjectCostPoint(ProjectCostPointDTO projectCostPointDTO)throws Exception{

        //漏验证
        ResponseBean responseBean = this.validateTechnicalFee(projectCostPointDTO);
        if("1".equals(responseBean.getError())){
            return responseBean;
        }

        //漏验证
        ProjectCostPointEntity entity = new ProjectCostPointEntity();
        BaseDTO.copyFields(projectCostPointDTO,entity);
        //新增
        if(StringUtil.isNullOrEmpty(projectCostPointDTO.getId())){
//            List<ProjectCostPointEntity> list =  projectCostPointDao.selectByType(objectMap("projectId",entity.getProjectId(),"type","1"));
//            entity.setSeq(list.size()+1);
            entity.setId(StringUtil.buildUUID());
            entity.setCreateBy(projectCostPointDTO.getAccountId());
            if(StringUtil.isNullOrEmpty(projectCostPointDTO.getFlag())){
                entity.setFlag("1");
            }
            projectCostPointDao.insert(entity);
            //添加项目动态
            dynamicService.addDynamic(null,entity,projectCostPointDTO.getCurrentCompanyId(),projectCostPointDTO.getAccountId());
        } else {
            ProjectCostPointEntity origin = projectCostPointDao.selectById(entity.getId());//保留修改前的数据
            projectCostPointDao.updateById(entity);
            //添加项目动态
            dynamicService.addDynamic(origin,entity,projectCostPointDTO.getCurrentCompanyId(),projectCostPointDTO.getAccountId());
            //查询子节点
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("type", projectCostPointDTO.getType());
            param.put("pid", projectCostPointDTO.getId());
            List<ProjectCostPointDTO> childList = this.projectCostPointDao.selectByParam(param);
            for (ProjectCostPointDTO dto1 : childList) {
                if (!StringUtil.isNullOrEmpty(projectCostPointDTO.getFeeProportion())) {
                    setProjectCostFee(dto1,projectCostPointDTO.getFee());
                }
            }
        }
        return ResponseBean.responseSuccess("操作成功").addData("projectCostPointDTO", projectCostPointDTO);
    }

    //4.其他费用（付款），5.其他费用（收款）
    @Override
    public ResponseBean saveOtherProjectCostPoint(ProjectCostPointDTO projectCostPointDTO) throws Exception {
        String companyId = projectCostPointDTO.getAppOrgId();
        ProjectCostPointEntity entity = new ProjectCostPointEntity();
        BaseDTO.copyFields(projectCostPointDTO, entity);
        //新增
        if (StringUtil.isNullOrEmpty(projectCostPointDTO.getId())) {
            //无需做总金额校验
            //查看是否存在ProjectCost数据。此数据仅此用于关联一个costId，用于区分该数据属于哪个公司
            //查询总费用
            String costId=null;
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("projectId",projectCostPointDTO.getProjectId());
            if("4".equals(projectCostPointDTO.getType())){
                //查询是否存在
                map.put("type","4");
                map.put("fromCompanyId",companyId);

            }else {
                map.put("type","5");
                map.put("toCompanyId",companyId);
            }
            List<ProjectCostDTO> totalCost = this.projectCostDao.selectByParam(map);//理论上只会存在一条
            if(!CollectionUtils.isEmpty(totalCost)){
                costId = totalCost.get(0).getId();
            }else {//如果不存在
                costId = this.saveProjectCostForOther(projectCostPointDTO);
            }
            entity.setCostId(costId);
            entity.setId(StringUtil.buildUUID());
            entity.setCreateBy(projectCostPointDTO.getAccountId());
            projectCostPointDao.insert(entity);
            //添加项目动态
            dynamicService.addDynamic(null,entity,projectCostPointDTO.getCurrentCompanyId(),projectCostPointDTO.getAccountId());
        } else {
            ProjectCostPointEntity origin = projectCostPointDao.selectById(entity.getId()); //保留更改前的数据
            projectCostPointDao.updateById(entity);
            //添加项目动态
            dynamicService.addDynamic(origin,entity,projectCostPointDTO.getCurrentCompanyId(),projectCostPointDTO.getAccountId());
        }

        return ResponseBean.responseSuccess("操作成功");
    }

    private String saveProjectCostForOther(ProjectCostPointDTO projectCostPointDTO){
        String costId = StringUtil.buildUUID();
        ProjectCostEntity costEntity = new ProjectCostEntity();
        costEntity.setId(costId);
        costEntity.setProjectId(projectCostPointDTO.getProjectId());
        if("4".equals(projectCostPointDTO.getType())){
            costEntity.setFromCompanyId(projectCostPointDTO.getAppOrgId());
        }else {
            costEntity.setToCompanyId(projectCostPointDTO.getAppOrgId());
        }
        costEntity.setType(projectCostPointDTO.getType());
        this.projectCostDao.insert(costEntity);

        return costId;
    }

    /**
     * 方法描述：发起收款
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    public ResponseBean saveOrUpdateReturnMoneyDetail(ProjectCostPointDetailDTO projectCostPointDetailDTO)throws Exception{
        ResponseBean responseBean = validteReturnMoneyDetail(projectCostPointDetailDTO);
        //not todo 查询流程，当前节点的类型为：3，以下待处理
        if (responseBean != null) {
            return responseBean;
        }
        //todo 查询是否是内部组织
        ProjectCostEntity p = this.projectCostDao.getProjectCostByPointId(projectCostPointDetailDTO.getPointId());
        ProjectCostDTO costDTO = new ProjectCostDTO();
        BaseDTO.copyFields(p,costDTO);
        if(costDTO == null){
            return ResponseBean.responseError("数据错误");
        }
        //todo 根据流程状态，是否处理发票信息
        String invoiceId = null;
        if("1".equals(projectCostPointDetailDTO.getIsInvoice())){ //如果开票
            invoiceId = this.saveInvoice(projectCostPointDetailDTO);
        }
        ProjectCostPointDetailEntity entity = null;
        projectCostPointDetailDTO.setInvoice(invoiceId);
        //新增(发起收收款)
        if (StringUtil.isNullOrEmpty(projectCostPointDetailDTO.getId())) {
            boolean isInnerCompany = this.isInnerCompany(costDTO);
            if(projectCostPointDetailDTO.getCurrentCompanyId().equals(costDTO.getFromCompanyId())){//如果是付款方新增数据
                entity = this.savePointDetailForPay(projectCostPointDetailDTO,costDTO,isInnerCompany);
            }else{ //如果是内部组织，以收款方做为主体方，进行发起收款，同时通知付款方，可以进行付款申请的动作
                entity = this.saveProjectCostPointDetailEntity(projectCostPointDetailDTO,costDTO,isInnerCompany);
                if(isInnerCompany){ //推送消息,等对方确认后，方可财务处理
                    //need todo 推送消息
                    this.sendMessageToOperatorAndOrgAdminForTaskType(p,entity.getId(),entity.getPointId(),307,projectCostPointDetailDTO.getAccountId(),projectCostPointDetailDTO.getCurrentCompanyId());
                    if("1".equals(projectCostPointDetailDTO.getIsInvoice())){
                        this.sendMyTask(entity.getId(), projectCostPointDetailDTO);
                    }
                }else {
                    //推送任务
                    this.sendMyTask(entity.getId(), projectCostPointDetailDTO);
                }
            }
            //保存操作
            this.saveOperator(entity.getId(),projectCostPointDetailDTO);
            //添加项目动态
            dynamicService.addDynamic(null,entity,projectCostPointDetailDTO.getCurrentCompanyId(),projectCostPointDetailDTO.getAccountId());
        } else {
            this.updateProjectCostPointDetailEntity(projectCostPointDetailDTO);
        }
        return ResponseBean.responseSuccess("操作成功").addData("projectCostDetailDTO", projectCostPointDetailDTO);
    }

    private ProjectCostPointDetailEntity savePointDetailForPay(ProjectCostPointDetailDTO projectCostPointDetailDTO,ProjectCostDTO costDTO,boolean isInnerCompany) throws Exception{
        ProjectCostPointDetailEntity entity = null;
        ActivitiDTO activitiDTO = new ActivitiDTO(null,null,projectCostPointDetailDTO.getCurrentCompanyId(),null,projectCostPointDetailDTO.getFee(), ProcessTypeConst.PROCESS_TYPE_PROJECT_PAY_APPLY);
        activitiDTO.getParam().put("approveUser",projectCostPointDetailDTO.getAuditPerson());
        boolean isNeedStartProcess = processService.isNeedStartProcess(activitiDTO);
        if(isNeedStartProcess){
            projectCostPointDetailDTO.setFeeStatus(ProjectCostConst.FEE_STATUS_START);
            entity = this.saveProjectCostPointDetailEntity(projectCostPointDetailDTO,costDTO,isInnerCompany);
            this.startProcessForProjectFeeApply(projectCostPointDetailDTO);
        }else {
            projectCostPointDetailDTO.setFeeStatus(ProjectCostConst.FEE_STATUS_APPROVE);
            entity = this.saveProjectCostPointDetailEntity(projectCostPointDetailDTO,costDTO,isInnerCompany);
            this.sendMyTask(entity.getId(), projectCostPointDetailDTO);
        }
        return entity;
    }

    private ProjectCostPointDetailEntity saveProjectCostPointDetailEntity(ProjectCostPointDetailDTO projectCostPointDetailDTO,ProjectCostDTO costDTO,boolean isInnerCompany) throws Exception {
        //todo 根据流程状态，是否处理发票信息
        ProjectCostPointDetailEntity entity = new ProjectCostPointDetailEntity();
        BaseDTO.copyFields(projectCostPointDetailDTO, entity);
        //新增(发起收收款)
        if (StringUtil.isNullOrEmpty(projectCostPointDetailDTO.getId())) {
            String id = StringUtil.buildUUID();
            entity.setId(id);
            entity.setCompanyId(projectCostPointDetailDTO.getCurrentCompanyId());//设置金额发起的主体方公司
            if(entity.getFeeStatus()==null){
                entity.setFeeStatus(ProjectCostConst.FEE_STATUS_START);//发起的状态，还没有进入审批阶段
            }
            if("1".equals(projectCostPointDetailDTO.getIsInvoice())) { //如果开票，对应收款方而言，则不进入应收状态
                entity.setPaidFeeStatus(ProjectCostConst.FEE_STATUS_START);
            }else {
                entity.setPaidFeeStatus(ProjectCostConst.FEE_STATUS_APPROVE);//如果没有开票，则直接进入应付的状态
            }
            //todo 查询是否是内部组织
            if(isInnerCompany){
                entity.setRelationCompanyId(costDTO.getFromCompanyId());//如果是内部组织，则发起方为收款组织，关联组织为付款方
            }
            projectCostPointDetailDao.insert(entity);
            projectCostPointDetailDTO.setId(entity.getId());
        }
        return entity;
    }

    /**
     * 保存操作记录
     */
    private void saveOperator(String costDetailId,ProjectCostPointDetailDTO dto) {
        //保存操作
        CompanyUserEntity userEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(), dto.getCurrentCompanyId());
        ProjectCostOperaterEntity operaterEntity = new ProjectCostOperaterEntity();
        if (userEntity != null) {
            operaterEntity.setId(StringUtil.buildUUID());
            operaterEntity.setCostDetailId(costDetailId);
            operaterEntity.setCompanyUserId(userEntity.getId());
            operaterEntity.setType("1");//统一用1，发起回款，发起收款，发起付款
            this.projectCostOperaterDao.insert(operaterEntity);
        }
    }

    private String saveInvoice(ProjectCostPointDetailDTO projectCostPointDetailDTO) throws Exception{
        InvoiceEditDTO invoice = new InvoiceEditDTO();
        BeanUtils.copyProperties(projectCostPointDetailDTO,invoice);
        invoice.setId(projectCostPointDetailDTO.getInvoice());
        invoice.setCompanyId(projectCostPointDetailDTO.getCurrentCompanyId());
        String invoiceId = invoiceService.saveInvoice(invoice);
        return invoiceId;
    }

    /**
     *
     * 付款方发起付款申请
     * @return
     * @throws Exception
     */
    private AjaxMessage startProcessForProjectFeeApply(ProjectCostPointDetailDTO dto) throws Exception{
        ApplyProjectCostDTO applyProjectCost = new ApplyProjectCostDTO();
        applyProjectCost.setAccountId(dto.getAccountId());
        applyProjectCost.setCurrentCompanyId(dto.getCurrentCompanyId());
        applyProjectCost.setTargetId(dto.getId());
        applyProjectCost.setCompanyUserId(dto.getCurrentCompanyUserId());
        applyProjectCost.setApplyAmount(dto.getFee());
        applyProjectCost.setProjectId(dto.getProjectId());
        applyProjectCost.setAuditPerson(dto.getAuditPerson());
        expMainService.applyProjectCost(applyProjectCost);
        return AjaxMessage.succeed("操作成功");
    }

    private ProjectCostPointDetailEntity updateProjectCostPointDetailEntity(ProjectCostPointDetailDTO projectCostPointDetailDTO) throws Exception {
        //todo 根据流程状态，是否处理发票信息
        ProjectCostPointDetailEntity entity = new ProjectCostPointDetailEntity();
        BaseDTO.copyFields(projectCostPointDetailDTO, entity);
        ProjectCostPointDetailEntity origin = projectCostPointDetailDao.selectById(projectCostPointDetailDTO.getId());//保留原有数据
        projectCostPointDetailDao.updateById(entity);
        //添加项目动态
        dynamicService.addDynamic(origin,entity,projectCostPointDetailDTO.getCurrentCompanyId(),projectCostPointDetailDTO.getAccountId());
        //createPaymentTask(projectCostPointDetailDTO.getId(),projectCostPointDetailDTO.getAccountId(),projectCostPointDetailDTO.getCurrentCompanyId());
        return entity;
    }
    private ResponseBean validteReturnMoneyDetail(ProjectCostPointDetailDTO dto) throws Exception {
        ProjectCostPointEntity pointEntity = this.projectCostPointDao.selectById(dto.getPointId());
        if (pointEntity == null) {
            return ResponseBean.responseError("操作失败");
        }
        if (null == pointEntity.getFee() || pointEntity.getFee().compareTo(new BigDecimal("0"))==0) {
            return ResponseBean.responseError("请先设置总金额");
        }

        String typememo=getTypememo(pointEntity.getType());
        if (!StringUtil.isNullOrEmpty(dto.getFee())) {
            double sumReturnFee = this.projectCostPaymentDetailDao.getSumFee(pointEntity.getId());
            if (StringUtil.isNullOrEmpty(dto.getId())) {
                if (CommonUtil.doubleCompare(dto.getFee().doubleValue() + sumReturnFee, pointEntity.getFee().doubleValue()) > 0) {
                    return ResponseBean.responseError(typememo+ pointEntity.getFee());
                }
            } else {
                ProjectCostPaymentDetailEntity detailEntity = this.projectCostPaymentDetailDao.selectById(dto.getId());
                if (CommonUtil.doubleCompare(sumReturnFee + dto.getFee().doubleValue() - detailEntity.getFee().doubleValue(), pointEntity.getFee().doubleValue()) > 0) {
                    return ResponseBean.responseError(typememo + pointEntity.getFee());
                }
            }
        }

        return null;
    }
    private ProjectCostDTO getProjectCostDTO(ProjectCostPointDetailDTO projectCostPointDetailDTO) throws Exception{
        ProjectCostEntity p = this.projectCostDao.getProjectCostByPointId(projectCostPointDetailDTO.getPointId());
        ProjectCostDTO dto = new ProjectCostDTO();
        BaseDTO.copyFields(p,dto);

        return dto;
    }


    private boolean isInnerCompany(ProjectCostDTO p){
        if(p!=null && ("2".equals(p.getType()) || "3".equals(p.getType()))){
            CompanyRelationDTO fromRootCompany =  companyDao.getOrgType(p.getFromCompanyId());
            CompanyRelationDTO toRootCompany =  companyDao.getOrgType(p.getToCompanyId());
            if(fromRootCompany!=null && StringUtil.isNullOrEmpty(fromRootCompany.getOrgPid())){
                fromRootCompany.setOrgPid(fromRootCompany.getOrgId());//如果为空，默认为自己
            }
            if(toRootCompany!=null && StringUtil.isNullOrEmpty(toRootCompany.getOrgPid())){
                toRootCompany.setOrgPid(toRootCompany.getOrgId());//如果为空，默认为自己
            }
            if(fromRootCompany!=null && toRootCompany!=null
                    && fromRootCompany.getOrgPid().equals(toRootCompany.getOrgPid())
                    && (
                    (StringUtil.isNullOrEmpty(fromRootCompany.getTypeId()) && "3".equals(toRootCompany.getTypeId()))
                            || (StringUtil.isNullOrEmpty(toRootCompany.getTypeId()) && "3".equals(fromRootCompany.getTypeId()))
                            || ("3".equals(fromRootCompany.getTypeId()) && "3".equals(toRootCompany.getTypeId()))
            )
                    ) {
                return true;
            }
        }
        return false;
    }

    private boolean isInnerCompany(ProjectCostEntity p){
        ProjectCostDTO dto = new ProjectCostDTO();
        BeanUtils.copyProperties(p,dto);
        return isInnerCompany(dto);
    }
    @Override
    public ResponseBean applyProjectCostPayFee(ProjectCostPointDetailDTO projectCostPointDetailDTO) throws Exception {
        ActivitiDTO activitiDTO = new ActivitiDTO(null,projectCostPointDetailDTO.getCurrentCompanyUserId(),projectCostPointDetailDTO.getCurrentCompanyId(),null,projectCostPointDetailDTO.getFee(), ProcessTypeConst.PROCESS_TYPE_PROJECT_PAY_APPLY);
        activitiDTO.getParam().put("approveUser",projectCostPointDetailDTO.getAuditPerson());
        if(processService.isNeedStartProcess(activitiDTO)){
            projectCostPointDetailDTO.setFeeStatus(ProjectCostConst.FEE_STATUS_APPROVE_ING);//申请中状态
            this.startProcessForProjectFeeApply(projectCostPointDetailDTO);
        }else {
            //更改费用的状态
            projectCostPointDetailDTO.setFeeStatus(ProjectCostConst.FEE_STATUS_APPROVE);//如果没有走流程，费用直接通过状态
            this.sendMyTask(projectCostPointDetailDTO.getId(), projectCostPointDetailDTO);
        }
        this.updateProjectCostPointDetailEntity(projectCostPointDetailDTO);
        return ResponseBean.responseSuccess("操作成功");
    }

    private String getTypememo(String type){
        switch (type){
            case "1":
                return "发起回款总金额不能大于";
            case "2":
                return "发起收款总金额不能大于";
            case "3":
                return "发起收款总金额不能大于";
            case "4":
                return "发起付款总金额不能大于";
            case "5":
                return "发起收款总金额不能大于";
        }
        return "";
    }

    /**
     * 方法描述：其他费用收款付款
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    @Override
    public ResponseBean saveOtherCostDetail(ProjectCostPointDetailDTO projectCostPointDetailDTO) throws Exception {
        ProjectCostPointDetailEntity entity = this.projectCostPointDetailDao.selectById(projectCostPointDetailDTO.getId());
        //保留原有数据
        ProjectCostPointDetailEntity origin = new ProjectCostPointDetailEntity();
        BeanUtilsEx.copyProperties(entity,origin);
        entity.setFee(entity.getFee());
        projectCostPointDetailDTO.setFee(entity.getFee());
        projectCostPointDetailDao.updateById(entity);
        //保存项目动态
        dynamicService.addDynamic(origin,entity,projectCostPointDetailDTO.getCurrentCompanyId(),projectCostPointDetailDTO.getAccountId());
        return ResponseBean.responseSuccess("操作成功");
    }


    private void sendMyTaskForReturnMoney(String costDetailId, ProjectCostPointDetailDTO dto) throws Exception {
        ProjectCostPointEntity pointEntity = this.projectCostPointDao.selectById(dto.getPointId());
        String type = "";
        //推送任务 || "4".equals(pointEntity.getType()) || "5".equals(pointEntity.getType())
        if("1".equals(pointEntity.getType()) )//合同回款，其他费用付款收款
        {
            type ="2";
            this.myTaskService.saveMyTask(costDetailId,SystemParameters.CONTRACT_FEE_PAYMENT_CONFIRM ,dto.getAppOrgId(),dto.getAccountId(),dto.getAppOrgId());
        }
        if("2".equals(pointEntity.getType())){//技术审查费
            type="1";
            //给立项组织发起确认信息
            ProjectEntity projectEntity = this.projectDao.selectById(pointEntity.getProjectId());
            this.myTaskService.saveMyTask(costDetailId, SystemParameters.TECHNICAL_REVIEW_FEE_OPERATOR_MANAGER,projectEntity.getCompanyId(),dto.getAccountId(),dto.getAppOrgId());
        }
        if("3".equals(pointEntity.getType())){//合作设计费
            type="1";
            //给发包人发起确认信息
            ProjectCostEntity costEntity = this.projectCostDao.selectById(pointEntity.getCostId());
            this.myTaskService.saveMyTask(costDetailId, SystemParameters.COOPERATIVE_DESIGN_FEE_ORG_MANAGER,costEntity.getFromCompanyId(),dto.getAccountId(),dto.getAppOrgId());
        }
        if ("4".equals(pointEntity.getType()))//其他费用付款
        {
            type = "2";
            this.myTaskService.saveMyTask(costDetailId, SystemParameters.OTHER_FEE_FOR_PAY, dto.getAppOrgId(),dto.getAccountId(),dto.getAppOrgId());
        }
        if ("5".equals(pointEntity.getType()))//其他费用收款
        {
            type = "2";
            this.myTaskService.saveMyTask(costDetailId, SystemParameters.OTHER_FEE_FOR_PAID, dto.getAppOrgId(),dto.getAccountId(),dto.getAppOrgId());
        }
        //保存操作
        CompanyUserEntity userEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(),dto.getAppOrgId());
        ProjectCostOperaterEntity operaterEntity = new ProjectCostOperaterEntity();
        if(userEntity!=null){
            operaterEntity.setId(StringUtil.buildUUID());
            operaterEntity.setCostDetailId(costDetailId);
            operaterEntity.setCompanyUserId(userEntity.getId());
            operaterEntity.setType(type);
            this.projectCostOperaterDao.insert(operaterEntity);
        }
    }

    private void sendMyTask(String costDetailId, ProjectCostPointDetailDTO dto) throws Exception {
        if("1".equals(dto.getIsInvoice())){//代表开发票
            //增加财务对开票信息的确认
            this.myTaskService.saveMyTask(costDetailId,SystemParameters.INVOICE_FINN_IN_FOR_PAID ,dto.getCurrentCompanyId(),dto.getAccountId(),dto.getCurrentCompanyId());
        }else {
            ProjectCostPointEntity pointEntity = this.projectCostPointDao.selectById(dto.getPointId());
            ProjectCostEntity costEntity = this.projectCostDao.selectById(pointEntity.getCostId());
            if ("1".equals(costEntity.getType()))//合同回款，
            {
                this.myTaskService.saveMyTask(costDetailId,SystemParameters.CONTRACT_FEE_PAYMENT_CONFIRM ,dto.getCurrentCompanyId(),dto.getAccountId(),dto.getCurrentCompanyId());
            }
            if ("2".equals(costEntity.getType())) {//技术审查费
                int taskType = 0;
                if(dto.getCurrentCompanyId().equals(costEntity.getFromCompanyId())){//付款组织
                    taskType = SystemParameters.TECHNICAL_REVIEW_FEE_FOR_PAY_2;
                }
                if(dto.getCurrentCompanyId().equals(costEntity.getToCompanyId())){
                    taskType = SystemParameters.TECHNICAL_REVIEW_FEE_FOR_PAID_2;
                }
                if(taskType>0){
                    this.myTaskService.saveMyTask(costDetailId, taskType, dto.getCurrentCompanyId(),dto.getAccountId(),dto.getCurrentCompanyId());
                }
            }
            if ("3".equals(costEntity.getType())) {//合作设计费
                int taskType = 0;
                if(dto.getCurrentCompanyId().equals(costEntity.getFromCompanyId())){//付款组织
                    taskType = SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAY_2;
                }
                if(dto.getCurrentCompanyId().equals(costEntity.getToCompanyId())){
                    taskType = SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAID_2;
                }
                //给发包人发起确认信息
                this.myTaskService.saveMyTask(costDetailId, taskType, dto.getCurrentCompanyId(),dto.getAccountId(),dto.getCurrentCompanyId());
            }

            if ("4".equals(costEntity.getType()))//其他费用付款
            {
                this.myTaskService.saveMyTask(costDetailId, SystemParameters.OTHER_FEE_FOR_PAY, dto.getCurrentCompanyId(),dto.getAccountId(),dto.getCurrentCompanyId());
            }
            if ("5".equals(costEntity.getType()))//其他费用收款
            {
                this.myTaskService.saveMyTask(costDetailId, SystemParameters.OTHER_FEE_FOR_PAID, dto.getCurrentCompanyId(),dto.getAccountId(),dto.getCurrentCompanyId());
            }
        }
    }

    /**
     * 方法描述：查询合同回款(map:projectId)
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    public ResponseBean getContractInfo(ProjectCostPaymentDetailDTO projectCostPaymentDetailDTO)throws Exception{
        //查询当前人在团队的id
        CompanyUserEntity userEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(projectCostPaymentDetailDTO.getAccountId(), projectCostPaymentDetailDTO.getAppOrgId());
        if(userEntity==null){
             ResponseBean.responseError("查询失败");
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("projectId", projectCostPaymentDetailDTO.getProjectId());
        map.put("type",CompanyBillType.FEE_TYPE_CONTRACT.toString());
        map.put("flag","1");
        map.put("costTypeValue",CompanyBillType.DIRECTION_PAYEE);
        Map<String,Object> result = this.getReviewFeeInfo(map,CompanyBillType.FEE_TYPE_CONTRACT.toString());

        //判断是否是经营负责人
        String cpyId = (StringUtil.isNullOrEmpty(projectCostPaymentDetailDTO.getAppOrgId())) ?
                this.projectDao.selectById(projectCostPaymentDetailDTO.getProjectId()).getCompanyId() :
                projectCostPaymentDetailDTO.getAppOrgId();

        ProjectMemberEntity projectMember =  this.projectMemberService.getOperatorManager(projectCostPaymentDetailDTO.getProjectId(),cpyId);
        CompanyUserEntity companyUserEntity = companyUserDao.getCompanyUserByUserIdAndCompanyId(projectCostPaymentDetailDTO.getAccountId(), projectCostPaymentDetailDTO.getAppOrgId());
        String isManager = "0";
        if(companyUserEntity!=null && projectMember!=null && companyUserEntity.getId().equals(projectMember.getCompanyUserId())){
            isManager = "1";
        }

        String isFinancal = "0";
        Map<String,Object> param = new HashMap<String,Object>();
        //财务人员
        param.put("permissionId", "49");//
        param.put("companyId", projectCostPaymentDetailDTO.getAppOrgId());//
        List<CompanyUserDataDTO> companyUserList = this.companyUserDao.getCompanyUserByPermissionId(param);
        //companyUserService.getFinancialManagerForReceive(projectCostPaymentDetailDTO.getAppOrgId());
        if (!CollectionUtils.isEmpty(companyUserList)) {
            for (CompanyUserDataDTO userTableDTO : companyUserList) {
                if (userTableDTO.getId().equals(companyUserEntity.getId())) {
                    isFinancal = "1";
                }
            }
        }

        result.put("isManager",isManager);
        result.put("isFinancal",isFinancal);
        return ResponseBean.responseSuccess("查询成功").setData(result);
    }

    /**
     * 方法描述：查询节点明细
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    public ResponseBean getPointInfo(ProjectCostPointDTO projectCostPointDTO)throws Exception{
        ProjectCostPointEntity entity = projectCostPointDao.selectById(projectCostPointDTO.getId());
        return ResponseBean.responseSuccess("查询成功")
                .addData("projectCostPoint", entity);
    }



    //------------技术审查费--------------
    /**
     * 方法描述：删除费用（目前界面上没有删除操作。用于删除签发的任务时候，如果不存在签发的记录，则合作设计费删除）
     * 作者：MaoSF
     * 日期：2017/3/2
     *
     * @param id
     * @param accountId
     * @param:
     * @return:
     */
    @Override
    public ResponseBean deleteProjectCost(String id, String accountId) throws Exception {

        Map<String,Object> map = new HashMap<>();
        map.put("costId",id);
        List<ProjectCostPointDTO> projectCostPointList = this.projectCostPointDao.selectByParam(map);
        for(ProjectCostPointDTO pointDTO : projectCostPointList){
            this.deleteProjectCostPoint(pointDTO.getId(),accountId);
        }

        ProjectCostEntity costEntity = new ProjectCostEntity();
        costEntity.setId(id);
        costEntity.setUpdateBy(accountId);
        costEntity.setStatus("1");
        this.projectCostDao.updateById(costEntity);
        return ResponseBean.responseSuccess("删除成功");
    }

    /**
     * 方法描述：删除费用节点
     * 作者：MaoSF
     * 日期：2017/3/2
     *
     * @param id
     * @param:
     * @return:
     */
    @Override
    public ResponseBean deleteProjectCostPoint(String id,String accountId) throws Exception {
        ProjectCostPointEntity entity = this.projectCostPointDao.selectById(id);
        if (entity != null) {//有可能不是签发节点，所以entity可能为null
            List<ProjectCostPointDetailEntity> list = this.projectCostPointDetailDao.getCostPointDetailByPointId(id);
            this.projectCostPointDao.updateByPid(id);
            entity.setStatus("1");
            this.projectCostPointDao.updateById(entity);
            //忽略任务
            for (ProjectCostPointDetailEntity entity1 : list) {
                deleteProjectCostPointDetail(entity1.getId(),accountId);
                this.myTaskService.ignoreMyTask(entity1.getId());
                messageService.deleteMessage(entity1.getId());
            }
            messageService.deleteMessage(id);
        }
        return ResponseBean.responseSuccess("删除成功");
    }

    /**
     * 方法描述：删除发起收款明细节点
     * 作者：MaoSF
     * 日期：2017/3/2
     *
     * @param id
     * @param:
     * @return:
     */
    @Override
    public ResponseBean deleteProjectCostPointDetail(String id, String accountId) throws Exception {
        //逻辑删除（发起收款）
        ProjectCostPointDetailEntity pointDetailEntity = new ProjectCostPointDetailEntity();
        pointDetailEntity.setId(id);
        pointDetailEntity.setUpdateBy(accountId);
        pointDetailEntity.setStatus("1");//逻辑删除的标示
        this.projectCostPointDetailDao.updateById(pointDetailEntity);

        //逻辑删除收款的明细
        ProjectCostPaymentDetailEntity paymentDetailEntity = new ProjectCostPaymentDetailEntity();
        paymentDetailEntity.setUpdateBy(accountId);
        paymentDetailEntity.setStatus("1");//逻辑删除的标示
        paymentDetailEntity.setPointDetailId(id);
        this.projectCostPaymentDetailDao.updateCostPaymentDetailByPointDetailId(paymentDetailEntity);
        // this.projectCostOperaterDao.deleteByCostDetailId(id);
        //忽略任务
        this.myTaskService.ignoreMyTask(id);
        //删除消息
        this.messageService.deleteMessage(id);
        return ResponseBean.responseSuccess("删除成功");
    }

    /**
     * 方法描述：处理删除收款明细，是否重新触发任务发送给（合同回款-财务人员，技术审查费--
     * 作者：MaoSF
     * 日期：2017/4/27
     * @param:
     * @return:
     */
    private void handleSendMyTaskForChangeProjectCostPayment(ProjectCostPaymentDetailEntity paymentDetailEntity,String accountId,String currentCompanyId) throws Exception{
        if (paymentDetailEntity == null) return;
        createPaymentTask(paymentDetailEntity.getPointDetailId(),accountId,currentCompanyId);
    }

    /**
     * 方法描述：查找是否存在确认付款/确认付款任务，如果没有且需要确认付款/到款，添加一条任务
     * 作者：ZCL
     * 日期：2017/5/4
     */
    private void createPaymentTask(String detailId,String accountId,String currentCompanyId) throws Exception{
        if (detailId == null) return;
        Map<String, Object> map = new HashMap<>();
        map.put("targetId", detailId);
        List<MyTaskEntity> myTaskList = this.myTaskService.getMyTaskByParam(map);
        ProjectCostPointDetailEntity pointDetailEntity = this.projectCostPointDetailDao.selectById(detailId);
        double paid = projectCostPaymentDetailDao.getSumFee(detailId);
        if(CollectionUtils.isEmpty(myTaskList)){

            if(pointDetailEntity!=null) {
                if ((CommonUtil.doubleCompare(paid, pointDetailEntity.getFee().doubleValue())) < 0) {
                    ProjectCostPointEntity pointEntity = this.projectCostPointDao.selectById(pointDetailEntity.getPointId());
                    if (pointEntity != null) {
                        int type = Integer.parseInt(pointEntity.getType());
                        //合同回款
                        if (type==CompanyBillType.FEE_TYPE_CONTRACT) {
                            ProjectEntity projectEntity = this.projectDao.selectById(pointEntity.getProjectId());
                            this.myTaskService.saveMyTask(detailId, SystemParameters.CONTRACT_FEE_PAYMENT_CONFIRM, projectEntity.getCompanyId(),accountId,currentCompanyId);
                        }
                        //技术审查费
                        if (type==CompanyBillType.FEE_TYPE_TECHNICAL) {
                            //给立项组织发起确认信息
                            ProjectEntity projectEntity = this.projectDao.selectById(pointEntity.getProjectId());
                            this.myTaskService.saveMyTask(detailId, SystemParameters.TECHNICAL_REVIEW_FEE_OPERATOR_MANAGER, projectEntity.getCompanyId(),accountId,currentCompanyId);
                        }
                        //合作设计费
                        if (type==CompanyBillType.FEE_TYPE_COOPERATIVE) {
                            //给发包人发起确认信息
                            ProjectCostEntity costEntity = this.projectCostDao.selectById(pointEntity.getCostId());
                            this.myTaskService.saveMyTask(detailId, SystemParameters.COOPERATIVE_DESIGN_FEE_ORG_MANAGER, costEntity.getFromCompanyId(),accountId,currentCompanyId);
                        }
                        //其他费用付款
                        if (type==CompanyBillType.FEE_TYPE_OTHER) {
                            ProjectCostEntity costEntity = this.projectCostDao.selectById(pointEntity.getCostId());
                            this.myTaskService.saveMyTask(detailId, SystemParameters.OTHER_FEE_FOR_PAY, costEntity.getFromCompanyId(),accountId,currentCompanyId);
                        }
                        //其他费用收款
                        if (type==CompanyBillType.FEE_TYPE_OTHER_PAID) {
                            ProjectCostEntity costEntity = this.projectCostDao.selectById(pointEntity.getCostId());
                            this.myTaskService.saveMyTask(detailId, SystemParameters.OTHER_FEE_FOR_PAID, costEntity.getToCompanyId(),accountId,currentCompanyId);
                        }
                    }
                }
            }else {
                if ((CommonUtil.doubleCompare(paid,pointDetailEntity.getFee().doubleValue())) >= 0) {
                    //此处为经营负责人，把付款或许收款金额修改比原来大的时候调用
                    //把任务设置为完成
                    //理论上，该种任务只会存在一条
                    for(MyTaskEntity myTaskEntity:myTaskList){
                        if(myTaskEntity.getTaskType()==SystemParameters.TECHNICAL_REVIEW_FEE_OPERATOR_MANAGER || myTaskEntity.getTaskType()==SystemParameters.COOPERATIVE_DESIGN_FEE_ORG_MANAGER){
                            myTaskEntity.setStatus("1");
                            this.myTaskService.updateById(myTaskEntity);
                        }
                    }
                }
            }
        }
    }

    /**
     * 方法描述：删除收款明细节点
     * 作者：MaoSF
     * 日期：2017/4/27
     *
     * @param id
     * @param accountId
     * @param:
     * @return:
     */
    @Override
    public ResponseBean deleteProjectCostPaymentDetail(String id, String accountId,String currentCompanyId) throws Exception {
        //逻辑删除收款的明细
        ProjectCostPaymentDetailEntity paymentDetailEntity = this.projectCostPaymentDetailDao.selectById(id);
        if(paymentDetailEntity==null){
            return ResponseBean.responseError("删除失败");
        }
        paymentDetailEntity.setUpdateBy(accountId);
        paymentDetailEntity.setStatus("1");//逻辑删除的标示
        this.projectCostPaymentDetailDao.updateById(paymentDetailEntity);

        //this.projectCostOperaterDao.deleteByCostDetailId(id);

        //忽略任务
        this.myTaskService.ignoreMyTask(id);
        //删除消息
        this.messageService.deleteMessage(id);

        //处理是否触发重新发起任务
        this.handleSendMyTaskForChangeProjectCostPayment(paymentDetailEntity,accountId,currentCompanyId);

        return ResponseBean.responseSuccess("删除成功");
    }


    /**
     * 方法描述：删除费用节点
     * 作者：MaoSF
     * 日期：2017/3/2
     *
     * @param taskId
     * @param:
     * @return:
     */
    @Override
    public ResponseBean deleteProjectCostPointByTaskId(String taskId) throws Exception {
        ProjectCostPointEntity entity = this.projectCostPointDao.getCostPointByTaskId(taskId);
        if(entity!=null){//有可能不是签发节点，所以entity可能为null
            List<ProjectCostPaymentDetailEntity> list = this.projectCostPaymentDetailDao.getDetailByRootId(entity.getId());
            //忽略任务
            for (ProjectCostPaymentDetailEntity entity1 : list) {
                if (entity != null) {
                    this.myTaskService.ignoreMyTask(entity1.getId());
                    this.messageService.deleteMessage(entity1.getId());
                }
            }
            this.projectCostPointDao.updateByPid(entity.getId());
            entity.setStatus("1");
            this.projectCostPointDao.updateById(entity);

            this.messageService.deleteMessage(entity.getId());
        }
        return ResponseBean.responseError("删除成功");
    }



    /**
     * 方法描述：删除费用节点
     * 作者：MaoSF
     * 日期：2017/3/2
     *
     * @param taskId
     * @param:
     * @return:
     */
    @Override
    public ResponseBean deleteProjectCostPointForDesignContent(String taskId) throws Exception {
        ProjectCostPointEntity entity = this.projectCostPointDao.getCostPointByTaskId(taskId);
        if(entity==null){//不做处理，理由，有可能没有设置乙方或许乙方为自己的情况下
            return  ResponseBean.responseSuccess();
        }
        //先胖的是否有子节点或许收款节点
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("pid",entity.getId());
        List<ProjectCostPointDTO> list = this.projectCostPointDao.selectByParam(map);
        if(!CollectionUtils.isEmpty(list)){
            return ResponseBean.responseSuccess();//不做删除操作
        }

        //判断是否存在收款节点
        List<ProjectCostPaymentDetailEntity> list2 = this.projectCostPaymentDetailDao.getDetailByRootId(entity.getId());
        if(!CollectionUtils.isEmpty(list2)){
            return ResponseBean.responseSuccess();//不做删除操作
        }

        //不存在子节点的情况下，则删除
        entity.setStatus("1");
        this.projectCostPointDao.updateById(entity);

        this.messageService.deleteMessage(entity.getId());

        return  ResponseBean.responseSuccess();
    }


    /**
     * 方法描述：合同乙方更改技术审查费
     * 作者：MaoSF
     * 日期：2017/3/2
     *
     * @param projectId
     * @param accountId
     * @param flag
     * @param:flag(1:重新添加，2.全部删除，4.先删除后添加）此处3，在原有的代码中处理
     * @return:
     */
    @Override
    public ResponseBean handPartBChange(String projectId, String accountId, int flag) throws Exception {
        String type = CompanyBillType.FEE_TYPE_TECHNICAL.toString();
        if(flag!=0){
            if(flag==1){
                //saveTechnicalReviewFeePoint(projectId,"2");
            }
            if(flag==2){
                deleteTechnicalFee(projectId,type);
                deletePoint(projectId,accountId,type);
            }
            if(flag==4){
                deleteTechnicalFee(projectId,type);
                deletePoint(projectId,accountId,type);
               // saveTechnicalReviewFeePoint(projectId,"2");
            }
        }
        return null;
    }

    private void deleteTechnicalFee(String projectId,String type){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("projectId",projectId);
        map.put("type",type);
        List<ProjectCostDTO> list = this.projectCostDao.selectByParam(map);
        for(ProjectCostDTO dto:list){
            ProjectCostEntity projectCost = new ProjectCostEntity();
            projectCost.setId(dto.getId());
            projectCost.setStatus("1");
            this.projectCostDao.updateById(projectCost);
        }
    }

    private void deletePoint(String projectId,String accountId,String type) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("projectId",projectId);
        map.put("type",type);
        List<ProjectCostPointEntity> list = projectCostPointDao.selectByType(map);
        for(ProjectCostPointEntity entity:list){
            deleteProjectCostPoint(entity.getId(),accountId);
        }
    }


    /**
     * 方法描述：查询技术审查费(map:projectId)
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    public ResponseBean getTechicalReviewFeeInfo(Map<String,Object> map)throws Exception{
        map.put("pidIsNull","1");//标示，只查父节点
        map.put("flag","1");//标示，查询正式合同
        //查询当前人在团队的id
        CompanyUserEntity userEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId((String) map.get("accountId"),(String) map.get("appOrgId"));
        if(userEntity==null){
            ResponseBean.responseError("查询失败");
        }
        ProjectEntity projectEntity = this.projectDao.selectById(map.get("projectId"));
        if(projectEntity==null){
            ResponseBean.responseError("查询失败");
        }
        map.put("companyUserId",userEntity.getId());
        map.put("flag","1");
        Map<String,Object> result = new HashMap<>();
        result.put("projectName",projectEntity.getProjectName());
        CompanyEntity companyEntity = this.companyDao.selectById(projectEntity.getCompanyId());
        if(companyEntity!=null){
            result.put("companyName",companyEntity.getCompanyName());
        }
        if(!StringUtil.isNullOrEmpty(projectEntity.getCompanyBid()))
        {
            CompanyEntity companyBEntity = this.companyDao.selectById(projectEntity.getCompanyBid());
            if(companyBEntity!=null){
                result.put("companyBName",companyBEntity.getCompanyName());
            }
        }
        if (projectEntity.getCompanyId().equals(map.get("appOrgId"))) {//1为立项方，0：为乙方
            result.put("isSetUpProject", "1");
            map.put("costTypeValue",CompanyBillType.DIRECTION_PAYER);//付款方
        } else {
            result.put("isSetUpProject", "0");
            map.put("costTypeValue",CompanyBillType.DIRECTION_PAYEE);//收款方
        }
        Map<String,Object> resultData =  this.getReviewFeeInfo(map,CompanyBillType.FEE_TYPE_TECHNICAL.toString());
        result.putAll(resultData);
        String isManager = "0";
        if(!StringUtil.isNullOrEmpty(projectEntity.getCompanyBid()) && !projectEntity.getCompanyBid().equals(projectEntity.getCompanyId()))
        {
            ProjectMemberEntity projectMember = this.projectMemberService.getOperatorManager(projectEntity.getId(),projectEntity.getCompanyBid());
            CompanyUserEntity companyUserEntity = companyUserDao.getCompanyUserByUserIdAndCompanyId((String)map.get("accountId"),(String)map.get("appOrgId"));
            if(companyUserEntity!=null && projectMember!=null && companyUserEntity.getId().equals(projectMember.getCompanyUserId())){
                isManager = "1";
            }
        }

        result.put("isManager",isManager);
        return ResponseBean.responseSuccess("查询成功").setData(result);
    }

    /**
     * 方法描述：合作设计费
     * 作者：chenzhujie
     * 日期：2017/3/1
     *
     * @param map(projectId,companyId:当前公司)
     */
    @Override
    public ResponseBean getCooperativeDesignFeeInfo(Map<String, Object> map) throws Exception {

        //查询当前人在团队的id
        CompanyUserEntity userEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId((String) map.get("accountId"),(String) map.get("appOrgId"));
        if(userEntity==null){
            ResponseBean.responseError("查询失败");
        }
        map.put("companyUserId",userEntity.getId());
        map.put("companyId",map.get("appOrgId"));

        ProjectEntity projectEntity = this.projectDao.selectById(map.get("projectId"));
        if (projectEntity == null) {
            return ResponseBean.responseError("查询失败");
        }

        List<ProjectCooperationCostTotalDTO> resultList = new ArrayList<>();
        //首先查询关系

        List<Map<String,String>> relationList = projectTaskRelationDao.getProjectRelation(map);
        //1.查询总费用

        //2.查询每个表的明细
        for(Map<String,String> map1:relationList){
            Map<String,Object> param = new HashMap<>();
            //param.put("taskIdList",map1.get("taskId").split(","));
            param.put("pidIsNull", "1");//标示，只查父节点
            param.put("projectId",map.get("projectId"));
            param.put("fromCompanyId",map1.get("fromCompanyId"));
            param.put("toCompanyId",map1.get("toCompanyId"));
            param.put("appOrgId",map.get("appOrgId"));
            param.put("companyId", map.get("appOrgId"));
            param.put("companyUserId",userEntity.getId());
            param.put("projectCompanyId", projectEntity.getCompanyId());
            param.put("flag", "1");
            ProjectCooperationCostTotalDTO result = this.getReviewFeeInfoForCooperative(param,CompanyBillType.FEE_TYPE_COOPERATIVE.toString());
            result.setFromCompanyId(map1.get("fromCompanyId"));
            result.setToCompanyId(map1.get("toCompanyId"));

            String isManager = "0";
            ProjectMemberEntity projectManagerEntity = this.projectMemberService.getOperatorManager((String)map.get("projectId"), map1.get("fromCompanyId"));
            CompanyUserEntity companyUserEntity = companyUserDao.getCompanyUserByUserIdAndCompanyId((String)map.get("accountId"),(String)map.get("appOrgId"));
            if (companyUserEntity!=null  && projectManagerEntity!=null && companyUserEntity.getId().equals(projectManagerEntity.getCompanyUserId())) {
                isManager = "1";
            }
            result.setIsManager(isManager);

            //收款方
            String isManager2 = "0";
            ProjectMemberEntity projectManagerEntity2 = this.projectMemberService.getOperatorManager((String)map.get("projectId"), map1.get("toCompanyId"));
            if (companyUserEntity!=null  && projectManagerEntity2!=null && companyUserEntity.getId().equals(projectManagerEntity2.getCompanyUserId())) {
                isManager2 = "1";
            }
            result.setIsManager2(isManager2);

            resultList.add(result);
        }
        CompanyEntity companyEntity = this.companyDao.selectById(projectEntity.getCompanyId());
        //合作关系单独请求
        return ResponseBean.responseSuccess("查询成功").addData("resultList",resultList)
                .addData("companyName",companyEntity.getCompanyName())
                .addData("cooperationCompanyCount",relationList.size());
    }

    @Override
    public ResponseBean getCooperativeDesignFeeInfoByCostId(Map<String, Object> map) throws Exception {
        map.put("pidIsNull","1");//标示，只查父节点
        map.put("flag","1");//标示，查询正式合同
        //查询当前人在团队的id
        CompanyUserEntity userEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId((String) map.get("accountId"),(String) map.get("appOrgId"));
        if(userEntity==null){
            ResponseBean.responseError("查询失败");
        }
        map.put("companyUserId",userEntity.getId());
        map.put("flag","1");
        Map<String,Object> result = this.getReviewFeeInfo(map,"3");
        return ResponseBean.responseSuccess("查询成功").setData(result);
    }


    private ProjectCooperationCostTotalDTO getReviewFeeInfoForCooperative(Map<String,Object> map,String type) throws Exception{
        map.put("type",type);
        //查询总费用
        List<ProjectCostDTO> totalCost = this.projectCostDao.selectByParam(map);//理论上只会存在一条
        if(!CollectionUtils.isEmpty(totalCost)){//根据costId查询相对应的节点数据
            map.put("costId",totalCost.get(0).getId());
        }
        List<ProjectCostPointDTO> projectCostPointDTOS = this.projectCostPointDao.selectByParam(map);
        List<ProjectCostPointDataDTO> projectCostPointDataList = new ArrayList<ProjectCostPointDataDTO>();
        ProjectCostTotalDTO totalDTO = new ProjectCostTotalDTO();
        ProjectCooperationCostTotalDTO returnDTO = new ProjectCooperationCostTotalDTO();
        for(ProjectCostPointDTO dto:projectCostPointDTOS){
            this.getProjectCostPointData(dto,totalDTO,projectCostPointDataList,map);
        }
        BaseDTO.copyFields(totalDTO,returnDTO);
        if(!CollectionUtils.isEmpty(totalCost)){
            returnDTO.setTotalCost(totalCost.get(0).getFee());
            returnDTO.setCostId(totalCost.get(0).getId());
            //查询合作方的组织名称
            if(type.equals(CompanyBillType.FEE_TYPE_COOPERATIVE.toString()))
            {
                String companyId =(String)map.get("appOrgId");
                CompanyEntity companyEntity = null;
                if(companyId.equals(map.get("fromCompanyId"))){
                    companyEntity = this.companyDao.selectById(map.get("toCompanyId"));
                }else {
                    companyEntity = this.companyDao.selectById(map.get("fromCompanyId"));
                }
                if(companyEntity!=null)
                {
                    returnDTO.setCompanyName(companyEntity.getAliasName());
                }
            }
        }

        returnDTO.setPointList(projectCostPointDataList);
        return returnDTO;
    }

    private Map<String,Object> getReviewFeeInfo(Map<String,Object> map,String type) throws Exception{
        map.put("type",type);

        List<ProjectCostDTO> totalCost = this.projectCostDao.selectByParam(map);//理论上只会存在一条
        ProjectCostTotalDTO totalDTO = new ProjectCostTotalDTO();
        List<ProjectCostPointDTO> projectCostPointDTOS = new ArrayList<>();
        if(!CollectionUtils.isEmpty(totalCost)){//根据costId查询相对应的节点数据
            map.put("costId",totalCost.get(0).getId());
            totalDTO.setTotalFee(totalCost.get(0).getFee());
            projectCostPointDTOS = this.projectCostPointDao.selectByParam(map);
        }

        Map<String,Object> result = new HashMap<String,Object>();
        result.put("totalFlag",0);
        if(!CollectionUtils.isEmpty(totalCost)){
            result.put("totalCost",totalCost.get(0).getFee());
            if(totalCost.get(0).getFee()!=null){
                result.put("totalFlag",1);
            }
            result.put("costId",totalCost.get(0).getId());
            //查询合作方的组织名称
            if(type.equals(CompanyBillType.FEE_TYPE_COOPERATIVE.toString()))
            {
                String companyId =(String)map.get("appOrgId");
                CompanyEntity companyEntity = null;
                if(companyId.equals(totalCost.get(0).getFromCompanyId())){
                    companyEntity = this.companyDao.selectById(totalCost.get(0).getToCompanyId());
                    map.put("costTypeValue",CompanyBillType.DIRECTION_PAYER);
                }else {
                    companyEntity = this.companyDao.selectById(totalCost.get(0).getFromCompanyId());
                    map.put("costTypeValue",CompanyBillType.DIRECTION_PAYEE);
                }
                if(companyEntity!=null)
                {
                    result.put("companyName",companyEntity.getCompanyName());
                }
            }
        }
        List<ProjectCostPointDataDTO> projectCostPointDataList = new ArrayList<ProjectCostPointDataDTO>();
        for(ProjectCostPointDTO dto:projectCostPointDTOS){
            this.getProjectCostPointData(dto,totalDTO,projectCostPointDataList,map);
        }
        result.put("pointList",projectCostPointDataList);
        result.put("total",totalDTO);

        return result;
    }

    private ProjectCostPointDataDTO getProjectCostPointData(ProjectCostPointDTO dto,ProjectCostTotalDTO totalDTO, List<ProjectCostPointDataDTO> projectCostPointDataList,Map<String,Object> otherMap)throws Exception{

        ProjectCostPointDataDTO dataDTO = new ProjectCostPointDataDTO();
        BaseDTO.copyFields(dto,dataDTO);
        projectCostPointDataList.add(dataDTO);
        dataDTO.setUnpaid(new BigDecimal(0));//首先默认为0

        //查询子节点
        Map<String,Object> param = new HashMap<>();
        param.put("type",dto.getType());
        param.put("pid",dataDTO.getId());
        List<ProjectCostPointDTO> childList = this.projectCostPointDao.selectByParam(param);

        if(!CollectionUtils.isEmpty(childList)){
            BigDecimal paidFee = new BigDecimal("0");
            for(ProjectCostPointDTO dto1:childList){
                ProjectCostPointDataDTO dataDTO1 = new ProjectCostPointDataDTO();
                BaseDTO.copyFields(dto1,dataDTO1);
                this.getPointDetailData(dataDTO1, otherMap,false,totalDTO);
                if(dataDTO1.getDeleteFlag()==1){
                    dataDTO.setDeleteFlag(1);
                }
                if(dataDTO1.getPaidFee()!=null){
                    paidFee = paidFee.add(dataDTO1.getPaidFee());
                }
                dataDTO.setPaidFee(paidFee);
            }

            if (dataDTO.getFee()!=null) {
                dataDTO.setUnpaid(dataDTO.getFee().subtract(paidFee));
            }

            dataDTO.setMemo("包含"+childList.size()+"个子节点。");
        }else {
            this.getPointDetailData(dataDTO,otherMap,true,totalDTO);
        }

        if(CompanyBillType.FEE_TYPE_COOPERATIVE.toString().equals(dto.getType()) && !StringUtil.isNullOrEmpty(dto.getTaskId())){
            dataDTO.setDeleteFlag(1);
        }

        //处理合计
        if(!StringUtil.isNullOrEmpty(dto.getFeeProportion())){
            totalDTO.setFeeProportion(totalDTO.getFeeProportion()+Double.parseDouble(dto.getFeeProportion()));
        }

        if(null!=dto.getFee()){
            totalDTO.setFee(totalDTO.getFee().add(dto.getFee()));
        }
        if(null!=dataDTO.getUnpaid()) {
            totalDTO.setUnpaid(totalDTO.getUnpaid().add(dataDTO.getUnpaid()));
        }
        return dataDTO;
    }

    private void getPointDetailData(ProjectCostPointDataDTO dto,Map<String,Object> otherMap,boolean isSelectOperator,ProjectCostTotalDTO totalDTO) throws Exception{
        //查询明细
        Map<String,Object> map = new HashMap<>();
        map.put("pointId",dto.getId());
        dto.setUnpaid(new BigDecimal(0));
        dto.setBackFee(new BigDecimal("0"));
        dto.setPayFee(new BigDecimal("0"));
        dto.setPaidFee(new BigDecimal("0"));
        List<ProjectCostPointDetailEntity> pointDetailList = this.projectCostPointDetailDao.getCostPointDetailByPointId(dto.getId());
        List<ProjectCostPointDetailDataDTO> pointDetailDataList = new ArrayList<>();
        for(ProjectCostPointDetailEntity detail:pointDetailList){
            ProjectCostPointDetailDataDTO pointDetailDataDTO = new ProjectCostPointDetailDataDTO();
            BaseDTO.copyFields(detail,pointDetailDataDTO);
            this.getPaymentDetailData(pointDetailDataDTO,otherMap);
            //累积发起收款的金额
            dto.setBackFee(dto.getBackFee().add(detail.getFee()));

            /******************************/
            dto.setPayFee(dto.getPayFee().add(pointDetailDataDTO.getPayFee()));
            dto.setPaidFee(dto.getPaidFee().add(pointDetailDataDTO.getPaidFee()));
            /******************************/
            //只要有到款，则不可被删除
            if(pointDetailDataDTO.getPayFee().compareTo(new BigDecimal("0"))>0 || pointDetailDataDTO.getPaidFee().compareTo(new BigDecimal("0"))>0){
                pointDetailDataDTO.setDeleteFlag(1);//不可被删除
            }

            //累积发起收款
            totalDTO.setBackMoney(totalDTO.getBackMoney().add(pointDetailDataDTO.getFee()));

            //累积总到款
            totalDTO.setToTheMoney(totalDTO.getToTheMoney().add(pointDetailDataDTO.getPaidFee()));

            //累积总付款
            totalDTO.setPayTheMoney(totalDTO.getPayTheMoney().add(pointDetailDataDTO.getPayFee()));

            //累积经营负责人付款（到款）的金额
            totalDTO.setPaymentFee(totalDTO.getPaymentFee().add(pointDetailDataDTO.getPaymentFee()));

            if(isSelectOperator){
                //查询操作人
                this.getOperatorForCostDetail(pointDetailDataDTO,dto.getType(),otherMap);
            }

            pointDetailDataList.add(pointDetailDataDTO);
        }

        dto.setPointDetailList(pointDetailDataList);
    }

    /**
     * 方法描述：处理操作人
     * 作者：MaoSF
     * 日期：2017/3/6
     */
    private void getOperatorForCostDetail(ProjectCostPointDetailDataDTO detailDataDTO, String type, Map<String,Object> otherMap) throws Exception{
        if(StringUtil.isNullOrEmpty(type)){
            return;
        }
        int typeValue = Integer.parseInt(type);
        String companyUserId = (String)otherMap.get("companyUserId");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("costDetailId",detailDataDTO.getId());
        List<ProjectCostOperaterDTO> operaterDTOS = this.projectCostOperaterDao.getCostOperator(map);
        //设置权限
        detailDataDTO.setRoleMap(handleOperateRole(detailDataDTO.getId(),type,companyUserId));
        handleNeedRoleToHandle(detailDataDTO,companyUserId);//对权限做补偿
        if(typeValue==CompanyBillType.FEE_TYPE_CONTRACT
                || typeValue==CompanyBillType.FEE_TYPE_OTHER
                || typeValue==CompanyBillType.FEE_TYPE_OTHER_PAID){//合同回款,其他费用
            for(ProjectCostOperaterDTO dto:operaterDTOS){
                if("2".equals(dto.getType())){
                    detailDataDTO.setUserName(dto.getUserName());
                }
            }
        }

        if(typeValue== CompanyBillType.FEE_TYPE_TECHNICAL || typeValue==CompanyBillType.FEE_TYPE_COOPERATIVE){//技术审查费
            for(ProjectCostOperaterDTO dto:operaterDTOS){
                if("1".equals(dto.getType())){
                    detailDataDTO.setUserName(dto.getUserName());
                }
                if("3".equals(dto.getType())){
                    detailDataDTO.setUserName2(dto.getUserName());
                }
                if("4".equals(dto.getType())){
                    if(StringUtil.isNullOrEmpty( detailDataDTO.getUserName2())){
                        detailDataDTO.setUserName2(dto.getUserName());
                    }
                }
            }
        }
    }

    /**
     * 方法描述：获取收款详情
     * 作者：MaoSF
     * 日期：2017/4/25
     */
    private void getPaymentDetailData(ProjectCostPointDetailDataDTO dto, Map<String,Object> otherMap) throws Exception {
        //查询明细
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pointDetailId", dto.getId());
        List<ProjectCostPaymentDetailDTO> detailDTOS = this.projectCostPaymentDetailDao.selectByPointDetailId(map);
        List<ProjectCostPaymentDetailDataDTO> detailDataList = new ArrayList<>();
        BigDecimal paidFee = new BigDecimal("0");
        BigDecimal payFee = new BigDecimal("0");
        BigDecimal paymentFee = new BigDecimal("0");
        for (ProjectCostPaymentDetailDTO detailDataDTO : detailDTOS) {
            ProjectCostPaymentDetailDataDTO detailDataDTO1 = new ProjectCostPaymentDetailDataDTO();
            BaseDTO.copyFields(detailDataDTO, detailDataDTO1);
            //查询操作人
            this.getOperatorForCostDetail(detailDataDTO1, dto.getType(), otherMap);
            //统计到款
            if(!StringUtil.isNullOrEmpty(detailDataDTO.getPaidDate())){
                paidFee = paidFee.add(detailDataDTO.getFee());
            }
            //统计付款
            if(!StringUtil.isNullOrEmpty(detailDataDTO.getPayDate())){
                payFee = payFee.add(detailDataDTO.getFee());
            }

            //累积明细金额
            paymentFee = paymentFee.add(detailDataDTO.getFee());

            detailDataList.add(detailDataDTO1);
        }
        //付金额
        dto.setPayFee(payFee);
        //收金额
        dto.setPaidFee(paidFee);
        //总收款（付款）明细
        dto.setPaymentFee(paymentFee);
        dto.setPaymentList(detailDataList);

    }

    /**
     * 方法描述：处理操作人
     * 作者：MaoSF
     * 日期：2017/3/6
     */
    private void getOperatorForCostDetail(ProjectCostPaymentDetailDataDTO detailDataDTO, String type, Map<String,Object> otherMap) throws Exception{
        if(StringUtil.isNullOrEmpty(otherMap.get("type"))){
            return;
        }
        type = otherMap.get("type").toString();
        int typeValue = Integer.parseInt(type);
        String companyUserId = (String)otherMap.get("companyUserId");
        int costTypeValue = Integer.parseInt(otherMap.get("costTypeValue").toString());
        Map<String,Object> map = new HashMap<>();
        map.put("costDetailId",detailDataDTO.getId());
        List<ProjectCostOperaterDTO> operaterDTOS = this.projectCostOperaterDao.getCostOperator(map);
        //设置权限
        detailDataDTO.setRoleMap(handleOperateRole(detailDataDTO.getId(),type,companyUserId));
        handleNeedRoleToHandle(detailDataDTO,companyUserId);//对权限做补偿
        if(typeValue==CompanyBillType.FEE_TYPE_CONTRACT
                || typeValue==CompanyBillType.FEE_TYPE_OTHER
                || typeValue==CompanyBillType.FEE_TYPE_OTHER_PAID){//合同回款,其他费用
            for(ProjectCostOperaterDTO dto:operaterDTOS){
                if(costTypeValue==CompanyBillType.DIRECTION_PAYER){
                    if("6".equals(dto.getType())){
                        detailDataDTO.setHandlerName(dto.getUserName());
                    }
                }else {
                    if("5".equals(dto.getType())){
                        detailDataDTO.setHandlerName(dto.getUserName());
                    }
                }
            }
        }

        if(typeValue== CompanyBillType.FEE_TYPE_TECHNICAL || typeValue==CompanyBillType.FEE_TYPE_COOPERATIVE){//技术审查费，合作设计费
            String userName = "";
            for(ProjectCostOperaterDTO dto:operaterDTOS){
                if(costTypeValue==CompanyBillType.DIRECTION_PAYER){
                    if("3".equals(dto.getType()) || "6".equals(dto.getType())){
                        if(StringUtil.isNullOrEmpty(userName)){
                            userName = dto.getUserName();
                        }else {
                            userName +="  " + dto.getUserName();
                        }
                    }
                }else {
                    if("3".equals(dto.getType()) || "5".equals(dto.getType())){
                        if(StringUtil.isNullOrEmpty(userName)){
                            userName = dto.getUserName();
                        }else {
                            userName +="  " + dto.getUserName();
                        }
                    }
                }
            }
            detailDataDTO.setHandlerName(userName);
        }
    }

    private void handleNeedRoleToHandle(ProjectCostPaymentDetailDataDTO detailDataDTO, String companyUserId) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("targetId", detailDataDTO.getId());
        List<MyTaskEntity> myTaskList = this.myTaskService.getMyTaskByParam(map);
        CompanyUserEntity companyUserEntity = this.companyUserDao.selectById(companyUserId);
        if(companyUserEntity==null){
            return;
        }
        String companyId = companyUserEntity.getCompanyId();
        if(!CollectionUtils.isEmpty(myTaskList)){//理论上只会存在一条有效数据
            MyTaskEntity entity = myTaskList.get(0);
            if(companyId.equals(entity.getCompanyId())) {
                if (entity.getTaskType() == 5 || entity.getTaskType() == 7) {
                    map.clear();
                    map.put("permissionId", "50");
                    map.put("companyId", companyId);
                    map.put("userId", companyUserEntity.getUserId());
                    List<CompanyUserDataDTO> companyUserList = this.companyUserDao.getCompanyUserByPermissionId(map);
                    if (!CollectionUtils.isEmpty(companyUserList)){
                        detailDataDTO.getRoleMap().put("flag" + entity.getTaskType(), entity.getId());
                    }else {
                        detailDataDTO.getRoleMap().remove("flag" + entity.getTaskType());
                    }
                }

                if(entity.getTaskType()==8 || entity.getTaskType()==9 || entity.getTaskType()==10 || (entity.getTaskType()>15 &&  entity.getTaskType()<22)){
                    map.clear();
                    map.put("permissionId", "49");
                    map.put("companyId", companyId);
                    map.put("userId", companyUserEntity.getUserId());
                    List<CompanyUserDataDTO> companyUserList = this.companyUserDao.getCompanyUserByPermissionId(map);
                    if (!CollectionUtils.isEmpty(companyUserList)) {
                        detailDataDTO.getRoleMap().put("flag" + entity.getTaskType(), entity.getId());
                    } else {
                        detailDataDTO.getRoleMap().remove("flag" + entity.getTaskType());
                    }
                }
            }
        }
    }

    private void handleNeedRoleToHandle(ProjectCostPointDetailDataDTO detailDataDTO, String companyUserId) throws Exception{
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("targetId", detailDataDTO.getId());
        List<MyTaskEntity> myTaskList = this.myTaskService.getMyTaskByParam(map);
        CompanyUserEntity companyUserEntity = this.companyUserDao.selectById(companyUserId);
        if(companyUserEntity==null){
            return;
        }
        String companyId = companyUserEntity.getCompanyId();
        if(!CollectionUtils.isEmpty(myTaskList)){//理论上只会存在一条有效数据
            MyTaskEntity entity = myTaskList.get(0);
            if(companyId.equals(entity.getCompanyId())) {
                if (entity.getTaskType() == 5 || entity.getTaskType() == 7) {
                    map.clear();
                    map.put("permissionId", "50");
                    map.put("companyId", companyId);
                    map.put("userId", companyUserEntity.getUserId());
                    List<CompanyUserDataDTO> companyUserList = this.companyUserDao.getCompanyUserByPermissionId(map);
                    if (!CollectionUtils.isEmpty(companyUserList)){
                        detailDataDTO.getRoleMap().put("flag" + entity.getTaskType(), entity.getId());
                    }else {
                        detailDataDTO.getRoleMap().remove("flag" + entity.getTaskType());
                    }
                }

                if(entity.getTaskType()==8 || entity.getTaskType()==9 || entity.getTaskType()==10 || (entity.getTaskType()>15 &&  entity.getTaskType()<22)){
                    map.clear();
                    map.put("permissionId", "49");
                    map.put("companyId", companyId);
                    map.put("userId", companyUserEntity.getUserId());
                    List<CompanyUserDataDTO> companyUserList = this.companyUserDao.getCompanyUserByPermissionId(map);
                    if (!CollectionUtils.isEmpty(companyUserList)) {
                        detailDataDTO.getRoleMap().put("flag" + entity.getTaskType(), entity.getId());
                    } else {
                        detailDataDTO.getRoleMap().remove("flag" + entity.getTaskType());
                    }
                }
            }
        }
    }

    private Map<String,Object> handleOperateRole(String costDetailId,String type,String companyUserId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("handlerId",companyUserId);
        map.put("targetId",costDetailId);
        List<MyTaskEntity> myTaskList = this.myTaskService.getMyTaskByParam(map);
        Map<String,Object> roleMap = new HashMap<String,Object>();
        for(MyTaskEntity entity:myTaskList){
            roleMap.put("flag"+entity.getTaskType(),entity.getId());
        }
        return roleMap;
    }


    /**
     * 方法描述：合作设计费（技术审查费）详情
     * 作者：MaoSF
     * 日期：2017/3/9
     *
     * @param map
     * @param:map(id,appOrgId,accountId)
     * @return:
     */
    @Override
    public ResponseBean getProjectCostPointDetail(Map<String, Object> map) throws Exception {
        //查询当前人在团队的id
        CompanyUserEntity userEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId((String) map.get("accountId"),(String)map.get("appOrgId"));
        if(userEntity==null){
            ResponseBean.responseError("查询失败");
        }
        map.put("companyUserId",userEntity.getId());
        ProjectCostPointEntity pointEntity = this.projectCostPointDao.selectById(map.get("id"));
        ProjectCostPointDataDTO dataDTO = new ProjectCostPointDataDTO();
        BaseDTO.copyFields(pointEntity,dataDTO);
        dataDTO.setUnpaid(new BigDecimal(0));//首先默认为0
        BigDecimal paidFee = new BigDecimal("0");
        //查询子节点
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("type",dataDTO.getType());
        param.put("pid",dataDTO.getId());
        List<ProjectCostPointDTO> childList = this.projectCostPointDao.selectByParam(param);

        ProjectCostTotalDTO totalDTO = new ProjectCostTotalDTO();
        if(!CollectionUtils.isEmpty(childList)){
            for(ProjectCostPointDTO dto1:childList){
                ProjectCostPointDataDTO dataDTO1 = new ProjectCostPointDataDTO();
                BaseDTO.copyFields(dto1,dataDTO1);
                getPointDetailData(dataDTO1, map,true,totalDTO);

                if(dataDTO1.getPaidFee()!=null){
                    paidFee = paidFee.add(dataDTO1.getPaidFee());
                }
            }
        }

        if(dataDTO.getFee()!=null){
            dataDTO.setUnpaid(dataDTO.getFee().subtract(paidFee));
        }

        //查询总金额
        if(StringUtil.isNullOrEmpty(pointEntity.getPid())){
            if(CompanyBillType.FEE_TYPE_TECHNICAL.toString().equals(pointEntity.getType())){
                param.clear();
                param.put("type",dataDTO.getType());
                param.put("projectId",pointEntity.getProjectId());
                List<ProjectCostDTO> list = this.projectCostDao.selectByParam(param);
                if(!CollectionUtils.isEmpty(list)){
                    dataDTO.setTotalFee(list.get(0).getFee());
                }
            }else {
                ProjectCostEntity costEntity = this.projectCostDao.selectById(pointEntity.getCostId());
                if(costEntity!=null){
                    dataDTO.setTotalFee(costEntity.getFee());
                }
            }
        }else {
            ProjectCostPointEntity point = this.projectCostPointDao.selectById(pointEntity.getPid());
            if(point!=null){
                dataDTO.setTotalFee(point.getFee());
            }
        }
        return ResponseBean.responseSuccess("查询成功").addData("result",dataDTO);
    }

    /**
     * 方法描述：查询合同回款(map:projectId)map.put("type"="4"：付款，5：收款);
     * 作者：chenzhujie
     * 日期：2017/3/1
     */
    @Override
    public ResponseBean getOtherFee(Map<String,Object> map)throws Exception{
        String type = map.get("type").toString();
        String companyId = map.get("appOrgId").toString();

        //查询当前人在团队的id
        CompanyUserEntity userEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId((String) map.get("accountId"),(String)map.get("appOrgId"));
        if(userEntity==null){
            ResponseBean.responseError("查询失败");
        }

        if(CompanyBillType.FEE_TYPE_OTHER.toString().equals(type)){
            //查询是否存在
            map.put("type",CompanyBillType.FEE_TYPE_OTHER.toString());
            map.put("fromCompanyId",companyId);
            map.put("costTypeValue",CompanyBillType.DIRECTION_PAYER);

        }else {
            map.put("type",CompanyBillType.FEE_TYPE_OTHER_PAID.toString());
            map.put("toCompanyId",companyId);
            map.put("costTypeValue",CompanyBillType.DIRECTION_PAYEE);
        }

        Map<String,Object> result = this.getReviewFeeInfo(map,type);

        //是否是经营人员
        ProjectMemberEntity projectMember =  this.projectMemberService.getOperatorManager((String) map.get("projectId"), (String) map.get("appOrgId"));
        String isManager = "0";
        if (projectMember!=null && projectMember.getCompanyUserId().equals(userEntity.getId())) {
            isManager = "1";
        }

        //财务人员
        String isFinancal = "0";
        result.put("companyId",map.get("appOrgId"));
        result.put("permissionId", "49");
        List<CompanyUserDataDTO> companyUserList = this.companyUserDao.getCompanyUserByPermissionId(map);
        if (!CollectionUtils.isEmpty(companyUserList)) {
            for (CompanyUserDataDTO userTableDTO : companyUserList) {
                if (userTableDTO.getId().equals(userEntity.getId())) {
                    isFinancal = "1";
                }
            }
        }
        result.put("isManager",isManager);
        result.put("isFinancal",isFinancal);
        return ResponseBean.responseSuccess("查询成功").setData(result);
    }

    /**
     * 方法描述：验证合作设计费
     * 作者：MaoSF
     * 日期：2017/3/12
     */
    public ResponseBean validateTechnicalFee(ProjectCostPointDTO projectCostPointDTO) throws Exception {
        if(StringUtil.isNullOrEmpty(projectCostPointDTO.getFee())){
            return ResponseBean.responseSuccess();
        }
        double total = 0;
        ProjectCostPointEntity pointEntity= null;
        String pointPid = projectCostPointDTO.getPid();
        double fee = 0;
        //如果是修改，则先查询出原来的数据
        if (!StringUtil.isNullOrEmpty(projectCostPointDTO.getId())) {
            pointEntity = this.projectCostPointDao.selectById(projectCostPointDTO.getId());
            pointPid = pointEntity.getPid();
            fee =  pointEntity.getFee().doubleValue();
        }

        //查询子节点的总金额
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("projectId",projectCostPointDTO.getProjectId());
        map.put("type",projectCostPointDTO.getType());
        if(CompanyBillType.FEE_TYPE_COOPERATIVE.toString().equals(projectCostPointDTO.getType()))
        {
            map.put("costId",projectCostPointDTO.getCostId());
        }

        if(!StringUtil.isNullOrEmpty(projectCostPointDTO.getPid())){
            ProjectCostPointEntity projectCostPoint = this.projectCostPointDao.selectById(projectCostPointDTO.getPid());
            if(projectCostPoint.getFee()!=null){
                total = projectCostPoint.getFee().doubleValue();
            }
            map.put("pid",pointPid);
        }else {
            //如果是技术审查费，其他费用，第一级是不需要验证总金额 type=1 or 3暂时不做限制
            if("1".equals(projectCostPointDTO.getType()) || "2".equals(projectCostPointDTO.getType()) || "3".equals(projectCostPointDTO.getType()) ||  "4".equals(projectCostPointDTO.getType()) || "5".equals(projectCostPointDTO.getType()) ){
                //判断不能小于设置的子节点的总金额
                if(pointEntity!=null){
                    double pointTotalFee = this.projectCostPointDetailDao.getSumFee(pointEntity.getId());
                    if(projectCostPointDTO.getFee().doubleValue()<pointTotalFee){
                        return ResponseBean.responseError("金额不能小于" + pointTotalFee);
                    }
                }

                return ResponseBean.responseSuccess();
            }
            //查询总金额
            if(CompanyBillType.FEE_TYPE_COOPERATIVE.toString().equals(projectCostPointDTO.getType())){
                ProjectCostEntity costEntity = this.projectCostDao.selectById(projectCostPointDTO.getCostId());
                if(costEntity.getFee()!=null){
                    total = costEntity.getFee().doubleValue();
                }
                map.put("costId",costEntity.getId());
                map.put("pidIsNull","1");
            }else {//如果是合同回款
                List<ProjectCostDTO> costDTOs = this.projectCostDao.selectByParam(map);
                if(!CollectionUtils.isEmpty(costDTOs)){
                    if(null!=costDTOs.get(0).getFee()){
                        total = costDTOs.get(0).getFee().doubleValue();
                    }
                }
            }

        }

        if(total==0){
            return ResponseBean.responseError("请先设置总金额");
        }

        double totalFee = this.projectCostPointDao.getTotalFee(map);

        if(!StringUtil.isNullOrEmpty(projectCostPointDTO.getFee())){
            if(StringUtil.isNullOrEmpty(projectCostPointDTO.getId())){
 				Double d = projectCostPointDTO.getFee().doubleValue() + totalFee;
                if (CommonUtil.doubleCompare(d,total) > 0) {
                    return ResponseBean.responseError("回款总金额大于"+total);
                }
            }else {
                if(pointEntity.getFee()!=null){
                    fee = pointEntity.getFee().doubleValue();
                }
                Double d = (totalFee + projectCostPointDTO.getFee().doubleValue() - fee);
                if (CommonUtil.doubleCompare(d,total) > 0) {
                    return ResponseBean.responseError("回款总金额大于"+total);

                }
            }
        }
        return ResponseBean.responseSuccess();
    }

    /**
     * 方法描述：验证收款(如果是新增的话，originalFee默认为0)
     * 作者：MaoSF
     * 日期：2017/4/26
     * @param:
     * @return:
     */
    public ResponseBean validateSaveCostPaymentDetail(ProjectCostPaymentDetailDTO dto,BigDecimal pointFee,BigDecimal originalFee,String feeType) throws Exception{

        if(pointFee==null){
            return ResponseBean.responseError("操作失败");
        }

        if(dto.getFee()!=null){//当财务到款，付款是不需要传递fee的
            double sumFee = this.projectCostPaymentDetailDao.getSumFee(dto.getPointDetailId());
            if (CommonUtil.doubleCompare((sumFee + dto.getFee().doubleValue()-originalFee.doubleValue()) ,pointFee.doubleValue()) > 0) {

                String errorMsg = "";
                if("1".equals(feeType) || "5".equals(feeType)){
                    errorMsg = "收款总金额不能大于";
                }
                if("2".equals(feeType) || "3".equals(feeType) || "4".equals(feeType)){
                    errorMsg = "付款总金额不能大于";
                }

                return ResponseBean.responseError(errorMsg+StringUtil.getRealData(pointFee.subtract(new BigDecimal(sumFee+""))));
            }
        }
        return null;
    }

    @Override
    public ResponseBean saveCostPaymentDetail(ProjectCostPaymentDetailDTO dto) throws Exception {

        Integer operateFlag = dto.getOperateFlag();
        boolean isInsert = false;
        boolean isSaveAdverseFinancial = false;
        BigDecimal originalFee = new BigDecimal("0");
        if(!StringUtil.isNullOrEmpty(dto.getId())) {//存在ID修改
            ProjectCostPaymentDetailEntity paymentDetail = this.projectCostPaymentDetailDao.selectById(dto.getId());
            if(paymentDetail!=null){
                if("1".equals(paymentDetail.getStatus())){
                    return ResponseBean.responseError("该记录已被删除");
                }
                originalFee = paymentDetail.getFee();
                //防止在任务调用方，没有传递pointDetailId
                dto.setPointDetailId(paymentDetail.getPointDetailId());
            }
        }
        ProjectCostPointDetailEntity pointDetail = this.projectCostPointDetailDao.selectById(dto.getPointDetailId());
        if(pointDetail==null){
            return ResponseBean.responseError("操作失败");
        }
        ProjectCostPointEntity costPoint = this.projectCostPointDao.selectById(pointDetail.getPointId());
        if(pointDetail==null){
            return ResponseBean.responseError("操作失败");
        }
        ProjectCostEntity cost = projectCostDao.selectById(costPoint.getCostId());
        if(cost==null){
            return ResponseBean.responseError("操作失败");
        }
        //验证
        ResponseBean ajax = this.validateSaveCostPaymentDetail(dto,pointDetail.getFee(),originalFee,costPoint.getType());
        if(ajax != null){
            return ajax;
        }
        int res = 0;
        ProjectCostPaymentDetailEntity entity = new ProjectCostPaymentDetailEntity();
        BaseDTO.copyFields(dto, entity);
        if(!StringUtil.isNullOrEmpty(dto.getId())){//存在ID修改
            ProjectCostPaymentDetailEntity origin = projectCostPaymentDetailDao.selectById(entity.getId()); //保存原有数据
            entity.setUpdateBy(dto.getAccountId());
            //保存项目动态
            dynamicService.addDynamic(origin,entity,dto.getCurrentCompanyId(),dto.getAccountId());
            Boolean isReceive = operateFlag==1?true:false;
            //保存操作
            String type = null;
            String payDate = null;
            if(!StringUtil.isNullOrEmpty(dto.getPaidDate())){
                type = "5";
                payDate = dto.getPaidDate();
            }
            if(!StringUtil.isNullOrEmpty(dto.getPayDate())){
                type = "6";
                payDate = dto.getPayDate();
            }
            isSaveAdverseFinancial = handleAdverseFinancialAccount(entity.getId());
            if(isSaveAdverseFinancial || !isReceive){
                //判断余额付款方的余额
                validateBalance(cost.getFromCompanyId(),origin.getFee(),payDate);
            }
            isInsert = this.saveProjectCostOperater(entity.getId(),operateFlag,dto.getCurrentCompanyUserId(),dto.getFee(),dto.getAccountId());
            if(isInsert){
                this.financialAccount(costPoint,dto,isReceive,entity.getId(),dto.getFee());
            }
            if(isSaveAdverseFinancial){
                isReceive = !isReceive;
                isInsert = this.saveProjectCostOperater(entity.getId(),operateFlag,dto.getCurrentCompanyUserId(),dto.getFee(),dto.getAccountId());
                if(isInsert){
                    this.financialAccount(costPoint,dto,isReceive,entity.getId(),dto.getFee());
                }
            }
            if(isSaveAdverseFinancial){ //当前财务如果是 技术审查费/合作设计费 双方的共同财务，则同时把付款信息默认记录到系统
                if(!StringUtil.isNullOrEmpty(entity.getPayDate()) && StringUtil.isNullOrEmpty(entity.getPaidDate())){
                    entity.setPaidDate(entity.getPayDate());
                }else if(!StringUtil.isNullOrEmpty(entity.getPaidDate()) && StringUtil.isNullOrEmpty(entity.getPayDate())){
                    entity.setPayDate(entity.getPaidDate());
                }
            }
            res = projectCostPaymentDetailDao.updateById(entity);
        }else{//添加
            //如果是合同回款，其他费用收款
            boolean isReceive = operateFlag==1?true:false;
            boolean isInnerCompany = this.isInnerCompany(cost);
            String paidDate = dto.getDateStr()==null?DateUtils.date2Str(DateUtils.date_sdf): dto.getDateStr();
            if(isInnerCompany){
                entity.setPaidDate(paidDate);
                entity.setPayDate(paidDate);
            }else {
                if(isReceive){
                    entity.setPaidDate(paidDate);
                }else {
                    entity.setPayDate(paidDate);
                    //判断余额付款方的余额
                    validateBalance(cost.getFromCompanyId(),dto.getFee(),dto.getPayDate());
                }
            }
            entity.setId(StringUtil.buildUUID());
            entity.setCreateBy(dto.getAccountId());
            entity.setProjectId(costPoint.getProjectId());
            res = projectCostPaymentDetailDao.insert(entity);
            //保存项目日志
            dynamicService.addDynamic(null,entity,dto.getCurrentCompanyId(),dto.getAccountId());
            //保存操作
            //保存操作
            isInsert = this.saveProjectCostOperater(entity.getId(),operateFlag,dto.getCurrentCompanyUserId(),dto.getFee(),dto.getAccountId());
            if(operateFlag<3){
                if(isInsert){
                    this.financialAccount(costPoint,dto,isReceive,entity.getId(),dto.getFee());
                }
            }
            //如果是内部组织，把对方的财务也保存一份数据
            if(operateFlag<3 && isInnerCompany){
                operateFlag = operateFlag==1?2:1;//此处取反
                isInsert = this.saveProjectCostOperater(entity.getId(),operateFlag,dto.getCurrentCompanyUserId(),dto.getFee(),dto.getAccountId());
                if(isInsert){
                    this.financialAccount(costPoint,dto,!isReceive,entity.getId(),dto.getFee());
                }
            }
        }
        //财务到款，付款给企业负责人和经营负责人推送消息
        this.sendMessage(cost,pointDetail,entity,dto,isSaveAdverseFinancial);
        if(res>0){
            Map<String,Object> map = new HashMap<>();
            map.put("costId",costPoint.getCostId());
            map.put("paymentDetailId",entity.getId());
            map.put("pointDetailId",pointDetail.getId());
            map.put("pointId",costPoint.getId());
            return ResponseBean.responseSuccess("操作成功").setData(map);
        }else{
            return ResponseBean.responseError("操作失败");
        }
    }

    private void validateBalance(String companyId,BigDecimal fee,String payDate) throws Exception{
        fee = fee.multiply(new BigDecimal("10000"));
        if(!companyBalanceService.isCanBeAllocate(companyId,fee.toString(),payDate)){
            //抛异常
            throw new CustomException("当前支出的金额不能大于账目余额与最低余额的差值");
        }
    }

    private boolean saveProjectCostOperater(String paymentDetailId,Integer operateFlag,String companyUserId,BigDecimal fee,String accountId){
        ProjectCostOperaterEntity costOperaterEntity = new ProjectCostOperaterEntity();
        costOperaterEntity.setId(StringUtil.buildUUID());
        costOperaterEntity.setCompanyUserId(companyUserId);
        costOperaterEntity.setCostDetailId(paymentDetailId);//记录到款的数据的id
        costOperaterEntity.setCreateBy(accountId);
        if(operateFlag==3){//为了兼容经营负责人，所有使用Boolean
            costOperaterEntity.setType("3");//经营负责人付款确认
        }else {
            if(operateFlag==1){
                costOperaterEntity.setType("5");//到款类型
            }else {
                costOperaterEntity.setType("6");//财务付款
            }
        }
        //理论上，这种操作只会是一次，为了防止数据库中的数据错误，检查再处理
        List<ProjectCostOperaterEntity> list = this.projectCostOperaterDao.selectByType(costOperaterEntity);
        if(CollectionUtils.isEmpty(list)){
            this.projectCostOperaterDao.insert(costOperaterEntity);
            return true;
        }else {
            costOperaterEntity = list.get(0);
            costOperaterEntity.setCompanyUserId(companyUserId);
            this.projectCostOperaterDao.updateById(costOperaterEntity);
            return false;
        }
    }

    private void financialAccount(ProjectCostPointEntity costPoint,ProjectCostPaymentDetailDTO dto,boolean isReceive,String projectPaymentDetailId,BigDecimal fee) throws Exception{
        SaveCompanyBillDTO billDTO = new SaveCompanyBillDTO();
        String paymentDate = StringUtil.isNullOrEmpty(dto.getPaidDate())?dto.getPayDate():dto.getPaidDate();
        if(dto.getDateStr()!=null){
            paymentDate = dto.getDateStr();
        }
        ProjectCostEntity cost = projectCostDao.selectById(costPoint.getCostId());
        billDTO.setFromCompanyId(cost.getFromCompanyId());
        billDTO.setToCompanyId(cost.getToCompanyId());
        billDTO.setFee(fee);
        ProjectCostPaymentDetailEntity payment = null;
        if(fee==null){
            payment = this.projectCostPaymentDetailDao.selectById(projectPaymentDetailId);
            billDTO.setFee(payment.getFee());
        }
        if(isReceive){
            billDTO.setPayType(CompanyBillType.DIRECTION_PAYEE);
            billDTO.setCompanyId(cost.getToCompanyId());
        }else {
            billDTO.setPayType(CompanyBillType.DIRECTION_PAYER);
            billDTO.setCompanyId(cost.getFromCompanyId());
        }
        billDTO.setFeeType(Integer.valueOf(cost.getType()));
        if("5".equals(cost.getType())){ //如果类型是5，则属于其他费用
            billDTO.setFeeType(CompanyBillType.FEE_TYPE_OTHER);
        }
        billDTO.setBillDescription(costPoint.getFeeDescription());
        billDTO.setProjectId(cost.getProjectId());
        billDTO.setOperatorId(dto.getCurrentCompanyUserId());
        billDTO.setPaymentDate(paymentDate);
        billDTO.setTargetId(projectPaymentDetailId);
        companyBillService.saveCompanyBill(billDTO);
    }
    /**
     * 方法描述：修改付款或到款明细
     * 作者：wrb
     * 日期：2017/4/26
     *
     * @param dto
     */
    @Override
    public ResponseBean updateCostPaymentDetail(ProjectCostPaymentDetailDTO dto) throws Exception {
        BigDecimal originalFee = new BigDecimal("0");
        ProjectCostPaymentDetailEntity origin = null;
        if(!StringUtil.isNullOrEmpty(dto.getId())) {//存在ID修改
            ProjectCostPaymentDetailEntity paymentDetail = this.projectCostPaymentDetailDao.selectById(dto.getId());
            if(paymentDetail!=null){
                //保存原有数据
                origin = new ProjectCostPaymentDetailEntity();
                BeanUtilsEx.copyProperties(paymentDetail,origin);

                originalFee = paymentDetail.getFee();
                //防止在任务调用方，没有传递pointDetailId
                dto.setPointDetailId(paymentDetail.getPointDetailId());
            }
        }
        ProjectCostPointDetailEntity pointDetail = this.projectCostPointDetailDao.selectById(dto.getPointDetailId());
        if(pointDetail==null){
            return ResponseBean.responseError("操作失败");
        }
        ProjectCostPointEntity costPoint = this.projectCostPointDao.selectById(pointDetail.getPointId());
        if(pointDetail==null){
            return ResponseBean.responseError("操作失败");
        }
        //验证
        ResponseBean responseBean = this.validateSaveCostPaymentDetail(dto,pointDetail.getFee(),originalFee,costPoint.getType());
        if(responseBean != null){
            return responseBean;
        }
        ProjectCostPaymentDetailEntity entity = new ProjectCostPaymentDetailEntity();
        BaseDTO.copyFields(dto, entity);
        entity.setUpdateBy(dto.getAccountId());
        projectCostPaymentDetailDao.updateById(entity);

        //保存项目日志
        dynamicService.addDynamic(origin,entity,dto.getAppOrgId(),dto.getAccountId());
        //处理任务
        handleSendMyTaskForChangeProjectCostPayment(entity,dto.getAccountId(),dto.getAppOrgId());

        return ResponseBean.responseSuccess("操作成功");
    }

    /**
     * 财务处理发票信息（发票确认）
     */
    @Override
    public ResponseBean saveCostPointDetailForInvoice(InvoiceEditDTO dto) throws Exception {
        ProjectCostPointDetailEntity pointDetail = this.projectCostPointDetailDao.selectById(dto.getPointDetailId());
        ProjectCostEntity costEntity = this.projectCostDao.getProjectCostByPointId(pointDetail.getPointId());
            if(pointDetail==null || costEntity==null){
            return ResponseBean.responseError("参数错误");
        }
        //处理PaidFeeStatus = 1
        pointDetail.setPaidFeeStatus(ProjectCostConst.FEE_STATUS_APPROVE);
        this.projectCostPointDetailDao.updateById(pointDetail);
        //处理发票信息
        String invoice = pointDetail.getInvoice();
        dto.setId(invoice);
        invoiceService.saveInvoice(dto);
        //处理任务
        ProjectCostPointDetailDTO pointDetailDTO = new ProjectCostPointDetailDTO();
        BeanUtils.copyProperties(pointDetail,pointDetailDTO);//把基本参数复制进去即可
        pointDetailDTO.setPointId(pointDetail.getPointId());
        pointDetailDTO.setCurrentCompanyId(dto.getCurrentCompanyId());
        pointDetailDTO.setAccountId(dto.getAccountId());
        if(!this.isInnerCompany(costEntity)){//如果不是内部组织，推送任务
            this.sendMyTask(dto.getPointDetailId(),pointDetailDTO);
        }
        return ResponseBean.responseSuccess("操作成功");
    }

    /**
     * 方法描述：处理操作人
     * 作者：MaoSF
     * 日期：2017/3/6
     */
    private String getOperatorForCostDetail2(String type,String costDetailId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("costDetailId",costDetailId);
        List<ProjectCostOperaterDTO> operaterDTOS = this.projectCostOperaterDao.getCostOperator(map);
        if("1".equals(type)  || "4".equals(type) || "5".equals(type)){//合同回款
            for(ProjectCostOperaterDTO dto:operaterDTOS){
                if("2".equals(dto.getType())){
                    return dto.getUserName();

                }
            }
        }

        if("2".equals(type) || "3".equals(type)){//技术审查费
            for(ProjectCostOperaterDTO dto:operaterDTOS){
                if("1".equals(dto.getType())){
                    return dto.getUserName();
                }
            }
        }
        return null;
    }


    private String getOperatorForCostDetail3(String paymentId,int taskType,boolean includeManager) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("costDetailId",paymentId);
        if(taskType==4 || taskType==6){
            map.put("types","3,6");//经营负责人付款确认
        }
        if(taskType==10 || taskType==21){
            map.put("types","5");//财务到款查询
        }
        if(taskType==16 || taskType==18 ){
            if(includeManager){
                map.put("types","3,6");//经营负责人付款确认，付款查询
            }else {
                map.put("types","6");//经营负责人付款确认，付款查询
            }
        }
        if(taskType==17 || taskType==19 ){
            if(includeManager) {
                map.put("types", "3,5");//经营负责人付款确认，财务到款查询
            }else {
                map.put("types","5");//经营负责人付款确认，付款查询
            }
        }
        List<ProjectCostOperaterDTO> operaterDTOS = this.projectCostOperaterDao.getCostOperator(map);
        String userName = "";
        for(ProjectCostOperaterDTO dto:operaterDTOS){
            if(StringUtil.isNullOrEmpty(userName)){
                userName = dto.getUserName();
            }else {
                userName +="  " + dto.getUserName();
            }
        }
        return userName;
    }

    /**
     * 方法描述：合作设计费（技术审查费）详情
     * 作者：MaoSF
     * 日期：2017/3/9
     *
     * @param:map(pointDetailId,taskType)
     * @return:
     */
    @Override
    public ResponseBean getProjectCostPointDetailForMyTask(String paymentDetailId,String pointDetailId, MyTaskEntity task) throws Exception {
        ProjectCostPointDataForMyTaskDTO dataDTO = this.getProjectCostPointForMyTask(paymentDetailId,pointDetailId,task);
        ProjectEntity project = projectDao.selectById(dataDTO.getProjectId());
        dataDTO.setProjectName(project!=null?project.getProjectName():"");
        return ResponseBean.responseSuccess("查询成功").addData("result",dataDTO);
    }

    @Override
    public ProjectCostPointDataForMyTaskDTO getProjectCostPointForMyTask(String paymentDetailId, String pointDetailId, MyTaskEntity task) throws Exception {
        int taskType = task.getTaskType();
        String companyId = task.getCompanyId();
        if("1".equals(task.getParam3()) || "2".equals(task.getParam3())){
            companyId = projectCostPointDao.getCostFeeCompanyByTaskId(task.getId());
        }
        ProjectCostPointDataForMyTaskDTO dataDTO = this.projectCostPointDao.getCostPointData(pointDetailId,paymentDetailId,companyId);
        BigDecimal paidFee = new BigDecimal("0");
        BigDecimal totalPaymentFee = new BigDecimal("0");
        dataDTO.setUserName(getOperatorForCostDetail2(dataDTO.getType(),dataDTO.getPointDetailId()));
        //查询明细
        Map<String, Object> map = new HashMap<>();
        map.put("pointDetailId",dataDTO.getPointDetailId());
        List<ProjectCostPaymentDetailDTO> detailDTOS = this.projectCostPaymentDetailDao.selectByPointDetailId(map);
        List<PaymentDataDTO> paymentList = new ArrayList<>();
        for(ProjectCostPaymentDetailDTO detail:detailDTOS){
            PaymentDataDTO payment = new PaymentDataDTO();
            BaseDTO.copyFields(detail,payment);
            //获取处理人
            payment.setHandlerName(this.getOperatorForCostDetail3(detail.getId(),taskType,true));
            if(taskType==10 || taskType==17 || taskType==19 || taskType==21){
                payment.setPayDate(detail.getPaidDate());
                if(!StringUtil.isNullOrEmpty(detail.getPaidDate())){
                    paidFee = paidFee.add(detail.getFee());
                }
            }else {
                payment.setPayDate(detail.getPayDate());
                if(!StringUtil.isNullOrEmpty(detail.getPayDate())){
                    paidFee = paidFee.add(detail.getFee());
                }
            }
            if(paymentDetailId!=null){
                if(paymentDetailId.equals(detail.getId())){
                    payment.setHighLightFlag(1);
                    dataDTO.setHandlerName(this.getOperatorForCostDetail3(detail.getId(),taskType,false));
                }
            }
            totalPaymentFee = totalPaymentFee.add(detail.getFee());
            paymentList.add(payment);
        }
        dataDTO.setPaidFee(paidFee);//已付,已收总金额
        dataDTO.setPaymentList(paymentList);
        if(dataDTO.getPointDetailFee()!=null){
            dataDTO.setUnpaid(dataDTO.getPointDetailFee().subtract(paidFee));
        }
        if(totalPaymentFee !=null){//经营负责人确认付款的总金额
            dataDTO.setTotalPaymentFee(totalPaymentFee);
        }
        return dataDTO;
    }

    @Override
    public ProjectFeeSummaryDTO getProjectFeeSummary(ProjectCostQueryDTO query) throws Exception {
        ProjectFeeSummaryDTO result = new ProjectFeeSummaryDTO();
        if(StringUtil.isNullOrEmpty(query.getCompanyId())){
            query.setCompanyId(query.getAppOrgId());
        }
        CompanyUserEntity user = companyUserDao.getCompanyUserByUserIdAndCompanyId(query.getAccountId(),query.getAppOrgId());
        if(user==null){
            return result;
        }
        List<ProjectCostSummaryDTO> list = this.projectCostDao.getProjectCostSummary(query);
        for(ProjectCostSummaryDTO dto:list){
            BigDecimal bakFee = new BigDecimal("0"); //发起金额
            BigDecimal payFee = new BigDecimal("0"); //已付金额
            BigDecimal paidFee = new BigDecimal("0");//已收金额
            BigDecimal otherIncomeFee = new BigDecimal("0");//其他费用-收入总金额
            BigDecimal otherOutcomeFee = new BigDecimal("0");//其他费用-支出总金额
            BigDecimal notPaidFee = new BigDecimal("0"); //应收未收
            BigDecimal notPayFee = new BigDecimal("0"); //应付未付
            if(dto.getFee()==null){
                dto.setFee(new BigDecimal("0"));
            }

            for(ProjectCostPointDTO point:dto.getPointList()){
                if(dto.getType()==SystemParameters.FEE_TYPE_OTHER_OUTCOME){
                    otherOutcomeFee = otherOutcomeFee.add(point.getFee());
                }
                if(dto.getType()==SystemParameters.FEE_TYPE_OTHER_INCOME){
                    otherIncomeFee = otherIncomeFee.add(point.getFee());
                }
                for(ProjectCostPointDetailDTO detail:point.getProjectCostDetailList()){
                    if(detail.getFee()!=null){
                        bakFee = bakFee.add(detail.getFee());
                    }
                    for(ProjectCostPaymentDetailDTO payment:detail.getPaymentDetailList()){
                        if(!StringUtil.isNullOrEmpty(payment.getPaidDate())){
                            paidFee = paidFee.add(payment.getFee());
                        }
                        if(!StringUtil.isNullOrEmpty(payment.getPayDate())){
                            payFee = payFee.add(payment.getFee());
                        }
                    }

                }
            }


            notPaidFee = bakFee.subtract(paidFee);
            notPayFee = bakFee.subtract(payFee);

            //合同回款
            if(dto.getType()==SystemParameters.FEE_TYPE_CONTRACT && query.getCompanyId().equals(dto.getToCompanyId())){
                result.setContractSumFee(result.getContractSumFee().add(dto.getFee()));
                result.setContractPaidFee(result.getContractPaidFee().add(paidFee));
                result.setContractNotPaidFee(result.getContractNotPaidFee().add(notPaidFee));
            }
            //技术审查费收款
            if(dto.getType()==SystemParameters.FEE_TYPE_TECH  && query.getCompanyId().equals(dto.getToCompanyId())){
                result.setTechIncomeFee(result.getTechIncomeFee().add(dto.getFee()));
                result.setTechPaidFee(result.getTechPaidFee().add(paidFee));
                result.setTechNotPaidFee(result.getTechNotPaidFee().add(notPaidFee));
            }
            //技术审查费付款
            if(dto.getType()==SystemParameters.FEE_TYPE_TECH  && query.getCompanyId().equals(dto.getFromCompanyId())){
                result.setTechOutcomeFee(result.getTechOutcomeFee().add(dto.getFee()));
                result.setTechPayFee(result.getTechPayFee().add(payFee));
                result.setTechNotPayFee(result.getTechNotPayFee().add(notPayFee));
            }

            //合作设计费收款
            if(dto.getType()==SystemParameters.FEE_TYPE_COOPERATE && query.getCompanyId().equals(dto.getToCompanyId())){
                result.setCooperateIncomeFee(result.getCooperateIncomeFee().add(dto.getFee()));
                result.setCooperatePaidFee(result.getCooperatePaidFee().add(paidFee));
                result.setCooperateNotPaidFee(result.getCooperateNotPaidFee().add(notPaidFee));
            }
            //合作设计费付款
            if(dto.getType()==SystemParameters.FEE_TYPE_COOPERATE && query.getCompanyId().equals(dto.getFromCompanyId())){
                result.setCooperateOutcomeFee(result.getCooperateOutcomeFee().add(dto.getFee()));
                result.setCooperatePayFee(result.getCooperatePayFee().add(payFee));
                result.setCooperateNotPayFee(result.getCooperateNotPayFee().add(notPayFee));
            }


            //其他费用收款
            if(dto.getType()==SystemParameters.FEE_TYPE_OTHER_INCOME && query.getCompanyId().equals(dto.getToCompanyId())){
                result.setOtherIncomeFee(result.getOtherIncomeFee().add(otherIncomeFee));
                result.setOtherPaidFee(result.getOtherPaidFee().add(paidFee));
                result.setOtherNotPaidFee(result.getOtherNotPaidFee().add(notPaidFee));
            }
            //其他费用付款
            if(dto.getType()==SystemParameters.FEE_TYPE_OTHER_OUTCOME && query.getCompanyId().equals(dto.getFromCompanyId())){
                result.setOtherOutcomeFee(result.getOtherOutcomeFee().add(otherOutcomeFee));
                result.setOtherPayFee(result.getOtherPayFee().add(payFee));
                result.setOtherNotPayFee(result.getOtherNotPayFee().add(notPayFee));
            }
        }

        //权限设置
        V2ProjectTableDTO projectDTO = new V2ProjectTableDTO();
        ProjectEntity project = projectDao.selectById(query.getProjectId());
        if(project!=null){//此处不能用copy,因为project中定义的字段和projectDTO字段名一致，却类型不一样
            projectDTO.setId(project.getId());
            projectDTO.setProjectName(project.getProjectName());
            projectDTO.setCreateBy(project.getCreateBy());
            projectDTO.setCompanyBid(project.getCompanyBid());
            projectDTO.setCompanyId(project.getCompanyId());
            result.setRoleMap(projectService.projectNavigationRole(projectDTO,query.getCurrentCompanyId(),query.getAccountId(),user.getId()));
        }
        return result;
    }

    @Override
    public List<ProjectCooperatorCostDTO> getProjectCooperatorFee(ProjectCostQueryDTO queryDTO) throws Exception {
        if(StringUtil.isNullOrEmpty(queryDTO.getCompanyId())){
            queryDTO.setCompanyId(queryDTO.getAppOrgId());
        }
        return projectCostDao.getProjectCooperatorFee(queryDTO.getProjectId(),queryDTO.getCompanyId());
    }

    @Override
    public Map<String, Object> listProjectCost(ProjectCostQueryDTO queryDTO) throws Exception {
        if(StringUtil.isNullOrEmpty(queryDTO.getCompanyId())){
            queryDTO.setCompanyId(queryDTO.getAppOrgId());
        }
        List<ProjectCostDataDTO> costList = new ArrayList<>();
        List<ProjectCostDTO> list = this.getProjectCostList(queryDTO);
        for(ProjectCostDTO cost:list){
            costList.add( getProjectCost(queryDTO,cost,true));
        }
        Map<String,Object> result = new HashMap<>();
        result.put("listProjectCost",costList);
        result.put("isManager",this.getManagerFlag(queryDTO.getProjectId(),queryDTO.getCompanyId(),queryDTO.getCurrentCompanyUserId()));
        return result;
    }

    @Override
    public Map<String, Object> getProjectCostPointByCostId(ProjectCostQueryDTO queryDTO) throws Exception {
        if(StringUtil.isNullOrEmpty(queryDTO.getCompanyId())){
            queryDTO.setCompanyId(queryDTO.getAppOrgId());
        }
        ProjectCostDataDTO costData = getProjectCost(queryDTO,null,true);
        //查看详情
      //  List<ProjectCostPointInfoDTO> pointList = this.projectCostPointDao.listProjectAmountFeeByCostId(queryDTO);
        String isManager = this.getManagerFlag(queryDTO.getProjectId(),queryDTO.getCompanyId(),queryDTO.getCurrentCompanyUserId());
        String isEdit = ("1".equals(isManager) && queryDTO.getAppOrgId().equals(costData.getOperateCompanyId()))?"1":"0";
        Map<String,Object> role = this.getEditRole(queryDTO.getAppOrgId(),costData,isManager);
        Map<String,Object> result = new HashMap<>();
        result.put("cost",costData);
        result.put("pointList",listProjectAmountFeeByCostId(queryDTO,role));
        result.put("isManager",isEdit);//此处包含了当前组织的判断
        result.putAll(role);
        return result;
    }

    private List<ProjectCostPointInfoDTO> listProjectAmountFeeByCostId(ProjectCostQueryDTO queryDTO,Map<String,Object> role){
        List<ProjectCostPointInfoDTO> pointList = this.projectCostPointDao.listProjectAmountFeeByCostId(queryDTO);
        if(role.containsKey("startReceiveFlag") && "1".equals((String)role.get("startReceiveFlag"))
                || role.containsKey("startPayFlag") && "1".equals((String)role.get("startPayFlag"))){
            pointList.stream().forEach(p->{
                if(p.getFee().doubleValue()>p.getDetailFee().doubleValue()){
                    p.setIsEditFlag("1");//可以发起回款,发起收付
                }
            });
        }
        return pointList;
    }

    private Map<String,Object> getEditRole(String currentCompanyId,ProjectCostDataDTO costData,String isManager) {
        return this.getEditRole(currentCompanyId,costData,null,isManager);
    }

    private Map<String,Object> getEditRole(String currentCompanyId,ProjectCostDataDTO costData,ProjectCostPointDetailInfoDTO pointDetailInfo,String isManager) {
        ProjectCostDTO cost = new ProjectCostDTO();
        BeanUtils.copyProperties(costData,cost);
        boolean  isInnerCompany = this.isInnerCompany(cost);
        Map<String,Object> result = new HashMap<>();
        result.put("startReceiveFlag",this.getStartReceiveFlag(currentCompanyId,costData,isManager));//发起回款,发起付款
        result.put("startPayFlag",this.getStartPayFlag(currentCompanyId,costData,isManager,isInnerCompany)); //发起付款申请（外部）
        if(pointDetailInfo!=null && pointDetailInfo.getFeeStatus()!=null && pointDetailInfo.getFeeStatus()==0){
            result.put("startPayFlagForInner",this.getStartPayFlagForInner(currentCompanyId,costData,isManager,isInnerCompany));  //发起付款申请（内部）
        }
        return result;
    }

    /**
     * 发起收款的标识 1：可以发起收款，2：不可以发起收款
     */
    private String getStartReceiveFlag(String currentCompanyId,ProjectCostDataDTO costData,String isManager){
        if((currentCompanyId).equals(costData.getToCompanyId()) && "1".equals(isManager)){
            return "1";
        }
        return "0";
    }

    private String getStartPayFlag(String currentCompanyId,ProjectCostDataDTO costData,String isManager,boolean isInnerCompany){
        if(currentCompanyId.equals(costData.getFromCompanyId()) && "1".equals(isManager) && !isInnerCompany){
            return "1";
        }
        return "0";
    }

    private String getStartPayFlagForInner(String currentCompanyId,ProjectCostDataDTO costData,String isManager,boolean isInnerCompany){
        if(currentCompanyId.equals(costData.getFromCompanyId()) && "1".equals(isManager) && isInnerCompany){
            return "1";
        }
        return "0";
    }

    @Override
    public Map<String, Object> getProjectCostPointDetailByCostId(ProjectCostQueryDTO queryDTO) throws Exception {
        if(StringUtil.isNullOrEmpty(queryDTO.getCompanyId())){
            queryDTO.setCompanyId(queryDTO.getAppOrgId());
        }
        ProjectCostDataDTO costData = getProjectCost(queryDTO,null,true);
        //查询权限
        String isManager = this.getManagerFlag(queryDTO.getProjectId(),queryDTO.getCompanyId(),queryDTO.getCurrentCompanyUserId());
        Map<String,Object> role = this.getEditRole(queryDTO.getAppOrgId(),costData,isManager);
        //查询节点信息
        List<ProjectCostPointInfoDTO> pointList = this.listProjectAmountFeeByCostId(queryDTO,role);
        //此接口，只有一条信息
        ProjectCostPointInfoDTO pointInfoDTO = CollectionUtils.isEmpty(pointList)? new ProjectCostPointInfoDTO():pointList.get(0);
        pointInfoDTO.setPlanFee(pointInfoDTO.getFee());
        //查看详情
        List<ProjectCostPointDetailInfoDTO> pointDetailInfoList = this.projectCostPointDao.listProjectAmountFeeByPointId(queryDTO);
        Map<String,Object> result = new HashMap<>();
        result.put("cost",costData);
        result.put("point",pointInfoDTO);
        result.put("pointDetailInfoList",pointDetailInfoList);
        result.put("isManager",isManager);
        if(queryDTO.getPayType()!=null && 2==queryDTO.getPayType()) {  //如果是付款
            handleProjectFeeAuditStatus(pointDetailInfoList);
        }
        return result;
    }

    private void handleProjectFeeAuditStatus(List<ProjectCostPointDetailInfoDTO> pointDetailInfoList){
        //处理费用状态
        pointDetailInfoList.stream().forEach(pointDetailDataDTO->{
            ExpMainDTO auditEntity =  expMainDao.getExpMainByRelationId(pointDetailDataDTO.getId());
            if(auditEntity == null){//如果没有发起申请
                pointDetailDataDTO.setAuditStatus("4");//没有经过审批
            }else {
                pointDetailDataDTO.setAuditStatus(auditEntity.getApproveStatus());
            }
        });
    }
    @Override
    public Map<String, Object> getProjectCostPaymentDetailByPointDetailIdForReceive(ProjectCostQueryDTO queryDTO) throws Exception {
        if(StringUtil.isNullOrEmpty(queryDTO.getCompanyId())){
            queryDTO.setCompanyId(queryDTO.getAppOrgId());
        }
        this.setParam(queryDTO);
        ProjectCostDataDTO costData = getProjectCost(queryDTO,null,true);
        queryDTO.setPayType(CompanyBillType.DIRECTION_PAYEE);//先默让为收款方
        if(queryDTO.getCompanyId().equals(costData.getFromCompanyId())){
            queryDTO.setPayType(CompanyBillType.DIRECTION_PAYER);
        }
        //查看详情
        List<ProjectCostPointInfoDTO> pointList = this.projectCostPointDao.listProjectAmountFeeByCostId(queryDTO);
        List<ProjectCostPointDetailInfoDTO> pointDetailInfoList = this.projectCostPointDao.listProjectAmountFeeByPointId(queryDTO);
        ProjectCostPointDetailInfoDTO pointDetailInfo =  CollectionUtils.isEmpty(pointDetailInfoList)?new ProjectCostPointDetailInfoDTO():pointDetailInfoList.get(0);

        //开票信息
        InvoiceInfoDTO invoiceInfo = new InvoiceInfoDTO();
        if(!StringUtil.isNullOrEmpty(pointDetailInfo.getInvoice())){
            invoiceInfo = invoiceService.getInvoice(pointDetailInfo.getInvoice());
        }
        //到款信息
        List<ProjectCostPaymentDetailDTO> paymentDetailList = this.projectCostPaymentDetailDao.listProjectPaymentFeeByPointDetailId(queryDTO);
        Map<String,Object> result = new HashMap<>();
        result.put("cost",costData);
        result.put("point",CollectionUtils.isEmpty(pointList)? new ProjectCostPointInfoDTO():pointList.get(0));
        result.put("pointDetailInfo",pointDetailInfo);
        result.put("invoiceInfo",invoiceInfo);
        result.put("paymentDetailList",paymentDetailList);
        result.put("isManager",this.getManagerFlag(queryDTO.getProjectId(),queryDTO.getCompanyId(),queryDTO.getCurrentCompanyUserId()));
        result.putAll(this.handleOperateRole(queryDTO.getPointDetailId(),queryDTO));//处理权限
        if(result.containsKey("highLineId")){
            this.setPaymentDetailHighLine(paymentDetailList,(String)result.get("highLineId"));
        }
        return result;
    }

    private void setPaymentDetailHighLine(List<ProjectCostPaymentDetailDTO> paymentDetailList,String highLineId){
        paymentDetailList.stream().forEach(p->{
            if(p.getId().equals(highLineId)){
                p.setHighLine(1);
            }
        });
    }

    private Map<String, Object> handleOperateRole(String costDetailId, ProjectCostQueryDTO queryDTO) throws Exception {
        Map<String, Object> roleMap = new HashMap<>();
        MyTaskEntity task = null;
        if(!StringUtil.isNullOrEmpty(queryDTO.getTaskId())){//如果已经带了任务进来
            task = myTaskService.selectById(queryDTO.getTaskId());//就直接查询
        }else {
            String accountId = queryDTO.getAccountId();
            String companyId = queryDTO.getAppOrgId();
            if(permissionService.isFinancialReceive(companyId,accountId) || permissionService.isFinancial(companyId,accountId)){
                Map<String, Object> map = new HashMap<>();
                map.put("targetId", costDetailId);
                map.put("companyId", companyId);
                List<MyTaskEntity> myTaskList = this.myTaskService.getMyTaskByParam(map);
                if(!CollectionUtils.isEmpty(myTaskList)){
                    task = myTaskList.get(0);//理论上只会存在一条
                }
            }
        }
        if(task!=null && "0".equals(task.getStatus())){
            if(task.getTaskType()==29){
                roleMap.put("invoiceConfirm",task.getId());//发票确认
            }else {
                if(task.getTaskType()>15 && task.getTaskType()<20){
                    roleMap.put("onlyDayFlag","1");//只填日期
                    roleMap.put("highLineId",task.getTargetId());
                }
                roleMap.put("financialForFee",task.getId());//财务到账，付款
            }
        }
        return roleMap;
    }

    /**
     * 由于前端通过任务进入详情接口，只传入了任务的id，需要重新获取参数
     */
    private void setParam(ProjectCostQueryDTO queryDTO) throws Exception {
        this.setCurrentTaskRealCompanyId(queryDTO);
        if(StringUtil.isNullOrEmpty(queryDTO.getCostId())){
            if(StringUtil.isNullOrEmpty(queryDTO.getPointDetailId())){
                if(!StringUtil.isNullOrEmpty(queryDTO.getTaskId())){//从任务中获取相关参数
                    MyTaskEntity task = this.myTaskService.selectById(queryDTO.getTaskId());
                    if(task==null){
                        throw new CustomException("参数错误");
                    }
                    if(task.getTaskType()>15 && task.getTaskType()<20){//此处为老数据处理的内容
                        String paymentDetailId = task.getTargetId();
                        if(!StringUtil.isNullOrEmpty(paymentDetailId)){
                            ProjectCostPaymentDetailEntity paymentDetail = this.projectCostPaymentDetailDao.selectById(paymentDetailId);
                            if(paymentDetail==null){
                                throw new CustomException("参数错误");
                            }
                            queryDTO.setPointDetailId(paymentDetail.getPointDetailId());
                        }
                    }else {
                        queryDTO.setPointDetailId(task.getTargetId());
                    }
                }
            }
            ProjectCostPointDetailEntity pointDetail = null;
            if(!StringUtil.isNullOrEmpty(queryDTO.getPointDetailId())){
                pointDetail = this.projectCostPointDetailDao.selectById(queryDTO.getPointDetailId());
                if(pointDetail==null){
                    throw new CustomException("参数错误");
                }
                ProjectCostPointEntity pointEntity = this.projectCostPointDao.selectById(pointDetail.getPointId());
                queryDTO.setProjectId(pointEntity.getProjectId());
                queryDTO.setPointId(pointEntity.getId());
                queryDTO.setCostId(pointEntity.getCostId());
            }
        }
    }

    private void setCurrentTaskRealCompanyId(ProjectCostQueryDTO queryDTO){
        if(!StringUtil.isNullOrEmpty(queryDTO.getTaskId())){
            String companyId = this.projectCostPointDao.getCostFeeCompanyByTaskId(queryDTO.getTaskId());
            if(!StringUtil.isNullOrEmpty(companyId)){
                queryDTO.setCompanyId(companyId);
            }
        }
    }

    @Override
    public Map<String, Object> getProjectCostPaymentDetailByPointDetailIdForPay(ProjectCostQueryDTO queryDTO) throws Exception {
        if(StringUtil.isNullOrEmpty(queryDTO.getCompanyId())){
            queryDTO.setCompanyId(queryDTO.getAppOrgId());
        }
        this.setParam(queryDTO);
        ProjectCostDataDTO costData = getProjectCost(queryDTO,null,true);
        queryDTO.setPayType(CompanyBillType.DIRECTION_PAYEE);//先默让为收款方
        if(queryDTO.getCompanyId().equals(costData.getFromCompanyId())){
            queryDTO.setPayType(CompanyBillType.DIRECTION_PAYER);
        }
        //查看详情
        List<ProjectCostPointInfoDTO> pointList = this.projectCostPointDao.listProjectAmountFeeByCostId(queryDTO);
        List<ProjectCostPointDetailInfoDTO> pointDetailInfoList = this.projectCostPointDao.listProjectAmountFeeByPointId(queryDTO);
        ProjectCostPointDetailInfoDTO pointDetailInfo =  CollectionUtils.isEmpty(pointDetailInfoList)?new ProjectCostPointDetailInfoDTO():pointDetailInfoList.get(0);
        //审批记录
        Map<String,Object> auditInfo = expMainService.getAuditInfoByRelationId(queryDTO.getPointDetailId(),queryDTO.getCurrentCompanyUserId());
        //到款信息
        List<ProjectCostPaymentDetailDTO> paymentDetailList = this.projectCostPaymentDetailDao.listProjectPaymentFeeByPointDetailId(queryDTO);
        String isManager = this.getManagerFlag(queryDTO.getProjectId(),queryDTO.getCompanyId(),queryDTO.getCurrentCompanyUserId());
        //封装返回信息
        Map<String,Object> result = new HashMap<>();
        result.put("cost",costData);
        result.put("point",CollectionUtils.isEmpty(pointList)? new ProjectCostPointInfoDTO():pointList.get(0));
        result.put("pointDetailInfo",pointDetailInfo);
        result.put("paymentDetailList",paymentDetailList);
        result.putAll(auditInfo);
        result.put("isManager",isManager);
        result.putAll(this.handleOperateRole(queryDTO.getPointDetailId(),queryDTO));//处理权限
        result.putAll(getEditRole(queryDTO.getAppOrgId(),costData,pointDetailInfo,isManager));
        if(result.containsKey("highLineId")){
            this.setPaymentDetailHighLine(paymentDetailList,(String)result.get("highLineId"));
        }
        return result;
    }

    @Override
    public Map<String, Object> getProjectCostReceiveByCostId(ProjectCostQueryDTO queryDTO) throws Exception {
        if(StringUtil.isNullOrEmpty(queryDTO.getCompanyId())){
            queryDTO.setCompanyId(queryDTO.getAppOrgId());
        }
        //先查总记录
        ProjectCostDataDTO costData = getProjectCost(queryDTO,null,true);
        queryDTO.setPayType(CompanyBillType.DIRECTION_PAYEE);//先默让为收款方
        if(queryDTO.getCompanyId().equals(costData.getFromCompanyId())){
            queryDTO.setPayType(CompanyBillType.DIRECTION_PAYER);
        }
        //查询收款/付款
        List<ProjectCostPointReceiveOrPayInfoDTO> pointList = this.projectCostPaymentDetailDao.listProjectCostPointReceiveOrPayInfo(queryDTO);
        Map<String,Object> result = new HashMap<>();
        result.put("cost",costData);
        result.put("pointList",pointList);
        return result;
    }

    @Override
    public Map<String, Object> getProjectCostNotReceiveByCostId(ProjectCostQueryDTO queryDTO) throws Exception {
        if(StringUtil.isNullOrEmpty(queryDTO.getCompanyId())){
            queryDTO.setCompanyId(queryDTO.getAppOrgId());
        }
        this.setParam(queryDTO);
        //先查总记录
        ProjectCostDataDTO costData = getProjectCost(queryDTO,null,true);
        queryDTO.setPayType(CompanyBillType.DIRECTION_PAYEE);//先默让为收款方
        if(queryDTO.getCompanyId().equals(costData.getFromCompanyId())){
            queryDTO.setPayType(CompanyBillType.DIRECTION_PAYER);
        }
        //查询应收款/付款
        List<ProjectCostPointReceiveOrPayInfoDTO> pointList = this.projectCostPaymentDetailDao.listProjectCostPointNotReceiveOrPayInfo(queryDTO);
        Map<String,Object> result = new HashMap<>();
        result.put("cost",costData);
        result.put("pointList",pointList);
        return result;
    }

    @Override
    public ResponseBean completeProjectFeeApply(ProjectCostPointDetailDTO projectCostPointDetailDTO) throws Exception {
        RelationRecordEntity relationRecord = relationRecordService.getRelationRecord(projectCostPointDetailDTO.getMainId());
        if(relationRecord!=null){
            String pointDetailId = relationRecord.getRelationId();
            projectCostPointDetailDTO.setId(pointDetailId);
            ProjectCostPointDetailEntity pointDetailEntity = this.projectCostPointDetailDao.selectById(pointDetailId);
            ProjectCostEntity projectCost = this.projectCostDao.getProjectCostByPointId(pointDetailEntity.getPointId());
            projectCostPointDetailDTO.setCurrentCompanyId(projectCost.getFromCompanyId());//此处设置未付款组织的id
            projectCostPointDetailDTO.setAccountId(pointDetailEntity.getCreateBy());
            projectCostPointDetailDTO.setPointId(pointDetailEntity.getPointId());
            pointDetailEntity.setFeeStatus(projectCostPointDetailDTO.getFeeStatus());
            this.projectCostPointDetailDao.updateById(pointDetailEntity);
            if(projectCostPointDetailDTO.getFeeStatus()==ProjectCostConst.FEE_STATUS_APPROVE){
                this.sendMyTask(pointDetailId,projectCostPointDetailDTO);
            }
        }
        return null;
    }

    @Override
    public ProjectCostDataDTO getProjectCostByMainId(String mainId) throws Exception {
        ProjectCostDataDTO result = new ProjectCostDataDTO();
        RelationRecordEntity relationRecord = relationRecordService.getRelationRecord(mainId);
        if(relationRecord!=null){
            String pointDetailId = relationRecord.getRelationId();
            ProjectCostPointDetailEntity pointDetailEntity = this.projectCostPointDetailDao.selectById(pointDetailId);
            ProjectCostEntity costEntity = this.projectCostDao.getProjectCostByPointId(pointDetailEntity.getPointId());
            ProjectCostDTO cost = new ProjectCostDTO();
            BeanUtils.copyProperties(costEntity,cost);

            BeanUtils.copyProperties(costEntity,result);
            result.setCostId(cost.getId());
            result.setPlanFee(cost.getFee());
            result.setOperateCompanyId(cost.getOperateCompanyId());
            result.setToCompanyId(cost.getToCompanyId());
            result.setFromCompanyId(cost.getFromCompanyId());
            result.setType(cost.getType());
            result.setRelationCompanyName(this.getRelationCompanyName(cost,costEntity.getFromCompanyId()));
            result.setProjectName(this.projectDao.getProjectName(cost.getProjectId()));
            result.setTypeName(ProjectCostConst.COST_TYPE_MAP.get(cost.getType()));
            result.setDetailFee(pointDetailEntity.getFee());
        }
        return result;
    }

    @Override
    public Map<String, Object> getProjectCostPaymentDetailByMainIdForPay(ProjectCostQueryDTO queryDTO) throws Exception {
        //获取参数
        if(queryDTO.getMainId()==null){
            throw new CustomException("参数错误");
        }
        RelationRecordEntity relationRecord = this.relationRecordService.getRelationRecord(queryDTO.getMainId());
        if(relationRecord==null){
            throw new CustomException("参数错误");
        }
        ExpMainEntity main = this.expMainDao.selectById(queryDTO.getMainId());
        ProjectCostPointDetailEntity pointDetail = this.projectCostPointDetailDao.selectById(relationRecord.getRelationId());
        ProjectCostEntity projectCost = this.projectCostDao.getProjectCostByPointId(pointDetail.getPointId());
        queryDTO.setProjectId(projectCost.getProjectId());
        queryDTO.setCostId(projectCost.getId());
        queryDTO.setPointId(pointDetail.getPointId());
        queryDTO.setPointDetailId(pointDetail.getId());
        queryDTO.setCompanyId(main.getCompanyId());
        return this.getProjectCostPaymentDetailByPointDetailIdForPay(queryDTO);
    }

    private ProjectCostDataDTO getProjectCost(ProjectCostQueryDTO queryDTO,ProjectCostDTO cost,boolean isSelectAttach) throws Exception{
        if(cost==null){
            ProjectCostEntity costEntity = this.projectCostDao.selectById(queryDTO.getCostId());
            cost = new ProjectCostDTO();
            BeanUtils.copyProperties(costEntity,cost);
        }
        queryDTO.setCostId(cost.getId());
        ProjectCooperatorCostDTO costDetail = this.projectCostDao.getProjectAmountFeeByCostId(queryDTO);
        ProjectCostDataDTO result = new ProjectCostDataDTO();
        if(costDetail!=null){
            BeanUtils.copyProperties(costDetail,result);
        }
        result.setRelationCompanyName(this.getRelationCompanyName(cost,queryDTO.getAppOrgId()));
        result.setCostId(cost.getId());
        result.setPlanFee(cost.getFee());
        result.setOperateCompanyId(cost.getOperateCompanyId());
        result.setToCompanyId(cost.getToCompanyId());
        result.setFromCompanyId(cost.getFromCompanyId());
        result.setType(cost.getType());
        result.setProjectName(this.projectDao.getProjectName(cost.getProjectId()));

        if(isSelectAttach){
            //获取附件
            result.setAttachList(projectSkyDriverService.getAttachListByTargetId(cost.getId()));
        }
        return result;
    }

    /**
     * 当前是否是经营负责人 1=是，0=不是
     */
    private String getManagerFlag(String projectId,String companyId,String companyUserId) throws Exception{
        ProjectMemberEntity managerEntity = this.projectMemberService.getOperatorManager(projectId,companyId);
        if(managerEntity!=null && managerEntity.getCompanyUserId().equals(companyUserId)){
            return "1";
        }
        managerEntity = this.projectMemberService.getOperatorAssistant(projectId,companyId);
        if(managerEntity!=null && managerEntity.getCompanyUserId().equals(companyUserId)){
            return "1";
        }
        return "0";
    }

    /**
     * 获取立项方组织的名称
     */
    private String getRelationCompanyName(ProjectCostDTO cost,String currentCompanyId){
        CompanyEntity companyEntity = null;
        String relationCompanyId = null;
        if (currentCompanyId.equals(cost.getFromCompanyId())) {
            relationCompanyId = cost.getToCompanyId();
        } else {
            relationCompanyId = cost.getFromCompanyId();
        }
        companyEntity = this.companyDao.selectById(relationCompanyId);
        if (companyEntity != null) {
            return  companyEntity.getAliasName();
        }else {
            //从enterprise中查询
            String name = enterpriseService.getEnterpriseName(relationCompanyId);
            if(name!=null){
                return name;
            }
        }
        return relationCompanyId;
    }

    private List<ProjectCostDTO> getProjectCostList(ProjectCostQueryDTO queryDTO){
        String currentCompanyId = queryDTO.getAppOrgId();
        Map<String, Object> costParam = new HashMap<>();
        costParam.put("projectId",queryDTO.getProjectId());
        Integer payType =queryDTO.getPayType();
        if(CompanyBillType.DIRECTION_PAYEE == payType){
            costParam.put("toCompanyId",currentCompanyId);
        }else {
            costParam.put("fromCompanyId",currentCompanyId);
        }
        return this.projectCostDao.selectByParam(costParam);
    }

    boolean handleAdverseFinancialAccount(String paymentId) throws Exception{
        Map<String,Object> param = new HashMap<>();
        param.put("targetId",paymentId);
        List<MyTaskEntity> taskList = this.myTaskService.getMyTaskByParam(param);
        if(!CollectionUtils.isEmpty(taskList) && taskList.size()<2){
            return true;
        }
        return false;
    }

    private void sendMessage(ProjectCostEntity cost,ProjectCostPointDetailEntity pointDetail,ProjectCostPaymentDetailEntity paymentDetail,ProjectCostPaymentDetailDTO dto,boolean isSaveAdverseFinancial) throws Exception{
        //财务到款，付款给企业负责人和经营负责人推送消息
        sendMessageToOperatorAndOrgAdminForTaskType(cost,paymentDetail.getId(),pointDetail.getPointId(),dto.getTaskType(),dto.getAccountId(),dto.getCurrentCompanyId());
        if(isSaveAdverseFinancial){
            int type = dto.getTaskType();
            if(dto.getTaskType()==SystemParameters.TECHNICAL_REVIEW_FEE_FOR_PAY){
                type = SystemParameters.TECHNICAL_REVIEW_FEE_FOR_PAID;
            }
            if(dto.getTaskType()==SystemParameters.TECHNICAL_REVIEW_FEE_FOR_PAID){
                type = SystemParameters.TECHNICAL_REVIEW_FEE_FOR_PAY;
            }
            if(dto.getTaskType()==SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAY){
                type = SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAID;
            }
            if(dto.getTaskType()==SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAID){
                type = SystemParameters.COOPERATIVE_DESIGN_FEE_FOR_PAY;
            }
            sendMessageToOperatorAndOrgAdminForTaskType(cost,paymentDetail.getId(),pointDetail.getPointId(),type,dto.getAccountId(),dto.getCurrentCompanyId());
        }
    }
    /**
     * 方法描述：财务到款确认，给企业负责人和经营负责人推送信息
     * 作者：MaoSF
     * 日期：2017/3/17
     */
    private void sendMessageToOperatorAndOrgAdminForTaskType(ProjectCostEntity cost ,String paymentDetailId,String pointId,int taskType,String accountId,String currentCompanyId) throws Exception{
        int type = 0;
        String companyId = null;
        String projectId = cost.getProjectId();
        if(taskType==8){
            type = SystemParameters.MESSAGE_TYPE_13;
            companyId = cost.getToCompanyId();
        }
        if(taskType==9){
            type = SystemParameters.MESSAGE_TYPE_16;
            companyId = cost.getToCompanyId();
        }
        if(taskType==10){
            type = SystemParameters.MESSAGE_TYPE_18;
            companyId = cost.getToCompanyId();
        }
        if(taskType==16 || taskType ==30){
            type = SystemParameters.MESSAGE_TYPE_27;
            companyId = cost.getFromCompanyId();
        }
        if(taskType==17 || taskType ==31){
            type = SystemParameters.MESSAGE_TYPE_28;
            companyId = cost.getToCompanyId();
        }
        if(taskType==18 || taskType ==32){
            type = SystemParameters.MESSAGE_TYPE_29;
            companyId = cost.getFromCompanyId();
        }
        if(taskType==19 || taskType ==33){
            type = SystemParameters.MESSAGE_TYPE_30;
            companyId = cost.getToCompanyId();
        }
        if(taskType==20){
            type = SystemParameters.MESSAGE_TYPE_33;
            companyId = cost.getFromCompanyId();
        }
        if(taskType==21){
            type = SystemParameters.MESSAGE_TYPE_34;
            companyId = cost.getToCompanyId();
        }
        if(taskType==307){//特定307，经营负责人收到内部组织的付款申请的信息
            type = SystemParameters.MESSAGE_TYPE_307;
            companyId = cost.getFromCompanyId();
        }
        if(type==0){
            return;//如果不是以上内容，只直接返回
        }
        ProjectMemberEntity managerEntity = this.projectMemberService.getOperatorManager(projectId,companyId);
        ProjectMemberEntity assist = this.projectMemberService.getOperatorAssistant(projectId,companyId);
        CompanyUserDataDTO orgManager = null;
        if(taskType!=307){
            orgManager = this.companyUserService.getOrgManager(companyId);
        }
        List<String> userIdList = new ArrayList<>();
        if (managerEntity != null) {
            if (!userIdList.contains(managerEntity.getAccountId())) {
                userIdList.add(managerEntity.getAccountId());
            }
        }
        if (assist != null) {
            if (!userIdList.contains(assist.getAccountId())) {
                userIdList.add(assist.getAccountId());
            }
        }
        if (orgManager != null) {
            if (!userIdList.contains(orgManager.getUserId())) {
                userIdList.add(orgManager.getUserId());
            }
        }
        List<String> userList = userIdList.stream()
                .filter(line -> !accountId.equals(line))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(userList)) {
            for (String userId : userList) {
                MessageEntity messageEntity = new MessageEntity();
                messageEntity.setProjectId(projectId);
                messageEntity.setCompanyId(companyId);
                messageEntity.setTargetId(paymentDetailId);
                messageEntity.setParam1(pointId);
                messageEntity.setUserId(userId);
                messageEntity.setMessageType(type);
                messageEntity.setCreateBy(accountId);
                messageEntity.setCreateDate(new Date());
                messageEntity.setSendCompanyId(currentCompanyId);
                this.messageService.sendMessage(messageEntity);
            }
        }
    }



}
