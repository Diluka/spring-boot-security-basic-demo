/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.net.withub.demo.bootsec.hello.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;

/**
 * 数据源配置
 *
 * @author Diluka
 */
@Configuration
@PropertySource("classpath:jdbc.properties") //加载属性文件
public class SpringDataConfig {

    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String userName;
    @Value("${jdbc.password}")
    private String password;
    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    /**
     * 数据源
     *
     * @return
     */
    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(url, userName, password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    /**
     * OpenSessionInViewFilter
     *
     * @return
     */
    @Bean
    public OpenSessionInViewFilter openSessionInViewFilter() {
        OpenSessionInViewFilter openSessionInViewFilter = new OpenSessionInViewFilter();
        return openSessionInViewFilter;
    }

    /**
     * 配置session工厂
     *
     * @param dataSource 自动注入参数，数据源
     * @return
     */
    @Bean
    @Autowired
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        sessionFactory.setDataSource(dataSource); //设置数据源
        sessionFactory.setPackagesToScan("cn.net.withub.demo.bootsec.hello.entity"); //设置实体类扫描位置
        sessionFactory.setHibernateProperties(hibernateProperties()); //设置hibernate属性

        return sessionFactory;
    }

    /**
     * 配置事务管理器
     *
     * @param sessionFactory
     * @return
     */
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    /**
     * 创建hibernate属性
     *
     * @return
     */
    Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
                setProperty("hibernate.show_sql", "true");
                //setProperty("hibernate.format_sql", "true");
            }
        };
    }
}
