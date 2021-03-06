package com.hy.config.db;

import com.hy.mapper.MybatisPageQueryInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 默认数据源
 */
@Configuration
@MapperScan(basePackages = {"com.hy.mapper.ms"}, sqlSessionFactoryRef = "sqlSessionFactoryDefault")
public class DbDefaultConfig {

    @Bean(name = "sqlSessionFactoryDefault")
    @Primary
    public SqlSessionFactory sqlSessionFactoryDefault(@Qualifier("defaultDS") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        //对该数据源下的数据库操作都添加拦截器
        factoryBean.setPlugins(new Interceptor[]{new MybatisPageQueryInterceptor()});
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapping/*.xml"));
        return factoryBean.getObject();
    }
//
//    @Bean(name = "transactionManagerDefault")
//    @Primary
//    public DataSourceTransactionManager transactionManagerDefault(@Qualifier("defaultDS") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }

//    @Bean(name = "sqlSessionTemplateDefault")
//    @Primary
//    public SqlSessionTemplate sqlSessionTemplateDefault(@Qualifier("sqlSessionFactoryDefault") SqlSessionFactory sqlSessionFactory) throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
}
