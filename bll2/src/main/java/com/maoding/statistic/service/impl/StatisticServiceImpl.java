package com.maoding.statistic.service.impl;

import com.maoding.companybill.entity.CompanyBalanceEntity;
import com.maoding.companybill.service.CompanyBalanceService;
import com.maoding.core.util.StringUtil;
import com.maoding.statistic.dao.StatisticDao;
import com.maoding.statistic.dto.CompanyStatisticDTO;
import com.maoding.statistic.dto.StatisticDetailQueryDTO;
import com.maoding.statistic.dto.StatisticDetailSummaryDTO;
import com.maoding.statistic.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service("statisticService")
public class StatisticServiceImpl implements StatisticService{

    @Autowired
    protected StatisticDao statisticDao;

    @Autowired
    private CompanyBalanceService companyBalanceService;

    /**
     * 方法描述：公司人数统计
     * 作者：MaoSF
     * 日期：2016/10/17
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
     */
    @Override
    public int getCompanyStatisticCount(Map<String, Object> param) {
        return statisticDao.getCompanyStatisticCount(param);
    }

    @Override
    public StatisticDetailSummaryDTO getStatisticDetailSummaryByCompanyId(String companyId) {
        StatisticDetailQueryDTO param = new StatisticDetailQueryDTO();
        param.setCombineCompanyId(companyId);
        //查找该公司的最低余额设置
        CompanyBalanceEntity balance = companyBalanceService.getCompanyBalanceByCompanyId(companyId);
        if(balance!=null && balance.getSetBalanceDate()!=null){
            param.setStartDate(balance.getSetBalanceDate());
        }
        StatisticDetailSummaryDTO  data = statisticDao.getCompanyStandingBookSum(param);
        if(data==null){
            data = new StatisticDetailSummaryDTO();
            data.setPay(new BigDecimal("0"));
            data.setGain(new BigDecimal("0"));
        }
        data.setAmount(data.getGain().subtract(data.getPay()));
        if(balance!=null){
            if(!StringUtil.isNullOrEmpty(balance.getSetBalanceDate()) && !StringUtil.isNullOrEmpty(balance.getInitialBalance())){
                data.setAmount(data.getAmount().add(new BigDecimal(balance.getInitialBalance())));
            }
            data.setBalance(balance);
        }
        return data;
    }
}
