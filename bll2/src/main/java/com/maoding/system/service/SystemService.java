package com.maoding.system.service;

import com.maoding.core.bean.AjaxMessage;
import com.maoding.user.dto.AccountDTO;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：SystemService
 * 类描述：系统（公共）service
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午6:03:13
 */
public interface SystemService {


    /**
     * 方法描述：忘记密码
     * 作        者：MaoSF
     * 日        期：2016年7月13日-下午7:26:48
     */
    public AjaxMessage forgotPassword(AccountDTO dto) throws Exception;





}
