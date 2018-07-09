package com.maoding.statistic.service;

import com.maoding.statistic.dto.CompanyStatisticDTO;
import com.maoding.statistic.dto.StatisticDetailSummaryDTO;

import java.util.List;
import java.util.Map;

/**
 * Created by Idccapp22 on 2016/10/17.
 */
public interface StatisticService {

    /**
     * 方法描述：公司人数统计
     * 作者：MaoSF
     * 日期：2016/10/17
     * @param:
     * @return:
     */
    public List<CompanyStatisticDTO> getCompanyStatisticList(Map<String, Object> param);

    /**
     * 方法描述：公司人数统计 总条数
     * 作者：MaoSF
     * 日期：2016/10/17
     * @param:
     * @return:
     */
    public int getCompanyStatisticCount(Map<String, Object> param);

    StatisticDetailSummaryDTO getStatisticDetailSummaryByCompanyId(String companyId);
}
