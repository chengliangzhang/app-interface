package com.maoding.financial.service;

import com.maoding.core.bean.ResponseBean;
import com.maoding.financial.dto.LeaveDTO;
import com.maoding.financial.dto.SaveLeaveDTO;

import java.util.Map;

public interface LeaveService {

    /**
     * 保存请假信息
     */
    ResponseBean saveLeave(SaveLeaveDTO dto) throws Exception;

    LeaveDTO getLeaveDetail(String id) throws Exception;

}
