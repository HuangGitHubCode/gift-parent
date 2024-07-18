package org.huangjihang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("org.huangjihang.gift.mapper")
public class MPStarter {
    public static void main(String[] args) {
        SpringApplication.run(MPStarter.class,args);
    }
}
