package com.maoding.companybill.service.impl;

import com.maoding.companybill.dao.CompanyBillDao;
import com.maoding.companybill.dao.CompanyBillDetailDao;
import com.maoding.companybill.dao.CompanyBillRelationDao;
import com.maoding.companybill.dto.SaveCompanyBillDTO;
import com.maoding.companybill.entity.CompanyBillDetailEntity;
import com.maoding.companybill.entity.CompanyBillEntity;
import com.maoding.companybill.entity.CompanyBillRelationEntity;
import com.maoding.companybill.service.CompanyBillService;
import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.NewBaseService;
import com.maoding.core.constant.CompanyBillType;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;
import com.maoding.financial.dao.ExpDetailDao;
import com.maoding.financial.dao.ExpFixedDao;
import com.maoding.financial.dao.ExpMainDao;
import com.maoding.financial.dto.ExpMainDataDTO;
import com.maoding.financial.entity.ExpDetailEntity;
import com.maoding.org.dao.CompanyDao;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.project.dao.ProjectDao;
import com.maoding.project.entity.ProjectEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("companyBillService")
public class CompanyBillServiceImpl extends NewBaseService implements CompanyBillService {

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private ExpMainDao expMainDao;

    @Autowired
    private ExpDetailDao expDetailDao;

    @Autowired
    private CompanyBillDao companyBillDao;

    @Autowired
    private ExpFixedDao expfixedDao;

    @Autowired
    private CompanyBillRelationDao companyBillRelationDao;

    @Autowired
    private CompanyBillDetailDao companyBillDetailDao;

    @Override
    public int saveCompanyBill(SaveCompanyBillDTO dto) throws Exception {
        //保存主表数据
        CompanyBillEntity bill = new CompanyBillEntity();
        BaseDTO.copyFields(dto,bill);
        bill.initEntity();
        bill.setDeleted(0);
        ProjectEntity project =  projectDao.selectById(dto.getProjectId());
        bill.setOperatorName(companyUserDao.getUserName(dto.getOperatorId()));
        bill.setProjectName(project!=null?project.getProjectName():null);
        bill.setPayeeName(companyDao.getCompanyName(dto.getToCompanyId()));
        bill.setPayerName(companyDao.getCompanyName(dto.getFromCompanyId()));
        if(dto.getFeeType()==5 || dto.getFeeType()==6){//报销，费用
            this.saveExpFee(dto,bill);
        }else if(dto.getFeeType() ==7){ //固定支出
            this.saveFixFee(dto,bill);
        }else {
            if(dto.getFeeType()==1){
                //合同回款的付款方为甲方
                bill.setPayerName(this.projectDao.getEnterpriseNameByProjectId(dto.getProjectId()));
                dto.setFromCompanyId(project!=null?project.getConstructCompany():null);
            }
            this.saveProjectFee(dto,bill);
        }
        if(!StringUtil.isNullOrEmpty(bill.getPaymentDate())){
            bill.setPaymentDate(DateUtils.formatDateString(bill.getPaymentDate()));
        }
        //查询该记录是否已经存在，若存在则不添加，防止网络差，引起前端反应慢，用户多点的情形
        if(bill.getFeeType()!= CompanyBillType.FEE_TYPE_EXP_FIX && !CollectionUtils.isEmpty(companyBillDao.getCompanyBillByTargetId(dto))){
            return 0;
        }
        companyBillDao.insert(bill);
        //保存关联的字段
        CompanyBillRelationEntity billRelation = new CompanyBillRelationEntity();
        BaseDTO.copyFields(dto,billRelation);
        billRelation.initEntity();
        billRelation.setId(bill.getId());//必须设置bill.getId(),主键关联
        return companyBillRelationDao.insert(billRelation);
    }


