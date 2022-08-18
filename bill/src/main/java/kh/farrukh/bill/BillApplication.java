package kh.farrukh.bill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BillApplication {

    // TODO: 8/18/22 connect to other services (user, stats)
    // TODO: 8/18/22 remove utils (and global resources) from this service
    // TODO: 8/18/22 add security
    // TODO: 8/18/22 full code refactor/review & apply best practices (microservices)

    public static void main(String[] args) {
        SpringApplication.run(BillApplication.class, args);
    }
}
