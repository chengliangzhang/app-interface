package com.maoding.message.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.ResponseBean;
import com.maoding.message.entity.MessageEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by Idccapp21 on 2016/10/20.
 */
public interface MessageService extends BaseService<MessageEntity> {

    ResponseBean sendMessage(MessageEntity messageEntity) throws Exception;

    /**
     * 方法描述：发送消息
     * 作者：MaoSF
     * 日期：2017/6/8
     */
    ResponseBean sendMessage(String projectId, String companyId, String targetId, int messageType, String paramId,
                             String userId, String accountId, String content) throws Exception;

    /**
     * 通用的发送消息方法
     * @return: 发送失败原因，如果成功，返回null
     */
    <T> String sendMessage(T origin, T target, List<String> toUserIdList, String projectId, String companyId, String userId) throws Exception;

    ResponseBean sendMessage(List<MessageEntity> messageList) throws Exception;

    /**
     * 方法描述：获取消息
     * 作者：MaoSF
     * 日期：2017/3/17
     * @param:
     * @return:
     */
    ResponseBean getMessage(Map<String, Object> map) throws Exception;


    /**
     * 方法描述：根据关键字删除
     * 作者：MaoSF
     * 日期：2017/3/25
     * @param:
     * @return:
     */
    int deleteMessage(String field) throws Exception;

}
