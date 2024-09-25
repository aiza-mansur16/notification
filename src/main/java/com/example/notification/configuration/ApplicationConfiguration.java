package com.example.notification.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public JavaMailSender getJavaMailSender(@Value("${spring.mail.host}") String host,
                                            @Value("${spring.mail.port}") int port,
                                            @Value("${spring.mail.username}") String username,
                                            @Value("${spring.mail.password}") String password,
                                            @Value("${spring.mail.properties.mail.smtp.auth}") boolean isAuth,
                                            @Value("${spring.mail.properties.mail.smtp.starttls.enable}") boolean isStartTLSEnabled
    ) {
        final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        javaMailSender.setPort(port);
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(password);
        javaMailSender.getJavaMailProperties().put("mail.smtp.auth", isAuth);
        javaMailSender.getJavaMailProperties().put("mail.smtp.starttls.enable", isStartTLSEnabled);
        return javaMailSender;
    }
}
