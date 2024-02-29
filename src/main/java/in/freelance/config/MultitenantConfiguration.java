package in.freelance.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class MultitenantConfiguration {

    @Bean
    public DataSource dataSource(){
        File[] files = Paths.get("src/main/resources/tenants").toFile().listFiles();
        Map<Object, Object> resolvedDataSource = new HashMap<>();


        for(File propertyFile : files){
            Properties tenantProperties =  new Properties();
            DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();

            try {
                tenantProperties.load(new FileInputStream(propertyFile));
                String tenantId = tenantProperties.getProperty("name");

                dataSourceBuilder.driverClassName(tenantProperties.getProperty("spring.datasource.driver-class-name"));
                dataSourceBuilder.username(tenantProperties.getProperty("spring.datasource.username"));
                dataSourceBuilder.password(tenantProperties.getProperty("spring.datasource.password"));
                dataSourceBuilder.url(tenantProperties.getProperty("spring.datasource.url"));

                resolvedDataSource.put(tenantId, dataSourceBuilder.build());
                } catch (IOException e) {
                 throw new RuntimeException("Problem in tenant data source" + e);
            }
        }

        AbstractRoutingDataSource tenantRoutingDataSource = new TenantRoutingDataSource();
        tenantRoutingDataSource.setDefaultTargetDataSource(resolvedDataSource.get("tenant_1"));
        tenantRoutingDataSource.setTargetDataSources(resolvedDataSource);
        tenantRoutingDataSource.afterPropertiesSet();
        return tenantRoutingDataSource;
    }
}
