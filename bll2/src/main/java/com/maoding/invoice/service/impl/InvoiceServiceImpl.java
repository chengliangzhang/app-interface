package com.maoding.invoice.service.impl;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.NewBaseService;
import com.maoding.core.util.BeanUtils;
import com.maoding.core.util.StringUtil;
import com.maoding.enterprise.service.EnterpriseService;
import com.maoding.invoice.dao.InvoiceDao;
import com.maoding.invoice.dto.InvoiceEditDTO;
import com.maoding.invoice.dto.InvoiceInfoDTO;
import com.maoding.invoice.entity.InvoiceEntity;
import com.maoding.invoice.service.InvoiceService;
import com.maoding.org.dao.CompanyDao;
import com.maoding.org.entity.CompanyEntity;
import com.maoding.projectcost.dto.ProjectCostDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("invoiceService")
public class InvoiceServiceImpl extends NewBaseService implements InvoiceService {

    @Autowired
    private InvoiceDao invoiceDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private EnterpriseService enterpriseService;

    @Override
    public String saveInvoice(InvoiceEditDTO dto) throws Exception{
        InvoiceEntity invoice = (InvoiceEntity) BaseDTO.copyFields(dto, InvoiceEntity.class);
        if (StringUtil.isNullOrEmpty(dto.getId())) {
            invoice.initEntity();
            invoice.setDeleted(0);//设置为有效
            invoiceDao.insert(invoice);
        } else {
            invoiceDao.updateById(invoice);
        }
        return invoice.getId();
    }

    @Override
    public InvoiceInfoDTO getInvoice(String invoiceId) {
        InvoiceInfoDTO invoiceInfo = new InvoiceInfoDTO();
        InvoiceEntity invoice = invoiceDao.selectById(invoiceId);
        if(invoice!=null){
            BeanUtils.copyProperties(invoice,invoiceInfo);
            invoiceInfo.setRelationCompanyName(this.getRelationCompanyName(invoice.getRelationCompanyId(),invoice.getRelationCompanyName()));
        }
        return invoiceInfo;
    }

    @Override
    public String getInvoiceReceiveCompanyName(String invoiceId) {
        InvoiceInfoDTO invoiceInfo = this.getInvoice(invoiceId);
        if(invoiceInfo!=null){
            return invoiceInfo.getRelationCompanyName();
        }
        return null;
    }

    /**
     * 获取立项方组织的名称
     */
    private String getRelationCompanyName(String relationCompanyId,String relationCompanyName){
        if(StringUtil.isNullOrEmpty(relationCompanyId)){
            return relationCompanyName;
        }else {
            CompanyEntity companyEntity = null;
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
    }
}
