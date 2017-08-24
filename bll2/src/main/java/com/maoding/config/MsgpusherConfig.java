package com.maoding.config;

import com.maoding.msgpusher.Msgpusher;
import com.maoding.msgpusher.MsgpusherFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Idccapp21 on 2016/11/29.
 */

@Configuration
@PropertySource(value = {"classpath:config/system.properties"})
public class MsgpusherConfig {
    @Value("${IGT.appid}")
    private String appid;

    @Value("${IGT.appkey}")
    private String appkey;

    @Value("${IGT.appToken}")
    private String appToken;

    @Value("${IGT.host}")
    private String host;

    @Bean(name="msgpusherFactory")
    public MsgpusherFactory getMsgpusherFactory(){
        MsgpusherFactory msgpusherFactory =MsgpusherFactory.getInstance();
        msgpusherFactory.setAppid(appid);
        msgpusherFactory.setAppkey(appkey);
        msgpusherFactory.setAppToken(appToken);
        msgpusherFactory.setHost(host);
        return msgpusherFactory;
    }

    @Bean(name="msgpusher")
    public Msgpusher getMsgpusher(MsgpusherFactory msgpusherFactory){
      return   msgpusherFactory.defaultMsgpusher();
    }
}
