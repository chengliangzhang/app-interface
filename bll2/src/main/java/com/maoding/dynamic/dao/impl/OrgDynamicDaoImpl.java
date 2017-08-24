package com.maoding.dynamic.dao.impl;

import com.maoding.core.base.dao.GenericDao;
import com.maoding.dynamic.dao.OrgDynamicDao;
import com.maoding.dynamic.dto.OrgDynamicDataDTO;
import com.maoding.dynamic.entity.OrgDynamicEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("orgDynamicDao")
public class OrgDynamicDaoImpl extends GenericDao<OrgDynamicEntity> implements OrgDynamicDao {

    public List<OrgDynamicDataDTO> getOrgDynamicByParam(Map<String,Object> param){
        return this.sqlSession.selectList("GetOrgDynamicMapper.selectByParam",param);
    }

    /**
     * 根据参数查询动态
     *
     * @param param
     * @return
     */
    @Override
    public List<OrgDynamicEntity> getLastOrgDynamicByParam(Map<String, Object> param) {
        return this.sqlSession.selectList("OrgDynamicEntityMapper.getLastOrgDynamicByParam",param);
    }

    public int getOrgDynamicCountByParam(Map<String,Object> param){
        return this.sqlSession.selectOne("OrgDynamicEntityMapper.selectByParamCount",param);
    }

    @Override
    public int updatefield2ByTargetId(String targetId) {
        return this.sqlSession.update("OrgDynamicEntityMapper.updatefield2ByTargetId",targetId);
    }
}
