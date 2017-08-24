package com.maoding.message.controller;

import com.maoding.core.bean.ResponseBean;
import com.maoding.message.service.MessageService;
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
     * @param:
     * @return:
     */
    @RequestMapping("/getMessage")
    @ResponseBody
    public ResponseBean getMessage(@RequestBody Map<String,Object> map) throws Exception{
        return messageService.getMessage(map);
    }
}
