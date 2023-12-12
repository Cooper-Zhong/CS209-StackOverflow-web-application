package cn.sustech.cs209backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "*") // 允许所有域的跨域请求
public class Cs209BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(Cs209BackendApplication.class, args);
    }

}
