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
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Properties;

@SpringBootApplication
@ComponentScan(basePackageClasses = {ApplicationContextAwareSpringJdbcMigrationResolver.class, Application.class})
@EnableAsync
public class Application {

    @Value("${db.url}")
    String dbUrl;
    @Value("${db.username}")
    String dbUsername;
    @Value("${db.password}")
    String dbPassword;
    @Value("${db.driverClassName}")
    String dbDriverClassName;
    @Value("${spring.jpa.showSql}")
    String showSql;
    @Value("${spring.jpa.formatSql}")
    String formatSql;
    @Value("${spring.jpa.hibernate.ddlAuto}")
    String ddlAuto;
    @Value("${spring.jpa.hibernate.namingStrategy}")
    String namingStrategy;
    @Value("${spring.jpa.hibernate.physicalNamingStrategy}")
    String physicalNamingStrategy;
    @Value("${spring.jpa.hibernate.dialect}")
    String dialect;

    @Autowired
    public ApplicationContext context;

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
    public EntityManagerFactory entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaDialect(new HibernateJpaDialect());
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setPersistenceUnitName("persistenceUnit");
        factoryBean.setPackagesToScan("it.gdhi.model", "it.gdhi.internationalization.model");
        factoryBean.setJpaProperties(jpaProperties());
        factoryBean.afterPropertiesSet();

        return factoryBean.getObject();
    }

    @Bean
    public Properties jpaProperties() {
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", dialect);
        jpaProperties.put("hibernate.hbm2ddl.auto", ddlAuto);
        jpaProperties.put("hibernate.implicit_naming_strategy", namingStrategy);
        jpaProperties.put("hibernate.physical_naming_strategy", physicalNamingStrategy);
        jpaProperties.put("hibernate.show_sql", showSql);
        jpaProperties.put("hibernate.format_sql", formatSql);
        jpaProperties.put("hibernate.event.merge.entity_copy_observer", "allow");
        return jpaProperties;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
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

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(false);
        filter.setIncludeHeaders(true);
        return filter;
    }
}
