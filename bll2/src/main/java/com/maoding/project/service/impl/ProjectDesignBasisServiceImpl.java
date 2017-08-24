package com.maoding.project.service.impl;
import com.maoding.core.base.service.GenericService;
import com.maoding.project.dao.ProjectDesignBasisDao;
import com.maoding.project.dto.ProjectDesignBasisDTO;
import com.maoding.project.entity.ProjectDesignBasisEntity;
import com.maoding.project.service.ProjectDesignBasisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：DesignBasisServiceImpl
 * 类描述：设计依据
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午5:28:54
 */
@Service("projectDesignBasisService")
public class ProjectDesignBasisServiceImpl extends GenericService<ProjectDesignBasisEntity> implements ProjectDesignBasisService {
    @Autowired
    private ProjectDesignBasisDao projectDesignBasisDao;
    @Override
    public List<ProjectDesignBasisDTO> getDesignBasisByProjectId(String projectId) throws Exception {
        return projectDesignBasisDao.getDesignBasisByProjectId(projectId);
    }
}
