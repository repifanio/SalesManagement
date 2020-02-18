package com.camargo.salesmanagement.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * ConfigClass
 */
@ConfigurationProperties(prefix = "app.directories")
@Configuration("appSettings")
public class ConfigClass {

    @Getter
    @Setter
    private String filesdir;

}