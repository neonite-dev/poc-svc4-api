package com.cjt.svc4.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ComponentScan(basePackages = "com.cjt.svc4.service.extranet")
@MapperScan(
    value = "com.cjt.svc4.mapper.extranet",
    sqlSessionFactoryRef = "extranetSqlSessionFactory"
)
@Configuration
public class ExtraNetConfig {
  
    @Bean(name = "extranetSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("extranetDataSource") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/extranet/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.cjt.svc4");
        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = "extranetSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("extranetSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
