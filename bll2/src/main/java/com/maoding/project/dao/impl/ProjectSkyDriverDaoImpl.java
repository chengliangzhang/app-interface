package com.maoding.project.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.project.dao.ProjectSkyDriverDao;
import com.maoding.project.dto.NetFileDTO;
import com.maoding.project.entity.ProjectSkyDriveEntity;
import com.maoding.v2.project.dto.ProjectSkyDriveListDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：ProjectSkyDriverDaoImpl
 * 类描述：项目文件磁盘
 * 作    者：MaoSF
 * 日    期：2016/7/22 16:50
 */
@Service("projectSkyDriverDao")
public class ProjectSkyDriverDaoImpl extends GenericDao<ProjectSkyDriveEntity> implements ProjectSkyDriverDao {

    @Override
    public ProjectSkyDriveEntity getSkyDriveByPidAndFileName(String pid, String fileName, String projectId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("pid",pid);
        map.put("fileName",fileName);
        map.put("projectId",projectId);
        return this.sqlSession.selectOne("ProjectSkyDriveEntityMapper.getSkyDriveByPidAndFileName",map);
    }

    /**
     * 方法描述：查询文件id为pid的所有文件及文件夹
     * 作者：MaoSF
     * 日期：2016/12/18
     */
    @Override
    public List<ProjectSkyDriveEntity> getSkyDriveByPid(String pid, String projectId) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("pid",pid);
        map.put("projectId",projectId);
        return this.sqlSession.selectList("ProjectSkyDriveEntityMapper.getSkyDriveByPid",map);
    }

    /**
     * 方法描述：根据项目id，父级id，文件名查询
     * 作者：MaoSF
     * 日期：2016/12/18
     */
    @Override
    public ProjectSkyDriveEntity getSkyDriveByTaskId(String taskId) {
        return this.sqlSession.selectOne("ProjectSkyDriveEntityMapper.getSkyDriveByTaskId",taskId);
    }


    /**
     * 方法描述：查询文件(包含子文件个数)
     * 作者：MaoSF
     * 日期：2016/12/18
     */
    @Override
    public List<ProjectSkyDriveListDTO> getSkyDrive(Map<String, Object> map) {
        return this.sqlSession.selectList("ProjectSkyDriveEntityMapper.getSkyDrive",map);
    }


    /**
     * 方法描述：获取所有
     * 作者：MaoSF
     * 日期：2017/1/16
     */
    @Override
    public List<ProjectSkyDriveListDTO> getProjectUploadFile(String projectId) {
        return this.sqlSession.selectList("ProjectSkyDriveEntityMapper.getProjectUploadFile",projectId);
    }

    /**
     * 方法描述：查询公司是否存在“设计成果”中的文件
     * 作者：MaoSF
     * 日期：2017/4/12
     */
    @Override
    public List<ProjectSkyDriveEntity> getProjectSkyByCompanyId(String projectId, String companyId) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("companyId",companyId);
        return this.sqlSession.selectList("ProjectSkyDriveEntityMapper.getProjectSkyByCompanyId",map);
    }

    /**
     * 方法描述：更改状态（用于删除）
     * 作者：MaoSF
     * 日期：2017/4/21
     */
    @Override
    public int updateSkyDriveStatus(Map<String,Object> map) {
        return this.sqlSession.update("ProjectSkyDriveEntityMapper.updateSkyDriveStatus",map);
    }

    /**
     * 方法描述：获取远程文件
     * 作者：MaoSF
     * 日期：2017/6/1
     */
    @Override
    public List<NetFileDTO> getNetFileByParam(Map<String, Object> map) {
        return this.sqlSession.selectList("ProjectSkyDriveEntityMapper.getNetFileByParam",map);
    }

    @Override
    public ProjectSkyDriveEntity getOwnerProject(Map<String, Object> map) {
        return this.sqlSession.selectOne("ProjectSkyDriveEntityMapper.getOwnerProject", map);
    }

    @Override
    public List<ProjectSkyDriveEntity> getDirectoryDTOList(Map<String, Object> map) {
        return this.sqlSession.selectList("ProjectSkyDriveEntityMapper.getDirectoryDTOList", map);
    }
}
