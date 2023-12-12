package cn.sustech.cs209backend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@Slf4j
@CrossOrigin(origins = "*") // 允许所有域的跨域请求
public class Cs209BackendApplication {

    public static void main(String[] args) {
        log.debug("=====测试日志debug级别打印====");
        log.info("======测试日志info级别打印=====");
        log.error("=====测试日志error级别打印====");
        log.warn("======测试日志warn级别打印=====");
        SpringApplication.run(Cs209BackendApplication.class, args);
    }

}
