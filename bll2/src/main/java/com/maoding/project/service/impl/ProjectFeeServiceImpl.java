package com.maoding.project.service.impl;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.util.StringUtil;
import com.maoding.project.dao.ProjectFeeDao;
import com.maoding.project.dao.ProjectPointDao;
import com.maoding.project.dto.*;
import com.maoding.project.entity.*;
import com.maoding.project.entity.ProjectFeeEntity;
import com.maoding.project.service.*;
import com.maoding.project.service.ProjectFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectFeeServiceImpl
 * 类描述：项目合同节点ServiceImpl
 * 作    者：MaoSF
 * 日    期：2016年7月19日-下午5:28:54
 */
@Service("projectFeeService")
public class ProjectFeeServiceImpl extends GenericService<ProjectFeeEntity> implements ProjectFeeService {

    @Autowired
    private ProjectFeeDao projectFeeDao;

    @Autowired
    private ProjectPointDao projectPointDao;

    @Autowired
    private ProjectPointService projectPointService;



    @Autowired
    private ProjectDesignContentService projectDesignContentService;

    public  AjaxMessage validateSaveOrUpdateProjectFee(ProjectFeeDTO dto) throws Exception{
        AjaxMessage ajax = new AjaxMessage();

        if(null == dto.getFee() || StringUtil.isNullOrEmpty(dto.getFee().toString())){
            return ajax.setCode("1").setInfo("金额不能为空");
        }

        if(StringUtil.isNullOrEmpty(dto.getFeeDate())){
            return ajax.setCode("1").setInfo("日期不能为空");
        }

        if(!StringUtil.isNullOrEmpty(dto.getRemark()) && dto.getRemark().length()>200){
            return ajax.setCode("1").setInfo("备注过长");
        }

        return ajax.setCode("0");
    }


    /**
     * 方法描述：保存合同节点
     * 作者：MaoSF
     * 日期：2016/8/1
     * @param dto
     */
    @Override
    public AjaxMessage   saveOrUpdateProjectFee(ProjectFeeDTO dto) throws Exception {
        AjaxMessage ajax = this.validateSaveOrUpdateProjectFee(dto);
        if(!"0".equals(ajax.getCode())){
            return ajax;
        }

        ProjectFeeEntity feeEntity = new ProjectFeeEntity();
        BaseDTO.copyFields(dto,feeEntity);

        //如果是新增开票
        if(StringUtil.isNullOrEmpty(dto.getId()) && ("1".equals(dto.getType()) || "2".equals(dto.getType()))){
            ajax = addProjectFee(feeEntity,dto);
        }

        //如果是修改开票，回款
        if(!StringUtil.isNullOrEmpty(dto.getId()) && ("1".equals(dto.getType()) || "2".equals(dto.getType()))){
            ajax = updateProjectFee(feeEntity,dto);
        }
        //如果是修改技术审查费，合作设计费
        if("3".equals(dto.getType()) || "4".equals(dto.getType()) || "5".equals(dto.getType()) || "6".equals(dto.getType())){
            ajax = this.validateProjectFee(dto);
            if(!"0".equals(ajax.getCode())){
                return ajax;
            }
            if(StringUtil.isNullOrEmpty(dto.getId())){
                //新增费用
                dto.setId(StringUtil.buildUUID());
                feeEntity.setId(dto.getId());
                feeEntity.setCreateBy(dto.getAccountId());
                projectFeeDao.insert(feeEntity);
            }else{
                //修改费用
                feeEntity.setUpdateBy(dto.getAccountId());
                feeEntity.setStatus("0");
                projectFeeDao.updateById(feeEntity);
            }
        }

        //获取返回数据，开票，到款，返回合同回款三个列表
        if("0".equals(ajax.getCode()) && ("1".equals(dto.getType()) || "2".equals(dto.getType()))){
            //获取合同回款所有数据
//            ProjectDTO projectDTO = new ProjectDTO();
//            projectPointService.getProjectPointDetail(dto.getProjectId(),projectDTO);
            return ajax.setCode("0").setInfo("保存成功").setData(this.getProjectPointFeeResult(feeEntity.getProjectId(),feeEntity.getPointId(),feeEntity.getType()));
        } else if("0".equals(ajax.getCode()) && ("3".equals(dto.getType()) || "4".equals(dto.getType())) || "5".equals(dto.getType()) || "6".equals(dto.getType())){
            //获取返回数据，合作设计费，技术审查费 到款，付款，返回服务内容三个列表
            ProjectDesignContentDTO projectDesignContentDTO = new ProjectDesignContentDTO();
            projectDesignContentDTO.setProjectId(dto.getProjectId());
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("companyId",dto.getCurrentCompanyId());
            map.put("userId",dto.getAccountId());
           // projectDesignContentService.getProjectServerContentFeeDetail(dto.getDesignContentId(),projectDesignContentDTO,map);
            return ajax.setCode("0").setInfo("保存成功").setData(projectDesignContentDTO);
        }else{
            return ajax;
        }
    }

