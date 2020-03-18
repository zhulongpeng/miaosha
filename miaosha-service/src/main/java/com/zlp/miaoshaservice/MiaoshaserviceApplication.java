package com.zlp.miaoshaservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zlp.miaoshadao.mapper")
public class MiaoshaserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiaoshaserviceApplication.class, args);
    }

}
