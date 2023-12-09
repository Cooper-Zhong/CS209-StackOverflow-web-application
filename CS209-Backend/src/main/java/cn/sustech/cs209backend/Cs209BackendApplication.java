package cn.sustech.cs209backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@org.springframework.boot.autoconfigure.domain.EntityScan(basePackages = {"cn.sustech.cs209backend.entity"})
@org.springframework.data.jpa.repository.config.EnableJpaRepositories(basePackages = {"cn.sustech.cs209backend.repo"})

public class Cs209BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(Cs209BackendApplication.class, args);
    }

}
