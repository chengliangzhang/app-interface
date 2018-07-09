package com.maoding.circle.dao;

import com.maoding.circle.dto.MaodingCircleCommentDTO;
import com.maoding.circle.dto.MaodingCircleCommentDataDTO;
import com.maoding.circle.entity.MaodingCircleCommentEntity;
import com.maoding.circle.entity.MaodingCircleEntity;
import com.maoding.core.base.dao.BaseDao;

import java.util.List;

/**
 * Creator: sandy
 * Date:2017/11/14
 * 类名：app-interface
 */
public interface MaodingCircleCommentDao extends BaseDao<MaodingCircleCommentEntity>{

    List<MaodingCircleCommentDataDTO> getCircleCommentByCircleId(String circleId);

    MaodingCircleCommentDataDTO getCircleCommentById(String commentId);
}
