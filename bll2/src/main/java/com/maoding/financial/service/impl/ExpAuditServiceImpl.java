package com.maoding.financial.service.impl;

import com.maoding.core.base.service.GenericService;
import com.maoding.financial.dao.ExpAuditDao;
import com.maoding.financial.dao.ExpMainDao;
import com.maoding.financial.dto.AuditDTO;
import com.maoding.financial.entity.ExpAuditEntity;
import com.maoding.financial.entity.ExpMainEntity;
import com.maoding.financial.service.ExpAuditService;
import com.maoding.org.dao.CompanyUserDao;
import com.maoding.org.entity.CompanyUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名 : ExpAuditServiceImpl
 * 描    述 : 报销审核Serviceimpl
 * 作    者 : LY
 * 日    期 : 2016/7/26-16:03
 */

@Service("expAuditService")
public class ExpAuditServiceImpl extends GenericService<ExpAuditEntity> implements ExpAuditService{

    @Autowired
    private ExpAuditDao expAuditDao;

    @Autowired
    private ExpMainDao expMainDao;

    @Autowired
    private CompanyUserDao companyUserDao;

    @Override
    public String getAuditPerson(String mainId,String accountId) {
        //查询报销单申请人
//        ExpMainEntity main = expMainDao.selectById(mainId);
//        String ignoreUserId = accountId;
//        if(main!=null){
//            CompanyUserEntity applyUser = companyUserDao.selectById(main.getCompanyUserId());
//            if(applyUser!=null){
//                ignoreUserId +=","+applyUser.getUserId();
//            }
//            Map<String,Object> map = new HashMap<>();
//            map.put("id",mainId);
//            List<AuditDTO> auditList = this.expAuditDao.selectAuditByMainId(map);
//            for(AuditDTO audit:auditList){
//                ignoreUserId +=","+audit.getAccountId();
//            }
//        }

        ExpMainEntity main = expMainDao.selectById(mainId);
        String ignoreUserId = accountId;
        if(main!=null){
            CompanyUserEntity applyUser = companyUserDao.selectById(main.getCompanyUserId());
            if(applyUser!=null){ //提交并继续，过滤申请人
                ignoreUserId = applyUser.getUserId();
            }
        }else { //如果是新增，则过来当前人，也就是报销人
            return accountId;
        }
        return ignoreUserId;
    }
}
