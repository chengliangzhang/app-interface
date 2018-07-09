package com.maoding.circle.service;

import com.maoding.circle.dto.*;
import com.maoding.core.bean.ResponseBean;

import java.util.List;
import java.util.Map;

/**
 * Creator: sandy
 * Date:2017/11/14
 * 类名：app-interface
 */
public interface MaodingCircleService {

    /**
     *@Author：MaoSF
     *Description：发卯丁圈
     *@Date:2017/11/14
    */
    ResponseBean publishMaodingCircle(MaodingCircleDTO dto) throws Exception;

    /**
     *@Author：MaoSF
     *Description：评论
     *@Date:2017/11/14
     */
    ResponseBean commentMaodingCircle(MaodingCircleCommentDTO dto) throws Exception;


    Map<String,Object> listMaodingCircle(QueryCircleDTO query)  throws Exception;

    /**
     * 获取详情
     */
    MaodingCircleDataDTO getMaodingCircleById(QueryCircleDTO query) throws Exception;

    /**
     *@Author：MaoSF
     *Description：评论
     *@Date:2017/11/14
     */
    ResponseBean deleteCommentMaodingCircle(MaodingCircleCommentDTO dto) throws Exception;

    /**
     *@Author：MaoSF
     *Description：删除讨论
     *@Date:2017/11/14
     */
    ResponseBean deleteMaodingCircle(MaodingCircleDTO dto) throws Exception;

    /**
     *@Author：MaoSF
     *Description：删除消息列表中的讨论组块
     *@Date:2017/11/14
     */
    ResponseBean deleteMaodingCircleRead(QueryCircleDTO dto) throws Exception;


    List<MaodingCircleNoticeDTO> getNotReadCircle(QueryCircleDTO query) throws Exception;

    List<MaodingCircleCommentDataDTO> getNotReadProjectCircle(QueryCircleDTO query) throws Exception;

    MaodingCircleCommentDataDTO getComment(MaodingCircleReadDTO read,String accountId) throws Exception;

}
