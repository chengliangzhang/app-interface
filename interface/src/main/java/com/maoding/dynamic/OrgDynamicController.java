package com.maoding.dynamic;

import com.maoding.core.bean.ResponseBean;
import com.maoding.core.util.BeanUtilsEx;
import com.maoding.dynamic.dto.ProjectDynamicDTO;
import com.maoding.dynamic.dto.QueryDynamicDTO;
import com.maoding.dynamic.service.DynamicService;
import com.maoding.dynamic.service.OrgDynamicService;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Idccapp22 on 2017/3/10.
 */
@Controller
@RequestMapping("/v2/dynamic")
public class OrgDynamicController extends BaseWSController {

    @Autowired
    private OrgDynamicService orgDynamicService;

    @Autowired
    private DynamicService dynamicService;
    /**
     * 方法描述：组织动态
     * 作者：MaoSF
     * 日期：2016/11/30
     * @param:
     * @return:
     */
    @RequestMapping("/getOrgDynamic")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean getOrgDynamic(@RequestBody Map<String,Object> map) throws Exception{
        return ResponseBean.responseSuccess("查询成功").addData("data",orgDynamicService.getLastOrgDynamicListByCompanyId(map));
    }


    /**
     * 方法描述：组织动态(更多)
     * 作者：MaoSF
     * 日期：2016/11/30
     * @param:
     * @return:
     */
    @RequestMapping("/getOrgDynamicList")
    @ResponseBody
    public ResponseBean getOrgDynamicList(@RequestBody Map<String,Object> map) throws Exception{
        return ResponseBean.responseSuccess("查询成功").addData("data",orgDynamicService.getOrgDynamicListByCompanyId(map));
    }


    /**
     * 方法描述：项目动态
     * 作者：MaoSF
     * 日期：2016/11/30
     * @param:
     * @return:
     */
    @RequestMapping("/getProjectDynamicByPage")
    @ResponseBody
    public ResponseBean getProjectDynamicByPage(@RequestBody Map<String,Object> map) throws Exception{
        QueryDynamicDTO query = new QueryDynamicDTO();
        BeanUtilsEx.copyProperties(map,query);
        ProjectDynamicDTO result = dynamicService.getProjectDynamic(query);
        ResponseBean resp = ResponseBean.responseSuccess();
        Map<String,Object> respMap = new HashMap<>();
        BeanUtilsEx.copyProperties(result,respMap);
        resp.setData(respMap);
        return  resp;
    }

}
