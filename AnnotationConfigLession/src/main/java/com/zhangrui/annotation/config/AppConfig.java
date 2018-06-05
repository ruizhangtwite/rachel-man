package com.zhangrui.annotation.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
@EnableAspectJAutoProxy
@MapperScan(basePackages = "com.zhangrui.annotation", markerInterface = BaseMapperInterface.class)
public class AppConfig {

    @Autowired
    Environment env;

    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidDataSource initDruidDataSource(){
        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setDriverClassName(env.getProperty("jdbc.dirverClass"));
        dataSource.setUrl(env.getProperty("jdbc.url"));
        dataSource.setUsername(env.getProperty("jdbc.userName"));
        dataSource.setPassword(env.getProperty("jdbc.password"));
        return  dataSource;

    }

    @Bean
    public SqlSessionFactoryBean initSqlSessionFactory(){
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(initDruidDataSource());
        sessionFactoryBean.setTypeAliasesPackage(env.getProperty("scaner.baseBackage"));
        return sessionFactoryBean;
    }




    @Bean
    public PlatformTransactionManager initPlatformTransactionManager(){
        PlatformTransactionManager manager = new DataSourceTransactionManager(initDruidDataSource());
        return manager;
    }



}
