package com.maoding.project.dao.impl;



import com.maoding.core.base.dao.GenericDao;
import com.maoding.project.dao.ProjectMajorDao;
import com.maoding.project.entity.ProjectMajorEntity;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectReceivePointDao
 * 类描述：服务内容
 * 作    者：wangrb
 * 日    期：2015年8月14日-上午10:13:28
 */
@Service("projectMajorDao")
public class ProjectMajorDaoImpl extends GenericDao<ProjectMajorEntity> implements ProjectMajorDao {


    /**
     * 方法描述：根据公司id获取专业
     * 作者：MaoSF
     * 日期：2016/10/26
     *
     * @param companyId
     * @param:
     * @return:
     */
    @Override
    public List<ProjectMajorEntity> getProjectMajorByCompanyId(String companyId) {
        return this.sqlSession.selectList("ProjectMajorEntityMapper.getProjectMajorByCompanyId",companyId);
    }
}
