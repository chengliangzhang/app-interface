package com.maoding.project.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.project.dao.ProjectDesignContentDao;
import com.maoding.project.dto.ProjectDesignContentDTO;
import com.maoding.project.entity.ProjectDesignContentEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectDesignContentDaoImpl
 * 类描述：设计阶段（daoImpl）
 * 作    者：ChenZJ
 * 日    期：2016年7月7日-上午5:21:29
 */
@Service("projectDesignContentDao")
public class ProjectDesignContentDaoImpl extends GenericDao<ProjectDesignContentEntity> implements ProjectDesignContentDao {

    @Override
    public List<ProjectDesignContentEntity> getProjectDesignContentByProjectId(String projectId) {
        return this.sqlSession.selectList("ProjectDesignContentEntityMapper.getProjectDesignContentByProjectId", projectId);
    }

    /**
     * 方法描述：根据参数查询设计阶段
     * 作        者：ChenZJ
     * 日        期：2016年7月21日-上午11:28:56
     *
     * @param projectId
     * @return
     */
    @Override
    public List<ProjectDesignContentDTO> getProjectDesignContent(String projectId) {
        return this.sqlSession.selectList("ProjectDesignContentEntityMapper.getProjectDesignContent", projectId);
    }

    /**
     * 方法描述：根据项目Id删除设计阶段
     * 作   者：LY
     * 日   期：2016/7/22 16:21
     * @param
     * @return
     *
     */
    public int deleteDesignContentByProjectId(String projectId){
        return this.sqlSession.delete("ProjectDesignContentEntityMapper.deleteDesignContentByProjectId", projectId);
    }

    /**
     * 方法描述：获取最大的seq
     * 作者：MaoSF
     * 日期：2017/4/20
     *
     * @param projectId
     * @param:
     * @return:
     */
    @Override
    public int getProjectContentMaxSeq(String projectId) {
        return this.sqlSession.selectOne("ProjectDesignContentEntityMapper.getProjectContentMaxSeq", projectId);
    }
}


