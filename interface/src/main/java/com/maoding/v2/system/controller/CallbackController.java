package com.maoding.v2.system.controller;

import com.maoding.core.bean.ApiResult;
import com.maoding.core.component.sms.SmsSender;
import com.maoding.core.component.sms.bean.SenderResponseBean;
import com.maoding.core.component.sms.bean.Sms;
import com.maoding.core.constant.SystemParameters;
import com.maoding.core.util.StringUtil;
import com.maoding.v2.project.dto.CallbackDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Created by sandy on 2017/11/1.
 */
@RestController
@RequestMapping("/fileCenter/callback")
public class CallbackController {

    Logger logger = LoggerFactory.getLogger(CallbackController.class);

    @Autowired
    private SmsSender smsSender;

    /**
     * 上传项目合同扫描件
     */
    @RequestMapping(value = "/callbackNotParam", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult callbackNotParam() throws Exception {

        Sms sms = new Sms();
        sms.addMobile("18665965065");
        sms.setMsg("测试callbackNotParam");
        smsSender.send(sms);

        return ApiResult.success("请求成功","测试数据");
    }


    /**
     * 上传项目合同扫描件
     */
    @RequestMapping(value = "/callbackParam", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult callbackParam(@RequestBody CallbackDTO callback) throws Exception {
        logger.info("cellphone==="+callback.getCellphone()+"  password=="+callback.getPassword());
        logger.error("bucket==="+callback.getBucket()+"  size=="+callback.getSize());

        Sms sms = new Sms();
        sms.addMobile("18665965065");
        sms.setMsg("测试callbackParam成功");
        smsSender.send(sms);
        return ApiResult.success("请求成功",callback);
    }
}