    private void saveExpFee(SaveCompanyBillDTO dto,CompanyBillEntity bill) throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("id", dto.getTargetId());
        ExpMainDataDTO exp = expMainDao.selectByIdWithUserName(param);
        List<ExpDetailEntity> detailList = expDetailDao.selectByMainId(dto.getTargetId());
        BigDecimal sum = new BigDecimal("0");
        String projectName = "";
        for (ExpDetailEntity detail:detailList){
            CompanyBillDetailEntity billDetail = new CompanyBillDetailEntity();
            billDetail.initEntity();
            billDetail.setBillId(bill.getId());
            billDetail.setFee(detail.getExpAmount()==null?new BigDecimal("0"):detail.getExpAmount());
            billDetail.setExpTypeParentName(detail.getExpPName());
            billDetail.setExpTypeName(detail.getExpName());
            billDetail.setFeeDescription(detail.getExpUse());
            billDetail.setSeq(detail.getSeq());
            if(!StringUtil.isNullOrEmpty(detail.getProjectId())){
                billDetail.setProjectName(projectDao.getProjectName(detail.getProjectId()));
                projectName+= billDetail.getProjectName()+",";
            }
            sum = sum.add(billDetail.getFee());
            companyBillDetailDao.insert(billDetail);
        }
        bill.setFee(sum);
        bill.setProjectName(StringUtil.isNullOrEmpty(projectName)?null:projectName.substring(0,projectName.length()-1));
        bill.setBillDescription(exp.getUserName()+"的"+(exp.getType()==1?"报销单":"费用申请单"));
        bill.setPayeeName(exp.getUserName());
        bill.setFeeUnit(2);
    }

    private void saveProjectFee(SaveCompanyBillDTO dto,CompanyBillEntity bill) throws Exception {
        CompanyBillDetailEntity billDetail = new CompanyBillDetailEntity();
        billDetail.initEntity();
        billDetail.setBillId(bill.getId());
        billDetail.setProjectName(bill.getProjectName());
        billDetail.setFee(bill.getFee().multiply(new BigDecimal("10000")));
        billDetail.setFeeDescription(bill.getBillDescription());
        if(dto.getPayType()==CompanyBillType.DIRECTION_PAYEE){
            billDetail.setExpTypeParentName("主营业务收入");
        }else {
            billDetail.setExpTypeParentName("直接项目成本");
        }
        switch (bill.getFeeType()){
            case 1:
                billDetail.setExpTypeName("合同回款");
                break;
            case 2:
                billDetail.setExpTypeName("技术审查费");
                break;
            case 3:
                billDetail.setExpTypeName("合作设计费");
                break;
            case 4:
                billDetail.setExpTypeName("其他收支");
                break;
            default:
                ;
        }
        companyBillDetailDao.insert(billDetail);
        bill.setFeeUnit(2);
        bill.setFee(bill.getFee().multiply(new BigDecimal("10000")));
    }

    private void saveFixFee(SaveCompanyBillDTO dto,CompanyBillEntity bill) throws Exception {
//        List<ExpFixedDataDTO> detailList = expfixedDao.getExpFixedByExpDate(dto.getCompanyId(),dto.getPaymentDate());
//        BigDecimal sum = new BigDecimal("0");
//        //先删除原来的数据
//        deleteCompanyBillForFixData(dto);
//        for (ExpFixedDataDTO detail:detailList){
//            CompanyBillDetailEntity billDetail = new CompanyBillDetailEntity();
//            billDetail.initEntity();
//            billDetail.setBillId(bill.getId());
//            billDetail.setFee(detail.getExpAmount()==null?new BigDecimal("0"):detail.getExpAmount());
//            billDetail.setExpTypeParentName(detail.getExpTypeParentName());
//            billDetail.setExpTypeName(detail.getExpTypeName());
//            billDetail.setFeeDescription("固定支出");
//            billDetail.setSeq(detail.getSeq());
//            sum = sum.add(billDetail.getFee());
//            companyBillDetailDao.insert(billDetail);
//        }
//        bill.setFee(sum);
//        bill.setBillDescription("规定支出");
//        bill.setFeeUnit(2);
    }

    private void deleteCompanyBillForFixData(SaveCompanyBillDTO dto) throws Exception{
        List<CompanyBillEntity> list = companyBillDao.getCompanyBill(dto);
        if(!CollectionUtils.isEmpty(list)){
            CompanyBillEntity bill = list.get(0);//理论上只存在一条
            companyBillDao.deleteById(bill.getId());
            companyBillRelationDao.deleteById(bill.getId());
            companyBillDetailDao.deleteCompanyBillDetailByBillId(bill.getId());
        }
    }
}
