package it.gdhi;

import db.migration.utils.ApplicationContextAwareSpringJdbcMigrationResolver;
import org.apache.commons.dbcp.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ComponentScan;

import javax.sql.DataSource;
import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackageClasses = {
        ApplicationContextAwareSpringJdbcMigrationResolver.class, Application.class})
public class Application {

    @Autowired
    public ApplicationContext context;
    @Value("${db.url}")
    String dbUrl;
    @Value("${db.username}")
    String dbUsername;
    @Value("${db.password}")
    String dbPassword;
    @Value("${db.driverClassName}")
    String dbDriverClassName;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

    @Bean
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

    @Bean
    public FlywayMigrationInitializer flywayMigrationInitializer(Flyway flyway) {
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner((BeanDefinitionRegistry) context,
                true);
        scanner.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
        scanner.scan("db.migration");
        return new FlywayMigrationInitializer(flyway);
    }

    @Bean
    public Flyway flyway(ApplicationContext applicationContext) {
        Flyway customFlyway = new Flyway();
        customFlyway.setSkipDefaultResolvers(true);
        customFlyway.setDataSource(applicationContext.getBean(DataSource.class));
        customFlyway.setResolvers(applicationContext.getBean(ApplicationContextAwareSpringJdbcMigrationResolver.class));
        return customFlyway;
    }

}
