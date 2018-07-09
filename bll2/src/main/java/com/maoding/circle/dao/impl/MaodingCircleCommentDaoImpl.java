package com.maoding.circle.dao.impl;

import com.maoding.circle.dao.MaodingCircleCommentDao;
import com.maoding.circle.dto.MaodingCircleCommentDTO;
import com.maoding.circle.dto.MaodingCircleCommentDataDTO;
import com.maoding.circle.entity.MaodingCircleCommentEntity;
import com.maoding.core.base.dao.GenericDao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Creator: sandy
 * Date:2017/11/14
 * 类名：app-interface
 */
@Service("maodingCircleCommentDao")
public class MaodingCircleCommentDaoImpl extends GenericDao<MaodingCircleCommentEntity> implements MaodingCircleCommentDao{

    @Override
    public List<MaodingCircleCommentDataDTO> getCircleCommentByCircleId(String circleId) {
        return this.sqlSession.selectList("GetMaodingCircleMapper.getAllCommentByCircleId",circleId);
    }

    @Override
    public MaodingCircleCommentDataDTO getCircleCommentById(String commentId) {
        return this.sqlSession.selectOne("GetMaodingCircleMapper.getCircleCommentById",commentId);
    }
}
