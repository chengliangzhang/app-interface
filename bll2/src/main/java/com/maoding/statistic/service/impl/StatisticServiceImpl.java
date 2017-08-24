package com.maoding.statistic.service.impl;

import com.maoding.statistic.dao.StatisticDao;
import com.maoding.statistic.dto.CompanyStatisticDTO;
import com.maoding.statistic.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("statisticService")
public class StatisticServiceImpl implements StatisticService{

    @Autowired
    protected StatisticDao statisticDao;

    /**
     * 方法描述：公司人数统计
     * 作者：MaoSF
     * 日期：2016/10/17
     * @param:
     * @return:
     */
    public List<CompanyStatisticDTO> getCompanyStatisticList(Map<String, Object> param){
        if(null!=param.get("pageNumber")){
            int page=(Integer)param.get("pageNumber");
            int pageSize=(Integer) param.get("pageSize");
            param.put("startPage",page*pageSize);
            param.put("endPage",pageSize);
        }
        return statisticDao.getCompanyStatisticList(param);
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
        return statisticDao.getCompanyStatisticCount(param);
    }
}
