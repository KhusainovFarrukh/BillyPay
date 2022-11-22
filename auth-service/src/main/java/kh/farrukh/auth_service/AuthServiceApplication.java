package kh.farrukh.auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;

@EnableEurekaClient
@SpringBootApplication(scanBasePackages = {
        "kh.farrukh.auth_service", "kh.farrukh.common"
})
@EnableFeignClients(basePackages = "kh.farrukh.feign_clients")
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
