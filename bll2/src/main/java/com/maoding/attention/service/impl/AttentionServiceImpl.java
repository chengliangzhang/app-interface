package com.maoding.attention.service.impl;

import com.maoding.attention.dao.AttentionDao;
import com.maoding.attention.dto.AttentionDTO;
import com.maoding.attention.entity.AttentionEntity;
import com.maoding.attention.service.AttentionService;
import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.util.StringUtil;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.entity.CompanyUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("attentionService")
public class AttentionServiceImpl extends GenericService<AttentionEntity> implements AttentionService {

    @Autowired
    private AttentionDao attentionDao;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Override
    public ResponseBean addAttention(AttentionDTO dto) throws Exception{
        dto.setId(StringUtil.buildUUID());
        AttentionEntity attentionEntity = new AttentionEntity();
        BaseDTO.copyFields(dto, attentionEntity);
        attentionEntity.setCompanyId(dto.getAppOrgId());
        attentionEntity.setCreateBy(dto.getAccountId());
        CompanyUserEntity companyUserEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(),dto.getAppOrgId());
        if(companyUserEntity!=null){
            attentionEntity.setCompanyUserId(companyUserEntity.getId());
            int res = attentionDao.insert(attentionEntity);
            if(res==1){
                return ResponseBean.responseSuccess("关注成功").addData("id",dto.getId());
            }
        }
        return ResponseBean.responseError("关注失败");
    }

    @Override
    public ResponseBean cancelAttention(AttentionDTO dto) throws Exception {
        AttentionEntity attentionEntity = new AttentionEntity();
        BaseDTO.copyFields(dto, attentionEntity);
        attentionEntity.setCompanyId(dto.getAppOrgId());
        attentionEntity.setCreateBy(dto.getAccountId());
        CompanyUserEntity companyUserEntity = this.companyUserDao.getCompanyUserByUserIdAndCompanyId(dto.getAccountId(),dto.getAppOrgId());
        if(companyUserEntity!=null) {
            attentionEntity.setCompanyUserId(companyUserEntity.getId());
        }
        int res = attentionDao.cancelAttention(attentionEntity);
        if(res==1){
            return ResponseBean.responseSuccess("取消关注成功");
        }else {
            return ResponseBean.responseError("取消关注失败");
        }
    }
}
