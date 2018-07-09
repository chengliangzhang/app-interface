package com.maoding.partner.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.partner.dto.PartnerQueryDTO;
import com.maoding.partner.entity.PartnerEntity;
import com.maoding.project.dto.ProjectPartnerDTO;

import java.util.List;

/**
 * Created by Chengliang.zhang on 2017/5/6.
 */
public interface PartnerDao extends BaseDao<PartnerEntity> {
    /**
     * 获取项目合伙人列表
     */
    List<ProjectPartnerDTO> getProjectPartnerList(PartnerQueryDTO dto);
}
