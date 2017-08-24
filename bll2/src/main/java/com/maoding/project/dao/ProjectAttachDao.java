package com.maoding.project.dao;


import com.maoding.core.base.dao.BaseDao;
import com.maoding.project.entity.ProjectAttachEntity;

import java.util.List;
import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名 : ProjectAttachDao
 * 描    述 : 项目附件（dao）
 * 作    者 : LY
 * 日    期 : 2016/7/22 16:50
 */
public interface ProjectAttachDao extends BaseDao<ProjectAttachEntity> {

    /**
     * 方法描述：根据项目Id和附件类型删除
     * @param projectId 项目Id
     * @param fileType 附件类型(1.合同附件,2:设计依据附件，3，设计范围附件,4任务分解附件)
     * @return
     */
    public int deleteByProIdAndType(String projectId, String fileType);

    /**
     * 方法描述：根据项目Id和附件类型查询
     * 作   者：LY
     * 日   期：2016/7/25 10:58
     *
    */
    public List<ProjectAttachEntity> selectByType(String projectId,String fileType);

    /**
     * 方法描述：删除附件(根据target_id)
     * 作   者：LY
     * 日   期：2016/8/12 11:02
    */
    public int deleteProjectAttach(ProjectAttachEntity attach);

    /**
     * 方法描述：查询附件(根据target_id)
     * 作   者：LY
     * 日   期：2016/8/12 11:18
    */
    public List<ProjectAttachEntity> selectByTargetId(String targetId);
}
