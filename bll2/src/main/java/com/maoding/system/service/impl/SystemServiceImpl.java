package com.maoding.system.service.impl;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.util.StringUtil;
import com.maoding.hxIm.service.ImService;
import com.maoding.org.dao.CompanyDao;
import com.maoding.org.dao.TeamOperaterDao;
import com.maoding.org.dto.CompanyDTO;
import com.maoding.org.dto.LoginAdminDTO;
import com.maoding.org.entity.CompanyUserEntity;
import com.maoding.org.entity.TeamOperaterEntity;
import com.maoding.org.service.CompanyService;
import com.maoding.org.service.CompanyUserService;
import com.maoding.role.dao.RoleDao;
import com.maoding.role.entity.RoleEntity;
import com.maoding.system.service.SystemService;
import com.maoding.user.dao.AccountDao;
import com.maoding.user.dto.AccountDTO;
import com.maoding.user.entity.AccountEntity;
import com.maoding.user.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private AccountDao accountDao;
    @Autowired
    private TeamOperaterDao teamOperaterDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CompanyUserService companyUserService;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ImService imService;

    @Override
    public Map<String, Object> getUserSessionObjOfWork(AccountDTO dto, HttpServletRequest request) throws Exception {
        String userId = dto.getId();
        String companyId = null;
        /********下面一段代码*******/
        AccountEntity account = accountDao.selectById(userId);
        BaseDTO.copyFields(account, dto);
        //从cookie中查找此用户的cookie
        Cookie companyCookie = getCookieByName(request, account.getId());
        if (companyCookie != null) {
            companyId = companyCookie.getValue();
            CompanyUserEntity companyUser = companyUserService.getCompanyUserByUserIdAndCompanyId(userId, companyId);
            //CompanyEntity companyEntity = companyDao.selectById(companyId);
            if (companyUser == null || !"1".equals(companyUser.getAuditStatus())) {//如果cookie中的公司是失效的。则companyId设置为null
                companyId = null;
            }
        }
        Map<String, Object> m = new HashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", dto.getId());
        List<CompanyDTO> companies = companyDao.getCompanyByUserId(userId);
        if (companyId == null || "".equals(companyId)) {// 如果
            for (CompanyDTO c : companies) {
                companyId = c.getId();
                break;
            }
        }
        if (companyId != null && !"".equals(companyId)) {
            for (CompanyDTO c : companies) {
                if (companyId.equals(c.getId())) {
                    // 设置默认公司
                    m.put("company", c);//当前组织， 切换组织的时候，company需要更换
                    m.put("defaultCompany", c);//默认组织， 切换组织的时候，defaultCompany不需要更换
                }
            }
        }
        /*************权限设置**************/
        List<RoleEntity> roles;
        // 此处执行用户查询操作，并将用户信息保存到session中
        roles = roleDao.getUserRolesByOrgId(userId, companyId);

        m.put("companies", companies);
        m.put("user", dto);
        Map<String, Object> role = new HashMap<String, Object>();
        role.put("roles", roles);
        m.put("roleCompany", role);
        m.put("role", roles);
        m.put("userId", userId);
        m.put("companyId", companyId);
        m.put("fastdfsUrl", fastdfsUrl);
        return m;
    }

    public Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    /**
     * 将cookie封装到Map里面
     */
    private Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /**
     * 方法描述：登录时，获取相关信息
     * 作        者：MaoSF
     * 日        期：2016年7月7日-下午6:03:38
     */
    @Override
    public Map<String, Object> getUserSessionObjOfAdmin(AccountDTO dto) throws Exception {
        String userId = dto.getId();
        String companyId = dto.getDefaultCompanyId();//此处是注册公司带过来的CompanyId
        /********下面一段代码*******/
        AccountEntity account = accountDao.selectById(userId);
        BaseDTO.copyFields(account, dto);
        if (StringUtil.isNullOrEmpty(companyId)) {
            companyId = account.getDefaultCompanyId();
        }
        Map<String, Object> m = new HashMap<String, Object>();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", dto.getId());
        List<CompanyDTO> companies = companyDao.getCompanyByUserId(userId);

        if (companyId == null || "".equals(companyId)) {// 如果
            for (CompanyDTO c : companies) {
                companyId = c.getId();
                break;
            }
        }
        if (companyId != null && !"".equals(companyId)) {
            for (CompanyDTO c : companies) {
                if (companyId.equals(c.getId())) {
                    // 设置默认公司
                    m.put("company", c);//当前组织， 切换组织的时候，company需要更换
                    m.put("defaultCompany", c);//默认组织， 切换组织的时候，defaultCompany不需要更换
                }
            }
        }
        /*************权限设置**************/
        List<RoleEntity> roles;
        // 此处执行用户查询操作，并将用户信息保存到session中
        roles = roleDao.getUserRolesByOrgId(userId, companyId);

        m.put("companies", companies);
        m.put("user", dto);
        Map<String, Object> role = new HashMap<String, Object>();
        role.put("roles", roles);
        m.put("roleCompany", role);
        m.put("role", roles);
        m.put("userId", userId);
        m.put("companyId", companyId);
        m.put("fastdfsUrl", fastdfsUrl);
        return m;
    }

    @Override
    public AjaxMessage login(AccountDTO dto) throws Exception {
        AjaxMessage ajax = new AjaxMessage();
        if (null == dto.getPassword() || "".equals(dto.getPassword())) {
            return ajax.setCode("1").setInfo("密码不能为空！");
        }
        AccountEntity account = accountDao.getAccountByCellphoneOrEmail(dto.getCellphone());
        //如果用户不存在
        if (account == null) {
            return ajax.setCode("1").setInfo("手机号或密码无效");
        }
        //如果密码不正确
        if (!account.getPassword().equals(dto.getPassword())) {
            return ajax.setCode("1").setInfo("手机号或密码无效");
        }
        //用户名密码都正确
        BaseDTO.copyFields(account, dto);
        return ajax.setCode("0").setInfo("登录成功").setData(dto);
    }

    @Override
    public AjaxMessage loginAdminFirst(LoginAdminDTO dto) throws Exception {
        AjaxMessage ajax = new AjaxMessage();
        AccountEntity account = accountDao.getAccountByCellphoneOrEmail(dto.getCellphone());
        //如果用户不存在
        if (account == null) {
            return ajax.setCode("1").setInfo("手机号或密码无效");
        }
        //如果密码不正确
        if (!account.getPassword().equals(dto.getPassword())) {
            return ajax.setCode("1").setInfo("手机号或密码无效");
        }
        //验证是否有管理员账户的公司
        List<CompanyDTO> list = companyService.getAdminOfCompanyByUserId(account.getId());
        if (list == null || list.size() == 0) {
            return ajax.setCode("1").setInfo("你不是管理员");
        }
        //用户名密码都正确
        BaseDTO.copyFields(account, dto);
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("account", dto);
        returnMap.put("companyList", list);
        return ajax.setCode("0").setData(returnMap);
    }

    @Override
    public AjaxMessage loginAdmin(LoginAdminDTO dto) throws Exception {

        AjaxMessage ajax = new AjaxMessage();

        if (null == dto.getAdminPassword() || "".equals(dto.getAdminPassword())) {
            return ajax.setCode("1").setInfo("密码不能为空");
        }

        AccountEntity account = accountDao.selectById(dto.getId());

        AccountDTO accountDto = new AccountDTO();
        BaseDTO.copyFields(account, accountDto);

        TeamOperaterEntity teamOperater = teamOperaterDao.getTeamOperaterByCompanyId(dto.getCompanyId(), account.getId());
        //非管理员
        if (teamOperater == null) {
            return ajax.setCode("1").setInfo("密码错误");
        }

        //如果密码不正确
        if (!teamOperater.getAdminPassword().equals(dto.getAdminPassword())) {
            return ajax.setCode("1").setInfo("密码错误");
        }

        Map<String, Object> m = new HashMap<String, Object>();

        m.put("userId", accountDto.getId());
        m.put("user", accountDto);
        m.put("companyId", dto.getCompanyId());
        m.put("company", companyService.getCompanyById(dto.getCompanyId()));

        //密码正确
        return ajax.setCode("0").setInfo("登录成功").setData(m);
    }

    @Override
    public AjaxMessage loginAdmin_bak(LoginAdminDTO dto) throws Exception {

        AjaxMessage ajax = new AjaxMessage();

        if (null == dto.getAdminPassword() || "".equals(dto.getAdminPassword())) {
            return ajax.setCode("1").setInfo("密码不能为空");
        }

        AccountEntity account = accountDao.getAccountByCellphoneOrEmail(dto.getCellphone());

        AccountDTO accountDto = new AccountDTO();
        BaseDTO.copyFields(account, accountDto);

        TeamOperaterEntity teamOperater = teamOperaterDao.getTeamOperaterByCompanyId(dto.getCompanyId(), account.getId());
        //非管理员
        if (teamOperater == null) {
            return ajax.setCode("1").setInfo("密码错误");
        }

        //如果密码不正确
        if (!teamOperater.getAdminPassword().equals(dto.getAdminPassword())) {
            return ajax.setCode("1").setInfo("密码错误");
        }

        Map<String, Object> m = new HashMap<String, Object>();

        m.put("userId", accountDto.getId());
        m.put("user", accountDto);
        m.put("companyId", dto.getCompanyId());
        m.put("company", companyService.getCompanyById(dto.getCompanyId()));

        //密码正确
        return ajax.setCode("0").setInfo("登录成功").setData(m);
    }

    @Override
    public AccountDTO updateAccount(AccountDTO dto) throws Exception {
        AccountEntity entity = new AccountEntity();
        BaseDTO.copyFields(dto, entity);
        accountDao.updateById(entity);
        imService.updateImAccount(entity.getId(), entity.getPassword());
        return dto;
    }


	/*@Override
    public Map<String, Object> switchCompany(String companyId, String userId,Session getSession) {
		
		Map<String, Object>m=new HashMap<String, Object>();
		
		m.put("userId", userId);
		m.put("companyId", companyId);
		
		List<CompanyDTO> companies=companyDao.getCompanyByUserId(userId);
		
		for (CompanyDTO c : companies) {
			if (companyId.equals(c.getId())) {
				// 设置默认公司
				m.put("company", c);//当前组织， 切换组织的时候，company需要更换
			}
		}
		
		Map<String,Object> role = new HashMap<String,Object>();
		
		*//*************权限设置**************//*
        List<RoleEntity> roles;
		//获取在当前公司下的部门
		// 此处执行用户查询操作，并将用户信息保存到session中
		roles=roleDao.getUserRolesByOrgId(userId,companyId);
		m.put("companies", companies);
		m.put("user", (AccountDTO) getSession.getAttribute("user"));
		role.put("roles", roles);
		m.put("roleCompany", role);
		m.put("role", roles);
		m.put("userId",userId);

		return m;
	}*/

    /**
     * 方法描述：admin模块获取用户相关信息
     * 作        者：MaoSF
     * 日        期：2016年7月13日-上午10:58:10
     *
     * @param model
     * @return
     */
	/*@Override
	public ModelMap getCurrUserInfoOfAdmin(ModelMap model,Session getSession) throws Exception {
		String userId = (String) getSession.getAttribute("userId");
		String companyId = (String) getSession.getAttribute("adminCompanyId");
		//组织列表
		List<CompanyDTO> orgList;
		orgList = companyService.getCompanyByUserId(userId);

		//用户信息
		//AccountDTO userInfo = (AccountDTO) getSession.getAttribute("user");
		AccountDTO userInfo = accountService.getAccountById(userId);

		//当前组织信息
		CompanyDTO company = companyService.getCompanyById(companyId);

		CompanyUserEntity companyUser =companyUserService.getCompanyUserByUserIdAndCompanyId(userId,companyId);

		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId",userId);
		param.put("companyId",companyId);
		String roleCodes =permissionDao.getPermissionCodeByUserId(param);

		model.addAttribute("userInfo",userInfo);
		model.addAttribute("orgList",orgList);
		model.addAttribute("companyInfo",company);
		model.addAttribute("serverUrl",serverUrl);
		model.addAttribute("fastdfsUrl",fastdfsUrl);
		model.addAttribute("companyUser",companyUser);
		model.addAttribute("roleCodes",roleCodes);
		model.addAttribute("scoketUrl",scoketUrl);
		return model;
	}*/
    /**
     * 方法描述：admin模块获取用户相关信息
     * 作        者：MaoSF
     * 日        期：2016年7月13日-上午10:58:10
     *
     * @param model
     * @return
     */
	/*@Override
	public ModelMap getCurrUserInfoOfAdmin2(ModelMap model,Session getSession) throws Exception {


		String userId = (String) getSession.getAttribute("userId");
		String companyId = (String) getSession.getAttribute("companyId");

		//组织列表
		List<CompanyDTO> orgList;
		orgList = companyService.getCompanyByUserId(userId);

		//用户信息
		//AccountDTO userInfo = (AccountDTO) getSession.getAttribute("user");
		AccountDTO userInfo = accountService.getAccountById(userId);

		//当前组织信息
		CompanyDTO company = companyService.getCompanyById(companyId);

		CompanyUserEntity companyUser =companyUserService.getCompanyUserByUserIdAndCompanyId(userId,companyId);


		getSession.setAttribute("adminCompanyId", companyId);
		getSession.setAttribute("adminCompany", company);

		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId",userId);
		param.put("companyId",companyId);
		String roleCodes =permissionDao.getPermissionCodeByUserId(param);

		//管理员数据查询
		TeamOperaterEntity teamOperaterEntity = teamOperaterDao.getTeamOperaterByCompanyId(companyId,userId);
		if(teamOperaterEntity!=null){
			model.addAttribute("adminFlag","1");
		}else {
			model.addAttribute("adminFlag","0");
		}

		model.addAttribute("userInfo",userInfo);
		model.addAttribute("orgList",orgList);
		model.addAttribute("companyInfo",company);
		model.addAttribute("serverUrl",serverUrl);
		model.addAttribute("fastdfsUrl",fastdfsUrl);
		model.addAttribute("companyUser",companyUser);
		model.addAttribute("roleCodes",roleCodes);
		model.addAttribute("scoketUrl",scoketUrl);
		return model;
	}*/

    /**
     * 方法描述：work模块获取用户相关信息
     * 作        者：MaoSF
     * 日        期：2016年7月13日-上午10:58:10
     */
	/*@Override
	public ModelMap getCurrUserInfoOfWork(ModelMap model,Session getSession) throws Exception {
		String userId = (String) getSession.getAttribute("userId");
		String companyId = (String) getSession.getAttribute("companyId");
		//组织列表
		List<CompanyDTO> orgList;
		orgList = companyService.getCompanyByUserId(userId);
		//账号信息
		AccountDTO userInfo = accountService.getAccountById(userId);
		model.addAttribute("userInfo",userInfo);
		//当前组织信息
		CompanyDTO company = companyService.getCompanyById(companyId);
		//用户所在公司信息
		CompanyUserEntity companyUser =companyUserService.getCompanyUserByUserIdAndCompanyId(userId,companyId);

		//管理员数据查询
		TeamOperaterEntity teamOperaterEntity = teamOperaterDao.getTeamOperaterByCompanyId(companyId,userId);

		//String roleCodes =roleDao.getUserRoleCodes(userId,companyId);

		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId",userId);
		param.put("companyId",companyId);
		String roleCodes =permissionDao.getPermissionCodeByUserId(param);
//
//		if(company!=null){
//			Map<String,Object> paraMap=new HashMap<String,Object>();
//			paraMap.put("msReceiver",userInfo.getId());
//			paraMap.put("msIsRead","0");
//			//paraMap.put("msCompany",company.getId());
//			int  unReadCount=messageDao.getMessageCountByParam(paraMap);
//			List<MessageEntity> messageEntities=messageDao.getMessageByParam(paraMap);
//			String unReadIds="";
//			for(MessageEntity messageEntity:messageEntities){
//				if("".equals(unReadIds)){
//					unReadIds=messageEntity.getId();
//				}
//				else {
//					unReadIds=unReadIds+","+messageEntity.getId();
//				}
//			}
//			model.addAttribute("unReadCount",unReadCount);
//			model.addAttribute("unReadIds",unReadIds);
//		}

		if(teamOperaterEntity!=null){
			model.addAttribute("adminFlag","1");
		}else {
			model.addAttribute("adminFlag","0");
		}
		model.addAttribute("userInfo",userInfo);
		model.addAttribute("orgList",orgList);
		model.addAttribute("companyInfo",company);
		model.addAttribute("serverUrl",serverUrl);
		model.addAttribute("fastdfsUrl",fastdfsUrl);
		model.addAttribute("companyUser",companyUser);
		model.addAttribute("roleCodes",roleCodes);
		model.addAttribute("scoketUrl",scoketUrl);
		return model;
	}*/
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


    @Override
    public AjaxMessage changePassword(AccountDTO accountDto) throws Exception {
        AjaxMessage ajax = new AjaxMessage();
        if (StringUtil.isNullOrEmpty(accountDto.getPassword())) {
            return ajax.setCode("1").setInfo("密码不能为空");
        }
        AccountEntity accountEntity = accountDao.selectById(accountDto.getId());
        if (!accountEntity.getPassword().equals(accountDto.getOldPassword())) {
            return ajax.setCode("1").setInfo("旧密码错误");
        }
        if (accountEntity.getPassword().equals(accountDto.getPassword())) {
            return ajax.setCode("1").setInfo("新旧密码不能一致");
        }
        this.updateAccount(accountDto);

        return ajax.setCode("0").setInfo("保存成功");
    }

    @Override
    public AjaxMessage changeCellphone(AccountDTO accountDto) throws Exception {
        AjaxMessage ajax = new AjaxMessage();
        if (StringUtil.isNullOrEmpty(accountDto.getCellphone())) {
            return ajax.setCode("1").setInfo("手机号不能为空");
        }
        //注册前验证是否已经注册过
        if (null != accountService.getAccountByCellphoneOrEmail(accountDto.getCellphone())) {
            return ajax.setCode("1").setInfo(accountDto.getCellphone() + "已经注册过了！");
        }
        this.updateAccount(accountDto);
        return ajax.setCode("0").setInfo("保存成功");
    }


    @Override
    public AjaxMessage bindMailbox(AccountDTO accountDto) throws Exception {
        AjaxMessage ajax = new AjaxMessage();
        if (StringUtil.isNullOrEmpty(accountDto.getEmail())) {
            return ajax.setCode("1").setInfo("邮箱不能为空！");
        }

        if (null != accountService.getAccountByCellphoneOrEmail(accountDto.getEmail())) {
            return ajax.setCode("1").setInfo(accountDto.getEmail() + "已经注册过了！");
        }
        this.updateAccount(accountDto);
        return ajax.setCode("0").setInfo("保存成功");
    }

}
