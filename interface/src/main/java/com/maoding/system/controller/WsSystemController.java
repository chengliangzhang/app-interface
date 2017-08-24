package com.maoding.system.controller;

import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.component.mail.MailSender;
import com.maoding.core.component.mail.bean.Mail;
import com.maoding.core.component.sms.SmsSender;
import com.maoding.core.component.sms.bean.SenderResponseBean;
import com.maoding.core.component.sms.bean.Sms;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.SecurityCodeUtil;
import com.maoding.core.util.StringUtil;
import com.maoding.core.util.TokenProcessor;
import com.maoding.msgpusher.Msgpusher;
import com.maoding.org.service.CompanyUserAuditService;
import com.maoding.project.dao.ProjectDao;
import com.maoding.project.entity.ProjectEntity;
import com.maoding.project.service.ProjectSkyDriverService;
import com.maoding.system.entity.VersionEntity;
import com.maoding.system.service.SystemService;
import com.maoding.system.service.VersionService;
import com.maoding.user.dto.AccountDTO;
import com.maoding.user.dto.ShareInvateDTO;
import com.maoding.user.entity.AccountEntity;
import com.maoding.user.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：WsSystemController
 * 类描述：
 * 作    者：Chenxj
 * 日    期：2015年12月28日-上午11:58:01
 */
@Controller
@RequestMapping("/sys")
public class WsSystemController extends BaseWSController {


    @Autowired
    private AccountService accountService;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private SmsSender smsSender;

    @Autowired
    private SystemService systemService;

    @Autowired
    private VersionService versionService;

    @Autowired
    private Msgpusher msgpusher;
    @Autowired
    private CompanyUserAuditService companyUserAuditService;

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectSkyDriverService projectSkyDriverService;

    public boolean sendCode(String to, String code) {
        if (StringUtil.isEmail(to)) {
            Mail mail = new Mail();
            mail.setTo(to);
            mail.setSubject("卯丁邮箱验证");
            mail.setHtmlBody("您的验证码为：" + code);
            return mailSender.sendMail(mail);
        } else if (StringUtil.isNumeric(to) && to.length() == 11) {
            Sms sms = new Sms();
            sms.addMobile(to);
            sms.setMsg(StringUtil.format("【卯 丁】亲爱的用户，您的验证码是?。如非本人操作，请忽略本短信", code));
            SenderResponseBean r = smsSender.send(sms);
            return "0".equals(r.getCodeKey());
        }
        return false;
    }

    /**
     * 方法描述：获取验证码
     * 作        者：Chenxj
     * 日        期：2015年8月10日-上午11:18:31
     */


    @RequestMapping("/get_code")
    @ResponseBody
    public ResponseBean getCode(@RequestBody Map<String, String> map, HttpSession session) throws Exception {
        String sessionid = session.getId();
        String code = SecurityCodeUtil.createSecurityCode();
        String loginname = map.get("loginName");
        boolean registed = false;
        boolean isOK = false;
        if (loginname != null) {
            if (StringUtil.isEmail(loginname)) {
                if (null != accountService.getAccountByCellphoneOrEmail(loginname)) {
                    if ("1".equals(map.get("type"))) {
                        return ResponseBean.responseError("邮箱错误或已被占用");
                    }
                    return ResponseBean.responseError("账号错误或已被占用");
                }
            }

            isOK = this.sendCode(loginname, code);
            saveCode(sessionid + loginname, code);
            AccountEntity o = accountService.getAccountByCellphoneOrEmail(loginname);
            registed = (o != null);
        }
        log.debug("验证码[{}]发送成功，接收::[{}],sessionid::[{}]", code, loginname, sessionid);
        return ResponseBean.responseSuccess("获取验证码成功")
                .addData("status", (isOK ? "0" : "1"))
                .addData("err_msg", (isOK ? "获取验证码成功" : "获取验证码失败"))
                .addData("sessionId", sessionid)
                .addData("registed", registed);
    }