    /**
     * 方法描述：新增费用
     * 作者：MaoSF
     * 日期：2016/8/4
     * @param:
     * @return:
     */
    public AjaxMessage addProjectFee(ProjectFeeEntity projectFeeEntity,ProjectFeeDTO dto) {

        ProjectPointEntity projectPointEntity = projectPointDao.selectById(projectFeeEntity.getPointId());
        BigDecimal fees= null;
        if(null!=projectPointEntity){
            if("1".equals(projectFeeEntity.getType())){

                if(null == projectPointEntity.getInvoiceMoney()){
                    projectPointEntity.setInvoiceMoney(new BigDecimal("0"));
                }
                fees = projectPointEntity.getInvoiceMoney().add(projectFeeEntity.getFee());
                projectPointEntity.setInvoiceMoney(fees);
            }else if("2".equals(projectFeeEntity.getType())){

                if(null == projectPointEntity.getPayedMoney()){
                    projectPointEntity.setPayedMoney(new BigDecimal("0"));
                }
                fees = projectPointEntity.getPayedMoney().add(projectFeeEntity.getFee());
                projectPointEntity.setPayedMoney(fees);
            }

            if(fees.compareTo(new BigDecimal(0))==-1){
                BigDecimal tempFee = fees.abs();
                BigDecimal receivableFee = projectPointEntity.getReceivableMoney().abs();
                if(receivableFee.compareTo(tempFee)==-1){
                    return new AjaxMessage().setCode("1").setInfo("总金额不能大于："+StringUtil.subZeroAndDot( projectPointEntity.getReceivableMoney().abs().toString())+"万元");
                }
                if(projectPointEntity.getReceivableMoney().compareTo(new BigDecimal(0))==-1&&projectFeeEntity.getFee().compareTo(new BigDecimal(0))==1){
                    return new AjaxMessage().setCode("1").setInfo("金额需同为负");
                }
                if(projectPointEntity.getReceivableMoney().compareTo(new BigDecimal(0))==1&&projectFeeEntity.getFee().compareTo(new BigDecimal(0))==-1){
                    return new AjaxMessage().setCode("1").setInfo("金额需同为正");
                }

            }else {
                if(projectPointEntity.getReceivableMoney().compareTo(fees)==-1){
                    return new AjaxMessage().setCode("1").setInfo("总金额不能大于："+StringUtil.subZeroAndDot( projectPointEntity.getReceivableMoney().abs().toString())+"万元");
                }
                if(projectPointEntity.getReceivableMoney().compareTo(new BigDecimal(0))==-1&&projectFeeEntity.getFee().compareTo(new BigDecimal(0))==1){
                    return new AjaxMessage().setCode("1").setInfo("金额需同为负");
                }
                if(projectPointEntity.getReceivableMoney().compareTo(new BigDecimal(0))==1&&projectFeeEntity.getFee().compareTo(new BigDecimal(0))==-1){
                    return new AjaxMessage().setCode("1").setInfo("金额需同为正");
                }
            }

            projectPointEntity.setUpdateBy(dto.getAccountId());
            //修改节点
            projectPointEntity.setStatus("0");
            projectPointDao.updateById(projectPointEntity);
        }

        //新增费用
        dto.setId(StringUtil.buildUUID());
        projectFeeEntity.setId(dto.getId());
        projectFeeEntity.setCreateBy(dto.getAccountId());
        this.insert(projectFeeEntity);
        return new AjaxMessage().setCode("0").setInfo("保存成功").setData(dto);
    }

