package com.cjt.svc4.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfiguration {
  
  @Bean
  @Primary
  @Qualifier("extranetHikariConfig")
  @ConfigurationProperties(prefix = "spring.datasource.extranet")
  public HikariConfig extranetHikariConfig(){
    return new HikariConfig();
  }

  @Bean
  @Primary
  @Qualifier("extranetDataSource")
  public DataSource extranetDataSource() throws Exception {
    return new HikariDataSource(extranetHikariConfig());
  }

  @Bean
  @Qualifier("isherpaHikariConfig")
  @ConfigurationProperties(prefix = "spring.datasource.isherpa")
  public HikariConfig isherpaHikariConfig(){
    return new HikariConfig();
  }

  @Bean
  @Qualifier("isherpaDataSource")
  public DataSource primaryDataSource() throws Exception {
    return new HikariDataSource(isherpaHikariConfig());
  }

  @Bean
  @Qualifier("eduappHikariConfig")
  @ConfigurationProperties(prefix = "spring.datasource.eduapp")
  public HikariConfig eduappHikariConfig() {
    return new HikariConfig();
  }

  @Bean
  @Qualifier("eduappDataSource")
  public DataSource secondaryDataSource() throws Exception {
    return new HikariDataSource(eduappHikariConfig());
  }


    // @Bean
    // @Primary
    // @ConfigurationProperties(prefix = "spring.datasource.mid-isherpa")
    // public DataSource dataSource() {
    //     return DataSourceBuilder.create().type(HikariDataSource.class).build();
    // }

    // @Bean
    // public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
    //   SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    //   sessionFactory.setDataSource(dataSource);
    //   sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver() .getResources("classpath:mapper/*.xml"));
    //   return sessionFactory.getObject();
    // }
  
    // @Bean
    // public SqlSessionTemplate sqlSession(
    //     SqlSessionFactory sqlSessionFactory
    // ) throws Exception {
    //   return new SqlSessionTemplate(sqlSessionFactory);
    // }
  
    // @Bean
    // public DataSourceTransactionManager transactionManager() {
    //   return new DataSourceTransactionManager(dataSource());
    // }
    
    // // sql-myBatis
    // @Bean
    // public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws Exception{
    //     SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
    //     sessionFactoryBean.setDataSource(dataSource);
    //     // Java 17 이상, myBatis 3.x 버전에서는 xml 만들 필요 없음. (현재 boot 3.2, java 21)
    //     //sessionFactoryBean.setConfigLocation(new ClassPathResource("classpath:/resources/mybatis-config.xml"));
    //     //sessionFactoryBean.setConfigLocation(new ClassPathResource("classpath:mappers/**/*.xml"));

    //     // xml 설정
    //     // sessionFactoryBean.setConfigLocation(new ClassPathResource("classpath:/resources/config/mybatis-config.xml"));
    //     // sessionFactoryBean.setConfigLocation(new ClassPathResource("classpath:mybatis/**/**/*.xml"));
    //     // sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/**/**/*.xml"));
    //     sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*.xml"));
    //     // sessionFactoryBean.setTypeAliasesPackage(package_name);
    //     return sessionFactoryBean.getObject();
    // }

    // // sql-myBatis
    // @Bean
    // public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
    //     return new SqlSessionTemplate(sqlSessionFactory);
    // }

    // // sql-transaction
    // @Bean
    // public PlatformTransactionManager transactionManager(DataSource dataSource){
    //     return new DataSourceTransactionManager(dataSource);
    // }
}
