package it.gdhi;

import db.migration.utils.ApplicationContextAwareSpringJdbcMigrationResolver;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;

@SpringBootApplication
@ComponentScan(basePackageClasses = {
        ApplicationContextAwareSpringJdbcMigrationResolver.class, Application.class})
public class ApplicationTest extends Application {

    @Value("${dbTest.url}")
    String dbUrl;
    @Value("${dbTest.username}")
    String dbUsername;
    @Value("${dbTest.password}")
    String dbPassword;
    @Value("${dbTest.driverClassName}")
    String dbDriverClassName;


    @Autowired
    public ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    @Override
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(dbDriverClassName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        dataSource.setMinIdle(2);
        dataSource.setMaxIdle(8);
        dataSource.setMaxWait(200);
        dataSource.setMaxActive(20);
        dataSource.setTestOnBorrow(true);
        return dataSource;
    }

}
