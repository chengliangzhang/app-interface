package com.maoding.project.service.impl;

import com.maoding.core.base.service.GenericService;
import com.maoding.project.dao.ProjectMajorDao;
import com.maoding.project.entity.ProjectMajorEntity;
import com.maoding.project.service.ProjectMajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectService
 * 类描述：项目Service
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午5:28:54
 */
@Service("projectMajorService")
public class ProjectMajorServiceImpl extends GenericService<ProjectMajorEntity>  implements ProjectMajorService {

    @Autowired
    private ProjectMajorDao projectMajorDao;

    @Override
    public List<ProjectMajorEntity> getProjectMajorByCompanyId(String companyId) throws Exception {
        return projectMajorDao.getProjectMajorByCompanyId(companyId);
    }

}