    /**
     * 方法描述：忘记密码获取验证码
     * 作        者：Chenxj
     * 日        期：2015年8月10日-上午11:18:31
     */


    @RequestMapping("/forgetPassWordGetCode")
    @ResponseBody
    public ResponseBean forgetPassWordGetCode(@RequestBody Map<String, String> map, HttpSession session) throws Exception {
        String sessionid = session.getId();
        String code = SecurityCodeUtil.createSecurityCode();
        String loginname = map.get("loginName");
        boolean registed = false;
        boolean isOK = false;
        if (loginname != null) {
            if (StringUtil.isEmail(loginname)) {
                if (null != accountService.getAccountByCellphoneOrEmail(loginname)) {
                    return ResponseBean.responseError("账号错误或已被占用");
                }
            } else {
                AccountEntity accountEntity = accountService.getAccountByCellphoneOrEmail(loginname);
                if (null == accountEntity) {
                    return ResponseBean.responseError("该账号没有注册");
                }
            }

            isOK = this.sendCode(loginname, code);
            saveCode(sessionid + loginname, code);
            AccountEntity o = accountService.getAccountByCellphoneOrEmail(loginname);
            registed = (o != null);
        }
        log.debug("验证码[{}]发送成功，接收::[{}],sessionid::[{}]", code, loginname, sessionid);
        return responseSuccess()
                .addData("status", (isOK ? "0" : "1"))
                .addData("sessionId", sessionid)
                .addData("registed", registed);
    }

    /**
     * 方法描述：校验验证码
     * 作        者：Guozb
     * 日        期：2016年04月07日-15:18:18
     */
    @RequestMapping("/check_code")
    @ResponseBody
    public ResponseBean checkCode(@RequestBody Map<String, String> map, HttpServletRequest request) throws Exception {
        String code = map.get("code");
        String sessionid = map.get("sessionId");
        String loginname = map.get("loginName");
        if (checkCode(sessionid + loginname, code)) {
            return responseSuccess().addData("status", "0").addData("err_msg", "验证成功");
        } else {
            return responseError("短信验证码有误，请重新输入");
        }
    }

    /**
     * 方法描述：校验验证码
     * 作        者：Guozb
     * 日        期：2016年04月07日-15:18:18
     */
    @RequestMapping("/check_codetest")
    @ResponseBody
    public ResponseBean checkCodeTest(@RequestBody Map<String, String> map) throws Exception {

        return responseSuccess().addData("status", "0").addData("err_msg", " ");

    }

    /**
     * 方法描述：用户注册
     * 作        者：Chenxj
     * 日        期：2015年8月10日-上午11:18:18
     */
    @RequestMapping("/register")
    @ResponseBody
    public ResponseBean register(@RequestBody AccountDTO accountDTO, HttpServletRequest request) throws Exception {
        String code = accountDTO.getCode();
        String sessionid = accountDTO.getSessionId();
        if (checkCode(sessionid + accountDTO.getCellphone(), code)) {
            AccountDTO accountUser = (AccountDTO) accountService.register(accountDTO).getData();
            String userId = accountUser.getId();
            String token = TokenProcessor.getInstance().generateTokeCode(request);
            saveToken(userId, token, accountDTO.getIMEI(), accountDTO.getPlatform());
            log.debug("userId-->" + userId);
            //注册环信账号
//            iMClient.getAPI(IMUserAPI.class).createNewIMUserSingle(userId, accountUser.getPassword(), accountUser.getUserName());
            return responseSuccess()
                    .addData("token", token)
                    .addData("fastdfsUrl", fastdfsUrl)
                    .addData("accountId", userId);
        } else {
            return responseError("短信验证码有误，请重新输入");
        }
    }

