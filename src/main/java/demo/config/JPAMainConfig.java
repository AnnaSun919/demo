package demo.config;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;
import javax.sql.DataSource;




import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import demo.common.constants.AppConstants;
import demo.db.CommonJavaRepositoryImpl;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
	basePackages = "demo.db.main", //all repositories under this basePackages will use this connection pool 
	entityManagerFactoryRef = "entityManagerFactoryDemoMain", //this name must be unique throughout all java application within same tomcat, format : entityManagerFactoryXXXXyyy (XXXX stands for application name, yyy stands for DB name)
	transactionManagerRef = "transactionManagerDemoMain", //this name must be unique throughout all java application within same tomcat , format : transactionManagerXXXXyyy (XXXX stands for application name, yyy stands for DB name)
	repositoryBaseClass = CommonJavaRepositoryImpl.class
)
public class JPAMainConfig {

	@Inject
	private Environment env;
	
	
	@Bean
	@Primary //Only Main DB is needed to set Primary
	//this name must be unique throughout all java application within same tomcat, format : entityManagerFactoryXXXXyyy (XXXX stands for application name, yyy stands for DB name)
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryDemoMain() { 
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSourceDemoMain());
		em.setPackagesToScan(new String[] { "demo.db.main" }); //all repositories under this basePackages will use this connection pool 
 
		Map<String,Object> props = new HashMap<String,Object>();
		props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		em.setJpaPropertyMap(props);
		
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		return em;
	}
 
	@Bean
	//@Primary //Only Main DB is needed to set Primary
	//this name must be unique throughout all java application within same tomcat, format : sessionFactoryXXXXyyy (XXXX stands for application name, yyy stands for DB name)
	public LocalSessionFactoryBean sessionFactoryDemoMain() { 
		Properties prop = new Properties();
		prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSourceDemoMain());
		sessionFactory.setPackagesToScan(new String[] { "demo.db.main" }); //all repositories under this basePackages will use this connection pool 
		sessionFactory.setHibernateProperties(prop);
		
		return sessionFactory;
	}
   
	@Bean
	@Primary //Only Main DB is needed to set Primary
	//function name must be unique throughout all java application within same tomcat, format : dataSourceXXXXyyy (XXXX stands for application name, yyy stands for DB name)
	public DataSource dataSourceDemoMain() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty(AppConstants.DB_DRIVER));
		dataSource.setUrl(env.getProperty(AppConstants.MAIN_DB_URL));
		dataSource.setUsername(env.getProperty(AppConstants.MAIN_DB_USERNAME));
		dataSource.setPassword(env.getProperty(AppConstants.MAIN_DB_PASSWORD));
		dataSource.setMaxTotal(Integer.parseInt(env.getProperty(AppConstants.DB_MAX_ACTIVE)));
		dataSource.setMaxIdle(Integer.parseInt(env.getProperty(AppConstants.DB_MAX_IDLE)));
		dataSource.setMaxWaitMillis(Integer.parseInt(env.getProperty(AppConstants.DB_MAX_WAIT)));
		dataSource.setValidationQuery("SELECT 1 FROM DUAL");
		return dataSource;
		//return new Log4jdbcProxyDataSource(dataSource); //log4jdbc, for pre-dev only
	}
   
	@Bean
	@Primary //Only Main DB is needed to set Primary
	//function name must be unique throughout all java application within same tomcat, format : transactionManagerXXXXyyy (XXXX stands for application name, yyy stands for DB name)
	public PlatformTransactionManager transactionManagerDemoMain() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryDemoMain().getObject());
 
		return transactionManager;
	}
 
	@Bean
	@Primary //Only Main DB is needed to set Primary
	//function name must be unique throughout all java application within same tomcat, format : exceptionTranslationXXXXyyy (XXXX stands for application name, yyy stands for DB name)
	public PersistenceExceptionTranslationPostProcessor exceptionTranslationDemoMain() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

}