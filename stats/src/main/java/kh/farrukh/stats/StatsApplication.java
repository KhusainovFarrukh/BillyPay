package kh.farrukh.stats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "kh.farrukh.clients")
@PropertySource("classpath:clients.properties")
public class StatsApplication {

    // TODO: 8/18/22 connect to other services (bill)
    // TODO: 8/18/22 remove utils (and global resources) from this service
    // TODO: 8/18/22 add security
    // TODO: 8/18/22 full code refactor/review & apply best practices (microservices)

    public static void main(String[] args) {
        SpringApplication.run(StatsApplication.class, args);
    }
}
