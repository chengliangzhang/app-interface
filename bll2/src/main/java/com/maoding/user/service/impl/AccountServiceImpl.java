package com.maoding.user.service.impl;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.base.service.GenericService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.StringUtil;
import com.maoding.hxIm.service.ImService;
import com.maoding.org.dto.CompanyDTO;
import com.maoding.org.entity.CompanyEntity;
import com.maoding.org.service.CompanyService;
import com.maoding.user.dao.AccountDao;
import com.maoding.user.dao.UserDao;
import com.maoding.user.dto.AccountDTO;
import com.maoding.user.dto.RegisterCompanyDTO;
import com.maoding.user.entity.AccountEntity;
import com.maoding.user.entity.UserEntity;
import com.maoding.user.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：UserServiceImpl
 * 类描述：账号信息Service
 * 作    者：MaoSF
 * 日    期：2015年11月23日-下午3:37:01
 */
@Service("accountService")
public class AccountServiceImpl extends GenericService<AccountEntity> implements AccountService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private ImService imService;

    /**
     * 方法描述：创建用户
     * 作者：MaoSF
     * 日期：2016/11/29
     * @param:
     * @return:
     */
    @Override
    public AccountEntity createAccount(String userName, String password, String cellphone) throws Exception {
        return createAccount(userName, password, cellphone, "1");
    }

    public AccountEntity createAccount(String userName, String password, String cellphone, String status) throws Exception {
        AccountEntity account = new AccountEntity();
        account.setId(StringUtil.buildUUID());
        account.setUserName(userName);
        account.setPassword(password);
        account.setCellphone(cellphone);
        account.setStatus(status == null ? "1" : status);
        if ("0".equals(status)) {
            account.setActiveTime(DateUtils.date2Str(DateUtils.datetimeFormat));
        }
        accountDao.insert(account);
        UserEntity user = new UserEntity();
        BaseDTO.copyFields(account, user);
        userDao.insert(user);
        imService.createImAccount(account.getId(), account.getUserName(), account.getPassword());
        return account;
    }

    @Override
    public AjaxMessage register(AccountDTO dto) throws Exception {
        if (null == dto.getPassword() || "".equals(dto.getPassword())) {
            return new AjaxMessage().setCode("1").setInfo("密码不能为空！");
        }
        //状态设置为激活状态
        dto.setStatus("0");
        dto.setActiveTime(DateUtils.date2Str(DateUtils.datetimeFormat));
        //判断当前手机是否已经注册
        AccountEntity account = this.getAccountByCellphoneOrEmail(dto.getCellphone());
        UserEntity user = null;
        if (account == null) {
            //如果没有注册
            account = this.createAccount(dto.getUserName(), dto.getPassword(), dto.getCellphone(), "0");
        } else {//如果注册，更新
            dto.setId(account.getId());
            BaseDTO.copyFields(dto, account);
            this.accountDao.updateById(account);
            user = this.userDao.selectById(account.getId());
            user.setUserName(dto.getUserName());
            this.userDao.updateById(user);
            imService.updateImAccount(account.getId(), account.getPassword());
        }
        dto.setId(account.getId());
        return new AjaxMessage().setCode("0").setInfo("注册成功，欢迎进入卯丁！").setData(dto);
    }

    public ResponseBean validateRegisterCompany(RegisterCompanyDTO dto) throws Exception {

        if (StringUtil.isNullOrEmpty(dto.getCompanyName())) {
            return ResponseBean.responseError("组织名称不能为空");
        }
        /*if (StringUtil.isNullOrEmpty(dto.getCompanyShortName())) {
            return ResponseBean.responseError("公司简称不能为空");
		}*/
        //公司名可以重名
		/*if( this.companyDao.getCompanyByCompanyName(dto.getCompanyName()) != null){
			return ResponseBean.responseError(dto.getCompanyName()+"：公司名已经被注册了");
		}*/
		/*if( this.companyDao.getCompanyByCompanyShortName(dto.getCompanyShortName()) != null){
			return ResponseBean.responseError(dto.getCompanyShortName()+"：公司简称已经被占用");
		}*/

        return null;
    }


    @Override
    public AccountDTO getAccountDtoByCellphoneOrEmail(String cellphone) throws Exception {

        AccountEntity account = this.getAccountByCellphoneOrEmail(cellphone);
        if (account == null) {
            return null;
        }
        AccountDTO dto = new AccountDTO();
        BaseDTO.copyFields(account, dto);
        return dto;
    }

    /**
     * 方法描述：根据id查询(并且转化成DTO)
     * 作        者：MaoSF
     * 日        期：2016年7月7日-上午11:33:07
     */
    @Override
    public AccountDTO getAccountById(String id) throws Exception {
        AccountEntity account = this.selectById(id);
        if (account == null) {
            return null;
        }
        AccountDTO dto = new AccountDTO();
        BaseDTO.copyFields(account, dto);
        return dto;
    }

    @Override
    public AccountEntity getAccountByCellphoneOrEmail(String cellphone)
            throws Exception {
        return accountDao.getAccountByCellphoneOrEmail(cellphone);
    }


    public List<AccountEntity> selectAll() throws Exception {
        return accountDao.selectAll();
    }

    /**
     * v2
     */
    public List<AccountDTO> selectV2AllPersonByParam(Map<String, Object> map) throws Exception {
        //所在的所有公司
        List<CompanyDTO> list = companyService.getCompanyByUserId((String) map.get("accountId"));
        if (!CollectionUtils.isEmpty(list)) {
            List<String> orgList = new ArrayList<String>();
            List<CompanyEntity> companyList = new ArrayList<CompanyEntity>();
            for (int j = 0; j < list.size(); j++) {
                orgList.add(list.get(j).getId());
                String rootId = companyService.getRootCompanyId(list.get(j).getId());
                companyList.addAll(companyService.getAllChilrenCompany(rootId));
                orgList.add(list.get(j).getId());
            }
            for (CompanyEntity companyEntity : companyList) {
                orgList.add(companyEntity.getId());
            }
            map.put("orgList", orgList);
            return accountDao.selectAllPersonByParam(map);
        }

        return new ArrayList<>();
    }


    /**
     * v2
     */
    @Override
    public ResponseBean v2RegisterCompanyOfWork(RegisterCompanyDTO dto, String currUserId) throws Exception {
        //判断该公司名是否存在
        ResponseBean responseBean = this.validateRegisterCompany(dto);
        if (responseBean == null) {
            AccountDTO accountDTO = this.getAccountById(currUserId);
            if (accountDTO == null) {
                return ResponseBean.responseError("创建失败");
            }
            //简称=全称
            dto.setCompanyShortName(dto.getCompanyName());
            //创建公司
            //保存公司信息
            CompanyEntity company = new CompanyEntity();
            company.setStatus("0");
            BaseDTO.copyFields(dto, company);
            String companyId = this.companyService.saveCompany(company, accountDTO.getUserName(), currUserId, currUserId);

            return ResponseBean.responseSuccess("成功建立“" + dto.getCompanyName() + "”");
        }
        return responseBean;
    }

}
