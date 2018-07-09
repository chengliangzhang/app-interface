import com.maoding.core.bean.ApiResult;
import com.maoding.core.bean.ResponseBean;
import com.maoding.core.util.JsonUtils;
import com.maoding.core.util.OkHttpUtils;
import com.maoding.enterprise.dto.EnterpriseSearchQueryDTO;
import com.maoding.project.constDefine.EnterpriseServer;
import com.maoding.project.service.ProjectService;
import junit.framework.Test;
import junit.framework.TestSuite;
import okhttp3.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Map;

/**
 * ProjectServiceImpl Tester.
*/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy(exposeProxy = true)
public class ProjectServiceImplTest {

    @Autowired
    private ProjectService projectService;

    @Before
    public void setUp() throws Exception {

        if(projectService==null){
            tearDown();
        }
        System.out.print(projectService);
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("失败");
    }

    @org.junit.Test
    public void testGetV2ProjectList() throws Exception {
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
    public void testGetProjectParticipantsList() throws Exception {
        //TODO: Test goes here...
    }

    @org.junit.Test
    public void testSetProjectDynamicsByProject() throws Exception {
        //TODO: Test goes here...
    }

    @org.junit.Test
    public void testGetProjectTaskCompany() throws Exception {
        //TODO: Test goes here...
    }

    @org.junit.Test
    public void testGetMyProjectList() throws Exception {
        //TODO: Test goes here...
    }

    public static Test suite() {
        return new TestSuite(ProjectServiceImplTest.class);
    }


}
