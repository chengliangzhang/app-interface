package com.maoding.project.dao;

import com.maoding.core.base.dao.BaseDao;
import com.maoding.org.dto.CompanyUserAppDTO;
import com.maoding.project.dto.ProjectTaskResponsibleDTO;
import com.maoding.project.entity.ProjectTaskResponsiblerEntity;

import java.util.List;
import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectDao
 * 类描述：项目（dao）
 * 作    者：ChenZJ
 * 日    期：2016年7月19日-下午5:20:47
 */
public interface ProjectTaskResponsibleDao extends BaseDao<ProjectTaskResponsiblerEntity> {

    /**
     * 方法描述：获取当前项目在当前公司是否存在设计人
     * 作者：MaoSF
     * 日期：2016/11/1
     * @param:
     * @return:
     */
    int getProjectMajorPersonByProjectCount(Map<String, Object> param);
    /**
     * 方法描述：根据项目获取专业技术人
     * 作者：MaoSF
     * 日期：2016/10/26
     * @param:（companyId,projectId,majorId)
     * @return:
     */
    String getProjectTaskResponsibleByProject(Map<String, Object> param);

    /**
     * 方法描述：根据项目获取项目负责人
     * 作者：MaoSF
     * 日期：2016/10/26
     * @param:（companyId,projectId)
     * @return:
     */
    List<ProjectTaskResponsibleDTO> getProjectResponsiblePersonByProject(Map<String, Object> param);

    /**
     * 方法描述：根据项目获取项目负责人
     * 作者：MaoSF
     * 日期：2016/10/26
     * @param:（companyId,projectId,type,majorId(可以没有))
     * @return:
     */
    int deleteProjectResponsiblePerson(Map<String, Object> param);

    /**
     * 方法描述：获取项目的负责人（把company_user_id拼接成字符串返回，用于任务判断权限）
     * 作者：MaoSF
     * 日期：2016/12/23
     * @param:
     * @return:
     */
    String getProjectResponsiblePersonIdToString(String projectId, String companyId);

    /**
     * 方法描述：根据项目获取专业技术人(根据任务id，追溯到根任务。查询专业负责人)，用于任务权限判断
     * 作者：MaoSF
     * 日期：2016/10/26
     * @param:（companyId,projectId,taskManageId)
     * @return:
     */
    Map<String,String> getProjectMajorPersonByTaskManageList(Map<String, Object> param);

    /**
     * 方法描述：根据参数获取专业技术人或者负责人
     * 作者：TangY
     * 日期：2016/10/26
     * @param:（companyId,projectId,taskManageId，type，id)
     * @return:
     */
    List<ProjectTaskResponsiblerEntity> getProjectResponsiblePersonByParam(Map<String, Object> param);

    //***************************v2*****************************//

    /**
     * 方法描述：获取任务的负责人
     * 作者：MaoSF
     * 日期：2016/12/31
     * @param:
     * @return:
     */
    Map<String,String> getResponsiblerByTaskId(String taskId);
    
    /**
     * 方法描述：查询任务负责人（项目参与人使用）
     * 作者：MaoSF
     * 日期：2017/2/9
     * @param:
     * @return:
     */
    List<CompanyUserAppDTO> getResponsiblerByTaskPath(Map<String, Object> param);

    /**
     * 方法描述：查询任务流程人员（项目参与人使用）
     * 作者：MaoSF
     * 日期：2017/2/9
     * @param:
     * @return:
     */
    List<CompanyUserAppDTO> getProcessUserByTaskPath(Map<String, Object> param);


}
