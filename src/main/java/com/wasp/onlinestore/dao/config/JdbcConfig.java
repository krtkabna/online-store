package com.wasp.onlinestore.dao.config;

import org.jasypt.util.text.BasicTextEncryptor;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:/properties/application.properties")
public class JdbcConfig {
    @Value("${PASSWORD_SECRET}")
    private String passwordSecret;

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

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    private String getDecryptedPassword(String password) {
        BasicTextEncryptor decryptor = new BasicTextEncryptor();
        decryptor.setPasswordCharArray(passwordSecret.toCharArray());
        return decryptor.decrypt(password);
    }
}