    /**
     * 方法描述：修改费用
     * 作者：MaoSF
     * 日期：2016/8/4
     * @param:
     * @return:
     */
    public AjaxMessage updateProjectFee(ProjectFeeEntity entity,ProjectFeeDTO dto) {

        ProjectFeeEntity projectFeeEntity = projectFeeDao.selectById(entity.getId());
        ProjectPointEntity projectPointEntity = projectPointDao.selectById(projectFeeEntity.getPointId());
        BigDecimal fees= null;

        if(null == projectFeeEntity.getFee()){
            projectFeeEntity.setFee(new BigDecimal("0"));
        }

        if(null!=projectPointEntity){
            if("1".equals(projectFeeEntity.getType())){
                if(null == projectPointEntity.getInvoiceMoney()){
                    projectPointEntity.setInvoiceMoney(new BigDecimal("0"));
                }
                fees = projectPointEntity.getInvoiceMoney().add(entity.getFee()).subtract(projectFeeEntity.getFee());
                projectPointEntity.setInvoiceMoney(fees);

            }else if("2".equals(projectFeeEntity.getType())){
                if(null == projectPointEntity.getPayedMoney()){
                    projectPointEntity.setPayedMoney(new BigDecimal("0"));
                }
                fees = projectPointEntity.getPayedMoney().add(entity.getFee()).subtract(projectFeeEntity.getFee());
                projectPointEntity.setPayedMoney(fees);
            }

            if(fees.compareTo(new BigDecimal(0))==-1){
                BigDecimal tempFee = fees.abs();
                BigDecimal receivableFee = projectPointEntity.getReceivableMoney().abs();
                if(receivableFee.compareTo(tempFee)==-1){
                    return new AjaxMessage().setCode("1").setInfo("总金额不能大于："+StringUtil.subZeroAndDot( projectPointEntity.getReceivableMoney().abs().toString())+"万元");
                }
                if(projectPointEntity.getReceivableMoney().compareTo(new BigDecimal(0))==-1&&projectFeeEntity.getFee().compareTo(new BigDecimal(0))==1){
                    return new AjaxMessage().setCode("1").setInfo("金额需同为负");
                }
                if(projectPointEntity.getReceivableMoney().compareTo(new BigDecimal(0))==1&&projectFeeEntity.getFee().compareTo(new BigDecimal(0))==-1){
                    return new AjaxMessage().setCode("1").setInfo("金额需同为正");
                }

            }else {
                if(projectPointEntity.getReceivableMoney().compareTo(fees)==-1){
                    return new AjaxMessage().setCode("1").setInfo("总金额不能大于："+StringUtil.subZeroAndDot( projectPointEntity.getReceivableMoney().abs().toString())+"万元");
                }
                if(projectPointEntity.getReceivableMoney().compareTo(new BigDecimal(0))==-1&&projectFeeEntity.getFee().compareTo(new BigDecimal(0))==1){
                    return new AjaxMessage().setCode("1").setInfo("金额需同为负");
                }
                if(projectPointEntity.getReceivableMoney().compareTo(new BigDecimal(0))==1&&projectFeeEntity.getFee().compareTo(new BigDecimal(0))==-1){
                    return new AjaxMessage().setCode("1").setInfo("金额需同为正");
                }
            }

            //修改节点
            projectPointEntity.setUpdateBy(dto.getAccountId());
            projectPointEntity.setStatus("0");
            projectPointDao.updateById(projectPointEntity);
        }

        //修改费用
        entity.setUpdateBy(dto.getAccountId());
        entity.setStatus("0");
        projectFeeDao.updateById(entity);
        return new AjaxMessage().setCode("0").setInfo("保存成功").setData(dto);
    }

