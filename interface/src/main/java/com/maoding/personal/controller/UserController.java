package com.maoding.personal.controller;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.util.StringUtil;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import com.maoding.user.dto.*;
import com.maoding.user.entity.AccountEntity;
import com.maoding.user.entity.UserEntity;
import com.maoding.user.service.AccountService;
import com.maoding.user.service.UserAttachService;
import com.maoding.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Idccapp21 on 2016/7/27.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseWSController {

    private final String th1 = "431X272";//头像缩略图大小
    private final String th2 = "180X240";//头像缩略图大小
    private final String th3 = "245X155";
    @Autowired
    public UserAttachService userAttachService;
    @Autowired
    public UserService userService;
    @Autowired
    public AccountService accountService;
    @Value("${person}")
    private String personUrl;

    /**
     * 方法描述：个人中心－获取账号信息
     * 作    者：wangrb
     * 日    期：2016年7月11日-下午9:38:13
     */
    @RequestMapping("/userInfo")
    @ResponseBody
    public ResponseBean getUserInfo(@RequestBody Map<String, String> paraMap) throws Exception {
        //userService.get
        String id = paraMap.get("accountId");
        UserDTO userDto = userService.getUserById(id);
        AccountDTO accountDTO = accountService.getAccountById(id);
        if (null == accountDTO) {
            return responseError("获取账号信息错误!");
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BaseDTO.copyFields(accountDTO, userInfoDTO);
        if (userDto != null) {
            userInfoDTO.setBirthday(userDto.getBirthday());
            userInfoDTO.setSex(userDto.getSex());
            userInfoDTO.setUserName(userDto.getUserName());
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userId", accountDTO.getId());
        param.put("attachType", "5");
        List<UserAttachDTO> list = userAttachService.getAttachByTypeToDTO(param);
        if (list != null && list.size() > 0) {
            String img = list.get(0).getAttachPath();
            String lastStr = img.substring(img.lastIndexOf('.') + 1);
            String firstStr = img.substring(0, img.lastIndexOf('.'));
            String newImg = "";
            if (firstStr.endsWith("_cut")) {
                newImg = img;
            } else {
                newImg = firstStr + "_cut" + "." + lastStr;
            }
            userInfoDTO.setFileFullPath(newImg);
        }
        return responseSuccess().addData("userInfo", userInfoDTO);
    }

    /**
     * 方法描述：获取公司邀请链接
     * 作        者：Chenxj
     * 日        期：2016年4月19日-上午11:36:44
     * @return link
     */
    @RequestMapping("/invitation_link")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean invitationLink(@RequestBody Map<String, String> map) {
        System.out.println("==========>" + serverUrl + "/iAdmin/sys/shareInvitation/" + map.get("companyId") + "/" + map.get("accountId"));

        return responseSuccess()
                .addData("link", serverUrl + "/iAdmin/sys/shareInvitation/" + map.get("companyId") + "/" + map.get("accountId"));
    }

//    /**
//     * 方法描述：重置密码
//     * 作        者：Chenxj
//     * 日        期：2015年8月12日-上午10:55:45
//     *
//     * @return
//     */
//    @RequestMapping("/change_password")
//    @ResponseBody
//    @AuthorityCheckable
//    public ResponseBean changepsw(@RequestBody AccountDTO accountDto) throws Exception {
//        //userId,token,password,new_password
//        String userId = accountDto.getAccountId();
//        AccountEntity loginUser = accountService.selectById(userId);
//        if (loginUser.getPassword().equals(accountDto.getOldPassword()))//移动端返回的密码直接是加密过的
//        {
//            loginUser.setPassword(accountDto.getPassword());
//            int i = accountService.updateById(loginUser);
//            if (i == 1) {
//               ImUserEntity imUserEntity=new ImUserEntity();
//                imUserEntity.setId(loginUser.getId());
//                imUserEntity.setUserName(loginUser.getUserName());
//                imUserEntity.setPassword(loginUser.getPassword());
//                imUserEntity.setFlag("1");
//                producerService.sendUserMessage(createUserDestination,imUserEntity);
//                return ResponseBean.responseSuccess("修改密码成功，请重新登录");
//            }
//            return ResponseBean.responseError("密码修改失败");
//        }
//        return ResponseBean.responseError("原始密码有误，请重新输入");
//    }

    /**
     * 方法描述：添加或修改用户信息
     * 作        者：TangY
     * 日        期：2016年7月11日-下午5:58:40
     */
    @RequestMapping("saveorupdate_userinfo")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean saveOrupdateUser(@RequestBody AccountDTO dto) throws Exception {
        return returnResponseBean(userService.saveOrUpdateUser(dto));
    }

    /**
     * 方法描述：绑定邮箱
     * 作        者：Chenxj
     * 日        期：2015年8月19日-下午2:25:28
     */
    @RequestMapping("/bind_email")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean bindEmail(@RequestBody AccountDTO dto) throws Exception {
        //userId,token,email,code,sessionid
        String code = dto.getCode();
        String sessionid = dto.getSessionId();
        if (checkCode(sessionid + dto.getEmail(), code)) {
            String email = dto.getEmail();
            if (email == null || !(StringUtil.isEmail(email)) || null != accountService.getAccountByCellphoneOrEmail(email)) {
                return ResponseBean.responseError("账号错误或已被占用");
            } else {
                AccountEntity loginUser = new AccountEntity();
                UserEntity user = new UserEntity();
                loginUser.setId(dto.getAccountId());
                loginUser.setEmail(email);
                int i = accountService.updateById(loginUser);
                user.setId(dto.getAccountId());
                user.setEmail(email);
                if (dto.getBirthday() != null) {
                    user.setBirthday(dto.getBirthday());
                }
                i = userService.updateById(user);
                if (i == 1) {
                    return ResponseBean.responseSuccess();
                } else {
                    return ResponseBean.responseError("绑定邮箱失败");
                }
            }
        } else {
            return ResponseBean.responseError("验证码错误或已失效");
        }
    }


    /**
     * 方法描述：更改绑定手机
     * 作        者：Chenxj
     * 日        期：2015年8月12日-上午10:50:00
     */
    @RequestMapping("/modify_phone")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean modifyPhone(@RequestBody AccountDTO dto) throws Exception {
        //userId,token,phone,code,sessionid
        String code = dto.getCode();
        String sessionid = dto.getSessionId();
        if (checkCode(sessionid + dto.getCellphone(), code)) {
            String phone = dto.getCellphone();
            if (phone == null || !(StringUtil.isNumeric(phone) && phone.length() == 11) || null != accountService.getAccountByCellphoneOrEmail(phone)) {
                return ResponseBean.responseError("账号错误或已被占用");
            } else {
                AccountEntity loginUser = new AccountEntity();
                loginUser.setId(dto.getAccountId());
                loginUser.setCellphone(phone);
                int i = accountService.updateById(loginUser);
                if (i == 1) {
                    return ResponseBean.responseSuccess("手机号更换成功，请重新登录");
                } else {
                    return ResponseBean.responseError("绑定手机号码失败");
                }
            }
        } else {
            return ResponseBean.responseError("短信验证码有误，请重新输入");
        }
    }

    /**
     * 方法描述：上传用户附件   只保存缩略图
     * 作        者：wangrb
     * 日        期：2015年11月17日-下午5:18:09
     * @return (code, msg, UserAttachEntity)
     */
    @RequestMapping("/uploadAttach")
    @ResponseBody
    public ResponseBean uploadAttach(UploadAttachDTO attachDTO) {
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("type", attachDTO.getAttachType());
            param.put("userId", attachDTO.getAccountId());
            param.put("companyId", attachDTO.getAppOrgId());
            param.put("personUrl", personUrl);
            //此标识是用来标示app端上传头像头像
            param.put("cutFlg", "1");
            Object object = userAttachService.saveUserAttach(attachDTO.getUploadFile(), param);
            if (null == object) {
                return ResponseBean.responseError("上传头像失败");
            }
            AjaxMessage ajaxMessage = (AjaxMessage) object;
            return returnResponseBean(ajaxMessage);
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getMessage();
            return ResponseBean.responseError("上传头像失败==>" + message);
        }
    }

    /**
     * 方法描述：校验密码
     * 作        者：wangrb
     * 日        期：2015年11月17日-下午5:18:09
     * @return (code, msg, UserAttachEntity)
     */
    @RequestMapping("/check_password")
    @ResponseBody
    public ResponseBean checkPassword(@RequestBody AccountDTO accountDTO) {
        try {
            AccountDTO dto = accountService.getAccountById(accountDTO.getAccountId());
            if (dto.getPassword().equals(accountDTO.getPassword())) {
                return ResponseBean.responseSuccess();
            }
            return ResponseBean.responseError("您输入的密码有误，请重新输入！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBean.responseError("操作失败");
        }
    }
//    /**
//     * 方法描述：sendMessage
//     * 作        者：TangY
//     * 日        期：2016年7月11日-下午5:58:40
//     * @param messageMap
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/sendMessage")
//    @ResponseBody
//    public ResponseBean sendMessage(@RequestBody Map<String,Object> messageMap)throws Exception {
//       String userId = (String)messageMap.get("messSender");
//        String companyId = (String)messageMap.get("companyId");
//        CompanyUserEntity companyUser =companyUserService.getCompanyUserByUserIdAndCompanyId(userId,companyId);
//        if(companyUser != null){
//            messageMap.put("messSender",companyUser.getId());
//        }else{
//            return ResponseBean.responseError("消息发送失败");
//
//        }
//
//        messageProducer.sendSystemMessage(systemMessageDestination,messageMap);
//        return ResponseBean.responseSuccess("消息发送成功");
//    }


//    /**
//     * 方法描述：获取未读信息
//     * 作    者：chenzhujie
//     * 日    期：2016年10月31日-上午11:52
//     *
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("/unReadInfo")
//    @ResponseBody
//    public ResponseBean unReadInfo(@RequestBody Map<String, Object> param) throws Exception {
//
//        Map<String,Object> paraMap=new HashMap<String,Object>();
//        if(null != param.get("accountId")){
//            paraMap.put("msReceiver",param.get("accountId"));
//        }
//        paraMap.put("msIsRead","0");
//        if(null != param.get("appOrgId")){
//            paraMap.put("msCompany",param.get("appOrgId"));
//        }
//        if(null != param.get("appOrgId")){
//            paraMap.put("msType","0");
//        }
//       int unReadCount = messageService.getMessageCountByParam(paraMap);
//       // int  unReadCount=messageDao.getMessageCountByParam(paraMap);
//        List<MessageEntity> messageEntities=messageService.getMessageByParam(paraMap);
//
//       // List<MessageEntity> messageEntities=messageDao.getMessageByParam(paraMap);
//        String unReadIds="";
//        for(MessageEntity messageEntity:messageEntities){
//            if("".equals(unReadIds)){
//                unReadIds=messageEntity.getId();
//            }
//            else {
//                unReadIds=unReadIds+","+messageEntity.getId();
//            }
//        }
//        return responseSuccess().addData("unReadCount", unReadCount).addData("unReadIds",unReadIds);
//    }


//
//    /**
//     * 方法描述：改变未读数据状态
//     * 作者：MaoSF
//     * 日期：2016/7/29
//     * @param:int pageSize,int pageIndex
//     *
//     * @return:
//     */
//    @RequestMapping("/messageUpdate")
//    @ResponseBody
//    public ResponseBean messageUpdate(@RequestBody Map<String,Object>paraMap) {
//        String messageIds=paraMap.get("messageIds").toString();
//        String[] arrayMessageId=messageIds.split("[,]");
//        for(int i=0;i<arrayMessageId.length;i++) {
//            MessageEntity messageEntity=new MessageEntity();
//            messageEntity.setId(arrayMessageId[i]);
//            messageEntity.setMsIsRead("1");
//            messageService.updateById(messageEntity);
//        }
//        return ResponseBean.responseSuccess("操作成功");
//    }

//    /**
//     * 方法描述：查询分页数据
//     * 作者：MaoSF
//     * 日期：2016/7/29
//     * @param:int pageSize,int pageIndex
//     *
//     * @return:
//     */
//    @RequestMapping("/message")
//    @ResponseBody
//    public ResponseBean message(@RequestBody Map<String,Object> paraMap)throws Exception {
//        Map<String,Object> paramMap=new HashMap<String,Object>();
//        if(null != paraMap.get("pageSize") && null != paraMap.get("pageIndex")){
//            paramMap.put("pageSize", Integer.parseInt(paraMap.get("pageSize").toString()));
//            paramMap.put("pageNumber", Integer.parseInt(paraMap.get("pageIndex").toString()));
//        }
//       /* if(null != paraMap.get("accountId")){
//            paramMap.put("msReceiver", paraMap.get("accountId"));
//        }*/
//        if(null != paraMap.get("appOrgId")){
//            paramMap.put("msCompany",paraMap.get("appOrgId"));
//        }
//        if(null != paraMap.get("ms_sender")){
//            CompanyUserEntity companyUser =companyUserService.getCompanyUserByUserIdAndCompanyId((String)paraMap.get("ms_sender"),(String)paraMap.get("appOrgId"));
//            paramMap.put("msSender",companyUser.getId());
//        }
//        if(null != paraMap.get("ms_receiver")){
//           // CompanyUserEntity companyUser =companyUserService.getCompanyUserByUserIdAndCompanyId((String)paraMap.get("ms_receiver"),(String)paraMap.get("appOrgId"));
//            paramMap.put("msReceiver",paraMap.get("ms_receiver"));
//        }
//        if(null != paraMap.get("msIsRead")){
//            paramMap.put("msIsRead",paraMap.get("msIsRead"));
//        }
//        //消息类型(1.系统消息,0.前端通知消息.2.为受邀请的消息,不在本表中的数据)
//        if(null != paraMap.get("msType")){
//            paramMap.put("msType",paraMap.get("msType"));
//        }
//        if(null != paraMap.get("appOrgId")){
//            paramMap.put("msType","0");
//        }
//
//        //下面两个参数是app选择查询条件时加上的
//        if(null != paraMap.get("type")){
//            paramMap.put("msType",paraMap.get("type"));
//        }
//        if(null != paraMap.get("orgId")){
//            paramMap.put("msCompany",paraMap.get("orgId"));
//        }
//
//       /* if(null!=type&&!"".equals(type)){
//            paramMap.put("msType",type);
//        }
//        if(null!=companyId&&!"".equals(companyId)){
//            paramMap.put("msCompany",companyId);
//        }*/
//        Map<String,Object> resultMap=new HashMap<String,Object>();
//       // resultMap=
//
//        Map<String,Object> reData = messageService.selectPageDataWs(paramMap);
//
//        paramMap.put("msIsRead","1");
//        //已读
//        Map<String,Object> isReadDataMap = messageService.selectPageDataWs(paramMap);
//
//        List<MessageEntity> isReadData = (List<MessageEntity>)isReadDataMap.get("data");
//        paramMap.put("msIsRead","0");
//        //未读
//        Map<String,Object> noReadDataMap = messageService.selectPageDataWs(paramMap);
//
//        List<MessageEntity> noReadData = (List<MessageEntity>)noReadDataMap.get("data");
//
//
//        List<MessageEntity> data = (List<MessageEntity>)reData.get("data");
//        handleData(data);
//        handleData(isReadData);
//        handleData(noReadData);
//
//        resultMap.put("data",data);
//        resultMap.put("isReadData",isReadData);
//        resultMap.put("noReadData",noReadData);
//       // paramMap=null;
//        return responseSuccess().setData(resultMap);
//    }

//    private void handleData(List<MessageEntity> data) {
//        for(MessageEntity me : data){
//            int a = DateUtils.formatDateTime(DateUtils.formatTime(me.getCreateDate()));
//            if(a == 1){
//                String date =  DateUtils.formatTime(me.getCreateDate());
//                me.setCurrentDatetime(date.split(" ")[1]);
//            }else{
//                me.setCurrentDatetime(DateUtils.formatDate(me.getCreateDate()));
//            }
//            String msResouce = me.getMsResouce();
//            String[] msResouceStr = null;
//            if(null != msResouce && !"".equals(msResouce)){
//                msResouceStr = msResouce.split(";");
//            }
//
//
//            String msResouceType = me.getMsResouceType();
//            String[] msResouceTypeStr = null;
//            if(null != msResouceType && !"".equals(msResouceType)){
//                msResouceTypeStr = msResouceType.split(";");
//            }
//            String  msResouceTypeName = "";
//            if(null != msResouceStr && !"".equals(msResouceStr) && null != msResouceTypeStr && !"".equals(msResouceTypeStr)){
//                for(int j = 0 ; j < msResouceTypeStr.length ; j ++){
//                    String id =  msResouceStr[j];
//                    String tempTypeName = "";
//
//                    //人
//                    if("person".equals(msResouceTypeStr[j])){
//                        CompanyUserEntity companyUserEntity = companyUserService.selectById(id);
//                        if(null !=companyUserEntity){
//                            tempTypeName = companyUserEntity.getUserName();
//                        }
//                        //部门
//                    }else if("depart".equals(msResouceTypeStr[j])){
//                        DepartEntity departEntity = departService.selectById(id);
//                        if(null !=departEntity){
//                            tempTypeName = departEntity.getDepartName();
//                        }
//                    }else{
//                        CompanyEntity companyEntity = companyService.selectById(id);
//                        if(null !=companyEntity) {
//                            tempTypeName = companyEntity.getCompanyName();
//                        }
//                    }
//                    if(j==0){
//                        msResouceTypeName = tempTypeName;
//                    }else{
//                        msResouceTypeName = msResouceTypeName + ";" +  tempTypeName;
//                    }
//                }
//            }
//            me.setMsResouceTypeName(msResouceTypeName);
//
//        }
//    }

//    /**
//     * 方法描述：删除消息
//     * 作者：MaoSF
//     * 日期：2016/7/29
//     * @param:int
//     *
//     * @return:
//     */
//    @RequestMapping("/deleteMessages")
//    @ResponseBody
//    public ResponseBean deleteMessage(@RequestBody Map<String,Object> paraMap) {
//        String ids = (String)paraMap.get("ids");
//        String[] idsStr = ids.split(",");
//        for(int i = 0 ; i < idsStr.length ; i++){
//            MessageEntity messageEntity =new MessageEntity();
//            messageEntity.setId(idsStr[i]);
//            messageEntity.setMsStatus("0");
//            messageService.updateById(messageEntity);
//        }
//        return ResponseBean.responseSuccess("消息删除成功");
//    }
}
