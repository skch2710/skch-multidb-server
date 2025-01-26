package com.skch.skch_multidb_server.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.skch.skch_multidb_server.sql",
        entityManagerFactoryRef = "sqlEntityManagerFactory",
        transactionManagerRef = "sqlTransactionManager"
)
public class SqlConfig {

    @Bean(name = "sqlDataSourceProperties")
    @ConfigurationProperties("spring.datasource.sql")
    DataSourceProperties sqlDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "sqlDataSource")
    DataSource sqlDataSource(@Qualifier("sqlDataSourceProperties") DataSourceProperties sqlDataSourceProperties) {
        System.out.println("URL: " + sqlDataSourceProperties.getUrl());
        System.out.println("Driver: " + sqlDataSourceProperties.getDriverClassName());
        return sqlDataSourceProperties.initializeDataSourceBuilder().build();
    }


    @Bean(name = "sqlEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean sqlEntityManagerFactory(
            @Qualifier("sqlDataSource") DataSource sqlDataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(sqlDataSource);
        emf.setPackagesToScan("com.skch.skch_multidb_server.sql");

        // Specify Hibernate as the JPA provider
        emf.setPersistenceProviderClass(org.hibernate.jpa.HibernatePersistenceProvider.class);

        // Configure other JPA properties as needed
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
        emf.setJpaPropertyMap(properties);

        return emf;
    }

    @Bean(name = "sqlTransactionManager")
    PlatformTransactionManager sqlTransactionManager(
            @Qualifier("sqlEntityManagerFactory") EntityManagerFactory sqlEntityManagerFactory) {
        return new JpaTransactionManager(sqlEntityManagerFactory);
    }

}
