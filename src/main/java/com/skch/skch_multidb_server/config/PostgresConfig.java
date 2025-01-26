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
@EnableJpaRepositories(basePackages = "com.skch.skch_multidb_server.postgres",
		entityManagerFactoryRef = "postgresEntityManagerFactory", 
		transactionManagerRef = "postgresTransactionManager")
public class PostgresConfig {

    @Bean(name = "postgresDataSourceProperties")
    @ConfigurationProperties("spring.datasource.postgres")
    DataSourceProperties db1DataSourceProperties() {
		return new DataSourceProperties();
	}

    @Bean(name = "postgresDataSource")
    DataSource db1DataSource(
            @Qualifier("postgresDataSourceProperties") DataSourceProperties postgresDataSourceProperties) {
		return postgresDataSourceProperties.initializeDataSourceBuilder().build();
	}

    @Bean(name = "postgresEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean postgresEntityManagerFactory(
            @Qualifier("postgresDataSource") DataSource postgresDataSource) {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(postgresDataSource);
		emf.setPackagesToScan("com.skch.skch_multidb_server.postgres");

		// Specify Hibernate as the JPA provider
		emf.setPersistenceProviderClass(org.hibernate.jpa.HibernatePersistenceProvider.class);

		// Configure other JPA properties as needed
		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		emf.setJpaPropertyMap(properties);

		return emf;
	}

    @Bean(name = "postgresTransactionManager")
    PlatformTransactionManager postgresTransactionManager(
            @Qualifier("postgresEntityManagerFactory") EntityManagerFactory postgresEntityManagerFactory) {
		return new JpaTransactionManager(postgresEntityManagerFactory);
	}

}
