package com.maoding.config;

import com.maoding.core.component.fastdfs.StorageClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Idccapp21 on 2016/11/29.
 */

@Configuration
@PropertySource(value = {"classpath:config/system.properties"})
public class FastDFSConfig {
    @Value("${fastdfs.connect_timeout}")
    private String connectTimeout;

    @Value("${fastdfs.network_timeout}")
    private String networkTimeout;

    @Value("${fastdfs.charset}")
    private String charset;

    @Value("${fastdfs.tracker_http_port}")
    private String trackerHttpPort;

    @Value("${fastdfs.anti_steal_token}")
    private String antiStealToken;

    @Value("${fastdfs.secret_key}")
    private String secretKey;

    @Value("${fastdfs.tracker_server}")
    private String trackerServer;

    @Bean(name="storageClientFactory")
    public StorageClientFactory getStorageClientFactory(){
        Map<String, String> initProperties=new HashMap<String,String>();
        initProperties.put("connect_timeout",connectTimeout);
        initProperties.put("network_timeout",networkTimeout);
        initProperties.put("charset",charset);
        initProperties.put("tracker_http_port",trackerHttpPort);
        initProperties.put("anti_steal_token",antiStealToken);
        initProperties.put("secret_key",secretKey);
        initProperties.put("tracker_server",trackerServer);
        StorageClientFactory storageClientFactory= StorageClientFactory.getInstance();
        storageClientFactory.setInitProperties(initProperties);
        return storageClientFactory;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipart = new CommonsMultipartResolver();
        multipart.setMaxUploadSize(20 * 1024 * 1024);
        return multipart;
    }

    @Bean
    @Order(0)
    public MultipartFilter multipartFilter() {
        MultipartFilter multipartFilter = new MultipartFilter();
        multipartFilter.setMultipartResolverBeanName("multipartResolver");
        return multipartFilter;
    }
}
