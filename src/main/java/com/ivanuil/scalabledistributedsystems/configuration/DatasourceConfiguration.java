package com.ivanuil.scalabledistributedsystems.configuration;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.left")
    public DataSourceProperties leftDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.right")
    public DataSourceProperties rightDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource leftDataSource() {
        return leftDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public DataSource rightDataSource() {
        return rightDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean
    public JdbcTemplate leftJdbcTemplate() {
        return new JdbcTemplate(leftDataSource());
    }

    @Bean
    public JdbcTemplate rightJdbcTemplate() {
        return new JdbcTemplate(rightDataSource());
    }

}
