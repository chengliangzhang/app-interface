package com.maoding.statistic.dao.impl;

import com.maoding.statistic.dao.StatisticDao;
import com.maoding.statistic.dto.CompanyStatisticDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Idccapp22 on 2016/10/17.
 */
@Service("statisticDao")
public class StatisticDaoImpl implements StatisticDao{
    @Autowired
    protected SqlSessionTemplate sqlSession;
    /**
     * 方法描述：公司人数统计
     * 作者：MaoSF
     * 日期：2016/10/17
     * @param:
     * @return:
     */
    public List<CompanyStatisticDTO> getCompanyStatisticList(Map<String, Object> param){
        return sqlSession.selectList("GetCompanyStatisticMapper.getCompanyStatisticList",param);
    }

    /**
     * 方法描述：公司人数统计 总条数
     * 作者：MaoSF
     * 日期：2016/10/17
     *
     * @param param
     * @param:
     * @return:
     */
    @Override
    public int getCompanyStatisticCount(Map<String, Object> param) {
        return sqlSession.selectOne("GetCompanyStatisticMapper.getCompanyStatisticCount");
    }
}
