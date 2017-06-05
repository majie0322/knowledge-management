package com.huizhi.management.conf;

import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * neo4j数据库配置
 * @author onlyo
 */
@org.springframework.context.annotation.Configuration
@EnableNeo4jRepositories(basePackages = "com.huizhi.management.dao")
@EnableTransactionManagement
public class Neo4jConfig extends Neo4jConfiguration {

    private static String DOMAIN_PACKAGE = "com.huizhi.management.entity";

    @Value("${neo4j.driver}")
    private String neoDriverClass ;

    @Value("${neo4j.url}")
    private String neoUrl;

    @Bean
    public Configuration configuration(){
        Configuration configuration = new Configuration();
        configuration.driverConfiguration().setDriverClassName(neoDriverClass).setURI(neoUrl);

        return configuration;
    }

    /**
     * 这里有个坑：
     * 根据不同的springboot的版本注入SessionFactory的方法不一样
     * 在低版本中只要这样注入即可，但是在1.5.X.RELEASE中则不能这样注入
     * 可以在测试中打印出该对象，可以发现高版本的sessionFactory的值为null
     <parent>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-parent</artifactId>
     <version>1.4.3.RELEASE</version>
     <relativePath/> <!-- lookup parent from repository -->
     </parent>
     * @return
     */
    @Bean
    @Override
    public SessionFactory getSessionFactory() {
        return new SessionFactory(configuration(),DOMAIN_PACKAGE);
    }
}
