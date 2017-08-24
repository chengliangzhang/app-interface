package com.maoding.project.dao.impl;


import com.maoding.core.base.dao.GenericDao;
import com.maoding.org.dto.CompanyUserAppDTO;
import com.maoding.project.dao.ProjectTaskResponsibleDao;
import com.maoding.project.dto.ProjectTaskResponsibleDTO;
import com.maoding.project.entity.ProjectTaskResponsiblerEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectReceivePointDao
 * 类描述：服务内容
 * 作    者：wangrb
 * 日    期：2015年8月14日-上午10:13:28
 */
@Service("projectTaskResponsibleDao")
public class ProjectTaskResponsibleDaoImpl extends GenericDao<ProjectTaskResponsiblerEntity> implements ProjectTaskResponsibleDao {


    /**
     * 方法描述：获取当前项目在当前公司是否存在设计人
     * 作者：MaoSF
     * 日期：2016/11/1
     *
     * @param param
     * @param:
     * @return:
     */
    @Override
    public int getProjectMajorPersonByProjectCount(Map<String, Object> param) {
        return this.sqlSession.selectOne("GetProjectResponsiblePersonMapper.getProjectMajorPersonByProjectCount",param);
    }

    /**
     * 方法描述：根据项目获取专业技术人
     * 作者：MaoSF
     * 日期：2016/10/26
     *
     * @param param
     * @param:（companyId,projectId,majorId)
     * @return:
     */
    @Override
    public String getProjectTaskResponsibleByProject(Map<String, Object> param) {
        return this.sqlSession.selectOne("GetProjectTaskResponsibleMapper.getProjectTaskResponsibleByProject",param);
    }

    /**
     * 方法描述：根据项目或去专业技术人
     * 作者：MaoSF
     * 日期：2016/10/26
     *
     * @param param
     * @param:（companyId,projectId,majorId)
     * @return:
     */
    @Override
    public List<ProjectTaskResponsibleDTO> getProjectResponsiblePersonByProject(Map<String, Object> param) {
        return this.sqlSession.selectList("GetProjectTaskResponsibleMapper.getProjectResponsiblePersonByProject",param);
    }

    /**
     * 方法描述：根据项目获取项目负责人
     * 作者：MaoSF
     * 日期：2016/10/26
     *
     * @param param
     * @param:（companyId,projectId,type,majorId(可以没有))
     * @return:
     */
    @Override
    public int deleteProjectResponsiblePerson(Map<String, Object> param) {
        return this.sqlSession.delete("ProjectTaskResponsiblerEntityMapper.deleteProjectResponsiblePerson",param);
    }

    /**
     * 方法描述：获取项目的负责人（把company_user_id拼接成字符串返回，用于任务判断权限）
     * 作者：MaoSF
     * 日期：2016/12/23
     *
     * @param projectId
     * @param companyId
     * @param:
     * @return:
     */
    @Override
    public String getProjectResponsiblePersonIdToString(String projectId, String companyId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("projectId",projectId);
        map.put("companyId",companyId);
        return this.sqlSession.selectOne("GetProjectTaskResponsibleMapper.getProjectResponsiblePersonIdToString",map);
    }

    /**
     * 方法描述：根据项目获取专业技术人(根据任务id，追溯到根任务。查询专业负责人)，用于任务权限判断
     * 作者：MaoSF
     * 日期：2016/10/26
     *
     * @param param
     * @param:（companyId,projectId,taskManageId)
     * @return:
     */
    @Override
    public Map<String, String> getProjectMajorPersonByTaskManageList(Map<String, Object> param) {
        return this.sqlSession.selectOne("GetProjectTaskResponsibleMapper.getProjectMajorPersonByTaskManageList",param);
    }

    /**
     * 方法描述：根据参数获取专业技术人或者负责人
     * 作者：TangY
     * 日期：2016/10/26
     * @param:（companyId,projectId,taskManageId，type，id)
     * @return:
     */
    public List<ProjectTaskResponsiblerEntity> getProjectResponsiblePersonByParam(Map<String,Object> param){
        return this.sqlSession.selectList("ProjectTaskResponsiblerEntityMapper.selectByParam",param);
    }

    //**********************v2*************************//
    /**
     * 方法描述：获取任务的负责人
     * 作者：MaoSF
     * 日期：2016/12/31
     * @param:taskId
     * @return:
     */
    @Override
    public Map<String,String> getResponsiblerByTaskId(String taskId) {
        return this.sqlSession.selectOne("GetProjectTaskResponsibleMapper.getResponsiblerByTaskId",taskId);
    }

    /**
     * 方法描述：查询任务负责人（项目参与人使用）
     * 作者：MaoSF
     * 日期：2017/2/9
     *
     * @param param
     * @param:
     * @return:
     */
    @Override
    public List<CompanyUserAppDTO> getResponsiblerByTaskPath(Map<String, Object> param) {
        return this.sqlSession.selectList("GetProjectParticipationMapper.getResponsiblerByTaskPath",param);
    }

    /**
     * 方法描述：查询任务流程人员（项目参与人使用）
     * 作者：MaoSF
     * 日期：2017/2/9
     *
     * @param param
     * @param:
     * @return:
     */
    @Override
    public List<CompanyUserAppDTO> getProcessUserByTaskPath(Map<String, Object> param) {
        return this.sqlSession.selectList("GetProjectParticipationMapper.getProcessUserByTaskPath",param);
    }
}
