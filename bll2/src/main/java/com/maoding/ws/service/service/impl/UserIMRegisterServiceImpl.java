package com.maoding.ws.service.service.impl;

import com.maoding.user.dao.AccountDao;
import com.maoding.ws.service.service.UserIMRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Idccapp22 on 2016/8/8.
 */
@Service("userIMRegisterService")
public class UserIMRegisterServiceImpl implements UserIMRegisterService {

    @Autowired
    private AccountDao accountDao;

  /*  @Autowired
    private IMClient iMClient;*/
    /**
     * 方法描述：account没有注册IM的全部注册一遍
     * 作者：MaoSF
     * 日期：2016/8/8
     *
     * @param:
     * @return:
     */
    @Override
    public void registerIm() throws Exception{
       /* List<AccountEntity> accountEntityList = accountDao.selectAll();
        int start = 0;
        for(int i =0;i<accountEntityList.size();i++){
            if(start<30) {
                start++;
            }else {
                start=0;
                new Thread().sleep(1000);//休眠一分钟
            }
            AccountEntity account = accountEntityList.get(i);
            iMClient.getAPI(IMUserAPI.class).createNewIMUserSingle(account.getId(), account.getPassword(), account.getUserName());

        }*/
    }
}
