package com.maoding.partner.service.impl;

import com.maoding.core.bean.ResponseBean;
import com.maoding.core.component.sms.SmsSender;
import com.maoding.core.component.sms.bean.Sms;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.StringUtil;
import com.maoding.org.dao.CompanyDao;
import com.maoding.org.dao.CompanyInviteDao;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.entity.CompanyEntity;
import com.maoding.org.entity.CompanyInviteEntity;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.partner.dao.PartnerDao;
import com.maoding.partner.dto.PartnerQueryDTO;
import com.maoding.partner.service.PartnerService;
import com.maoding.project.dto.ProjectPartnerDTO;
import com.maoding.project.service.ProjectSkyDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wrb on 2017/5/8.
 */
@Service("organizationService")
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    private PartnerDao partnerDao;

    @Autowired
    private CompanyInviteDao companyInviteDao;

    @Autowired
    private SmsSender smsSender;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Autowired
    private ProjectSkyDriverService projectSkyDriverService;

    @Override
    public List<ProjectPartnerDTO> getProjectPartnerList(PartnerQueryDTO dto) throws Exception{
        List<ProjectPartnerDTO> list = partnerDao.getProjectPartnerList(dto);
        for(ProjectPartnerDTO data:list){
            if(!StringUtil.isNullOrEmpty(data.getCompanyId())){
                data.setFileFullPath(projectSkyDriverService.getCompanyLogo(data.getCompanyId()));
            }
        }
        return list;
    }

    @Override
    public ResponseBean relieveRelationship(String id) {
        int res = 0;
        res = partnerDao.deleteById(id);
        if(res>0){
            return ResponseBean.responseSuccess("解除成功");
        }else {
            return ResponseBean.responseError("解除失败");
        }
    }

    @Override
    public ResponseBean resendSMS(String id, String currentUserId, String currentCompanyId) {
        CompanyInviteEntity companyInviteEntity = companyInviteDao.selectById(id);
        if(null!=companyInviteEntity){
            CompanyEntity companyEntity = companyDao.selectById(companyInviteEntity.getCompanyId());
            CompanyUserEntity companyUser = companyUserDao.getCompanyUserByUserIdAndCompanyId(currentUserId,currentCompanyId);
            if(companyUser==null || companyEntity==null){
                return ResponseBean.responseError("操作失败");
            }
            //发送信息
            Sms sms = new Sms();
            sms.addMobile(companyInviteEntity.getInviteCellphone());
            sms.setMsg(StringUtil.format(SystemParameters.INVITE_PARENT_MSG3,companyUser.getUserName(),companyEntity.getCompanyName(),companyInviteEntity.getUrl()));
            smsSender.send(sms);
        }
        return ResponseBean.responseSuccess("发送成功");
    }

}
