package com.dbtest.dbkeshe;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.dbtest.dbkeshe.repository")
public class DbkesheApplication {

    public static void main(String[] args) {
        SpringApplication.run(DbkesheApplication.class, args);

    }
}
