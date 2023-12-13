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
@MapperScan(basePackages = {"com.csin.dh.admin.**.mapper"}, sqlSessionFactoryRef = "adminSqlSessionFactory")
public class AdminDruidConfiguration {

    @Value("${spring.datasource.admin.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.admin.jdbc-url}")
    private String url;
    @Value("${spring.datasource.admin.username}")
    private String username;
    @Value("${spring.datasource.admin.password}")
    private String password;

    @Primary
    @Bean(name = "adminDataSource")
    public DataSource adminDataSource() {
        return DataUtils.buildDataSource(url, password, username, driverClassName);
    }

    @Bean(name = "adminSqlSessionFactory")
    public SqlSessionFactory adminSqlSessionFactory(@Qualifier("adminDataSource") DataSource dataSource) throws Exception {
        return DataUtils.buildSqlSessionFactory(dataSource, "classpath*:mappers/admin/*.xml");
    }

    @Bean(name = "adminTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("adminDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "adminSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("adminSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