    public AjaxMessage validateProjectFee(ProjectFeeDTO dto) throws Exception{
        ProjectAmountFeeDTO amountFeeDTO = projectFeeDao.getProjectDesignFeeAmount(dto.getPointId());
        if(null == amountFeeDTO.getContractDesignPaidFee()){
            amountFeeDTO.setContractDesignPaidFee(new BigDecimal("0"));
        }
        if(null == amountFeeDTO.getContractDesignPayFee()){
            amountFeeDTO.setContractDesignPayFee(new BigDecimal("0"));
        }
        if(null == amountFeeDTO.getManagePaidFee()){
            amountFeeDTO.setManagePaidFee(new BigDecimal("0"));
        }
        if(null == amountFeeDTO.getManagePayFee()){
            amountFeeDTO.setManagePayFee(new BigDecimal("0"));
        }
        if(StringUtil.isNullOrEmpty(dto.getId())) {
            if ("3".equals(dto.getType())) {
                BigDecimal amount = amountFeeDTO.getContractDesignPayFee().add(dto.getFee());
                if(amountFeeDTO.getContractDesignFee().compareTo(amount)==-1){
                    return  new AjaxMessage().setCode("1").setInfo("总金额不能大于："+amountFeeDTO.getContractDesignFee()+"万元");
                }
            }
            if ("4".equals(dto.getType())) {
                BigDecimal amount = amountFeeDTO.getContractDesignPaidFee().add(dto.getFee());
                if(amountFeeDTO.getContractDesignFee().compareTo(amount)==-1){
                    return  new AjaxMessage().setCode("1").setInfo("总金额不能大于："+amountFeeDTO.getContractDesignFee()+"万元");
                }
            }
            if ("5".equals(dto.getType())) {
                BigDecimal amount = amountFeeDTO.getManagePaidFee().add(dto.getFee());
                if(amountFeeDTO.getManageFee().compareTo(amount)==-1){
                    return  new AjaxMessage().setCode("1").setInfo("总金额不能大于："+amountFeeDTO.getManageFee()+"万元");
                }
            }
            if ("6".equals(dto.getType())) {
                BigDecimal amount = amountFeeDTO.getManagePayFee().add(dto.getFee());
                if(amountFeeDTO.getManageFee().compareTo(amount)==-1){
                    return  new AjaxMessage().setCode("1").setInfo("总金额不能大于："+amountFeeDTO.getManageFee()+"万元");
                }
            }
        }else{
            ProjectFeeEntity projectFeeEntity = projectFeeDao.selectById(dto.getId());
            if ("3".equals(dto.getType())) {
                BigDecimal amount = amountFeeDTO.getContractDesignPayFee().add(dto.getFee()).subtract(projectFeeEntity.getFee());
                if(amountFeeDTO.getContractDesignFee().compareTo(amount)==-1){
                    return  new AjaxMessage().setCode("1").setInfo("总金额不能大于："+amountFeeDTO.getContractDesignFee()+"万元");
                }
            }
            if ("4".equals(dto.getType())) {
                BigDecimal amount = amountFeeDTO.getContractDesignPaidFee().add(dto.getFee()).subtract(projectFeeEntity.getFee());
                if(amountFeeDTO.getContractDesignFee().compareTo(amount)==-1){
                    return  new AjaxMessage().setCode("1").setInfo("总金额不能大于："+amountFeeDTO.getContractDesignFee()+"万元");
                }
            }
            if ("5".equals(dto.getType())) {
                BigDecimal amount = amountFeeDTO.getManagePaidFee().add(dto.getFee()).subtract(projectFeeEntity.getFee());
                if(amountFeeDTO.getManageFee().compareTo(amount)==-1){
                    return  new AjaxMessage().setCode("1").setInfo("总金额不能大于："+amountFeeDTO.getManageFee()+"万元");
                }
            }
            if ("6".equals(dto.getType())) {
                BigDecimal amount = amountFeeDTO.getManagePayFee().add(dto.getFee()).subtract(projectFeeEntity.getFee());
                if(amountFeeDTO.getManageFee().compareTo(amount)==-1){
                    return  new AjaxMessage().setCode("1").setInfo("总金额不能大于："+amountFeeDTO.getManageFee()+"万元");
                }
            }
        }
        return  new AjaxMessage().setCode("0");
    }


