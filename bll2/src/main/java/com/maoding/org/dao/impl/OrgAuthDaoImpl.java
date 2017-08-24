package com.maoding.org.dao.impl;


import com.maoding.core.base.dao.GenericDao;
import com.maoding.org.dao.OrgAuthDao;
import com.maoding.org.dto.OrgAuthenticationDataDTO;
import com.maoding.org.entity.OrgAuthEntity;
import org.springframework.stereotype.Service;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：OrgAuthenticationDaoImpl
 * 类描述：组织审核dao
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午3:44:06
 */
@Service("orgAuthDao")
public class OrgAuthDaoImpl extends GenericDao<OrgAuthEntity> implements OrgAuthDao {

    @Override
    public OrgAuthenticationDataDTO getOrgAuthenticationInfo(String id) {
        return this.sqlSession.selectOne("GetOrgAuthMapper.getOrgAuthenticationInfo",id);
    }
}
