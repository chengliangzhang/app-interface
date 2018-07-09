package com.maoding.partner.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.partner.dao.PartnerDao;
import com.maoding.partner.dto.PartnerQueryDTO;
import com.maoding.partner.entity.PartnerEntity;
import com.maoding.project.dto.ProjectPartnerDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Chengliang.zhang on 2017/5/8.
 */
@Service("organizationDao")
public class PartnerDaoImpl extends GenericDao<PartnerEntity> implements PartnerDao {
    /**
     * 获取项目合伙人列表
     * @param dto 合伙人查找条件
     *            必填字段：fromCompanyId,projectId
     */
    @Override
    public List<ProjectPartnerDTO> getProjectPartnerList(PartnerQueryDTO dto) {
        return this.sqlSession.selectList("GetProjectPartnerMapper.getProjectPartnerList",dto);
    }

}
