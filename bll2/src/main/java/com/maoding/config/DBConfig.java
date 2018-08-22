//package com.maoding.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import java.io.IOException;
//
///**
// * Created by Idccapp21 on 2016/10/13.
// */
//
//@Configuration
//@PropertySource(value = {"classpath:config/jdbc.properties"})
//public class DBConfig {
//    private static final Logger logger = LoggerFactory.getLogger(DBConfig.class);
//
//    @Value("${jdbc.driver}")
//    private String dbDriver;
//
//    @Value("${jdbc.jdbcUrl}")
//    private String dbUrl;
//
//    @Value("${jdbc.user}")
//    private String userName;
//
//    @Value("${jdbc.password}")
//    private String password;
//
//    @Value("${druid.initialSize}")
//    private int initialSize;
//
//    @Value("${druid.maxActive}")
//    private int maxActive;
//
//    @Value("${druid.minIdle}")
//    private int minIdle;
//
//    @Value("${druid.maxWait}")
//    private int maxWait;
//
//    @Value("${druid.removeAbandoned}")
//    private boolean removeAbandoned;
//
//    @Value("${druid.removeAbandonedTimeout}")
//    private int removeAbandonedTimeout;
//
//    @Value("${druid.timeBetweenEvictionRunsMillis}")
//    private int timeBetweenEvictionRunsMillis;
//
//    @Value("${druid.minEvictableIdleTimeMillis}")
//    private int minEvictableIdleTimeMillis;
//
//    @Value("${druid.poolPreparedStatements}")
//    private boolean poolPreparedStatements;
//
//    @Value("${druid.maxPoolPreparedStatementPerConnectionSize}")
//    private int maxPoolPreparedStatementPerConnectionSize;
//
//    @Value("${druid.filters}")
//    private String filters;
//
//
//    @Bean(name = "druidDataSource")
//    public DruidDataSource getDruidDataSource() throws Exception {
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setDriverClassName(dbDriver);
//        druidDataSource.setUrl(dbUrl);
//        druidDataSource.setUsername(userName);
//        druidDataSource.setPassword(password);
//        druidDataSource.setInitialSize(initialSize);
//        druidDataSource.setMaxActive(maxActive);
//        druidDataSource.setMinIdle(minIdle);
//        druidDataSource.setMaxWait(maxWait);
//        druidDataSource.setRemoveAbandoned(removeAbandoned);
//        druidDataSource.setTimeBetweenConnectErrorMillis(timeBetweenEvictionRunsMillis);
//        druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//        druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
//        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
//        druidDataSource.setFilters(filters);
//        return druidDataSource;
//    }
//
//    @Bean(name = "jdbcTemplate")
//    public JdbcTemplate getJdbcTemplate(DruidDataSource druidDataSource) {
//        JdbcTemplate jdbcTemplate = new JdbcTemplate();
//        jdbcTemplate.setDataSource(druidDataSource);
//        return jdbcTemplate;
//    }
//
//    @Bean(name = "transactionManager")
//    public DataSourceTransactionManager getDataSourceTransactionManager(DruidDataSource druidDataSource) {
//        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
//        dataSourceTransactionManager.setDataSource(druidDataSource);
//        return dataSourceTransactionManager;
//    }
//
//    @Bean(name = "persistenceExceptionTranslationPostProcessor")
//    public PersistenceExceptionTranslationPostProcessor getPersistenceExceptionTranslationPostProcessor(DruidDataSource druidDataSource) {
//        PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor = new PersistenceExceptionTranslationPostProcessor();
//        return persistenceExceptionTranslationPostProcessor;
//    }
//
//    @Bean
//    public SqlSessionFactory getSqlSessionFactoryBean(DruidDataSource druidDataSource) throws Exception {
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//
//        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//        try {
//            sqlSessionFactoryBean.setDataSource(druidDataSource);
//            sqlSessionFactoryBean.setConfigLocation(resolver.getResource("classpath:mybatis-configuration.xml"));
//            sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapping/*/*Mapper.xml"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return sqlSessionFactoryBean.getObject();
//    }
//
//    @Bean(name = "sqlSession")
//    public SqlSessionTemplate getSqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
//        SqlSessionTemplate  sqlSessionTemplate=new SqlSessionTemplate(sqlSessionFactory);
//        return  sqlSessionTemplate;
//    }
//
//
//}
