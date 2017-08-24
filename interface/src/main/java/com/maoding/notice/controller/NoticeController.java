package com.maoding.notice.controller;

import com.maoding.core.bean.AjaxMessage;
import com.maoding.core.bean.ResponseBean;
import com.maoding.notice.dto.NoticeDTO;
import com.maoding.notice.dto.NoticeOrgDTO;
import com.maoding.notice.service.NoticeService;
import com.maoding.org.dto.OrgTreeWsDTO;
import com.maoding.org.service.CompanyService;
import com.maoding.org.service.CompanyUserService;
import com.maoding.system.annotation.AuthorityCheckable;
import com.maoding.system.controller.BaseWSController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Idccapp21 on 2016/7/27.
 */
@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseWSController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyUserService companyUserService;

    /**
     * 方法描述：保存通知公告
     * 作者：MaoSF
     * 日期：2016/11/30
     * @param:
     * @return:
     */
    @RequestMapping("/saveNotice")
    @ResponseBody
    public ResponseBean saveNotice(@RequestBody NoticeDTO dto) throws Exception{
       dto.setCurrentCompanyId(dto.getAppOrgId());
       dto.setCompanyId(dto.getAppOrgId());
       AjaxMessage ajaxMessage =  this.noticeService.saveNotice(dto);
       if("0".equals(ajaxMessage.getCode())){
           return ResponseBean.responseSuccess("保存成功");
       }
       return ResponseBean.responseError((String)ajaxMessage.getInfo());
    }


    /**
     * 方法描述：根据主键获取通知公告
     * 作        者：MaoSF
     * 日        期：2016年7月8日-下午3:14:31
     *
     * @return
     */
    @RequestMapping("/getNoticeById")
    @ResponseBody
    @AuthorityCheckable
    public ResponseBean getNoticeById(@RequestBody NoticeDTO param) throws Exception {
        NoticeDTO dto = this.noticeService.getNoticeById(param.getId());
        return responseSuccess().addData("NoticeDTO", dto);
    }


    /**
     * 方法描述：删除通知公告
     * 作者：MaoSF
     * 日期：2016/11/30
     * @param:
     * @return:
     */
    @RequestMapping("/deleteNotice")
    @ResponseBody
    public ResponseBean deleteNotice(@RequestBody NoticeDTO dto) throws Exception{
        AjaxMessage ajaxMessage =  this.noticeService.deleteNotice(dto.getId());
        if("0".equals(ajaxMessage.getCode())){
            return ResponseBean.responseSuccess("删除通知公告成功");
        }
        return ResponseBean.responseError("删除通知公告失败！");
    }


    /**
     * 方法描述：批量删除通知公告
     * 作者：MaoSF
     * 日期：2016/11/30
     * @param:
     * @return:
     */
    @RequestMapping("/deleteNoticeForBatch")
    @ResponseBody
    public ResponseBean deleteNoticeForBatch(@RequestBody Map<String,List<String>> idList) throws Exception{
        AjaxMessage ajaxMessage =  this.noticeService.deleteNoticeForBatch(idList.get("idList"));
        if("0".equals(ajaxMessage.getCode())){
            return ResponseBean.responseSuccess("批量删除通知公告成功");
        }
        return ResponseBean.responseError("批量删除通知公告失败！");
    }
    public static String delHTMLTag(String htmlStr){
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script=p_script.matcher(htmlStr);
        htmlStr=m_script.replaceAll(""); //过滤script标签

        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(htmlStr);
        htmlStr=m_style.replaceAll(""); //过滤style标签

        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(htmlStr);
        htmlStr=m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }
    /**
     * 方法描述：获取通知公告
     * 作者：MaoSF
     * 日期：2016/11/30
     * @param:
     * @return:
     */
    @RequestMapping("/getNotice")
    @ResponseBody
    public ResponseBean getNotice(@RequestBody Map<String,Object> para) throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("companyId",para.get("appOrgId"));
        param.put("userId",para.get("accountId"));
        if(null != para.get("pageSize")){
            param.put("pageSize", Integer.parseInt(para.get("pageSize").toString()));
        }
        if(null != para.get("pageIndex")){
            param.put("pageNumber", Integer.parseInt(para.get("pageIndex").toString()));
        }
        List<NoticeDTO> data = this.noticeService.getNoticeByParam(param);
        for(NoticeDTO noticeDTO : data){
            noticeDTO.setNoticeContent(delHTMLTag(noticeDTO.getNoticeContent()));
        }

        int totalNumber = this.noticeService.getNoticeCountByParam(param);
        param.clear();
        param.put("data",data);
        param.put("total", totalNumber);
       /* CompanyUserEntity companyUser =companyUserService.getCompanyUserByUserIdAndCompanyId((String)para.get("accountId"),(String)para.get("appOrgId"));
        String companyUserId = companyUser.getId();
        param.put("companyUserId",companyUserId);
        List<NoticeDTO> dataPublish = this.noticeService.getNoticeByParam(param);
        int totalNumberPublish = this.noticeService.getNoticeCountByParam(param);*/

        Map<String,Object> param2 = new HashMap<String,Object>();
        param2.put("companyId",para.get("appOrgId"));
        if(null != para.get("pageSize")){
            param2.put("pageSize", Integer.parseInt(para.get("pageSize").toString()));
        }
        if(null != para.get("pageIndex")){
            param2.put("pageNumber", Integer.parseInt(para.get("pageIndex").toString()));
        }
        List<NoticeDTO> dataPublish = this.noticeService.getNoticeByCompanyId(param2);
        for(NoticeDTO noticeDTO : dataPublish){
            noticeDTO.setNoticeContent(delHTMLTag(noticeDTO.getNoticeContent()));
        }
        int totalNumberPublish = this.noticeService.getNoticeCountByCompanyId(param2);
        param.put("dataPublish",dataPublish);
        param.put("totalNumberPublish", totalNumberPublish);

        for(int i = 0 ; i < dataPublish.size();i++){
            NoticeDTO noticeDTO = dataPublish.get(i);
            List<NoticeOrgDTO> noticeOrgList = noticeDTO.getNoticeOrgList();
            String noticeOrgNames = "";
            for(int j = 0 ; j < noticeOrgList.size();j++){
                if(j==0){
                    noticeOrgNames = noticeOrgList.get(j).getOrgName();
                }else{
                    noticeOrgNames = noticeOrgNames + "," + noticeOrgList.get(j).getOrgName();
                }
            }
            noticeDTO.setNoticeOrgNames(noticeOrgNames);
        }
        return responseSuccess().addData(param);
    }


    /**
     * 方法描述：根据公司id通知公告（用于公告维护界面）
     * 作者：MaoSF
     * 日期：2016/11/30
     * @param:
     * @return:
     */
    @RequestMapping("/getNoticeByCompanyId")
    @ResponseBody
    public ResponseBean getNoticeByCompanyId(@RequestBody Map<String,Object> para) throws Exception {
        Map<String,Object> param = new HashMap<String,Object>();
        param.put("companyId",para.get("appOrgId"));
        if(null != para.get("pageSize")){
            param.put("pageSize", Integer.parseInt(para.get("pageSize").toString()));
        }
        if(null != para.get("pageIndex")){
            param.put("pageNumber", Integer.parseInt(para.get("pageIndex").toString()));
        }
        List<NoticeDTO> data = this.noticeService.getNoticeByCompanyId(param);
        int totalNumber = this.noticeService.getNoticeCountByCompanyId(param);
        param.clear();
        param.put("data",data);
        param.put("total", totalNumber);
        return responseSuccess().addData(param);
    }

    /**
     * 方法描述：发送通知公告界面获取组织架构树
     * 作者：MaoSF
     * 日期：2016/12/8
     * @param:
     * @return:
     */
    @RequestMapping("/getOrgTree")
    @ResponseBody
    public ResponseBean getOrgTree(@RequestBody Map<String,Object> param) throws Exception{
        OrgTreeWsDTO tree = this.companyService.getOrgTreeForNotice(param);
        param.clear();
        param.put("orgTree",tree);
        return responseSuccess().addData(param);
    }
}
