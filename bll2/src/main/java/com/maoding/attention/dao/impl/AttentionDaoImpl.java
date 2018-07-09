package com.maoding.attention.dao.impl;

import com.maoding.attention.dao.AttentionDao;
import com.maoding.attention.entity.AttentionEntity;
import com.maoding.core.base.dao.GenericDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;


@Service("attentionDao")
public class AttentionDaoImpl extends GenericDao<AttentionEntity> implements AttentionDao {
    protected final Logger log= LoggerFactory.getLogger(getClass());

    @Override
    public int cancelAttention(AttentionEntity dto) {
        return this.sqlSession.delete("AttentionEntityMapper.cancelAttention",dto);
    }


    public AttentionEntity getAttentionEntity(Map<String,Object> paraMap){
        List<AttentionEntity> list = this.sqlSession.selectList("AttentionEntityMapper.selectByParam",paraMap);
        if(!CollectionUtils.isEmpty(list)){
            if(list.size()>1){
                //存在错误数据
                try{
                    // log.error(getClass()+"：存在错误数据，参数："+param.);
                }catch (Exception e){

                }
            }
            return list.get(0);
        }
        return null;
    }

}
