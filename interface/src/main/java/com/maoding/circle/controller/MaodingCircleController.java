package com.maoding.circle.controller;

import com.maoding.circle.dto.MaodingCircleCommentDTO;
import com.maoding.circle.dto.MaodingCircleDTO;
import com.maoding.circle.dto.QueryCircleDTO;
import com.maoding.circle.service.MaodingCircleService;
import com.maoding.core.bean.ResponseBean;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("v2/circle/")
public class MaodingCircleController extends BaseWSController{

    @Autowired
    private MaodingCircleService maodingCircleService;


    /**
     * 方法描述：项目讨论组查询列表
     * 作   者：MaoSF
     * 日   期：2018/03/15 15:08
     */
    @RequestMapping("/listMaodingCircle")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean listMaodingCircle(@RequestBody QueryCircleDTO query) throws Exception {
        return ResponseBean.responseSuccess().setData(maodingCircleService.listMaodingCircle(query));
    }


    /**
     * 方法描述：项目讨论组查询详情
     * 作   者：MaoSF
     * 日   期：2018/03/15 15:08
     */
    @RequestMapping("/getMaodingCircleById")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getMaodingCircle(@RequestBody QueryCircleDTO query) throws Exception {
        return ResponseBean.responseSuccess().addData("circleDetail",maodingCircleService.getMaodingCircleById(query));
    }

    /**
     * 方法描述：发布项目讨论
     * 作   者：MaoSF
     * 日   期：2018/03/15 15:08
     */
    @RequestMapping("/publishMaodingCircle")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean publishMaodingCircle(@RequestBody MaodingCircleDTO dto) throws Exception {
        return maodingCircleService.publishMaodingCircle(dto);
    }



    /**
     * 方法描述：评论项目讨论
     * 作   者：MaoSF
     * 日   期：2018/03/15 15:08
     */
    @RequestMapping("/commentMaodingCircle")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean commentMaodingCircle(@RequestBody MaodingCircleCommentDTO dto) throws Exception {
        return maodingCircleService.commentMaodingCircle(dto);
    }


    /**
     * 方法描述：删除项目讨论评论
     * 作   者：MaoSF
     * 日   期：2018/03/15 15:08
     */
    @RequestMapping("/deleteCommentMaodingCircle")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean deleteCommentMaodingCircle(@RequestBody MaodingCircleCommentDTO dto) throws Exception {
        return maodingCircleService.deleteCommentMaodingCircle(dto);
    }


    /**
     * 方法描述：删除项目讨论评论
     * 作   者：MaoSF
     * 日   期：2018/03/15 15:08
     */
    @RequestMapping("/deleteMaodingCircle")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean deleteMaodingCircle(@RequestBody MaodingCircleDTO dto) throws Exception {
        return maodingCircleService.deleteMaodingCircle(dto);
    }

    /**
     * 方法描述：删除项目讨论评论
     * 作   者：MaoSF
     * 日   期：2018/03/15 15:08
     */
    @RequestMapping("/deleteMaodingCircleRead")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean deleteMaodingCircleRead(@RequestBody QueryCircleDTO dto) throws Exception {
        return maodingCircleService.deleteMaodingCircleRead(dto);
    }

    /**
     * 方法描述：未读项目讨论评论信息
     * 作   者：MaoSF
     * 日   期：2018/03/15 15:08
     */
    @RequestMapping("/getNotReadCircle")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getNotReadCircle(@RequestBody QueryCircleDTO query) throws Exception {
        return ResponseBean.responseSuccess().addData("circleCommentList",maodingCircleService.getNotReadProjectCircle(query));
    }
}
