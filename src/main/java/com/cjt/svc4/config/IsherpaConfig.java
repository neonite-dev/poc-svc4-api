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
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ComponentScan(basePackages = "com.cjt.svc4.service.isherpa")
@MapperScan(
    value = "com.cjt.svc4.mapper.isherpa",
    sqlSessionFactoryRef = "isherpaSqlSessionFactory"
)
@Configuration
public class IsherpaConfig {
  
    @Primary
    @Bean(name = "isherpaSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("isherpaDataSource") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis/mybatis-config.xml"));
        sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:mybatis/isherpa/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.cjt.svc4");
        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean(name = "isherpaSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("isherpaSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
      return new SqlSessionTemplate(sqlSessionFactory);
    }

  
  

}
