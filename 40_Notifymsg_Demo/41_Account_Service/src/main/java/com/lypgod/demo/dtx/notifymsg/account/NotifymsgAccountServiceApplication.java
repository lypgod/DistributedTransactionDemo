package com.lypgod.demo.dtx.notifymsg.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author lypgod
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableFeignClients
public class NotifymsgAccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotifymsgAccountServiceApplication.class, args);
    }

}
