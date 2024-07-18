package org.huangjihang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EurekaStarter {
    public static void main(String[] args) {
        SpringApplication.run(EurekaStarter.class,args);
    }
}
