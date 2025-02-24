package org.example.sastwoc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;



@SpringBootApplication

@EnableWebMvc
@MapperScan("org.example.sastwoc.mapper")
public class SastwocApplication {

    public static void main(String[] args) {
        SpringApplication.run(SastwocApplication.class, args);
    }

}
