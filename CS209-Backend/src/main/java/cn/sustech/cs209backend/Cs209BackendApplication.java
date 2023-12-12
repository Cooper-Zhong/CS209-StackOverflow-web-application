package cn.sustech.cs209backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@org.springframework.boot.autoconfigure.domain.EntityScan(basePackages = {"cn.sustech.cs209backend.entity"})
@org.springframework.data.jpa.repository.config.EnableJpaRepositories(basePackages = {"cn.sustech.cs209backend.repo"})

public class Cs209BackendApplication {

    public static void main(String[] args) {
        log.debug("=====测试日志debug级别打印====");
        log.info("======测试日志info级别打印=====");
        log.error("=====测试日志error级别打印====");
        log.warn("======测试日志warn级别打印=====");
        SpringApplication.run(Cs209BackendApplication.class, args);
    }

}
