package com.maoding.v2.feedback;

import com.maoding.core.bean.ResponseBean;
import com.maoding.feedback.dto.FeedbackDTO;
import com.maoding.feedback.service.FeedbackService;
import com.maoding.system.controller.BaseWSController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by sandy on 2017/9/26.
 */
@Controller
@RequestMapping("v2/feedback")
public class FeedbackController extends BaseWSController {

    @Autowired
    private FeedbackService feedbackService;
    /**
     * 方法描述：反馈
     * 作者：MaoSF
     * 日期：2016/11/30
     * @param:
     * @return:
     */
    @RequestMapping("/saveFeedback")
    @ResponseBody
    public ResponseBean saveFeedback(@RequestBody FeedbackDTO dto) throws Exception{
        return this.feedbackService.saveFeedback(dto);
    }

}