    /**
     * 方法描述：删除费用节点
     * 作者：MaoSF
     * 日期：2016/8/4
     *
     * @param id
     * @param:
     * @return:
     */
    @Override
    public AjaxMessage deleteProjectFee(String id,String companyId,String userId) throws Exception {

        ProjectFeeEntity feeEntity = projectFeeDao.selectById(id);
        if(feeEntity==null){
            return new AjaxMessage().setCode("1").setInfo("删除失败");
        }

        ProjectPointEntity pointEntity = null;
        BigDecimal moneys = null;

        if("1".equals(feeEntity.getType()) || "2".equals(feeEntity.getType()) ){
            pointEntity = projectPointDao.selectById(feeEntity.getPointId());
        }

        if("1".equals(feeEntity.getType())){
            moneys=pointEntity.getInvoiceMoney().subtract(feeEntity.getFee());
            pointEntity.setInvoiceMoney(moneys);
            projectPointDao.updateById(pointEntity);
        }else if("2".equals(feeEntity.getType())){
            moneys=pointEntity.getPayedMoney().subtract(feeEntity.getFee());
            pointEntity.setPayedMoney(moneys);
            projectPointDao.updateById(pointEntity);
        }

        //删除费用
        projectFeeDao.deleteById(id);

        if("1".equals(feeEntity.getType()) || "2".equals(feeEntity.getType()) ){
            //获取合同回款所有数据
            return new AjaxMessage().setCode("0").setInfo("删除成功").setData(this.getProjectPointFeeResult(feeEntity.getProjectId(),feeEntity.getPointId(),feeEntity.getType()));
        }
        /*if("3".equals(feeEntity.getType()) || "4".equals(feeEntity.getType()) || "5".equals(feeEntity.getType()) || "6".equals(feeEntity.getType())){
            ProjectServerContentEntity projectServerContent = projectServerContentService.selectById(feeEntity.getPointId());
            //获取返回数据，合作设计费，技术审查费 到款，付款，返回服务内容三个列表
            ProjectDesignContentDTO projectDesignContentDTO = new ProjectDesignContentDTO();
            projectDesignContentDTO.setProjectId(projectServerContent.getProjectId());
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("companyId",companyId);
            map.put("userId",userId);
            projectDesignContentService.getProjectServerContentFeeDetail(projectServerContent.getDesignContentId(),projectDesignContentDTO,map);
            return new AjaxMessage().setCode("0").setInfo("保存成功").setData(projectDesignContentDTO);
        }*/
        return new AjaxMessage().setCode("1").setInfo("删除失败");
    }

    /**
     * 方法描述：根据节点查询费用
     * 作        者：MaoSF
     * 日        期：2015年8月10日-下午4:18:42
     *
     * @param： pointId，
     * @param： type：1:开票，2：收款,3合作设计费付款，4合作设计费到款，5技术审查费付款，6技术审查费到款
     * @return
     */
    @Override
    public List<ProjectFeeEntity> getProjectFeeByPointAndType(String pointId,String type) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("pointId",pointId);
        map.put("type",type);
        return projectFeeDao.getProjectFeeByParameter(map);
    }

    /**
     * 方法描述：添加，修改，删除合同回款的开票，到款，返回的数据
     * 作者：MaoSF
     * 日期：2016/9/27
     * @param:
     * @return:
     */
    public Map<String,Object> getProjectPointFeeResult(String projectId,String pointId,String type) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        List<ProjectFeeEntity> list = getProjectFeeByPointAndType(pointId, type);

        ProjectDTO projectDTO = new ProjectDTO();
        projectPointService.getProjectPointDetail(projectId,projectDTO);
        BigDecimal amount = new BigDecimal(0);//总金额
        for(ProjectFeeEntity fee:list){
            amount=amount.add(fee.getFee());
        }
        map.put("list", list);
//        map.put("projectPointDTOList",projectDTO.getProjectPointDTOList());
//        map.put("invoiceDetailList",projectDTO.getInvoiceDetailList());
//        map.put("receivedPaymentDetailList",projectDTO.getReceivedPaymentDetailList());
        map.put("amount", amount);
        return map;
    }
}
