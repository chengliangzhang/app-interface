package com.maoding.project.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.project.dao.ProjectDesignContentDetailDao;
import com.maoding.project.entity.ProjectDesignContentDetailEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectDesignContentDaoImpl
 * 类描述：设计阶段（daoImpl）
 * 作    者：MaoSF
 * 日    期：2016年8月25日-上午11:21:29
 */
@Service("projectDesignContentDetailDao")
public class ProjectDesignContentDetailDaoImpl extends GenericDao<ProjectDesignContentDetailEntity> implements ProjectDesignContentDetailDao {

    /**
     * 方法描述：根据参数查询设计阶段
     * 作        者：MaoSF
     * 日        期：2016年8月25日-上午11:21:29
     *
     * @param designContentId
     * @return
     */
    @Override
    public List<ProjectDesignContentDetailEntity> selectByDesignContentId(String designContentId) {
        return this.sqlSession.selectList("ProjectDesignContentDetailEntityMapper.selectByDesignContentId",designContentId);
    }

    /**
     * 方法描述：根据设计阶段id删除变更信息
     * 作者：MaoSF
     * 日期：2016/9/30
     *
     * @param designContentId
     * @param:
     * @return:
     */
    @Override
    public int deleteByDesignContentId(String designContentId) {
        return this.sqlSession.delete("ProjectDesignContentDetailEntityMapper.deleteByDesignContentId",designContentId);
    }
}


