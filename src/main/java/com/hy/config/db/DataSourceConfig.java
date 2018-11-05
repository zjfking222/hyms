package com.hy.config.db;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Primary
    @Bean(name = "defaultDSProperties")
    @ConfigurationProperties(prefix = "spring.datasource.defalut")
    public DataSourceProperties dataSourcePropertiesDefault() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "defaultDS")
    @ConfigurationProperties(prefix = "spring.datasource.defalut")
    public DataSource dataSourceDefault() {
        return dataSourcePropertiesDefault().initializeDataSourceBuilder().build();
    }

    /**OA数据源*/
    @Bean(name = "oaDSProperties")
    @ConfigurationProperties(prefix = "spring.datasource.oa")
    public DataSourceProperties dataSourcePropertiesOA() {
        return new DataSourceProperties();
    }

    @Bean(name = "oaDS")
    @ConfigurationProperties(prefix = "spring.datasource.oa")
    public DataSource dataSourceOA() {
        return dataSourcePropertiesOA().initializeDataSourceBuilder().build();
    }

    /**中控数据源*/
    @Bean(name = "zkDSProperties")
    @ConfigurationProperties(prefix = "spring.datasource.zk")
    public DataSourceProperties dataSourcePropertiesZK() {
        return new DataSourceProperties();
    }

    @Bean(name = "zkDS")
    @ConfigurationProperties(prefix = "spring.datasource.zk")
    public DataSource dataSourceZK() {
        return dataSourcePropertiesZK().initializeDataSourceBuilder().build();
    }
}
