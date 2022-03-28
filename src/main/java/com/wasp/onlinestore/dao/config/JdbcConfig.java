package com.wasp.onlinestore.dao.config;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.util.text.BasicTextEncryptor;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/properties/application.properties")
public class JdbcConfig {
    private static final String SALT = "i-like-cookies";

    @Bean
    public PGSimpleDataSource pgSimpleDataSource(@Value("${db.url}") String dbUrl,
                                                 @Value("${db.username}") String username,
                                                 @Value("${db.password}") String password) {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setUrl(dbUrl);
        pgSimpleDataSource.setUser(username);
        pgSimpleDataSource.setPassword(getDecryptedPassword(password));
        return pgSimpleDataSource;
    }

    private String getDecryptedPassword(String password) {
        BasicTextEncryptor decryptor = new BasicTextEncryptor();
        decryptor.setPasswordCharArray(SALT.toCharArray());
        return decryptor.decrypt(password);
    }
}
