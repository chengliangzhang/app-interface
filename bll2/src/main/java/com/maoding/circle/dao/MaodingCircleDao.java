package com.maoding.circle.dao;

import com.maoding.circle.dto.MaodingCircleDataDTO;
import com.maoding.circle.dto.QueryCircleDTO;
import com.maoding.circle.entity.MaodingCircleEntity;
import com.maoding.core.base.dao.BaseDao;

import java.util.List;

/**
 * Creator: sandy
 * Date:2017/11/14
 * 类名：app-interface
 */
public interface MaodingCircleDao extends BaseDao<MaodingCircleEntity>{

    List<MaodingCircleDataDTO> listMaodingCircle(QueryCircleDTO query);

    MaodingCircleDataDTO getMaodingCircleById(QueryCircleDTO query);

    String getPartInCompany(QueryCircleDTO query);
}
