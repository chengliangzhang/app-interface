package com.maoding.project.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.project.entity.ProjectMajorEntity;

import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectService
 * 类描述：Service
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午5:28:54
 */
public interface ProjectMajorService extends BaseService<ProjectMajorEntity>{

    public List<ProjectMajorEntity> getProjectMajorByCompanyId(String companyId) throws Exception;



}
