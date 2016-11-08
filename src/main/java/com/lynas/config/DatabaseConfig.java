package com.lynas.config;

import com.lynas.model.*;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by sazzad on 9/7/15
 */
@Configuration
@EnableTransactionManagement
@PropertySource(value = "classpath:application.properties")
public class DatabaseConfig {

    @Autowired
    private ApplicationContext appContext;
    @Value("${db.password}")
    private String dbPasswordLinux;
    @Value("${db.password.win.mac}")
    private String dbPasswordWinMac;


    @Bean(name = "DataSource")
    @Profile("OPENSHIFT")
    public HikariDataSource dataSourceOpenShift() {
        return getDataSource(
                "570d2c5c89f5cf48d8000002-szapp.rhcloud.com",
                "admin517XZak",
                "jkxps3adZflt",
                48206,
                "invmrest");
    }

    @Bean(name = "DataSource")
    @Profile("WINMAC")
    public HikariDataSource dataSourceWinMac() {
        return getDataSource("127.0.0.1", "root", "", 3306,"invmrest");
    }

    @Bean(name = "DataSource")
    @Profile("LINUX")
    public HikariDataSource dataSourceLinux() {
        System.out.println("=============================");
        System.out.println("=============================");
        System.out.println("Using Password : "+dbPasswordLinux);
        System.out.println("=============================");
        System.out.println("=============================");
        return getDataSource("127.3.38.2", "adminJi8hmw5", "8UiDDTAECJyn", 3306,"invmrest");
    }

    private HikariDataSource getDataSource(String serverName, String user, String password, int port, String dbName) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        dataSource.addDataSourceProperty("databaseName", dbName);
        dataSource.addDataSourceProperty("portNumber", port);
        dataSource.addDataSourceProperty("serverName", serverName);
        dataSource.addDataSourceProperty("user", user);
        dataSource.addDataSourceProperty("password", password);
        return dataSource;
    }


    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager manager = new HibernateTransactionManager();
        manager.setSessionFactory(hibernate5SessionFactoryBean().getObject());
        return manager;
    }

    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean hibernate5SessionFactoryBean() {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
        localSessionFactoryBean.setDataSource(appContext.getBean(DataSource.class));
        localSessionFactoryBean.setAnnotatedClasses(
                AppUser.class,
                Organization.class,
                Book.class,
                Account.class,
                AccountTransaction.class,
                Stock.class,
                StockTransaction.class
        );

        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        //properties.put("hibernate.current_session_context_class","thread");
        properties.put("hibernate.hbm2ddl.auto", "update");

        localSessionFactoryBean.setHibernateProperties(properties);
        return localSessionFactoryBean;
    }
}
