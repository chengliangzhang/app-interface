package com.maoding.project.dao;


import com.maoding.core.base.dao.BaseDao;
import com.maoding.project.entity.ProjectDesignContentDetailEntity;

import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectDesignContentDetailDao
 * 类描述：设计阶段详情（dao）
 * 作    者：MaoSF
 * 日    期：2016年8月25日-下午5:20:47
 */
public interface ProjectDesignContentDetailDao extends BaseDao<ProjectDesignContentDetailEntity> {
    /**
     * 方法描述：根据参数查询设计阶段
     * 作        者：ChenZJ
     * 日        期：2016年7月21日-上午11:28:56
     * @param designContentId
     * @return
     */
    public List<ProjectDesignContentDetailEntity> selectByDesignContentId(String designContentId);
    
    /**
     * 方法描述：根据设计阶段id删除变更信息
     * 作者：MaoSF
     * 日期：2016/9/30
     * @param:
     * @return:
     */
    public int deleteByDesignContentId(String designContentId);
    
}
