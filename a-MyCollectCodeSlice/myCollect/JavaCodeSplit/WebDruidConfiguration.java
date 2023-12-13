package com.csin.dh.common.config;

import com.csin.dh.common.utils.DataUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author gaosc
 */
@SpringBootConfiguration
@MapperScan(basePackages = {"com.csin.dh.web.**.mapper"}, sqlSessionFactoryRef = "webSqlSessionFactory")
public class WebDruidConfiguration {

    @Value("${spring.datasource.web.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.web.jdbc-url}")
    private String url;
    @Value("${spring.datasource.web.username}")
    private String username;
    @Value("${spring.datasource.web.password}")
    private String password;

    @Primary
    @Bean(name = "webDataSource")
    public DataSource webDataSource() {
        return DataUtils.buildDataSource(url, password, username, driverClassName);
    }

    @Primary
    @Bean(name = "webSqlSessionFactory")
    public SqlSessionFactory webSqlSessionFactory(@Qualifier("webDataSource") DataSource dataSource) throws Exception {
        return DataUtils.buildSqlSessionFactory(dataSource, "classpath*:mappers/web/*Mapper.xml");
    }

    @Primary
    @Bean(name = "webTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("webDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "webSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("webSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
