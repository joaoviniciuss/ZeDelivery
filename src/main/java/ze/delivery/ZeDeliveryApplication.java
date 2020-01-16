package ze.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
@PropertySource("classpath:application.yml")
@EntityScan(basePackages = "ze.delivery")
public class ZeDeliveryApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZeDeliveryApplication.class, args);
    }

}
