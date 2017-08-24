package com.maoding.v2.im.controller;

import com.maoding.core.base.dto.BaseDTO;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.util.StringUtil;
import com.maoding.hxIm.dto.*;
import com.maoding.hxIm.service.ImService;
import com.maoding.org.dto.ImUserInfoQueryDTO;
import com.maoding.org.service.CompanyUserService;
import com.maoding.project.service.ProjectService;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import com.maoding.user.entity.UserAttachEntity;
import com.maoding.user.entity.UserEntity;
import com.maoding.user.service.UserAttachService;
import com.maoding.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Idccapp21 on 2016/8/1.
 */
@Controller
@RequestMapping("/v2/im")
public class V2ImController extends BaseWSController {

    @Autowired
    public UserAttachService userAttachService;

    @Autowired
    public UserService userService;

    @Autowired
    private CompanyUserService companyUserService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ImService imService;
    /**
     * 方法描述：群组信息接口(公司群)
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/imGroupInfo")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean imGroupInfo(@RequestBody ImGroupQuery query) throws Exception {
        return ResponseBean.responseSuccess().addDataFromObject(this.imService.imGroupInfo(query));
    }

    /**
     * 方法描述：查询用户信息
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/imUserInfo")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean imUserInfo(@RequestBody Map<String, Object> map) throws Exception {
        map.put("userId", map.get("accountId"));
        UserEntity personal = userService.selectById(map.get("uid"));
        String headImg = "";
        if (personal == null) {
            personal = new UserEntity();
        } else {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("userId", map.get("uid"));
            param.put("attachType", "5");//个人头像
            List<UserAttachEntity> userAttachList = userAttachService.getAttachByType(param);
            headImg = userAttachList.size() > 0 ? (fastdfsUrl + userAttachList.get(0).getFileGroup() + "/" + userAttachList.get(0).getAttachPath()) : "";
        }
        return responseSuccess()
                .addData("name", personal.getUserName())
                .addData("img", headImg)
                .addData("sex", personal.getSex());
    }

    /**
     * 方法描述：新增自定义群组
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("saveImgroup")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean saveImgroup(@RequestBody ImGroupCustomerDTO dto) throws Exception {
        return imService.saveCustomGroup(dto);
    }

    /**
     * 方法描述：修改自定义群组
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("updateImgroup")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean updateImgroup(@RequestBody ImGroupCustomerDTO dto) throws Exception {
        return imService.updateCustomGroup(dto);
    }

    /**
     * 方法描述：添加成员到自定义群组
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("addUserListToCustomGroup")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean addUserListToCustomGroup(@RequestBody ImGroupCustomerDTO dto) throws Exception {
        return imService.addMemberToGroup(dto);
    }

    /**
     * 方法描述：修改自定义群组
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("exitGroup")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean exitGroup(@RequestBody ImGroupUserDTO dto) throws Exception {
       return imService.deleteMemberFromGroup(dto);
    }

    /**
     * 方法描述：删除自定义群组
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("deleteImgroup")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean deleteImgroup(@RequestBody ImGroupCustomerDTO dto) throws Exception {
        //删除群组
       return imService.deleteGroup(dto);
    }


    /**
     * 方法描述：查询用户信息
     * 作    者 : ChenZhujie
     * 日    期 : 2016/12/23
     */
    @RequestMapping("/newImUserInfo")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean newImUserInfo(@RequestBody ImUserInfoQueryDTO query) throws Exception {
        return ResponseBean.responseSuccess("查询成功").addData("userInfo", this.companyUserService.getImUserInfo(query));
    }


    /**
     * 方法描述：查询项目群成员
     * 作    者 : MaoSF
     * 日    期 : 2017/05/23
     */
    @RequestMapping("/getProjectGroupUser")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getProjectGroupUser(@RequestBody Map<String, Object> map) throws Exception {

        if (map.get("projectId") == null) {
            return ResponseBean.responseError("获取信息失败");
        }
        return ResponseBean.responseSuccess().addData("groupList", this.projectService.getProjectParticipantsList((String) map.get("projectId")));
    }

    /**
     * 方法描述：查询自定义群的人员信息
     * 作    者 : MaoSF
     * 日    期 : 2017/05/23
     */
    @RequestMapping("/listCustomerImGroup")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean listCustomerImGroup(@RequestBody Map<String, Object> map) throws Exception {
        if (map.get("groupId") == null) {
            return ResponseBean.responseError("获取信息失败");
        }
        return ResponseBean.responseSuccess().addData("companyUserList", this.imService.listCustomerImGroupMember((String) map.get("groupId")));
    }

    /**
     * 方法描述：获取当前人所有群组及群组成员
     * 作    者 : MaoSF
     * 日    期 : 2017/05/23
     */
    @RequestMapping("/getAllImGroupByUserId")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getAllImGroupByUserId(@RequestBody ImGroupQuery query) throws Exception {
        return ResponseBean.responseSuccess().addData("groupList",this.imService.listAllGroupByUserId(query));
    }

}
