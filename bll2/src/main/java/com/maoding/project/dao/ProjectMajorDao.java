package com.maoding.project.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.project.entity.ProjectMajorEntity;

import java.util.List;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectDao
 * 类描述：项目（dao）
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午5:20:47
 */
public interface ProjectMajorDao extends BaseDao<ProjectMajorEntity> {

    /**
     * 方法描述：根据公司id获取专业
     * 作者：MaoSF
     * 日期：2016/10/26
     * @param:
     * @return:
     */
    public List<ProjectMajorEntity> getProjectMajorByCompanyId(String companyId);
}
