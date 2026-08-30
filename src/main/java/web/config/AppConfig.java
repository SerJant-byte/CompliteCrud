package web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.sql.DataSource;
import java.util.Properties;

@PropertySource("classpath:db.properties")
//@PropertySource ищет файл в classpath, а resources при сборке становится корнем classpath.
@Configuration
@EnableTransactionManagement
public class AppConfig {
    // источник конфигурации приложения

    private Environment environment;
    //Это объект Spring, через который можно получить значения свойств

    public AppConfig(Environment environment) {
        //Spring создаёт AppConfig и передаёт ему Environment
        this.environment = environment;
    }

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

        driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        driverManagerDataSource.setUrl(environment.getProperty("db.url"));
        driverManagerDataSource.setUsername(environment.getProperty("db.username"));
        driverManagerDataSource.setPassword(environment.getProperty("db.password"));

        return driverManagerDataSource;
    }
    @Bean
    // Мы создаём Spring Bean, который отвечает за создание и настройку EntityManagerFactory
    public LocalContainerEntityManagerFactoryBean getLocalFactory() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        //Наш JPA-провайдер — Hibernate
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean= new LocalContainerEntityManagerFactoryBean();
        // Создали объект, который Спринг будет использовать для настройки EntityManagerFactory
        Properties properties = new Properties();
        // Создали пустой контейнер настроек, потом его заполнили и передали в обьект
        // который Спринг будет использовать для настройки EntityManagerFactory
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        localContainerEntityManagerFactoryBean.setJpaProperties(properties);
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        //Связываем фабрику с Hibernate
        localContainerEntityManagerFactoryBean.setDataSource(getDataSource());
        //говорим EntityManagerFactory - вот через это подключение работай с базой данных
        localContainerEntityManagerFactoryBean.setPackagesToScan("web.model");
        // Просканируй пакет web.model и найди там Entity
        return localContainerEntityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager getPlatformTra() {
        //Это менеджер транзакций
        PlatformTransactionManager platformTransactionManager = new JpaTransactionManager(getLocalFactory().getObject());
        // getLocalFactory() -> LocalContainerEntityManagerFactoryBean ->
        // из getObject получаем EntityManagerFactory -> и его передаем в JpaTransactionManager
        return platformTransactionManager;
    }


}