    /**
     * 方法描述：用户登陆
     * 作        者：Chenxj
     * 日        期：2015年8月10日-上午11:18:18
     */
    @RequestMapping("/login")
    @ResponseBody
    public ResponseBean login(@RequestBody Map<String, String> map, HttpServletRequest request) throws Exception {
        String loginname = map.get("loginName");
        String password = map.get("password");
        AccountEntity user = accountService.getAccountByCellphoneOrEmail(loginname);
        if (user == null) {
            return responseError("账号不存在！");
        }
        if (user.getPassword().equals(password)) {
            String userId = user.getId();
            String token = TokenProcessor.getInstance().generateTokeCode(request);
            saveToken(userId, token, map.get("IMEI"), map.get("platform"));
            if ("1".equals(user.getStatus())) {
                user.setStatus("0");
                user.setActiveTime(DateUtils.date2Str(DateUtils.datetimeFormat));
                accountService.updateById(user);
            }
            return responseSuccess()
                    .addData("token", token)
                    // .addData("token", "12454564sdfsdfsdf")
                    .addData("fastdfsUrl", fastdfsUrl)
                    .addData("accountId", userId);

        } else {
            return responseError("手机号或密码错误");
        }
    }

    /**
     * 方法描述：忘记密码
     * 作        者：TangY
     * 日        期：2016年7月8日-下午4:13:09
     */
    @RequestMapping("/forgotPassword")
    @ResponseBody
    public ResponseBean forgotPassword(@RequestBody AccountDTO accountDto) throws Exception {
        String code = accountDto.getCode();
        String sessionid = accountDto.getSessionId();
        if (checkCode(sessionid + accountDto.getCellphone(), code)) {
            AjaxMessage ajaxMessage = systemService.forgotPassword(accountDto);
            if (ajaxMessage.getCode().equals("0")) {
//                imGroupService.updateUserPassword(accountDto.getId(), accountDto.getPassword());
                return ResponseBean.responseSuccess();
            } else {
                return ResponseBean.responseError("密码重置失败");
            }

        } else {
            return ResponseBean.responseError("短信验证码有误，请重新输入");
        }
    }

    /**
     * 方法描述：系统更新
     * 作        者：Chenxj
     * 日        期：2015年8月12日-下午2:42:39
     */
    @RequestMapping("/help_center_update")
    @ResponseBody
    public ResponseBean helpCenterUpdate(@RequestBody Map<String, String> map) {
        //userId,token
        String platform = map.get("platform");
        VersionEntity v = versionService.lastVersion(platform);
        if (v == null) {
            v = new VersionEntity();
        }
        return responseSuccess()
                .addData("version", v);
    }

    @RequestMapping("/bind_cid")
    @ResponseBody
    public ResponseBean bindCID(@RequestBody Map<String, String> map) {
        String userId = map.get("accountId");
        String cid = map.get("cid");
        boolean isOK = msgpusher.bindMultyCID(userId, cid);
        if (isOK) {
            return ResponseBean.responseSuccess();
        } else {
            return ResponseBean.responseError("绑定CID失败");
        }
    }

    /*******************分享邀请注册*******************/
    @RequestMapping("/shareInvateRegister")
    @ResponseBody
    public ResponseBean shareInvateRegister(@RequestBody ShareInvateDTO dto) throws Exception {
        AjaxMessage ajaxMessage = companyUserAuditService.shareInvateRegister(dto);
        if ("0".equals(ajaxMessage.getCode())) {
            return ResponseBean.responseSuccess();
        } else {
            return ResponseBean.responseError("绑定CID失败");
        }
    }

    /**
     * 方法描述：初始话项目设计阶段+任务+经营负责人
     * 作者：MaoSF
     * 日期：2017/4/1
     * @param:
     * @return:
     */
    @RequestMapping(value = "/initProject2", method = RequestMethod.POST)
    @ResponseBody
    public void initProject2() throws Exception {

        List<ProjectEntity> list = projectDao.selectAll();
        for (ProjectEntity project : list) {
            //1.创建文件夹
            projectSkyDriverService.createProjectFile(project);//

        }
    }
}

