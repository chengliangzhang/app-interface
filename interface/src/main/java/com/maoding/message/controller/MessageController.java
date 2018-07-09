package com.maoding.message.controller;

import com.maoding.core.bean.ResponseBean;
import com.maoding.message.dto.QueryMessageDTO;
import com.maoding.message.service.MessageService;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by Idccapp22 on 2017/3/17.
 */
@Controller
@RequestMapping("/v2/message")
public class MessageController extends BaseWSController {

    @Autowired
    private MessageService messageService;
    /**
     * 方法描述：组织动态
     * 作者：MaoSF
     * 日期：2016/11/30
     */
    @RequestMapping("/getMessage")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getMessage(@RequestBody Map<String,Object> map) throws Exception{
        return messageService.getMessage(map);
    }

    /**
     * 方法描述：获取最新一条未读消息
     * 作者：MaoSF
     * 日期：2016/11/30
     */
    @RequestMapping("/getLastMessage")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getLastMessage(@RequestBody Map<String,Object> map) throws Exception{
        return messageService.getLastMessage(map);
    }


    /**
     * 方法描述：获取最新一条未读消息
     * 作者：MaoSF
     * 日期：2016/11/30
     */
    @RequestMapping("/readMessage")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean readMessage(@RequestBody QueryMessageDTO dto) throws Exception{
        return messageService.readMessage(dto);
    }

    /**
     * 方法描述：把卯丁秘书的消息全部清空
     * 作者：MaoSF
     * 日期：2016/11/30
     */
    @RequestMapping("/clearMessage")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean clearMessage(@RequestBody QueryMessageDTO dto) throws Exception{
        return messageService.clearMessage(dto);
    }

}
