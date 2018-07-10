package com.maoding.project.constDefine;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = {"classpath:config/system.properties"})
public class FileUploadServer {

    @Value("${fastdfs.fileCenterUrl}")
    public  String fileCenterUrl;

    public static String URL_HANDLE_PROJECT_CONTRACT = "/attachment/handleProjectContract";


    @Bean
    public FileUploadServer getImServer(@Value("${fastdfs.fileCenterUrl}") String fileCenterUrl) {
//        URL_ENTERPRISE_QUERY_DETAIL = yongyouCloudServerUrl +  "/enterpriseSearch/queryDetail";
//        URL_ENTERPRISE_QUERY_FULL =  yongyouCloudServerUrl +  "/enterpriseSearch/queryFull";
        return new FileUploadServer();
    }

    public String getHandleProjectContractUrl(){
        return fileCenterUrl +URL_HANDLE_PROJECT_CONTRACT;
    }


}
