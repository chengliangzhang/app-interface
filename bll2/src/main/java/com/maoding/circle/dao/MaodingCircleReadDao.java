package com.maoding.circle.dao;

import com.maoding.circle.dto.MaodingCircleDataDTO;
import com.maoding.circle.dto.MaodingCircleReadDTO;
import com.maoding.circle.dto.QueryCircleDTO;
import com.maoding.circle.entity.MaodingCircleEntity;
import com.maoding.circle.entity.MaodingCircleReadEntity;
import com.maoding.core.base.dao.BaseDao;

import java.util.List;

/**
 * Creator: sandy
 * Date:2017/11/14
 * 类名：app-interface
 */
public interface MaodingCircleReadDao extends BaseDao<MaodingCircleReadEntity>{

    List<MaodingCircleReadDTO> getNotReadCircle(QueryCircleDTO query);

    int updateCircleReadStatus(QueryCircleDTO query);
}
