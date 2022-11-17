package kh.farrukh.bill_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
        "kh.farrukh.bill_service",
        "kh.farrukh.amqp"
})
@EnableEurekaClient
@EnableFeignClients(basePackages = "kh.farrukh.feign_clients")
public class BillServiceApplication {

    // TODO: 8/18/22 add security
    // TODO: 8/18/22 full code refactor/review & apply best practices (microservices)

    public static void main(String[] args) {
        SpringApplication.run(BillServiceApplication.class, args);
    }
}
