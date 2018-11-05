package com.hy.config.db;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.hy.mapper.zk"}, sqlSessionFactoryRef = "sqlSessionFactoryZK")
public class DbZKConfig {
    @Bean(name = "sqlSessionFactoryZK")
    public SqlSessionFactory sqlSessionFactoryZK(@Qualifier("zkDS") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapping/*.xml"));
        return factoryBean.getObject();
    }
}
