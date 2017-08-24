package com.maoding.project.dao;


import com.maoding.core.base.dao.BaseDao;
import com.maoding.project.dto.ProjectDesignContentDTO;
import com.maoding.project.entity.ProjectDesignContentEntity;

import java.util.List;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectDcontentDao
 * 类描述：设计阶段（dao）
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午5:20:47
 */
public interface ProjectDesignContentDao extends BaseDao<ProjectDesignContentEntity> {
    /**
     * 方法描述：根据参数查询设计阶段
     * 作        者：ChenZJ
     * 日        期：2016年7月21日-上午11:28:56
     * @param projectId
     * @return
     */
    List<ProjectDesignContentEntity> getProjectDesignContentByProjectId(String projectId);

    /**
     * 方法描述：根据参数查询设计阶段
     * 作        者：ChenZJ
     * 日        期：2016年7月21日-上午11:28:56
     * @param projectId
     * @return
     */
    List<ProjectDesignContentDTO> getProjectDesignContent(String projectId);
    
    /**
     * 方法描述：根据项目Id删除设计阶段
     * 作   者：LY
     * 日   期：2016/7/22 16:21
     * @param  
     * @return 
     *
    */
    int deleteDesignContentByProjectId(String projectId);

    /**
     * 方法描述：获取最大的seq
     * 作者：MaoSF
     * 日期：2017/4/20
     * @param:
     * @return:
     */
    int getProjectContentMaxSeq(String projectId);
}
