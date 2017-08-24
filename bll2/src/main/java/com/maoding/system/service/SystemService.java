package com.maoding.system.service;

import com.maoding.core.bean.AjaxMessage;
import com.maoding.org.dto.LoginAdminDTO;
import com.maoding.user.dto.AccountDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 深圳市设计同道技术有限公司
 * 类    名：SystemService
 * 类描述：系统（公共）service
 * 作    者：MaoSF
 * 日    期：2016年7月7日-下午6:03:13
 */
public interface SystemService {


    /**
     * 方法描述：登录时，获取相关信息
     * 作        者：MaoSF
     * 日        期：2016年7月7日-下午6:03:38
     */
    public Map<String, Object> getUserSessionObjOfWork(AccountDTO dto, HttpServletRequest request) throws Exception;

    /**
     * 方法描述：登录时，获取相关信息
     * 作        者：MaoSF
     * 日        期：2016年7月7日-下午6:03:38
     */
    public Map<String, Object> getUserSessionObjOfAdmin(AccountDTO dto) throws Exception;

    /************************登录信息*****************************/

    /**
     * 方法描述：个人登录
     * 作        者：MaoSF
     * 日        期：2016年7月8日-上午10:02:00
     */
    public AjaxMessage login(AccountDTO dto) throws Exception;

    /**
     * 方法描述：企业登录 （第一步）
     * 作        者：MaoSF
     * 日        期：2016年7月8日-上午10:02:00
     */
    public AjaxMessage loginAdminFirst(LoginAdminDTO dto) throws Exception;

    /**
     * 方法描述：企业登录 (admin,work项目合并之后的)
     * 作        者：MaoSF
     * 日        期：2016年7月8日-上午10:02:00
     */
    public AjaxMessage loginAdmin(LoginAdminDTO dto) throws Exception;

    /**
     * 方法描述：企业登录(admin,work项目合并之前的)
     * 作        者：MaoSF
     * 日        期：2016年7月8日-上午10:02:00
     */
    public AjaxMessage loginAdmin_bak(LoginAdminDTO dto) throws Exception;

    /**
     * 方法描述：修改账号信息（修改密码，绑定邮箱，修改手机号，等各种账号里面的信息）
     * 作        者：MaoSF
     * 日        期：2016年7月8日-下午3:40:54
     */
    public AccountDTO updateAccount(AccountDTO dto) throws Exception;

    /**
     * 方法描述：忘记密码
     * 作        者：MaoSF
     * 日        期：2016年7月13日-下午7:26:48
     */
    public AjaxMessage forgotPassword(AccountDTO dto) throws Exception;

    /**
     * 方法描述：更换密码
     * 作        者：MaoSF
     * 日        期：2016年7月13日-下午7:27:24
     */
    public AjaxMessage changePassword(AccountDTO accountDto) throws Exception;

    /**
     * 方法描述：更换手机号
     * 作        者：MaoSF
     * 日        期：2016年7月13日-下午7:27:51
     */
    public AjaxMessage changeCellphone(AccountDTO accountDto) throws Exception;

    /**
     * 方法描述：绑定邮箱
     * 作        者：MaoSF
     * 日        期：2016年7月13日-下午7:28:23
     */
    public AjaxMessage bindMailbox(AccountDTO accountDto) throws Exception;


    /**
     * 方法描述：组织切换，获取当前切换组织的相应信息
     * 作        者：MaoSF
     * 日        期：2016年7月13日-上午10:58:10
     * @param companyId
     * @param userId
     * @return
     */
    //public Map<String,Object> switchCompany(String companyId,String userId,Session getSession);

    /**
     * 方法描述：admin模块 获取用户当前基本信息（用户信息，当前组织信息，组织列表...）
     * 作        者：MaoSF
     * 日        期：2016年7月13日-上午10:58:10
     * @param model
     * @return
     */
    //public ModelMap getCurrUserInfoOfAdmin(ModelMap model,Session getSession) throws Exception;

    /**
     * 方法描述：admin模块 获取用户当前基本信息（用户信息，当前组织信息，组织列表...）
     * 作        者：MaoSF
     * 日        期：2016年7月13日-上午10:58:10
     * @param model
     * @return
     */
    //public ModelMap getCurrUserInfoOfAdmin2(ModelMap model,Session getSession) throws Exception;

    /**
     * 方法描述：work模块 获取用户当前基本信息（用户信息，当前组织信息，组织列表...）
     * 作        者：MaoSF
     * 日        期：2016年7月13日-上午10:58:10
     * @param model
     * @return
     */
    //public ModelMap getCurrUserInfoOfWork(ModelMap model,Session getSession) throws Exception;

}
