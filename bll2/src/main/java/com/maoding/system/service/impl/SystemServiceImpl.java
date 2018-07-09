package com.maoding.system.service.impl;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.util.StringUtil;
import com.maoding.hxIm.service.ImService;
import com.maoding.system.service.SystemService;
import com.maoding.user.dao.AccountDao;
import com.maoding.user.dto.AccountDTO;
import com.maoding.user.entity.AccountEntity;
import com.maoding.user.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：SystemServiceImpl
 * 类描述：系统（公共）serviceImpl
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午6:07:14
 */
@Service("systemService")
public class SystemServiceImpl implements SystemService {

    @Value("${server.url}")
    protected String serverUrl;

    @Value("${fastdfs.url}")
    protected String fastdfsUrl;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ImService imService;


    @Override
    public AjaxMessage forgotPassword(AccountDTO accountDto) throws Exception {
        AjaxMessage ajax = new AjaxMessage();
        if (StringUtil.isNullOrEmpty(accountDto.getPassword())) {
            return ajax.setCode("1").setInfo("密码不能为空");
        }
        AccountEntity account = accountService.getAccountByCellphoneOrEmail(accountDto.getCellphone());
        if (account == null) {
            return ajax.setCode("1").setInfo("账号不存在");
        }
        account.setPassword(accountDto.getPassword());
        accountService.updateById(account);
        imService.updateImAccount(account.getId(), account.getPassword());
        return ajax.setCode("0").setInfo("保存成功");
    }


}
