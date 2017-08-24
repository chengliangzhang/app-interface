package com.maoding.v2.controller;

import com.maoding.attention.dto.AttentionDTO;
import com.maoding.attention.service.AttentionService;
import com.maoding.core.bean.ResponseBean;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：AttentionController
 * 类描述：个人关注Controller
 * 作    者：MaoSF
 * 日    期：2017年2月14日-下午3:12:45
 */
@Controller
@RequestMapping("/v2/attention")
public class AttentionController extends BaseWSController {

    @Autowired
    public AttentionService attentionService;

    /**
     * 方法描述：关注
     * 作者：MaoSF
     * 日期：2017/2/14
     * @param:
     * @return:
     */
    @RequestMapping("/saveAttention")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean saveAttention(@RequestBody AttentionDTO dto) throws Exception{
        return this.attentionService.addAttention(dto);
    }

    /**
     * 方法描述：取消关注
     * 作者：MaoSF
     * 日期：2017/2/14
     * @param:
     * @return:
     */
    @RequestMapping("/cancelAttention")
    @AuthorityCheckable
    @ResponseBody
    public ResponseBean cancelAttention(@RequestBody AttentionDTO dto) throws Exception{
        return this.attentionService.cancelAttention(dto);
    }
}
