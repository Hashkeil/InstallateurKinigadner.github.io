package at.DiplomProjekt.Installateur.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {


    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.driverClassName("com.mysql.cj.jdbc.Driver");
        builder.url("jdbc:mysql://localhost:3306/billing?useSSL=false&serverTimezone=UTC");
        builder.username("root");
        builder.password("412020");
        return builder.build();
    }

}


