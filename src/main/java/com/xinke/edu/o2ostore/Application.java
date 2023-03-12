package com.xinke.edu.o2ostore;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Administrator
 */
@SpringBootApplication
@MapperScan(basePackages = "com.xinke.edu.o2ostore.mapper")
@ComponentScan(basePackages = {"com.xinke.edu.o2ostore.controller", "com.xinke.edu.o2ostore.service", "com.xinke.edu.o2ostore.util"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
