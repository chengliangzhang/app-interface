import com.maoding.Application;
import com.maoding.core.bean.ApiResult;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.util.DateUtils;
import com.maoding.core.util.JsonUtils;
import com.maoding.core.util.OkHttpUtils;
import com.maoding.core.util.SecretUtil;
import com.maoding.enterprise.dto.EnterpriseSearchQueryDTO;
import com.maoding.labor.dto.LaborHourDTO;
import com.maoding.labor.dto.LaborHourDataDTO;
import com.maoding.labor.dto.SaveLaborHourDTO;
import com.maoding.labor.service.LaborHourService;
import com.maoding.org.dto.CompanyUserAppDTO;
import com.maoding.project.constDefine.EnterpriseServer;
import com.maoding.project.dto.ProjectDTO;
import com.maoding.project.dto.ProjectDesignContentDTO;
import com.maoding.project.dto.ProjectSimpleDataDTO;
import com.maoding.project.service.ProjectService;
import com.maoding.schedule.dto.*;
import com.maoding.schedule.service.ScheduleService;
import com.maoding.user.dto.AccountDTO;
import com.maoding.user.service.AccountService;
import junit.framework.Test;
import junit.framework.TestSuite;
import okhttp3.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProjectServiceImpl Tester.
*/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class ProjectServiceImplTest {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private LaborHourService laborHourService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private AccountService accountService;

    @Before
    public void setUp() throws Exception {

        if(projectService==null){
            tearDown();
        }
        System.out.print(projectService);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("-----------------------");
    }

    @org.junit.Test
    public void handleProjectConstruct() throws Exception {

//        dto.setAppOrgId("210b23a226984f8d8b00e176a24f2dd5");
//        dto.setCompanyId("210b23a226984f8d8b00e176a24f2dd5");
//        dto.setProjectName("testxiangmu");
//        dto.setAccountId("8b11983cd972427fb3ef5640bd3cde81");
//        dto.setCity("深圳市");
//        dto.setProvince("广东");
//        dto.setConstructCompany("8b558c8e-873f-11e7-8a67-70106fac13fa");
        EnterpriseSearchQueryDTO query = new EnterpriseSearchQueryDTO();
        query.setEnterpriseId("8b558c8e-873f-11e7-8a67-70106fac13fa");
        query.setCompanyId("210b23a226984f8d8b00e176a24f2dd5");
        query.setSave(true);
        Response res = null;
        try {
            res = OkHttpUtils.postJson(EnterpriseServer.URL_ENTERPRISE_QUERY_DETAIL, query);
        } catch (IOException e) {
        } catch (Exception e) {
            //logger.error("imServerRequest 请求工商数据异常", e);
        }
        if (res.isSuccessful()) {
            try {
                ApiResult<Map<String,Object>> apiResult = JsonUtils.json2pojo(res.body().string(), ApiResult.class);
                ResponseBean responseBean = new ResponseBean();
                responseBean.setError(apiResult.getCode());
                responseBean.setMsg(apiResult.getMsg());
                String enterpriseOrgId = (String)apiResult.getData().get("enterpriseOrgId");
                System.out.println(enterpriseOrgId);
            } catch (Exception e) {
                //logger.error("imServerRequest 解析返回结果失败", e);
            }
        } else {
            //logger.info("自定义群失败：" + res.message());
        }
    }
    @org.junit.Test
    public void testSaveOrUpdateProjectNew() throws Exception {
        ProjectDTO dto = new ProjectDTO();
        dto.setAppOrgId("210b23a226984f8d8b00e176a24f2dd5");
        dto.setCompanyId("210b23a226984f8d8b00e176a24f2dd5");
        dto.setProjectName("testxiangmu");
        dto.setAccountId("8b11983cd972427fb3ef5640bd3cde81");
        dto.setCity("深圳市");
        dto.setProvince("广东");
        dto.setConstructCompany("8b558c8e-873f-11e7-8a67-70106fac13fa");

        List<ProjectDesignContentDTO> contentList = new ArrayList<ProjectDesignContentDTO>();
        ProjectDesignContentDTO contentDTO = new ProjectDesignContentDTO();
        contentDTO.setContentName("hello");
        contentList.add(contentDTO);
        dto.setProjectDesignContentList(contentList);
        this.projectService.saveOrUpdateProjectNew(dto);
        //TODO: Test goes here...
    }



    @org.junit.Test
    public void testGetProjectListByConditionCount() throws Exception {
        //TODO: Test goes here...
    }

    @org.junit.Test
    public void testGetProjectById() throws Exception {
        //TODO: Test goes here...
    }

    @org.junit.Test
    public void testGetProjectDetail() throws Exception {
        //TODO: Test goes here...
    }

    @org.junit.Test
    public void testGetProjectListByCompanyId() throws Exception {
        //TODO: Test goes here...
    }

    @org.junit.Test
    public void testGetAddProjectOfBaseData() throws Exception {
        //TODO: Test goes here...
    }

    @org.junit.Test
    public void testGetProjectsAbouts() throws Exception {
        //TODO: Test goes here...
    }

    @org.junit.Test
    public void testGetProjectInfo() throws Exception {
        //TODO: Test goes here...
    }

    @org.junit.Test
    public void testGetProjectBasicNum() throws Exception {
        //TODO: Test goes here...
    }

    @org.junit.Test
    public void registerAccount() throws Exception {

        AccountDTO dto1 = new AccountDTO();
        dto1.setCellphone("91120150512");
        dto1.setPassword( SecretUtil.MD5("123456"));//默认密码);
        dto1.setUserName("甲");

        AccountDTO dto2 = new AccountDTO();
        dto2.setCellphone("91120150502");
        dto2.setPassword( SecretUtil.MD5("123456"));//默认密码);
        dto2.setUserName("乙");

        AccountDTO dto3 = new AccountDTO();
        dto3.setCellphone("91120150503");
        dto3.setPassword( SecretUtil.MD5("123456"));//默认密码);
        dto3.setUserName("丙");

        AccountDTO dto4 = new AccountDTO();
        dto4.setCellphone("91120150504");
        dto4.setPassword( SecretUtil.MD5("123456"));//默认密码);
        dto4.setUserName("丁");

        AccountDTO dto5 = new AccountDTO();
        dto5.setCellphone("91120150505");
        dto5.setPassword( SecretUtil.MD5("123456"));//默认密码);
        dto5.setUserName("戊");

        AccountDTO dto6 = new AccountDTO();
        dto6.setCellphone("91120150506");
        dto6.setPassword( SecretUtil.MD5("123456"));//默认密码);
        dto6.setUserName("戌");

        this.accountService.register(dto1);
//        this.accountService.register(dto2);
//        this.accountService.register(dto3);
//        this.accountService.register(dto4);
//        this.accountService.register(dto5);
//        this.accountService.register(dto6);
    }

    /**
     * 保存会议
     */
    @org.junit.Test
    public void testSaveSchedule() throws Exception {
        //测试日程
        SaveScheduleDTO dto = new SaveScheduleDTO();
        dto.setAccountId("8b11983cd972427fb3ef5640bd3cde81");
        dto.setAppOrgId("ad89564855ad4cc2a39bcb602b0a02a8");
        dto.setCompanyId("ad89564855ad4cc2a39bcb602b0a02a8");
        dto.setContent("test1222");
        dto.setRemark("测试会议");
        dto.setScheduleStartTime("2018/01/05 13:30");
        dto.setScheduleEndTime("2018/01/05 15:30");
        dto.setScheduleType(2);
        dto.setReminderMode(1);
        dto.setReminderTime(30); // 提前30分钟提醒
        CompanyUserAppDTO user1 = new CompanyUserAppDTO();
        user1.setId("9d5276704ad543abb57a5b2a81cf9995");
        user1.setUserId("8b11983cd972427fb3ef5640bd3cde81");
        CompanyUserAppDTO user2 = new CompanyUserAppDTO();
        user2.setId("7e4c82f170c646ac872fbcc3ad233f8d");
        user2.setUserId("9b6971e5414948b3aff09226d44e1ff9");
        dto.getMemberList().add(user1);
        dto.getMemberList().add(user2);
//        dto.setUserIds(Arrays.asList("8b11983cd972427fb3ef5640bd3cde81","9b6971e5414948b3aff09226d44e1ff9"));
//        dto.setMemberIds(Arrays.asList("9d5276704ad543abb57a5b2a81cf9995","7e4c82f170c646ac872fbcc3ad233f8d"));
        this.scheduleService.saveSchedule(dto);
    }


    /**
     * 保存日程
     */
    @org.junit.Test
    public void testSaveSchedule2() throws Exception {
        //测试日程
        SaveScheduleDTO dto = new SaveScheduleDTO();
        dto.setAccountId("8b11983cd972427fb3ef5640bd3cde81");
        dto.setAppOrgId("ad89564855ad4cc2a39bcb602b0a02a8");
        dto.setCompanyId("ad89564855ad4cc2a39bcb602b0a02a8");
        dto.setContent("test1222");
        dto.setRemark("测试日程3");
        dto.setScheduleStartTime("2018/01/05 16:30");
        dto.setScheduleEndTime("2018/01/05 17:30");
        dto.setReminderMode(1);
        dto.setReminderTime(30); // 提前30分钟提醒
        dto.setScheduleType(1);
     //   dto.setReminderMode(1);
        CompanyUserAppDTO user1 = new CompanyUserAppDTO();
        user1.setId("9d5276704ad543abb57a5b2a81cf9995");
        user1.setUserId("8b11983cd972427fb3ef5640bd3cde81");
        user1.setCompanyId("ad89564855ad4cc2a39bcb602b0a02a8");
//        CompanyUserAppDTO user2 = new CompanyUserAppDTO();
//        user2.setId("7e4c82f170c646ac872fbcc3ad233f8d");
//        user2.setUserId("9b6971e5414948b3aff09226d44e1ff9");
        dto.getMemberList().add(user1);
       // dto.getMemberList().add(user2);
//        dto.setUserIds(Arrays.asList("8b11983cd972427fb3ef5640bd3cde81","9b6971e5414948b3aff09226d44e1ff9"));
//        dto.setMemberIds(Arrays.asList("9d5276704ad543abb57a5b2a81cf9995","7e4c82f170c646ac872fbcc3ad233f8d"));
        this.scheduleService.saveSchedule(dto);
    }


    /**
      修改日程/会议
     */
    @org.junit.Test
    public void testSaveSchedule3() throws Exception {
        //测试日程
        SaveScheduleDTO dto = new SaveScheduleDTO();
        dto.setId("dfb9c9cef01243bbbb90e1f24504b6d3");
        dto.setAccountId("8b11983cd972427fb3ef5640bd3cde81");
        dto.setAppOrgId("ad89564855ad4cc2a39bcb602b0a02a8");
        dto.setCompanyId("ad89564855ad4cc2a39bcb602b0a02a8");
        dto.setContent("修改会议");
        dto.setRemark("测试会议");
        dto.setScheduleStartTime("2018/01/05 13:40");
        dto.setScheduleEndTime("2018/01/05 15:30");
        dto.setReminderTime(30); // 提前30分钟提醒
        dto.setScheduleType(2);
        dto.setReminderMode(1);
        CompanyUserAppDTO user1 = new CompanyUserAppDTO();
        user1.setId("9d5276704ad543abb57a5b2a81cf9995");
        user1.setUserId("8b11983cd972427fb3ef5640bd3cde81");
//        CompanyUserAppDTO user2 = new CompanyUserAppDTO();
//        user2.setId("7e4c82f170c646ac872fbcc3ad233f8d");
//        user2.setUserId("9b6971e5414948b3aff09226d44e1ff9");
        dto.getMemberList().add(user1);
      //  dto.getMemberList().add(user2);
//        dto.setUserIds(Arrays.asList("8b11983cd972427fb3ef5640bd3cde81","9b6971e5414948b3aff09226d44e1ff9"));
//        dto.setMemberIds(Arrays.asList("9d5276704ad543abb57a5b2a81cf9995","7e4c82f170c646ac872fbcc3ad233f8d"));
        this.scheduleService.saveSchedule(dto);


    }

    /**
      *删除日程/会议
     */
    @org.junit.Test
    public void testDeleteSchedule() throws Exception {
        //测试日程
        HandleScheduleDTO dto = new HandleScheduleDTO();
        dto.setId("dfb9c9cef01243bbbb90e1f24504b6d3");
        dto.setAccountId("8b11983cd972427fb3ef5640bd3cde81");
        dto.setAppOrgId("ad89564855ad4cc2a39bcb602b0a02a8");
        dto.setType(1);//删除
        this.scheduleService.handleSchedule(dto);
    }

    /**
     取消会议
     */
    @org.junit.Test
    public void testCancleSchedule() throws Exception {
        //测试日程
        HandleScheduleDTO dto = new HandleScheduleDTO();
        dto.setId("dfb9c9cef01243bbbb90e1f24504b6d3");
        dto.setAccountId("8b11983cd972427fb3ef5640bd3cde81");
        dto.setAppOrgId("ad89564855ad4cc2a39bcb602b0a02a8");
        dto.setType(2);//取消
        this.scheduleService.handleSchedule(dto);
    }


    /**
     * 查询某个人某个日期的工时
     */
    @org.junit.Test
    public void testGetLaborHour() throws Exception {
        SaveLaborHourDTO dto = new SaveLaborHourDTO();
        dto.setLaborDate(DateUtils.str2Date("2017-02-01"));
        dto.setAccountId("8b11983cd972427fb3ef5640bd3cde81");
        dto.setAppOrgId("ad89564855ad4cc2a39bcb602b0a02a8");
        LaborHourDataDTO dataDTO = laborHourService.getLaborHour(dto);
        System.out.print(dataDTO);
    }

    /**
     *
     * 保存与更新工时
     */
    @org.junit.Test
    public void testSaveLaborHour() throws Exception {
        SaveLaborHourDTO dto = new SaveLaborHourDTO();
        dto.setLaborDate(DateUtils.str2Date("2018-01-05"));
        dto.setAccountId("8b11983cd972427fb3ef5640bd3cde81");
        dto.setAppOrgId("ad89564855ad4cc2a39bcb602b0a02a8");
        LaborHourDTO labor = new LaborHourDTO();
      //  labor.setId("826bb91b097942a0bc9393db9445a3a0");
        labor.setLaborHours("8");
        labor.setProjectId("c8a95a79cb1747c18e493f786c67304a");

//        LaborHourDTO labor2 = new LaborHourDTO();
//        labor2.setLaborHours("1");
//        labor2.setProjectId("2b8f32bce324486193d338c6d984d1b1");
//
//        LaborHourDTO labor3 = new LaborHourDTO();
//        labor3.setLaborHours("3");
//        labor3.setProjectId("466ff5dacc7f46e1928e5ee0bf3cdf3a");

        dto.getLaborList().add(labor);
       // dto.getLaborList().add(labor2);
        laborHourService.saveLaborHour(dto);
    }

    /**
     *
     * 保存与更新工时
     */
    @org.junit.Test
    public void testSaveLaborHour3() throws Exception {
        SaveLaborHourDTO dto = new SaveLaborHourDTO();
        dto.setLaborDate(DateUtils.str2Date("2018-01-05"));
        dto.setAccountId("189f436bb05b4090b512e279d4a1a0a1");
        dto.setAppOrgId("4d0431400f3e41668ececd918ffe2cd9");
        LaborHourDTO labor = new LaborHourDTO();
        //  labor.setId("826bb91b097942a0bc9393db9445a3a0");
        labor.setLaborHours("8");
        labor.setProjectId("4d0431400f3e41668ececd918ffe2cd9");

        LaborHourDTO labor2 = new LaborHourDTO();
        labor2.setLaborHours("1");
        labor2.setProjectId("2b8f32bce324486193d338c6d984d1b1");

        LaborHourDTO labor3 = new LaborHourDTO();
        labor3.setLaborHours("3");
        labor3.setProjectId("466ff5dacc7f46e1928e5ee0bf3cdf3a");

        dto.getLaborList().add(labor);
        // dto.getLaborList().add(labor2);
        laborHourService.saveLaborHour(dto);
    }

    /**
     *
     * 保存与更新工时
     */
    @org.junit.Test
    public void testUpdateLaborHour3() throws Exception {
        SaveLaborHourDTO dto = new SaveLaborHourDTO();
        dto.setLaborDate(DateUtils.str2Date("2017-02-03"));
        dto.setAccountId("189f436bb05b4090b512e279d4a1a0a1");
        dto.setAppOrgId("4d0431400f3e41668ececd918ffe2cd9");
        LaborHourDTO labor = new LaborHourDTO();
        labor.setId("826bb91b097942a0bc9393db9445a3a0"); //此处为修改
        labor.setLaborHours("8");
        labor.setProjectId("4d0431400f3e41668ececd918ffe2cd9");

        LaborHourDTO labor2 = new LaborHourDTO();
        labor2.setLaborHours("1");
        labor2.setProjectId("2b8f32bce324486193d338c6d984d1b1");

        LaborHourDTO labor3 = new LaborHourDTO();
        labor3.setLaborHours("3");
        labor3.setProjectId("466ff5dacc7f46e1928e5ee0bf3cdf3a");

        dto.getLaborList().add(labor);
        // dto.getLaborList().add(labor2);
        laborHourService.saveLaborHour(dto);
    }

    /**
     * 是否参加会议 status = 1:参加，status = 2 不参加
     */
    @org.junit.Test
    public void handleScheduleMember() throws Exception {
        HandleScheduleMemberDTO dto = new HandleScheduleMemberDTO();
        dto.setAccountId("189f436bb05b4090b512e279d4a1a0a1");
        dto.setAppOrgId("4d0431400f3e41668ececd918ffe2cd9");
        dto.setScheduleId("11111111111111111111");
        dto.setStatus(1); //1:参加
        /*********下面的情形是不参加的情形，不参加需要填写不参加的原因***************/
//        dto.setStatus(2); //2:不参加
//        dto.setRefuseReason("不想参加");
        scheduleService.handleScheduleMember(dto);
    }

    /**
     * 获取日程/会议详情
     */
    @org.junit.Test
    public void getScheduleById() throws Exception {
        QueryScheduleDTO dto = new QueryScheduleDTO();
        dto.setAccountId("189f436bb05b4090b512e279d4a1a0a1");
        dto.setAppOrgId("4d0431400f3e41668ececd918ffe2cd9");
        dto.setId("11111111111111111111");
        scheduleService.getScheduleById(dto);
    }


    @org.junit.Test
    public void testMyProject() throws Exception {
        Map<String,Object> map =  new HashMap<>();
        map.put("accountId","8b11983cd972427fb3ef5640bd3cde81");
        map.put("appOrgId","ad89564855ad4cc2a39bcb602b0a02a8");
        List<ProjectSimpleDataDTO> list = projectService.getProjectListForLaborHour(map);
        System.out.print(list);
    }


    @org.junit.Test
    public void testGetScheduleByDate() throws Exception {
        QueryScheduleDTO dto = new QueryScheduleDTO();
        dto.setAccountId("8b11983cd972427fb3ef5640bd3cde81");
        dto.setAppOrgId("ad89564855ad4cc2a39bcb602b0a02a8");
        dto.setDate("2018-01-05");
        List<ScheduleAndLaborDTO> list = scheduleService.getScheduleByDate(dto);
        System.out.print(list);
    }


    @org.junit.Test
    public void testGetScheduleDate() throws Exception {
        QueryScheduleDTO dto = new QueryScheduleDTO();
        dto.setAccountId("8b11983cd972427fb3ef5640bd3cde81");
        dto.setAppOrgId("ad89564855ad4cc2a39bcb602b0a02a8");
        dto.setDate("2018-01");
        List<String> list = scheduleService.getScheduleDate(dto);
        System.out.print(list);
    }


    @org.junit.Test
    public void getTodayAndFutureSchedule() throws Exception {
        QueryScheduleDTO dto = new QueryScheduleDTO();
        dto.setAccountId("8b11983cd972427fb3ef5640bd3cde81");
        dto.setAppOrgId("ad89564855ad4cc2a39bcb602b0a02a8");
        List<ScheduleGroupDataDTO> list = scheduleService.getTodayAndFutureSchedule(dto);
        System.out.print(list);
    }


    public static Test suite() {
        return new TestSuite(ProjectServiceImplTest.class);
    }
}
