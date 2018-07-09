package com.maoding.notice.service;

import com.maoding.core.base.service.BaseService;
import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.message.dto.SendMessageDataDTO;
import com.maoding.notice.dto.NoticeDTO;
import com.maoding.notice.dto.NoticeDataDTO;
import com.maoding.notice.entity.NoticeEntity;
import com.maoding.project.dto.ProjectDesignContentDTO;

import java.util.List;
import java.util.Map;


/**
 * 深圳市设计同道技术有限公司
 * 类    名：NoticeService
 * 类描述：通知公告Service
 * 作    者：MaoSF
 * 日    期：2016年11月30日-下午3:10:45
 */
public interface NoticeService extends BaseService<NoticeEntity> {

    /**
     * 方法描述：保存通知公告
     * 作者：MaoSF
     * 日期：2016/11/30
     * @param:
     * @return:
     */
    AjaxMessage saveNotice(NoticeDTO dto) throws Exception;

    /**
     * 方法描述：获取未读消息数量
     * 作        者：MaoSF
     * 日        期：2016年11月30日-下午3:33:39
     */
    int getNoticeCountByParam(Map<String, Object> paraMap) throws Exception;

    /**
     * 方法描述：获取未读消息记录
     * 作        者：MaoSF
     * 日        期：2016年11月30日-下午3:33:39
     */
    List<NoticeDTO> getNoticeByParam(Map<String, Object> paraMap) throws Exception;

    /**
     * 方法描述：删除公告
     * 作者：MaoSF
     * 日期：2016/11/30
     * @param:
     * @return:
     */
    AjaxMessage deleteNotice(String id) throws Exception;

    /**
     * 方法描述：批量删除公告
     * 作者：MaoSF
     * 日期：2016/11/30
     * @param:
     * @return:
     */
    AjaxMessage deleteNoticeForBatch(List<String> idList) throws Exception;


    /**
     * 方法描述：根据id获取公告
     * 作者：MaoSF
     * 日期：2016/12/1
     * @param:
     * @return:
     */
    NoticeDTO getNoticeById(String id) throws Exception;

    /**
     * 方法描述：根据id获取公告
     * 作者：MaoSF
     * 日期：2016/12/1
     * @param:
     * @return:
     */
    NoticeDTO getNoticeByIdForDynamics(String id) throws Exception;


    /**
     * 方法描述：根据companyId获取公告
     * 作者：MaoSF
     * 日期：2016/12/1
     * @param:
     * @return:
     */
    List<NoticeDTO> getNoticeByCompanyId(Map<String, Object> paraMap) throws Exception;


    /**
     * 方法描述：获取本公司发布的公告数量
     * 作        者：MaoSF
     * 日        期：2016年11月30日-下午3:33:39
     */
    int getNoticeCountByCompanyId(Map<String, Object> paraMap) throws Exception;

    /**
     * 方法描述：获取公告，并且按照时间分组
     * 作者：MaoSF
     * 日期：2017/6/8
     */
    List<NoticeDataDTO> getNoticeGroupTime(Map<String, Object> param) throws Exception;

    /** 发送通知 **/
    void notify(String notifyDestination, SendMessageDataDTO dto);

    /**
     * 项目立项生产公告
     */
    void saveNoticeForProject(String projectId, String companyId, String createPersonId, String currentCompanyUserId, List<ProjectDesignContentDTO> designContent) throws Exception;


}
