package ze.delivery;

import org.junit.ClassRule;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;

import static java.util.Objects.nonNull;

@Lazy
@Configuration
@EnableAutoConfiguration
public class PostgresSQLTestsConfiguration {

    @ClassRule
    private static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11-alpine");

    static {
        postgreSQLContainer.withDatabaseName("zeDelivery");
        postgreSQLContainer.withUsername("postgres");
        postgreSQLContainer.withPassword("postgres");
        postgreSQLContainer.start();
    }

    @Bean
    @Scope("singleton")
    public PostgreSQLContainer postgreSQLContainer() {
        return postgreSQLContainer;
    }

    @Bean
    @Scope("singleton")
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .url(postgreSQLContainer.getJdbcUrl())
                .username(postgreSQLContainer.getUsername())
                .password(postgreSQLContainer.getPassword())
                .driverClassName(postgreSQLContainer.getDriverClassName())
                .build();
    }

    public String getJdbcUrl() {
        return nonNull(postgreSQLContainer) ? postgreSQLContainer.getJdbcUrl() : null;
    }

}
