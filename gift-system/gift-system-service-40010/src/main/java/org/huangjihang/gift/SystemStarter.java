package org.huangjihang.gift;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SystemStarter {
    public static void main(String[] args) {
        SpringApplication.run(SystemStarter.class,args);
    }
}
