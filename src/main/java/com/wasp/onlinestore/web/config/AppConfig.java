package com.wasp.onlinestore.web.config;

import com.wasp.onlinestore.config.PropertyReader;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import java.util.Properties;

@Configuration
public class AppConfig {

    @Bean
    public FreeMarkerViewResolver freemarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(true);
        resolver.setPrefix("");
        resolver.setSuffix(".html");
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/view/");
        return freeMarkerConfigurer;
    }

    @Bean
    public PGSimpleDataSource pgSimpleDataSource() {
        PropertyReader propertyReader = new PropertyReader("/properties/application.properties");
        Properties properties = propertyReader.getProperties();

        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setUrl(properties.getProperty("db_url"));
        pgSimpleDataSource.setUser(properties.getProperty("username"));
        pgSimpleDataSource.setPassword(properties.getProperty("password"));
        return pgSimpleDataSource;
    }

}
