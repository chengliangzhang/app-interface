package com.maoding.config;

import com.maoding.core.component.mail.MailSender;
import com.maoding.core.component.mail.MailSenderFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Idccapp21 on 2016/11/29.
 */
@Configuration
@PropertySource(value = {"classpath:config/system.properties"})
public class MailConfig {

    @Value("${mail.server}")
    public String server;

    @Value("${mail.port}")
    public String port;

    @Value("${mail.username}")
    public String username;

    @Value("${mail.password}")
    public String password;

    @Value("${mail.fromname}")
    public String fromname;

    @Value("${mail.count}")
    public int mCount;

    @Bean(name="mailSenderFactory")
    public MailSenderFactory getMailSenderFactory(){
        MailSenderFactory mailSenderFactory=new MailSenderFactory();
        mailSenderFactory.setServer(server);
        mailSenderFactory.setPort(port);
        mailSenderFactory.setUsername(username);
        mailSenderFactory.setPassword(password);
        mailSenderFactory.setFromname(fromname);
        mailSenderFactory.setmCount(mCount);
        mailSenderFactory.init();
        return MailSenderFactory.getInstance();
    }

    @Bean(name="mailSender")
    public MailSender getMailSender(MailSenderFactory mailSenderFactory){
      return   mailSenderFactory.getMailSender();
    }
}
