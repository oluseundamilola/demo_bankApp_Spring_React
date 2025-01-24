package com.demo_bank.demoBank.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "moviebotconfiguration.google")
public class MaxiBotConfiguration {
    private String name;
    private String projectid;
    private String languageCode;
}
